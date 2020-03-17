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
<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> --> 
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>连途</title>
<%@ include file="../base.jsp"%>
<style>
.showCustomDetail{
	color: #2ea9df;
}
</style>
<script type="text/javascript">
//表单初始化
function userDefinedLoadForm(){
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	var laydate = layui.laydate

	laydate.render({
		elem: "#dateInput",
		range: true,
		value: getRangeDate()
	});
	
	var tableIns = table.render({
		elem : '#rechargeInfo',
		url : 'recharge/getList.do',
		page : true,
		limit : 10,
		limits : [10, 20, 30, 40, 50 ],
		toolbar : '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		where:  {
			"dateRangeStartTime": getStartTime(),
			"dateRangeEndTime": getEndTime(),
			"custName":$("#custName").val(),
			"cardNo":$("#cardNo").val()
		},
		cols : [[
		         {
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center'
			},{
				field: 'creatTime',
				width: '15%',
				align: 'center',
				title: '开单时间',
				templet: function(row) {
					return formatDate(row.creatTime);
				}
			}, {
				field: 'carNumber',
				width: '10%',
				align: 'center',
				title: '车牌号',
				templet: function(row) {
					return getCarNumber(row);
				}
			}, {
				field: 'orderNo',
				width: '15%',
				align: 'center',
				title: '订单编号',
				templet: function(row) {
					return getOrderNo(row);
				}
			}, {
				field: 'orderStatus',
				width: '10%',
				align: 'center',
				title: '订单状态',
				templet: function(row) {
					return getOrderStatus(row);
				}
			}, {
				field: 'orderAmt',
				width: '10%',
				align: 'center',
				title: '订单金额',
				templet: function(row) {
					return getOrderAmt(row);
				}
			},
			{
				field: 'opt',
				width: '20%',
				align: 'center',
				title: '操作',
				toolbar: '#barLine',
				templet: function(row) {
					return getBarRow(row);
				}
			}] ]
	});
	
	//查询事件
	var active = {
		reload: function() {
			// 执行重载
			table.reload('rechargeInfo', {
				page: {
					curr: 1
					// 重新从第 1 页开始
				},
				where:  {
					"dateRangeStartTime": getStartTime(),
					"dateRangeEndTime": getEndTime(),
					"custName":$("#custName").val(),
					"cardNo":$("#cardNo").val()
				}
			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
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

function userDefinedToolHandle(target, obj){
	var $ = layui.jquery;
	switch (obj.event) {
		case 'editData':
			var data = obj.data;
			var id=data.id;
			var openw="600px";
			var openh="400px"
			layer.open({
				type: 2,
				title: "赠送明细",
				area: [openw, openh],
				fixed: false, //不固定
	            offset:'10%',
				content: "recharge/couponDetail.do?id="+id,
				btn: ['关闭']
			});
			break;
	}
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


</script>
</head>

<body>
	<div class="page-content-wrap">
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
						<label class="layui-form-label">会员姓名</label>
						<div class="layui-input-inline">
							<input type="text" name="custName" id="custName" autocomplete="off" class="layui-input">
						</div>
					</div>
				    <div class="layui-inline">
						<label class="layui-form-label">会员卡号</label>
						<div class="layui-input-inline">
							<input type="text" name="cardNo" id="cardNo" autocomplete="off" class="layui-input">
						</div>
					</div>
					<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
				</fieldset>
			</div>
		</form>
		
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="rechargeInfo" lay-filter="tableInfo"></table>
			<script type="text/html" id="barLine">
            <a class="layui-btn layui-btn-xs" lay-event="editData">赠送明细</a>
            </script>
		</div>
	</div>
</body>

</html>
