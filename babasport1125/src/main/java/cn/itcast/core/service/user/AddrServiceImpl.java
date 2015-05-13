package cn.itcast.core.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.core.bean.user.Addr;
import cn.itcast.core.bean.user.AddrQuery;
import cn.itcast.core.bean.user.Buyer;
import cn.itcast.core.dao.user.AddrMapper;
@Service
public class AddrServiceImpl  implements AddrService{

	
	@Autowired
	private AddrMapper addrMapper;
	//获取收货地址
	public List<Addr> selectAddrsByUserName(Buyer buyer){
		AddrQuery addrQuery = new AddrQuery();
		addrQuery.createCriteria().andBuyerIdEqualTo(buyer.getUsername());
		
		return addrMapper.selectByExample(addrQuery);
	}
	@Override
	public Addr selectAddrsByUserNameAndIsDef(Buyer buyer) {
		AddrQuery addrQuery = new AddrQuery();
		addrQuery.createCriteria().andBuyerIdEqualTo(buyer.getUsername()).andIsDefEqualTo(1);
		
		return addrMapper.selectByExample(addrQuery).get(0);
	}
}
