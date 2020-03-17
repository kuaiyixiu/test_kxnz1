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
		      <select id="payId" lay-search lay-filter="selectPay">
		        <option value=""></option>
		       	<c:forEach items="${payTypes}" var="pay" varStatus="status">
		       		<option value='${pay.id }'>${pay.payName }</option>
		       	</c:forEach>
		      </select>
		    </div>
	  	</div>
		<div class="layui-form-item">
				<label class="layui-form-label">付款金额</label>
				<div class="layui-input-block">
					<input type="text" id="payAmount" placeholder="请输入付款金额" class="layui-input"  value="">
				</div>
		</div>
		<div class="layui-form-item layui-form-text">
		    <label class="layui-form-label">备注</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入备注" class="layui-textarea" id="remark"></textarea>
		    </div>
  		</div>
  		 <input type="hidden" id="canEdit" value="1" >
  		<input type="hidden" id="shopId" value="${shopId}" >
	</form>
</body>
<script type="text/javascript">
layui.use([ 'jquery', 'form', 'layer' ], function() {
	var form = layui.form;
	var layer = layui.layer;
	var $ = layui.jquery;
	//监听提交
	form.on('select(selectPay)', function(data){
		  console.log(data.elem); //得到select原始DOM对象
		  console.log(data.value); //得到被选中的值
		  console.log(data.othis); //得到美化后的DOM对象
	}); 

});

</script>
</html>

