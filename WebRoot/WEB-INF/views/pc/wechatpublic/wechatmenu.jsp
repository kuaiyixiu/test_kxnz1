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
<link rel="stylesheet" href="css/wechatmenu.css">
<script src="js/plugin/Sortable.min.js"></script>
<style>
.fsh-rightPanel {
	background: #fff;
}

.weixin-body {
	height: 550px;
}
</style>
<script type="text/javascript">
    function userDefinedLoadForm(){
		var layer = layui.layer;
		var $ = layui.$;
		var table = layui.table;
		var menu = null, responseData = null, responselist = {};
		getWxMenuData();
		//获取菜单数据
		function getWxMenuData() {
			$.ajax({url : "wechatmenu/list.do",
					success : function(result) {
						var obj = JSON.parse(result);
						if (obj.retCode == 'success') {
							menu = obj.retData&& obj.retData.button ? obj.retData.button: [];
							responseData = obj.retData&& obj.retData.msgs ? obj.retData.msgs: [];
							$.each(responseData, function(_, v) {
								responselist[v.id] = {
									title : v.title,
									msgType : v.type
								}
							});
							wxMenuInit();
						}else {
							layer.msg(obj.retMsg, {
								icon : 2
							});
						}
					}
				});
		}
		//菜单初始化
		function wxMenuInit() {
			String.prototype.subByte = function(start, bytes) {
				for ( var i = start; bytes > 0; i++) {
					var code = this.charCodeAt(i);
					bytes -= code < 256 ? 1 : 2;
				}
				return this.slice(start, i + bytes)
			};
			var init_menu = function(menu) {
				var str = "";
				var items = menu;
				var type = "", action = "", msgType = "";
				for (i in items) {
					if (items[i]['sub_button'] != undefined) {
						type = action = "";
					} else {
						type = items[i]['type'];
						if (items[i]['url'] != undefined)
							action = "url|" + items[i]['url'];
						if (items[i]['msgId'] != undefined)
							action = "key|" + items[i]['msgId'];
						if (items[i]['msgType'] != undefined)
							msgType = items[i]['msgType'];
					}
					str += '<li id="menu-' + i + '" class="menu-item" data-type="' + type + '" data-msgtype="' + msgType + '" data-action="' + action + '" data-name="' + items[i]['name'] + '"> <a href="javascript:;" class="menu-link"> <i class="icon-menu-dot"></i> <i class="weixin-icon sort-gray"></i> <span class="title">'
							+ items[i]['name'] + '</span> </a>';
					var tem = '';
					if (items[i]['sub_button'] != undefined) {
						var sub_menu = items[i]['sub_button'];
						for (j in sub_menu) {
							type = sub_menu[j]['type'];
							if (sub_menu[j]['url'] != undefined)
								action = "url|"
										+ sub_menu[j]['url'];
							if (sub_menu[j]['key'] != undefined)
								action = "key|"
										+ sub_menu[j]['msgId'];
							if (sub_menu[j]['msgType'] != undefined)
								msgType = sub_menu[j]['msgType'];
							tem += '<li id="sub-menu-' + j + '" class="sub-menu-item" data-type="' + type + '" data-msgtype="' + msgType + '" data-action="' + action + '" data-name="' + sub_menu[j]['name'] + '"> <a href="javascript:;"> <i class="weixin-icon sort-gray"></i><span class="sub-title">'
									+ sub_menu[j]['name']
									+ '</span></a> </li>';
						}
					}
					str += '<div class="sub-menu-box" style="'
							+ (i != 0 ? 'display:none;' : '')
							+ '"> <ul class="sub-menu-list">'
							+ tem
							+ '<li class=" add-sub-item"><a href="javascript:;" title="添加子菜单"><span class=" "><i class="weixin-icon add-gray"></i></span></a></li> </ul> <i class="arrow arrow-out"></i> <i class="arrow arrow-in"></i></div>';
					str += '</li>';
				}
				$("#add-item").before(str);
			};
			//初始化菜单
			init_menu(menu);
			//拖动排序
			new Sortable($("#menu-list")[0], {
				draggable : 'li.menu-item'
			});
			$(".sub-menu-list").each(function() {
				new Sortable(this, {
					draggable : 'li.sub-menu-item'
				});
			});
			//添加主菜单
			$("#menu-container").on('click','#add-item',function() {
				var menu_item_total = $(".menu-item").size();
				if (menu_item_total < 3) {
					var item = '<li class="menu-item" data-type="view" data-action="key|" data-name="添加菜单" > <a href="javascript:;" class="menu-link"> <i class="icon-menu-dot"></i> <i class="weixin-icon sort-gray"></i> <span class="title">添加菜单</span> </a> <div class="sub-menu-box" style=""> <ul class="sub-menu-list"><li class=" add-sub-item"><a href="javascript:;" title="添加子菜单"><span class=" "><i class="weixin-icon add-gray"></i></span></a></li> </ul> <i class="arrow arrow-out"></i> <i class="arrow arrow-in"></i> </div></li>';
					var itemDom = $(item);
					itemDom.insertBefore(this);
					itemDom.trigger("click");
					$(".sub-menu-box", itemDom).show();
					new Sortable($(".sub-menu-list",itemDom)[0],{draggable : 'li.sub-menu-item'});
					}
			});
			//删除菜单
			$("#menu-container").on('click','#item_delete',function() {
				var current = $("#menu-list li.current");
				var prev = current.prev("li[data-type]");
				var next = current.next("li[data-type]");
				var last;
				if (prev.size() == 0&& next.size() == 0&& $(".sub-menu-box",current).size() == 0) {
					last = current.closest(".menu-item");
				} else if (prev.size() > 0|| next.size() > 0) {
					last = prev.size() > 0 ? prev: next;
				} else {
					last = null;
					$(".weixin-content-wrap").hide();
					$(".no-weixin-content").show();
				}
				$("#menu-list li.current").remove();
				if (last) {
					last.trigger('click');
				} else {
					$("input[name='item-title']").val('');
				}
				updateChangeMenu();
			});
			
			//更新修改与变动
			var updateChangeMenu = function() {
				var title = $("input[name='item-title']").val();
				var key = "url", value;
				value = $("input[name='" + key + "']").val();
				var msgtype = $("input[name='" + key + "']").attr("data-msgtype");
				var currentItem = $("#menu-list li.current");
				if (currentItem.size() > 0) {
					currentItem.attr('data-type', 'view');
					currentItem.attr('data-msgtype', msgtype);
					currentItem.attr('data-action', key + "|"+ value);
					if (currentItem.siblings().size() == 4) {
						$(".add-sub-item").show();
					} else if (false) {

					}
					currentItem.children("a").find("span").text(title.subByte(0, 16));
					$("input[name='item-title']").val(title);
					currentItem.attr('data-name', title);
					$('#current-item-name').text(title);
				}
			};
			
			//获取菜单数据
			var getMenuList = function() {
				var menus = new Array();
				var sub_button = new Array();
				var menu_i = 0;
				var sub_menu_i = 0;
				var item;
				$("#menu-list li").each(function(i) {
					item = $(this);
					var name = item.attr('data-name');
					var type = item.attr('data-type');
					var action = item.attr('data-action');
					var msgType = item.attr('data-msgtype');
					if (name != null) {
						var actions = action.split('|');
						if (item.hasClass('menu-item')) {
							sub_menu_i = 0;
							if (item.find('.sub-menu-item').size() > 0) {
								menus[menu_i] = {
									"name" : name,
									"sub_button" : "sub_button"
								}
							} else {
								if (actions[0] == 'url')
									menus[menu_i] = {
										"name" : name,
										"type" : type,
										"url" : actions[1]
									};
								else
									menus[menu_i] = {
										"name" : name,
										"type" : type,
										"msgId" : actions[1],
										"msgType" : msgType
									};
							}
							if (menu_i > 0) {
								if (menus[menu_i - 1]['sub_button'] == "sub_button")
									menus[menu_i - 1]['sub_button'] = sub_button;
								else
									menus[menu_i - 1]['sub_button'];
							}
							sub_button = new Array();
							menu_i++;
						} else {
							if (actions[0] == 'url')
								sub_button[sub_menu_i++] = {
									"name" : name,
									"type" : type,
									"url" : actions[1]
								};
							else
								sub_button[sub_menu_i++] = {
									"name" : name,
									"type" : type,
									"msgId" : actions[1],
									"msgType" : msgType
								};
						}
					}
				});
				
				if (sub_button.length > 0) {
					var len = menus.length;
					menus[len - 1]['sub_button'] = sub_button;
				}
				return menus;			
			};
			
			//添加子菜单
			$("#menu-container").on('click',".add-sub-item",function() {
				var sub_menu_item_total = $(this).parent().find(".sub-menu-item").size();
				if (sub_menu_item_total < 5) {
					var item = '<li class="sub-menu-item" data-type="view" data-action="key|" data-name="添加子菜单"><a href="javascript:;"><span class=" "><i class="weixin-icon sort-gray"></i><span class="sub-title">添加子菜单</span></span></a></li>';
					var itemDom = $(item);
					itemDom.insertBefore(this);
					itemDom.trigger("click");
					if (sub_menu_item_total == 4) {
						$(this).hide();
					}
				}
				return false;
			});
			//主菜单子菜单点击事件
			$("#menu-container").on('click',".menu-item, .sub-menu-item",function() {
				if ($(this).hasClass("sub-menu-item")) {
					$("#menu-list li").removeClass('current');
					$(".is-item").show();
					$(".is-sub-item").show();
				} else {
					$("#menu-list li").removeClass('current');
					$("#menu-list > li").not(this).find(".sub-menu-box").hide();
					$(".sub-menu-box", this).toggle();
					//如果当前还没有子菜单
					if ($(".sub-menu-item",this).size() == 0) {
						$(".is-item").show();
						$(".is-sub-item").show();
					} else {
						$(".is-item").hide();
						$(".is-sub-item").hide();
					}
				}
				$(this).addClass('current');
				var type = $(this).attr('data-type');
				var action = $(this).attr('data-action');
				var title = $(this).attr('data-name');
				var msgtype = $(this).attr('data-msgtype');
				var actions = action.split('|');
				$("input[name='item-title']").val(title);
				$('#current-item-name').text(title);
				if (actions[0] == 'url') {
					$('input[name=key]').val('');
				} else {
					$('input[name=url]').val('');
				}
				$("input[name='"+ actions[0]+ "']").val(actions[1]);
				$(".weixin-content-wrap").show();
				$(".no-weixin-content").hide();
				return false;
			});
			//栏位异动
			$("form#form-item").on('change', "input,textarea",function() {
				updateChangeMenu();
			});
			//保存
			$("#menu-container").on('click',"#menuSave",function() {
				var menus = getMenuList();
				$.ajax({
					url : 'wechatmenu/save.do',
					data : {
						"menus" : JSON.stringify(menus)
					},
					success : function(result) {
						var obj = JSON.parse(result);
						if (obj.retCode == 'success') {
							layer.msg("菜单保存成功，请点击同步按钮到微信官网！", {icon : 1});
						} else {
							layer.msg("菜单保存失败",{icon : 2});
						}
					}
				});
			});
			//同步
			$("#menu-container").on('click',"#menuSyn",function() {
				$.ajax({
					url : 'wechatmenu/doPublishMenu.do',
					data : {},
					success : function(result) {
						var obj = JSON.parse(result);
						if (obj.retCode == 'success') {
							layer.msg("菜单同步更新成功，生效时间看微信官网说明，或者重新关注微信号！", {icon : 1});
						} else {
							layer.msg(obj.retMsg,{icon : 2});
						}
					}
				});
			});
			
			
		}
    }
</script>
</head>
<body>
	<div class="fsh-rightPanel" id="menu-container">
		<div class="layui-anim layui-anim-upbit">
			<div class="weixin-menu-setting clear-after"
				style="min-width: 900px;">
				<div class="mobile-menu-preview">
					<div class="mobile-head-title">公众号</div>
					<ul class="menu-list" id="menu-list">
						<li class="add-item extra" id="add-item"><a
							href="javascript:;" class="menu-link" title="添加菜单"><i
								class="weixin-icon add-gray"></i>
						</a></li>
					</ul>
				</div>
				<div class="weixin-body">
					<div class="weixin-content-wrap" style="display:none">
						<div class="weixin-content">
							<div class="item-info">
								<form id="form-item" class="form-item" data-value="">
									<div class="item-head">
										<h4 id="current-item-name">添加子菜单</h4>
										<div class="item-delete">
											<a href="javascript:;" id="item_delete">删除菜单</a>
										</div>
									</div>
									<div style="margin-top: 20px;">
										<dl>
											<dt id="current-item-option">
												<span class="is-sub-item">子</span>菜单标题：
											</dt>
											<dd>
												<div class="input-box">
													<input id="item_title" name="item-title" type="text"
														value="">
												</div>
											</dd>
										</dl>
										<div id="menu-content" class="is-item">
											<div class="viewbox is-view">
												<p class="menu-content-tips">
													点击该<span class="is-sub-item">子</span>菜单会跳到以下链接
												</p>
												<dl>
													<dt>页面地址：</dt>
													<dd>
														<div class="input-box">
															<input type="text" id="url" name="url">
														</div>
													</dd>
												</dl>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
						<div class="layui-btn-container"
							style="margin-top: 50px;text-align: center">
							<button class="layui-btn btn-primary layui-btn-radius"
								id="menuSave">保存</button>
							<button class="layui-btn btn-primary layui-btn-radius"
								id="menuSyn">发布</button>
						</div>
					</div>

					<div class="no-weixin-content">点击左侧菜单进行编辑操作</div>

				</div>
			</div>
			<div class="layui-row">
				<div class="layui-col-md4"
					style="color: #FF5722;text-align: center;">
					<small>可直接拖动菜单排序</small>
				</div>
			</div>
		</div>
	</div>
</body>
</html>


