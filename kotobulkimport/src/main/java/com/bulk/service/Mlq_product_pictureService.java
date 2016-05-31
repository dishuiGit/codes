package com.bulk.service;

import com.bulk.pojo.Mlq_product_picture;
import com.bulk.pojo.Mlq_product_pictureQuery;
import java.util.List;

public interface Mlq_product_pictureService {
    int countByExample(Mlq_product_pictureQuery example);

    Mlq_product_picture selectByPrimaryKey(Integer id);

    List<Mlq_product_picture> selectByExample(Mlq_product_pictureQuery example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Mlq_product_picture record);

    int updateByPrimaryKey(Mlq_product_picture record);

    int deleteByExample(Mlq_product_pictureQuery example);

    int updateByExampleSelective(Mlq_product_picture record, Mlq_product_pictureQuery example);

    int updateByExample(Mlq_product_picture record, Mlq_product_pictureQuery example);

    void insert(Mlq_product_picture record);

    void insertSelective(Mlq_product_picture record);
}