package cn.itcast.erp.invoice.orderDetail.dao.impl;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.erp.auth.base.BaseDaoImpl;
import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.invoice.orderDetail.dao.dao.OrderDetialDao;
import cn.itcast.erp.invoice.orderDetail.vo.OrderDetialModel;

public class OrderDetialDaoImpl extends BaseDaoImpl<OrderDetialModel> implements OrderDetialDao{

	public DetachedCriteria doQBC(BaseQueryModel bqm,
			Class<OrderDetialModel> entryclass) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderDetialModel.class);
		return dc;
	}

}
