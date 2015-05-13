package cn.itcast.erp.invoice.order.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseAction;
import cn.itcast.erp.auth.emp.business.ebi.EmpEbi;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.exception.AppException;
import cn.itcast.erp.invoice.goods.business.ebi.GoodsEbi;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.goodstype.business.ebi.GoodsTypeEbi;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.invoice.order.business.ebi.OrderEbi;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.order.vo.OrderQueryModel;
import cn.itcast.erp.invoice.orderDetail.vo.OrderDetialModel;
import cn.itcast.erp.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;

public class OrderAction extends BaseAction {

	// 注入ebi
	@Resource(name = "orderEbi")
	private OrderEbi orderEbi;
	public String actionName = getActionName(OrderAction.class);
	// 属性驱动封装数据
	public OrderModel om = new OrderModel();
	public OrderDetialModel odm = new OrderDetialModel();
	public OrderQueryModel oqm = new OrderQueryModel();

	public String list() {
		// 查询所有部门
		List<OrderModel> omList = orderEbi.getAll(oqm, (pageNum - 1)
				* pageCount, pageCount);
		// 初始化总页面数
		initTotalPage(orderEbi.getCount(oqm));
		// 将查询到的集合放入值栈中(这里推荐使用action或request)
		put("omList", omList);
		return LIST;
	}

	// 保存部门
	// 商品的uuid
	public Long[] goodsUuids;
	// 商品的数量
	public Integer[] nums;
	// 商品价格
	public Double[] prices;

	public String save() {
		// // 判断是否有uuid
		// if (om.getUuid() != null) {
		// // 调用业务层更新
		// orderEbi.update(om);
		// } else {
		// orderEbi.save(om);
		// }
		return NONE;
	}

	// 保存订单
	public String buyOrderSave() {

		orderEbi.buyOrderSave(om, getLogin(), goodsUuids, prices, nums);
		return "toBuyList";
	}

	// 修改

	public String input() {
		// 查询所有供应商
		// 新建:判断是否有uuid
		if (om.getUuid() != null) {
			om = orderEbi.getByUuid(om.getUuid());
		}
		return INPUT;
	}

	// -----------------------------------------------
	// ----------------采购订单------------------------------
	// 注入
	@Resource(name = "supplierEbi")
	private SupplierEbi supplierEbi;
	@Resource(name = "gtEbi")
	private GoodsTypeEbi gtEbi;

	//
	public String buyInput() {
		// 查询所有供应商
		List<SupplierModel> supplierList = supplierEbi.getAll();
		put("supplierList", supplierList);
		// 查询指定供应商id的商品分类
		List<GoodsTypeModel> gtList = supplierEbi.getAllGTBySM(supplierList
				.get(0).getUuid());
		put("gtList", gtList);
		// 查询指定类别的商品
		List<GoodsModel> gmList = gtEbi.getAllGmByGtm(gtList.get(0).getUuid());
		put("gmList", gmList);
		// 新建:判断是否有uuid
		if (om.getUuid() != null) {
			om = orderEbi.getByUuid(om.getUuid());
		}
		return "buyInput";
	}

	public String buyList() {
		List<OrderModel> orderList = orderEbi.getAll();
		put("orderList", orderList);
		return "buyList";
	}

	// 详情
	public String inDetialList() {
		om = orderEbi.getByUuid(om.getUuid());
		return "inDetialList";
	}

	// 审批列表
	public String buyCheckList() {
		List<OrderModel> orderList = orderEbi.getByType(oqm);
		put("orderList", orderList);
		return "buyCheckList";
	}

	public String inApprove() {
		om = orderEbi.getByUuid(om.getUuid());
		return "inApprove";
	}

	public String approve() {
		// om的uuid,审核人checker

		orderEbi.buyInApprove(om.getUuid(), getLogin());
		return "toBuyList";
	}

	// -----------------------------------任务指派
	public String taskList() {
		// 供应商
		List<SupplierModel> supplierList = supplierEbi.getAll();
		put("supplierList", supplierList);
		// 过滤订单
		// 查询订单
		/*
		 * List<OrderModel> orderList = orderEbi.getByEmp(getLogin());
		 * put("orderList", orderList);
		 */
		List<OrderModel> orderList = orderEbi.getAll();
		put("orderList", orderList);
		return "taskList";
	}

	// 任务指派
	@Resource(name = "empEbi")
	private EmpEbi empEbi;

	public String assignTask() {
		om = orderEbi.getByUuid(om.getUuid());
		List<EmpModel> empList = empEbi.getAll();
		put("empList", empList);
		return "assignTask";
	}

	// 指派任务
	public String toTasks() {
		// 更新跟单人
		orderEbi.completerUpdate(om.getCompleter().getUuid(), om.getUuid());
		return "toTaskList";
	}

	// 查询任务
	public String queryTasks() {
		// 供应商
		List<SupplierModel> supplierList = supplierEbi.getAll();
		put("supplierList", supplierList);
		// 根据登陆人 查询 订单
		List<OrderModel> orderList = orderEbi.getTasksByEmp(getLogin());
		put("orderList", orderList);
		return "queryTasks";
	}
	//任务详情
	public String taskDetail(){
		//查询订单
		om = orderEbi.getByUuid(om.getUuid());
		return "taskDetail";
	}
	//结束任务
	public String endTask(){
		
		//更新订单状态,
		orderEbi.endTask(om.getUuid());
		return "toQueryTasks";
	}
	// -------------------------------------------------
	// ----------------AJAX-----------------------------
	// -------------------------------------------------
	// 收集参数
	public Long supplierUuid;
	List<GoodsTypeModel> gtList;
	List<GoodsModel> gmList;

	public List<GoodsTypeModel> getGtList() {
		return gtList;
	}

	public List<GoodsModel> getGmList() {
		return gmList;
	}

	// 接收商品的goodUuids
	// public Long[] goodsUuids;
	public String used;

	public String getAllGTBySM() {
		// System.out.println(supplierUuid);

		if (getLogin() == null) {
			throw new AppException("请登录!");
		}
		Set<Long> goodsUuids = new HashSet<Long>();
		if (used != null) {
			String[] tmpStr = used.split(",");
			for (String str : tmpStr) {
				goodsUuids.add(new Long(str));
			}
		}
		// 根据供应商uuid查询
		// 查询指定供应商id的商品分类(过滤已经显示的商品)
		if (goodsUuids.size() > 0) {
			gtList = supplierEbi.newLineBySmAndGm(supplierUuid, goodsUuids);
		} else {
			gtList = supplierEbi.getAllGTBySM(supplierUuid);
		}

		put("gtList", gtList);
		// 查询指定类别的商品
		if (goodsUuids.size() > 0) {
			gmList = goodsEbi
					.getByGtmAndSm(gtList.get(0).getUuid(), goodsUuids);
		} else {
			gmList = goodsEbi.getByGtmAndSm(gtList.get(0).getUuid());
		}
		put("gmList", gmList);
		return "getAllGTBySM";
	}

	// 通过商品类别找商品
	// 收集goodsTypeUuid
	public Long goodsTypeUuid;

	public String getAllGMByGT() {
		if (getLogin() == null) {
			throw new AppException("请登录!");
		}
		// 查询指定类别的商品
		gmList = gtEbi.getAllGmByGtm(goodsTypeUuid);
		put("gmList", gmList);
		return "getAllGMByGT";
	}

	// 通过商品id找到指定商品
	// 注入goodsEbi
	@Resource(name = "goodsEbi")
	private GoodsEbi goodsEbi;
	public Long gmUuid;
	// 提供get方法
	private GoodsModel goods;

	public GoodsModel getGoods() {
		return goods;
	}

	public String getGm() {
		if (getLogin() == null) {
			throw new AppException("请登录!");
		}
		goods = goodsEbi.getByUuid(gmUuid);
		return "getGm";
	}
}
