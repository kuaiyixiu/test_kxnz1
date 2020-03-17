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
<%@ include file="../base.jsp"%>
<title>连途</title>
<style type="text/css">
.layui-table-tool-temp {
	padding-right: 0px;
}
</style>
</head>
<body>
	<form class="layui-form" action="" id="form"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<input type="hidden" name="id" id="id" value="${product.id }" />
		<input type="hidden" name="shopName" id="shopName" value="${shops.shopName}" />
		<input type="hidden" name="shopId" id="shopId" value="${shops.id}" />
		<div style="width: 98%;margin-left: 1%;">
			<div style="width: 100%;margin-top: 5px;">
				<blockquote class="layui-elem-quote">库存调拨-${product.productName}</blockquote>
				<div style="width: 96%;margin-left: 2%;">
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label"
								style="width: 500px;text-align: left;">调出门店：${shops.shopName
								}</label>
						</div>
						<div style="width: 500px;float: right;">
							<label class="layui-form-label">调入门店：</label>
							<div class="layui-input-block">
								<select name="relationShop" id="relationShop" lay-verify="required">
									<c:forEach items="${shopList }" var="shop">
										<c:if test="${shop.id !=shops.id }">
											<option value="${shop.id }">${shop.shopName}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<table class="layui-table" id="productInfo" lay-filter="tableInfo"></table>
				</div>
			</div>
		</div>
	</form>
</body>
<script type="text/javascript">
function userDefinedLoadGrd($, table, parentId, id) {
	var　 url = "product/getAllocateList.do";
	//初始化网格
	var tableIns = table.render({
		elem: '#productInfo',
		url: url,
		page: false,
		method:'post',
		where:{"productId":$("#id").val(),"available":1},
		cols: [
			[{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center',
			}, {
				field: 'addTime',
				width: '20%',
				align: 'center',
				title: '入库时间'
			}, {
				field: 'supplyName',
				width: '20%',
				align: 'center',
				title: '供应商'
			},{
				field: 'price',
				width: '15%',
				align: 'center',
				title: '单价'
			},{
				field: 'quantity',
				width: '15%',
				align: 'center',
				title: '剩余数量'
			},{
				field: 'allocation',
				width: '30%',
				align: 'center',
				title: '调拨数量',
				edit: 'text'
			}]
		]
	});
}
	layui.use([ 'jquery', 'table', 'dialog', 'element', 'form' ], function() {
		var table = layui.table;
		var dialog = layui.dialog;
		var $ = layui.jquery;
		table.on('edit(tableInfo)', function (obj) {
		      var value = obj.value;
		      var field = obj.field;
		      var objData=obj.data;
		      var inputElem = $(this);
		      var tdElem = inputElem.closest('td');
		      var valueOld = inputElem.prev().text();
		      var data = {};
		      var errMsg = ''; // 错误信息
		      if (field === 'allocation') {
		        // 调拨数量
		        var ival = parseInt(value); 
		        if(isNaN(ival)){
		        	errMsg = '调拨数量只能输入整数';
		        }else if (ival <= 0) {
		          errMsg = '调拨数量不能小于1';
		        }else if(ival>objData.quantity){
		          errMsg = '调拨数量不能大于剩余数量';
		        }
		      }

		      if (errMsg) {
		        // 如果不满足的时候
		        data[field] = valueOld;
		        layer.msg(errMsg, {time: 1000, anim: 6, shade: 0.01}, function () {
		          inputElem.blur();
		          obj.update(data);
		          tdElem.click();
		        });
		      }
		    });
		

		
	});
	function getData(){
		var data=layui.table.cache.productInfo;
		var arr=new Array();
		for(var i=0;i<data.length;i++){
			if(data[i].allocation){
				var object=new Object();
				object.id=data[i].id;
				object.allocation=data[i].allocation;
				arr.push(object);
			}
		}
		return arr;
	}
</script>

</html>

