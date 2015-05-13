package cn.itcast.erp.invoice.goodstype.dao.dao;

import java.util.List;

import cn.itcast.erp.auth.base.BaseDao;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;

public interface GoodsTypeDao extends BaseDao<GoodsTypeModel>{

	List<GoodsTypeModel> getAllGTBySupplierUuid(Long supplierUuid);

	List<GoodsModel> getAllGmByUuid(Long uuid);

}
