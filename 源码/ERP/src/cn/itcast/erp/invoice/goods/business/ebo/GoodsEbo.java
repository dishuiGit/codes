package cn.itcast.erp.invoice.goods.business.ebo;

import java.util.List;
import java.util.Set;

import cn.itcast.erp.invoice.goods.business.ebi.GoodsEbi;
import cn.itcast.erp.invoice.goods.dao.dao.GoodsDao;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.util.base.BaseQueryModel;

public class GoodsEbo implements GoodsEbi{
	private GoodsDao goodsDao;
	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public void save(GoodsModel gm) {
		gm.setUseNum(0);
		gm.setMinNum(0);
		gm.setMaxNum(100);
		goodsDao.save(gm);
	}

	public List<GoodsModel> getAll() {
		return goodsDao.getAll();
	}

	public GoodsModel get(Long uuid) {
		return goodsDao.get(uuid);
	}

	public void update(GoodsModel gm) {
		//快照更新：未修复
		goodsDao.update(gm);
	}

	public void delete(GoodsModel gm) {
		goodsDao.delete(gm);
	}

	public List<GoodsModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return goodsDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return goodsDao.getCount(qm);
	}

	public List<GoodsModel> getAllByGtm(Long uuid) {
		return goodsDao.getAllByGtmUuid(uuid);
	}

	public List<GoodsModel> getByGtmAndDelUsed(Long uuid, Set<Long> uuids) {
		return goodsDao.getByGtmUuidAndUuidNotInSet(uuid,uuids);
	}

	public void updateGoodsUseNum() {
		goodsDao.updateGoodsUseNum();
	}

	public List<Object[]> getWarnInfo() {
		return goodsDao.getWarnInfo();
	}

}