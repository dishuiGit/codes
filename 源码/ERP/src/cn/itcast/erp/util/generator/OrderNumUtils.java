package cn.itcast.erp.util.generator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNumUtils {
	private static Long uuidSer = 1L;
	private static final Integer LEN = 8;
	public static final String getOrderNum(){
		DateFormat df = new SimpleDateFormat("yyMMdd");
		String first = df.format(new Date());
		Long uuid = uuidSer++;
		for(int i = 0;i< 8 - uuid.toString().length() ;i++){
			first+="0";
		}
		Long all = new Long(first+uuid);
		return Long.toHexString(all).toUpperCase();
	}
}
