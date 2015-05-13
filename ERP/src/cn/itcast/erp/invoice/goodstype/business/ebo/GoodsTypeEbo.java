package cn.itcast.erp.invoice.goodstype.business.ebo;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.goodstype.business.ebi.GoodsTypeEbi;
import cn.itcast.erp.invoice.goodstype.dao.dao.GoodsTypeDao;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;

public class GoodsTypeEbo implements GoodsTypeEbi{

	//注入dao
	@Resource(name="gtDao")
	private GoodsTypeDao gtDao;
	@Resource(name="supplierEbi")
	private SupplierEbi supplierEbi;
	public void save(GoodsTypeModel gtm) {
		//查询供应商 supplier
		SupplierModel sm = supplierEbi.getByUuid(gtm.getSm().getUuid());
		gtm.setSm(sm);
		gtDao.save(gtm);
	}
	
	public void delete(GoodsTypeModel gtm) {
		gtDao.delete(gtm);
	}
	
	public void update(GoodsTypeModel gtm) {
		gtDao.update(gtm);
	}
	
	public List<GoodsTypeModel> getAll() {
		return gtDao.getAll();
	}
	
	public GoodsTypeModel getByUuid(Long uuid) {
	
		return gtDao.get(uuid);
	}

	
	public List<GoodsTypeModel> getAll(BaseQueryModel bqm) {
	
		return gtDao.getAll(bqm);
	}

	
	public Integer getCount(BaseQueryModel bqm) {
	
		return gtDao.getCount(bqm);
	}

	public List<GoodsTypeModel> getAll(BaseQueryModel bqm, Integer pageNum,
			Integer pageCount) {
		return gtDao.getAll(bqm, pageNum, pageCount);
	}

	public List<GoodsTypeModel> getAllGTBySupplier(Long supplierUuid) {
		return gtDao.getAllGTBySupplierUuid(supplierUuid);
	}

	public List<GoodsModel> getAllGmByGtm(Long uuid) {
		return gtDao.getAllGmByUuid(uuid);
	}

}
