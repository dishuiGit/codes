package cn.itcast.core.dao.product;

import java.util.List;

import cn.itcast.core.bean.product.Brand;
import cn.itcast.core.bean.product.BrandQuery;

public interface BrandDao {

	//符合条件的总条数
	public Integer selectBrandCount(BrandQuery brandQuery);
	//符合条件的List结果集   指定条数  返回5条
	public List<Brand> selectBrandListWithWhere(BrandQuery brandQuery);
	//添加品牌
	public void addBrand(Brand brand);
	
	//批量删除
	public void deleteBrands(Integer[] ids);
	
	//查询品牌
	public Brand selectBrandById(Integer id);
	//修改
	public void updateBrandById(Brand brand);
	
	//品牌 结果集
	public List<Brand> selectBrandList(BrandQuery brandQuery);
}
