package cn.itcast.erp.auth.emp.vo;

import cn.itcast.erp.util.base.BaseQueryModel;
import cn.itcast.erp.util.format.FormatUtils;

public class EmpQueryModel extends EmpModel implements BaseQueryModel{
	// TODO 请添加自定义查询条件
	private Long birthday2;	//表示最大值，原始的birthday表示最小值

	private String birthday2View;
	
	public String getBirthday2View() {
		return birthday2View;
	}
	public Long getBirthday2() {
		return birthday2;
	}
	public void setBirthday2(Long birthday2) {
		this.birthday2 = birthday2;
		this.birthday2View = FormatUtils.formatDate(birthday2);
	}
	
}
