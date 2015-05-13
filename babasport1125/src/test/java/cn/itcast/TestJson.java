package cn.itcast;

import java.io.StringWriter;
import java.io.Writer;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.junit.Test;

import cn.itcast.core.bean.user.Buyer;

public class TestJson {

	
	@Test
	public void testJson() throws Exception {
		//@RequestBoby  接口JSON字符串 转成POJO对象
		//@ResponseBoby  json 交互POJO对象 转成JOSN串
		//第三方转JSON的jar
		//一个对象转成JSON
		Buyer buyer = new Buyer();
		buyer.setUsername("fbb2014");
		
		ObjectMapper  om = new ObjectMapper();
		//设置成如果字段为Null就不要转了
		om.setSerializationInclusion(Inclusion.NON_NULL);
		//转
		StringWriter str = new StringWriter();
		om.writeValue(str, buyer);
		
		//w转成 String
		//对象转字符串
		System.out.println(str.toString());
		
		//字符串转对象
		
		Buyer readValue = om.readValue(str.toString(), Buyer.class);
		
		System.out.println(readValue);
		
		
		
		
	}
}
