package cn.itcast.erp.invoice.order.business.ebo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.order.business.ebi.OrderEbi;
import cn.itcast.erp.invoice.order.dao.dao.OrderDao;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.order.vo.OrderQueryModel;
import cn.itcast.erp.invoice.orderdetail.dao.dao.OrderDetailDao;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailModel;
import cn.itcast.erp.invoice.store.vo.StoreModel;
import cn.itcast.erp.invoice.storedetail.dao.dao.StoreDetailDao;
import cn.itcast.erp.invoice.storedetail.vo.StoreDetailModel;
import cn.itcast.erp.invoice.storeoper.dao.dao.StoreOperDao;
import cn.itcast.erp.invoice.storeoper.vo.StoreOperModel;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;
import cn.itcast.erp.util.base.BaseQueryModel;
import cn.itcast.erp.util.exception.AppException;
import cn.itcast.erp.util.generator.OrderNumUtils;

public class OrderEbo implements OrderEbi{
	private OrderDao orderDao;
	private OrderDetailDao orderDetailDao;
	private StoreDetailDao storeDetailDao;
	private StoreOperDao storeOperDao;
	public void setStoreOperDao(StoreOperDao storeOperDao) {
		this.storeOperDao = storeOperDao;
	}

	public void setStoreDetailDao(StoreDetailDao storeDetailDao) {
		this.storeDetailDao = storeDetailDao;
	}

	public void setOrderDetailDao(OrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void save(OrderModel om) {
		orderDao.save(om);
	}

	public List<OrderModel> getAll() {
		return orderDao.getAll();
	}

	public OrderModel get(Long uuid) {
		return orderDao.get(uuid);
	}

	public void update(OrderModel om) {
		orderDao.update(om);
	}

	public void delete(OrderModel om) {
		orderDao.delete(om);
	}

	public List<OrderModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return orderDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return orderDao.getCount(qm);
	}

	public void saveBuyOrder(OrderModel om, Long[] goodsUuids, Integer[] nums,Double[] prices,EmpModel creater) {
		//业务：保存订单
		//om对象中需要填写哪些数据？
		
		//订单号：来源于订单号生成策略
		om.setOrderNum(OrderNumUtils.getOrderNum());
		//创建订单的时间:当前系统时间
		om.setCreateTime(System.currentTimeMillis());
		//当前订单的类别:采购
		om.setOrderType(OrderModel.ORDER_ORDERTYPE_OF_BUY);
		//当前订单的状态：未审核
		om.setType(OrderModel.ORDER_TYPE_OF_BUY_NO_CHECK);
		//设置订单的创建人：
		om.setCreater(creater);
		
		Integer totalNum = 0;
		Double totalPrice = 0.0d;
		Set<OrderDetailModel> odms = new HashSet<OrderDetailModel>();
		//订单明细数据怎么得到？从传递过来的三个数组中获取
		for(int i = 0;i<goodsUuids.length;i++){
			OrderDetailModel odm = new OrderDetailModel();
			odm.setNum(nums[i]);		//数量
			odm.setSurplus(nums[i]);	//剩余数量
			odm.setPrice(prices[i]);	//单价
			
			GoodsModel gm = new GoodsModel();
			gm.setUuid(goodsUuids[i]);	//商品uuid
			odm.setGm(gm);
			
			odm.setOm(om);
			
			odms.add(odm);
			
			totalNum += nums[i];
			totalPrice += nums[i]*prices[i];
		}
		//订单到明细的关系就建立起来了
		om.setOdms(odms);
		//
		om.setTotalNum(totalNum);
		om.setTotalPrice(totalPrice);
		
		orderDao.save(om);
	}
	
	private Integer[] buyCheckOrderTypes = new Integer[]{
			OrderModel.ORDER_ORDERTYPE_OF_BUY,
			OrderModel.ORDER_ORDERTYPE_OF_RETURN_BUY,
		};
	public static Set<Integer> buyNoCheckTypes = new HashSet<Integer>();
	static
	{
		buyNoCheckTypes.add(OrderModel.ORDER_TYPE_OF_BUY_NO_CHECK);
		//buyNoCheckTypes.add(//OrderModel.采购状态为采购退货未审核);
	}
	
	public Integer getCountBuyCheck(OrderQueryModel oqm) {
		//限定采购审核的订单类别条件为采购或采购退货
		//定义两个条件数据，并将其传递到数据层，因此将数据封装成数组或集合
		
		oqm.setOrderTypes(buyCheckOrderTypes);
		return orderDao.getCount(oqm);
	}
	public List<OrderModel> getAllBuyCheck(OrderQueryModel oqm,
			Integer pageNum, Integer pageCount) {
		//限定采购审核的订单类别条件为采购或采购退货
		oqm.setOrderTypes(buyCheckOrderTypes);
		return orderDao.getAll(oqm, pageNum, pageCount);
	}

	public void buyCheckPass(Long uuid, EmpModel checker) {
		//业务：
		//将指定uuid对应的订单审核通过：修改type，添加审核人，添加审核时间
		//快照
		OrderModel om = orderDao.get(uuid);
		//此时om的状态是未审核
		//判断如果状态是未审核
		
		//逻辑校验：对业务安全性进行校验
		if(!om.getType().equals(OrderModel.ORDER_TYPE_OF_BUY_NO_CHECK)){
			throw new AppException("对不起，请不要进行非法操作！");
		}
		
		//设置订单状态为审核通过
		om.setType(OrderModel.ORDER_TYPE_OF_BUY_PASS);
		//添加审核时间
		om.setCheckTime(System.currentTimeMillis());
		//添加审核人
		om.setChecker(checker);
	}

	public void buyCheckNoPass(Long uuid, EmpModel checker) {
		OrderModel om = orderDao.get(uuid);
		if(!om.getType().equals(OrderModel.ORDER_TYPE_OF_BUY_NO_CHECK)){
			throw new AppException("对不起，请不要进行非法操作！");
		}
		//设置订单状态为审核通过
		om.setType(OrderModel.ORDER_TYPE_OF_BUY_NO_PASS);
		om.setCheckTime(System.currentTimeMillis());
		om.setChecker(checker);
	}
	private Integer[] transportTaskTypes = new Integer[]{
		OrderModel.ORDER_TYPE_OF_BUY_PASS,
		OrderModel.ORDER_TYPE_OF_BUY_BUYYING,
		OrderModel.ORDER_TYPE_OF_BUY_IN_STORE,
		OrderModel.ORDER_TYPE_OF_BUY_COMPLETE,
		//OrderModel.还有12个状态
		};
	public Integer getCountTaskList(OrderQueryModel oqm) {
		//条件中应该设置订单的状态不能是未审核或者审核驳回
		oqm.setTransportTaskTypes(transportTaskTypes);
		return orderDao.getCount(oqm);
	}

	public List<OrderModel> getAllTaskList(OrderQueryModel oqm,
			Integer pageNum, Integer pageCount) {
		oqm.setTransportTaskTypes(transportTaskTypes);
		return orderDao.getAll(oqm, pageNum, pageCount);
	}

	public void assignedTask(Long uuid, EmpModel completer) {
		//查询，快照更新
		OrderModel om = orderDao.get(uuid);
		//实际上应该使用集合contains(type),集合中保存的是4种审核通过
		if(!om.getType().equals(OrderModel.ORDER_TYPE_OF_BUY_PASS)){
			throw new AppException("对不起，请不要进行非法操作！");
		}
		om.setType(OrderModel.ORDER_TYPE_OF_BUY_BUYYING);
		om.setCompleter(completer);
		
	}
	private Integer[] taskTypes = new Integer[]{
			OrderModel.ORDER_TYPE_OF_BUY_BUYYING,
			OrderModel.ORDER_TYPE_OF_BUY_IN_STORE,
			OrderModel.ORDER_TYPE_OF_BUY_COMPLETE,
			//OrderModel.还有9个状态
			};
	public Integer getCountTasks(OrderQueryModel oqm, EmpModel completer) {
		//要将当前登陆人作为跟单人条件
		//type应该限定在正在采购和入库中，入库完毕
		oqm.setCompleter(completer);
		oqm.setTransportTaskTypes(taskTypes);
		return orderDao.getCount(oqm);
	}

	public List<OrderModel> getAllTasks(OrderQueryModel oqm, Integer pageNum,
			Integer pageCount, EmpModel completer) {
		oqm.setCompleter(completer);
		oqm.setTransportTaskTypes(taskTypes);
		return orderDao.getAll(oqm, pageNum, pageCount);
	}

	public void endTask(Long uuid) {
		//快照更新
		OrderModel om = orderDao.get(uuid);
		if(!om.getType().equals(OrderModel.ORDER_TYPE_OF_BUY_BUYYING)){
			throw new AppException("悟空，不要调皮！");
		}
		om.setType(OrderModel.ORDER_TYPE_OF_BUY_IN_STORE);
	}

	public Integer getCountInStore(OrderQueryModel oqm, EmpModel login) {
		//状态必须是采购入库中或销售退货入库中
		oqm.setType(OrderModel.ORDER_TYPE_OF_BUY_IN_STORE);
		return orderDao.getCount(oqm);
	}

	public List<OrderModel> getAllInStore(OrderQueryModel oqm, Integer pageNum,
			Integer pageCount, EmpModel login) {
		oqm.setType(OrderModel.ORDER_TYPE_OF_BUY_IN_STORE);
		return orderDao.getAll(oqm, pageNum, pageCount);
	}
	
	//复杂
	public OrderDetailModel inGoods(Long odmUuid, Long storeUuid, Integer num, EmpModel login) {
		//入库
		//1.订单明细中的数据跟随着入库发生变化（剩余数量减少num）
		//修改明细数据中的剩余数量
		OrderDetailModel odm = orderDetailDao.get(odmUuid);
		OrderModel om = odm.getOm();
		
		//逻辑校验
		if(!om.getType().equals(OrderModel.ORDER_TYPE_OF_BUY_IN_STORE)){
			throw new AppException("对不起，请不要进行非法操作！");
		}
		//数据正确性逻辑校验
		//取出当前入库操作中的数据
		//与本次操作的入库数据比对，如果出问题，throw ....
		if(odm.getSurplus() < num ){
			//入库数量比剩余数量还大
			throw new AppException("数量有误！");
		}
		
		
		//快照更新
		//剩余数量修改为：当前剩余数量-本次操作数量
		odm.setSurplus(odm.getSurplus()-num);
		
		StoreModel sm = new StoreModel();
		sm.setUuid(storeUuid);
		
		//操作的商品
		GoodsModel gm = odm.getGm();
		
		//2.仓库中的货物数量要变化
		//获取仓库中现有的数据，在现有数量上+num
		StoreDetailModel sdm = storeDetailDao.getBySmAndGm(storeUuid,gm.getUuid());
		if(sdm != null){
			//如果对应仓库有对应货物，修改数量
			//快照更新数量
			sdm.setNum(sdm.getNum()+num);
		}else{
			//如果对应仓库没有对应货物，新建一条数据
			sdm = new StoreDetailModel();
			//本次入库量即为新建数据的数量
			sdm.setNum(num);
			
			sdm.setSm(sm);
			
			sdm.setGm(gm);
			storeDetailDao.save(sdm);
		}
		
		//3.入库操作数据日志
		StoreOperModel som = new StoreOperModel();
		
		//操作数量即为本次入库数量
		som.setNum(num);
		//操作时间为当前系统时间
		som.setOperTime(System.currentTimeMillis());
		//本操作是入库
		som.setType(StoreOperModel.OPER_TYPE_OF_IN);
		//操作人
		som.setEm(login);
		//操作对应的仓库
		som.setSm(sm);
		//操作对应的货物
		som.setGm(gm);
		//save
		storeOperDao.save(som);
		
		//4.当订单所有明细入库完毕，修改订单状态
		Integer sum = 0;
		for(OrderDetailModel temp :om.getOdms()){
			sum += temp.getSurplus();
		}
		if(sum == 0){
			//修改订单的状态为结单，记录完成时间
			//快照
			om.setType(OrderModel.ORDER_TYPE_OF_BUY_COMPLETE);
			om.setEndTime(System.currentTimeMillis());
		}
		
		//该数据中封装了订单明细的总数量与剩余未操作数量
		return odm;
	}

}







