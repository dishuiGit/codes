package cn.dishui.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Controller;

import redis.clients.jedis.Jedis;

@Controller
public class TestJedis extends SpringBaseTest {
	@Resource
	private JedisConnectionFactory jedisConnectionFactory;
	
	@Test
	public void testJedis(){
	
		Jedis jedis = jedisConnectionFactory.getShardInfo().createResource();
		//jedis.set("1", "2");
		
		System.out.println(jedis.get("1"));
		
	}
	
	

}
