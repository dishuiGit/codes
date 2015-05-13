package cn.dishui.core.service.impl.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import cn.dishui.core.dao.product.ColorMapper;
import cn.dishui.core.dao.product.ImgMapper;
import cn.dishui.core.dao.product.ProductMapper;
import cn.dishui.core.dao.product.SkuMapper;
import cn.dishui.core.po.product.Img;
import cn.dishui.core.po.product.ImgQuery;
import cn.dishui.core.po.product.ImgQuery.Criteria;
import cn.dishui.core.po.product.Color;
import cn.dishui.core.po.product.Product;
import cn.dishui.core.po.product.ProductQuery;
import cn.dishui.core.po.product.ProductWithBLOBs;
import cn.dishui.core.po.product.Sku;
import cn.dishui.core.po.product.SkuQuery;
import cn.dishui.core.service.service.StaticPageService;
import cn.dishui.core.service.service.product.ProductService;
import cn.itcast.common.page.Pagination;

@Service
public class ProductServiceImpl implements ProductService {
	@Resource
	private ImgMapper imgMapper;
	@Resource
	private SkuMapper skuMapper;
	@Resource
	private ProductMapper productMapper;

	public Pagination selectByProductQuery(ProductQuery productQuery) {
		// pagiation,pageNo,startRow,
		// 每页显示数
		productQuery.setPageSize(5);
		// 总数量
		Integer totalCount = productMapper.countByExample(productQuery);
		List<Product> productList = productMapper.selectByExample(productQuery);
		Pagination pagination = new Pagination(productQuery.getPageNo(),
				productQuery.getPageSize(), totalCount, productList);

		return pagination;
	}

	// 保存商品
	@Resource
	private JedisConnectionFactory jedisConnectionFactory;

	public void addProduct(ProductWithBLOBs product) {
		// (商品表)商品类型,商品名称,商品品牌,商品毛重,材质,颜色,尺码,状态
		// 商品编号,通过redis生成,添加时间,上下架:0否 1是
		// 商品编号
		Jedis jedis = jedisConnectionFactory.getShardInfo().createResource();
		Long pno = jedis.incr("pno");
		product.setNo(pno + "");
		// 添加时间
		product.setCreateTime(new Date());
		// 下架
		product.setIsShow(false);
		// TODO 保存商品,返回id
		productMapper.insert(product);
		// (图片表) 商品ID 图片URL 是否默认:0否 1:是
		Img img = new Img();
		// 商品ID
		img.setProductId(product.getId());
		// 图片URL
		img.setUrl(product.getImg().getUrl());
		// 是否默认
		img.setIsDef(true);
		// 保存Img
		imgMapper.insert(img);
		// 保存sku表(销售最小单元)
		// ----------

		// 颜色ID,尺码(笛卡儿积)
		for (String colorId : product.getColor().split(",")) {
			for (String size : product.getSize().split(",")) {
				Sku sku = new Sku();
				sku.setColorId(Integer.parseInt(colorId));
				// 商品ID,
				sku.setProductId(product.getId());
				// 运费 默认10元
				sku.setDeliveFee(10.0);
				// 售价
				sku.setSkuPrice(666.0);
				// 市场价
				sku.setMarketPrice(888.0);
				// create_time
				sku.setCreateTime(product.getCreateTime());
				// 尺码
				sku.setSize(size);
				//TODO 库存[强制类型](不写报错)
				sku.setStockInventory(200);
				//保存sku
				skuMapper.insert(sku);
			}
		}

		//

	}
	
	@Autowired
	private SolrServer solrServer;
	@Resource
	private ColorMapper colorMapper;
	public void upRack(Integer[] ids) {
		//1,改变商品状态
		ProductWithBLOBs product = new ProductWithBLOBs();
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		for(Integer id : ids){
			product.setId(id);
			product.setIsShow(false);
			//部分更新(需要id)
			productMapper.updateByPrimaryKeySelective(product);
			//2,添加商品到solr服务器
			SolrInputDocument doc = new SolrInputDocument();
				//2.1,商品图片(img表)
			ImgQuery imgQuery = new ImgQuery();
				//select 图片表 from 图片表 where 商品id=id and 是否默认=true
			imgQuery.createCriteria().andProductIdEqualTo(id).andIsDefEqualTo(true);
			imgQuery.setOrderByClause("id desc");
				//默认(升序排列)第一个
			List<Img> imgs = imgMapper.selectByExample(imgQuery);
			
			doc.setField("url", imgs.get(0).getUrl());
				//2.2,商品名称(product表)
			ProductWithBLOBs qProduct = productMapper.selectByPrimaryKey(id);
			doc.setField("name_ik", qProduct.getName());
				//2.3,商品价格(sku表或在product对象中添加price字段)
			SkuQuery skuQuery = new SkuQuery();
			skuQuery.createCriteria().andProductIdEqualTo(id);
			skuQuery.setOrderByClause("id asc");
			
			List<Sku> skus = skuMapper.selectByExample(skuQuery);
			//添加域到文档
			doc.setField("price", skus.get(0).getSkuPrice());
			//TODO 设置id(必需品)
			doc.setField("id", id);
			//设置brandId,createTime(查询用)
			doc.setField("brandId", qProduct.getBrandId());
			doc.setField("createTime", qProduct.getCreateTime());
			docs.add(doc);
			//=======================
			//      生成静态页                    //
			//=======================
			Map<String,Object> root = new HashMap<String, Object>();
			//回显
			root.put("imgAllUrl", imgs.get(0).getAllUrl() );
			//商品表:名称(name),介绍(description),规格参数,包装清单(pack_list)
			ProductWithBLOBs sproduct = selectByKey(id);
			root.put("product", sproduct );
			//最小销售单元表:(sku) 销售价,市场价,运费,库存,颜色
			/*SkuQuery skuQuery = new SkuQuery();
			skuQuery.createCriteria().andProductIdEqualTo(id);
			skuQuery.setOrderByClause("id asc");
			List<Sku> skus = skuMapper.selectByExample(skuQuery);
			*/
			root.put("skus", skus);
			//颜色
			List<Color> colors = colorMapper.selectColorsByPId(id);
			root.put("colors", colors);
			staticPageService.index2(root, id);
		}
		//添加文档集合到solrServer
		try {
			//提交
			solrServer.add(docs);
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Resource
	private StaticPageService staticPageService;
	//====================
	//      SolrQuery   //                 
	//====================
	
	public Pagination solrQuery(SolrQuery solrQuery,Integer pageNo){
		//每页显示个数
		int pageSize = 8;
		//设置solr查询分页
		solrQuery.setStart((pageNo-1)*pageSize);
		solrQuery.setRows(pageSize);
		//查询结果
		QueryResponse result = null;
		//productList
		try {
			result = solrServer.query(solrQuery);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		//Map<String,List<String>>
		SolrDocumentList docs = result.getResults();
		List<Product> products = new ArrayList<Product>();
		for(SolrDocument doc:docs){
			Product p = new Product();
			Img img = new Img();
			//商品id
			p.setId(Integer.valueOf(doc.getFieldValue("id")+""));
			//name
			p.setName(doc.getFieldValue("name_ik")+"");
			//price
			p.setPrice(Double.valueOf(doc.getFieldValue("price")+""));
			//图片url
			img.setUrl(doc.getFieldValue("url")+"");
			p.setImg(img);
			products.add(p);
		}
		//分页对象
		Pagination pagination = new Pagination();
		
		pagination.setPageSize(pageSize);
		pagination.setTotalCount(Integer.valueOf(docs.getNumFound()+""));
		pagination.setList(products);
		
		return pagination;
	}
	
	//========================
	//       单品页                          //
	//========================
	//查询单个商品
	public ProductWithBLOBs selectByKey(Integer id){
		return productMapper.selectByPrimaryKey(id);
	}
}
