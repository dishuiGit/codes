package cn.itcast.erp.invoice.order.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.itcast.erp.auth.emp.business.ebi.EmpEbi;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.invoice.goods.business.ebi.GoodsEbi;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.goodstype.business.ebi.GoodsTypeEbi;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.invoice.order.business.ebi.OrderEbi;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.order.vo.OrderQueryModel;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailModel;
import cn.itcast.erp.invoice.store.business.ebi.StoreEbi;
import cn.itcast.erp.invoice.store.vo.StoreModel;
import cn.itcast.erp.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;
import cn.itcast.erp.util.base.BaseAction;

public class OrderAction extends BaseAction{
	public OrderModel om = new OrderModel();
	public OrderQueryModel oqm = new OrderQueryModel();

	private OrderEbi orderEbi;
	private EmpEbi empEbi;
	private GoodsTypeEbi goodsTypeEbi;
	private GoodsEbi goodsEbi;
	private SupplierEbi supplierEbi;
	private StoreEbi storeEbi;
	public void setStoreEbi(StoreEbi storeEbi) {
		this.storeEbi = storeEbi;
	}
	public void setEmpEbi(EmpEbi empEbi) {
		this.empEbi = empEbi;
	}
	public void setGoodsEbi(GoodsEbi goodsEbi) {
		this.goodsEbi = goodsEbi;
	}
	public void setGoodsTypeEbi(GoodsTypeEbi goodsTypeEbi) {
		this.goodsTypeEbi = goodsTypeEbi;
	}
	public void setSupplierEbi(SupplierEbi supplierEbi) {
		this.supplierEbi = supplierEbi;
	}
	public void setOrderEbi(OrderEbi orderEbi) {
		this.orderEbi = orderEbi;
	}
	
	//进入采购列表
	public String buyList(){
		//必须将订单类别限制在采购范围内，查询条件中要添加一个固定的查询条件，oqm.orderType的值为采购
		//方案一：在表现层中设置该条件
		oqm.setOrderType(OrderModel.ORDER_ORDERTYPE_OF_BUY);
		//方案二：在业务层中设置该条件，每一个业务方法都必须重新定义(常用)
		
		setDataTotal(orderEbi.getCount(oqm));
		List<OrderModel> orderList = orderEbi.getAll(oqm, pageNum, pageCount);
		put("orderList",orderList);
		return "buyList";
	}

	//进入采购订单页面
	public String buyInput(){
		//加载具有商品类别，并且商品类别中具有商品的供应商数据
		List<SupplierModel> supplierList = supplierEbi.getAllUnionTwo();
		//第一个供应商对应的具有商品的商品类别数据
		List<GoodsTypeModel> gtmList = goodsTypeEbi.getAllUnionBySm(supplierList.get(0).getUuid());
		//第一个供应商对应的第一个商品类别对应的商品数据
		List<GoodsModel> gmList = goodsEbi.getAllByGtm(gtmList.get(0).getUuid());
		//第一个商品的价格数据（采购）可以省略，包含在商品集合中
		put("supplierList",supplierList);
		put("gtmList",gtmList);
		put("gmList",gmList);
		return "buyInput";
	}
	
	public Long[] goodsUuids;
	public Integer[] nums;
	public Double[] prices;
	//保存采购订单
	public String buyOrderSave(){
		//保存订单  om.sm.uuid(om),三个数组
		/*
		System.out.println(om.getSm().getUuid());
		System.out.println("==================");
		for(Long uuid:goodsUuids){
			System.out.println(uuid);
		}
		System.out.println("==================");
		for(Integer num:nums){
			System.out.println(num);
		}
		System.out.println("==================");
		for(Double price:prices){
			System.out.println(price);
		}
		*/
		//数据已经完全接受到，将数据传递到业务层，完成订单的保存，跳转到列表页
		orderEbi.saveBuyOrder(om,goodsUuids,nums,prices,getLogin());
		return "toBuyList";
	}
	
	//采购订单明细
	public String buyDetail(){
		//根据om.uuid查询对应的数据，展示
		om = orderEbi.get(om.getUuid());
		return "buyDetail";
	}
	
	//进入采购审核列表页
	public String buyCheckList(){
		setDataTotal(orderEbi.getCountBuyCheck(oqm));
		List<OrderModel> orderList = orderEbi.getAllBuyCheck(oqm, pageNum, pageCount);
		put("orderList",orderList);
		return "buyCheckList";
	}
	
	//进入审核页
	public String toBuyCheck(){
		//根据om.uuid查询到对应要审核的数据，显示
		om = orderEbi.get(om.getUuid());
		return "toBuyCheck";
	}
	//采购审核通过
	public String buyCheckPass(){
		orderEbi.buyCheckPass(om.getUuid(),getLogin());
		return "toBuyCheckList";
	}
	//采购审核驳回
	public String buyCheckNoPass(){
		orderEbi.buyCheckNoPass(om.getUuid(),getLogin());
		return "toBuyCheckList";
	}
	
	//----运输任务相关-----------------------------------------------
	//任务指派列表
	public String taskList(){
		setDataTotal(orderEbi.getCountTaskList(oqm));
		List<OrderModel> orderList = orderEbi.getAllTaskList(oqm, pageNum, pageCount);
		put("orderList",orderList);
		return "taskList";
	}
	//跳转到任务指派页
	public String toAssign(){
		//加载当前登陆人所在部门的所有员工信息
		List<EmpModel> empList = empEbi.getAllByDep(getLogin().getDm().getUuid());
		put("empList",empList);
		om = orderEbi.get(om.getUuid());
		return "toAssign";
	}
	//指派任务
	public String assignedTask(){
		//om.uuid,om.completer.uuid
		orderEbi.assignedTask(om.getUuid(),om.getCompleter());
		return "toTaskList";
	}
	
	//获取当前登陆人所有任务
	public String tasks(){
		setDataTotal(orderEbi.getCountTasks(oqm,getLogin()));
		List<OrderModel> orderList = orderEbi.getAllTasks(oqm, pageNum, pageCount,getLogin());
		put("orderList",orderList);
		return "tasks";
	}
	
	//跳转到任务详情
	public String toTask(){
		om = orderEbi.get(om.getUuid());
		return "toTask";
	}
	
	//完成任务
	public String endTask(){
		orderEbi.endTask(om.getUuid());
		return "toTasks";
	}
	
	//----入库相关------------------------------
	//入库任务列表
	public String inStoreList(){
		//获取所有入库相关的订单信息
		setDataTotal(orderEbi.getCountInStore(oqm,getLogin()));
		List<OrderModel> orderList = orderEbi.getAllInStore(oqm, pageNum, pageCount,getLogin());
		put("orderList",orderList);
		return "inStoreList";
	}
	//跳转到入库操作页
	public String toInStore(){
		//加载当前登陆人可以管理的所有仓库信息
		List<StoreModel> storeList = storeEbi.getAllByEmp(getLogin().getUuid());
		put("storeList",storeList);
		om = orderEbi.get(om.getUuid());
		return "toInStore";
	}
	
	//----AJAX--------------------------------------------
	//----AJAX--------------------------------------------
	//----AJAX--------------------------------------------
	//----AJAX--------------------------------------------
	//----AJAX--------------------------------------------
	
	public Long supplierUuid;
	public Long goodsTypeUuid;
	public Long goodsUuid;
	
	private List<GoodsTypeModel> gtmList ;
	private List<GoodsModel> gmList ;
	private GoodsModel gm;
	public List<GoodsTypeModel> getGtmList() {
		return gtmList;
	}
	public List<GoodsModel> getGmList() {
		return gmList;
	}
	public GoodsModel getGm() {
		return gm;
	}
	//根据供应商uuid获取对应的类别和商品信息
	public String ajaxGetGtmAndGm(){
		gtmList = goodsTypeEbi.getAllUnionBySm(supplierUuid);
		gmList = goodsEbi.getAllByGtm(gtmList.get(0).getUuid());
		gm = gmList.get(0);
		//gm = goodsEbi.get(gmList.get(0).getUuid());
		return "ajaxGetGtmAndGm";
	}
	
	//已经使用过的商品uuid字符序列
	public String used;		//"1,2,4,3,6,5,"
	//根据供应商uuid获取对应的类别和商品信息
	public String ajaxGetGtmAndGm2(){
		//public Long goodsUuids;
		//System.out.println("==============="+goodsUuids.length);
		//System.out.println("==============="+goodsUuids[0]);
		//public Long goodsUuids[];
		/*
		String[] uuids = getRequest().getParameterValues("goodsUuids[]");
		for(String s:uuids){
			System.out.println(s);
		}
		*/
		//used(string)->List/Set/Array
		String[] uuidStr = used.split(",");
		Set<Long> uuids = new HashSet<Long>();
		for(String uuid:uuidStr){
			uuids.add(new Long(uuid));
		}
		
		//由于每个类别中具有的商品数量有限，因此当所有当前类别中的商品都使用过后，类别数据也应该被过滤
		gtmList = goodsTypeEbi.getAllUnionBySmAndDelUsed(supplierUuid,uuids);
		//gmList = goodsEbi.getAllByGtm(gtmList.get(0).getUuid());
		//由于商品数据具有重复的，因此需要过滤，过滤方案两种
		//1.方案一：获取全部，删除重复
		//2.方案二：获取时直接将重复的去掉（SQL完成，推荐使用这一种）
		gmList = goodsEbi.getByGtmAndDelUsed(gtmList.get(0).getUuid(),uuids);
		gm = gmList.get(0);
		return "ajaxGetGtmAndGm";
	}
	
	//根据商品类别uuid获取对应的商品信息
	public String ajaxGetGm(){
		gmList = goodsEbi.getAllByGtm(goodsTypeUuid);
		gm = gmList.get(0);
		return "ajaxGetGm";
	}
	//根据商品uuid获取对应的商品数据
	public String ajaxGetOne(){
		gm = goodsEbi.get(goodsUuid);
		return "ajaxGetOne";
	}
	
	public Integer num;
	public Long storeUuid;
	public Long odmUuid;
	private OrderDetailModel odm ;
	public OrderDetailModel getOdm() {
		return odm;
	}
	//ajax入库
	public String ajaxInGoods(){
		odm = orderEbi.inGoods(odmUuid,storeUuid,num,getLogin());
		return "ajaxInGoods";
	}
	
}









