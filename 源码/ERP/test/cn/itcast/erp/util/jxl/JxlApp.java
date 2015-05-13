package cn.itcast.erp.util.jxl;

import java.io.File;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class JxlApp {
	public static void main(String[] args) throws Exception {
		/*
		//读Excel文件
		Workbook wb = Workbook.getWorkbook(new File("1.xls")); 
		Sheet s = wb.getSheet(0); 
		Cell a1 = s.getCell(0,0);
		String ss = a1.getContents(); 
		System.out.println(ss);
		*/
		//写Excel
		
		WritableWorkbook workbook = Workbook.createWorkbook(new File("2.xls")); 
		
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
		for (; i < 10; i++) {
			JxlUtil.sRowSize(s, row+i, 19);
			
			Label lab_data_1 = JxlUtil.cLabel(row+i, 2, i+1+"");
			JxlUtil.sLabelStyle(lab_data_1, "宋体", 14, Colour.BLACK, Colour.WHITE, 1, "0120");
			JxlUtil.aLabelToSheet(lab_data_1, s);
			
			Label lab_data_2 = JxlUtil.cLabel(row+i, 3, "厂商"+i);
			JxlUtil.sLabelStyle(lab_data_2, "宋体", 14, Colour.BLACK, Colour.WHITE, 1, "0110");
			JxlUtil.aLabelToSheet(lab_data_2, s);
			
			Label lab_data_3 = JxlUtil.cLabel(row+i, 4, "商品"+i);
			JxlUtil.sLabelStyle(lab_data_3, "宋体", 14, Colour.BLACK, Colour.WHITE, 1, "0110");
			JxlUtil.aLabelToSheet(lab_data_3, s);
			
			Label lab_data_4 = JxlUtil.cLabel(row+i, 5, i*i+"");
			JxlUtil.sLabelStyle(lab_data_4, "宋体", 14, Colour.BLACK, Colour.WHITE, 1, "0112");
			JxlUtil.aLabelToSheet(lab_data_4, s);
			
		}
		
		JxlUtil.sMerge(s, row+i , 2 , row+i, 4);
		
		Label lab_tail_2 = JxlUtil.cLabel(row+i, 2, "总计：");
		JxlUtil.sLabelStyle(lab_tail_2, "黑体", 18, Colour.BLACK, Colour.WHITE, 1, "2220");
		JxlUtil.aLabelToSheet(lab_tail_2, s);
		
		Label lab_tail_5 = JxlUtil.cLabel(row+i, 5, "1999");
		JxlUtil.sLabelStyle(lab_tail_5, "黑体", 18, Colour.BLACK, Colour.WHITE, 1, "2222");
		JxlUtil.aLabelToSheet(lab_tail_5, s);
		
		workbook.write();
		workbook.close(); 
	}
}





