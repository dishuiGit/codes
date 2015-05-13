package cn.itcast.erp.invoice.supplier.web;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseAction;
import cn.itcast.erp.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;
import cn.itcast.erp.invoice.supplier.vo.SupplierQueryModel;

public class SupplierAction extends BaseAction{

	//注入ebi
	@Resource(name="supplierEbi")
	private SupplierEbi supplierEbi;
	public String actionName = getActionName(SupplierAction.class);
	//属性驱动封装数据
	public SupplierModel sm = new SupplierModel();
	public SupplierQueryModel sqm = new SupplierQueryModel();

	public String list(){
		//查询所有部门
		List<SupplierModel> supplierList =  supplierEbi.getAll(sqm,(pageNum-1)*pageCount,pageCount);
		//初始化总页面数
		initTotalPage(supplierEbi.getCount(sqm));
		//将查询到的集合放入值栈中(这里推荐使用action或request)
		put("supplierList", supplierList);
		return LIST;
	}
	//保存部门
	public String save(){
		//判断是否有uuid
		if(sm.getUuid()!=null){
			//调用业务层更新
			supplierEbi.update(sm);
		}else{
			supplierEbi.save(sm);
		}
		return TOLIST;
	}
	//修改
	public String input() {
		//新建:判断是否有uuid
		if(sm.getUuid()!=null){
			sm = supplierEbi.getByUuid(sm.getUuid());
		}
		return INPUT;
	}
	//删除
	public String delete(){
		supplierEbi.delete(sm);
		return TOLIST;
	}
}
