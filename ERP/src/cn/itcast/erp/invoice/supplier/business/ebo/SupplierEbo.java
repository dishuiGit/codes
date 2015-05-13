package cn.itcast.erp.invoice.supplier.business.ebo;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.erp.invoice.supplier.dao.dao.SupplierDao;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;

public class SupplierEbo implements SupplierEbi{

	//注入dao
	@Resource(name="supplierDao")
	private SupplierDao supplierDao;
	public void save(SupplierModel sm) {
		supplierDao.save(sm);
	}

	
	public void delete(SupplierModel sm) {
		supplierDao.delete(sm);
	}

	
	public void update(SupplierModel sm) {
		supplierDao.update(sm);
	}
	
	public List<SupplierModel> getAll() {
		return supplierDao.getAll();
	}

	public SupplierModel getByUuid(Long uuid) {
		return supplierDao.get(uuid);
	}

	
	public List<SupplierModel> getAll(BaseQueryModel bqm) {
		return supplierDao.getAll(bqm);
	}

	public Integer getCount(BaseQueryModel bqm) {
		return supplierDao.getCount(bqm);
	}
	
	public List<SupplierModel> getAll(BaseQueryModel bqm, Integer pageNum,
			Integer pageCount) {
		return supplierDao.getAll(bqm, pageNum, pageCount);
	}

	public List<GoodsTypeModel> getAllGTBySM(Long uuid) {
		
		return supplierDao.getAllGTByUuid(uuid);
	}


	public List<GoodsTypeModel> newLineBySmAndGm(Long supplierUuid,
			Set<Long> goodsUuids) {
		
		return supplierDao.getBySmUuidAndGmUuid(supplierUuid,goodsUuids);
	}

}
