function userDefinedLoadGrd($, table) {
	// 已完成状态
	var FINISHED = 1;
	// 日常提醒
	var REMIND_DAY = 1;
	// 客户提醒
	var REMIND_CUSTOMER = 2;
	// 当前时间
	var nowDate = new Date();
	nowDate = formatDate1(nowDate);
	
	var userMap = $("#userMap").val();
	userMap = eval("("+userMap+")");
	
	// 今日提醒表
	var tableIns = table.render({
		elem: '#finishRemindInfo',
		url: "remind/queryOldReminds.do",
		method: 'post',
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 315,
		defaultToolbar: ['print', 'exports'],
		cols: [
			[{
				field: 'remindDate',
				width: '10%',
				align: 'center',
				title: '时间',
				templet: function(row) {
					return formatDate1(row.remindDate);
				}
			}, {
				field: 'userId',
				width: '20%',
				align: 'center',
				title: '用户名',
				templet: function(row) {
					return userMap[row.userId] || "";
				}	
			}, {
				field: 'remindContent',
				align: 'center',
				title: '描述详情'
			}]
		],where: {
			"remindStatus" : FINISHED,
			"remindType":  REMIND_DAY,
			"oldDate": nowDate
		}
	});
	
	// 待办提醒表
	var futureRemindTab = table.render({
		elem: '#finishRemindInfos',
		url: "remind/queryOldReminds.do",
		method: 'post',
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 315,
		defaultToolbar: ['print', 'exports'],
		cols: [
			[{
				field: 'remindDate',
				width: '10%',
				align: 'center',
				title: '时间',
				templet: function(row) {
					return formatDate1(row.remindDate);
				}
			}, {
				field: 'userId',
				width: '20%',
				align: 'center',
				title: '用户名',
				templet: function(row) {
					return userMap[row.userId] || "";
				}	
			}, {
				field: 'remindContent',
				align: 'center',
				title: '描述详情'
			}]
		],where: {
			"remindStatus" : FINISHED,
			"remindType": REMIND_CUSTOMER,
			"futureDate": nowDate
		}
	});
}

layui.use(['jquery', 'laydate', 'table', 'form', 'element'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	var laydate = layui.laydate

	form.render();
});

