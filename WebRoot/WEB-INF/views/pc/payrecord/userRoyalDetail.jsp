<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	.x-table {
  min-height: 1px;
  font-size: 14px;
  margin: 0px 20px 20px 20px;
  background-color: #f5f7f9; }
	.order-info-li{    margin-bottom: 5px;}
</style>
</head>
<body>
	<div class="page-content-wrap">
			<div class="layui-row section" style="">
				<div class="layui-col-sm6 section-title">员工信息 </div>
		</div>
		<div class="x-table">
			<div class="layui-row x-tr order-list">
			<ul>
		    	<li class="order-info-li">姓名:${user.userRealname } </li>
		     	<li class="order-info-li">联系方式:${user.userPhone } </li>
		     	<li class="order-info-li">入职时间:<fmt:formatDate value="${user.entryDate }" pattern="yyyy-MM-dd"/> </li>
		     	<li class="order-info-li">状态:<c:if test="${user.status eq '1' }">在职</c:if><c:if test="${user.status eq '0' }">离职</c:if>
		     	</li>
	 		</ul>
			</div>
		</div>
	
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
		<div class="layui-tab" lay-filter="roy">
		  <ul class="layui-tab-title">
		    <li class="layui-this">施工项目</li>
		    <li>销售服务</li>
		    <li>销售产品</li>
		    <li>会员充值</li>
		  </ul>
		  <div class="layui-tab-content" >
		    <div class="layui-tab-item layui-show" >
			   <div class="layui-form" id="table-list">
					<table class="layui-table" id="ordersTable1" lay-filter="tableInfo"></table>
				</div>
		    </div>
		    <div class="layui-tab-item">
			    <div class="layui-tab-item layui-show">
				   <div class="layui-form" id="table-list">
						<table class="layui-table" id="ordersTable2" lay-filter="tableInfo"></table>
					</div>
			    </div>	
		    </div>
		    <div class="layui-tab-item">
		    	<div class="layui-tab-item layui-show">
				   <div class="layui-form" id="table-list">
						<table class="layui-table" id="ordersTable3" lay-filter="tableInfo"></table>
					</div>
			    </div>	
		    </div>
		    <div class="layui-tab-item">
		    	<div class="layui-tab-item layui-show">
				   <div class="layui-form" id="table-list">
						<table class="layui-table" id="ordersTable4" lay-filter="tableInfo"></table>
					</div>
			    </div>	
		    </div>
		  </div>
		</div>
	</div>
</body>
	<script type="text/html" id="barLine">
     	<a class="layui-btn layui-btn-xs" lay-event="detail"  tabId="ordersTable">明细详情</a>
     </script>
</html>

<script type="text/javascript">
//注意：选项卡 依赖 element 模块，否则无法进行功能性操作
var tabIdx = 0;
var kind = 1;
layui.use('element', function(){
  var element = layui.element;
  element.on('tab(roy)', function(data){
	  tabIdx = data.index;
		if( tabIdx == 0 ){
			kind = 1;
		}else if ( tabIdx == 1){
			kind = 2;
		}else if ( tabIdx == 2){
			kind = 3;
		}
  });
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
	
	var　 url = "royalty/getUserRoyalDetail.do";
	var tableIns = table.render({
		elem: '#ordersTable1',
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
			"kind" : 1,
			"userId":'${user.id}'
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
				field: 'itemName',
				width: '50%',
				align: 'center',
				title: '施工项目'
			}, {
				field: 'quantity',
				width: '20%',
				align: 'center',
				title: '施工次数'
			}, {
				field: 'amount',
				width: '20%',
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
	
	
	 url = "royalty/getUserRoyalDetail.do";
	 tableIns = table.render({
		elem: '#ordersTable2',
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
			"kind" : 2,
			"userId":'${user.id}'
		},
		done: function(res, curr, count) {
		
		},
		cols: [
				[{
					field : 'itemId',
					hide : 'true',
					title : 'ID',
					align : 'center'
				}, {
					field: 'itemName',
					width: '50%',
					align: 'center',
					title: '服务项目'
				}, {
					field: 'quantity',
					width: '20%',
					align: 'center',
					title: '出售次数'
				}, {
					field: 'amount',
					width: '20%',
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
	
	 url = "royalty/getUserRoyalDetail.do";
	 tableIns = table.render({
		elem: '#ordersTable3',
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
			"kind" : 3,
			"userId":'${user.id}'
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
				field: 'itemName',
				width: '40%',
				align: 'center',
				title: '产品'
			},{
				field: 'productType',
				width: '30%',
				align: 'center',
				title: '型号'
			}, {
				field: 'quantity',
				width: '10%',
				align: 'center',
				title: '出售个数'
			},{
				field: 'amount',
				width: '10%',
				align: 'center',
				title: '提成金额'
			},{
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
		reload: function(tabIdx) {// 执行重载
			var tableId = '';
			if( tabIdx == 0 ){
				kind = 1;
				tableId = 'ordersTable1'; 
			}else if ( tabIdx == 1){
				kind = 2;
				tableId = 'ordersTable2'; 
			}else if ( tabIdx == 2){
				kind = 3;
				tableId = 'ordersTable3'; 
			}
			table.reload(tableId, {
				method: 'post',
				page: {
					curr: 1
				},
				where: {
					"dateRangeStartTime": getStartTime(),
					"dateRangeEndTime": getEndTime(),
					"kind" : kind,
					"userId":'${user.id}'
				}
			});
			
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this,tabIdx) : '';
	});

});

function userDefinedToolHandle($arg, obj) {
	var data = obj.data
	var $ = layui.jquery;
	if (obj.event === 'detail') { //修改订单
		showDetail(data);
	}
}



var orderFrmId = 'royalList';
function showDetail(data){
	var  param =  '?itemId='+data.itemId+'&userId=${user.id}&kind='+kind+'&dateRangeStartTime='+getStartTime()+'&dateRangeEndTime='+getEndTime();
	layui.use('element', function(){
			var element = parent.layui.element;
			var id = orderFrmId;
			var url = 'royalty/orderRoyalList.do'+param;
			element.tabDelete('tab', id);
			element.tabAdd('tab', {
				title: "订单提成详情",
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



</script>

