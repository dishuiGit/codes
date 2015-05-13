package cn.itcast.common.web.session;

import java.io.Serializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpSessionProvider implements SessionProvider{

	@Override
	public void setAttribute(HttpServletRequest request,
			HttpServletResponse response, String name, Serializable value) {
		// TODO Auto-generated method stub
		request.getSession().setAttribute(name, value);
	}

	@Override
	public Serializable getAttribute(HttpServletRequest request,
			HttpServletResponse response, String name) {
		// TODO Auto-generated method stub
		//表示: 从request 从当前用户Session对象  如果此Session对象  就创建一个新的Session返回
		//返回原来的Session
		HttpSession session = request.getSession(false);
		if(null != session){
			return (Serializable) session.getAttribute(name);
		}
		return null;
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(null != session){
			session.invalidate();
		}
		//同时要求 将Cookie中的jsessionid 清空
/*		Cookie cookie = new Cookie("Constants.usersession",null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);*/
	}

	@Override
	public String getSessionId(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		//request.getRequestedSessionId()
		return request.getSession().getId();
	}

}
