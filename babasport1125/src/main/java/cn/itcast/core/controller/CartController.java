package cn.itcast.core.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.web.session.SessionProvider;
import cn.itcast.core.bean.BuyCart;
import cn.itcast.core.bean.BuyItem;
import cn.itcast.core.bean.order.Order;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.bean.user.Addr;
import cn.itcast.core.bean.user.Buyer;
import cn.itcast.core.service.order.OrderService;
import cn.itcast.core.service.product.SkuService;
import cn.itcast.core.service.user.AddrService;
import cn.itcast.core.web.Constants;

/**
 * 购物车
 * 购买
 * 清空购物车
 * 删除购物项
 * 小计
 * @author lx
 *
 */
@Controller
public class CartController {

	
	//购买按钮
	@RequestMapping(value = "/shopping/buyCart.shtml")
	public String  buyCart(Integer skuId,Integer amount,HttpServletRequest request,HttpServletResponse response,Model model){
		ObjectMapper  om = new ObjectMapper();
		//设置成如果字段为Null就不要转了
		om.setSerializationInclusion(Inclusion.NON_NULL);
		//购物车对象
		BuyCart buyCart =  null;//json串
		//判断 看Cookie是否为空
		Cookie[] cookies = request.getCookies();
		//jsessionid 最少要有它
		if(null != cookies && cookies.length > 0){
			for (Cookie cookie : cookies) {
				 if(Constants.BUYER_CART.equals(cookie.getName())){
					 try {
						buyCart = om.readValue(cookie.getValue(), BuyCart.class);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 break;
				 }
			}
		}
		//判断是否为第一次购物
		if(null == buyCart){
			buyCart = new BuyCart();
		}
		//购物车肯定有
		if(null != skuId){
			Sku sku = new Sku();
			sku.setId(skuId);
			//购物项
			BuyItem buyItem = new BuyItem();
			buyItem.setSku(sku);
			//数量
			buyItem.setAmount(amount);
			//装购物项到购物车
			//List  items
			
			//追加商品到购物车中  购物车
			//buyCart.setItems(items);
			buyCart.addItem(buyItem);
			
			//转
			StringWriter str = new StringWriter();
			try {
				om.writeValue(str, buyCart);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//最新
			Cookie cookie = new Cookie(Constants.BUYER_CART,str.toString());
			//要求关闭浏览器也要存在  设置Cookie 存活时间  或
			//0 : 立即消毁
			//-1 : 关闭浏览器消毁    默认
			// > 0 : 表示到时间才消毁    单位 秒
			cookie.setMaxAge(60*60*24);
			//设置 Cookie的路径  jsessionid
			cookie.setPath("/");
			//写回浏览器
			response.addCookie(cookie);
		}
		//把购物车数据装满  此时购物车中只有 skuId  amount
		List<BuyItem> items = buyCart.getItems();
		if(null != items && items.size() > 0){
			for (BuyItem buyItem : items) {
				Sku sk = skuService.selectSkusById(buyItem.getSku().getId());
				buyItem.setSku(sk);
			}
		}
	
		model.addAttribute("buyCart", buyCart);
		return "product/cart";
	}
	
	//删除
	@RequestMapping(value = "/shopping/delProduct.shtml")
	public String delProduct(Integer skuId,HttpServletRequest request,HttpServletResponse response){
		ObjectMapper  om = new ObjectMapper();
		//设置成如果字段为Null就不要转了
		om.setSerializationInclusion(Inclusion.NON_NULL);
		//购物车对象
		BuyCart buyCart =  null;//json串
		//判断 看Cookie是否为空
		Cookie[] cookies = request.getCookies();
		//jsessionid 最少要有它
		if(null != cookies && cookies.length > 0){
			for (Cookie cookie : cookies) {
				 if(Constants.BUYER_CART.equals(cookie.getName())){
					 try {
						buyCart = om.readValue(cookie.getValue(), BuyCart.class);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 break;
				 }
			}
		}
		//删除
		buyCart.deleteBuyItem(skuId);
		//转
		StringWriter str = new StringWriter();
		try {
			om.writeValue(str, buyCart);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//最新
		Cookie cookie = new Cookie(Constants.BUYER_CART,str.toString());
		//要求关闭浏览器也要存在  设置Cookie 存活时间  或
		//0 : 立即消毁
		//-1 : 关闭浏览器消毁    默认
		// > 0 : 表示到时间才消毁    单位 秒
		cookie.setMaxAge(60*60*24);
		//设置 Cookie的路径  jsessionid
		cookie.setPath("/");
		//写回浏览器
		response.addCookie(cookie);
		
		return "redirect:/shopping/buyCart.shtml";
	}
	//删除
	@RequestMapping(value = "/shopping/clearCart.shtml")
	public String clearCart(HttpServletRequest request,HttpServletResponse response){

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
		
		return "redirect:/shopping/buyCart.shtml";
	}
	//结算
	@RequestMapping(value = "/buyer/trueBuy.shtml")
	public String trueBuy(HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
		//1:从Cookie取购物车
		ObjectMapper  om = new ObjectMapper();
		//设置成如果字段为Null就不要转了
		om.setSerializationInclusion(Inclusion.NON_NULL);
		//购物车对象
		BuyCart buyCart =  null;//json串
		//判断 看Cookie是否为空
		Cookie[] cookies = request.getCookies();
		//jsessionid 最少要有它
		if(null != cookies && cookies.length > 0){
			for (Cookie cookie : cookies) {
				 if(Constants.BUYER_CART.equals(cookie.getName())){
					 try {
						buyCart = om.readValue(cookie.getValue(), BuyCart.class);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 break;
				 }
			}
		}
		//2:判断 购物车是否为空
		if(null != buyCart && null != buyCart.getItems() && buyCart.getItems().size() > 0){
			List<BuyItem>  items = buyCart.getItems();
			boolean flag = true;
			for (BuyItem buyItem : items) {
				//3:判断购物车中的商品是否有库存 buyItem.getAmount() 要3件  只有2件
				Integer stock = skuService.selectStockById(buyItem.getSku().getId());
				if(stock < buyItem.getAmount()){
					buyItem.setIsHave(0);
					//刷新购物车页面
					flag = false;
				}
			}
			if(!flag){
				//把购物车对象转成JSON串写回浏览器的Cookie中
				//转
				StringWriter str = new StringWriter();
				try {
					om.writeValue(str, buyCart);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//最新
				Cookie cookie = new Cookie(Constants.BUYER_CART,str.toString());
				//要求关闭浏览器也要存在  设置Cookie 存活时间  或
				//0 : 立即消毁
				//-1 : 关闭浏览器消毁    默认
				// > 0 : 表示到时间才消毁    单位 秒
				cookie.setMaxAge(60*60*24);
				//设置 Cookie的路径  jsessionid
				cookie.setPath("/");
				//写回浏览器
				response.addCookie(cookie);
				
				return "redirect:/shopping/buyCart.shtml";
			}
			
		}else{
			//刷新购物车页面
			return "redirect:/shopping/buyCart.shtml";
		}
		//走到这里
		Buyer buyer = (Buyer) sessionProvider.getAttribute(request, response, Constants.BUYER_SESSION);
		
		Addr addr = addrService.selectAddrsByUserNameAndIsDef(buyer);
		
		model.addAttribute("addr", addr);
		
		return "product/productOrder";
	}
	//提交订单
	@RequestMapping(value = "/buyer/confirmOrder.shtml")
	public String confirmOrder(Order order ,HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
		//1:从Cookie取购物车
		ObjectMapper  om = new ObjectMapper();
		//设置成如果字段为Null就不要转了
		om.setSerializationInclusion(Inclusion.NON_NULL);
		//购物车对象
		BuyCart buyCart =  null;//json串
		//判断 看Cookie是否为空
		Cookie[] cookies = request.getCookies();
		//jsessionid 最少要有它
		if(null != cookies && cookies.length > 0){
			for (Cookie cookie : cookies) {
				 if(Constants.BUYER_CART.equals(cookie.getName())){
					 try {
						buyCart = om.readValue(cookie.getValue(), BuyCart.class);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 break;
				 }
			}
		}
		//把购物车数据装满  此时购物车中只有 skuId  amount
		List<BuyItem> items = buyCart.getItems();
		if(null != items && items.size() > 0){
			for (BuyItem buyItem : items) {
				Sku sk = skuService.selectSkusById(buyItem.getSku().getId());
				buyItem.setSku(sk);
			}
		}
		Buyer buyer = (Buyer) sessionProvider.getAttribute(request, response, Constants.BUYER_SESSION);
		//用户
		order.setBuyerId(buyer.getUsername());
		//保存
		orderService.addOrder(order, buyCart,response);
		//回显数据
		
		
		return "product/confirmOrder";
	}
	
	
	@Autowired
	private SkuService skuService;
	@Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private AddrService addrService;
	@Autowired
	private OrderService orderService;
}
