package cn.itcast.core.service.country;

import java.util.List;

import cn.itcast.core.bean.country.City;
import cn.itcast.core.bean.country.CityQuery;

public interface CityService {

	public List<City> selectProvinces(CityQuery provinceQuery);
}
