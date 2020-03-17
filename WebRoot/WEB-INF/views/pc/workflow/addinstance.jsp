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
				url : "workflowInstance/createInstance.do",
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
		<div class="layui-form-item">
		    <label class="layui-form-label">流程模版</label>
		    <div class="layui-input-block">
		      <select lay-search name="templateId" id="templateId" lay-filter="changetemplateId" required lay-verify="required">
		       	<option value=''></option>
		       	<c:forEach items="${templateList}" var="tpl" >
		       		<option value='${tpl.id }'>${tpl.name }</option>
		       	</c:forEach>
		      </select>
		    </div>
	  	</div>
		<div class="layui-form-item">
			<label class="layui-form-label">审批名称</label>
			<div class="layui-input-block">
				<input type="text" name="name" id="name" required lay-verify="required"
					placeholder="请输入审批名称" autocomplete="off" class="layui-input"
					value="">
			</div>
		</div>
	  	  <div class="layui-form-item">
		    <label class="layui-form-label">单据类型</label>
		    <div class="layui-input-block">
		      <select name="slipType" lay-search >
		       	<option value=''>无</option>
		       	<option value='1'>入库单</option>
		       	<option value='2'>退货单</option>
		      </select>
		    </div>
	  	</div>
	  	
	  	<div class="layui-form-item">
			<label class="layui-form-label">单据编号</label>
			<div class="layui-input-block">
				<input type="text" name="slipId"
					placeholder="请输入单据编号" autocomplete="off" class="layui-input"
					value="">
			</div>
		</div>
	  	
	  <div class="layui-form-item layui-form-text">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<textarea name="remark" placeholder="请输入备注"
					class="layui-textarea"></textarea>
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
<script type="text/javascript">
layui.use(['form'], function() {
	form=layui.form;
	form.on('select(changetemplateId)', function(data){   
		var $ = layui.jquery;
		$("#name").val(data.elem[data.elem.selectedIndex].text+" "+formatDate(new Date()));
	 });
});

</script>
</html>

