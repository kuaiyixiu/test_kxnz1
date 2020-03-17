function userDefinedLoadGrd($, table) {
	// 未邀约
	var UN_USED = 0; 
	// 服务邀约表
	var tableIns = table.render({
		elem: '#serverInfo',
		url: "remind/queryInvitationServer.do",
		method: 'post',
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 315,
		defaultToolbar: ['print', 'exports'],
		cols: [
			[{
				field: 'invitationTime',
				width: '10%',
				align: 'center',
				title: '邀约时间',
				templet: function(row) {
					return formatDate1(row.invitationTime);
				}
			}, {
				field: 'custName',
				width: '15%',
				align: 'center',
				title: '会员名'
			}, {
				field: 'carNumber',
				width: '15%',
				align: 'center',
				title: '车牌号'
			}, {
				field: 'customPhone',
				width: '10%',
				align: 'center',
				title: '会员电话'
			}, {
				field: 'doneTime',
				width: '15%',
				align: 'center',
				title: '上次服务时间',
				templet: function(row) {
					return formatDate1(row.doneTime);
				}
			}, {
				field: 'serverName',
				width: '10%',
				align: 'center',
				title: '服务名'
			}, {
				field: 'opt',
				align: 'center',
				title: '操作',
				toolbar: '#serverBarLine'
			}]
		],where: {
			"status" : UN_USED
		}
	});

	initCarInfo($, table);
}

layui.use(['jquery', 'laydate', 'table', 'form', 'element'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	var laydate = layui.laydate

	form.render();
});

/**
 * 初始化车辆列表
 * @param $
 * @param table
 */
function initCarInfo($, table) {
	var UN_USED = 0; 
	var tableIns = table.render({
		elem: '#carInfo',
		url: "remind/queryInvitationCar.do",
		method: 'post',
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 315,
		cols: [
			[{
				field: 'carNumber',
				width: '20%',
				align: 'center',
				title: '车牌号'
			}, {
				field: 'custName',
				width: '20%',
				align: 'center',
				title: '会员名'
			}, {
				field: 'customPhone',
				width: '10%',
				align: 'center',
				title: '会员电话'
			}, {
				field: 'type',
				width: '10%',
				align: 'center',
				title: '类型',
				templet: function(row) {
					return renderTypeHtml(row);
				}
			}, {
				field: 'type',
				width: '10%',
				align: 'center',
				title: '到期时间',
				templet: function(row) {
					return renderTimeHtml(row);
				}
			}, {
				field: 'opt',
				align: 'center',
				title: '操作',
				toolbar: '#carBarLine'
			}]
		],where: {
			"status" : UN_USED
		}
	});
}

/**
 * 加载描述html
 * @param data
 */
function renderTypeHtml(data) {
	var check = "车检险";
	var compulsory = "交强险";
	var commercialDate = "商业险";
	var maintainDate = "保养到期";
	var span = "<span>";
	var endSpan = "</span></br>"
	var html = "";

	if (data.isCheckDate) {
		html += span + check + endSpan;
	}
	if (data.isCompulsoryDate) {
		html += span + compulsory + endSpan;
	}
	if (data.isCommercialDate) {
		html += span + commercialDate + endSpan;
	}
	if (data.isMaintainDate) {
		html += span + maintainDate + endSpan;
	}

	return html;
}

/**
 * 渲染时间html
 * @param data
 * 
 */
function renderTimeHtml(data) {
	var span = "<span>";
	var endSpan = "</span></br>"
	var html = "";
	if (data.isCheckDate) {
		html += span + formatDate1(data.checkDate) + endSpan;
	}
	if (data.isCompulsoryDate) {
		html += span + formatDate1(data.compulsoryDate) + endSpan;
	}
	if (data.isCommercialDate) {
		html += span + formatDate1(data.commercialDate) + endSpan;
	}
	if (data.isMaintainDate) {
		html += span + formatDate1(data.maintainDate) + endSpan;
	}

	return html;
}

/**
 * 完成，回复事件
 * @param $arg
 * @param obj
 */
function userDefinedToolHandle($arg, obj) {
	var tableId = $arg.attr("tabId");
	var url = $arg.attr("data-url");
	
	var data = obj.data
	// 完成提醒
	if (obj.event === 'carryOutServer') { 
		var id = data.id;
		var obj = {
			"id": id
		};
		layer.confirm("确认完成？", function(i){
			 if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
				 layui.$(".layui-layer-btn0").addClass("layui-btn-disabled");
				 layui.$(".layui-layer-btn0").attr("disabled","disabled");
				 layer.close(i);
				ajaxMethod(obj, url, tableId);
			 }
		});
	}
	// 回复提醒
	if (obj.event === 'serverInvitation') {
		replyInvitation(data);
	}
	
	if (obj.event === 'carInvitation') {
		var data = getCarParam(obj.data);
		layer.confirm("确认回复？", function(i){
			 if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
				 layui.$(".layui-layer-btn0").addClass("layui-btn-disabled");
				 layui.$(".layui-layer-btn0").attr("disabled","disabled");
				 ajaxMethod(data, url, tableId);
				 layer.close(i); 
			 }
		});
	}
	
	// 车辆完成
	if (obj.event === 'carryOutCar') { 
		var id = data.id;
		var obj = getCarryOutCar(data);
		layer.confirm("确认完成？", function(i){
			 if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
				 layui.$(".layui-layer-btn0").addClass("layui-btn-disabled");
				 layui.$(".layui-layer-btn0").attr("disabled","disabled");
			     layer.close(i);
			     ajaxMethod(obj, url, tableId);
			 }
		});
	}
}

function getCarryOutCar(data){
	var obj = {};
	obj.isCheckDate = data.isCheckDate;
	obj.isCommercialDate = data.isCommercialDate;
	obj.isCompulsoryDate = data.isCompulsoryDate;
	obj.isMaintainDate = data.isMaintainDate;
	obj.id = data.id;
	obj.carMainTainId = data.carMainTainId;
	
	return obj;
}

function getCarParam(data){
	var result = {};
	result.checkDate = formatDate(data.checkDate);
	result.commercialDate = formatDate(data.commercialDate);
	result.compulsoryDate = formatDate(data.compulsoryDate);
	result.maintainDate = formatDate(data.maintainDate);
	result.carNumber = data.carNumber;
	result.cardNo = data.cardNo;
	result.custName = data.custName;
	result.customPhone = data.customPhone;
	result.isCheckDate = data.isCheckDate;
	result.isCommercialDate = data.isCommercialDate;
	result.isCompulsoryDate = data.isCompulsoryDate;
	result.isMaintainDate = data.isMaintainDate;
	
	return result;
}

function replyInvitation(data){
	var $ = layui.jquery;
	data.doneTimeStr = formatDate1(data.doneTime);
	var url = 'remind/replyServerView.do?id='+data.id;
	
	var iframeObj = $(window.frameElement).attr('name');
	//将iframeObj传递给父级窗口,执行操作完成刷新
	page("回复提醒", url, iframeObj, "700px", "550px");
}

/**
 * 异步请求
 * 
 * @param requestData
 * @param url
 * @param tabId
 */
function ajaxMethod(requestData, url, tabId) {
	var table = layui.table;
	var $ = layui.jquery;
	
	$.ajax({
		type: "POST",
		url: url,
		data: requestData,
		dataType: "json",
		async: false,
		success: function(result) {
			if (result.retCode == 'success') {
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