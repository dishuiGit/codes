package cn.itcast.erp.invoice.store.web;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseAction;
import cn.itcast.erp.invoice.order.business.ebi.OrderEbi;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.store.business.ebi.StoreEbi;
import cn.itcast.erp.invoice.store.vo.StoreModel;
import cn.itcast.erp.invoice.store.vo.StoreQueryModel;
import cn.itcast.erp.invoice.storeDetail.business.ebi.StoreDetailEbi;
import cn.itcast.erp.invoice.storeDetail.vo.StoreDetailModel;

public class StoreAction extends BaseAction {

	// 注入business
	@Resource(name="storeEbi")
	private StoreEbi storeEbi;


	public String actionName = getActionName(StoreAction.class);
	// 属性驱动封装数据
	public StoreModel sm = new StoreModel();
	public StoreQueryModel sqm = new StoreQueryModel();
	//
	@Resource(name = "storeDetailEbi")
	private StoreDetailEbi storeDetailEbi;
	/*
	 * public String list(){ List<StoreDetailModel> storeDetailList =
	 * storeDetailEbi.getAllByEmp(getLogin()); // 初始化总页面数
	 * initTotalPage(storeDetailEbi.getCount(sqm)); //
	 * 将查询到的集合放入值栈中(这里推荐使用action或request) put("storeDetailList",
	 * storeDetailList); return LIST; }
	 */
	// 注入orderEbi
	@Resource(name = "orderEbi")
	private OrderEbi orderEbi;

	public String inList() {
		/*
		 * // 查询所有部门
		 */
		// 根据订单状态,查找订单
		List<OrderModel> orderList = orderEbi.getByOrderState();
		put("orderList", orderList);
		return "inList";
	}

	// 保存部门
	public String save() {
		// 判断是否有uuid
		if (sm.getUuid() != null) {
			// 调用业务层更新
			storeEbi.update(sm);
		} else {
			storeEbi.save(sm);
		}
		return TOLIST;
	}

	// 修改
	public String input() {
		// 新建:判断是否有uuid
		if (sm.getUuid() != null) {
			sm = storeEbi.getByUuid(sm.getUuid());
		}
		return INPUT;
	}

	// 删除
	public String delete() {
		storeEbi.delete(sm);
		return TOLIST;
	}

	// -----------------------------------
	// --------------AJAX---------------------
	// -----------------------------------
	// 获取仓库列表
	List<StoreModel> storeList;
	public List<StoreModel> getStoreList() {
		return storeList;
	}

	public String list() {
		// 所有仓库(没有过滤,企业要过滤)
		 storeList = storeEbi.getAll();
		put("storeList", storeList);
		return "storeList";
	}
}
