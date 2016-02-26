package com.bizoss.trade.${table}.dao;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.bizoss.trade.base.BaseDaoImpl;
import com.bizoss.trade.${table}.bean.${table?cap_first};
import com.ibatis.sqlmap.client.SqlMapClient;
@Repository
public class ${table?cap_first}DaoImpl extends BaseDaoImpl<${table?cap_first}>{
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}
}
