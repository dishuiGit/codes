package cn.itcast.erp.auth.role.business.ebo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.auth.res.business.ebi.ResEbi;
import cn.itcast.erp.auth.res.vo.ResModel;
import cn.itcast.erp.auth.role.business.ebi.RoleEbi;
import cn.itcast.erp.auth.role.dao.dao.RoleDao;
import cn.itcast.erp.auth.role.vo.RoleModel;

public class RoleEbo implements RoleEbi{
	
	//注入dao
	private RoleDao roleDao;
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void save(RoleModel rm) {
		
	}
	
	public void delete(RoleModel rm) {
		roleDao.delete(rm);
	}
	
	public void update(RoleModel rm) {
		//根据快照更新呢
		RoleModel cache_model = this.getByUuid(rm.getUuid());
		cache_model.setName(rm.getName());
		cache_model.setCode(rm.getCode());
	}

	public List<RoleModel> getAll() {
		return roleDao.getAll();
	}

	public RoleModel getByUuid(Long uuid) {
	
		return roleDao.get(uuid);
	}

	
	public List<RoleModel> getAll(BaseQueryModel bqm) {
	
		return roleDao.getAll(bqm);
	}

	
	public Integer getCount(BaseQueryModel bqm) {
	
		return roleDao.getCount(bqm);
	}

	
	public List<RoleModel> getAll(BaseQueryModel bqm, Integer pageNum,
			Integer pageCount) {
	
		return roleDao.getAll(bqm, pageNum, pageCount);
	}
	//注入ebi
	@Resource(name="resEbi")
	private ResEbi resEbi;
	public void save(RoleModel rm, Long[] resUuids) {
		//数组转换 array===>集合 set
		Set<ResModel> set_tmp = new HashSet<ResModel>();
		for(Long uuid: resUuids){
			ResModel rm_tmp = resEbi.getByUuid(uuid);
			set_tmp.add(rm_tmp);
		}
		rm.setRess(set_tmp);
		roleDao.save(rm);
	}

}
