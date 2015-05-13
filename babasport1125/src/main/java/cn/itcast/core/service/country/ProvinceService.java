package cn.itcast.core.service.country;

import java.util.List;

import cn.itcast.core.bean.country.Province;
import cn.itcast.core.bean.country.ProvinceQuery;

public interface ProvinceService {

	public List<Province> selectProvinces(ProvinceQuery provinceQuery);
}
