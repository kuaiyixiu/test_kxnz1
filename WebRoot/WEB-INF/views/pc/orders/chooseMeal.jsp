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
<%@ include file="../base.jsp"%>
<style type="text/css">
.custmeal{margin-bottom: 10px;padding-bottom: 20px;background: #F6F8FA}
.meal-title{background: #1E9FFF; height: 35px; position: relative; padding-left: 30px;}
.meal-name{position: relative;display: inline-block;transform: translate(0, -50%);top: 50%;color: white;font-size: 16px;}
.meal-end-date{position: absolute;display: inline-block; transform: translate(0, -50%); top: 50%; color: #B5D2FE;}
.meal-content{padding: 15px 30px 0;}
.meal-dt{margin-bottom: 5px;position: relative;}
.meal-dt-name{padding: 4px 10px;color: white;width: fit-content;display: inline-block;border-radius: 20px;position: relative;background-color: #1E9FFF;}
.meal-dt-line{display: inline-block; height: 1px; width: 86%; background: #C0C4C8;position: absolute; transform: translate(0, -50%);top: 50%;left: 13%;}
.meal-item{position: relative;height: 30px;padding-left: 20px;line-height: 30px;margin: 0 40px;border-bottom: 1px dashed gray;}
.input-number{ position: absolute;  transform: translate(0, -50%); top: 50%;right: 5%;display: inline-block;background: grey;}
.calc { height: 22px; line-height: 22px; width: 24px; cursor: pointer;display: inline-block; background: #F1F2F6;
    border: 1px solid #C0C4C8; box-sizing: content-box; font-size: 14px; float: left;  text-align: center; }
.sub{border-right: none;}
.add{border-left: none;}    
.number{height: 22px;line-height: 22px; width: 40px; display: inline-block;background: #F1F2F6; border: 1px solid #C0C4C8;
    	box-sizing: content-box; font-size: 14px; float: left;text-align: center;}
.number span{position: relative;top: 1px;color: #1E9FFF;}
.total-bar { position: relative;  height: 38px;background: #DFE1E6;}
.total-number{position: absolute;transform: translate(0, -50%);top: 50%;left: 3%;}
</style>

</head>
<body>
	<div style="padding: 15px;position: relative;">
		<c:forEach items="${customMeals }" var="meal">
			<div class="custmeal">
				<div class="meal-title">
					<span class="meal-name">${meal.name}</span> <span class="meal-end-date">（过期时间: <fmt:formatDate value="${meal.endDate }" pattern="yyyy-MM-dd"/>） </span>
					<c:if test="${meal.shopId ne shopsSession.id}">
						<span class="meal-end-date layui-badge" title="跨店套餐" style="margin-left: 200px;color: white;">跨店套餐</span>
					</c:if>
				</div>
				<div class="meal-content">
					<div class="meal-dt">
						<div class="meal-dt-name">已购服务</div>
						<div class="meal-dt-line"></div>
					</div>
					<div class="meal-items">
						<c:forEach  items="${meal.serveMealDt }" var="dt"  varStatus="status">
							<div class="meal-item" 	<c:if test="${status.last}"> style="border-bottom: none;"</c:if>>
								${dt.mealDtName } <span style="font-size: 12px;">＊</span> ${dt.quantity}
								<div class="input-number">
									<div class="calc sub">
										<span onclick="calcQuantity('sub','${dt.id}')">－</span>
									</div>
									<div class="number">
										<span id="quantity${dt.id}" dtId="${dt.id}" store="${dt.quantity}" itemId="${dt.itemId}" type="2" >0</span>
									</div>
									<div class="calc add">
										<span onclick="calcQuantity('add','${dt.id}')">+</span>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="meal-content">
					<div class="meal-dt">
						<div class="meal-dt-name">已购产品</div>
						<div class="meal-dt-line"></div>
					</div>
					<div class="meal-items">
						<c:forEach  items="${meal.productMealDts }" var="dt"  varStatus="status">
							<div class="meal-item" 	<c:if test="${status.last}"> style="border-bottom: none;"</c:if>>
								${dt.mealDtName } <span style="font-size: 12px;">＊</span> ${dt.quantity }
								<div class="input-number">
									<div class="calc sub"  onclick="calcQuantity('sub','${dt.id}')">
										<span>－</span>
									</div>
									<div class="number">
										<span id="quantity${dt.id}" dtId="${dt.id}" store="${dt.quantity}" itemId="${dt.itemId}" type="1">0</span>
									</div>
									<div class="calc add" onclick="calcQuantity('add','${dt.id}')">
										<span >+</span>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:forEach>
	
	

	
		
		<div  class="total-bar"><div class="total-number">  选择项目合计：<i id="total-number" style="font-weight: bold;">0</i> 项</div> 
		<button class="layui-btn layui-btn-normal" style="float: right;" onclick="submitMeal();">确定</button>
		</div>
		
	</div>
</body>
<script type="text/javascript">
var order = {};
function calcQuantity(btnType,id) {
	layui.use([ 'jquery'], function() {
		var $ = layui.jquery;
	    if (btnType == 'add') {
	    	if (parseInt($('#quantity'+id).attr("store")) > parseInt($('#quantity'+id).html())){
		        $('#quantity'+id).html(parseInt($('#quantity'+id).html()) + 1);
		        $('#total-number').html(parseInt($('#total-number').html()) + 1);
		        
	    	}
	    } else if (btnType == 'sub') {
	    	if (parseInt($('#quantity'+id).html()) > 0 ){
	        	$('#quantity'+id).html(parseInt($('#quantity'+id).html()) - 1)
		        $('#total-number').html(parseInt($('#total-number').html()) - 1);
	    	}
	    }
	});
}

function submitMeal(){
	layui.use([ 'jquery'], function() {
		 var $ = layui.jquery;
		var chooseMealInfo = "";
		var valid = true;
		 $("[id^=quantity]").each(function(){
			if (checkPositiveFloat($(this).text())){
				var dtId = $(this).attr("dtId");
				var itemId = $(this).attr("itemId");
				var type = $(this).attr("type");
				var quantity = $(this).text();
				<c:if test="${not empty itemId}">
					if('${itemType}'!= type || '${itemId}' != itemId){
						valid = false;
					}
				</c:if>
				chooseMealInfo += dtId +"#"+itemId+"#"+quantity+",";
			}
		 });
		 if(!valid){
			 layer.msg("请选择与订单相同产品或服务", {icon: 2});
			 return false;	
		 }
		 
		 if(chooseMealInfo != ''){
			$.ajax({
				type: "POST",
				url: "orders/submitMeal.do",
				data: {'chooseMealInfo':chooseMealInfo,'customId':'${customId}','orderId':'${orderId}','itemType':'${itemType}','dtId':'${dtId}'},
				dataType: "json",
				success: function(result) {
					if (result.retCode == 'success') {
						parent.orderMeal = result.retData;
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭当前页  
						
					} else {
						layer.msg(result.retMsg, {
							icon: 2
						});
					}
				}
			});
		 }else{
			 layer.msg("请选择套餐", {icon: 2});
			 return false;	
		 }
		
		
	});
	
	
	
}


</script>

</html>

