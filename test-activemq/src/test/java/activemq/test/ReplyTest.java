package activemq.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import activemq.reply.Producer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-jms.xml")
public class ReplyTest {
	
	@Resource
	private Producer producer;
	
	@Test
	public void send(){
		String msg = "hello";
		
		producer.sendMessage(msg);
	}
}
