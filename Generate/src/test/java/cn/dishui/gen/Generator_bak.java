package cn.dishui.gen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class Generator_bak {
	@Test
	public void generator() {
		String attrs[] = { 
				"id",
				"shop_name",
				"shop_title",
				"shop_logo",
				"user_id",
				"qq",
				"ww",
				"ym",
				"msn",
				"email",
				"phone",
				"mobile",
				"receive_account",
				"gmt_create",
				"gmt_modify",
				"address_id",
				"service_tel",
				"favicon_logo",
				"promation_show_status",
				"withdrawal_bank",
				"withdrawal_account",
				"category_id",
				"is_entity",
				"tax_register_number",
				"business_license",
				"entity_shop_name",
				"entity_shop_address",
				"goods_no_auditing",
				"back_goods_7day",
				"shop_style",
				"commission",
				"security_amount",
				"security_alert",
				"shop_desc",
				"shop_bulletin",
				"promation_desc",
				"is_recommend",
				"recom_modify",
				"withdrawal_name",
				"registered_capital",
				"rec_pay",
				"index_shop_logo",
				"shop_standard_logo",
				"favorite_count",
				"goods_count",
				"address_province",
				"address_city",
				"dept",
				"dept_sub",
				"withdrawal_province",
				"withdrawal_city",
				"withdrawal_desc",
				"is_customer",
				"agencies_code",
				"is_auction",
				"countryhouse_id",
				"cert_flag",
				"shop_show",
				"is_diy",
				"minisite",
				"is_invoice",
				"customer_code",
				"company_name",
				"company_addr",
				"legal_prson",
				"lp_card_type",
				"lp_card_no",
				"lp_card_img",
				"business_license_deadline",
				"business_license_img",
				"cus_declare_days",
				"business_region",
				"contancts",
				"agencies_imp",
				"sales_point",
				"vip_discount",
				"status",
				"priority",
				"bus_products",
				"max_img_space",
				"used_img_space",
				"is_deleted",
				"modifier",
				"creater",
				"shop_type",
				"brand_names",
				"sale_num",
				"video_url",
				"video_img",
				"page_dis_type",
				"is_webchat",
				"qr_code_url",
				"un_need_sku",
				"max_deposit",
				"agencies",
				"templet_link",
				"weight",};
		gen("mlq_shop_info", attrs);
	}

	@Test
	public void moveFiles() {
		move("mlq_shop_info");
	}

	public static void move(String table) {
		String src = "gen/" + table + "";
		String dest = "E:/mailiqing_3/b2b/src/com/bizoss/trade";
		// -----------------
		String xml_src = "gen/" + captureName(table) + ".xml";
		String xml_dest = "E:/mailiqing_3/b2b/src/com/bizoss/trade/sqlMaps";

		try {
			// 如果目录已经存在,删除
			File destF = new File(dest, table);
			if (destF.exists()) {
				FileUtils.deleteDirectory(destF);
			}
			File xml_destF = new File(xml_dest, captureName(table) + ".xml");
			if (xml_destF.exists()) {
				FileUtils.forceDelete(xml_destF);
			}
			// ---------------
			FileUtils.moveDirectoryToDirectory(new File(src), new File(dest),
					false);
			FileUtils.moveFileToDirectory(new File(xml_src),
					new File(xml_dest), false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void gen(String html_n, String[] attrs) {

		// html--> D:\1125\笔记\note\html
		// md--> D:\1125\笔记\note\md
		//
		String pack = "gen/" + html_n;
		String packJava = "gen/" + captureName(html_n) + ".java";
		String packJavaProp = "gen/" + captureName(html_n) + "_prop.java";
		String packInfoJava = "gen/" + captureName(html_n) + "Info.java";
		String packXML = "gen/" + captureName(html_n) + ".xml";
		String packBpm = "gen/bpm.xml";
		String packPropImpl = "gen/PropImpl.java";

		// 配置FK类
		Configuration conf = new Configuration();
		// 模板+数据 = 输出
		// 模板文件夹
		String ftl_folder = "ftl";
		Template tm_pojo = null;
		Template tm_pojo_prop = null;
		Template tm_xml = null;
		Template tm_info = null;
		Template tm_bpm = null;
		Template tm_propImpl = null;
		try {
			// 判断文件夹是否存在
			// 清空目录下的所有文件/文件夹
			FileUtils.cleanDirectory(new File("gen"));
			
			conf.setDirectoryForTemplateLoading(new File(ftl_folder));
			// 模板
			tm_pojo = conf.getTemplate("pojo.java", "utf-8");
			tm_pojo_prop = conf.getTemplate("pojoProp.java", "utf-8");
			tm_xml = conf.getTemplate("pojo.xml", "utf-8");
			tm_info = conf.getTemplate("pojoInfo.java", "utf-8");
			tm_bpm = conf.getTemplate("bpm.xml", "utf-8");
			tm_propImpl = conf.getTemplate("propImpl.java", "utf-8");
			// 数据
			List attr_list = new ArrayList();
			Map root = new HashMap();

			for (String attr : attrs) {
				attr_list.add(attr);
			}

			root.put("attr_list", attr_list);
			// 表名
			root.put("table", html_n);
			// 实例
			Writer w_pack = new FileWriter(new File(packJava));
			Writer w_pack_prop = new FileWriter(new File(packJavaProp));
			Writer w_xml = new FileWriter(new File(packXML));
			Writer w_packInfo = new FileWriter(new File(packInfoJava));
			Writer w_bpm = new FileWriter(new File(packBpm));
			Writer w_propImpl = new FileWriter(new File(packPropImpl));

			tm_pojo.process(root, w_pack);
			tm_pojo_prop.process(root, w_pack_prop);
			tm_xml.process(root, w_xml);
			tm_info.process(root, w_packInfo);
			tm_bpm.process(root, w_bpm);
			tm_propImpl.process(root, w_propImpl);

			w_pack.close();
			w_pack_prop.close();
			w_xml.close();
			w_packInfo.close();
			w_bpm.close();
			w_propImpl.close();
			
			FileUtils.forceMkdir(new File(pack));
			// 文件夹
			File packf = new File(pack);

			FileUtils.moveFileToDirectory(new File(packJava), packf, false);
			FileUtils.moveFileToDirectory(new File(packJavaProp), packf, false);
			FileUtils.moveFileToDirectory(new File(packPropImpl), packf, false);

			FileUtils.moveFileToDirectory(new File(packInfoJava), packf, false);
			// tm_bpm.process(root, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 首字母大写
	 */
	// 首字母大写
	public static String captureName(String name) {
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);

	}

}
