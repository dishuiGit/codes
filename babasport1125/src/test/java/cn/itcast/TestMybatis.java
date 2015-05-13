package cn.itcast;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.common.junit.SpringJunitTest;
import cn.itcast.core.bean.product.Img;
import cn.itcast.core.bean.product.ImgQuery;
import cn.itcast.core.bean.user.Buyer;
import cn.itcast.core.bean.user.BuyerQuery;
import cn.itcast.core.bean.user.BuyerQuery.Criteria;
import cn.itcast.core.dao.product.ImgMapper;
import cn.itcast.core.dao.user.BuyerMapper;
/**
 * 测试逆向工程
 * @author lx
 *
 */
public class TestMybatis extends SpringJunitTest{

	@Autowired
	private BuyerMapper buyerMapper;
	@Autowired
	private ImgMapper imgMapper;
	
	
	
	@Test
	public void testSelect() throws Exception {
		//查询列表  通过条件
		BuyerQuery example = new BuyerQuery();
		
		//设置条件  对象    
		Criteria criteria = example.createCriteria();
		
		criteria.andUsernameEqualTo("fbb2014");
		//测试指定字段  select username , passsword from bbs_buyer where....
		example.setFields("username,password");
		//排序  order by  id
		
		
		
		List<Buyer> list = buyerMapper.selectByExample(example);
		
		for (Buyer buyer : list) {
			System.out.println(buyer.toString());
		}
	}
	@Test
	public void testSelectImg() throws Exception {
		//查询列表  通过条件
		ImgQuery example = new ImgQuery();
		// 设置 条件
		cn.itcast.core.bean.product.ImgQuery.Criteria criteria = example.createCriteria();
		
		
		criteria.andIsDefEqualTo(true);
		
		
		//排序  <where>  and id = 1 and   order by 
		//example.setOrderByClause("id desc"); 
		
		
		List<Img> selectByExample = imgMapper.selectByExample(example);
		for (Img img : selectByExample) {
			System.out.println(img);
		}
	}
	@Test
	public void testUpdate() throws Exception {
		Img img = new Img();
		
		img.setIsDef(true);
		img.setId(216);
		img.setUrl("res/img/pic/ppp1.jpg");
		
		imgMapper.updateByPrimaryKeySelective(img);
		
	}
}
