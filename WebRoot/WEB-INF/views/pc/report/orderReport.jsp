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
<style type="text/css">
.showCustomDetail{
	color: #2ea9df;
}
</style>
<script type="text/javascript">
var tabIdx = 0;
  function userDefinedLoadForm(){
		var layer = layui.layer;
		var $ = layui.jquery;
		var laydate = layui.laydate;
		laydate.render({
			elem : "#dateInput",
			range : true,
			value : getRangeDate()
		});
		
		var form = layui.form;
		var element = layui.element;
		form.on('checkbox(optTime1)', function(obj){
			$("[name='optTime']").prop("checked", "");
			$(this).prop("checked", "checked");
			form.render('checkbox');
			
			var endTime = $(this).attr("data-time");
			renderRangeDate1(endTime);
		});
		
		
		element.on('tab(changeTab)', function(data){
			tabIdx = data.index;
			if(tabIdx == 0){
				loadChart1();
			}else if(tabIdx == 1){
				loadChart2();
			}else if(tabIdx == 2){
				loadPayRecord();
			}
			else if(tabIdx == 3){
				loadChart3();
			} 
		});
		
		loadChart1();
  }
  
  
 function loadChart(){
  if(tabIdx == 0){
		loadChart1();
	}else if(tabIdx == 1){
		loadChart2();
	}else if(tabIdx == 2){
		loadPayRecord();
	}else if(tabIdx == 3){
		loadChart3();
	}  
 }
  
  function loadChart1(){
	  var $ = layui.jquery;
		//图表
		var myChart;
		require.config({
			paths : {
				echarts : 'lib/admin/lib/echarts'
			}
		});
		require([ 'echarts', 'echarts/chart/line'], function(ec) {
			//--- 折柱 ---
			myChart = ec.init(document.getElementById('chart'));
			myChart.showLoading(); //数据加载完之前先显示一段简单的loading动画
			$.ajax({
				type : "post",
				async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
				url : "report/getOrderCountChart.do",
				data : {
					"startDate": getStartTime(),
					"endDate": getEndTime(),
				},
				dataType : "json", //返回数据形式为json
				success : function(result) {
					//请求成功时执行该函数内容，result即为服务器返回的json对象
					if (result) {
						myChart.hideLoading(); //隐藏加载动画
						myChart.setOption({ //加载数据图表
							title : {
								text : "数据统计",
								textStyle : {
									color : "rgb(85, 85, 85)",
									fontSize : 18,
									fontStyle : "normal",
									fontWeight : "normal"
								}
							},
							tooltip : {
								trigger : "axis"
							},
							legend : {
								data : [ "进场台次(在查询时间段内开单的订单数量)", "挂账数量(在查询时间段内开单且挂账了的订单数量)", "入账数量(在查询时间端内开单且入账了的订单数量)" ],
								selectedMode : false,
							},
							toolbox : {
								show : true,
								feature : {
									restore : {
										show : true
									},
									saveAsImage : {
										show : true
									}
								}
							},
							calculable : false,
							xAxis : [ {
								type : "category",
								boundaryGap : false,
								data : result.x,
								axisLabel :{rotate :45}
							} ],
							
							yAxis : [ {
								type : "value"
							} ],
							grid : {
								x2 : 30,
								x : 50
							},
							series : [ {
								name :"进场台次(在查询时间段内开单的订单数量)" ,
								type : "line",
								smooth : true,
								itemStyle : {
									normal : {
										areaStyle : {
											type : "default"
											
										}
									}
								},
								data : result.y1
							}, {
								name : "挂账数量(在查询时间段内开单且挂账了的订单数量)",
								type : "line",
								smooth : true,
								itemStyle : {
									normal : {
										areaStyle : {
											type : "default"
										}
									}
								},
								data : result.y2
							}, {
								name : "入账数量(在查询时间端内开单且入账了的订单数量)",
								type : "line",
								smooth : true,
								itemStyle : {
									normal : {
										areaStyle : {
											type : "default"
										},
										color : "rgb(110, 211, 199)"
									}
								},
								data : result.y3
							} ]
						});

						//刷新汇总网格
						layui.table.render({
							elem: '#orderCountTotal',
							limit: '9999',
							page : false,
							cols: [
								[{
									field: 'y1Count',
									width: '33%',
									align: 'center',
									title: '进场台次'
								}, {
									field: 'y2Count',
									width: '33%',
									align: 'center',
									title: '挂账数量'
								}, {
									field: 'y3Count',
									width: '33%',
									align: 'center',
									title: '入账数量'
								}]
							],
							data : result.reportList
						});
						
						
						//刷新明细网格
						layui.table.render({
							elem: '#orderCountDetail',
							limit: '9999',
							page : false,
							cols: [
								[{
									field: 'creatTime',
									width: '20%',
									align: 'center',
									title: '日期'
								}, {
									field: 'y1Count',
									width: '20%',
									align: 'center',
									title: '进场台词'
								}, {
									field: 'y2Count',
									width: '20%',
									align: 'center',
									title: '挂账数量'
								}, {
									field: 'y3Count',
									width: '20%',
									align: 'center',
									title: '入账数量'
								},{
									field: 'opt',
									width: '20%',
									align: 'center',
									title: '操作',
									toolbar: '#barLine',
									templet: function(row) {
										return getBarRow(row);
									}
								}
								]
							],
							data : result.detailList
						});
					}

				},
				error : function(errorMsg) {
					//请求失败时执行该函数
					alert("图表请求数据失败!");
					myChart.hideLoading();
				}
			})
		});

		$(window).resize(function() {
			myChart.resize();
		}) 
  }
  
  
  function loadChart2(){
	  var $ = layui.jquery;
		//图表
		var myChart;
		require.config({
			paths : {
				echarts : 'lib/admin/lib/echarts'
			}
		});
		require([ 'echarts', 'echarts/chart/line'], function(ec) {
			//--- 折柱 ---
			myChart = ec.init(document.getElementById('chart2'));
			myChart.showLoading(); //数据加载完之前先显示一段简单的loading动画
			$.ajax({
				type : "post",
				async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
				url : "report/getEchartOrderProfitInfo.do",
				data : {
					"startDate": getStartTime(),
					"endDate": getEndTime(),
				},
				dataType : "json", //返回数据形式为json
				success : function(result) {
					//请求成功时执行该函数内容，result即为服务器返回的json对象
					if (result) {
						myChart.hideLoading(); //隐藏加载动画
						myChart.setOption({ //加载数据图表
							title : {
								text : "数据统计",
								textStyle : {
									color : "rgb(85, 85, 85)",
									fontSize : 18,
									fontStyle : "normal",
									fontWeight : "normal"
								}
							},
							tooltip : {
								trigger : "axis"
							},
							legend : {
								data : [ "营收(订单实际收入)", "成本(订单消耗的产品进价)", "毛利润(营收-成本)" ],
								selectedMode : false,
							},
							toolbox : {
								show : true,
								feature : {
									restore : {
										show : true
									},
									saveAsImage : {
										show : true
									}
								}
							},
							calculable : false,
							xAxis : [ {
								type : "category",
								boundaryGap : false,
								data : result.x,
								axisLabel :{rotate :45}
							} ],
							
							yAxis : [ {
								type : "value"
							} ],
							grid : {
								x2 : 30,
								x : 50
							},
							series : [ {
								name : "营收(订单实际收入)",
								type : "line",
								smooth : true,
								itemStyle : {
									normal : {
										areaStyle : {
											type : "default"
											
										}
									}
								},
								data : result.y1
							}, {
								name : "成本(订单消耗的产品进价)",
								type : "line",
								smooth : true,
								itemStyle : {
									normal : {
										areaStyle : {
											type : "default"
										}
									}
								},
								data : result.y2
							}, {
								name : "毛利润(营收-成本)",
								type : "line",
								smooth : true,
								itemStyle : {
									normal : {
										areaStyle : {
											type : "default"
										},
										color : "rgb(110, 211, 199)"
									}
								},
								data : result.y3
							} ]
						});

						
						//刷新汇总网格
						layui.table.render({
							elem: '#orderCountTotal2',
							page : false,
							limit: '9999',
							cols: [
								[{
									field: 'y1Float',
									width: '33%',
									align: 'center',
									title: '营收'
								}, {
									field: 'y2Float',
									width: '33%',
									align: 'center',
									title: '成本',
									templet : function(row) {
										return parseFloat(row.y2Float).toFixed(2);
									}
								}, {
									field: 'y3Float',
									width: '33%',
									align: 'center',
									title: '毛利',
									templet : function(row) {
										return parseFloat(row.y3Float).toFixed(2);
									}
								}]
							],
							data : result.reportList2
						});
						
						
						//刷新汇总网格
						layui.table.render({
							elem: '#orderCountTotal3',
							page : false,
							limit: '9999',
							totalRow:true,
							cols: [
								[{
									field: 'creatTime',
									width: '20%',
									align: 'center',
									title: '日期',
									totalRowText : '合计'
								},{
									field: 'y1Float',
									width: '20%',
									align: 'center',
									title: '营收',
									templet : function(row) {
										return parseFloat(row.y1Float).toFixed(2);
									},
									totalRow : true
								}, {
									field: 'y2Float',
									width: '20%',
									align: 'center',
									title: '成本',
									templet : function(row) {
										return parseFloat(row.y2Float).toFixed(2);
									},
									totalRow : true
								}, {
									field: 'y3Float',
									width: '20%',
									align: 'center',
									title: '毛利',
									templet : function(row) {
										return parseFloat(row.y3Float).toFixed(2);
									},
									totalRow : true
								},{
									field: 'opt',
									width: '20%',
									align: 'center',
									title: '操作',
									toolbar: '#barLine',
									templet: function(row) {
										return getBarRow(row);
									}
								}]
							],
							data : result.reportList
						});
						
					}

				},
				error : function(errorMsg) {
					//请求失败时执行该函数
					alert("图表请求数据失败!");
					myChart.hideLoading();
				}
			})
		});

		$(window).resize(function() {
			myChart.resize();
		}) 
  }
 
  
  
  function loadChart3(){
	  var $ = layui.jquery;
		//图表
		var myChart;
		require.config({
			paths : {
				echarts : 'lib/admin/lib/echarts'
			}
		});
		require([ 'echarts', 'echarts/chart/bar'], function(ec) {
			//--- 折柱 ---
			myChart = ec.init(document.getElementById('chart3'));
			myChart.showLoading(); //数据加载完之前先显示一段简单的loading动画
			$.ajax({
				type : "post",
				async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
				url : "report/getEchartOrderEvaluateInfo.do",
				data : {
					"startDate": getStartTime(),
					"endDate": getEndTime(),
				},
				dataType : "json", //返回数据形式为json
				success : function(result) {
					//请求成功时执行该函数内容，result即为服务器返回的json对象
					if (result) {
						myChart.hideLoading(); //隐藏加载动画
						myChart.setOption({ //加载数据图表
							title : {
								text : "数据统计",
								textStyle : {
									color : "rgb(85, 85, 85)",
									fontSize : 18,
									fontStyle : "normal",
									fontWeight : "normal"
								}
							},
							tooltip : {
								trigger : "axis"
							},
							toolbox : {
								show : true,
								feature : {
									restore : {
										show : true
									},
									saveAsImage : {
										show : true
									}
								}
							},
							calculable : false,
							xAxis : [ {
								type : "category",
								data : result.x,
								axisLabel :{rotate :45}
							} ],
							
							yAxis : [ {
								type : "value"
							} ],
							grid : {
								x2 : 30,
								x : 50
							},
							series : [ {
								name : "订单评价",
								type : "bar",
								data : result.y1
							} ]
						});
						
						
						
						//刷新汇总网格
						layui.table.render({
							elem: '#orderCountTotal4',
							page : false,
							limit: '9999',
							cols: [
								[{
									field: 'title',
									width: '33%',
									align: 'center',
									title: '订单评分'
								}, {
									field: 'count',
									width: '33%',
									align: 'center',
									title: '订单数量'
								},{
									field: 'opt',
									width: '33%',
									align: 'center',
									title: '操作',
									toolbar: '#barLine2',
									templet: function(row) {
										return barLine2(row);
									}
								}]
							],
							data : result.evalList
						});

					}

				},
				error : function(errorMsg) {
					//请求失败时执行该函数
					alert("图表请求数据失败!");
					myChart.hideLoading();
				}
			})
		});

		$(window).resize(function() {
			myChart.resize();
		}) 
  }
 
  
function loadPayRecord(){
	var $ = layui.jquery;
	$.ajax({
		type : "post",
		async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
		url : "report/getPayRecordReport.do",
		data : {
			"startDate": getStartTime(),
			"endDate": getEndTime(),
		},
		dataType : "json", //返回数据形式为json
		success : function(result) {
			//请求成功时执行该函数内容，result即为服务器返回的json对象
			if (result) {
				//刷新汇总网格
				layui.table.render({
					elem: '#orderCountTotal5',
					page : false,
					limit: '9999',
					cols: [
						[{
							field: 'payName',
							width: '33%',
							align: 'center',
							title: '支付类型'
						}, {
							field: 'count',
							width: '33%',
							align: 'center',
							title: '使用次数'
						}, {
							field: 'amount',
							width: '33%',
							align: 'center',
							title: '使用额度',
							templet : function(row) {
								return parseFloat(row.amount).toFixed(2);
							}
						}]
					],
					data : result
				});
			}

		},
		error : function(errorMsg) {
			//请求失败时执行该函数
			alert("请求数据失败!");
		}
	})

	
  }
 
  
  /**
   * 渲染时间范围
   * @param endTime
   * @returns
   */
  function renderRangeDate1(startTime) {
  	var $ = layui.jquery;
  	
  	if (!startTime) {
  		return false;
  	}

  	var date = new Date();
  	var nowDate = formatDate1(date);

  	$("#dateInput").val(startTime + " - " + nowDate);
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
		
	}
  

  function getBarRow(row){
		var tpl = barLine.innerHTML;
		// 如果不存在，则最后一个选中
		layui.laytpl(tpl).render(row, function(html) {
			return html;
		});
	}
  
  function getBarRow2(row){
		var tpl = barLine2.innerHTML;
		// 如果不存在，则最后一个选中
		layui.laytpl(tpl).render(row, function(html) {
			return html;
		});
	}
  
  
  function userDefinedToolHandle($arg, obj) {
		var data = obj.data
		var $ = layui.jquery;
		if (obj.event === 'showDetail') { //修改订单
			showDetail(data.creatTime);
		}else if(obj.event === 'showEvaluateDetail'){
			showEvaluateDetail(data);
		}
	}

  
  function showDetail(creatTime){
		layui.use('element', function(){
			var element = parent.layui.element;
			var id = "showReportOrderDetail";
			var url = 'report/showReportOrderDetail.do?createTime='+creatTime;
			element.tabDelete('tab', id);
			element.tabAdd('tab', {
				title: "当日订单数量统计",
				content: '<iframe src="' + url + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
				id: id
			});
			element.tabChange('tab', id);
		});
	}
  
  function showEvaluateDetail(data){
		layui.use('element', function(){
			var time = getRangeDate();
			var startTime = time.substr(0, 10);
			var endTime = time.substr(13);
			var element = parent.layui.element;
			var id = "showEvaluateDetail";
			var url = 'report/showEvaluateDetail.do?dateRangeStartTime='+ startTime+'&dateRangeEndTime='+endTime+'&stars='+data.stars;
			element.tabDelete('tab', id);
			element.tabAdd('tab', {
				title: "订单评分统计",
				content: '<iframe src="' + url + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
				id: id
			});
			element.tabChange('tab', id);
		});
	}


</script>
</head>
<body>
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<div class="layui-inline">
				      <label class="layui-form-label">日期范围</label>
				      <div class="layui-input-inline">
				        <input type="text" class="layui-input" id="dateInput" placeholder=" - " lay-key="7">
				      </div>
				    </div>
				   <div class="layui-inline">
					    <input type="checkbox" lay-filter="optTime1" name="optTime" title="近一个月" checked="checked" data-time="${nextOneMonth}" lay-filter="optTime"><div class="layui-unselect layui-form-checkbox"><i class="layui-icon layui-icon-ok layui-icon-default"></i></div>
					    <input type="checkbox" lay-filter="optTime1" name="optTime" title="近两个月" data-time="${nextTwoMonth}" lay-filter="optTime"><div class="layui-unselect layui-form-checkbox layui-form-checked"><i class="layui-icon layui-icon-ok layui-icon-default"></i></div>
					    <input type="checkbox" lay-filter="optTime1" name="optTime" title="近三个月" data-time="${nextThreeMonth}" lay-filter="optTime"><div class="layui-unselect layui-form-checkbox"><i class="layui-icon layui-icon-ok layui-icon-default"></i></div>
					</div>
					<button class="layui-btn layui-btn-normal" data-type="reload"  type="button"  onclick="loadChart();">搜索</button>
				</fieldset>
			</div>
		</form>
	<div class="layui-fluid">

		<div class="layui-tab" lay-filter="changeTab">
			<ul class="layui-tab-title">
				<li class="layui-this">数量统计</li>
				<li>利润统计</li>
				<li>支付项统计</li>
				<li>订单评价</li>
			</ul>
			<div class="layui-tab-content">
				<div class="layui-tab-item layui-show">
					<div class="layui-col-md12">
						<div class="layui-card" style="margin-top: 1%;">
							<div class="layui-card-header">订单数量统计</div>
							<div class="layui-card-body">
								<div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade" lay-filter="LAY-index-dataview" style="width: 100%; height: 180px;" lay-anim="fade" lay-indicator="inside" lay-arrow="none" id="chart"></div>
							</div>
						</div>
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
						<div class="layui-card" style="margin-top: 1%;">
							<div class="layui-card-header">明细</div>
							<div class="layui-card-body">
								<div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade" lay-filter="LAY-index-dataview" style="width: 100%;" lay-anim="fade" lay-indicator="inside" lay-arrow="none" id="">
									<div class="layui-form" id="">
										<table class="layui-table" id="orderCountDetail" lay-filter="tableInfo"></table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="layui-tab-item">
					<div class="layui-col-md12">
						<div class="layui-card" style="margin-top: 1%;">
							<div class="layui-card-header">订单数量统计</div>
							<div class="layui-card-body">
								<div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade" lay-filter="LAY-index-dataview" style="width: 100%; height: 180px;" lay-anim="fade" lay-indicator="inside" lay-arrow="none" id="chart2"></div>
							</div>
						</div>
						<div class="layui-card" style="margin-top: 1%;">
							<div class="layui-card-header">汇总</div>
							<div class="layui-card-body">
								<div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade" lay-filter="LAY-index-dataview" style="width: 100%;" lay-anim="fade" lay-indicator="inside" lay-arrow="none" id="">
									<div class="layui-form" id="">
										<table class="layui-table" id="orderCountTotal2" lay-filter="tableInfo"></table>
									</div>
								</div>
							</div>
						</div>
						<div class="layui-card" style="margin-top: 1%;">
							<div class="layui-card-header">明细</div>
							<div class="layui-card-body">
								<div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade" lay-filter="LAY-index-dataview" style="width: 100%;" lay-anim="fade" lay-indicator="inside" lay-arrow="none" id="">
									<div class="layui-form" id="">
										<table class="layui-table" id="orderCountTotal3" lay-filter="tableInfo"></table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="layui-tab-item">
					<div class="layui-col-md12">
					<div class="layui-card" style="margin-top: 1%;">
							<div class="layui-card-header">明细</div>
							<div class="layui-card-body">
								<div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade" lay-filter="LAY-index-dataview" style="width: 100%;" lay-anim="fade" lay-indicator="inside" lay-arrow="none" id="">
									<div class="layui-form" id="">
										<table class="layui-table" id="orderCountTotal5" lay-filter="tableInfo"></table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="layui-tab-item">
					<div class="layui-col-md12">
						<div class="layui-card" style="margin-top: 1%;">
							<div class="layui-card-header">订单评价</div>
							<div class="layui-card-body">
								<div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade" lay-filter="LAY-index-dataview" style="width: 100%; height: 180px;" lay-anim="fade" lay-indicator="inside" lay-arrow="none" id="chart3"></div>
							</div>
						</div>
						
						<div class="layui-card" style="margin-top: 1%;">
							<div class="layui-card-header">汇总</div>
							<div class="layui-card-body">
								<div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade" lay-filter="LAY-index-dataview" style="width: 100%;" lay-anim="fade" lay-indicator="inside" lay-arrow="none" id="">
									<div class="layui-form" id="">
										<table class="layui-table" id="orderCountTotal4" lay-filter="tableInfo"></table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
		<script type="text/html" id="barLine">
            	<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="showDetail"  tabId="">详 情</a>
     	</script>
		<script type="text/html" id="barLine2">
            	<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="showEvaluateDetail"  tabId="">评价详情</a>
     	</script>
</body>
</html>