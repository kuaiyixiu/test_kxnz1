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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>连途</title>
<%@ include file="../base.jsp"%>
<script type="text/javascript">
	function userDefinedLoadForm() {
		var form = layui.form;
		var $ = layui.jquery;
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			$.ajax({
				type : "POST",
				url : "shop/editWechat.do",
				data : data.field,
				dataType : "json",
				beforeSend : function() { //触发ajax请求开始时执行 
					$("#formSubmitBtn").attr("disabled", "true"); //改变提交按钮上的文字并将按钮设置为不可点击 
				},
				async : false,
				success : function(result) {
					if (result.retCode == 'success') {
						layer.msg(result.retMsg, {
							icon : 1
						}
						//,function(){top.window.location.reload();}
						);
					} else {
						layer.msg(result.retMsg, {
							icon : 2
						});
					}
					$("#formSubmitBtn").removeAttr("disabled");
				}
			});
			return false;
		});
	}
</script>
</head>
<body>
	<form class="layui-form" action="" id="editMenuform"
		style="width: 50%;height: 98%;margin-top: 1%;">
		<input type="hidden" name="id" value="${config.id}">
		<div class="layui-form-item">
			<label class="layui-form-label">公众号名称</label>
			<div class="layui-input-block">
				<input type="text" name="wechatName" placeholder="请输入公众号名称"
					autocomplete="off" class="layui-input"
					value="${config.wechatName }" lay-verify="required">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">令牌(Token)</label>
			<div class="layui-input-block">
				<input type="text" name="token" lay-verify="required" placeholder="请输入公众号令牌"
					autocomplete="off" class="layui-input" value="${config.token }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">开发者ID</label>
			<div class="layui-input-block">
				<input type="text" name="appid" lay-verify="required" placeholder="请输入公众号开发者ID"
					autocomplete="off" class="layui-input" value="${config.appid }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">开发者密码</label>
			<div class="layui-input-block">
				<input type="text" name="appsecret" lay-verify="required" placeholder="请输入公众号开发者密码"
					autocomplete="off" class="layui-input" value="${config.appsecret }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">消息密钥</label>
			<div class="layui-input-block">
				<input type="text" name="encodingaeskey" lay-verify="required" placeholder="请输入公众号消息密钥"
					autocomplete="off" class="layui-input"
					value="${config.encodingaeskey }">
			</div>
		</div>
		<div class="layui-form-item">
			<div align="center">
				<button class="layui-btn kxnz-btn-default" lay-submit
					lay-filter="formSubmitBtn" id="formSubmitBtn">提交</button>
				
			</div>
		</div>
	</form>

</body>
</html>

