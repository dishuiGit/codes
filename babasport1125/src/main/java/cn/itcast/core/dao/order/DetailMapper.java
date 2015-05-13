package cn.itcast.core.dao.order;

import cn.itcast.core.bean.order.Detail;
import cn.itcast.core.bean.order.DetailQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DetailMapper {
    int countByExample(DetailQuery example);

    int deleteByExample(DetailQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(Detail record);

    int insertSelective(Detail record);

    List<Detail> selectByExample(DetailQuery example);

    Detail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Detail record, @Param("example") DetailQuery example);

    int updateByExample(@Param("record") Detail record, @Param("example") DetailQuery example);

    int updateByPrimaryKeySelective(Detail record);

    int updateByPrimaryKey(Detail record);
}