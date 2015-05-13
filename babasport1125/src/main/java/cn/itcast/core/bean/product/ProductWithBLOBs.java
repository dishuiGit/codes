package cn.itcast.core.bean.product;

import java.io.Serializable;

public class ProductWithBLOBs extends Product implements Serializable {
    /**
     * 商品描述
     */
    private String description;

    /**
     * 包装清单
     */
    private String packageList;
    
    
    


    private static final long serialVersionUID = 1L;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPackageList() {
        return packageList;
    }

    public void setPackageList(String packageList) {
        this.packageList = packageList == null ? null : packageList.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", description=").append(description);
        sb.append(", packageList=").append(packageList);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}