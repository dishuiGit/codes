package com.bizoss.trade.${table}.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bizoss.trade.base.BaseQuery;
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

	public void update(${table?cap_first} ${table}) {
		${table}DaoImpl.update(spring_demo);
	}

	public ${table?cap_first} get(String id) {
		return ${table}DaoImpl.get(id);
	}

	public List<${table?cap_first}> getAll() {
		return ${table}DaoImpl.getAll();
	}

	public List<${table?cap_first}> getAll(BaseQuery bqm) {
		return ${table}DaoImpl.getAll(bqm);
	}

	public Integer getCount(BaseQuery bqm) {
		return ${table}DaoImpl.getCount(bqm);
	}

	public List<Spring_demo> getAll(BaseQuery bqm, Integer pageNum,
			Integer pageCount) {
		return ${table}DaoImpl.getAll(bqm, pageNum, pageCount);
	}

	public void delete(${table?cap_first} ${table}) {
		${table}DaoImpl.delete(${table});
	}
}
