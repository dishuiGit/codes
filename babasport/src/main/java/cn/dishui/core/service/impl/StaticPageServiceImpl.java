package cn.dishui.core.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ViewResource;
import com.sun.jersey.api.client.WebResource;

import cn.dishui.core.service.service.StaticPageService;
import cn.dishui.core.web.Constants;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class StaticPageServiceImpl implements StaticPageService,
		ServletContextAware {
	// 注入获取FreeMarker类
	private Configuration conf;

	public void setFreeMarkerConfigurer(
			FreeMarkerConfigurer freeMarkerConfigurer) {
		conf = freeMarkerConfigurer.getConfiguration();
	}

	// 模板目录位置 /WEB-INF/ftl/
	public void index(Map<String, Object> root, Integer id) {
		// 模板文件位置
		Template template = null;
		Writer out = null;
		try {
			template = conf.getTemplate("productDetail.html");
			// 生成静态页
			// 静态页路径 webapp/html/278.html
			String staticPath = getPath("/html/" + id + ".html");
			// String staticPath =
			// "D:/1125/workspace_erp/babasport/src/test/resources/hhh.html";
			File staticFile = new File(staticPath);
			// 此文件的父文件夹如果不存在,创建一个
			File f = staticFile.getParentFile();
			if (!f.exists()) {
				f.mkdirs();
			}
			/*
			 * TODO 报错原因 if(!staticFile.getParentFile().exists()){
			 * staticFile.mkdirs(); }
			 */
			out = new OutputStreamWriter(new FileOutputStream(staticFile),
					"UTF-8");
			template.process(root, out);

		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// http://localhost:8080/html/278.html
	public String getPath(String path) {

		//
		return servletContext.getRealPath(path);
		// return Constants.PAGE_WEB+path;
	}

	private ServletContext servletContext;

	// 注入

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	// 把静态图片生成到linux下的tomcat服务器
	public void index2(Map<String, Object> root, Integer id) {
		// 模板文件位置
		Template template = null;
		Writer out = null;
		File staticFile = null;
		try {
			template = conf.getTemplate("productDetail.html");
			String tmp = getPath("/tmp/" + id + ".html");
			staticFile = new File(tmp);
			// 此文件的父文件夹如果不存在,创建一个
			File f = staticFile.getParentFile();
			if (!f.exists()) {
				f.mkdirs();
			}
			out = new OutputStreamWriter(new FileOutputStream(staticFile),
					"UTF-8");
			template.process(root, out);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// 使Jersey框架
		Client client = new Client();
		String path = "upload/" + id + ".html";
		String url = Constants.PAGE_WEB + path;
		// 发送图片
		WebResource resource = client.resource(url);
		try {
			resource.put(String.class,
					FileUtils.readFileToByteArray(staticFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
