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
		<title></title>
		<link href="lib/mui/css/mui.min.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="lib/mui/css/mui.picker.min.css" />
		<link href="lib/mui/css/mui.poppicker.css" rel="stylesheet" />
		<link href="lib/mui/css/style.css" rel="stylesheet" />
		<script src="lib/mui/js/mui.min.js"></script>
		<script src="lib/mui/js/mui.picker.min.js"></script>
		<script src="lib/jquery/jquery-3.4.1.js"></script>
		<style>
		
		</style>

	</head>

	<body>
		<header class="mui-bar mui-bar-nav">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">连途门店管理系统</h1>
		</header>
		<div class="mui-content">
			<div class="mui-content-padded" style="margin: 5px;">
				<form class="mui-input-group">
					<div class="mui-input-row">
						<label>门店名称：</label>
						<input type="text" class="" placeholder="" value="${currentShopName }" readonly="readonly"  id="shopName" >
						<input type="hidden"  id="shopId" name="shopId" value="${currentShopId }">
					</div>
					<div class="mui-input-row">
						<label>预约手机号：</label>
						<input type="text" class="mui-input-clear" id="cellphone" name="cellphone" placeholder="请输入手机号">
					</div>
					<div class="mui-input-row">
						<label>车牌号：</label>
						<input type="text" class="mui-input-clear" id="carNumber" name="carNumber" placeholder="请输入车牌号">
					</div>
					<div class="mui-input-row">
						<label>预约项目：</label>
						<input type="text" class="" placeholder="请选择预约项目" id="itemName"  readonly="readonly">
						<input type="hidden"  id="itemId" name="itemId">
					</div>
					
					<div class="mui-input-row">
						<label>预约时间：</label>
						<input type="text"  placeholder="请选择预约时间"  readonly="readonly" id="appointTime" name="appointTime">
					</div>
					<div class="mui-input-row" style="margin: 10px 5px;height: auto;">
						<textarea id="remark" name="remark" rows="5" placeholder="请输入预约备注"></textarea>
					</div>
					<div class="mui-button-row">
						<button type="button" class="mui-btn mui-btn-primary"  id="appoint">预约</button>&nbsp;&nbsp;
						<button type="button" class="mui-btn mui-btn-danger" >取消</button>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>
<script>
/*
document.getElementById("inOrderList").addEventListener('tap', function() {
	  mui.openWindow({
		    url: 'wechat/orders/orderList/3.do' 
		  });
});*/

(function(mui, doc) {
	mui.init();
	mui.ready(function() {
		var userPicker = new mui.PopPicker();
		userPicker.setData([{
			value: '1',
			text: '保养'
		}, {
			value: '2',
			text: '打蜡'
		}, {
			value: '3',
			text: '洗车'
		}, {
			value: '4',
			text: '其他'
		}]);
		
		doc.getElementById('itemName').addEventListener('tap', function(event) {
			userPicker.show(function(items) {
				mui("#itemId")[0].value = items[0].value;
				mui("#itemName")[0].value = items[0].text;
			});
		}, false);
		
		var shopPicker = new mui.PopPicker();
		var shops='${shopList}';
		shopPicker.setData(JSON.parse(shops));
		
		doc.getElementById('shopName').addEventListener('tap', function(event) {
			shopPicker.show(function(items) {
				mui("#shopId")[0].value = items[0].value;
				mui("#shopName")[0].value = items[0].text;
			});
		}, false);
		
		 doc.getElementById('appointTime').addEventListener('tap', function(event) {
				var _self = this;
				_self.picker = new mui.DtPicker({});
				_self.picker.show(function(rs) {
					/*
					 * rs.value 拼合后的 value
					 * rs.text 拼合后的 text
					 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
					 * rs.m 月，用法同年
					 * rs.d 日，用法同年
					 * rs.h 时，用法同年
					 * rs.i 分（minutes 的第二个字母），用法同年
					 */
					 mui("#appointTime")[0].value =  rs.text;
				});
			}, false);
		 
		 
			doc.getElementById('appoint').addEventListener('tap', function(event) {
				$("#appoint").attr("disabled","disabled");
				var param = {cellphone:mui("#cellphone")[0].value.trim(),carNumber:mui("#carNumber")[0].value.trim()
						,itemId:mui("#itemId")[0].value.trim(),remark:mui("#remark")[0].value.trim(),
						appointTime: mui("#appointTime")[0].value.trim()};
				
				if(param.cellphone==''){
					mui.alert('请输入手机号', '提示', function() {
						$("#appoint").removeAttr("disabled");
					});
					return false;
				}
				
				if(!checkMobile(param.cellphone)){
					mui.alert('请输入正确手机号', '提示', function() {
						$("#appoint").removeAttr("disabled");
					});
					return false;
				}
				
				if(param.carNumber ==''){
					mui.alert('请输入车牌号', '提示', function() {
						$("#appoint").removeAttr("disabled");
					});
					return false;
				}
				if(param.itemId ==''){
					mui.alert('请选择预约项目', '提示', function() {
						$("#appoint").removeAttr("disabled");
					});
					return false;
				}
				if(param.appointTime ==''){
					mui.alert('请选择预约时间', '提示', function() {
						$("#appoint").removeAttr("disabled");
					});
					return false;
				}
				
				$.ajax({
					type : 'post',
					url : "wechat/appoint/addAppoint.do",
					data : param,
					dataType : 'json',
					success : function(res) {
						if (res.retCode == 'success'){
							mui.alert("预约成功", '提示', function() {
								  mui.openWindow({
									    url: 'wechat/index.do' 
									  });
							});	
						}else{
							mui.alert(res.retMsg, '提示', function() {
								$("#appoint").removeAttr("disabled");
							});	
						}
						
					},
					error : function(res) {
						mui.alert("预约失败", '提示', function() {
							$("#appoint").removeAttr("disabled");
						});
					}
				});
				
			}, false);
	});
})(mui, document);


//校验手机号
function  checkMobile(str) {
	var re = /^1\d{10}$/
	if (re.test(str)) {
		return true;
	} else {
		return false;
	}
}
</script>

