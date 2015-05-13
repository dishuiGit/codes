package cn.itcast.erp.invoice.storedetail.business.ebo;

import java.util.List;

import cn.itcast.erp.invoice.storedetail.business.ebi.StoreDetailEbi;
import cn.itcast.erp.invoice.storedetail.dao.dao.StoreDetailDao;
import cn.itcast.erp.invoice.storedetail.vo.StoreDetailModel;
import cn.itcast.erp.util.base.BaseQueryModel;

public class StoreDetailEbo implements StoreDetailEbi{
	private StoreDetailDao storeDetailDao;
	public void setStoreDetailDao(StoreDetailDao storeDetailDao) {
		this.storeDetailDao = storeDetailDao;
	}

	public void save(StoreDetailModel sm) {
		storeDetailDao.save(sm);
	}

	public List<StoreDetailModel> getAll() {
		return storeDetailDao.getAll();
	}

	public StoreDetailModel get(Long uuid) {
		return storeDetailDao.get(uuid);
	}

	public void update(StoreDetailModel sm) {
		storeDetailDao.update(sm);
	}

	public void delete(StoreDetailModel sm) {
		storeDetailDao.delete(sm);
	}

	public List<StoreDetailModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return storeDetailDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return storeDetailDao.getCount(qm);
	}

}