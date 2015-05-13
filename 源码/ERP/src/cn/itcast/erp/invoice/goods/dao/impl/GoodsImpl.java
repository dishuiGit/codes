package cn.itcast.erp.invoice.goods.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.erp.invoice.goods.dao.dao.GoodsDao;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.goods.vo.GoodsQueryModel;
import cn.itcast.erp.util.base.BaseImpl;
import cn.itcast.erp.util.base.BaseQueryModel;

public class GoodsImpl extends BaseImpl<GoodsModel> implements GoodsDao{

	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		GoodsQueryModel gqm = (GoodsQueryModel)qm;
		if(gqm.getName()!=null && gqm.getName().trim().length()>0){
			dc.add(Restrictions.like("name", "%"+gqm.getName()+"%"));
		}
		if(gqm.getProducer()!=null && gqm.getProducer().trim().length()>0){
			dc.add(Restrictions.like("producer", "%"+gqm.getProducer()+"%"));
		}
		if(gqm.getUnit()!=null && gqm.getUnit().trim().length()>0){
			dc.add(Restrictions.eq("unit", gqm.getUnit()));
		}
		if(gqm.getInPrice()!=null && gqm.getInPrice()>=0){
			dc.add(Restrictions.ge("inPrice", gqm.getInPrice()));
		}
		if(gqm.getInPrice2()!=null && gqm.getInPrice2()>=0){
			dc.add(Restrictions.le("inPrice", gqm.getInPrice2()));
		}
		if(gqm.getGtm()!=null && gqm.getGtm().getSm()!=null && gqm.getGtm().getSm().getUuid()!=null && gqm.getGtm().getSm().getUuid()!=-1){
			/*
			//为了告诉h3使用的属性是关联属性，必须进行关联关系的声明
			dc.createAlias("gtm", "gt");
			dc.createAlias("gt.sm", "s");
			dc.add(Restrictions.eq("s.uuid", gqm.getGtm().getSm().getUuid()));
			*/
			
			dc.createAlias("gtm", "gt");
			dc.add(Restrictions.eq("gt.sm", gqm.getGtm().getSm()));
			
			/*
			dc.createAlias("gtm", "gt");
			dc.add(Restrictions.eq("gt.sm.uuid", gqm.getGtm().getSm().getUuid()));
			*/
		}
		
	}

	public List<GoodsModel> getAllByGtmUuid(Long uuid) {
		String hql = " from GoodsModel where gtm.uuid = ?";
		return this.getHibernateTemplate().find(hql,uuid);
	}

	public List<GoodsModel> getByGtmUuidAndUuidNotInSet(Long uuid,Set<Long> uuids) {
		//String hql = " from GoodsModel where gtm.uuid = ? and uuid not in (1,2,4) " ;
		//return this.getHibernateTemplate().find(hql,uuid);
		//还原成原始HibernateAPI查询，不使用HibernateTemplate
		//used不解析，直接传递到数据层
		//String hql = " from GoodsModel where gtm.uuid = :uuid and uuid not in ("+used+")" ;
		
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String hql = " from GoodsModel where gtm.uuid = :uuid and uuid not in :uuids " ;
		Query q = s.createQuery(hql);
		q.setLong("uuid", uuid);
		q.setParameterList("uuids", uuids);
		return q.list();
	}
	/*
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml","applicationContext-goods.xml");
		GoodsDao dao = (GoodsDao) ctx.getBean("goodsDao");
		Set<Long> uuids = new HashSet<Long>();
		uuids.add(1L);
		uuids.add(2L);
		uuids.add(4L);
		System.out.println(dao.getByGtmUuidAndUuidNotInSet(2L, uuids));;
	}
	 */
	public void updateGoodsUseNum() {
		/*
		UPDATE
			tbl_goods g
		SET
			useNum = 
		(
		SELECT
			COUNT(*)
		FROM
			tbl_orderdetail
		WHERE
			goodsUuid = g.uuid
		)
		*/
		String hql = "update GoodsModel g set g.useNum = ( select count(od.uuid) from OrderDetailModel od where od.gm.uuid = g.uuid )";  
		this.getHibernateTemplate().bulkUpdate(hql);
	}

	public List<Object[]> getWarnInfo() {
		/*
		SELECT
			g.name , 
			g.maxNum<SUM(num),
			g.minNum>SUM(num)
		FROM
			tbl_storeDetail od,
			tbl_goods g
		WHERE 
			g.uuid = od.goodsUuid
		GROUP BY
			g.uuid
		*/
		//String hql = " select g.name,g.maxNum < sum(sdm.num) ,g.minNum > sum(sdm.num) from StoreDetailModel sdm join sdm.gm g group by sdm.gm.uuid";
		//return this.getHibernateTemplate().find(hql);
		//执行SQL语句
		String sql = "SELECT g.name , g.maxNum<SUM(num), g.minNum>SUM(num) FROM tbl_storeDetail od,	tbl_goods g WHERE g.uuid = od.goodsUuid GROUP BY g.uuid";
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		SQLQuery q = s.createSQLQuery(sql);
		return q.list();
	}
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml","applicationContext-goods.xml");
		GoodsDao dao = (GoodsDao) ctx.getBean("goodsDao");
		System.out.println(dao.getWarnInfo());
	}
}





