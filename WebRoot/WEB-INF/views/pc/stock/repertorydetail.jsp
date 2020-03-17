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
<%@ include file="../base.jsp"%>
<title>连途</title>
</head>
<body>
	<input type="hidden" id="id" name="id" value="${repertory.id }" />
	<input type="hidden" name="kind" id="kind" value="${kind }" />
	<div style="width: 98%;margin-left: 1%;">
		<div style="width: 100%;margin-top: 5px;">
			<blockquote class="layui-elem-quote"><c:if test="${kind=='1' }">采购单明细</c:if><c:if test="${kind=='2' }">退货单明细</c:if> </blockquote>
			<blockquote class="layui-elem-quote layui-quote-nm"><label>供应商：${repertory.supplyName
					}</label><br><label><c:if test="${kind=='1' }">采购时间：</c:if><c:if test="${kind=='2' }">退货时间：</c:if><fmt:formatDate value="${repertory.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </label></blockquote>
					<c:if test="${kind=='1' }">
			        <table class="layui-table" id="tableInfo1" lay-filter="tableInfo1"></table>
			        </c:if>
			        <c:if test="${kind=='2' }">
			        <table class="layui-table" id="tableInfo2" lay-filter="tableInfo2"></table>
			        </c:if>
		</div>
	</div>
</body>
<script type="text/javascript">
	layui.use([ 'jquery', 'table', 'dialog', 'element' ], function() {
		var table = layui.table;
		var dialog = layui.dialog;
		var $ = layui.jquery;
		var id = $("#id").val();
		var kind=$("#kind").val();
		if(kind=="1"){
			//历史明细
			var url = "repertory/getCGHistoryList/" + id + ".do";
			table.render({
				elem : '#tableInfo1',
				url : url,
				method : 'post',
				totalRow : true,
				done : function(res, curr, count) {
				},
				cols : [ [{
					field : 'productName',
					title : '产品名称',
					align : 'center',
					width : '20%',
					totalRowText : '合计'
				}, {
					field : 'productType',
					title : '产品型号',
					align : 'center',
					width : '15%'
				}, {
					field : 'inprice',
					title : '进货单价',
					align : 'center',
					width : '15%',
					templet : function(d) {
						return parseFloat(d.inprice).toFixed(2);
					}
				}, {
					field : 'inQuantity',
					title : '进货数量',
					align : 'center',
					width : '10%',
					totalRow : true
				}, {
					field : 'sum',
					title : '进货总价',
					align : 'center',
					width : '15%',
					totalRow : true,
					templet : function(d) {
						return parseFloat(d.sum).toFixed(2);
					}
				}, {
					field : 'quantity',
					title : '剩余数量',
					align : 'center',
					width : '10%',
					totalRow : true
				}, {
					field : 'sum1',
					title : '剩余价值',
					align : 'center',
					width : '15%',
					totalRow : true,
					templet : function(d) {
						return parseFloat(d.sum1).toFixed(2);
					}
				}  ] ]
			});
		}else{
			//退货历史明细
			var url = "repertory/getTHHistoryList/" + id + ".do";
			table.render({
				elem : '#tableInfo2',
				url : url,
				method : 'post',
				totalRow : true,
				done : function(res, curr, count) {
				},
				cols : [ [{
					field : 'productName',
					title : '产品名称',
					align : 'center',
					width : '30%',
					totalRowText : '合计'
				}, {
					field : 'productType',
					title : '产品型号',
					align : 'center',
					width : '20%'
				}, {
					field : 'inprice',
					title : '退货单价',
					align : 'center',
					width : '15%',
					templet : function(d) {
						return parseFloat(d.inprice).toFixed(2);
					}
				}, {
					field : 'inQuantity',
					title : '退货数量',
					align : 'center',
					width : '15%',
					totalRow : true
				}, {
					field : 'sum',
					title : '退货总价',
					align : 'center',
					width : '20%',
					totalRow : true,
					templet : function(d) {
						return parseFloat(d.sum).toFixed(2);
					}
				} ] ]
			});
		}

	});
</script>

</html>

