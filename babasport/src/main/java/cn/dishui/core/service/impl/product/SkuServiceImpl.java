package cn.dishui.core.service.impl.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.dishui.core.dao.product.ColorMapper;
import cn.dishui.core.dao.product.ImgMapper;
import cn.dishui.core.dao.product.ProductMapper;
import cn.dishui.core.dao.product.SkuMapper;
import cn.dishui.core.po.product.Img;
import cn.dishui.core.po.product.ImgQuery;
import cn.dishui.core.po.product.Product;
import cn.dishui.core.po.product.ProductWithBLOBs;
import cn.dishui.core.po.product.Sku;
import cn.dishui.core.service.service.product.SkuService;

@Service
public class SkuServiceImpl implements SkuService {

	@Resource
	private SkuMapper skuMapper;
	@Resource
	private ProductMapper productMapper;
	@Resource
	private ColorMapper colorMapper;
	@Resource
	private ImgMapper imgMapper;

	// 通过skuid查询,product,color,img
	public Sku selectBySkuId(Integer id) {
		Sku sku = skuMapper.selectByPrimaryKey(id);
		// product
		Product product = productMapper.selectByPrimaryId(sku.getProductId());
		sku.setProduct(product);
		// img
		// 图片表:图片地址(allUrl)
		ImgQuery imgQuery = new ImgQuery();
		// 根据商品id
		imgQuery.createCriteria().andProductIdEqualTo(sku.getProductId()).andIsDefEqualTo(true);
		imgQuery.setOrderByClause("id desc");
		// 默认(升序排列)第一个
		List<Img> imgs = imgMapper.selectByExample(imgQuery);
		product.setImg(imgs.get(0));
		// color
		sku.setColor(colorMapper.selectByPrimaryKey(sku.getColorId()));
		return sku;
	}
}
