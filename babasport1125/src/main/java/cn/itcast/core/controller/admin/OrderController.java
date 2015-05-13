package cn.itcast.core.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.bean.order.Order;
import cn.itcast.core.bean.order.OrderQuery;
import cn.itcast.core.bean.order.OrderQuery.Criteria;
import cn.itcast.core.service.order.OrderService;

/**
 * 订单列表
 * 订单查看
 * @author lx
 *
 */
@Controller
public class OrderController {

	
	//列表
	@RequestMapping(value = "/order/list.do")
	public String list(Integer isPaiy,Integer state,Model model){
		//订单数据
		//实例化一个订单Query对象
		OrderQuery orderQuery = new OrderQuery();
		Criteria createCriteria = orderQuery.createCriteria();
		//判断 
		//支付状态
		if(null != isPaiy){
			createCriteria.andIsPaiyEqualTo(isPaiy);
		}
		//订单状态
		if(null != state){
			createCriteria.andStateEqualTo(state);
		}
		//
		List<Order> orders = orderService.selectOrdersByExample(orderQuery);
		
		model.addAttribute("orders", orders);
		
		
		return "order/list";
	}
	@Autowired
	private OrderService orderService;
}
