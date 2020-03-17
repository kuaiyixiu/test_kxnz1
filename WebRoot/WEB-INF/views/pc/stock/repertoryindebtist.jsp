<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
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
<title>连途</title>
<link rel="stylesheet" type="text/css" href="css/fieldset.css" />
<link rel="stylesheet" type="text/css" href="lib/admin/css/admin.css" />
<%@ include file="../base.jsp"%>
</head>
<body>
	<div class="page-content-wrap">
	    <input type="hidden" name="kind" id="kind" value="${kind }" />
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<div class="layui-inline">
						<label class="layui-form-label">采购日期</label>
						<div class="layui-input-inline">
							<input type="text" class="layui-input" id="dateInput"
								placeholder=" - " lay-key="7">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">供应商</label>
						<div class="layui-input-inline">
							<input type="text" name="supplyName" id="supplyName"
								autocomplete="off" class="layui-input" />
						</div>
					</div>
					<button class="layui-btn layui-btn-normal" data-type="reload"
						lay-filter="searchBtn" type="button">搜索</button>
				</fieldset>
			</div>
		</form>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="repertoryInRecordInfo" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            <div class="layui-btn-container" style="float:left">
            <button class="layui-btn layui-btn-sm" data-url="debtRecord/batchReturnDebt.do" lay-event="openData"><i class="layui-icon layui-icon-rmb"></i>批量还款</button>
            </div>
            <font color="red" id="returnTip">(已选中0笔记录，还款金额：¥0.00)</font>
            </script>
			<script type="text/html" id="barLine">
            <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="editData">编辑</a>
            </script>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
/**
 * 加载表格
 * @param $
 * @param table
 * @param parentId 父级id
 * @param id 当前等级id
 * @param type back返回类型
 * @returns
 */
function userDefinedLoadGrd($, table, parentId, id) {
	var time = getRangeDate();
	var startTime = time.substr(0, 10) + " 00:00:00";
	var endTime = time.substr(13) + " 23:59:59";
	var　 url = "debtRecord/getList.do";
	//初始化网格
	var tableIns = table.render({
		elem: '#repertoryInRecordInfo',
		url: url,
		page: true,
		method:'post',
		where:{
			"kind":$("#kind").val(),
			"enable":1,
			"dateRangeStartTime": startTime,
			"dateRangeEndTime": endTime
			},
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		//height: 'full-250',
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		done: function(res, curr, count) {},
		cols: [
			[{
				type: 'checkbox',
				fixed: 'left',
				width: '5%'
			},{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center',
			}, {
				field: 'addtime',
				width: '20%',
				align: 'center',
				title: '日期'
			}, {
				field: 'supplyName',
				width: '30%',
				align: 'center',
				title: '供应商'
			},{
				field: 'leftAmount',
				width: '15%',
				align: 'center',
				title: '挂账金额',
				templet : function(d) {
					return parseFloat(d.leftAmount).toFixed(2);
				}
			},{
				field: 'userName',
				width: '20%',
				align: 'center',
				title: '采购员'
			},{
				field: 'opt',
				width: '10%',
				align: 'center',
				title: '操作',
				toolbar: '#barLine'
			}]
		]
	});
}

var needPay=0;
layui.use(['jquery','table','laydate','form'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	var laydate = layui.laydate
	laydate.render({
		elem: "#dateInput",
		range: true,
		value: getRangeDate()
	});
	form.render();
	//查询事件
	var active = {
		reload: function() {
			// 执行重载
			table.reload('repertoryInRecordInfo', {
				page: {
					curr: 1
					// 重新从第 1 页开始
				},
				where: {
					"supplyName": $.trim($("#supplyName").val()),
					"kind":$("#kind").val(),
					"enable":1,
					"dateRangeStartTime": getStartTime(),
					"dateRangeEndTime": getEndTime()
				}
			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});
	//监听checkbox 计算需要还款的金额
	table.on('checkbox(tableInfo)', function(obj){
		      var checkStatus = table.checkStatus('repertoryInRecordInfo'); 
		      var data=checkStatus.data;
		      needPay=0;
		      var num=0;
		      for(var i=0;i<data.length;i++){
		    	  num++;
		    	  var d=data[i];
		    	  needPay=needPay+d.leftAmount;
		      }
		      $("#returnTip").html("(已选中"+num+"笔记录，还款金额：¥"+needPay+")");
		});

});

function getRangeDate() {
	var $ = layui.jquery;
	// 如果已有选择的日期
	var date = $("#dateInput").val();
	if (date) {
		return date;
	}
	var date1 = new Date();
	date1.setDate(date1.getDate()-13);
	var date2 = new Date();
	var fromDate = formatDate1(date1);
	var nowDate = formatDate1(date2);
	return fromDate + " - " + nowDate
}

function userDefinedToolHandle(target, obj){
	var $ = layui.jquery;
	switch (obj.event) {
		case 'editData':
			var data = obj.data;
			var id=data.id;
			var url="debtRecord/editDebtRecordData/"+id+".do";
			addTempOrder(url);
			//location.href=url;
			break;
	}
}

/**
 * 新增tab页
 */
function addTempOrder(url){
	layui.use(['jquery','element'], function(){
        var $=layui.jquery;
		var element = parent.layui.element;
		var id = 'openCGDebtFrmId';
		var kind=$("#kind").val();
		var title="采购挂账单";
		if(kind=='3'){
			id="openTHDebtFrmId";
			title="退货挂账单";
		}
		element.tabDelete('tab', id);
		element.tabAdd('tab', {
			title: title,
			content: '<iframe src="' + url + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
			id: id
		});
		element.tabChange('tab', id);
	});
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

function userDefinedToolBarHandle(target, obj){
	var $ = layui.jquery;
	switch (obj.event) {
		case 'openData':
			var checkStatus = layui.table.checkStatus('repertoryInRecordInfo'); 
		    var data=checkStatus.data;
		    var ids="";
		    for(var i=0;i<data.length;i++){
		    	var d=data[i];
		    	ids=ids+d.id+";";
		    }
	  		var url=target.attr('data-url');
	  		var id=data.id;
			var openw="500px";
			var openh="350px";
			layer.open({
				type: 2,
				title: "挂账批量还款",
				area: [openw, openh],
				fixed: false, //不固定
				content: url,
				btn: ['确定', '关闭'],
				yes: function(index, layero){
					var body = layer.getChildFrame('body', index); //得到iframe页的body内容
					var payId = body.find("#payId").val();
					var data={"payId":payId,"ids":ids};
					var result=saveReturnDebtInfo(data);
					if(result.retCode=='success'){
						layui.table.reload('repertoryInRecordInfo');
						layer.close(index);
						layer.msg(result.retMsg, {icon : 1});
					}else{
						layer.msg(result.retMsg, {icon : 2});
					}
						
				}
			});
			break;
	}
}


function saveReturnDebtInfo(data){
	var $=layui.$;
	var status;
	$.ajax({
		type : "POST",
		url : "debtRecord/saveReturnDebtInfo.do",
		data : data,
		dataType : "json",
		async:false,
		success : function(result) {
			status=result;
		}
	});
	return status;
}

</script>

