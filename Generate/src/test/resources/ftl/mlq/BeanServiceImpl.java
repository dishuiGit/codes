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
	
	public void save(${table?cap_first} ${table}) {
		${table}DaoImpl.save(${table});
	}

	public void update(${table?cap_first} ${table}) {
		${table}DaoImpl.update(${table});
	}

	public ${table?cap_first} get(String id) {
		return ${table}DaoImpl.get(id);
	}

	public List<${table?cap_first}> getAll() {
		return ${table}DaoImpl.getAll();
	}

	public List<${table?cap_first}> getAll(${table?cap_first}Query bqm) {
		return ${table}DaoImpl.getAll(bqm);
	}
	
	public List<${table?cap_first}> getAll(${table?cap_first}Query bqm, Integer pageNum) {
		if(ObjUtils.empty(bqm.getLimit())){
			return getAll(bqm, pageNum, 10);
		}
		return ${table}DaoImpl.getAll(bqm, pageNum, bqm.getLimit());
	}

	public Integer getCount(${table?cap_first}Query bqm) {
		return ${table}DaoImpl.getCount(bqm);
	}

	public List<${table?cap_first}> getAll(${table?cap_first}Query bqm, Integer pageNum,
			Integer pageCount) {
		Integer startPage = 1;
		if(ObjUtils.notEmpty(pageNum)){
		    startPage = Pagination.cpn(pageNum);
		}
		
		bqm.setStart((startPage-1)*pageCount);
		bqm.setLimit(pageCount);
		return ${table}DaoImpl.getAll(bqm, (startPage-1), pageCount);
	}

	public void delete(${table?cap_first} ${table}) {
		${table}DaoImpl.delete(${table});
	}
	
	public Pagination ge${table?cap_first}Pagination(${table?cap_first}Query bq,Integer pageNo,Integer limit,String url){
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
		Integer totalCount = getCount(bq);
		//列表
		List<{table?cap_first}> mdr_l = getAll(bq, pageNo);
		Pagination pagination = new Pagination(pageNo,limit,totalCount);
		//页面展示功能
		pagination.pageView(url, params.toString());
		pagination.setList(mdr_l);
		
		return pagination;
	}
}
