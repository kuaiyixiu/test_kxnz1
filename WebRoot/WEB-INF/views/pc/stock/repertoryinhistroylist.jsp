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
<title>连途</title>
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
				        <input type="text" class="layui-input" id="dateInput" placeholder=" - " lay-key="7">
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
			<table class="layui-table" id="repertoryInRecordInfo"
				lay-filter="tableInfo"></table>
			<script type="text/html" id="barLine">
<%--            <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="printData">凭据</a>--%>
            <a class="layui-btn layui-btn-xs" lay-event="editData">明细</a>
            <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="destoryData">作废</a>
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
	var　 url = "repertory/getList/"+$("#kind").val()+".do";
	//初始化网格
	var tableIns = table.render({
		elem: '#repertoryInRecordInfo',
		url: url,
		page: true,
		method:'post',
		where:{
			"repertoryStatus":2,
			"dateRangeStartTime": startTime,
			"dateRangeEndTime": endTime
			},
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		//height: 'full-250',
		defaultToolbar: ['print', 'exports'],
		done: function(res, curr, count) {},
		cols: [
			[{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center',
			}, {
				field: 'addTime',
				width: '20%',
				align: 'center',
				title: '日期'
			}, {
				field: 'supplyName',
				width: '30%',
				align: 'center',
				title: '供应商'
			},{
				field: 'userName',
				width: '20%',
				align: 'center',
				title: '采购员'
			},{
				field: 'opt',
				width: '30%',
				align: 'center',
				title: '操作',
				toolbar: '#barLine'
			}]
		]
	});
}


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
					"repertoryStatus":2,
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
    var data = obj.data;
	var id=data.id;
	switch (obj.event) {
		case 'editData':
			var url="repertory/editHistoryData/"+id+".do";
			addTempOrder(url);
			//location.href=url;
			break;
		case 'destoryData':
			var title="作废后产品将增加相应的数量，支付款项将原路回退，作废后无法回退，是否确认？";
			var kind=$("#kind").val();
			if(kind=='1')
				title="作废后产品将减少相应的数量，支付款项将原路回退，作废后无法回退，是否确认？";
			layer.confirm(title,{icon: 3, title:'提示'}, function(index){
				 if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
					 layui.$(".layui-layer-btn0").addClass("layui-btn-disabled");
					 layui.$(".layui-layer-btn0").attr("disabled","disabled");
					var result=saveDestoryData(id);
					if(result.retCode=='success'){
						layer.close(index);
						layer.msg(result.retMsg, {icon : 1});
						layui.table.reload('repertoryInRecordInfo');
					}else{
						layer.msg(result.retMsg, {icon : 2});
					}
				 }
				
				
			});
			break;
		case 'printData':
			alert("凭据");
			break;
	}
}
/**
 * 新增tab页
 */
function addTempOrder(url){
	layui.use(['jquery', 'element'], function(){
		var $=layui.jquery;
		var element = parent.layui.element;
		var kind=$("#kind").val();
		var id = '';
		var title="";
		if(kind=="1"){//采购
			id = 'openCGFrmId';
			title="采购临单";
		}else if(kind=="2"){//退货
			id = 'openTHFrmId';
			title="退货临单";
		}
		url=url+"?kind="+kind;
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

function saveDestoryData(id){
	  var res=null;
		layui.$.ajax({
			type : "POST",
			url : "repertory/saveDestoryData.do",
			data : {"id":id},
			dataType : "json",
			async:false,
			success : function(result) {
				res=result;
			}
		});
		return res;
} 

</script>

