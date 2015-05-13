package cn.itcast.erp.util.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.erp.auth.emp.vo.EmpModel;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
//通用
public class BaseAction extends ActionSupport{
	public static final String LIST = "list";
	public static final String TO_LIST = "toList";
	public static final String INPUT = "input";
	
	//当前页码值:初始值为1：当页面未提供页码值，默认显示第一页
	public Integer pageNum = 1;
	//每页显示数据总量
	public Integer pageCount = 10;
	//最大页码值
	public Integer maxPageNum;
	public Integer dataTotal;
	
	protected void setDataTotal(Integer dataTotal){
		this.dataTotal = dataTotal; 
		maxPageNum = (dataTotal+pageCount -1) /pageCount;
	}

	public String getActionName(){
		String allName = getClass().getSimpleName();			//DepAction
		String sub = allName.substring(0, allName.length()-6);	//Dep
		//sub.toLowerCase()		//dep			GoodsTypeAction		goodstype
		String first = sub.substring(0,1).toLowerCase();		//d
		return first+sub.substring(1);
	}
	
	protected void put(String name,Object obj){
		ActionContext.getContext().put(name, obj);
	}
	
	protected void putSession(String name,Object obj){
		ActionContext.getContext().getSession().put(name, obj);
	}
	
	protected Object get(String name){
		return ActionContext.getContext().get(name);
	}
	
	protected Object getSession(String name){
		return ActionContext.getContext().getSession().get(name);
	}
	
	protected EmpModel getLogin(){
		return (EmpModel) getSession(EmpModel.LOGIN_EMP_INFO);
	}
	
	//获取请求与相应对象
	protected HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	
	protected HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}
	
	
}
