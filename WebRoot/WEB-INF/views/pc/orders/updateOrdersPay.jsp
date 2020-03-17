<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
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
<link rel="stylesheet" type="text/css" href="lib/admin/layui/css/layui.css" />
<script src="lib/admin/layui/layui.js" type="text/javascript"charset="utf-8"></script>
<script src="lib/admin/js/common.js" type="text/javascript"charset="utf-8"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<form class="layui-form" action="" id="form" style="width: 95%;height: 98%;margin-top: 1%;">
  		  <div class="layui-form-item">
		    <label class="layui-form-label">付款方式</label>
		    <div class="layui-input-block">
					<input disabled="disabled" type="text"   class="layui-input layui-disabled"  value="${ordersPay.payName }">
		      <%--<select id="payId" lay-search disabled="disabled" class="layui-disabled">
		        <option value=""></option>
		       	<c:forEach items="${payTypes}" var="pay" varStatus="status">
		       	<option value='${pay.id }' <c:if test="${ordersPay.payId == pay.id }">selected</c:if>>${pay.payName }</option>
		       	</c:forEach>
		      </select>
		    --%></div>
	  	</div>
		<div class="layui-form-item">
				<label class="layui-form-label">付款日期</label>
				<div class="layui-input-block">
					<input type="text"  class="layui-input layui-disabled" disabled="disabled" value="<fmt:formatDate value="${ordersPay.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/>">
				</div>
		</div>
		<div class="layui-form-item">
				<label class="layui-form-label">付款金额</label>
				<div class="layui-input-block">
					<input <c:if test="${ordersPay.payId eq COUPONPAY or ordersPay.payId eq CARDSETPAY}">disabled="disabled"</c:if> type="text" id="payAmount" placeholder="请输入付款金额" class="layui-input <c:if test="${ordersPay.payId eq COUPONPAY or ordersPay.payId eq CARDSETPAY}"> layui-disabled</c:if>"  value="${ordersPay.payAmount }">
				</div>
		</div>
		<div class="layui-form-item layui-form-text">
		    <label class="layui-form-label">备注</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入备注" class="layui-textarea" id="remark">${ordersPay.remark }</textarea>
		    </div>
  		</div>
  		<input type="hidden" id="canEdit" value="${ordersPay.canEdit }" >
  		<input type="hidden" id="shopId" value="${ordersPay.shopId }" >
	</form>
</body>
<script type="text/javascript">


</script>
</html>

