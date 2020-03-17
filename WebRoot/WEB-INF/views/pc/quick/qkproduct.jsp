<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>连途</title>
<link rel="stylesheet" type="text/css" href="lib/admin/css/admin.css" />
<%@ include file="../base.jsp"%>
</head>
<body>
	<div class="page-content-wrap">
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
<%--				<fieldset>--%>
<%--					<legend>查询条件</legend>--%>
					<div class="layui-inline">
						<label class="layui-form-label">产品名称</label>
						<div class="layui-input-inline">
							<input type="text" name="productName" id="productName"
								autocomplete="off" class="layui-input" />
						</div>
					</div>
					<button class="layui-btn layui-btn-normal" data-type="reload"
						lay-filter="searchBtn" type="button">搜索</button>
<%--				</fieldset>--%>

			</div>
		</form>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="productInfo" lay-filter="tableInfo"></table>
			<script type="text/html" id="barLine">
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="returnProduct">退货</a>
            </script>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
/**
 * 加载表格
 * @param $
 * @param table
 * @param parentId 父级id
 * @param id 当前等级id
 * @param type back返回类型
 * @returns
 */
function userDefinedLoadGrd($, table, parentId, id) {
	var　 url = "quick/getList.do";
	//初始化网格
	var tableIns = table.render({
		elem: '#productInfo',
		url: url,
		page: true,
		method:'post',
		limit: 10,
		limits: [10],
		height: 'full-120',
		cols: [
			[{
				field : 'productId',
				hide : 'true',
				title : 'productId',
				align : 'center',
				hide : true,
			},{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center',
			},{
				field: 'addTime',
				width: '20%',
				align: 'center',
				title: '进货日期'
			},{
				field: 'productName',
				width: '25%',
				align: 'center',
				title: '产品'
			},{
				field: 'totalQuantity',
				width: '15%',
				align: 'center',
				title: '进货数量'
			},{
				field: 'price',
				width: '15%',
				align: 'center',
				title: '进货单价'
			},{
				field: 'quantity',
				width: '15%',
				align: 'center',
				title: '剩余数量'
			},{
				field: 'opt',
				width: '10%',
				align: 'center',
				title: '操作',
				toolbar: '#barLine'
			}]
		]
	});
}


layui.use(['jquery', 'table', 'form'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	form.render();
	//查询事件
	var active = {
		reload: function() {
			// 执行重载
			table.reload('productInfo', {
				page: {
					curr: 1
					// 重新从第 1 页开始
				},
				where: {
					"productName": $.trim($("#productName").val())
				}
			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});

});


function userDefinedToolHandle(target, obj){
	var $ = layui.jquery;
	switch (obj.event) {
		case 'returnProduct':
			var data = obj.data;
<%--			var id=data.id;--%>
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);//关闭当前页  	
            parent.setProductInfo(data);
			break;
	}
}

</script>

