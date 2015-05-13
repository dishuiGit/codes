package cn.itcast.erp.invoice.bill.business.ebi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import cn.itcast.erp.invoice.bill.vo.BillQueryModel;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailModel;

public interface BillEbi{

	public List<Object[]> getBuyBill(BillQueryModel bqm);

	public List<OrderDetailModel> getBillDetail(BillQueryModel bqm);

	public void writePieToStream(OutputStream os,BillQueryModel bqm)throws IOException;

	public InputStream writeExcelToStream(BillQueryModel bqm) throws Exception ;

}