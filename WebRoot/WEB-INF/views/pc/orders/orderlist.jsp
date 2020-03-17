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
<%@ include file="../base.jsp"%>
<link rel="stylesheet" type="text/css" href="css/fieldset.css" />
<link rel="stylesheet" type="text/css" href="lib/admin/css/admin.css" />
<style type="text/css">
	.create-order {
    	background-color: #F1F1F1;
    	padding: 20px 0px;
    	border-radius: 5px 5px;
    	width: 60%;
    	margin-bottom: 20px;
	}
	.showCar{
	color: #2ea9df;
	text-decoration: underline;
}
</style>
</head>
<body>
	<div class="page-content-wrap">
	<c:if test="${pageType eq '1' }">
	<div class="create-order" >
		  <div class="layui-row">
		    <div class="layui-col-sm4">
		      <div class="layui-row">
		        <div class="col-sm-6 col-sm-offset-3">
		          <button type="button" class="layui-btn   layui-btn-normal" onclick="addOrder(1);" style="margin-top: 22px;">临牌开单</button>
		        </div>
		      </div>
		    </div>
		    <div class="layui-col-sm8" style="border-left: 1px solid #ccc;">
			    <div class="layui-row">
			      <div class="layui-col-sm10 layui-col-sm-offset1">
			        <div class="">
			        <input id="orderCarNo" name="orderCarNo" class="layui-input"  value="" placeholder="请输入车牌号"  style="margin-bottom: 10px;" autocomplete="off" />
			        </div>
			        <div class="layui-row">
			          <div class="layui-col-sm5">
			            <button type="button" class="layui-btn   layui-btn-normal" onclick="addOrder(2);">开单</button>
			          </div>
			        </div>
			      </div>
			    </div>
		    </div>
		  </div>
	</div>
		</c:if>
	<form class="layui-form">
		<div class="layui-form-item searchDiv">
			<fieldset>
				<legend>查询条件</legend>
				<div class="layui-inline">
					<label class="layui-form-label">订单编号</label>
					<div class="layui-input-inline">
						<input type="text" name="orderNo" id="orderNo" placeholder="请输入订单编号" autocomplete="off"  class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">车牌号</label>
					<div class="layui-input-inline">
						<input type="text" name="carNumber" id="carNumber" placeholder="请输入车牌号" autocomplete="off"  class="layui-input">
					</div>
				</div>
				<div class="layui-inline" <c:if test="${pageType ne '1' }">style="display: none;"</c:if> >
					<label class="layui-form-label">订单状态</label>
					<div class="layui-input-inline">
						<select name="orderStatus" id="orderStatus">
							<option value="">全部</option>
							<option value="0">作废</option>
							<option value="1">开单中</option>
							<option value="2">待施工</option>
							<option value="3">施工中</option>
							<option value="4">施工完成</option>
							<option value="7">反入账</option>
							<option value="8">反挂账</option>
						</select>
					</div>
				</div>
				<div class="layui-inline" <c:if test="${pageType eq '4' }">style="display: none;"</c:if>>
					<label class="layui-form-label">订单类型</label>
					<div class="layui-input-inline">
						<select name="orderType" id="orderType">
							<option value="">全部</option>
							<option value="0">临牌开单</option>
							<option value="1">非会员开单</option>
							<option value="2">会员开单</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
				      <label class="layui-form-label">开单日期</label>
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
			<table class="layui-table" id="ordersTable" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            
            </script>
			<script type="text/html" id="barLine">
            	<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="updateOrder"  tabId="ordersTable">详情</a>
                <a class="layui-btn layui-btn-xs" lay-event="toPrint">凭据</a> 
                <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="toPrintReceipt">小票</a> 
				<c:if test="${pageType ne '4' }">
					{{#  if(d.orderStatus === 1 || d.orderStatus === 7 || d.orderStatus === 8  ){ }}
        	    	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-url="orders/deleteOrders.do" tabId="ordersTable">作废</a>
  					{{#  } }} 
					{{#  if(d.orderStatus === 6){ }}
            		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="updateOrder"  tabId="ordersTable">解除挂账</a>
            		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="updateOrder"  tabId="ordersTable">还款</a>
  					{{#  } }} 
					{{#  if(d.orderStatus === 5){ }}
            		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="updateOrder"  tabId="ordersTable">反入账</a>
  					{{#  } }} 
				</c:if>
			

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
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	var laydate = layui.laydate

	laydate.render({
		elem: "#dateInput",
		range: true,
		value: getRangeDate()
	});
	var　 url = "orders/getList.do";
	//初始化网格
	var tableIns = table.render({
		elem: '#ordersTable',
		url: url,
		page: true,
		method: 'post',
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 'auto',
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		where: {
			"custName":$("#custName").val(),
			"cardNo":$("#cardNo").val(),
			"dateRangeStartTime": getStartTime(),
			"dateRangeEndTime": getEndTime(),
			<c:if test="${pageType eq '1' }">
			"notInOrderStatus": '5,6',
			</c:if>
			<c:if test="${pageType eq '2' }">
			"orderStatus": '6',
			</c:if>
			<c:if test="${pageType eq '3' }">
			"orderStatus": '5',
			</c:if>
			<c:if test="${pageType eq '4' }">
			"orderStatus": '56',
			</c:if>
			"pageType" : ${pageType}
		},
		done: function(res, curr, count) {
		
		},
		cols: [
			[
			 /*
			 {
				type: 'checkbox',
				fixed: 'left',
				width: '5%'
			} ,*/
			{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center'
			}, {
				field: 'orderNo',
				width: '15%',
				align: 'center',
				title: '订单编号',
				templet: function(row) {
					return getOrderNo(row);
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
				field: 'orderStatus',
				width: '10%',
				align: 'center',
				title: '订单状态',
				templet: function(row) {
					return getOrderStatus(row);
				}
			}, {
				field: 'orderType',
				width: '8%',
				align: 'center',
				title: '订单类型',
				templet: function(row) {
					return getOrderType(row);
				}
			}, {
				field: 'custInfo',
				width: '12%',
				align: 'center',
				title: '会员信息',
				templet: function(row) {
					return getCustInfo(row);
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
				field: 'creatTime',
				width: '15%',
				align: 'center',
				title: '开单时间',
				templet: function(row) {
					return formatDate(row.creatTime);
				}
			},{
				field: 'opt',
				width: '20%',
				align: 'center',
				title: '操作',
				toolbar: '#barLine',
				templet: function(row) {
					return getBarRow(row);
				}
			}]
		]
	});
	
	var active = {
			reload: function() {
				// 执行重载
				tableIns.reload({
					method: 'post',
					page: {
						curr: 1
					},
					where: {
						"custName":$("#custName").val(),
						"cardNo":$("#cardNo").val(),
						"dateRangeStartTime": getStartTime(),
						"dateRangeEndTime": getEndTime(),
						"orderNo": $.trim($("#orderNo").val()),
						"carNumber": $.trim($("#carNumber").val()),
						"orderType": $.trim($("#orderType").val()),
						<c:if test="${pageType eq '1' }">
						"orderStatus": $.trim($("#orderStatus").val()),
						"notInOrderStatus": '5,6',
						</c:if>
						<c:if test="${pageType eq '2' }">
						"orderStatus": '6',
						</c:if>
						<c:if test="${pageType eq '3' }">
						"orderStatus": '5',
						</c:if>
						<c:if test="${pageType eq '4' }">
						"orderStatus": '56',
						</c:if>
						"pageType" : ${pageType}
					}
				});
			}
		};

		$('.searchDiv .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});

}


var orderFrmId = 'orderFrm';

function addOrder(kind){
	var $ = layui.jquery;
	if (kind == 2){ //车牌开单
		if (!isLicensePlate($("#orderCarNo").val())){
			layer.msg('请输入正确的车牌号', {icon:7});  
			return false;
		}
	}
	var orderurl = "";
	var title = "";
	if (kind == 1){
		title = "临时开单";
		orderurl = 'orders/addOrder/'+kind+'.do';
	}else{
		title = "开单";
		//orderurl = 'orders/addOrder/'+kind+'.do?carNumber='+encodeURI(encodeURI($.trim($("#carNumber").val())));
		orderurl = 'orders/addOrder/'+kind+'.do?carNumber='+$.trim($("#orderCarNo").val());
	}
	layui.use('element', function(){
		var element = parent.layui.element;
		var id = orderFrmId;
		element.tabDelete('tab', id);
		element.tabAdd('tab', {
			title: title,
			content: '<iframe src="' + orderurl + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
			id: id
		});
		element.tabChange('tab', id);
	});	
	
}




function updateOrders(orderId,pageType){
	layui.use('element', function(){
		var element = parent.layui.element;
		var id = orderFrmId;
		var url = 'orders/toUpdateOrder/'+orderId+'/'+pageType+' .do';
		element.tabDelete('tab', id);
		element.tabAdd('tab', {
			title: "修改订单",
			content: '<iframe src="' + url + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
			id: id
		});
		element.tabChange('tab', id);
	});
}



function userDefinedToolHandle($arg, obj) {
	var data = obj.data
	var $ = layui.jquery;
	if (obj.event === 'updateOrder') { //修改订单
		updateOrders(data.id,2);
	}else if (obj.event === 'payOrder'){
		updateOrders(data.id,3);
	}else if (obj.event === 'toPrint'){
		toPrint(data.id);
	}else if (obj.event === 'toPrintReceipt'){
		toPrintReceipt(data.id);
	}
}

/**
 * 新增tab页
 */
 
function toPrintReceipt(orderId){
	layui.use(['jquery', 'element'], function(){
		var $=layui.jquery;
		var element = parent.layui.element;
		var pageType='${pageType}';
		var id = 'openReceiptFrmId';
		var title="凭据小票";
		/*
		if(pageType=="2"){//挂账订单凭据
			id = 'openGZFrmId';
			title="挂账凭据";
		}else if(pageType=="3"){//入账订单凭据
			id = 'openRZFrmId';
			title="入账凭据";
		}
		*/
		var url="orders/toPrintReceipt/"+pageType+"/"+orderId+".do"
		element.tabDelete('tab', id);
		element.tabAdd('tab', {
			title: title,
			content: '<iframe src="' + url + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
			id: id
		});
		element.tabChange('tab', id);
	});
}

function toPrint(orderId){
	layui.use(['jquery', 'element'], function(){
		var $=layui.jquery;
		var element = parent.layui.element;
		var pageType='${pageType}';
		var id = 'openBillFrmId';
		var title="结算单";
		/*
		if(pageType=="2"){//挂账订单凭据
			id = 'openGZFrmId';
			title="挂账凭据";
		}else if(pageType=="3"){//入账订单凭据
			id = 'openRZFrmId';
			title="入账凭据";
		}
		*/
		var url="orders/toprint/"+pageType+"/"+orderId+".do"
		element.tabDelete('tab', id);
		element.tabAdd('tab', {
			title: title,
			content: '<iframe src="' + url + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
			id: id
		});
		element.tabChange('tab', id);
	});
}

//订单类型  0临牌开单，1非会员开单 2
function getOrderType(row){
	var orderType = row.orderType;
	if (orderType == "0")
		return "临牌开单";
	else if (orderType == "1")
		return "非会员开单";
	else if (orderType == "2")
		return "会员开单";
	return "";
}


//订单状态  1编辑，2待施工（已提交），3施工中，  4施工完成  5入账 6挂账
function getOrderStatus(row){
	var orderStatus = row.orderStatus;
	if (orderStatus == "0")
		return "作废";
	else if (orderStatus == "1")
		return "开单中";
	else if (orderStatus == "2")
		return "待施工";
	else if (orderStatus == "3")
		return "施工中";
	else if (orderStatus == "4")
		return "施工完成";
	else if (orderStatus == "5")
		return "入账";
	else if (orderStatus == "6")
		return "挂账";
	else if (orderStatus == "7")
		return "反入账";
	else if (orderStatus == "8")
		return "反挂账";
	return "";
}	

function getOrderAmt(row){
	<c:if test="${pageType eq '2' }">
		return  parseFloat(row.orderAmt).toFixed(2)+' 挂账金额:'+parseFloat(row.debtAmt).toFixed(2)  ;
	</c:if>
	<c:if test="${pageType ne '2' }">
		return  parseFloat(row.orderAmt).toFixed(2);
	</c:if>
}

function getCustInfo(row){
	if(row.orderType == 2){ //2会员开单
		return row.custom.cardNo + '-'+ row.custom.custName;
	}else{
		return "非会员";
	}
}

function getCarNumber(row){
	var carNumber = row.carNumber;
	if (!!carNumber){
		return "<a href='javascript:void(0);' onClick='showCar("+"\""+carNumber+"\""+")' class='showCar'>"+carNumber+"</a>";
	}else{
		return '临牌';
	}
}
function getOrderNo(row){
	return "<a href='javascript:void(0);' onClick='updateOrders("+"\""+row.id+"\""+",2)' class='showCar'>"+row.orderNo+"</a>";
}

function showCar(carNumber){
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
	var option = {
			type : 2,
			title : '编辑车辆',
			area : [ '700px', '500px' ],
			fixed : false, //不固定
			content : [ 'car/editCarViewByNumber.do?carNumber='+carNumber, 'no' ],
			success : function(layero, index) {
				layer.iframeAuto(index);
			}
		};
		layer.open(option);
	});
	
}

function getBarRow(row){
	var tpl = barLine.innerHTML;
	// 如果不存在，则最后一个选中
	layui.laytpl(tpl).render(row, function(html) {
		return html;
	});
}


layui.use(['yutons_sug'], function() {
	//创建yutons_sug搜索框提示插件||输入框提示插件实例
	var yutons_sug = layui.yutons_sug;

	sessionStorage.setItem("url", "orders/getCarInfoList.do");
	
	//初始化部门输入提示框
	yutons_sug.render({
		id: "orderCarNo", //设置容器唯一id
		type: "sug", //设置输入框提示类型：sug-下拉框，sugTable-下拉表格
		url: sessionStorage.getItem("url") + "?carNumber=" //设置异步数据接口,url为必填项
	});
});

function getRangeDate() {
	var $ = layui.jquery;
	// 如果已有选择的日期
	var date = $("#dateInput").val();
//	if (date) {
	return date;
//	}
	/*
	var date1 = new Date();
	date1.setDate(1);
	var date2 = new Date();
	var fromDate = formatDate1(date1);
	var nowDate = formatDate1(date2);
	return fromDate + " - " + nowDate*/
}

function getStartTime() {
	var time = getRangeDate();
	if (time == '')
		return "";
	var startTime = time.substr(0, 10) + " 00:00:00";

	return startTime;
}

function getEndTime() {
	var time = getRangeDate();
	if (time == '')
		return "";
	var endTime = time.substr(13) + " 23:59:59";

	return endTime;
}





</script>

