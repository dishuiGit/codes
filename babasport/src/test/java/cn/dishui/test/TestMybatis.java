package cn.dishui.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import cn.dishui.core.dao.product.ColorMapper;
import cn.dishui.core.po.product.Color;

public class TestMybatis extends SpringBaseTest{
	@Resource
	private ColorMapper colorMapper;
	@Test
	public void lianhechaxun(){
		List<Color> colors = colorMapper.selectColorsByPId(278);
		for (Color color : colors) {
			System.out.println(color.getName());
		}
	}
}
