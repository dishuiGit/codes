package cn.itcast.erp.invoice.store.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.invoice.store.dao.dao.StoreDao;
import cn.itcast.erp.invoice.store.vo.StoreModel;
import cn.itcast.erp.invoice.store.vo.StoreQueryModel;
import cn.itcast.erp.util.base.BaseImpl;
import cn.itcast.erp.util.base.BaseQueryModel;

public class StoreImpl extends BaseImpl<StoreModel> implements StoreDao{

	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		StoreQueryModel sqm = (StoreQueryModel)qm;
		// TODO 未添加自定义查询条件查询方式设定
	}

	public List<StoreModel> getAllByEmpUuid(Long uuid) {
		String hql = " from StoreModel where em.uuid = ?" ;
		return this.getHibernateTemplate().find(hql,uuid);
	}
}