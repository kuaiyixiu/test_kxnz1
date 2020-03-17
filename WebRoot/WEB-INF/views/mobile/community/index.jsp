<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	<meta name="viewport"
		content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<title>连途社区</title>
	<link href="lib/mui/css/mui.min.css" rel="stylesheet" />
	<link href="lib/mui/css/mui-icons-extra.css" rel="stylesheet" />
	<link href="lib/mui/css/app.css" rel="stylesheet" />
	<script src="lib/mui/js/mui.min.js"></script>
	<script src="lib/jquery/jquery-3.4.1.js"></script>
	<style>
		html, body{
			height: 100%;
			width: 100%;
		}
		.mui-control-content{
			height: 100%;
		}

		.mui-bar-tab ~ .mui-content {
			padding-bottom: 50px !important;
		}
		.mui-control-content{
			overflow-y: auto;
			overflow-x: hidden;
		}
	</style>
</head>
<body>
	<%--<header class="mui-bar mui-bar-nav">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 class="mui-title">首页</h1>
	</header>--%>
	<nav class="mui-bar mui-bar-tab">
		<a class="mui-tab-item mui-active" href="#consultation" data-id="consultation">
			<span class="mui-icon mui-icon-home"></span>
			<span class="mui-tab-label" data-title="连途社区">首页</span>
		</a>
		<a class="mui-tab-item" href="#vehicle" data-id="vehicle">
			<span class="mui-icon mui-icon-extra mui-icon-extra-xiaoshuo"></span>
			<span class="mui-tab-label" data-title="车型查找">车型</span>
		</a>
		<a class="mui-tab-item" href="#mine" data-id="mine">
			<span class="mui-icon mui-icon-contact"></span>
			<span class="mui-tab-label" data-title="个人中心">我的</span>
		</a>
	</nav>
	<div id="mui-content" class="mui-content">
		<div id="consultation" class="mui-control-content mui-active"></div>
		<div id="vehicle" class="mui-control-content"></div>
		<div id="mine" class="mui-control-content"></div>
	</div>

</body>
<script>
	mui.init();
	$("#mui-content").css("height",$("body").height()+ "px");

	var activeTab = document.querySelector('.mui-tab-item.mui-active').getAttribute('data-id');
	$("#"+activeTab).load("wechat/community/page/"+activeTab+".do");

	// 点击切换，动态创建其它webview；
	mui('.mui-bar-tab').on('tap', 'a.mui-tab-item', function(e) {
		var _self = this;
		var targetTab = _self.getAttribute('data-id');
		if(targetTab === activeTab) {
			return;
		}
		$("head title").html($(this).find(".mui-tab-label").attr("data-title"));
		if($("#"+targetTab).children().length == 0){
			$("#"+targetTab).load("wechat/community/page/"+targetTab+".do");
		}
		activeTab = targetTab;
	});


</script>
</html>


