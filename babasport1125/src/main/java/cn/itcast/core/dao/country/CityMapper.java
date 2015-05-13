package cn.itcast.core.dao.country;

import cn.itcast.core.bean.country.City;
import cn.itcast.core.bean.country.CityQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CityMapper {
    int countByExample(CityQuery example);

    int deleteByExample(CityQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(City record);

    int insertSelective(City record);

    List<City> selectByExample(CityQuery example);

    City selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") City record, @Param("example") CityQuery example);

    int updateByExample(@Param("record") City record, @Param("example") CityQuery example);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
}