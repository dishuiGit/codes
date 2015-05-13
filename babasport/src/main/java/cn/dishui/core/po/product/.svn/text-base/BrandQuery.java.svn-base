package cn.dishui.core.po.product;

public class BrandQuery {

	private Integer id;

	private String name;
	private String description;
	private String imgUrl;
	private String sort;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}

	// 当前页 pageNo 每页数 pageSize 总页数 totalSize
	private Integer pageNo = 1;
	private Integer pageSize = 3;
	private Integer startRow;

	public Integer getPageNo() {
		return pageNo;
	}

	// 当设置了当前页,计算开始行
	public void setPageNo(Integer pageNo) {
		this.startRow = (pageNo - 1) * pageSize;
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	
	//option选项  查询
	private String fields;

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}
}
