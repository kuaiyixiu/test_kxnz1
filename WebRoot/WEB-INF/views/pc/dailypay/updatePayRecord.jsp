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
	<form action="" class="layui-form" id="updateTypeForm"  style="width: 95%;height: 98%;margin-top: 1%;">
		  <div class="layui-form-item">
		    <label class="layui-form-label">收入/支出</label>
		    <div class="layui-input-block">
		      <input type="radio" name="kind" value="2" title="支出" checked>
		      <input type="radio" name="kind" value="1" title="收入">
		    </div>
		  </div>
		    <div class="layui-form-item">
			    <label class="layui-form-label">收支类型</label>
			    <div class="layui-input-block">
			      <select name="payTypeId" lay-verify="required">
			        <option value=""></option>
			        <c:forEach items="${typeList }" var="types">
					    <option value="${types.id }" <c:if test="${types.id==dailyPayRecord.payTypeId }">selected="selected"</c:if>>${types.typeName }</option>
					</c:forEach>
			      </select>
			    </div>
			  </div>
		    <div class="layui-form-item">
			    <label class="layui-form-label">收支类型</label>
			    <div class="layui-input-block">
			      <select name="payId" lay-verify="required">
			        <option value=""></option>
			        <option value="1" <c:if test="${dailyPayRecord.payId == '1' }">selected="selected"</c:if>>现金</option>
			        <option value="2" <c:if test="${dailyPayRecord.payId == '2' }">selected="selected"</c:if>>微信</option>
			        <option value="3" <c:if test="${dailyPayRecord.payId == '3' }">selected="selected"</c:if>>支付宝</option>
			        <option value="4" <c:if test="${dailyPayRecord.payId == '4' }">selected="selected"</c:if>>其他</option>
			      </select>
			    </div>
			  </div>
				  <div class="layui-form-item">
				   <label class="layui-form-label">分摊周期</label>
					开始：<div class="layui-inline"> 
						  <input type="text" class="layui-input" id="startSharePeriod" name="startSharePeriod" lay-verify="required|date" value="<fmt:formatDate value="${dailyPayRecord.startSharePeriod }" pattern="yyyy-MM-dd"/>" >
					</div>
					结束：<div class="layui-inline">
						  <input type="text" class="layui-input" id="endSharePeriod" name="endSharePeriod" lay-verify="required|date"  value="<fmt:formatDate value="${dailyPayRecord.endSharePeriod }" pattern="yyyy-MM-dd"/>">
					</div>
				  </div>
				  
				  <div class="layui-form-item">
				  <label class="layui-form-label">支付金额</label>
				  <div class="layui-input-block">
				    <input type="number" name="amount" lay-verify="required" placeholder="请输入收支金额" autocomplete="off" class="layui-input" lay-verify="required|number"
				    value="${dailyPayRecord.amount }" >
				  </div>
				</div>
				  
				  <div class="layui-form-item layui-form-text">
					    <label class="layui-form-label">收支摘要</label>
					    <div class="layui-input-block">
					      <textarea name="remark" id="remark" placeholder="请输入收支摘要" class="layui-textarea">${dailyPayRecord.remark }</textarea>
					    </div>
  					</div>
				<div class="layui-form-item">
					<div align="left" style="margin-left: 250px;">
						<a class="layui-btn layui-btn-normal" lay-submit lay-filter="formSubmitBtn">提交</a>
						<a class="layui-btn closeBtn layui-btn-normal">关闭</a>
					</div>
				</div>
			<input type="hidden" name="id" id="id" value="${dailyPayRecord.id }">
	</form>
</body>
<script type="text/javascript">
layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
	var form = layui.form;
	var layer = layui.layer;
	var $ = layui.jquery;
	var laydate = layui.laydate;
	laydate.render({
		elem : '#startSharePeriod'
	});
	
	laydate.render({
		elem : '#endSharePeriod'
	});
	
	form.render();
	
	form.on('submit(formSubmitBtn)', function(data) {	
		$.ajax({
			type : "POST",
			url : "dailyPay/updateRecord.do",
			data : data.field,
			dataType : "json",
			success : function(result) {
				if (result.retCode == 'success') {
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);//关闭当前页  
					parent.layui.table.reload('ordersTable');
					parent.layer.msg(result.retMsg, {icon : 1});
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

</html>

 