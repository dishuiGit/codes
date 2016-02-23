package cn.dishui.gen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import cn.dishui.inter.GeneratorConfig;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class Generator {
	GeneratorConfig gc = ConfigFactory.create(GeneratorConfig.class);
	
	@Test
	public void generator() {
		gen();
//		move();
	}

	@Test
	public void moveFiles() {
		move();
	}

	public void move() {
		String base = getBasePath();
		
		String class_src = base + "/gen/" + gc.name() + "";
		String class_dest = gc.class_dest();
		// -----------------
		String xml_src = base + "/gen/" + captureName(gc.name()) + ".xml";
		String xml_dest = gc.xml_dest();

		try {
			// 如果目录已经存在,删除
			File destF = new File(class_dest, gc.name());
			if (destF.exists()) {
				FileUtils.deleteDirectory(destF);
			}
			File xml_destF = new File(xml_dest, captureName(gc.name()) + ".xml");
			if (xml_destF.exists()) {
				FileUtils.forceDelete(xml_destF);
			}
			// ---------------
			FileUtils.moveDirectoryToDirectory(new File(class_src), new File(class_dest),
					false);
			FileUtils.moveFileToDirectory(new File(xml_src),
					new File(xml_dest), false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void gen() {

		// 配置FK类
		Configuration conf = new Configuration();
		// 模板+数据 = 输出
		// 模板文件夹
		String ftl_folder = this.getClass().getResource("/ftl").getPath();
		System.out.println(ftl_folder);
		Template tm_pojo = null;
		try {
			// 判断文件夹是否存在
			// 清空目录下的所有文件/文件夹
			//FileUtils.cleanDirectory(new File("gen"));
			
			executeGen(conf,new File(ftl_folder));
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	private void executeGen(Configuration conf,File ftl_folder) throws Exception {
		
		String base = getBasePath();
		File gen_f = new File(base+"\\gen");
		if(!gen_f.exists()){
			gen_f.createNewFile();
		}else{
			FileUtils.cleanDirectory(gen_f);
		}
		String pack =base + "/gen/" + gc.name();
		String packJava =base + "/gen/" + captureName(gc.name()) + ".java";
		String packJavaProp =base + "/gen/" + captureName(gc.name()) + "_prop.java";
		String packInfoJava =base + "/gen/" + captureName(gc.name()) + "Info.java";
		String packXML = base +"/gen/" + captureName(gc.name()) + ".xml";
		String packImpl = base +"/gen/" + captureName(gc.name()) + "Impl.java";
		
		conf.setDirectoryForTemplateLoading(ftl_folder);
		List<String> temp_l = gc.ftls();
		List<String> pack_l = ImmutableList.of(packJava,packJavaProp,packInfoJava,packXML,packImpl);
		
		Map root = new HashMap();
		root.put("attr_list", gc.fields());
		// 表名
		root.put("table", gc.name());
		
		Template tm_pojo = null;
		for(int i=0;i<temp_l.size();i++){
			// 模板
			tm_pojo = conf.getTemplate(temp_l.get(i), "utf-8");
			Writer w_pack = new FileWriter(new File(pack_l.get(i)));
			tm_pojo.process(root, w_pack);
			System.out.println(temp_l.get(i)+"--生成成功");
			w_pack.close();
		}
		
		FileUtils.forceMkdir(new File(pack));
		// 文件夹
		File packf = new File(pack);
		for(String pack_s:pack_l){
			if(pack_s.endsWith("java")){
				FileUtils.moveFileToDirectory(new File(pack_s), packf, false);
			}
		}
	}

	private String getBasePath() {
		String base = this.getClass().getResource("/").getPath();
		File base_f = new File(base);
		base = base_f.getParent();
		return base;
	}

	/**
	 * 首字母大写
	 */
	// 首字母大写
	public static String captureName(String name) {
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);

	}

	
}
