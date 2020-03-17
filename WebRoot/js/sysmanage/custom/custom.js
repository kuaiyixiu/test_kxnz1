function userDefinedLoadGrd($, table) {
	// 会员类型
	var customTypesMap = formatCustomType();
	//初始化网格
	var tableIns = table.render({
		elem: '#customInfo',
		method:'post',
		url: "custom/queryCustomAndCar.do",
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 'full-250',
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		cols: [
			[{
				type: 'checkbox',
				fixed: 'left',
				width: '5%'
			}, {
				field: 'cardNo',
				width: '10%',
				align: 'center',
				title: '会员卡号',
				templet: function(row) {
					return "<a href='javascript:void(0);' onClick='showCustomDetail("+row.id+")' class='showCustomDetail'>"+row.cardNo+"</a>";
				}
			}, {
				field: 'custType',
				width: '20%',
				align: 'center',
				title: '会员类型',
				templet: function(row) {
					return customTypesMap[row.custType];
				}
			}, {
				field: 'custName',
				//width : '20%',
				align: 'center',
				title: '会员名称'
			}, {
				field: 'cellphone',
				//width : '20%',
				align: 'center',
				title: '手机号'
			}, 
			{
				field: 'carDataList',
				//width : '20%',
				align: 'center',
				title: '车牌号'
			},{
				field: 'opt',
				width: '25%',
				align: 'center',
				title: '操作',
				toolbar: '#barLine'
			}]
		]
	});
	
}

layui.use(['jquery', 'table', 'form', 'element'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	form.render();
	//查询事件
	var active = {
		reload: function() {
			// 执行重载
			table.reload('customInfo', {
				page: {
					curr: 1
					// 重新从第 1 页开始
				},
				where: {
					"keyword": $("#keyword").val().trim()
				}
			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});

	var buttonActive = {
		// 添加会员
		addCustom: function() {
			var element = parent.layui.element;
			var id = "addCustomPage";
			element.tabDelete('tab', id);
			var url = "custom/addCustomView.do";
			element.tabAdd('tab', {
				title: "会员开卡",
				content: '<iframe src="' + url + '" name="iframe' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
				id: id
			});
			element.tabChange('tab', id);
		}
	};
	
	$('#table-list').on('click', '#addCustomBtn', function() {
		buttonActive.addCustom.call();
	});
});

function userDefinedToolHandle($arg, obj) {
	var data = obj.data
	var $ = layui.jquery;
	var iframeObj = $(window.frameElement).attr('name');
	if (obj.event === 'customDetail') { //详情
		var id = data.id;
		showCustomDetail(id);
	}
	
	var element = parent.layui.element;
	if (obj.event === 'recharge') { //充值
		var element = parent.layui.element;
		var id= "rechargePage";
		element.tabDelete('tab', id);
		var url = "custom/rechargeView.do?id="+data.id;
		element.tabAdd('tab', {
			title: "会员充值",
			content: '<iframe src="' + url + '" name="iframe' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
			id: id
		});
		element.tabChange('tab', id);
	}
	
}

function showCustomDetail(id){
	var url = "custom/detailView.do";
	var openw = "700px";
	var openh = "800px";

	//将iframeObj传递给父级窗口,执行操作完成刷新
	
	var option = {
			type: 2,
			title: "详情",
			area: ["800px", "700px"],
			fixed: false, //不固定
			content: url+"?id="+id,
			success: function(layero, index) {
				//layer.iframeAuto(index);
			}
		};
	
	var index = layer.open(option);
}

/**
 * 格式化会员类型
 * {1:自助开卡类型}
 */
function formatCustomType() {
	var $ = layui.jquery;
	var customTypes = $("#customTypes").val();
	customTypes = JSON.parse(customTypes);

	var customTypesMap = {};
	$.each(customTypes, function(index, item) {
		customTypesMap[item.id] = item.typeName;
	})

	return customTypesMap;
}