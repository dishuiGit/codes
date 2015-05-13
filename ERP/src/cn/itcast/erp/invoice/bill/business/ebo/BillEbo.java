package cn.itcast.erp.invoice.bill.business.ebo;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.invoice.bill.business.ebi.BillEbi;
import cn.itcast.erp.invoice.bill.dao.dao.BillDao;
import cn.itcast.erp.invoice.orderDetail.vo.OrderDetialModel;

public class BillEbo implements BillEbi{

	//注入dao
	@Resource(name="billDao")
	private BillDao billDao;

	public List<Object[]> getBillData() {
		
		return billDao.getData();
	}
	
	public List<OrderDetialModel> getAllDetailByGm(Long goodsUuid) {
		return billDao.getByGmUuid(goodsUuid);
	}
}
