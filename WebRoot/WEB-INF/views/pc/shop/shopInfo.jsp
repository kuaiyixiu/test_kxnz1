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
<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> -->
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>连途</title>
<%@ include file="../base.jsp"%>
<script type="text/javascript">
	function userDefinedLoadGrd($, table) {
		var tableIns = table.render({
			elem : '#shopInfo',
			url : "shop/queryShops.do",
			method : 'post',
			page : true,
			limit : 10,
			limits : [ 10, 20, 30, 40, 50 ],
			toolbar : '#toolbarTop',
			where : {
				"shopName" : $("#shopName").val().trim(),
				"bossId" : $("#bossId").val()
			},
			cols : [ [ {
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center',
			}, {
				field : 'shopName',
				align : 'center',
				title : '门店名'
			}, {
				field : 'shopAddress',
				align : 'center',
				title : '门店地址'
			}, {
				field : 'shopTel',
				align : 'center',
				title : '门店联系方式'
			}, {
				field : 'userCount',
				align : 'center',
				title : '员工数'
			}, {
				field : 'smsAmount',
				align : 'center',
				title : '短信数量'
			},  {
				field : 'opt',
				align : 'center',
				title : '操作',
				width: '20%',
				templet : function(row) {
					return renderOptHtml(row);
				}
			} ] ]
		});

		// 查询事件
		var active = {
			reload : function() {
				// 执行重载
				tableIns.reload({
					page : {
						curr : 1
					// 重新从第 1 页开始
					},
					where : {
						"shopName" : $("#shopName").val().trim(),
						"bossId" : $("#bossId").val()
					}
				});
			}
		};

		$('.searchDiv .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});

		/**
		 * 渲染操作界面
		 * 
		 * @param data
		 */
		function renderOptHtml(data) {
			var html = barLine.innerHTML;

			// 状态html
			var statusHtmlMap = {
				"0" : disabled.innerHTML, // 运行中展示禁用按钮
				"-1" : running.innerHTML
			// 禁用中展示运行按钮
			}

			var status = data.accountStatus;
			if (statusHtmlMap[status]) {
				html += statusHtmlMap[status];
			}
			
			debugger;
			// 如果开启夜间服务则可新增盒子/编辑
			if(data.openNightServeFlag == 0){
				html += addBoxTpl.innerHTML;
				//html += editBoxTpl.innerHTML;
			}

			return html;
		}

	}

	function userDefinedToolBarHandle(target, obj) {
		var $ = layui.jquery;
		
		switch (obj.event) {
		case 'addShop':
			var openw = "700px";
			var openh = "400px";
			var index = layer.open({
				type : 2,
				title : "添加门店",
				area : [ openw, openh ],
				fixed : false, //不固定
				content : 'shop/addShopView.do',
				maxmin : true,
				btn : [ '确定', '关闭' ],
				yes : function(index, layero) {
					if (!layui.$(".layui-layer-btn0").hasClass(
							"layui-btn-disabled")) {
						setYesBtnDisable("disabled");
						//$(layero).find("iframe")[0].contentWindow.layer.load(1, {shade: [0.1,'#000']});
						var body = layer.getChildFrame('body', index); //得到iframe页的body内容
						var shopName = body.find("#shopName").val();
						var shopTel = body.find("#shopTel").val();
						var shopAddress = body.find("#shopAddress").val();
						if ($.trim(shopName) == "") {
							layer.msg("门店名不能为空", {
								icon : 2
							});
							setYesBtnDisable("undisabled");
							return;
						}
						if ($.trim(shopTel) == "") {
							layer.msg("门店电话不能为空", {
								icon : 2
							});
							setYesBtnDisable("undisabled");
							return;
						}
					      var myreg = /^[1][3,4,5,7,8][0-9]{9}$/; 
					      if (!myreg.test(shopTel)) {

					    	  layer.msg("门店电话不正确", {
									icon : 2
								});
								setYesBtnDisable("undisabled");
								return;
						 }
						if ($.trim(shopAddress) == "") {
							layer.msg("门店地址不能为空", {
								icon : 2
							});
							setYesBtnDisable("undisabled");
							return;
						}
						debugger;
						var openFlag = -1;
						if(body.find("#openNightServeFlag").is(":checked")){
							openFlag = 0;
						}
						
						var data = {
							"shopName" : shopName,
							"shopTel" : shopTel,
							"shopAddress" : shopAddress,
							"bossId" : $("#bossId").val(),
							"openNightServeFlag": openFlag
						};
						debugger;
						var result = saveShopInfo(data);
						layer.closeAll('loading');
						if (result.retCode == 'success') {
							layer.close(index);
							layui.table.reload('shopInfo');
							layer.msg(result.retMsg, {
								icon : 1
							});
						} else {
							layer.msg(result.retMsg, {
								icon : 2
							});
						}
					}

				}
			});
			break;
		case 'back':
			window.location.href = "shop/bossInfo.do";
			break;
		}
	}

	function userDefinedToolHandle(target, obj) {
		var event = obj.event;
		var data = obj.data;
		var id = data.id;
		var disabled = "-1";
		var running = "0";

		if (event == "loginShop") {// 登录门店
			changeShop(id);
		}

		if (event == "disabled") {
			updateShop(id, disabled, "禁用");
		}

		if (event == "running") {
			updateShop(id, running, "启用");
		}

		if (event == "editShop") {// 编辑
			editShop(id);
		}
		if (event == "addSmsCount") {// 编辑
			addSmsCount(id);
		}
		
		if(event == "addBox"){
			addBoxAjax(id);		
		}
		
		return false;
	}
	
	function addSmsCount(id){
		var $=layui.$;
		var openw = "500px";
		var openh = "200px";
		var index = layer.open({
			type : 2,
			title : "增加短信数量",
			area : [ openw, openh ],
			fixed : false, //不固定
			content : 'shop/addSmsCount.do',
			maxmin : true,
			btn : [ '确定', '关闭' ],
			yes : function(index, layero) {
				if (!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")) {
					setYesBtnDisable("disabled");
					var body = layer.getChildFrame('body', index); //得到iframe页的body内容
					var smsAmount = body.find("#smsAmount").val();
					if ($.trim(smsAmount) == ""&&Number(smsAmount)<0) {
						layer.msg("短信数量输入不合法,请重新输入", {
							icon : 2
						});
						setYesBtnDisable("undisabled");
						return;
					}
					var data = {
						"addQuantity" : smsAmount,
						"id":id
					};
					var result = updateShopSmsCount(data);
					layer.closeAll('loading');
					if (result.retCode == 'success') {
						layer.close(index);
						layui.table.reload('shopInfo');
						layer.msg("短信数量添加成功", {
							icon : 1
						});
					} else {
						setYesBtnDisable("undisabled");
						layer.msg("短信数量添加失败", {icon : 2});
					}
				}

			}
		});
	}
	
	function updateShopSmsCount(data) {
		var $ = layui.$;
		var status;
		$.ajax({
			type : "POST",
			url : "shop/updateShopSmsCount.do",
			data : data,
			dataType : "json",
			async : false,
			success : function(result) {
				status = result;
			}
		});
		return status;
	}
	

	function changeShop(id) {
		var $ = layui.jquery;
		$.ajax({
			type : "POST",
			url : "changeShopByAdmin.do",
			data : {
				"shopId" : id
			},
			dataType : "json",
			success : function(result) {
				if (result.retCode == 'success') {
					top.window.location.replace("index.do")
				} else {
					layer.msg(result.retMsg, {
						icon : 2
					});
				}
			}
		});
	}

	function saveShopInfo(data) {
		var $ = layui.$;
		var status;
		$.ajax({
			type : "POST",
			url : "shop/saveShopInfo.do",
			data : data,
			dataType : "json",
			async : false,
			success : function(result) {
				status = result;
			}
		});
		return status;
	}

	function updateShop(id, status, title) {
		var $ = layui.jquery;
		var tip = "确认" + title + "?";
		layer.confirm(tip, function(i) {
			if (!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")) {
				setYesBtnDisable("disabled");
				layer.close(i);
				$.ajax({
					type : "POST",
					url : "shop/updateShops.do",
					data : {
						"id" : id,
						"accountStatus" : status
					},
					dataType : "json",
					success : function(result) {
						if (result.retCode == 'success') {
							layui.table.reload('shopInfo');
						} else {
							layer.msg(result.retMsg, {
								icon : 2
							});
						}
					}
				});
			}

		});
	}

	function editShop(id) {
		var $=layui.$;
		var openw = "700px";
		var openh = "400px";
		var index = layer.open({
			type : 2,
			title : "编辑门店",
			area : [ openw, openh ],
			fixed : false, //不固定
			content : 'shop/addShopView.do?id='+id,
			maxmin : true,
			btn : [ '确定', '关闭' ],
			yes : function(index, layero) {
				if (!layui.$(".layui-layer-btn0")
						.hasClass("layui-btn-disabled")) {
					setYesBtnDisable("disabled");
					var body = layer.getChildFrame('body', index); //得到iframe页的body内容
					var shopName = body.find("#shopName").val();
					var shopTel = body.find("#shopTel").val();
					var shopAddress = body.find("#shopAddress").val();
					if ($.trim(shopName) == "") {
						layer.msg("门店名不能为空", {
							icon : 2
						});
						setYesBtnDisable("undisabled");
						return;
					}
					if ($.trim(shopTel) == "") {
						layer.msg("门店电话不能为空", {
							icon : 2
						});
						setYesBtnDisable("undisabled");
						return;
					}
				      var myreg = /^[1][3,4,5,7,8][0-9]{9}$/; 
				      if (!myreg.test(shopTel)) {

				    	  layer.msg("门店电话不正确", {
								icon : 2
							});
							setYesBtnDisable("undisabled");
							return;
					 }
					if ($.trim(shopAddress) == "") {
						layer.msg("门店地址不能为空", {
							icon : 2
						});
						setYesBtnDisable("undisabled");
						return;
					}
					var data = {
						"shopName" : shopName,
						"shopTel" : shopTel,
						"shopAddress" : shopAddress,
						"bossId" : $("#bossId").val(),
						"id":id
					};
					var result = saveShopInfo(data);
					layer.closeAll('loading');
					if (result.retCode == 'success') {
						layer.close(index);
						layui.table.reload('shopInfo');
						layer.msg(result.retMsg, {
							icon : 1
						});
					} else {
						layer.msg(result.retMsg, {
							icon : 2
						});
					}
				}

			}
		});
	}
	
	function addBoxAjax(id){
		var $=layui.$;
		var openw = "700px";
		var openh = "400px";
		var index = layer.open({
			type : 2,
			title : "添加盒子",
			area : [ openw, openh ],
			fixed : false, //不固定
			content : 'shop/addBoxView.do?id='+id,
			maxmin : true,
			btn : [ '确定', '关闭' ],
			yes : function(index, layero) {
				if (!layui.$(".layui-layer-btn0")
						.hasClass("layui-btn-disabled")) {
					setYesBtnDisable("disabled");
					showLoading();
					var body = layer.getChildFrame('body', index); //得到iframe页的body内容
					var shopId = body.find("#shopId").val();
					debugger;
					var boxInfoJsonList = getBoxInfo(body);
					if(boxInfoJsonList == ""){
						layer.msg("至少添加一条盒子信息", {
							icon : 2
						});
						layer.closeAll('loading');
						
						return false;
					}
					var data = {
						"boxInfoJsonList": boxInfoJsonList,
						"shopId": shopId
					};
					
					$.ajax({
						type : "POST",
						url : "shop/addBox.do",
						data : data,
						dataType : "json",
						async : false,
						success : function(result) {
							if (result.retCode == 'success') {
								layer.close(index);
								layui.table.reload('shopInfo');
								layer.msg(result.retMsg, {
									icon : 1
								});
							} else {
								layer.msg(result.retMsg, {
									icon : 2
								});
							}
						}
					});
					layer.closeAll('loading');
				}
			}
		});
	}
	
	function getBoxInfo(body){
		var $=layui.$;
		var $args = body.find(".boxModule");
		var shopId = body.find("#shopId").val();
		var list = [];
		$.each($args, function(index, item){
			var boxName = $(item).find("[data-id='boxName']").val();
			var identifier = $(item).find("[data-id='identifier']").val();
			var remark = $(item).find("[data-id='remark']").val();
			if(boxName == "" || identifier == ""){
				return;
			}
			var data = {
				"boxName": boxName,
				"identifier": identifier,
				"remark": remark,
				"shopId": shopId
			}
			list.push(data);
		})
		if(list.length == 0){
			return ""
		}
	
		return JSON.stringify(list);
	}
</script>
</head>
<body>
	<input type="hidden" name="bossId" id="bossId" value="${bossId }">
	<div class="page-content-wrap">
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<div class="layui-fluid">
						<div class="layui-row">
							<div class="layui-col-md8">
								<div class="layui-form-item">
									<label class="layui-form-label">门店信息</label>
									<div class="layui-input-block">
										<input type="text" id="shopName" placeholder="门店名称"
											autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-col-md2" align="center">
								<button class="layui-btn layui-btn-normal" data-type="reload"
									lay-filter="searchBtn" type="button">搜索</button>
							</div>
						</div>
					</div>
				</fieldset>

			</div>
		</form>

		<div class="layui-form" id="table-list">

			<table class="layui-table" id="shopInfo" lay-filter="tableInfo"></table>
		</div>
	</div>
</body>

</html>

<div style="display:none;">
	<input data-shopId="username" value='${ user.userName }'> <input
		data-shopId="password" value='${ user.userPassword }'> <input
		data-shopId="shopId" value='${ shopId }'>
</div>

<script type="text/html" id="barLine">                     
	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="loginShop">进入门店</a>
	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="editShop">编辑</a>
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="addSmsCount">短信充值</a>
</script>

<script type="text/html" id="disabled">                     
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="disabled">禁用</a>
</script>

<script type="text/html" id="running">                     
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="running">启用</a>
</script>

<script type="text/html" id="addBoxTpl">                     
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="addBox">盒子</a>
</script>

<script type="text/html" id="toolbarTop">
	<div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm kxnz-btn-default" lay-event="back" id="back"><i class="layui-icon">&#xe65c;</i>后退</button>
		<button class="layui-btn layui-btn-sm kxnz-btn-default" lay-event="addShop"><i class="layui-icon">&#xe608;</i>新增门店</button>
	</div>
</script>