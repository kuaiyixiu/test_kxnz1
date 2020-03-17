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
		
		loadCarConsume();
  }
  
  
function loadCarConsume(){
	var $ = layui.jquery;
	$.ajax({
		type : "post",
		async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
		url : "report/getCarConsumeReport.do",
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
					elem: '#orderCountTotal',
					page : false,
					limit: '9999',
					cols: [
						[{
							field: 'carNumber',
							width: '20%',
							align: 'center',
							title: '车牌号'
						}, {
							field: 'custName',
							width: '20%',
							align: 'center',
							title: '车主'
						}, {
							field: 'count',
							width: '20%',
							align: 'center',
							title: '消费次数'
						}, {
							field: 'orderAmt',
							width: '20%',
							align: 'center',
							title: '消费总额',
							templet : function(row) {
								return parseFloat(row.orderAmt).toFixed(2);
							}
						}, {
							field: 'avaAmt',
							width: '20%',
							align: 'center',
							title: '平均消费',
							templet : function(row) {
								return getAvaAmont(row);
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
  
  

  function getBarRow(row){
		var tpl = barLine.innerHTML;
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
  
  
  function getAvaAmont(row){
	  return (row.orderAmt/row.count).toFixed(2)
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
					<button class="layui-btn layui-btn-normal" data-type="reload"  type="button"  onclick="loadCarConsume();">搜索</button>
				</fieldset>
			</div>
		</form>
	<div class="layui-fluid">
			<div class="layui-tab-content">
					<div class="layui-col-md12">
					<div class="layui-card" style="margin-top: 1%;">
							<div class="layui-card-header">车辆活跃与贡献值(只显示前100)</div>
							<div class="layui-card-body">
								<div class="layui-carousel layadmin-carousel layadmin-dataview" data-anim="fade" lay-filter="LAY-index-dataview" style="width: 100%;" lay-anim="fade" lay-indicator="inside" lay-arrow="none" id="">
									<div class="layui-form" id="">
										<table class="layui-table" id="orderCountTotal" lay-filter="tableInfo"></table>
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
</body>
</html>