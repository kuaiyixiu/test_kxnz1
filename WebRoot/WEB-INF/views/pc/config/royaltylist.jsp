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
	    <input type="hidden" name="kind" id="kind" value="${kind }" />
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<div class="layui-inline">
						<label class="layui-form-label">项目名称</label>
						<div class="layui-input-inline">
							<input type="text" name="royaltyName" id="royaltyName" autocomplete="off" class="layui-input" />
						</div>
					</div>
					<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
				</fieldset>

			</div>
		</form>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="royaltyInfo" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm" tabId="royaltyInfo" data-url="royalty/editData/${kind }.do" lay-event="setData"><i class="layui-icon">&#xe642;</i>批量修改</button>
            </div>
            </script>
			<script type="text/html" id="barLine">
            <a class="layui-btn layui-btn-xs" lay-event="setData" data-url="royalty/editData/${kind }.do" tabId="royaltyInfo">修改</a>
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
	var kind=$("#kind").val();
	var　 url = "royalty/getList/"+kind+".do";
	var col;
	if(kind=='1'){
		col=[
				[{
					type: 'checkbox',
					fixed: 'left',
					width: '5%'
				},{
					field : 'royaltyId',
					hide : 'true',
					title : 'ID',
					align : 'center',
				}, {
					field: 'royaltyName',
					width: '30%',
					align: 'center',
					title: '项目名称'
				}, {
					field: 'royaltyCount',
					width: '40%',
					align: 'center',
					title: '提成金额',
					templet: function(d){
						if(d.royaltyCount==null)
							return '0.00';
						else
							return parseFloat(d.royaltyCount).toFixed(2);
					}
				},{
					field: 'opt',
					width: '25%',
					align: 'center',
					title: '操作',
					toolbar: '#barLine'
				}]
			];
	}else{
		col=[
				[{
					type: 'checkbox',
					fixed: 'left',
					width: '5%'
				},{
					field : 'royaltyId',
					hide : 'true',
					title : 'ID',
					align : 'center',
				}, {
					field: 'royaltyName',
					width: '30%',
					align: 'center',
					title: '项目名称'
				}, {
					field: 'royaltyType',
					width: '20%',
					align: 'center',
					title: '提成类型',
					templet: function(d){
						if(d.royaltyType==null)
							return '固定值';
						else{
							if(d.royaltyType=='2')
								return '百分比';
							else
								return '固定值';
						}
					}
				},{
					field: 'royaltyCount',
					width: '20%',
					align: 'center',
					title: '提成金额',
					templet: function(d){
						if(d.royaltyCount==null)
							return '0.00';
						else
							return parseFloat(d.royaltyCount).toFixed(2);
					}
				},{
					field: 'opt',
					width: '25%',
					align: 'center',
					title: '操作',
					toolbar: '#barLine'
				}]
			];
	}
	//初始化网格
	var tableIns = table.render({
		elem: '#royaltyInfo',
		url: url,
		page: true,
		method:'post',
		limit: 10,
		limits: [10, 20, 30, 40, 50,500],
		//height: 'full-250',
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		done: function(res, curr, count) {},
		cols: col
	});
}
/**
 * 批量修改
 */
function userDefinedToolBarHandle(target, obj){
	var $ = layui.jquery;
	switch (obj.event) {
		case 'setData':
			var checkStatus = layui.table.checkStatus('royaltyInfo');
			if(checkStatus.data.length==0){
				layer.msg("请选择要修改的记录", {icon : 2});
				return;
			}
	  		var url=target.attr('data-url');
	  		var kind=$("#kind").val();
	  		var openw;
	  		var openh;
	  		if(kind=='1'){
	  			openw="600px";
	  			openh="200px";
	  		}else{
	  			openw="700px";
	  			openh="400px;"
	  		}
			layer.open({
				type: 2,
				title: "批量修改服务施工提成",
				area: [openw, openh],
				fixed: false, //不固定
				content: url,
				btn: ['确定', '关闭'],
				yes: function(index, layero){
					var body = layer.getChildFrame('body', index); //得到iframe页的body内容
					var royaltyCount = body.find("#royaltyCount").val();
					var royaltyType="1";
					if(kind!='1')
						royaltyType=body.find("input[name='royaltyType']:checked").val();
					var ids=getCkData(checkStatus.data);
					var data={"royaltyCount":royaltyCount,"ids":ids,"kind":kind,"royaltyType":royaltyType,"statue":"add"};
					var result=saveRoyaltyInfo(data);
					if(result.retCode=='success'){
						layer.close(index);
						layui.table.reload('royaltyInfo');
						layer.msg(result.retMsg, {icon : 1});
					}else{
						layer.msg(result.retMsg, {icon : 2});
					}
						
				}
			});
			break;
	}
}
/**
 * 单笔修改
 */
function userDefinedToolHandle(target, obj){
	var $ = layui.jquery;
	switch (obj.event) {
		case 'setData':
	  		var url=target.attr('data-url');
	  		var kind=$("#kind").val();
	  		var openw;
	  		var openh;
	  		if(kind=='1'){
	  			openw="600px";
	  			openh="200px";
	  		}else{
	  			openw="700px";
	  			openh="400px;"
	  		}
			var data = obj.data;
			layer.open({
				type: 2,
				title: "修改服务施工提成",
				area: [openw, openh],
				fixed: false, //不固定
				content: url+"?royaltyId="+data.royaltyId,
				btn: ['确定', '关闭'],
				yes: function(index, layero){
					var body = layer.getChildFrame('body', index); //得到iframe页的body内容
					var royaltyCount = body.find("#royaltyCount").val();
					var ids=data.royaltyId;
					var royaltyType="1";
					if(kind!='1')
						royaltyType=body.find("input[name='royaltyType']:checked").val();
					var data1={"royaltyCount":royaltyCount,"ids":ids,"kind":kind,"royaltyType":royaltyType,"statue":"modify"};
					var result=saveRoyaltyInfo(data1);
					if(result.retCode=='success'){
						layer.close(index);
						layui.table.reload('royaltyInfo');
						layer.msg(result.retMsg, {icon : 1});
					}else{
						layer.msg(result.retMsg, {icon : 2});
					}
						
				}
			});
			break;
	}
}
/**
 * 查询选中的网格信息
 */
function getCkData(data){
	var ids="";
	for(var i=0;i<data.length;i++){
		var rowInfo=data[i];
		ids=ids+rowInfo.royaltyId+";";
	}
	return ids;
}

function saveRoyaltyInfo(data){
	var $=layui.$;
	var status;
	$.ajax({
		type : "POST",
		url : "royalty/saveData.do",
		data : data,
		dataType : "json",
		async:false,
		success : function(result) {
			status=result;
		}
	});
	return status;
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
			table.reload('royaltyInfo', {
				page: {
					curr: 1
					// 重新从第 1 页开始
				},
				where: {
					"royaltyName": $.trim($("#royaltyName").val())
				//	"description": $.trim($("#description").val())
					//,"parentId": $("#parentId").val()
				}
			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});

});

</script>

