package cn.itcast.core.bean.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Order implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 订单号
     */
    private String oid;

    /**
     * 运费
     */
    private Double deliverFee;

    /**
     * 应付金额
     */
    private Double payableFee;

    /**
     * 订单金额
     */
    private Double totalPrice;

    /**
     * 支付方式 0:到付 1:在线 2:邮局 3:公司转帐
     */
    private Integer paymentWay;

    /**
     * 货到付款方式.1现金,2POS刷卡
     */
    private Integer paymentCash;

    /**
     * 送货时间
     */
    private Integer delivery;

    /**
     * 是否电话确认 1:是  0: 否
     */
    private Integer isconfirm;

    /**
     * 支付状态 :0到付1待付款,2已付款,3待退款,4退款成功,5退款失败
     */
    private Integer isPaiy;

    /**
     * 订单状态 0:提交订单 1:仓库配货 2:商品出库 3:等待收货 4:完成 5待退货 6已退货
     */
    private Integer state;

    /**
     * 订单生成时间
     */
    private Date createDate;

    /**
     * 附言
     */
    private String note;

    /**
     * 用户名
     */
    private String buyerId;
    
    
	/** 特殊处理字段   */
	/** 支付方式           */
	public String getPaymentWayName() {
		switch (paymentWay) {
			case 0:  return "货到到付";
			case 1:  return "在线支付";
			case 2:  return "公司转帐";
			case 3:  return "邮局汇款";
			default: return "";
		}
	}
	/** 支付要求     必须是货到付款时  才会有支付要求 分为 0:现金 1:POS机       */
	public String getPaymentCashName() {
		if(null == paymentCash) return null;
		
		switch (paymentCash) {
			case 0:  return "现金";
			case 1:  return "POS机";
			default: return "";
		}
	}
	/** 支付状态        //支付状态 :0到付,1待付款,2已付款,3待退款,4退款成功,5退款失败  */
	public String getIsPaiyName() {
		switch (isPaiy) {
			case 0:  return "货到到付";
			case 1:  return "待付款";
			case 2:  return "已付款";
			case 3:  return "待退款";
			case 4:  return "退款成功";
			case 5:  return "退款失败";
			default: return "";
		}
	}
	/** 订单状态        //订单状态 0:提交订单 1:仓库配货 2:商品出库 3:等待收货 4:完成 5:待退货 6已退货 7已取消  */
	public String getStateName() {
		switch (state) {
			case 0:  return "提交订单";
			case 1:  return "仓库配货";
			case 2:  return "商品出库";
			case 3:  return "等待收货";
			case 4:  return "已完成";
			case 5:  return "待退货";
			case 6:  return "已退货";
			case 7:  return "已取消";
			default: return "";
		}
	}
	/** 送货时间    //  1:工作日，双休日，假日均可送货  2:只双休日，假日送货   3:只工作日送货（双休日，节假日不送）         */
	public String getDeliveryName() {
		switch (delivery) {
		case 1:  return "工作日，双休日，假日均可送货";
		case 2:  return "只双休日，假日送货";
		case 3:  return "只工作日送货（双休日，节假日不送）";
		default: return "";
		}
	}
	/** 电话确认     1:是   0:否          */
	public String getIsconfirmName() {
		switch (isconfirm) {
			case 0:  return "否";
			case 1:  return "是";
			default: return "";
		}
	}


    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }


    public Double getDeliverFee() {
		return deliverFee;
	}

	public void setDeliverFee(Double deliverFee) {
		this.deliverFee = deliverFee;
	}

	public Double getPayableFee() {
        return payableFee;
    }

    public void setPayableFee(Double payableFee) {
        this.payableFee = payableFee;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(Integer paymentWay) {
        this.paymentWay = paymentWay;
    }

    public Integer getPaymentCash() {
        return paymentCash;
    }

    public void setPaymentCash(Integer paymentCash) {
        this.paymentCash = paymentCash;
    }

    public Integer getDelivery() {
        return delivery;
    }

    public void setDelivery(Integer delivery) {
        this.delivery = delivery;
    }

    public Integer getIsconfirm() {
        return isconfirm;
    }

    public void setIsconfirm(Integer isconfirm) {
        this.isconfirm = isconfirm;
    }

    public Integer getIsPaiy() {
        return isPaiy;
    }

    public void setIsPaiy(Integer isPaiy) {
        this.isPaiy = isPaiy;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId == null ? null : buyerId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", oid=").append(oid);
        sb.append(", deliverFee=").append(deliverFee);
        sb.append(", payableFee=").append(payableFee);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", paymentWay=").append(paymentWay);
        sb.append(", paymentCash=").append(paymentCash);
        sb.append(", delivery=").append(delivery);
        sb.append(", isconfirm=").append(isconfirm);
        sb.append(", isPaiy=").append(isPaiy);
        sb.append(", state=").append(state);
        sb.append(", createDate=").append(createDate);
        sb.append(", note=").append(note);
        sb.append(", buyerId=").append(buyerId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}