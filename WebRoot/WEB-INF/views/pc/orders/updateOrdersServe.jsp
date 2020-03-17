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
<link rel="stylesheet" type="text/css"
	href="lib/admin/layui/css/layui.css" />
<script src="lib/admin/layui/layui.js" type="text/javascript"
	charset="utf-8"></script>
<script src="lib/admin/js/common.js" type="text/javascript"
	charset="utf-8"></script>
<style type="text/css">
.layui-word-aux{color: red!important;}
</style>	
<script type="text/javascript">
</script>
</head>
<body>
	<form class="layui-form" action="" id="form" style="width: 95%;height: 98%;margin-top: 1%;">
		<div class="layui-form-item">
				<label class="layui-form-label">服务名称</label>
				<div class="layui-input-block">
					<input type="text"  class="layui-input  layui-disabled"  value="${ordersServe.serveName }" disabled="disabled">
				</div> 
		</div>
		<div class="layui-form-item">
				<label class="layui-form-label">价格</label>
				<div class="layui-input-block">
					<input type="text" id="price" placeholder="请输入价格" class="layui-input <c:if test="${not empty ordersServe.mealPayRecordId }">	layui-disabled</c:if>"  value="${ordersServe.price }"  onchange="changePrice()" 	<c:if test="${not empty ordersServe.mealPayRecordId }">	 disabled="disabled"</c:if> >
					<c:if test="${not empty ordersServe.mealPayRecordId }"><div class="layui-form-mid layui-word-aux">套餐服务，不能修改价格</div></c:if>
				</div>
		</div>
		<div class="layui-form-item layui-form-text">
		    <label class="layui-form-label">备注</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入备注" class="layui-textarea" id="remark">${ordersServe.remark }</textarea>
		    </div>
  		</div>
  		  <div class="layui-form-item">
		    <label class="layui-form-label">施工人员</label>
		    <div class="layui-input-inline">
		      <select id="" lay-search lay-filter="serveSel">
		        <option value=""></option>
		       	<c:forEach items="${users}" var="user" varStatus="status">
		       	<option value='${user.id }' <c:if test="${ordersServe.serveUser == user.id }">selected</c:if>>${user.userRealname }</option>
		       	</c:forEach>
		      </select>
		    </div>
		     <div class="layui-form-mid layui-word-aux">提成金额：<i id="serveRoyal-i">${ordersServe.serveRoyal }</i> 元</div>
	  	</div>
	  	
	    <div class="layui-form-item">
	    <label class="layui-form-label">销售人员</label>
	    <div class="layui-input-inline">
	      <select  id="sellUser" lay-search lay-filter="sellSel">
	        <option value=""></option>
	       	<c:forEach items="${users}" var="user" varStatus="status">
		       	<option value='${user.id }' <c:if test="${ordersServe.sellUser == user.id }">selected</c:if>>${user.userRealname }</option>
		    </c:forEach>
	      </select>
	    </div>
	     <div class="layui-form-mid layui-word-aux">提成金额：<i id="sellRoyal-i">${ordersServe.sellRoyal }</i> 元</div>
	  </div>
		<input type="hidden" id="serveUser"  value="${ordersServe.serveUser }">
		<input type="hidden" id="serveRoyal"  value="${ordersServe.serveRoyal }">
		<input type="hidden" id="sellUser"  value="${ordersServe.sellUser }">
		<input type="hidden" id="sellRoyal"  value="${ordersServe.sellRoyal }">
	</form>
</body>
<script type="text/javascript">
var serveRoy = {'royaltyCount':0};//服务提成
var sellRoy = {'royaltyCount':0};//销售提成
layui.use(['form','jquery','laytpl', 'form', 'element'], function() {
	var $ = layui.jquery;
	layui.jquery.ajax({
		type : "POST",
		url : "royalty/getByRoyaltyListId.do",
		data:{'royaltyId':'${ordersServe.serveId}' ,'kind' : '12'},
		dataType : 'json',
		success : function(data) {
			$(data).each(function(index,item){
				if (item.kind == 1){//服务提成
					serveRoy.royaltyType =	item.royaltyType;
					serveRoy.royaltyCount = item.royaltyCount;
				}else if (item.kind == 2){//销售提成
					sellRoy.royaltyType =	item.royaltyType;
					sellRoy.royaltyCount = item.royaltyCount;
				}
			});
		}
	});
	
	
	layui.form.on('select(serveSel)', function(data){
		$("#serveUser").val("");
		$("#serveRoyal").val("0");
		if (data.value != ''){
			$("#serveUser").val(data.value);
			if( serveRoy.royaltyCount > 0 ){
				if (serveRoy.royaltyType == 1){ //固定值
					$("#serveRoyal").val(serveRoy.royaltyCount);
				}else{ //百分比
					$("#serveRoyal").val(serveRoy.royaltyCount / 100 * parseFloat($("#price").val()));
				}
			}
		}
		$("#serveRoyal-i").text($("#serveRoyal").val());
	});	
	
	layui.form.on('select(sellSel)', function(data){
		$("#sellUser").val("");
		$("#sellRoyal").val("0");
		if (data.value != ''){
			$("#sellUser").val(data.value);
			if( sellRoy.royaltyCount > 0 ){
				if (sellRoy.royaltyType == 1){ //固定值
					$("#sellRoyal").val(sellRoy.royaltyCount);
				}else{ //百分比
					$("#sellRoyal").val(sellRoy.royaltyCount / 100 * parseFloat($("#price").val()));
				}
			}
		}
		$("#sellRoyal-i").text($("#sellRoyal").val());
	});	
	
});

function changePrice(){
	var $ = layui.jquery;
	if( serveRoy.royaltyCount > 0 && $("#serveUser").val() != ""){
		if (serveRoy.royaltyType == 1){ //固定值
			$("#serveRoyal").val(serveRoy.royaltyCount);
		}else{ //百分比
			$("#serveRoyal").val(serveRoy.royaltyCount / 100 * parseFloat($("#price").val()));
		}
		$("#serveRoyal-i").text($("#serveRoyal").val());
	}
	if( sellRoy.royaltyCount > 0 && $("#sellUser").val() != ""){
		if (sellRoy.royaltyType == 1){ //固定值
			$("#sellRoyal").val(sellRoy.royaltyCount);
		}else{ //百分比
			$("#sellRoyal").val(sellRoy.royaltyCount / 100 * parseFloat($("#price").val()));
		}
		$("#sellRoyal-i").text($("#sellRoyal").val());
	}
}

</script>
</html>

