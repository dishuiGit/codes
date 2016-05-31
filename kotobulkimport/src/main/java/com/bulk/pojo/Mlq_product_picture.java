package com.bulk.pojo;

import java.io.Serializable;
import java.util.Date;

public class Mlq_product_picture implements Serializable {
    private static final long serialVersionUID = 986961204087563583L;

    /**
     * id
     */
    private Integer id;

    /**
     * å•†å“å¤§å›¾url
     */
    private String imgLarge;

    /**
     * å•†å“ä¸­å›¾ è·¯å¾„
     */
    private String imgMiddle;

    /**
     * å•†å“å°å›¾ è·¯å¾„
     */
    private String imgSmall;

    /**
     * å•†å“åŽŸå§‹å›¾ç‰‡è·¯å¾„
     */
    private String imgOriginal;

    /**
     * åˆ›å»ºæ—¶é—´
     */
    private Date gmtCreate;

    /**
     * æ›´æ–°æ—¶é—´
     */
    private Date gmtModify;

    /**
     * å•†å“id
     */
    private String goodsId;

    /**
     * å›¾ç‰‡æè¿°
     */
    private byte[] imgDesc;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return å•†å“å¤§å›¾url
     */
    public String getImgLarge() {
        return imgLarge;
    }

    /**
     * @param imgLarge 
	 *            å•†å“å¤§å›¾url
     */
    public void setImgLarge(String imgLarge) {
        this.imgLarge = imgLarge;
    }

    /**
     * @return å•†å“ä¸­å›¾ è·¯å¾„
     */
    public String getImgMiddle() {
        return imgMiddle;
    }

    /**
     * @param imgMiddle 
	 *            å•†å“ä¸­å›¾ è·¯å¾„
     */
    public void setImgMiddle(String imgMiddle) {
        this.imgMiddle = imgMiddle;
    }

    /**
     * @return å•†å“å°å›¾ è·¯å¾„
     */
    public String getImgSmall() {
        return imgSmall;
    }

    /**
     * @param imgSmall 
	 *            å•†å“å°å›¾ è·¯å¾„
     */
    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall;
    }

    /**
     * @return å•†å“åŽŸå§‹å›¾ç‰‡è·¯å¾„
     */
    public String getImgOriginal() {
        return imgOriginal;
    }

    /**
     * @param imgOriginal 
	 *            å•†å“åŽŸå§‹å›¾ç‰‡è·¯å¾„
     */
    public void setImgOriginal(String imgOriginal) {
        this.imgOriginal = imgOriginal;
    }

    /**
     * @return åˆ›å»ºæ—¶é—´
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate 
	 *            åˆ›å»ºæ—¶é—´
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return æ›´æ–°æ—¶é—´
     */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
     * @param gmtModify 
	 *            æ›´æ–°æ—¶é—´
     */
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    /**
     * @return å•†å“id
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * @param goodsId 
	 *            å•†å“id
     */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * @return å›¾ç‰‡æè¿°
     */
    public byte[] getImgDesc() {
        return imgDesc;
    }

    /**
     * @param imgDesc 
	 *            å›¾ç‰‡æè¿°
     */
    public void setImgDesc(byte[] imgDesc) {
        this.imgDesc = imgDesc;
    }
}