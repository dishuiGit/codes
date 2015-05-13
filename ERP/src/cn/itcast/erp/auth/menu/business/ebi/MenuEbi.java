package cn.itcast.erp.auth.menu.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.base.BaseEbi;
import cn.itcast.erp.auth.menu.vo.MenuModel;

@Transactional
public interface MenuEbi extends BaseEbi<MenuModel>{
	/**
	 * 保存菜单
	 * @param mm 菜单
	 * @param roleUuids 菜单关联的角色
	 */
	public void save(MenuModel mm, Long[] roleUuids);
	/**
	 * 根据子菜单uuid和父菜单uuid 查询父菜单
	 * @param uuid 子菜单uuid
	 * @param parentuuid 父菜单uuid
	 * @return 父菜单结果集
	 */
	public MenuModel getByPmUuid(Long uuid, Long parentuuid);
	/**
	 * 获得所有一级菜单
	 * @return
	 */
	public List<MenuModel> getAllOneLevel();
	/**
	 * 查询所有二级菜单
	 * @param parentUuid 父菜单uuid
	 * @return 二级菜单结果集
	 */
	public List<MenuModel> getTwoLevelByMenu(Long parentUuid);
}
