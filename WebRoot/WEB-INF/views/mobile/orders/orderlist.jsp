<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html class="ui-page-login">
<base href="<%=basePath%>">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title></title>
		<link href="lib/mui/css/mui.min.css" rel="stylesheet" />
		<link href="lib/mui/css/style.css" rel="stylesheet" />
		<script src="lib/mui/js/mui.min.js"></script>
		<script src="lib/jquery/jquery-3.4.1.js"></script>
		<script src="lib/mui/js/template-web.js"></script>
		<style>
			.mui-table-view-cell dl {margin: 0px;}
			.mui-table-view-cell dt {font-size: 15px;font-weight: bold;}
			.mui-table-view-cell dd {margin-left: 0px;font-size: 15px;}
			dt{color:#049cf2;}
			.yellow{color: #f5762c;}
		</style>

	</head>

	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<c:choose>
				<c:when test="${orderStatus eq '3'}"><h1 class="mui-title">施工中的订单</h1></c:when>
				<c:when test="${orderStatus eq '456' and pageType eq '1'}"><h1 class="mui-title">历史订单</h1></c:when>
				<c:when test="${orderStatus eq '456' and pageType eq '2'}"><h1 class="mui-title">消费记录</h1></c:when>
			</c:choose>
		</header>
			<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
			<div class="mui-scroll">
				<!--数据列表-->
				<ul class="mui-table-view">
				
				</ul>
			</div>
			</div>
		
	
	</body>

</html>
<script>
var page = 0;  //开始笔数 
var limit = 10; //每次页数
mui.init({
	  pullRefresh : {
	    container:"#pullrefresh",//待刷新区域标识，querySelector能定位的css选择器均可，比如：id、.class等
	    up : {
	      height:50,//可选.默认50.触发上拉加载拖动距离
	      auto:true,//可选,默认false.自动上拉加载一次
	      contentrefresh : "正在加载...",//可选，正在加载状态时，上拉加载控件上显示的标题内容
	      contentnomore:'没有更多数据了',//可选，请求完毕若没有更多数据时显示的提醒内容；
	      callback :function(){ //必选，刷新函数，根据具体业务来编写，比如通过ajax从服务器获取新数据；
	    	  var ul = $('.mui-table-view');
	    	  var flag = pullupRefresh(ul);
	    	  mui('#pullrefresh').pullRefresh().endPullupToRefresh(flag);
	      } 
	    }
	 }
});


var pullupRefresh = function(ul) {
	var flag = true;
	var param = {
		orderStatus : '${orderStatus}',
		pageType : '${pageType}',
		page : page,
		limit : limit
	};
	$.ajax({
		type : 'post',
		url : "wechat/orders/getList.do",
		data : param,
		dataType : 'json',
		async:false,
		success : function(res) {
			if (res.data.length > 0) {
				page += limit;
				var html = template('orderTpl', res);
				ul.append(html);
				mui('body').on('tap','.evaluate',function(e){
					var orderId = $(e.target).attr("orderId");
					 mui.openWindow({
						  url: 'wechat/orders/evaluateOrder/'+orderId+'.do' 
					});
				})
				flag = res.data.length < limit;
			}
		},
		error : function() {
			return;
		}
	});
	return flag;
}


</script>


<script id="orderTpl" type="text/html">
    {{each data as order i}}
		<li class="mui-table-view-cell">
			<dl>
			<c:if test="${orderStatus eq '456'}">
					{{if order.evaluateTime == undefined}}
						<button type="button" class="mui-btn mui-btn-primary evaluate" orderId="{{order.id}}"  style="position: absolute;right: 10px;">评价订单</button>
					{{/if}}
			</c:if>
				<dt>订单</dt>
				<dd><div class="mui-row"><span class="mui-col-sm-4 mui-col-xs-4">订单编号：</span><span class="yellow mui-col-sm-8 mui-col-xs-8">{{order.orderNo}}</span></div></dd>
				<dd><div class="mui-row"><span class="mui-col-sm-4 mui-col-xs-4">订单状态：</span><span class=" mui-col-sm-8 mui-col-xs-8">{{order.orderStatusName}}</span></div></dd>
				<dd><div class="mui-row"><span class="mui-col-sm-4 mui-col-xs-4">创建时间：</span><span class=" mui-col-sm-8 mui-col-xs-8">{{order.creatTime}}</span></div></dd>
				<dd><div class="mui-row"><span class="mui-col-sm-4 mui-col-xs-4">施工车辆：</span><span class="yellow mui-col-sm-8 mui-col-xs-8">{{order.carNumber}}</span></div></dd>
			</dl>
			{{if order.ordersServeList.length >0}}
			<dl>
				<dt>服务</dt>
				 {{each order.ordersServeList as serve j}}
					<dd><div class="mui-row"><span class="mui-col-sm-4 mui-col-xs-4">{{serve.serveName}}</span>  <span class="yellow mui-col-sm-8 mui-col-xs-8">{{serve.price}}元</span></div></dd>
    			 {{/each}}
			</dl>
			{{/if}}
			{{if order.ordersProductList.length >0}}
			<dl>
				<dt>产品</dt>
				 {{each order.ordersProductList as product k}}
					<dd><div class="mui-row"><span class="mui-col-sm-4 mui-col-xs-4">{{product.productName}}</span> <span class="yellow mui-col-sm-4 mui-col-xs-4">x {{product.quantity}} </span>   <span class="yellow mui-col-sm-4 mui-col-xs-4">{{product.quantity * product.price}}元</span></div></dd>
    			 {{/each}}
			</dl>
			{{/if}}
			{{if order.ordersPayList.length >0}}
			<dl>
				<dt>支付</dt>
				 {{each order.ordersPayList as pay l}}
					<dd><div class="mui-row"><span class="mui-col-sm-4 mui-col-xs-4">{{pay.payName}}</span>  <span class="yellow mui-col-sm-6 mui-col-xs-6"> {{pay.addTime}} </span>  <span class="yellow mui-col-sm-2 mui-col-xs-2"> {{pay.payAmount}}元</span></div></dd>
    			 {{/each}}
			</dl>
			{{/if}}
		</li>
    {{/each}}
</script>
