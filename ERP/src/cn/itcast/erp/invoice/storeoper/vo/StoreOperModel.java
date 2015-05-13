package cn.itcast.erp.invoice.storeoper.vo;

import java.util.HashMap;
import java.util.Map;

import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.format.DateViewFormat;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.store.vo.StoreModel;

public class StoreOperModel {
	
	public static final Integer OPER_TYPE_OF_IN = 1;
	public static final Integer OPER_TYPE_OF_OUT = 2;
	
	public static final String OPER_TYPE_OF_IN_VIEW = "入库";
	public static final String OPER_TYPE_OF_OUT_VIEW = "出库";
	
	private static final Map<Integer, String> operTypeMap = new HashMap<Integer, String>();
	static {
		operTypeMap.put(OPER_TYPE_OF_IN, OPER_TYPE_OF_IN_VIEW);
		operTypeMap.put(OPER_TYPE_OF_OUT, OPER_TYPE_OF_OUT_VIEW);
	}
	private Long uuid;
	/*	操作数量（出入库数量） num
		操作类别（出入/入库）type
		操作时间 operTime

		操作人 EmpModel
		所属订单  Order
		出入库对应的仓库 StoreModel
		出入库对应的商品 GoodsModel
	
		上述两条进行合并，使用仓库货物明细进行描述*/

	private Integer num;
	//视图
	private Integer type;
	private Long operTime;
	
	private String typeView;
	private String operTimeView;
	
	//关系
	private EmpModel em;
	private OrderModel om;
	private StoreModel sm;
	private GoodsModel gm;
	
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
		this.typeView = operTypeMap.get(type);
	}
	public Long getOperTime() {
		return operTime;
	}
	public void setOperTime(Long operTime) {
		this.operTime = operTime;
		this.operTimeView = DateViewFormat.viewFormat(operTime);
	}
	public EmpModel getEm() {
		return em;
	}
	public void setEm(EmpModel em) {
		this.em = em;
	}
	public OrderModel getOm() {
		return om;
	}
	public void setOm(OrderModel om) {
		this.om = om;
	}
	public StoreModel getSm() {
		return sm;
	}
	public void setSm(StoreModel sm) {
		this.sm = sm;
	}
	public GoodsModel getGm() {
		return gm;
	}
	public void setGm(GoodsModel gm) {
		this.gm = gm;
	}
	
	public String getTypeView() {
		return typeView;
	}
	public String getOperTimeView() {
		return operTimeView;
	}
	
}
