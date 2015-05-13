package cn.itcast.erp.invoice.order.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.order.vo.OrderQueryModel;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailModel;
import cn.itcast.erp.util.base.BaseEbi;

@Transactional
public interface OrderEbi extends BaseEbi<OrderModel>{
	/**
	 * 保存采购订单
	 * @param om 订单数据模型
	 * @param goodsUuids 订单明细商品uuid数组
	 * @param nums 订单明细商品采购数量数组
	 * @param prices 订单明细商品采购单价数组
	 * @param empModel 创建订单的员工对象
	 */
	public void saveBuyOrder(OrderModel om, Long[] goodsUuids, Integer[] nums,
			Double[] prices, EmpModel empModel);

	public Integer getCountBuyCheck(OrderQueryModel oqm);

	public List<OrderModel> getAllBuyCheck(OrderQueryModel oqm,
			Integer pageNum, Integer pageCount);
	/**
	 * 采购审核通过
	 * @param uuid 被审核通过的订单uuid
	 * @param checker 审核人
	 */
	public void buyCheckPass(Long uuid, EmpModel checker);

	public void buyCheckNoPass(Long uuid, EmpModel login);

	public Integer getCountTaskList(OrderQueryModel oqm);

	public List<OrderModel> getAllTaskList(OrderQueryModel oqm,
			Integer pageNum, Integer pageCount);
	/**
	 * 指派任务到跟单人
	 * @param uuid 订单uuid
	 * @param completer 跟单人
	 */
	public void assignedTask(Long uuid, EmpModel completer);

	public Integer getCountTasks(OrderQueryModel oqm, EmpModel completer);

	public List<OrderModel> getAllTasks(OrderQueryModel oqm, Integer pageNum,
			Integer pageCount, EmpModel completer);

	public void endTask(Long uuid);

	public Integer getCountInStore(OrderQueryModel oqm, EmpModel login);

	public List<OrderModel> getAllInStore(OrderQueryModel oqm, Integer pageNum,
			Integer pageCount, EmpModel login);
	/**
	 * 入库货物到指定仓库
	 * @param odmUuid 货物对应的订单明细uuid
	 * @param storeUuid 仓库uuid
	 * @param num 入库数量
	 * @param login 入库人
	 */
	public OrderDetailModel inGoods(Long odmUuid, Long storeUuid, Integer num,
			EmpModel login);

}