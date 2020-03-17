<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<link rel="stylesheet" type="text/css" href="css/fieldset.css" />
<link rel="stylesheet" type="text/css" href="lib/admin/css/admin.css" />
<script src="lib/admin/layui/layui.js" type="text/javascript"
	charset="utf-8"></script>
<script src="lib/admin/js/common.js" type="text/javascript"
	charset="utf-8"></script>
<script type="text/javascript" src="js/sysmanage/userinfo.js"></script>
</head>
<body>
	<div class="page-content-wrap">
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<div class="layui-inline">
						<label class="layui-form-label">用户名</label>
						<div class="layui-input-inline">
							<input type="text" name="userName" id="userName"
								placeholder="请输入用户名" autocomplete="off"
								class="layui-input">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">员工姓名</label>
						<div class="layui-input-inline">
							<input type="text" name="userRealname" id="userRealname" 
								placeholder="请输入姓名" autocomplete="off"
								class="layui-input">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">员工状态</label>
						<div class="layui-input-inline">
							<select name="status" id="status">
								<option value="">全部</option>
								<option value="1">在职</option>
								<option value="0">离职</option>
							</select>
						</div>
					</div>
					<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
				</fieldset>

			</div>
		</form>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="userInfo" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm addBtn" tabId="userInfo" data-url="user/addUser.do" openw="800px;" openh="600px" lay-event="addData"><i class="layui-icon">&#xe608;</i>新增</button>
<%--            <button class="layui-btn layui-btn-sm delBtn" tabId="userInfo" data-url="user/delData.do" lay-event="delData"><i class="layui-icon">&#xe640;</i>删除</button>--%>
            </div>
            </script>
			<script type="text/html" id="barLine1">
            <a class="layui-btn layui-btn-xs" lay-event="edit" data-url="user/editData.do" tabId="userInfo" openw="700px;" openh="400px">修改</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-url="user/delData.do" tabId="userInfo">删除</a>
            </script>
			<script type="text/html" id="barLine2">
            <a class="layui-btn layui-btn-xs" lay-event="edit" data-url="user/editData.do" tabId="userInfo" openw="700px;" openh="400px">修改</a>
            </script>
		</div>
	</div>
</body>
</html>

