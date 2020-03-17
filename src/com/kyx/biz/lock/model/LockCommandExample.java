package com.kyx.biz.lock.model;

import java.util.ArrayList;
import java.util.List;

public class LockCommandExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LockCommandExample() {
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

        public Criteria andCommandIsNull() {
            addCriterion("command is null");
            return (Criteria) this;
        }

        public Criteria andCommandIsNotNull() {
            addCriterion("command is not null");
            return (Criteria) this;
        }

        public Criteria andCommandEqualTo(String value) {
            addCriterion("command =", value, "command");
            return (Criteria) this;
        }

        public Criteria andCommandNotEqualTo(String value) {
            addCriterion("command <>", value, "command");
            return (Criteria) this;
        }

        public Criteria andCommandGreaterThan(String value) {
            addCriterion("command >", value, "command");
            return (Criteria) this;
        }

        public Criteria andCommandGreaterThanOrEqualTo(String value) {
            addCriterion("command >=", value, "command");
            return (Criteria) this;
        }

        public Criteria andCommandLessThan(String value) {
            addCriterion("command <", value, "command");
            return (Criteria) this;
        }

        public Criteria andCommandLessThanOrEqualTo(String value) {
            addCriterion("command <=", value, "command");
            return (Criteria) this;
        }

        public Criteria andCommandLike(String value) {
            addCriterion("command like", value, "command");
            return (Criteria) this;
        }

        public Criteria andCommandNotLike(String value) {
            addCriterion("command not like", value, "command");
            return (Criteria) this;
        }

        public Criteria andCommandIn(List<String> values) {
            addCriterion("command in", values, "command");
            return (Criteria) this;
        }

        public Criteria andCommandNotIn(List<String> values) {
            addCriterion("command not in", values, "command");
            return (Criteria) this;
        }

        public Criteria andCommandBetween(String value1, String value2) {
            addCriterion("command between", value1, value2, "command");
            return (Criteria) this;
        }

        public Criteria andCommandNotBetween(String value1, String value2) {
            addCriterion("command not between", value1, value2, "command");
            return (Criteria) this;
        }

        public Criteria andLockNumberIsNull() {
            addCriterion("lock_number is null");
            return (Criteria) this;
        }

        public Criteria andLockNumberIsNotNull() {
            addCriterion("lock_number is not null");
            return (Criteria) this;
        }

        public Criteria andLockNumberEqualTo(Integer value) {
            addCriterion("lock_number =", value, "lockNumber");
            return (Criteria) this;
        }

        public Criteria andLockNumberNotEqualTo(Integer value) {
            addCriterion("lock_number <>", value, "lockNumber");
            return (Criteria) this;
        }

        public Criteria andLockNumberGreaterThan(Integer value) {
            addCriterion("lock_number >", value, "lockNumber");
            return (Criteria) this;
        }

        public Criteria andLockNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("lock_number >=", value, "lockNumber");
            return (Criteria) this;
        }

        public Criteria andLockNumberLessThan(Integer value) {
            addCriterion("lock_number <", value, "lockNumber");
            return (Criteria) this;
        }

        public Criteria andLockNumberLessThanOrEqualTo(Integer value) {
            addCriterion("lock_number <=", value, "lockNumber");
            return (Criteria) this;
        }

        public Criteria andLockNumberIn(List<Integer> values) {
            addCriterion("lock_number in", values, "lockNumber");
            return (Criteria) this;
        }

        public Criteria andLockNumberNotIn(List<Integer> values) {
            addCriterion("lock_number not in", values, "lockNumber");
            return (Criteria) this;
        }

        public Criteria andLockNumberBetween(Integer value1, Integer value2) {
            addCriterion("lock_number between", value1, value2, "lockNumber");
            return (Criteria) this;
        }

        public Criteria andLockNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("lock_number not between", value1, value2, "lockNumber");
            return (Criteria) this;
        }

        public Criteria andBoxAddressIsNull() {
            addCriterion("box_address is null");
            return (Criteria) this;
        }

        public Criteria andBoxAddressIsNotNull() {
            addCriterion("box_address is not null");
            return (Criteria) this;
        }

        public Criteria andBoxAddressEqualTo(Integer value) {
            addCriterion("box_address =", value, "boxAddress");
            return (Criteria) this;
        }

        public Criteria andBoxAddressNotEqualTo(Integer value) {
            addCriterion("box_address <>", value, "boxAddress");
            return (Criteria) this;
        }

        public Criteria andBoxAddressGreaterThan(Integer value) {
            addCriterion("box_address >", value, "boxAddress");
            return (Criteria) this;
        }

        public Criteria andBoxAddressGreaterThanOrEqualTo(Integer value) {
            addCriterion("box_address >=", value, "boxAddress");
            return (Criteria) this;
        }

        public Criteria andBoxAddressLessThan(Integer value) {
            addCriterion("box_address <", value, "boxAddress");
            return (Criteria) this;
        }

        public Criteria andBoxAddressLessThanOrEqualTo(Integer value) {
            addCriterion("box_address <=", value, "boxAddress");
            return (Criteria) this;
        }

        public Criteria andBoxAddressIn(List<Integer> values) {
            addCriterion("box_address in", values, "boxAddress");
            return (Criteria) this;
        }

        public Criteria andBoxAddressNotIn(List<Integer> values) {
            addCriterion("box_address not in", values, "boxAddress");
            return (Criteria) this;
        }

        public Criteria andBoxAddressBetween(Integer value1, Integer value2) {
            addCriterion("box_address between", value1, value2, "boxAddress");
            return (Criteria) this;
        }

        public Criteria andBoxAddressNotBetween(Integer value1, Integer value2) {
            addCriterion("box_address not between", value1, value2, "boxAddress");
            return (Criteria) this;
        }

        public Criteria andLockAddressIsNull() {
            addCriterion("lock_address is null");
            return (Criteria) this;
        }

        public Criteria andLockAddressIsNotNull() {
            addCriterion("lock_address is not null");
            return (Criteria) this;
        }

        public Criteria andLockAddressEqualTo(String value) {
            addCriterion("lock_address =", value, "lockAddress");
            return (Criteria) this;
        }

        public Criteria andLockAddressNotEqualTo(String value) {
            addCriterion("lock_address <>", value, "lockAddress");
            return (Criteria) this;
        }

        public Criteria andLockAddressGreaterThan(String value) {
            addCriterion("lock_address >", value, "lockAddress");
            return (Criteria) this;
        }

        public Criteria andLockAddressGreaterThanOrEqualTo(String value) {
            addCriterion("lock_address >=", value, "lockAddress");
            return (Criteria) this;
        }

        public Criteria andLockAddressLessThan(String value) {
            addCriterion("lock_address <", value, "lockAddress");
            return (Criteria) this;
        }

        public Criteria andLockAddressLessThanOrEqualTo(String value) {
            addCriterion("lock_address <=", value, "lockAddress");
            return (Criteria) this;
        }

        public Criteria andLockAddressLike(String value) {
            addCriterion("lock_address like", value, "lockAddress");
            return (Criteria) this;
        }

        public Criteria andLockAddressNotLike(String value) {
            addCriterion("lock_address not like", value, "lockAddress");
            return (Criteria) this;
        }

        public Criteria andLockAddressIn(List<String> values) {
            addCriterion("lock_address in", values, "lockAddress");
            return (Criteria) this;
        }

        public Criteria andLockAddressNotIn(List<String> values) {
            addCriterion("lock_address not in", values, "lockAddress");
            return (Criteria) this;
        }

        public Criteria andLockAddressBetween(String value1, String value2) {
            addCriterion("lock_address between", value1, value2, "lockAddress");
            return (Criteria) this;
        }

        public Criteria andLockAddressNotBetween(String value1, String value2) {
            addCriterion("lock_address not between", value1, value2, "lockAddress");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNull() {
            addCriterion("enabled is null");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNotNull() {
            addCriterion("enabled is not null");
            return (Criteria) this;
        }

        public Criteria andEnabledEqualTo(Integer value) {
            addCriterion("enabled =", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotEqualTo(Integer value) {
            addCriterion("enabled <>", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThan(Integer value) {
            addCriterion("enabled >", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThanOrEqualTo(Integer value) {
            addCriterion("enabled >=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThan(Integer value) {
            addCriterion("enabled <", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThanOrEqualTo(Integer value) {
            addCriterion("enabled <=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledIn(List<Integer> values) {
            addCriterion("enabled in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotIn(List<Integer> values) {
            addCriterion("enabled not in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledBetween(Integer value1, Integer value2) {
            addCriterion("enabled between", value1, value2, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotBetween(Integer value1, Integer value2) {
            addCriterion("enabled not between", value1, value2, "enabled");
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