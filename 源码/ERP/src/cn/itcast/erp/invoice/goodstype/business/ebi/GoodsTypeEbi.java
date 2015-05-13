package cn.itcast.erp.invoice.goodstype.business.ebi;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.util.base.BaseEbi;

@Transactional
public interface GoodsTypeEbi extends BaseEbi<GoodsTypeModel>{
	/**
	 * 获取指定供应商商品类别信息
	 * @param uuid
	 * @return
	 */
	public List<GoodsTypeModel> getAllBySm(Long uuid);
	/**
	 * 获取指定供应商具有商品信息的商品类别信息
	 * @param uuid
	 * @return
	 */
	public List<GoodsTypeModel> getAllUnionBySm(Long uuid);
	/**
	 * 获取指定供应商的商品类别信息，并排除掉对应类别信息中所有商品已经被使用完毕的
	 * @param supplierUuid 供应商uuid
	 * @param uuids 已使用商品uuid集合
	 * @return
	 */
	public List<GoodsTypeModel> getAllUnionBySmAndDelUsed(Long supplierUuid,
			Set<Long> uuids);

}