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
    <input type="hidden" name="kind" id="kind" value="${kind }" />
	<div class="page-content-wrap">
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<div class="layui-inline">
						<label class="layui-form-label">会员类型</label>
						<div class="layui-input-inline">
							<select name="custType" id="custType" lay-verify="required">
							    <option value=""></option>
								<c:forEach items="${customTypes }" var="customType">
								<option value="${customType.id }">${customType.typeName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label" ><c:if test="${kind==1 }">服务类型</c:if><c:if test="${kind==2 }">产品类型</c:if></label>
						<div class="layui-input-inline">
							<select name="classId" id="classId" lay-verify="required">
							    <option value=""></option>
							    <c:forEach items="${classList }" var="s">
								<option value="${s.id }">${s.className}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">项目名称</label>
						<div class="layui-input-inline">
							<input type="text" name="itemName" id="itemName" autocomplete="off" class="layui-input" />
						</div>
					</div>
					<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
				</fieldset>

			</div>
		</form>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="custPriceInfo" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm" tabId="serveInfo" lay-event="editBatchData"><i class="layui-icon layui-icon-edit"></i>批量修改</button>
            </div>
            </script>
			<script type="text/html" id="barLine">
            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="saveOneData">保存</a>
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
	var　 url = "customprice/getList.do";
	var kind=$("#kind").val();
	var title="";
	if(kind=="1")
		title="服务";
	else if(kind=="2")
		title="产品";
	//初始化网格
	var tableIns = table.render({
		elem: '#custPriceInfo',
		url: url,
		method:'post',
		toolbar: '#toolbarTop',
		defaultToolbar: ['print', 'exports'],
		where: {
			"kind": $.trim($("#kind").val())
		},
		done: function(res, curr, count) {
		
		},
		cols: [
			[{
				type: 'checkbox',
				fixed: 'left',
				width: '5%'
			},{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center'
			},{
				field : 'itemId',
				hide : 'true',
				title : 'itemId',
				align : 'center'
			}, {
				field: 'name',
				width: '20%',
				align: 'center',
				title:  title
			}, {
				field: 'oldprice',
				width: '20%',
				align: 'center',
				title: '原价'
			},{
				field: 'price',
				width: '30%',
				align: 'center',
				title: '会员价',
				edit:'text'
			},{
				field: 'opt',
				width: '25%',
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
	
	table.on('edit(tableInfo)', function (obj) {
	      var value = obj.value;
	      var field = obj.field;
	      var objData=obj.data;
	      var inputElem = $(this);
	      var tdElem = inputElem.closest('td');
	      var valueOld = inputElem.prev().text();
	      var data = {};
	      var errMsg = ''; // 错误信息
	      if (field === 'price') {
	        // 会员价
	        var ival = parseFloat(value); 
	        if(isNaN(ival)){
	        	errMsg = '会员价只能输入数字';
	        }else if (ival < 0) {
	          errMsg = '会员价不能小于0';
	        }
	      }

	      if (errMsg) {
	        // 如果不满足的时候
	        data[field] = valueOld;
	        layer.msg(errMsg, {time: 1000, anim: 6, shade: 0.01}, function () {
	          inputElem.blur();
	          obj.update(data);
	          tdElem.click();
	        });
	      }
	    });
	
	//查询事件
	var active = {
		reload: function() {
			var custType=$("#custType").val();
			var classId=$("#classId").val();
			var kind=$("#kind").val();
			var title="";
			if(kind=="1")
				title="请选择服务类型";
			else if(kind=="2")
				title="请选择产品类型";
			if(custType==""){
				layer.msg("请选择会员种类", {icon : 2});
				return;
			}
			if(classId==""){
				layer.msg(title, {icon : 2});
				return;
			}
			// 执行重载
			table.reload('custPriceInfo', {
				where: {
					"itemName": $.trim($("#itemName").val()),
					"kind": $.trim($("#kind").val()),
					"custType":$("#custType").val(),
					"classId":$("#classId").val()
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
    var data = obj.data;
	switch (obj.event) {
		case 'saveOneData':
			var id=data.id;
			var itemId=data.itemId;
			var price=data.price;
			var kind=$("#kind").val();
			var custType=$("#custType").val();
			var data={"id":id,"itemId":itemId,"price":price,"kind":kind,"custType":custType}
			var result=saveOneData(data);
			if(result.retCode=='success'){
				layer.msg(result.retMsg, {icon : 1});
				layui.table.reload('custPriceInfo');
			}else{
				layer.msg(result.retMsg, {icon : 2});
			}
			break;
	}
}
/**
 * 保存
 */
function saveOneData(data){
	  var res=null;
		layui.$.ajax({
			type : "POST",
			url : "customprice/saveOneData.do",
			data :data,
			dataType : "json",
			async:false,
			success : function(result) {
				res=result;
			}
		});
		return res;
} 


/**
 * 批量修改
 */
function userDefinedToolBarHandle(target, obj){
	var $ = layui.jquery;
	switch (obj.event) {
		case 'editBatchData':
			var checkStatus = layui.table.checkStatus('custPriceInfo');
			if(checkStatus.data.length==0){
				layer.msg("请选择要修改的记录", {icon : 2});
				return;
			}
	  		var openw="600px";
	  		var openh="250px";
			layer.open({
				type: 2,
				title: "批量修改会员产品折扣价格",
				area: [openw, openh],
				fixed: false, //不固定
				content: "customprice/batchaddcustprice.do",
				btn: ['确定', '关闭'],
				yes: function(index, layero){
					var body = layer.getChildFrame('body', index); //得到iframe页的body内容
					var zc = body.find("#zc").val();
					if(zc==""){
						layer.msg("请输入折扣百分比", {icon : 2});
						return ;
					}
					var kind=$("#kind").val();
					var custType=$("#custType").val();
					var ids=JSON.stringify(checkStatus.data)
					var data={"zc":zc,"ids":ids,"kind":kind,"custType":custType};
					var result=saveBatchData(data);
					if(result.retCode=='success'){
						layer.close(index);
						layui.table.reload('custPriceInfo');
						layer.msg(result.retMsg, {icon : 1});
					}else{
						layer.msg(result.retMsg, {icon : 2});
					}
						
				}
			});
			break;
	}
}

function saveBatchData(data){
	  var res=null;
		layui.$.ajax({
			type : "POST",
			url : "customprice/saveBatchData.do",
			data :data,
			dataType : "json",
			async:false,
			success : function(result) {
				res=result;
			}
		});
		return res;
}

</script>

