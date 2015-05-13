package cn.dishui.core.po.product;

import java.io.Serializable;

public class Img implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 图片URL
     */
    private String url;

    /**
     * 是否默认:0否 1:是
     */
    private Boolean isDef;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Boolean getIsDef() {
        return isDef;
    }

    public void setIsDef(Boolean isDef) {
        this.isDef = isDef;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", url=").append(url);
        sb.append(", isDef=").append(isDef);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}