package cn.itcast.erp.invoice.supplier.dao.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.erp.auth.base.BaseDaoImpl;
import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.invoice.supplier.dao.dao.SupplierDao;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;

public class SupplierDaoImpl extends BaseDaoImpl<SupplierModel> implements SupplierDao{

	public DetachedCriteria doQBC(BaseQueryModel bqm,
			Class<SupplierModel> entryclass) {
		DetachedCriteria dc = DetachedCriteria.forClass(SupplierModel.class);
		//添加查询条件
		return dc;
	}

	public List<GoodsTypeModel> getAllGTByUuid(Long uuid) {
		String hql = "select distinct gtm from SupplierModel sm join sm.gtms gtm where sm.uuid = ?";
		return this.getHibernateTemplate().find(hql,uuid);
	}

	public List<GoodsTypeModel> getBySmUuidAndGmUuid(Long supplierUuid,
			Set<Long> goodsUuids) {
		String hql = "select distinct gtm from SupplierModel sm join sm.gtms gtm join gtm.gms gm " +
				"where sm.uuid = :uuid and gm.uuid not in :uuids";
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query q = s.createQuery(hql);
		q.setLong("uuid", supplierUuid);
		q.setParameterList("uuids", goodsUuids);
		return q.list();
	}


}
