package cn.itcast.erp.invoice.store.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.base.BaseEbi;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.invoice.store.vo.StoreModel;
import cn.itcast.erp.invoice.storeDetail.vo.StoreDetailModel;
@Transactional
public interface StoreEbi extends BaseEbi<StoreModel>{
	/**
	 * 根据登陆用户 获取仓库
	 * @param login 仓库管理员
	 * @return
	 */
	List<StoreModel> getAllByEmp(EmpModel login);


}
