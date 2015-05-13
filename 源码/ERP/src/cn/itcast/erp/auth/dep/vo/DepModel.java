package cn.itcast.erp.auth.dep.vo;

public class DepModel {
	public DepModel(){
		System.out.println("create.....");
	}
	private Long uuid;
	private String name;
	private String tele;
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
	public String getTele() {
		return tele;
	}
	public void setTele(String tele) {
		this.tele = tele;
	}
	
}
