package cn.itcast.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.bean.product.Feature;
import cn.itcast.core.bean.product.FeatureQuery;
import cn.itcast.core.dao.product.FeatureMapper;

/**
 * 材质
 * @author lx
 *
 */
@Service
@Transactional
public class FeatureServiceImpl implements FeatureSerivce {

	@Autowired
	private FeatureMapper featureMappper;
	
	
	//查询符合条件的 材质
	public List<Feature> selectFeatureByExample(FeatureQuery example){
		return featureMappper.selectByExample(example);
	}
	
	
}
