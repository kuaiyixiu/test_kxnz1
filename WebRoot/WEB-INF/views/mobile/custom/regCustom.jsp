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
		<script src="lib/jquery/jquery.autocompleter.js"></script>
		<script src="lib/jquery/jquery-ui.js"></script>
		<link rel="stylesheet" type="text/css" href="css/wechatKxnz.css" />
	</head>

<style>
.ui-menu-item {
	list-style: none;
}

.ui-menu{
	left: -25px !important;
}

</style>

<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">自助开卡</h1>
		</header>
		<div class="mui-content">
			<form class="mui-input-group">
				<div class="mui-input-row">
					<input id='custName' type="text" class="mui-input-clear mui-input" maxlength="20" placeholder="会员姓名">
				</div>
				<div class="mui-input-row">
					<input id='cellphone' type="text" class="mui-input-clear mui-input" placeholder="手机号">
				</div>
				<div class="mui-input-row">
					<input id='carDataList' type="text" class="mui-input-clear mui-input" placeholder="车牌号">
				</div>
				<div class="mui-input-row">
					<input style="padding: 10px 15px;-webkit-user-select: auto;" id="shopId" data-id="" class="mui-input mui-input-clear" autocomplete="off" placeholder="请输入门店关键字">
				</div>
			</form>
			<div class="mui-content-padded" style="padding-top: 120px;">
				<button id='reg' class="mui-btn mui-btn-block mui-btn-primary">注册</button>
			</div>
		</div>

	</body>
</html>
					
<script type="text/javascript">
	var shops = '${shops}';
	mui.init()
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
		})
	});
		
	$("#reg").click(
		function(){
                if(!checkData()){
                     
                  return ;
                 } 
			$.ajax({
				type : "POST",
				url : "wechat/custom/saveCustom.do",
				dataType : "json",
				contentType:"application/x-www-form-urlencoded; charset=UTF-8",
				data : getRegParam(),
				success : function(data) {
					if (data.retCode == 'success'){
					  	  mui.openWindow({
							url: 'wechat/index.do' 
						  });
					}else{
						mui.alert('自助开卡失败', function() {});
					}
				}
			});
		}
	)
	
	// 注册参数
	function getRegParam(){
		var data = {
			"custName": $("#custName").val(),
			"cellphone": $("#cellphone").val(),
			"shopId": $("#shopId").attr("data-id"),
			"carDataList": $("#carDataList").val().toUpperCase()
		}
		
		return data;
	}
	//校验参数
   function	checkData (){
      var custName =  $("#custName").val();
      if(!custName.trim().length){

    	  mui.alert("会员姓名不能为空");
         return false;
       }
      var phone =  $("#cellphone").val();
      var myreg = /^[1][3,4,5,7,8][0-9]{9}$/; 
      if (!myreg.test(phone)) {

    	  mui.alert("手机号码不正确");
          return false;
	 }
	  if (!isLicensePlate($("#carDataList").val().toUpperCase())){

		  mui.alert("请输入正确的车牌号");
	      return false;
	  }
	  if(!$("#shopId").attr("data-id").trim().length){

		  mui.alert("请选择门店");
	      return false;
	 }
		 return true ;
	}

   //校验车牌号
   function isLicensePlate(str) {
       return /^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$/.test(str);
   }
</script>

