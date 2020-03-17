<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> -->
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>连途</title>
<%@ include file="../base.jsp"%>
<script type="text/javascript">

function userDefinedLoadForm(){
	var laydate = layui.laydate
	laydate.render({
		elem: "#dateInput",
		range: true,
		value: getRangeDate()
	});
}

function getRangeDate() {
	var $ = layui.jquery;
	// 如果已有选择的日期
	var date = $("#dateInput").val();
	if (date) {
		return date;
	}
	var date1 = new Date();
	date1.setDate(1);
	var date2 = new Date();
	var fromDate = formatDate1(date1);
	var nowDate = formatDate1(date2);
	return fromDate + " - " + nowDate
}

function getStartTime() {
	var time = getRangeDate();
	var startTime = time.substr(0, 10) + " 00:00:00";

	return startTime;
}

function getEndTime() {
	var time = getRangeDate();
	var endTime = time.substr(13) + " 23:59:59";

	return endTime;
}
	function userDefinedLoadGrd($, table) {
		var tableIns = table.render({
			elem : '#smsRechargeInfo',
			url : "sms/queryRechargeList.do",
			method : 'post',
			page : true,
			limit : 10,
			limits : [ 10, 20, 30, 40, 50 ],
			where : {
				"shopName" : $("#shopName").val().trim(),
				"dateRangeStartTime": getStartTime(),
				"dateRangeEndTime": getEndTime()
			},
			cols : [ [{
				field : 'createTime',
				align : 'center',
				title : '充值时间',
				templet: function(row) {
					return formatDate(row.createTime);
				}
			}, {
				field : 'shopName',
				align : 'center',
				title : '店名'
			},{
				field : 'beforeCount',
				align : 'center',
				title : '充值前数量'
			},{
				field : 'amount',
				align : 'center',
				title : '充值数量'
			},{
				field : 'afterCount',
				align : 'center',
				title : '充值后数量'
			},{
				field : 'optUser',
				align : 'center',
				title : '操作人'
			} ] ]
		});

		// 查询事件
		var active = {
			reload : function() {
				// 执行重载
				tableIns.reload({
					page : {
						curr : 1
					// 重新从第 1 页开始
					},
					where : {
						"shopName" : $("#shopName").val().trim(),
						"dateRangeStartTime": getStartTime(),
						"dateRangeEndTime": getEndTime()
					}
				});
			}
		};

		$('.searchDiv .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});

	}
</script>
</head>
<body>
	<div class="page-content-wrap">
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<div class="layui-fluid">
						<div class="layui-row">
							<div class="layui-col-md4">
								<div class="layui-form-item">
									<label class="layui-form-label">充值时间</label>
									<div class="layui-input-block">
										<input type="text" class="layui-input" id="dateInput" placeholder=" - " lay-key="7">
									</div>
								</div>
							</div>
							<div class="layui-col-md4">
								<div class="layui-form-item">
									<label class="layui-form-label">门店信息</label>
									<div class="layui-input-block">
										<input type="text" id="shopName" placeholder="请输入门店名称"
											autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-col-md2" align="center">
								<button class="layui-btn layui-btn-normal" data-type="reload"
									lay-filter="searchBtn" type="button">搜索</button>
							</div>
						</div>
					</div>
				</fieldset>

			</div>
		</form>

		<div class="layui-form" id="table-list">
			<table class="layui-table" id="smsRechargeInfo" lay-filter="tableInfo"></table>
		</div>
	</div>
</body>

</html>