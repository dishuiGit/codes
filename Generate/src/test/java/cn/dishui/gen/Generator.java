package cn.dishui.gen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import cn.dishui.inter.AppGeneratorConfig;
import cn.dishui.inter.GeneratorConfig;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class Generator {
//	GeneratorConfig gc = ConfigFactory.create(GeneratorConfig.class);
	
	AppGeneratorConfig gc = ConfigFactory.create(AppGeneratorConfig.class);
	@Test
	public void generator() {
		gen();
//		move();
	}
	@Test
	public void moveFiles() {
		move();
	}

	private void move() {
		String base = getBasePath();
		
		String class_src = base + "/gen/" + gc.name() + "";
		String class_dest = gc.class_dest();

		try {
			// 如果目录已经存在,删除
			File destF = new File(class_dest, gc.name());
			if (destF.exists()) {
				FileUtils.deleteDirectory(destF);
			}
			
			// ---------------
			FileUtils.moveDirectoryToDirectory(new File(class_src), new File(class_dest),
					false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void gen() {

		// 配置FK类
		Configuration conf = new Configuration();
		// 模板+数据 = 输出
		// 模板文件夹
		String ftl_folder = this.getClass().getResource(gc.ftl_floder()).getPath();
		System.out.println(ftl_folder);
		Template tm_pojo = null;
		try {
			
			executeGen(conf,new File(ftl_folder));
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	private void executeGen(Configuration conf,File ftl_folder) throws Exception {
		
		String base = getBasePath();
		File gen_f = new File(base+gc.floder());
		if(!gen_f.exists()){
			FileUtils.forceMkdir(gen_f);
		}else{
			FileUtils.cleanDirectory(gen_f);
			for(String pack:gc.packages()){
				File packf = new File(gen_f+"/"+gc.name()+"/"+pack);
				FileUtils.forceMkdir(packf);
			}
		}
		final String gen_f_path = gen_f.getAbsolutePath()+ "/" + gc.name();
		
		conf.setDirectoryForTemplateLoading(ftl_folder);
		List<String> temp_l = gc.ftls();
		List<String> pack_l = gc.files();
		
		Function<String, String> function = new Function<String, String>() {
			public String apply(String input) {
				return gen_f_path+input;
			}
	    	
	    };  
	    pack_l = Lists.transform(pack_l, function);  
		
		Map root = new HashMap();
		root.put("attr_list", gc.fields());
		// 表名
		root.put("table", gc.name());
		// 表别名
		root.put("table_alias", gc.alias());
		
		Template tm_pojo = null;
		for(int i=0;i<temp_l.size();i++){
			// 模板
			tm_pojo = conf.getTemplate(temp_l.get(i), "utf-8");
			Writer w_pack = new FileWriter(new File(pack_l.get(i)));
			tm_pojo.process(root, w_pack);
			System.out.println(pack_l.get(i)+"--生成成功");
			w_pack.close();
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
