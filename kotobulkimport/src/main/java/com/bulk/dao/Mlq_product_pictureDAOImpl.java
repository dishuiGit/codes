package com.bulk.dao;

import com.bulk.pojo.Mlq_product_picture;
import com.bulk.pojo.Mlq_product_pictureQuery;
import com.ibatis.sqlmap.client.SqlMapClient;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class Mlq_product_pictureDAOImpl extends SqlMapClientDaoSupport implements Mlq_product_pictureDAO {

    @Autowired
    public Mlq_product_pictureDAOImpl(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }

    public int countByExample(Mlq_product_pictureQuery example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("mlq_product_picture_countByExample", example);
        return count;
    }

    public int deleteByExample(Mlq_product_pictureQuery example) {
        int rows = getSqlMapClientTemplate().delete("mlq_product_picture_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Integer id) {
        Mlq_product_picture _key = new Mlq_product_picture();
        _key.setId(id);
        int rows = getSqlMapClientTemplate().delete("mlq_product_picture_deleteByPrimaryKey", _key);
        return rows;
    }

    public void insert(Mlq_product_picture record) {
        getSqlMapClientTemplate().insert("mlq_product_picture_insert", record);
    }

    public void insertSelective(Mlq_product_picture record) {
        getSqlMapClientTemplate().insert("mlq_product_picture_insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<Mlq_product_picture> selectByExampleWithBLOBs(Mlq_product_pictureQuery example) {
        List<Mlq_product_picture> list = getSqlMapClientTemplate().queryForList("mlq_product_picture_selectByExampleWithBLOBs", example);
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<Mlq_product_picture> selectByExampleWithoutBLOBs(Mlq_product_pictureQuery example) {
        List<Mlq_product_picture> list = getSqlMapClientTemplate().queryForList("mlq_product_picture_selectByExample", example);
        return list;
    }

    public Mlq_product_picture selectByPrimaryKey(Integer id) {
        Mlq_product_picture _key = new Mlq_product_picture();
        _key.setId(id);
        Mlq_product_picture record = (Mlq_product_picture) getSqlMapClientTemplate().queryForObject("mlq_product_picture_selectByPrimaryKey", _key);
        return record;
    }

    public int updateByExampleSelective(Mlq_product_picture record, Mlq_product_pictureQuery example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("mlq_product_picture_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExampleWithBLOBs(Mlq_product_picture record, Mlq_product_pictureQuery example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("mlq_product_picture_updateByExampleWithBLOBs", parms);
        return rows;
    }

    public int updateByExampleWithoutBLOBs(Mlq_product_picture record, Mlq_product_pictureQuery example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("mlq_product_picture_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(Mlq_product_picture record) {
        int rows = getSqlMapClientTemplate().update("mlq_product_picture_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKeyWithBLOBs(Mlq_product_picture record) {
        int rows = getSqlMapClientTemplate().update("mlq_product_picture_updateByPrimaryKeyWithBLOBs", record);
        return rows;
    }

    public int updateByPrimaryKeyWithoutBLOBs(Mlq_product_picture record) {
        int rows = getSqlMapClientTemplate().update("mlq_product_picture_updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends Mlq_product_pictureQuery {
        private Object record;

        public UpdateByExampleParms(Object record, Mlq_product_pictureQuery example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}