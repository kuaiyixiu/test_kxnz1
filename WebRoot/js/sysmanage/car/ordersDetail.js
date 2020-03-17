function userDefinedLoadGrd($, table) {
	var tableInfos = table.render({
		elem: '#serversInfo',
		method: 'post',
		url: "car/queryOrdersServers.do",
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: '200px',
		defaultToolbar: ['print', 'exports'],
		cols: [
			[{
				field: 'serveName',
				width: '20%',
				align: 'center',
				title: '服务名称'
			}, {
				field: 'aa',
				width: '10%',
				align: 'center',
				title: '销售人员'
			}, {
				field: 'progressMileage',
				width: '10%',
				align: 'center',
				title: '施工人员'
			}, {
				field: 'price',
				width: '10%',
				align: 'center',
				title: '应付金额'
			}, {
				field: 'doneTime',
				align: 'center',
				title: '施工完成时间',
				templet: function(row) {
					return formatDateMinutes(row.doneTime);
				}
			}]
		],
		where: {
			"orderId": $("#orderId").val()
		}
	});
	
	initProductTable();
}

layui.use(['jquery', 'table', 'form', 'element'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	
});

/**
 * 初始化消费列表
 */
function initProductTable(){
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	
	var tableInfo = table.render({
		elem: '#productInfo',
		method: 'post',
		url: "car/queryOrdersProduct.do",
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: '200px',
		cols: [
			[{
				field: 'productName',
				width: '10%',
				align: 'center',
				title: '产品名'
			}, {
				field: 'productType',
				width: '20%',
				align: 'center',
				title: '型号'
			}, {
				field: 'quantity',
				width: '30%',
				align: 'center',
				title: '数量'
			}, {
				field: 'price',
				width: '20%',
				align: 'center',
				title: '金额'
			}]
		],
		where: {
			"orderId": $("#orderId").val()
		}
	});	
}
