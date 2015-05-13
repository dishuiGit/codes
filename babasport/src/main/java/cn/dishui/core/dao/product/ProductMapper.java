package cn.dishui.core.dao.product;

import cn.dishui.core.po.product.Product;
import cn.dishui.core.po.product.ProductQuery;
import cn.dishui.core.po.product.ProductWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductMapper {
    int countByExample(ProductQuery example);

    int deleteByExample(ProductQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductWithBLOBs record);

    int insertSelective(ProductWithBLOBs record);

    List<ProductWithBLOBs> selectByExampleWithBLOBs(ProductQuery example);

    List<Product> selectByExample(ProductQuery example);

    ProductWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductWithBLOBs record, @Param("example") ProductQuery example);

    int updateByExampleWithBLOBs(@Param("record") ProductWithBLOBs record, @Param("example") ProductQuery example);

    int updateByExample(@Param("record") Product record, @Param("example") ProductQuery example);

    int updateByPrimaryKeySelective(ProductWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ProductWithBLOBs record);

    int updateByPrimaryKey(Product record);
    
    Product selectByPrimaryId(Integer id);
}