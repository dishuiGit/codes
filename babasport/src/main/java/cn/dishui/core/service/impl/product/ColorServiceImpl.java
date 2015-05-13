package cn.dishui.core.service.impl.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.dishui.core.dao.product.ColorMapper;
import cn.dishui.core.po.product.Color;
import cn.dishui.core.po.product.ColorQuery;
import cn.dishui.core.service.service.product.ColorService;
@Service
public class ColorServiceImpl implements ColorService{

	@Resource
	private ColorMapper colorMapper;
	public List<Color> selectByExample(ColorQuery colorQuery) {
		return colorMapper.selectByExample(colorQuery);
	}

}
