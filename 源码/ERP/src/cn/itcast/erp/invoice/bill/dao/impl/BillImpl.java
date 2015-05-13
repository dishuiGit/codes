package cn.itcast.erp.invoice.bill.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.erp.invoice.bill.dao.dao.BillDao;
import cn.itcast.erp.invoice.bill.vo.BillQueryModel;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailModel;

public class BillImpl extends HibernateDaoSupport implements BillDao{

	public List<Object[]> getBuyBill(BillQueryModel bqm) {
		/*
		SELECT
			g.uuid,
			g.name,
			SUM(od.num)
		FROM
			tbl_orderDetail od,
			tbl_goods g
		WHERE
			g.uuid = od.goodsUuid
		GROUP BY
			g.uuid
		*/
		//QBC
		DetachedCriteria dc = DetachedCriteria.forClass(OrderDetailModel.class);
		//目前：select * from OrderDetailModel
		//目标：select sum(num) from OrderDetailModel
		//dc.setProjection(Projections.sum("num"));
		//dc.setProjection(Projections.groupProperty("gm"));
		
		//select gm,sum(num) from ...  group by gm
		//如果要设置多投影
		ProjectionList plist = Projections.projectionList();
		plist.add(Projections.groupProperty("gm"));
		plist.add(Projections.sum("num"));
		dc.setProjection(plist);
		
		dc.createAlias("om", "o");
		if(bqm.getType()!=null && bqm.getType()!= -1){
			dc.add(Restrictions.eq("o.type", bqm.getType()));
		}
		if(bqm.getSupplierUuid()!=null && bqm.getSupplierUuid()!= -1){
			dc.createAlias("o.sm", "s");
			dc.add(Restrictions.eq("s.uuid", bqm.getSupplierUuid()));
		}
		
		return this.getHibernateTemplate().findByCriteria(dc);
	}


	public List<OrderDetailModel> getBillDetail(BillQueryModel bqm) {
		//QBC
		DetachedCriteria dc = DetachedCriteria.forClass(OrderDetailModel.class);
		
		dc.createAlias("om", "o");
		if(bqm.getType()!=null && bqm.getType()!= -1){
			dc.add(Restrictions.eq("o.type", bqm.getType()));
		}
		if(bqm.getSupplierUuid()!=null && bqm.getSupplierUuid()!= -1){
			dc.createAlias("o.sm", "s");
			dc.add(Restrictions.eq("s.uuid", bqm.getSupplierUuid()));
		}
		//goodsUuid
		if(bqm.getGoodsUuid()!=null && bqm.getGoodsUuid() != -1){
			dc.add(Restrictions.eq("gm.uuid", bqm.getGoodsUuid()));
		}
		
		return this.getHibernateTemplate().findByCriteria(dc);
	}
	

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml","applicationContext-bill.xml");
		BillDao dao = (BillDao) ctx.getBean("billDao");
		List<Object[]> temp = dao.getBuyBill(new BillQueryModel());
		for(Object[] objs: temp){
			System.out.println(objs[0]);
			System.out.println(objs[1]);
		}
	}
}





