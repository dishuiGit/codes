package cn.itcast.erp.invoice.storeDetail.web;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseAction;
import cn.itcast.erp.invoice.order.business.ebi.OrderEbi;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.store.business.ebi.StoreEbi;
import cn.itcast.erp.invoice.store.vo.StoreModel;
import cn.itcast.erp.invoice.storeDetail.business.ebi.StoreDetailEbi;
import cn.itcast.erp.invoice.storeDetail.vo.StoreDetailModel;
import cn.itcast.erp.invoice.storeDetail.vo.StoreDetailQueryModel;

public class StoreDetailAction extends BaseAction{

	// 注入business
	private StoreDetailEbi storeDetailEbi;
	public void setDepEbi(StoreDetailEbi storeDetailEbi) {
		this.storeDetailEbi = storeDetailEbi;
	}

	public String actionName = getActionName(StoreDetailAction.class);
	// 属性驱动封装数据
	public StoreDetailModel sm = new StoreDetailModel();
	public StoreDetailQueryModel sqm = new StoreDetailQueryModel();
	@Resource(name="orderEbi")
	private OrderEbi orderEbi;
	public Long orderUuid;
	
	public String inDetail(){
		//订单,仓库明细
		OrderModel om = orderEbi.getByUuid(orderUuid);
		put("om", om);
		return "inDetail";
	}
	
	// 保存部门
	public String save() {
		// 判断是否有uuid
		if (sm.getUuid() != null) {
			// 调用业务层更新
			storeDetailEbi.update(sm);
		} else {
			storeDetailEbi.save(sm);
		}
		return TOLIST;
	}

	// 修改
	public String input() {
		// 新建:判断是否有uuid
		if (sm.getUuid() != null) {
			sm = storeDetailEbi.getByUuid(sm.getUuid());
		}
		return INPUT;
	}

	// 删除
	public String delete() {
		storeDetailEbi.delete(sm);
		return TOLIST;
	}
}
