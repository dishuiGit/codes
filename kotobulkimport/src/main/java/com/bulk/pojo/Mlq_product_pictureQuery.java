package com.bulk.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mlq_product_pictureQuery {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public Mlq_product_pictureQuery() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected Mlq_product_pictureQuery(Mlq_product_pictureQuery example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.distinct = example.distinct;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<String> criteriaWithoutValue;

        protected List<Map<String, Object>> criteriaWithSingleValue;

        protected List<Map<String, Object>> criteriaWithListValue;

        protected List<Map<String, Object>> criteriaWithBetweenValue;

        protected GeneratedCriteria() {
            super();
            criteriaWithoutValue = new ArrayList<String>();
            criteriaWithSingleValue = new ArrayList<Map<String, Object>>();
            criteriaWithListValue = new ArrayList<Map<String, Object>>();
            criteriaWithBetweenValue = new ArrayList<Map<String, Object>>();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0;
        }

        public List<String> getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public List<Map<String, Object>> getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public List<Map<String, Object>> getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public List<Map<String, Object>> getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List<? extends Object> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List<Object> list = new ArrayList<Object>();
            list.add(value1);
            list.add(value2);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andImgLargeIsNull() {
            addCriterion("img_large is null");
            return (Criteria) this;
        }

        public Criteria andImgLargeIsNotNull() {
            addCriterion("img_large is not null");
            return (Criteria) this;
        }

        public Criteria andImgLargeEqualTo(String value) {
            addCriterion("img_large =", value, "imgLarge");
            return (Criteria) this;
        }

        public Criteria andImgLargeNotEqualTo(String value) {
            addCriterion("img_large <>", value, "imgLarge");
            return (Criteria) this;
        }

        public Criteria andImgLargeGreaterThan(String value) {
            addCriterion("img_large >", value, "imgLarge");
            return (Criteria) this;
        }

        public Criteria andImgLargeGreaterThanOrEqualTo(String value) {
            addCriterion("img_large >=", value, "imgLarge");
            return (Criteria) this;
        }

        public Criteria andImgLargeLessThan(String value) {
            addCriterion("img_large <", value, "imgLarge");
            return (Criteria) this;
        }

        public Criteria andImgLargeLessThanOrEqualTo(String value) {
            addCriterion("img_large <=", value, "imgLarge");
            return (Criteria) this;
        }

        public Criteria andImgLargeLike(String value) {
            addCriterion("img_large like", value, "imgLarge");
            return (Criteria) this;
        }

        public Criteria andImgLargeNotLike(String value) {
            addCriterion("img_large not like", value, "imgLarge");
            return (Criteria) this;
        }

        public Criteria andImgLargeIn(List<String> values) {
            addCriterion("img_large in", values, "imgLarge");
            return (Criteria) this;
        }

        public Criteria andImgLargeNotIn(List<String> values) {
            addCriterion("img_large not in", values, "imgLarge");
            return (Criteria) this;
        }

        public Criteria andImgLargeBetween(String value1, String value2) {
            addCriterion("img_large between", value1, value2, "imgLarge");
            return (Criteria) this;
        }

        public Criteria andImgLargeNotBetween(String value1, String value2) {
            addCriterion("img_large not between", value1, value2, "imgLarge");
            return (Criteria) this;
        }

        public Criteria andImgMiddleIsNull() {
            addCriterion("img_middle is null");
            return (Criteria) this;
        }

        public Criteria andImgMiddleIsNotNull() {
            addCriterion("img_middle is not null");
            return (Criteria) this;
        }

        public Criteria andImgMiddleEqualTo(String value) {
            addCriterion("img_middle =", value, "imgMiddle");
            return (Criteria) this;
        }

        public Criteria andImgMiddleNotEqualTo(String value) {
            addCriterion("img_middle <>", value, "imgMiddle");
            return (Criteria) this;
        }

        public Criteria andImgMiddleGreaterThan(String value) {
            addCriterion("img_middle >", value, "imgMiddle");
            return (Criteria) this;
        }

        public Criteria andImgMiddleGreaterThanOrEqualTo(String value) {
            addCriterion("img_middle >=", value, "imgMiddle");
            return (Criteria) this;
        }

        public Criteria andImgMiddleLessThan(String value) {
            addCriterion("img_middle <", value, "imgMiddle");
            return (Criteria) this;
        }

        public Criteria andImgMiddleLessThanOrEqualTo(String value) {
            addCriterion("img_middle <=", value, "imgMiddle");
            return (Criteria) this;
        }

        public Criteria andImgMiddleLike(String value) {
            addCriterion("img_middle like", value, "imgMiddle");
            return (Criteria) this;
        }

        public Criteria andImgMiddleNotLike(String value) {
            addCriterion("img_middle not like", value, "imgMiddle");
            return (Criteria) this;
        }

        public Criteria andImgMiddleIn(List<String> values) {
            addCriterion("img_middle in", values, "imgMiddle");
            return (Criteria) this;
        }

        public Criteria andImgMiddleNotIn(List<String> values) {
            addCriterion("img_middle not in", values, "imgMiddle");
            return (Criteria) this;
        }

        public Criteria andImgMiddleBetween(String value1, String value2) {
            addCriterion("img_middle between", value1, value2, "imgMiddle");
            return (Criteria) this;
        }

        public Criteria andImgMiddleNotBetween(String value1, String value2) {
            addCriterion("img_middle not between", value1, value2, "imgMiddle");
            return (Criteria) this;
        }

        public Criteria andImgSmallIsNull() {
            addCriterion("img_small is null");
            return (Criteria) this;
        }

        public Criteria andImgSmallIsNotNull() {
            addCriterion("img_small is not null");
            return (Criteria) this;
        }

        public Criteria andImgSmallEqualTo(String value) {
            addCriterion("img_small =", value, "imgSmall");
            return (Criteria) this;
        }

        public Criteria andImgSmallNotEqualTo(String value) {
            addCriterion("img_small <>", value, "imgSmall");
            return (Criteria) this;
        }

        public Criteria andImgSmallGreaterThan(String value) {
            addCriterion("img_small >", value, "imgSmall");
            return (Criteria) this;
        }

        public Criteria andImgSmallGreaterThanOrEqualTo(String value) {
            addCriterion("img_small >=", value, "imgSmall");
            return (Criteria) this;
        }

        public Criteria andImgSmallLessThan(String value) {
            addCriterion("img_small <", value, "imgSmall");
            return (Criteria) this;
        }

        public Criteria andImgSmallLessThanOrEqualTo(String value) {
            addCriterion("img_small <=", value, "imgSmall");
            return (Criteria) this;
        }

        public Criteria andImgSmallLike(String value) {
            addCriterion("img_small like", value, "imgSmall");
            return (Criteria) this;
        }

        public Criteria andImgSmallNotLike(String value) {
            addCriterion("img_small not like", value, "imgSmall");
            return (Criteria) this;
        }

        public Criteria andImgSmallIn(List<String> values) {
            addCriterion("img_small in", values, "imgSmall");
            return (Criteria) this;
        }

        public Criteria andImgSmallNotIn(List<String> values) {
            addCriterion("img_small not in", values, "imgSmall");
            return (Criteria) this;
        }

        public Criteria andImgSmallBetween(String value1, String value2) {
            addCriterion("img_small between", value1, value2, "imgSmall");
            return (Criteria) this;
        }

        public Criteria andImgSmallNotBetween(String value1, String value2) {
            addCriterion("img_small not between", value1, value2, "imgSmall");
            return (Criteria) this;
        }

        public Criteria andImgOriginalIsNull() {
            addCriterion("img_original is null");
            return (Criteria) this;
        }

        public Criteria andImgOriginalIsNotNull() {
            addCriterion("img_original is not null");
            return (Criteria) this;
        }

        public Criteria andImgOriginalEqualTo(String value) {
            addCriterion("img_original =", value, "imgOriginal");
            return (Criteria) this;
        }

        public Criteria andImgOriginalNotEqualTo(String value) {
            addCriterion("img_original <>", value, "imgOriginal");
            return (Criteria) this;
        }

        public Criteria andImgOriginalGreaterThan(String value) {
            addCriterion("img_original >", value, "imgOriginal");
            return (Criteria) this;
        }

        public Criteria andImgOriginalGreaterThanOrEqualTo(String value) {
            addCriterion("img_original >=", value, "imgOriginal");
            return (Criteria) this;
        }

        public Criteria andImgOriginalLessThan(String value) {
            addCriterion("img_original <", value, "imgOriginal");
            return (Criteria) this;
        }

        public Criteria andImgOriginalLessThanOrEqualTo(String value) {
            addCriterion("img_original <=", value, "imgOriginal");
            return (Criteria) this;
        }

        public Criteria andImgOriginalLike(String value) {
            addCriterion("img_original like", value, "imgOriginal");
            return (Criteria) this;
        }

        public Criteria andImgOriginalNotLike(String value) {
            addCriterion("img_original not like", value, "imgOriginal");
            return (Criteria) this;
        }

        public Criteria andImgOriginalIn(List<String> values) {
            addCriterion("img_original in", values, "imgOriginal");
            return (Criteria) this;
        }

        public Criteria andImgOriginalNotIn(List<String> values) {
            addCriterion("img_original not in", values, "imgOriginal");
            return (Criteria) this;
        }

        public Criteria andImgOriginalBetween(String value1, String value2) {
            addCriterion("img_original between", value1, value2, "imgOriginal");
            return (Criteria) this;
        }

        public Criteria andImgOriginalNotBetween(String value1, String value2) {
            addCriterion("img_original not between", value1, value2, "imgOriginal");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtModifyIsNull() {
            addCriterion("gmt_modify is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifyIsNotNull() {
            addCriterion("gmt_modify is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifyEqualTo(Date value) {
            addCriterion("gmt_modify =", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyNotEqualTo(Date value) {
            addCriterion("gmt_modify <>", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyGreaterThan(Date value) {
            addCriterion("gmt_modify >", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modify >=", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyLessThan(Date value) {
            addCriterion("gmt_modify <", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modify <=", value, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyIn(List<Date> values) {
            addCriterion("gmt_modify in", values, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyNotIn(List<Date> values) {
            addCriterion("gmt_modify not in", values, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyBetween(Date value1, Date value2) {
            addCriterion("gmt_modify between", value1, value2, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGmtModifyNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modify not between", value1, value2, "gmtModify");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIsNull() {
            addCriterion("goods_id is null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIsNotNull() {
            addCriterion("goods_id is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdEqualTo(String value) {
            addCriterion("goods_id =", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotEqualTo(String value) {
            addCriterion("goods_id <>", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThan(String value) {
            addCriterion("goods_id >", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThanOrEqualTo(String value) {
            addCriterion("goods_id >=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThan(String value) {
            addCriterion("goods_id <", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThanOrEqualTo(String value) {
            addCriterion("goods_id <=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLike(String value) {
            addCriterion("goods_id like", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotLike(String value) {
            addCriterion("goods_id not like", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIn(List<String> values) {
            addCriterion("goods_id in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotIn(List<String> values) {
            addCriterion("goods_id not in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdBetween(String value1, String value2) {
            addCriterion("goods_id between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotBetween(String value1, String value2) {
            addCriterion("goods_id not between", value1, value2, "goodsId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}