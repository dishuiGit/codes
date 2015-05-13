package cn.itcast.core.service.country;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.core.bean.country.Town;
import cn.itcast.core.bean.country.TownQuery;
import cn.itcast.core.dao.country.TownMapper;

@Service
public class TownServiceImpl implements TownService {

	@Autowired
	private TownMapper provinceMapper;
	
	public List<Town> selectTowns(TownQuery provinceQuery){
		
		return provinceMapper.selectByExample(provinceQuery);
	}
}
