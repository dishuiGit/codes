package cn.itcast.erp.invoice.order.vo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailModel;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;
import cn.itcast.erp.util.format.FormatUtils;

public class OrderModel {
	public static final Integer ORDER_ORDERTYPE_OF_BUY = 1; 
	public static final Integer ORDER_ORDERTYPE_OF_SALE = 2; 
	public static final Integer ORDER_ORDERTYPE_OF_RETURN_BUY = 3; 
	public static final Integer ORDER_ORDERTYPE_OF_RETURN_SALE = 4; 
	
	public static final String ORDER_ORDERTYPE_OF_BUY_VIEW = "采购";
	public static final String ORDER_ORDERTYPE_OF_SALE_VIEW = "销售"; 
	public static final String ORDER_ORDERTYPE_OF_RETURN_BUY_VIEW = "采购退货"; 
	public static final String ORDER_ORDERTYPE_OF_RETURN_SALE_VIEW = "销售退货";
	
	public static final Integer ORDER_TYPE_OF_BUY_NO_CHECK = 111; 
	public static final Integer ORDER_TYPE_OF_BUY_NO_PASS = 120; 
	public static final Integer ORDER_TYPE_OF_BUY_PASS = 121; 
	public static final Integer ORDER_TYPE_OF_BUY_BUYYING = 131; 
	public static final Integer ORDER_TYPE_OF_BUY_IN_STORE = 141; 
	public static final Integer ORDER_TYPE_OF_BUY_COMPLETE = 199;
	
	public static final String ORDER_TYPE_OF_BUY_NO_CHECKE_VIEW = "未审核";
	public static final String ORDER_TYPE_OF_BUY_NO_PASS_VIEW = "驳回";
	public static final String ORDER_TYPE_OF_BUY_PASS_VIEW = "通过";
	public static final String ORDER_TYPE_OF_BUY_BUYYING_VIEW = "采购中";
	public static final String ORDER_TYPE_OF_BUY_IN_STORE_VIEW = "入库中";
	public static final String ORDER_TYPE_OF_BUY_COMPLETE_VIEW = "已结单";

	public static final Integer ORDER_TYPE_OF_SALE_NO_CHECK = 211; 
	public static final Integer ORDER_TYPE_OF_SALE_NO_PASS = 220; 
	
	public static final String ORDER_TYPE_OF_SALE_NO_CHECKE_VIEW = "未审核";
	public static final String ORDER_TYPE_OF_SALE_NO_PASS_VIEW = "驳回";
	
	private static final Map<Integer, String> orderTypeMap = new HashMap<Integer, String>();
	public static final Map<Integer, String> buyTypeMap = new TreeMap<Integer, String>();
	public static final Map<Integer, String> saleTypeMap = new TreeMap<Integer, String>();
	private static final Map<Integer, String> typeMap = new HashMap<Integer, String>();
	static{
		orderTypeMap.put(ORDER_ORDERTYPE_OF_BUY, ORDER_ORDERTYPE_OF_BUY_VIEW);
		orderTypeMap.put(ORDER_ORDERTYPE_OF_SALE, ORDER_ORDERTYPE_OF_SALE_VIEW);
		orderTypeMap.put(ORDER_ORDERTYPE_OF_RETURN_BUY, ORDER_ORDERTYPE_OF_RETURN_BUY_VIEW);
		orderTypeMap.put(ORDER_ORDERTYPE_OF_RETURN_SALE, ORDER_ORDERTYPE_OF_RETURN_SALE_VIEW);

		buyTypeMap.put(ORDER_TYPE_OF_BUY_NO_CHECK, ORDER_TYPE_OF_BUY_NO_CHECKE_VIEW);
		buyTypeMap.put(ORDER_TYPE_OF_BUY_NO_PASS, ORDER_TYPE_OF_BUY_NO_PASS_VIEW);
		buyTypeMap.put(ORDER_TYPE_OF_BUY_PASS, ORDER_TYPE_OF_BUY_PASS_VIEW);
		buyTypeMap.put(ORDER_TYPE_OF_BUY_BUYYING, ORDER_TYPE_OF_BUY_BUYYING_VIEW);
		buyTypeMap.put(ORDER_TYPE_OF_BUY_IN_STORE, ORDER_TYPE_OF_BUY_IN_STORE_VIEW);
		buyTypeMap.put(ORDER_TYPE_OF_BUY_COMPLETE, ORDER_TYPE_OF_BUY_COMPLETE_VIEW);

		saleTypeMap.put(ORDER_TYPE_OF_SALE_NO_CHECK, ORDER_TYPE_OF_SALE_NO_CHECKE_VIEW);
		saleTypeMap.put(ORDER_TYPE_OF_SALE_NO_PASS, ORDER_TYPE_OF_SALE_NO_PASS_VIEW);
		
		typeMap.putAll(buyTypeMap);
		typeMap.putAll(saleTypeMap);
		
	}
	
	private Long uuid;
	//订单号
	private String orderNum;
	
	//制单时间
	private Long createTime;
	//审核时间
	private Long checkTime;
	//结单时间
	private Long endTime;
	//订单类别：采购，销售，采购退货，销售退货
	private Integer orderType;
	//订单状态
	private Integer type;
	//订单的状态都应该有哪些状态
	/*
	未审核	111
	驳回		120
	通过		121
	采购中	131	
	入库中	141
	已结单	199
	*/
	private String createTimeView;
	private String checkTimeView;
	private String endTimeView;
	private String orderTypeView;
	private String typeView;
	
	//制单人
	private EmpModel creater;
	//审核人
	private EmpModel checker;
	//跟单人
	private EmpModel completer;
	//对应供应商
	private SupplierModel sm;
	//订单到明细一对多
	private Set<OrderDetailModel> odms;
	
	//追加两个字段
	private Integer totalNum;
	private Double totalPrice;
	
	private String totalPriceView;
	
	public Set<OrderDetailModel> getOdms() {
		return odms;
	}
	public void setOdms(Set<OrderDetailModel> odms) {
		this.odms = odms;
	}
	public String getTotalPriceView() {
		return totalPriceView;
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
		this.totalPriceView = FormatUtils.formatMoney(totalPrice);
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
	public String getOrderTypeView() {
		return orderTypeView;
	}
	public String getTypeView() {
		return typeView;
	}
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
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
		this.createTimeView = FormatUtils.formatDate(createTime);
	}
	public Long getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Long checkTime) {
		this.checkTime = checkTime;
		this.checkTimeView = FormatUtils.formatDate(checkTime);
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
		this.endTimeView = FormatUtils.formatDate(endTime);
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
		this.orderTypeView = orderTypeMap.get(orderType);
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
		this.typeView = typeMap.get(type);
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
	public SupplierModel getSm() {
		return sm;
	}
	public void setSm(SupplierModel sm) {
		this.sm = sm;
	}
	
	
}
