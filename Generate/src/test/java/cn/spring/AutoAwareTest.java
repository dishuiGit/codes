package cn.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.spring.impl.Test1Impl;

public class AutoAwareTest {

	@Test
	public void test(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		Test1Impl t1 = (Test1Impl)ctx.getBean("test1Impl");
		Test1Impl t2 = (Test1Impl)ctx.getBean("test1Impl");
//		ti.test1out();
		
		t1.yy();
		t2.yy();
	}
}
