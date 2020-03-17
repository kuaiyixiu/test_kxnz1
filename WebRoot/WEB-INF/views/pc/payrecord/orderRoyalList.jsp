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
			      <label class="layui-form-label">结算日期</label>
			      <div class="layui-input-inline">
			        <input type="text" class="layui-input" id="dateInput" placeholder=" - " lay-key="7" value="${dateRangeStartTimeStr} - ${dateRangeEndTimeStr}">
			      </div>
			    </div>
				<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
			</fieldset>
			
			
		</div>
		</form>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="ordersTable" lay-filter="tableInfo"></table>
			<script type="text/html" id="barLine">
            	<a class="layui-btn layui-btn-xs" lay-event="detail"  tabId="ordersTable">订单详情</a>
            </script>
		</div>
	</div>
</body>
</html>

<script type="text/javascript">
layui.use(['jquery'], function() {
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
	var　 url = "royalty/getOrderRoyalList.do";
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
			"dateRangeEndTime": endTime,
			"kind" : ${ordersRoyalty.kind},
			"userId" : '${ordersRoyalty.userId}',
			"itemId" : '${ordersRoyalty.itemId}' 
		},
		done: function(res, curr, count) {
		
		},
		cols: [
			[{
				field : 'id',
				hide : 'true',
				align : 'center'
			},{
				field : 'orderId',
				hide : 'true',
				align : 'center'
			}, {
				field: 'finishTime',
				width: '30%',
				align: 'center',
				title: '结算时间',
				templet: function(row) {
					return formatDate(row.finishTime);
				}
			}, {
				field: 'itemName',
				width: '50%',
				align: 'center',
				<c:if test="${ordersRoyalty.kind eq '1'}">
					title: '项目名称'
				</c:if>
				<c:if test="${ordersRoyalty.kind eq '2'}">
					title: '服务名称'
				</c:if>
				<c:if test="${ordersRoyalty.kind eq '3'}">
					title: '产品名称'
				</c:if>
			}, {
				field: 'amount',
				width: '10%',
				align: 'center',
				title: '提成金额'
			}, {
				field: 'opt',
				width: '10%',
				align: 'center',
				title: '详情',
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
					"dateRangeEndTime": getEndTime(),
					"kind" : ${ordersRoyalty.kind},
					"userId" : '${ordersRoyalty.userId}',
					"itemId" : '${ordersRoyalty.itemId}' 
				}
			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});

});

function userDefinedToolHandle($arg, obj) {
	var data = obj.data
	var $ = layui.jquery;
	if (obj.event === 'detail') { //修改订单
		showDetail(data);
	}
}



var orderFrmId = 'orderFrm';
function showDetail(data){
	layui.use('element', function(){
		var element = parent.layui.element;
		var id = orderFrmId;
		var url = 'orders/toUpdateOrder/'+data.orderId+'/4.do';
		element.tabDelete('tab', id);
		element.tabAdd('tab', {
			title: "订单详情",
			content: '<iframe src="' + url + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
			id: id
		});
		element.tabChange('tab', id);
	});	
}

function getRangeDate(){
	var $ = layui.jquery;
	// 如果已有选择的日期
	var date = $("#dateInput").val();
	if (date) {
		return date;
	}
	var date1 = new Date(${dateRangeStartTime});
	date1.setDate(date1.getDate()-30);
	var date2 = new Date(${dateRangeEndTime});
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

function getRoy1(row){
	return row.kind1Amt + '/' + row.kind1Qty
}

function getRoy2(row){
	return row.kind2Amt + '/' + row.kind2Qty
}
function getRoy3(row){
	return row.kind3Amt + '/' + row.kind3Qty
}

function getTotal (row){
	return parseFloat(row.kind1Amt)  + parseFloat(row.kind2Amt) + parseFloat(row.kind3Amt)
}

function getuserRealName(row){
	return userMap[row.userId];
}

</script>

