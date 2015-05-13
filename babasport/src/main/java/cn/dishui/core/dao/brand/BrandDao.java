package cn.dishui.core.dao.brand;

import java.util.List;

import cn.dishui.core.po.product.Brand;
import cn.dishui.core.po.product.BrandQuery;

public interface BrandDao {
	/**
	 * 查询品牌总数量
	 * @param brandQuery 
	 * @return 品牌总数量
	 */
	public Integer selectBrandCount(BrandQuery brandQuery);

	public List<Brand> selectBrandList(BrandQuery brandQuery);
	/**
	 * 插入一条数据
	 * @param brand
	 */
	public void insertBrand(Brand brand);
	
	public void deleteBrands(Integer[] ids);
	/**
	 * 品牌option下拉选
	 * @return
	 */
	public List<Brand> selectBrandsForOption(BrandQuery brandQuery);
	
	public Brand selectBrandById(Integer id);
}
