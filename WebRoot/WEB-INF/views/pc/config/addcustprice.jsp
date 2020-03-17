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
</head>
<body>
	<form class="layui-form" action="" id="form"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<input type="hidden" name="kind" id="kind" value="${kind }" />
		<c:if test="${kind !='1' }">
			<div class="layui-form-item">
				<label class="layui-form-label">提成类型</label>
				<div class="layui-input-block">
					<input type="radio" name="royaltyType" value="1" title="固定值" <c:if test="${empty royalty.royaltyType or royalty.royaltyType=='1'}">checked</c:if> /> 
					<input type="radio" name="royaltyType" value="2" title="百分比" <c:if test="${royalty.royaltyType=='2'}">checked</c:if> />
				</div>
			</div>
		</c:if>
		<div class="layui-form-item">
			<label class="layui-form-label">提成金额</label>
			<div class="layui-input-block">
				<input type="number" name="royaltyCount" id="royaltyCount"
					lay-verify="required" onblur="initNum(this,2)" autocomplete="off"
					class="layui-input"
					value="<fmt:formatNumber type="number" value="${royalty.royaltyCount }" pattern="0.00"/>" />
			</div>
		</div>
		<c:if test="${kind !='1' }">
			<fieldset class="layui-elem-field layui-field-title">
				<legend>
					<font size="2" color="red">说明：</font>
				</legend>
			</fieldset>
			<div style="margin-left: 10px;">
				<font size="2" color="red"><i class="layui-icon layui-icon-about"></i>&nbsp;&nbsp;当提成类型为【固定值】时，提成数值应填提成的固定值，比如"10"表示不管销售额为多少，都提成
					￥10;</font>
			</div>
			<div style="margin-left: 10px;">
				<font size="2" color="red"><i class="layui-icon layui-icon-about"></i>&nbsp;&nbsp;当提成类型为【百分比】时，提成数值应填提成的占营收的百分比，比如"10"表示"10%"即当服务销售额为100时提成
					￥10;</font>
			</div>
		</c:if>

	</form>
</body>
</html>

