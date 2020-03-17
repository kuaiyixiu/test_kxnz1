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
<link rel="stylesheet" type="text/css" href="lib/admin/layui/css/layui.css" />
<script src="lib/admin/layui/layui.js" type="text/javascript" charset="utf-8"></script>
<script src="lib/admin/js/common.js" type="text/javascript" charset="utf-8"></script>
<script src="js/layui-xtree.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var xtree1;
    function userDefinedLoadForm(){
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		xtree1 = new layuiXtree({
		       elem: 'xtree1',                 //必填
		       form: form,                   //必填
		       data: 'role/rolePermission.do?id=${roleId}', //必填
		       //isopen: false,  //加载完毕后的展开状态，默认值：true
		       //ckall: false,   //启用全选功能，默认值：false
		});
    }
</script>
</head>
<body>
	<form class="layui-form" action="" id="form" style="width: 95%;height: 98%;margin-top: 1%;">
	    <input type="hidden" id="roleId" value="${roleId}" />
		<div id="xtree1" style="width:400px"></div>
	</form>
</body>
</html>

