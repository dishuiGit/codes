package cn.dishui.core.dao.user;

import cn.dishui.core.po.user.Buyer;
import cn.dishui.core.po.user.BuyerQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BuyerMapper {
    int countByExample(BuyerQuery example);

    int deleteByExample(BuyerQuery example);

    int deleteByPrimaryKey(String username);

    int insert(Buyer record);

    int insertSelective(Buyer record);

    List<Buyer> selectByExample(BuyerQuery example);

    Buyer selectByPrimaryKey(String username);

    int updateByExampleSelective(@Param("record") Buyer record, @Param("example") BuyerQuery example);

    int updateByExample(@Param("record") Buyer record, @Param("example") BuyerQuery example);

    int updateByPrimaryKeySelective(Buyer record);

    int updateByPrimaryKey(Buyer record);
}