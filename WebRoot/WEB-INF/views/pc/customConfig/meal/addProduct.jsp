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
<%@ include file="../../base.jsp"%>
<script type="text/javascript">
	layui.use([ 'jquery', 'form', 'laytpl', 'layer' ], function() {
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		var laytpl = layui.laytpl;
		
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);//关闭当前页  
			var data = {
				"id": $("#productId").val(),
				"name": $("#productId").find("option:selected").text(),
				"price": $("#productId").find("option:selected").attr("data-price"),
				"count": $("#count").val()
			}
			
			var tpl = productTpl.innerHTML;
			laytpl(tpl).render(data, function(html) {
				$(window.parent.document).find("#productInfo").find("tbody").append(html);
			});
			
			parent.layer.msg("添加产品成功", {
				icon : 1
			});
			
			return false;
		});

	});
</script>
</head>
<body>
	<form class="layui-form" action="" id="addServeform"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">产品名</label>
				 <div class="layui-input-block normal-select">
				  	<select id="productId" lay-search="">
				  		<option value=""></option>
					    <c:forEach items="${productList}" var="item">
					    	<option value="${ item.id }" data-price="${ item.price }">${ item.productName }</option> 
					    </c:forEach>
				 	 </select>
			     </div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">数量</label>
				<div class="layui-input-block">
					<input type="number" id="count" lay-verify="required"
						placeholder="请输入数量" autocomplete="off" class="layui-input"
						value="">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div align="center">
				<a class="layui-btn kxnz-btn-default" lay-submit lay-filter="formSubmitBtn">提交</a>
				<button class="layui-btn kxnz-btn-default closeBtn" id="closeBtn">关闭</button>
			</div>
		</div>
	</form>
</body>
</html>

<script id="productTpl" type="text/html">
	<tr class="mealDts" data-type='1'>
		<td>{{ d.name }}</td>
		<td>{{ d.price }}</td>
		<td data-id="quantity">{{ d.count }}</td>
		<td data-id='itemId' style="display:none;">{{ d.id }}</td>
		<td><a class="layui-btn layui-btn-danger layui-btn-xs delBtn">删除</a>
		</td>
	</tr>
</script>

