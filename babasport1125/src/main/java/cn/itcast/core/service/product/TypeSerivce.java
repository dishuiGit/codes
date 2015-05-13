package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.core.bean.product.Type;
import cn.itcast.core.bean.product.TypeQuery;

public interface TypeSerivce {

	public List<Type> selectType(TypeQuery example);
}
