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
					<label class="layui-form-label">子节点名称</label>
					<div class="layui-input-inline">
						<input type="text" name="name" id="name" placeholder="请输入子节点名称" autocomplete="off"  class="layui-input">
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
            	<button class="layui-btn layui-btn-sm kxnz-btn-default addBtn" tabId="ordersTable" data-url="workflowTemplate/addnode/${templateId}.do" openw="900px;" openh="800px" id="addCustomBtn"  lay-event="addData"><i class="layui-icon">&#xe608;</i>编辑节点</button>
			</div>
            </script>
			<script type="text/html" id="barLine">
 					<%--<a class="layui-btn layui-btn-xs" lay-event="edit" data-url="workflowTemplate/updateNode.do" tabId="ordersTable" openw="700px;" openh="400px">修改</a> --%>
					 <a class="layui-btn layui-btn-xs" lay-event="edit" data-url="workflowTemplate/nodeUserList.do" tabId="ordersTable" openw="700px;" openh="400px">审核人员</a>
					 <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-url="workflowTemplate/delNode.do" tabId="ordersTable">删除</a>
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
	var　 url = "workflowTemplate/getNodeList.do";
	//初始化网格
	var tableIns = table.render({
		elem: '#ordersTable',
		url: url,
		page: true,
		method: 'post',
		totalRow:true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		done: function(res, curr, count) {
			
		},
		where: {
			"templateId": '${templateId}'
		},
		cols: [
			[
			{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center'
			}, {
				field: 'iidno',
				width: '10%',
				align: 'center',
				title: '序号'
			}, {
				field: 'name',
				width: '20%',
				align: 'center',
				title: '节点名称'
			}, {
				field: 'type',
				width: '10%',
				align: 'center',
				title: '审核类型',
				templet: function(row) {
					return getType(row);
				}
			}, {
				field: 'preNodeName',
				width: '15%',
				align: 'center',
				title: '前节点'
			}, {
				field: 'nextNodeName',
				width: '15%',
				align: 'center',
				title: '后节点'
			}, {
				field: 'remark',
				width: '15%',
				align: 'center',
				title: '备注'
			}
			, {
				field: 'opt',
				width: '15%',
				align: 'center',
				title: '详情',
				toolbar: '#barLine'
			}
			
			]
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
					"name": $.trim($("#name").val()),
					"templateId": '${templateId}'
				}
			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});

});

function getType(row){
	if(row.type == 1){ 
		return "串签";
	}else if(row.type == 2){
		return "并签";
	}else if(row.type == 3){
		return "汇签"		
	}
}

</script>

