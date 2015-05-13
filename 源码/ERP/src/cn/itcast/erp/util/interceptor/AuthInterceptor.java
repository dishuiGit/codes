package cn.itcast.erp.util.interceptor;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.auth.res.business.ebi.ResEbi;
import cn.itcast.erp.auth.res.vo.ResModel;
import cn.itcast.erp.util.exception.AppException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthInterceptor extends AbstractInterceptor{
	//在拦截器中注入的资源没有进行配置，为什么可以直接有值
	private ResEbi resEbi;
	public void setResEbi(ResEbi resEbi) {
		this.resEbi = resEbi;
	}
	
	//优化方案二：
	//问题：登陆用户加载自己所能操作的资源查询次数过多，降低查询次数
	//解决方案：在一个特定的时间点，将当前用户所能操作的所有资源查询出，放置在一个当前用户可以访问的区域中，使用时直接获取
	//位置：session
	//时间：只能执行一次，放置在登陆时完成
	//提高效率，降低查询执行次数
	public String intercept(ActionInvocation invocation) throws Exception {
		String actionName = invocation.getProxy().getAction().getClass().getName();
		String methodName = invocation.getProxy().getMethod();
		String allName = actionName+"."+methodName;
		
		//从ServletContext范围内获取启动服务器时加载的全资源信息
		String allRes = ServletActionContext.getServletContext().getAttribute(ResModel.RES_ALL).toString();
		if(!allRes.contains(allName)){
			//放行
			return invocation.invoke();
		}
		EmpModel loginEm = (EmpModel) ActionContext.getContext().getSession().get(EmpModel.LOGIN_EMP_INFO);
		if(loginEm.getResAll().contains(allName)){
			return invocation.invoke();
		}
		throw new AppException("对不起，您没有访问权限，请不要进行非法操作！");
	}

	//优化方案一：
	//问题：全资源获取在每次操作任何东西时都要进行，该操作将会成为系统瓶颈
	//解决方案：在一个特定的时间点，将所有数据查询出，放置在一个所有用户的共享区域中，使用时直接获取
	//位置：application  ServletContext
	//时间：执行次数为1次，启动服务器时，直接查询
	//提高效率，降低查询执行次数
	public String intercept2(ActionInvocation invocation) throws Exception {
		String actionName = invocation.getProxy().getAction().getClass().getName();
		String methodName = invocation.getProxy().getMethod();
		String allName = actionName+"."+methodName;
		
		//从ServletContext范围内获取启动服务器时加载的全资源信息
		String allRes = ServletActionContext.getServletContext().getAttribute(ResModel.RES_ALL).toString();
		if(!allRes.contains(allName)){
			//放行
			return invocation.invoke();
		}
		
		EmpModel loginEm = (EmpModel) ActionContext.getContext().getSession().get(EmpModel.LOGIN_EMP_INFO);
		List<ResModel> resList = resEbi.getAllByEmp(loginEm.getUuid());
		for(ResModel rm :resList){
			if(rm.getUrl().equals(allName)){
				return invocation.invoke();
			}
		}
		throw new AppException("对不起，您没有访问权限，请不要进行非法操作！");
	}
	
	
	//最原始设计
	public String intercept1(ActionInvocation invocation) throws Exception {
		//1.获取本次操作
		String actionName = invocation.getProxy().getAction().getClass().getName();
		String methodName = invocation.getProxy().getMethod();
		String allName = actionName+"."+methodName;
		
		//1.5不需要拦截的资源，直接放行
		List<ResModel> resAll = resEbi.getAll();
		//如果本次操作不在资源列表中，放行
		//包含性测试
		//测试的内容是一个字符串
		//将被测试的全资源组织成一个超大的字符串，比对
		StringBuilder resStr = new StringBuilder();
		for(ResModel rm:resAll){
			resStr.append(rm.getUrl());
			resStr.append(";");
			//cn.itcat.save;cn.itcast.delete;
		}
		if(!resStr.toString().contains(allName)){
			//放行
			return invocation.invoke();
		}
		
		//2.判断当前用户是否能干这件事
		//当前用户可以执行的事情也就是当前用户可以操作的资源
		//用户->资源  不成立的   用户与资源之间无直接的关系，用户与资源之间可以通过角色关联
		//用户->角色->资源
		EmpModel loginEm = (EmpModel) ActionContext.getContext().getSession().get(EmpModel.LOGIN_EMP_INFO);
		//基于登陆者信息获取对应的资源
		//方式一：当前用户信息中已经存在有你要的数据，直接获取
		//loginEm.getRoles()->rm.getReses()
		//System.out.println(loginEm.getRoles());
		//方式二：当前用户信息中不存在有你要的数据，通过查询再次获取
		
//		System.out.println("=====================");
//		System.out.println(resEbi);
//		System.out.println("=====================");
		
		List<ResModel> resList = resEbi.getAllByEmp(loginEm.getUuid());
		//2.1.如果可以执行，放行
		for(ResModel rm :resList){
			if(rm.getUrl().equals(allName)){
				//当前操作与可操作资源匹配成功
				return invocation.invoke();
			}
		}
		//2.2.如果不能执行，拦截，提示用户，不能进行该操作
		throw new AppException("对不起，您没有访问权限，请不要进行非法操作！");
	}
	
}












