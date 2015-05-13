package cn.dishui.core.service.service.product;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.transaction.annotation.Transactional;

import cn.dishui.core.po.product.ProductQuery;
import cn.dishui.core.po.product.ProductWithBLOBs;
import cn.itcast.common.page.Pagination;

@Transactional
public interface ProductService {
	/**
	 * 根据查询条件分页查找
	 * 
	 * @param productQuery
	 * @return 分页对象
	 */
	public Pagination selectByProductQuery(ProductQuery productQuery);

	/**
	 * 保存商品
	 * 
	 * @param record
	 */
	public void addProduct(ProductWithBLOBs record);

	/**
	 * 上架
	 * 
	 * @param ids
	 */
	public void upRack(Integer[] ids);

	/**
	 * solrQuery
	 * 
	 * @param query
	 * @return
	 */
	public Pagination solrQuery(SolrQuery query, Integer pageNo);

	// 查询单个商品
	public ProductWithBLOBs selectByKey(Integer id);

}
