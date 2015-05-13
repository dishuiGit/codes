package cn.dishui.core.dao.product;

import cn.dishui.core.po.product.Sku;
import cn.dishui.core.po.product.SkuQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SkuMapper {
    int countByExample(SkuQuery example);

    int deleteByExample(SkuQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(Sku record);

    int insertSelective(Sku record);

    List<Sku> selectByExample(SkuQuery example);

    Sku selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Sku record, @Param("example") SkuQuery example);

    int updateByExample(@Param("record") Sku record, @Param("example") SkuQuery example);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKey(Sku record);
}