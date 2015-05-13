package cn.itcast;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.common.junit.SpringJunitTest;
import cn.itcast.core.bean.TestTb;
import cn.itcast.core.dao.TestTbDao;
import cn.itcast.core.service.TestTbService;



public class TestTestTb extends SpringJunitTest{

	@Autowired
	private TestTbService testTbService;
	
	//保存数据
	@Test
	public void testAdd() throws Exception {
		TestTb tb = new TestTb();
		tb.setBirthday(new Date());
		tb.setName("哧哧");
		
		testTbService.addTestTb(tb);
	}
}
