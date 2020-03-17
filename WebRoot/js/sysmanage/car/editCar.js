layui.use(['laydate', 'jquery', 'laytpl', 'form', 'element'], function() {
	var laydate = layui.laydate;
	var $ = layui.jquery;
	var form = layui.form;
	$("[data-id='dateInput']").each(function() {
		laydate.render({
			elem: this,
			trigger: 'click'
		});
	})

	var carDetail = JSON.parse($("#carDetail").val());
	renderDefaultForm(carDetail);
	renderVehicleType(carDetail.vehicleType);
	
	form.on('submit(formSubmitBtn)', function(data) {
		$.ajax({
			type: "POST",
			url: "car/updateCar.do",
			data: formatParam(data.field, carDetail),
			dataType: "json",
			success: function(result) {
				if (result.retCode == 'success') {
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index); //关闭当前页  
					parent.layui.table.reload('carInfo');

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
});

/**
 * 格式化参数
 * 时间name变更
 * @param data
 */
function formatParam(data, carDetail){
	data.compulsoryDate = data.compulsoryDateStr;
	data.commercialDate = data.commercialDateStr;
	data.checkDate = data.checkDateStr;
	data.vehicleType = Number(layui.jquery("input[name=vehicleType]:checked").val());
	
	return data;
}

/**
 * 渲染基本元素
 * @param carDetail
 */
function renderDefaultForm(carDetail) {
	var $ = layui.jquery;
	var defaultRenderEles = ["carBrand", "carVin", "carEngine", "compulsoryDateStr",
		"commercialDateStr", "checkDateStr", "remark","customId","id","carType"
	];
	for (var key in carDetail) {
		if ($.inArray(key, defaultRenderEles) != -1) {
			$("[name=" + key + "]").val(carDetail[key]);
		}
	}
	
	/**
	 * type{0:非会员,1:会员}
	 * 0:ownerName:ownerCellphone;
	 * 1:custName:cellphone;
	 */
	var type = carDetail.carType;
	var ownerName = type == 1?carDetail.custName:carDetail.ownerName;
	$("input[name='ownerName']").val(ownerName);
	var ownerCellphone = type == 1?carDetail.cellphone:carDetail.ownerCellphone;
	$("input[name='ownerCellphone']").val(ownerCellphone);
}

/**
 * 渲染车辆类型
 * @param type
 */
function renderVehicleType(type) {
	var $ = layui.jquery;
	var form = layui.form;
	var customTypes = JSON.parse($("#carTypes").val());
	var tpl = vehicleTypeTpl.innerHTML;
	// 如果不存在，则最后一个选中
	type = type ? type : customTypes.length;
	var data = {
		list: customTypes,
		checkid: type
	};

	layui.laytpl(tpl).render(data, function(html) {
		$("#vehicleType").empty();
		$("#vehicleType").append(html)
	});

	form.render();
}