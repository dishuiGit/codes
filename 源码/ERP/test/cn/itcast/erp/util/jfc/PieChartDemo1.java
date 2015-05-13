package cn.itcast.erp.util.jfc;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class PieChartDemo1 {
	public static void main(String[] paramArrayOfString) throws IOException {
		JFreeChart jfc = createChart();
		//获取JFreeChart，生成图片
		BufferedImage bi = jfc.createBufferedImage(500, 370);
		//做图片
		ImageIO.write(bi, "PNG", new File("1.png"));
		
	}
	
	private static JFreeChart createChart() {
		DefaultPieDataset dataSet = new DefaultPieDataset();
		dataSet.setValue("One", new Double(1D));
		dataSet.setValue("Two", new Double(2D));
		dataSet.setValue("Three", new Double(3D));
		
		JFreeChart jfc = ChartFactory.createPieChart("采购报表", dataSet, true, false, false);
		PiePlot plot = (PiePlot) jfc.getPlot();
		plot.setLabelFont(new Font("黑体", 1, 12));
		plot.setNoDataMessage("没有对应的报表数据");
		plot.setCircular(true);
		plot.setLabelGap(0.02D);
		return jfc;
	}

}










