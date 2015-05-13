package cn.itcast.common.web.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Session提供类 接口
 * @author lx
 *
 */
public interface SessionProvider {

	//接口 先写
	//4个   
	//往Sesseion放值
	public void setAttribute(HttpServletRequest request,HttpServletResponse response,String name,Serializable value)throws Exception;
	
	//取值 
	public Serializable getAttribute(HttpServletRequest request,HttpServletResponse response,String name) throws Exception;
	
	//退出登陆 失效
	public void logout(HttpServletRequest request,HttpServletResponse response);
	//取Session的ID
	public String getSessionId(HttpServletRequest request,HttpServletResponse response);
	
}
