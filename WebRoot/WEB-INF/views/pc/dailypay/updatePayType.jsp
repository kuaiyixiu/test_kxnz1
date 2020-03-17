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
<link rel="stylesheet" type="text/css"
	href="lib/admin/layui/css/layui.css" />
 <script src="lib/admin/layui/layui.js" type="text/javascript" 
	charset="utf-8"></script>
<script src="lib/admin/js/common.js" type="text/javascript"
	charset="utf-8"></script> 
<script type="text/javascript" src="js/sysmanage/car/editCar.js"></script> 
</head>
<body>
	<form action="" class="layui-form" id="updateTypeForm"  style="width: 95%;height: 98%;margin-top: 1%;">
		<div class="layui-form-item">
				<label class="layui-form-label">分类名称</label>
				<div class="layui-input-block">
					<input type="text"  class="layui-input"  name="typeName"  id="typeName" value="${dailyPayType.typeName }" >
				</div> 
		</div>
		<div class="layui-form-item">
			<div align="left" style="margin-left: 250px;">
				<a class="layui-btn layui-btn-normal" lay-submit lay-filter="formSubmitBtn">提交</a>
				<a class="layui-btn closeBtn layui-btn-normal">关闭</a>
			</div>
		</div>
		<input type="hidden" name="id" id="id" value="${dailyPayType.id }">
	</form>
</body>
<script type="text/javascript">
layui.use(['laydate', 'jquery', 'form', 'element'], function() {
	var $ = layui.jquery;
	var form = layui.form;
	form.on('submit(formSubmitBtn)', function(data) {
		$.ajax({
			type: "POST",
			url: "dailyPay/updateType.do",
			data: data.field,
			dataType: "json",
			success: function(result) {
				if (result.retCode == 'success') {
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index); //关闭当前页  
					parent.layui.table.reload('ordersTable');

					parent.layer.msg(result.retMsg, {
						icon: 1
					});
				} else {
					layer.msg(result.retMsg, {
						icon: 2
					});
				}
			}
		});
	});
});

</script>

</html>

 