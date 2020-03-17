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
<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> -->
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>连途</title>
<%@ include file="../base.jsp"%>
</head>

<body>
	<div class="page-content-wrap">
		<table class="layui-table" style="margin-left: 1%;width: 98%;">
			<thead>
				<tr>
					<th style="text-align: center;">名称</th>
					<th style="text-align: center;">总金额</th>
					<th style="text-align: center;">赠送时间</th>
					<th style="text-align: center;">到期时间</th>
					<th style="text-align: center;">可用金额</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${dtList }" var="custCoupon">
					<tr>
						<td align="center">${custCoupon.couponName }</td>
						<td align="center">${custCoupon.totalAmount }</td>
						<td align="center"><fmt:formatDate value="${custCoupon.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td align="center"><fmt:formatDate value="${custCoupon.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td align="center">${custCoupon.availAmount }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>

</html>
