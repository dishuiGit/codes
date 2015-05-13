package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Brand;
import cn.itcast.core.bean.product.BrandQuery;

public interface BrandService {

	

	//符合条件的  分页对象
	public Pagination selectBrandListWithWhere(BrandQuery brandQuery);
	//品牌 结果集
	public List<Brand> selectBrandList(BrandQuery brandQuery);
	
	//添加品牌
	public void addBrand(Brand brand);
	
	
	//批量删除
	public void deleteBrands(Integer[] ids);
	
	//查询品牌
	public Brand selectBrandById(Integer id);
	//修改
	public void updateBrandById(Brand brand);
}
