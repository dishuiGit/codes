package cn.itcast.erp.auth.menu.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import cn.itcast.erp.auth.menu.business.ebi.MenuEbi;
import cn.itcast.erp.auth.menu.vo.MenuModel;
import cn.itcast.erp.auth.menu.vo.MenuQueryModel;
import cn.itcast.erp.util.base.BaseAction;

public class MenuAction extends BaseAction{
	public MenuModel mm = new MenuModel();
	public MenuQueryModel mqm = new MenuQueryModel();

	private MenuEbi menuEbi;
	public void setMenuEbi(MenuEbi menuEbi) {
		this.menuEbi = menuEbi;
	}

	//列表
	public String list(){
		//加载系统菜单和一级菜单数据
		List<MenuModel> parentList = menuEbi.getOneLevel();
		put("parentList",parentList);
		//加载
		setDataTotal(menuEbi.getCount(mqm));
		List<MenuModel> menuList = menuEbi.getAll(mqm,pageNum,pageCount);
		put("menuList", menuList);
		return LIST;
	}

	//跳转到添加/修改
	public String input(){
		//加载系统菜单和一级菜单数据
		List<MenuModel> menuList = menuEbi.getOneLevel();
		put("menuList",menuList);
		if(mm.getUuid()!=null){
			mm = menuEbi.get(mm.getUuid());
		}
		return INPUT;
	}

	//添加/修改
	public String save(){
		if(mm.getUuid() == null){
			menuEbi.save(mm);
		}else{
			menuEbi.update(mm);
		}
		return TO_LIST;
	}

	//删除
	public String delete(){
		menuEbi.delete(mm);
		return TO_LIST;
	}
	
	public String root;
	//显示动态菜单
	public void showMenu() throws IOException{
		//String root = getRequest().getParameter("root");

		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		StringBuilder json = new StringBuilder();
		
		json.append("[");
		
		if(root.equals("source")){
			//获取对应要显示的数据，然后使用获取的数据动态产生拼接的json数组串
			List<MenuModel> menuList = menuEbi.getAllOneLevelByEmp(getLogin().getUuid());
			for(MenuModel temp : menuList){
				//一级菜单
				json.append("{\"text\":\"");
				json.append(temp.getName());
				json.append("\",\"hasChildren\":true,\"classes\":\"folder\",\"id\":\"");
				json.append(temp.getUuid());
				json.append("\"},");
			}
		}else{
			Long puuid = new Long(root);
			//获取对应要显示的数据，然后使用获取的数据动态产生拼接的json数组串
			List<MenuModel> menuList = menuEbi.getAllTwoLevelByEmp(getLogin().getUuid(),puuid);
			for(MenuModel temp : menuList){
				//菜单项
				json.append("{\"text\":\"<a class='hei' target='main' href='");
				json.append(temp.getUrl());
				json.append("'>");
				json.append(temp.getName());
				json.append("</a>\",\"classes\":\"file\"},");
			}
		}
		
		json.deleteCharAt(json.length()-1);
		json.append("]");
		pw.write(json.toString());
		pw.flush();
	}
	
	/*
	//显示动态菜单
	public void showMenu() throws IOException{
		//struts2中如何设置一个方法返回json数据
		HttpServletResponse response = getResponse();
		//设置中文字符集过滤
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		StringBuilder json = new StringBuilder();
		json.append("[");
		//获取对应要显示的数据，然后使用获取的数据动态产生拼接的json数组串
		
		List<MenuModel> menuList = menuEbi.getAllOneLevelByEmp(getLogin().getUuid());
		for(MenuModel temp : menuList){
			//一级菜单
			json.append("{\"text\":\"");
			json.append(temp.getName());
			json.append("\",\"hasChildren\":true,\"classes\":\"folder\",\"id\":\"");
			json.append(temp.getUuid());
			json.append("\"},");
		}
		//删除多余的逗号
		json.deleteCharAt(json.length()-1);
		
		json.append("]");
		pw.write(json.toString());
		pw.flush();
	}
	*/
	/*
	//显示动态菜单
	public void showMenu() throws IOException{
		//从请求中的root参数中获取值，如果是source则。。。。否则。。。。
		
		//struts2中如何设置一个方法返回json数据
		HttpServletResponse response = getResponse();
		//设置中文字符集过滤
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		StringBuilder json = new StringBuilder();
		json.append("[");
		//一级菜单
		json.append("{\"text\":\"最帅的菜单\",\"hasChildren\":true,\"classes\":\"folder\"},");
		//菜单项
		json.append("{\"text\":\"第二帅的菜单\",\"classes\":\"file\"},");
		//一级菜单
		json.append("{\"text\":\"ccccc\",\"hasChildren\":true,\"classes\":\"folder\"}");
		
		json.append("]");
		pw.write(json.toString());
		pw.flush();
	}
	*/
	
	
	/*
	//显示动态菜单
	public String showMenu() throws IOException{
		//从请求中的root参数中获取值，如果是source则。。。。否则。。。。
		
		//struts2中如何设置一个方法返回json数据
		HttpServletResponse response = getResponse();
		PrintWriter pw = response.getWriter();
		pw.write("{\"aa\":\"bb\"}");
		pw.flush();
		//return null,return NONE
		return NONE;
	}
	 */
}