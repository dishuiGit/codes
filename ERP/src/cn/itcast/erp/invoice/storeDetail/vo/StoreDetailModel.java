package cn.itcast.erp.invoice.storeDetail.vo;

import java.util.HashSet;
import java.util.Set;

import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.store.vo.StoreModel;

public class StoreDetailModel {

	private Long uuid;
	private Integer num;
	//多个商品明细 对 一个仓库
	private StoreModel sm;
	//商品明细 多 对商品[1]
	private GoodsModel gm;
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public StoreModel getSm() {
		return sm;
	}
	public void setSm(StoreModel sm) {
		this.sm = sm;
	}
	public GoodsModel getGm() {
		return gm;
	}
	public void setGm(GoodsModel gm) {
		this.gm = gm;
	}
	
}
