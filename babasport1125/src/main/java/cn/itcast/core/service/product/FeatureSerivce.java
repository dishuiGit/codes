package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.core.bean.product.Feature;
import cn.itcast.core.bean.product.FeatureQuery;

public interface FeatureSerivce {

	public List<Feature> selectFeatureByExample(FeatureQuery example);
}
