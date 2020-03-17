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
			      <label class="layui-form-label">发送日期</label>
			      <div class="layui-input-inline">
			        <input type="text" class="layui-input" id="dateInput" placeholder=" - " lay-key="7">
			      </div>
			    </div>
				<div class="layui-inline">
					<label class="layui-form-label">内容类型</label>
					<div class="layui-input-inline">
						<select name="contentType" id="contentType">
							<option value="">全部</option>
							<option value="1">文本</option>
							<option value="2">图片</option>
							<option value="3">图文</option>
						</select>
					</div>
				</div>
				<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
				<button class="layui-btn layui-btn-normal" type="button" onclick="groupSend()" >微信群发</button>
			</fieldset>
		</div>
		</form>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="ordersTable" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            
            </script>
			<script type="text/html" id="barLine">
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
	var time = getRangeDate();
	var startTime = time.substr(0, 10) + " 00:00:00";
	var endTime = time.substr(13) + " 23:59:59";
	var　 url = "wechatMessage/getList.do";
	//初始化网格
	var tableIns = table.render({
		elem: '#ordersTable',
		url: url,
		page: true,
		method: 'post',
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		where: {
			"contentType":$("#contentType").val(),
			"dateRangeStartTime": startTime,
			"dateRangeEndTime": endTime
		},
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
				field: 'createTime',
				width: '15%',
				align: 'center',
				title: '发送日期',
				templet: function(row) {
					return formatDate(row.createTime);
				}
			}, {
				field: 'contentType',
				width: '15%',
				align: 'center',
				title: '内容类型',
				templet: function(row) {
					return getContentType(row);
				}
			}, {
				field: 'sendStatus',
				width: '15%',
				align: 'center',
				title: '发送状态',
				templet: function(row) {
					return getSendStatus(row);
				}
			}, {
				field: 'userCount',
				width: '15%',
				align: 'center',
				title: '数量'
			}, {
				field: 'sendContent',
				width: '40%',
				align: 'center',
				title: '内容'
			}
			]
		]
	});
	
	var active = {
			reload: function() {
				// 执行重载
				tableIns.reload( {
					method: 'post',
					page: {
						curr: 1
					},
					where: {
						"contentType":$("#contentType").val(),
						"dateRangeStartTime": getStartTime(),
						"dateRangeEndTime": getEndTime()
					}
				});
			}
		};

		$('.searchDiv .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
}


function userDefinedLoadForm(){
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	var laydate = layui.laydate
	laydate.render({
		elem: "#dateInput",
		range: true,
		value: getRangeDate()
	});
	form.render();
}


function getContentType(row){
	var contentType = row.contentType;
	if(contentType == '1'){
		return "文本";
	}else if (contentType == '2'){
		return "图片";
	}else{
		return "图文";
	}
}

function getSendStatus(row){
	var sendStatus = row.sendStatus;
	if(sendStatus == '0'){
		return "发送中";
	}else if (sendStatus == '1'){
		return "发送成功";
	}else{
		return "发送失败";
	}
}


function getRangeDate() {
	var $ = layui.jquery;
	// 如果已有选择的日期
	var date = $("#dateInput").val();
	if (date) {
		return date;
	}
	var date1 = new Date();
	date1.setDate(date1.getDate()-30);
	var date2 = new Date();
	var fromDate = formatDate1(date1);
	var nowDate = formatDate1(date2);
	return fromDate + " - " + nowDate
}

function getStartTime() {
	var time = getRangeDate();
	if (time == '')
		return "";
	var startTime = time.substr(0, 10) + " 00:00:00";
	return startTime;
}

function getEndTime() {
	var time = getRangeDate();
	if (time == '')
		return "";
	var endTime = time.substr(13) + " 23:59:59";

	return endTime;
}



var orderFrmId = 'groupSend';

function groupSend(){
	var $ = layui.jquery;
	var orderurl = "wechatMessage/groupSend.do";
	var title = "微信群发";
	layui.use('element', function(){
		var element = parent.layui.element;
		var id = orderFrmId;
		element.tabDelete('tab', id);
		element.tabAdd('tab', {
			title: title,
			content: '<iframe src="' + orderurl + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
			id: id
		});
		element.tabChange('tab', id);
	});	
	
}

</script>

