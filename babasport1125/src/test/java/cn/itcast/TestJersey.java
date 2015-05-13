package cn.itcast;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class TestJersey {

	//发送图片 测试
	public static void main(String[] args) throws IOException {
		//使用Jersey框架
		Client client = new Client();
		String url = "http://localhost:8088/img-web/upload/ppp.jpg";
		//发送图片
		WebResource resource = client.resource(url);
		
		String path = "D:\\讲课内容--\\新巴巴运动网\\图片资源\\53d8d9bbN48f21a85.jpg";
		//
		byte[] readFileToByteArray = FileUtils.readFileToByteArray(new File(path));
		
		//发送  网线发送  二进制   流  字符串
		resource.put(String.class, readFileToByteArray);
		
		System.out.println("发送完成");
	}
}
