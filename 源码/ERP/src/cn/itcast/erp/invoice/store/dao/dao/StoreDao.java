package cn.itcast.erp.invoice.store.dao.dao;

import java.util.List;

import cn.itcast.erp.invoice.store.vo.StoreModel;
import cn.itcast.erp.util.base.BaseDao;

public interface StoreDao extends BaseDao<StoreModel>{

	public List<StoreModel> getAllByEmpUuid(Long uuid);

}