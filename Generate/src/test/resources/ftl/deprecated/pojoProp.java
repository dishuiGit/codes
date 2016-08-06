package com.bizoss.trade.${table};

import java.io.Serializable;

public class ${table?cap_first}_prop extends ${table?cap_first} implements Serializable {
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