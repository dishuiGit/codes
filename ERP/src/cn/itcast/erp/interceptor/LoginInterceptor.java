package cn.itcast.erp.interceptor;

import org.apache.struts2.ServletActionContext;

import cn.itcast.erp.auth.emp.vo.EmpModel;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor {

	public String intercept(ActionInvocation invocation) throws Exception {
		// 不拦截登陆login方法
		// cn.itcast.erp.auth.emp.web.EmpAction.login
		// 获取类名
		
		String className = invocation.getAction().getClass().getName();
		String methodName = invocation.getProxy().getMethod();
		String actionName = invocation.getProxy().getActionName();
		String totalName = className + "." + methodName;
		if ("cn.itcast.erp.auth.emp.web.EmpAction.login".equals(totalName)) {
			// 判断session中是否有用户登录信息
			EmpModel empModel = (EmpModel) ActionContext.getContext()
					.getSession().get(EmpModel.LOGIN_EMP_INFO);
			if (empModel == null) {
				// 判断request中是否有 用户登录信息
				int paramsize = ServletActionContext.getRequest()
						.getParameterMap().size();
				if (paramsize == 0) {
					return "noLogin";
				}
				return invocation.invoke();
			}
		}
		// 不拦截page_login方法
		if ("page_login".equals(actionName)) {
			return invocation.invoke();
		}
		// 判断session中是否有用户登录信息
		EmpModel empModel = (EmpModel) ActionContext.getContext().getSession()
				.get(EmpModel.LOGIN_EMP_INFO);
		if (empModel == null) {
			return "noLogin";
		}
		return invocation.invoke();
	}
}
