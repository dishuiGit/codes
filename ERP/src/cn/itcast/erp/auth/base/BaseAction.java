package cn.itcast.erp.auth.base;

import cn.itcast.erp.auth.dep.business.ebi.DepEbi;
import cn.itcast.erp.auth.emp.vo.EmpModel;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{
	public static final String LIST = "list";
	public static final String INPUT = "input";
	public static final String TOLIST = "toList";
	
	//当前页数,每页显示数
	public Integer pageNum=1;
	public Integer pageCount = 10;
	//总页数
	//总数据数量
	public Integer totalPageCount;
	public Integer totalPage;
	public void initTotalPage(Integer totalPageCount){
		this.totalPageCount = totalPageCount;
		this.totalPage = (totalPageCount+pageCount-1)/pageCount;
		
	}
	//定义actionName,用于找对应的jsp页面
	public String getActionName(Class clazz){
		String classname = clazz.getSimpleName();
		//DepAction GoodTypeClass
		String first = classname.substring(0,1);
		String last = classname.substring(1,(classname.length()-6));
		String actionName = first.toLowerCase()+last;
		return actionName;
	}
	
	public void put(String name,Object obj){
		ActionContext.getContext().put(name, obj);
	}
	public Object get(String name){
		return ActionContext.getContext().get(name);
	}
	
	public void putSession(String name,Object obj){
		ActionContext.getContext().getSession().put(name, obj);
	}

	public Object getSession(String name){
		return ActionContext.getContext().getSession().get(name);
	}

	public EmpModel getLogin(){
		
		return (EmpModel) getSession(EmpModel.LOGIN_EMP_INFO);
	}
}
