package cn.itcast.erp.invoice.store.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.erp.auth.base.BaseDaoImpl;
import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.invoice.store.dao.dao.StoreDao;
import cn.itcast.erp.invoice.store.vo.StoreModel;

public class StoreDaoImpl extends BaseDaoImpl<StoreModel> implements StoreDao{

	public DetachedCriteria doQBC(BaseQueryModel bqm,
			Class<StoreModel> entryclass) {
		DetachedCriteria dc = DetachedCriteria.forClass(StoreModel.class);
		//添加查询条件
		return dc;
	}

	public List<StoreModel> getByUuid(Long uuid) {
		String hql = "from StoreModel where emp.uuid = ?";
		return this.getHibernateTemplate().find(hql, uuid);
	}

}
