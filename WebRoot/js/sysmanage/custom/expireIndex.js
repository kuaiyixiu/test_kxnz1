function userDefinedLoadGrd($, table) {
	var time = getRangeDate();
	var startTime = time.substr(0, 10) + " 00:00:00";
	var endTime = time.substr(13) + " 23:59:59";

	//初始化网格
	var tableIns = table.render({
		elem: '#expireTable',
		method:'post',
		url: "custom/queryExpireMealDts.do",
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 'full-250',
		defaultToolbar: ['print', 'exports'],
		cols: [
			[{
				type: 'checkbox',
				fixed: 'left',
				width: '5%'
			}, {
				field: 'cardNo',
				width: '10%',
				align: 'center',
				title: '会员卡号'
			}, {
				field: 'custName',
				width: '20%',
				align: 'center',
				title: '会员名'
			}, {
				field: 'cellphone',
				align: 'center',
				title: '手机号'
			}, {
				field: 'mealDtName',
				align: 'center',
				title: '服务项目'
			}, {
				field: 'createDate',
				align: 'center',
				title: '购买时间',
				templet: function(row) {
					return formatDate1(row.createDate);
				}
			}, {
				field: 'endDate',
				align: 'center',
				title: '过期时间',
				templet: function(row) {
					return formatDate1(row.endDate);
				}
			},{
				field: 'quantity',
				align: 'center',
				title: '剩余次数'
			}]
		],
		where: {
			"dateRangeStartTime": startTime,
			"dateRangeEndTime": endTime
		}
		
	});
}

layui.use(['jquery', 'table', 'form', 'element'], function() {
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
			table.reload('expireTable', {
				page: {
					curr: 1
					// 重新从第 1 页开始
				},
				where: {
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