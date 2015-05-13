package cn.itcast.erp.invoice.orderDetail.vo;

import cn.itcast.erp.format.ViewFormat;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.order.vo.OrderModel;

public class OrderDetialModel {
	
	private Long uuid;
	
	//某个东西,花了多少钱,买了多少个
	private Integer num;
	private Integer suplus;
	public Integer getSuplus() {
		return suplus;
	}
	public void setSuplus(Integer suplus) {
		this.suplus = suplus;
	}
	private Double price;
	//视图值
	private String numView;
	private String priceView;
	private String subTotalView;
	
	public String getSubTotalView() {
		return subTotalView;
	}
	//关系
	private GoodsModel gm;
	private OrderModel om;
	
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
		this.priceView = ViewFormat.moneyFormat(price);
		this.subTotalView = ViewFormat.moneyFormat(num*price);
	}
	public GoodsModel getGm() {
		return gm;
	}
	public void setGm(GoodsModel gm) {
		this.gm = gm;
	}
	public OrderModel getOm() {
		return om;
	}
	public void setOm(OrderModel om) {
		this.om = om;
	}
	public String getNumView() {
		return numView;
	}
	public String getPriceView() {
		return priceView;
	}
	
	
}
