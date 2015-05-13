package cn.itcast.erp.invoice.orderdetail.vo;

import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.util.format.FormatUtils;

public class OrderDetailModel {
	private Long uuid;
	//某个东西，花了多少钱，买了多少个
	private Integer num;
	//剩余未入库数量
	private Integer surplus;
	
	private Double price;
	
	private String priceView;
	private String totalPriceView;
	
	private GoodsModel gm;
	private OrderModel om;
	
	public Integer getSurplus() {
		return surplus;
	}
	public void setSurplus(Integer surplus) {
		this.surplus = surplus;
	}
	public String getTotalPriceView() {
		return totalPriceView;
	}
	public String getPriceView() {
		return priceView;
	}
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
		this.priceView = FormatUtils.formatMoney(price);
		this.totalPriceView = FormatUtils.formatMoney(num*price);
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
	
}
