package cn.dishui.core.controller.product;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.dishui.core.dao.product.ColorMapper;
import cn.dishui.core.dao.product.ImgMapper;
import cn.dishui.core.dao.product.SkuMapper;
import cn.dishui.core.po.product.Brand;
import cn.dishui.core.po.product.BrandQuery;
import cn.dishui.core.po.product.Color;
import cn.dishui.core.po.product.Img;
import cn.dishui.core.po.product.ImgQuery;
import cn.dishui.core.po.product.ProductWithBLOBs;
import cn.dishui.core.po.product.Sku;
import cn.dishui.core.po.product.SkuQuery;
import cn.dishui.core.service.service.brand.BrandService;
import cn.dishui.core.service.service.product.ProductService;
import cn.itcast.common.page.Pagination;

@Controller
public class FrontProductController {
	@Resource
	private ProductService productService;
	@Resource
	private BrandService brandService;
	@RequestMapping("/product/display/list.shtml")
	public String list(String keyword,String price,Integer pageNo,Integer brandId, Model model){
		//查询solr服务器
		//封装
		//查询
		SolrQuery query = new SolrQuery();
		//拼接分页查询条件 keyword=依&price=0-69&brandId
		StringBuilder sb = new StringBuilder();
		//标记
		boolean flag = false;
		//
		Map<String,String> 	filters = new LinkedHashMap<String,String>();
		//keyword(判断)
		if(null != keyword){
			//TODO query.setQuery-->报错
			query.set("q","name_ik:"+keyword);
			sb.append("keyword="+keyword);
			model.addAttribute("keyword", keyword);
		}else{
			query.setQuery("name_ik:*");
		}
		//price(范围)(判断)0-69
		if(null != price){
			if(price.indexOf("-")>0){
				String[] prices = price.split("-");
				query.addFilterQuery("price:["+Float.valueOf(prices[0])+" TO "+Float.valueOf(prices[1])+"]");
			}else{
				query.addFilterQuery("price:[600 TO *]");
			}
			filters.put("价格", price);
			sb.append("&price="+price);
			flag = true;
			model.addAttribute("price", price);
		}
		//brandId(判断)
		if(null != brandId){
			query.addFilterQuery("brandId:"+brandId);
			sb.append("&brandId="+brandId);
			String brandName = brandService.selectBrandById(brandId).getName();
			filters.put("品牌", brandName);
			flag = true;
			model.addAttribute("brandId", brandId);
		}
		model.addAttribute("filters", filters);
		model.addAttribute("flag", flag);
		pageNo = Pagination.cpn(pageNo);
		//分页
		Pagination pagination = productService.solrQuery(query,pageNo);
		
		//分页视图
		pagination.pageView("/product/display/list.shtml", sb.toString());
		
		model.addAttribute("pagination", pagination);
		
		//查询品牌(只查询id,name)
		BrandQuery brandQuery = new BrandQuery();
		brandQuery.setFields("id,name");
		//显示所有可见的品牌
		brandQuery.setIsDisplay(1);
		List<Brand> brandList = brandService.selectOptionBrands(brandQuery);
		model.addAttribute("brandList", brandList);
		return "product/product";
	}
	//=======================
	//       单品页                        //                 
	//=======================
	//img不涉及业务,(直接在controller层调用maper)
	@Resource
	private ImgMapper imgMapper;
	@Resource
	private SkuMapper skuMapper;
	@Resource
	private ColorMapper colorMapper;
	@RequestMapping("/product/display/productDetail.shtml")
	public String productDetail(Integer id,Model model){
		//图片表:图片地址(allUrl)
		ImgQuery imgQuery = new ImgQuery();
			//根据商品id
		imgQuery.createCriteria().andProductIdEqualTo(id).andIsDefEqualTo(true);
		imgQuery.setOrderByClause("id desc");
			//默认(升序排列)第一个
		List<Img> imgs = imgMapper.selectByExample(imgQuery);
		//回显
		model.addAttribute("imgAllUrl", imgs.get(0).getAllUrl() );
		//商品表:名称(name),介绍(description),规格参数,包装清单(pack_list)
		ProductWithBLOBs product = productService.selectByKey(id);
		model.addAttribute("product", product );
		//最小销售单元表:(sku) 销售价,市场价,运费,库存,颜色
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(id);
		skuQuery.setOrderByClause("id asc");
		List<Sku> skus = skuMapper.selectByExample(skuQuery);
		model.addAttribute("skus", skus);
		//颜色
		List<Color> colors = colorMapper.selectColorsByPId(id);
		model.addAttribute("colors", colors);
		
		return "product/productDetail";
	}
}
