package cn.itcast.erp.auth.emp.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.util.base.BaseEbi;

@Transactional
public interface EmpEbi extends BaseEbi<EmpModel>{
	/**
	 * 根据用户名密码登陆（密码MD5加密）
	 * @param userName 用户名
	 * @param pwd 密码
	 * @param loginIp 登陆IP地址
	 * @return 登陆人信息，如果用户名密码错误，返回null
	 */
	public EmpModel login(String userName, String pwd, String loginIp);
	/**
	 * 修改密码功能
	 * @param userName
	 * @param pwd
	 * @param newPwd
	 * @return 
	 */
	public boolean changePwd(String userName, String pwd, String newPwd);
	/**
	 * 保存用户信息
	 * @param em 用户信息模型
	 * @param roleUuids 用户对应的角色uuid数组
	 */
	public void save(EmpModel em, Long[] roleUuids);
	
	public void update(EmpModel em, Long[] roleUuids);
	/**
	 * 获取指定部门所有员工信息
	 * @param uuid 部门uuid
	 * @return
	 */
	public List<EmpModel> getAllByDep(Long uuid);
}