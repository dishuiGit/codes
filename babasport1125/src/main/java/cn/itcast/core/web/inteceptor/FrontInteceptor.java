package cn.itcast.core.web.inteceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.common.web.session.SessionProvider;
import cn.itcast.core.bean.user.Buyer;
import cn.itcast.core.web.Constants;

/**
 * 拦截器
 * 上下文
 * 用户是否登陆的判断
 * @author lx
 *
 */
public class FrontInteceptor implements HandlerInterceptor{

	@Autowired
	private SessionProvider sessionProvider;
	//开发阶段
	private Integer adminId;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		if(null != adminId){
			Buyer b = new Buyer();
			b.setUsername("fbb2014");
			sessionProvider.setAttribute(request, response, Constants.BUYER_SESSION, b);
		}else{
			
			//判断用户是否登陆
			Buyer buyer = (Buyer) sessionProvider.getAttribute(request, response, Constants.BUYER_SESSION);
			if(null == buyer){
				//判断 是不是拦截的 请求 路径   /buyer/index.shtml
				//url 全
				//uri
				request.setAttribute("isLogin", false);
				String requestURI = request.getRequestURI();
				if(requestURI.startsWith("/buyer")){
					//登陆页面
					response.sendRedirect("/shopping/login.shtml?returnUrl=" + URLEncoder.encode(request.getParameter("returnUrl"),"UTF-8"));
					return false;
				}
			}else{
				request.setAttribute("isLogin", true);
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}


	

}
