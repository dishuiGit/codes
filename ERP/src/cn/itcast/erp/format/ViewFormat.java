package cn.itcast.erp.format;

import java.text.DecimalFormat;

public class ViewFormat {

	public static String moneyFormat(Double money){
		
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(money);
	}
}
