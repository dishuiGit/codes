package cn.itcast.core.bean;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import cn.itcast.core.bean.product.Sku;

/**
 * 购物车   内存对象转成二进制  内存对象转成JSON串
 * @author lx
 *
 */
public class BuyCart {

	//集合  1  2
	private List<BuyItem> items = new ArrayList<BuyItem>();
	
	//删除
	public void deleteBuyItem(Integer skuId){
		Sku sku = new Sku();
		sku.setId(skuId);
		BuyItem i = new BuyItem();
		i.setSku(sku);
		
		items.remove(i);
	}
	
	//添加购物项
	public void addItem(BuyItem item){
		//判断是否有重复的
		//目前是判断的地址  
		if(items.contains(item)){
			//数量追加
			for(BuyItem it : items){
				if(it.equals(item)){
					it.setAmount(it.getAmount() + item.getAmount());
				}
			}
		}else{
			items.add(item);
		}
		
	}
	
	//商品数量
	@JsonIgnore
	public Integer getProductAmount(){
		Integer result = 0;
		//计算 商品数据
		for(BuyItem item : items){
			result += item.getAmount();
		}
		return result;
	}
	//商品金额
	//对象转成JSON  
	//要求: POJO  标准的
	// private Integer id
	// set get 
	@JsonIgnore
	public Double getProductPrice(){
		Double result = 0.00;
		for(BuyItem item : items){
			result += item.getAmount()*(item.getSku().getSkuPrice());
		}
		return result;
	}
	//运费
	@JsonIgnore
	public Double getFee(){
		Double result = 0.00;
		if(getProductPrice() <= 79){
			result = 10.00;
		}
		return result;
	}
	
	//应付总额
	@JsonIgnore
	public Double getTotalPrice(){
		return getProductPrice() + getFee();
	}
	
	
	
	
	
	public List<BuyItem> getItems() {
		return items;
	}

	public void setItems(List<BuyItem> items) {
		this.items = items;
	}
	
	//set get 
	
	
}
