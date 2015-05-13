package cn.itcast.erp.auth.emp.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.base.BaseEbi;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.auth.res.vo.ResModel;

@Transactional
public interface EmpEbi extends BaseEbi<EmpModel>{
	/**
	 * 根据登陆用户名,密码查询
	 * @param userName 登陆用户名
	 * @param pwd	密码
	 * @param loginIp 
	 * @return	查询到的empModel
	 */
	EmpModel login(String userName, String pwd, String loginIp);
	
	/**
	 *	根据session中的用户信息,修改密码 
	 * @param userName 用户名
	 * @param pwd 密码
	 * @param newPwd 新密码
	 */
	public void changePwd(String userName, String pwd, String newPwd);
	/**
	 * 保存用户
	 * @param emp 用户
	 * @param roleUuids 角色数组
	 */
	public void save(EmpModel emp, Long[] roleUuids);
	/**
	 * 根据用户uuid 查询用户可操作的资源(方法)
	 * @param uuid 
	 * @return 资源集
	 */
	List<ResModel> getAllResByEmp(Long uuid);


}
