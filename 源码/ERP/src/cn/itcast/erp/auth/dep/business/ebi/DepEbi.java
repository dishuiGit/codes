package cn.itcast.erp.auth.dep.business.ebi;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.dep.vo.DepModel;
import cn.itcast.erp.util.base.BaseEbi;

@Transactional
//注解式事务->AOP
//切面实现类+切入点
//execution(* cn.itcast.erp.auth.dep.business.ebi.DepEbi.*(..))
public interface DepEbi extends BaseEbi<DepModel>{

}
