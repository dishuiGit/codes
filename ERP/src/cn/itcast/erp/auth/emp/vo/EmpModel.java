package cn.itcast.erp.auth.emp.vo;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import cn.itcast.erp.auth.dep.vo.DepModel;
import cn.itcast.erp.auth.res.vo.ResModel;
import cn.itcast.erp.auth.role.vo.RoleModel;
import cn.itcast.erp.format.DateViewFormat;

public class EmpModel {

	public static final String LOGIN_EMP_INFO = "loginEmp";
	public static final String EMP_GENDER_MAN = "男";
	public static final String EMP_GENDER_WOMAN = "女";
	public static Map<Integer, String> gender_map;
	static {
		gender_map = new LinkedHashMap<Integer, String>();
		gender_map.put(0, EMP_GENDER_WOMAN);
		gender_map.put(1, EMP_GENDER_MAN);
	}
	/*
	 * `uuid` bigint(20) NOT NULL AUTO_INCREMENT, `username` varchar(15) NOT
	 * NULL, `pwd` varchar(32) NOT NULL, `name` varchar(28) NOT NULL,
	 * 
	 * `email` varchar(255) NOT NULL, `tele` varchar(30) NOT NULL, `gender`
	 * int(1) NOT NULL, `address` varchar(255) NOT NULL,
	 * 
	 * `birthday` bigint(20) NOT NULL, `dep_uuid` bigint(20) NOT NULL,
	 * lastLoginIp,loginTimes,lastLoginTime
	 */

	private Long uuid;

	private String userName;
	private String pwd;
	private String name;

	private String email;
	private String tele;
	private String address;
	// 登陆ip
	private String lastLoginIp;

	// 登陆次数
	private Integer loginTimes;
	private Integer gender;
	private Long birthday;
	// 最后一次登录时间
	private Long lastLoginTime;
	// 关系多对一
	private DepModel dm;
	// 员工对角色[多]对多
	private Set<RoleModel> roles = new HashSet<RoleModel>();
	
	public Set<RoleModel> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleModel> roles) {
		this.roles = roles;
	}

	// 视图显示值
	private String birthdayView;
	private String genderView;
	private String lastLoginTimeView;

	public String getLastLoginTimeView() {
		return lastLoginTimeView;
	}

	public String getGenderView() {
		return genderView;
	}

	public String getBirthdayView() {
		return birthdayView;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
		this.genderView = gender_map.get(gender);
	}

	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
		this.birthdayView = DateViewFormat.viewFormat(birthday);
	}

	public DepModel getDm() {
		return dm;
	}

	public void setDm(DepModel dm) {
		this.dm = dm;
	}

	public Long getUuid() {
		return uuid;
	}

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	public Long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTimeView = DateViewFormat.viewFormat(lastLoginTime);
		this.lastLoginTime = lastLoginTime;
	}

}
