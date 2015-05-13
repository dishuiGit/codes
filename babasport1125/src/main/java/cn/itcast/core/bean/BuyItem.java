package cn.itcast.core.bean;

import cn.itcast.core.bean.product.Sku;

/**
 * 购物项
 * @author lx
 *
 */
public class BuyItem {

	//id
	//图片
	//商品名
	//颜色
	//尺码 
	//售价
	private Sku sku;
	
	//数量  100件
	private int amount = 1;
	
	//是否有货    0 无货    1 有货
	private Integer isHave = 1;
	
	
	

	public Integer getIsHave() {
		return isHave;
	}

	public void setIsHave(Integer isHave) {
		this.isHave = isHave;
	}

	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)  //地址一样不?
			return true;
		if (obj == null)//传入的对象是否为NULL
			return false;
		if (getClass() != obj.getClass())//cn.itcast.core.bean.BuyItem  == cn.itcast.core.bean.BuyItem
			return false;
		BuyItem other = (BuyItem) obj;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.getId().equals(other.sku.getId()))
			return false;
		return true;
	}
	
	
	
}
