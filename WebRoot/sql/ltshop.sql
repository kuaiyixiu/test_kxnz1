DROP TABLE IF EXISTS `appoint`;
CREATE TABLE `appoint` (`id` int(11) NOT NULL AUTO_INCREMENT,`cust_id` int(11) DEFAULT NULL COMMENT '客户ID',`item_id` int(11) DEFAULT NULL COMMENT ' 预约项目 1保养 2打蜡 3洗车 4其他',`cellphone` varchar(15) DEFAULT NULL COMMENT '手机号',`car_number` varchar(20) DEFAULT NULL COMMENT '车牌号',`appoint_time` datetime DEFAULT NULL COMMENT '预约时间',`remark` varchar(100) DEFAULT NULL COMMENT '预约备注',`add_time` datetime DEFAULT NULL COMMENT '新增时间',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (`id` int(11) NOT NULL AUTO_INCREMENT,`car_type` int(3) DEFAULT NULL COMMENT '车辆类型 0临牌 1会员',`owner_name` varchar(20) DEFAULT NULL COMMENT '车主姓名',`owner_cellphone` varchar(15) DEFAULT NULL COMMENT '车主手机号',`vehicle_type` int(3) DEFAULT NULL COMMENT '车辆类型：1轿车 2SUV 3MPV 4其他',`car_brand` varchar(10) DEFAULT NULL COMMENT '车辆品牌',`car_vin` varchar(30) DEFAULT NULL COMMENT '车架号',`car_engine` varchar(30) DEFAULT NULL COMMENT '发动机号',`compulsory_date` date DEFAULT NULL COMMENT '交强险日期',`commercial_date` date DEFAULT NULL COMMENT '商业险日期',`check_date` date DEFAULT NULL COMMENT '年检到期',`custom_id` int(20) DEFAULT NULL COMMENT '外键 关联custom的id',`remark` varchar(50) DEFAULT NULL COMMENT '车辆备注',`car_number` varchar(10) DEFAULT NULL COMMENT '车牌号',`compulsory_status` int(3) DEFAULT '0' COMMENT '车辆状态 0未提醒 1已完成',`commercial_status` int(3) DEFAULT '0' COMMENT '商业险状态 0未邀约 1邀约过',`check_status` int(3) DEFAULT '0' COMMENT '年检邀约状态 0未邀约 1邀约过',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆表';

DROP TABLE IF EXISTS `cardset`;
CREATE TABLE `cardset` (`id` int(11) NOT NULL AUTO_INCREMENT,`card_name` varchar(50) DEFAULT NULL COMMENT '卡卷名称',`card_value` decimal(8,2) DEFAULT NULL,`date_type` int(1) DEFAULT '0' COMMENT '有效期类型0日期 1固定有效期',`end_day` int(11) DEFAULT NULL COMMENT '固定有效时长，从派发日计算天数',`end_date` date DEFAULT NULL COMMENT '截止日期',`use_first` int(1) DEFAULT '0' COMMENT '仅限未进店过的客户可领取使用',`remark` varchar(150) DEFAULT NULL COMMENT '使用说明',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `car_maintain_info`;
CREATE TABLE `car_maintain_info` (`id` int(11) NOT NULL AUTO_INCREMENT,`create_time` date DEFAULT NULL COMMENT '当前保养时间',`remind_time` date DEFAULT NULL COMMENT '下次保养时间',`progress_mileage` int(11) DEFAULT NULL COMMENT '进展里程',`remind_mileage` int(11) DEFAULT NULL COMMENT '提醒里程',`car_id` int(11) NOT NULL COMMENT '车辆id 关联车辆表id',`remark` varchar(128) DEFAULT NULL COMMENT '备注',`status` int(3) DEFAULT '0' COMMENT '保养邀约状态 0未邀约 1邀约过',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (`id` int(11) NOT NULL AUTO_INCREMENT,`name` varchar(50) NOT NULL COMMENT '优惠券名称',`value` decimal(8,2) DEFAULT NULL COMMENT '价值',`days` int(11) DEFAULT NULL COMMENT '有效天数',`create_time` datetime DEFAULT NULL COMMENT '创建时间',`statue` int(1) DEFAULT '1' COMMENT '状态1启用0禁用',`remark` varchar(150) DEFAULT NULL COMMENT '备注',`shop_id` int(11) DEFAULT NULL COMMENT '创建门店',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `coupon_pack`;
CREATE TABLE `coupon_pack` (`id` int(11) NOT NULL AUTO_INCREMENT,`name` varchar(50) DEFAULT NULL COMMENT '代金券包名',`value` decimal(8,2) DEFAULT '0.00' COMMENT '价值',`remark` varchar(150) DEFAULT NULL COMMENT '备注',`create_time` datetime DEFAULT NULL COMMENT '创建时间',`shop_id` int(11) DEFAULT NULL COMMENT '创建门店',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `coupon_pack_dt`;
CREATE TABLE `coupon_pack_dt` (`id` int(11) NOT NULL AUTO_INCREMENT,`pack_id` int(11) DEFAULT NULL COMMENT '包id',`coupon_id` int(11) DEFAULT NULL COMMENT '优惠券id',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `custom`;
CREATE TABLE `custom` (`id` int(20) NOT NULL AUTO_INCREMENT,`card_no` varchar(20) DEFAULT NULL COMMENT '会员卡卡号',`cust_type` int(11) DEFAULT NULL COMMENT '会员类型',`cust_name` varchar(20) DEFAULT NULL COMMENT '会员名称',`cellphone` varchar(15) DEFAULT NULL COMMENT '手机号',`score` int(11) DEFAULT '0' COMMENT '会员积分',`birthday` date DEFAULT NULL COMMENT '日期',`sex` int(3) DEFAULT NULL COMMENT '性别',`remark` varchar(50) DEFAULT NULL COMMENT '额外备注',`balance` decimal(8,2) DEFAULT '0.00' COMMENT '余额',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',`enabled` int(3) DEFAULT '0' COMMENT '0 开启 1禁用',`open_id` varchar(50) DEFAULT NULL COMMENT '微信公众号关注id',`wechat_name` varchar(50) DEFAULT NULL COMMENT '微信昵称', `add_time` datetime DEFAULT NULL COMMENT '创建时间',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='顾客表';

DROP TABLE IF EXISTS `custom_meal`;
CREATE TABLE `custom_meal` (`id` int(11) NOT NULL AUTO_INCREMENT,`cust_id` int(11) DEFAULT NULL COMMENT '客户id',`meal_id` int(11) DEFAULT NULL COMMENT '套餐id',`price` decimal(8,2) DEFAULT NULL COMMENT '价格',`end_date` date DEFAULT NULL COMMENT '过期时间',`create_date` date NOT NULL COMMENT '创建时间',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户套餐表';

DROP TABLE IF EXISTS `custom_meal_dt`;
CREATE TABLE `custom_meal_dt` (`id` int(11) NOT NULL AUTO_INCREMENT,`custom_meal_id` int(11) NOT NULL COMMENT '关联客户订单表',`meal_dt_id` int(11) NOT NULL COMMENT '关联meal_dt表id',`quantity` int(11) DEFAULT NULL COMMENT '数量',`used` int(3) NOT NULL DEFAULT '0' COMMENT '0未使用 1使用过',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `custom_price`;
CREATE TABLE `custom_price` (`id` int(11) NOT NULL AUTO_INCREMENT,`cust_type` int(11) DEFAULT NULL COMMENT '会员类型',`kind` int(3) DEFAULT NULL COMMENT '种类 1服务2产品',`item_id` int(11) DEFAULT NULL COMMENT '服务或产品id',`price` decimal(8,2) DEFAULT NULL COMMENT '价格',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `custom_type`;
CREATE TABLE `custom_type` (`id` int(11) NOT NULL AUTO_INCREMENT,`type_name` varchar(20) DEFAULT NULL COMMENT '类型名称',`remark` varchar(50) DEFAULT NULL COMMENT '备注',`shop_id` int(11) DEFAULT NULL,`status` int(3) DEFAULT '0' COMMENT '1不可编辑，0可编辑可删除',PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='会员卡类型';

INSERT INTO `custom_type` VALUES ('1', '自助开卡', '通过微信端自助开卡会员，不可删除', '2', null);
INSERT INTO `custom_type` VALUES ('2', '到店开卡', '到店后有员工推荐开卡', '2', null);
INSERT INTO `custom_type` VALUES ('3', '公司员工卡', '本公司员工使用', '2', null);

DROP TABLE IF EXISTS `cust_coupon`;
CREATE TABLE `cust_coupon` (`id` int(11) NOT NULL AUTO_INCREMENT,`coupon_id` int(11) DEFAULT NULL COMMENT '代金券id',`total_amount` decimal(8,2) DEFAULT '0.00' COMMENT '代金券总金额',`avail_amount` decimal(8,2) DEFAULT '0.00' COMMENT '可用金额',`cust_id` int(11) DEFAULT NULL COMMENT '客户id',`create_time` datetime DEFAULT NULL COMMENT '创建日期',`end_time` datetime DEFAULT NULL COMMENT '失效日期',`state` int(1) DEFAULT '1' COMMENT '状态0已过期1可用',`source_id` int(11) DEFAULT '1' COMMENT '来源id',`source_type` int(1) DEFAULT '1' COMMENT '来源种类1充值',`coupon_name` varchar(50) DEFAULT NULL COMMENT '代金券名称',PRIMARY KEY (`id`),KEY `cust_id` (`cust_id`) USING BTREE) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `daily_pay_record`;
CREATE TABLE `daily_pay_record` (`id` int(11) NOT NULL AUTO_INCREMENT,`kind` int(3) DEFAULT NULL COMMENT '1收入 2支出',`pay_type_id` int(3) DEFAULT NULL COMMENT '收支类型 关联daily_pay_type 的id',`pay_id` int(3) DEFAULT NULL COMMENT '支付方式 1现金 2微信 3支付宝 4其他',`amount` decimal(8,2) DEFAULT NULL COMMENT '收支金额',`start_share_period` date DEFAULT NULL COMMENT '开始分摊周期',`end_share_period` date DEFAULT NULL COMMENT '结束分摊周期',`remark` varchar(100) DEFAULT NULL COMMENT '收支摘要',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',`add_time` datetime DEFAULT NULL COMMENT '录入时间',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日常收支流水';

DROP TABLE IF EXISTS `daily_pay_type`;
CREATE TABLE `daily_pay_type` (`id` int(11) NOT NULL AUTO_INCREMENT,`type_name` varchar(20) DEFAULT NULL COMMENT '类型名称',`shop_id` int(11) DEFAULT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日常收支类型';

DROP TABLE IF EXISTS `debt_record`;
CREATE TABLE `debt_record` (`id` int(11) NOT NULL AUTO_INCREMENT,`kind` int(1) DEFAULT NULL COMMENT '挂账种类1客户订单挂账2门店入库挂账3门店退货挂账',`source_id` int(11) DEFAULT NULL COMMENT '挂账来源id',`debt_amount` decimal(8,2) DEFAULT NULL COMMENT '挂账金额',`return_amount` decimal(8,2) DEFAULT NULL COMMENT '已还款金额',`left_amount` decimal(8,2) DEFAULT NULL COMMENT '待还款金额',`addtime` datetime DEFAULT NULL COMMENT '添加时间',`enable` int(3) DEFAULT '1' COMMENT '1有效 0无效（反挂账时候用）',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='挂账记录表';

DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (`id` int(11) NOT NULL AUTO_INCREMENT,`user_name` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名',`module` varchar(150) CHARACTER SET utf8 DEFAULT NULL COMMENT '模块名称',`description` varchar(150) CHARACTER SET utf8 DEFAULT NULL COMMENT '描述',`res_url` varchar(150) CHARACTER SET utf8 DEFAULT NULL COMMENT '请求url',`user_ip` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT 'ip',`oper_time` datetime DEFAULT NULL,`class_name` varchar(100) DEFAULT NULL COMMENT '类名',`method_name` varchar(100) DEFAULT NULL COMMENT '方法名',`params` text COMMENT '参数json字符串',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',`source` int(3) DEFAULT '1' COMMENT '日志来源 1PC 2微信 3安卓',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日志信息';

DROP TABLE IF EXISTS `meal`;
CREATE TABLE `meal` (`id` int(11) NOT NULL AUTO_INCREMENT,`day` int(11) DEFAULT NULL COMMENT '天数',`name` varchar(50) DEFAULT NULL COMMENT '套餐名称',`amount` decimal(10,0) DEFAULT NULL COMMENT '金额',`status` varchar(1) DEFAULT '1' COMMENT '状态备用 1上架 0下架',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',`remark` varchar(150) DEFAULT NULL COMMENT '备注',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='套餐信息表';

DROP TABLE IF EXISTS `meal_dt`;
CREATE TABLE `meal_dt` (`id` int(11) NOT NULL AUTO_INCREMENT,`type` int(3) DEFAULT NULL COMMENT '套餐种类  1产品 2服务',`meal_id` int(11) DEFAULT NULL,`item_id` int(11) DEFAULT NULL COMMENT '服务/产品ID',`quantity` int(11) DEFAULT NULL COMMENT '数量',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='套餐明细表';

DROP TABLE IF EXISTS `meal_pay_record`;
CREATE TABLE `meal_pay_record` (`id` int(11) NOT NULL AUTO_INCREMENT,`cust_id` int(11) DEFAULT NULL COMMENT '客户ID',`cust_meal_dt_id` int(11) DEFAULT NULL COMMENT 'cust_meal_dt的ID',`quantity` int(11) DEFAULT NULL COMMENT '数量',`order_id` int(11) DEFAULT NULL COMMENT '订单 ID',`add_time` datetime DEFAULT NULL COMMENT '添加时间',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='套餐消费记录表';

DROP TABLE IF EXISTS `oa_workflow_check_user`;
CREATE TABLE `oa_workflow_check_user` (`id` int(11) NOT NULL AUTO_INCREMENT,`instance_node_id` int(11) DEFAULT NULL COMMENT '关联wf_instance_node的Id',`instance_id` int(11) DEFAULT NULL,`opt_user` int(11) DEFAULT NULL,`opt_time` datetime DEFAULT NULL,`result` int(11) DEFAULT NULL COMMENT '审批结果 1通过 2不通过 3退回',`remark` varchar(50) DEFAULT NULL COMMENT '审批意见表',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

DROP TABLE IF EXISTS `oa_workflow_instance`;
CREATE TABLE `oa_workflow_instance` (`id` int(3) NOT NULL AUTO_INCREMENT,`template_id` int(11) DEFAULT NULL COMMENT '关联wf_template表的id',`slip_type` int(11) DEFAULT NULL COMMENT '单据类型 1入库',`slip_id` int(11) DEFAULT NULL COMMENT '单据ID',`current_instance_node_id` int(11) DEFAULT NULL COMMENT '当前进行的子节点实例ID',`name` varchar(50) DEFAULT NULL COMMENT '实例名称',`status` int(3) DEFAULT NULL COMMENT '实例处理状态（1进行中 2已完结 3终止）',`create_user` int(11) DEFAULT NULL COMMENT '操作人',`create_time` datetime DEFAULT NULL COMMENT '创建时间',`end_time` datetime DEFAULT NULL COMMENT '终止时间',`remark` varchar(50) DEFAULT NULL COMMENT '备注',PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='流程处理实例表';

DROP TABLE IF EXISTS `oa_workflow_instance_node`;
CREATE TABLE `oa_workflow_instance_node` (`id` int(11) NOT NULL AUTO_INCREMENT,`iidno` int(11) DEFAULT NULL COMMENT '显示顺序',`name` varchar(50) DEFAULT NULL COMMENT '节点名称',`instance_id` int(11) DEFAULT NULL COMMENT '实例主表ID',`node_id` int(11) DEFAULT NULL COMMENT '流程环节配置表节点ID',`status` int(3) DEFAULT NULL COMMENT '1进行中 2通过 3不通过 4退回',`create_time` datetime DEFAULT NULL COMMENT '创建时间',`remark` varchar(50) DEFAULT NULL COMMENT '节点备注',PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='流程处理实例节点表';

DROP TABLE IF EXISTS `oa_workflow_node`;
CREATE TABLE `oa_workflow_node` (`id` int(11) NOT NULL AUTO_INCREMENT,`template_id` int(11) DEFAULT NULL COMMENT '模版主表ID',`iidno` int(11) DEFAULT NULL COMMENT ' 显示顺序',`name` varchar(50) DEFAULT NULL COMMENT '节点名称',`type` int(3) DEFAULT NULL COMMENT '流程类型（串签1 并签2 会签3）',`pre_node` int(11) DEFAULT NULL COMMENT ' 上一个节点',`next_node` int(11) DEFAULT NULL COMMENT ' 下一个节点',`remark` varchar(50) DEFAULT NULL,`create_time` datetime DEFAULT NULL COMMENT '创建时间',PRIMARY KEY (`id`),KEY `template_id` (`template_id`)) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT=' 流程模版配置节点表';

DROP TABLE IF EXISTS `oa_workflow_node_user`;
CREATE TABLE `oa_workflow_node_user` (`id` int(11) NOT NULL AUTO_INCREMENT,`user_id` int(11) DEFAULT NULL COMMENT 'user的ID',`node_id` int(11) DEFAULT NULL COMMENT '关联的模版wf_node表的ID',PRIMARY KEY (`id`),KEY `node_id` (`node_id`)) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `oa_workflow_template`;
CREATE TABLE `oa_workflow_template` (`id` int(11) NOT NULL AUTO_INCREMENT,`name` varchar(50) DEFAULT NULL COMMENT '流程名称',`add_time` datetime DEFAULT NULL COMMENT '新增日期',`remark` varchar(100) DEFAULT NULL COMMENT '备注',`is_system` int(11) DEFAULT '0' COMMENT '是否系统默认（不可删除） 1是 0否',PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT=' 流程模版配置主表';

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (`id` int(11) NOT NULL AUTO_INCREMENT,`order_no` varchar(20) NOT NULL COMMENT '订单编号',`car_number` varchar(20) DEFAULT NULL COMMENT ' 车牌号',`cust_id` int(11) DEFAULT NULL COMMENT '顾客ID',`order_status` int(11) DEFAULT NULL COMMENT '订单状态  1编辑，2待施工（已提交），3施工中，\r\n 4施工完成  5入账 6挂账 7反入账 8反挂账 0删除（逻辑删除）',`order_type` int(11) DEFAULT NULL COMMENT '订单类型  0临牌开单，1非会员开单 2会员开单',`order_amt` decimal(8,2) DEFAULT '0.00' COMMENT '订单原金额',`pay_amount` decimal(8,2) DEFAULT '0.00' COMMENT '订单实际支付金额',`creat_time` datetime DEFAULT NULL,`submit_time` datetime DEFAULT NULL COMMENT '提交时间（待施工时间）',`doing_time` datetime DEFAULT NULL COMMENT '开始施工时间',`done_time` datetime DEFAULT NULL COMMENT '施工完成时间',`finish_time` datetime DEFAULT NULL COMMENT '结算时间 （入账，挂账）',`operate_time` datetime DEFAULT NULL COMMENT '操作日期',`remark` varchar(50) DEFAULT NULL COMMENT '订单备注',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',`source` int(3) DEFAULT NULL COMMENT '订单来源 1PC 2安卓',`create_user` varchar(20) DEFAULT NULL COMMENT '开单人',`evaluate_time` datetime DEFAULT NULL COMMENT '评价时间',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

DROP TABLE IF EXISTS `orders_evaluate`;
CREATE TABLE `orders_evaluate` (`id` int(11) NOT NULL AUTO_INCREMENT,`order_id` int(11) DEFAULT NULL COMMENT '订单编号',`cust_id` int(11) DEFAULT NULL COMMENT '用户编号',`stars` int(3) DEFAULT NULL COMMENT '评价等级 1，2，3，4，5',`remark` varchar(200) DEFAULT NULL,`add_time` datetime DEFAULT NULL COMMENT '评价时间',`shop_id` int(11) DEFAULT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单评价表';

DROP TABLE IF EXISTS `orders_pay`;
CREATE TABLE `orders_pay` (`id` int(11) NOT NULL AUTO_INCREMENT,`iidno` int(11) DEFAULT NULL COMMENT '序号',`order_id` int(11) DEFAULT NULL COMMENT '订单ID',`pay_id` int(11) DEFAULT NULL COMMENT '支付类型ID',`pay_amount` decimal(8,2) DEFAULT '0.00' COMMENT '付款金额',`add_time` datetime DEFAULT NULL COMMENT '新增时间',`remark` varchar(50) DEFAULT NULL COMMENT '备注',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',`can_edit` int(1) DEFAULT NULL COMMENT '是否能编辑 1能编辑 0不能  结算后不能编辑，反挂账或者反入账后可以编辑',`source_id` int(11) DEFAULT NULL COMMENT '来源ID（代金券：关联cust_coupon）',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `orders_product`;
CREATE TABLE `orders_product` (`id` int(11) NOT NULL AUTO_INCREMENT,`iidno` int(11) DEFAULT NULL,`order_id` int(11) DEFAULT NULL COMMENT '订单ID',`product_id` int(11) DEFAULT NULL,`quantity` int(11) DEFAULT NULL COMMENT '数量',`price` decimal(8,2) DEFAULT '0.00' COMMENT '金额',`remark` varchar(50) DEFAULT NULL COMMENT '备注',`meal_pay_record_id` int(11) DEFAULT NULL COMMENT '客户套餐消费记录ID',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单-产品明细';

DROP TABLE IF EXISTS `orders_royalty`;
CREATE TABLE `orders_royalty` (`id` int(11) NOT NULL AUTO_INCREMENT,`kind` int(1) DEFAULT NULL COMMENT '提成类型 ''1''施工服务提成 ‘2’销售服务提成 3产品销售提成',`orders_dt_id` int(11) DEFAULT NULL COMMENT '订单细表ID',`amount` decimal(8,2) DEFAULT '0.00' COMMENT '提成金额',`shop_id` int(3) DEFAULT NULL COMMENT '门店ID',`order_id` int(3) DEFAULT NULL COMMENT '订单编号',`user_id` int(11) DEFAULT NULL COMMENT '员工ID',`quantity` int(11) DEFAULT '0' COMMENT '销售数量',`item_id` int(11) DEFAULT NULL COMMENT '产品/服务ID',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单提成表';

DROP TABLE IF EXISTS `orders_serve`;
CREATE TABLE `orders_serve` (`id` int(11) NOT NULL AUTO_INCREMENT,`iidno` int(11) DEFAULT NULL,`order_id` int(11) DEFAULT NULL COMMENT '订单ID',`serve_id` int(11) DEFAULT NULL COMMENT '服务ID',`quantity` int(11) DEFAULT NULL COMMENT '数量',`price` decimal(8,2) DEFAULT '0.00' COMMENT '订单金额',`serve_status` int(11) DEFAULT NULL COMMENT '服务状态：待完工1，施工中2，已完工3',`done_time` datetime DEFAULT NULL COMMENT '施工完成时间',`remark` varchar(50) DEFAULT NULL COMMENT '备注',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',`status` int(3) DEFAULT '0' COMMENT '服务状态 0未提醒 1已完成',`meal_pay_record_id` int(11) DEFAULT NULL COMMENT '客户套餐消费记录表ID',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单-服务明细';

DROP TABLE IF EXISTS `pay_record`;
CREATE TABLE `pay_record` (`id` int(11) NOT NULL AUTO_INCREMENT,`type_id` int(11) DEFAULT NULL COMMENT 'pay_type表的ID',`amount` decimal(8,2) DEFAULT '0.00' COMMENT '价格',`remark` varchar(50) DEFAULT NULL COMMENT '备注',`meal_dt_id` int(11) DEFAULT NULL COMMENT '套餐明细表ID',`kind` int(3) DEFAULT NULL COMMENT '付款来源  1客户订单支付 （收入）2门店入库（支出） 3客户购买套餐（收入） 4客户挂账还款（收入） 5.门店挂账还款（支出）6门店退货（收入）\r\n7.门店退货挂账还款（收入 供应商还款） 8订单反入账或反挂账（支出）9入库单作废(收入) 10退货单作废(支出)11调拨还款(收入)',`source_id` int(11) DEFAULT NULL COMMENT '来源主表ID ： 1orders,2reposity,3meal',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',`add_time` datetime DEFAULT NULL COMMENT '新增日期',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消费记录表';

DROP TABLE IF EXISTS `pay_type`;
CREATE TABLE `pay_type` (`id` int(11) NOT NULL AUTO_INCREMENT,`pay_name` varchar(20) DEFAULT NULL COMMENT '支付名称',`remark` varchar(50) DEFAULT NULL COMMENT '备注',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',`shop_checked` int(1) DEFAULT '0' COMMENT '门店是否启用',`cust_checked` int(1) DEFAULT '0' COMMENT '客户是否启用',`is_system` int(1) DEFAULT '0' COMMENT '是否系统默认（不可删除） 1是 0否',PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='支付类型';

INSERT INTO `pay_type` VALUES ('1', '微信', '系统内置，不可变更', null, '1', '1', '1');
INSERT INTO `pay_type` VALUES ('2', '支付宝', '系统内置，不可变更', null, '1', '1', '1');
INSERT INTO `pay_type` VALUES ('3', '现金', '系统内置，不可变更', null, '1', '1', '1');
INSERT INTO `pay_type` VALUES ('4', '余额', '系统内置，不可变更', null, '0', '1', '1');
INSERT INTO `pay_type` VALUES ('5', '代金券', '系统内置，不可变更', null, '0', '1', '1');
INSERT INTO `pay_type` VALUES ('6', '优惠券', '系统内置，不可变更', null, '0', '0', '1');

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (`id` int(11) NOT NULL AUTO_INCREMENT,`product_name` varchar(20) DEFAULT NULL COMMENT '产品名称',`product_type` varchar(40) DEFAULT NULL COMMENT '产品型号',`class_id` int(11) DEFAULT NULL COMMENT '产品分类',`price` decimal(8,2) DEFAULT '0.00' COMMENT '销售价格',`remark` varchar(50) DEFAULT NULL COMMENT '备注',`car_type` varchar(100) DEFAULT NULL COMMENT '适用车型',`alarm_quantity` int(11) DEFAULT '0' COMMENT '报警数量',`qr_code` varchar(100) DEFAULT NULL COMMENT '条形码',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',`quantity` int(11) DEFAULT '0' COMMENT '数量',`amount` decimal(8,2) DEFAULT '0.00' COMMENT '进价',`del` int(1) DEFAULT '0' COMMENT '删除标志1删除0未删除',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品';

DROP TABLE IF EXISTS `product_allocation`;
CREATE TABLE `product_allocation` (`id` int(11) NOT NULL AUTO_INCREMENT,`addtime` datetime DEFAULT NULL COMMENT '调拨时间',`callout_id` int(11) DEFAULT NULL COMMENT '调出产品',`callin_id` int(11) DEFAULT NULL COMMENT '调入产品',`count` int(11) DEFAULT NULL COMMENT '数量',`amount` decimal(8,2) DEFAULT NULL COMMENT '总价',`shop_id` int(11) DEFAULT NULL COMMENT '所属门店',`relation_shop` int(11) DEFAULT NULL COMMENT '调入或调出门店',`kind` int(1) DEFAULT NULL COMMENT '调拨方式1调出 2调入',`pay_type` int(1) DEFAULT NULL COMMENT '付款方式  1现金2其他3微信4支付宝',`enable` int(1) DEFAULT NULL COMMENT '状态 1正常 2作废',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品调拨记录表';

DROP TABLE IF EXISTS `product_change`;
CREATE TABLE `product_change` (`id` int(11) NOT NULL AUTO_INCREMENT,`change_time` datetime DEFAULT NULL COMMENT '异动时间',`product_id` int(11) DEFAULT NULL COMMENT '异动产品',`change_reason` varchar(100) DEFAULT NULL COMMENT '来源去向',`type` int(1) DEFAULT NULL COMMENT '异动类型 1采购入库 2采购退货3订单消耗4进货单作废5退货单作废6库存调入7库存调出8库存调出作废9订单反入账/挂账10调整单增加11调整单减少',`count` int(11) DEFAULT NULL COMMENT '异动数量',`before_count` int(11) DEFAULT NULL COMMENT '异动前数量',`after_count` int(11) DEFAULT NULL COMMENT '异动后数量',`relation_id` int(11) DEFAULT NULL COMMENT '关联单跳转使用的ID',`opt_user` varchar(20) DEFAULT NULL COMMENT '操作人',`change_reason_id` int(11) DEFAULT NULL COMMENT '来源去向ID',`change_amt` decimal(8,2) DEFAULT '0.00' COMMENT '异动金额',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库存异动表';

DROP TABLE IF EXISTS `product_class`;
CREATE TABLE `product_class` (`id` int(11) NOT NULL AUTO_INCREMENT,`class_name` varchar(20) DEFAULT NULL COMMENT '产品名称',`remark` varchar(50) DEFAULT NULL COMMENT '备注',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `product_consume`;
CREATE TABLE `product_consume` (`id` int(11) NOT NULL AUTO_INCREMENT,`product_repertory_id` int(11) DEFAULT NULL COMMENT '关联产品库存表ID',`order_id` int(11) DEFAULT NULL COMMENT '订单ID',`quantity` int(11) DEFAULT NULL COMMENT '消耗数量',`add_time` datetime DEFAULT NULL COMMENT '新增日期',`kind` int(3) DEFAULT NULL COMMENT '单据种类 1.订单产生 2调拨单产生3库存调整减少',`product_id` int(11) DEFAULT NULL COMMENT '产品ID',`shop_id` int(11) DEFAULT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品消耗表';

DROP TABLE IF EXISTS `product_repertory`;
CREATE TABLE `product_repertory` (`id` int(11) NOT NULL AUTO_INCREMENT,`product_id` int(11) DEFAULT NULL COMMENT '产品ID',`add_time` datetime DEFAULT NULL COMMENT '进货日期',`price` decimal(8,2) DEFAULT NULL COMMENT '进货价格',`quantity` int(11) DEFAULT NULL COMMENT '可用数量',`total_quantity` int(11) DEFAULT NULL COMMENT '入库总数量',`repertory_id` int(11) DEFAULT NULL COMMENT '入库单主表ID',`available` int(3) DEFAULT NULL COMMENT '是否可用(尚有库存) 1是 0否',`kind` int(3) DEFAULT '1' COMMENT '批次来源种类 1采购2调拨3.调整单增加',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品库存表';

DROP TABLE IF EXISTS `recharge`;
CREATE TABLE `recharge` (`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',`recharge_time` datetime DEFAULT NULL COMMENT '充值时间',`recharge_type` int(11) DEFAULT NULL COMMENT '充值方式',`recharge_amount` decimal(8,2) DEFAULT NULL COMMENT '充值金额',`card_no` varchar(20) DEFAULT NULL COMMENT '充值卡号',`opt_user` varchar(80) DEFAULT NULL COMMENT '充值人员',`remark` varchar(150) DEFAULT NULL COMMENT '充值额外说明',`send_coupon_type` int(1) DEFAULT '0' COMMENT '赠送方式0不赠送 1赠送代金券2赠送代金券包',`coupon_id` int(11) DEFAULT NULL COMMENT '代金券或者包id',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `remind`;
CREATE TABLE `remind` (`id` int(11) NOT NULL AUTO_INCREMENT,`remind_type` int(3) DEFAULT NULL COMMENT '提醒类别  1日常提醒 2客户预约 3客户邀约 4库存补货',`remind_name` varchar(20) DEFAULT NULL COMMENT '提醒名称',`remind_date` date DEFAULT NULL COMMENT '提醒日期',`user_id` int(11) DEFAULT NULL COMMENT '提醒员工ID',`remind_content` varchar(100) DEFAULT NULL COMMENT '提醒内容',`remark` varchar(100) DEFAULT NULL COMMENT '备注',`remind_status` int(3) DEFAULT NULL COMMENT '状态 0未提醒 1已提醒',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',`remind_user_id` int(11) DEFAULT NULL COMMENT '被提醒人id',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提醒表';

DROP TABLE IF EXISTS `repertory`;
CREATE TABLE `repertory` (`id` int(11) NOT NULL AUTO_INCREMENT,`supply_id` int(11) DEFAULT NULL COMMENT '供应商ID',`remark` varchar(50) DEFAULT NULL COMMENT '备注',`repertory_status` int(3) DEFAULT NULL COMMENT '状态 0.临时单 2.已入库3作废 4审批中 5审批通过',`add_time` datetime DEFAULT NULL COMMENT '新增日期',`user_name` varchar(80) DEFAULT NULL COMMENT '采购员',`kind` int(1) DEFAULT NULL COMMENT '单据种类 1入库 2退货',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `repertory_check`;
CREATE TABLE `repertory_check` (`id` int(11) NOT NULL AUTO_INCREMENT,`check_time` datetime DEFAULT NULL,`product_id` int(11) DEFAULT NULL COMMENT '产品id',`before_quantity` int(11) DEFAULT NULL COMMENT '盘点前数量',`after_quantity` int(11) DEFAULT NULL COMMENT '盘点后数量',`opt_user` varchar(80) DEFAULT NULL COMMENT '操作人',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='盘点历史表';

DROP TABLE IF EXISTS `repertory_dt`;
CREATE TABLE `repertory_dt` (`id` int(11) NOT NULL AUTO_INCREMENT,`iidno` int(11) DEFAULT NULL COMMENT '显示ID',`repertory` int(11) NOT NULL COMMENT '库存主表ID',`product_id` int(11) NOT NULL COMMENT '产品ID',`inprice` decimal(8,2) DEFAULT NULL COMMENT '入库单价',`in_quantity` int(11) DEFAULT NULL COMMENT '入库数量',`remark` varchar(50) DEFAULT NULL COMMENT '备注',`sum` decimal(8,2) DEFAULT NULL COMMENT '总价',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `repertory_pay`;
CREATE TABLE `repertory_pay` (`id` int(11) NOT NULL AUTO_INCREMENT,`iidno` int(11) DEFAULT NULL,`repertory_id` int(11) DEFAULT NULL COMMENT '订单ID',`pay_id` int(11) DEFAULT NULL COMMENT '支付类型 1现金2其他3微信4支付宝',`pay_amount` decimal(8,2) DEFAULT NULL COMMENT '付款金额',`add_time` datetime DEFAULT NULL COMMENT '新增时间',`remark` varchar(50) DEFAULT NULL COMMENT '注释',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',`can_edit` int(1) DEFAULT '1' COMMENT '是否能编辑 1能编辑 0不能  结算后不能编辑，反挂账或者反入账后可以编辑',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库支付明细表';

DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources` (`id` int(11) NOT NULL AUTO_INCREMENT,`name` varchar(20) NOT NULL COMMENT '菜单名称',`parent_id` int(11) DEFAULT NULL COMMENT '父菜单',`res_key` varchar(20) DEFAULT NULL COMMENT '资源主键',`type` varchar(1) DEFAULT NULL COMMENT '资源类型1菜单',`res_url` varchar(120) DEFAULT NULL COMMENT '资源url',`level` int(10) DEFAULT NULL COMMENT '排序',`description` varchar(150) DEFAULT NULL COMMENT '描述',`icon` varchar(150) DEFAULT NULL COMMENT '图标',`enabled` varchar(1) DEFAULT NULL COMMENT '是否启用1启用',`create_date` datetime DEFAULT NULL COMMENT '创建时间',`itemed` int(1) DEFAULT '0' COMMENT '是否默认展开1展开0不展开',PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COMMENT='资源菜单表';

INSERT INTO `resources` VALUES (1, '基本配置', 0, 'sys_mgr', '1', 'sys_mgr', 9, '系统管理菜单', 'icon-peizhi', '1', '2019-4-1 17:18:23', 0);
INSERT INTO `resources` VALUES (2, '员工管理', 1, 'user_mgr', '1', 'user/userinfo.do', 2, '用户管理菜单', 'icon-yuangongguanli', '0', '2019-4-1 17:20:35', 0);
INSERT INTO `resources` VALUES (3, '角色管理', 9, 'role_mgr', '1', 'role/roleinfo.do', 1, '角色管理菜单', 'icon-yuangong', '1', '2019-4-1 17:20:40', 0);
INSERT INTO `resources` VALUES (4, '菜单管理', 1, 'menu_mgr', '1', 'menu/index.do', 4, '菜单管理菜单', 'icon-xitongguanli', '0', '2019-4-1 17:20:37', 0);
INSERT INTO `resources` VALUES (5, '客户管理', 0, 'custom_mgr', '1', 'custom_mgr', 2, '客户管理菜单', 'icon-kehuguanli-copy-copy', '1', '2019-4-11 09:19:20', 0);
INSERT INTO `resources` VALUES (6, '订单管理', 0, 'orders_mgr', '1', 'orders_mgr', 1, '订单管理', 'icon-dingdanguanli-', '1', '2019-4-11 13:47:49', 1);
INSERT INTO `resources` VALUES (7, '订单列表', 6, 'orderlist_mgr', '1', 'orders/orderList/1.do', 1, '订单列表', 'icon-dingdan', '1', '2019-4-11 13:49:04', 0);
INSERT INTO `resources` VALUES (8, '会员列表', 5, 'customlist_mgr', '1', 'custom/index.do', 1, '客户列表菜单', 'icon-yuangongguanli', '1', '2019-4-11 15:27:44', 0);
INSERT INTO `resources` VALUES (9, '员工管理', 0, 'employ_mgr', '1', 'employ_mgr', 4, '员工管理一级菜单', 'icon-yuangongguanli', '1', '2019-4-12 15:39:24', 0);
INSERT INTO `resources` VALUES (10, '员工技能', 9, 'employ_skill', '1', 'userskills/infolist.do', 3, '员工技能配置', 'icon-zhuanyezhishijineng', '1', '2019-4-12 15:46:23', 0);
INSERT INTO `resources` VALUES (11, '员工信息', 9, 'employ_info', '1', 'user/userinfo.do', 2, '员工信息', 'icon-yuangong', '1', '2019-4-12 15:48:36', 0);
INSERT INTO `resources` VALUES (12, '配置', 0, 'configure_mgr', '1', 'configure_mgr', 6, '配置一级菜单', 'icon-xitongguanli', '0', '2019-4-12 15:52:53', 0);
INSERT INTO `resources` VALUES (13, '服务配置', 1, 'config_service', '1', 'config_service', 2, '服务配置', 'icon-fuwufei', '1', '2019-4-12 15:54:50', 0);
INSERT INTO `resources` VALUES (14, '会员配置', 1, 'config_vip', '1', 'config_vip', 1, '会员配置', 'icon-type', '0', '2019-4-12 15:55:49', 0);
INSERT INTO `resources` VALUES (15, '服务分类', 13, 'service_type', '1', 'serveclass/infolist.do', 1, '服务分类', 'icon-fenlei', '1', '2019-4-12 15:57:07', 0);
INSERT INTO `resources` VALUES (16, '服务项目', 13, 'service_subject', '1', 'serve/infolist.do', 2, '服务项目', 'icon-fuwufei', '1', '2019-4-12 15:58:40', 0);
INSERT INTO `resources` VALUES (17, '车辆列表', 5, 'carlist_mgr', '1', 'car/index.do', 1, '车辆列表菜单', 'icon-yuangongguanli', '1', '2019-4-16 08:43:01', 0);
INSERT INTO `resources` VALUES (18, '提成配置', 1, 'config_royalty', '1', 'config_royalty', 4, '提成配置', 'icon-tichengguize', '1', '2019-4-16 17:09:34', 0);
INSERT INTO `resources` VALUES (19, '服务提成', 18, 'royalty_server', '1', 'royalty_server', 1, '服务提成', 'icon-fuwufei', '1', '2019-4-16 17:11:56', 0);
INSERT INTO `resources` VALUES (20, '产品提成', 18, 'royalty_product', '1', 'royalty_product', 2, '产品提成', 'icon-caiwuguanli', '1', '2019-4-16 17:13:11', 0);
INSERT INTO `resources` VALUES (21, '施工提成', 19, 'construction_royalty', '1', 'royalty/infolist/1.do', 1, '施工提成', 'icon-lirun2', '1', '2019-4-16 17:43:11', 0);
INSERT INTO `resources` VALUES (22, '销售提成', 19, 'sale_royalty', '1', 'royalty/infolist/2.do', 2, '销售提成', 'icon-caiwuguanli', '1', '2019-4-16 17:49:25', 0);
INSERT INTO `resources` VALUES (23, '车辆保险到期', 5, 'carInsurelist_mgr', '1', 'car/insureIndex.do', 3, '车辆保险到期', 'icon-yuangongguanli', '1', '2019-4-17 11:00:11', 0);
INSERT INTO `resources` VALUES (24, '产品销售提成', 20, 'royalty_pro_sales', '1', 'royalty/infolist/3.do', 1, '产品销售提成', 'icon-caiwuguanli', '1', '2019-4-18 14:54:02', 0);
INSERT INTO `resources` VALUES (25, '产品配置', 1, 'config_product', '1', 'config_product', 3, '产品配置', 'icon-chanpinpeizhi', '1', '2019-4-18 15:32:21', 0);
INSERT INTO `resources` VALUES (26, '产品分类', 25, 'product_class', '1', 'productclass/infolist.do', 1, '产品分类', 'icon-fenlei', '1', '2019-4-18 15:35:41', 0);
INSERT INTO `resources` VALUES (27, '产品信息', 25, 'product_info', '1', 'product/infolist.do', 2, '产品信息', 'icon-chanpinxinxi', '1', '2019-4-18 15:36:22', 0);
INSERT INTO `resources` VALUES (28, '车检到期', 5, 'carChecklist_mgr', '1', 'car/checkIndex.do', 4, '车检到期菜单', 'icon-yuangongguanli', '1', '2019-4-19 09:04:52', 0);
INSERT INTO `resources` VALUES (29, '提醒', 0, 'remind_mgr', '1', 'remind_mgr', 5, '提醒管理菜单', 'icon-tixing', '1', '2019-4-19 13:29:01', 0);
INSERT INTO `resources` VALUES (30, '日常提醒', 29, 'remindlist_mgr', '1', 'remind/index.do', 1, '日常提醒菜单', 'icon-yuangongguanli', '1', '2019-4-19 13:31:22', 0);
INSERT INTO `resources` VALUES (31, '已完成提醒', 29, 'remindFinsh', '1', 'remind/finishIndex.do', 2, '已经完成提醒菜单', 'icon-yuangongguanli', '1', '2019-4-19 15:42:49', 0);
INSERT INTO `resources` VALUES (32, '库存管理', 0, 'stock_mgr', '1', 'stock_mgr', 3, '库存管理', 'icon-kucunguanli', '1', '2019-4-19 15:50:06', 0);
INSERT INTO `resources` VALUES (33, '供应商信息', 32, 'supply', '1', 'supply/infolist.do', 2, '供应商信息', 'icon-gongyingshangguanli', '1', '2019-4-19 15:51:54', 0);
INSERT INTO `resources` VALUES (34, '采购入库', 32, 'repertory_in', '1', 'repertory/infolist/1.do', 3, '采购入库', 'icon-ruku', '1', '2019-4-22 10:23:49', 0);
INSERT INTO `resources` VALUES (35, '挂账订单', 6, 'debt_orders', '1', 'orders/orderList/2.do', 1, '挂账订单', 'icon-dingdan', '1', '2019-4-25 09:59:00', 0);
INSERT INTO `resources` VALUES (36, '入账订单', 6, 'pay_orders', '1', 'orders/orderList/3.do', 1, '入账订单', 'icon-dingdan', '1', '2019-4-25 09:59:56', 0);
INSERT INTO `resources` VALUES (37, '财务管理', 0, 'finance_mgr', '1', 'finance_mgr', 7, '财务管理', 'icon-caiwuguanli', '1', '2019-4-25 16:52:31', 0);
INSERT INTO `resources` VALUES (38, '财务日记账', 37, 'fance_day_mgr', '1', 'payRecord/payRecordList.do', 3, '财务日记账', 'icon-dingdan', '1', '2019-4-25 16:53:57', 0);
INSERT INTO `resources` VALUES (39, '采购记录', 32, 'repertory_in_record', '1', 'repertory/historylist/1.do', 4, '采购记录', 'icon-caigoudan', '1', '2019-4-26 13:21:19', 0);
INSERT INTO `resources` VALUES (40, '过期查询', 5, 'customExpirelist_mgr', '1', 'custom/expireIndex.do', 2, '过期查询菜单', 'icon-yuangongguanli', '1', '2019-4-26 13:23:34', 0);
INSERT INTO `resources` VALUES (41, '采购挂账', 32, 'caigou_debt', '1', 'debtRecord/infolist/2.do', 5, '采购挂账', 'icon-caigoudan', '1', '2019-4-26 17:06:04', 0);
INSERT INTO `resources` VALUES (42, '客户跨店', 5, 'customShopList_mgr', '1', 'custom/otherShopIndex.do', 3, '客户跨店菜单', 'icon-yuangongguanli', '1', '2019-4-27 15:31:17', 0);
INSERT INTO `resources` VALUES (43, '员工提成概览', 37, 'user_royal_mgr', '1', 'royalty/userRoyalList.do', 4, '员工提成概览', 'icon-dingdan', '1', '2019-4-27 15:51:25', 0);
INSERT INTO `resources` VALUES (44, '采购退货', 32, 'repertory_out', '1', 'repertory/infolist/2.do', 6, '采购退货', 'icon-ruku', '1', '2019-4-29 11:46:52', 0);
INSERT INTO `resources` VALUES (45, '退货记录', 32, 'repertory_out_record', '1', 'repertory/historylist/2.do', 7, '退货记录', 'icon-caigoudan', '1', '2019-4-29 11:50:51', 0);
INSERT INTO `resources` VALUES (46, '退货挂账', 32, 'tuihuo_debt', '1', 'debtRecord/infolist/3.do', 8, '退货挂账', 'icon-caigoudan', '1', '2019-4-29 11:51:05', 0);
INSERT INTO `resources` VALUES (47, '财务配置', 1, 'finace_config', '1', 'finace_config', 4, '财务配置', 'icon-dingdan', '0', '2019-4-30 11:42:01', 0);
INSERT INTO `resources` VALUES (48, '日常收支类型', 37, 'daily_pay_record', '1', 'dailyPay/payTypeList.do', 2, '日常收支类型', 'icon-dingdan', '1', '2019-4-30 11:43:03', 0);
INSERT INTO `resources` VALUES (49, '日常收支统计', 37, 'daily_pay_record', '1', 'dailyPay/payRecordList.do', 5, '日常收支统计', 'icon-dingdan', '1', '2019-4-30 14:40:38', 0);
INSERT INTO `resources` VALUES (50, '库存管理', 32, 'stock_mgr', '1', 'product/stockInfo.do', 1, '库存管理', 'icon-caigoudan', '1', '2019-5-2 08:57:29', 0);
INSERT INTO `resources` VALUES (51, '客户邀约', 29, 'invitation_mgr', '1', 'remind/invitationIndex.do', 3, '客户邀约菜单', 'icon-yuangongguanli', '1', '2019-5-6 09:07:14', 0);
INSERT INTO `resources` VALUES (52, '邀约历史', 29, 'history_mgr', '1', 'remind/invitationHistory.do', 4, '邀约历史菜单', 'icon-yuangongguanli', '1', '2019-5-8 10:34:16', 0);
INSERT INTO `resources` VALUES (55, '支付类型', 37, 'pay_type', '1', 'payType/payTypeList.do', 1, '支付类型', 'icon-caigoudan', '1', '2019-5-8 14:27:11', 0);
INSERT INTO `resources` VALUES (56, '会员配置', 1, 'customType_mgr', '1', 'customType_config', 5, '会员配置菜单', 'icon-yuangongguanli', '1', '2019-5-8 14:38:39', 0);
INSERT INTO `resources` VALUES (57, '会员类型', 56, 'customType_mgr', '1', 'customType/index.do', 1, '会员类型菜单', 'icon-yuangongguanli', '1', '2019-5-8 17:15:41', 0);
INSERT INTO `resources` VALUES (58, '套餐配置', 56, 'meal_mgr', '1', 'meal/index.do', 2, '套餐配置菜单', 'icon-yuangongguanli', '1', '2019-5-9 09:02:06', 0);
INSERT INTO `resources` VALUES (59, '盘点历史', 32, 'pd_history', '1', 'repertoryCheck/infolist.do', 9, '盘点历史', 'icon-caigoudan', '1', '2019-5-9 14:07:48', 0);
INSERT INTO `resources` VALUES (60, '调拨历史', 32, 'db_history', '1', 'productAllocation/history.do', 91, '调拨历史', 'icon-caigoudan', '1', '2019-5-9 14:08:15', 0);
INSERT INTO `resources` VALUES (61, '服务价格', 56, 'service_price', '1', 'customprice/infolist/1.do', 3, '服务价格', 'icon-yuangongguanli', '1', '2019-5-11 09:16:12', 0);
INSERT INTO `resources` VALUES (62, '产品价格', 56, 'product_price', '1', 'customprice/infolist/2.do', 4, '产品价格', 'icon-yuangongguanli', '1', '2019-5-11 09:16:32', 0);
INSERT INTO `resources` VALUES (63, '客户预约', 29, 'appointlist_mgr', '1', 'appoint/index.do', 5, '客户预约菜单', 'icon-yuangongguanli', '1', '2019-5-17 10:18:11', 0);
INSERT INTO `resources` VALUES (64, '利润统计', 37, 'profitList', '1', 'payRecord/profitList.do', 6, '利润统计', 'icon-dingdan', '1', '2019-5-17 16:13:03', 0);
INSERT INTO `resources` VALUES (65, '库存预警', 29, 'preview_product', '1', 'product/previewinfo.do', 6, '库存预警', 'icon-yuangongguanli', '1', '2019-5-20 11:21:50', 0);
INSERT INTO `resources` VALUES (66, '代金卷', 1, 'coupon_mgr', '1', 'coupon_mgr', 6, '代金卷', 'icon-yuangongguanli', '1', '2019-5-20 11:21:50', 0);
INSERT INTO `resources` VALUES (67, '会员代金卷', 66, 'user_coupon', '1', 'coupon/couponlist.do', 1, '会员代金卷', 'icon-yuangongguanli', '1', '2019-5-20 11:21:50', 0);
INSERT INTO `resources` VALUES (68, '代金卷包', 66, 'coupon_pack', '1', 'coupon/couponpacklist.do', 2, '代金卷包', 'icon-yuangongguanli', '1', '2019-5-20 11:21:50', 0);
INSERT INTO `resources` VALUES (69, '充值记录', 5, 'RECHARGE_RECORD', '1', 'recharge/rechargelist.do', 7, '充值记录', 'icon-yuangongguanli', '1', '2019-5-20 11:21:50', 0);
INSERT INTO `resources` VALUES (70, '消费记录', 5, 'consume_record', '1', 'orders/orderList/4.do', 8, '消费记录', 'icon-yuangongguanli', '1', '2019-5-20 11:21:50', 0);
INSERT INTO `resources` VALUES (71, '微信', 0, 'WECHAT_CONFIG', '1', 'WECHAT_CONFIG', 7, '微信管理', 'icon-weixin', '1', '2019-7-29 13:11:39', 0);
INSERT INTO `resources` VALUES (72, '营销', 0, 'MARKETING', '1', 'MARKETING', 8, '营销管理', 'icon-yingxiao', '0', '2019-7-29 13:14:47', 0);
INSERT INTO `resources` VALUES (73, '活动群发', 71, 'SEND_CONFIG', '1', 'wechatMessage/groupSend.do', 1, '活动群发', 'icon-dingdan', '1', '2019-7-29 13:56:49', 0);
INSERT INTO `resources` VALUES (74, '微信卡卷', 71, 'COUPON_CONFIG', '1', 'COUPON_CONFIG', 2, '微信卡卷', 'icon-dingdan', '0', '2019-7-29 13:58:50', 0);
INSERT INTO `resources` VALUES (75, '微信设置', 71, 'WECHAT_MENU_CONFIG', '1', 'WECHAT_MENU_CONFIG', 3, '微信设置', '', '0', '2019-7-29 14:09:24', 0);
INSERT INTO `resources` VALUES (76, '微信素材', 71, 'WECHAT_PIC', '1', 'WECHAT_PIC', 4, '微信素材', '', '0', '2019-7-29 14:10:06', 0);
INSERT INTO `resources` VALUES (77, '公众号助手', 71, 'WECHAT_HELP', '1', 'WECHAT_HELP', 5, '公众号助手', '', '0', '2019-7-29 14:11:42', 0);
INSERT INTO `resources` VALUES (78, '卡卷管理', 71, 'CARD_MGR', '1', 'wechatcardrecord/index.do', 2, '卡卷管理', '', '1', '2019-7-29 15:11:00', 0);
INSERT INTO `resources` VALUES (79, '素材库', 71, 'PIC_MGR', '1', 'managerImg/index.do', 3, '素材库', '', '1', '2019-7-29 15:12:00', 0);
INSERT INTO `resources` VALUES (80, '统计分析', 71, 'statistical_analysis', '1', 'statistical_analysis', 4, '统计分析', '', '0', '2019-7-29 15:16:53', 0);
INSERT INTO `resources` VALUES (81, '渠道分红', 72, 'CUST_BONUS', '1', 'CUST_BONUS', 1, '渠道分红', '', '0', '2019-7-29 15:19:39', 0);
INSERT INTO `resources` VALUES (82, '股东分红', 72, 'Shareholder_Dividend', '1', 'Shareholder_Dividend', 2, '股东分红', '', '0', '2019-7-29 15:21:39', 0);
INSERT INTO `resources` VALUES (83, '卡卷设置', 71, 'CARD_SET', '1', 'cardset/index.do', 1, '卡卷设置', '', '1', '2019-7-31 10:51:18', 0);
INSERT INTO `resources` VALUES (84, '系统参数', 1, 'sys_param_mgr', '1', 'sysParam/paramlist.do', 7, '系统参数', '', '1', '2019-8-5 10:55:07', 0);
INSERT INTO `resources` VALUES (85, 'OA办公', 1, 'oa_mgr', '1', 'oa_mgr', 8, 'OA办公', '', '1', '2019-8-5 15:55:47', 0);
INSERT INTO `resources` VALUES (86, '模版配置', 85, 'oa_template', '1', 'workflowTemplate/templatelist.do', 1, '模版配置', '', '1', '2019-8-5 15:57:09', 0);
INSERT INTO `resources` VALUES (87, '我发起的审批', 85, 'my_instance', '1', 'workflowInstance/instancelist.do', 2, '我发起的审批', '', '1', '2019-8-5 16:00:51', 0);
INSERT INTO `resources` VALUES (88, '待审批', 85, 'oa_checklist', '1', 'workflowInstance/checklist.do', 3, '待审批', '', '1', '2019-8-5 16:02:56', 0);
INSERT INTO `resources` VALUES (89, '全部审批', 85, 'all_workflow', '1', 'workflowInstance/allinstancelist.do', 4, '全部审批', '', '1', '2019-8-7 15:58:40', 0);
INSERT INTO `resources` VALUES (90, '门店信息完善', 1, 'COMPLENTSHOP', '1', 'shop/toEditShop.do', 9, '门店信息完善', '', '1', '2019-8-13 16:42:30', 0);
INSERT INTO `resources` VALUES (91, '图文管理', 71, 'MSG_NEWS_MGR', '1', 'wechatMessage/msgNewsList.do', 4, '图文管理', '', '1', '2019-8-14 16:24:10', 0);
INSERT INTO `resources` VALUES ('92', '报表管理', '0', 'REPORT_MGR', '1', 'REPORT_MGR', '7', '报表管理', 'icon-yingxiao', '1', '2019-08-19 15:09:58', '0');
INSERT INTO `resources` VALUES ('93', '订单统计', '92', 'SLIP_REPORT', '1', 'report/orderReport.do', '1', '订单统计', '', '1', '2019-08-19 15:12:18', '0');
INSERT INTO `resources` VALUES ('94', '充值统计', '92', 'RECHARGE_REPORT', '1', 'report/rechargeReport.do', '2', '充值统计', '', '1', '2019-08-19 15:13:36', '0');
INSERT INTO `resources` VALUES ('95', '会员流失', '92', 'CUSTOM_REPORT', '1', 'report/lossReport.do', '3', '会员流失', '', '1', '2019-08-19 15:14:15', '0');
INSERT INTO `resources` VALUES ('96', '车辆消费', '92', 'CAR_REPORT', '1', 'report/carConsumeReport.do', '4', '车辆消费', '', '1', '2019-08-19 15:14:47', '0');
INSERT INTO `resources` VALUES ('97', '微信统计', '92', 'WECHAT_REPORT', '1', 'report/wechatReport.do', '5', '微信统计', '', '1', '2019-08-19 15:15:47', '0');
INSERT INTO `resources` VALUES ('98', '业绩统计', '92', 'ACHIEVEMENT_REPORT', '1', 'report/achievementReport.do', '6', '业绩统计', '', '1', '2019-08-19 15:17:44', '0');
INSERT INTO `resources` VALUES ('99', '员工产值', '92', 'EMPLOY_REPORT', '1', 'report/userRoyaltyReport.do', '7', '员工产值', '', '1', '2019-08-19 15:18:15', '0');
INSERT INTO `resources` VALUES ('100', '库存消耗', '92', 'STOCK_REPORT', '1', 'report/stockReport.do', '8', '库存消耗', '', '1', '2019-08-19 15:19:40', '0');
INSERT INTO `resources` VALUES ('101', '微信菜单', '71', 'WECHAT_MENU', '1', 'wechatmenu/wechatmenu.do', '6', '微信菜单', '', '1', '2019-08-19 15:19:40', '0');
DROP TABLE IF EXISTS `resources_role`;
CREATE TABLE `resources_role` (`id` int(11) NOT NULL AUTO_INCREMENT,`resc_id` int(11) DEFAULT NULL,`role_id` int(11) DEFAULT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=307 DEFAULT CHARSET=utf8;

INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('1', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('2', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('3', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('4', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('5', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('6', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('7', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('8', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('9', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('10', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('11', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('12', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('13', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('14', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('15', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('16', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('17', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('18', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('19', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('20', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('21', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('22', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('23', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('24', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('25', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('26', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('27', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('28', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('29', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('30', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('31', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('32', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('33', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('34', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('35', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('36', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('37', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('38', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('39', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('40', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('41', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('42', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('43', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('44', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('45', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('46', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('47', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('48', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('49', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('50', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('51', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('52', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('55', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('56', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('57', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('58', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('59', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('60', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('61', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('62', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('63', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('64', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('65', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('66', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('67', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('68', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('69', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('70', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('71', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('72', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('73', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('74', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('75', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('76', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('77', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('78', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('79', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('80', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('81', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('82', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('83', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('84', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('85', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('86', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('87', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('88', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('89', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('90', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('91', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('92', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('93', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('94', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('95', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('96', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('97', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('98', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('99', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('100', '1');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('6', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('36', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('35', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('7', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('5', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('17', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('8', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('40', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('23', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('42', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('28', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('32', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('50', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('33', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('34', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('39', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('41', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('44', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('45', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('46', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('59', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('60', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('29', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('30', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('31', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('51', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('52', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('63', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('65', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('9', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('10', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('11', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('1', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('13', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('15', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('16', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('25', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('26', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('27', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('3', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('47', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('48', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('55', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('4', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('18', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('19', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('21', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('22', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('20', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('24', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('56', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('57', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('58', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('61', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('62', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('66', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('37', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('38', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('43', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('49', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('64', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('66', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('67', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('68', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('69', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('70', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('71', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('72', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('73', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('74', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('75', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('76', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('77', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('78', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('79', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('80', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('81', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('82', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('83', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('84', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('85', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('86', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('87', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('88', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('89', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('90', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('91', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('92', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('93', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('94', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('95', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('96', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('97', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('98', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('99', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('100', '2');
INSERT INTO `resources_role`(`resc_id`,`role_id`) VALUES ('101', '1');

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (`id` int(11) NOT NULL AUTO_INCREMENT,`name` varchar(50) DEFAULT NULL COMMENT '角色名称',`role_key` varchar(20) DEFAULT NULL COMMENT '角色标识',`description` varchar(200) DEFAULT NULL COMMENT '描述',`enable` varchar(1) DEFAULT NULL COMMENT '是否启用 1启用  0禁用',PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

INSERT INTO `role` VALUES ('1', 'admin', 'ROLE_ADMIN', '系统管理员', '1');
INSERT INTO `role` VALUES ('2', '老板', 'ROLE_BOSS', '老板', '1');

DROP TABLE IF EXISTS `royalty`;
CREATE TABLE `royalty` (`id` int(11) NOT NULL AUTO_INCREMENT,`kind` varchar(1) DEFAULT NULL COMMENT '提成类型 ''1''施工服务提成 ‘2’产品服务提成 3产品销售提成',`royalty_id` int(11) DEFAULT NULL COMMENT '提成对应的id 1服务2服务 3产品',`royalty_type` varchar(1) DEFAULT '1' COMMENT '提成类型 1固定值2百分比',`royalty_count` decimal(8,2) DEFAULT '0.00' COMMENT '提成金额',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `serve`;
CREATE TABLE `serve` (`id` int(11) NOT NULL AUTO_INCREMENT,`serve_name` varchar(20) DEFAULT NULL COMMENT '服务名称',`class_id` int(11) DEFAULT NULL COMMENT '产品分类',`price` decimal(8,2) DEFAULT '0.00' COMMENT '价格',`remark` varchar(50) DEFAULT NULL COMMENT '备注',`sgtc` decimal(8,2) DEFAULT NULL COMMENT '施工提成',`sz` int(11) DEFAULT NULL COMMENT '时钟',`zq` decimal(8,2) DEFAULT NULL COMMENT '周期',`construction` varchar(1) DEFAULT '0' COMMENT '1免施工',`completion` varchar(1) DEFAULT '0' COMMENT '1免完工',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',`del` int(1) DEFAULT '0' COMMENT '删除标志 1已删除0未删除',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务';
INSERT INTO serve ( `serve_name`, `class_id`, `price`, `remark`, `sgtc`, `sz`, `zq`, `construction`, `completion`, `del`) VALUES ('夜间洗车', -1, 20.00, NULL, NULL, NULL, NULL, '0', '0', 0);

DROP TABLE IF EXISTS `serve_class`;
CREATE TABLE `serve_class` (`id` int(11) NOT NULL AUTO_INCREMENT,`class_name` varchar(20) DEFAULT NULL COMMENT '名称',`remark` varchar(50) DEFAULT NULL COMMENT '备注',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO serve_class (`id`, `class_name`, `remark`) VALUES (-1, '夜间服务', '');

DROP TABLE IF EXISTS `supply`;
CREATE TABLE `supply` (`id` int(11) NOT NULL AUTO_INCREMENT,`supply_name` varchar(20) DEFAULT NULL COMMENT '供应商名称',`contact_name` varchar(20) DEFAULT NULL COMMENT '联系人名称',`cellphone` varchar(15) DEFAULT NULL COMMENT '手机号',`telephone` varchar(15) DEFAULT NULL COMMENT '座机号',`address` varchar(100) DEFAULT NULL COMMENT '地址',`remark` varchar(100) DEFAULT NULL COMMENT '备注',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',`enabled` int(1) DEFAULT '1' COMMENT '1启用0禁用',`candel` int(1) DEFAULT '1' COMMENT '是否是内置数据  1允许删除0不允许删除',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商表';

DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (`id` int(11) NOT NULL AUTO_INCREMENT,`iidno` int(11) DEFAULT NULL COMMENT '显示顺序',`param_name` varchar(50) DEFAULT NULL COMMENT '参数名称',`param_value` varchar(50) DEFAULT NULL COMMENT '参数值',`create_time` datetime DEFAULT NULL COMMENT '创建时间',`opt_time` datetime DEFAULT NULL COMMENT '操作时间',`opt_user` int(11) DEFAULT NULL COMMENT '操作人',`remark` varchar(50) DEFAULT NULL COMMENT '备注',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',`class_name` varchar(50) DEFAULT NULL COMMENT '类名',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统参数表';

DROP TABLE IF EXISTS `user_skills`;
CREATE TABLE `user_skills` (`id` int(11) NOT NULL AUTO_INCREMENT,`user_id` int(11) DEFAULT NULL COMMENT '员工id',`serve_id` int(11) DEFAULT NULL COMMENT '服务id',`shop_id` int(11) DEFAULT NULL COMMENT '门店ID',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工技能表';

DROP TABLE IF EXISTS `wechat_send_record`;
CREATE TABLE `wechat_send_record` (`id` int(11) NOT NULL AUTO_INCREMENT,`wechat_card_name` varchar(50) DEFAULT NULL COMMENT '优惠券名称',`wechat_card_value` decimal(8,2) DEFAULT NULL COMMENT '优惠券面值',`wechat_name` varchar(50) DEFAULT NULL COMMENT '昵称',`cust_id` int(11) DEFAULT NULL COMMENT '绑定客户信息',`end_date` date DEFAULT NULL COMMENT '有效截止日期',`get_date` date DEFAULT NULL COMMENT '领取日期',`remark` varchar(150) DEFAULT NULL COMMENT '备注',`state` int(1) DEFAULT NULL COMMENT '状态：0未使用1已使用2已作废3已占用',`use_date` datetime DEFAULT NULL COMMENT '使用时间',`use_car` varchar(50) DEFAULT NULL COMMENT '使用车辆信息',`slip_no` varchar(20) DEFAULT NULL COMMENT '使用单据号码',`slip_amount` decimal(8,2) DEFAULT NULL COMMENT '订单使用金额',`void_remark` varchar(150) DEFAULT NULL COMMENT '作废说明',`cardset_remark` varchar(100) DEFAULT NULL COMMENT '卡卷备注',`cardset_id` int(11) DEFAULT NULL COMMENT '微信卡卷的id',`opt_user_name` varchar(50) DEFAULT NULL COMMENT '收银员',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `wxcms_img_resource`;
CREATE TABLE `wxcms_img_resource` (`id` int(11) NOT NULL AUTO_INCREMENT,`media_id` varchar(100) DEFAULT NULL COMMENT '上传素材id',`true_name` varchar(100) NOT NULL COMMENT '文件名称',`type` varchar(10) NOT NULL COMMENT '媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）',`name` varchar(100) NOT NULL COMMENT '文件名',`url` varchar(200) NOT NULL COMMENT '文件上传路径',`http_url` varchar(200) DEFAULT NULL COMMENT '公众号文件路径',`size` int(9) NOT NULL COMMENT '图片尺寸',`create_time` datetime NOT NULL COMMENT '创建时间',`update_time` datetime NOT NULL COMMENT '更新时间',`flag` int(1) NOT NULL DEFAULT '0',`account` varchar(100) DEFAULT NULL COMMENT '微信账号',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `wxcms_group_send`;
CREATE TABLE `wxcms_group_send` (`id` int(11) NOT NULL AUTO_INCREMENT,`send_user_type` int(11) DEFAULT NULL COMMENT '发送对象类型  1客户  2微信粉丝',`tag_id` varchar(20) DEFAULT NULL COMMENT '标签ID all标签 ',`content_type` int(11) DEFAULT NULL COMMENT '内容类型 1文本  2图片 3图文',`send_content` text COMMENT '文本内容',`media_id` varchar(100) DEFAULT NULL,`choose_openids` text COMMENT '选择的OPENID数组',`user_count` int(11) DEFAULT NULL COMMENT '发送的客户数量',`app_id` varchar(100) DEFAULT NULL COMMENT '公众号ID',`create_time` datetime DEFAULT NULL COMMENT '发送时间',`send_status` int(11) DEFAULT NULL COMMENT '0发送中 1发送成功 2发送失败',`error_msg` text,PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

DROP TABLE IF EXISTS `wxcms_article`;
CREATE TABLE `wxcms_article` (`ar_id` int(11) NOT NULL AUTO_INCREMENT,`title` varchar(30) DEFAULT NULL,`author` varchar(50) DEFAULT NULL,`content` longtext,`digest` varchar(100) DEFAULT NULL,`show_cover_pic` int(1) DEFAULT '0',`url` varchar(200) DEFAULT NULL,`thumb_media_id` varchar(150) DEFAULT NULL,`content_source_url` varchar(200) DEFAULT NULL,`media_id` varchar(150) DEFAULT NULL,`news_id` int(11) DEFAULT NULL,`news_index` int(11) DEFAULT NULL,`pic_url` varchar(200) DEFAULT NULL,`need_open_comment` int(1) DEFAULT '0' COMMENT '是否打开评论，0不打开，1打开',`only_fans_can_comment` int(1) DEFAULT '0' COMMENT '是否粉丝才可评论，0所有人可评论，1粉丝才可评论',PRIMARY KEY (`ar_id`),KEY `news_id` (`news_id`) USING BTREE) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

DROP TABLE IF EXISTS `wxcms_msg_news`;
CREATE TABLE `wxcms_msg_news` (`id` int(11) NOT NULL AUTO_INCREMENT,`mult_type` varchar(5) DEFAULT NULL COMMENT '单图文多图文类型',`title` varchar(30) DEFAULT NULL,`author` varchar(255) DEFAULT NULL,`digest` varchar(255) DEFAULT NULL,`description` longtext,`pic_path` varchar(255) DEFAULT NULL,`show_pic` int(11) DEFAULT '0',`url` varchar(255) DEFAULT NULL,`from_url` varchar(255) DEFAULT NULL,`base_id` int(11) DEFAULT NULL,`media_id` varchar(100) DEFAULT NULL COMMENT '上传后返回的媒体素材id',`thumb_media_id` varchar(150) DEFAULT NULL COMMENT '封面图片id',`news_index` int(11) DEFAULT NULL COMMENT '多图文中的第几条',`account` varchar(100) DEFAULT NULL,`create_time` datetime DEFAULT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `wxcms_account_menu`;
CREATE TABLE `wxcms_account_menu` ( `id` int(11) NOT NULL AUTO_INCREMENT,`mtype` varchar(50) DEFAULT NULL COMMENT '菜单类型 ："click", "scancode_push", "scancode_waitmsg", "pic_sysphoto", "pic_photo_or_album", "pic_weixin", "location_select"', `event_type` varchar(50) DEFAULT NULL COMMENT '事件类型',`name` varchar(100) DEFAULT NULL COMMENT '菜单名称', `input_code` varchar(255) DEFAULT NULL,`url` varchar(255) DEFAULT NULL, `sort` int(11) DEFAULT NULL,`parent_id` int(11) DEFAULT NULL, `msg_type` varchar(64) DEFAULT NULL,`msg_id` varchar(100) DEFAULT NULL,`gid` int(11) DEFAULT NULL,`account` varchar(100) DEFAULT NULL,`create_time` datetime DEFAULT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

