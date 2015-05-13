package cn.itcast.erp.invoice.order.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.invoice.order.dao.dao.OrderDao;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.order.vo.OrderQueryModel;
import cn.itcast.erp.util.base.BaseImpl;
import cn.itcast.erp.util.base.BaseQueryModel;

public class OrderImpl extends BaseImpl<OrderModel> implements OrderDao{

	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		OrderQueryModel oqm = (OrderQueryModel)qm;
		
		//oqm.orderType
		if(oqm.getOrderType()!=null && oqm.getOrderType()!= -1){
			dc.add(Restrictions.eq("orderType", oqm.getOrderType()));
		}
		//oqm.orderTypes
		if(oqm.getOrderTypes()!=null && oqm.getOrderTypes().length > 0){
			dc.add(Restrictions.in("orderType", oqm.getOrderTypes()));
		}
		//oqm.transportTaskTypes
		if(oqm.getTransportTaskTypes()!=null && oqm.getTransportTaskTypes().length > 0){
			dc.add(Restrictions.in("type", oqm.getTransportTaskTypes()));
		}
		//oqm.type
		if(oqm.getType()!=null && oqm.getType()!= -1){
			dc.add(Restrictions.eq("type", oqm.getType()));
		}
		//oqm.completer.uuid
		if(oqm.getCompleter()!=null && oqm.getCompleter().getUuid()!=null && oqm.getCompleter().getUuid()!=-1){
			dc.add(Restrictions.eq("completer", oqm.getCompleter()));
		}
		//oqm.creater.name
		if(oqm.getCreater()!=null && oqm.getCreater().getName()!=null && oqm.getCreater().getName().trim().length()>0){
			dc.createAlias("creater", "c1");
			dc.add(Restrictions.like("c1.name", "%"+oqm.getCreater().getName().trim()+"%"));
		}
		// TODO 未添加自定义查询条件查询方式设定
	}
}