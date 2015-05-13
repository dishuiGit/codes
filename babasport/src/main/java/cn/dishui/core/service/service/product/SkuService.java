package cn.dishui.core.service.service.product;

import org.springframework.transaction.annotation.Transactional;

import cn.dishui.core.po.product.Sku;

@Transactional
public interface SkuService {

	// 通过skuid查询,product,color,img
	public Sku selectBySkuId(Integer id);
}
