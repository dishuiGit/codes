package cn.dishui.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class StaticPage extends SpringBaseTest{
	@Resource
	private FreeMarkerConfigurer markerConfigurer;
	//配置FreeMarker类
	private Configuration conf;
	public void setConf(FreeMarkerConfigurer markerConfigurer){
		conf = markerConfigurer.getConfiguration();
	}
	@Test
	public void testStaticPage() throws IOException, TemplateException{
		
		
		
		//模板目录
		String path = "D:\\1125\\workspace_erp\\babasport\\src\\test\\resources\\";
		conf.setDirectoryForTemplateLoading(new File(path));
		//模板文件
		Template template = conf.getTemplate("Test.html");
		//数据
		Map root = new HashMap();
		//root.put("test", "haha");
		root.put("test", "haha2");
		
		Writer writer = new FileWriter(new File(path+"hello.html"));
		template.process(root, writer);
		System.out.println("生成成功!");
	}
}
