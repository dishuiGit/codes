package cn.itcast.erp.invoice.goods.business.ebo;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.invoice.goods.business.ebi.GoodsEbi;
import cn.itcast.erp.invoice.goods.dao.dao.GoodsDao;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.goodstype.business.ebi.GoodsTypeEbi;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;

public class GoodsEbo implements GoodsEbi{

	//注入dao
	@Resource(name="goodsDao")
	private GoodsDao goodsDao;
	@Resource(name="supplierEbi")
	private SupplierEbi supplierEbi;
	@Resource(name="gtEbi")
	private GoodsTypeEbi goodsTypeEbi;
	public void save(GoodsModel gm) {
		//查询供应商数据
		SupplierModel sm = supplierEbi.getByUuid(gm.getGtm().getSm().getUuid());
		//查询商品类别数据
		GoodsTypeModel gtm = goodsTypeEbi.getByUuid(gm.getGtm().getUuid());
		gtm.setSm(sm);
		gm.setGtm(gtm);
		goodsDao.save(gm);
	}
	
	public void delete(GoodsModel gm) {
		goodsDao.delete(gm);
	}
	
	public void update(GoodsModel gm) {
		goodsDao.update(gm);
	}

	public List<GoodsModel> getAll() {
		return goodsDao.getAll();
	}

	public GoodsModel getByUuid(Long uuid) {
		return goodsDao.get(uuid);
	}
	
	public List<GoodsModel> getAll(BaseQueryModel bqm) {
		return goodsDao.getAll(bqm);
	}

	public Integer getCount(BaseQueryModel bqm) {
		return goodsDao.getCount(bqm);
	}
	public List<GoodsModel> getAll(BaseQueryModel bqm, Integer pageNum,
			Integer pageCount) {
		return goodsDao.getAll(bqm, pageNum, pageCount);
	}

	public List<GoodsModel> getByGtmAndSm(Long supplierUuid,
			Set<Long> goodsUuids) {
		
		return goodsDao.getByGtmIdAndSmId(supplierUuid,goodsUuids);
	}

	public List<GoodsModel> getByGtmAndSm(Long supplierUuid) {
		return goodsDao.getByGtmId(supplierUuid);
	}
}
