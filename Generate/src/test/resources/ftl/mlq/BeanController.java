package com.bizoss.trade.${table}.controler;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bizoss.frame.util.DateUtil;
import com.bizoss.frame.util.ObjUtils;
import com.bizoss.frame.util.RandomID;
import com.bizoss.frame.util.page.Pagination;
import com.bizoss.trade.${table}.bean.${table?cap_first};
import com.bizoss.trade.${table}.service.${table?cap_first}ServiceImpl;
import com.bizoss.trade.${table}.bean.${table?cap_first}Query;

@Controller
@RequestMapping("/${table_alias}")
public class ${table?cap_first}Controller {
	private static Logger logger = Logger.getLogger(${table?cap_first}Controller.class);
	
	@Resource
	private ${table?cap_first}ServiceImpl ${table}ServiceImpl;
	@Resource
	private RandomID randomID;
	@RequestMapping("/test")
	public String test(${table?cap_first} ${table}){
		logger.info("test");
		return "test";
	}
	/**
	 * 列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model,${table?cap_first}Query ${table_alias}q,Integer pageNo){
		Pagination pagination = ${table}ServiceImpl.get${table?cap_first}Pagination(${table_alias}q, pageNo, 10, "/${table_alias}/index.do");
		model.addAttribute("pagination", pagination);
		return "${table}/index";
	}
	
	@RequestMapping("/toModify")
	public String toModify(Model model,String id){
		${table?cap_first} ${table_alias} = ${table}ServiceImpl.get(id);
		model.addAttribute("${table_alias}",${table_alias});
		return "${table}/edit";
	}
	
	/**
	 * 修改
	 * @param mlq_goods_filter
	 * @return
	 */
	@RequestMapping("/modify")
	public String modify(${table?cap_first} ${table_alias}){
		
		if(ObjUtils.notEmpty(${table_alias}.getId())){
			${table_alias}.setGmt_modify(DateUtil.getDateTime());
			${table}ServiceImpl.update(${table_alias});
		}else{
			${table_alias}.setId(randomID.GenTradeId());
			${table_alias}.setGmt_create(DateUtil.getDateTime());
			${table_alias}.setGmt_modify(DateUtil.getDateTime());
			${table}ServiceImpl.save(${table_alias});
		}
		
		return "redirect:index.do";
	}
}
