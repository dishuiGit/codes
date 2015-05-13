package cn.itcast.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Brand;
import cn.itcast.core.bean.product.BrandQuery;
import cn.itcast.core.dao.product.BrandDao;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandDao brandDao;

	@Override
	public Pagination selectBrandListWithWhere(BrandQuery brandQuery) {
		// TODO Auto-generated method stub
		//实例化分页对象  组装分页
		//1:当前页
		//2:每页数
		//3:总条数
		Pagination pagination = new Pagination(
				brandQuery.getPageNo(),brandQuery.getPageSize(),brandDao.selectBrandCount(brandQuery));
		
		//List结果集
		pagination.setList(brandDao.selectBrandListWithWhere(brandQuery));
		
		return pagination;
	}

	@Override
	public void addBrand(Brand brand) {
		// TODO Auto-generated method stub
		brandDao.addBrand(brand);
	}

	@Override
	public void deleteBrands(Integer[] ids) {
		// TODO Auto-generated method stub
		brandDao.deleteBrands(ids);
	}

	@Override
	public Brand selectBrandById(Integer id) {
		// TODO Auto-generated method stub
		// 1 : select * from bbs_brand where id = 1
		// 金乐乐
		//Key : sdfsdafsd + 1  去Redis数据库去取值
		return brandDao.selectBrandById(id);
	}

	@Override
	public void updateBrandById(Brand brand) {
		// TODO Auto-generated method stub
		brandDao.updateBrandById(brand);
	}

	@Override
	public List<Brand> selectBrandList(BrandQuery brandQuery) {
		// TODO Auto-generated method stub
		return brandDao.selectBrandList(brandQuery);
	}




}
