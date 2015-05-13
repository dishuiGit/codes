package cn.itcast.erp.invoice.order.vo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.format.DateViewFormat;
import cn.itcast.erp.format.ViewFormat;
import cn.itcast.erp.invoice.orderDetail.vo.OrderDetialModel;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;

public class OrderModel {

	//订单类型
	public static final Integer ORDER_TYPE_OF_BUY = 1;
	public static final Integer ORDER_TYPE_OF_Sale = 2;
	public static final Integer ORDER_TYPE_OF_RETURN_BUY = 3;
	public static final Integer ORDER_TYPE_OF_RETURN_SALE = 4;
	
	private static final String ORDER_TYPE_OF_BUY_VIEW = "采购";
	private static final String ORDER_TYPE_OF_Sale_VIEW = "销售";
	private static final String ORDER_TYPE_OF_RETURN_BUY_VIEW = "采购退货";
	private static final String ORDER_TYPE_OF_RETURN_SALE_VIEW = "销售退货";
	
	//订单状态
	public static final Integer ORDER_ORDERSTATE_OF_BUY_NO_CHECK = 111;
	public static final Integer ORDER_ORDERSTATE_OF_BUY_NO_PASS = 120;
	public static final Integer ORDER_ORDERSTATE_OF_BUY_PASS = 121;
	public static final Integer ORDER_ORDERSTATE_OF_BUY_BUYYING = 131;
	public static final Integer ORDER_ORDERSTATE_OF_BUY_IN_STORE = 141;
	public static final Integer ORDER_ORDERSTATE_OF_BUY_COMPLETE = 199;
	
	private static final String ORDER_ORDERSTATE_OF_BUY_NO_CHECK_VIEW = "未审核";
	private static final String ORDER_ORDERSTATE_OF_BUY_NO_PASS_VIEW = "驳回";
	private static final String ORDER_ORDERSTATE_OF_BUY_PASS_VIEW = "通过";
	private static final String ORDER_ORDERSTATE_OF_BUY_BUYYING_VIEW = "采购中";
	private static final String ORDER_ORDERSTATE_OF_BUY_IN_STORE_VIEW = "入库中";
	private static final String ORDER_ORDERSTATE_OF_BUY_COMPLETE_VIEW = "已结算";
	
	public static final Map<Integer, String> typeMap = new HashMap<Integer, String>();
	public static final Map<Integer, String> orderStateBuyMap = new HashMap<Integer, String>();
	public static final Map<Integer, String> orderStateSaleMap = new HashMap<Integer, String>();
	public static final Map<Integer, String> orderStateMap = new HashMap<Integer, String>();
	
	static {
		typeMap.put(ORDER_TYPE_OF_BUY, ORDER_TYPE_OF_BUY_VIEW);
		typeMap.put(ORDER_TYPE_OF_RETURN_BUY,ORDER_TYPE_OF_RETURN_BUY_VIEW );
		typeMap.put(ORDER_TYPE_OF_Sale,ORDER_TYPE_OF_Sale_VIEW );
		typeMap.put(ORDER_TYPE_OF_RETURN_SALE,ORDER_TYPE_OF_RETURN_SALE_VIEW );
		//订单状态
		orderStateBuyMap.put(ORDER_ORDERSTATE_OF_BUY_NO_CHECK, ORDER_ORDERSTATE_OF_BUY_NO_CHECK_VIEW);
		orderStateBuyMap.put(ORDER_ORDERSTATE_OF_BUY_NO_PASS, ORDER_ORDERSTATE_OF_BUY_NO_PASS_VIEW);
		orderStateBuyMap.put(ORDER_ORDERSTATE_OF_BUY_PASS, ORDER_ORDERSTATE_OF_BUY_PASS_VIEW);

		orderStateBuyMap.put(ORDER_ORDERSTATE_OF_BUY_BUYYING, ORDER_ORDERSTATE_OF_BUY_BUYYING_VIEW);
		orderStateBuyMap.put(ORDER_ORDERSTATE_OF_BUY_IN_STORE, ORDER_ORDERSTATE_OF_BUY_IN_STORE_VIEW);
		orderStateBuyMap.put(ORDER_ORDERSTATE_OF_BUY_COMPLETE, ORDER_ORDERSTATE_OF_BUY_COMPLETE_VIEW);
		
		orderStateMap.putAll(orderStateBuyMap);
		orderStateMap.putAll(orderStateSaleMap);
		
	}
	//订单号, 
	//制单人,审核人,跟单人,  
	//制单时间,审核时间,跟单完成时间,  
	//订单类型,订单状态,  
	//订单货物总量,订单货物总金额,
	//订单供应商,
	private Long uuid;
	//订单号
	private String orderNum;
	//人/关系
	private EmpModel creater;
	private EmpModel checker;
	private EmpModel completer;
	private Set<OrderDetialModel> odms = new HashSet<OrderDetialModel>();
	//供应商
	private SupplierModel supplier;
	//时间
	private Long createTime;
	private Long checkTime;
	private Long endTime;
	//时间视图值
	private String createTimeView;
	private String checkTimeView;
	private String endTimeView;
	//订单类型/状态
	//订单类型:采购,销售,采购退货,销售退货
	private Integer type;
	//订单状态:
	/*
	 * 未审核 111
	 * 驳回     120
	 * 通过     121
	 * 采购中  131
	 * 入库中  141
	 * 已结单 199
	 */
	private Integer orderState;
	//
	private Integer totalNum;
	private Double totalPrice;
	//总价视图值
	private String typeView;
	private String orderStateView;
	private String totalPriceView;
	
	
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public EmpModel getCreater() {
		return creater;
	}
	public void setCreater(EmpModel creater) {
		this.creater = creater;
	}
	public EmpModel getChecker() {
		return checker;
	}
	public void setChecker(EmpModel checker) {
		this.checker = checker;
	}
	public EmpModel getCompleter() {
		return completer;
	}
	public void setCompleter(EmpModel completer) {
		this.completer = completer;
	}
	public SupplierModel getSupplier() {
		return supplier;
	}
	public void setSupplier(SupplierModel supplier) {
		this.supplier = supplier;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
		this.createTimeView = DateViewFormat.viewFormat(createTime);
	}
	public Long getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Long checkTime) {
		this.checkTime = checkTime;
		if(checkTime!=null){
			this.checkTimeView = DateViewFormat.viewFormat(checkTime);
		}
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
		if(endTime!=null){
			this.endTimeView = DateViewFormat.viewFormat(endTime);
		}
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
		this.typeView = typeMap.get(type);
	}
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
		this.orderStateView = orderStateMap.get(orderState);
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
		this.totalPriceView = ViewFormat.moneyFormat(totalPrice);
	}
	
	public Set<OrderDetialModel> getOdms() {
		return odms;
	}
	public void setOdms(Set<OrderDetialModel> odms) {
		this.odms = odms;
	}
	public String getCreateTimeView() {
		return createTimeView;
	}
	public String getCheckTimeView() {
		return checkTimeView;
	}
	public String getEndTimeView() {
		return endTimeView;
	}
	public String getTotalPriceView() {
		return totalPriceView;
	}
	
	public String getTypeView() {
		return typeView;
	}
	public String getOrderStateView() {
		return orderStateView;
	}
	//------------------------视图值
	
	
	
	
	
	
	
	
}
