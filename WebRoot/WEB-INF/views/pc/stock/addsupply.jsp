<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<script src="lib/admin/layui/layui.js" type="text/javascript"
	charset="utf-8"></script>
<script src="lib/admin/js/common.js" type="text/javascript"
	charset="utf-8"></script>
<script type="text/javascript">
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		form.render();
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			$.ajax({
				type : "POST",
				url : "supply/saveData.do",
				data : data.field,
				dataType : "json",
				success : function(result) {
					if (result.retCode == 'success') {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭当前页  
						parent.layui.table.reload('supplyInfo');
						parent.layer.msg(result.retMsg, {icon : 1});
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
</head>
<body>
	<form class="layui-form" action="" id="form"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<input type="hidden" name="id" id="id" value="${supply.id }" />
		<input type="hidden" name="shopId" id="shopId" value="${shopId }" />
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><font color="red">*</font>供应商</label>
				<div class="layui-input-block">
					<input type="text" name="supplyName" lay-verify="required"
						placeholder="请输入供应商" autocomplete="off" class="layui-input"
						value="${supply.supplyName }" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">联系人</label>
				<div class="layui-input-block">
					<input type="text" name="contactName" placeholder="请输入联系人"
						autocomplete="off" class="layui-input"
						value="${supply.contactName }" />
				</div>
			</div>

		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">手机号</label>
				<div class="layui-input-block">
					<input type="text" name="cellphone" placeholder="请输入手机号"
						autocomplete="off" class="layui-input" value="${supply.cellphone }" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">座机号</label>
				<div class="layui-input-block">
					<input type="text" name="telephone" placeholder="请输入座机号"
						autocomplete="off" class="layui-input" value="${supply.telephone }" />
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">状态</label>
				<div class="layui-input-block">
				<input type="checkbox" name="enabled" lay-skin="switch" <c:if test="${supply.enabled==1 }">checked="checked"</c:if> value="1" lay-text="启用|禁用" />
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">地址</label>
			<div class="layui-input-block">
				<input type="text" name="address" placeholder="请输入地址"
					autocomplete="off" class="layui-input" value="${supply.address }" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<textarea name="remark" class="layui-textarea">${supply.remark }</textarea>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block" align="center">
				<button class="layui-btn" lay-submit lay-filter="formSubmitBtn">提交</button>
				<button class="layui-btn closeBtn" id="closeBtn" type="button">关闭</button>
			</div>
		</div>
	</form>
</body>
</html>

