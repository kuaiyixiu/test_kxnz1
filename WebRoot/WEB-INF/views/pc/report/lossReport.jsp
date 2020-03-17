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
		//图表
		require.config({
			paths : {
				echarts : 'lib/admin/lib/echarts'
			}
		});
		require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line',
				'echarts/chart/map' ], function(ec) {
			myChart = ec.init(document.getElementById('chart'));
			var result = getChartInfo();
			initTab(result);
			var option = {
				color : [ '#3398DB' ],
				tooltip : {
					trigger : 'axis',
					axisPointer : { // 坐标轴指示器，坐标轴触发有效
						type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
					}
				},
				grid : {
					left : '3%',
					right : '4%',
					bottom : '3%',
					containLabel : true
				},
				xAxis : [ {
					type : 'category',
					data : [ '45天未消费会员', '三个月未消费会员', '半年未消费会员', '一年未消费会员' ],
					axisTick : {
						alignWithLabel : true
					}
				} ],
				yAxis : [ {
					type : 'value'
				} ],
				series : [ {
					name : '会员数量',
					type : 'bar',
					barWidth : 60,
					data : [ result.count1, result.count2, result.count3,
							result.count4 ]
				} ]
			};
			myChart.setOption(option);
			$(window).resize(function() {
				myChart.resize();
			})

		});
	}
	
	function initTab(result){
		var $=layui.$;
		$("#count1").html(result.count1);
		$("#count2").html(result.count2);
		$("#count3").html(result.count3);
		$("#count4").html(result.count4);
		$("#total1").html(result.allCount);
		$("#total2").html(result.allCount);
		$("#total3").html(result.allCount);
		$("#total4").html(result.allCount);
		$("#percent1").html(Number(result.count1/result.allCount).toFixed(4)*100+"%");
		$("#percent2").html(Number(result.count2/result.allCount).toFixed(4)*100+"%");
		$("#percent3").html(Number(result.count3/result.allCount).toFixed(4)*100+"%");
		$("#percent4").html(Number(result.count4/result.allCount).toFixed(4)*100+"%");
	}

	function getChartInfo() {
		var data;
		myChart.showLoading(); //数据加载完之前先显示一段简单的loading动画
		layui.$.ajax({
			type : "post",
			//async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
			url : "report/getLossRep.do",
			dataType : "json", //返回数据形式为json
			async : false,
			success : function(result) {
				myChart.hideLoading(); //隐藏加载动画
				data = result;
			},
			error : function(errorMsg) {
				//请求失败时执行该函数
				myChart.hideLoading(); //隐藏加载动画
				alert("图表请求数据失败!");
			}
		})
		return data;
	}
	//明细
	/*
	function openDeatil(flag){
		var openw="1200px";
		var openh="680px"
		layer.open({
			type: 2,
			title: "未消费会员明细",
			area: [openw, openh],
			fixed: false, //不固定
            offset:'10%',
			content: 'report/lossdetail.do?flag='+flag,
			btn: ['关闭']
		});
	}
	*/
</script>
</head>
<body>
	<div class="page-content-wrap">
		<div class="layui-fluid">
			<div class="layui-col-md12">
				<div class="layui-card" style="margin-top: 1%;">
					<div class="layui-card-header">未消费会员数量</div>
					<div class="layui-card-body">
						<div class="layui-carousel layadmin-carousel layadmin-dataview"
							data-anim="fade" lay-filter="LAY-index-dataview"
							style="width: 100%; height: 250px;" lay-anim="fade"
							lay-indicator="inside" lay-arrow="none" id="chart"></div>
					</div>
				</div>
				<div class="layui-card" style="margin-top: 1%;">
					<div class="layui-card-header">未消费会员统计</div>
					<div class="layui-card-body">
						<table class="layui-table">
							<thead>
								<tr>
									<th style="text-align: center;">未消费时间</th>
									<th style="text-align: center;">未消费会员数量</th>
									<th style="text-align: center;">会员总数</th>
									<th style="text-align: center;">未消费会员占比</th>
<%--									<th style="text-align: center;">查看明细</th>--%>
								</tr>
							</thead>
							<tbody align="center">
								<tr>
									<td>45天未消费会员</td>
									<td id="count1"></td>
									<td id="total1"></td>
									<td id="percent1"></td>
<%--									<td><a class="layui-btn layui-btn-normal layui-btn-xs" href="javascript:void(0)" onclick="openDeatil(1)">会员明细</a></td>--%>
								</tr>
								<tr>
									<td>三个月未消费会员</td>
									<td id="count2"></td>
									<td id="total2"></td>
									<td id="percent2"></td>
<%--									<td><a class="layui-btn layui-btn-normal layui-btn-xs" href="javascript:void(0)" onclick="openDeatil(2)">会员明细</a></td>--%>
								</tr>
								<tr>
									<td>半年未消费会员</td>
									<td id="count3"></td>
									<td id="total3"></td>
									<td id="percent3"></td>
<%--									<td><a class="layui-btn layui-btn-normal layui-btn-xs" href="javascript:void(0)" onclick="openDeatil(3)">会员明细</a></td>--%>
								</tr>
								<tr>
									<td>一年未消费会员</td>
									<td id="count4"></td>
									<td id="total4"></td>
									<td id="percent4"></td>
<%--									<td><a class="layui-btn layui-btn-normal layui-btn-xs" href="javascript:void(0)" onclick="openDeatil(4)">会员明细</a></td>--%>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>