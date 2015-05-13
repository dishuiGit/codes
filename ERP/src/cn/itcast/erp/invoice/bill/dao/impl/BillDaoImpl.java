package cn.itcast.erp.invoice.bill.dao.impl;

import java.util.List;

import org.hibernate.classic.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.erp.auth.base.BaseDaoImpl;
import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.invoice.bill.dao.dao.BillDao;
import cn.itcast.erp.invoice.bill.vo.BillModel;
import cn.itcast.erp.invoice.orderDetail.vo.OrderDetialModel;

public class BillDaoImpl extends BaseDaoImpl<BillModel> implements BillDao{

	public DetachedCriteria doQBC(BaseQueryModel bqm,
			Class<BillModel> entryclass) {
		DetachedCriteria dc = DetachedCriteria.forClass(BillModel.class);
		//添加查询条件
		return dc;
	}

	public List<Object[]> getData() {
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "select sum(too.num) total,tg.name,tg.uuid "+
					"from tbl_orderdetial too,"+
					"(select uuid,name "+
					"from "+
					"tbl_goods) tg "+
					"where too.goods_uuid= tg.uuid "+
					"group by too.goods_uuid;";
		return s.createSQLQuery(sql).list();
	}
	//测试
	public void fn(){
		Session s = this.getHibernateTemplate().getSessionFactory().openSession();
		String sql = "select * from tbl_goods tg where tg.uuid = 1";
		List<Object[]> tmp = s.createSQLQuery(sql).list();
		for(Object obj:tmp.get(0)){
			System.out.println(obj);
		}
	}
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml","applicationContext-bill.xml");
		BillDaoImpl billDaoImpl = (BillDaoImpl) ctx.getBean("billDao");
		List<Object[]> tmp = billDaoImpl.getData();
		for(Object[] objs:tmp){
			for(Object obj:objs){
				System.out.println(obj);
			}
		}
	}

	public List<OrderDetialModel> getByGmUuid(Long goodsUuid) {
		String hql = "from OrderDetialModel p_odm where p_odm.gm.uuid = ? order by p_odm.uuid";
		return this.getHibernateTemplate().find(hql, goodsUuid);
	}

}
