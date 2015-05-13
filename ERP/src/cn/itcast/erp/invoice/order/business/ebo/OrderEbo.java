package cn.itcast.erp.invoice.order.business.ebo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.auth.emp.business.ebi.EmpEbi;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.invoice.goods.business.ebi.GoodsEbi;
import cn.itcast.erp.invoice.order.business.ebi.OrderEbi;
import cn.itcast.erp.invoice.order.dao.dao.OrderDao;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.order.vo.OrderQueryModel;
import cn.itcast.erp.invoice.orderDetail.vo.OrderDetialModel;
import cn.itcast.erp.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;
import cn.itcast.erp.utils.generator.OrderNumUtils;

public class OrderEbo implements OrderEbi{

	//注入dao
	@Resource(name="orderDao")
	private OrderDao orderDao;
	public void save(OrderModel om) {
		orderDao.save(om);
	}
	
	public void delete(OrderModel om) {
		orderDao.delete(om);
	}

	public void update(OrderModel om) {
		orderDao.update(om);
	}

	public List<OrderModel> getAll() {
		return orderDao.getAll();
	}

	
	public OrderModel getByUuid(Long uuid) {
		return orderDao.get(uuid);
	}

	
	public List<OrderModel> getAll(BaseQueryModel bqm) {
		
		return orderDao.getAll(bqm);
	}

	
	public Integer getCount(BaseQueryModel bqm) {
		return orderDao.getCount(bqm);
	}

	
	public List<OrderModel> getAll(BaseQueryModel bqm, Integer pageNum,
			Integer pageCount) {
	
		return orderDao.getAll(bqm, pageNum, pageCount);
	}
	//注入supplierEbi,goodsEbi
	@Resource(name="supplierEbi")
	private SupplierEbi supplierEbi;
	@Resource(name="goodsEbi")
	private GoodsEbi goodsEbi;
	public void buyOrderSave(OrderModel om, EmpModel creater, Long[] goodsUuids,
			Double[] prices,Integer[] nums) {

		/*
		 	orderState;totalNum;totalPrice;type;orderNum;creater;
			checker;completer;odms,supplier;createTime;checkTime;
			endTime;suplus
		 */
		//生成订单号
		om.setOrderNum(OrderNumUtils.getOrderNum());
		//创建时间
		om.setCreateTime(System.currentTimeMillis());
		//供应商
		SupplierModel supplier = supplierEbi.getByUuid(om.getSupplier().getUuid());
		om.setSupplier(supplier);
		//制单人
		om.setCreater(creater);
		//订单状态
		om.setOrderState(OrderModel.ORDER_ORDERSTATE_OF_BUY_NO_CHECK);
		//总数量,总价格
		Integer totalNum = 0;
		Double totalPrice = 0.0d;
		Set<OrderDetialModel> odms = om.getOdms();
		
		for(int i=0;i<nums.length;i++){
			OrderDetialModel odm = new OrderDetialModel();
			//num,price,gm,om
			odm.setNum(nums[i]);
			odm.setSuplus(nums[i]);
			odm.setPrice(prices[i]);
			//TODO 疑问  是否要查商品
			odm.setGm(goodsEbi.getByUuid(goodsUuids[i]));
			odm.setOm(om);
			totalNum += nums[i];
			totalPrice +=nums[i]*prices[i];
			//把odm添加到odms中
			odms.add(odm);
		}
		//设置odms
		om.setOdms(odms);
		//
		om.setTotalNum(totalNum);
		om.setTotalPrice(totalPrice);
		//订单类型
		om.setType(OrderModel.ORDER_TYPE_OF_BUY);
		
		
		//-------------保存订单
		orderDao.save(om);
	}

	public void buyInApprove(Long uuid,EmpModel checker) {
		OrderModel om = orderDao.get(uuid);
		//快照更新
		//checker,checkTime,uuid
		om.setOrderState(OrderModel.ORDER_ORDERSTATE_OF_BUY_PASS);
		om.setChecker(checker);
		om.setCheckTime(System.currentTimeMillis());
	}

	public List<OrderModel> getByEmp(EmpModel login) {
		return orderDao.getByEmpUuid(login.getUuid());
	}
	//注入empEbi,查询指定跟单人
	@Resource(name="empEbi")
	private EmpEbi empEbi;
	public void completerUpdate(Long empUuid,Long orderUuid) {
		OrderModel om = orderDao.get(orderUuid);
		EmpModel completer = empEbi.getByUuid(empUuid);
		//订单部分更新,使用快照更新
		//completer,orderState
		om.setCompleter(completer);
		om.setOrderState(OrderModel.ORDER_ORDERSTATE_OF_BUY_BUYYING);
	}

	public List<OrderModel> getTasksByEmp(EmpModel login) {
		return orderDao.getByEmpUuid(login.getUuid());
	}

	public void endTask(Long orderUuid) {
		OrderModel om = orderDao.get(orderUuid);
		//更新订单状态为完结,完结时间
		om.setOrderState(OrderModel.ORDER_ORDERSTATE_OF_BUY_IN_STORE);
		om.setEndTime(System.currentTimeMillis());
	}
	private Set<Integer> orderStateSet = new HashSet<Integer>();
	public List<OrderModel> getByType(OrderQueryModel oqm) {
		orderStateSet.add(OrderModel.ORDER_ORDERSTATE_OF_BUY_NO_PASS);
		orderStateSet.add(OrderModel.ORDER_ORDERSTATE_OF_BUY_NO_CHECK);
		return orderDao.getByOrderType(oqm,orderStateSet);
	}

	public List<OrderModel> getByOrderState() {
		orderStateSet.add(OrderModel.ORDER_ORDERSTATE_OF_BUY_IN_STORE);
		return orderDao.getByType(orderStateSet);
	}

}
