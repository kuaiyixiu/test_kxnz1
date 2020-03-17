function userDefinedLoadGrd($, table) {
	var time = getRangeDate();
	var startTime = time.substr(0, 10) + " 00:00:00";
	var endTime = time.substr(13) + " 23:59:59";
	//初始化网格
	var tableIns = table.render({
		elem: '#insureCarInfo',
		url: "car/queryCarInsure.do",
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
				title: '车牌'
			}, {
				field: 'carBrand',
				width: '20%',
				align: 'center',
				title: '品牌型号'
			}, {
				field: 'ownerName',

				align: 'center',
				title: '车主信息',
				templet: function(row) {
					return geCarMsg(row);
				}
			}, {
				field: 'remark',
				width: '10%',
				align: 'center',
				title: '类型',
				templet: "#typeTpl"
			}, {
				field: 'compulsoryDate',
				//width: '10%',
				align: 'center',
				title: '时间',
				templet: function(row) {
					return getTimeHtml(row);
				}
			}]
		],
		where: {
			"dateRangeStartTime": startTime,
			"dateRangeEndTime": endTime
		}
	});
}

layui.use(['jquery', 'laydate', 'table', 'form', 'element'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	var laydate = layui.laydate

	laydate.render({
		elem: "#dateInput",
		range: true,
		value: getRangeDate()
	});

	form.render();

	//查询事件
	var active = {
		reload: function() {
			// 执行重载
			table.reload('insureCarInfo', {
				page: {
					curr: 1
					// 重新从第 1 页开始
				},
				where: {
					"keyword": $("#keyword").val().trim(),
					"dateRangeStartTime": getStartTime(),
					"dateRangeEndTime": getEndTime()
				}

			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});
});

function getTimeHtml(row) {
	var commercialTime = formatDate1(row.commercialDate);
	commercialTime = commercialTime ? commercialTime : "暂无";
	var compulsoryTime = formatDate1(row.compulsoryDate);
	compulsoryTime = compulsoryTime ? compulsoryTime : "暂无";

	var tpl = dateTpl.innerHTML;
	var data = {
		commercialDate: commercialTime,
		compulsoryDate: compulsoryTime
	};

	var resultHtml = "";
	layui.laytpl(tpl).render(data, function(html) {
		resultHtml = html;
	});

	return resultHtml;
}
