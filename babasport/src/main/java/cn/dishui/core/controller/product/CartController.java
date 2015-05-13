package cn.dishui.core.controller.product;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.dishui.core.po.Cart;
import cn.dishui.core.po.CartItem;
import cn.dishui.core.po.product.Sku;
import cn.dishui.core.service.service.product.SkuService;
import cn.dishui.core.web.Constants;

@Controller
public class CartController {
	@Resource
	private SkuService skuService;

	@RequestMapping("/shopping/buyCart.shtml")
	public String buyCart(Integer skuId, Integer amount,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		// 将单品页信息存入cookie
		Cookie cookie = null;
		Cart cart = null;
		List<CartItem> clist = null;
		// 先获取cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			// 遍历查找指定的cookie
			for (Cookie c : cookies) {
				// 判断
				if (Constants.CART_COOKIE.equals(c.getName())) {
					// 如果找到,退出循环
					cookie = c;
					break;
				}
			}
		}
		if (cookie != null) {
			// 将cookie中的内容反序列化成对象
			String json = cookie.getValue();
			ObjectMapper om = new ObjectMapper();
			cart = om.readValue(json, Cart.class);
			clist = cart.getCartItems();
			// System.out.println(cart);
			Sku sku = new Sku();
			// 判断skuid是否为空
			if (skuId != null) {
				sku.setId(skuId);
				CartItem cartItem = new CartItem();
				cartItem.setSku(sku);
				cartItem.setAmount(amount);
				// 如果是相同商品,数量加一(根据id比对)
				for (CartItem tmp_ci : clist) {
					if (cartItem.equals(tmp_ci)) {
						int tmp_amount = tmp_ci.getAmount()
								+ cartItem.getAmount();
						tmp_ci.setAmount(tmp_amount);
					}
				}
				if (!clist.contains(cartItem)) {
					clist.add(cartItem);
				}
				cart.setCartItems(clist);
				// 添加新商品,购物车中再增加一条购物项
				// 通过skuid查询
				// TODO skuService
				// 序列化回json
				StringWriter sw = new StringWriter();
				om.setSerializationInclusion(Inclusion.NON_NULL);
				om.writeValue(sw, cart);
				// 过滤空的
				// 将cookie发送到客户端
				cookie.setValue(sw.toString());
				cookie.setMaxAge(60 * 60 * 24);
				cookie.setPath("/");
				// 将cookie发送到客户端
				response.addCookie(cookie);
			}
			// 回显
			for (CartItem ci : clist) {
				sku = skuService.selectBySkuId(ci.getSku().getId());
				ci.setSku(sku);
			}

		} else {
			if (skuId != null) {
				// 创建新的cookie TODO
				cart = new Cart();
				clist = new ArrayList<CartItem>();

				CartItem cartItem = new CartItem();
				Sku sku = new Sku();

				sku.setId(skuId);
				cartItem.setSku(sku);
				cartItem.setAmount(amount);
				clist.add(cartItem);

				cart.setCartItems(clist);
				// 对象序列化,json存入cookie
				ObjectMapper om = new ObjectMapper();
				// 过滤空的
				om.setSerializationInclusion(Inclusion.NON_NULL);
				StringWriter sw = new StringWriter();
				om.writeValue(sw, cart);

				cookie = new Cookie(Constants.CART_COOKIE, sw.toString());
				// 设置cookie
				cookie.setMaxAge(60 * 60 * 24);
				cookie.setPath("/");
				// 将cookie发送到客户端
				response.addCookie(cookie);
				// 回显
				for (CartItem ci : clist) {
					sku = skuService.selectBySkuId(skuId);
					ci.setSku(sku);
				}
			}
		}
		model.addAttribute("cart", cart);
		return "product/cart";
	}

	@RequestMapping("/shopping/toCart.shtml")
	public String toCart() {
		
		return "product/cart";
	}
}
