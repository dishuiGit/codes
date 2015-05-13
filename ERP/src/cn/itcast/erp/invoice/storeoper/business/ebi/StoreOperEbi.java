package cn.itcast.erp.invoice.storeoper.business.ebi;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.base.BaseEbi;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.invoice.orderDetail.vo.OrderDetialModel;
import cn.itcast.erp.invoice.storeoper.vo.StoreOperModel;

@Transactional
public interface StoreOperEbi extends BaseEbi<StoreOperModel>{

	public OrderDetialModel inStore(EmpModel login, Long orderDetailUuid, Long storeUuid,Long goodsUuid, Integer inStoreNum);

}
