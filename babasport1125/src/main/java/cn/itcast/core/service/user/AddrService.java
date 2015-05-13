package cn.itcast.core.service.user;

import java.util.List;

import cn.itcast.core.bean.user.Addr;
import cn.itcast.core.bean.user.Buyer;

public interface AddrService {

	public List<Addr> selectAddrsByUserName(Buyer buyer);
	public Addr selectAddrsByUserNameAndIsDef(Buyer buyer);
}
