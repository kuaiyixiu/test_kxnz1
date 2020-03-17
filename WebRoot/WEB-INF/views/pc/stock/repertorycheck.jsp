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
						<label class="layui-form-label">盘点日期</label>
						<div class="layui-input-inline">
							<input type="text" class="layui-input" id="dateInput" placeholder=" - " lay-key="7">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">产品名称</label>
						<div class="layui-input-inline">
							<input type="text" name="productName" id="productName" autocomplete="off" class="layui-input" />
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label" id="shopLabel">类型</label>
						<div class="layui-input-inline">
							<select name="type" id="type">
								<option value=""></option>
								<option value="1">正常</option>
								<option value="2">盘盈</option>
								<option value="3">盘亏</option>
							</select>
						</div>
					</div>
					<button class="layui-btn layui-btn-normal" data-type="reload"
						lay-filter="searchBtn" type="button">搜索</button>
				</fieldset>
				<div class="layui-form" id="table-list">
					<table class="layui-table" id="repertoryCheckInfo" lay-filter="tableInfo"></table>
				</div>
			</div>
		</form>

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
	var　 url = "repertoryCheck/getList.do";
	//初始化网格
	var tableIns = table.render({
		elem: '#repertoryCheckInfo',
		url: url,
		page: true,
		method:'post',
		where:{
			"dateRangeStartTime": startTime,
			"dateRangeEndTime": endTime,
			},
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		toolbar:'',
		defaultToolbar: ['print', 'exports'],
		done: function(res, curr, count) {},
		cols: [
			[{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center',
			}, {
				field: 'checkTime',
				width: '15%',
				align: 'center',
				title: '日期'
			}, {
				field: 'productName',
				width: '30%',
				align: 'center',
				title: '产品'
			},{
				field: 'type',
				width: '10%',
				align: 'center',
				title: '类型',
				templet: function(row) {
					if(row.beforeQuantity==row.afterQuantity)
						return "正常";
					else if(row.beforeQuantity>row.afterQuantity)
						return '<font color="red">盘亏</font>';
					else if(row.beforeQuantity<row.afterQuantity)
						return '<font color="red">盘盈</font>';
				}
			},{
				field: 'beforeQuantity',
				width: '15%',
				align: 'center',
				title: '盘点前'
			},{
				field: 'afterQuantity',
				width: '15%',
				align: 'center',
				title: '盘点后'
			},{
				field: 'optUser',
				width: '15%',
				align: 'center',
				title: '操作人'
			}]
		]
	});
}

	layui.use([ 'jquery', 'table', 'laydate', 'form', 'element' ], function() {
		var table = layui.table;
		var $ = layui.jquery;
		var form = layui.form;
		var laydate = layui.laydate;
		var element = layui.element;
		laydate.render({
			elem : "#dateInput",
			range : true,
			value : getRangeDate()
		});
		form.render();
		//查询事件
		var active = {
			reload : function() {
				// 执行重载
				table.reload('repertoryCheckInfo', {
					page : {
						curr : 1
					// 重新从第 1 页开始
					},
					where : {
						"dateRangeStartTime" : getStartTime(),
						"dateRangeEndTime" : getEndTime(),
						"productName":$.trim($("#productName").val()),
						"type":$.trim($("#type").val())
					}

				});
				
			}
		};

		$('.searchDiv .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});

	});

	function getRangeDate() {
		var $ = layui.jquery;
		// 如果已有选择的日期
		var date = $("#dateInput").val();
		if (date) {
			return date;
		}
		var date1 = new Date();
		date1.setDate(1);
		var date2 = new Date();
		var fromDate = formatDate1(date1);
		var nowDate = formatDate1(date2);
		return fromDate + " - " + nowDate
	}

	function getStartTime() {
		var time = getRangeDate();
		var startTime = time.substr(0, 10) + " 00:00:00";

		return startTime;
	}

	function getEndTime() {
		var time = getRangeDate();
		var endTime = time.substr(13) + " 23:59:59";

		return endTime;
	}
</script>

