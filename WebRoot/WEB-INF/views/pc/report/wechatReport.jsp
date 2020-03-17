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
  function userDefinedLoadForm(){
		var layer = layui.layer;
		var $ = layui.jquery;
		//图表
		var myChart;
		require.config({
			paths : {
				echarts : 'lib/admin/lib/echarts'
			}
		});
		require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line','echarts/chart/map','echarts/chart/pie','echarts/chart/funnel' ], function(ec) {
			myChart = ec.init(document.getElementById('chart'));
			myChart.showLoading(); //数据加载完之前先显示一段简单的loading动画
			$.ajax({
				type : "post",
				async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
				url : "report/getWechatChart.do",
				data : {},
				dataType : "json", //返回数据形式为json
				success : function(result) {
					//请求成功时执行该函数内容，result即为服务器返回的json对象
					if (result) {
						myChart.hideLoading(); //隐藏加载动画
						myChart.setOption({ //加载数据图表
						    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						    },
					        color:['#00BFFF','#F4A460'],
						    legend: {
						        orient : 'vertical',
						        x : 'left',
						        data:['已绑定','未绑定']
						    },
						    toolbox: {
						        show : true,
						        feature : {
						            dataView : {show: true, readOnly: false},
						            restore : {show: true},
						            saveAsImage : {show: true}
						        }
						    },
						    calculable : true,
						    series : [
						        {
						            type:'pie',
						            radius : '55%',
						            center: ['50%', '60%'],
						            data:[
						                {value:result.wechatBindCount, name:'已绑定'},
						                {value:result.wechatNoBindCount, name:'未绑定'},
						            ]
						        }
						    ]
						});

					}

				},
				error : function(errorMsg) {
					//请求失败时执行该函数
					alert("图表请求数据失败!");
					myChart.hideLoading();
				}
			})
		})
		$(window).resize(function() {
			myChart.resize();
		})
  }
  
  
  function userDefinedLoadGrd($, table) {
		//初始化网格
		var tableIns = table.render({
			elem: '#custInfo',
			method:'post',
			url: "custom/queryCustoms.do",
			page: true,
			limit: 10,
			limits: [10, 20, 30, 40, 50],
			where: {
				"bindFlag":1
			},
			cols: [
				[{
					field: 'cardNo',
					width: '10%',
					align: 'center',
					title: '会员卡号',
					templet: function(row) {
						return "<a href='javascript:void(0);' onClick='showCustomDetail("+row.id+")' class='showCustomDetail'>"+row.cardNo+"</a>";
					}
				},{
					field: 'custName',
					align: 'center',
					title: '会员名称'
				}, {
					field: 'cellphone',
					align: 'center',
					title: '手机号'
				}, {
					field: 'balance',
					align: 'center',
					title: '余额'
				}, {
					field: 'score',
					align: 'center',
					title: '积分'
				}, {
					field: 'wechatName',
					align: 'center',
					title: '微信名称'
				}]
			]
		});
		
	}
  
  function showCustomDetail(id){
		var url = "custom/detailView.do";
		var openw = "700px";
		var openh = "800px";

		//将iframeObj传递给父级窗口,执行操作完成刷新
		
		var option = {
				type: 2,
				title: "详情",
				area: ["800px", "700px"],
				fixed: false, //不固定
				content: url+"?id="+id,
				success: function(layero, index) {
					//layer.iframeAuto(index);
				}
			};
		
		var index = layer.open(option);
	}
</script>
</head>
<body>
	<div class="layui-fluid">
		<div class="layui-col-md12">
			<div class="layui-card" style="margin-top: 1%;">
				<div class="layui-card-header">会员微信关注统计</div>
				<div class="layui-card-body">
				<div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade" lay-filter="LAY-index-dataview" style="width: 100%; height: 180px;" lay-anim="fade" lay-indicator="inside" lay-arrow="none" id="chart">
                </div>
				</div>
			</div>
			<div class="layui-card" style="margin-top: 1%;">
				<div class="layui-card-header">绑定会员列表</div>
				<div class="layui-card-body">
				<div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade" lay-filter="LAY-index-dataview" style="width: 100%;" lay-anim="fade" lay-indicator="inside" lay-arrow="none" id="grdData">
                <div class="layui-form" id="table-list">
                <table class="layui-table" id="custInfo" lay-filter="tableInfo"></table>
		        </div>
                </div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>