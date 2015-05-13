package cn.itcast.core.bean.product;

import java.io.Serializable;

import cn.itcast.core.web.Constants;

public class Brand implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String description;
	private String imgUrl;//upload/sfsfds.jpg
	private Integer sort;
	private Integer isDisplay;
	
	//全路径
	public String getAllUrl(){
		return Constants.IMG_WEB + imgUrl;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + ", description="
				+ description + ", imgUrl=" + imgUrl + ", sort=" + sort
				+ ", isDisplay=" + isDisplay + "]";
	}
	
}
