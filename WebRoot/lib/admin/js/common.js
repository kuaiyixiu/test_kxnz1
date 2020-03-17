layui.config({
	base: 'lib/admin/'
}).extend({
	dialog: 'js/module/dialog',
	jquery_common_setting:'js/jquery_common_setting',
	yutons_sug:'js/module/yutons_sug',
	formSelects: 'otherPlugin/layui-formSelects/formSelects-v4'
});
var userDefinedLoadForm;//用户自定义页面初始化
var userDefinedLoadGrd; //用户自定义网格处理
var userDefinedDel; //用户自定义删除处理
var userDefinedBeforeAddData; //用户自定义新增之前的校验
var userDefinedToolHandle; //用户自定义tool按钮处理 右边
var userDefinedToolBarHandle; //用户自定义toolbar按钮处理 上边
var userDefinedOpenOptions;//用户自定义lay.open的option参数
layui.use(['form', 'jquery', 'laydate', 'layer', 'laypage', 'dialog', 'element', 'table','jquery_common_setting','upload'], function() {
	var form = layui.form,
		layer = layui.layer,
		$ = layui.jquery,
		table = layui.table,
		jquery_common_setting=layui.jquery_common_setting,
		upload=layui.upload,
		dialog = layui.dialog;
	if ($.isFunction(userDefinedLoadForm)) {
		userDefinedLoadForm();
	}
	if ($.isFunction(userDefinedLoadGrd)) {
		userDefinedLoadGrd($, layui.table);
	}

	//获取当前iframe的name值
	var iframeObj = $(window.frameElement).attr('name');
	//全选
	form.on('checkbox(allChoose)', function(data) {
		var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
		child.each(function(index, item) {
			item.checked = data.elem.checked;
		});
		form.render('checkbox');
	});
	
	// 公用日期选择
	form.on('checkbox(optTime)', function(obj){
		$("[name='optTime']").prop("checked", "");
		$(this).prop("checked", "checked");
		form.render('checkbox');
		
		var endTime = $(this).attr("data-time");
		renderRangeDate(endTime);
	});
	//渲染表单
	form.render();
	//头工具栏事件--添加
	table.on('toolbar(tableInfo)', function(obj) {
		switch (obj.event) {
			case 'addData':
				var canAdd = true;
				if ($.isFunction(userDefinedBeforeAddData)) {
					canAdd = userDefinedBeforeAddData($);
				}
				if (!canAdd)
					return;
				var url = $(this).attr('data-url');
				var openw = $(this).attr('openw');
				var openh = $(this).attr('openh');
				if (openw == undefined)
					openw = "700px";
				if (openh == undefined)
					openh = "620px"
				//将iframeObj传递给父级窗口,执行操作完成刷新
				page("新增", url, iframeObj, w = openw, h = openh);
				break;
			case 'delData':
				var url = $(this).attr('data-url');
				var tabId = $(this).attr('tabId');
				if ($.isFunction(userDefinedDel))
					userDefinedDel($, table, dialog, obj, true, url, tabId);
				else
					delData($, table, dialog, obj, true, url, tabId);
				break;
		};
		if ($.isFunction(userDefinedToolBarHandle)) {
			userDefinedToolBarHandle($(this), obj);
		}
	});

	//焦点触发提示功能
	//新增
	$('.addBtn').mouseenter(function() {
		dialog.tips('新增', '.addBtn');
	})
	//顶部批量删除
	$('.delBtn').mouseenter(function() {
		dialog.tips('批量删除', '.delBtn');
	})


	//行事件监听
	table.on('tool(tableInfo)', function(obj) {
		var data = obj.data
		if (obj.event === 'del') {
			var url = $(this).attr('data-url');
			var tabId = $(this).attr('tabId');
			if ($.isFunction(userDefinedDel))
				userDefinedDel($, table, dialog, obj, false, url, tabId);
			else
				delData($, table, dialog, obj, false, url, tabId);
		} else if (obj.event === 'edit') { //修改
			var url = $(this).attr('data-url');
			var openw = $(this).attr('openw');
			var openh = $(this).attr('openh');
			if (openw == undefined)
				openw = "700px";
			if (openh == undefined)
				openh = "620px"
			var data = obj.data;
			var id = data.id;
			//将iframeObj传递给父级窗口,执行操作完成刷新
			page("编辑", url + "?id=" + id + "&event=edit", iframeObj, w = openw, h = openh);
		} else {
			if ($.isFunction(userDefinedToolHandle))
				userDefinedToolHandle($(this), obj);
		}
	});
	//关闭按钮
	$(document).on('click', '.closeBtn', function() {
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index); //关闭当前页  
	});

	// 全局搜索回车出发事件
	document.onkeydown = function(e){    
	    var ev =document.all ? window.event : e;  
	    if(ev.keyCode==13) { 
	    	$('.searchDiv .layui-btn').trigger("click");
			return false
	    }  
	} 
});

/**
 * 控制iframe窗口的刷新操作
 */
var iframeObjName;

//父级弹出页面
function page(title, url, obj, w, h) {
	if (title == null || title == '') {
		title = false;
	};
	if (url == null || url == '') {
		url = "404.do";
	};
	if (w == null || w == '') {
		w = '700px';
	};
	if (h == null || h == '') {
		h = '350px';
	};
	
	var option = {
			type: 2,
			title: title,
			area: [w, h],
			fixed: false, //不固定
			content: url,
			offset:'10%',
			maxmin: true,
			success: function(layero, index) {
				layer.iframeAuto(index);
			}
		};
	
	if (layui.jquery.isFunction(userDefinedOpenOptions)) {
		userDefinedOpenOptions(option);
	}
	
	iframeObjName = obj;
	var index = layer.open(option);
	return index;
}

/**
 * 刷新子页,关闭弹窗
 */
function refresh() {
	//根据传递的name值，获取子iframe窗口，执行刷新
	if (window.frames[iframeObjName]) {
		window.frames[iframeObjName].location.reload();

	} else {
		window.location.reload();
	}

	layer.closeAll();
}

/**
 * 删除数据公共接口
 * @param $
 * @param table
 * @param dialog
 * @param obj
 * @param isDelALl 是否是checkbox删除
 * @param url 删除url
 * @param tabId 表格id
 */
function delData($, table, dialog, obj, isDelByCheck, url, tabId) {
	var ids = "";
	var title = "";
	if (isDelByCheck) {
		var checkStatus = table.checkStatus(tabId);
		var data = checkStatus.data;
		for (var i = 0; i < data.length; i++) {
			ids = ids + data[i].id + ";";
		}
		if (ids == "") {
			layer.msg("请选择要删除的数据", {
				icon: 2
			});
			return;
		}
		ids = ids.substring(0, ids.length - 1);
		title = "您确定要删除选中项?";
	} else {
		var data = obj.data;
		ids = data.id;
		title = "您确定要删除该笔记录?";
	}

	dialog.confirm({
		message: title,
		success: function() {
			del($, ids, table, obj, url, tabId);
		},
		cancel: function() {
			layer.msg('取消了');
		}
	})
}


function del($, ids, table, obj, url, tabId) {
	$.ajax({
		type: "POST",
		url: url,
		data: {
			"ids": ids
		},
		dataType: "json",
		async: false,
		success: function(result) {
			if (result.retCode == 'success') {
				//$(obj).del();
				//	    	  var tableId=obj.config.id;
				table.reload(tabId);
				layer.msg(result.retMsg, {
					icon: 1
				});
			} else {
				layer.msg(result.retMsg, {
					icon: 2
				});
			}
		}
	});
}

/**
 * 只保留到日
 * yyyy:hh:dd
 * @param time
 * @returns
 */
function formatDate1(time) {
	var date = formatDate(time);
	
	return date.substr(0, 10);
}

/**
 * 精确到分钟
 * yyyy:hh:dd HH:mm
 * @param time
 */
function formatDateMinutes(time){
	var date = formatDate(time);
	
	return date.substr(0, 16);
}

/**
 * 时间戳转时间str
 * yyyy:hh:dd HH:mm:ss
 * @param time
 */
function formatDate(time) {
	if(!time){
		return "";
	}
	
	var date = new Date(time);
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	month = month < 10 ? "0" + month : month;
	var day = date.getDate();
	day = day < 10 ? ("0" + day) : day;
	var hours = date.getHours();
	hours = hours < 10 ? ("0" + hours) : hours;
	var minutes = date.getMinutes();
	minutes = minutes < 10 ? ("0" + minutes) : minutes;
	var seconds = date.getSeconds();
	seconds = seconds < 10 ? ("0" + seconds) : seconds;

	var dateStr = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
	return dateStr;
}


function initNum(obj, dot) {
	var v = obj.value;
	if (v != '') {
		obj.value = parseFloat(v).toFixed(dot);
	} else
		obj.value = parseFloat('0').toFixed(dot);
}


//校验车牌号
function isLicensePlate(str) {
    return /^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$/.test(str);
}

//校验是正数就返回true
function checkPositiveFloat(val){
	var regPos = /^\d+(?=\.{0,1}\d+$|$)/; //正数浮点数
	///^\d+(\.{0,1}\d+){0,1}$/ 非负数
//	var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
    if(regPos.test(val) && parseFloat(val) > 0){
        return true;
    }else{
        return false;
    }
}

//校验手机号
function  checkMobile(str) {
	var re = /^1\d{10}$/
	if (re.test(str)) {
		return true;
	} else {
		return false;
	}
}

/**
 * 渲染时间范围
 * @param endTime
 * @returns
 */
function renderRangeDate(endTime) {
	var $ = layui.jquery;
	
	if (!endTime) {
		return false;
	}

	var date = new Date();
	var nowDate = formatDate1(date);

	$("#dateInput").val(nowDate + " - " + endTime);
}

function showLoading() {
    return layer.load(1, {
        shade: [0.1,'#000'] //0.1透明度的白色背景
    });
}

function getBarRow(row){
	var tpl = barLine.innerHTML;
	// 如果不存在，则最后一个选中
	layui.laytpl(tpl).render(row, function(html) {
		return html;
	});
}

function setYesBtnDisable(state){
	if(state=="undisabled"){
		layui.$(".layui-layer-btn0").removeClass("layui-btn-disabled");
		layui.$(".layui-layer-btn0").removeAttr("disabled");
	}else{
		layui.$(".layui-layer-btn0").addClass("layui-btn-disabled");
		layui.$(".layui-layer-btn0").attr("disabled","disabled");
	}

}
