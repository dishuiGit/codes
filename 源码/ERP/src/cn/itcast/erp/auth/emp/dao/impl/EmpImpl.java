package cn.itcast.erp.auth.emp.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.auth.emp.dao.dao.EmpDao;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.auth.emp.vo.EmpQueryModel;
import cn.itcast.erp.util.base.BaseImpl;
import cn.itcast.erp.util.base.BaseQueryModel;

public class EmpImpl extends BaseImpl<EmpModel> implements EmpDao{

	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		EmpQueryModel eqm = (EmpQueryModel)qm;
		if(eqm.getUserName()!=null && eqm.getUserName().trim().length() > 0 ){
			dc.add(Restrictions.like("userName", "%"+eqm.getUserName().trim()+"%"));
		}
		if(eqm.getName()!=null && eqm.getName().trim().length() > 0 ){
			dc.add(Restrictions.like("name", "%"+eqm.getName().trim()+"%"));
		}
		if(eqm.getTele()!=null && eqm.getTele().trim().length() > 0 ){
			dc.add(Restrictions.like("tele", "%"+eqm.getTele().trim()+"%"));
		}
		if(eqm.getGender()!=null && eqm.getGender()!= -1 ){
			dc.add(Restrictions.eq("gender", eqm.getGender()));
		}
		if(eqm.getEmail()!=null && eqm.getEmail().trim().length() > 0 ){
			dc.add(Restrictions.like("email", "%"+eqm.getEmail().trim()+"%"));
		}
		if(eqm.getBirthday()!=null){
			dc.add(Restrictions.ge("birthday", eqm.getBirthday()));
		}
		if(eqm.getBirthday2()!=null){
			dc.add(Restrictions.le("birthday", eqm.getBirthday2()+86400000-1));
		}
		if(eqm.getDm()!=null && eqm.getDm().getUuid()!=null && eqm.getDm().getUuid()!= -1){
			//此格式要求必须按照待查询对象的uuid进行查询，其他属性无效
			//dc.add(Restrictions.eq("dm.uuid", eqm.getDm().getUuid()));
			//此格式要求必须按照待查询对象的uuid进行查询，其他属性无效
			dc.add(Restrictions.eq("dm", eqm.getDm()));
			//非uuid属性如何写？后期查询再介绍：别名
		}
	}
	
	public static void main(String[] args) {
		//System.out.println(System.currentTimeMillis());
		//1425546507819
		//1425513600000
		//1425513600000+86400000-1
		//24*60*60*1000=86400000
		//System.out.println(1425546507819L/1000/60/60/24*24*60*60*1000);
		System.out.println(new Date(1425513600000L));
		System.out.println(new Date(1425513600000L+86400000-1));
		
	}
	
	public EmpModel getByUserNameAndPwd(String userName, String pwd) {
		String hql = "from EmpModel where userName = ? and pwd = ?";
		List<EmpModel> temp = this.getHibernateTemplate().find(hql,userName,pwd);
		return temp.size() > 0 ? temp.get(0) : null;
	}

	public boolean updatePwd(String userName, String pwd, String newPwd) {
		String hql = "update EmpModel set pwd = ? where userName = ? and pwd = ?";
		int count = this.getHibernateTemplate().bulkUpdate(hql, newPwd,userName,pwd);
		return count > 0;
	}

	public List<EmpModel> getAllByDepUuid(Long uuid) {
		String hql = " from EmpModel where dm.uuid = ? ";
		return this.getHibernateTemplate().find(hql,uuid);
	}
}