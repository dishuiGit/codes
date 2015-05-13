package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.ColorQuery;

public interface ColorSerivce {

	public List<Color> selectColorByExample(ColorQuery example);
}
