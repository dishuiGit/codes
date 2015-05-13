package cn.itcast.core.dao.product;

import cn.itcast.core.bean.product.Product;
import cn.itcast.core.bean.product.ProductQuery;
import cn.itcast.core.bean.product.ProductWithBLOBs;
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
}