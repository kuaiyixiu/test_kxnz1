/**
 * 加载表格
 * @param $
 * @param table
 * @param parentId 父级id
 * @param id 当前等级id
 * @param type back返回类型
 * @returns
 */
function userDefinedLoadGrd($, table, parentId, id, type) {
	type = type ? type : "empty";
	var　 url = "menu/queryMenu.do?queryType=" + type;
	url = parentId ? url += "&parentId=" + parentId : url;
	//初始化网格
	var tableIns = table.render({
		elem: '#menuInfo',
		url: url,
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 'full-250',
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		done: function(res, curr, count) {
			setParentLevel($, res, parentId);
		},
		cols: [
			[{
				type: 'checkbox',
				fixed: 'left',
				width: '5%'
			}, {
				field: 'name',
				width: '10%',
				align: 'center',
				title: '菜单名'
			}, {
				field: 'createDate',
				width: '20%',
				align: 'center',
				title: '创建时间',
				//sort: true,
				templet: function(row) {
					return formatDateMinutes(row.createDate);
				}
			}, {
				field: 'description',
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

/**
 * 设置父级别，用于退后
 * @param $
 * @param data
 * @param id
 * @returns
 */
function setParentLevel($, data, parentId) {
	var prevLevel = parentId;
	var addUrlParentId = parentId;
	// 当前不为空
	if (data.count != 0) {
		addUrlParentId = data.data[0].parentId;

		prevLevel = data.data[0].parentId;
		// 如果当前级别为1，则上一级别设计为0，返回顶层
		prevLevel = prevLevel === 1 ? 0 : prevLevel;
	}

	$(".addBtn").attr("data-url", "menu/addMenuView.do?parentId=" + addUrlParentId);

	$("#parentId").val(prevLevel);
}

layui.use(['jquery', 'table', 'form'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	form.render();
	//查询事件
	var active = {
		reload: function() {
			// 执行重载
			table.reload('menuInfo', {
				page: {
					curr: 1
					// 重新从第 1 页开始
				},
				where: {
					"name": $.trim($("#menuName").val()),
					"description": $.trim($("#description").val())
					//,"parentId": $("#parentId").val()
				}
			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});

});

function userDefinedToolHandle($arg, obj) {
	var data = obj.data
	
	if (obj.event === 'childrenDetail') { //此为菜单模块使用，查询子菜单
		//当前id 为parentId 
		var parentId = data.id;
		var id = data.parentId;
		userDefinedLoadGrd(layui.jquery, layui.table, parentId, id);
	}
}

function userDefinedToolBarHandle(target, obj){
	var $ = layui.jquery;
	switch (obj.event) {
		case 'back':
			var parentId = $("#parentId").val();
			var id = $("#nowLevel").val();
			userDefinedLoadGrd($, layui.table, parentId, id, "back");
			break;
	}
}