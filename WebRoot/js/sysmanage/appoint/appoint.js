function userDefinedLoadGrd($, table) {
	var tableIns = table.render({
		elem: '#appointInfo',
		url: "appoint/queryCustomAppoints.do",
		method: 'post',
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 'full-250',
		cols: [
			[{
				field: 'addTime',
				width: '10%',
				align: 'center',
				title: '提交时间',
				templet: function(row) {
					return formatDate1(row.addTime);
				}
			}, {
				field: 'appointTime',
				width:  '10%',
				align: 'center',
				title: '到店时间',
				templet: function(row) {
					return formatDate1(row.appointTime);
				} 
			}, {
				field: 'cellphone',
				align: 'left',
				title: '内容',
				templet: function(row) {
					return renderContent(row);
				} 
			}, {
				field: 'opt',
				width: '25%',
				align: 'center',
				title: '操作',
				toolbar: '#barLine'
			}]
		],where: {
			
		}
	});
	
}

layui.use(['jquery', 'laydate', 'table', 'form', 'element', 'laytpl'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	var laydate = layui.laydate

	form.render();
});

function userDefinedToolHandle(target, obj){
	var $ = layui.jquery;
	var titleMap = {
		"replyRemind": "预约提醒",
		"finish": "预约完成"
	}
	
	var title = titleMap[obj.event];
	var data = obj.data;
	var id = data.id;
	var url = target.attr('data-url');
	if(obj.event == "finish"){
		var obj = {
			"id": id
		};
		layer.confirm("确认完成？", function(i){
			 if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
				 setYesBtnDisable("disabled");
				 ajaxMethod(obj, url, "appointInfo");
			 }
		});
		
		return false;
	}
	
	var openw = target.attr('openw');
	var openh = target.attr('openh');

	url = url + "?id=" + id;
	
	var iframeObj = $(window.frameElement).attr('name');
	//将iframeObj传递给父级窗口,执行操作完成刷新
	page(title, url, iframeObj, openw, openh);
}

function renderContent(data){
	var contentHtml = contentTpl.innerHTML;
	data.itemName = getItemTypeCn(data.itemId);
	
	var resultHtml = "";
 	layui.laytpl(contentHtml).render(data, function(html){
 		resultHtml = html;
	});	
 	
 	return resultHtml;
}

function getItemTypeCn(itemId){
	if (itemId == 1) {
		return "保养";
	} else if (itemId == 2) {
		return "打蜡";
	} else if (itemId == 3) {
		return "洗车";
	} else if (itemId == 4) {
		return "其他";
	}
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