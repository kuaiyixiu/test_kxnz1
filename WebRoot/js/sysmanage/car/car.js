function userDefinedLoadGrd($, table) {
	//初始化网格
	var tableIns = table.render({
		elem: '#carInfo',
		url: "car/queryAllCars.do",
		method: 'post',
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 'full-250',
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		cols: [
			[{
				field: 'carNumber',
				width: '10%',
				align: 'center',
				title: '车牌',
				templet: function(row) {
					if(row.carNumber){
						return "<a href='javascript:void(0);' onClick='showCarDetail(" + row.id + ")' class='showCarDetail'>" + row.carNumber + "</a>";	
					}
					
					return "";
				}
			}, {
				field: 'ownerName',
				width: '20%',
				align: 'center',
				title: '车主信息',
				templet: function(row) {
					return getOwnerName(row);
				}
			}, {
				field: 'carBrand',
				//width : '20%',
				align: 'center',
				title: '品牌型号'
			}, {
				field: 'remark',
				//width : '20%',
				align: 'center',
				title: '备注'
			}, {
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
			table.reload('carInfo', {
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
});

function getOwnerName(row) {
	var type = row.carType;
	var name = type == 0 ? row.ownerName : row.custName;

	return name || "";
}

function userDefinedToolHandle($arg, obj) {
	var $ = layui.jquery;
	if (obj.event != 'addCarMaintain') {
		return;
	}
	var url = $arg.attr('data-url');
	var openw = $arg.attr('openw');
	var openh = $arg.attr('openh');
	var data = obj.data;
	var id = data.id;
	var iframeObj = $(window.frameElement).attr('name');
	//将iframeObj传递给父级窗口,执行操作完成刷新
	page("添加保养", url + "?id=" + id, iframeObj, w = openw, h = openh);

}

function showCarDetail(dataId) {
	var element = parent.layui.element;
	var id = "carDetailPage";
	element.tabDelete('tab', id);
	var url = "car/carDetailView.do?id=" + dataId;
	element.tabAdd('tab', {
		title: "车辆详情",
		content: '<iframe src="' + url + '" name="iframe' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
		id: id
	});
	element.tabChange('tab', id);
}