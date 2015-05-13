package cn.itcast.erp.invoice.goodstype.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.erp.auth.base.BaseDaoImpl;
import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.goodstype.dao.dao.GoodsTypeDao;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;

public class GoodsTypeDaoImpl extends BaseDaoImpl<GoodsTypeModel> implements GoodsTypeDao{

	public DetachedCriteria doQBC(BaseQueryModel bqm,
			Class<GoodsTypeModel> entryclass) {
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsTypeModel.class);
		//添加模糊查询条件
		return dc;
	}

	public List<GoodsTypeModel> getAllGTBySupplierUuid(Long supplierUuid) {
		String hql = "from GoodsTypeModel where supplier_uuid = ?";
		return this.getHibernateTemplate().find(hql,supplierUuid);
	}

	public List<GoodsModel> getAllGmByUuid(Long uuid) {
		String hql = "select gmss from GoodsTypeModel gtm join gtm.gms gmss where gtm.uuid = ?";
		return this.getHibernateTemplate().find(hql,uuid);
	}

}
