package cn.itcast.erp.auth.role.business.ebi;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.base.BaseEbi;
import cn.itcast.erp.auth.role.vo.RoleModel;
@Transactional
public interface RoleEbi extends BaseEbi<RoleModel>{
	/**
	 * 保存角色
	 * @param rm
	 * @param resUuids
	 */
	public void save(RoleModel rm, Long[] resUuids);

}
