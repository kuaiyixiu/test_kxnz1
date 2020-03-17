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
				url : "repertory/saveDtData.do",
				data : data.field,
				dataType : "json",
				success : function(result) {
					if (result.retCode == 'success') {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭当前页  
						parent.layui.table.reload('tableInfo1');
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
	
	function chkSum(obj){
		var $ = layui.jquery;
		initNum(obj,2);
		var price=$("#price").val();
		var quantity=parseInt($("#quantity").val());
		$("#sum").val(parseFloat(price*quantity).toFixed(2));
	}
</script>
</head>
<body>
	<form class="layui-form" action="" id="form"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<input type="hidden" name="id" value="${repertoryDt.id }" /> 
		<div class="layui-form-item">
			<label class="layui-form-label">单价</label>
			<div class="layui-input-block">
				<input type="number" name="inprice" id="price"
					autocomplete="off" class="layui-input" value="${repertoryDt.inprice }"
					onblur="chkSum(this)" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">数量</label>
			<div class="layui-input-block">
				<input type="number" name="inQuantity" id="quantity"
					autocomplete="off" class="layui-input layui-disabled" value="${repertoryDt.inQuantity }" 
					disabled="disabled" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">入库总价</label>
			<div class="layui-input-block">
				<input type="number" name="sum" id="sum" autocomplete="off" class="layui-input layui-disabled" value="<fmt:formatNumber type="number" value="${repertoryDt.sum }" pattern="0.00"/>"
					disabled="disabled" />
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

