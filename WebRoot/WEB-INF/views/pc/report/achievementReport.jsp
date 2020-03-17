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
<script src="lib/admin/lib/echarts/echarts.js"></script>
<%@ include file="../base.jsp"%>
<title>连途</title>
<script type="text/javascript">
	function userDefinedLoadForm() {
		var layer = layui.layer;
		var $ = layui.jquery;
		var laydate = layui.laydate;
		laydate.render({
			elem : "#dateInput",
			range : true,
			value : getRangeDate()
		});

	}
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

	function userDefinedLoadGrd($, table) {
		//初始化网格
		var tableIns = table.render({
			elem : '#serverRepInfo',
			method : 'post',
			url : "report/queryServeRep.do",
			page : false,
			where : {
				"startDate" : getStartTime(),
				"endDate" : getEndTime()
			},
			cols : [ [ {
				field : 'class_name',
				align : 'center',
				title : '服务分类'
			}, {
				field : 'usecount',
				align : 'center',
				width : '20%',
				title : '销售次数',
				templet : function(row) {
					if (row.usecount == null)
						return "0";
					else
						return row.usecount;
				}
			}, {
				field : 'useamt',
				align : 'center',
				width : '20%',
				title : '产值',
				templet : function(row) {
					if (row.useamt == null)
						return "0.00";
					else
						return parseFloat(row.useamt).toFixed(2);
				}
			}, {
				field : 'cost',
				align : 'center',
				title : '成本',
				width : '20%',
				templet : function(row) {
					return "0.00";
				}
			}, {
				field : 'useamt1',
				align : 'center',
				title : '毛利',
				width : '20%',
				templet : function(row) {
					if (row.useamt == null)
						return "0.00";
					else
						return parseFloat(row.useamt).toFixed(2);
				}
			} ] ]
		});

		var tableIns1 = table.render({
			elem : '#ProductRepInfo',
			method : 'post',
			url : "report/queryProductRep.do",
			page : false,
			where : {
				"startDate" : getStartTime(),
				"endDate" : getEndTime()
			},
			cols : [ [ {
				field : 'class_name',
				align : 'center',
				title : '产品分类'
			}, {
				field : 'usecount',
				align : 'center',
				title : '销售次数',
				templet : function(row) {
					if (row.usecount == null)
						return "0";
					else
						return row.usecount;
				}
			}, {
				field : 'useamt',
				align : 'center',
				title : '产值',
				templet : function(row) {
					if (row.useamt == null)
						return "0.00";
					else
						return parseFloat(row.useamt).toFixed(2);
				}
			}, {
				field : 'useamt1',
				align : 'center',
				title : '成本',
				templet : function(row) {
					if (row.useamt1 == null)
						return "0.00";
					else
						return parseFloat(row.useamt1).toFixed(2);
				}
			}, {
				field : 'useamt2',
				align : 'center',
				title : '毛利',
				templet : function(row) {
					var total=0.00;
					var amt=0.00;
					if (row.useamt == null)
						total="0.00";
					else
						total=parseFloat(row.useamt).toFixed(2);
					if (row.useamt1 == null)
						amt="0.00";
					else
						amt=parseFloat(row.useamt1).toFixed(2);
					return parseFloat(total-amt).toFixed(2);
				}
			} ] ]
		});

		//查询事件
		var active = {
			reload : function() {
				// 执行重载
				tableIns.reload({
					where : {
						"startDate" : getStartTime(),
						"endDate" : getEndTime()
					}

				});
				tableIns1.reload({
					where : {
						"startDate" : getStartTime(),
						"endDate" : getEndTime()
					}

				});

			}
		};

		$('#search').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});

	}
</script>
</head>
<body>
	<div class="page-content-wrap">
		<div class="layui-fluid">
			<div class="layui-col-md12">
				<div class="layui-card" style="margin-top: 1%;">
					<div class="layui-card-header">查询条件</div>
					<div class="layui-card-body">
						<form class="layui-form">
							<div class="layui-fluid">
								<div class="layui-row">
									<div class="layui-col-md4">
										<div class="layui-form-item">
											<label class="layui-form-label">时间段</label>
											<div class="layui-input-block">
												<input type="text" class="layui-input" id="dateInput"
													placeholder=" - " lay-key="7">
											</div>
										</div>
									</div>
									<div class="layui-col-md2" align="center">
										<button class="layui-btn layui-btn-normal" data-type="reload"
											lay-filter="searchBtn" type="button" id="search">搜索</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="layui-card" style="margin-top: 1%;">
					<div class="layui-card-header">服务分类产值</div>
					<div class="layui-card-body">
						<table class="layui-table" id="serverRepInfo"></table>
					</div>
				</div>
				<div class="layui-card" style="margin-top: 1%;">
					<div class="layui-card-header">产品分类产值</div>
					<div class="layui-card-body">
						<table class="layui-table" id="ProductRepInfo"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>