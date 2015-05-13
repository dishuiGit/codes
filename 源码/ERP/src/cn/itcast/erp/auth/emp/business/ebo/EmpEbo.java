package cn.itcast.erp.auth.emp.business.ebo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.itcast.erp.auth.emp.business.ebi.EmpEbi;
import cn.itcast.erp.auth.emp.dao.dao.EmpDao;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.auth.role.vo.RoleModel;
import cn.itcast.erp.util.base.BaseQueryModel;
import cn.itcast.erp.util.exception.AppException;
import cn.itcast.erp.util.format.MD5Utils;

public class EmpEbo implements EmpEbi{
	private EmpDao empDao;
	public void setEmpDao(EmpDao empDao) {
		this.empDao = empDao;
	}
	//废弃
	public void save(EmpModel em) {
		/*
		//如果用户没有输入用户名，认定为错误现象
		if(em.getUserName() == null || em.getUserName().trim().length() == 0){
			//错误现象
			throw new AppException("INFO_EMP_SAVE_USERNAME_IS_EMPTY");
		}
		
		//刚才添加的三个字段有没有？
		//设置当前系统时间（注册时间）为新用户最后登录时间
		em.setLastLoginTime(System.currentTimeMillis());
		//设置最后登录IP
		em.setLastLoginIp("-");
		//设置登录总次数
		em.setLoginTimes(0);
		//密码必须进行加密处理
		em.setPwd(MD5Utils.md5(em.getPwd()));
		empDao.save(em);
		*/
	}

	public List<EmpModel> getAll() {
		return empDao.getAll();
	}

	public EmpModel get(Long uuid) {
		return empDao.get(uuid);
	}
	//废弃
	public void update(EmpModel em) {
		/*
		//此时传递过来的em对象如果直接update是全部都修改
		//Hiberate从数据库中取出任何数据，将加载到一级缓存中，如果该数据发生了变化，利用快照思想，该数据将进行更新
		//解决方案2(推荐)：从数据库中取出原始数据，将要修改的数据覆盖到取出的数据中，快照
		EmpModel temp = empDao.get(em.getUuid());
		temp.setName(em.getName());
		temp.setEmail(em.getEmail());
		temp.setTele(em.getTele());
		temp.setAddress(em.getAddress());
		temp.setDm(em.getDm());
		
		//hibernate中的对象的OID不允许修改
		//temp.getDm().setUuid(em.getDm().getUuid());
		*/
		
		/*
		//解决方案1：从数据库中取出原始数据，覆盖传递过来的不能修改的数据，update
		EmpModel temp = empDao.get(em.getUuid());	//数据加载到了一级缓存，PO
		em.setUserName(temp.getUserName());
		//解决方案1补充：释放掉temp对象
		//session.ev...清除指定对象(temp)
		empDao.update(em);							//em->一级缓存，PO
		*/
	}

	public void delete(EmpModel em) {
		empDao.delete(em);
	}

	public List<EmpModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return empDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return empDao.getCount(qm);
	}
	
	public EmpModel login(String userName, String pwd, String loginIp) {
		//根据用户名密码登陆
		//密码需要进行加密（MD5）
		pwd = MD5Utils.md5(pwd);
		EmpModel loginEm = empDao.getByUserNameAndPwd(userName,pwd);		//PO
		//如果登陆成功，记录登陆信息
		if(loginEm !=null ){
			//最后登录时间：当前系统时间
			loginEm.setLastLoginTime(System.currentTimeMillis());
			//IP地址：只能从请求中获得
			loginEm.setLastLoginIp(loginIp);
			//原次数+1
			loginEm.setLoginTimes(loginEm.getLoginTimes()+1);
		}
		return loginEm;
	}

	public boolean changePwd(String userName, String pwd, String newPwd) {
		pwd = MD5Utils.md5(pwd);
		newPwd = MD5Utils.md5(newPwd);
		return empDao.updatePwd(userName,pwd,newPwd);
	}

	public void save(EmpModel em, Long[] roleUuids) {
		//设置当前系统时间（注册时间）为新用户最后登录时间
		em.setLastLoginTime(System.currentTimeMillis());
		//设置最后登录IP
		em.setLastLoginIp("-");
		//设置登录总次数
		em.setLoginTimes(0);
		//密码必须进行加密处理
		em.setPwd(MD5Utils.md5(em.getPwd()));
		
		//roleUuids中保存有员工到角色的关系
		//将roleUuids转化为关系设置到em对象中
		Set<RoleModel> roles = new HashSet<RoleModel>();
		//roleUuids中的数据->集合Set中的数据
		//array->set
		for(Long uuid:roleUuids){
			RoleModel temp = new RoleModel();
			temp.setUuid(uuid);
			roles.add(temp);
		}
		em.setRoles(roles);
		empDao.save(em);
	}
	
	public void update(EmpModel em, Long[] roleUuids) {
		EmpModel temp = empDao.get(em.getUuid());
		temp.setName(em.getName());
		temp.setEmail(em.getEmail());
		temp.setTele(em.getTele());
		temp.setAddress(em.getAddress());
		temp.setDm(em.getDm());
		
		//当前更新使用的是快照思想对temp对象进行
		//array->set->temp
		Set<RoleModel> roles = new HashSet<RoleModel>();
		//roleUuids中的数据->集合Set中的数据
		//array->set
		for(Long uuid:roleUuids){
			RoleModel temp2 = new RoleModel();
			temp2.setUuid(uuid);
			roles.add(temp2);
		}
		//set->temp
		temp.setRoles(roles);
		
		//执行关联关系的修改后，执行的SQL语句是什么？
		//关系1,2->1,3
		/*
		1,2->1,3 update
		1,2,3->1,3 delete
		1,2->1,2,3 insert
		1,2->1,3,4 insert update
		*/
		//删除所有的原始关系，重新定义全新的关系
		
	}
	public List<EmpModel> getAllByDep(Long uuid) {
		return empDao.getAllByDepUuid(uuid);
	}
}