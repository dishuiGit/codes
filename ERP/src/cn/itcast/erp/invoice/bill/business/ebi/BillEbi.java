package cn.itcast.erp.invoice.bill.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.invoice.orderDetail.vo.OrderDetialModel;

@Transactional
public interface BillEbi{

	List<Object[]> getBillData();
	/**
	 * 根据商品id查找订单明细
	 * @param goodsUuid
	 * @return
	 */
	List<OrderDetialModel> getAllDetailByGm(Long goodsUuid);

}
