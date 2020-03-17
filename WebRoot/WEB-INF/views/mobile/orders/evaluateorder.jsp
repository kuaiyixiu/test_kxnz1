<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
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
				.mui-icon-star{color: #B5B5B5;font-size: 30px;}
	    	.mui-icon-star-filled{color: #FFB400;font-size: 30px;} 
	   		ul{padding: 10px 0px;}
	    	li{display: -webkit-box;width: 100%;} 
	    	li a{-webkit-box-flex: 1;display: block;font-size: 15px;line-height: 30px;color: #000;}
	    	li div{-webkit-box-flex: 2;text-align: left;}
	    	#info{padding: 20px 10px;}
		
		</style>

	</head>

	<body>
		<header class="mui-bar mui-bar-nav">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">订单评价</h1>
		</header>
		<div class="mui-content">
			<div class="mui-content-padded" style="margin: 5px;">
				<form class="mui-input-group">
					<div class="mui-input-row">
						<label>订单编号：</label>
						<input type="text" class=""	 value="${orders.orderNo }" readonly="readonly" disabled="disabled">
					</div>
					<div class="mui-input-row">
						<label>创建时间：</label>
						<input type="text" value="<fmt:formatDate value="${orders.creatTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" id="appointTime" name="appointTime">
					</div>
					<div class="mui-input-row">
						<label>施工车辆：</label>
						<input type="text" class=""	 value="${orders.carNumber }" readonly="readonly" disabled="disabled">
					</div>
					<ul class="mui-table-view">
						<li class="star-li">
							<label style="padding: 11px 15px;" >用户评分：</label>
							<div class="icons">
								<i data-index="1" class="mui-icon mui-icon-star"></i>
								<i data-index="2" class="mui-icon mui-icon-star"></i>
								<i data-index="3" class="mui-icon mui-icon-star"></i>
								<i data-index="4" class="mui-icon mui-icon-star"></i>
								<i data-index="5" class="mui-icon mui-icon-star"></i>
							</div>
						</li>
					</ul>
					<input type="hidden" id="stars" name="stars">
					<div class="mui-input-row" style="margin: 10px 5px;height: auto;">
						<textarea id="remark" name="remark" rows="5" placeholder="请输入预约备注"></textarea>
					</div>

					<div class="mui-button-row">
						<button type="button" class="mui-btn mui-btn-primary"  id="evaluate">评价</button>&nbsp;&nbsp;
						<button type="button" class="mui-btn mui-btn-danger" >取消</button>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>

		<script type="text/javascript" charset="utf-8">
			mui.init();
		      mui('.icons').on('tap','i',function(){
		      	var index = parseInt(this.getAttribute("data-index"));//获取当前元素的索引值
		      	var parent = this.parentNode;//获取当前元素的父节点
		      	var children = parent.children;//获取父节点下所有子元素
		      	if(this.classList.contains("mui-icon-star")){//判断当前节点列表中是否含有.mui-icon-star元素
		      		for(var i=0;i<index;i++){//亮星
		      				children[i].classList.remove('mui-icon-star');
		      				children[i].classList.add('mui-icon-star-filled');
		      		}
		      	}else{//重置已经亮星的元素
		      		for (var i = index; i < 5; i++) {
		      			children[i].classList.add('mui-icon-star')
		      			children[i].classList.remove('mui-icon-star-filled')
		      		}
		      	}
		      		$("#stars").val(index);
		      })
		      
		      mui('body').on('tap','#evaluate',function(){
		    	  $("#evaluate").attr("disabled","disabled");
			      var stars = $("#stars").val();
			      if (stars == ''){
			  		mui.alert('请评分', '提示', function() {
						$("#evaluate").removeAttr("disabled");
					});
					return false;
			      }
			      var remark = $("#remark").val();
			      var param = {'stars':stars,'remark':remark,'orderId':'${orders.id}'};
		    	  
		    		$.ajax({
		    			type : 'post',
		    			url : "wechat/orders/addEvaluate.do",
		    			data : param,
		    			dataType : 'json',
		    			async:false,
		    			success : function(res) {
		    				if (res.retCode == 'success'){
								mui.alert("评价成功", '提示', function() {
									  mui.openWindow({
										    url: 'wechat/index.do' 
										  });
								});	
							}else{
								mui.alert(res.retMsg, '提示', function() {
									$("#evaluate").removeAttr("disabled");
								});	
							}
		    			},
		    			error : function() {
		    				mui.alert("评价失败", '提示', function() {
								$("#evaluate").removeAttr("disabled");
							});
		    			}
		    		});
				})
		</script>

</script>

