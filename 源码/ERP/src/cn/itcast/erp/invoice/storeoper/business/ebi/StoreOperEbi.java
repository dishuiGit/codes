package cn.itcast.erp.invoice.storeoper.business.ebi;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.invoice.storeoper.vo.StoreOperModel;
import cn.itcast.erp.util.base.BaseEbi;

@Transactional
public interface StoreOperEbi extends BaseEbi<StoreOperModel>{

}