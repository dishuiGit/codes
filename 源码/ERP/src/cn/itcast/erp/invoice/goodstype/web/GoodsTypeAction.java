package cn.itcast.erp.invoice.goodstype.web;

import java.util.List;

import cn.itcast.erp.invoice.goodstype.business.ebi.GoodsTypeEbi;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeQueryModel;
import cn.itcast.erp.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;
import cn.itcast.erp.util.base.BaseAction;

public class GoodsTypeAction extends BaseAction {
	public GoodsTypeModel gm = new GoodsTypeModel();
	public GoodsTypeQueryModel gqm = new GoodsTypeQueryModel();

	private GoodsTypeEbi goodsTypeEbi;
	private SupplierEbi supplierEbi;
	public void setSupplierEbi(SupplierEbi supplierEbi) {
		this.supplierEbi = supplierEbi;
	}
	public void setGoodsTypeEbi(GoodsTypeEbi goodsTypeEbi) {
		this.goodsTypeEbi = goodsTypeEbi;
	}

	//列表
	public String list(){
		setDataTotal(goodsTypeEbi.getCount(gqm));
		List<GoodsTypeModel> goodsTypeList = goodsTypeEbi.getAll(gqm,pageNum,pageCount);
		put("goodsTypeList", goodsTypeList);
		return LIST;
	}

	//跳转到添加/修改
	public String input(){
		//加载供应商数据
		List<SupplierModel> supplierList = supplierEbi.getAll();
		put("supplierList",supplierList);
		if(gm.getUuid()!=null){
			gm = goodsTypeEbi.get(gm.getUuid());
		}
		return INPUT;
	}

	//添加/修改
	public String save(){
		if(gm.getUuid() == null){
			goodsTypeEbi.save(gm);
		}else{
			goodsTypeEbi.update(gm);
		}
		return TO_LIST;
	}

	//删除
	public String delete(){
		goodsTypeEbi.delete(gm);
		return TO_LIST;
	}

	
	//----AJAX--------------------------------------------
	//----AJAX--------------------------------------------
	//----AJAX--------------------------------------------
	//----AJAX--------------------------------------------
	//----AJAX--------------------------------------------
	
	public Long supplierUuid;
	
	
	public String getAbc(){
		return "def";
	}
	public Integer getAge(){
		return 123;
	}
	private List<GoodsTypeModel> gtmList;
	public List<GoodsTypeModel> getGtmList(){
		return gtmList;
	}
	
	//ajax获取供应商对应的商品类别
	public String ajaxGetBySm(){
		//System.out.println("run......"+supplierUuid);
		gtmList = goodsTypeEbi.getAllBySm(supplierUuid);
		//数据如何传递到页面，通过json格式进行传递
		//1.将gtmList->json(JSONArray->)
		//2.将json写入response
		
		//使用struts2-json-plugin-2.3.7.jar步骤
		//1.导入struts2-json-plugin-2.3.7.jar包
		//2.定义返回json数据的结果集result的type="json"(每次进行)
		//3.修改对应的action所在的package,使其extends="json-default"(一次性工作)
		//4.该框架规定，只有当前action类中的get方法对应的数据才参与返回
		return "ajaxGetBySm";
	}
	
}

/*
{
	"abc":"def",
	"age":123,
	"em":{"name":"赵云",.....}
	"gtmList":
		[
		 	{
		 		"name":"冰棍",
		 		"sm":
		 			{
		 				"address":"哈尔滨中央大道1号",
		 				"contact":"王冰棍",
		 				"name":"哈尔滨奶制品公司",
		 				"needs":1,
		 				"needsView":"送货",
		 				"tele":"11111111",
		 				"uuid":2
		 			},
		 		"uuid":5
		 	},
		 	{"name":"酸奶","sm":{"address":"哈尔滨中央大道1号","contact":"王冰棍","name":"哈尔滨奶制品公司","needs":1,"needsView":"送货","tele":"11111111","uuid":2},"uuid":6},
		 	{"name":"奶酪","sm":{"address":"哈尔滨中央大道1号","contact":"王冰棍","name":"哈尔滨奶制品公司","needs":1,"needsView":"送货","tele":"11111111","uuid":2},"uuid":7}
		]
}
*/


