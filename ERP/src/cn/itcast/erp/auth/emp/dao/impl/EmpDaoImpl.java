package cn.itcast.erp.auth.emp.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.erp.auth.base.BaseDaoImpl;
import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.auth.emp.dao.dao.EmpDao;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.auth.emp.vo.EmpQueryModel;
import cn.itcast.erp.auth.res.vo.ResModel;

public class EmpDaoImpl extends BaseDaoImpl<EmpModel> implements EmpDao {

	public EmpModel getByNameAndPwd(String userName, String pwd) {
		String hql = "from EmpModel where userName = ? and pwd = ?";
		List<EmpModel> tmp = this.getHibernateTemplate().find(hql, userName,
				pwd);
		return tmp.size() > 0 ? tmp.get(0) : null;
	}

	public DetachedCriteria doQBC(BaseQueryModel bqm, Class<EmpModel> entryclass) {
		// TODO 添加查询条件 userName,name,tele,gender,email,birthday,birthday2,dm
		DetachedCriteria dc = DetachedCriteria.forClass(entryclass);
		EmpQueryModel eqm = (EmpQueryModel) bqm;
		// userName
		if (eqm.getUserName() != null && eqm.getUserName().trim().length() > 0) {
			dc.add(Restrictions.like("userName", "%" + eqm.getUserName() + "%"));
		}
		// name
		if (eqm.getName() != null && eqm.getName().trim().length() > 0) {
			dc.add(Restrictions.like("name", "%" + eqm.getName() + "%"));
		}
		// tele
		if (eqm.getTele() != null && eqm.getTele().trim().length() > 0) {
			dc.add(Restrictions.like("tele", "%" + eqm.getTele() + "%"));
		}
		// gender
		if (eqm.getGender() != null && eqm.getGender()!=-1) {
			dc.add(Restrictions.eq("gender", eqm.getGender()));
		}
		// email
		if (eqm.getEmail() != null && eqm.getEmail().trim().length() > 0) {
			dc.add(Restrictions.like("email", "%" + eqm.getEmail() + "%"));
		}
		// dm
		if (eqm.getDm() != null && eqm.getDm().getUuid()!=null&&eqm.getDm().getUuid()!=-1) {
			dc.add(Restrictions.eq("dm.uuid", eqm.getDm().getUuid()));
		}
		//birthday,birthday2
		if(eqm.getBirthday()!=null){
			dc.add(Restrictions.ge("birthday", eqm.getBirthday()));
		}
		if(eqm.getBirthday2()!=null){
			//TODO 时间范围
			dc.add(Restrictions.le("birthday2", eqm.getBirthday2()+86400000-1));
		}
		return dc;
	}

	public void changePwd(String userName, String pwd, String newPwd) {
		String hql = "update EmpModel set pwd = ? where userName = ? and pwd = ?";
		this.getHibernateTemplate().bulkUpdate(hql, newPwd,userName,pwd);
	}

	public List<ResModel> getResByUuid(Long uuid) {
		//TODO 内连接应用场景,书写方式
		//内连接 过来 不需要的信息
		// rm join rm.ress
		String hql = "select res from EmpModel em join em.roles role join role.ress res where em.uuid = ?";
		return this.getHibernateTemplate().find(hql, uuid);
	}

}
