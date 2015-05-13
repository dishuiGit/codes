package cn.itcast.erp.invoice.bill.dao.dao;

import java.util.List;

import cn.itcast.erp.auth.base.BaseDao;
import cn.itcast.erp.invoice.bill.vo.BillModel;
import cn.itcast.erp.invoice.orderDetail.vo.OrderDetialModel;

public interface BillDao extends BaseDao<BillModel>{

	List<Object[]> getData();

	List<OrderDetialModel> getByGmUuid(Long goodsUuid);

}
