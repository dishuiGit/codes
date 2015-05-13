package cn.dishui.core.po.product;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 商品编号
     */
    private String no;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 重量 单位:克
     */
    private Double weight;

    /**
     * 是否新品:0:旧品,1:新品
     */
    private Boolean isNew;

    /**
     * 是否热销:0,否 1:是
     */
    private Boolean isHot;

    /**
     * 推荐 1推荐 0 不推荐
     */
    private Boolean isCommend;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 添加人ID
     */
    private String createUserId;

    /**
     * 审核时间
     */
    private Date checkTime;

    /**
     * 审核人ID
     */
    private String checkUserId;

    /**
     * 上下架:0否 1是
     */
    private Boolean isShow;

    /**
     * 是否删除:0删除,1,没删除
     */
    private Boolean isDel;

    /**
     * 类型ID
     */
    private Integer typeId;

    /**
     * 品牌ID
     */
    private Integer brandId;

    /**
     * 检索关键词
     */
    private String keywords;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 商品属性集
     */
    private String feature;

    /**
     * 颜色集
     */
    private String color;

    /**
     * 尺寸集
     */
    private String size;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Boolean getIsHot() {
        return isHot;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public Boolean getIsCommend() {
        return isCommend;
    }

    public void setIsCommend(Boolean isCommend) {
        this.isCommend = isCommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId == null ? null : checkUserId.trim();
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature == null ? null : feature.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", no=").append(no);
        sb.append(", name=").append(name);
        sb.append(", weight=").append(weight);
        sb.append(", isNew=").append(isNew);
        sb.append(", isHot=").append(isHot);
        sb.append(", isCommend=").append(isCommend);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", checkTime=").append(checkTime);
        sb.append(", checkUserId=").append(checkUserId);
        sb.append(", isShow=").append(isShow);
        sb.append(", isDel=").append(isDel);
        sb.append(", typeId=").append(typeId);
        sb.append(", brandId=").append(brandId);
        sb.append(", keywords=").append(keywords);
        sb.append(", sales=").append(sales);
        sb.append(", feature=").append(feature);
        sb.append(", color=").append(color);
        sb.append(", size=").append(size);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}