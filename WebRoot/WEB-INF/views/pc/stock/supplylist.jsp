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
<link rel="stylesheet" type="text/css" href="lib/admin/css/admin.css" />
<%@ include file="../base.jsp"%>
</head>
<body>
	<div class="page-content-wrap">
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<div class="layui-inline">
						<label class="layui-form-label">供应商</label>
						<div class="layui-input-inline">
							<input type="text" name="supplyName" id="supplyName"
								autocomplete="off" class="layui-input" />
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">供应商</label>
						<div class="layui-input-inline">
							<select name="enabled" id="enabled">
								<option value="">全部</option>
								<option value="1">启用</option>
								<option value="0">禁用</option>
							</select>
						</div>
					</div>
					<button class="layui-btn layui-btn-normal" data-type="reload"
						lay-filter="searchBtn" type="button">搜索</button>
				</fieldset>

			</div>
		</form>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="supplyInfo" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm addBtn" tabId="supplyInfo" data-url="supply/addData.do" openw="700px;" openh="400px" lay-event="addData"><i class="layui-icon">&#xe608;</i>新增</button>
            <button class="layui-btn layui-btn-sm delBtn" tabId="supplyInfo" data-url="supply/delData.do" lay-event="delData"><i class="layui-icon">&#xe640;</i>删除</button>
            </div>
            </script>
			<script type="text/html" id="barLine">
            <a class="layui-btn layui-btn-xs" lay-event="edit" data-url="supply/editData.do" tabId="supplyInfo" openw="700px;" openh="400px">修改</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-url="supply/delData.do" tabId="supplyInfo">删除</a>
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
	var　 url = "supply/getList.do";
	//初始化网格
	var tableIns = table.render({
		elem: '#supplyInfo',
		url: url,
		page: true,
		method:'post',
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		//height: 'full-250',
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		done: function(res, curr, count) {},
		cols: [
			[{
				type: 'checkbox',
				fixed: 'left',
				width: '5%'
			},{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center',
			}, {
				field: 'supplyName',
				width: '10%',
				align: 'center',
				title: '供应商名称'
			}, {
				field: 'contactName',
				width: '10%',
				align: 'center',
				title: '联系人'
			},{
				field: 'cellphone',
				width: '10%',
				align: 'center',
				title: '手机号'
			},{
				field: 'telephone',
				width: '10%',
				align: 'center',
				title: '座机号'
			},{
				field: 'address',
				width: '20%',
				align: 'center',
				title: '地址'
			},{
				field: 'enabled',
				width: '10%',
				align: 'center',
				title: '状态',
				templet: function(d){
					if(d.enabled==1)
						return '启用';
					else
						return '禁用';
				}
			},{
				field: 'remark',
				width: '10%',
				align: 'center',
				title: '备注'
			},{
				field: 'opt',
				width: '15%',
				align: 'center',
				title: '操作',
				templet: function(row) {
					return getOptHtml(row);
				}
				//toolbar: '#barLine'
			}]
		]
	});
}

function getOptHtml(row){
	var barLineStr=barLine.innerHTML;
	if(row.candel==1)
		return barLineStr;
	else
		return "";
		
}


layui.use(['jquery', 'table', 'form'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	form.render();
	//查询事件
	var active = {
		reload: function() {
			// 执行重载
			table.reload('supplyInfo', {
				page: {
					curr: 1
					// 重新从第 1 页开始
				},
				where: {
					"productName": $.trim($("#productName").val()),
					"enabled":$("#enabled").val()
				}
			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});

});

</script>

