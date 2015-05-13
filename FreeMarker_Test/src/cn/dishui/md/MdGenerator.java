package cn.dishui.md;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class MdGenerator {
	@Test
	public void generator(){
		gen("Test03");
	}

	public static void gen(String html_n) {

		// html--> D:\1125\笔记\note\html
		// md--> D:\1125\笔记\note\md
		//
		String html_p = "D:/1125/笔记/note/html/" + html_n + ".html";
		String md_p = "D:/1125/笔记/note/md/" + html_n + ".md";
		String md_sp = "md/" + html_n + ".md";
		// 配置FK类
		Configuration conf = new Configuration();
		// 模板+数据 = 输出
		// 模板文件夹
		String ftl_folder = "ftl";
		Template tm = null;
		try {
			conf.setDirectoryForTemplateLoading(new File(ftl_folder));
			// 模板
			tm = conf.getTemplate("模板.html","utf-8");
			// 数据
			Map root = new HashMap();
			root.put("name", html_n);
			// 生成md文件
			File md_f = new File(md_p);
			if (!md_f.exists()) {
				md_f.createNewFile();
			}
			root.put("path", md_sp);
			// 实例
			Writer writer = new FileWriter(new File(html_p));
			tm.process(root, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

}
