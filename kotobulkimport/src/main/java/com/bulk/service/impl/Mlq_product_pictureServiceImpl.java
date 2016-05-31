package com.bulk.service.impl;

import com.bulk.dao.Mlq_product_pictureDAO;
import com.bulk.pojo.Mlq_product_picture;
import com.bulk.pojo.Mlq_product_pictureQuery;
import com.bulk.service.Mlq_product_pictureService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Mlq_product_pictureServiceImpl implements Mlq_product_pictureService {
    @Autowired
    private Mlq_product_pictureDAO mlq_product_pictureDAO;

    private static final Logger logger = LoggerFactory.getLogger(Mlq_product_pictureServiceImpl.class);

    public int countByExample(Mlq_product_pictureQuery example) {
        int count = this.mlq_product_pictureDAO.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Mlq_product_picture selectByPrimaryKey(Integer id) {
        return this.mlq_product_pictureDAO.selectByPrimaryKey(id);
    }

    public List<Mlq_product_picture> selectByExample(Mlq_product_pictureQuery example) {
        return this.mlq_product_pictureDAO.selectByExampleWithoutBLOBs(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.mlq_product_pictureDAO.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Mlq_product_picture record) {
        return this.mlq_product_pictureDAO.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Mlq_product_picture record) {
        return this.mlq_product_pictureDAO.updateByPrimaryKeyWithoutBLOBs(record);
    }

    public int deleteByExample(Mlq_product_pictureQuery example) {
        return this.mlq_product_pictureDAO.deleteByExample(example);
    }

    public int updateByExampleSelective(Mlq_product_picture record, Mlq_product_pictureQuery example) {
        return this.mlq_product_pictureDAO.updateByExampleSelective(record, example);
    }

    public int updateByExample(Mlq_product_picture record, Mlq_product_pictureQuery example) {
        return this.mlq_product_pictureDAO.updateByExampleWithoutBLOBs(record, example);
    }

    public void insert(Mlq_product_picture record) {
        this.mlq_product_pictureDAO.insert(record);
    }

    public void insertSelective(Mlq_product_picture record) {
        this.mlq_product_pictureDAO.insertSelective(record);
    }
}