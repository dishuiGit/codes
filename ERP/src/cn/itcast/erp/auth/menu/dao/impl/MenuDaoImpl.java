package cn.itcast.erp.auth.menu.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.auth.base.BaseDaoImpl;
import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.auth.menu.dao.dao.MenuDao;
import cn.itcast.erp.auth.menu.vo.MenuModel;

public class MenuDaoImpl extends BaseDaoImpl<MenuModel> implements MenuDao{

	public DetachedCriteria doQBC(BaseQueryModel bqm,
			Class<MenuModel> entryclass) {
		DetachedCriteria dc = DetachedCriteria.forClass(MenuModel.class);
		//查询模块添加查询条件
		dc.add(Restrictions.not(Restrictions.eq("uuid",MenuModel.SYSTEM_MENU_UUID)));
		return dc;
	}
	
	
	
	public MenuModel get(Long uuid, Long parentuuid) {
		String hql = "from MenuModel where uuid = ?";
		List<MenuModel> mms = this.getHibernateTemplate().find(hql, parentuuid);
		return mms.size()>0?mms.get(0):null;
	}



	public List<MenuModel> getAllOneLevelByUuid() {
		String hql = "from MenuModel where parent_uuid = ?";
		return this.getHibernateTemplate().find(hql, MenuModel.SYSTEM_MENU_UUID);
	}

	public List<MenuModel> getAllTwoLevelByUuid(Long parentUuid) {
		String hql = "from MenuModel where parent_uuid = ?";
		return this.getHibernateTemplate().find(hql, parentUuid);
	}

}
