<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html class="ui-page-login">
<base href="<%=basePath%>">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="lib/mui/css/mui.picker.min.css" />
		<link href="lib/mui/css/mui.min.css" rel="stylesheet" />
		<link href="lib/mui/css/mui.poppicker.css" rel="stylesheet" />
		<link href="lib/mui/css/style.css" rel="stylesheet" />
		<script src="lib/mui/js/mui.min.js"></script>
		<script src="lib/mui/js/mui.picker.min.js"></script>
		<script src="lib/jquery/jquery-3.4.1.js"></script>
		<link rel="stylesheet" type="text/css" href="css/wechatKxnz.css" />
	</head>

<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">填写下单信息</h1>
		</header>
		<div class="mui-content">
			<form class="mui-input-group">
				<div class="mui-input-row">
					<label class="addLable">车牌号：</label>
					<input class="addInput" id='carNumber' type="text"  placeholder="您的车牌号" value="${car.carNumber}">
				</div>
				<div class="mui-input-row">
					<label class="addLable">车品牌：</label>
					<input class="addInput" id='carBrand' type="text" placeholder="您的车品牌" value="${car.carBrand}">
				</div>
				<div class="mui-input-row">
					<label class="addLable">手机号：</label>
					<input class="addInput" id='cellPhone' type="text" placeholder="您的手机号" value="${custom.cellphone}">
				</div>
				<div class="mui-input-row">
					<input id='serveId' data-id="" type="text" class="mui-input-clear mui-input" placeholder="选择服务">
				</div>
			</form>
			<div class="mui-input-row" style="margin: 10px 5px;">
				<textarea id="customRemark" rows="3" placeholder="请输入备注"></textarea>
			</div>

		<h1 class="mui-title" style="color:red; margin-top: -20px;">下单后请关好门锁，避免钥匙丢失！</h1>				
			<div class="mui-content-padded">
				<lable id="priceLab">价格：</lable>
				<button id='addBoxOrder' class="mui-btn mui-btn-block mui-btn-primary">下单</button>
			</div>
			<input type="hidden" id="lockId" value="${lockId}">
			<input type="hidden" id="customId" value="${custom.id}">
		</div>
	</body>
</html>


<script type="text/javascript">
	mui.init()
	
	mui('body').on('tap','#addBoxOrder',function(){
		 addBoxOrder();
	});
	
	var serves = '${serves}';
	serves = JSON.parse(serves);
	mui.ready(function() {
		var userPicker = new mui.PopPicker();
		userPicker.setData(serves);
		
		document.getElementById('serveId').addEventListener('tap', function(event) {
			userPicker.show(function(items) {
				$("#serveId").attr("data-id", items[0].value);
				$("#serveId").val(items[0].text);
				$("#priceLab").text("价格："+items[0].price);
			});
		}, false);
	});

	function addBoxOrder(){
		var serverId = $("#serveId").attr("data-id")
		if(serverId == ""){
			mui.alert("请选择服务");
		 	return false;
		}
	
		var addParam = {
			"lockId": $("#lockId").val(),
			"serviceId": $("#serveId").attr("data-id"),
			"customId": $("#customId").val(),
			"status": 0
		};
	
		var carNumber = $("#carNumber").val();
		if(carNumber == ""){
		 	mui.alert("请填写车牌号");
		 	return false;
		}
		addParam.carNumber = carNumber;
		
		var carBrand = $("#carBrand").val();
		if(carBrand == ""){
		 	mui.alert("请填写车辆品牌");
		 	return false;
		}
		addParam.carBrand = carBrand;
		
		var customPhone = $("#cellPhone").val();
		if(customPhone == ""){
		 	mui.alert("请填写手机号");
		 	return false;
		}
		addParam.customPhone = customPhone;
		
		var customRemark = $("#customRemark").val();
		if(customRemark == ""){
			mui.alert("请填写备注信息");
		 	return false;
		}
		addParam.customRemark = customRemark;


		mui.post('wechat/box/checkBalance.do', addParam ,function (data){
			if (data.retCode == "success") {

				addOrder(addParam);
				
			}else{
				var msg = "下单失败";
				if(data && data.retMsg){
					msg = data.retMsg;
				}
				var btnArray = ['取消', '确定'];
	            mui.confirm(msg, '提示', btnArray, function(e) {
	                if (e.index == 1) {
	                	addOrder(addParam);
	                }
	            });
			}
			},'json'

		)
	}
	function addOrder(addParam){
		mui.post('wechat/box/addBoxOrder.do', addParam ,function(data){
					if (data.retCode == "success") {
						mui.toast("下单成功");
						mui.openWindow({
						    url: 'wechat/box/myBoxOrderView.do?'
						});
						return false;
					}else{
						mui.alert(data.retMsg);		
						return false;			
					}
					
					mui.alert("下单失败");
			},'json'
		);

    }
	
</script>

