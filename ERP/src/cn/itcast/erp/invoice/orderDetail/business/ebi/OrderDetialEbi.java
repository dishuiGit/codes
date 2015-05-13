package cn.itcast.erp.invoice.orderDetail.business.ebi;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.base.BaseEbi;
import cn.itcast.erp.invoice.orderDetail.vo.OrderDetialModel;
@Transactional
public interface OrderDetialEbi extends BaseEbi<OrderDetialModel>{

}
