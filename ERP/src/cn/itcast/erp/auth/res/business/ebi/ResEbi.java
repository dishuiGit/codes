package cn.itcast.erp.auth.res.business.ebi;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.base.BaseEbi;
import cn.itcast.erp.auth.res.vo.ResModel;

@Transactional
public interface ResEbi extends BaseEbi<ResModel>{

}
