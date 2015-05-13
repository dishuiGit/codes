package cn.itcast.erp.invoice.goodstype.vo;

import java.util.HashSet;
import java.util.Set;

import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;

public class GoodsTypeModel {
	
	private Long uuid;
	
	private String name;
	//供货商 [多]对一
	private SupplierModel sm;
	//商品类别[一]对多 商品
	private Set<GoodsModel> gms = new HashSet<GoodsModel>();
	public Set<GoodsModel> getGms() {
		return gms;
	}
	public void setGms(Set<GoodsModel> gms) {
		this.gms = gms;
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
	public SupplierModel getSm() {
		return sm;
	}
	public void setSm(SupplierModel sm) {
		this.sm = sm;
	}
}
