package cn.itcast.erp.invoice.orderDetail.business.ebo;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.invoice.orderDetail.business.ebi.OrderDetialEbi;
import cn.itcast.erp.invoice.orderDetail.dao.dao.OrderDetialDao;
import cn.itcast.erp.invoice.orderDetail.vo.OrderDetialModel;

public class OrderDetialEbo implements OrderDetialEbi{

	@Resource(name="odDao")
	private OrderDetialDao odDao;
	public void save(OrderDetialModel odm) {
		odDao.save(odm);
	}

	
	public void delete(OrderDetialModel odm) {
		odDao.delete(odm);
	}
	public void update(OrderDetialModel odm) {
		odDao.update(odm);
	}
	public List<OrderDetialModel> getAll() {
		return odDao.getAll();
	}

	
	public OrderDetialModel getByUuid(Long uuid) {
		return odDao.get(uuid);
	}

	
	public List<OrderDetialModel> getAll(BaseQueryModel bqm) {
		return odDao.getAll(bqm);
	}

	
	public Integer getCount(BaseQueryModel bqm) {
		return odDao.getCount(bqm);
	}

	
	public List<OrderDetialModel> getAll(BaseQueryModel bqm, Integer pageNum,
			Integer pageCount) {
		return odDao.getAll(bqm, pageNum, pageCount);
	}

}
