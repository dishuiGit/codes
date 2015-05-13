package cn.itcast.core.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Brand;
import cn.itcast.core.bean.product.BrandQuery;
import cn.itcast.core.service.product.BrandService;

/**
 * 品牌管理
 * 品牌列表带分页  带查询条件
 * 品牌添加(去添加页页 ,提交添加)
 * 品牌修改(去修改页页 ,提交修改)
 * 品牌删除
 * 品牌批量删除
 * @author lx
 *
 */
@Controller
public class BrandController {

	@Autowired
	private BrandService brandService;
	//查询分页对象  带条件
	//当前页
	//品牌名称
	//品牌是否可见
	//去掉前后空格  再判断如果是空串 绑定参数为Null
	@RequestMapping(value = "/brand/list.do")
	public String list(Integer pageNo,String name,Integer isDisplay,Model model){
		//判断传递的参数是否为NUll
		//先第一步:实例化Query对象
		BrandQuery brandQuery = new BrandQuery();
		
		//拼接条件
		StringBuilder params = new StringBuilder();
		//品牌名称
		if(null != name){
			//把品牌名称放在Query对象中 供Service Dao使用
			brandQuery.setName(name);
			//拼接上
			params.append("name=").append(name);
			//回显
			model.addAttribute("name", name);
		}
		//是否可见
		if(null != isDisplay){
			brandQuery.setIsDisplay(isDisplay);
			//拼接上
			params.append("&").append("isDisplay=").append(isDisplay);
			//回显
			model.addAttribute("isDisplay", isDisplay);
		}else{
			//默认值
			brandQuery.setIsDisplay(1);
			//拼接上
			params.append("&").append("isDisplay=").append(1);
			//回显
			model.addAttribute("isDisplay", 1);
		}
		//当前页  Pagination 自带如果为NUll或小于1 将页号设置成1
		brandQuery.setPageNo(Pagination.cpn(pageNo));
		//每页数显示为3条
		brandQuery.setPageSize(3);
		//把条件传递给Service层
		Pagination pagination = brandService.selectBrandListWithWhere(brandQuery);
		///brand/list.do?name=''依琪莲'&isDisplay=1&pageNo=2
		//分页中一个方法  传递二个参数
		//2:name=''依琪莲'&isDisplay=1
		//1:Url 
		String url = "/brand/list.do";
		//页面展示功能
		pagination.pageView(url, params.toString());
		
		//回显页号
		model.addAttribute("pageNo", brandQuery.getPageNo());
		
		//返回分页到页面
		model.addAttribute("pagination", pagination);

		//跳转页面
		return "brand/list";
	}
	//去添加页面
	@RequestMapping(value = "/brand/toAdd.do")
	public String toAdd(){
		return "brand/add";
	}
	//提交添加
	@RequestMapping(value = "/brand/add.do")
	public String add(Brand brand){
		//保存品牌
		brandService.addBrand(brand);
		//返回List页面
		return "redirect:/brand/list.do";
	}
	//批量删除
	@RequestMapping(value = "/brand/deleteBrands.do")
	public String deleteBrands(Integer[] ids,Integer pageNo,String name,Integer isDisplay,Model model){
		//批量删除
		brandService.deleteBrands(ids);
		
		if(null != pageNo){
			model.addAttribute("pageNo", pageNo);
		}
		if(null != name){
			model.addAttribute("name", name);
		}
		if(null != isDisplay){
			model.addAttribute("isDisplay", isDisplay);
		}
		
		//返回List页面
		return "redirect:/brand/list.do";
	}
	//去修改页面
	@RequestMapping(value = "/brand/toEdit.do")
	public String toEdit(Integer id,Model model){
		//查询
		Brand brand = brandService.selectBrandById(id);
		model.addAttribute("brand", brand);
		return "brand/edit";
	}
	//修改页面
	@RequestMapping(value = "/brand/edit.do")
	public String edit(Brand brand){

		//修改
		brandService.updateBrandById(brand);
		//返回List页面
		return "redirect:/brand/list.do";
	}
}
