<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html class="ui-page-login">
<base href="<%=basePath%>">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="lib/mui/css/mui.min.css" rel="stylesheet" />
		<link href="lib/mui/css/style.css" rel="stylesheet" />
		<script src="lib/mui/js/mui.min.js"></script>
		<script src="lib/jquery/jquery-3.4.1.js"></script>
		
		<link rel="stylesheet" type="text/css" href="css/wechatKxnz.css" />
	</head>
<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">车辆详情</h1>
		</header>
		<div class="mui-content">
			<ul class="mui-table-view">
				<li class="mui-table-view-cell mui-media">
					<a href="javascript:;" class="">
						<div class="mui-media-body">
							车主信息
							<p class="mui-ellipsis">车主姓名:${ custom.custName }</p>
							<p class="mui-ellipsis">联系方式:${ custom.cellphone }</p>
						</div>
					</a>
				</li>
			</ul>
			<br/>
			<c:choose>
				<c:when test="${empty carList }">
					暂无绑定车辆
				</c:when>
				<c:otherwise>
					<ul class="mui-table-view">
						<c:forEach items="${ carList }" var="item">
							<li class="mui-table-view-cell mui-media">
								<a href="javascript:;" class="">
									<div class="mui-media-body">
										车辆信息
										<p class="mui-ellipsis"><span class="carTitle">车牌号:</span>${ item.carNumber }</p>
										<p class="mui-ellipsis"><span class="carTitle">车辆型号:</span>${ item.carTypeName }</p>
										<p class="mui-ellipsis"><span class="carTitle">车辆品牌:</span>${ item.carBrand }</p>
										<p class="mui-ellipsis"><span class="carTitle">车架号:</span>${ item.carVin }</p>
										<p class="mui-ellipsis"><span class="carTitle">发动机号:</span>${ item.carEngine }</p>
										<p class="mui-ellipsis"><span class="carTitle">交强险到期:</span>${ item.compulsoryDateStr }</p>
										<p class="mui-ellipsis"><span class="carTitle">商业险到期:</span>${ item.commercialDateStr }</p>
										<p class="mui-ellipsis"><span class="carTitle">年检到期:</span>${ item.checkDateStr }</p>
										<p class="mui-ellipsis"><span class="carTitle">备注:</span>${ item.remark }</p>
									</div>
								</a>
							</li>
						</c:forEach>
					</ul>
				</c:otherwise>
			</c:choose>
		</div>
	</body>
</html>
<script type="text/javascript">
	

</script>

