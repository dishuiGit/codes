package cn.itcast.core.service.order;

import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import cn.itcast.core.bean.BuyCart;
import cn.itcast.core.bean.BuyItem;
import cn.itcast.core.bean.order.Detail;
import cn.itcast.core.bean.order.Order;
import cn.itcast.core.bean.order.OrderQuery;
import cn.itcast.core.dao.order.DetailMapper;
import cn.itcast.core.dao.order.OrderMapper;
import cn.itcast.core.web.Constants;

/**
 * 订单
 * @author lx
 *
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService{

	@Autowired
	OrderMapper orderMapper;
	@Autowired
	DetailMapper detailMapper;
	@Autowired
	JedisConnectionFactory jedisConnectionFactory;

	@Override
	public void addOrder(Order order,BuyCart buyCart,HttpServletResponse response) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisConnectionFactory.getShardInfo().createResource();
		//订单编号  商品编号 原子性  编号不会
		//Long numFound
		Long oid = jedis.incr("oid");
		order.setOid("" + oid);
		//保存订单 返回ID
		//运费
		order.setDeliverFee(buyCart.getFee());
		//应付金额
		order.setPayableFee(buyCart.getTotalPrice());
		//订单金额
		order.setTotalPrice(buyCart.getProductPrice());
		//支持状态
		if(order.getPaymentWay() == 0){
			order.setIsPaiy(0);
		}else{
			order.setIsPaiy(1);
			
		}
		//订单状态
		order.setState(0);
		//时间
		order.setCreateDate(new Date());
		
		//返回ID
		orderMapper.insert(order);
		//保存订单详情  List
		Detail detail = null;
		List<BuyItem> items = buyCart.getItems();
		for (BuyItem buyItem : items) {
			detail = new Detail();
			//订单ID
			detail.setOrderId(order.getId());
			//detail商品编号
			detail.setProductNo(buyItem.getSku().getProduct().getNo());
			//商品名
			detail.setProductName(buyItem.getSku().getProduct().getName());
			//颜色名
			detail.setColor(buyItem.getSku().getColor().getName());
			//尺码 
			detail.setSize(buyItem.getSku().getSize());
			//价格
			detail.setSkuPrice(buyItem.getSku().getSkuPrice());
			//数量
			detail.setAmount(buyItem.getAmount());
			detailMapper.insert(detail);
		
		}
		//最新
		Cookie cookie = new Cookie(Constants.BUYER_CART,null);
		//要求关闭浏览器也要存在  设置Cookie 存活时间  或
		//0 : 立即消毁
		//-1 : 关闭浏览器消毁    默认
		// > 0 : 表示到时间才消毁    单位 秒
		cookie.setMaxAge(0);
		//设置 Cookie的路径  jsessionid
		cookie.setPath("/");
		//写回浏览器
		response.addCookie(cookie);
	}
	//Order 集合
	public List<Order> selectOrdersByExample(OrderQuery example){
		return orderMapper.selectByExample(example);
	}

}
