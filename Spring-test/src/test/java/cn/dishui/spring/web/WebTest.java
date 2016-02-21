package cn.dishui.spring.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebTest {
	
	@RequestMapping("/test")
	public String brand_toEdit() {
		System.out.println("test");
		return "test";
	}
}
