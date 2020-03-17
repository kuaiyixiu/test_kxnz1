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
<script type="text/javascript" src="js/sysmanage/remind/addRemind.js"></script>
</head>
<body>
	<form class="layui-form" action="" id="addRemindform"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">提醒名</label>
				<div class="layui-input-block">
					<input type="text" name="remindName" lay-verify="required"
						placeholder="请输入提醒名" autocomplete="off" class="layui-input"
						value="">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">提醒时间</label>
				<div class="layui-input-block">
					<input type="text" name="remindDate" id="date" lay-verify="required" lay-verify="date" 
						placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">执行员工</label>
				<div class="layui-input-block" id="userDiv">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">提醒内容</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入提醒内容" name="remindContent" class="layui-textarea" 
						style="width: 490px;"></textarea>
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">备注内容</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入备注内容" name="remark" class="layui-textarea" 
						style="width: 490px;"></textarea>
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div align="center">
				<button class="layui-btn layui-btn-normal" lay-submit lay-filter="formSubmitBtn">提交</button>
				<button class="layui-btn layui-btn-normal closeBtn" id="closeBtn" type="button">关闭</button>
			</div>
		</div>
	</form>
</body>
</html>
<input type="hidden" id="userList" value='${userList}'>

<script id="usersTpl" type="text/html">
    <select name="remindUserId"  lay-search="" lay-verify="required">
		<option value=""></option>
	{{#  layui.each(d, function(index, item){ }}
		<option value="{{ item.id }}">{{ item.userRealname }}</option>
  	{{#  }); }}
	</select>
</script>