package cn.itcast.erp.invoice.goods.dao.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.erp.auth.base.BaseDaoImpl;
import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.invoice.goods.dao.dao.GoodsDao;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;

public class GoodsDaoImpl extends BaseDaoImpl<GoodsModel> implements GoodsDao{

	public DetachedCriteria doQBC(BaseQueryModel bqm,
			Class<GoodsModel> entryclass) {
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsModel.class);
		//添加查询条件
		return dc;
	}

	public List<GoodsModel> getByGtmIdAndSmId(Long goodsTypeUuid,
			Set<Long> goodsUuids) {
		String hql = "select distinct gm from SupplierModel sm join sm.gtms gtm join gtm.gms gm " +
				"where gtm.uuid = :uuid and gm.uuid not in :uuids";
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query q = s.createQuery(hql);
		q.setLong("uuid", goodsTypeUuid);
		q.setParameterList("uuids", goodsUuids);
		return q.list();
	}

	public List<GoodsModel> getByGtmId(Long supplierUuid) {
		String hql = "select distinct gm from SupplierModel sm join sm.gtms gtm join gtm.gms gm " +
				"where gtm.uuid = ?";
		return this.getHibernateTemplate().find(hql,supplierUuid);
	}

}
