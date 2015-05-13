package cn.itcast.erp.invoice.goods.business.ebi;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.util.base.BaseEbi;

@Transactional
public interface GoodsEbi extends BaseEbi<GoodsModel>{
	/**
	 * 获取指定商品类别对应的所有商品信息
	 * @param uuid
	 * @return
	 */
	public List<GoodsModel> getAllByGtm(Long uuid);
	/**
	 * 获取指定商品类别对应的所有商品数据，并将已使用数据删除
	 * @param uuid 类别uuid
	 * @param uuids 已使用商品uuid集合
	 * @return
	 */
	public List<GoodsModel> getByGtmAndDelUsed(Long uuid, Set<Long> uuids);
	
	public void updateGoodsUseNum();
	public List<Object[]> getWarnInfo();

}