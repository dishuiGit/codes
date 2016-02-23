package cn.dishui;

import java.util.Arrays;
import java.util.List;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import cn.dishui.inter.GeneratorConfig;

public class GenTest {
	
	@Before
	public void before(){
		
	}
	
	@Test
	public void OwnerTest(){
		GeneratorConfig gc = ConfigFactory.create(GeneratorConfig.class);
		System.out.println(gc.ftls());
		
		List<String> ftls_l = gc.ftls();
		System.out.println(ftls_l);
		System.out.println(gc.fields());
	}
}
