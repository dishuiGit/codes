package cn.itcast.erp.invoice.goods.dao.dao;

import java.util.List;
import java.util.Set;

import cn.itcast.erp.auth.base.BaseDao;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;

public interface GoodsDao extends BaseDao<GoodsModel>{

	List<GoodsModel> getByGtmIdAndSmId(Long supplierUuid, Set<Long> goodsUuids);

	List<GoodsModel> getByGtmId(Long supplierUuid);

}
