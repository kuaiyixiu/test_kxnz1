function userDefinedLoadGrd($, table) {
	var time = getRangeDate();
	var startTime = time.substr(0, 10) + " 00:00:00";
	var endTime = time.substr(13) + " 23:59:59";
	//初始化网格
	var tableIns = table.render({
		elem: '#maintainCarInfo',
		url: "car/queryCarMaintains.do",
		method: 'post',
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
				field: 'remindTime',
				width: '20%',
				align: 'center',
				title: '下次保养时间',
				templet: function(row) {
					return formatDate1(row.remindTime);
				}
			}, {
				field: 'createTime',
				align: 'center',
				title: '创建保养时间',
				templet: function(row) {
					return formatDate1(row.createTime);
				}
			}, {
				field: 'progressMileage',
				width: '10%',
				align: 'center',
				title: '进站里程'
			}, {
				field: 'remindMileage',
				width: '10%',
				align: 'center',
				title: '提醒里程'
			}, {
				field: 'remark',
				align: 'center',
				title: '备注'
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
			table.reload('maintainCarInfo', {
				page: {
					curr: 1
					// 重新从第 1 页开始
				},
				where: {
					"dateRangeStartTime": getStartTime(),
					"dateRangeEndTime": getEndTime(),
					"keyword": $("#keyword").val().trim()
				}

			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});
});
