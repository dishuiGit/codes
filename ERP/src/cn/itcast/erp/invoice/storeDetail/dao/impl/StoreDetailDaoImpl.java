package cn.itcast.erp.invoice.storeDetail.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.erp.auth.base.BaseDaoImpl;
import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.invoice.storeDetail.dao.dao.StoreDetailDao;
import cn.itcast.erp.invoice.storeDetail.vo.StoreDetailModel;

public class StoreDetailDaoImpl extends BaseDaoImpl<StoreDetailModel> implements StoreDetailDao{

	public DetachedCriteria doQBC(BaseQueryModel bqm,
			Class<StoreDetailModel> entryclass) {
		DetachedCriteria dc = DetachedCriteria.forClass(StoreDetailModel.class);
		//添加查询条件
		return dc;
	}

	public List<StoreDetailModel> getByUuid(Long uuid) {
		String hql = "from StoreDetailModel where om.uuid = ?";
		return this.getHibernateTemplate().find(hql, uuid);
	}

	public StoreDetailModel getByUuid(Long storeUuid, Long goodsUuid) {
		String hql = "from StoreDetailModel where sm.uuid = ? and gm.uuid = ?";
		List<StoreDetailModel> sdm_list = this.getHibernateTemplate().find(hql,storeUuid,goodsUuid);
		return sdm_list.size()>0?sdm_list.get(0):null;
	}

}
