<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<title>车型查找</title>
	<link href="lib/mui/css/mui.min.css" rel="stylesheet" />
	<link href="lib/mui/css/mui-icons-extra.css" rel="stylesheet" />
	<link href="lib/mui/css/mui.indexedlist.css" rel="stylesheet" />
	<link href="lib/mui/css/app.css" rel="stylesheet" />
	<script src="lib/mui/js/mui.min.js"></script>
	<script src="lib/jquery/jquery-3.4.1.js"></script>
	<script src="lib/jquery/jsrender.js"></script>

	<style>
		.car-infos-title{
			line-height: 40px;
			height: 40px;
			border-bottom: 1px solid #d4d4d4;
		}
		.car-infos-title span{
			font-weight: bold;
			font-size: 16px;
			line-height: 40px;
			text-align: left;
			vertical-align: top;
			color: #424242;
		}
		.car-infos-title img{
			width: 20px;
			height: 20px;
			margin-top: 10px;
			margin-left: 10px;
			margin-right: 5px;
		}

		.car-categorys{
			width: 100%;
		}
		.car-categorys .car-category{
			width: calc(25% - 20px);
			min-width: 60px;
			text-align: center;
			line-height: 30px;
			height: 30px;
			margin: 8px;
			display: inline-block;
			border-radius: 6px;
			background-color: #e4e4e4;
			font-size: 15px;
		}
		.car-categorys .car-category.active{
			background: #0367fd;
			color: white;
		}


		.mui-grid-view.mui-grid-9 .mui-table-view-cell{
			padding: 0px;
		}
		.mui-grid-view.mui-grid-9 .mui-table-view-cell>a:not(.mui-btn){
			padding: 5px 0px;
		}

		.brand-item-img{
			border: 1px solid #e2e2e2;
			border-radius: 4px;
			box-shadow: 0 0 10px 0px #b3b2b2;
			background-color: white;
		}
		.brand-item-img img{
			display: block;
			height: 55px;
			max-height: 60px;
			width: auto;
			max-width: 100%;
			margin: 0 auto;
		}
		.mui-table-view.mui-grid-view .mui-table-view-cell .mui-media-body{
			margin-top:3px;
			padding-left: 3px;
			padding-right: 3px;
			font-size: 13px;
		}
		.empty-tips{
			display: none;
			margin: calc(50% - 50px) auto;
			width: 180px;
			height: auto;
		}
		.mui-table-view-cell>a:not(.mui-btn).mui-active{
			background-color: transparent;
		}
		.mui-grid-view.mui-grid-9{
			background-color: transparent;
		}
	</style>

	<style>
		.search-box{
			display: none;
			position: absolute;
			top: 0px;
			left: 0px;
			bottom: 0px;
			right: 0px;
			background-color: rgba(111, 111, 111, 0.32);
			padding: 30px 25px;
		}

		.search-box .mui-card {
			height: 100%;
			width: 100%;
			margin: 0;
			overflow: visible;
		}

		.search-box .img-btn-close {
			position: absolute;
			right: -10px;
			top: -10px;
			width: 30px;
			height: 30px;
			border-radius: 50%;
			border: 1px solid #ddd;
			background-color: white;
			background-image: url("image/btn_close.png");
			background-position: center;
			background-size: 100%;
		}

		.search-box .mui-card-content {
			height: calc(100% - 50px);
			overflow-y: auto;
			overflow-x: hidden;
		}

		.mui-ios .mui-indexed-list-empty-alert, .mui-indexed-list-empty-alert {
			padding: 40px 15px !important;
		}

		li.mui-table-view-cell {
			font-size: 13px;
		}

		li.mui-table-view-cell .mui-navigate-right {
			overflow: hidden;
			white-space: nowrap;
			text-overflow: ellipsis;
			margin-right: 10px;
		}

		#search-box .mui-list-load-more {
			display: none;
			width: 100%;
		}
	</style>
</head>
<body>
	<div class="brand-box">
		<div class="car-infos-title"><img src="image/wx/icon_car.png"/><span>选择车系</span>
			<span class="mui-icon mui-icon-search mui-pull-right" onclick="$('#search-box').fadeIn();"
				  style="margin-right: 10px;font-size: 22px"></span>
		</div>
		<div id="car-categorys" class="car-categorys">
			<c:forEach items="${categorys}" var="cate" varStatus="i">
				<div class="car-category ${i.first ? 'active' : ''}" data-id="${cate.id}">${cate.category}</div>
			</c:forEach>
		</div>
		<div class="car-infos-title"><img src="image/wx/icon_car.png"/><span>选择品牌</span></div>
		<ul id="car-brands" class="mui-table-view mui-grid-view mui-grid-9" style="padding:10px;"></ul>
		<img class="empty-tips" src="image/no_datas.png"/>
	</div>
	<div id="search-box" class="search-box">
		<div class="mui-card">
			<div class="mui-card-header">
				<span class="img-btn-close" onclick="searchHide();"></span>
				<div class="mui-indexed-list-search mui-input-row mui-search">
					<input id="search" type="search" class="mui-input-clear mui-indexed-list-search-input" placeholder="搜索车型关键字">
					<input id="search-page" type="hidden" value="0"/>
				</div>
			</div>
			<div class="mui-card-content">
				<div class="mui-indexed-list-empty-alert">没有数据</div>
				<ul class="mui-table-view"></ul>
				<button type="button" data-loading-icon="mui-spinner mui-spinner-custom"
						data-loading-text="加载中..."
						class="mui-btn mui-btn-outlined mui-list-load-more">加载更多
				</button>
			</div>
		</div>
	</div>
</body>

<script type="text/x-jsrender" id="searchListBoxTpl">
	{{for}}
		<li data-value="{{:firstChar}}" class="mui-table-view-cell mui-media">
			<a href="wechat/carModel/model/">
				<div class="mui-media-body mui-navigate-right">{{:modelName}}</div>
			</a>
		</li>
	{{/for}}




</script>
<script type="text/x-jsrender" id="carModelListBoxTpl">
{{for}}
	<li class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-2">
		<a href="wechat/carModel/series/{{:id}}.do?name={{:brandName}}">
			<span class="mui-icon brand-item-img"><img src="mobile/shop/getImage.do?path=car/{{:logoUrl}}" onerror="this.onerror='';this.src='image/no_picture.jpg'"></span>
			<div class="mui-media-body">{{:brandName}}</div>
		</a>
	</li>
{{/for}}
</script>

<script type="text/javascript">
	mui.init({
		swipeBack:true,
	});

	mui.ready(function () {
		renderCarBrandView();
		mui('body').on('tap','.brand-item a',function(){
			window.top.location.href=this.href;
		}).on('tap', '.car-category', function(){
			$(".car-categorys .car-category").removeClass("active");
			$(this).addClass("active");
			renderCarBrandView();
		});

		$("#search-box #search").on("change", function (e) {
			$("#search-box #search-page").val("0");
			$("#search-box .mui-table-view").empty();
			$("#search-box button.mui-list-load-more").hide();

			mui.post("wechat/community/car/search.do", {
				search: e.delegateTarget.value, page: 0, limit: 20
			}, function (res) {
				if (res == null || res.length == 0) {
					$("#search-box .mui-indexed-list-empty-alert").show();
				} else {
					$("#search-box .mui-indexed-list-empty-alert").hide();
					var html = $("#searchListBoxTpl").render(res);
					$("#search-box .mui-table-view").append(html);
					$("#search-box button.mui-list-load-more").show();
				}
			});
		});
		mui(document.body).on('tap', '#search-box .mui-list-load-more', function (e) {
			console.log(this);
			if ($(this).hasClass("none-data")) {
				$(this).text("已加载全部");
			} else {
				mui(this).button('loading');
				var page = $("#search-box #search-page").val() * 1 + 1;
				mui.ajax("wechat/community/car/search.do", {
					data: {search: e.delegateTarget.value, page: page, limit: 20},
					dataType: 'json',
					type: 'post',
					timeout: 10000,
					success: function (res) {
						$("#search-box #search-page").val(page);
						if (res == null || res.length == 0) {
							$(this).text("已加载全部");
							$(this).addClass("none-data");
						} else {
							$(this).text("加载更多");
							$(this).removeClass("none-data");
							var html = $("#searchListBoxTpl").render(res);
							$("#search-box .mui-table-view").append(html);
						}
						mui(this).button('reset');
					},
					error: function (xhr, type, errorThrown) {
						$(this).text("加载失败, 请重试!");
						mui(this).button('reset');
					}
				});
			}
		});

	});

	/**
	 * 根据车系修改品牌列表
	 * @param category
	 */
	function renderCarBrandView() {
		var id = $(".car-categorys .car-category.active").attr("data-id");
		mui.post('wechat/community/brands.do',{category : id},function(data){
			$("#car-brands").empty();
			if(data == null || data.length == 0){
				$(".empty-tips").css("display", "block");
				$("#car-brands").css("display", "none");
				return;
			}else{
				$(".empty-tips").css("display", "none");
				$("#car-brands").css("display", "block");
			}
			var html = $("#carModelListBoxTpl").render(data);
			$("#car-brands").append(html);
		});
	}


	function searchHide() {
		$("#search-box #search-page").val(0);
		$("#search-box #search").val("");
		$("#search-box ul.mui-table-view").empty();
		$("#search-box button.mui-list-load-more").hide();
		$('#search-box').fadeOut();
	}
</script>
</html>
