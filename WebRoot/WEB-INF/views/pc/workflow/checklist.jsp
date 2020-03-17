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
		.showCar{
	color: #2ea9df;
	text-decoration: underline;
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
					<label class="layui-form-label">审批名称</label>
					<div class="layui-input-inline">
						<input type="text" name="name" id="name" placeholder="请输入审批名称" autocomplete="off"  class="layui-input">
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
			</div>
            </script>
			<script type="text/html" id="barLine">
					{{#  if(!d.result){ }}
						<a class="layui-btn layui-btn-xs" lay-event="edit" data-url="workflowInstance/checknode.do" tabId="ordersTable" openw="700px;" openh="400px">审批</a>
					{{#  } }} 
					 <a class="layui-btn layui-btn-xs" lay-event="edit" data-url="workflowInstance/checkUserList.do" tabId="ordersTable" openw="900px;" openh="400px">审批人员</a>
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
	var　 url = "workflowInstance/getCheckList.do";
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
		cols: [
			[
			{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center'
			}, {
				field: 'instanceName',
				width: '25%',
				align: 'center',
				title: '审批名称',
				templet: function(row) {
					return getName(row);
				}
			}, {
				field: 'name',
				width: '25%',
				align: 'center',
				title: '节点名称'
			}, {
				field: 'status',
				width: '10%',
				align: 'center',
				title: '处理状态',
				templet: function(row) {
					return getStatus(row);
				}
			}, {
				field: 'createTime',
				width: '20%',
				align: 'center',
				title: '新增日期',
				templet: function(row) {
					return formatDate(row.createTime);
				}
			}, {
				field: 'opt',
				width: '20%',
				align: 'center',
				title: '详情',
				toolbar: '#barLine',
				templet: function(row) {
					return getBarRow(row);
				}
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
					"name": $.trim($("#name").val())
				}
			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});

});


function getSlipType(row){
	if(row.instanceSlipType == 1){ 
		return "入库";
	}
}
function getStatus(row){
	if(row.status == 1){ 
		return "进行中";
	}else if(row.status == 2){
		return "已完结";
	}else if (row.status == 3){
		return "终止";
	}
}

function getBarRow(row){
	var tpl = barLine.innerHTML;
	// 如果不存在，则最后一个选中
	layui.laytpl(tpl).render(row, function(html) {
		return html;
	});
}

function getName(row){
	if(!!row.instanceSlipId){
		return "<a href='javascript:void(0);' onClick='updateOrders("+"\""+row.instanceSlipId+"\""+",1)' class='showCar'>"+row.instanceName+"</a>";
	}else{
		return row.instanceName;
	}
}


function updateOrders(slipId,kind){
	var url="repertory/editData/"+slipId+".do";
	addTempOrder(url,kind);
}

function addTempOrder(url,kind){
	layui.use(['jquery', 'element'], function(){
		var $=layui.jquery;
		var element = parent.layui.element;
		var id = '';
		var title="";
		if(kind=="1"){//采购
			id = 'openCGFrmId';
			title="采购临单";
		}else if(kind=="2"){//退货
			id = 'openTHFrmId';
			title="退货临单";
		}
		url=url+"?kind="+kind;
		element.tabDelete('tab', id);
		element.tabAdd('tab', {
			title: title,
			content: '<iframe src="' + url + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
			id: id
		});
		element.tabChange('tab', id);
	});
}

</script>

