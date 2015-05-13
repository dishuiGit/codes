package cn.itcast.erp.invoice.supplier.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.invoice.supplier.dao.dao.SupplierDao;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;
import cn.itcast.erp.invoice.supplier.vo.SupplierQueryModel;
import cn.itcast.erp.util.base.BaseImpl;
import cn.itcast.erp.util.base.BaseQueryModel;

public class SupplierImpl extends BaseImpl<SupplierModel> implements SupplierDao{

	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		SupplierQueryModel sqm = (SupplierQueryModel)qm;
		// TODO 未添加自定义查询条件查询方式设定
	}

	public List<SupplierModel> getAllUnion() {
		//供应商关联类别  供应商->类别 		类别->供应商
		String hql = "select distinct s from GoodsTypeModel gtm join gtm.sm s";
		return this.getHibernateTemplate().find(hql);
	}

	public List<SupplierModel> getAllUnionTwo() {
		//商品->类别->供应商
		String hql = "select distinct s from GoodsModel gm join gm.gtm gt join gt.sm s";
		return this.getHibernateTemplate().find(hql);
	}
}