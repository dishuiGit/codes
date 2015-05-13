package cn.itcast.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.bean.product.Img;
import cn.itcast.core.bean.product.ImgQuery;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.bean.product.SkuQuery;
import cn.itcast.core.bean.product.SkuQuery.Criteria;
import cn.itcast.core.dao.product.ColorMapper;
import cn.itcast.core.dao.product.ImgMapper;
import cn.itcast.core.dao.product.ProductMapper;
import cn.itcast.core.dao.product.SkuMapper;

/**
 * sku 
 * @author lx
 *
 */
@Service
@Transactional
public class SkuServiceImpl implements SkuService{

	@Autowired
	private SkuMapper skuMapper;
	@Autowired
	private ColorMapper colorMapper;
	//
	public List<Sku> selectByExample(Integer id){
		SkuQuery example = new SkuQuery();
		
		Criteria createCriteria = example.createCriteria();
		
		createCriteria.andProductIdEqualTo(id);
		List<Sku> skus = skuMapper.selectByExample(example);
		for (Sku sku : skus) {
			sku.setColor(colorMapper.selectByPrimaryKey(sku.getColorId()));
		}
		
		return skus;
	}
	
	public void addSku(Sku sku){
		
		skuMapper.updateByPrimaryKeySelective(sku);
	}
	//查询有库存的   id 276 
	public List<Sku> selectSkusByProductId(Integer id){
		SkuQuery example = new SkuQuery();
		//商品ID
		Criteria createCriteria = example.createCriteria();
		createCriteria.andProductIdEqualTo(id).andStockInventoryGreaterThan(0);
		
		List<Sku> skus = skuMapper.selectByExample(example);
		for (Sku sku : skus) {
			//发送了 三条Sql  语句    mapper.xml <cache/> 二级缓存  返回三个对象   五个引用  二个重复的
			// select  id ,name ..  from bbs_color  where  id = 1   
			sku.setColor(colorMapper.selectByPrimaryKey(sku.getColorId()));
		}
		
		return skus;
	}
	//通过Id查询
	public Sku selectSkusById(Integer id){
		Sku sku = skuMapper.selectByPrimaryKey(id);
		sku.setColor(colorMapper.selectByPrimaryKey(sku.getColorId()));
		sku.setProduct(productMapper.selectByPrimaryKey(sku.getProductId()));
		ImgQuery example = new ImgQuery();
		example.createCriteria().andProductIdEqualTo(sku.getProductId()).andIsDefEqualTo(true);
		List<Img> imgs = imgMapper.selectByExample(example);
		sku.getProduct().setImg(imgs.get(0));
	  return sku;
	}
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ImgMapper imgMapper;
	
	
	@Override
	public Integer selectStockById(Integer skuId) {
		// TODO Auto-generated method stub
		Sku sku = skuMapper.selectByPrimaryKey(skuId);
		return sku.getStockInventory();
	}
}
