package cn.itcast.erp.invoice.storeDetail.business.ebo;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.store.business.ebi.StoreEbi;
import cn.itcast.erp.invoice.storeDetail.business.ebi.StoreDetailEbi;
import cn.itcast.erp.invoice.storeDetail.dao.dao.StoreDetailDao;
import cn.itcast.erp.invoice.storeDetail.vo.StoreDetailModel;

public class StoreDetailEbo implements StoreDetailEbi{
	
	//注入dao
	@Resource(name="storeDetailDao")
	private StoreDetailDao storeDetailDao;
	public void save(StoreDetailModel sdm) {
		storeDetailDao.save(sdm);
	}

	public void delete(StoreDetailModel sdm) {
		storeDetailDao.delete(sdm);
	}
	
	public void update(StoreDetailModel sdm) {
		storeDetailDao.update(sdm);
	}
	
	public List<StoreDetailModel> getAll() {
		return storeDetailDao.getAll();
	}

	public StoreDetailModel getByUuid(Long uuid) {
		return storeDetailDao.get(uuid);
	}

	public List<StoreDetailModel> getAll(BaseQueryModel bqm) {
		return storeDetailDao.getAll(bqm);
	}

	public Integer getCount(BaseQueryModel bqm) {
		return storeDetailDao.getCount(bqm);
	}
	public List<StoreDetailModel> getAll(BaseQueryModel bqm, Integer pageNum,
			Integer pageCount) {
		return storeDetailDao.getAll(bqm, pageNum, pageCount);
	}
	//注入 storeEbi
	@Resource(name="storeEbi")
	private StoreEbi storeEbi;
//	public List<StoreDetailModel> getAllByEmp(EmpModel login) {
//		return storeEbi.getAllByEmp(login.getUuid());
//	}
	public List<StoreDetailModel> getDetailByOrder(OrderModel om) {
		
		return storeDetailDao.getByUuid(om.getUuid());
	}

	public StoreDetailModel getBySmAndGm(Long storeUuid, Long goodsUuid) {
		return storeDetailDao.getByUuid(storeUuid,goodsUuid);
	}
}
