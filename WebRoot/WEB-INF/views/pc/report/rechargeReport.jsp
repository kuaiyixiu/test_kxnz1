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
    var myChart;
	function userDefinedLoadForm() {
		var layer = layui.layer;
		var $ = layui.jquery;
		var laydate = layui.laydate;
		laydate.render({
			elem : "#dateInput",
			range : true,
			min: -31,
			max: 0,
			value : getRangeDate()
		});
		//图表
		require.config({
			paths : {
				echarts : 'lib/admin/lib/echarts'
			}
		});
		require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line','echarts/chart/map' ], function(ec) {
			myChart = ec.init(document.getElementById('chart'));
			var result=getChartInfo();
			myChart.setOption({ //加载数据图表
				title : {
					text : "充值消费统计",
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
					data : [ "充值", "消费" ],
					selectedMode : false,
				},
				toolbox : {
					show : true,
					feature : {
						dataView : {
							show : false,
							readOnly : true
						},
						magicType : {
							show : false,
							type : [ "line", "bar", "stack", "tiled" ]
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						},
						mark : {
							show : false
						}
					}
				},
				calculable : false,
				xAxis : [ {
					type : "category",
					boundaryGap : false,
					data : result.x,
					axisLabel : {
						rotate : 45
					}
				} ],
				yAxis : [ {
					type : "value"
				} ],
				grid : {
					x2 : 30,
					x : 50
				},
				series : [ {
					name : "充值",
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
					name : "消费",
					type : "line",
					smooth : true,
					itemStyle : {
						normal : {
							areaStyle : {
								type : "default"
							}
						}
					},
					data : result.y3
				} ]
			});
			$(window).resize(function() {
				myChart.resize();
			})
				
			});
	}
	
	function refreshData(data){
	     if(!myChart){
	          return;
	     }  
	     //更新数据
	      var option = myChart.getOption();
	      option.xAxis[0].data = data.x; 
	      option.series[0].data = data.y2;
	      option.series[1].data = data.y3;
	      myChart.setOption(option,true);    
	}
	
	function getChartInfo(){
		var data;
		myChart.showLoading(); //数据加载完之前先显示一段简单的loading动画
		layui.$.ajax({
			type : "post",
			//async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
			url : "report/getRechargeRep.do",
			data : {
				"startDate" : getStartTime(),
				"endDate" : getEndTime()
			},
			dataType : "json", //返回数据形式为json
			async:false,
			success : function(result) {
				myChart.hideLoading(); //隐藏加载动画
				data=result;
			},
			error : function(errorMsg) {
				//请求失败时执行该函数
				myChart.hideLoading(); //隐藏加载动画
				alert("图表请求数据失败!");
			}
		})
		return data;
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
			elem : '#rechargeInfo',
			method : 'post',
			url : "report/queryRechargeDetail.do",
			page : false,
			totalRow : true,
			done : function(res, curr, count) {
				var a1 = $(".layui-table-total div:eq(1)").html();
				var a2 = $(".layui-table-total div:eq(2)").html();
				var a4 = $(".layui-table-total div:eq(4)").html();
				var a6 = $(".layui-table-total div:eq(6)").html();
				var a8 = $(".layui-table-total div:eq(8)").html();
				$(".layui-table-total div:eq(1)").html(
						a1.substr(0, a1.indexOf(".")));
				$(".layui-table-total div:eq(2)").html(
						a2.substr(0, a2.indexOf(".")));
				$(".layui-table-total div:eq(4)").html(
						a4.substr(0, a4.indexOf(".")));
				$(".layui-table-total div:eq(6)").html(
						a6.substr(0, a6.indexOf(".")));
				$(".layui-table-total div:eq(8)").html(
						a8.substr(0, a8.indexOf(".")));
			},
			//limit : 10,
			//limits : [ 10, 20, 30, 40, 50 ],
			where : {
				"startDate" : getStartTime(),
				"endDate" : getEndTime()
			},
			cols : [ [ {
				field : 'dateStr',
				align : 'center',
				title : '时间',
				totalRowText : '合计'
			}, {
				field : 'openCardCount',
				align : 'center',
				title : '开卡数量',
				totalRow : true
			}, {
				field : 'rechargeCount',
				align : 'center',
				title : '充值次数',
				totalRow : true
			}, {
				field : 'rechargeAmt',
				align : 'center',
				title : '充值金额',
				totalRow : true
			}, {
				field : 'costCount',
				align : 'center',
				title : '消费次数',
				totalRow : true
			}, {
				field : 'costAmt',
				align : 'center',
				title : '消费金额',
				totalRow : true
			}, {
				field : 'couponCount',
				align : 'center',
				title : '赠送代金券次数',
				totalRow : true
			}, {
				field : 'couponAmt',
				align : 'center',
				title : '赠送代金券金额',
				totalRow : true
			}, {
				field : 'costCouponCount',
				align : 'center',
				title : '消费代金券次数',
				totalRow : true
			}, {
				field : 'costCouponAmt',
				align : 'center',
				title : '消费代金券金额',
				totalRow : true
			} ] ]
		});
		
		//查询事件
		var active = {
			reload : function() {
				var result=getChartInfo();
				refreshData(result);
				// 执行重载
				tableIns.reload({
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
					<div class="layui-card-header">查询结果</div>
					<div class="layui-card-body">
						<div class="layui-carousel layadmin-carousel layadmin-dataview"
							data-anim="fade" lay-filter="LAY-index-dataview"
							style="width: 100%; height: 180px;" lay-anim="fade"
							lay-indicator="inside" lay-arrow="none" id="chart"></div>
					</div>
				</div>
				<div class="layui-card" style="margin-top: 1%;">
					<div class="layui-card-header">详细数据</div>
					<div class="layui-card-body">
							<div class="layui-form" id="table-list">
								<table class="layui-table" id="rechargeInfo" lay-filter="tableInfo"></table>
							</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>