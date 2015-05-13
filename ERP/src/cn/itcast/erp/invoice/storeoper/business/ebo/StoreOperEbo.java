package cn.itcast.erp.invoice.storeoper.business.ebo;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.erp.auth.base.BaseQueryModel;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.exception.AppException;
import cn.itcast.erp.invoice.goods.business.ebi.GoodsEbi;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.order.business.ebi.OrderEbi;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.orderDetail.business.ebi.OrderDetialEbi;
import cn.itcast.erp.invoice.orderDetail.vo.OrderDetialModel;
import cn.itcast.erp.invoice.store.business.ebi.StoreEbi;
import cn.itcast.erp.invoice.store.vo.StoreModel;
import cn.itcast.erp.invoice.storeDetail.business.ebi.StoreDetailEbi;
import cn.itcast.erp.invoice.storeDetail.vo.StoreDetailModel;
import cn.itcast.erp.invoice.storeoper.business.ebi.StoreOperEbi;
import cn.itcast.erp.invoice.storeoper.dao.dao.StoreOperDao;
import cn.itcast.erp.invoice.storeoper.vo.StoreOperModel;

public class StoreOperEbo implements StoreOperEbi{
	
	@Resource(name="storeOperDao")
	private StoreOperDao storeOperDao;
	public void save(StoreOperModel som) {
		storeOperDao.save(som);
	}
	public void delete(StoreOperModel som) {
		storeOperDao.delete(som);
	}
	
	public void update(StoreOperModel som) {
		storeOperDao.update(som);
	}

	
	public List<StoreOperModel> getAll() {
		return storeOperDao.getAll();
	}

	
	public StoreOperModel getByUuid(Long uuid) {
		return storeOperDao.get(uuid);
	}

	public List<StoreOperModel> getAll(BaseQueryModel bqm) {
		return storeOperDao.getAll(bqm);
	}
	public Integer getCount(BaseQueryModel bqm) {
		return storeOperDao.getCount(bqm);
	}

	public List<StoreOperModel> getAll(BaseQueryModel bqm, Integer pageNum,
			Integer pageCount) {
		return storeOperDao.getAll(bqm, pageNum, pageCount);
	}
	//注入
	@Resource(name="orderEbi")
	private OrderEbi orderEbi;
	@Resource(name="storeEbi")
	private StoreEbi storeEbi;
	@Resource(name="goodsEbi")
	private GoodsEbi goodsEbi;
	@Resource(name="odEbi")
	private OrderDetialEbi orderDetialEbi;
	@Resource(name="storeDetailEbi")
	private StoreDetailEbi storeDetailEbi;
	public OrderDetialModel inStore(EmpModel login, Long orderDetailUuid, Long storeUuid, Long goodsUuid,Integer inStoreNum) {
		
		OrderDetialModel odm = orderDetialEbi.getByUuid(orderDetailUuid);
		OrderModel om = odm.getOm();
		StoreModel sm = storeEbi.getByUuid(storeUuid);
		GoodsModel gm = goodsEbi.getByUuid(goodsUuid);
		StoreDetailModel sdm = storeDetailEbi.getBySmAndGm(storeUuid,goodsUuid);
		//如果入库数量大于剩余数量
		if(inStoreNum>odm.getSuplus()){
			throw new AppException();
		}
		//-------------------------------
		StoreOperModel som = new StoreOperModel();
		//入库数量 num
		som.setNum(inStoreNum);
		//
		som.setType(StoreOperModel.OPER_TYPE_OF_IN);
		//操作时间
		som.setOperTime(System.currentTimeMillis());
		//操作人
		som.setEm(login);
		//操作订单
		som.setOm(om);
		//操作仓库
		som.setSm(sm);
		//操作商品
		som.setGm(gm);
		//-------------订单明细会跟着改变
		// num; suplus;
		odm.setSuplus(odm.getSuplus()-inStoreNum);
		//-------------仓库明细也要改变
		//如果仓库没有 入库的商品
		if(sdm==null){
			sdm = new StoreDetailModel();
			sdm.setGm(gm);
			sdm.setNum(inStoreNum);
			sdm.setSm(sm);
			storeDetailEbi.save(sdm);
		}else{
			//仓库已有要入库的商品
			sdm.setNum(sdm.getNum()+inStoreNum);
		}
		storeOperDao.save(som);
		//当订单所有明细更新完毕,修改订单状态
		//什么时候更新?当订单明细中 商品剩余数量为0 时,更新
		Integer sum =0;
		for(OrderDetialModel tmp_odm:om.getOdms()){
			sum+=tmp_odm.getSuplus();
		}
		if(sum==0){
			om.setOrderState(OrderModel.ORDER_ORDERSTATE_OF_BUY_COMPLETE);
			om.setEndTime(System.currentTimeMillis());
		}
		
		return odm;
	}

}
