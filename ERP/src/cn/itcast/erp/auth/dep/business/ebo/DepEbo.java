package cn.itcast.erp.auth.dep.business.ebo;

import java.util.List;

import cn.itcast.erp.auth.base.BaseDao;
import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.auth.dep.business.ebi.DepEbi;
import cn.itcast.erp.auth.dep.dao.dao.DepDao;
import cn.itcast.erp.auth.dep.vo.DepModel;
public class DepEbo implements DepEbi {
	//注入dao
	private DepDao depDao;
	public void setDepDao(DepDao depDao) {
		this.depDao = depDao;
	}
	public void save(DepModel dm) {
		depDao.save(dm);
	}
	public List<DepModel> getAll() {
		return depDao.getAll();
	}
	
	public void update(DepModel dm) {
		depDao.update(dm);
	}
	public DepModel getByUuid(Long uuid) {
		return (DepModel) depDao.get(uuid);
	}
	public List<DepModel> getAll(BaseQueryModel bqm) {
		return depDao.getAll(bqm);
	}
	public Integer getCount(BaseQueryModel bqm) {
		return depDao.getCount(bqm);
	}
	public List<DepModel> getAll(BaseQueryModel bqm, Integer pageNum,
			Integer pageCount) {
		return depDao.getAll(bqm,pageNum,pageCount);
	}
	public void delete(DepModel dm) {
		depDao.delete(dm);
	}
}
