<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<link rel="stylesheet" type="text/css"
	href="lib/admin/layui/css/layui.css" />
<link rel="stylesheet" type="text/css" href="lib/admin/css/admin.css" />
<link rel="stylesheet" type="text/css" href="lib/admin/fonts/iconfont.css" />
<link rel="shortcut icon" href="image/favicon.ico" type="image/x-icon">
<script src="lib/admin/layui/layui.js" type="text/javascript" charset="utf-8"></script>
<script src="lib/admin/js/main.js" type="text/javascript" charset="utf-8"></script>
<script src="js/index.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<div class="main-layout" id='main-layout'>
		<!--左侧菜单-->
		<div class="main-layout-side">
			<div class="m-logo"></div>
			<ul class="layui-nav layui-nav-tree" lay-filter="leftNav" id="memus">
			</ul>
		</div>
		<!--右侧内容-->
		<div class="main-layout-container">
			<!--头部-->
			<div class="main-layout-header" style="background-color: #333742">
				<div class="menu-btn" id="hideBtn">
					<a href="javascript:;" style="color: #c7bfbf"> <span
						class="layui-icon layui-icon-shrink-right"></span> </a>
				</div>
				<ul class="layui-nav right-nav" lay-filter="rightNav">
				    <c:if test="${user.roleId==1 or user.roleId==2}">
				    <c:if test="${fn:length(shopList) > 1}">
					<li class="layui-nav-item"><a href="javascript:;">${shops.shopName }&nbsp;&nbsp;</a>
						<dl class="layui-nav-child">
						   <c:forEach items="${shopList }" var="shop">
						    <c:if test="${shops.id != shop.id }">
							<dd><a href="javascript:;" onclick="changeShop(${shop.id})">${shop.shopName }</a></dd>
							</c:if>
							</c:forEach>
						</dl>
					</li>
					</c:if>
					<c:if test="${fn:length(shopList) <2}">
					 <li class="layui-nav-item"><a href="javascript:;">${shops.shopName }&nbsp;&nbsp;</a></li>
					</c:if>
					</c:if>
					 <c:if test="${user.roleId!=1 and user.roleId!=2}">
					 <li class="layui-nav-item"><a href="javascript:;">${shops.shopName }&nbsp;&nbsp;</a></li>
					 </c:if>
					<li class="layui-nav-item" style="margin-left: 19px;position: relative;"><a href="javascript:;" data-url="" id="${user.id }" data-id='5' data-text="个人信息" onclick="resetPwd(this)">${user.userRealname }</a></li>
					<li class="layui-nav-item" style="margin-left: -5px;position: relative;margin-right: 20px;"><a href="logout">退出</a>
					</li>
				</ul>
			</div>
			<!--主体内容-->
			<div class="main-layout-body">
				<!--tab 切换-->
				<div class="layui-tab layui-tab-brief main-layout-tab"
					lay-filter="tab" lay-allowClose="true">
					<ul class="layui-tab-title">
						<li class="layui-this welcome">后台主页</li>
					</ul>
					<div class="layui-tab-content">
						<div class="layui-tab-item layui-show"
							style="background: #f5f5f5;">
							<iframe src="welcome.do" width="100%" height="100%" name="iframe"
								scrolling="auto" class="iframe" framborder="0"></iframe>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--遮罩-->
		<div class="main-mask"></div>
	</div>
    <input type="hidden" id="userRoleLevel" value="${user.roleId }" />
</body>
</html>

