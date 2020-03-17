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
<%@ include file="../../base.jsp"%>

</head>
<body>
	<form id="customForm" style="margin-top: 1%;">
    <div class="cardDetail">
		<div class="detailTitle">
	    	<h3>基本信息</h3>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">套餐名称</label>
				<label class="layui-form-label">${meal.name}</label>
			</div>
			<div class="layui-inline">
			    <label class="layui-form-label">所属门店</label>
				<label class="layui-form-label">${meal.shopName}</label>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">有效天数</label>
				<label class="layui-form-label">${meal.day}</label>
			</div>
			<div class="layui-inline">
			    <label class="layui-form-label">套餐售价</label>
				<label class="layui-form-label">${meal.amount} <s>原价:${meal.originalPrice}</s></label>
			</div>
			
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">套餐备注</label>
					<div class="layui-input-block">
						<label class="layui-form-label">${meal.remark}</label>
					</div>
			</div>
		</div>
		
	    <div class="detailTitle">
	    	<h3>套餐明细</h3>
		</div>
		<table class="layui-table" id="mealDtInfo" lay-filter="tableInfo">
		  <colgroup>
		    <col width="150">
		    <col width="200">
		    <col width="200">
		    <col>
		  </colgroup>
		  <thead>
		    <tr>
		      <th lay-data="{field:'name'}">服务名</th>
		      <th lay-data="{field:'name'}">服务类型</th>
		      <th lay-data="{field:'price'}">单价</th>
		      <th lay-data="{field:'count'}">次数</th>
		    </tr> 
		  </thead>
		  <tbody>
		  	<c:forEach items="${meal.mealDts}" var="item">
				<tr>
			      <th>${item.mealDtName}</th>
			      <th>${item.typeName}</th>
			      <th>${item.price}</th>
			      <th>${item.quantity}</th>
				</tr>  
    		</c:forEach>
		  </tbody>
		</table>
		
		<div class="closeButton">
			<a class="layui-btn closeBtn layui-btn-normal">关闭</a>
		</div>
	</div>
	<input type="hidden" id="customDetailType" value='${custom.custType}'>
	<input type="hidden" id="customDetailShopId" value='${custom.shopId}'>    	
	

	</form>
	<input type="hidden" id="customId" value='${custom.id}'>
	
</body>

<script type="text/javascript">
/* function userDefinedLoadGrd($, table) {
	var tableIns = table.render({
		elem: '#customMealInfo',
		method:'post',
		url: "custom/queryCustomMeal.do",
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: '200px',
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
	 */
</script>
</html>



