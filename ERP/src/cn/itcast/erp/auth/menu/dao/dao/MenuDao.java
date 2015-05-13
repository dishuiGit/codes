package cn.itcast.erp.auth.menu.dao.dao;

import java.util.List;

import cn.itcast.erp.auth.base.BaseDao;
import cn.itcast.erp.auth.menu.vo.MenuModel;

public interface MenuDao extends BaseDao<MenuModel>{

	MenuModel get(Long uuid, Long parentuuid);

	List<MenuModel> getAllOneLevelByUuid();

	List<MenuModel> getAllTwoLevelByUuid(Long parentUuid);

}
