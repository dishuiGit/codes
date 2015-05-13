package cn.dishui.common.convertion;

import org.springframework.core.convert.converter.Converter;
/**
 * 去掉前后空格
 * @author lx
 *
 */
public class CustomTrimConverter implements Converter<String, String> {

	@Override
	public String convert(String source) {
		try {
			if(null != source){
				// "   "
				source = source.trim();// ""
				if(source.equals("")){
					return null; 
				}else{
					return source;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

}
