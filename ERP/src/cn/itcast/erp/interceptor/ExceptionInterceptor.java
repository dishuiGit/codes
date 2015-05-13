package cn.itcast.erp.interceptor;

import cn.itcast.erp.exception.AppException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ExceptionInterceptor extends AbstractInterceptor{
	//异常统一处理拦截器
	public String intercept(ActionInvocation invocation) throws Exception {
		//拦截抛出异常
		try{
			return invocation.invoke();
			//放行,查看是否携带自定义异常信息
		}catch (AppException e) { //拦截自定义异常
			ActionSupport curAction = (ActionSupport) invocation.getAction();
			curAction.addActionError(e.getMessage());
			return "error";
		}catch (Exception e) {
			//TODO [笔记]使用日志记录工具记录日志
			e.printStackTrace();
			return "serious";
		}
	}

}
