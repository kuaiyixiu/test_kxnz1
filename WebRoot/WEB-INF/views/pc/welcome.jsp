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
<link rel="stylesheet" type="text/css"
	href="lib/admin/layui/css/layui.css" />
<link rel="stylesheet" type="text/css" href="css/welcome.css" />
<link rel="stylesheet" type="text/css"
	href="lib/admin/fonts/iconfont.css" />
<script src="lib/admin/layui/layui.js" type="text/javascript"
	charset="utf-8"></script>
<script src="lib/admin/lib/echarts/echarts.js"></script>
<title>连途</title>
</head>
<body style="background-color: #fff;">
	<div style="width: 100%;height: 100%;">
		<div class="panel_box row"
			style="margin-top: 1%;margin-left: 2.5%;width: 95%;">
			<div class="panel col">
				<a href="javascript:;" onclick="openDetail(1)">
					<div class="panel_icon">
						<i class="iconfont icon-tixing1" data-icon="icon-wenben"></i>
					</div>
					<div class="panel_word">
						<span>${ remindCount }</span> <cite>日常提醒</cite>
					</div> </a>
			</div>
			<div class="panel col">
				<a href="javascript:;" onclick="openDetail(2)">
					<div class="panel_icon" style="background-color:#5FB878;">
						<i class="iconfont icon-yuyue" data-icon="icon-wenben"></i>
					</div>
					<div class="panel_word userAll">
						<span>${ appointCount }</span> <cite>客户预约</cite>
					</div> </a>
			</div>
			<div class="panel col">
				<a href="javascript:;" onclick="openDetail(3)">
					<div class="panel_icon" style="background-color:#009688;">
						<i class="iconfont icon-kehu" data-icon="icon-wenben"></i>
					</div>
					<div class="panel_word userAll">
						<span>${ invitationCount }</span> <cite>客户邀约</cite>
					</div> </a>
			</div>
			<div class="panel col">
				<a href="javascript:;" onclick="openDetail(4)">
					<div class="panel_icon" style="background-color:#FF5722;">
						<i class="iconfont icon-kucun" data-icon="icon-wenben"></i>
					</div>
					<div class="panel_word ">
						<span>${productArertCount }</span> <cite>库存预警</cite>
					</div> </a>
			</div>
			<div class="panel col">
				<a href="javascript:;" onclick="openDetail(5)">
					<div class="panel_icon" style="background-color:#F7B824;">
						<i class="iconfont icon-guoqirenwu" data-icon="icon-wenben"></i>
					</div>
					<div class="panel_word waitNews">
						<span>${ expireCount }</span> <cite>即将过期服务</cite>
					</div> </a>
			</div>
		</div>
		<!--图表-->
		<div class="chart-panel"
			style="margin-top: 1%;background-color: #fff;border: solid 1px transparent;border-color: #dddddd;margin-left:2.5%;width: 95%; ">
			<div class="panel-body" id="chart" style="height: 350px;"></div>
		</div>
		<div class="row" style="margin-left: 2%;width: 96%;margin-top: 1%;">
			<div class="sysNotice col">
				<blockquote class="layui-elem-quote title">更新日志</blockquote>
				<div class="layui-elem-quote layui-quote-nm">
				<ul class="layui-timeline">
				<li class="layui-timeline-item">
				<i  class="layui-icon layui-timeline-axis">&#xe63f;</i>
					<div class="layui-timeline-content layui-text">
						<h3 class="layui-timeline-title">2019年8月12日</h3>
						<p>连途系统更新主要内容：
						<ol>
							<li>微信公众号针对客户推送活动消息，群发消息</li>
							<li>新增报表管理模块</li>
							<li>新增订单，充值，会员流失 ，车辆消费，微信统计，业绩统计，员工产值，库存消耗报表统计功能</li>
							<li>微信公众号客户端加入查看优惠券功能</li>
							<li>优化订单系统管理显示问题</li>
							<li>修复了系统已知问题</li>
						</ol>
						</p>
					</div></li>
				<li class="layui-timeline-item"><i
					class="layui-icon layui-timeline-axis">&#xe63f;</i>
					<div class="layui-timeline-content layui-text">
						<div class="layui-timeline-title">连途系统上线</div>
					</div>
				</li>
			</ul>
				</div>
			</div>
			<div class="sysNotice col">
				<blockquote class="layui-elem-quote title">联系我们</blockquote>
				<table class="layui-table">
					<colgroup>
						<col width="150">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<td>联系电话</td>
							<td class="version">13345523000</td>
						</tr>
						<tr>
							<td>微信</td>
							<td class="version">18656928446</td>
						</tr>
						<tr>
							<td>QQ</td>
							<td class="homePage">2448793664</td>
						</tr>
						<tr>
							<td>邮箱</td>
							<td class="server">2448793664@qq.com</td>
						</tr>

					</tbody>
				</table>
			</div>
		</div>
		<div
			style="width: 100%;height: 20px;line-height: 20px; vertical-align: middle;position: fixed;bottom: 10px;"
			align="center">

			<font>Copyright&nbsp;&nbsp;© 2019&nbsp;&nbsp;安徽快易修网络科技有限公司&nbsp;&nbsp;</font>
		</div>
	</div>
	<script type="text/javascript">
		layui.use([ 'layer', 'jquery' ], function() {
			var layer = layui.layer;
			var $ = layui.jquery;
			//图表
			var myChart;
			require.config({
				paths : {
					echarts : 'lib/admin/lib/echarts'
				}
			});
			require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line',
					'echarts/chart/map' ], function(ec) {
				//--- 折柱 ---
				myChart = ec.init(document.getElementById('chart'));
				//myChart.setOption({});

				myChart.showLoading(); //数据加载完之前先显示一段简单的loading动画
				$.ajax({
					type : "post",
					async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
					url : "payRecord/getEchatInfo.do",
					data : {},
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
									data : [ "开单数", "收入", "支出" ],
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
									data : result.x
								} ],
								yAxis : [ {
									type : "value"
								} ],
								grid : {
									x2 : 30,
									x : 50
								},
								series : [ {
									name : "开单数",
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
									name : "收入",
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
									name : "支出",
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
		});

		function openDetail(type) {
			var moduleTypeMap = {
				1 : "remindlist_mgr", // 日常提醒
				2 : "appointlist_mgr", // 客户预约
				3 : "invitation_mgr", // 客户邀约
				4 : "preview_product", // 库存预警
				5 : "customExpirelist_mgr"//过期查询
			}

			var module = moduleTypeMap[type];

			if (!module) {
				return false;
			}

			parent.layui.$("[data-id=" + module + "]")[0].click();
		}
	</script>
</body>
</html>

