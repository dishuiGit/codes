package cn.dishui.core.po;

import java.util.ArrayList;
import java.util.List;

public class Cart {

	//CartItem
	//sku
	//	product
	//		img
	//	color
	//购物项
	private List<CartItem> cartItems = new ArrayList<CartItem>();
	//
	
	public List<CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	//商品金额=遍历(购物项*数量)
	public Double getSubTotal(){
		Double sum = 0.0;
		for(CartItem ci:cartItems){
			sum += ci.getSku().getSkuPrice()*ci.getAmount();
		}
		return sum;
	}
	public Double getTotalPrice(){
		//如果商品价格大于79,免运费
		if(getSubTotal()<79.0){
			return 10+getSubTotal();
		}
		return getSubTotal();
	}
}
