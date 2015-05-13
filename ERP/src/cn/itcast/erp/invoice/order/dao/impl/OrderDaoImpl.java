package cn.itcast.erp.invoice.order.dao.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.auth.base.BaseDaoImpl;
import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.invoice.order.dao.dao.OrderDao;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.order.vo.OrderQueryModel;

public class OrderDaoImpl extends BaseDaoImpl<OrderModel> implements OrderDao{

	public DetachedCriteria doQBC(BaseQueryModel bqm,
			Class<OrderModel> entryclass) {
		//添加查询条件
		DetachedCriteria dc = DetachedCriteria.forClass(OrderModel.class);
		return dc;
	}

	public List<OrderModel> getByEmpUuid(Long uuid) {
		String hql = "from OrderModel where completer.uuid=?";
		return this.getHibernateTemplate().find(hql, uuid);
	}

	public List<OrderModel> getByOrderType(OrderQueryModel oqm,
			Set<Integer> orderStateSet) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderModel.class);
		dc.add(Restrictions.in("orderState", orderStateSet));
		return this.getHibernateTemplate().findByCriteria(dc);
	}

	public List<OrderModel> getByType(Set<Integer> orderStateSet) {
		String hql = "from OrderModel o where o.orderState in :ids";
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query q = s.createQuery(hql);
		q.setParameterList("ids",orderStateSet);
		return q.list();
	}


}
