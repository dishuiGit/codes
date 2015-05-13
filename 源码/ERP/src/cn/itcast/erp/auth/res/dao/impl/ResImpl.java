package cn.itcast.erp.auth.res.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.erp.auth.res.dao.dao.ResDao;
import cn.itcast.erp.auth.res.vo.ResModel;
import cn.itcast.erp.auth.res.vo.ResQueryModel;
import cn.itcast.erp.util.base.BaseImpl;
import cn.itcast.erp.util.base.BaseQueryModel;

public class ResImpl extends BaseImpl<ResModel> implements ResDao{

	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		ResQueryModel rqm = (ResQueryModel)qm;
		// TODO 未添加自定义查询条件查询方式设定
	}

	public List<ResModel> getAllByEmpUuid(Long uuid) {
		/*
		//String hql = "from ResModel where em.uuid = ?";
		//员工->角色->资源
		String hql1 = "from EmpModel em join em.roles";
		String hql2 = "from RoleModel rm join rm.reses";
		//查询要求员工关联角色，角色关联资源
		String hql3 = "from EmpModel em join em.roles role join role.reses";
		String hql4 = "from ResModel rm join rm.roles role join role.emps";
		*/
		/*
		内连接
		1	a
			b
		2	c
			d
			
			e
		*/
		/*
		员工		角色		资源
		1		1		a	
				2		b
						c
		2
		3
				4		d
						e
		*/
		/*
		Apple	pad
				iphone		1,2,3,4			6 6plus
		*/
		String hql = "select distinct res from EmpModel em join em.roles role join role.reses res where em.uuid = ?";
		return this.getHibernateTemplate().find(hql,uuid);
	}
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml","applicationContext-res.xml");
		//测试增删改方法，一定要拿Ebi,测试查询方法，直接拿Dao
		ResDao dao = (ResDao) ctx.getBean("resDao");
		System.out.println(dao.getAllByEmpUuid(9L));;
	}
	
}