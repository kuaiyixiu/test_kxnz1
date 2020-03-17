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
<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> -->
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
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
<script type="text/javascript" src="js/sysmanage/menu/menu.js"></script>
</head>
<body>
	<div class="page-content-wrap">
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<div class="layui-inline">
						<label class="layui-form-label">菜单名</label>
						<div class="layui-input-inline">
							<input type="text" name="menuName" id="menuName"
								placeholder="请输入菜单名" autocomplete="off"
								class="layui-input">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">备注</label>
						<div class="layui-input-inline">
							<input type="text" name="description" id="description" 
								placeholder="请输入备注" autocomplete="off"
								class="layui-input">
						</div>
					</div>
					<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
				</fieldset>
			</div>
		</form>
		
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="menuInfo" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm addBtn" tabId="menuInfo" data-url="menu/addMenuView.do" openw="800px;" openh="600px" lay-event="addData"><i class="layui-icon">&#xe608;</i>新增</button>
            <button class="layui-btn layui-btn-sm delBtn" tabId="menuInfo" data-url="menu/delData.do" lay-event="delData"><i class="layui-icon">&#xe640;</i>删除</button>
            <button class="layui-btn layui-btn-sm " tabId="menuInfo" lay-event="back"><i class="layui-icon">&#xe640;</i>后退</button>
            </div>
            </script>
			<script type="text/html" id="barLine">
            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="childrenDetail" tabId="menuInfo" openw="700px;" openh="400px">展开</a>
            <a class="layui-btn layui-btn-xs" lay-event="edit" data-url="menu/menuDetailView.do" tabId="menuInfo" openw="700px;" openh="400px">修改</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-url="menu/delData.do" tabId="menuInfo">删除</a>
            </script>
		</div>
	</div>
	
	
	 <input type="hidden" id="parentId" value=""/>
	 <input type="hidden" id="nowLevel" value=""/>
</body>
</html>

