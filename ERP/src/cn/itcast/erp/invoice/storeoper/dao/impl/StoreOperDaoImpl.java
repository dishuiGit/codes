package cn.itcast.erp.invoice.storeoper.dao.impl;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.erp.auth.base.BaseDaoImpl;
import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.invoice.storeoper.dao.dao.StoreOperDao;
import cn.itcast.erp.invoice.storeoper.vo.StoreOperModel;

public class StoreOperDaoImpl extends BaseDaoImpl<StoreOperModel> implements StoreOperDao{

	public DetachedCriteria doQBC(BaseQueryModel bqm,
			Class<StoreOperModel> entryclass) {
		DetachedCriteria dc = DetachedCriteria.forClass(StoreOperModel.class);
		return dc;
	}

}
