<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<div class="layui-inline">
						<label class="layui-form-label">调拨日期</label>
						<div class="layui-input-inline">
							<input type="text" class="layui-input" id="dateInput"
								placeholder=" - " lay-key="7">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">调出产品</label>
						<div class="layui-input-inline">
							<input type="text" name="calloutName" id="calloutName"
								autocomplete="off" class="layui-input" />
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">调入产品</label>
						<div class="layui-input-inline">
							<input type="text" name="callinName" id="callinName"
								autocomplete="off" class="layui-input" />
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label" id="shopLabel">调入名称</label>
						<div class="layui-input-inline">
							<select name="shopId" id="shopId">
							    <option value=""></option>
								<c:forEach items="${shopList }" var="s">
								<c:if test="${s.id!=shops.id }">
									<option value="${s.id }">${s.shopName}</option>
								</c:if>
								</c:forEach>
							</select>
						</div>
					</div>
					<button class="layui-btn layui-btn-normal" data-type="reload"
						lay-filter="searchBtn" type="button">搜索</button>
				</fieldset>
				<div class="layui-tab" lay-filter="tab">
					<ul class="layui-tab-title">
						<li class="layui-this">调入记录</li>
						<li>调出记录</li>
					</ul>
					<div class="layui-tab-content">
						<div class="layui-tab-item layui-show">
							<div class="layui-form" id="table-list">
								<table class="layui-table" id="dbInRecordInfo"
									lay-filter="tableInfo"></table>
							</div>
						</div>
						<div class="layui-tab-item">
							<div class="layui-form" id="table-list">
								<table class="layui-table" id="dbOutRecordInfo"
									lay-filter="tableInfo"></table>
								<script type="text/html" id="barLine">
                                <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="payData">付款</a>
                                <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="destoryData">作废</a>
                                </script>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>

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
	var　 url = "productAllocation/getList.do";
	//初始化网格
	var tableIns = table.render({
		elem: '#dbInRecordInfo',
		url: url,
		page: true,
		method:'post',
		where:{
			"kind":2,
			"dateRangeStartTime": startTime,
			"dateRangeEndTime": endTime,
			"enable":1
			},
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 'full-300',
		defaultToolbar: ['print', 'exports'],
		done: function(res, curr, count) {},
		cols: [
			[{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center',
			}, {
				field: 'addtime',
				width: '10%',
				align: 'center',
				title: '调拨日期'
			}, {
				field: 'calloutName',
				width: '15%',
				align: 'center',
				title: '调出产品'
			},{
				field: 'relationShopName',
				width: '20%',
				align: 'center',
				title: '调入门店'
			},{
				field: 'callinName',
				width: '15%',
				align: 'center',
				title: '调入产品'
			},{
				field: 'count',
				width: '10%',
				align: 'center',
				title: '数量'
			},{
				field: 'amount',
				width: '15%',
				align: 'center',
				title: '总价'
			},{
				field: 'payType',
				width: '15%',
				align: 'center',
				title: '支付方式',
				templet: function(row) {
					return getPayId(row);
				}
			}]
		]
	});
	
	table.render({
		elem: '#dbOutRecordInfo',
		url: url,
		page: true,
		method:'post',
		where:{
			"kind":1,
			"dateRangeStartTime": startTime,
			"dateRangeEndTime": endTime,
			"enable":1
			},
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 'full-300',
		defaultToolbar: ['print', 'exports'],
		done: function(res, curr, count) {},
		cols: [
			[{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center',
			}, {
				field: 'addtime',
				width: '10%',
				align: 'center',
				title: '调拨日期'
			},{
				field: 'relationShopName',
				width: '20%',
				align: 'center',
				title: '调出门店'
			}, {
				field: 'calloutName',
				width: '15%',
				align: 'center',
				title: '调出产品'
			},{
				field: 'callinName',
				width: '15%',
				align: 'center',
				title: '调入产品'
			},{
				field: 'count',
				width: '10%',
				align: 'center',
				title: '数量'
			},{
				field: 'amount',
				width: '10%',
				align: 'center',
				title: '总价'
			},{
				field: 'payType',
				width: '10%',
				align: 'center',
				title: '支付方式',
				templet: function(row) {
					return getPayId(row);
				}
			},{
				field: 'opt',
				width: '10%',
				align: 'center',
				title: '操作',
				templet: function(row) {
					return getOptHtml(row);
				}
			}]
		]
	});
}

	layui.use([ 'jquery', 'table', 'laydate', 'form', 'element' ], function() {
		var table = layui.table;
		var $ = layui.jquery;
		var form = layui.form;
		var laydate = layui.laydate;
		var element = layui.element;
		element.on('tab(tab)', function(data){
			if(data.index==0){
				$("#shopLabel").html("调入门店");
			}else if(data.index==1){
				$("#shopLabel").html("调出门店");
			}
			});
		laydate.render({
			elem : "#dateInput",
			range : true,
			value : getRangeDate()
		});
		form.render();
		//查询事件
		var active = {
			reload : function() {
				// 执行重载
				table.reload('dbInRecordInfo', {
					page : {
						curr : 1
					// 重新从第 1 页开始
					},
					where : {
						"callinName" : $.trim($("#callinName").val()),
						"calloutName" : $.trim($("#calloutName").val()),
						"kind":2,
						"enable":1,
						"shopId":$("#shopId").val(),
						"dateRangeStartTime" : getStartTime(),
						"dateRangeEndTime" : getEndTime()
					}

				});
				table.reload('dbOutRecordInfo', {
					page : {
						curr : 1
					// 重新从第 1 页开始
					},
					where : {
						"callinName" : $.trim($("#callinName").val()),
						"calloutName" : $.trim($("#calloutName").val()),
						"kind":1,
						"enable":1,
						"relationShop":$("#shopId").val(),
						"dateRangeStartTime" : getStartTime(),
						"dateRangeEndTime" : getEndTime()
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
	
	function userDefinedToolHandle(target, obj){
		var $ = layui.jquery;
	    var data = obj.data;
		var id=data.id;
		switch (obj.event) {
			case 'payData':
		  		var url="productAllocation/addDBPay.do";
		  		var amount=data.amount;
				var openw="700px";
				var openh="400px";
				layer.open({
					type: 2,
					title: "调拨付款",
					area: [openw, openh],
					fixed: false, //不固定
					content: url,
					btn: ['确定', '关闭'],
				    success: function(layero, index){
				    	var body = layer.getChildFrame('body', index);
				    	 body.find("#payAmount").val(amount);
				      },
					yes: function(index, layero){
						var body = layer.getChildFrame('body', index); //得到iframe页的body内容
						var payId = body.find("#payId").val();
						var data={"id":id,"payType":payId};
						var result=savePayInfo(data);
						if(result.retCode=='success'){
							layer.close(index);
							layer.msg(result.retMsg, {icon : 1});
							layui.table.reload('dbOutRecordInfo');
						}else{
							layer.msg(result.retMsg, {icon : 2});
						}
							
					}
				});
				break;
			case 'destoryData':
				layer.confirm('确认作废调拨单吗？',{icon: 3, title:'提示'}, function(index){
					 if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
						 setYesBtnDisable("disabled");
						var result=saveDestoryData(id);
						if(result.retCode=='success'){
							layer.close(index);
							layer.msg(result.retMsg, {icon : 1});
							layui.table.reload('dbOutRecordInfo');
						}else{
							layer.msg(result.retMsg, {icon : 2});
						}
					 }
				});
				break;
		}
	}
	//付款
	function savePayInfo(data){
		var $=layui.$;
		var status;
		$.ajax({
			type : "POST",
			url : "productAllocation/savePayInfo.do",
			data : data,
			dataType : "json",
			async:false,
			success : function(result) {
				status=result;
			}
		});
		return status;
	}
	//付款方式
	function getPayId(row){
		if(row.payType==1)
			return "现金";
		else if(row.payType==2)
			return "其它";
		else if(row.payType==3)
			return "微信";
		else if(row.payType==4)
			return "支付宝";
		else
			return "";
	}
	//能否操作
	function getOptHtml(row){
		if(row.payType)
			return "";
		else{
			var tpl = barLine.innerHTML;
			return tpl;
		}
			
	}
	//作废
	function saveDestoryData(id){
		  var res=null;
			layui.$.ajax({
				type : "POST",
				url : "productAllocation/saveDestoryData.do",
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

