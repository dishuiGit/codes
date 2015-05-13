package cn.itcast.erp.auth.dep.web;

import java.util.List;

import cn.itcast.erp.auth.base.BaseAction;
import cn.itcast.erp.auth.dep.business.ebi.DepEbi;
import cn.itcast.erp.auth.dep.vo.DepModel;
import cn.itcast.erp.auth.dep.vo.DepQueryModel;

public class DepAction extends BaseAction{
	//注入business
	private DepEbi depEbi;
	public void setDepEbi(DepEbi depEbi) {
		this.depEbi = depEbi;
	}
	public String actionName = getActionName(DepAction.class);
	//属性驱动封装数据
	public DepModel dm = new DepModel();
	public DepQueryModel dqm = new DepQueryModel();

	public String list(){
		//查询所有部门
		List<DepModel> depList =  depEbi.getAll(dqm,(pageNum-1)*pageCount,pageCount);
		//初始化总页面数
		initTotalPage(depEbi.getCount(dqm));
		//将查询到的集合放入值栈中(这里推荐使用action或request)
		put("depList", depList);
		return LIST;
	}
	//保存部门
	public String save(){
		//判断是否有uuid
		if(dm.getUuid()!=null){
			//调用业务层更新
			depEbi.update(dm);
		}else{
			depEbi.save(dm);
		}
		return TOLIST;
	}
	//修改
	public String input() {
		//新建:判断是否有uuid
		if(dm.getUuid()!=null){
			dm = depEbi.getByUuid(dm.getUuid());
		}
		return INPUT;
	}
	//删除
	public String delete(){
		depEbi.delete(dm);
		return TOLIST;
	}
}
