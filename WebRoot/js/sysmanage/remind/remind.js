function userDefinedLoadGrd($, table) {
	// 未完成状态
	var Not_FINISHED = 0;
	// 日常提醒
	var REMIND_DAY = 1;
	// 当前时间
	var nowDate = new Date();
	nowDate = formatDate1(nowDate);
	
	var userMap = $("#userMap").val();
	userMap = eval("("+userMap+")");
	
	// 今日提醒表
	var tableIns = table.render({
		elem: '#remindInfo',
		url: "remind/queryOldReminds.do",
		method: 'post',
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 315,
		toolbar: '#toolbarTop',
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
			}, {
				field: 'opt',
				width: '25%',
				align: 'center',
				title: '操作',
				toolbar: '#barLine'
			}]
		],where: {
			"remindStatus" : Not_FINISHED,
			"remindType":  REMIND_DAY,
			"oldDate": nowDate
		}
	});
	
	// 待办提醒表
	var futureRemindTab = table.render({
		elem: '#remindInfos',
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
				title: '用户id',
				templet: function(row) {
					return userMap[row.userId] || "";
				} 
			}, {
				field: 'remindContent',
				align: 'center',
				title: '描述详情'
			}, {
				field: 'opt',
				width: '25%',
				align: 'center',
				title: '操作',
				toolbar: '#barLines'
			}]
		],where: {
			"remindStatus" : Not_FINISHED,
			"remindType": REMIND_DAY,
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

function userDefinedToolBarHandle(target, obj){
	var $ = layui.jquery;
	if(obj.event != "addRemind"){
		return false;
	}
	var url = target.attr('data-url');
	var openw = target.attr('openw');
	var openh = target.attr('openh');
	
	var iframeObj = $(window.frameElement).attr('name');
	//将iframeObj传递给父级窗口,执行操作完成刷新
	page("新增提醒", url, iframeObj, openw, openh);
}

function userDefinedToolHandle(target, obj) {
	var $ = layui.jquery;
	if (obj.event != 'carryOutRemind') { 
		return;
	}

	var data = obj.data;
	var id = data.id;
	var url = target.attr('data-url');
	var tabId = target.attr("tabId");
	
	layer.confirm("确认完成？", function(i){
		 if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
			 layui.$(".layui-layer-btn0").addClass("layui-btn-disabled");
			 layui.$(".layui-layer-btn0").attr("disabled","disabled");
			 layer.close(i);
			 $.ajax({ 
				 type : "POST", 
				 url : url,
				 data : {
					 "id": id
				 },
				 dataType: "json",
				 success : function(result) {
					 if (result.retCode =='success' ) {
						 layui.table.reload(tabId);
					 } else { 
						 layer.msg(result.retMsg, {icon: 2}); 
					 } 
				 } 
			 });
		 }
	});
}

