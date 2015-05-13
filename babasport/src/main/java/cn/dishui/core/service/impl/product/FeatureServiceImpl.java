package cn.dishui.core.service.impl.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.dishui.core.dao.product.FeatureMapper;
import cn.dishui.core.po.product.Feature;
import cn.dishui.core.po.product.FeatureQuery;
import cn.dishui.core.service.service.product.FeatureService;
@Service
public class FeatureServiceImpl implements FeatureService{

	@Resource
	private FeatureMapper featureMapper;
	public List<Feature> selectByExample(FeatureQuery featureQuery) {
		return featureMapper.selectByExample(featureQuery);
	}

}
