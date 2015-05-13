package cn.itcast.core.service.staticpage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 静态化 业务层
 * @author lx
 *
 */
public class StaticPageServiceImpl implements StaticPageService,ServletContextAware {

	//实例化Freemarker  配置文件中 自己手动实例化  <property >
	private Configuration conf;
	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.conf = freeMarkerConfigurer.getConfiguration();
	}



	//静态化方法
	//String path = "C:\\Users\\lx\\workspace\\babasport1125";
	//String path = "/WEB-INF/ftl/sfsfs.html";
	//乱码
	//web.xml Fitter UTF-8   POST
	//GET tomcat server.xml URIEncoding   request  response
	
	
	public void index(Map<String,Object> root,Integer id) {
		
		//模板对象
		//放入Map 数据  并指定输出路径
		String path = getPath("/html/product/" + id + ".html");
		//创建文件夹
		File file = new File(path);
		//此文件的父文件夹
		File f = file.getParentFile();
		//判断此文件夹存在不存在
		if(!f.exists()){
			f.mkdirs();//创建多级目录
		}
		
		//输出流
		Writer out = null;
		try {
			//输出的指定路径   c:.....   找不到指定文件
			//乱码?  写  UTF-8
			out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			//读  UTF-8  
			Template template = conf.getTemplate("productDetail.html");
			template.process(root, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//静态化过程
/*		   1)实例化  configaration  conf  = new Configration()    Freemarker 
				   2)设置上面对象的模板路径  文件夹  
				    3)设置上面文件夹里的哪个文件为模板
				    4)设置Map 数据给Freemarker  
				    5)处理 conf.process(root,out)   Writer  out  指定输出到哪里  */
		

	}
	
	//上下文
	private ServletContext servletContext;
	
	//获取当前项目的路径  wepapp/html
	//name =  "/html"
	public String getPath(String name){
		return servletContext.getRealPath(name);
	}


	//通过接口注入上下文
	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}
}
