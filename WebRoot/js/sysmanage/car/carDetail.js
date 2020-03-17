function userDefinedLoadGrd($, table) {
	renderCarDetailHtml();
	var tableInfos = table.render({
		elem: '#carMaintainInfo',
		method: 'post',
		url: "car/queryCarMaintain.do",
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: '300px',
		page: true,
		defaultToolbar: ['print', 'exports'],
		cols: [
			[{
				field: 'createTime',
				width: '10%',
				align: 'center',
				title: '到店保养时间',
				templet: function(row) {
					return formatDate1(row.createTime);
				}
			}, {
				field: 'remindTime',
				width: '20%',
				align: 'center',
				title: '保养提醒时间',
				templet: function(row) {
					return formatDate1(row.remindTime);
				}
			}, {
				field: 'progressMileage',
				width: '20%',
				align: 'center',
				title: '进站里程数'
			}, {
				field: 'remindMileage',
				width: '20%',
				align: 'center',
				title: '提醒里程数'
			}, {
				field: 'remark',
				align: 'center',
				title: '备注'
			}]
		],
		where: {
			"carId": $("[data-id='carId']").val()
		}
	});
	
	initConsumeTable();
}

layui.use(['jquery', 'table', 'form', 'element'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	
	layui.laydate.render({
		elem: "#dateInput",
		range: true,
		value: getRangeDate()
	});

});

/**
 * 初始化消费列表
 */
function initConsumeTable(){
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	
	var tableInfo = table.render({
		elem: '#consumeInfo',
		method: 'post',
		url: "car/queryConsumeOrders.do",
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: '300px',
		page: true,
		defaultToolbar: ['print', 'exports'],
		cols: [
			[{
				field: 'finishTime',
				width: '10%',
				align: 'center',
				title: '时间',
				templet: function(row) {
					return formatDateMinutes(row.finishTime);
				}
			}, {
				field: 'orderStatusName',
				width: '20%',
				align: 'center',
				title: '状态'
			}, {
				field: 'orderStatusName',
				width: '30%',
				align: 'left',
				title: '订单概要',
				templet: function(row) {
					return getContent(row)
				}
			}, {
				field: 'payAmount',
				width: '20%',
				align: 'center',
				title: '消费金额'
			}, {
				field: 'opt',
				//width: '25%',
				align: 'center',
				title: '操作',
				toolbar: '#barLine'
			}]
		],
		where: {
			"dateRangeStartTime": getStartTime(),
			"dateRangeEndTime": getEndTime(),
			"carNumber": $("#carNumber").val()
		}
	});	

	
	//查询事件
	var active = {
		reload: function() {
			// 执行重载
			table.reload('consumeInfo', {
				page: {
					curr: 1
				},
				where: {
					"dateRangeStartTime": getStartTime(),
					"dateRangeEndTime": getEndTime(),
					"carNumber": $("#carNumber").val()
				}
			});
		}
	};
	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});
}

function renderCarDetailHtml() {
	var $ = layui.jquery;
	var carDetail = $("#carDetail").val();
	carDetail = JSON.parse(carDetail);
	if (carDetail.carType == 1) {
		carDetail.ownerName = carDetail.custName;
		carDetail.ownerCellphone = carDetail.cellphone;
	}

	for (var key in carDetail) {
		$("[data-id=" + key + "]").html(carDetail[key]);
	}
}


function getContent(row){
	var $ = layui.jquery,
		laytpl = layui.laytpl;
	
	var html = "";
	var ordersServeList = row.ordersServeList;
	var ordersProductList = row.ordersProductList;
	if(!(ordersServeList && ordersProductList)){
		return html;
	}
	var pId = row.id;
	var product = "";
	var server = "";
	var total = 0;
	$.each(ordersProductList, function(index, item){
		laytpl(productTpl.innerHTML).render(item, function(tplHtml) {
			product += tplHtml;
		});
		total += item.price;
	});
	
	
	$.each(ordersServeList, function(index, item){
		laytpl(serverTpl.innerHTML).render(item, function(tplHtml) {
			server +=tplHtml;
		});
		
		total += item.price;
	});
	if(product!=""){
		product = "<b>产品:</b><ul class='tableUl'>"+product+"</ul>"; 
	}
	
	if(server!=""){
		server = "<b>服务:</b><ul class='tableUl'>"+server+"</ul>";
	}
	
	var totalHtml = "<b>付款:</b><ul class='tableUl'>"+total+"</ul>";
	
	return product + server + totalHtml;
}


function userDefinedToolHandle($arg, obj) {
	var $ = layui.jquery;
	if (obj.event != 'detail') {
		return;
	}
	var data = obj.data;
	var id = data.id;
	var url = $arg.attr('data-url');
	url = url + "?id=" + id;
	var openw = '1000px';
	var openh = '700px';
	
	
	var iframeObj = $(window.frameElement).attr('name');
	//将iframeObj传递给父级窗口,执行操作完成刷新
	
	var option = {
			type: 2,
			title: "订单详情",
			area: [openw, openh],
			fixed: false, //不固定
			content: url
		};
	
	var index = layer.open(option);
}