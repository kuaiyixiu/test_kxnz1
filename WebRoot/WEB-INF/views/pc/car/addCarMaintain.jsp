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
</head>
<body>
	<form action="" class="layui-form" id="addCarForm"
		style="width: 100%;height: 98%;margin-top: 1%;">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">当前保养时间</label>
				<div class="layui-input-block">
					<input type="text" name="createTime" data-id="dateInput" id="compulsoryDateStr" lay-verify="date"
						placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input"
						disabled="disabled">
				</div>
			</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">下次保养时间</label>
				<div class="layui-input-block">
					<input type="text" name="remindTime" id="remindTime" data-id="dateInput" id="compulsoryDateStr" lay-verify="date"
						placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input" value="${ nextAddTime }">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">进站里程</label>
				<div class="layui-input-block">
				<input type=number name="progressMileage" lay-verify="required" 
					 placeholder="请输入进站里程" autocomplete="off" class="layui-input">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
		
			<div class="layui-inline">
				<label class="layui-form-label">提醒里程</label>
				<div class="layui-input-block">
				<input type=number name="remindMileage" 
					 placeholder="请输入提醒里程" autocomplete="off" class="layui-input">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
				<label class="layui-form-label">备注</label>
			    <div class="layui-input-block">
			      <textarea placeholder="请输入内容" name="remark" class="layui-textarea" style="width: 300px;"></textarea>
			    </div>
		</div>

		<div class="layui-form-item">
			<div align="left" style="margin-left: 180px;">
				<a class="layui-btn layui-btn-normal" lay-submit lay-filter="formSubmitBtn">提交</a>
				<a class="layui-btn closeBtn layui-btn-normal">关闭</a>
			</div>
		</div>
	<input type="hidden" name="carId" value="${ carId }"> 
	</form>
</body>
</html>
<script type="text/javascript">
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		var laydate = layui.laydate;
		
		laydate.render({
			elem: "#remindTime",
			trigger: 'click'
		});
		
		var nowDate = formatDate1(new Date());
		$("[name='createTime']").val(nowDate);
		
		form.render();
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			$.ajax({
				type : "POST",
				url : "car/addCarMaintain.do",
				data : data.field,
				dataType : "json",
				success : function(result) {
					if (result.retCode == 'success') {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭当前页  
						parent.layer.msg(result.retMsg, {
							icon : 1
						});
					} else {
						layer.msg(result.retMsg, {
							icon : 2
						});
					}
				}
			});
			return false;
		});

	});
</script>