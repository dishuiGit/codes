package cn.itcast.erp.invoice.bill.web;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.invoice.bill.business.ebi.BillEbi;
import cn.itcast.erp.invoice.orderDetail.vo.OrderDetialModel;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BillAction extends ActionSupport{
	
	//注入ebi
	@Resource(name="billEbi")
	private BillEbi billEbi;
	
	public String buyBillList(){
		//查询订单类别
		List<Object[]> objs = billEbi.getBillData();
		ActionContext.getContext().put("objs", objs);
		//System.out.println(objs);
		return "buyBillList";
	}
	
	
	//--------------------AJAX---------------------------------
	//--------------------AJAX---------------------------------
	//--------------------AJAX---------------------------------
	public Long goodsUuid;
	List<OrderDetialModel>  orderDetialModels;
	public List<OrderDetialModel> getOrderDetialModels() {
		return orderDetialModels;
	}
	public String ajaxBillDetail(){
		//根据订单明细id,查询订单明细,订单
		orderDetialModels = billEbi.getAllDetailByGm(goodsUuid);
		return "billDetail";
	}
}
