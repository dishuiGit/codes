package cn.itcast.erp.auth.role.vo;

import java.util.HashSet;
import java.util.Set;

import cn.itcast.erp.auth.res.vo.ResModel;

public class RoleModel {

	private Long uuid;
	private String name;
	private String code;

	// 关系配置
	// 角色关联资源
	// 角色==资源 [多]对多
	private Set<ResModel> ress = new HashSet<ResModel>();

	public Set<ResModel> getRess() {
		return ress;
	}

	public void setRess(Set<ResModel> ress) {
		this.ress = ress;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
