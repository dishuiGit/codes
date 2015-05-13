package cn.itcast.erp.invoice.goodstype.dao.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.invoice.goodstype.dao.dao.GoodsTypeDao;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeQueryModel;
import cn.itcast.erp.util.base.BaseImpl;
import cn.itcast.erp.util.base.BaseQueryModel;

public class GoodsTypeImpl extends BaseImpl<GoodsTypeModel> implements GoodsTypeDao{

	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		GoodsTypeQueryModel gqm = (GoodsTypeQueryModel)qm;
	}

	public List<GoodsTypeModel> getAllBySmUuid(Long uuid) {
		String hql = " from GoodsTypeModel where sm.uuid = ? " ;
		return this.getHibernateTemplate().find(hql,uuid);
	}

	public List<GoodsTypeModel> getAllUnionBySmUuid(Long uuid) {
		String hql = "select distinct gt from GoodsModel gm join gm.gtm gt where gt.sm.uuid = ? " ;
		return this.getHibernateTemplate().find(hql,uuid);
	}

	public List<GoodsTypeModel> getBySmUuidAndGmUuidNotInSet(Long supplierUuid,	Set<Long> uuids) {
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		//查询的最终是商品类别from GoodsTypeModel
		//商品类别必须与商品具有关联关系 gtm->gm  gm->gtm  from GoodsModel gm join gm.gtm	
		
		String hql = " select distinct gt from GoodsModel gm join gm.gtm gt where gt.sm.uuid = :uuid and gm.uuid not in :uuids" ;
		Query q = s.createQuery(hql);
		q.setLong("uuid", supplierUuid);
		q.setParameterList("uuids", uuids);
		return q.list();
	}
}