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
				<label class="layui-form-label">产品名称</label>
				<div class="layui-input-block">
					<input type="text"  class="layui-input layui-disabled"  value="${ordersProduct.productName }" disabled="disabled">
				</div>
		</div>
		<div class="layui-form-item">
				<label class="layui-form-label">数量</label>
				<div class="layui-input-block">
					<input type="text" id="quantity" placeholder="请输入数量" class="layui-input <c:if test="${not empty ordersProduct.mealPayRecordId }">	layui-disabled</c:if>"  value="${ordersProduct.quantity }" onchange="changePrice()" <c:if test="${not empty ordersProduct.mealPayRecordId }">	 disabled="disabled"</c:if>>
					<c:if test="${not empty ordersProduct.mealPayRecordId }"><div class="layui-form-mid layui-word-aux">套餐产品，不能修改数量</div></c:if>
				</div>
		</div>
		<div class="layui-form-item">
				<label class="layui-form-label">单价</label>
				<div class="layui-input-block">
					<input type="text" id="price" placeholder="请输入价格" class="layui-input <c:if test="${not empty ordersProduct.mealPayRecordId }">	layui-disabled</c:if>"  value="${ordersProduct.price }"  onchange="changePrice()" <c:if test="${not empty ordersProduct.mealPayRecordId }">	 disabled="disabled"</c:if> >
					<c:if test="${not empty ordersProduct.mealPayRecordId }"><div class="layui-form-mid layui-word-aux">套餐产品，不能修改价格</div></c:if>
				</div>
		</div>
		<div class="layui-form-item layui-form-text">
		    <label class="layui-form-label">备注</label>
		    <div class="layui-input-block">
		      <textarea placeholder="请输入备注" class="layui-textarea" id="remark">${ordersProduct.remark }</textarea>
		    </div>
  		</div>
  		  <div class="layui-form-item">
		    <label class="layui-form-label">销售人员</label>
		    <div class="layui-input-inline">
		      <select id="" lay-search lay-filter="productSel">
		        <option value=""></option>
		       	<c:forEach items="${users}" var="user" varStatus="status">
		       	<option value='${user.id }' <c:if test="${ordersProduct.productUser == user.id }">selected</c:if>>${user.userRealname }</option>
		       	</c:forEach>
		      </select>
		    </div>
		    <div class="layui-form-mid layui-word-aux">提成金额：<i id="productRoyal-i">${ordersProduct.productRoyal }</i> 元</div>
	  	</div>
	  	<input type="hidden" id="productUser"  value="${ordersProduct.productUser }">
		<input type="hidden" id="productRoyal"  value="${ordersProduct.productRoyal }">
	</form>
</body>
<script type="text/javascript">
var productRoy = {'royaltyCount':0};//产品提成
layui.use(['form','jquery','laytpl', 'form', 'element'], function() {
	var $ = layui.jquery;
	layui.jquery.ajax({
		type : "POST",
		url : "royalty/getByRoyaltyListId.do",
		data:{'royaltyId':'${ordersProduct.productId}' ,'kind' : '3'},
		dataType : 'json',
		success : function(data) {
			$(data).each(function(index,item){
				productRoy.royaltyType =	item.royaltyType;
				productRoy.royaltyCount = item.royaltyCount;
			});
		}
	});
	
	
	layui.form.on('select(productSel)', function(data){
		$("#productUser").val("");
		$("#productRoyal").val("0");
		if (data.value != ''){
				$("#productUser").val(data.value);
			if( productRoy.royaltyCount > 0 ){
				if (productRoy.royaltyType == 1){ //固定值
					$("#productRoyal").val(productRoy.royaltyCount * parseFloat($("#quantity").val()));
				}else{ //百分比
					$("#productRoyal").val(productRoy.royaltyCount / 100 * parseFloat($("#price").val()));
				}
			}
		}
		$("#productRoyal-i").text($("#productRoyal").val());
	});	
	
});

function changePrice(){
	var $ = layui.jquery;
	if( productRoy.royaltyCount > 0 && $("#productUser").val() != ""){
		if (productRoy.royaltyType == 1){ //固定值
			$("#productRoyal").val(productRoy.royaltyCount * parseFloat($("#quantity").val()));
		}else{ //百分比
			$("#productRoyal").val(productRoy.royaltyCount / 100 * parseFloat($("#price").val()) * parseFloat($("#quantity").val()));
		}
		$("#productRoyal-i").text($("#productRoyal").val());
	}
}

</script>
</html>

