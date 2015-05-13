package cn.dishui.core.dao.country;

import cn.dishui.core.po.country.Town;
import cn.dishui.core.po.country.TownQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TownMapper {
    int countByExample(TownQuery example);

    int deleteByExample(TownQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(Town record);

    int insertSelective(Town record);

    List<Town> selectByExample(TownQuery example);

    Town selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Town record, @Param("example") TownQuery example);

    int updateByExample(@Param("record") Town record, @Param("example") TownQuery example);

    int updateByPrimaryKeySelective(Town record);

    int updateByPrimaryKey(Town record);
}