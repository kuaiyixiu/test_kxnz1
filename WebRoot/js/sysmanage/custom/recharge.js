function userDefinedLoadGrd($, table) {
	setShopIdHtml();
	setCustomTypesHtml();
	
	var tableIns = table.render({
		elem: '#customMealInfo',
		method:'post',
		url: "custom/queryCustomMeal.do",
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		//height: 'full-250',
		defaultToolbar: ['print', 'exports'],
		cols: [
			[{
				field: 'name',
				width: '10%',
				align: 'center',
				title: '套餐名'
			}, {
				field: 'createDate',
				width: '20%',
				align: 'center',
				title: '有效时间',
				templet: function(row) {
					return getValidDate(row);
				}
			}, {
				field: 'createDate',
				//width: '20%',
				align: 'center',
				title: '内容',
				templet: function(row) {
					return getContent(row);
				}
			}
//			, {
//				field: 'opt',
//				width: '20%',
//				align: 'center',
//				title: '操作',
//				toolbar: '#barLine'
//			}
			]
		],where: {
			"custId": $("#customId").val()
		},done : function(res, curr, count){
			drawTotalHtml(res);
			bindTableEvent();
		}
		
	});
	
	$("#tools").on('click', "[data-id='toolBtn']", function(){
		toolsActive($(this));
	});
}

layui.use(['jquery', 'table', 'form', 'element', 'dialog'], function() {
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

	
});

/**
 * 工具栏事件
 * @param $arg
 */
function toolsActive($arg){
	var $ = layui.jquery;
	var iframeObj = $(window.frameElement).attr('name');
	var url = $arg.attr('data-url');
	var openw = $arg.attr('openw');
	var openh = $arg.attr('openh');
	var title = $arg.attr('data-title');
	//将iframeObj传递给父级窗口,执行操作完成刷新
	page(title, url, iframeObj, openw, openh);
}

/**
 * 得到门店信息
 * @param shopId
 */
function getShopName(shopId){
	var $ = layui.jquery;
	var mapJson = $("#shopListMap").val();
	var shopList = eval("("+mapJson+")");
	var shopName = shopList[shopId];
	
	return  shopName? shopName:"";
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

/**
 * 设置会员类型
 */
function setCustomTypesHtml(){
	var $ = layui.jquery;
	var customTypesMap = formatCustomType();
	var custType = $("#custType").val();
	$("#custTypeTd").html(customTypesMap[custType]);
}

/**
 * 设置门店信息
 */
function setShopIdHtml(){
	var $ = layui.jquery;
	var mapJson = $("#shopListMap").val();
	var shopList = eval("("+mapJson+")");
	var shopId = $("#shopId").val(); 
	var shopName = shopList[shopId];
	shopName = shopName? shopName:"";
	
	$("#shopTd").html(shopName);
}

function bindTableEvent(){
	var $ = layui.jquery,
		dialog = layui.dialog,
		table = layui.table;
	
	
	//isLastMealDt

	$(document).on("click", ".delCutomMealDt", function(){
		delView($(this))
	})
}


function delView($arg){
	var $ = layui.jquery;
	var pId = "empty";
	var mealDtlength = $arg.parents("td").find(".delCutomMealDt").length;
	if(mealDtlength == 1){
		pId = $arg.attr("data-pId");
	}
	var id = $arg.attr('data-id');
	var price = $arg.attr('data-price');
	var iframeObj = $(window.frameElement).attr('name');
	var url = "custom/delMealDtView.do?id="+id+"&pId="+pId+"&price="+price;
	page("删除已购项目", url, iframeObj, '700px', '700px');
}