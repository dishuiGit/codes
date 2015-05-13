package cn.itcast.erp.util.quartz;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import cn.itcast.erp.invoice.goods.business.ebi.GoodsEbi;
import cn.itcast.erp.util.format.FormatUtils;

public class TimerTask {
	private GoodsEbi goodsEbi;
	private MailSender mailSender;
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setGoodsEbi(GoodsEbi goodsEbi) {
		this.goodsEbi = goodsEbi;
	}

	public void goodsUseNumUpdate(){
		/*
		UPDATE
			tbl_goods g
		SET
			useNum = 
		(
		SELECT
			COUNT(*)
		FROM
			tbl_orderdetail
		WHERE
			goodsUuid = g.uuid
		)
		*/
		goodsEbi.updateGoodsUseNum();
	}
	
	public void storeWarn(){
		//1.获取预警数据
		//使用库存数量与一个值进行比对
		//设计预警值：最大值，最小值	OK
		//查询
		/*
		SELECT
			g.name , 
			g.maxNum<SUM(num),
			g.minNum>SUM(num)
		FROM
			tbl_storeDetail od,
			tbl_goods g
		WHERE 
			g.uuid = od.goodsUuid
		GROUP BY
			g.uuid
		*/
		/*
		List<Object[]> warnList = goodsEbi.getWarnInfo();
		for(Object[] objs:warnList){
			System.out.println(objs[0]);
			System.out.println(objs[1]);
			System.out.println(objs[2]);
			System.out.println("================");
		}
		*/
		//2.根据预警数据进行消息发送（email）
		//发送邮件
		
		SimpleMailMessage smm = new SimpleMailMessage();
		//设置发送方
		smm.setFrom("itcast0228@126.com");
		//设置接受方
		smm.setTo("hehe@126.com");
		//设置主题
		smm.setSubject("库存预警【"+FormatUtils.formatDateTime(System.currentTimeMillis())+"】");
		//设置内容
		StringBuilder msg = new StringBuilder();
		List<Object[]> warnList = goodsEbi.getWarnInfo();
		for(Object[] objs:warnList){
			BigInteger maxFlag = (BigInteger) objs[1];
			if(maxFlag.intValue() == 1){
				String name = objs[0].toString();
				msg.append("商品[");
				msg.append(name);
				msg.append("]超过最大值，请停止补货！\r\n");
				continue;
			}
			BigInteger minFlag = (BigInteger) objs[2];
			if(minFlag.intValue() == 1){
				String name = objs[0].toString();
				msg.append("商品[");
				msg.append(name);
				msg.append("]低于最小值，请开始补货！\r\n");
				continue;
			}
		}
		smm.setText(msg.toString());
		//设置发送时间
		smm.setSentDate(new Date());
		mailSender.send(smm);
	}
	
}
