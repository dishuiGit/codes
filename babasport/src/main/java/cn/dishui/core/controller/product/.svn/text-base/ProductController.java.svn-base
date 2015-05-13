package cn.dishui.core.controller.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.dishui.core.dao.product.ProductMapper;
import cn.dishui.core.po.product.Brand;
import cn.dishui.core.po.product.BrandQuery;
import cn.dishui.core.po.product.Color;
import cn.dishui.core.po.product.ColorQuery;
import cn.dishui.core.po.product.Feature;
import cn.dishui.core.po.product.ProductQuery;
import cn.dishui.core.po.product.ProductWithBLOBs;
import cn.dishui.core.po.product.TypeQuery;
import cn.dishui.core.po.product.ProductQuery.Criteria;
import cn.dishui.core.po.product.Type;
import cn.dishui.core.service.service.brand.BrandService;
import cn.dishui.core.service.service.product.ColorService;
import cn.dishui.core.service.service.product.FeatureService;
import cn.dishui.core.service.service.product.ProductService;
import cn.dishui.core.service.service.product.TypeService;
import cn.itcast.common.page.Pagination;

@Controller
public class ProductController {
	@Resource
	private ProductService productService;
	@Resource
	private BrandService brandService;
	@RequestMapping("/product/list")
	public String list(String name,Integer brandId,Boolean isShow, Integer pageNo,Model model){
		//查询品牌(只查询id,name)
		BrandQuery brandQuery = new BrandQuery();
		brandQuery.setFields("id,name");
		//显示所有可见的品牌
		brandQuery.setIsDisplay(1);
		List<Brand> optionBrands = brandService.selectOptionBrands(brandQuery);
		model.addAttribute("optionBrands", optionBrands);
		
		//根据查询条件,查询
		ProductQuery productQuery = new ProductQuery();
		
		Criteria criteria = productQuery.createCriteria();
		
		//拼接params
		StringBuilder params = new StringBuilder();
		if(name != null){
			criteria.andNameLike("%"+name+"%");
			params.append("name=");
			params.append(name);
			params.append("&");
			//回显
			model.addAttribute("name", name);
		}
		if(brandId != null){
			criteria.andBrandIdEqualTo(brandId);
			params.append("brandId=");
			params.append(brandId);
			params.append("&");
			//回显
		}
		if(isShow != null){
			criteria.andIsShowEqualTo(isShow);
			params.append("isShow=");
			params.append(isShow);
		}
		if(pageNo !=null){
			productQuery.setPageNo(Pagination.cpn(pageNo));
			model.addAttribute("pageNo", Pagination.cpn(pageNo));
		}else{
			productQuery.setPageNo(1);
			model.addAttribute("pageNo", 1);
		}
		//分页
		Pagination pagination = productService.selectByProductQuery(productQuery);
		//分页显示
		pagination.pageView("/product/list.do", params.toString());
		model.addAttribute("pagination", pagination);
		return "product/list";
	}
	
	@Resource
	private TypeService typeService;
	//添加页面
	@RequestMapping("/product/toAdd")
	public String toAdd(Model model){
		//商品类型(下拉选)
		TypeQuery typeQuery = new TypeQuery();
		cn.dishui.core.po.product.TypeQuery.Criteria typeCriteria = typeQuery.createCriteria();
		//父id不能为0
		typeCriteria.andParentIdNotEqualTo(0);
		List<Type> typeList = typeService.selectByExample(typeQuery);
		model.addAttribute("typeList", typeList);
		List<Feature> featureList = featureService.selectByExample(null);
		model.addAttribute("featureList", featureList);
		//父id不能为0
		ColorQuery colorQuery = new ColorQuery();
		cn.dishui.core.po.product.ColorQuery.Criteria colorCriteria = colorQuery.createCriteria();
		colorCriteria.andParentIdNotEqualTo(0);
		
		List<Color> colorList = colorService.selectByExample(colorQuery);
		model.addAttribute("colorList", colorList);
		
		//查询品牌(只查询id,name)
		BrandQuery brandQuery = new BrandQuery();
		brandQuery.setFields("id,name");
		//显示所有可见的品牌
		brandQuery.setIsDisplay(1);
		List<Brand> brandList = brandService.selectOptionBrands(brandQuery);
		model.addAttribute("brandList", brandList);
		return "product/add";
	}
	
	//材质
	@Resource
	private FeatureService featureService;
	//颜色
	@Resource
	private ColorService colorService;
	
	//添加商品
	@RequestMapping("/product/add")
	public String addProduct(ProductWithBLOBs product){
		//添加,在service层进行
		productService.addProduct(product );
		return "redirect:/product/list.do";
	}
	
	//=========================
	//		       上架			 //
	//=========================
	@RequestMapping("/product/upRack")
	public String upRack(Integer[] ids){
		//1,改变商品状态
		//2,添加商品到solr服务器
		productService.upRack(ids);
		return "redirect:/product/list.do?isShow=false";
	}
}
