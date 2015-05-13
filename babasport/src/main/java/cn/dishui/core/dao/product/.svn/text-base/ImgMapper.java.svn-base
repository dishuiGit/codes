package cn.dishui.core.dao.product;

import cn.dishui.core.po.product.Img;
import cn.dishui.core.po.product.ImgQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImgMapper {
    int countByExample(ImgQuery example);

    int deleteByExample(ImgQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(Img record);

    int insertSelective(Img record);

    List<Img> selectByExample(ImgQuery example);

    Img selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Img record, @Param("example") ImgQuery example);

    int updateByExample(@Param("record") Img record, @Param("example") ImgQuery example);

    int updateByPrimaryKeySelective(Img record);

    int updateByPrimaryKey(Img record);
}