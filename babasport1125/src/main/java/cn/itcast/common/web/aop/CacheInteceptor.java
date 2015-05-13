package cn.itcast.common.web.aop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.Jedis;

/**
 * 切面对象
 * 查询时判断Redis当中是否有数据
 * 变更时要同步数据
 * 
 * @author lx
 *
 */
public class CacheInteceptor {
	
	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;

	//查询切面  环绕切面
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable{
		//判断 Redis当中是否有缓存数据
		Jedis jedis = jedisConnectionFactory.getShardInfo().createResource();
		//什么样Key数据  
		//有Key 是怎么生成的?  方式Key唯一性  Brand brand 金乐乐
		// key :  cn.itcast.core.service.BrandServiceImpl.selectBrandById + (Integer id)
		   //     cn.itcast.core.dao.BrandServiceImpl.selectBrandById+ (Integer id)
		//List  分页对象   Brand对象
		//从Jedis中取值  要求 Key生成的策略  包名+类名 + 方法 名  + 参数对象的转成JSON串
		String cacheKey = getCacheKey(pjp);
		
		byte[] bs = jedis.get(cacheKey.getBytes());
		if(null == bs){
			//从Service中取  返回Service  分页对象  List  Brand
			 Object proceed = pjp.proceed();
			 //放到Redis中一份
			//二进制流
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			//写入流
			oos.writeObject(proceed);
			//二进制流
			jedis.set(cacheKey.getBytes(), baos.toByteArray());
			//
			bs = jedis.get(cacheKey.getBytes());
		}
		//返回Controller层
		//转成品牌对象
		ByteArrayInputStream bais = new ByteArrayInputStream(bs);
		ObjectInputStream ois = new ObjectInputStream(bais);
		return ois.readObject();
	}
	
	//方法后
	public void doAfter(JoinPoint jp){
		//1:jp  cn.itcast.core.service.BrandServiceImpl.add*(Brand brand)
		//1:jp  cn.itcast.core.service.BrandServiceImpl.update*(Brand brand)
		//1:jp  cn.itcast.core.service.BrandServiceImpl.delete*(Brand brand)
		
		//1:jp  cn.itcast.core.service.BrandServiceImpl.select*(BrandQuery brand)  清理
		//jedis cn.itcast.core.service.BrandServiceImpl为开始的Key键
		//1:获取当前访问的包名+类名
		String packageAndClassName = jp.getTarget().getClass().getName();
		//2:去redis 服务器 查包名+类名开始的 所有的Key 
		//判断 Redis当中是否有缓存数据
		Jedis jedis = jedisConnectionFactory.getShardInfo().createResource();
		//所有符合的KeyS
		Set<String> keys = jedis.keys(packageAndClassName + "*");
		for (String key : keys) {
			//删除此Key及Value
			jedis.del(key);
		}
		
		
	}
	//生成Key
	public String getCacheKey(ProceedingJoinPoint pjp){
		
		StringBuilder cacheKey = new StringBuilder();
		//包类+类名 cn.itcast.core.dao.BrandServiceImpl   .selectBrandById
		String packageAndClassName = pjp.getTarget().getClass().getName();
		cacheKey.append(packageAndClassName);
		//方法名
		String methodName = pjp.getSignature().getName();
		cacheKey.append(".").append(methodName);
		//参数[]  参数对象的转成JSON串
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Inclusion.NON_NULL);
		
		Object[] args = pjp.getArgs();
		for (Object object : args) {
			StringWriter str = new StringWriter();
			try {
				om.writeValue(str, object);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cacheKey.append(".").append(str.toString());
		}
		//生成的Key
		return cacheKey.toString();
		
	}
}
