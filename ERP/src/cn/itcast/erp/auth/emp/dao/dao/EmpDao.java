package cn.itcast.erp.auth.emp.dao.dao;

import java.util.List;

import cn.itcast.erp.auth.base.BaseDao;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.auth.res.vo.ResModel;

public interface EmpDao extends BaseDao<EmpModel>{
	/**
	 * 根据登陆用户名,密码 查询用户
	 * @param userName 登陆用户名
	 * @param pwd	密码
	 * @return 查询到的EmpModel
	 */
	EmpModel getByNameAndPwd(String userName, String pwd);

	public void changePwd(String userName, String pwd, String newPwd);
	/**
	 * 根据uuid 获取资源
	 * @param uuid
	 * @return 资源集
	 */
	List<ResModel> getResByUuid(Long uuid);

}
