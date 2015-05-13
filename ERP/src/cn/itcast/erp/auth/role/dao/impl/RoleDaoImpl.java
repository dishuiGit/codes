package cn.itcast.erp.auth.role.dao.impl;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.erp.auth.base.BaseDaoImpl;
import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.auth.role.dao.dao.RoleDao;
import cn.itcast.erp.auth.role.vo.RoleModel;

public class RoleDaoImpl extends BaseDaoImpl<RoleModel> implements RoleDao{

	public DetachedCriteria doQBC(BaseQueryModel bqm,
			Class<RoleModel> entryclass) {
		DetachedCriteria dc = DetachedCriteria.forClass(RoleModel.class);
		//添加查询条件
		
		return dc;
	}

}
