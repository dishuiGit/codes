package cn.itcast.erp.format;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateViewFormat {
	
	public static String viewFormat(Long time){
		Date date = new Date(time);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		return dateFormat.format(date);
	}
}
