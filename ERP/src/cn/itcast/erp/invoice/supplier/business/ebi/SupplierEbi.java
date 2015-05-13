package cn.itcast.erp.invoice.supplier.business.ebi;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.base.BaseEbi;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;
@Transactional
public interface SupplierEbi extends BaseEbi<SupplierModel>{
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	List<GoodsTypeModel> getAllGTBySM(Long uuid);
	/**
	 * 根据供应商id和页面已经显示的商品id,查找没有被使用的商品id
	 * @param supplierUuid 供应商 id
	 * @param goodsUuids 商品 id
	 * @return 
	 */
	List<GoodsTypeModel> newLineBySmAndGm(Long supplierUuid, Set<Long> goodsUuids);

}
