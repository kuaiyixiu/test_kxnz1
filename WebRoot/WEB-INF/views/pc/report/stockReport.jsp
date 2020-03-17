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
			elem : '#custInfo',
			method : 'post',
			url : "report/queryProduct.do",
			page : true,
			limit : 10,
			limits : [ 10, 20, 50, 100, 500 ],
			where:{
				"dateRangeStartTime": getStartTime(),
				"dateRangeEndTime": getEndTime(),
				"keyword":$.trim($("#keyword").val())
				},
			cols : [ [ {
				field : 'productName',
				align : 'center',
				sort : true,
				title : '产品名称'
			}, {
				field : 'productType',
				align : 'center',
				sort : true,
				title : '产品型号'
			}, {
				field : 'inQty',
				align : 'center',
				sort : true,
				title : '入库数量',
				templet: function(row) {
					if(row.inQty==null)
						return "0";
					else
						return row.inQty;
				}
			}, {
				field : 'inAmt',
				align : 'center',
				sort : true,
				title : '入库总价',
				templet: function(row) {
					if(row.inAmt==null)
						return "0.00";
					else
						return parseFloat(row.inAmt).toFixed(2);
				}
			}, {
				field : 'outQty',
				align : 'center',
				sort : true,
				title : '出库数量',
				templet: function(row) {
					if(row.outQty==null)
						return "0";
					else
						return row.outQty;
				}
			}, {
				field : 'outAmt',
				align : 'center',
				sort : true,
				title : '出库总价',
				templet: function(row) {
					if(row.outAmt==null)
						return "0.00";
					else
						return parseFloat(row.outAmt).toFixed(2);
				}
			}, {
				field : 'quantity',
				align : 'center',
				sort : true,
				title : '当前库存'
			} ] ]
		});
		
		
		//查询事件
		var active = {
			reload : function() {
				// 执行重载
				tableIns.reload({
					page : {
						curr : 1
					// 重新从第 1 页开始
					},
					where : {
						"dateRangeStartTime" : getStartTime(),
						"dateRangeEndTime" : getEndTime(),
						"keyword":$.trim($("#keyword").val())
					}

				});
				
			}
		};

		$('.searchDiv .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});

	}

	function showCustomDetail(id) {
		var url = "custom/detailView.do";
		var openw = "700px";
		var openh = "800px";

		//将iframeObj传递给父级窗口,执行操作完成刷新

		var option = {
			type : 2,
			title : "详情",
			area : [ "800px", "700px" ],
			fixed : false, //不固定
			content : url + "?id=" + id,
			success : function(layero, index) {
				//layer.iframeAuto(index);
			}
		};

		var index = layer.open(option);
	}
</script>
</head>
<body>
	<div class="page-content-wrap">
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<form class="layui-form">
						<div class="layui-fluid">
							<div class="layui-row">
								<div class="layui-col-md4">
									<div class="layui-form-item">
										<label class="layui-form-label">关键字</label>
										<div class="layui-input-block">
											<input type="text" name="keyword" id="keyword"
												placeholder="产品名称/产品型号" autocomplete="off"
												class="layui-input">
										</div>
									</div>
								</div>
								<div class="layui-col-md4">
									<div class="layui-form-item">
										<label class="layui-form-label">时间段</label>
										<div class="layui-input-inline">
											<input type="text" class="layui-input" id="dateInput"
												placeholder=" - " lay-key="7">
										</div>
									</div>
								</div>
								<div class="layui-col-md2" align="center">
									<button class="layui-btn layui-btn-normal" data-type="reload"
										lay-filter="searchBtn" type="button">搜索</button>
								</div>
							</div>
						</div>
					</form>
				</fieldset>
			</div>
		</form>

		<div class="layui-form" id="table-list">
			<table class="layui-table" id="custInfo" lay-filter="tableInfo"></table>
		</div>
	</div>
</body>
</html>