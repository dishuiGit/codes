package cn.dishui.core.service.service.brand;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.dishui.core.po.product.Brand;
import cn.dishui.core.po.product.BrandQuery;
import cn.itcast.common.page.Pagination;

@Transactional
public interface BrandService {
	/**
	 * 查询所有品牌分类
	 * @return
	 */

	public Pagination selectPagination(BrandQuery brandQuery);
	/**
	 * 保存品牌分类
	 * @param brand
	 */
	public void save(Brand brand);
	/**
	 * 批量删除品牌分类
	 * @param ids
	 */
	public void batchDelete(Integer[] ids);
	/**
	 * 为option下拉选查询品牌分类
	 * @param brandQuery
	 */
	public List<Brand> selectOptionBrands(BrandQuery brandQuery);
	public Brand selectBrandById(Integer brandId);
	
}
