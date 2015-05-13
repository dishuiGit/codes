package cn.itcast.erp.auth.menu.business.ebo;

import java.util.List;

import cn.itcast.erp.auth.menu.business.ebi.MenuEbi;
import cn.itcast.erp.auth.menu.dao.dao.MenuDao;
import cn.itcast.erp.auth.menu.vo.MenuModel;
import cn.itcast.erp.util.base.BaseQueryModel;

public class MenuEbo implements MenuEbi{
	private MenuDao menuDao;
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public void save(MenuModel mm) {
		menuDao.save(mm);
	}

	public List<MenuModel> getAll() {
		return menuDao.getAll();
	}

	public MenuModel get(Long uuid) {
		return menuDao.get(uuid);
	}

	public void update(MenuModel mm) {
		//menuDao.update(mm);
		MenuModel temp = menuDao.get(mm.getUuid());
		temp.setName(mm.getName());
		temp.setUrl(mm.getUrl());
	}

	public void delete(MenuModel mm) {
		//当前mm对象中具有的数据有哪些？
		//uuid
		//parent,children对象是什么值？null
		//hibernate针对null对应的关系的处理策略（带有级联删除）
		
		//必须先将mm对象的关联关系找到，然后才能进行有效的级联删除
		mm = menuDao.get(mm.getUuid());
		menuDao.delete(mm);
	}

	public List<MenuModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return menuDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return menuDao.getCount(qm);
	}

	public List<MenuModel> getOneLevel() {
		return menuDao.getUuidOrPuuidIsOne();
	}

	public List<MenuModel> getAllOneLevelByEmp(Long uuid) {
		return menuDao.getAllOneLevelByEmpUuid(uuid);
	}

	public List<MenuModel> getAllTwoLevelByEmp(Long uuid, Long puuid) {
		return menuDao.getAllByEmpAndPuuid(uuid,puuid);
	}

}