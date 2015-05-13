package cn.itcast.erp.auth.dep.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.auth.base.BaseDaoImpl;
import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.auth.dep.dao.dao.DepDao;
import cn.itcast.erp.auth.dep.vo.DepModel;
import cn.itcast.erp.auth.dep.vo.DepQueryModel;

public class DepDaoImpl extends BaseDaoImpl<DepModel> implements DepDao{

	public DetachedCriteria doQBC(BaseQueryModel bqm, Class<DepModel> entryclass) {
		DepQueryModel dqm = (DepQueryModel)bqm;
		// QBC条件查询
		DetachedCriteria dc = DetachedCriteria.forClass(entryclass);
		// 组装条件
		if (dqm.getName() != null && dqm.getName().trim().length() > 0) {
			dc.add(Restrictions.like("name", "%" + dqm.getName().trim() + "%"));
		}
		if (dqm.getTele() != null && dqm.getTele().trim().length() > 0) {
			dc.add(Restrictions.like("tele", "%" + dqm.getTele().trim() + "%"));
		}
		return dc;
	}

	
}
