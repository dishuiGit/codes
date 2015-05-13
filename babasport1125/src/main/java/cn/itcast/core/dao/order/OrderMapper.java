package cn.itcast.core.dao.order;

import cn.itcast.core.bean.order.Order;
import cn.itcast.core.bean.order.OrderQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    int countByExample(OrderQuery example);

    int deleteByExample(OrderQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderQuery example);

    Order selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderQuery example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderQuery example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}