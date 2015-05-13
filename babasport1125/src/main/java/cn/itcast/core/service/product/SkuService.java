package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.core.bean.product.Sku;

public interface SkuService {

	
	public List<Sku> selectByExample(Integer id);
	
	public void addSku(Sku sku);
	
	//查询有库存的   id 276 
	public List<Sku> selectSkusByProductId(Integer id);
	//查询有库存的   id 276 
	public Sku selectSkusById(Integer id);
	
	//通过skuId 查询此 商品还有多少件
	public Integer selectStockById(Integer skuId);
}
