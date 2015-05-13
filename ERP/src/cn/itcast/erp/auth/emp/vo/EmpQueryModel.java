package cn.itcast.erp.auth.emp.vo;

import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.format.DateViewFormat;

public class EmpQueryModel extends EmpModel implements BaseQueryModel{
	//添加查询条件
	//出生日期范围
	private Long birthday2;
	
	//jsp页面显示值
	private String birthday2View;
	public String getBirthday2View() {
		return birthday2View;
	}

	public Long getBirthday2() {
		return birthday2;
	}

	public void setBirthday2(Long birthday2) {
		this.birthday2 = birthday2;
		this.birthday2View = DateViewFormat.viewFormat(birthday2);
	}
}
