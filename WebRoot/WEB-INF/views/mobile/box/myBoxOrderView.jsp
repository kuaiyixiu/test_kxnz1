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
		<script src="lib/jquery/jquery-3.4.1.js"></script>
		<script src="lib/jquery/jsrender.js"></script>
		
		<style type="text/css">
			.mui-media-body .carImageBox {
				height: 100px;
				width: 100px;
				background: white;
				border: 1px solid black;
				display: inline-flex;
    			align-items: center;
			    margin-right: 5px;
			}
			/* .mui-media-body .carImage {
			    width: auto;
				height: auto;
				margin: 0 auto;
				max-height:100%;
				max-width: 100%;
			} */
			.mui-media-body .carImage {
			    width: auto;
				height: 100px;
				margin-right:5px;
				max-height:100%;
				max-width: 100%;
			} 
			#zoomImageContent{
				height: calc(100% - 44px);
				width: 100%;
				background-color: rgb(0,0,0,0.5);
				display: none;
				position: fixed;
				top: 44px;
				left: 0;
				padding: 15px;
				align-items: center;
			}
			#zoomImageContent img{
				width: 100%;
				max-height:100%;
				height: auto;
			}
		</style>
		
	</head>

	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">我的盒子</h1>
			<span class="mui-icon mui-icon-refreshempty mui-pull-right" style="color: #007aff;font-size: 32px;" id="refreshBtn"></span>
			
		</header>
		<div class="mui-content" id="refreshContainer">
			<ul class="mui-table-view" id="boxDiv">
			</ul>
			<div class="mui-content" id="zoomImageContent" onclick="$(this).fadeOut('slow')">
				<img alt="" src=""/>
			</div>
		</div>
		
	</body>
</html>
<!-- <p class="mui-ellipsis">
							<span class="carImageBox">
								<img class="carImage" src="/mobile/shop/getImage.do?path={{:workerImgUrl}}" onclick="zoomImage(this)"/>
							</span>
						</p> -->
<script type="text/x-jsrender" id="boxTpl">
{{for}}
	<li class="mui-table-view-cell mui-media">
		<a href="javascript:;" class="">
			<div class="mui-media-body">
				{{:createTimeStr}}
				<p class="mui-ellipsis"><span class="carTitle">车牌号码：{{:carNumber}}</span></p>
				<p class="mui-ellipsis"><span class="carTitle">订单状态：{{:statusName}}</span></p>
				{{if workerUser != null}}
					<p class="mui-ellipsis"><span class="carTitle">师傅姓名：{{:workerUser.userRealname}}</span></p>
					<p class="mui-ellipsis"><span class="carTitle">师傅手机：{{:workerUser.userPhone}}</span></p>
					<p class="mui-ellipsis"><span class="carTitle">师傅备注：{{:workerRemark}}</span></p>
					{{if workerImgUrl != null}}
                     	<p class="mui-ellipsis">
								<img class="carImage" src="mobile/shop/getImage.do?path={{:workerImgUrl}}" onclick="zoomImage(this)"/>
						</p>
                    {{/if}}
				{{/if}}
				{{if status == 2 || status == -1}}
					<button type="button" class="mui-btn mui-btn-primary openLock" data-id="{{:id}}" data-text="开箱">开箱</button>
				{{/if}}
				{{if status == 0 }}
					<button type="button" class="mui-btn mui-btn-primary openLock" data-id="{{:id}}" data-text="取消订单">取消订单</button>
				{{/if}}
			</div>
		</a>
	</li>
{{/for}}
</script>

<script type="text/javascript">
	initModule();
	
   /**
    * 初始化模块
    * 
    */
	function initModule(){
		bindEvent();
	}
	
	/**
    * 绑定事件
    * 
    */
	function bindEvent(){
		renderOrderView();
		
		mui('body').on('tap','.openLock',function(){//我的车辆
			var orderId = $(this).attr("data-id")
			var buttonText = $(this).attr("data-text");
			var btnArray = ['取消', '确定'];
			var message = "确定"+buttonText+"?";
            mui.confirm(message, '提示', btnArray, function(e) {
                if (e.index == 1) {
                    ownerOperateOrder(orderId);
                }
            });
		}).on('tap', '#refreshBtn', function(){
			mui.toast("刷新中");
			renderOrderView();
		});
	}
	
	/**
    * 渲染订单
    * 
    */
	function renderOrderView(){
		mui.post('wechat/box/queryProcessingOrder.do',function(data){
			if(data.retCode != "success"){
				mui.alert("查询订单失败");
				return false
			}
			
			renderOrder(data.retData);
		});
	}
	
	/**
    * 渲染订单
    * 
    */
	function renderOrder(data){
		$("#boxDiv").empty();
		if(!data || data.length == 0){
			mui.alert("订单为空");
			return false;
		}
		
		var html = $("#boxTpl").render(data);
		$("#boxDiv").append(html);
	}
	
	/**
    * 车主开箱/取消订单
    * 
    */
	function ownerOperateOrder(orderId){
		mui.post('wechat/box/ownerOperateOrder.do',{
			"orderId": orderId
		},function(data){
			if(data.retCode != "success"){
				var message = data.retMsg? data.retMsg : "操作失败";
				mui.alert(message);
				return false
			}
			mui.alert(data.retMsg);
			renderOrderView();
		});
	}

	/**
	* 图片放大缩小
	*/
	function zoomImage(element){
		$("#zoomImageContent img").attr("src", element.src);
		$("#zoomImageContent").fadeIn("slow");
		$("#zoomImageContent").css("display", "flex");
	}
</script>