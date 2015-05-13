package cn.itcast.erp.invoice.storeoper.web;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseAction;
import cn.itcast.erp.invoice.orderDetail.vo.OrderDetialModel;
import cn.itcast.erp.invoice.storeoper.business.ebi.StoreOperEbi;
import cn.itcast.erp.invoice.storeoper.vo.StoreOperModel;
import cn.itcast.erp.invoice.storeoper.vo.StoreOperQueryModel;

public class StoreOperAction extends BaseAction{

	//收集参数
	public StoreOperModel som = new StoreOperModel();
	public StoreOperQueryModel soqm = new StoreOperQueryModel();
	//注入ebi
	@Resource(name="storeOperEbi")
	private StoreOperEbi storeOperEbi;
	
	
	
//	--------------------------------------
//	----------AJAX------------------------
//	--------------------------------------
	//入库
	//收集参数
	public Long orderDetailUuid;
	public Long storeUuid;
	public Long goodsUuid;
	public Integer inStoreNum;
	private OrderDetialModel odm;
	public OrderDetialModel getOdm() {
		return odm;
	}
	public String ajax_inStore(){
		//入库:商品入仓库||谁把商品入仓库||谁把多少商品放入仓库||用户 什么时候 把 什么商品 入 哪个 仓库
		//用户  操作时间 商品 仓库
		//商品的id,仓库的id需要传过来
		odm = storeOperEbi.inStore(getLogin(),orderDetailUuid,storeUuid,goodsUuid,inStoreNum);
		//System.out.println(orderDetailUuid+"="+storeUuid+"="+goodsUuid+"="+inStoreNum);
		return "ajax_inStore";
	}
	
}
