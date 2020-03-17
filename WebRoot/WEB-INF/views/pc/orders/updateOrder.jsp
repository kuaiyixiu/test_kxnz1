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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"  content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<%@ include file="../base.jsp"%>
<script src="js/orders/orders.js" type="text/javascript"charset="utf-8"></script>
<title>连途</title>

<style type="text/css">
.x-table {
  min-height: 1px;
  font-size: 14px;
  margin: 0px 20px 20px 20px;
  background-color: #f5f7f9; }


.x-table > .x-tr {
  border-bottom: 1px solid #EAEDEE; }

.x-table > .x-tr:first-child {
  border-top: 1px solid #EAEDEE; }

.x-tr-left {
  padding-top: 12px;
  padding-bottom: 12px; }

.x-tr-right {
  padding-top: 8px; 
  text-align: right;
  }

.x-tr {
	padding-left:15px;
	padding-right:15px;
   background-color: #f5f7f9; }

.order-list {
    margin-left: -20px;
    margin-right: -20px;
}
.section{margin: 10px 10px 3px 10px;}
.section-title{font-size: 16px;}

</style>
<script type="text/javascript">
</script>
</head>
<body>
	<div class="order-car-card"  style="width:70%;margin-left:20px; 
	background-image: url(&quot;//cdn.qixinginc.com/static/img/autoplus/order/no_vip_bg.png&quot;);background-repeat:no-repeat; background-size:100% 100%;;border-radius: 4px 4px;padding: 9px 17px;" >
		<div class="order-car-card-1 layui-row">
			<div class="layui-col-sm8">
				<span class="vip-category-name" style="background-image: none;color: white;">非会员 </span>
			</div>
			<div class="layui-col-sm4 text-right">
				<!---->
				<span class="wechat-noactive">
					<i aria-hidden="true"  class="fa fa-weixin fa-lg"></i>
				</span>
			</div>
		</div>
		<div class="order-car-card-2" style="cursor: pointer;text-align: center;color: white; padding: 20px 0;font-size: 22px;">
			临牌 <span style="font-size: 14px;"> </span>
		</div>
		<div class="order-car-card-3 layui-row">
			<div class="layui-col-sm6">
			</div>
			<div class="layui-col-sm6 text-right">
				<div>
					<br>
				</div>
			</div>
		</div>
	</div>
	<div class="" style="margin: 20px;width: 70%;">
		<!-- 产品 -->
		<div class="layui-row section" style="">
				<div class="layui-col-sm6 section-title">服务项目 </div>
				<div class="layui-col-sm6 text-right"><button class="layui-btn layui-btn-sm layui-btn-radius layui-btn-normal" onclick="addOrdersServe('${orders.id}')">添 加</button> </div>
		</div>
		<div class="x-table" id="orderServeSection">
		</div>
		
		<!-- 服务项目 -->
		<div class="layui-row section" style="">
				<div class="layui-col-sm6 section-title">销售产品 </div>
				<div class="layui-col-sm6 text-right" ><button class="layui-btn layui-btn-sm layui-btn-radius layui-btn-normal"  onclick="addOrdersProduct('${orders.id}')">添 加</button> </div>
		</div>
		<div class="x-table" id="orderProductSection">

		</div>
		<!-- 结算信息 -->
		<div class="layui-row section" style="">
			<div class="layui-col-sm6 section-title">结算信息 </div>
			<div class="layui-col-sm6 text-right"><button class="layui-btn layui-btn-sm layui-btn-radius layui-btn-normal" onclick="addOrdersPay('${orders.id}')">添 加</button> </div>
		</div>
		<div class="x-table" id="orderPaySection">

		</div>
		<div class="text-right section" style="color: rgb(120, 120, 120);"><p>应付总额：108.00，已完成支付，可入账 </p></div>
		<div class="layui-btn-container  text-right section">
	 		<button class="layui-btn layui-btn-lg  layui-btn-radius  layui-btn-warm">挂 账</button>
	 		<button class="layui-btn layui-btn-lg  layui-btn-radius layui-btn-normal">入 账</button>
	 	</div>
	
	</div>
	<input type="hidden" name="orderId" id="orderId" value="${orders.id}">
</body>

<script type="text/javascript">
layui.use(['form','jquery','laytpl', 'form', 'element'], function() {
	var $ = layui.jquery;
	layui.jquery.ajax({
		type : "POST",
		url : "orders/getOrdersInfo.do",
		data:{'id':'${orders.id}'},
		dataType : 'json',
		success : function(data) {
			orderProductList = data.ordersProductList;
			orderServeList = data.ordersServeList;
			orderPayList = data.ordersPayList;
			renderServeTpl();
			renderProductTpl();
			renderPayTpl();
			
		}
	});
});

</script>

<script id="orderServeDemo" type="text/html">
{{#  layui.each(d, function(index, item){ }}
	<div class="layui-row x-tr order-list">
		<div class="layui-col-sm10 x-tr-left">
			<div class="layui-col-sm1">{{index+1}}</div>
			<div class="layui-col-sm5">{{item.serveName}}</div>
			<div class="layui-col-sm4">待完工</div>
			<div class="layui-col-sm2">{{item.price}}元</div>
		</div>
		<div class="layui-col-sm2  x-tr-right">
            <a class="layui-btn layui-btn-xs  layui-btn-normal" onclick="updateOrdersServe('{{item.id}}',{{index}},'${orders.id}')" >修改</a>
            <a class="layui-btn layui-btn-xs  layui-btn-normal" onclick="delServe({{index}},'${orders.id}')">删除</a>
		</div>
	</div>
{{#  }); }}
</script>

<script id="orderProductDemo" type="text/html">
{{#  layui.each(d, function(index, item){ }}
	<div class="layui-row x-tr order-list">
		<div class="layui-col-sm10 x-tr-left">
			<div class="layui-col-sm1">{{index+1}}</div>
			<div class="layui-col-sm5">{{item.productName}}</div>
			<div class="layui-col-sm4">x {{item.quantity}}</div>
			<div class="layui-col-sm2">{{item.price}}元</div>

		</div>
		<div class="layui-col-sm2  x-tr-right">
            <a class="layui-btn layui-btn-xs  layui-btn-normal" onclick="updateOrdersProduct('{{item.id}}',{{index}},'${orders.id}')" >修改</a>
            <a class="layui-btn layui-btn-xs  layui-btn-normal" onclick="delProduct({{index}},'${orders.id}')">删除</a>
		</div>
	</div>
{{#  }); }}
</script>

<script id="orderPayDemo" type="text/html">


{{#  layui.each(d, function(index, item){ }}
	<div class="layui-row x-tr order-list">
		<div class="layui-col-sm10 x-tr-left">
			<div class="layui-col-sm1">{{index+1}}</div>
			<div class="layui-col-sm5">{{item.payName}}</div>
			<div class="layui-col-sm4">{{formatDate(item.addTime)}}</div>
			<div class="layui-col-sm2">{{item.payAmount}}元</div>
		</div>
		<div class="layui-col-sm2  x-tr-right">
            <a class="layui-btn layui-btn-xs  layui-btn-normal" onclick="updateOrdersPay('{{item.id}}',{{index}},'${orders.id}')" >修改</a>
            <a class="layui-btn layui-btn-xs  layui-btn-normal" onclick="delPay({{index}})">删除</a>
		</div>
	</div>
{{#  }); }}
</script>


</html>

