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
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<%@ include file="../base.jsp"%>
<script src="js/orders/orders.js" type="text/javascript" charset="utf-8"></script>
<title>连途</title>

<style type="text/css">
.x-table {
	min-height: 1px;
	font-size: 14px;
	margin: 0px 10px 20px 10px;
	/*background-color: #f5f7f9;*/
}

.x-table>.x-tr {
	/*border-bottom: 1px solid #EAEDEE;*/
}

.x-table>.x-tr:first-child {
	/*border-top: 1px solid #EAEDEE;*/
}

.x-tr-left {
	padding-top: 12px;
	padding-bottom: 12px;
}

.x-tr-right {
	padding-top: 12px;
	padding-bottom: 12px;
	text-align: right;
}

.x-tr {
	/*padding-left: 15px;*/
	/*padding-right: 15px;*/
	/*background-color: #f5f7f9;*/
}

.order-list {
	margin-left: -20px;
	margin-right: -20px;
}

.section {
	/*margin: 10px 10px 3px 10px;*/
}

.section-title {
	font-size: 16px;
}

.vip-category-name {
	background-repeat: no-repeat;
	background-size: 100% 100%;
	padding: 4px 14px 4px 0;
}

.order-car-card {
	color: white;
	border-radius: 4px 4px;
	padding: 9px 17px;
	background-size: 100% 100%;
	background-repeat: no-repeat;
}

.order-info-li {
	margin-bottom: 5px;
}

.choose-meal {
	margin-top: 20px;
	background: url(image/order/meal-bg.png) no-repeat;
	background-size: 100% 100%;
	padding-top: 10px;
	padding-bottom: 7px;
	padding-left: 10px;
	color: white;
}

.view-meal {
	float: right;
	margin-right: 30px;
	border: 1px solid white;
	padding: 1px 8px;
	position: relative;
    width: 50px;
    text-align: center;
	
}
.order-btn{
	color:#1E9FFF;
	cursor: pointer;
	text-decoration:underline;
}
.order-btn2{
	width: 66px;
}


button[disabled] {
    border: none !important;
    color: grey !important;
    background: gainsboro !important;
   	cursor: not-allowed;
    pointer-events:none;
}

</style>
<script type="text/javascript">
	
</script>
</head>
<body>
	<div style="width: 80%;">
		<div class="order-car-card" style="margin-left:20px;background-image: url('image/order/<c:if test="${orders.orderType eq '0' or orders.orderType eq '1' }">no-</c:if>vip-bg.png');background-repeat:no-repeat; background-size:100% 100%;border-radius: 4px 4px;padding: 9px 17px;">
			<div class="order-car-card-1 layui-row">
				<div class="layui-col-sm8">
					<c:if test="${orders.orderType eq '0' or orders.orderType eq '1' }">
						<span class="vip-category-name" style="background-image: none;color: white;">非会员 </span>
					</c:if>
					<c:if test="${orders.orderType eq '2' }">
						<img src="image/order/icon_vip.png" style="height: 20px; position: relative; top: -2px;">
						<span class="vip-category-name" style="background-image: url('//cdn.qixinginc.com/static/img/autoplus/order/strip_member.png');">到店开卡 </span>
					</c:if>
				</div>
				<div class="layui-col-sm4 text-right">
					<!---->
					<span class="wechat-noactive"> <i aria-hidden="true" class="fa fa-weixin fa-lg"></i> </span>
				</div>
			</div>
			<div id="order-car-card-info" class="order-car-card-2" style="<c:if test="${orders.orderType eq '1' or orders.orderType eq '2' }">cursor: pointer;</c:if>text-align: center;color: white; padding: 13px 0;font-size: 22px;">
				<c:choose>
					<c:when test="${orders.orderType eq '0' }">临牌</c:when>
					<c:when test="${orders.orderType eq '1' or orders.orderType eq '2' }">${orders.carNumber }</c:when>
				</c:choose>
				<span style="font-size: 14px;"> <c:if test="${orders.orderType eq '2'}">${car.ownerName}|${custom.custName}|${custom.cellphone}</c:if> </span>
			</div>
			<div class="order-car-card-3 layui-row">
				<div class="layui-col-sm6">
					<br>
				</div>
				<div class="layui-col-sm6 text-right">
					<div>
						<c:if test="${orders.orderType eq '2' }">
						${custom.custName}|${custom.cardNo}|￥ <fmt:formatNumber type="number" value="${custom.balance }" pattern="0.00"/>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	<div class="" style="margin: 20px;">
		<!-- 客户可用套餐 -->
		<c:if test="${orders.orderStatus eq '1' or orders.orderStatus eq '2' or orders.orderStatus eq '3' or orders.orderStatus eq '7' or orders.orderStatus eq '8' }">
			<c:if test="${showMeal eq '1' }">
				<div class="choose-meal" style="position: relative; cursor: pointer;">
					<span style="width: 80%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; display: inline-block;"> 可用套餐：${mealName } </span>
					<div class="view-meal">查看</div>
				</div>
			</c:if>
		</c:if>

	<div style="margin-right: 20px;">
		<!-- 订单信息 -->
		<div class="layui-row section " style="margin-bottom: 10px;">
			<div class="layui-col-sm6 section-title">订单信息</div>
		</div>

			<div class="x-table layui-row ">
				<div class="layui-col-sm12 " style="margin-bottom: 10px;">
					<div class="layui-col-sm2 ">订单编号：</div>
					<div class="layui-col-sm10">${orders.orderNo }</div>
				</div>
				<div class="layui-col-sm12 "  style="margin-bottom: 10px;">
					<div class="layui-col-sm2 ">订单状态:</div>
					<div class="layui-col-sm10">
					 <c:choose>
							<c:when test="${orders.orderStatus eq '0' }">作废</c:when>
							<c:when test="${orders.orderStatus eq '1' }">未提交</c:when>
							<c:when test="${orders.orderStatus eq '2' }">待施工</c:when>
							<c:when test="${orders.orderStatus eq '3' }">施工中</c:when>
							<c:when test="${orders.orderStatus eq '4' }">施工完成</c:when>
							<c:when test="${orders.orderStatus eq '5' }">入账</c:when>
							<c:when test="${orders.orderStatus eq '6' }">挂账</c:when>
							<c:when test="${orders.orderStatus eq '7' }">反入账</c:when>
							<c:when test="${orders.orderStatus eq '8' }">反挂账</c:when>
						</c:choose>
					</div>
				</div>
			</div>

		<!-- 产品 -->
		<div class="layui-row section" style="">
			<div class="layui-col-sm6 section-title">服务项目</div>
			<div class="layui-col-sm6 text-right">
				<c:if test="${pageType ne '4'}">
					<c:if test="${orders.orderStatus eq '1' or orders.orderStatus eq '2' or orders.orderStatus eq '3' or orders.orderStatus eq '7' or orders.orderStatus eq '8' }">
						<button class="layui-btn layui-btn-sm  layui-btn-normal" style="width: 66px;" onclick="addOrdersServe('${orders.id}','${custom.id}')">添 加</button>
					</c:if>
				</c:if>
			</div>
		</div>
		<div class="x-table" id="orderServeSection"></div>

		<!-- 服务项目 -->
		<div class="layui-row section" style="">
			<div class="layui-col-sm6 section-title">销售产品</div>
			<div class="layui-col-sm6 text-right">
				<c:if test="${pageType ne '4'}">
					<c:if test="${orders.orderStatus eq '1' or orders.orderStatus eq '2' or orders.orderStatus eq '3' or orders.orderStatus eq '7' or orders.orderStatus eq '8'}">
						<button class="layui-btn layui-btn-sm  layui-btn-normal" style="width: 66px;" onclick="addOrdersProduct('${orders.id}','${custom.id}')">添 加</button>
					</c:if>
				</c:if>
			</div>
		</div>
		<div class="x-table" id="orderProductSection"></div>

		<!-- 结算信息 -->
		<div class="layui-row section" <c:if test="${orders.orderStatus ne '4'  and orders.orderStatus ne '6' and orders.orderStatus ne '7' and orders.orderStatus ne '8' }">style="display: none;"</c:if>>
			<div class="layui-col-sm6 section-title">结算信息</div>
			<div class="layui-col-sm6 text-right">
				<c:if test="${pageType ne '4'}">
					<button class="layui-btn layui-btn-sm  layui-btn-normal" style="width: 66px;" onclick="addOrdersPay('${orders.id}')">添 加</button>
					<button <c:if test="${empty couponCount }">disabled="disabled"</c:if> class="layui-btn layui-btn-sm  layui-btn-normal choose-coupon" style="width: 80px;">代金券<c:if test="${not empty couponCount }"> x ${couponCount }</c:if><c:if test="${ empty couponCount }"> x 0</c:if></button>
					<button <c:if test="${empty cardsetCount }">disabled="disabled"</c:if> class="layui-btn layui-btn-sm  layui-btn-normal choose-cardset" style="width: 80px;">优惠券<c:if test="${not empty cardsetCount }"> x ${cardsetCount }</c:if><c:if test="${ empty cardsetCount }"> x 0</c:if></button>
				</c:if>
			</div>
		</div>
		<div class="x-table" id="orderPaySection"></div>

		<div class="text-right section" <c:if test="${orders.orderStatus eq '0' }">style="display: none;"</c:if>>
			<p id="orderAmt-i" style="color: rgb(120, 120, 120);"></p>
		</div>

		<form class="layui-form">
				<div class="layui-row " style="">
			<div class="layui-col-sm-offset6 layui-col-sm6 text-right">
				<div class="layui-btn-container  text-right ">
					<c:if test="${pageType ne '4' }">
						<!-- 编辑 -->
						<c:if test="${orders.orderStatus eq '1' or orders.orderStatus eq '7' or orders.orderStatus eq '8'}">
							<button class="layui-btn layui-btn-sm    layui-btn-normal order-btn2" lay-submit lay-filter="submit" id="submit">提交</button>
						</c:if>
						<!-- 待施工， 施工中 -->
						<c:if test="${ orders.orderStatus eq '2' or orders.orderStatus eq '3' }">
							<button class="layui-btn layui-btn-sm    layui-btn-normal order-btn2" lay-submit lay-filter="done" id="done">施工完成</button>
						</c:if>
						<!-- 施工完成 -->
						<c:if test="${orders.orderStatus eq '4'}">
							<button class="layui-btn layui-btn-sm    layui-btn-warm order-btn2" lay-submit lay-filter="debt" id="debt" style="display: none">挂 账</button>
							<button class="layui-btn layui-btn-sm   layui-btn-normal order-btn2" lay-submit lay-filter="pay" id="pay" style="display: none">入 账</button>
						</c:if>
						<!-- 挂账 -->
						<c:if test="${orders.orderStatus eq '6'}">
							<button class="layui-btn layui-btn-sm    layui-btn-warm order-btn2" lay-submit lay-filter="disDebt" id="disDebt" style="">解除挂账</button>
							<button class="layui-btn layui-btn-sm    layui-btn-warm order-btn2" lay-submit lay-filter="debt" id="debt" style="">继续挂账</button>
							<button class="layui-btn layui-btn-sm   layui-btn-normal order-btn2" lay-submit lay-filter="pay" id="pay" style="">入 账</button>
						</c:if>
						<!-- 入账 -->
						<c:if test="${orders.orderStatus eq '5'}">
							<button class="layui-btn layui-btn-sm   layui-btn-normal order-btn2" lay-submit lay-filter="disPay" id="disPay" style="">反入账</button>
						</c:if>
					</c:if>
				</div>
				
			</div>
		</div>
		
			<input type="hidden" name="orderStatus" id="orderStatus" value="${orders.orderStatus}"> <input type="hidden" name="id" id="id" value="${orders.id}">
		</form>
	</div>
	</div>
	</div>
</body>

<script type="text/javascript">
	<c:if test="${not empty car.id }">
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		layui.jquery("#order-car-card-info").click(function() {
			var option = {
				type : 2,
				title : '编辑车辆',
				area : [ '700px', '500px' ],
				fixed : false, //不固定
				content : [ 'car/editCarView.do?id=${car.id}', 'no' ],
				success : function(layero, index) {
					layer.iframeAuto(index);
				}
			};
			layer.open(option);
		});
		
		
		<c:if test="${showMeal eq '1' }">
		layui.jquery(".choose-meal").click(function() {
			showMeal(null,null,null,null);
		});
		</c:if>
		
		
		<c:if test="${not empty couponCount}">
			layui.jquery(".choose-coupon").click(function() {
				var option = {
					type : 2,
					title : '选择代金券',
					area : [ '700px', '500px' ],
					offset : 't',
					fixed : false, //不固定
					content : [ 'coupon/chooseCoupon/${car.customId}/${orders.id}.do', 'no' ],
					success : function(layero, index) {
						layer.iframeAuto(index);
					//	orderMeal = {};
					},end: function(){ 
						renderServeTpl();
					} 
				};
				layer.open(option);
			});	
		</c:if>
		
		
		<c:if test="${not empty cardsetCount}">
			layui.jquery(".choose-cardset").click(function() {
				var option = {
					type : 2,
					title : '选择优惠券',
					area : [ '500px', '500px' ],
					offset : 't',
					fixed : false, //不固定
					content : [ 'wechatcardrecord/chooseCardset/${car.customId}/${orders.id}.do', 'no' ],
					success : function(layero, index) {
						layer.iframeAuto(index);
					},end: function(){ 
						renderServeTpl();
					} 
				};
				layer.open(option);
			});	
		</c:if>
		
	});
	</c:if>

	<c:if test="${pageType eq '2' or pageType eq '3' or pageType eq '4'}">
	layui.use([ 'form', 'jquery', 'laytpl', 'form', 'element' ], function() {
		var $ = layui.jquery;
		layui.jquery.ajax({
			type : "POST",
			url : "orders/getOrdersInfo.do",
			data : {
				'id' : '${orders.id}'
			},
			dataType : 'json',
			success : function(data) {
				orderProductList = data.ordersProductList;
				orderServeList = data.ordersServeList;
				orderPayList = data.ordersPayList;
				renderServeTpl();
				renderProductTpl();
				renderPayTpl();
				calcOrderAmt();
			}
		});
	});
	</c:if>
	
	
	function showMeal(itemId,itemType,dtId,index){
		var queryStr = "";
		if(!!itemId && !!itemType && !!dtId){
			queryStr = "?itemId="+itemId+"&itemType="+itemType+"&dtId="+dtId;
		}
		var option = {
				type : 2,
				title : '选择套餐项目',
				area : [ '800px', '500px' ],
				offset : 't',
				fixed : false, //不固定
				content : [ 'orders/chooseMeal/${car.customId}/${orders.id}.do'+queryStr, 'no' ],
				success : function(layero, index) {
					layer.iframeAuto(index);
					orderMeal = {};
				},end: function(){
					if(!!orderMeal.ordersServeList && orderMeal.ordersServeList.length > 0){
						if(!!itemId && !!itemType && !!dtId){
							 orderServeList.splice(index,1);
						}
						ajaxsaveOrdersServe('${orders.id}',orderMeal.ordersServeList);
					}	
					
					if(!!orderMeal.ordersProductList && orderMeal.ordersProductList.length > 0){
						if(!!itemId && !!itemType && !!dtId){
							orderProductList.splice(index,1);
						}
						ajaxsaveOrdersProduct('${orders.id}',orderMeal.ordersProductList)
					}
				} 
			};
		layer.open(option);		
	}
	
</script>

<script id="orderServeDemo" type="text/html">
{{#  layui.each(d, function(index, item){ }}
	<div class="layui-row x-tr">
		<div class="layui-col-sm10 x-tr-left">
			<div class="layui-col-sm1" style="text-align:center;">{{index+1}}</div>
			<div class="layui-col-sm5">{{item.serveName}} 
				{{#  if(!!item.mealPayRecordId){ }}
					<span class="layui-badge" title="套餐服务" style="margin-left:10px;">套餐</span>
				{{#  } }} 			
			</div>
			<div class="layui-col-sm4">
				{{#  if(item.serveStatus == 1){ }}待施工{{#  } }} 
				{{#  if(item.serveStatus == 2){ }}施工中{{#  } }} 
				{{#  if(item.serveStatus == 3){ }}已完工{{#  } }} 
			</div>
			<div class="layui-col-sm2">{{parseFloat(item.price).toFixed(2)}}元</div>
		</div>
		<div class="layui-col-sm2  x-tr-right">
			<c:if test="${pageType ne '4' }">
			<c:if test="${orders.orderStatus eq '1' or orders.orderStatus eq '2' or orders.orderStatus eq '3' or orders.orderStatus eq '7' or orders.orderStatus eq '8' }">
            <a class="order-btn" onclick="updateOrdersServe('{{item.id}}',{{index}},'${orders.id}')" >修改</a>
            	<a class="order-btn" onclick="delServe({{index}})">删除</a>
			</c:if>
			<c:if test="${showMeal eq '1' }">
				<c:if test="${orders.orderStatus eq '1' or orders.orderStatus eq '2' or orders.orderStatus eq '3' or orders.orderStatus eq '4' or orders.orderStatus eq '7' or orders.orderStatus eq '8' }">
		            {{#  if(!item.mealPayRecordId){ }}
						<a class="order-btn" onclick="showMeal('{{item.serveId}}',2,'{{item.id}}',{{index}})" >替换套餐</a>
					{{#  } }} 			
				</c:if>
			</c:if>
			</c:if>
		</div>
	</div>
{{#  }); }}
</script>

<script id="orderProductDemo" type="text/html">
{{#  layui.each(d, function(index, item){ }}
	<div class="layui-row x-tr">
		<div class="layui-col-sm10 x-tr-left">
			<div class="layui-col-sm1" style="text-align:center;">{{index+1}}</div>
			<div class="layui-col-sm5">{{item.productName}}
				{{#  if(!!item.mealPayRecordId){ }}
					<span class="layui-badge" title="套餐产品" style="margin-left:10px;">套餐</span>
				{{#  } }} 
			</div>
			<div class="layui-col-sm4">x {{item.quantity}}</div>
			<div class="layui-col-sm2">{{parseFloat(item.quantity * item.price).toFixed(2)}}元</div>

		</div>
		<div class="layui-col-sm2  x-tr-right">
		<c:if test="${pageType ne '4' }">
			<c:if test="${orders.orderStatus eq '1' or orders.orderStatus eq '2' or orders.orderStatus eq '3' or orders.orderStatus eq '7' or orders.orderStatus eq '8'}">
            <a class="order-btn" onclick="updateOrdersProduct('{{item.id}}',{{index}},'${orders.id}')" >修改</a>
            	<a class="order-btn" onclick="delProduct({{index}})">删除</a>
			</c:if>
			<c:if test="${showMeal eq '1' }">
			<c:if test="${orders.orderStatus eq '1' or orders.orderStatus eq '2' or orders.orderStatus eq '3' or orders.orderStatus eq '4' or orders.orderStatus eq '7' or orders.orderStatus eq '8'}">
            	  {{#  if(!item.mealPayRecordId){ }}		
					<a class="order-btn" onclick="showMeal('{{item.productId}}',1,'{{item.id}}',{{index}})" >替换套餐</a>
				{{#  } }} 
			</c:if>
			</c:if>
		</c:if>
		</div>
	</div>
{{#  }); }}
</script>

<script id="orderPayDemo" type="text/html">
{{#  layui.each(d, function(index, item){ }}
	<div class="layui-row x-tr ">
		<div class="layui-col-sm10 x-tr-left">
			<div class="layui-col-sm1" style="text-align:center;">{{index+1}}</div>
			<div class="layui-col-sm5">{{item.payName}}</div>
			<div class="layui-col-sm4">{{formatDate(item.addTime)}}</div>
			<div class="layui-col-sm2">{{parseFloat(item.payAmount).toFixed(2)}}元</div>
		</div>
		<div class="layui-col-sm2  x-tr-right">
		<c:if test="${pageType ne '4' }">
		{{#  if(item.canEdit == 1){ }}
  			<a class="order-btn" onclick="updateOrdersPay('{{item.id}}',{{index}},'${orders.id}')" >修改</a>
  			<a class="order-btn" onclick="delPay({{index}})">删除</a>
		{{#  } }} 
		</c:if>
		</div>
	</div>
{{#  }); }}
</script>
</html>