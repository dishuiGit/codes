package cn.itcast.erp.invoice.goods.vo;

import cn.itcast.erp.util.base.BaseQueryModel;

public class GoodsQueryModel extends GoodsModel implements BaseQueryModel{
	private Double inPrice2;
	private Double outPrice2;
	public Double getInPrice2() {
		return inPrice2;
	}
	public void setInPrice2(Double inPrice2) {
		this.inPrice2 = inPrice2;
	}
	public Double getOutPrice2() {
		return outPrice2;
	}
	public void setOutPrice2(Double outPrice2) {
		this.outPrice2 = outPrice2;
	}
}
