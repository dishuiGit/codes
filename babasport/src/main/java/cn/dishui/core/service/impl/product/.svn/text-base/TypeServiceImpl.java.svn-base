package cn.dishui.core.service.impl.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.dishui.core.dao.product.TypeMapper;
import cn.dishui.core.po.product.Type;
import cn.dishui.core.po.product.TypeQuery;
import cn.dishui.core.service.service.product.TypeService;
@Service
public class TypeServiceImpl implements TypeService{

	@Resource
	private TypeMapper typeMapper;
	public List<Type> selectByExample(TypeQuery typeQuery) {
		return typeMapper.selectByExample(typeQuery);
	}

}
