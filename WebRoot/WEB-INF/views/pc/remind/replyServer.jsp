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
	<form class="layui-form" action="" id=""
		style="width: 95%;height: 98%;margin-top: 1%;">
		<div class="layui-form-item">
				<label class="layui-form-label">回复标题</label>
				<div class="layui-input-block">
					<input type="text" name="first" lay-verify="required"
						autocomplete="off" class="layui-input"
						value="尊敬的客户您好，您的服务周期已成功。">
				</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">车牌号码</label>
				<div class="layui-input-block">
					<input type="text" name="keyword1" autocomplete="off" class="layui-input layui-disabled"
						 value="${ invitation.carNumber }">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">车主电话</label>
				<div class="layui-input-block">
					<input type="text" name="keyword2" autocomplete="off" class="layui-input layui-disabled"
						 value="${ invitation.customPhone }">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">操作时间</label>
				<div class="layui-input-block">
					<input type="text" name="keyword3" autocomplete="off" class="layui-input layui-disabled"
						 value="${ invitation.doneTimeStr }">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">预约时间</label>
				<div class="layui-input-block">
					<input type="text" name="keyword4" id="date" lay-verify="date" 
						 autocomplete="off" class="layui-input" placeholder="yyyy-MM-dd">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">预约项目</label>
				<div class="layui-input-block">
					<input type="text" name="keyword5"  lay-verify="required"
						 autocomplete="off" class="layui-input"
						 value="${ invitation.serverName }">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">额外备注</label>
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
		
		<input type="hidden" name="cardNo" value="${ invitation.cardNo }">
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
			elem: "#date",
			trigger: 'click'
		});
		
		form.render();
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			$.ajax({
				type : "POST",
				url : "send/server.do",
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