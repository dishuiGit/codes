package cn.itcast.erp.invoice.supplier.vo;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;

public class SupplierModel {
	public static final Map<Integer, String> needsViewMap = new LinkedHashMap<Integer,String>();
	static{
		needsViewMap.put(1, "自提");
		needsViewMap.put(2, "送货");
	}	
	private Long uuid;
	private String name;
	private String address;
	private String tele;
	private String contact;
	//送货方式
	private Integer needs;
	//视图值
	private String needsView;
	
	//关系:供应商-->商品类别[一]-->多
	private Set<GoodsTypeModel> gtms = new HashSet<GoodsTypeModel>();
	
	public Set<GoodsTypeModel> getGtms() {
		return gtms;
	}
	public void setGtms(Set<GoodsTypeModel> gtms) {
		this.gtms = gtms;
	}
	public String getNeedsView() {
		return needsView;
	}
	public Integer getNeeds() {
		return needs;
	}
	public void setNeeds(Integer needs) {
		this.needs = needs;
		this.needsView =needsViewMap.get(needs)  ;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTele() {
		return tele;
	}
	public void setTele(String tele) {
		this.tele = tele;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
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
	
	
	
}
