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
						<label class="layui-form-label">供应商</label>
						<div class="layui-input-inline">
							<input type="text" name="supplyName" id="supplyName" autocomplete="off" class="layui-input" />
						</div>
					</div>
					<div class="layui-inline">
					<label class="layui-form-label">审核状态</label>
					<div class="layui-input-inline">
						<select name="repertoryStatus" id="repertoryStatus">
							<option value="450">全部</option>
							<option value="0">临单</option>
							<option value="4">审批中</option>
							<option value="5">审批通过</option>
						</select>
					</div>
				</div>
					<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
				</fieldset>

			</div>
		</form>
		<blockquote class="layui-elem-quote"> <c:if test="${kind=='1' }">采购入库临时单据列表</c:if><c:if test="${kind=='2' }">采购退货临时单据列表</c:if> </blockquote>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="repertoryInInfo" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm" data-url="repertory/addData/${kind }.do" lay-event="openData"><i class="layui-icon">&#xe608;</i>新增</button>
            <button class="layui-btn layui-btn-sm delBtn" tabId="repertoryInInfo" data-url="repertory/delData.do" lay-event="delData"><i class="layui-icon">&#xe640;</i>删除</button>
            </div>
            </script>
			<script type="text/html" id="barLine">
            <a class="layui-btn layui-btn-xs" lay-event="editData">
			{{#  if(d.repertoryStatus === 4){ }}
				查看
			{{#  } }} 
			{{#  if(d.repertoryStatus != 4){ }}
				修改
			{{#  } }} 
			</a>
			{{#  if(d.repertoryStatus === 0){ }}
            	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-url="repertory/delData.do" tabId="repertoryInInfo">删除</a>
			{{#  } }} 
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
	var　 url = "repertory/getList/"+$("#kind").val()+".do";
	//初始化网格
	var tableIns = table.render({
		elem: '#repertoryInInfo',
		url: url,
		page: true,
		method:'post',
		where:{"repertoryStatus":450},
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		//height: 'full-250',
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		done: function(res, curr, count) {},
		cols: [
			[{
				type: 'checkbox',
				fixed: 'left',
				width: '5%'
			},{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center',
			}, {
				field: 'addTime',
				width: '15%',
				align: 'center',
				title: '日期'
			}, {
				field: 'supplyName',
				width: '25%',
				align: 'center',
				title: '供应商'
			},{
				field: 'userName',
				width: '20%',
				align: 'center',
				title: '采购员'
			},{
				field: 'repertoryStatus',
				width: '20%',
				align: 'center',
				title: '审核状态',
				templet: function(row) {
					return getStatus(row);
				}
			},{
				field: 'opt',
				width: '15%',
				align: 'center',
				title: '操作',
				toolbar: '#barLine',
				templet: function(row) {
					return getBarRow(row);
				}
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
			table.reload('repertoryInInfo', {
				page: {
					curr: 1
					// 重新从第 1 页开始
				},
				where: {
					"supplyName": $.trim($("#supplyName").val()),
					"repertoryStatus": $.trim($("#repertoryStatus").val())
				}
			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});

});

function userDefinedToolBarHandle(target, obj){
	var $ = layui.jquery;
	switch (obj.event) {
		case 'openData':
	  		var url=target.attr('data-url');
	  		var openw="600px";
	  		var openh="400px";
			layer.open({
				type: 2,
				title: "选择供应商",
				area: [openw, openh],
				fixed: false, //不固定
				content: url,
				btn: ['确定', '关闭'],
				yes: function(index, layero){
					var body = layer.getChildFrame('body', index); //得到iframe页的body内容
					var shopId = body.find("#shopId").val();
					var supplyId = body.find("#supplyId").val();
					var kind=body.find("#kind").val();
					var repertoryStatus=body.find("#repertoryStatus").val();
					var data={"shopId":shopId,"supplyId":supplyId,"kind":kind,"repertoryStatus":repertoryStatus};
					var result=saveRepertoryInfo(data);
					if(result.retCode=='success'){
						layer.close(index);
						layui.table.reload('repertoryInInfo');
						//layer.msg(result.retMsg, {icon : 1});
						var url="repertory/editData/"+result.retDesc+".do";
						addTempOrder(url);
						//location.href="index.html";
					}else{
						layer.msg(result.retMsg, {icon : 2});
					}
						
				}
			});
			break;
	}
}
/**
 * 保存入库表主表信息
 */
function saveRepertoryInfo(data){
	var $=layui.$;
	var status;
	$.ajax({
		type : "POST",
		url : "repertory/saveData.do",
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
	switch (obj.event) {
		case 'editData':
			var data = obj.data;
			var id=data.id;
			var url="repertory/editData/"+id+".do";
			addTempOrder(url);
			//location.href=url;
			break;
	}
}

/**
 * 新增tab页
 */
function addTempOrder(url){
	layui.use(['jquery', 'element'], function(){
		var $=layui.jquery;
		var element = parent.layui.element;
		var kind=$("#kind").val();
		var id = '';
		var title="";
		if(kind=="1"){//采购
			id = 'openCGFrmId';
			title="采购临单";
		}else if(kind=="2"){//退货
			id = 'openTHFrmId';
			title="退货临单";
		}
		url=url+"?kind="+kind;
		element.tabDelete('tab', id);
		element.tabAdd('tab', {
			title: title,
			content: '<iframe src="' + url + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
			id: id
		});
		element.tabChange('tab', id);
	});
}


function getStatus(row){
	if(row.repertoryStatus == 0){
		return "临单";
	}else if(row.repertoryStatus == 4){
		return "审批中";
	}else if(row.repertoryStatus == 5){
		return "审批通过";
	}
}


</script>

