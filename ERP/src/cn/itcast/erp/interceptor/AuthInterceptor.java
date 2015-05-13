package cn.itcast.erp.interceptor;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.auth.emp.business.ebi.EmpEbi;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.auth.res.business.ebi.ResEbi;
import cn.itcast.erp.auth.res.vo.ResModel;
import cn.itcast.erp.exception.AppException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthInterceptor extends AbstractInterceptor {
	@Resource(name = "empEbi")
	private EmpEbi empEbi;
	@Resource(name = "resEbi")
	private ResEbi resEbi;

	public String intercept(ActionInvocation invocation) throws Exception {
		// 资源拦截器
		// 1.为角色 分配 资源
		// 2.角色的资源(方法)
		// 3.角色调用的方法,在角色资源里(放行),不在(提示没有权限)
		String className = invocation.getAction().getClass().getName();
		String methodName = invocation.getProxy().getMethod();
		String actionName = invocation.getProxy().getActionName();
		String allName = className + "." + methodName;

		// 获取所有需要校验的方法(如果不这样,所有方法都要过拦截器,主页的page_login直接爆 nullPointerException)
		List<ResModel> resall = resEbi.getAll();
		// 把所有资源(方法)拼接成字符串,方便比对
		StringBuilder sbf = new StringBuilder();
		for (ResModel tmp_rm : resall) {
			sbf.append(tmp_rm.getUrl());
			// 添加分隔符
			sbf.append(";");
		}
		// 访问方法在所有需要校验的方法中,继续往下执行,否则放行
		if (!sbf.toString().contains(allName)) {
			return invocation.invoke();
		}
		// 2.角色的资源(方法)
		EmpModel em = (EmpModel) ActionContext.getContext().getSession()
				.get(EmpModel.LOGIN_EMP_INFO);
		// 根据用户 id 去查询 用户的可操作资源(方法)
		List<ResModel> ress = empEbi.getAllResByEmp(em.getUuid());
		// 将查找到的用户可访问(所有资源)连接成字符串
		for (ResModel resm : ress) {
			if(resm.getUrl().equals(allName)){
				// 添加分隔符
				return invocation.invoke();
			}
		}
		throw new AppException("权限不够!!!");
	}

}
