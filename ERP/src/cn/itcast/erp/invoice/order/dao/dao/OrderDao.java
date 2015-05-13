package cn.itcast.erp.invoice.order.dao.dao;

import java.util.List;
import java.util.Set;

import cn.itcast.erp.auth.base.BaseDao;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.order.vo.OrderQueryModel;

public interface OrderDao extends BaseDao<OrderModel>{

	List<OrderModel> getByEmpUuid(Long uuid);

	List<OrderModel> getByOrderType(OrderQueryModel oqm,
			Set<Integer> orderStateSet);

	List<OrderModel> getByType(Set<Integer> orderStateSet);


}
