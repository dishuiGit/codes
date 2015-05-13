package cn.itcast.erp.invoice.order.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.base.BaseEbi;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.order.vo.OrderQueryModel;

@Transactional
public interface OrderEbi extends BaseEbi<OrderModel>{
	/**
	 * 保存订单
	 * @param om 接收order uuid
	 * @param login 当前的登陆用户
	 * @param goodsUuids 商品id集合
	 * @param prices 商品价格集合
	 * @param nums 商品个数集合
	 */
	public void buyOrderSave(OrderModel om, EmpModel login, Long[] goodsUuids,
			Double[] prices, Integer[] nums);

	public void buyInApprove(Long uuid, EmpModel empModel);
	/**
	 * 根据当前登录用户 查询订单
	 * @param login 登陆人
	 * @return
	 */
	public List<OrderModel> getByEmp(EmpModel login);
	/**
	 * 对订单指定跟单人
	 * @param empUuid 订单uuid
	 * @param orderUuid 跟单人uuid
	 */
	public void completerUpdate(Long empUuid,Long orderUuid);
	/**
	 * 根据用户,查找该用户的任务单
	 * @param login 当前登录用户
	 * @return 该用户的任务单
	 */
	public List<OrderModel> getTasksByEmp(EmpModel login);
	/**
	 * 任务完结,更新订单状态
	 * @param uuid
	 */
	public void endTask(Long uuid);
	/**
	 * 根据订单类别过滤订单
	 * @param oqm 订单查询模型
	 * @return 订单结果集
	 */
	public List<OrderModel> getByType(OrderQueryModel oqm);
	/**
	 * 根据订单状态过滤订单
	 * @return 符合状态的订单
	 */
	public List<OrderModel> getByOrderState();

}
