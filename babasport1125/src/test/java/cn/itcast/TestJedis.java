package cn.itcast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.Jedis;
import cn.itcast.common.junit.SpringJunitTest;
import cn.itcast.core.bean.product.Brand;

/**
 * 测试Redis  jedis
 * @author lx
 *
 */
public class TestJedis extends SpringJunitTest {

	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;
	
	@Test
	public void testJedis() throws Exception {
		//获取Jedis
		Jedis redis = jedisConnectionFactory.getShardInfo().createResource();
		
		Brand brand = new Brand();
		brand.setName("金乐乐");
		
		//二进制流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		//写入流
		oos.writeObject(brand);
		//二进制流
		byte[] b = baos.toByteArray();
		//把对象保存到Redis当中了
		redis.set("brandtest".getBytes(), b);
		
		//取出来
		byte[] cs = redis.get("brandtest".getBytes());
		//转成品牌对象
		ByteArrayInputStream bais = new ByteArrayInputStream(cs);
		ObjectInputStream ois = new ObjectInputStream(bais);
		
		Brand bb = (Brand) ois.readObject();
		
		System.out.println(bb);

		
	
		
		
	}
}
