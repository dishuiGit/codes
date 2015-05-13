package cn.itcast.erp.invoice.goodstype.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.base.BaseEbi;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
@Transactional
public interface GoodsTypeEbi extends BaseEbi<GoodsTypeModel>{
	/**
	 * 根据指定 供应商,获取 商品类别
	 * @param supplierUuid
	 * @return
	 */
	public List<GoodsTypeModel> getAllGTBySupplier(Long supplierUuid);
	/**
	 * 根据分类查找商品
	 * @param uuid 分类id
	 * @return 查询到商品结果集
	 */
	public List<GoodsModel> getAllGmByGtm(Long uuid);

}
