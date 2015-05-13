package cn.itcast.erp.invoice.store.business.ebo;

import java.util.List;

import cn.itcast.erp.invoice.store.business.ebi.StoreEbi;
import cn.itcast.erp.invoice.store.dao.dao.StoreDao;
import cn.itcast.erp.invoice.store.vo.StoreModel;
import cn.itcast.erp.util.base.BaseQueryModel;

public class StoreEbo implements StoreEbi{
	private StoreDao storeDao;
	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public void save(StoreModel sm) {
		storeDao.save(sm);
	}

	public List<StoreModel> getAll() {
		return storeDao.getAll();
	}

	public StoreModel get(Long uuid) {
		return storeDao.get(uuid);
	}

	public void update(StoreModel sm) {
		storeDao.update(sm);
	}

	public void delete(StoreModel sm) {
		storeDao.delete(sm);
	}

	public List<StoreModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return storeDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return storeDao.getCount(qm);
	}

	public List<StoreModel> getAllByEmp(Long uuid) {
		return storeDao.getAllByEmpUuid(uuid);
	}

}