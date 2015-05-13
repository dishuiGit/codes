package cn.itcast.core.service.country;

import java.util.List;

import cn.itcast.core.bean.country.Town;
import cn.itcast.core.bean.country.TownQuery;

public interface TownService {

	public List<Town> selectTowns(TownQuery provinceQuery);
}
