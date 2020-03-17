layui.use(['jquery', 'laydate', 'table', 'form', 'element'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	var laytpl = layui.laytpl;
	var SERVE_CONSTANT = {
		"URL": "addServeView.do",
		"TITLE": "添加服务"
	};
	
	var PRODUCT_CONSTANT = {
		"URL": "addProductView.do",
		"TITLE": "添加产品"
	};
	
	form.on('submit(formSubmitBtn)', function(data) {
		$.ajax({
			type: "POST",
			url: "meal/addMeal.do",
			data: getAddMealParam(data.field),
			dataType: "json",
			success: function(result) {
				if (result.retCode == 'success') {
					parent.layer.msg(result.retMsg, {
						icon: 1
					});
					// 关闭会员开卡界面
					parent.layui.element.tabDelete('tab', 'addMealPage');
				} else {
					layer.msg(result.retMsg, {
						icon: 2
					});
				}
			}
		});
	});
	
	$("#mealForm").on("click", "#addServeBtn", function(){
		addMealDt(SERVE_CONSTANT.URL, SERVE_CONSTANT.TITLE)
	}).on("click", ".delBtn", function(){
		$(this).parents("tr").remove();
	}).on("click", "#addProductBtn", function(){
		addMealDt(PRODUCT_CONSTANT.URL, PRODUCT_CONSTANT.TITLE)
	}).on('click', "#closeButton", function(){
		parent.layui.element.tabDelete('tab', 'addMealPage');
	});

});

/**
 * 添加套餐明细
 * 
 * @param url
 * @param title
 */
function addMealDt(url, title){
	var $ = layui.jquery;
	var parentUrl = "meal/"
	var openw = "700px";
	var openh = "700px";
	url = parentUrl + url;
	var iframeObj = $(window.frameElement).attr('name');
	
	page(title, url, iframeObj, openw, openh);
}


function getAddMealParam(data){
	var $ = layui.jquery;
	var mealName = $("#mealName").val();
	var mealDtStr = getMealDtStr();
	data.name = mealName;
	data.mealDtStr = mealDtStr;
	
	return data;
}

/**
 * 得到套餐明细
 * @returns
 */
function getMealDtStr(){
	var $ = layui.jquery;
	
	var list = [];
	$(".mealDts").each(function(index, item){
		var td = $(item).find("td");
		var obj = {
			"quantity": $(item).find("td[data-id='quantity']").text(),
			"itemId": $(item).find("td[data-id='itemId']").text(),
			"type": $(item).attr("data-type")
		}
		
		list.push(obj);
	})
	
	if(list.length == 0){
		return "";
	}
	
	return JSON.stringify(list);
}