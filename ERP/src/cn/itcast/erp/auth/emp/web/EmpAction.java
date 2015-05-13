package cn.itcast.erp.auth.emp.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.itcast.erp.auth.base.BaseAction;
import cn.itcast.erp.auth.dep.business.ebi.DepEbi;
import cn.itcast.erp.auth.dep.vo.DepModel;
import cn.itcast.erp.auth.emp.business.ebi.EmpEbi;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.auth.emp.vo.EmpQueryModel;
import cn.itcast.erp.auth.role.business.ebi.RoleEbi;
import cn.itcast.erp.auth.role.vo.RoleModel;

public class EmpAction extends BaseAction {
	// 注入business业务成
	private EmpEbi empEbi;
	public void setEmpEbi(EmpEbi empEbi) {
		this.empEbi = empEbi;
	}
	// 注入depEbi
	private DepEbi depEbi;
	public void setDepEbi(DepEbi depEbi) {
		this.depEbi = depEbi;
	}
	//注入roleEbi
	@Resource(name="roleEbi")
	private RoleEbi roleEbi;
	// 跳转页面
	public String actionName = getActionName(EmpAction.class);
	// 属性驱动封装数据
	public EmpModel emp = new EmpModel();
	public EmpQueryModel eqm = new EmpQueryModel();

	public String list() {
		//查询部门信息
		List<DepModel> depList = depEbi.getAll();
		put("depList", depList);
		// 查询所有用户
		List<EmpModel> empList = empEbi.getAll(eqm, (pageNum - 1) * pageCount,
				pageCount);
		// 初始化总页面数
		initTotalPage(empEbi.getCount(eqm));
		// 将查询到的集合放入值栈中(这里推荐使用action或request)
		put("empList", empList);
		return LIST;
	}

	// 修改
	public String input() {
		List<DepModel> depList = depEbi.getAll();
		put("depList", depList);
		//查询角色列表
		List<RoleModel> roleList = roleEbi.getAll();
		put("roleList", roleList);
		
		// 新建/修改:判断是否有uuid
		if (emp.getUuid() != null) {
			emp = empEbi.getByUuid(emp.getUuid());
			
			//roleUuids初始化为空
			//TODO checklist name 属性 必须是数组 
			//下面这段代码的主要作用:用于checklist的回显
			roleUuids = new Long[roleList.size()];
			int i =0;
			for(RoleModel rm: roleList){
				roleUuids[i++] = rm.getUuid();
			}
		}
		return INPUT;
	}

	// 删除
	public String delete() {
		empEbi.delete(emp);
		return TOLIST;
	}

	// 新建/保存
	//收集部门列表
	public Long[] roleUuids;
	public String save() {
		// 判断是否有uuid
		if (emp.getUuid() != null) {
			// 调用业务层更新
			empEbi.update(emp);
		} else {
			empEbi.save(emp,roleUuids);
		}
		return TOLIST;
	}

	// 登陆
	public String login() {
		// 先从session中取empModel
		EmpModel loginEmp = null;
		loginEmp = getLogin();
		if (loginEmp == null) {
			// 添加登陆ip(lastLoginIp)
			HttpServletRequest request = ServletActionContext.getRequest();
			String loginIp = request.getHeader("x-forwarded-for");
			if (loginIp == null || loginIp.length() == 0
					|| "unknown".equalsIgnoreCase(loginIp)) {
				loginIp = request.getHeader("Proxy-Client-IP");
			}
			if (loginIp == null || loginIp.length() == 0
					|| "unknown".equalsIgnoreCase(loginIp)) {
				loginIp = request.getHeader("WL-Proxy-Client-IP");
			}
			if (loginIp == null || loginIp.length() == 0
					|| "unknown".equalsIgnoreCase(loginIp)) {
				loginIp = request.getRemoteAddr();
			}
			// 根据业务层查询登陆的用户,用户名/密码是否正确
			loginEmp = empEbi.login(emp.getUserName(), emp.getPwd(), loginIp);
		}
		// 判断是否查到用户
		if (loginEmp != null) {
			// 将用户信息放入session中

			putSession(EmpModel.LOGIN_EMP_INFO, loginEmp);
			return "loginSuccess";
		} else {
			// 添加错误提示信息
			this.addActionError("用户名/密码错误!");
			return "loginFail";
		}
	}
	//修改密码
	public String newPwd; //收集新密码
	public String changePwd(){
		//
		//从session中获取emp
		EmpModel em = (EmpModel) get(EmpModel.LOGIN_EMP_INFO);
		put(EmpModel.LOGIN_EMP_INFO,null);
		empEbi.changePwd(em.getUserName(),em.getPwd(),newPwd);
		return "loginFail";
	}
	//登出
	public String logout(){
		//删除session,将存入session中的用户,设为null
		putSession(EmpModel.LOGIN_EMP_INFO, null);
		return "loginFail";
	}

}
