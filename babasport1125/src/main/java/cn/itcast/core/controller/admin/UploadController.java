package cn.itcast.core.controller.admin;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.fckeditor.response.UploadResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import cn.itcast.core.web.Constants;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

/**
 * 上传图片
 * 品牌图片
 * 商品图片
 * Fck图片
 * @author lx
 *
 */
@Controller
public class UploadController {

	//上传图片   商品  品牌  input type file  name pic
	@RequestMapping(value = "/upload/uploadPic.do")
	public void uploadPic(@RequestParam MultipartFile pic,HttpServletResponse response){
		
		System.out.println(pic.getOriginalFilename());
		//apache 扩展名
		String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
		
		//53a73....  会不会重复 有可能
		//图片名称的生成策略    yyyy-MM-dd HH:mm:ssSSS   + 随机三位  10以内
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String name = df.format(new Date());
		// 随机三位
		Random r = new Random();
		for (int i = 0; i < 3; i++) {
			name += r.nextInt(10);
		}
		//图片名称
		
		//使用Jersey框架
		Client client = new Client();
		
		String path = "upload/" + name + "." + ext;
		String url =  Constants.IMG_WEB + path;
		//发送图片
		WebResource resource = client.resource(url);
		//发送  网线发送  二进制   流  字符串
		try {
			resource.put(String.class, pic.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//返回路径
		JSONObject jo = new JSONObject();
		jo.put("url", url);
		jo.put("path", path);
		//url  显示
		//path  保存
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(jo.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//上传图片 Fck
	@RequestMapping("/upload/fck.do")
	public void fck(HttpServletRequest request,HttpServletResponse response){
		//由Spring提供  
		//将request 强转成 MultipartRequest的
		MultipartRequest mr = (MultipartRequest)request;
		//从MultipartRequest取出上传的文件    K名   :   V 文件
		Map<String, MultipartFile> fileMap = mr.getFileMap();
		
		//遍历Map
		Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
		//支持 多文件上传  本次只有一个
		for (Entry<String, MultipartFile> entry : entrySet) {
			//文件
			MultipartFile pic = entry.getValue();
			System.out.println(pic.getOriginalFilename());
			//apache 扩展名
			String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
			
			//53a73....  会不会重复 有可能
			//图片名称的生成策略    yyyy-MM-dd HH:mm:ssSSS   + 随机三位  10以内
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String name = df.format(new Date());
			// 随机三位
			Random r = new Random();
			for (int i = 0; i < 3; i++) {
				name += r.nextInt(10);
			}
			//图片名称
			
			//使用Jersey框架
			Client client = new Client();
			
			String path = "upload/" + name + "." + ext;
			//全路径
			
			String url =  Constants.IMG_WEB + path;
			//发送图片
			WebResource resource = client.resource(url);
			//发送  网线发送  二进制   流  字符串
			try {
				resource.put(String.class, pic.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//返回路径  fck Jar包  url 返回  
			UploadResponse ok = UploadResponse.getOK(url);
			//返回   request  response
			try {
				//write 字符流
				response.getWriter().print(ok);
				//字节流
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			

		}
		
		
		
		System.out.println(1);
	}
}
