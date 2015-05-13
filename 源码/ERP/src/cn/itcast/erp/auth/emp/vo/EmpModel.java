package cn.itcast.erp.auth.emp.vo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.itcast.erp.auth.dep.vo.DepModel;
import cn.itcast.erp.auth.role.vo.RoleModel;
import cn.itcast.erp.util.format.FormatUtils;

//时间数据为什么使用 Long
//Date  d1	Date  d2
//Date  d1  求出3天前的数据
//2016/02/29
//707238947389257  -3*24*60*60*1000
//System.currentTimeMillis()	->   long

public class EmpModel {
	public static final String LOGIN_EMP_INFO = "loginEm";
	
	//缓存的设计
	public static final Integer EMP_GENDER_OF_MAN = 1;
	public static final Integer EMP_GENDER_OF_WOMAN = 0;
	
	public static final String EMP_GENDER_OF_MAN_VIEW = "男";
	public static final String EMP_GENDER_OF_WOMAN_VIEW = "女";
	
	public static final Map<Integer, String> genderMap = new HashMap<Integer, String>();
	static{
		genderMap.put(EMP_GENDER_OF_MAN, EMP_GENDER_OF_MAN_VIEW);
		genderMap.put(EMP_GENDER_OF_WOMAN, EMP_GENDER_OF_WOMAN_VIEW);
	}
	
	
	private Long uuid;
	
	private String userName;
	private String pwd;
	private String name;
	private String email;
	private String tele;
	private String address;
	private String lastLoginIp;
	
	private Integer loginTimes;
	
	private Integer gender;
	//long型的日期无法直接显示，添加一个新的变量，用来辅助long型日期进行显示
	private Long lastLoginTime;
	private Long birthday;
	//视图值
	//由于真实值不能直接显示，所以设置对应的视图值
	//视图值是由真实值计算而来
	//视图值的类型定义：String
	//视图值的名称定义：真实值变量+View
	//视图值的初始化：在真实值初始化时进行
	private String birthdayView;
	private String lastLoginTimeView;
	//类别视图值
	//定义所有的类别真实值常量
	//定义所有的类别视图值常量
	//使真实值常量与视图值常量产生一对一的对应关系（Map）
	//使用值，仅需提供真实值，即可快速获取视图值
	private String genderView;
	
	//辅助字段
	private String resAll;
	
	//对部门一对多
	private DepModel dm;
	//对角色多对多
	private Set<RoleModel> roles;
	
	public Set<RoleModel> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleModel> roles) {
		this.roles = roles;
	}

	public String getResAll() {
		return resAll;
	}

	public void setResAll(String resAll) {
		this.resAll = resAll;
	}

	public String getGenderView() {
		return genderView;
	}

	public String getLastLoginTimeView() {
		return lastLoginTimeView;
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
		this.lastLoginTime = lastLoginTime;
		this.lastLoginTimeView = FormatUtils.formatDate(lastLoginTime);
	}

	public String getBirthdayView() {
		return birthdayView;
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
		this.genderView = genderMap.get(gender);
	}

	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
		this.birthdayView = FormatUtils.formatDate(birthday);
	}

	public DepModel getDm() {
		return dm;
	}

	public void setDm(DepModel dm) {
		this.dm = dm;
	}

	public static String getLoginEmpInfo() {
		return LOGIN_EMP_INFO;
	}
	
}
