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
				url : "product/saveData.do",
				data : data.field,
				dataType : "json",
				success : function(result) {
					if (result.retCode == 'success') {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭当前页  
						parent.layui.table.reload('productInfo');
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
</head>
<body>
	<form class="layui-form" action="" id="form"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<input type="hidden" name="id" id="id" value="${product.id }" />
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label"><font color="red">*</font>产品名称</label>
				<div class="layui-input-block">
					<input type="text" name="productName" lay-verify="required"
						placeholder="请输入产品名称" autocomplete="off" class="layui-input"
						value="${product.productName }" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label"><font color="red">*</font>所属分类</label>
				<div class="layui-input-block">
					<select name="classId" lay-verify="required">
					    <option value="">请选择所属分类</option>
					    <c:forEach items="${classList }" var="productClass">
					    <option value="${productClass.id }" <c:if test="${product.classId==productClass.id }">selected="selected"</c:if>>${productClass.className }</option>
					    </c:forEach>
					</select>
				</div>
			</div>

		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">产品型号</label>
				<div class="layui-input-block">
					<input type="text" name="productType"
						placeholder="请输入产品型号" autocomplete="off" class="layui-input"
						value="${product.productType }" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">试用车型</label>
				<div class="layui-input-block">
					<input type="text" name="carType"
						placeholder="请输入试用车型" autocomplete="off" class="layui-input"
						value="${product.carType }" />
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">出售价格</label>
				<div class="layui-input-block">
					<input type="number" name="price" 
						placeholder="请输入出售价格" autocomplete="off" class="layui-input"
						value="${product.price }" onblur="initNum(this,2)" />
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">报警数量</label>
				<div class="layui-input-block">
					<input type="number" name="alarmQuantity"
						placeholder="请输入报警数量" autocomplete="off" class="layui-input"
						value="${product.alarmQuantity }" onblur="initNum(this,0)" />
				</div>
			</div>
		</div>
		<c:if test="${not empty product.id }">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">库存数量</label>
				<div class="layui-input-block">
					<input type="number" name="quantity"  autocomplete="off" class="layui-input layui-disabled" value="${product.quantity }" disabled="disabled"/>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">进货价格</label>
				<div class="layui-input-block">
					<input type="number" name="amount" autocomplete="off" class="layui-input layui-disabled" value="${product.amount }" disabled="disabled"/>
				</div>
			</div>
		</div>
		</c:if>
		<div class="layui-form-item">
				<label class="layui-form-label">条形码</label>
				<div class="layui-input-block">
					<input type="text" name="qrCode"
						placeholder="请输入条形码" autocomplete="off" class="layui-input"
						value="${product.qrCode }" />
				</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<textarea name="remark" class="layui-textarea">${product.remark }</textarea>
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

