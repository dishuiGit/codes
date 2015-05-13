package cn.itcast.erp.auth.res.web;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseAction;
import cn.itcast.erp.auth.res.business.ebi.ResEbi;
import cn.itcast.erp.auth.res.vo.ResModel;
import cn.itcast.erp.auth.res.vo.ResQueryModel;

public class ResAction extends BaseAction {

	// 注入ebi
	@Resource(name = "resEbi")
	private ResEbi resEbi;
	// 属性模型
	// struts.xml中跳转页面
	public String actionName = getActionName(ResAction.class);
	// 属性模型接收数据
	public ResModel res = new ResModel();
	public ResQueryModel rqm = new ResQueryModel();

	public String list() {
		List<ResModel> resList = resEbi.getAll(rqm, (pageNum - 1)
				* pageCount, pageCount);
		initTotalPage(resEbi.getCount(rqm));
		put("resList", resList);
		return LIST;
	}

	// 保存部门

	public String save() {
		// 判断是否有uuid
		if (res.getUuid() != null) {
			// 调用业务层更新
			resEbi.update(res);
		} else {
			resEbi.save(res);
		}
		return TOLIST;
	}

	// 修改
	public String input() {
		// 新建:判断是否有uuid
		if (res.getUuid() != null) {
			res = resEbi.getByUuid(res.getUuid());
		}
		return INPUT;
	}

	// 删除
	public String delete() {
		resEbi.delete(res);
		return TOLIST;
	}
}
