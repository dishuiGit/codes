package cn.itcast.erp.auth.emp.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.erp.auth.dep.business.ebi.DepEbi;
import cn.itcast.erp.auth.dep.vo.DepModel;
import cn.itcast.erp.auth.emp.business.ebi.EmpEbi;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.auth.emp.vo.EmpQueryModel;
import cn.itcast.erp.auth.res.business.ebi.ResEbi;
import cn.itcast.erp.auth.res.vo.ResModel;
import cn.itcast.erp.auth.role.business.ebi.RoleEbi;
import cn.itcast.erp.auth.role.vo.RoleModel;
import cn.itcast.erp.util.base.BaseAction;

public class EmpAction extends BaseAction{
	public EmpModel em = new EmpModel();
	public EmpQueryModel eqm = new EmpQueryModel();

	private EmpEbi empEbi;
	private DepEbi depEbi;
	private RoleEbi roleEbi;
	private ResEbi resEbi;
	public void setResEbi(ResEbi resEbi) {
		this.resEbi = resEbi;
	}
	public void setRoleEbi(RoleEbi roleEbi) {
		this.roleEbi = roleEbi;
	}
	public void setDepEbi(DepEbi depEbi) {
		this.depEbi = depEbi;
	}
	public void setEmpEbi(EmpEbi empEbi) {
		this.empEbi = empEbi;
	}

	//列表
	public String list(){
		//加载所有的部门信息
		List<DepModel> depList = depEbi.getAll();
		put("depList",depList);
		
		setDataTotal(empEbi.getCount(eqm));
		List<EmpModel> empList = empEbi.getAll(eqm,pageNum,pageCount);
		put("empList", empList);
		return LIST;
	}

	//跳转到添加/修改
	public String input(){
		//加载所有角色信息
		List<RoleModel> roleList = roleEbi.getAll();
		put("roleList",roleList);
		//加载所有的部门信息
		List<DepModel> depList = depEbi.getAll();
		//放入指定范围，页面使用
		put("depList",depList);
		if(em.getUuid()!=null){
			em = empEbi.get(em.getUuid());
			//此时需要将em中所携带的关系roles中的数据转化成页面可以回显的数据格式（roleUuids）
			//set->array
			roleUuids = new Long[em.getRoles().size()];
			int i = 0;
			for(RoleModel temp : em.getRoles()){
				roleUuids[i++] = temp.getUuid();
			}
		}
		return INPUT;
	}

	//http://localhost:8080/ERP/emp_save.action?aa=1&aa=2&em.name=1
	//保存员工对应的角色uuid
	public Long[] roleUuids;
	
	//添加/修改
	public String save(){
		if(em.getUuid() == null){
			empEbi.save(em,roleUuids);
		}else{
			empEbi.update(em,roleUuids);
		}
		return TO_LIST;
	}

	//删除
	public String delete(){
		empEbi.delete(em);
		return TO_LIST;
	}

	//登陆
	public String login(){
		HttpServletRequest request = getRequest();
		String loginIp = request.getHeader("x-forwarded-for"); 
		if(loginIp == null || loginIp.length() == 0 || "unknown".equalsIgnoreCase(loginIp)) { 
			loginIp = request.getHeader("Proxy-Client-IP"); 
		} 
		if(loginIp == null || loginIp.length() == 0 || "unknown".equalsIgnoreCase(loginIp)) { 
			loginIp = request.getHeader("WL-Proxy-Client-IP"); 
		} 
		if(loginIp == null || loginIp.length() == 0 || "unknown".equalsIgnoreCase(loginIp)) { 
			loginIp = request.getRemoteAddr(); 
		}
		//用户名密码封装在了em对象中
		EmpModel loginEm = empEbi.login(em.getUserName(),em.getPwd(),loginIp);
		if(loginEm == null){
			//如果登陆失败，显示错误信息
			this.addActionError("对不起，用户名密码错误！");
			return "loginFail";
		}else{
			//优化当前用户所能操作的资源加载
			//查询出当前用户对应的所有的资源
			List<ResModel> resList = resEbi.getAllByEmp(loginEm.getUuid());
			//放入session范围
			StringBuilder resStr = new StringBuilder();
			for(ResModel rm:resList){
				resStr.append(rm.getUrl());
				resStr.append(";");
			}
			//putSession("loginRes",resStr.toString());
			//将当前用户可操作资源设置为员工模型中的一个变量
			loginEm.setResAll(resStr.toString());
			//如果登陆成功，跳转到主页
			//将登陆人信息放入session
			putSession(EmpModel.LOGIN_EMP_INFO, loginEm);
			return "loginSuccess";
		}
	}
	
	//注销
	public String logout(){
		//清除登陆者信息，session信息
		//1.删除session中的信息
		//2.设置session中的信息为null
		putSession(EmpModel.LOGIN_EMP_INFO,null);
		//跳转到登陆页面
		return "noLogin";
	}
	
	//转向修改密码页面
	public String toChangePwd(){
		return "toChangePwd";
	}
	
	public String newPwd;
	
	//修改密码
	public String changePwd(){
		//将数据进行处理
		//旧密码em.pwd,新密码newPwd
		//1.使用老密码与登陆人的session中的密码进行比对，如果成功，更新新密码
		//问题：客户端可能产生多个用户，最后一个修改密码的人是有效的
		//2.使用老密码与用户名进行查询，得到信息后，使用新密码进行快照更新
		//问题：与问题1相似
		//3.执行update pwd = 新 where userName =用户名 and pwd = 老
		boolean flag = empEbi.changePwd(getLogin().getUserName(),em.getPwd(),newPwd);
		
		if(flag){
			//修改成功
			//返回登陆页
			putSession(EmpModel.LOGIN_EMP_INFO,null);
			return "noLogin";
		}else{
			//......
			return "toChangePwd";
		}
	}
	
}