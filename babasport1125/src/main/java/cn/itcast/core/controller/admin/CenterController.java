package cn.itcast.core.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 中心控制
 * 跳转WEB-INF下的页面
 * @author lx
 *
 */
@Controller
@RequestMapping(value = "/control")
public class CenterController {

	//入门页面
	@RequestMapping(value = "/index.do")
	public String index(){
		return "index";
	}
	//头
	@RequestMapping(value = "/top.do")
	public String top(){
		return "top";
	}
	//身体
	@RequestMapping(value = "/main.do")
	public String main(){
		return "main";
	}
	//左
	@RequestMapping(value = "/left.do")
	public String left(){
		return "left";
	}
	//右
	@RequestMapping(value = "/right.do")
	public String right(){
		return "right";
	}
}
