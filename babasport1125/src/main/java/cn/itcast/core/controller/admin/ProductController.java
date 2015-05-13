package cn.itcast.core.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Brand;
import cn.itcast.core.bean.product.BrandQuery;
import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.ColorQuery;
import cn.itcast.core.bean.product.Feature;
import cn.itcast.core.bean.product.ProductQuery;
import cn.itcast.core.bean.product.ProductQuery.Criteria;
import cn.itcast.core.bean.product.ProductWithBLOBs;
import cn.itcast.core.bean.product.Type;
import cn.itcast.core.bean.product.TypeQuery;
import cn.itcast.core.service.product.BrandService;
import cn.itcast.core.service.product.ColorSerivce;
import cn.itcast.core.service.product.FeatureSerivce;
import cn.itcast.core.service.product.ProductService;
import cn.itcast.core.service.product.TypeSerivce;

/**
 * 商品控制层   后台 商品管理
 * 商品列表
 * 上架
 * 查看
 * 添加
 * @author lx
 *
 */
@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandSerice;
	//商品列表
	@RequestMapping("/product/list.do")
	public String list(Integer pageNo,String name , Integer brandId,Boolean isShow,Model model){
		BrandQuery brandQuery = new BrandQuery();
		//指定字段 id name
		brandQuery.setFields("id,name");
		//可见
		brandQuery.setIsDisplay(1);
		//加载页面所需要的品牌  是可见的
		List<Brand> brands = brandSerice.selectBrandList(brandQuery);
		
		//参数回显
		model.addAttribute("brands", brands);
		//查询商品  实例化 商品Query对象
		ProductQuery productQuery = new ProductQuery();
		//获取内部类
		Criteria criteria = productQuery.createCriteria();
		//分页拼接条件的
		StringBuilder params = new StringBuilder();
		
		//判断请求的参数是否为NUll  "     "  商品名
		if(null != name){
			criteria.andNameLike("%" + name + "%");
			
			params.append("name=").append(name);
			
			//参数回显
			model.addAttribute("name", name);
		}
		//品牌ID   where  name = ""  or ( brandId = 1 and is_show = 1)
		if(null != brandId){
			criteria.andBrandIdEqualTo(brandId);
			
			params.append("&").append("brandId=").append(brandId);
			
			//参数回显
			model.addAttribute("brandId", brandId);
		}
		//是否上下架  默认是下架   Boolean 布尔类型
		if(null != isShow){
			criteria.andIsShowEqualTo(isShow);
			params.append("&").append("isShow=").append(isShow);
			//参数回显
			model.addAttribute("isShow", isShow);
		}else{
			criteria.andIsShowEqualTo(false);
			params.append("&").append("isShow=").append(false);
			//参数回显
			model.addAttribute("isShow", false);
		}
		//查询3条
		productQuery.setPageSize(3);
		//当前页
		productQuery.setPageNo(Pagination.cpn(pageNo));
		//
		productQuery.setOrderByClause("id desc");
		//查询分页数据
	   //分页  = productService.select...(productQuery);
		Pagination pagination = productService.selectByExample(productQuery);
		//
		String url = "/product/list.do";///product/list.do?name=''&brandId=1&isShow=1&pageNo=2
		//分页展示
		pagination.pageView(url, params.toString());
		
		//回显
		//参数回显
		model.addAttribute("pagination", pagination);
		
		return "product/list";
	}
	//去添加页面
	@RequestMapping("/product/toAdd.do")
	public String toAdd(Model model){
		//类型
		TypeQuery example = new TypeQuery();
		
		cn.itcast.core.bean.product.TypeQuery.Criteria criteria = example.createCriteria();
		
		criteria.andParentIdNotEqualTo(0);
		
		List<Type> types = typeSerivce.selectType(example);
		model.addAttribute("types", types);
		
		//品牌
		BrandQuery brandQuery = new BrandQuery();
		//指定字段 id name
		brandQuery.setFields("id,name");
		//可见
		brandQuery.setIsDisplay(1);
		//加载页面所需要的品牌  是可见的
		List<Brand> brands = brandSerice.selectBrandList(brandQuery);
		
		model.addAttribute("brands", brands);
		//材质\featureSeri
		List<Feature> features = featureSerivce.selectFeatureByExample(null);
		model.addAttribute("features", features);
		//颜色
		ColorQuery e = new ColorQuery();
		//父ID不等于0
		cn.itcast.core.bean.product.ColorQuery.Criteria createCriteria = e.createCriteria();
		
		createCriteria.andParentIdNotEqualTo(0);
		List<Color> colors = colorSerivce.selectColorByExample(e);
		
		model.addAttribute("colors", colors);
		
		return "product/add";
	}
	//
	//商品添加
	@RequestMapping("/product/add.do")
	public String add(ProductWithBLOBs product,Model model){
		//商品保存  返回商品ID  自增长数据库
		
		productService.addProductAndImgAndSku(product);
		
		
		return "redirect:/product/list.do";
	}
	//上架
	@RequestMapping("/product/isShow.do")
	public String isShow(Integer[] ids,Model model){
		//1:上架
		productService.isShow(ids);
		return "redirect:/product/list.do";
	}
	
	@Autowired
	private ColorSerivce colorSerivce;
	@Autowired
	private FeatureSerivce featureSerivce;
	@Autowired
	private TypeSerivce typeSerivce;

	

}
