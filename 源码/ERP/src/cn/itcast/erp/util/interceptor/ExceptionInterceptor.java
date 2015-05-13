package cn.itcast.erp.util.interceptor;

import cn.itcast.erp.util.exception.AppException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ExceptionInterceptor extends AbstractInterceptor{

	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			return invocation.invoke();
		} catch (AppException e) {
			ActionSupport as = (ActionSupport) invocation.getAction();
			as.addActionError(as.getText(e.getMessage()));
			//将e通过邮件发送给开发者
			return "error";
		} catch(Exception e){
			//记录 日志，发送错误
			e.printStackTrace();
			return invocation.invoke();
		}
	}

}
