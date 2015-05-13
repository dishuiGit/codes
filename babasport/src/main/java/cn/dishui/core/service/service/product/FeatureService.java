package cn.dishui.core.service.service.product;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.dishui.core.po.product.Feature;
import cn.dishui.core.po.product.FeatureQuery;
import cn.dishui.core.po.product.Type;
import cn.dishui.core.po.product.TypeQuery;

@Transactional
public interface FeatureService {
	
	/**
	 * 商品材质 下拉选
	 * @param typeQuery
	 * @return
	 */
	public List<Feature> selectByExample(FeatureQuery featureQuery);
}
