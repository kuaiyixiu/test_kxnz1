<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>连途</title>
<%@ include file="../base.jsp"%>
<script type="text/javascript">
function userDefinedLoadForm(){
	var ifm= document.getElementById("viewIframe");
	ifm.height=document.documentElement.clientHeight;
}
//window.onresize=function(){ userDefinedLoadForm();}
</script>
</head>
<body>
	<div class="page-content-wrap">
	    <input type="hidden" id="pid" value="" />
	    <iframe src="shop/bossInfo.do" id="viewIframe" frameborder=no scrolling="yes" style=" overflow-x:scroll; overflow-y:hidden; " width="100%" ></iframe>
<%--	    <iframe id="viewIframe" width="100%" src="shop/bossInfo.do"  frameborder="no" border="0" scrolling="auto">--%>
<%--	    </iframe>--%>
	</div>
</body>
</html>