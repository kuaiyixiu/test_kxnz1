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
<style type="text/css">
.showCar{
	color: #2ea9df;
	text-decoration: underline;
}
</style>	
<title>连途</title>
<%@ include file="../base.jsp"%>
<link rel="stylesheet" type="text/css" href="css/fieldset.css" />
<link rel="stylesheet" type="text/css" href="lib/admin/css/admin.css" />
<style type="text/css">
</style>
</head>
<body>
	<div class="page-content-wrap">
		<div class="layui-col-md12">
			<div class="layui-card" style="margin-top: 1%;">
				<div class="layui-card-header">汇总</div>
				<div class="layui-card-body">
					<div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade" lay-filter="LAY-index-dataview" style="width: 100%;" lay-anim="fade" lay-indicator="inside" lay-arrow="none" id="">
						<div class="layui-form" id="">
							<table class="layui-table" id="orderCountTotal" lay-filter="tableInfo"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="ordersTable" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            
            </script>
			<script type="text/html" id="barLine">
            	<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="showDetail"  tabId="ordersTable">详 情</a>
            </script>
		</div>
	</div>
</body>
</html>

<script type="text/javascript">
/**
 * 加载表格
 * @param $
 * @param table
 * @param parentId 父级id
 * @param id 当前等级id
 * @param type back返回类型
 * @returns
 */
function userDefinedLoadGrd($, table, parentId, id) {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	
	$.ajax({
		type : "post",
		async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
		url : "report/getOrderEvaluateInfo.do",
		data : {
			"startDate": '${dateRangeStartTime}',
			"endDate": '${dateRangeEndTime}',
			"stars": '${stars}'
		},
		dataType : "json", //返回数据形式为json
		success : function(result) {
			//请求成功时执行该函数内容，result即为服务器返回的json对象
			if (result) {
				//刷新汇总网格
				layui.table.render({
					elem: '#orderCountTotal',
					page : false,
					limit: '9999',
					cols: [
						[{
							field: 'addTime',
							width: '25%',
							align: 'center',
							title: '评价时间',
							templet: function(row) {
								return formatDate(row.addTime);
							}
						}, {
							field: 'custName',
							width: '25%',
							align: 'center',
							title: '客户名称'
						}, {
							field: 'stars',
							width: '25%',
							align: 'center',
							title: '评分'
						},{
							field: 'opt',
							width: '25%',
							align: 'center',
							title: '操作',
							toolbar: '#barLine',
							templet: function(row) {
								return getBarRow(row);
							}
						}
						]
					],
					data : result
				});
			}
		},
		error : function(errorMsg) {
		}
	})

}


function getBarRow(row){
		var tpl = barLine.innerHTML;
		// 如果不存在，则最后一个选中
		layui.laytpl(tpl).render(row, function(html) {
			return html;
		});
}


function userDefinedToolHandle($arg, obj) {
	var data = obj.data
	var $ = layui.jquery;
	if (obj.event === 'showDetail') { //修改订单
		showDetail(data);
	}
}


var orderFrmId = 'showOrderDetail';
function showDetail(data){
	layui.use('element', function(){
		var element = parent.layui.element;
		var id = orderFrmId;
		var url = 'orders/toUpdateOrder/'+data.orderId+'/4.do';
		element.tabDelete('tab', id);
		element.tabAdd('tab', {
			title: "订单详情",
			content: '<iframe src="' + url + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
			id: id
		});
		element.tabChange('tab', id);
	});
}



</script>

