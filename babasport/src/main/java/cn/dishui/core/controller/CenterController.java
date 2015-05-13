package cn.dishui.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CenterController {

	@RequestMapping("/index")
	public String index(){
		return "index";
	}
	@RequestMapping("/top")
	public String top(){
		return "top";
	}
	@RequestMapping("/main")
	public String main(){
		return "main";
	}
	@RequestMapping("/left")
	public String left(){
		return "left";
	}
	@RequestMapping("/right")
	public String right(){
		return "right";
	}
	@RequestMapping("/frame/product_main")
	public String product_main(){
		return "frame/product_main";
	}
	@RequestMapping("/frame/product_left")
	public String product_left(){
		return "frame/product_left";
	}
	@RequestMapping("/frame/product_list")
	public String f_product_list(){
		return "frame/product_list";
	}

}
