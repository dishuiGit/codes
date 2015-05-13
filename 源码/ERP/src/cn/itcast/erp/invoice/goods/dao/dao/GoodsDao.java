package cn.itcast.erp.invoice.goods.dao.dao;

import java.util.List;
import java.util.Set;

import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.util.base.BaseDao;

public interface GoodsDao extends BaseDao<GoodsModel>{

	public List<GoodsModel> getAllByGtmUuid(Long uuid);

	public List<GoodsModel> getByGtmUuidAndUuidNotInSet(Long uuid,
			Set<Long> uuids);

	public void updateGoodsUseNum();

	public List<Object[]> getWarnInfo();

}