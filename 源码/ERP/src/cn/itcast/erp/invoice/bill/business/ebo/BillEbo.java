package cn.itcast.erp.invoice.bill.business.ebo;

import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import cn.itcast.erp.invoice.bill.business.ebi.BillEbi;
import cn.itcast.erp.invoice.bill.dao.dao.BillDao;
import cn.itcast.erp.invoice.bill.vo.BillQueryModel;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailModel;
import cn.itcast.erp.util.jxl.JxlUtil;

public class BillEbo implements BillEbi{
	static {
		StandardChartTheme theme = new StandardChartTheme("unicode") {
			public void apply(JFreeChart chart) {
				chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				super.apply(chart);
			}
		};
		theme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 20));
		theme.setLargeFont(new Font("宋体", Font.PLAIN, 14));
		theme.setRegularFont(new Font("宋体", Font.PLAIN, 12));
		theme.setSmallFont(new Font("宋体", Font.PLAIN, 10));
		ChartFactory.setChartTheme(theme);
	}
	private BillDao billDao;
	public void setBillDao(BillDao billDao) {
		this.billDao = billDao;
	}
	public List<Object[]> getBuyBill(BillQueryModel bqm) {
		return billDao.getBuyBill(bqm);
	}
	public List<OrderDetailModel> getBillDetail(BillQueryModel bqm) {
		return billDao.getBillDetail(bqm);
	}
	public void writePieToStream(OutputStream os,BillQueryModel bqm) throws IOException {
		//将图片内容写入流中
		DefaultPieDataset dataSet = new DefaultPieDataset();
		List<Object[]> temp = billDao.getBuyBill(bqm);
		for(Object[] objs:temp){
			GoodsModel gm = (GoodsModel) objs[0];
			Long sum = (Long) objs[1];
			dataSet.setValue(gm.getName(), new Double(sum));
		}
		
		JFreeChart jfc = ChartFactory.createPieChart("采购报表", dataSet, true, false, false);
		PiePlot plot = (PiePlot) jfc.getPlot();
		plot.setLabelFont(new Font("黑体", 1, 12));
		plot.setNoDataMessage("没有对应的报表数据");
		plot.setCircular(true);
		plot.setLabelGap(0.02D);
		
		BufferedImage bi = jfc.createBufferedImage(500, 370);
		
		ImageIO.write(bi, "PNG", os);
	}
	
	public InputStream writeExcelToStream(BillQueryModel bqm) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		//创建Excel文件，然后写入到流中，返回流
		WritableWorkbook workbook = Workbook.createWorkbook(bos); 
		
		WritableSheet s = workbook.createSheet("总括", 0); 
		
		//设置宽度高度
		JxlUtil.sColumnSize(s, 1, 8);
		JxlUtil.sColumnSize(s, 2, 8);
		JxlUtil.sColumnSize(s, 3, 25);
		JxlUtil.sColumnSize(s, 4, 25);
		JxlUtil.sColumnSize(s, 5, 25);
		
		JxlUtil.sRowSize(s, 1, 15);
		JxlUtil.sRowSize(s, 2, 37);
		JxlUtil.sRowSize(s, 3, 6);
		JxlUtil.sRowSize(s, 4, 23);
		
		//合并单元格
		JxlUtil.sMerge(s, 2 , 2 , 2, 4);
		JxlUtil.sMerge(s, 3,2,3,5);
		
		//单元格
		Label lab_22 = JxlUtil.cLabel(2, 2, "进货统计报表");
		JxlUtil.sLabelStyle(lab_22, "黑体", 24, Colour.BLACK, Colour.LIGHT_BLUE, 1, "2020");
		JxlUtil.aLabelToSheet(lab_22, s);
		
		Label lab_25 = JxlUtil.cLabel(2, 5, "不限");
		JxlUtil.sLabelStyle(lab_25, "黑体", 12, Colour.BLACK, Colour.LIGHT_BLUE, 1, "2002");
		JxlUtil.aLabelToSheet(lab_25, s);
		
		Label lab_32 = JxlUtil.cLabel(3, 2, "");
		JxlUtil.sLabelStyle(lab_32, "黑体", 1, Colour.BLACK, Colour.GRAY_25, 1, "2022");
		JxlUtil.aLabelToSheet(lab_32, s);
		
		Label lab_42 = JxlUtil.cLabel(4, 2, "编号");
		JxlUtil.sLabelStyle(lab_42, "黑体", 18, Colour.BLACK, Colour.WHITE, 1, "2220");
		JxlUtil.aLabelToSheet(lab_42, s);
		
		Label lab_43 = JxlUtil.cLabel(4, 3, "厂商");
		JxlUtil.sLabelStyle(lab_43, "黑体", 18, Colour.BLACK, Colour.WHITE, 1, "2220");
		JxlUtil.aLabelToSheet(lab_43, s);
		
		Label lab_44 = JxlUtil.cLabel(4, 4, "商品名");
		JxlUtil.sLabelStyle(lab_44, "黑体", 18, Colour.BLACK, Colour.WHITE, 1, "2220");
		JxlUtil.aLabelToSheet(lab_44, s);
		
		Label lab_45 = JxlUtil.cLabel(4, 5, "数量");
		JxlUtil.sLabelStyle(lab_45, "黑体", 18, Colour.BLACK, Colour.WHITE, 1, "2222");
		JxlUtil.aLabelToSheet(lab_45, s);
		
		int row = 5;
		int i = 0;
		Long all = 0L;
		List<Object[]> temp = billDao.getBuyBill(bqm);
		for(Object[] objs:temp){
			GoodsModel gm = (GoodsModel) objs[0];
			Long sum = (Long) objs[1];
			
			JxlUtil.sRowSize(s, row+i, 19);
			
			Label lab_data_1 = JxlUtil.cLabel(row+i, 2, i+1+"");
			JxlUtil.sLabelStyle(lab_data_1, "宋体", 14, Colour.BLACK, Colour.WHITE, 1, "0120");
			JxlUtil.aLabelToSheet(lab_data_1, s);
			
			Label lab_data_2 = JxlUtil.cLabel(row+i, 3, gm.getGtm().getSm().getName());
			JxlUtil.sLabelStyle(lab_data_2, "宋体", 14, Colour.BLACK, Colour.WHITE, 1, "0110");
			JxlUtil.aLabelToSheet(lab_data_2, s);
			
			Label lab_data_3 = JxlUtil.cLabel(row+i, 4, gm.getName());
			JxlUtil.sLabelStyle(lab_data_3, "宋体", 14, Colour.BLACK, Colour.WHITE, 1, "0110");
			JxlUtil.aLabelToSheet(lab_data_3, s);
			
			Label lab_data_4 = JxlUtil.cLabel(row+i, 5, sum.toString());
			JxlUtil.sLabelStyle(lab_data_4, "宋体", 14, Colour.BLACK, Colour.WHITE, 1, "0112");
			JxlUtil.aLabelToSheet(lab_data_4, s);
			
			i++;
			all+=sum;
		}
		
		JxlUtil.sMerge(s, row+i , 2 , row+i, 4);
		
		Label lab_tail_2 = JxlUtil.cLabel(row+i, 2, "总计：");
		JxlUtil.sLabelStyle(lab_tail_2, "黑体", 18, Colour.BLACK, Colour.WHITE, 1, "2220");
		JxlUtil.aLabelToSheet(lab_tail_2, s);
		
		Label lab_tail_5 = JxlUtil.cLabel(row+i, 5, all.toString());
		JxlUtil.sLabelStyle(lab_tail_5, "黑体", 18, Colour.BLACK, Colour.WHITE, 1, "2222");
		JxlUtil.aLabelToSheet(lab_tail_5, s);
		
		workbook.write();
		workbook.close(); 
		
		//当前Excel文件被写入了一个输出流对象中，但是目标是将Excel文件写入到输入流中
		//将输出流的数据写入到输入流中
		//数据在bos中
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		return bis;
	}

}