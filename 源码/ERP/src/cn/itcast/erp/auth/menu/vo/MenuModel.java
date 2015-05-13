package cn.itcast.erp.auth.menu.vo;

import java.util.Set;

import cn.itcast.erp.auth.role.vo.RoleModel;

public class MenuModel {
	public static final Long SYSTEM_MENU_UUID = 1L;
	private Long uuid;
	
	private String name;
	private String url;
	
	//所属父菜单
	private MenuModel parent;
	//拥有的子菜单项
	private Set<MenuModel> children;
	//菜单到角色多对多
	private Set<RoleModel> roles;
	
	public Set<RoleModel> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleModel> roles) {
		this.roles = roles;
	}
	public Set<MenuModel> getChildren() {
		return children;
	}
	public void setChildren(Set<MenuModel> children) {
		this.children = children;
	}
	public Long getUuid() {
		return uuid;
	}
	public MenuModel getParent() {
		return parent;
	}
	public void setParent(MenuModel parent) {
		this.parent = parent;
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
	
}
