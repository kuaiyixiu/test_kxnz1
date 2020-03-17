layui.use(['laydate', 'jquery', 'laytpl', 'form', 'element'], function() {
	var laydate = layui.laydate;
	var $ = layui.jquery;
	var form = layui.form;
	loadPayTypes();

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
					parent.layui.table.reload('customMealInfo');
					
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
	
/*	$(document).on('click', '#closeButton', function() {
		parent.layui.element.tabDelete('tab', 'addCustomPage');
	})
	
	form.on('select(mealsSelect)', function(data){
		changeMeals(data.value);
	});*/
	
});


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
			loadMealDtHtml(result);
		}
	});
}

function loadPayTypes(){
	var $ = layui.jquery,
	laytpl = layui.laytpl;
	
	var payTypes = $("#payTypes").val();
	payTypes = JSON.parse(payTypes);
	var tpl = payTypesTpl.innerHTML;

	laytpl(tpl).render(payTypes, function(html) {
		$("#payTypeDiv").empty();
		$("#payTypeDiv").append(html)
	});
}