<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>连途</title>
<%@ include file="../base.jsp"%>
<link rel="stylesheet" type="text/css" href="css/fieldset.css" />
<link rel="stylesheet" type="text/css" href="lib/admin/css/admin.css" />
<style type="text/css">
	.create-order {
    	background-color: #F1F1F1;
    	padding: 20px 0px;
    	border-radius: 5px 5px;
    	width: 60%;
    	margin-bottom: 20px;
	}
</style>
</head>
<body>
	<div class="page-content-wrap">
	<form class="layui-form">
		<div class="layui-form-item searchDiv">
			<fieldset>
				<legend>查询条件</legend>
				<div class="layui-inline">
			      <label class="layui-form-label">记账日期</label>
			      <div class="layui-input-inline">
			        <input type="text" class="layui-input" id="dateInput" placeholder=" - " lay-key="7">
			      </div>
				</div>
				<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
			</fieldset>
		</div>
		</form>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="ordersTable" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            <div class="layui-btn-container">
            	<button class="layui-btn layui-btn-sm addBtn" tabId="userInfo" data-url="dailyPay/toAddRecord.do" openw="800px;" openh="600px" lay-event="addRecord"><i class="layui-icon">&#xe608;</i>新增</button>
            </div>
            </script>
			<script type="text/html" id="barLine">
            	<a class="layui-btn layui-btn-xs" lay-event="updateRecord"  tabId="ordersTable" openw="800px;" openh="600px">修改</a>
            	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-url="dailyPay/delRecord.do" tabId="ordersTable">删除</a>
            </script>
		</div>
	</div>
			<input type="hidden" value='${payMap}' id="payMap">
</body>
</html>

<script type="text/javascript">
var payMap;
layui.use(['jquery'], function() {
	payMap = layui.jquery("#payMap").val();
	payMap = eval("("+payMap+")");
});

/**
 * 加载表格
 * @param $
 * @param table
 * @param parentId 父级id
 * @param id 当前等级id
 * @param type back返回类型
 * @returns
 */
function userDefinedLoadGrd($, table, parentId, id) {
	var time = getRangeDate();
	var startTime = time.substr(0, 10) + " 00:00:00";
	var endTime = time.substr(13) + " 23:59:59";
	var　 url = "dailyPay/getPayRecordList.do";
	//初始化网格
	var tableIns = table.render({
		elem: '#ordersTable',
		url: url,
		page: true,
		method: 'post',
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		//height: 'full-250',
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		where: {
			"dateRangeStartTime": startTime,
			"dateRangeEndTime": endTime
		},
		done: function(res, curr, count) {
		
		},
		cols: [
				[{
					field : 'id',
					hide : 'true',
					title : 'ID',
					align : 'center'
				}, {
					field: 'addTime',
					width: '15%',
					align: 'center',
					title: '录入日期',
					templet: function(row) {
						return formatDate(row.addTime);
					}
				}, {
					field: 'kind',
					width: '10%',
					align: 'center',
					title: '收支类型',
					templet: function(row) {
						return getKind(row);
					}
				}, {
					field: 'payTypeId',
					width: '20%',
					align: 'center',
					title: '类型',
					templet: function(row) {
						return getPayType(row);
					}
				}, {
					field: 'payId',
					width: '10%',
					align: 'center',
					title: '支付方式',
					templet: function(row) {
						return getPayId(row);
					}
				}, {
					field: 'amount',
					width: '10%',
					align: 'center',
					title: '金额',
					templet: function(row) {
						return parseFloat(row.amount).toFixed(2);
					}
				}, {
					field: 'sharePeriod',
					width: '25%',
					align: 'center',
					title: '分摊周期',
					templet: function(row) {
						return getPeriod(row);
					}
				}, {
					field: 'opt',
					width: '10%',
					align: 'center',
					title: '操作',
					toolbar: '#barLine'
				}]
			]
	});
}


layui.use(['jquery', 'table','laydate','form'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	var laydate = layui.laydate
	laydate.render({
		elem: "#dateInput",
		range: true,
		value: getRangeDate()
	});
	form.render();
	//查询事件
	var active = {
		reload: function() {
			// 执行重载
			table.reload('ordersTable', {
				method: 'post',
				page: {
					curr: 1
				},
				where: {
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

});


function getRangeDate() {
	var $ = layui.jquery;
	// 如果已有选择的日期
	var date = $("#dateInput").val();
	if (date) {
		return date;
	}
	var date1 = new Date();
	date1.setDate(date1.getDate()-30);
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

function userDefinedToolBarHandle(target, obj){
	var $ = layui.jquery;
	var url =target.attr('data-url');
	var openw = target.attr('openw');
	var openh = target.attr('openh');
	switch (obj.event) {
		case 'addRecord':
			layer.open({
				type: 2,
				title: "新增收支记录",
				area: [openw, openh],
				fixed: false, //不固定
				content: url,
				yes: function(index, layero){
				}
			});
			break;
	}
	
}


function userDefinedToolHandle($arg, obj) {
	var $ = layui.jquery;
	var url = "dailyPay/toUpdateRecord.do";
	var openw =  "800px";
	var openh = "600px";
	var id = obj.data.id;
	switch (obj.event) {
		case 'updateRecord':
			url +=  "?id=" + id;
			layer.open({
				type: 2,
				title: "修改收支记录",
				area: [openw, openh],
				fixed: false, //不固定
				content: url,
				yes: function(index, layero){
				}
			});
			break;
	}
}

function getPayId(row){
	var payId = row.payId;
	if (payId == "1")
		return "现金";
	else if (payId == "2")
		return "微信";
	else if (payId == "3")
		return "支付宝";
	else if (payId == "4")
		return "其他";
	return "";
}

function getKind(row){
	var kind = row.kind;
	if (kind == "1")
		return "收入";
	else if (kind == "2")
		return "支出";
	return "";
}

function getPeriod(row){
	return row.startSharePeriod +' ~ '+row.endSharePeriod;
}

function getPayType(row){
	return payMap[row.payTypeId];
}


</script>

