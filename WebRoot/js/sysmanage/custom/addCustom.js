layui.use(['laydate', 'jquery', 'laytpl', 'form', 'element'], function() {
	var laydate = layui.laydate;
	var $ = layui.jquery;
	var form = layui.form;
	//执行一个laydate实例
	laydate.render({
		elem: '#date' //指定元素
	});

	var customTypes = $("#customTypes").val();
	customTypes = JSON.parse(customTypes);
	var tpl = customTypesTpl.innerHTML;

	var laytpl = layui.laytpl;
	laytpl(tpl).render(customTypes, function(html) {
		$("#customSelect").empty();
		$("#customSelect").append(html)
	});

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

	$("#addCustomForm").on("click", "[data-id='carButton']", function() {
		var othis = $(this),
			method = othis.data('method');
		active[method].call(this, othis);
	})

	form.on('submit(formSubmitBtn)', function(data) {
		 if(!layui.$(".btn-submit").hasClass("layui-btn-disabled")){
				layui.$(".btn-submit").addClass("layui-btn-disabled");
				layui.$(".btn-submit").attr("disabled","disabled");
				$.ajax({
					type: "POST",
					url: "custom/saveCustom.do",
					data: getSaveCutomParam(data.field),
					dataType: "json",
					success: function(result) {
						if (result.retCode == 'success') {
							parent.layer.msg(result.retMsg, {
								icon: 1
							});
							// 关闭会员开卡界面
							parent.layui.element.tabDelete('tab', 'addCustomPage');
						} else {
							layer.msg(result.retMsg, {
								icon: 2
							});
						}
					}
				});
         }
	});
	
	form.verify({
		  carNo: function(value, item){ //value：表单的值、item：表单的DOM对象
			  if (!isLicensePlate(value))
				  return "请输入正确的车牌号";
		  }
		});      
	
	$(document).on('click', '#closeButton', function() {
		parent.layui.element.tabDelete('tab', 'addCustomPage');
	});
	
});

/**
 * 获得保存custom的参数
 */
function getSaveCutomParam(data) {
	var $ = layui.jquery;
	var carListLength = $(".childrenCar").length;
	if (carListLength === 0) {
		return data;
	}

	// 车辆信息
	var carDataList = [];
	$(".childrenCar").each(function(index, item) {
		var carChildrenData = {
			carNo: $(item).find("[data-id='carNumber']").val().toUpperCase()
		}

		carDataList.push(carChildrenData);
	})

	data.carDataList = JSON.stringify(carDataList);

	return data;
}