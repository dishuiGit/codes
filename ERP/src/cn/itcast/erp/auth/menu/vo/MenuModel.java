package cn.itcast.erp.auth.menu.vo;

import java.util.HashSet;
import java.util.Set;

import cn.itcast.erp.auth.role.vo.RoleModel;

public class MenuModel {

	public static final Long SYSTEM_MENU_UUID = 1l;
	
	private Long uuid;
	
	private String name;
	private String url;
	
	//关系-- 子对父,[多]对一
	private MenuModel parentMM;
	//关系-- 父对子,[一]对多
	private Set<MenuModel> children = new HashSet<MenuModel>();
	//关系-- 菜单对角色,[多]对多
	private Set<RoleModel> roles = new HashSet<RoleModel>();
	
	
	public Set<MenuModel> getChildren() {
		return children;
	}
	public void setChildren(Set<MenuModel> children) {
		this.children = children;
	}
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public MenuModel getParentMM() {
		return parentMM;
	}
	public void setParentMM(MenuModel parentMM) {
		this.parentMM = parentMM;
	}
	public Set<RoleModel> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleModel> roles) {
		this.roles = roles;
	}
	
	
}
