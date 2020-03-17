layui.use(['laydate', 'jquery', 'laytpl', 'form', 'element'], function() {
	var laydate = layui.laydate;
	var $ = layui.jquery;
	var form = layui.form;
	//执行一个laydate实例
	laydate.render({
		elem: '#date' //指定元素
	});

	renderCustomTypes();
	// 性别
	var sexVal = $("#editSex").val();
	sexVal = sexVal?sexVal:"0"; 
	$("input[name=sex][value=" + sexVal + "]").attr("checked", true);
	renderCarList();


	var active = {
		addCarTpl: function() {
			var carHtml = carTpl.innerHTML;
			$("#carList").append(carHtml);
			return false;
		},
		delCarTpl: function(othis) {
			othis.parent().remove();
		}
	};

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
					url: "custom/updateCustom.do",
					data: getUpdateCutomParam(data.field),
					dataType: "json",
					success: function(result) {
						if (result.retCode == 'success') {
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index); //关闭当前页  
							parent.layui.table.reload('customInfo');

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
		}
	});
	
	form.verify({
		  carNo: function(value, item){ //value：表单的值、item：表单的DOM对象
			  if (!isLicensePlate(value))
				  return "请输入正确的车牌号";
		  }
		}); 
});

/**
 * 获得保存custom的参数
 */
function getUpdateCutomParam(data) {
	var $ = layui.jquery;
	var carListLength = $(".childrenCar").length;
	if (carListLength === 0) {
		return data;
	}

	// 车辆信息
	var carDataList = [];
	$(".childrenCar").each(function(index, item) {
		var carChildrenData = {
			carNo: $(item).find("[data-id='carNumber']").val().toUpperCase(),
			id: $(item).find("[data-id='id']").val()
		}

		carDataList.push(carChildrenData);
	})

	data.carDataList = JSON.stringify(carDataList);

	return data;
}

/**
 * 渲染选中会员类型
 */
function renderCustomTypes() {
	var $ = layui.jquery,
		laytpl = layui.laytpl;

	var customTypes = window.parent.document.getElementById("customTypes").value;
	customTypes = JSON.parse(customTypes);
	var tpl = customTypesTpl.innerHTML;
	var data = {
		list: customTypes,
		selectedId: $("#editCustType").val()
	};

	laytpl(tpl).render(data, function(html) {
		$("#customSelect").empty();
		$("#customSelect").append(html)
	});
}

/**
 * 渲染车辆列表
 */
function renderCarList() {
	var $ = layui.jquery,
		laytpl = layui.laytpl,
		form = layui.form;

	var carList = layui.jquery("#editCarDataList").val();
	lists = JSON.parse(carList);
	if (lists.length === 0) {
		return;
	}

	var tpl = carListTpl.innerHTML;
	var data = {
		list: lists
	};

	laytpl(tpl).render(data, function(html) {
		$("#carList").empty();
		$("#carList").append(html)
	});

	form.render();
}