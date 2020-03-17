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
<%@ include file="../base.jsp"%>
<script type="text/javascript" src="js/sysmanage/custom/customUtil.js"></script>

</head>
<style>
.layui-table-cell {
    height: auto;
    line-height: 28px;
}
</style>
<body>
	<form id="customForm" style="margin-top: 1%;">
    <div class="order-car-card">
      <div class="order-car-card-1 row">
        <div  id="shopDetail"></div>
        <div class="col-sm-4 text-right">
          <div class="wechat-active">
            <i aria-hidden="true" class="fa fa-weixin fa-lg"></i>
          </div>
        </div>
      </div>
      <div class="order-car-card-2 text-center">
        <span style="font-size: 26px;">${custom.cardNo}</span>
        <span style="font-size: 18px; color: rgb(255, 244, 0);">￥${custom.balance}</span></div>
      <div class="order-car-card-3 row">
        <div class="col-sm-4"></div>
        <div class="col-sm-8 text-right">${custom.custName} | <c:if test="${custom.sex==1}">女</c:if><c:if test="${custom.sex==0}">男</c:if> | ${custom.cellphone} | ${custom.score}(积分)
          <a href="/vip/recharge-list/A0484/" target="blank" style="color: rgb(255, 244, 0); display: none; text-decoration: none;">充值记录</a>
          <a href="/vip/consume-list/A0484/" target="blank" style="color: rgb(255, 244, 0); display: none;text-decoration: none;">消费记录</a></div>
      </div>
    </div>
    <div class="cardDetail">
	    <div class="detailTitle">
	    	<h3>绑定车辆</h3>
		</div>
		<ul class="carList" id="carList"></ul>
		
		<div class="detailTitle">
			<h3>已购套餐</h3>
		</div>
		<span id="total" class="totalSpan">总价格为：</span>
		<table class="layui-table customTable" id="customMealInfo" lay-filter="tableInfo"></table>
		
		<div class="closeButton">
			<a class="layui-btn closeBtn layui-btn-normal">关闭</a>
		</div>
	</div>
	<input type="hidden" id="customDetailType" value='${custom.custType}'>
	<input type="hidden" id="customDetailShopId" value='${custom.shopId}'>    	
	

	</form>
	<input type="hidden" id="customId" value='${custom.id}'>
	
</body>
<script id="carLiTpl" type="text/html">
	{{#  layui.each(d.list, function(index, item){ }}
    	<li><div class="carData">{{ item.carNumber }} {{ item.carBrand || ""}}</div></li>
	{{#  }); }} 
  	{{#  if(d.list.length === 0){ }}
    		暂无车辆信息
  	{{#  } }} 
</script>

<script type="text/html" id="contentTpl">
	<dd>{{ d.mealDtName }} x {{ d.quantity}}</dd>
</script>

<script type="text/javascript">
function userDefinedLoadGrd($, table) {
	var tableIns = table.render({
		elem: '#customMealInfo',
		method:'post',
		url: "custom/queryCustomMeal.do",
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: '250px',
		defaultToolbar: ['print', 'exports'],
		cols: [
			[{
				field: 'name',
				width: '10%',
				align: 'center',
				title: '套餐名'
			}, {
				field: 'createDate',
				width: '35%',
				align: 'center',
				title: '有效时间',
				templet: function(row) {
					return getValidDate(row);
				}
			}, {
				field: 'createDate',
				//width: '20%',
				align: 'center',
				title: '内容',
				templet: function(row) {
					return getContent(row);
				}
			}]
		],where: {
			"custId": $("#customId").val()
		},done : function(res, curr, count){
			drawTotalHtml(res);
		}
		
	});
	
	$("#tools").on('click', "[data-id='toolBtn']", function(){
		toolsActive($(this));
	});
}


	var car = '${car}';
	car = JSON.parse(car);
	var data = {
		list: car
	};
	layui.use(['jquery', 'laytpl'], function() {
		var $ = layui.jquery;
		var tpl = carLiTpl.innerHTML;
		var laytpl = layui.laytpl;
		
		laytpl(tpl).render(data, function(html) {
			$("#carList").empty();
			$("#carList").append(html);
		});
		
		// 类型
		var type = $("#customDetailType").val();
		var customTypesMap = formatCustomType();
		var typeName = customTypesMap[type];
		
		// 门店 
		var shopId = $("#customDetailShopId").val();
		var mapJson = window.parent.document.getElementById("shopListMap").value;
		var shopList = eval("("+mapJson+")");
		var shopName = shopList[shopId];
		shopName = shopName? shopName:"";
		
		// 门店详情
		$("#shopDetail").html(shopName+" | "+typeName); 		
	});
	
	function formatCustomType() {
		var $ = layui.jquery;
		var customTypes = window.parent.document.getElementById("customTypes").value; 
		customTypes = JSON.parse(customTypes);

		var customTypesMap = {};
		$.each(customTypes, function(index, item) {
			customTypesMap[item.id] = item.typeName;
		})

		return customTypesMap;
	} 
	
</script>
</html>



