package cn.itcast.erp.auth.menu.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.menu.vo.MenuModel;
import cn.itcast.erp.util.base.BaseEbi;

@Transactional
public interface MenuEbi extends BaseEbi<MenuModel>{
	/**
	 * 获取系统菜单与一级菜单数据
	 * @return
	 */
	public List<MenuModel> getOneLevel();
	/**
	 * 获取指定员工对应的所有一级菜单
	 * @param uuid
	 * @return
	 */
	public List<MenuModel> getAllOneLevelByEmp(Long uuid);
	/**
	 * 获取指定员工对应的指定菜单的所有菜单项
	 * @param uuid 员工uuid
	 * @param puuid 所属菜单的uuid
	 * @return
	 */
	public List<MenuModel> getAllTwoLevelByEmp(Long uuid, Long puuid);

}