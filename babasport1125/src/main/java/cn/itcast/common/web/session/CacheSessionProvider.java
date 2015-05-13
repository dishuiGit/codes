package cn.itcast.common.web.session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.Jedis;

/**
 * 远程Session
 * 
 * @author lx
 * 
 */
public class CacheSessionProvider implements SessionProvider {

	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;

	//
	private int exp = 30;

	// 往Session
	@SuppressWarnings("unchecked")
	@Override
	public void setAttribute(HttpServletRequest request,
			HttpServletResponse response, String name, Serializable value)
			throws Exception {
		Jedis jedis = jedisConnectionFactory.getShardInfo().createResource();
		// TODO Auto-generated method stub
		/*
		 * HttpSession session = request.getSession();
		 * session.setAttribute(name, value);
		 */
		// 在本地Session中存储值 1M 
		byte[] bs = jedis.get(getSessionId(request, response).getBytes());
		
		Map<String, Serializable> session = null;
		
		if(null != bs){
			// 转成品牌对象
			ByteArrayInputStream bais = new ByteArrayInputStream(bs);
			ObjectInputStream ois = new ObjectInputStream(bais);
			//从Redis中取出来的
			session = (Map<String, Serializable>) ois.readObject();
			//之前存储的  code_session  : code验证码
			 session.put(name, value);
			
		}else{
			session = new HashMap<String,Serializable>();
			//上前存储了  buyer_session  : 用户对象
			session.put(name, value);
		}
		// 二进制流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		
		// 写入流
		oos = new ObjectOutputStream(baos);
		oos.writeObject(session);
		// Redis 1M JSESSIONID Session 30分钟
		jedis.setex(getSessionId(request, response).getBytes(), 60 * exp,
				baos.toByteArray());


	}

	@Override
	@SuppressWarnings("unchecked")
	public Serializable getAttribute(HttpServletRequest request,
			HttpServletResponse response, String name) throws Exception {
		Jedis jedis = jedisConnectionFactory.getShardInfo().createResource();
		// TODO Auto-generated method stub
		// 在本地Session中存储值 1M 
		byte[] bs = jedis.get(getSessionId(request, response).getBytes());
		if(null != bs){
			// 转成品牌对象
			ByteArrayInputStream bais = new ByteArrayInputStream(bs);
			ObjectInputStream ois = new ObjectInputStream(bais);
			//从Redis中取出来的
			Map<String, Serializable> session = (Map<String, Serializable>) ois.readObject();
			//判断 是否为NUll
			if(null != session){
				//之前存储的  code_session  : code验证码
				return session.get(name);
			}
		}
		return null;
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//判断此Key在redis 当中有没有  getSessionId(request,response)
		Jedis jedis = jedisConnectionFactory.getShardInfo().createResource();
		if(jedis.exists(getSessionId(request,response))){
			//删除
			jedis.del(getSessionId(request,response));
		}

	}

	@Override
	public String getSessionId(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		return request.getSession().getId();
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

}
