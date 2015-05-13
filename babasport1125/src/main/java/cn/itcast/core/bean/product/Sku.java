package cn.itcast.core.bean.product;

import java.io.Serializable;
import java.util.Date;

public class Sku implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 颜色ID
     */
    private Integer colorId;

    /**
     * 尺码
     */
    private String size;

    /**
     * 运费 默认10元
     */
    private Double deliveFee;

    /**
     * 售价
     */
    private Double skuPrice;

    /**
     * 库存
     */
    private Integer stockInventory;

    /**
     * 购买限制
     */
    private Integer skuUpperLimit;

    /**
     * 仓库位置:货架号
     */
    private String location;

    /**
     * SKU图片  精确到颜色及尺码对应的图片
     */
    private String skuImg;

    /**
     * 前台显示排序
     */
    private Integer skuSort;

    /**
     * SKU名称
     */
    private String skuName;

    /**
     * 市场价
     */
    private Double marketPrice;

    private Date createTime;

    private Date updateTime;

    private String createUserId;

    private String updateUserId;

    /**
     * 0,历史 1最新
     */
    private Integer lastStatus;

    /**
     * 0:赠品,1普通
     */
    private Integer skuType;

    /**
     * 销量
     */
    private Integer sales;
    
    
    //颜色对象
    private Color color;
    
    //商品
    private Product product;
    
    
    
    

    public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public Double getDeliveFee() {
        return deliveFee;
    }

    public void setDeliveFee(Double deliveFee) {
        this.deliveFee = deliveFee;
    }

    public Double getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(Double skuPrice) {
        this.skuPrice = skuPrice;
    }

    public Integer getStockInventory() {
        return stockInventory;
    }

    public void setStockInventory(Integer stockInventory) {
        this.stockInventory = stockInventory;
    }

    public Integer getSkuUpperLimit() {
        return skuUpperLimit;
    }

    public void setSkuUpperLimit(Integer skuUpperLimit) {
        this.skuUpperLimit = skuUpperLimit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getSkuImg() {
        return skuImg;
    }

    public void setSkuImg(String skuImg) {
        this.skuImg = skuImg == null ? null : skuImg.trim();
    }

    public Integer getSkuSort() {
        return skuSort;
    }

    public void setSkuSort(Integer skuSort) {
        this.skuSort = skuSort;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    public Integer getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(Integer lastStatus) {
        this.lastStatus = lastStatus;
    }

    public Integer getSkuType() {
        return skuType;
    }

    public void setSkuType(Integer skuType) {
        this.skuType = skuType;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", colorId=").append(colorId);
        sb.append(", size=").append(size);
        sb.append(", deliveFee=").append(deliveFee);
        sb.append(", skuPrice=").append(skuPrice);
        sb.append(", stockInventory=").append(stockInventory);
        sb.append(", skuUpperLimit=").append(skuUpperLimit);
        sb.append(", location=").append(location);
        sb.append(", skuImg=").append(skuImg);
        sb.append(", skuSort=").append(skuSort);
        sb.append(", skuName=").append(skuName);
        sb.append(", marketPrice=").append(marketPrice);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", updateUserId=").append(updateUserId);
        sb.append(", lastStatus=").append(lastStatus);
        sb.append(", skuType=").append(skuType);
        sb.append(", sales=").append(sales);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}