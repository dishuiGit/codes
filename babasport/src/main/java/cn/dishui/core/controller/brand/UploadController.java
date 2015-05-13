package cn.dishui.core.controller.brand;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.fckeditor.response.UploadResponse;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.dishui.core.web.Constants;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Controller
public class UploadController {

	// 上传图片
	@RequestMapping(value = "/upload/uploadPic")
	public void uploadPic(@RequestParam MultipartFile pic,
			HttpServletResponse response) {
		upload(pic, response);
	}

	private void upload(MultipartFile pic, HttpServletResponse response) {
		// 扩展名
		String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
		// 图片名称生成策略
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String name = df.format(new Date());
		// 随机三位数,生成图片名称
		Random r = new Random();
		for (int i = 0; i < 3; i++) {
			name += r.nextInt(10);
		}
		// 使Jersey框架
		Client client = new Client();
		String path = "upload/" + name + "." + ext;
		String url = Constants.IMG_WEB + path;
		// 发送图片
		WebResource resource = client.resource(url);
		try {
			resource.put(String.class, pic.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回路径
		JSONObject jo = new JSONObject();
		jo.put("url", url);
		jo.put("path", path);
		// 告诉浏览器,你发送的数据类型
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// /product/uploadFck
	@RequestMapping(value = "/product/uploadFck")
	public void uploadFck(HttpServletRequest request,
			HttpServletResponse response) {
		// 上传文件在request中,强转request
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;

		Map<String, MultipartFile> fileMap = mRequest.getFileMap();
		Set<String> keySet = fileMap.keySet();
		// 返回唯一的 上传文件
		String fileName = keySet.iterator().next();
		MultipartFile pic = fileMap.get(fileName);
		// 扩展名
		String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
		// 图片名称生成策略
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String name = df.format(new Date());
		// 随机三位数,生成图片名称
		Random r = new Random();
		for (int i = 0; i < 3; i++) {
			name += r.nextInt(10);
		}
		// 使Jersey框架
		Client client = new Client();
		String path = "upload/" + name + "." + ext;
		String url = Constants.IMG_WEB + path;
		// 发送图片
		WebResource resource = client.resource(url);
		try {
			resource.put(String.class, pic.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回路径 使用 fckresponse
		UploadResponse ok = UploadResponse.getOK(url);
		try {
			response.getWriter().print(ok);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
