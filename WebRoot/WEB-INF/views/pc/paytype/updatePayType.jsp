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
				<label class="layui-form-label">支付类型</label>
				<div class="layui-input-block">
					<input type="text"  class="layui-input"  name="payName"  id="payName" value="${payType.payName }" lay-verify="required|payName" />
				</div> 
		</div>
		<div class="layui-form-item">
				<label class="layui-form-label">使用对象</label>
                <div class="layui-input-block">
                  <input type="checkbox" name="shopChecked" title="门店" <c:if test="${payType.shopChecked==1 }">checked</c:if> value="1">
                  <input type="checkbox" name="custChecked" title="客户" 
                  <c:if test="${payType.custChecked==1 }">checked</c:if> value="1">
                </div>
		</div>
		
		<div class="layui-form-item">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-block">
					<input type="text"  class="layui-input"  name="remark"  id="remark" value="${payType.remark }" />
				</div> 
		</div>
		<div class="layui-form-item">
			<div align="left" style="margin-left: 250px;">
				<a class="layui-btn layui-btn-normal" lay-submit lay-filter="formSubmitBtn">提交</a>
				<a class="layui-btn closeBtn layui-btn-normal">关闭</a>
			</div>
		</div>
		<input type="hidden" name="id" id="id" value="${payType.id }">
	</form>
</body>
<script type="text/javascript">
layui.use(['laydate', 'jquery', 'form', 'element'], function() {
	var $ = layui.jquery;
	var form = layui.form;
	//表单校验
	form.verify({
		payName: function(value, item){ //value：表单的值、item：表单的DOM对象
			var res=ajaxMaxCount(value);
			if (res.retCode == 'error')
				return res.retMsg;
		  }
		}); 
	form.on('submit(formSubmitBtn)', function(data) {
		$.ajax({
			type: "POST",
			url: "payType/savePayType.do",
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
	
	function ajaxMaxCount(payName){
		var res=null;
		$.ajax({
			type : "POST",
			url : "payType/chkData.do",
			data : {"payName":payName},
			dataType : "json",
			async:false, 
			success : function(result) {
				res=result;
			}
		});
		return res;
	}
});

</script>

</html>

 