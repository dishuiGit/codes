package cn.dishui.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import cn.dishui.inter.GeneratorConfig;

public class GenTest {
	private static Logger logger = Logger.getLogger(GenTest.class);
	GeneratorConfig gc = ConfigFactory.create(GeneratorConfig.class);
	
	@Test
	public void gen() throws IOException{
		String base = getBasePath();
		logger.info(base);
		
		List<String> packages = gc.packages();
		File upper = new File(base+"/"+gc.name());
		if(!upper.exists()){
			FileUtils.forceMkdir(upper);
		}
		logger.info(upper.getAbsolutePath());
		
		for(String pack:packages){
			File packf = new File(upper+"/"+pack);
			FileUtils.forceMkdir(packf);
		}
	}
	
	
	private String getBasePath() {
		String base = this.getClass().getResource("/").getPath();
		File base_f = new File(base);
		base = base_f.getParent();
		return base;
	}
}
