package com.kyx.basic.shops.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ShopsExample() {
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

        public Criteria andShopNameIsNull() {
            addCriterion("shop_name is null");
            return (Criteria) this;
        }

        public Criteria andShopNameIsNotNull() {
            addCriterion("shop_name is not null");
            return (Criteria) this;
        }

        public Criteria andShopNameEqualTo(String value) {
            addCriterion("shop_name =", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotEqualTo(String value) {
            addCriterion("shop_name <>", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameGreaterThan(String value) {
            addCriterion("shop_name >", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameGreaterThanOrEqualTo(String value) {
            addCriterion("shop_name >=", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLessThan(String value) {
            addCriterion("shop_name <", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLessThanOrEqualTo(String value) {
            addCriterion("shop_name <=", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLike(String value) {
            addCriterion("shop_name like", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotLike(String value) {
            addCriterion("shop_name not like", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameIn(List<String> values) {
            addCriterion("shop_name in", values, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotIn(List<String> values) {
            addCriterion("shop_name not in", values, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameBetween(String value1, String value2) {
            addCriterion("shop_name between", value1, value2, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotBetween(String value1, String value2) {
            addCriterion("shop_name not between", value1, value2, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopAddressIsNull() {
            addCriterion("shop_address is null");
            return (Criteria) this;
        }

        public Criteria andShopAddressIsNotNull() {
            addCriterion("shop_address is not null");
            return (Criteria) this;
        }

        public Criteria andShopAddressEqualTo(String value) {
            addCriterion("shop_address =", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressNotEqualTo(String value) {
            addCriterion("shop_address <>", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressGreaterThan(String value) {
            addCriterion("shop_address >", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressGreaterThanOrEqualTo(String value) {
            addCriterion("shop_address >=", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressLessThan(String value) {
            addCriterion("shop_address <", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressLessThanOrEqualTo(String value) {
            addCriterion("shop_address <=", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressLike(String value) {
            addCriterion("shop_address like", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressNotLike(String value) {
            addCriterion("shop_address not like", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressIn(List<String> values) {
            addCriterion("shop_address in", values, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressNotIn(List<String> values) {
            addCriterion("shop_address not in", values, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressBetween(String value1, String value2) {
            addCriterion("shop_address between", value1, value2, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressNotBetween(String value1, String value2) {
            addCriterion("shop_address not between", value1, value2, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopTelIsNull() {
            addCriterion("shop_tel is null");
            return (Criteria) this;
        }

        public Criteria andShopTelIsNotNull() {
            addCriterion("shop_tel is not null");
            return (Criteria) this;
        }

        public Criteria andShopTelEqualTo(String value) {
            addCriterion("shop_tel =", value, "shopTel");
            return (Criteria) this;
        }

        public Criteria andShopTelNotEqualTo(String value) {
            addCriterion("shop_tel <>", value, "shopTel");
            return (Criteria) this;
        }

        public Criteria andShopTelGreaterThan(String value) {
            addCriterion("shop_tel >", value, "shopTel");
            return (Criteria) this;
        }

        public Criteria andShopTelGreaterThanOrEqualTo(String value) {
            addCriterion("shop_tel >=", value, "shopTel");
            return (Criteria) this;
        }

        public Criteria andShopTelLessThan(String value) {
            addCriterion("shop_tel <", value, "shopTel");
            return (Criteria) this;
        }

        public Criteria andShopTelLessThanOrEqualTo(String value) {
            addCriterion("shop_tel <=", value, "shopTel");
            return (Criteria) this;
        }

        public Criteria andShopTelLike(String value) {
            addCriterion("shop_tel like", value, "shopTel");
            return (Criteria) this;
        }

        public Criteria andShopTelNotLike(String value) {
            addCriterion("shop_tel not like", value, "shopTel");
            return (Criteria) this;
        }

        public Criteria andShopTelIn(List<String> values) {
            addCriterion("shop_tel in", values, "shopTel");
            return (Criteria) this;
        }

        public Criteria andShopTelNotIn(List<String> values) {
            addCriterion("shop_tel not in", values, "shopTel");
            return (Criteria) this;
        }

        public Criteria andShopTelBetween(String value1, String value2) {
            addCriterion("shop_tel between", value1, value2, "shopTel");
            return (Criteria) this;
        }

        public Criteria andShopTelNotBetween(String value1, String value2) {
            addCriterion("shop_tel not between", value1, value2, "shopTel");
            return (Criteria) this;
        }

        public Criteria andAccountStatusIsNull() {
            addCriterion("account_status is null");
            return (Criteria) this;
        }

        public Criteria andAccountStatusIsNotNull() {
            addCriterion("account_status is not null");
            return (Criteria) this;
        }

        public Criteria andAccountStatusEqualTo(String value) {
            addCriterion("account_status =", value, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusNotEqualTo(String value) {
            addCriterion("account_status <>", value, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusGreaterThan(String value) {
            addCriterion("account_status >", value, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusGreaterThanOrEqualTo(String value) {
            addCriterion("account_status >=", value, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusLessThan(String value) {
            addCriterion("account_status <", value, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusLessThanOrEqualTo(String value) {
            addCriterion("account_status <=", value, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusLike(String value) {
            addCriterion("account_status like", value, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusNotLike(String value) {
            addCriterion("account_status not like", value, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusIn(List<String> values) {
            addCriterion("account_status in", values, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusNotIn(List<String> values) {
            addCriterion("account_status not in", values, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusBetween(String value1, String value2) {
            addCriterion("account_status between", value1, value2, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andAccountStatusNotBetween(String value1, String value2) {
            addCriterion("account_status not between", value1, value2, "accountStatus");
            return (Criteria) this;
        }

        public Criteria andShopPhoneIsNull() {
            addCriterion("shop_phone is null");
            return (Criteria) this;
        }

        public Criteria andShopPhoneIsNotNull() {
            addCriterion("shop_phone is not null");
            return (Criteria) this;
        }

        public Criteria andShopPhoneEqualTo(String value) {
            addCriterion("shop_phone =", value, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneNotEqualTo(String value) {
            addCriterion("shop_phone <>", value, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneGreaterThan(String value) {
            addCriterion("shop_phone >", value, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("shop_phone >=", value, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneLessThan(String value) {
            addCriterion("shop_phone <", value, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneLessThanOrEqualTo(String value) {
            addCriterion("shop_phone <=", value, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneLike(String value) {
            addCriterion("shop_phone like", value, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneNotLike(String value) {
            addCriterion("shop_phone not like", value, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneIn(List<String> values) {
            addCriterion("shop_phone in", values, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneNotIn(List<String> values) {
            addCriterion("shop_phone not in", values, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneBetween(String value1, String value2) {
            addCriterion("shop_phone between", value1, value2, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneNotBetween(String value1, String value2) {
            addCriterion("shop_phone not between", value1, value2, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andFaTitleIsNull() {
            addCriterion("fa_title is null");
            return (Criteria) this;
        }

        public Criteria andFaTitleIsNotNull() {
            addCriterion("fa_title is not null");
            return (Criteria) this;
        }

        public Criteria andFaTitleEqualTo(String value) {
            addCriterion("fa_title =", value, "faTitle");
            return (Criteria) this;
        }

        public Criteria andFaTitleNotEqualTo(String value) {
            addCriterion("fa_title <>", value, "faTitle");
            return (Criteria) this;
        }

        public Criteria andFaTitleGreaterThan(String value) {
            addCriterion("fa_title >", value, "faTitle");
            return (Criteria) this;
        }

        public Criteria andFaTitleGreaterThanOrEqualTo(String value) {
            addCriterion("fa_title >=", value, "faTitle");
            return (Criteria) this;
        }

        public Criteria andFaTitleLessThan(String value) {
            addCriterion("fa_title <", value, "faTitle");
            return (Criteria) this;
        }

        public Criteria andFaTitleLessThanOrEqualTo(String value) {
            addCriterion("fa_title <=", value, "faTitle");
            return (Criteria) this;
        }

        public Criteria andFaTitleLike(String value) {
            addCriterion("fa_title like", value, "faTitle");
            return (Criteria) this;
        }

        public Criteria andFaTitleNotLike(String value) {
            addCriterion("fa_title not like", value, "faTitle");
            return (Criteria) this;
        }

        public Criteria andFaTitleIn(List<String> values) {
            addCriterion("fa_title in", values, "faTitle");
            return (Criteria) this;
        }

        public Criteria andFaTitleNotIn(List<String> values) {
            addCriterion("fa_title not in", values, "faTitle");
            return (Criteria) this;
        }

        public Criteria andFaTitleBetween(String value1, String value2) {
            addCriterion("fa_title between", value1, value2, "faTitle");
            return (Criteria) this;
        }

        public Criteria andFaTitleNotBetween(String value1, String value2) {
            addCriterion("fa_title not between", value1, value2, "faTitle");
            return (Criteria) this;
        }

        public Criteria andFaxIsNull() {
            addCriterion("fax is null");
            return (Criteria) this;
        }

        public Criteria andFaxIsNotNull() {
            addCriterion("fax is not null");
            return (Criteria) this;
        }

        public Criteria andFaxEqualTo(String value) {
            addCriterion("fax =", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotEqualTo(String value) {
            addCriterion("fax <>", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxGreaterThan(String value) {
            addCriterion("fax >", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxGreaterThanOrEqualTo(String value) {
            addCriterion("fax >=", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxLessThan(String value) {
            addCriterion("fax <", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxLessThanOrEqualTo(String value) {
            addCriterion("fax <=", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxLike(String value) {
            addCriterion("fax like", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotLike(String value) {
            addCriterion("fax not like", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxIn(List<String> values) {
            addCriterion("fax in", values, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotIn(List<String> values) {
            addCriterion("fax not in", values, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxBetween(String value1, String value2) {
            addCriterion("fax between", value1, value2, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotBetween(String value1, String value2) {
            addCriterion("fax not between", value1, value2, "fax");
            return (Criteria) this;
        }

        public Criteria andAccountEndIsNull() {
            addCriterion("account_end is null");
            return (Criteria) this;
        }

        public Criteria andAccountEndIsNotNull() {
            addCriterion("account_end is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEndEqualTo(Date value) {
            addCriterion("account_end =", value, "accountEnd");
            return (Criteria) this;
        }

        public Criteria andAccountEndNotEqualTo(Date value) {
            addCriterion("account_end <>", value, "accountEnd");
            return (Criteria) this;
        }

        public Criteria andAccountEndGreaterThan(Date value) {
            addCriterion("account_end >", value, "accountEnd");
            return (Criteria) this;
        }

        public Criteria andAccountEndGreaterThanOrEqualTo(Date value) {
            addCriterion("account_end >=", value, "accountEnd");
            return (Criteria) this;
        }

        public Criteria andAccountEndLessThan(Date value) {
            addCriterion("account_end <", value, "accountEnd");
            return (Criteria) this;
        }

        public Criteria andAccountEndLessThanOrEqualTo(Date value) {
            addCriterion("account_end <=", value, "accountEnd");
            return (Criteria) this;
        }

        public Criteria andAccountEndIn(List<Date> values) {
            addCriterion("account_end in", values, "accountEnd");
            return (Criteria) this;
        }

        public Criteria andAccountEndNotIn(List<Date> values) {
            addCriterion("account_end not in", values, "accountEnd");
            return (Criteria) this;
        }

        public Criteria andAccountEndBetween(Date value1, Date value2) {
            addCriterion("account_end between", value1, value2, "accountEnd");
            return (Criteria) this;
        }

        public Criteria andAccountEndNotBetween(Date value1, Date value2) {
            addCriterion("account_end not between", value1, value2, "accountEnd");
            return (Criteria) this;
        }

        public Criteria andBossIdIsNull() {
            addCriterion("boss_id is null");
            return (Criteria) this;
        }

        public Criteria andBossIdIsNotNull() {
            addCriterion("boss_id is not null");
            return (Criteria) this;
        }

        public Criteria andBossIdEqualTo(Integer value) {
            addCriterion("boss_id =", value, "bossId");
            return (Criteria) this;
        }

        public Criteria andBossIdNotEqualTo(Integer value) {
            addCriterion("boss_id <>", value, "bossId");
            return (Criteria) this;
        }

        public Criteria andBossIdGreaterThan(Integer value) {
            addCriterion("boss_id >", value, "bossId");
            return (Criteria) this;
        }

        public Criteria andBossIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("boss_id >=", value, "bossId");
            return (Criteria) this;
        }

        public Criteria andBossIdLessThan(Integer value) {
            addCriterion("boss_id <", value, "bossId");
            return (Criteria) this;
        }

        public Criteria andBossIdLessThanOrEqualTo(Integer value) {
            addCriterion("boss_id <=", value, "bossId");
            return (Criteria) this;
        }

        public Criteria andBossIdIn(List<Integer> values) {
            addCriterion("boss_id in", values, "bossId");
            return (Criteria) this;
        }

        public Criteria andBossIdNotIn(List<Integer> values) {
            addCriterion("boss_id not in", values, "bossId");
            return (Criteria) this;
        }

        public Criteria andBossIdBetween(Integer value1, Integer value2) {
            addCriterion("boss_id between", value1, value2, "bossId");
            return (Criteria) this;
        }

        public Criteria andBossIdNotBetween(Integer value1, Integer value2) {
            addCriterion("boss_id not between", value1, value2, "bossId");
            return (Criteria) this;
        }

        public Criteria andJdbcDriverclassnameIsNull() {
            addCriterion("jdbc_driverclassname is null");
            return (Criteria) this;
        }

        public Criteria andJdbcDriverclassnameIsNotNull() {
            addCriterion("jdbc_driverclassname is not null");
            return (Criteria) this;
        }

        public Criteria andJdbcDriverclassnameEqualTo(String value) {
            addCriterion("jdbc_driverclassname =", value, "jdbcDriverclassname");
            return (Criteria) this;
        }

        public Criteria andJdbcDriverclassnameNotEqualTo(String value) {
            addCriterion("jdbc_driverclassname <>", value, "jdbcDriverclassname");
            return (Criteria) this;
        }

        public Criteria andJdbcDriverclassnameGreaterThan(String value) {
            addCriterion("jdbc_driverclassname >", value, "jdbcDriverclassname");
            return (Criteria) this;
        }

        public Criteria andJdbcDriverclassnameGreaterThanOrEqualTo(String value) {
            addCriterion("jdbc_driverclassname >=", value, "jdbcDriverclassname");
            return (Criteria) this;
        }

        public Criteria andJdbcDriverclassnameLessThan(String value) {
            addCriterion("jdbc_driverclassname <", value, "jdbcDriverclassname");
            return (Criteria) this;
        }

        public Criteria andJdbcDriverclassnameLessThanOrEqualTo(String value) {
            addCriterion("jdbc_driverclassname <=", value, "jdbcDriverclassname");
            return (Criteria) this;
        }

        public Criteria andJdbcDriverclassnameLike(String value) {
            addCriterion("jdbc_driverclassname like", value, "jdbcDriverclassname");
            return (Criteria) this;
        }

        public Criteria andJdbcDriverclassnameNotLike(String value) {
            addCriterion("jdbc_driverclassname not like", value, "jdbcDriverclassname");
            return (Criteria) this;
        }

        public Criteria andJdbcDriverclassnameIn(List<String> values) {
            addCriterion("jdbc_driverclassname in", values, "jdbcDriverclassname");
            return (Criteria) this;
        }

        public Criteria andJdbcDriverclassnameNotIn(List<String> values) {
            addCriterion("jdbc_driverclassname not in", values, "jdbcDriverclassname");
            return (Criteria) this;
        }

        public Criteria andJdbcDriverclassnameBetween(String value1, String value2) {
            addCriterion("jdbc_driverclassname between", value1, value2, "jdbcDriverclassname");
            return (Criteria) this;
        }

        public Criteria andJdbcDriverclassnameNotBetween(String value1, String value2) {
            addCriterion("jdbc_driverclassname not between", value1, value2, "jdbcDriverclassname");
            return (Criteria) this;
        }

        public Criteria andJdbcUrlIsNull() {
            addCriterion("jdbc_url is null");
            return (Criteria) this;
        }

        public Criteria andJdbcUrlIsNotNull() {
            addCriterion("jdbc_url is not null");
            return (Criteria) this;
        }

        public Criteria andJdbcUrlEqualTo(String value) {
            addCriterion("jdbc_url =", value, "jdbcUrl");
            return (Criteria) this;
        }

        public Criteria andJdbcUrlNotEqualTo(String value) {
            addCriterion("jdbc_url <>", value, "jdbcUrl");
            return (Criteria) this;
        }

        public Criteria andJdbcUrlGreaterThan(String value) {
            addCriterion("jdbc_url >", value, "jdbcUrl");
            return (Criteria) this;
        }

        public Criteria andJdbcUrlGreaterThanOrEqualTo(String value) {
            addCriterion("jdbc_url >=", value, "jdbcUrl");
            return (Criteria) this;
        }

        public Criteria andJdbcUrlLessThan(String value) {
            addCriterion("jdbc_url <", value, "jdbcUrl");
            return (Criteria) this;
        }

        public Criteria andJdbcUrlLessThanOrEqualTo(String value) {
            addCriterion("jdbc_url <=", value, "jdbcUrl");
            return (Criteria) this;
        }

        public Criteria andJdbcUrlLike(String value) {
            addCriterion("jdbc_url like", value, "jdbcUrl");
            return (Criteria) this;
        }

        public Criteria andJdbcUrlNotLike(String value) {
            addCriterion("jdbc_url not like", value, "jdbcUrl");
            return (Criteria) this;
        }

        public Criteria andJdbcUrlIn(List<String> values) {
            addCriterion("jdbc_url in", values, "jdbcUrl");
            return (Criteria) this;
        }

        public Criteria andJdbcUrlNotIn(List<String> values) {
            addCriterion("jdbc_url not in", values, "jdbcUrl");
            return (Criteria) this;
        }

        public Criteria andJdbcUrlBetween(String value1, String value2) {
            addCriterion("jdbc_url between", value1, value2, "jdbcUrl");
            return (Criteria) this;
        }

        public Criteria andJdbcUrlNotBetween(String value1, String value2) {
            addCriterion("jdbc_url not between", value1, value2, "jdbcUrl");
            return (Criteria) this;
        }

        public Criteria andJdbcUsernameIsNull() {
            addCriterion("jdbc_username is null");
            return (Criteria) this;
        }

        public Criteria andJdbcUsernameIsNotNull() {
            addCriterion("jdbc_username is not null");
            return (Criteria) this;
        }

        public Criteria andJdbcUsernameEqualTo(String value) {
            addCriterion("jdbc_username =", value, "jdbcUsername");
            return (Criteria) this;
        }

        public Criteria andJdbcUsernameNotEqualTo(String value) {
            addCriterion("jdbc_username <>", value, "jdbcUsername");
            return (Criteria) this;
        }

        public Criteria andJdbcUsernameGreaterThan(String value) {
            addCriterion("jdbc_username >", value, "jdbcUsername");
            return (Criteria) this;
        }

        public Criteria andJdbcUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("jdbc_username >=", value, "jdbcUsername");
            return (Criteria) this;
        }

        public Criteria andJdbcUsernameLessThan(String value) {
            addCriterion("jdbc_username <", value, "jdbcUsername");
            return (Criteria) this;
        }

        public Criteria andJdbcUsernameLessThanOrEqualTo(String value) {
            addCriterion("jdbc_username <=", value, "jdbcUsername");
            return (Criteria) this;
        }

        public Criteria andJdbcUsernameLike(String value) {
            addCriterion("jdbc_username like", value, "jdbcUsername");
            return (Criteria) this;
        }

        public Criteria andJdbcUsernameNotLike(String value) {
            addCriterion("jdbc_username not like", value, "jdbcUsername");
            return (Criteria) this;
        }

        public Criteria andJdbcUsernameIn(List<String> values) {
            addCriterion("jdbc_username in", values, "jdbcUsername");
            return (Criteria) this;
        }

        public Criteria andJdbcUsernameNotIn(List<String> values) {
            addCriterion("jdbc_username not in", values, "jdbcUsername");
            return (Criteria) this;
        }

        public Criteria andJdbcUsernameBetween(String value1, String value2) {
            addCriterion("jdbc_username between", value1, value2, "jdbcUsername");
            return (Criteria) this;
        }

        public Criteria andJdbcUsernameNotBetween(String value1, String value2) {
            addCriterion("jdbc_username not between", value1, value2, "jdbcUsername");
            return (Criteria) this;
        }

        public Criteria andJdbcPasswordIsNull() {
            addCriterion("jdbc_password is null");
            return (Criteria) this;
        }

        public Criteria andJdbcPasswordIsNotNull() {
            addCriterion("jdbc_password is not null");
            return (Criteria) this;
        }

        public Criteria andJdbcPasswordEqualTo(String value) {
            addCriterion("jdbc_password =", value, "jdbcPassword");
            return (Criteria) this;
        }

        public Criteria andJdbcPasswordNotEqualTo(String value) {
            addCriterion("jdbc_password <>", value, "jdbcPassword");
            return (Criteria) this;
        }

        public Criteria andJdbcPasswordGreaterThan(String value) {
            addCriterion("jdbc_password >", value, "jdbcPassword");
            return (Criteria) this;
        }

        public Criteria andJdbcPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("jdbc_password >=", value, "jdbcPassword");
            return (Criteria) this;
        }

        public Criteria andJdbcPasswordLessThan(String value) {
            addCriterion("jdbc_password <", value, "jdbcPassword");
            return (Criteria) this;
        }

        public Criteria andJdbcPasswordLessThanOrEqualTo(String value) {
            addCriterion("jdbc_password <=", value, "jdbcPassword");
            return (Criteria) this;
        }

        public Criteria andJdbcPasswordLike(String value) {
            addCriterion("jdbc_password like", value, "jdbcPassword");
            return (Criteria) this;
        }

        public Criteria andJdbcPasswordNotLike(String value) {
            addCriterion("jdbc_password not like", value, "jdbcPassword");
            return (Criteria) this;
        }

        public Criteria andJdbcPasswordIn(List<String> values) {
            addCriterion("jdbc_password in", values, "jdbcPassword");
            return (Criteria) this;
        }

        public Criteria andJdbcPasswordNotIn(List<String> values) {
            addCriterion("jdbc_password not in", values, "jdbcPassword");
            return (Criteria) this;
        }

        public Criteria andJdbcPasswordBetween(String value1, String value2) {
            addCriterion("jdbc_password between", value1, value2, "jdbcPassword");
            return (Criteria) this;
        }

        public Criteria andJdbcPasswordNotBetween(String value1, String value2) {
            addCriterion("jdbc_password not between", value1, value2, "jdbcPassword");
            return (Criteria) this;
        }

        public Criteria andShopKeyIsNull() {
            addCriterion("shop_key is null");
            return (Criteria) this;
        }

        public Criteria andShopKeyIsNotNull() {
            addCriterion("shop_key is not null");
            return (Criteria) this;
        }

        public Criteria andShopKeyEqualTo(String value) {
            addCriterion("shop_key =", value, "shopKey");
            return (Criteria) this;
        }

        public Criteria andShopKeyNotEqualTo(String value) {
            addCriterion("shop_key <>", value, "shopKey");
            return (Criteria) this;
        }

        public Criteria andShopKeyGreaterThan(String value) {
            addCriterion("shop_key >", value, "shopKey");
            return (Criteria) this;
        }

        public Criteria andShopKeyGreaterThanOrEqualTo(String value) {
            addCriterion("shop_key >=", value, "shopKey");
            return (Criteria) this;
        }

        public Criteria andShopKeyLessThan(String value) {
            addCriterion("shop_key <", value, "shopKey");
            return (Criteria) this;
        }

        public Criteria andShopKeyLessThanOrEqualTo(String value) {
            addCriterion("shop_key <=", value, "shopKey");
            return (Criteria) this;
        }

        public Criteria andShopKeyLike(String value) {
            addCriterion("shop_key like", value, "shopKey");
            return (Criteria) this;
        }

        public Criteria andShopKeyNotLike(String value) {
            addCriterion("shop_key not like", value, "shopKey");
            return (Criteria) this;
        }

        public Criteria andShopKeyIn(List<String> values) {
            addCriterion("shop_key in", values, "shopKey");
            return (Criteria) this;
        }

        public Criteria andShopKeyNotIn(List<String> values) {
            addCriterion("shop_key not in", values, "shopKey");
            return (Criteria) this;
        }

        public Criteria andShopKeyBetween(String value1, String value2) {
            addCriterion("shop_key between", value1, value2, "shopKey");
            return (Criteria) this;
        }

        public Criteria andShopKeyNotBetween(String value1, String value2) {
            addCriterion("shop_key not between", value1, value2, "shopKey");
            return (Criteria) this;
        }

        public Criteria andDbNameIsNull() {
            addCriterion("db_name is null");
            return (Criteria) this;
        }

        public Criteria andDbNameIsNotNull() {
            addCriterion("db_name is not null");
            return (Criteria) this;
        }

        public Criteria andDbNameEqualTo(String value) {
            addCriterion("db_name =", value, "dbName");
            return (Criteria) this;
        }

        public Criteria andDbNameNotEqualTo(String value) {
            addCriterion("db_name <>", value, "dbName");
            return (Criteria) this;
        }

        public Criteria andDbNameGreaterThan(String value) {
            addCriterion("db_name >", value, "dbName");
            return (Criteria) this;
        }

        public Criteria andDbNameGreaterThanOrEqualTo(String value) {
            addCriterion("db_name >=", value, "dbName");
            return (Criteria) this;
        }

        public Criteria andDbNameLessThan(String value) {
            addCriterion("db_name <", value, "dbName");
            return (Criteria) this;
        }

        public Criteria andDbNameLessThanOrEqualTo(String value) {
            addCriterion("db_name <=", value, "dbName");
            return (Criteria) this;
        }

        public Criteria andDbNameLike(String value) {
            addCriterion("db_name like", value, "dbName");
            return (Criteria) this;
        }

        public Criteria andDbNameNotLike(String value) {
            addCriterion("db_name not like", value, "dbName");
            return (Criteria) this;
        }

        public Criteria andDbNameIn(List<String> values) {
            addCriterion("db_name in", values, "dbName");
            return (Criteria) this;
        }

        public Criteria andDbNameNotIn(List<String> values) {
            addCriterion("db_name not in", values, "dbName");
            return (Criteria) this;
        }

        public Criteria andDbNameBetween(String value1, String value2) {
            addCriterion("db_name between", value1, value2, "dbName");
            return (Criteria) this;
        }

        public Criteria andDbNameNotBetween(String value1, String value2) {
            addCriterion("db_name not between", value1, value2, "dbName");
            return (Criteria) this;
        }

        public Criteria andUserCountIsNull() {
            addCriterion("user_count is null");
            return (Criteria) this;
        }

        public Criteria andUserCountIsNotNull() {
            addCriterion("user_count is not null");
            return (Criteria) this;
        }

        public Criteria andUserCountEqualTo(Integer value) {
            addCriterion("user_count =", value, "userCount");
            return (Criteria) this;
        }

        public Criteria andUserCountNotEqualTo(Integer value) {
            addCriterion("user_count <>", value, "userCount");
            return (Criteria) this;
        }

        public Criteria andUserCountGreaterThan(Integer value) {
            addCriterion("user_count >", value, "userCount");
            return (Criteria) this;
        }

        public Criteria andUserCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_count >=", value, "userCount");
            return (Criteria) this;
        }

        public Criteria andUserCountLessThan(Integer value) {
            addCriterion("user_count <", value, "userCount");
            return (Criteria) this;
        }

        public Criteria andUserCountLessThanOrEqualTo(Integer value) {
            addCriterion("user_count <=", value, "userCount");
            return (Criteria) this;
        }

        public Criteria andUserCountIn(List<Integer> values) {
            addCriterion("user_count in", values, "userCount");
            return (Criteria) this;
        }

        public Criteria andUserCountNotIn(List<Integer> values) {
            addCriterion("user_count not in", values, "userCount");
            return (Criteria) this;
        }

        public Criteria andUserCountBetween(Integer value1, Integer value2) {
            addCriterion("user_count between", value1, value2, "userCount");
            return (Criteria) this;
        }

        public Criteria andUserCountNotBetween(Integer value1, Integer value2) {
            addCriterion("user_count not between", value1, value2, "userCount");
            return (Criteria) this;
        }

        public Criteria andWechatIdIsNull() {
            addCriterion("wechat_id is null");
            return (Criteria) this;
        }

        public Criteria andWechatIdIsNotNull() {
            addCriterion("wechat_id is not null");
            return (Criteria) this;
        }

        public Criteria andWechatIdEqualTo(Integer value) {
            addCriterion("wechat_id =", value, "wechatId");
            return (Criteria) this;
        }

        public Criteria andWechatIdNotEqualTo(Integer value) {
            addCriterion("wechat_id <>", value, "wechatId");
            return (Criteria) this;
        }

        public Criteria andWechatIdGreaterThan(Integer value) {
            addCriterion("wechat_id >", value, "wechatId");
            return (Criteria) this;
        }

        public Criteria andWechatIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("wechat_id >=", value, "wechatId");
            return (Criteria) this;
        }

        public Criteria andWechatIdLessThan(Integer value) {
            addCriterion("wechat_id <", value, "wechatId");
            return (Criteria) this;
        }

        public Criteria andWechatIdLessThanOrEqualTo(Integer value) {
            addCriterion("wechat_id <=", value, "wechatId");
            return (Criteria) this;
        }

        public Criteria andWechatIdIn(List<Integer> values) {
            addCriterion("wechat_id in", values, "wechatId");
            return (Criteria) this;
        }

        public Criteria andWechatIdNotIn(List<Integer> values) {
            addCriterion("wechat_id not in", values, "wechatId");
            return (Criteria) this;
        }

        public Criteria andWechatIdBetween(Integer value1, Integer value2) {
            addCriterion("wechat_id between", value1, value2, "wechatId");
            return (Criteria) this;
        }

        public Criteria andWechatIdNotBetween(Integer value1, Integer value2) {
            addCriterion("wechat_id not between", value1, value2, "wechatId");
            return (Criteria) this;
        }

        public Criteria andCreatUserIsNull() {
            addCriterion("creat_user is null");
            return (Criteria) this;
        }

        public Criteria andCreatUserIsNotNull() {
            addCriterion("creat_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreatUserEqualTo(Integer value) {
            addCriterion("creat_user =", value, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserNotEqualTo(Integer value) {
            addCriterion("creat_user <>", value, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserGreaterThan(Integer value) {
            addCriterion("creat_user >", value, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("creat_user >=", value, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserLessThan(Integer value) {
            addCriterion("creat_user <", value, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserLessThanOrEqualTo(Integer value) {
            addCriterion("creat_user <=", value, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserIn(List<Integer> values) {
            addCriterion("creat_user in", values, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserNotIn(List<Integer> values) {
            addCriterion("creat_user not in", values, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserBetween(Integer value1, Integer value2) {
            addCriterion("creat_user between", value1, value2, "creatUser");
            return (Criteria) this;
        }

        public Criteria andCreatUserNotBetween(Integer value1, Integer value2) {
            addCriterion("creat_user not between", value1, value2, "creatUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserIsNull() {
            addCriterion("manager_user is null");
            return (Criteria) this;
        }

        public Criteria andManagerUserIsNotNull() {
            addCriterion("manager_user is not null");
            return (Criteria) this;
        }

        public Criteria andManagerUserEqualTo(Integer value) {
            addCriterion("manager_user =", value, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserNotEqualTo(Integer value) {
            addCriterion("manager_user <>", value, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserGreaterThan(Integer value) {
            addCriterion("manager_user >", value, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("manager_user >=", value, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserLessThan(Integer value) {
            addCriterion("manager_user <", value, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserLessThanOrEqualTo(Integer value) {
            addCriterion("manager_user <=", value, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserIn(List<Integer> values) {
            addCriterion("manager_user in", values, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserNotIn(List<Integer> values) {
            addCriterion("manager_user not in", values, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserBetween(Integer value1, Integer value2) {
            addCriterion("manager_user between", value1, value2, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserNotBetween(Integer value1, Integer value2) {
            addCriterion("manager_user not between", value1, value2, "managerUser");
            return (Criteria) this;
        }

        public Criteria andSmsAmountIsNull() {
            addCriterion("sms_amount is null");
            return (Criteria) this;
        }

        public Criteria andSmsAmountIsNotNull() {
            addCriterion("sms_amount is not null");
            return (Criteria) this;
        }

        public Criteria andSmsAmountEqualTo(Integer value) {
            addCriterion("sms_amount =", value, "smsAmount");
            return (Criteria) this;
        }

        public Criteria andSmsAmountNotEqualTo(Integer value) {
            addCriterion("sms_amount <>", value, "smsAmount");
            return (Criteria) this;
        }

        public Criteria andSmsAmountGreaterThan(Integer value) {
            addCriterion("sms_amount >", value, "smsAmount");
            return (Criteria) this;
        }

        public Criteria andSmsAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("sms_amount >=", value, "smsAmount");
            return (Criteria) this;
        }

        public Criteria andSmsAmountLessThan(Integer value) {
            addCriterion("sms_amount <", value, "smsAmount");
            return (Criteria) this;
        }

        public Criteria andSmsAmountLessThanOrEqualTo(Integer value) {
            addCriterion("sms_amount <=", value, "smsAmount");
            return (Criteria) this;
        }

        public Criteria andSmsAmountIn(List<Integer> values) {
            addCriterion("sms_amount in", values, "smsAmount");
            return (Criteria) this;
        }

        public Criteria andSmsAmountNotIn(List<Integer> values) {
            addCriterion("sms_amount not in", values, "smsAmount");
            return (Criteria) this;
        }

        public Criteria andSmsAmountBetween(Integer value1, Integer value2) {
            addCriterion("sms_amount between", value1, value2, "smsAmount");
            return (Criteria) this;
        }

        public Criteria andSmsAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("sms_amount not between", value1, value2, "smsAmount");
            return (Criteria) this;
        }

        public Criteria andOpenNightServeFlagIsNull() {
            addCriterion("open_night_serve_flag is null");
            return (Criteria) this;
        }

        public Criteria andOpenNightServeFlagIsNotNull() {
            addCriterion("open_night_serve_flag is not null");
            return (Criteria) this;
        }

        public Criteria andOpenNightServeFlagEqualTo(Integer value) {
            addCriterion("open_night_serve_flag =", value, "openNightServeFlag");
            return (Criteria) this;
        }

        public Criteria andOpenNightServeFlagNotEqualTo(Integer value) {
            addCriterion("open_night_serve_flag <>", value, "openNightServeFlag");
            return (Criteria) this;
        }

        public Criteria andOpenNightServeFlagGreaterThan(Integer value) {
            addCriterion("open_night_serve_flag >", value, "openNightServeFlag");
            return (Criteria) this;
        }

        public Criteria andOpenNightServeFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("open_night_serve_flag >=", value, "openNightServeFlag");
            return (Criteria) this;
        }

        public Criteria andOpenNightServeFlagLessThan(Integer value) {
            addCriterion("open_night_serve_flag <", value, "openNightServeFlag");
            return (Criteria) this;
        }

        public Criteria andOpenNightServeFlagLessThanOrEqualTo(Integer value) {
            addCriterion("open_night_serve_flag <=", value, "openNightServeFlag");
            return (Criteria) this;
        }

        public Criteria andOpenNightServeFlagIn(List<Integer> values) {
            addCriterion("open_night_serve_flag in", values, "openNightServeFlag");
            return (Criteria) this;
        }

        public Criteria andOpenNightServeFlagNotIn(List<Integer> values) {
            addCriterion("open_night_serve_flag not in", values, "openNightServeFlag");
            return (Criteria) this;
        }

        public Criteria andOpenNightServeFlagBetween(Integer value1, Integer value2) {
            addCriterion("open_night_serve_flag between", value1, value2, "openNightServeFlag");
            return (Criteria) this;
        }

        public Criteria andOpenNightServeFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("open_night_serve_flag not between", value1, value2, "openNightServeFlag");
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