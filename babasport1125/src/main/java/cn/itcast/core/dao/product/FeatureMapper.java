package cn.itcast.core.dao.product;

import cn.itcast.core.bean.product.Feature;
import cn.itcast.core.bean.product.FeatureQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FeatureMapper {
    int countByExample(FeatureQuery example);

    int deleteByExample(FeatureQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(Feature record);

    int insertSelective(Feature record);

    List<Feature> selectByExample(FeatureQuery example);

    Feature selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Feature record, @Param("example") FeatureQuery example);

    int updateByExample(@Param("record") Feature record, @Param("example") FeatureQuery example);

    int updateByPrimaryKeySelective(Feature record);

    int updateByPrimaryKey(Feature record);
}