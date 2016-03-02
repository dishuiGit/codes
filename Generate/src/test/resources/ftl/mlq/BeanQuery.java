package com.bizoss.trade.${table}.bean;

import com.bizoss.trade.base.BaseQuery;
import com.bizoss.trade.${table}.bean.${table?cap_first};

public class ${table?cap_first}Query extends ${table?cap_first} implements BaseQuery {
	private Integer start;
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	private Integer limit;
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
