package cn.itcast.erp.invoice.store.vo;

import cn.itcast.erp.auth.emp.vo.EmpModel;

public class StoreModel {

	//1.仓库名称,2.仓库地址,3,仓库管理员:不同管理员操作的仓库是不同的
	private Long uuid;
	
	private String name;
	private String address;
	//一个仓库对一个管理员
	private EmpModel em;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public EmpModel getEm() {
		return em;
	}
	public void setEm(EmpModel em) {
		this.em = em;
	}
}
