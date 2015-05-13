package cn.itcast.core.controller.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.service.product.SkuService;

/**
 * 库存管理
 * 加载库存 列表
 * 修改库存信息
 * 
 * @author lx
 *
 */
@Controller
public class SkuController {
	
	@Autowired
	private SkuService skuService;
	//加载列表
	@RequestMapping(value = "/sku/list.do")
	public String list (Integer id , String pno,Model model){
		//通过商品ID 查询Sku结果集
		
		model.addAttribute("pno", pno);
		model.addAttribute("skus", skuService.selectByExample(id));
		
		return "sku/list";
	}
	
	//保存
	@RequestMapping(value = "/sku/add.do")
	public void add (Sku sku,Model model,HttpServletResponse response){
		skuService.addSku(sku);
		
		JSONObject jo = new JSONObject();
		jo.put("message", "保存成功!");
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(jo.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
