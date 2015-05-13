package cn.itcast.erp.invoice.goodstype.web;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseAction;
import cn.itcast.erp.invoice.goodstype.business.ebi.GoodsTypeEbi;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeQueryModel;
import cn.itcast.erp.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;

public class GoodsTypeAction extends BaseAction {

	// 注入ebi
	@Resource(name = "gtEbi")
	private GoodsTypeEbi gtEbi;
	public String actionName = getActionName(GoodsTypeAction.class);
	// 属性驱动封装数据
	public GoodsTypeModel gtm = new GoodsTypeModel();
	public GoodsTypeQueryModel gtqm = new GoodsTypeQueryModel();

	public String list() {
		// 查询所有部门
		List<GoodsTypeModel> gtList = gtEbi.getAll(gtqm,
				(pageNum - 1) * pageCount, pageCount);
		// 初始化总页面数
		initTotalPage(gtEbi.getCount(gtqm));
		// 将查询到的集合放入值栈中(这里推荐使用action或request)
		put("gtList", gtList);
		return LIST;
	}

	// 保存部门
	public String save() {
		// 判断是否有uuid
		if (gtm.getUuid() != null) {
			// 调用业务层更新
			gtEbi.update(gtm);
		} else {
			gtEbi.save(gtm);
		}
		return TOLIST;
	}

	// 修改
	//注入供应商 supplierEbi
	@Resource(name="supplierEbi")
	SupplierEbi supplierEbi;
	public String input() {
		//查询所有供应商
		List<SupplierModel> supplierList = supplierEbi.getAll();
		put("supplierList",supplierList);
		// 新建:判断是否有uuid
		if (gtm.getUuid() != null) {
			gtm = gtEbi.getByUuid(gtm.getUuid());
		}
		return INPUT;
	}

	// 删除
	public String delete() {
		gtEbi.delete(gtm);
		return TOLIST;
	}
	
	//-------------------AJAX------------------------------------
	//-------------------AJAX------------------------------------
	//-------------------AJAX------------------------------------
	//根据供应商,获取商品类别
	public Long supplierUuid;
	public List<GoodsTypeModel> gtList;
	public List<GoodsTypeModel> getGtList() {
		return gtList;
	}
	public String getAllGT(){
		 gtList = gtEbi.getAllGTBySupplier(supplierUuid);
		return "getAllGT";
	}
}
