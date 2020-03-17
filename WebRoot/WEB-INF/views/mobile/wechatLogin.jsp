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
		<link href="lib/mui/css/mui.min.css" rel="stylesheet" />
		<link href="lib/mui/css/style.css" rel="stylesheet" />
		<script src="lib/mui/js/mui.min.js"></script>
		<script src="lib/mui/js/mui.picker.min.js"></script>
		<script src="lib/jquery/jquery-3.4.1.js"></script>
		<script src="lib/jquery/jquery.autocompleter.js"></script>
		<script src="lib/jquery/jquery-ui.js"></script>
		
		<link rel="stylesheet" type="text/css" href="css/kxnz.css" />
		<link rel="stylesheet" type="text/css" href="css/wechatKxnz.css" />
	</head>
<style>
.ui-menu-item {
	list-style: none;
}

.ui-menu{
	left: -25px !important;
}

.ui-helper-hidden-accessible{
	display: none !important;
}
</style>

<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">会员绑定</h1>
		</header>
		<div class="mui-content">
			<form id='login-form' class="mui-input-group">
				<div class="mui-input-row">
					<input id='cardNo' type="text" class="mui-input-clear mui-input" placeholder="会员卡号">
				</div>
				<div class="mui-input-row">
					<input id='shopId' style="padding: 10px 15px;-webkit-user-select: auto;" data-id="" class="mui-input mui-input-clear"  autocomplete="off" placeholder="请输入门店关键字">
				</div>
				<div id="div"></div>
				
			</form>
			
			<div class="mui-content-padded" style="padding-top: 150px;">
				<button id='login' type="button" class="mui-btn mui-btn-block mui-btn-primary">登录</button>
				<div class="link-area"><a id='reg' >自助开卡</a>
				</div>
			</div>
			<div class="mui-content-padded oauth-area">

			</div>
		</div>
		
	    <form class="mui-popover" id="popover"  style="max-height: 250px;">

		</form>
	</body>
</html>

<script type="text/javascript" charset="utf-8">
	var shops = '${shops}';
	mui.init();
	
	mui.ready(function() {
		shops = JSON.parse(shops);
		$("#shopId").autocomplete({
		    source: shops,
			select: function (event, ui) {
				$("#shopId").val(ui.item.value);
				$("#shopId").attr("data-id", ui.item.lable)
				event.stopPropagation(); 
				return false;
			}
		}).focus(function(){
			$(this).autocomplete("search", "");
		});
	});
	
	mui('body').on('tap','#reg',function(){
  	  mui.openWindow({
		    url: 'wechat/custom/regIndex.do' 
		  }); 
	})
	mui('body').on('tap','#login',function(){
			var cardNo = $("#cardNo").val();
			var shopId = $("#shopId").attr("data-id")
			if(!cardNo || !shopId){
				alert("请填写完整信息");
				return false;
			}
			$.ajax({
				type : "POST",
				url : "wechat/isHasCustom.do",
				dataType : "json",
				data : {
					"cardNo": cardNo,
					"shopId": shopId
				},
				async:false,
				before : function() {
					$("#login").attr('disabled', 'disabled');
					$("#login").text('登录中');
				},
				complete: function () {
					$("#login").removeAttr('disabled');
				},
				success : function(data) {
					if (data.retCode == 'success'){
						 mui.openWindow({
			    			url: 'wechat/index.do' 
			  			}); 
					}else{
						mui.alert('该会员不存在', function() {});
					}
				}
			});	
	})
	

    
</script>

