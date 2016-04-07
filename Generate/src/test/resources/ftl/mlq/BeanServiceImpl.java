package com.bizoss.trade.${table}.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bizoss.frame.util.ObjUtils;
import com.bizoss.frame.util.page.Pagination;
import com.bizoss.trade.base.BaseQuery;
import com.bizoss.trade.base.BaseServiceWarper;
import com.bizoss.trade.${table}.bean.${table?cap_first};
import com.bizoss.trade.${table}.bean.${table?cap_first}Query;
import com.bizoss.trade.${table}.dao.${table?cap_first}DaoImpl;

@Service
public class ${table?cap_first}ServiceImpl extends BaseServiceWarper<${table?cap_first}>{
	
	@Resource
	private ${table?cap_first}DaoImpl ${table}DaoImpl;
	
	public void save(${table?cap_first} ${table_alias}) {
		${table}DaoImpl.save(${table_alias});
	}

	public void update(${table?cap_first} ${table_alias}) {
		${table}DaoImpl.update(${table_alias});
	}

	public ${table?cap_first} get(String id) {
		return ${table}DaoImpl.get(id);
	}

	public List<${table?cap_first}> getAll() {
		return ${table}DaoImpl.getAll();
	}

	public List<${table?cap_first}> getAll(${table?cap_first}Query ${table_alias}q) {
		return ${table}DaoImpl.getAll(${table_alias}q);
	}
	
	public List<${table?cap_first}> getAll(${table?cap_first}Query ${table_alias}q, Integer pageNum) {
		if(ObjUtils.empty(${table_alias}q.getLimit())){
			return getAll(${table_alias}q, pageNum, 10);
		}
		return ${table}DaoImpl.getAll(${table_alias}q, pageNum, ${table_alias}q.getLimit());
	}

	public Integer getCount(${table?cap_first}Query ${table_alias}q) {
		return ${table}DaoImpl.getCount(${table_alias}q);
	}

	public List<${table?cap_first}> getAll(${table?cap_first}Query ${table_alias}q, Integer pageNum,
			Integer pageCount) {
		Integer startPage = 1;
		if(ObjUtils.notEmpty(pageNum)){
		    startPage = Pagination.cpn(pageNum);
		}
		
		${table_alias}q.setStart((startPage-1)*pageCount);
		${table_alias}q.setLimit(pageCount);
		return ${table}DaoImpl.getAll(${table_alias}q, (startPage-1), pageCount);
	}

	public void delete(${table?cap_first} ${table_alias}) {
		${table}DaoImpl.delete(${table_alias});
	}
	
	public Pagination get${table?cap_first}Pagination(${table?cap_first}Query ${table_alias}q,Integer pageNo,Integer limit,String url){
		StringBuilder params = new StringBuilder();
		/*
			String goods_status = request.getParameter("goods_status");
			if(goods_status!=null && !"".equals(goods_status.trim())){
				mg.setGoods_status(goods_status);
				params.append("&goods_status="+goods_status);
			}
		*/
		if(ObjUtils.notEmpty(pageNo)){
			pageNo = Pagination.cpn(pageNo);
		}else{
			pageNo = 1;
		}
		if(ObjUtils.empty(limit)){
			limit = 10;
		}
		//总条数
		Integer totalCount = getCount(${table_alias}q);
		//列表
		List<${table?cap_first}> mdr_l = getAll(${table_alias}q, pageNo);
		Pagination pagination = new Pagination(pageNo,limit,totalCount);
		//页面展示功能
		pagination.pageView(url, params.toString());
		pagination.setList(mdr_l);
		
		return pagination;
	}
}
