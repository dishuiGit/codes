package cn.dishui.core.dao.product;

import cn.dishui.core.po.product.Color;
import cn.dishui.core.po.product.ColorQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ColorMapper {
    int countByExample(ColorQuery example);

    int deleteByExample(ColorQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(Color record);

    int insertSelective(Color record);

    List<Color> selectByExample(ColorQuery example);

    Color selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Color record, @Param("example") ColorQuery example);

    int updateByExample(@Param("record") Color record, @Param("example") ColorQuery example);

    int updateByPrimaryKeySelective(Color record);

    int updateByPrimaryKey(Color record);
    
    public List<Color> selectColorsByPId(Integer id);
}