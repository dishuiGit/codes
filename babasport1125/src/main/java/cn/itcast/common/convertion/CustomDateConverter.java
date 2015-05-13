package cn.itcast.common.convertion;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * 日期继承Converter接口    
 * @author lx
 *
 */
public class CustomDateConverter implements Converter<String, Date>{

	@Override
	public Date convert(String source) {
		//配置转换
		try {
			//判断是否为Null
			if(null != source){
				return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").parse(source);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
