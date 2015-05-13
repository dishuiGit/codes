package cn.itcast.erp.invoice.bill.web;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.itcast.erp.invoice.bill.business.ebi.BillEbi;
import cn.itcast.erp.invoice.bill.vo.BillQueryModel;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailModel;
import cn.itcast.erp.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;
import cn.itcast.erp.util.base.BaseAction;

public class BillAction extends BaseAction{
	public BillQueryModel bqm = new BillQueryModel();

	private BillEbi billEbi;
	private SupplierEbi supplierEbi;
	public void setSupplierEbi(SupplierEbi supplierEbi) {
		this.supplierEbi = supplierEbi;
	}

	public void setBillEbi(BillEbi billEbi) {
		this.billEbi = billEbi;
	}

	public String buyBill(){
		//加载所有供应商数据集合
		List<SupplierModel> supplierList = supplierEbi.getAll();
		put("supplierList",supplierList);
		//查询的数据
		//select ??,??,?? from ->result  Object[]
		//select name from ... ->result  String
		//select age from ... ->result  Integer
		//select name,age from ... ->result Object[]
		/*
		SELECT
			g.uuid,
			g.name,
			SUM(od.num)
		FROM
			tbl_orderDetail od,
			tbl_goods g
		WHERE
			g.uuid = od.goodsUuid
		GROUP BY
			g.uuid
		*/
		List<Object[]> billList = billEbi.getBuyBill(bqm);
		put("billList",billList);
		return "buyBill";
	}
	/*
	public String getPieBill() throws Exception{
		//读取一个文件，将内容写入到流中
		File f = new File("1.png");
		System.out.println(f.getAbsolutePath());
		FileInputStream fis = new FileInputStream(f);
		byte[] buf = new byte[1024];
		
		HttpServletResponse response = getResponse();
		ServletOutputStream os = response.getOutputStream();
		int len = -1;
		do{
			len = fis.read(buf);
			if(len != -1){
				//将读取的内容写入到响应中
				os.write(buf, 0, len);
			}
		}while(len != -1);
		
		os.flush();
		return null;
	}
	*/
	public void getPieBill() throws Exception{
		//读取饼状报表图片，然后写入到流中
		//更改：将流传递到业务层，然后在业务层中将饼状报表图片写入流中
		billEbi.writePieToStream(getResponse().getOutputStream(),bqm);
	}
	
	private InputStream downloadExcelStream;
	public InputStream getDownloadExcelStream() {
		return downloadExcelStream;
	}

	public String getXlsName() throws UnsupportedEncodingException {
		return new String("采购报表.xls".getBytes("utf8"),"iso8859-1");
	}

	//下载 EXCEL报表
	public String downloadExcelBill() throws Exception {
		//将Excel文件写入到downloadExcelStream
		downloadExcelStream = billEbi.writeExcelToStream(bqm);
		return "downloadExcelBill";
	}
	
	/*
	*********************************************
	*											*
	*				AJAX获取开始：					*
	*											*
	*********************************************
	*/
	
	private List<OrderDetailModel> odmList;
	public List<OrderDetailModel> getOdmList() {
		return odmList;
	}

	public String ajaxGetInfo(){
		odmList = billEbi.getBillDetail(bqm);
		return "ajaxGetInfo";
	}
	
	
	
}













