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
		<link href="lib/mui/css/mui.min.css" rel="stylesheet" />
		<link href="lib/mui/css/style.css" rel="stylesheet" />
		<script src="lib/mui/js/mui.min.js"></script>
		<script src="lib/jquery/jquery-3.4.1.js"></script>
		<script src="lib/jquery/jsrender.js"></script>
		<link rel="stylesheet" type="text/css" href="css/wechatKxnz.css" />
	</head>

<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">套餐详情</h1>
		</header>
		<div class="mui-content" >
			<ul class="mui-table-view" id="mealDiv">
			</ul>
		</div>
	</body>
</html>
<script type="text/x-jsrender" id="mealTpl">
{{for #data}}
	<li class="mui-table-view-cell mui-media">
		<a href="javascript:;" class="">
			<div class="mui-media-body">
				可用套餐
				<p class="mui-ellipsis"><span class="carTitle">套餐名：{{:name}}</p>
				<p class="mui-ellipsis"><span class="carTitle">有效时间： {{:createDateStr}} ~  {{:endDateStr}}</p>
			</div>
		</a>
		<a href="javascript:;" class="">
			<div class="mui-media-body">
				内容
			{{if serveMealDt.length > 0}}
				<p class="mui-ellipsis"><span class="carTitle">服务</p>
				{{for serveMealDt}}	
					<p class="mui-ellipsis"><span class="carTitle">{{:mealDtName}}x{{:quantity}}</p>
				{{/for}}
			{{/if}}

			{{if productMealDts.length > 0}}
				<p class="mui-ellipsis"><span class="carTitle">产品</p>
				{{for productMealDts}}
					<p class="mui-ellipsis"><span class="carTitle">{{:mealDtName}}x{{:quantity}}</p>
				{{/for}}
			{{/if}}
			</div>
		</a>
	</li>
{{/for}}
</script>

<script type="text/javascript">
	var meals = ${meals};
	if(meals.length > 0){
		var html = $("#mealTpl").render(meals);
		$("#mealDiv").append(html);
	}
</script>