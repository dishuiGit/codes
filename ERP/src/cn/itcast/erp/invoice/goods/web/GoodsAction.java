package cn.itcast.erp.invoice.goods.web;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseAction;
import cn.itcast.erp.invoice.goods.business.ebi.GoodsEbi;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.goods.vo.GoodsQueryModel;
import cn.itcast.erp.invoice.goodstype.business.ebi.GoodsTypeEbi;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;

public class GoodsAction extends BaseAction {

	// 注入ebi
	@Resource(name = "goodsEbi")
	private GoodsEbi goodsEbi;
	public String actionName = getActionName(GoodsAction.class);
	// 属性驱动封装数据
	public GoodsModel gm = new GoodsModel();
	public GoodsQueryModel gqm = new GoodsQueryModel();

	public String list() {
		// 查询所有部门
		List<GoodsModel> gmList = goodsEbi.getAll(gqm, (pageNum - 1)
				* pageCount, pageCount);
		// 初始化总页面数
		initTotalPage(goodsEbi.getCount(gqm));
		// 将查询到的集合放入值栈中(这里推荐使用action或request)
		put("gmList", gmList);
		return LIST;
	}

	// 保存部门
	public String save() {
		// 判断是否有uuid
		if (gm.getUuid() != null) {
			// 调用业务层更新
			goodsEbi.update(gm);
		} else {
			goodsEbi.save(gm);
		}
		return TOLIST;
	}

	// 修改
	// 注入供应商 supplierEbi
	@Resource(name = "supplierEbi")
	private SupplierEbi supplierEbi;
	@Resource(name = "gtEbi")
	private GoodsTypeEbi gtEbi;
	public String input() {
		// 查询所有供应商
		List<SupplierModel> supplierList = supplierEbi.getAll();
		put("supplierList", supplierList);
		//查询所有商品类别
		List<GoodsTypeModel> gtmList = null;
		// 新建:判断是否有uuid
		if (gm.getUuid() != null) {
			gm = goodsEbi.getByUuid(gm.getUuid());
			gtmList = supplierEbi.getAllGTBySM(gm.getGtm().getSm().getUuid());
		}else{
			gtmList = supplierEbi.getAllGTBySM(supplierList.get(0).getUuid());
		}
		put("gtmList", gtmList);
		return INPUT;
	}

	// 删除
	public String delete() {
		goodsEbi.delete(gm);
		return TOLIST;
	}
}
