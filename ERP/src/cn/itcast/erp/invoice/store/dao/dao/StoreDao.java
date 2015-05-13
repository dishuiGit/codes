package cn.itcast.erp.invoice.store.dao.dao;

import java.util.List;

import cn.itcast.erp.auth.base.BaseDao;
import cn.itcast.erp.invoice.store.vo.StoreModel;

public interface StoreDao extends BaseDao<StoreModel>{

	List<StoreModel> getByUuid(Long uuid);

}
