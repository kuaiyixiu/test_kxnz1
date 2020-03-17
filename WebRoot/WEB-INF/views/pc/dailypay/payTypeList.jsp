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
<link rel="stylesheet" type="text/css" href="css/fieldset.css" />
<link rel="stylesheet" type="text/css" href="lib/admin/css/admin.css" />
<style type="text/css">
	.create-order {
    	background-color: #F1F1F1;
    	padding: 20px 0px;
    	border-radius: 5px 5px;
    	width: 60%;
    	margin-bottom: 20px;
	}
</style>
</head>
<body>
	<div class="page-content-wrap">
	<form class="layui-form">
		<div class="layui-form-item searchDiv">
			<fieldset>
				<legend>查询条件</legend>
				<div class="layui-inline">
					<label class="layui-form-label">分类名称</label>
					<div class="layui-input-inline">
						<input type="text" name="typeName" id="typeName" placeholder="请输入分类名称" autocomplete="off"  class="layui-input">
					</div>
				</div>
				<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
			</fieldset>
			
			
		</div>
		</form>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="ordersTable" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            <div class="layui-btn-container">
            	<button class="layui-btn layui-btn-sm addBtn" tabId="userInfo" data-url="dailyPay/toAddType.do" openw="800px;" openh="600px" lay-event="addData"><i class="layui-icon">&#xe608;</i>新增</button>
            </div>
            </script>
			<script type="text/html" id="barLine">
            	<a class="layui-btn layui-btn-xs" lay-event="edit" data-url="dailyPay/toUpdateType.do" tabId="ordersTable">修改</a>
            	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-url="dailyPay/delType.do" tabId="ordersTable">删除</a>
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
	var　 url = "dailyPay/getPayTypeList.do";
	//初始化网格
	var tableIns = table.render({
		elem: '#ordersTable',
		url: url,
		page: true,
		method: 'post',
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		//height: 'full-250',
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		where: {
			
		},
		done: function(res, curr, count) {
		
		},
		cols: [
			[{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center'
			}, {
				field: 'typeName',
				width: '70%',
				align: 'center',
				title: '分类名称'
			}, {
				field: 'opt',
				width: '30%',
				align: 'center',
				title: '操作',
				toolbar: '#barLine'
			}]
		]
	});
}


layui.use(['jquery', 'table','laydate','form'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	form.render();
	//查询事件
	var active = {
		reload: function() {
			// 执行重载
			table.reload('ordersTable', {
				method: 'post',
				page: {
					curr: 1
				},
				where: {
					"typeName": $.trim($("#typeName").val()),	
				}
			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});

});

function userDefinedToolHandle($arg, obj) {
	var data = obj.data
	var $ = layui.jquery;
	if (obj.event === 'detail') { //修改订单
		showDetail(data);
	}
}


var orderFrmId = 'orderFrm';
function showDetail(data){
	if (data.kind == 1){ //客户订单支付
		layui.use('element', function(){
			var element = parent.layui.element;
			var id = orderFrmId;
			var url = 'orders/toUpdateOrder/'+data.sourceId+'/4.do';
			element.tabDelete('tab', id);
			element.tabAdd('tab', {
				title: "订单详情",
				content: '<iframe src="' + url + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
				id: id
			});
			element.tabChange('tab', id);
		});		
	}
}


</script>

