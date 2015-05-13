package cn.itcast.erp.util.interceptor;

import cn.itcast.erp.auth.emp.vo.EmpModel;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


public class LoginInterceptor extends AbstractInterceptor{

	public String intercept(ActionInvocation invocation) throws Exception {
		//获取本次操作
		
//		System.out.println(invocation.getAction());
//		System.out.println(invocation.getProxy().getAction());
//		System.out.println(invocation.getProxy().getActionName());
//		System.out.println(invocation.getProxy().getMethod());
		
		String className = invocation.getProxy().getAction().getClass().getName();
		String methodName = invocation.getProxy().getMethod();
		String allName = className+"."+methodName;
		//获取本次操作，判断是不是EmpAction类中的login方法
		//"cn.itcast.erp.auth.emp.web.EmpAction.login"
		//0.如果是登陆操作，放行
		
		String operName = invocation.getProxy().getActionName();	//page_login
		//放行登陆页操作
		if("page_login".equals(operName)){
			//跳转到登陆页面，放行
			return invocation.invoke();
		}
		
		if("cn.itcast.erp.auth.emp.web.EmpAction.login".equals(allName)){
			return invocation.invoke();
		}
		
		//1.获取登陆人信息
		EmpModel loginEm = (EmpModel) ActionContext.getContext().getSession().get(EmpModel.LOGIN_EMP_INFO);
		//2.判断是否登陆，如果没有登陆，返回登陆页面
		if(loginEm == null){
			//返回登陆页面
			return "noLogin";
		}
		//如果登陆，放行
		return invocation.invoke();
	}


}
