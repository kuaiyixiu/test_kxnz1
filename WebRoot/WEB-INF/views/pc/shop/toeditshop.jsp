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
<title>快易修系统</title>
<%@ include file="../base.jsp"%>
<script type="text/javascript">
	function userDefinedLoadForm() {
		var form = layui.form;
		var $ = layui.jquery;
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			$.ajax({
				type : "POST",
				url : "shop/saveEditShop.do",
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
		style="width: 95%;height: 98%;margin-top: 1%;">
		<input type="hidden" name="role" value="${role}">
		<div class="layui-form-item">
			<label class="layui-form-label"><font color="red">*</font>
				门店名称</label>
			<div class="layui-input-block">
				<input type="text" name="shopName" lay-verify="required"
					placeholder="请输入门店名称" autocomplete="off" class="layui-input"
					value="${shops.shopName }">
			</div>
		</div>
		<div class="layui-form-item layui-form-text">
			<label class="layui-form-label"><font color="red">*</font>
				门店地址</label>
			<div class="layui-input-block">
				<textarea name="shopAddress" placeholder="请输入门店详细地址"
					class="layui-textarea">${shops.shopAddress }</textarea>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">固定电话</label>
			<div class="layui-input-block">
				<input type="text" name="shopTel" placeholder="请输入固定电话"
					autocomplete="off" class="layui-input" value="${shops.shopTel }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">联系电话</label>
			<div class="layui-input-block">
				<input type="text" name="shopPhone" placeholder="请输入联系电话"
					autocomplete="off" class="layui-input" value="${shops.shopPhone }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">发票抬头</label>
			<div class="layui-input-block">
				<input type="text" name="faTitle" placeholder="请输入发票抬头"
					autocomplete="off" class="layui-input" value="${shops.faTitle }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">传真</label>
			<div class="layui-input-block">
				<input type="text" name="fax" placeholder="请输入传真" autocomplete="off"
					class="layui-input" value="${shops.fax }">
			</div>
		</div>
		<div <c:if test="${'admin' ne role}">style="display: none;"</c:if>>
		<div class="layui-form-item">
			<label class="layui-form-label">公众号名称</label>
			<div class="layui-input-block">
				<input type="text" name="wechatName" lay-verify="required" placeholder="请输入公众号名称"
					autocomplete="off" class="layui-input"
					value="${config.wechatName }">
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
				<input type="text" name="appsecret"  lay-verify="required" placeholder="请输入公众号开发者密码"
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
	</div>
		<div class="layui-form-item">
			<div align="center">
				<button class="layui-btn kxnz-btn-default" lay-submit
					lay-filter="formSubmitBtn" id="formSubmitBtn">提交</button>
				<button class="layui-btn closeBtn kxnz-btn-default" id="closeBtn"
					type="button">关闭</button>
			</div>
		</div>
	</form>

</body>
</html>

