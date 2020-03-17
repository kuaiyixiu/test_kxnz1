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
			.mui-table-view-cell dd {margin-left: 5px;font-size: 15px;}
		</style>

	</head>

	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<button type="button" class="mui-btn mui-btn-primary"  id="serviceappointment" style="float: right;">预约</button>
			<h1 class="mui-title">预约列表</h1>
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
var limit = 5; //每次页数
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
		page : page,
		limit : limit
	};
	$.ajax({
		type : 'post',
		url : "wechat/appoint/getList.do",
		data : param,
		dataType : 'json',
		async:false,
		success : function(res) {
			if (res.data.length > 0) {
				page += limit;
				var html = template('orderTpl', res);
				ul.append(html);
				flag = res.data.length < limit;
			}
		},
		error : function() {
			return;
		}
	});
	return flag;
}


mui('body').on('tap','#serviceappointment',function(){
	 mui.openWindow({
		  url: 'wechat/appoint/serviceappointment.do' 
		  });
})



</script>


<script id="orderTpl" type="text/html">
    {{each data as appoint i}}
		<li class="mui-table-view-cell">
			<dl>
				<dd>手机号：{{appoint.cellphone}}</dd>
				<dd>车牌号：{{appoint.carNumber}}</dd>
				<dd>预约项目：{{appoint.itemName}}</dd>
				<dd>预约时间：{{appoint.appointTime}}</dd>
				<dd>预约备注：{{appoint.remark}}</dd>
			</dl>
		</li>
    {{/each}}
</script>
