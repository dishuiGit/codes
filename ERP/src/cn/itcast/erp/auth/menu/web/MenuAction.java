package cn.itcast.erp.auth.menu.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.erp.auth.base.BaseAction;
import cn.itcast.erp.auth.menu.business.ebi.MenuEbi;
import cn.itcast.erp.auth.menu.vo.MenuModel;
import cn.itcast.erp.auth.menu.vo.MenuQueryModel;
import cn.itcast.erp.auth.role.business.ebi.RoleEbi;
import cn.itcast.erp.auth.role.vo.RoleModel;

public class MenuAction extends BaseAction{

	//注入ebi
	@Resource(name="menuEbi")
	private MenuEbi menuEbi;
	@Resource(name="roleEbi")
	private RoleEbi roleEbi; 
	public String actionName = getActionName(MenuAction.class);
	//属性驱动封装数据
	public MenuModel mm = new MenuModel();
	public MenuQueryModel mqm = new MenuQueryModel();

	public String list(){
		//查询所有部门
		List<MenuModel> menuList =  menuEbi.getAll(mqm,(pageNum-1)*pageCount,pageCount);
		//初始化总页面数
		initTotalPage(menuEbi.getCount(mqm));
		//将查询到的集合放入值栈中(这里推荐使用action或request)
		put("menuList", menuList);
		return LIST;
	}
	//接收角色 checkboxlist,select
	public Long[] roleUuids;
	//保存
	public String save(){
		//判断是否有uuid
		if(mm.getUuid()!=null){
			//调用业务层更新
			menuEbi.update(mm);
		}else{
			menuEbi.save(mm,roleUuids);
		}
		return TOLIST;
	}
	//修改
	public String input() {
		//所有菜单
		List<MenuModel> parentMMList =  menuEbi.getAllOneLevel();
		put("parentMMList",parentMMList);
		//所有角色名称
		List<RoleModel> roleList =  roleEbi.getAll();
		put("roleList",roleList);
		//新建:判断是否有uuid
		if(mm.getUuid()!=null){
			mm = menuEbi.getByUuid(mm.getUuid());
		}
		return INPUT;
	}
	//删除
	public String delete(){
		menuEbi.delete(mm);
		return TOLIST;
	}
	
	//---------------AJax-----------------------
	//---------------AJax-----------------------
	//---------------AJax-----------------------
	//---------------AJax-----------------------
	//获取request携带的参数
	public String root;
	public String showMenu() throws IOException{
		//获取response
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		//查询所有父菜单
		List<MenuModel> mmsone = menuEbi.getAllOneLevel();
		
		StringBuilder sbf = new StringBuilder();
		if("source".equals(root)){
			sbf.append("[");
			for(MenuModel mm:mmsone){
				sbf.append("{");
				sbf.append("\"id\": \"");
				sbf.append(mm.getUuid().toString());
				sbf.append("\",");
				sbf.append("\"text\": \"");
				sbf.append(mm.getName());
				sbf.append("\",");
				sbf.append("\"hasChildren\": true");
				sbf.append("},");
			}
			sbf.deleteCharAt(sbf.length()-1);
			sbf.append("]");
		}else{
			//字符串转Long值
			Long parentUuid = new Long(root);
			List<MenuModel> mmstwo = menuEbi.getTwoLevelByMenu(parentUuid);
			sbf.append("[");
			for(MenuModel mm:mmstwo){
				sbf.append("{");
				sbf.append("\"text\": \"<a href='");
				sbf.append(mm.getUrl());
				sbf.append("'>");
				sbf.append(mm.getName());
				sbf.append("</a>\"");
				sbf.append("},");
			}
			sbf.deleteCharAt(sbf.length()-1);
			sbf.append("]");
		}
		
		response.getWriter().write(sbf.toString());
		response.flushBuffer();
		return null;
	}
}

