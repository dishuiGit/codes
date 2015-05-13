package cn.itcast.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.ColorQuery;
import cn.itcast.core.dao.product.ColorMapper;

/**
 * 材质
 * @author lx
 *
 */
@Service
@Transactional
public class ColorServiceImpl implements ColorSerivce {

	@Autowired
	private ColorMapper colorMapper;
	
	public List<Color> selectColorByExample(ColorQuery example){
		return colorMapper.selectByExample(example);
	}
}
