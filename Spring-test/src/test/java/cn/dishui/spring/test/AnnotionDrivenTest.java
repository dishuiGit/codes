package cn.dishui.spring.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.dishui.spring.bean.BeanT1;

@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationcontext.xml")
public class AnnotionDrivenTest {
	@Resource
	private BeanT1 bt1;
	
	@Test
	public void bt1Test(){
		System.out.println(bt1.getName());
	}
}
