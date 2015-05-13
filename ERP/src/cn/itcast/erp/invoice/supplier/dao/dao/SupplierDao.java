package cn.itcast.erp.invoice.supplier.dao.dao;

import java.util.List;
import java.util.Set;

import cn.itcast.erp.auth.base.BaseDao;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;

public interface SupplierDao extends BaseDao<SupplierModel>{

	List<GoodsTypeModel> getAllGTByUuid(Long uuid);

	List<GoodsTypeModel> getBySmUuidAndGmUuid(Long supplierUuid, Set<Long> goodsUuids);

}
