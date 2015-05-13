package cn.itcast.core.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Brand;
import cn.itcast.core.bean.product.BrandQuery;
import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.ProductQuery;
import cn.itcast.core.bean.product.ProductWithBLOBs;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.service.product.BrandService;
import cn.itcast.core.service.product.ProductService;
import cn.itcast.core.service.product.SkuService;

/**
 * 商品控制层
 * 商品列表页面
 * 商品详情页面
 * @author lx
 *
 */
@Controller
public class FrontProductController {

	@Autowired
	private SolrServer solrServer;
	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	//测试
	@RequestMapping("/product/display/list.shtml")
	public String list(String keyword,Integer pageNo,Integer brandId,String brandName,String price,Model model){
		//加载品牌
		BrandQuery brandQuery = new BrandQuery();
		//指定字段 id name
		brandQuery.setFields("id,name");
		//可见
		brandQuery.setIsDisplay(1);
		//加载页面所需要的品牌  是可见的
		List<Brand> brands = brandService.selectBrandList(brandQuery);
		model.addAttribute("brands", brands);
		
		//已选条件显隐
		boolean flag = false;
		
		//商品对象Query
		ProductQuery productQuery = new ProductQuery();
		//条件参数  http://localhost:8080/product/display/list.shtml?keyword='瑜伽服'&brandId=8&price=....&pageNo=2
		StringBuilder p = new StringBuilder();
		//页号
		productQuery.setPageNo(Pagination.cpn(pageNo));
		//每页数
		productQuery.setPageSize(8);
		
		//去Solr服务器查询数据
		//查询对象
		SolrQuery params = new SolrQuery();
		//设置通过名称
		params.set("q", "name_ik:" + keyword);
		p.append("keyword=").append(keyword);
		
		//Map 已选条件  
		Map<String,String> keys = new HashMap<String,String>();
		//设置过滤条件
		if(null != brandId){
			///过滤品牌ID
			params.addFilterQuery("brandId:" + brandId);
			p.append("&brandId=").append(brandId);
			model.addAttribute("brandId", brandId);
			model.addAttribute("brandName", brandName);
			//更改此标记
			flag = true;
			//
			keys.put("品牌", brandName);
		}
		//价格
		if(null != price){
			///过滤价格  0-79  float类型   600
			String[] price0 = price.split("-");
			if(price0.length == 1){
				//最小600以上
				float pStart = new Float(price0[0]);
				float pEnd = new Float(99999999);
				params.addFilterQuery("price:[" + pStart + " TO " + pEnd + "]");
				keys.put("价格", price + "以上");
			}else if(price0.length == 2){
				//区间
				float pStart = new Float(price0[0]);
				float pEnd = new Float(price0[1]);
				params.addFilterQuery("price:[" + pStart + " TO " + pEnd + "]");
				keys.put("价格", price);
			}
			p.append("&price=").append(price);
			model.addAttribute("price", price);
			//更改此标记
			flag = true;
			
		}
		
		//标记回显
		model.addAttribute("keys", keys);
		model.addAttribute("flag", flag);
		//设置从哪行开始查询数据
		params.setStart(productQuery.getStartRow());
		//设置每页数
		params.setRows(productQuery.getPageSize());
		//按钮ID倒排
		params.addSort("id", ORDER.desc);
		
		
		//返回分页对象
		Pagination pagination = productService.selectPByKeyword(params,p,productQuery.getPageNo());
		//分页的页面展示
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageNo", pageNo);
		
		return "product/product";
	}
	//商品详情页面
	@RequestMapping(value = "/product/detail.shtml")
	public String detail(Integer id , Model model){
		//加载的商品信息  名   介绍  清单
		ProductWithBLOBs product = productService.selectProductWithBLOBsById(id);
		
		model.addAttribute("product", product);
		//加载SKu集合  要求有库存
		List<Sku> skus = skuService.selectSkusByProductId(id);
		model.addAttribute("skus", skus);
		//去重  Set  Color对象
		Set<Color> colors = new HashSet<Color>();
		for (Sku sku : skus) {
			colors.add(sku.getColor());
		}
		model.addAttribute("colors", colors);
		
		return "product/productDetail";
	}
	@Autowired
	private SkuService skuService;
}
