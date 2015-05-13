package cn.itcast.erp.util.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.erp.auth.res.business.ebi.ResEbi;
import cn.itcast.erp.auth.res.vo.ResModel;

public class AllResLoadListener implements ServletContextListener{
	public void contextInitialized(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();
		//获取SpringContext对象，读取Bean
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc );
		ResEbi resEbi = (ResEbi) ctx.getBean("resEbi");
		//加载所有资源
		List<ResModel> resList = resEbi.getAll();
		//将集合放入进去，回头使用时，还要取出来，迭代转化字符串，然后使用
		StringBuilder resStr = new StringBuilder();
		for(ResModel rm:resList){
			resStr.append(rm.getUrl());
			resStr.append(";");
			//cn.itcat.save;cn.itcast.delete;
		}
		//放入ServletContext范围
		sc.setAttribute(ResModel.RES_ALL, resStr.toString());
		//使用时直接获取
	}

	public void contextDestroyed(ServletContextEvent event) {
	}
}
