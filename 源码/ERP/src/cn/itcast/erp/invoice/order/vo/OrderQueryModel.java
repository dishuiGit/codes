package cn.itcast.erp.invoice.order.vo;

import cn.itcast.erp.util.base.BaseQueryModel;

public class OrderQueryModel extends OrderModel implements BaseQueryModel{
	//订单类别查询条件
	private Integer[] orderTypes;
	private Integer[] transportTaskTypes;
	
	public Integer[] getTransportTaskTypes() {
		return transportTaskTypes;
	}
	public void setTransportTaskTypes(Integer[] transportTaskTypes) {
		this.transportTaskTypes = transportTaskTypes;
	}
	public Integer[] getOrderTypes() {
		return orderTypes;
	}
	public void setOrderTypes(Integer[] orderTypes) {
		this.orderTypes = orderTypes;
	}
	
	// TODO 请添加自定义查询条件
}
