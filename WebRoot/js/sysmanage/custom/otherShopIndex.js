function userDefinedLoadGrd($, table) {
	renderShopsSelect($, table);
}

layui.use(['jquery', 'table', 'form', 'element'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	var laydate = layui.laydate

	laydate.render({
		elem: "#dateInput",
		range: true,
		value: getRangeDate()
	});
	
	//查询事件
	var active = {
		reload: function() {
			// 执行重载
			table.reload('otherShopInfo', {
				page: {
					curr: 1
					// 重新从第 1 页开始
				},
				where:  {
					"dateRangeStartTime": getStartTime(),
					"dateRangeEndTime": getEndTime(),
					"customShopId": $("#customShopId").val(),
					"shopId": $("select").val()
				}
			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});

});

/**
 * 渲染门店下拉框
 */
function renderShopsSelect($, table){
	var laytpl = layui.laytpl,
	form = layui.form;
	
	$("#shopsDiv").empty();
	var shops = $("#shopListMap").val();
	shops = eval("("+shops+")");
	// 排除当前门店id
	var customShopId = $("#customShopId").val();
	var shopList = [];
	for(var key in shops){
		if(key != customShopId){
			var obj = {
					"id": key,
					"shopName": shops[key]
				};
			shopList.push(obj)
		}
	}	
	
	var tpl = shopsTpl.innerHTML;
	var data = {
		list: shopList
	};
	
	laytpl(tpl).render(data, function(html) {
		$("#shopsDiv").append(html)
	});
	
	form.render();
	
	initTableData($, table);
}


function initTableData($, table){
	laytpl = layui.laytpl;
	form = layui.form;
	
	//初始化网格
	var tableIns = table.render({
		elem: '#otherShopInfo',
		method:'post',
		url: "custom/queryOtherShop.do",
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 'full-250',
		defaultToolbar: ['print', 'exports'],
		cols: [
			[{
				field: 'cardNo',
				width: '10%',
				align: 'center',
				title: '会员卡号',
				templet: function(row) {
					return "<a href='javascript:void(0);' onClick='showCustomDetail("+row.id+")' class='showCustomDetail'>"+row.cardNo+"</a>";
				}
			}, {
				field: 'custName',
				width: '20%',
				align: 'center',
				title: '会员名'
			}, {
				field: 'payAmount',
				//width : '20%',
				align: 'center',
				title: '金额'
			}, {
				field: 'finishTime',
				//width : '20%',
				align: 'center',
				title: '时间',
				templet: function(row) {
					return formatDateMinutes(row.finishTime);
				}
			}]
		], where: {
			"dateRangeStartTime": getStartTime(),
			"dateRangeEndTime": getEndTime(),
			"customShopId": $("#customShopId").val(),
			"shopId": $("select").val()
		}
	});
	
}