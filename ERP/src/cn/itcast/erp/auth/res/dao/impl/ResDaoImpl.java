package cn.itcast.erp.auth.res.dao.impl;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.erp.auth.base.BaseDaoImpl;
import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.auth.res.dao.dao.ResDao;
import cn.itcast.erp.auth.res.vo.ResModel;

public class ResDaoImpl extends BaseDaoImpl<ResModel> implements ResDao{

	public DetachedCriteria doQBC(BaseQueryModel bqm, Class<ResModel> entryclass) {
		DetachedCriteria dc = DetachedCriteria.forClass(ResModel.class);
		//添加查询条件
		return dc;
	}

}
