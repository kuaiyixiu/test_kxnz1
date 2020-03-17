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
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>连途</title>
<%@ include file="../base.jsp"%>
<style type="text/css">
.x-table{margin: 0px 20px;}
.x-tr-left {padding-top: 10px; padding-bottom: 10px;}
.x-tr-right {padding-top: 10px;}
.section-title{margin-left: 5px;}
.selectUser{display: block!important;height: 34px; line-height: 34px;width: 100%;}
.td-user{padding-left: 3px!important;padding-right: 3px!important;}
</style>
<script type="text/javascript">
	
</script>
</head>
<body>
	<form class="layui-form"  style="width: 95%;height: 98%;margin-top: 1%;">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">选择产品</label>
				<div class="layui-input-block" style="width: 400px;">
					<select name="serviceId"  lay-search  lay-filter="chooseServe" id="serviceId">
				        <option value="">请输入名称或者名称简写</option>
			      </select>
				</div>
			</div>
		</div>
		
		<div class="layui-row section" style="">
			<div class="layui-col-xs6 section-title">已选择产品： </div>
		</div>
		<table class="layui-table">
		  <colgroup>
		    <col width="10%">
		    <col width="28%">
		    <col width="15%">
		    <col width="15%">
		    <col width="12%">
		    <col width="10%">
		    <col width="10%">
		    <col>
		  </colgroup>
		  <thead>
		    <tr>
		      <th>序号</th>
		      <th>产品名称</th>
		      <th>单价</th>
		      <th>数量</th>
		      <th>销售人员</th>
		      <th>提成价格</th>
		      <th>操作</th>
		    </tr> 
		  </thead>
		  <tbody  id="serveSection">
		  </tbody>
		</table>
		
		</form>
</body>
<script type="text/javascript">
var productList = new Array();
layui.use(['form','jquery','laytpl', 'form', 'element'], function(){
	var $ = layui.jquery;
	var serviceId  = $("#serviceId");
	var dataList = new Array();
	$.ajax({
		type : "POST",
		url : "product/getProductCustomPriceList.do",
		data: {'custId':'${custId}'},
		dataType : "json",
		success : function(data) {
			dataList = data;
			$(dataList).each(function(){
				serviceId.append("<option value='"+this.id+"'>"+this.productName+"</option>");
			});
			layui.form.render('select'); 
		}
	});
	
	
	layui.form.on('select(chooseServe)', function(data){
		if (data.elem.selectedIndex >0){
			var productData = dataList[data.elem.selectedIndex - 1];
			var ordersProduct = {};
			ordersProduct.price = productData.price;
			ordersProduct.productName = productData.productName;
			ordersProduct.shopId = productData.shopId;
			ordersProduct.productId = productData.id;
			ordersProduct.quantity = 1; //默认数量 1
			ordersProduct.productRoyal = 0;
			ordersProduct.isCust =  productData.isCust; //1是会员价
			productList.push(ordersProduct);
			renderServeTpl();
		   var index = parent.layer.getFrameIndex(window.name);
		   parent.layer.iframeAuto(index)
		}
	});	
});

function delProduct(index){
	productList.splice(index,1);
	renderServeTpl();
}


//渲染模版
function renderServeTpl(){
	var getTpl = demo.innerHTML;
	var serveSection = document.getElementById('serveSection');
	layui.laytpl(getTpl).render(productList, function(html){
		serveSection.innerHTML =  html;
	});
}

function chooseUser(obj,index){
	if (obj.selectedIndex > 0 ){
		productList[index]['productUser'] = obj.value;
		calcRoyal(productList[index],'3');
	}else{
		productList[index]['productUser'] = '';
		productList[index].productRoyal = 0;
	}
	
	renderServeTpl();
}

function calcRoyal(serveData,kind){ //计算提成
	var royal;
	layui.jquery.ajax({
		type : "POST",
		async:false, 	
		dataType : "json",
		url : "royalty/getByRoyaltyId.do",
		data : {'royaltyId':serveData.productId,'kind':kind},
		success : function(data) {
			serveData.productRoyal = 0;
			if (!!data){
				royal = data;	
				if( royal.royaltyCount > 0 ){
					if (royal.royaltyType == 1){ //固定值
						serveData.productRoyal = royal.royaltyCount * serveData.quantity;
					}else{ //百分比
						serveData.productRoyal = royal.royaltyCount / 100 * serveData.price * serveData.quantity;
					}
				}
			}
		}
	});
	return royal;	
}

function changeQuantity(obj,index){
	var $ = layui.jquery;
	productList[index]['quantity'] = $(obj).val();
	if (!!productList[index]['productUser']){
		calcRoyal(productList[index],'3');
		renderServeTpl();
	}
}

</script>

<script id="demo" type="text/html">
{{#  layui.each(d, function(index, item){ }}
    <tr>
      <td>{{index+1 }}</td>
      <td>{{ item.productName }}</td>
      <td>{{ item.price }}元
		{{#  if(item.isCust == 1){ }}
					<span class="layui-badge" title="会员价">会员价</span>
		{{#  } }} 
		</td>
      <td> <input type="text"  onchange="changeQuantity(this,{{index}})"   placeholder="请输入数量" value="{{item.quantity}}"  class="layui-input"></td>
      <td class="td-user"><select  class="selectUser" onchange="chooseUser(this,{{index}})">${productUserStr} </select></td>
      <td>{{ item.productRoyal }}元</td>
      <td><a class="layui-btn layui-btn-xs  layui-btn-normal" onclick="delProduct({{index}})">删除</a></td>
    </tr>
{{#  }); }}
</script>
</html>

