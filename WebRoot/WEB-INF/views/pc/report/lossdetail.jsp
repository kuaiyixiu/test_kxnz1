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
    <input type="hidden" name="flag" id="flag" value="${flag }" />
	<div style="width: 98%;margin-left: 1%;">
		<div style="width: 100%;margin-top: 5px;">
			<blockquote class="layui-elem-quote">${title }</blockquote>
			<table class="layui-table" id="tableInfo" lay-filter="tableInfo"></table>
		</div>
	</div>
</body>
<script type="text/javascript">
	layui.use([ 'jquery', 'table', 'dialog', 'element' ], function() {
		var table = layui.table;
		var dialog = layui.dialog;
		var $ = layui.jquery;
		var flag = $("#flag").val();
		var url = "product/getDetailList.do";
		//采购列表
		table.render({
			elem : '#tableInfo',
			url : url,
			method : 'post',
			page: true,
			limit: 10,
			limits: [10],
			where: {"flag": flag},
			cols : [ [{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center',
			},{
				field : 'changeTime',
				title : '时间',
				align : 'center',
				width : '15%'
			},{
				field : 'changeReason',
				title : '来源/去向',
				align : 'center',
				width : '20%'
			},{
				field : 'type',
				title : '类型',
				align : 'center',
				width : '20%',
				templet : function(d) {
					var text="";
					if(d.type==1)
						text="采购入库(+"+d.count+")";
					else if(d.type==2)
						text="采购退货(-"+d.count+")";
					else if(d.type==3)
						text="订单消耗(-"+d.count+")";
					else if(d.type==4)
						text="进货单作废(-"+d.count+")";
					else if(d.type==5)
						text="退货单作废(+"+d.count+")";
					else if(d.type==6)
						text="库存调入(+"+d.count+")";
					else if(d.type==7)
						text="库存调出(-"+d.count+")";
					else if(d.type==8)
						text="库存调出作废(+"+d.count+")";
					else if(d.type==9)
						text="订单反入账/挂账(+"+d.count+")";
					return text;
				}
			},{
				field : 'beforeCount',
				title : '操作前',
				align : 'center',
				width : '10%'
			},{
				field : 'afterCount',
				title : '操作后',
				align : 'center',
				width : '10%'
			},{
				field : 'relationId',
				title : '关联单',
				align : 'center',
				width : '10%',
				templet : function(d) {
					var text="";
					if(d.type==1)
						text="进货单";
					else if(d.type==2)
						text="退货单";
					else if(d.type==3)
						text="查看订单";
					return text;
				}
			},{
				field : 'optUser',
				title : '操作员工',
				align : 'center',
				width : '15%'
			}] ]
		});
	});

</script>

</html>

