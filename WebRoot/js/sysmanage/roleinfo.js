function userDefinedLoadGrd($, table) {
	//初始化网格
	var tableIns = table.render({
		elem : '#roleInfo',
		url : 'role/getList.do',
		page : true,
		limit : 10,
		limits : [10, 20, 30, 40, 50 ],
		height : 'full-250',
		toolbar : '#toolbarTop',
		cols : [ [{
			field : 'id',
			hide : 'true',
			title : 'ID',
			align : 'center',
			width : '5%'
		}, {
			field : 'name',
			width : '20%',
			align : 'center',
			title : '角色名称'
		}, {
			field : 'enableText',
			width : '10%',
			align : 'center',
			title : '是否启用'
		}, {
			field : 'description',
			width : '20%',
			align : 'center',
			title : '描述'
		}, {
			field : 'opt',
			width : '20%',
			align : 'center',
			title : '操作',
			templet: function(row) {
				return getOptHtml(row);
			}
		} ] ]
	});
}

layui.use([ 'jquery', 'table' ], function() {
	var table = layui.table;
	var $ = layui.$;

    //查询事件
	var active = {
		reload : function() {
			// 执行重载
			table.reload('roleInfo', {
				page : {
					curr : 1
				// 重新从第 1 页开始
				},
				where : {
					"name" : $.trim($("#name").val())
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
 * 授权
 * @param $
 * @param table
 * @param dialog
 * @param obj
 */
function userDefinedToolHandle(jo, obj){
	var iframeObj = layui.$(window.frameElement).attr('name');
	var data = obj.data;
	var $=layui.$;
	if(obj.event=="setPermission"){//角色授权
  		var url=jo.attr('data-url');
  		var id=data.id;
		var openw=jo.attr('openw');
		var openh=jo.attr('openh');
		if(openw==undefined)
			openw="700px";
		if(openh==undefined)
			openh="620px"
		layer.open({
			type: 2,
			title: "授权",
			area: [openw, openh],
			fixed: false, //不固定
			content: url+"?id="+id,
			btn: ['确定', '关闭'],
			yes: function(index, layero){
				var xtree1=$(layero).find("iframe")[0].contentWindow.xtree1;
				var arr=xtree1.GetAllCheckBox();
				var resIds="";//所有菜单的id
				for(var i=0;i<arr.length;i++){
					var obj=arr[i];
					if(obj.checked)
						resIds=resIds+obj.value+",";
				}
				var body = layer.getChildFrame('body', index); //得到iframe页的body内容
				var roleId = body.find("#roleId").val();
				var data={"roleId":roleId,"resIds":resIds};
				var result=saveResRoleInfo(data);
				if(result.retCode=='success'){
					layer.close(index);
					layer.msg(result.retMsg, {icon : 1});
				}else{
					layer.msg(result.retMsg, {icon : 2});
				}
					
			}
		});
	}
	
	}


function saveResRoleInfo(data){
	var $=layui.$;
	var status;
	$.ajax({
		type : "POST",
		url : "role/savePermission.do",
		data : data,
		dataType : "json",
		async:false,
		success : function(result) {
			status=result;
		}
	});
	return status;
}


function getOptHtml(row){
	var roleId=parent.layui.$("#userRoleLevel").val();
	var barLineStr=barLine.innerHTML;
	if(roleId==1)
		return barLineStr;
	if(row.id==1 || row.id==2)//排除管理员账户和老板账户 内置用户不能删除
		return "";
	else{
		return barLineStr;
	}
		
}