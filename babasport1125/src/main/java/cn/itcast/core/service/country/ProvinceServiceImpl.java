package cn.itcast.core.service.country;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.core.bean.country.Province;
import cn.itcast.core.bean.country.ProvinceQuery;
import cn.itcast.core.dao.country.ProvinceMapper;
@Service
public class ProvinceServiceImpl implements ProvinceService {

	@Autowired
	private ProvinceMapper provinceMapper;
	
	public List<Province> selectProvinces(ProvinceQuery provinceQuery){
		
		return provinceMapper.selectByExample(provinceQuery);
	}
}
