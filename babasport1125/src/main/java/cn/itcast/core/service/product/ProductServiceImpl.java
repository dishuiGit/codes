package cn.itcast.core.service.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.Img;
import cn.itcast.core.bean.product.ImgQuery;
import cn.itcast.core.bean.product.ImgQuery.Criteria;
import cn.itcast.core.bean.product.Product;
import cn.itcast.core.bean.product.ProductQuery;
import cn.itcast.core.bean.product.ProductWithBLOBs;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.bean.product.SkuQuery;
import cn.itcast.core.dao.product.ImgMapper;
import cn.itcast.core.dao.product.ProductMapper;
import cn.itcast.core.dao.product.SkuMapper;
import cn.itcast.core.service.staticpage.StaticPageService;

/**
 * 商品
 * 
 * @author lx
 * 
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ImgMapper imgMapper;
	@Autowired
	private SkuMapper skuMapper;

	@Override
	public Pagination selectByExample(ProductQuery example) {
		// TODO Auto-generated method stub
		// 创建 分页对象
		Pagination pagination = new Pagination(example.getPageNo(),
				example.getPageSize(), productMapper.countByExample(example));

		List<Product> products = productMapper.selectByExample(example);

		for (Product product : products) {
			ImgQuery imgQuery = new ImgQuery();
			// 商品ID
			Criteria imgQ = imgQuery.createCriteria();
			imgQ.andProductIdEqualTo(product.getId());
			imgQ.andIsDefEqualTo(true);
			// 是默认图片
			// 加载图片 只有一张图
			List<Img> imgs = imgMapper.selectByExample(imgQuery);

			product.setImg(imgs.get(0));
		}

		pagination.setList(products);
		return pagination;
	}

	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;

	// 保存商品 保存图片 保存库存
	public void addProductAndImgAndSku(ProductWithBLOBs product) {
		// 获取jedis
		Jedis redis = jedisConnectionFactory.getShardInfo().createResource();
		// 编号
		Long pno = redis.incr("pno");
		// 设置编号到商品中去
		product.setNo("" + pno);
		// 添加时间
		product.setCreateTime(new Date());
		// 下架
		product.setIsShow(false);
		// 不删除
		product.setIsDel(true);

		// 保存商品 返回ID
		productMapper.insert(product);

		// 保存图片
		Img img = product.getImg();
		// 商品ID
		img.setProductId(product.getId());
		// 默认
		img.setIsDef(true);

		imgMapper.insert(img);

		// 保存SKu
		Sku sku = null;
		for (String color : product.getColor().split(",")) {
			// 实例化
			sku = new Sku();
			// 商品ID
			sku.setProductId(product.getId());
			// 颜色ID
			sku.setColorId(Integer.parseInt(color));
			for (String size : product.getSize().split(",")) {
				sku.setSize(size);
				// 价格
				sku.setSkuPrice(0.00);
				// 运费 默认10元
				sku.setDeliveFee(10.00);
				// 库存
				sku.setStockInventory(0);
				// 默认200件
				sku.setSkuUpperLimit(200);
				// 市场价
				sku.setMarketPrice(0.00);
				// 时间
				sku.setCreateTime(new Date());
				//
				sku.setLastStatus(1);
				//
				sku.setSkuType(1);

				sku.setSales(0);
				// 保存
				skuMapper.insert(sku);

			}

		}

	}

	@Autowired
	private SolrServer solrServer;

	// 上架
	public void isShow(Integer[] ids) {
		ProductWithBLOBs product = new ProductWithBLOBs();
		// 1:上架
		product.setIsShow(true);
		for (Integer id : ids) {
			product.setId(id);
			// 更改状态
			productMapper.updateByPrimaryKeySelective(product);
			// 2:发送数据到Solr服务器
			// 创建SolrInputDocument
			SolrInputDocument doc = new SolrInputDocument();
			// 1:商品ID
			doc.setField("id", id);
			// 2:商品名称 中文分词
			ProductWithBLOBs productWithBLOBs = productMapper
					.selectByPrimaryKey(id);
			doc.setField("name_ik", productWithBLOBs.getName());
			// 3:图片Url
			ImgQuery imgQuery = new ImgQuery();
			Criteria createCriteria = imgQuery.createCriteria();
			createCriteria.andProductIdEqualTo(id).andIsDefEqualTo(true);

			List<Img> imgs = imgMapper.selectByExample(imgQuery);
			doc.setField("url", imgs.get(0).getUrl());
			// 4:价格 Sku 中 第一个的价格 (可选有库存)
			SkuQuery skuQuery = new SkuQuery();
			cn.itcast.core.bean.product.SkuQuery.Criteria createCriteria2 = skuQuery
					.createCriteria();
			createCriteria2.andProductIdEqualTo(id);
			// 设置Limit 1 limit 开始行 , 每页数 limit 1 limit 0,1 limit
			// (pageNo-1)*pageSize,pageSize
			skuQuery.setPageNo(1);
			skuQuery.setPageSize(1);
			List<Sku> skus = skuMapper.selectByExample(skuQuery);
			doc.setField("price", skus.get(0).getSkuPrice());

			// 5:时间
			doc.setField("last_modified", new Date());

			// 6:品牌ID
			doc.setField("brandId", productWithBLOBs.getBrandId());
			// 保存
			try {
				solrServer.add(doc);
				solrServer.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// TODO
			// 3:静态化
			//数据
			Map<String,Object> root = new HashMap<String,Object>();
			
			//加载的商品信息  名   介绍  清单
			ProductWithBLOBs p = selectProductWithBLOBsById(id);
			
			root.put("product", p);
			//加载SKu集合  要求有库存
			List<Sku> skuss = skuService.selectSkusByProductId(id);
			root.put("skus", skuss);
			//去重  Set  Color对象
			Set<Color> colors = new HashSet<Color>();
			for (Sku sku : skuss) {
				colors.add(sku.getColor());
			}
			root.put("colors", colors);
			// TODO
			staticPageService.index(root, id);
			
		}
	}
	@Autowired
	private StaticPageService staticPageService;

	@Autowired
	private SkuService skuService;

	// 查询Solr
	public Pagination selectPByKeyword(SolrQuery params,StringBuilder p,Integer pageNo) {
		//高亮设置
		//开关  <span>  name_ik 瑜伽服
		params.setHighlight(true);
		//指定高亮字段
		params.addHighlightField("name_ik");
		//指定要求高亮字段如何进行显示  <span style="color:red">瑜伽服</span>
		//前缀
		params.setHighlightSimplePre("<span style='color:red'>");
		//后缀
		params.setHighlightSimplePost("</span>");
		
		
		//声明    商品集合
		List<Product> products = new ArrayList<Product>();
		
		//商品对象
		Product product = null;

		// 查询
		QueryResponse response = null;
		try {
			response = solrServer.query(params);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 结果集
		SolrDocumentList results = response.getResults();
		//高亮结果  id : 
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		//总条数
		long numFound = results.getNumFound();
		
		//遍历结果
		for (SolrDocument solrDocument : results) {
			//创建新的
			product = new Product();
			//把ID取出
			String id = (String) solrDocument.get("id");
			product.setId(Integer.parseInt(id));
			//此Id对应那条一数据 
			//1:name_ik :sfsfs  
			//2:price : 666 ...
			Map<String, List<String>> map = highlighting.get(id);
			//取name_ik值  瑜伽服,手机,电脑
			List<String> list = map.get("name_ik");
			// <span style="color:red">瑜伽服</span>
			String name = list.get(0);
			//商品名
			//String name = (String) solrDocument.get("name_ik");
			product.setName(name);
			//价格
			float price = (float) solrDocument.get("price");
			product.setPrice(price);
			//图片 相对路径
			String url = (String) solrDocument.get("url");
			Img img = new Img();
			img.setUrl(url);
			product.setImg(img);
			//把商品添加到商品集合中
			products.add(product);
			
		}
		//solr服务器 0 5 
		// 创建 分页对象
		// 分页对象    
		//第一个参数: 当前页
		//第二个参数 :每页数
		//第三个参数:符合条件的总条数 (pageNo-1)*pageSize = startRow
		Pagination pagination = new Pagination(pageNo,params.getRows(), (int)numFound);
		//分页页面展示
		String url = "/product/display/list.shtml";
		pagination.pageView(url, p.toString());
		
		pagination.setList(products);
		
		return pagination;
	}
	//获取一个商品的信息
	public ProductWithBLOBs selectProductWithBLOBsById(Integer id){
		//查询商品
		ProductWithBLOBs product = productMapper.selectByPrimaryKey(id);
		
		ImgQuery imgQuery = new ImgQuery();
		// 商品ID
		Criteria imgQ = imgQuery.createCriteria();
		imgQ.andProductIdEqualTo(product.getId());
		imgQ.andIsDefEqualTo(true);
		// 是默认图片
		// 加载图片 只有一张图
		List<Img> imgs = imgMapper.selectByExample(imgQuery);

		product.setImg(imgs.get(0));
		
		return product;
	}

}
