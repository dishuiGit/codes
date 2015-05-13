package cn.itcast.core.service.country;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.core.bean.country.City;
import cn.itcast.core.bean.country.CityQuery;
import cn.itcast.core.dao.country.CityMapper;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityMapper provinceMapper;
	
	public List<City> selectProvinces(CityQuery provinceQuery){
		
		return provinceMapper.selectByExample(provinceQuery);
	}
}
