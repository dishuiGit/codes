package cn.dishui.core.controller.brand;

import javax.annotation.Resource;

import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.dishui.core.po.product.Brand;
import cn.dishui.core.po.product.BrandQuery;
import cn.dishui.core.service.service.brand.BrandService;
import cn.itcast.common.page.Pagination;

@Controller
public class BrandController {
	@Resource
	private BrandService brandService;

	@RequestMapping("/brand/list")
	public String brand_list(String name, Integer isDisplay, Integer pageNo,
			Model model) {
		// 查询所有品牌(条件),品牌名称:name,是否可用:isDisplay,当前页数:pageNo
		// 封装pageView
		StringBuilder sb = new StringBuilder();
		// 封装查询对象
		BrandQuery brandQuery = new BrandQuery();
		// 逻辑判断
		if (name != null) {
			brandQuery.setName(name);
			// 拼接name字符串
			sb.append("name=");
			sb.append("" + name + "");
			sb.append("&");
		}
		if (isDisplay != null) {
			brandQuery.setIsDisplay(isDisplay);
			sb.append("isDisplay=" + isDisplay);
		}
		brandQuery.setPageNo(Pagination.cpn(pageNo));

		Pagination pagination = brandService.selectPagination(brandQuery);
		// 添加分页显示对象 pageView ==> /brand/list.do?name=依琦莲&isDisplay=1&pageNo=2
		System.out.println(sb.toString());

		pagination.pageView("/brand/list.do", sb.toString());

		model.addAttribute("pagination", pagination);
		return "brand/list";
	}

	@RequestMapping("/brand/toEdit")
	public String brand_toEdit() {

		return "brand/edit";
	}

	@RequestMapping("/brand/toAdd")
	public String brand_toAdd() {

		return "brand/add";
	}

	@RequestMapping("/brand/add")
	public String brand_Add(Brand brand) {
		// 保存分类
		System.out.println(brand);
		brandService.save(brand);

		return "redirect:/brand/list.do";
	}
	@RequestMapping("/brand/brands_Delete")
	public String brands_Delete(String name, Integer pageNo, Integer isDisplay,
			Integer[] ids) {
		brandService.batchDelete(ids);
		StringBuilder sb = new StringBuilder();
		if (name != null) {
			// 拼接name字符串
			sb.append("name=");
			sb.append("" + name + "");
			sb.append("&");
		}
		if (isDisplay != null) {
			sb.append("isDisplay=" + isDisplay);
			sb.append("&");
		}
		sb.append("pageNo="+pageNo);
		return "redirect:/brand/list.do?"+sb.toString();
	}

}
