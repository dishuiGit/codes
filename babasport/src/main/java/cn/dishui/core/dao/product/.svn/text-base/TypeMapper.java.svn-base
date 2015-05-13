package cn.dishui.core.dao.product;

import cn.dishui.core.po.product.Type;
import cn.dishui.core.po.product.TypeQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TypeMapper {
    int countByExample(TypeQuery example);

    int deleteByExample(TypeQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(Type record);

    int insertSelective(Type record);

    List<Type> selectByExample(TypeQuery example);

    Type selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Type record, @Param("example") TypeQuery example);

    int updateByExample(@Param("record") Type record, @Param("example") TypeQuery example);

    int updateByPrimaryKeySelective(Type record);

    int updateByPrimaryKey(Type record);
}