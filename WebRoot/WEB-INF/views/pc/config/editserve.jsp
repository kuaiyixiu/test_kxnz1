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
		form.render();
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			$.ajax({
				type : "POST",
				url : "serve/saveData.do",
				data : data.field,
				dataType : "json",
				success : function(result) {
					if (result.retCode == 'success') {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭当前页  
						parent.layui.table.reload('serveInfo');
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
	<form class="layui-form" action="" id="form"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<input type="hidden" name="id" id="id" value="${serve.id }" />
		<div class="layui-form-item">
			<label class="layui-form-label"><font color="red">*</font>服务名称</label>
			<div class="layui-input-block">
				<input type="text" name="serveName" lay-verify="required"
					placeholder="请输入服务名称" autocomplete="off" class="layui-input"
					value="${serve.serveName }" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><font color="red">*</font>所属分类</label>
			<div class="layui-input-block">
				<select name="classId" lay-verify="required">
				    <option value="">请选择分类</option>
					<c:forEach items="${serveClassList }" var="serveClass">
						<option value="${serveClass.id }"
							<c:if test="${serve.classId==serveClass.id }">selected</c:if>>${serveClass.className}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><font color="red">*</font>服务价格</label>
			<div class="layui-input-block">
				<input type="number" name="price" onblur="initNum(this,2)"
					placeholder="请输入服务价格" autocomplete="off" class="layui-input"
					value="${serve.price }" lay-verify="required" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">时间(分钟)</label>
			<div class="layui-input-block">
				<input type="number" name="sz" onblur="initNum(this,0)"
					placeholder="请输入时间" autocomplete="off" class="layui-input"
					value="${serve.sz }" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">周期(天数)</label>
			<div class="layui-input-block">
				<input type="text" name="zq" onblur="initNum(this,0)"
					placeholder="请输入周期" autocomplete="off" class="layui-input"
					value="${serve.zq }" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">工单配置</label>
			<div class="layui-input-block">
				<input type="checkbox" name="construction" title="免施工" value="1" <c:if test="${serve.construction=='1' }">checked="checked"</c:if>/> 
				<input type="checkbox" name="completion" title="免完工" value="1" <c:if test="${serve.completion=='1' }">checked="checked"</c:if> />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">项目描述</label>
			<div class="layui-input-block">
				<textarea name="remark" class="layui-textarea">${serve.remark }</textarea>
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

