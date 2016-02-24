package com.bizoss.trade.${table}.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bizoss.trade.base.BaseServiceWarper;
import com.bizoss.trade.${table}.bean.${table?cap_first};
import com.bizoss.trade.${table}.dao.${table?cap_first}DaoImpl;

@Service
public class ${table?cap_first}ServiceImpl extends BaseServiceWarper<${table?cap_first}>{
	
	@Resource
	private ${table?cap_first}DaoImpl ${table}DaoImpl;
	
	public void save(${table?cap_first} ${table}) {
		${table}DaoImpl.save(${table});
	}

	
}
