package cn.itcast.erp.invoice.orderDetail.web;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseAction;
import cn.itcast.erp.invoice.orderDetail.business.ebi.OrderDetialEbi;
import cn.itcast.erp.invoice.orderDetail.vo.OrderDetialModel;
import cn.itcast.erp.invoice.orderDetail.vo.OrderDetialQueryModel;

public class OrderDetialAction extends BaseAction {

	// 注入ebi
	@Resource(name = "odEbi")
	private OrderDetialEbi odEbi;
	public String actionName = getActionName(OrderDetialAction.class);
	// 属性驱动封装数据
	public OrderDetialModel odm = new OrderDetialModel();
	public OrderDetialQueryModel sqm = new OrderDetialQueryModel();

	public String list() {
		// 查询所有部门
		List<OrderDetialModel> orderDetialList = odEbi.getAll(sqm,
				(pageNum - 1) * pageCount, pageCount);
		// 初始化总页面数
		initTotalPage(odEbi.getCount(sqm));
		// 将查询到的集合放入值栈中(这里推荐使用action或request)
		put("orderDetialList", orderDetialList);
		return LIST;
	}

	// 保存部门
	public String save() {
		// 判断是否有uuid
		if (odm.getUuid() != null) {
			// 调用业务层更新
			odEbi.update(odm);
		} else {
			odEbi.save(odm);
		}
		return TOLIST;
	}

	// 修改
	public String input() {
		// 新建:判断是否有uuid
		if (odm.getUuid() != null) {
			odm = odEbi.getByUuid(odm.getUuid());
		}
		return INPUT;
	}

	// 删除
	public String delete() {
		odEbi.delete(odm);
		return TOLIST;
	}
}
