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
		form.val("addform", {
			  "payAmount": parseFloat(parent.needPay).toFixed(2)
			})   
		form.render();
        form.verify({
        	payAmount: function(value, item){ //value：表单的值、item：表单的DOM对象
        		if(value<0)
        			return "还款金额必须大于0";
        		}
        });   
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			var payAmount=$("#payAmount").val();
			$.ajax({
				type : "POST",
				url : "repertory/savePayData.do",
				data : data.field,
				dataType : "json",
				success : function(result) {
					if (result.retCode == 'success') {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭当前页  
						parent.layui.table.reload('tableInfo2');
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
	<form class="layui-form" action="" id="form" style="width: 95%;height: 98%;margin-top: 1%;" lay-filter="addform">
		<input type="hidden" name="repertoryId" value="${repertoryId }" /> 
		<div class="layui-form-item">
			<label class="layui-form-label">付款方式</label>
			<div class="layui-input-block">
				<select name="payId" lay-verify="required">
				<option value=''>请选择付款方式</option>
				<c:forEach items="${payTypes}" var="pay" varStatus="status">
		       		<option value='${pay.id }'>${pay.payName }</option>
		       	</c:forEach>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">金额</label>
			<div class="layui-input-block">
				<input type="number" name="payAmount" id="payAmount"  lay-verify="payAmount"
					autocomplete="off" class="layui-input" value="0.00"
					onblur="initNum(this,2)" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<textarea name="remark" class="layui-textarea"></textarea>
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

