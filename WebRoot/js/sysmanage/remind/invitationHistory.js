function userDefinedLoadGrd($, table) {
	// 已邀约
	var USED = 1; 
	// 服务邀约表
	var tableIns = table.render({
		elem: '#serverInfo',
		url: "remind/queryInvitationServer.do",
		method: 'post',
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 315,
		width : 1200,
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
				align: 'center',
				title: '服务名'
			}]
		],where: {
			"status" : USED
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
	// 已邀约
	var USED = 1; 
	var tableIns = table.render({
		elem: '#carInfo',
		url: "remind/queryInvitationCar.do",
		method: 'post',
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 315,
		width : 1200,
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
				align: 'center',
				title: '到期时间',
				templet: function(row) {
					return renderTimeHtml(row);
				}
			}]
		],where: {
			"status" : USED
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