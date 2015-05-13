package cn.itcast.erp.auth.menu.business.ebo;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.auth.menu.business.ebi.MenuEbi;
import cn.itcast.erp.auth.menu.dao.dao.MenuDao;
import cn.itcast.erp.auth.menu.vo.MenuModel;
import cn.itcast.erp.auth.role.business.ebi.RoleEbi;
import cn.itcast.erp.auth.role.vo.RoleModel;

public class MenuEbo implements MenuEbi{

	//注入menuDao
	@Resource(name="menuDao")
	private MenuDao menuDao;
	public void save(MenuModel mm) {
		menuDao.save(mm);
	}
	
	public void delete(MenuModel mm) {
		//级联删除
		//必须先将mm对象的关联关系找到，然后才能进行有效的级联删除
		mm = menuDao.get(mm.getUuid());
		menuDao.delete(mm);
	}

	public void update(MenuModel mm) {
		menuDao.update(mm);
	}

	public List<MenuModel> getAll() {
		
		return menuDao.getAll();
	}

	public MenuModel getByUuid(Long uuid) {
		return menuDao.get(uuid);
	}
	public List<MenuModel> getAll(BaseQueryModel bqm) {
		
		return menuDao.getAll(bqm);
	}

	public Integer getCount(BaseQueryModel bqm) {
		
		return menuDao.getCount(bqm);
	}

	
	public List<MenuModel> getAll(BaseQueryModel bqm, Integer pageNum,
			Integer pageCount) {
		
		return menuDao.getAll(bqm, pageNum, pageCount);
	}
	//注入roleEbi
	@Resource(name="roleEbi")
	private RoleEbi roleEbi;
	public void save(MenuModel mm, Long[] roleUuids) {
		//array-> set
		Set<RoleModel> mms = mm.getRoles();
		for(Long tmpuuid:roleUuids){
			RoleModel tmprm = roleEbi.getByUuid(tmpuuid);
			mms.add(tmprm);
		}
		mm.setRoles(mms);
		//设置父菜单 
		mm.setParentMM(this.getByPmUuid(mm.getUuid(),mm.getParentMM().getUuid()));
		//
		menuDao.save(mm);
	}

	public MenuModel getByPmUuid(Long uuid, Long parentuuid) {
		return menuDao.get(uuid,parentuuid);
	}

	public List<MenuModel> getAllOneLevel() {
		
		return menuDao.getAllOneLevelByUuid();
	}

	public List<MenuModel> getTwoLevelByMenu(Long parentUuid) {
		
		return menuDao.getAllTwoLevelByUuid(parentUuid);
	}



}
