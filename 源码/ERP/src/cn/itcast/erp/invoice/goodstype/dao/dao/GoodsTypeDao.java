package cn.itcast.erp.invoice.goodstype.dao.dao;

import java.util.List;
import java.util.Set;

import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.util.base.BaseDao;

public interface GoodsTypeDao extends BaseDao<GoodsTypeModel>{

	public List<GoodsTypeModel> getAllBySmUuid(Long uuid);

	public List<GoodsTypeModel> getAllUnionBySmUuid(Long uuid);

	public List<GoodsTypeModel> getBySmUuidAndGmUuidNotInSet(Long supplierUuid,
			Set<Long> uuids);

}