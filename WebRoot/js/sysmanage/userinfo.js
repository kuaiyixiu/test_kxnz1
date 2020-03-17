function userDefinedLoadGrd($, table) {
	//初始化网格
	var tableIns = table.render({
		elem : '#userInfo',
		url : 'user/getList.do',
		page : true,
		limit : 10,
		limits : [10, 20, 30, 40, 50 ],
		height : 'full-250',
		toolbar : '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		cols : [ [ {
			field : 'roleId',
			hide : 'true',
			title : 'roleId',
			align : 'center',
		},{
			field : 'id',
			hide : 'true',
			title : 'ID',
			align : 'center',
		}, {
			field : 'userName',
			width : '10%',
			align : 'center',
			title : '用户名'
		},{
			field : 'userRealname',
			width : '15%',
			align : 'center',
			title : '真实姓名'
		},{
			field : 'post',
			width : '10%',
			align : 'center',
			title : '职务'
		},{
			field : 'idCard',
			width : '10%',
			align : 'center',
			title : '身份证号'
		},{
			field : 'entryDate',
			width : '10%',
			align : 'center',
			title : '入职时间'
		},{
			field : 'userPhone',
			width : '10%',
			align : 'center',
			title : '手机号'
		}, {
			field : 'statusStr',
			width : '10%',
			align : 'center',
			title : '员工状态'
		},{
			field : 'opt',
			width : '25%',
			align : 'center',
			title : '操作',
			templet: function(row) {
				return getOptHtml(row);
			}
		} ] ]
	});
}

layui.use([ 'jquery', 'table','form' ], function() {
	var table = layui.table;
	var $ = layui.$;
	var form = layui.form;
	form.render();
    //查询事件
	var active = {
		reload : function() {
			// 执行重载
			table.reload('userInfo', {
				page : {
					curr : 1
				// 重新从第 1 页开始
				},
				where : {
					"userName" : $.trim($("#userName").val()),
					"status" : $.trim($("#status").val()),
					"userRealname" : $.trim($("#userRealname").val())
				}
			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});

});
/**
 * 判断用户上限
 * @param $
 */
function userDefinedBeforeAddData($){
	var canAdd=true;
	$.ajax({
		type : "POST",
		url : "user/checkUserCount.do",
		data : {},
		dataType : "json",
		async:false,
		success : function(result) {
			if (result.retCode == 'success') {
				canAdd=true;
			} else {
				canAdd=false;
				layer.msg(result.retMsg, {
					icon : 2
				});
			}
		}
	});
	return canAdd;
}

function getOptHtml(row){
	var barLine1Str=barLine1.innerHTML;
	var barLine2Str=barLine2.innerHTML;
	if(row.roleId==1 || row.roleId==2)//排除管理员账户和老板账户 内置用户不能删除
		return barLine2Str;
	else{
		return barLine1Str;
	}
		
}