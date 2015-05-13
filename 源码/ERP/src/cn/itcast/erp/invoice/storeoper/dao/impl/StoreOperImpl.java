package cn.itcast.erp.invoice.storeoper.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.invoice.storeoper.dao.dao.StoreOperDao;
import cn.itcast.erp.invoice.storeoper.vo.StoreOperModel;
import cn.itcast.erp.invoice.storeoper.vo.StoreOperQueryModel;
import cn.itcast.erp.util.base.BaseImpl;
import cn.itcast.erp.util.base.BaseQueryModel;

public class StoreOperImpl extends BaseImpl<StoreOperModel> implements StoreOperDao{

	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		StoreOperQueryModel sqm = (StoreOperQueryModel)qm;
		// TODO 未添加自定义查询条件查询方式设定
	}
}