<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<link rel="stylesheet" type="text/css"
	href="lib/admin/layui/css/layui.css" />
<script src="lib/admin/layui/layui.js" type="text/javascript"
	charset="utf-8"></script>
<script src="lib/admin/js/common.js" type="text/javascript"
	charset="utf-8"></script>
<script type="text/javascript">
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		var element = layui.element;
		form.render();
		refreshDataDiv($("#userId").val());
		form.on('checkbox(ckFilter)', function(data) {
			var id = data.elem.id;
			var idArr = id.split("_");
			var pid = idArr[1];
			var flag = idArr[0];
			if (flag == "ckAll") {
				var divArr = $("#div_" + pid + " input");
				if (data.elem.checked) {
					$.each(divArr, function(i, n) {
						this.checked = true;
						form.render();
					});
				} else {
					$.each(divArr, function(i, n) {
						this.checked = false;
						form.render();
					});
				}
			}
		});
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			var ids = getCheckedInfo();
			$.ajax({
				type : "POST",
				url : "userskills/saveData.do",
				data : {
					ids : ids,
					userId : $("#userId").val(),
				},
				dataType : "json",
				success : function(result) {
					if (result.retCode == 'success') {
						layer.msg(result.retMsg, {
							icon : 1
						});
					} else {
						layer.msg(result.retMsg, {
							icon : 2
						});
					}
				}
			});
			return false;
		});

		function getCheckedInfo() {
			var ids = "";
			var divArr = $("#ckdata input");
			$.each(divArr, function(i, n) {
				var idArr = this.id.split("_");
				var dtId = idArr[1];
				var flag = idArr[0];
				if (flag == "dt") {
					if (this.checked)
						ids = ids + dtId + ";";
				}
			});
			return ids;
		}
		//下拉框事件监听
		form.on('select(selectFilter)', function(data){
			refreshDataDiv(data.value);
		}); 
		function refreshDataDiv(userId){//重新加载div
			var data=getDataInfo(userId);
		    var html='';
		    for(var i=0;i<data.length;i++){
		    	var serveClass=data[i].serveClass;
		    	html=html+'<div class="layui-card" style="margin-left: 1%;">';
		    	html=html+'<div class="layui-card-header">'+serveClass.className+'</div>';
		    	html=html+'<div class="layui-card-body">';
		    	html=html+'<div class="layui-input-block">';
		    	html=html+'<input type="checkbox" title="全选" id="ckAll_'+serveClass.id+'" lay-filter="ckFilter" ';
		    	var serveList=data[i].serveList;
		    	var isck=getIsck(serveList);
		    	if(isck)
		    		html=html+' checked="checked" ';
		    	html=html+'/></div>';
		    	html=html+'<div id="div_'+serveClass.id+'">';
		    	for(var j=0;j<serveList.length;j++){
		    		var serve=serveList[j];
		    		html=html+'<div class="layui-input-block">';
		    		html=html+'<input type="checkbox" value="1" id="dt_'+serve.id+'" title="'+serve.serveName+'" lay-filter="ckFilter" ';
		    		if(serve.userId!=null && typeof(serve.userId)!="undefined")
		    			html=html+' checked="checked" ';
		    		html=html+'/></div>';
		    	}
		    	html=html+'</div>';
		    	html=html+'</div>';
		    	html=html+'</div>';
		    }
		    $("#ckdata").html(html);
		    form.render();
		}
		
		function getIsck(serveList){
			var isck=true;
	    	for(var j=0;j<serveList.length;j++){
	    		var serve=serveList[j];
	    		if(serve.userId==null || typeof(serve.userId)=="undefined"){
	    			isck=false;
	    			break;
	    		}
	    	}
	    	return isck;
		}
		
		function getDataInfo(userId){
			var data;
			$.ajax({
				type : "POST",
				url : "userskills/getData.do",
				data : {userId : userId},
				dataType : "json",
				async:false,
				success : function(result) {
					data=result;
				}
			});
			return data;
		}

	});
</script>
</head>
<body style="background-color: #F0F8FF;">
	<form class="layui-form" action="" id="form"
		style="width: 98%;height: 98%;margin-top: 1%;">
		<div class="layui-form-item" style="width: 50%;">
			<label class="layui-form-label">选择员工</label>
			<div class="layui-input-block">
				<select name="userId" id="userId" lay-filter="selectFilter">
					<c:forEach items="${list }" var="user">
						<option value="${user.id }">${user.userRealname }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div id="ckdata">
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block" align="center">
				<button class="layui-btn" lay-submit lay-filter="formSubmitBtn">提交</button>
			</div>
		</div>
	</form>
</body>
</html>

