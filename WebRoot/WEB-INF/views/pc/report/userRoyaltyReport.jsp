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
		
		$('.usertip').mouseenter(function() {
			layui.dialog.tips('没有产值的员工不显示', '.usertip');
		})
		
  }
  
  
function loadCarConsume(){
	var $ = layui.jquery;
	$.ajax({
		type : "post",
		async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
		url : "report/getUserRoyaltyReport.do",
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
							field: 'userRealname',
							width: '25%',
							align: 'center',
							title: '员工姓名'
						}, {
							field: 'serveRoyal',
							width: '25%',
							align: 'center',
							title: '服务施工产值&nbsp;<i class="layui-icon layui-icon-tips servetip"></i>',
							templet : function(row) {
								return parseFloat(row.serveRoyal).toFixed(2);
							}
						}, {
							field: 'sellRoyal',
							width: '25%',
							align: 'center',
							title: '服务销售产值&nbsp;<i class="layui-icon layui-icon-tips selltip"></i>',
							templet : function(row) {
								return parseFloat(row.sellRoyal).toFixed(2);
							}
						}, {
							field: 'productRoyal',
							width: '25%',
							align: 'center',
							title: '产品销售产值&nbsp;<i class="layui-icon layui-icon-tips producttip"></i>',
							templet : function(row) {
								return parseFloat(row.productRoyal).toFixed(2);
							}
						}
						]
					],
					data : result
				});
				
				//焦点触发提示功能
				$('.servetip').mouseenter(function() {
					layui.dialog.tips('派工给员工后计算为该员工施工产值', '.servetip');
				})
				$('.selltip').mouseenter(function() {
					layui.dialog.tips('该员工用自己账号登录系统添加服务项目后计算为该员工销售产值', '.selltip');
				})
				$('.producttip').mouseenter(function() {
					layui.dialog.tips('该员工用自己账号登录系统添加销售产品后计算为该员工销售产值', '.producttip');
				})
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
  
  
  function userDefinedToolHandle($arg, obj) {
		var data = obj.data
		var $ = layui.jquery;
		if (obj.event === 'showDetail') { //修改订单
			showDetail(data.creatTime);
		}
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
							<div class="layui-card-header">员工产值&nbsp;<i class="layui-icon layui-icon-tips usertip"></i></div>
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
</body>
</html>