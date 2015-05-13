package cn.itcast.core.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.itcast.common.web.session.SessionProvider;
import cn.itcast.core.bean.country.City;
import cn.itcast.core.bean.country.CityQuery;
import cn.itcast.core.bean.country.Province;
import cn.itcast.core.bean.country.Town;
import cn.itcast.core.bean.country.TownQuery;
import cn.itcast.core.bean.user.Addr;
import cn.itcast.core.bean.user.Buyer;
import cn.itcast.core.service.country.CityService;
import cn.itcast.core.service.country.ProvinceService;
import cn.itcast.core.service.country.TownService;
import cn.itcast.core.service.user.AddrService;
import cn.itcast.core.service.user.BuyerService;
import cn.itcast.core.web.Constants;

/**
 * 登陆
 * 
 * @author lx
 * 
 */
@Controller
public class LoginController {

	// GET ${param.returnUrl}
	@RequestMapping(value = "/shopping/login.shtml", method = RequestMethod.GET)
	public String login() {
		return "buyer/login";
	}

	@Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private BuyerService buyerService;

	// POST
	@RequestMapping(value = "/shopping/login.shtml", method = RequestMethod.POST)
	public String login(Buyer buyer, String code, String returnUrl,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 1:判断 验证码 不能为空
		if (null != code) {
			// 2:判断验证码 必须正确
			String c = (String) sessionProvider.getAttribute(request, response,
					Constants.CODE_SESSION);
			if (code.equalsIgnoreCase(c)) {
				// 3:判断用户不能空
				if (null != buyer && null != buyer.getUsername()) {
					// 4:密码不能空
					if (null != buyer.getPassword()) {
						// 5:用户必须正确
						Buyer b = buyerService.selectBuyerByKey(buyer
								.getUsername());
						if (null != b) {
							// 6:密码必须正确
							if (encodePassword(buyer.getPassword()).equals(
									b.getPassword())) {
								// 7:把用户放进Session中
								sessionProvider.setAttribute(request, response,
										Constants.BUYER_SESSION, b);
								// 8:判断 返回路径是否为空
								if (null != returnUrl) {
									// 2)不为空 跳转到用户之前访问的页面
									try {
										//
										// http://localhost:8080/product/display/list.shtml?keyword=瑜伽服&brandId=1&brandName=依
										URL url = new URL(returnUrl);
										// 获取returnUrl 的条件参数
										// keyword=瑜伽服&brandId=1&brandName=依
										// keyword=%E7%91%9C%E4%BC%BD%E6%9C%8D&price=200-299&brandId=1&brandName=%E4%BE%9D%E7%90%A6%E8%8E%B2
										String query = url.getQuery();
										// & 4
										String[] params = query.split("&");
										for (int i = 0; i < params.length; i++) {
											String[] kv = params[i].split("=");

											model.addAttribute(kv[0],
													URLDecoder.decode(kv[1],
															"UTF-8"));
										} // /product/display/list.shtml
										return "redirect:" + url.getPath();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								// 1)是空 要求路径 会员中心
								return "redirect:/buyer/index.shtml";

							} else {
								model.addAttribute("error", "密码必须正确");
								return "buyer/login";
							}

						} else {
							model.addAttribute("error", "用户必须正确");
							return "buyer/login";
						}

					} else {
						model.addAttribute("error", "密码不能空");
						return "buyer/login";
					}
				} else {
					model.addAttribute("error", "用户不能空");
					return "buyer/login";
				}

			} else {
				model.addAttribute("error", "验证码必须正确");
				return "buyer/login";
			}
		} else {
			model.addAttribute("error", "验证码不能为空");
			return "buyer/login";
		}
	}

	// 会员中心
	@RequestMapping(value = "/buyer/index.shtml")
	public String index() {

		return "buyer/index";
	}

	// 验证码生成
	@RequestMapping(value = "/shopping/getCodeImage.shtml")
	public void getCodeImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out
				.println("#######################生成数字和字母的验证码#######################");
		BufferedImage img = new BufferedImage(68, 22,
				BufferedImage.TYPE_INT_RGB);
		// 得到该图片的绘图对象
		Graphics g = img.getGraphics();

		Random r = new Random();

		Color c = new Color(200, 150, 255);

		g.setColor(c);

		// 填充整个图片的颜色

		g.fillRect(0, 0, 68, 22);

		// 向图片中输出数字和字母

		StringBuffer sb = new StringBuffer();

		char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

		int index, len = ch.length;

		for (int i = 0; i < 4; i++) {

			index = r.nextInt(len);

			g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt

			(255)));

			g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
			// 输出的 字体和大小

			g.drawString("" + ch[index], (i * 15) + 3, 18);
			// 写什么数字，在图片 的什么位置画

			sb.append(ch[index]);

		}
		sessionProvider.setAttribute(request, response, Constants.CODE_SESSION,
				sb.toString());
		try {
			ImageIO.write(img, "JPG", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Md5加密 ERP 电商 纯 Md5 非常被破解 Hex进行加密 加盐
	public String encodePassword(String password) {
		String algorithm = "MD5";
		// 要求 密码不要太有规则
		// password qwsdgsfdsdfsayuio
		// password = "qwsd" + password + "yuio";
		// Md5 JDK
		MessageDigest instance = null;
		try {
			instance = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 加密
		// 二进制
		byte[] digest = instance.digest(password.getBytes());
		// 十六进制
		char[] encodeHex = Hex.encodeHex(digest);
		// 密文 MD5 十六进制
		return new String(encodeHex);
	}

	public static void main(String[] args) {
		LoginController l = new LoginController();
		String encodePassword = l.encodePassword("123456");
		System.out.println(encodePassword);
	}

	//
	// 去个人资料
	// 验证码生成
	@RequestMapping(value = "/buyer/toProfile.shtml")
	public String toProfile(HttpServletRequest request,
			HttpServletResponse response,Model model) throws Exception {
		Buyer buyer = (Buyer) sessionProvider.getAttribute(request, response, Constants.BUYER_SESSION);
		//加载用户
		Buyer b = buyerService.selectBuyerByKey(buyer.getUsername());
		model.addAttribute("buyer", b);
		//加载省  市   县
		//省
		List<Province> provinces = provinceService.selectProvinces(null);
		model.addAttribute("provinces", provinces);
		//市 
		CityQuery cityQuery = new CityQuery();
		cityQuery.createCriteria().andProvinceEqualTo(b.getProvince());
		
		List<City> citys = cityService.selectProvinces(cityQuery);
		
		model.addAttribute("citys", citys);
		TownQuery townQuery = new TownQuery();
		townQuery.createCriteria().andCityEqualTo(b.getCity());
		List<Town> towns = townService.selectTowns(townQuery);
		model.addAttribute("towns", towns);
		
		return "buyer/profile";
		
	}
	@RequestMapping(value = "/buyer/deliver_address.shtml")
	public String deliverAddress(HttpServletRequest request,
			HttpServletResponse response,Model model) throws Exception{
		//
		Buyer buyer = (Buyer) sessionProvider.getAttribute(request, response, Constants.BUYER_SESSION);
		
		List<Addr> addrs = addrService.selectAddrsByUserName(buyer);
		model.addAttribute("addrs", addrs);
		return "buyer/deliver_address";
	}
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private TownService townService;
	@Autowired
	private AddrService addrService;
	

}
