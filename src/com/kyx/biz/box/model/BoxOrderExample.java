package com.kyx.biz.box.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BoxOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BoxOrderExample() {
        oredCriteria = new ArrayList<Criteria>();
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
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
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

        public Criteria andCustomIdIsNull() {
            addCriterion("custom_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomIdIsNotNull() {
            addCriterion("custom_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomIdEqualTo(Integer value) {
            addCriterion("custom_id =", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdNotEqualTo(Integer value) {
            addCriterion("custom_id <>", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdGreaterThan(Integer value) {
            addCriterion("custom_id >", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("custom_id >=", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdLessThan(Integer value) {
            addCriterion("custom_id <", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdLessThanOrEqualTo(Integer value) {
            addCriterion("custom_id <=", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdIn(List<Integer> values) {
            addCriterion("custom_id in", values, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdNotIn(List<Integer> values) {
            addCriterion("custom_id not in", values, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdBetween(Integer value1, Integer value2) {
            addCriterion("custom_id between", value1, value2, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdNotBetween(Integer value1, Integer value2) {
            addCriterion("custom_id not between", value1, value2, "customId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Integer value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Integer value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Integer value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Integer value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Integer value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Integer> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Integer> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Integer value1, Integer value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andServiceIdIsNull() {
            addCriterion("service_id is null");
            return (Criteria) this;
        }

        public Criteria andServiceIdIsNotNull() {
            addCriterion("service_id is not null");
            return (Criteria) this;
        }

        public Criteria andServiceIdEqualTo(Integer value) {
            addCriterion("service_id =", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotEqualTo(Integer value) {
            addCriterion("service_id <>", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdGreaterThan(Integer value) {
            addCriterion("service_id >", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("service_id >=", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdLessThan(Integer value) {
            addCriterion("service_id <", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdLessThanOrEqualTo(Integer value) {
            addCriterion("service_id <=", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdIn(List<Integer> values) {
            addCriterion("service_id in", values, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotIn(List<Integer> values) {
            addCriterion("service_id not in", values, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdBetween(Integer value1, Integer value2) {
            addCriterion("service_id between", value1, value2, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("service_id not between", value1, value2, "serviceId");
            return (Criteria) this;
        }

        public Criteria andCustomImgNameIsNull() {
            addCriterion("custom_img_name is null");
            return (Criteria) this;
        }

        public Criteria andCustomImgNameIsNotNull() {
            addCriterion("custom_img_name is not null");
            return (Criteria) this;
        }

        public Criteria andCustomImgNameEqualTo(String value) {
            addCriterion("custom_img_name =", value, "customImgName");
            return (Criteria) this;
        }

        public Criteria andCustomImgNameNotEqualTo(String value) {
            addCriterion("custom_img_name <>", value, "customImgName");
            return (Criteria) this;
        }

        public Criteria andCustomImgNameGreaterThan(String value) {
            addCriterion("custom_img_name >", value, "customImgName");
            return (Criteria) this;
        }

        public Criteria andCustomImgNameGreaterThanOrEqualTo(String value) {
            addCriterion("custom_img_name >=", value, "customImgName");
            return (Criteria) this;
        }

        public Criteria andCustomImgNameLessThan(String value) {
            addCriterion("custom_img_name <", value, "customImgName");
            return (Criteria) this;
        }

        public Criteria andCustomImgNameLessThanOrEqualTo(String value) {
            addCriterion("custom_img_name <=", value, "customImgName");
            return (Criteria) this;
        }

        public Criteria andCustomImgNameLike(String value) {
            addCriterion("custom_img_name like", value, "customImgName");
            return (Criteria) this;
        }

        public Criteria andCustomImgNameNotLike(String value) {
            addCriterion("custom_img_name not like", value, "customImgName");
            return (Criteria) this;
        }

        public Criteria andCustomImgNameIn(List<String> values) {
            addCriterion("custom_img_name in", values, "customImgName");
            return (Criteria) this;
        }

        public Criteria andCustomImgNameNotIn(List<String> values) {
            addCriterion("custom_img_name not in", values, "customImgName");
            return (Criteria) this;
        }

        public Criteria andCustomImgNameBetween(String value1, String value2) {
            addCriterion("custom_img_name between", value1, value2, "customImgName");
            return (Criteria) this;
        }

        public Criteria andCustomImgNameNotBetween(String value1, String value2) {
            addCriterion("custom_img_name not between", value1, value2, "customImgName");
            return (Criteria) this;
        }

        public Criteria andCustomRemarkIsNull() {
            addCriterion("custom_remark is null");
            return (Criteria) this;
        }

        public Criteria andCustomRemarkIsNotNull() {
            addCriterion("custom_remark is not null");
            return (Criteria) this;
        }

        public Criteria andCustomRemarkEqualTo(String value) {
            addCriterion("custom_remark =", value, "customRemark");
            return (Criteria) this;
        }

        public Criteria andCustomRemarkNotEqualTo(String value) {
            addCriterion("custom_remark <>", value, "customRemark");
            return (Criteria) this;
        }

        public Criteria andCustomRemarkGreaterThan(String value) {
            addCriterion("custom_remark >", value, "customRemark");
            return (Criteria) this;
        }

        public Criteria andCustomRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("custom_remark >=", value, "customRemark");
            return (Criteria) this;
        }

        public Criteria andCustomRemarkLessThan(String value) {
            addCriterion("custom_remark <", value, "customRemark");
            return (Criteria) this;
        }

        public Criteria andCustomRemarkLessThanOrEqualTo(String value) {
            addCriterion("custom_remark <=", value, "customRemark");
            return (Criteria) this;
        }

        public Criteria andCustomRemarkLike(String value) {
            addCriterion("custom_remark like", value, "customRemark");
            return (Criteria) this;
        }

        public Criteria andCustomRemarkNotLike(String value) {
            addCriterion("custom_remark not like", value, "customRemark");
            return (Criteria) this;
        }

        public Criteria andCustomRemarkIn(List<String> values) {
            addCriterion("custom_remark in", values, "customRemark");
            return (Criteria) this;
        }

        public Criteria andCustomRemarkNotIn(List<String> values) {
            addCriterion("custom_remark not in", values, "customRemark");
            return (Criteria) this;
        }

        public Criteria andCustomRemarkBetween(String value1, String value2) {
            addCriterion("custom_remark between", value1, value2, "customRemark");
            return (Criteria) this;
        }

        public Criteria andCustomRemarkNotBetween(String value1, String value2) {
            addCriterion("custom_remark not between", value1, value2, "customRemark");
            return (Criteria) this;
        }

        public Criteria andWorkerAddressImgUrlIsNull() {
            addCriterion("worker_address_img_url is null");
            return (Criteria) this;
        }

        public Criteria andWorkerAddressImgUrlIsNotNull() {
            addCriterion("worker_address_img_url is not null");
            return (Criteria) this;
        }

        public Criteria andWorkerAddressImgUrlEqualTo(String value) {
            addCriterion("worker_address_img_url =", value, "workerAddressImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerAddressImgUrlNotEqualTo(String value) {
            addCriterion("worker_address_img_url <>", value, "workerAddressImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerAddressImgUrlGreaterThan(String value) {
            addCriterion("worker_address_img_url >", value, "workerAddressImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerAddressImgUrlGreaterThanOrEqualTo(String value) {
            addCriterion("worker_address_img_url >=", value, "workerAddressImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerAddressImgUrlLessThan(String value) {
            addCriterion("worker_address_img_url <", value, "workerAddressImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerAddressImgUrlLessThanOrEqualTo(String value) {
            addCriterion("worker_address_img_url <=", value, "workerAddressImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerAddressImgUrlLike(String value) {
            addCriterion("worker_address_img_url like", value, "workerAddressImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerAddressImgUrlNotLike(String value) {
            addCriterion("worker_address_img_url not like", value, "workerAddressImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerAddressImgUrlIn(List<String> values) {
            addCriterion("worker_address_img_url in", values, "workerAddressImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerAddressImgUrlNotIn(List<String> values) {
            addCriterion("worker_address_img_url not in", values, "workerAddressImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerAddressImgUrlBetween(String value1, String value2) {
            addCriterion("worker_address_img_url between", value1, value2, "workerAddressImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerAddressImgUrlNotBetween(String value1, String value2) {
            addCriterion("worker_address_img_url not between", value1, value2, "workerAddressImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerImgUrlIsNull() {
            addCriterion("worker_img_url is null");
            return (Criteria) this;
        }

        public Criteria andWorkerImgUrlIsNotNull() {
            addCriterion("worker_img_url is not null");
            return (Criteria) this;
        }

        public Criteria andWorkerImgUrlEqualTo(String value) {
            addCriterion("worker_img_url =", value, "workerImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerImgUrlNotEqualTo(String value) {
            addCriterion("worker_img_url <>", value, "workerImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerImgUrlGreaterThan(String value) {
            addCriterion("worker_img_url >", value, "workerImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerImgUrlGreaterThanOrEqualTo(String value) {
            addCriterion("worker_img_url >=", value, "workerImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerImgUrlLessThan(String value) {
            addCriterion("worker_img_url <", value, "workerImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerImgUrlLessThanOrEqualTo(String value) {
            addCriterion("worker_img_url <=", value, "workerImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerImgUrlLike(String value) {
            addCriterion("worker_img_url like", value, "workerImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerImgUrlNotLike(String value) {
            addCriterion("worker_img_url not like", value, "workerImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerImgUrlIn(List<String> values) {
            addCriterion("worker_img_url in", values, "workerImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerImgUrlNotIn(List<String> values) {
            addCriterion("worker_img_url not in", values, "workerImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerImgUrlBetween(String value1, String value2) {
            addCriterion("worker_img_url between", value1, value2, "workerImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerImgUrlNotBetween(String value1, String value2) {
            addCriterion("worker_img_url not between", value1, value2, "workerImgUrl");
            return (Criteria) this;
        }

        public Criteria andWorkerRemarkIsNull() {
            addCriterion("worker_remark is null");
            return (Criteria) this;
        }

        public Criteria andWorkerRemarkIsNotNull() {
            addCriterion("worker_remark is not null");
            return (Criteria) this;
        }

        public Criteria andWorkerRemarkEqualTo(String value) {
            addCriterion("worker_remark =", value, "workerRemark");
            return (Criteria) this;
        }

        public Criteria andWorkerRemarkNotEqualTo(String value) {
            addCriterion("worker_remark <>", value, "workerRemark");
            return (Criteria) this;
        }

        public Criteria andWorkerRemarkGreaterThan(String value) {
            addCriterion("worker_remark >", value, "workerRemark");
            return (Criteria) this;
        }

        public Criteria andWorkerRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("worker_remark >=", value, "workerRemark");
            return (Criteria) this;
        }

        public Criteria andWorkerRemarkLessThan(String value) {
            addCriterion("worker_remark <", value, "workerRemark");
            return (Criteria) this;
        }

        public Criteria andWorkerRemarkLessThanOrEqualTo(String value) {
            addCriterion("worker_remark <=", value, "workerRemark");
            return (Criteria) this;
        }

        public Criteria andWorkerRemarkLike(String value) {
            addCriterion("worker_remark like", value, "workerRemark");
            return (Criteria) this;
        }

        public Criteria andWorkerRemarkNotLike(String value) {
            addCriterion("worker_remark not like", value, "workerRemark");
            return (Criteria) this;
        }

        public Criteria andWorkerRemarkIn(List<String> values) {
            addCriterion("worker_remark in", values, "workerRemark");
            return (Criteria) this;
        }

        public Criteria andWorkerRemarkNotIn(List<String> values) {
            addCriterion("worker_remark not in", values, "workerRemark");
            return (Criteria) this;
        }

        public Criteria andWorkerRemarkBetween(String value1, String value2) {
            addCriterion("worker_remark between", value1, value2, "workerRemark");
            return (Criteria) this;
        }

        public Criteria andWorkerRemarkNotBetween(String value1, String value2) {
            addCriterion("worker_remark not between", value1, value2, "workerRemark");
            return (Criteria) this;
        }

        public Criteria andWorkerIdIsNull() {
            addCriterion("worker_id is null");
            return (Criteria) this;
        }

        public Criteria andWorkerIdIsNotNull() {
            addCriterion("worker_id is not null");
            return (Criteria) this;
        }

        public Criteria andWorkerIdEqualTo(Integer value) {
            addCriterion("worker_id =", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdNotEqualTo(Integer value) {
            addCriterion("worker_id <>", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdGreaterThan(Integer value) {
            addCriterion("worker_id >", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("worker_id >=", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdLessThan(Integer value) {
            addCriterion("worker_id <", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdLessThanOrEqualTo(Integer value) {
            addCriterion("worker_id <=", value, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdIn(List<Integer> values) {
            addCriterion("worker_id in", values, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdNotIn(List<Integer> values) {
            addCriterion("worker_id not in", values, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdBetween(Integer value1, Integer value2) {
            addCriterion("worker_id between", value1, value2, "workerId");
            return (Criteria) this;
        }

        public Criteria andWorkerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("worker_id not between", value1, value2, "workerId");
            return (Criteria) this;
        }

        public Criteria andCustomPhoneIsNull() {
            addCriterion("custom_phone is null");
            return (Criteria) this;
        }

        public Criteria andCustomPhoneIsNotNull() {
            addCriterion("custom_phone is not null");
            return (Criteria) this;
        }

        public Criteria andCustomPhoneEqualTo(String value) {
            addCriterion("custom_phone =", value, "customPhone");
            return (Criteria) this;
        }

        public Criteria andCustomPhoneNotEqualTo(String value) {
            addCriterion("custom_phone <>", value, "customPhone");
            return (Criteria) this;
        }

        public Criteria andCustomPhoneGreaterThan(String value) {
            addCriterion("custom_phone >", value, "customPhone");
            return (Criteria) this;
        }

        public Criteria andCustomPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("custom_phone >=", value, "customPhone");
            return (Criteria) this;
        }

        public Criteria andCustomPhoneLessThan(String value) {
            addCriterion("custom_phone <", value, "customPhone");
            return (Criteria) this;
        }

        public Criteria andCustomPhoneLessThanOrEqualTo(String value) {
            addCriterion("custom_phone <=", value, "customPhone");
            return (Criteria) this;
        }

        public Criteria andCustomPhoneLike(String value) {
            addCriterion("custom_phone like", value, "customPhone");
            return (Criteria) this;
        }

        public Criteria andCustomPhoneNotLike(String value) {
            addCriterion("custom_phone not like", value, "customPhone");
            return (Criteria) this;
        }

        public Criteria andCustomPhoneIn(List<String> values) {
            addCriterion("custom_phone in", values, "customPhone");
            return (Criteria) this;
        }

        public Criteria andCustomPhoneNotIn(List<String> values) {
            addCriterion("custom_phone not in", values, "customPhone");
            return (Criteria) this;
        }

        public Criteria andCustomPhoneBetween(String value1, String value2) {
            addCriterion("custom_phone between", value1, value2, "customPhone");
            return (Criteria) this;
        }

        public Criteria andCustomPhoneNotBetween(String value1, String value2) {
            addCriterion("custom_phone not between", value1, value2, "customPhone");
            return (Criteria) this;
        }

        public Criteria andCarNumberIsNull() {
            addCriterion("car_number is null");
            return (Criteria) this;
        }

        public Criteria andCarNumberIsNotNull() {
            addCriterion("car_number is not null");
            return (Criteria) this;
        }

        public Criteria andCarNumberEqualTo(String value) {
            addCriterion("car_number =", value, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberNotEqualTo(String value) {
            addCriterion("car_number <>", value, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberGreaterThan(String value) {
            addCriterion("car_number >", value, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberGreaterThanOrEqualTo(String value) {
            addCriterion("car_number >=", value, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberLessThan(String value) {
            addCriterion("car_number <", value, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberLessThanOrEqualTo(String value) {
            addCriterion("car_number <=", value, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberLike(String value) {
            addCriterion("car_number like", value, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberNotLike(String value) {
            addCriterion("car_number not like", value, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberIn(List<String> values) {
            addCriterion("car_number in", values, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberNotIn(List<String> values) {
            addCriterion("car_number not in", values, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberBetween(String value1, String value2) {
            addCriterion("car_number between", value1, value2, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarNumberNotBetween(String value1, String value2) {
            addCriterion("car_number not between", value1, value2, "carNumber");
            return (Criteria) this;
        }

        public Criteria andCarBrandIsNull() {
            addCriterion("car_brand is null");
            return (Criteria) this;
        }

        public Criteria andCarBrandIsNotNull() {
            addCriterion("car_brand is not null");
            return (Criteria) this;
        }

        public Criteria andCarBrandEqualTo(String value) {
            addCriterion("car_brand =", value, "carBrand");
            return (Criteria) this;
        }

        public Criteria andCarBrandNotEqualTo(String value) {
            addCriterion("car_brand <>", value, "carBrand");
            return (Criteria) this;
        }

        public Criteria andCarBrandGreaterThan(String value) {
            addCriterion("car_brand >", value, "carBrand");
            return (Criteria) this;
        }

        public Criteria andCarBrandGreaterThanOrEqualTo(String value) {
            addCriterion("car_brand >=", value, "carBrand");
            return (Criteria) this;
        }

        public Criteria andCarBrandLessThan(String value) {
            addCriterion("car_brand <", value, "carBrand");
            return (Criteria) this;
        }

        public Criteria andCarBrandLessThanOrEqualTo(String value) {
            addCriterion("car_brand <=", value, "carBrand");
            return (Criteria) this;
        }

        public Criteria andCarBrandLike(String value) {
            addCriterion("car_brand like", value, "carBrand");
            return (Criteria) this;
        }

        public Criteria andCarBrandNotLike(String value) {
            addCriterion("car_brand not like", value, "carBrand");
            return (Criteria) this;
        }

        public Criteria andCarBrandIn(List<String> values) {
            addCriterion("car_brand in", values, "carBrand");
            return (Criteria) this;
        }

        public Criteria andCarBrandNotIn(List<String> values) {
            addCriterion("car_brand not in", values, "carBrand");
            return (Criteria) this;
        }

        public Criteria andCarBrandBetween(String value1, String value2) {
            addCriterion("car_brand between", value1, value2, "carBrand");
            return (Criteria) this;
        }

        public Criteria andCarBrandNotBetween(String value1, String value2) {
            addCriterion("car_brand not between", value1, value2, "carBrand");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeIsNull() {
            addCriterion("accept_time is null");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeIsNotNull() {
            addCriterion("accept_time is not null");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeEqualTo(Date value) {
            addCriterion("accept_time =", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeNotEqualTo(Date value) {
            addCriterion("accept_time <>", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeGreaterThan(Date value) {
            addCriterion("accept_time >", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("accept_time >=", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeLessThan(Date value) {
            addCriterion("accept_time <", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeLessThanOrEqualTo(Date value) {
            addCriterion("accept_time <=", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeIn(List<Date> values) {
            addCriterion("accept_time in", values, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeNotIn(List<Date> values) {
            addCriterion("accept_time not in", values, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeBetween(Date value1, Date value2) {
            addCriterion("accept_time between", value1, value2, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeNotBetween(Date value1, Date value2) {
            addCriterion("accept_time not between", value1, value2, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andWorkEndTimeIsNull() {
            addCriterion("work_end_time is null");
            return (Criteria) this;
        }

        public Criteria andWorkEndTimeIsNotNull() {
            addCriterion("work_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andWorkEndTimeEqualTo(Date value) {
            addCriterion("work_end_time =", value, "workEndTime");
            return (Criteria) this;
        }

        public Criteria andWorkEndTimeNotEqualTo(Date value) {
            addCriterion("work_end_time <>", value, "workEndTime");
            return (Criteria) this;
        }

        public Criteria andWorkEndTimeGreaterThan(Date value) {
            addCriterion("work_end_time >", value, "workEndTime");
            return (Criteria) this;
        }

        public Criteria andWorkEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("work_end_time >=", value, "workEndTime");
            return (Criteria) this;
        }

        public Criteria andWorkEndTimeLessThan(Date value) {
            addCriterion("work_end_time <", value, "workEndTime");
            return (Criteria) this;
        }

        public Criteria andWorkEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("work_end_time <=", value, "workEndTime");
            return (Criteria) this;
        }

        public Criteria andWorkEndTimeIn(List<Date> values) {
            addCriterion("work_end_time in", values, "workEndTime");
            return (Criteria) this;
        }

        public Criteria andWorkEndTimeNotIn(List<Date> values) {
            addCriterion("work_end_time not in", values, "workEndTime");
            return (Criteria) this;
        }

        public Criteria andWorkEndTimeBetween(Date value1, Date value2) {
            addCriterion("work_end_time between", value1, value2, "workEndTime");
            return (Criteria) this;
        }

        public Criteria andWorkEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("work_end_time not between", value1, value2, "workEndTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andLockIdIsNull() {
            addCriterion("lock_id is null");
            return (Criteria) this;
        }

        public Criteria andLockIdIsNotNull() {
            addCriterion("lock_id is not null");
            return (Criteria) this;
        }

        public Criteria andLockIdEqualTo(Integer value) {
            addCriterion("lock_id =", value, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdNotEqualTo(Integer value) {
            addCriterion("lock_id <>", value, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdGreaterThan(Integer value) {
            addCriterion("lock_id >", value, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("lock_id >=", value, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdLessThan(Integer value) {
            addCriterion("lock_id <", value, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdLessThanOrEqualTo(Integer value) {
            addCriterion("lock_id <=", value, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdIn(List<Integer> values) {
            addCriterion("lock_id in", values, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdNotIn(List<Integer> values) {
            addCriterion("lock_id not in", values, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdBetween(Integer value1, Integer value2) {
            addCriterion("lock_id between", value1, value2, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdNotBetween(Integer value1, Integer value2) {
            addCriterion("lock_id not between", value1, value2, "lockId");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}