<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
						<label class="layui-form-label">卷名称</label>
						<div class="layui-input-inline">
							<input type="text" name="cardName" id="cardName" autocomplete="off" class="layui-input" />
						</div>
					</div>
					<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
				</fieldset>

			</div>
		</form>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="cardSetInfo" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm" lay-event="openData"><i class="layui-icon">&#xe608;</i>新增</button>
            </div>
            </script>
			<script type="text/html" id="barLine">
            <a class="layui-btn layui-btn-xs" lay-event="editData">修改</a>
            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="sendCard">派发</a>
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
	//初始化网格
	var tableIns = table.render({
		elem: '#cardSetInfo',
		url: "cardset/getList.do",
		page: true,
		method:'post',
		//where:{"repertoryStatus":0},
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		//height: 'full-250',
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		done: function(res, curr, count) {},
		cols: [
			[{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center',
			}, {
				field: 'cardName',
				width: '30%',
				align: 'center',
				title: '卷名称'
			}, {
				field: 'cardValue',
				width: '15%',
				align: 'center',
				title: '面值',
				templet : function(d) {
					return parseFloat(d.cardValue).toFixed(2);
				}
			},{
				field: 'endDate',
				width: '15%',
				align: 'center',
				title: '截止日期'
			},{
				field: 'remark',
				width: '25%',
				align: 'center',
				title: '卷说明'
			},{
				field: 'opt',
				width: '15%',
				align: 'center',
				title: '操作',
				toolbar: '#barLine'
			}]
		]
	});
	//查询事件
	var active = {
		reload: function() {
			// 执行重载
			tableIns.reload({
				page: {
					curr: 1
					// 重新从第 1 页开始
				},
				where: {
					"cardName": $.trim($("#cardName").val())
				}
			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});
}

function userDefinedToolBarHandle(target, obj){
	var $ = layui.jquery;
	switch (obj.event) {
		case 'openData':
	  		var openw="600px";
	  		var openh="400px";
			var index=layer.open({
				type: 2,
				title: "新增卡卷",
				area: [openw, openh],
				fixed: false, //不固定
				content: 'cardset/addCardSet.do',
				maxmin: true,
				btn: ['确定', '关闭'],
				yes: function(index, layero){
					var body = layer.getChildFrame('body', index); //得到iframe页的body内容
					var cardName = body.find("#cardName").val();
					var cardValue = body.find("#cardValue").val();
					var endDate=body.find("#endDate").val();
					var remark=body.find("#remark").val();
					if($.trim(cardName) == ""){
						layer.msg("卡卷名称不能为空", {icon : 2});
						return ;
					}
					if($.trim(cardValue) == ""){
						layer.msg("金额不能为空", {icon : 2});
						return ;
					}
					if($.trim(endDate) == ""){
						layer.msg("有效期不能为空", {icon : 2});
						return ;
					}
					var data={"cardName":cardName,"cardValue":cardValue,"endDate":endDate,"remark":remark};
					var result=saveCardSetInfo(data);
					if(result.retCode=='success'){
						layer.close(index);
						layui.table.reload('cardSetInfo');
						layer.msg(result.retMsg, {icon : 1});
					}else{
						layer.msg(result.retMsg, {icon : 2});
					}
				}
			});
			layer.full(index);
			break;
	}
}
/**
 * 保存卡卷设定
 */
function saveCardSetInfo(data){
	var $=layui.$;
	var status;
	$.ajax({
		type : "POST",
		url : "cardset/saveData.do",
		data : data,
		dataType : "json",
		async:false,
		success : function(result) {
			status=result;
		}
	});
	return status;
}
/**
 * 卡卷推送
 */
function sendCardInfo(data){
	var $=layui.$;
	var status;
	$.ajax({
		type : "POST",
		url : "cardset/sendCardInfo.do",
		data : data,
		dataType : "json",
		async:false,
		success : function(result) {
			status=result;
		}
	});
	return status;
}


function userDefinedToolHandle(target, obj){
	var $ = layui.jquery;
	var data = obj.data;
	var id=data.id;
	switch (obj.event) {
		case 'editData':
			//设置一个对象来控制是否进入AJAX过程
			var post_flag = false; 
	  		var url=target.attr('data-url');
	  		var openw="600px";
	  		var openh="400px";
			var index=layer.open({
				type: 2,
				title: "修改卡卷",
				area: [openw, openh],
				fixed: false, //不固定
				content: 'cardset/addCardSet.do?id='+id,
				maxmin: true,
				btn: ['确定', '关闭'],
				yes: function(index, layero){
				    //如果正在提交则直接返回，停止执行
				    if(post_flag) return; 
				    //标记当前状态为正在提交状态
				    post_flag = true;
					var body = layer.getChildFrame('body', index); //得到iframe页的body内容
					var cardName = body.find("#cardName").val();
					var cardValue = body.find("#cardValue").val();
					var endDate=body.find("#endDate").val();
					var remark=body.find("#remark").val();
					if($.trim(cardName) == ""){
						layer.msg("卡卷名称不能为空", {icon : 2});
						return ;
					}
					if($.trim(cardValue) == ""){
						layer.msg("金额不能为空", {icon : 2});
						return ;
					}
					if($.trim(endDate) == ""){
						layer.msg("有效期不能为空", {icon : 2});
						return ;
					}
					var data={"cardName":cardName,"cardValue":cardValue,"endDate":endDate,"remark":remark,"id":id};
					var result=saveCardSetInfo(data);
					if(result.retCode=='success'){
						layer.close(index);
						layui.table.reload('cardSetInfo');
						layer.msg(result.retMsg, {icon : 1});
					}else{
						layer.msg(result.retMsg, {icon : 2});
					}
					post_flag =false;
				}
			});
			layer.full(index);
			break;
		case 'sendCard':
	  		var openw="600px";
	  		var openh="400px";
			//设置一个对象来控制是否进入AJAX过程
			var post_flag = false; 
			var index=layer.open({
				type: 2,
				title: "发送卡卷",
				area: [openw, openh],
				fixed: false, //不固定
				content: 'cardset/sendcard.do?id='+id,
				maxmin: true,
				btn: ['确定', '关闭'],
				yes: function(index, layero){
				    //如果正在提交则直接返回，停止执行
				    if(post_flag)
				    	return ;
				    //标记当前状态为正在提交状态
				    post_flag = true;
					var chooseAdids=$(layero).find("iframe")[0].contentWindow.chooseAdids;
					var checkAll=$(layero).find("iframe")[0].contentWindow.checkAll;
					var body = layer.getChildFrame('body', index); //得到iframe页的body内容
					//获取查询内容
					var keyword = body.find("#keyword").val();
					var custType = body.find("#custType").val();
					var sumNum = body.find("#sumNum").val();
					if(!checkAll&&chooseAdids.length<1){
						layer.msg("请选择派发对象", {icon : 2});
						post_flag =false;
						return ;
					}
					if($.trim(sumNum) == ""){
						layer.msg("派发数量不能为空", {icon : 2});
						post_flag =false;
						return ;
					}
					var data={"keyword":keyword,"custType":custType,"chooseAdids":chooseAdids.toString(),"sumNum":sumNum,"wechatCardId":id};
					var result=sendCardInfo(data);
					if(result.retCode=='success'){
						layer.close(index);
						layui.table.reload('cardSetInfo');
						layer.msg(result.retMsg, {icon : 1});
					}else{
						layer.msg(result.retMsg, {icon : 2});
					}
					post_flag =false;
				}
			});
			layer.full(index);
			break;
	}
}



</script>

