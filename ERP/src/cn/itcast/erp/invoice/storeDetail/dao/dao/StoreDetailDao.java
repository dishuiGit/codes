package cn.itcast.erp.invoice.storeDetail.dao.dao;

import java.util.List;

import cn.itcast.erp.auth.base.BaseDao;
import cn.itcast.erp.invoice.storeDetail.vo.StoreDetailModel;

public interface StoreDetailDao extends BaseDao<StoreDetailModel>{

	List<StoreDetailModel> getByUuid(Long uuid);

	StoreDetailModel getByUuid(Long storeUuid, Long goodsUuid);

}
