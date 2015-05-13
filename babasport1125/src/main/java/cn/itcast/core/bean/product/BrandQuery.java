package cn.itcast.core.bean.product;

import java.io.Serializable;

/**
 * 品牌条件对象
 * @author lx
 *
 */
public class BrandQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String description;
	private String imgUrl;
	private Integer sort;
	private Integer isDisplay;
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
	//分页用到的 Limit参数
	//当前页
	private Integer pageNo;
	//每页数  5条
	private Integer pageSize = 10;
	//开始行   ==  (pageNO - 1) * pageSize;
	private Integer startRow;
	
	
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		//计算开始行
		this.startRow = (pageNo - 1) * pageSize;
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		//计算开始行
		this.startRow = (pageNo - 1) * pageSize;
		this.pageSize = pageSize;
	}
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	//指定字段
	private String fields;
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	
	
	
	
	
	
	
	
}
