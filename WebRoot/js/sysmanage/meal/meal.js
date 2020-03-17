function userDefinedLoadGrd($, table) {
	var shopMap = getShopMap();

	var tableIns = table.render({
		elem: '#mealInfo',
		url: "meal/queryMeals.do",
		method: 'post',
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 'full-250',
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		cols: [
			[{
				field: 'shopId',
				width: '10%',
				align: 'center',
				title: '所属门店',
				templet: function(row) {
					return shopMap[row.shopId];
				}
			}, {
				field: 'name',
				width: '15%',
				align: 'center',
				title: '套餐名',
				templet: function(row) {
					return "<a href='javascript:void(0);' onClick='showMealDetail(" + row.id + ")' class='showDetail'>" + row.name + "</a>";
				}
			}, {
				field: 'amount',
				align: 'center',
				title: '套餐价'
			}, {
				field: 'total',
				align: 'center',
				title: '总价值',
				templet: function(row) {
					return getTotalPrice(row);
				}
			}, {
				field: 'opt',
				align: 'center',
				title: '操作',
				templet: function(row) {
					return renderOptHtml(row);
				}
			}]
		],
		where: {
			"shopIds": getShopIds(),
			"statusStr": getStatusStr()
		},
		done: function(res) {
			bindEvent();
		}
	});
};

function bindEvent() {
	var $ = layui.jquery;

	// 添加
	var ADD_CONSTANT = {
		"OPT": "add",
		"TITLE": "新增套餐",
		"ID": "addMealPage"
	};

	/*// 编辑
	var EDIT_CONSTANT = {
		"OPT": "edit",
		"TITLE": "编辑套餐",
		"ID": "editMealPage"
	}

	// 详情
	var DETAIL_CONSTANT = {
		"OPT": "detail",
		"TITLE": "套餐详情",
		"ID": "detailMealPage"
	}*/

	var active = {
		// 添加会员
		addMeal: function() {
			mealOperate("", ADD_CONSTANT.OPT, ADD_CONSTANT.ID, ADD_CONSTANT.TITLE)
		}
/*		// 编辑
		,editMeal: function() {
			mealOperate("", EDIT_CONSTANT.OPT, EDIT_CONSTANT.ID, EDIT_CONSTANT.TITLE)
		},
		// 详情
		detailMeal: function() {
			mealOperate("", DETAIL_CONSTANT.OPT, DETAIL_CONSTANT.ID, DETAIL_CONSTANT.TITLE)
		}*/
	};


	$('#addMealBtn').on('click', function() {
		active.addMeal.call();
	})

}

layui.use(['jquery', 'formSelects', 'laydate', 'table', 'form', 'element'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	var laytpl = layui.laytpl;

	var active = {
		reload: function() {
			// 执行重载
			table.reload('mealInfo', {
				page: {
					curr: 1
					// 重新从第 1 页开始
				},
				where: {
					"shopIds": getShopIds(),
					"statusStr": getStatusStr()
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
 * 套餐操作
 * 
 * @param mealId
 * @param type:add/edit/detail
 */
function mealOperate(mealId, type, id, title) {
	var element = parent.layui.element;
	// element.tabDelete('tab', id);
	var url = "meal/addMealView.do";
	element.tabAdd('tab', {
		title: title,
		content: '<iframe src="' + url + '" name="iframe' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
		id: id
	});

	element.tabChange('tab', id);
}

/**
 * 得到门店map
 * @returns
 */
function getShopMap() {
	var $ = layui.jquery;
	var shops = $("#shopListMap").val();
	shops = eval("(" + shops + ")");

	return shops;
}

/**
 * 得到总价值
 * @param data
 * 
 */
function getTotalPrice(data) {
	var $ = layui.jquery;
	var mealDts = data.mealDts;
	var total = 0;

	$.each(mealDts, function(index, item) {
		total += Number(item.price) * Number(item.quantity);
	})

	return total;
}

/**
 * 得到状态
 * @returns 
 */
function getStatusStr() {
	var $ = layui.jquery;
	// 上架下架都查询
	if ($("#obtained").is(":checked")) {
		return "1,0";
	}

	// 只查询上架
	return "1";
}

/**
 * 得到门店id
 * @returns
 */
function getShopIds() {
	var formSelects = layui.formSelects;
	var shopIds = formSelects.value('select1', 'val')

	return shopIds.join(",");
}

/**
 * 渲染操作栏
 * 
 * @param data
 */
function renderOptHtml(data){
	var $ = layui.jquery;
	var shopId = $("#shopId").val();
	if(data.status == 0 || shopId != data.shopId){
		return "";
	}
	
	return barLine.innerHTML;
}

function showMealDetail(id){
	var url = "meal/detailView.do";
	var openw = "700px";
	var openh = "800px";

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