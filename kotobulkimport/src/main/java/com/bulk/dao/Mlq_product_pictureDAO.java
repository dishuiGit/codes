package com.bulk.dao;

import com.bulk.pojo.Mlq_product_picture;
import com.bulk.pojo.Mlq_product_pictureQuery;
import java.util.List;

public interface Mlq_product_pictureDAO {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(Mlq_product_pictureQuery example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(Mlq_product_pictureQuery example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    void insert(Mlq_product_picture record);

    /**
     * 保存属性不为空的记录
     */
    void insertSelective(Mlq_product_picture record);

    /**
     * 根据条件查询记录集
     */
    List<Mlq_product_picture> selectByExampleWithBLOBs(Mlq_product_pictureQuery example);

    /**
     * 根据条件查询记录集
     */
    List<Mlq_product_picture> selectByExampleWithoutBLOBs(Mlq_product_pictureQuery example);

    /**
     * 根据主键查询记录
     */
    Mlq_product_picture selectByPrimaryKey(Integer id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(Mlq_product_picture record, Mlq_product_pictureQuery example);

    /**
     * 根据条件更新记录
     */
    int updateByExampleWithBLOBs(Mlq_product_picture record, Mlq_product_pictureQuery example);

    /**
     * 根据条件更新记录
     */
    int updateByExampleWithoutBLOBs(Mlq_product_picture record, Mlq_product_pictureQuery example);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Mlq_product_picture record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKeyWithBLOBs(Mlq_product_picture record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKeyWithoutBLOBs(Mlq_product_picture record);
}