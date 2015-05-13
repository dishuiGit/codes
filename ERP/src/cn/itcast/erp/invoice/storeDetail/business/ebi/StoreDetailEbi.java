package cn.itcast.erp.invoice.storeDetail.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.base.BaseEbi;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.storeDetail.vo.StoreDetailModel;
@Transactional
public interface StoreDetailEbi extends BaseEbi<StoreDetailModel>{
	/**
	 * 查找指定订单在仓库的存货量
	 * @param om
	 */
	public List<StoreDetailModel> getDetailByOrder(OrderModel om);
	/**
	 * 根据仓库和商品,查找仓库明细
	 * @param storeUuid
	 * @param goodsUuid
	 * @return
	 */
	public StoreDetailModel getBySmAndGm(Long storeUuid, Long goodsUuid);


}
