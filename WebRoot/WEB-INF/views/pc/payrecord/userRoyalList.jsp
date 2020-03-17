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
			        <input type="text" class="layui-input" id="dateInput" placeholder=" - " lay-key="7" >
			      </div>
			    </div>
				<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
			</fieldset>
			
			
		</div>
		</form>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="ordersTable" lay-filter="tableInfo"></table>
			<script type="text/html" id="barLine">
            	<a class="layui-btn layui-btn-xs" lay-event="detail"  tabId="ordersTable">员工提成详情</a>
            </script>
		</div>
		<input type="hidden" value='${ userMap }' id="userMap">
	</div>
</body>
</html>

<script type="text/javascript">
var userMap;
layui.use(['jquery'], function() {
	userMap = layui.jquery("#userMap").val();
	userMap = eval("("+userMap+")");
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
	var　 url = "royalty/getUserRoyalList.do";
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
				field: 'userRealname',
				width: '20%',
				align: 'center',
				title: '员工姓名',
				templet: function(row) {
					return getuserRealName(row);
				}
			}, {
				field: 'roy1',
				width: '20%',
				align: 'center',
				title: '服务施工提成/单数',
				templet: function(row) {
					return getRoy1(row);
				}
			}, {
				field: 'roy2',
				width: '20%',
				align: 'center',
				title: '服务销售提成/单数',
				templet: function(row) {
					return getRoy2(row);
				}
			}, {
				field: 'roy3',
				width: '20%',
				align: 'center',
				title: '产品销售提成/单数',
				templet: function(row) {
					return getRoy3(row);
				}
			}, {
				field: 'total',
				width: '10%',
				align: 'center',
				title: '合计',
				templet: function(row) {
					return getTotal(row);
				}
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

function userDefinedToolHandle($arg, obj) {
	var data = obj.data
	var $ = layui.jquery;
	if (obj.event === 'detail') { //修改订单
		showDetail(data);
	}
}


var orderFrmId = 'userRoyalDetail';
function showDetail(data){
	var  param =  '?dateRangeStartTime='+getStartTime()+'&dateRangeEndTime='+getEndTime();
		layui.use('element', function(){
			var element = parent.layui.element;
			var id = orderFrmId;
			var url = 'royalty/userRoyalDetail/'+data.userId+'.do'+param;
			element.tabDelete('tab', id);
			element.tabAdd('tab', {
				title: "员工提成详情",
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

