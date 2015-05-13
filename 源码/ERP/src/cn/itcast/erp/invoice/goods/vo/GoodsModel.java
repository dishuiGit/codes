package cn.itcast.erp.invoice.goods.vo;

import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.util.format.FormatUtils;

public class GoodsModel {
	
	@Override
	public String toString() {
		return "GoodsModel [uuid=" + uuid + ", name=" + name + "]";
	}

	private Long uuid;
	
	private String name;
	private String origin;
	private String producer;
	private String unit;
	private Integer useNum;
	private Integer minNum;
	private Integer maxNum;
	
	private Double inPrice;
	private Double outPrice;
	
	private String inPriceView;
	private String outPriceView;
	
	private GoodsTypeModel gtm ;

	public Long getUuid() {
		return uuid;
	}

	public Integer getUseNum() {
		return useNum;
	}

	public Integer getMinNum() {
		return minNum;
	}

	public void setMinNum(Integer minNum) {
		this.minNum = minNum;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public void setUseNum(Integer useNum) {
		this.useNum = useNum;
	}

	public String getInPriceView() {
		return inPriceView;
	}

	public String getOutPriceView() {
		return outPriceView;
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

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getInPrice() {
		return inPrice;
	}

	public void setInPrice(Double inPrice) {
		this.inPrice = inPrice;
		this.inPriceView = FormatUtils.formatMoney(inPrice);
	}

	public Double getOutPrice() {
		return outPrice;
	}

	public void setOutPrice(Double outPrice) {
		this.outPrice = outPrice;
		this.outPriceView = FormatUtils.formatMoney(outPrice);
	}

	public GoodsTypeModel getGtm() {
		return gtm;
	}

	public void setGtm(GoodsTypeModel gtm) {
		this.gtm = gtm;
	}

}
