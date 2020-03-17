layui.use(['laydate', 'jquery', 'laytpl', 'form', 'element'], function() {
	var laydate = layui.laydate;
	var $ = layui.jquery;
	var form = layui.form;
	form.verify({
		money : function(value, item) { //value：表单的值、item：表单的DOM对象
			if (value <= 0)
				return "充值金额输入不合法，请重新输入！";
		}
	});
	// 订单
	var meals = $("#meals").val();
	meals = JSON.parse(meals);
	var tpl = mealsTpl.innerHTML;

	var laytpl = layui.laytpl;
	laytpl(tpl).render(meals, function(html) {
		$("#mealTypeDiv").empty();
		$("#mealTypeDiv").append(html)
	});

	loadPayTypes();
	var active = {
		addCarTpl: function() {
			var carHtml = carTpl.innerHTML;
			$("#carList").append(carHtml);
			return false;
		},
		delCarTpl: function(othis) {
			othis.parent().remove();
		}
	}

	form.on('submit(formSubmitBtn)', function(data) {
		$.ajax({
			type: "POST",
			url: "custom/saveCustomMeal.do",
			data: getSaveCustomMealParam(data.field),
			dataType: "json",
			success: function(result) {
				if (result.retCode == 'success') {
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);//关闭当前页  
					//parent.layui.table.reload('customMealInfo');
					window.parent.location.reload();
					parent.layer.msg(result.retMsg, {
						icon: 1
					});
				} else {
					layer.msg(result.retMsg, {
						icon: 2
					});
				}
			}
		});
	});
	
	$(document).on('click', '#closeButton', function() {
		parent.layui.element.tabDelete('tab', 'addCustomPage');
	})
	
	form.on('select(mealsSelect)', function(data){
		changeMeals(data.value);
	});
	
});

/**
 * 得到保存客户订单的参数
 * @param data
 * @returns
 */
function getSaveCustomMealParam(data){
	var $ = layui.jquery;
	data.custId = window.parent.document.getElementById("customId").value;
	data.createDate = formatDate1(new Date());
	
	// 订单详情id
	var mealDtIdList = []; 
	$("#mealUl ul li").each(function(index, item){
		var id = $(item).attr("data-id");
		var quantity =  $(item).attr("data-quantity");
		if(!id){
			return;
		}
		var obj = {
			id: id,
			quantity: quantity
		}
		mealDtIdList.push(obj);
	});
	data.mealDts = JSON.stringify(mealDtIdList);

	return data;
}

/**
 * 套餐改变事件
 * @param id
 */
function changeMeals(id){
	var $ = layui.jquery;
	$.ajax({
		type: "POST",
		url: "mealDt/queryMealDts.do",
		data: {"mealId": id},
		dataType: "json",
		success: function(result) {
			loadMealDtHtml(result, id);
		}
	});
}

/**
 * 加载订单明细html
 * @param result
 * 
 */
function loadMealDtHtml(result, id){
	var $ = layui.jquery,
		laytpl = layui.laytpl;
	// 套餐价格
	var amount = layui.jquery('select[lay-filter="mealsSelect"]').find("option:selected").attr("data-amount");
	$("#totalPrice").val(0);
	$("#mealDetail").hide(); 
	$("#mealUl").empty();
	if(result.length === 0){
		return false;
	};
	
	var tpl = mealsDtTpl.innerHTML;
	laytpl(tpl).render(result, function(html) {
		$("#mealUl").append(html)
	});
	
	var total = Number(0);
	$.each(result, function(index, item){
		total += Number(item.price) * Number(item.quantity); 
	});
	
	$("#mealUl ul li:last").append("<li><font color='red'> 套餐价："+amount+"</font>"+"   <s>原价:"+total+"</s>"+"</li>");
	$("#totalPrice").val(amount);
	
	$("#mealDetail").show();
	
	var mealMap = getMealMap();
	var endTime = mealMap[id];
	$("#expireDate").val(endTime);
}

function loadPayTypes(){
	var $ = layui.jquery,
	laytpl = layui.laytpl;
	
	var payTypes = $("#payTypes").val();
	payTypes = JSON.parse(payTypes);
	var tpl = payTypesTpl.innerHTML;

	payTypes = payTypes.filter(function(item){
		return item.payName != "套餐";
	});
	laytpl(tpl).render(payTypes, function(html) {
		$("#payTypeDiv").empty();
		$("#payTypeDiv").append(html)
	});
}

/**
 * 得到套餐映射
 * id: endDate
 * @param meals
 * 
 */
function getMealMap(){
	var $ = layui.jquery;
	var meals = $("#meals").val();
	meals = JSON.parse(meals);
	var mealMap = {};
	$.each(meals, function(index, item){
		mealMap[item.id] = item.endTime;
	})
	
	return mealMap;
}
