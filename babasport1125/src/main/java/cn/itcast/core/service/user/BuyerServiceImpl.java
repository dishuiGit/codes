package cn.itcast.core.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.core.bean.user.Buyer;
import cn.itcast.core.dao.user.BuyerMapper;

@Service
public class BuyerServiceImpl implements BuyerService {

	@Autowired
	private BuyerMapper buyerMapper;
	public Buyer selectBuyerByKey(String username){
		return buyerMapper.selectByPrimaryKey(username);
	}
}
