package cn.itcast.erp.invoice.store.business.ebo;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.invoice.store.business.ebi.StoreEbi;
import cn.itcast.erp.invoice.store.dao.dao.StoreDao;
import cn.itcast.erp.invoice.store.vo.StoreModel;
import cn.itcast.erp.invoice.storeDetail.vo.StoreDetailModel;

public class StoreEbo implements StoreEbi{

	//注入 dao
	@Resource(name="storeDao")
	private StoreDao storeDao;
	public void save(StoreModel sm) {
		storeDao.save(sm);
	}

	
	public void delete(StoreModel sm) {
		storeDao.delete(sm);
	}

	
	public void update(StoreModel sm) {
		storeDao.update(sm);
	}

	public List<StoreModel> getAll() {
		return storeDao.getAll();
	}

	
	public StoreModel getByUuid(Long uuid) {
		return storeDao.get(uuid);
	}
	public List<StoreModel> getAll(BaseQueryModel bqm) {
		return storeDao.getAll(bqm);
	}

	public Integer getCount(BaseQueryModel bqm) {
	
		return storeDao.getCount(bqm);
	}

	
	public List<StoreModel> getAll(BaseQueryModel bqm, Integer pageNum,
			Integer pageCount) {
	
		return storeDao.getAll(bqm, pageNum, pageCount);
	}


	public List<StoreModel> getAllByEmp(EmpModel login) {
		
		return storeDao.getByUuid(login.getUuid());
	}


}
