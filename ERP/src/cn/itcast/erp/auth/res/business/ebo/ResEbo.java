package cn.itcast.erp.auth.res.business.ebo;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.auth.res.business.ebi.ResEbi;
import cn.itcast.erp.auth.res.dao.dao.ResDao;
import cn.itcast.erp.auth.res.vo.ResModel;

public class ResEbo implements ResEbi{
	//注入resDao
	@Resource(name="resDao")
	private ResDao resDao;
	public void save(ResModel rm) {
		resDao.save(rm);
	}

	
	public void delete(ResModel rm) {
		resDao.delete(rm);
	}

	
	public void update(ResModel rm) {
		resDao.update(rm);
	}

	
	public List<ResModel> getAll() {
	
		return resDao.getAll();
	}

	
	public ResModel getByUuid(Long uuid) {
	
		return resDao.get(uuid);
	}

	
	public List<ResModel> getAll(BaseQueryModel bqm) {
	
		return resDao.getAll(bqm);
	}

	
	public Integer getCount(BaseQueryModel bqm) {
	
		return resDao.getCount(bqm);
	}

	
	public List<ResModel> getAll(BaseQueryModel bqm, Integer pageNum,
			Integer pageCount) {
	
		return resDao.getAll(bqm, pageNum, pageCount);
	}

}
