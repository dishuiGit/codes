package cn.itcast.core.service.order;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import cn.itcast.core.bean.BuyCart;
import cn.itcast.core.bean.order.Order;
import cn.itcast.core.bean.order.OrderQuery;

public interface OrderService {

	public void addOrder(Order order, BuyCart buyCart,
			HttpServletResponse response);

	// Order 集合
	public List<Order> selectOrdersByExample(OrderQuery example);
}
