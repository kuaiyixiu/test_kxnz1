function userDefinedLoadGrd($, table) {
	var tableIns = table.render({
		elem: '#customTypeInfo',
		url: "customType/queryAllCustomTypes.do",
		method: 'post',
		page: true,
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		height: 'full-250',
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		cols: [
			[ {
				field: 'typeName',
				width: '15%',
				align: 'center',
				title: '名称'
			}, {
				field: 'remark',
				align: 'center',
				title: '备注'
			}, {
				field: 'opt',
				width: '15%',
				align: 'center',
				title: '操作',
				templet: function(row) {
					return renderOptTpl(row);
				}
			}]
		]
	});

}

layui.use(['jquery', 'laydate', 'table', 'form', 'element'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;

});

function renderOptTpl(data){
	if(data.status == 1){
		return "";
	}
	var barLineStr=barLine.innerHTML;
	if(data.id==1 || data.id==2)
		return "";
	else{
		return barLineStr;
	}
	
	return barLine.innerHTML;
}