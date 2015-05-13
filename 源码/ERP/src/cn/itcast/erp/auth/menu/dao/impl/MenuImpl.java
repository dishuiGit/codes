package cn.itcast.erp.auth.menu.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.auth.menu.dao.dao.MenuDao;
import cn.itcast.erp.auth.menu.vo.MenuModel;
import cn.itcast.erp.auth.menu.vo.MenuQueryModel;
import cn.itcast.erp.util.base.BaseImpl;
import cn.itcast.erp.util.base.BaseQueryModel;

public class MenuImpl extends BaseImpl<MenuModel> implements MenuDao{

	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		MenuQueryModel mqm = (MenuQueryModel)qm;
		//过滤掉系统菜单
		//uuid!=1
		dc.add(Restrictions.not(Restrictions.eq("uuid", MenuModel.SYSTEM_MENU_UUID)));
		//dc.add(Restrictions.not(Restrictions.le("aa", 1)));
		//dc.add(Restrictions.gt("aa", 1));

		//name
		if(mqm.getName()!=null && mqm.getName().trim().length() > 0){
			dc.add(Restrictions.like("name", "%"+mqm.getName().trim()+"%"));
		}
		//parent.uuid
		if(mqm.getParent()!=null && mqm.getParent().getUuid()!=null && mqm.getParent().getUuid()!= -1){
			dc.add(Restrictions.eq("parent", mqm.getParent()));
		}
	}

	public List<MenuModel> getUuidOrPuuidIsOne() {
		String hql = " from MenuModel where uuid = ? or parent.uuid = ?";
		return this.getHibernateTemplate().find(hql,MenuModel.SYSTEM_MENU_UUID,MenuModel.SYSTEM_MENU_UUID);
	}

	public List<MenuModel> getAllOneLevelByEmpUuid(Long uuid) {
		//员工->角色->菜单
		//菜单->角色->员工
		String hql = "select distinct mm from MenuModel mm join mm.roles role join role.emps emp where emp.uuid = ? and mm.parent.uuid = ? order by mm.uuid";
		return this.getHibernateTemplate().find(hql,uuid,MenuModel.SYSTEM_MENU_UUID);
	}

	public List<MenuModel> getAllByEmpAndPuuid(Long uuid, Long puuid) {
		String hql = "select distinct mm from MenuModel mm join mm.roles role join role.emps emp where emp.uuid = ? and mm.parent.uuid = ? order by mm.uuid";
		return this.getHibernateTemplate().find(hql,uuid,puuid);
	}
}


