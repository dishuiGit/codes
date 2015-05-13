package cn.dishui.core.service.impl.brand;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dishui.core.dao.brand.BrandDao;
import cn.dishui.core.po.product.Brand;
import cn.dishui.core.po.product.BrandQuery;
import cn.dishui.core.service.service.brand.BrandService;
import cn.itcast.common.page.Pagination;
@Service
public class BrandServiceImpl implements BrandService{
	@Resource
	private BrandDao brandDao;
	//封装分页对象

	public Pagination selectPagination(BrandQuery brandQuery) {
		Integer brandsCount = brandDao.selectBrandCount(brandQuery);
		
		//limit(开始行,每页显示个数)
		List<Brand> brandList = brandDao.selectBrandList(brandQuery);
		
		Pagination pagination = new Pagination(
				brandQuery.getPageNo(), brandQuery.getPageSize(), brandsCount, brandList );
		
		return pagination;
	}

	public void save(Brand brand) {
		brandDao.insertBrand(brand);
	}

	public void batchDelete(Integer[] ids) {
		brandDao.deleteBrands(ids);
	}

	public List<Brand> selectOptionBrands(BrandQuery brandQuery) {
		return brandDao.selectBrandsForOption(brandQuery);
	}

	public Brand selectBrandById(Integer brandId) {
		return brandDao.selectBrandById(brandId);
	}


}
