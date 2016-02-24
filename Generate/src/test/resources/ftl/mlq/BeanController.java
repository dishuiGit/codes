package com.bizoss.trade.${table}.controler;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bizoss.trade.${table}.bean.${table?cap_first};

@Controller
@RequestMapping("/..")
public class ${table?cap_first}Controller {
	private static Logger logger = Logger.getLogger(${table?cap_first}Controller.class);
	
	@RequestMapping("/test")
	public String test(${table?cap_first} ${table}){
		logger.info("test");
		return "test";
	}
}
