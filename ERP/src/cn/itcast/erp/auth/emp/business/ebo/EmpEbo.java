package cn.itcast.erp.auth.emp.business.ebo;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.auth.emp.business.ebi.EmpEbi;
import cn.itcast.erp.auth.emp.dao.dao.EmpDao;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.auth.res.vo.ResModel;
import cn.itcast.erp.auth.role.business.ebi.RoleEbi;
import cn.itcast.erp.auth.role.vo.RoleModel;
import cn.itcast.erp.exception.AppException;
import cn.itcast.erp.format.MD5Utils;

public class EmpEbo implements EmpEbi {

	// 注入empDao
	private EmpDao empDao;

	public void setEmpDao(EmpDao empDao) {
		this.empDao = empDao;
	}

	public void save(EmpModel em) {
		// MD5加密密码
		em.setPwd(MD5Utils.md5(em.getPwd()));
		empDao.save(em);
	}

	public void delete(EmpModel em) {
		empDao.delete(em);
	}

	public void update(EmpModel em) {
		// 从数据库中查询
		EmpModel cache = empDao.get(em.getUuid());
		// 使用快照更新
		cache.setName(em.getName());
		cache.setAddress(em.getAddress());
		cache.setEmail(em.getEmail());
		cache.setTele(em.getTele());
		cache.setDm(em.getDm());
	}

	public List<EmpModel> getAll() {
		return empDao.getAll();
	}

	public EmpModel getByUuid(Long uuid) {
		return empDao.get(uuid);
	}

	public List<EmpModel> getAll(BaseQueryModel bqm) {
		return empDao.getAll(bqm);
	}

	public Integer getCount(BaseQueryModel bqm) {
		return empDao.getCount(bqm);
	}

	public List<EmpModel> getAll(BaseQueryModel bqm, Integer pageNum,
			Integer pageCount) {
		return empDao.getAll(bqm, pageNum, pageCount);
	}

	public EmpModel login(String userName, String pwd, String loginIp) {
		// MD5加密用户密码
		pwd = MD5Utils.md5(pwd);
		// 登陆的时候添加登陆信息(lastLoginIp),登陆次数(+1),最后登陆时间
		EmpModel em = empDao.getByNameAndPwd(userName, pwd);
		//先从数据库中插到信息,存入一级缓存,修改信息与快照比对(利用快照更新)
		if(em == null){
			throw new AppException("请登录!");
		}
		em.setLastLoginIp(loginIp);
		em.setLoginTimes(em.getLoginTimes()+1);
		em.setLastLoginTime(System.currentTimeMillis());
		//二次开发:将查询用户能操作的所有资源放入session中
		
		return em;
	}

	public void changePwd(String userName, String pwd, String newPwd) {
		empDao.changePwd(userName,pwd,newPwd);
	}
	//注入roleEbi
	@Resource(name="roleEbi")
	private RoleEbi roleEbi;
	public void save(EmpModel emp, Long[] roleUuids) {
		//将角色数组==>角色Set
		Set<RoleModel> roles = emp.getRoles();
		for(Long uuid:roleUuids){
			RoleModel tmp = roleEbi.getByUuid(uuid);
			roles.add(tmp);
		}
		emp.setRoles(roles);
		//最后登陆ip
		emp.setLastLoginIp("-");
		//登陆次数
		emp.setLoginTimes(0);
		emp.setLastLoginTime(System.currentTimeMillis());
		empDao.save(emp);
	}

	public List<ResModel> getAllResByEmp(Long uuid) {
		return empDao.getResByUuid(uuid);
	}
}
