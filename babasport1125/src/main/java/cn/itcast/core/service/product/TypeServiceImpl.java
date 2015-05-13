package cn.itcast.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.bean.product.Type;
import cn.itcast.core.bean.product.TypeQuery;
import cn.itcast.core.dao.product.TypeMapper;

/**
 * 类型
 * @author lx
 *
 */
@Service
@Transactional
public class TypeServiceImpl implements TypeSerivce {

	@Autowired
	private TypeMapper typeMapper;
	//
	public List<Type> selectType(TypeQuery example){
		return typeMapper.selectByExample(example);
	}
}
