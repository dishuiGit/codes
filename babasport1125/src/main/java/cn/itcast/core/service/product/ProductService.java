package cn.itcast.core.service.product;

import org.apache.solr.client.solrj.SolrQuery;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.ProductQuery;
import cn.itcast.core.bean.product.ProductWithBLOBs;

public interface ProductService {

	// 返回 分页对象
	public Pagination selectByExample(ProductQuery example);

	// 保存商品 保存图片 保存库存
	public void addProductAndImgAndSku(ProductWithBLOBs product);

	// 上架
	public void isShow(Integer[] ids);

	// 查询Solr
	public Pagination selectPByKeyword(SolrQuery params, StringBuilder p,
			Integer pageNo);

	// 获取一个商品的信息
	public ProductWithBLOBs selectProductWithBLOBsById(Integer id);
}
