<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	layui.use([ 'jquery', 'form', 'layer' ], function() {
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		form.render();
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			$.ajax({
				type : "POST",
				url : "coupon/saveCouponPackDt.do",
				data : data.field,
				dataType : "json",
				success : function(result) {
					if (result.retCode == 'success') {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭当前页  
						parent.layui.table.reload('ordersTable');
						parent.layer.msg(result.retMsg, {icon : 1});
					} else {
						layer.msg(result.retMsg, {icon : 2});
					}
				}
			});
			return false;
		});

	});
</script>
</head>
<body>
	<form class="layui-form" action="" id="form"  style="width: 95%;height: 98%;margin-top: 1%;">
		<input type="hidden" name="couponId" id="couponId" value="<c:if test="${fn:length(couponList) > 0}">${couponList[0].id }</c:if>" />
		<input type="hidden" name="packId" id="packId" value="${packId}" />
		  <div class="layui-form-item">
		    <label class="layui-form-label">代金券</label>
		    <div class="layui-input-block">
		      <select name="" lay-verify="required"   lay-filter="changeCoupon">
				<c:forEach items="${ couponList }" var="coupon">
					<option id="opt-${coupon.id }" remark="${coupon.remark }"  value="${coupon.id }">${coupon.name }|价值(元)：${coupon.value }|有效天数(天):${coupon.days }</option>
				</c:forEach>
		      </select>
		    </div>
		  </div>

		<div class="layui-form-item layui-form-text">
			<label class="layui-form-label">描述</label>
			<div class="layui-input-block">
				<textarea id="remark" placeholder=""  disabled="disabled"  class="layui-textarea layui-disabled"><c:if test="${fn:length(couponList) > 0}">${couponList[0].remark }</c:if></textarea>
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

<script type="text/javascript">
layui.use(['form'], function() {
	form=layui.form;
	form.on('select(changeCoupon)', function(data){   
		var $ = layui.jquery;
	  	var val=data.value;
		$("#couponId").val(data.value);
		$("#remark").val($("#opt-"+val).attr("remark"));
	 });
});
</script>