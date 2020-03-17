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
<script type="text/javascript" src="js/sysmanage/roleinfo.js"></script>
</head>
<body>
	<div class="page-content-wrap">
		<div class="layui-form-item searchDiv">
			<fieldset>
				<legend>查询条件</legend>
				<label class="layui-form-label">角色名称</label>
				<div class="layui-inline">
					<input type="text" name="name" id="name" required
						lay-verify="required" placeholder="请输入名称" autocomplete="off"
						class="layui-input">
				</div>
				<button class="layui-btn layui-btn-normal" data-type="reload">搜索</button>
			</fieldset>
		</div>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="roleInfo" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm addBtn" tabId="roleInfo" data-url="role/addRole.do" openw="700px;" openh="400px" lay-event="addData"><i class="layui-icon">&#xe608;</i>新增</button>
<%--            <button class="layui-btn layui-btn-sm delBtn" tabId="roleInfo" data-url="role/delData.do" lay-event="delData"><i class="layui-icon">&#xe640;</i>删除</button>--%>
            </div>
            </script>
			<script type="text/html" id="barLine">
            <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="setPermission" data-url="role/setPermission.do" openw="500px;" openh="400px">授权</a>
            <a class="layui-btn layui-btn-xs" lay-event="edit" data-url="role/editRole.do" tabId="roleInfo" openw="700px;" openh="400px">修改</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-url="role/delData.do" tabId="roleInfo">删除</a>
            </script>
		</div>
	</div>
</body>
</html>

