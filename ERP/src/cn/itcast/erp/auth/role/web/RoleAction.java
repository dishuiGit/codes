package cn.itcast.erp.auth.role.web;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseAction;
import cn.itcast.erp.auth.res.business.ebi.ResEbi;
import cn.itcast.erp.auth.res.vo.ResModel;
import cn.itcast.erp.auth.role.business.ebi.RoleEbi;
import cn.itcast.erp.auth.role.vo.RoleModel;
import cn.itcast.erp.auth.role.vo.RoleQueryModel;

public class RoleAction extends BaseAction{

	//注入ebi
	private RoleEbi roleEbi;
	public void setRoleEbi(RoleEbi roleEbi) {
		this.roleEbi = roleEbi;
	}
	@Resource(name="resEbi")
	private ResEbi resEbi;
	//struts.xml中跳转页面
	public String actionName = getActionName(RoleAction.class);
	//属性模型接收数据
	public RoleModel rm = new RoleModel();
	public RoleQueryModel rqm = new RoleQueryModel();
	
	public String list(){
		List<RoleModel> roleList =  roleEbi.getAll(rqm,(pageNum-1)*pageCount,pageCount);
		initTotalPage(roleEbi.getCount(rqm));
		put("roleList", roleList);
		return LIST;
	}
	//保存部门
	//	收集资源
	public Long[] resUuids;
	public String save(){
		//判断是否有uuid
		if(rm.getUuid()!=null){
			//调用业务层更新
			roleEbi.update(rm);
		}else{
			roleEbi.save(rm,resUuids);
		}
		return TOLIST;
	}
	//修改
	public String input() {
		//查询所有资源
		List<ResModel> resList = resEbi.getAll();
		put("resList",resList);
		//新建:判断是否有uuid
		if(rm.getUuid()!=null){
			rm = roleEbi.getByUuid(rm.getUuid());
		}
		return INPUT;
	}
	//删除
	public String delete(){
		roleEbi.delete(rm);
		return TOLIST;
	}
}
