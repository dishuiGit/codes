package cn.itcast.erp.invoice.goods.business.ebi;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.base.BaseEbi;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
@Transactional
public interface GoodsEbi extends BaseEbi<GoodsModel>{
	/**
	 * 根据供应商id 已用商品列表集合 查找没有被使用的商品
	 * @param supplierUuid 供应商id
	 * @param goodsUuids 已用商品id集合
	 * @return 没有被使用的商品集合
	 */
	List<GoodsModel> getByGtmAndSm(Long supplierUuid, Set<Long> goodsUuids);

	List<GoodsModel> getByGtmAndSm(Long uuid);

}
