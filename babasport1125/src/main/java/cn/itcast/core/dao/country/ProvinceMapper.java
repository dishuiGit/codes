package cn.itcast.core.dao.country;

import cn.itcast.core.bean.country.Province;
import cn.itcast.core.bean.country.ProvinceQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProvinceMapper {
    int countByExample(ProvinceQuery example);

    int deleteByExample(ProvinceQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(Province record);

    int insertSelective(Province record);

    List<Province> selectByExample(ProvinceQuery example);

    Province selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Province record, @Param("example") ProvinceQuery example);

    int updateByExample(@Param("record") Province record, @Param("example") ProvinceQuery example);

    int updateByPrimaryKeySelective(Province record);

    int updateByPrimaryKey(Province record);
}