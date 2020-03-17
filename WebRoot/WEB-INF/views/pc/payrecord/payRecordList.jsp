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
		.showCar{
	color: #2ea9df;
	text-decoration: underline;
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
				<div class="layui-inline">
					<label class="layui-form-label">来源</label>
					<div class="layui-input-inline">
						<select name="kind" id="kind">
							<option value="">全部</option>
							<option value="1">客户订单支付</option>
							<option value="2">门店入库</option>
							<option value="3">客户购买套餐</option>
							<option value="4">客户挂账还款</option>
							<option value="5">门店挂账还款</option>
							<option value="6">门店退货</option>
							<option value="7">门店退货挂账还款</option>
							<option value="8">订单反入账或反挂账</option>
							<option value="9">入库单作废</option>
							<option value="10">退货单作废</option>
							<option value="11">调拨还款</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">支付方式</label>
					<div class="layui-input-inline">
						<select name="typeId" id="typeId">
							<option value="">全部</option>
							<c:forEach items="${ typeList }" var="type">
								<option value="${type.id }">${type.payName }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
			</fieldset>
		</div>
		</form>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="ordersTable" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            
            </script>
			<script type="text/html" id="barLine">
            	<a class="layui-btn layui-btn-xs" lay-event="detail"  tabId="ordersTable">详情</a>
            </script>
		</div>
	</div>
</body>
</html>

<script type="text/javascript">
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
	var　 url = "payRecord/getList.do";
	//初始化网格
	var tableIns = table.render({
		elem: '#ordersTable',
		url: url,
		page: true,
		method: 'post',
		totalRow:true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		where: {
			"kind":$("#kind").val(),
			"typeId":$("#typeId").val(),
			"dateRangeStartTime": startTime,
			"dateRangeEndTime": endTime
		},
		done: function(res, curr, count) {
		
		},
		cols: [
			[
			 /*{
				type: 'checkbox',
				fixed: 'left',
				width: '5%'
			} ,*/
			{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center'
			}, {
				field: 'addTime',
				width: '15%',
				align: 'center',
				title: '日期',
				totalRowText: '合计',
				templet: function(row) {
					return formatDate(row.addTime);
				}
			}, {
				field: 'kind',
				width: '15%',
				align: 'center',
				title: '类型',
				templet: function(row) {
					return getKind(row);
				}
			}, {
				field: 'typeName',
				width: '10%',
				align: 'center',
				title: '支付方式'
			}, {
				field: 'balance',
				width: '10%',
				align: 'center',
				title: '收支',
				templet: function(row) {
					return getBalance(row);
				}
			}, {
				field: 'amount',
				width: '10%',
				align: 'center',
				totalRow: true,
				title: '金额'
			}
			/*
			, {
				field: 'opt',
				width: '15%',
				align: 'center',
				title: '详情',
				toolbar: '#barLine'
			}
			*/
			]
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
					"kind":$("#kind").val(),
					"typeId":$("#typeId").val(),
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


var orderFrmId = 'orderFrm';
function showDetail(data){
	if (data.kind == 1){ //客户订单支付
		layui.use('element', function(){
			var element = parent.layui.element;
			var id = orderFrmId;
			var url = 'orders/toUpdateOrder/'+data.sourceId+'/4.do';
			element.tabDelete('tab', id);
			element.tabAdd('tab', {
				title: "订单详情",
				content: '<iframe src="' + url + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
				id: id
			});
			element.tabChange('tab', id);
		});		
	}
}

//订单类型  0临牌开单，1非会员开单 2
function getOrderType(row){
	var orderType = row.orderType;
	if (orderType == "0")
		return "临牌开单";
	else if (orderType == "1")
		return "非会员开单";
	else if (orderType == "2")
		return "会员开单";
	return "";
}

//付款来源  1客户订单支付 （收入）2门店入库（支出） 3客户购买套餐（收入） 4客户挂账还款（收入）
//5.门店挂账还款（支出）6门店退货（收入） 7.门店退货挂账还款（收入 供应商还款） 8订单反入账或反挂账（支出）9入库单作废(收入) 10退货单作废(支出)11调拨还款(收入)
function getKind(row){
	var kind = row.kind;
	if (kind == "1"){
		return "<a href='javascript:void(0);' onClick='updateOrders("+"\""+row.sourceId+"\""+",4)' class='showCar'>客户订单支付</a>";
	}
	else if (kind == "2")
		return "门店入库";
	else if(kind == "3")
		return "客户购买套餐";
	else if(kind == "4")
		return "客户挂账还款";
	else if(kind == "5")
		return "门店挂账还款";
	else if(kind == "6")
		return "门店退货";
	else if(kind == "7")
		return "门店退货挂账还款";
	else if(kind == "8")
		return "订单反入账或反挂账";
	else if(kind == "9")
		return "入库单作废";
	else if(kind == "10")
		return "退货单作废";
	else if(kind == "11")
		return "调拨还款";
	return "";
}


function updateOrders(orderId,pageType){
	layui.use('element', function(){
		var element = parent.layui.element;
		var id = orderFrmId;
		var url = 'orders/toUpdateOrder/'+orderId+'/'+pageType+' .do';
		element.tabDelete('tab', id);
		element.tabAdd('tab', {
			title: "查看订单",
			content: '<iframe src="' + url + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
			id: id
		});
		element.tabChange('tab', id);
	});
}

//返回收支情况
//付款来源  1客户订单支付 （收入）2门店入库（支出） 3客户购买套餐（收入） 4客户挂账还款（收入） 5.门店挂账还款（支出）6门店退货（收入）
//7.门店退货挂账还款（收入 供应商还款） 8订单反入账或反挂账（支出）
function getBalance(row){
	var kind = row.kind;
	if (kind == "1" || kind == "3" || kind =="4" || kind == "6"|| kind=="7"|| kind=="9"|| kind=="11")
		return  "<i style='color:red;'>收入</i>";
	else if (kind == "2" ||  kind == "5" || kind == "8"|| kind == "10"){
		return "<i style='color:green;'>支出</i>";
	}
		return "";
}


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

</script>

