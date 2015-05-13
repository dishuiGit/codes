package cn.itcast.erp.invoice.store.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.invoice.store.vo.StoreModel;
import cn.itcast.erp.util.base.BaseEbi;

@Transactional
public interface StoreEbi extends BaseEbi<StoreModel>{
	/**
	 * 获取指定员工对应的所有管理的仓库
	 * @param uuid 员工uuid
	 * @return
	 */
	public List<StoreModel> getAllByEmp(Long uuid);

}