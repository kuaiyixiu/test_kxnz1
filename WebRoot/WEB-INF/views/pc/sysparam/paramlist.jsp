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
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>连途</title>
<link rel="stylesheet" type="text/css" href="lib/admin/layui/css/layui.css" />
<script src="lib/admin/layui/layui.js" type="text/javascript" charset="utf-8"></script>
<script src="lib/admin/js/common.js" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
.layui-input,.layui-textarea {
	width: 80%;
	display: inline-block;
}

.layui-form-label {
	width: 200px;
	text-align: left;
}

.layui-input-block {
	margin-left: 0px;
}

.layui-elem-field {
	margin: 5px;
}
</style>
<script type="text/javascript"><%--
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		//监听提交
		$(document).on('click','.submitParam',function(e){
			var $ = layui.jquery;
			var paramName = $(e.currentTarget).attr("paramName");
			var id = $(e.currentTarget).attr("id");
			var data = {'paramName':paramName,'id':id};
			if(paramName == 'repository_oa'){//是否启用审批流程
				var paramValue = $('input:radio[name="'+paramName+'"]:checked').val();
				data['paramValue'] = paramValue;
			}
			
			$.ajax({ 
				type : "POST",
				url : "sysParam/saveParm.do",
				data : data,
				dataType : "json",
				success : function(result) {
					if (result.retCode == 'success') {
						parent.layer.msg(result.retMsg, {icon : 1});
					} else {
						layer.msg(result.retMsg, {
							icon : 2
						});
					}
				}
			});
			return false;
		});
	});
--%></script>
</head>
<body>
	<form class="layui-form" action="" id="form" style="width: 95%;height: 98%;margin-top: 1%;">
		<fieldset class="layui-elem-field">
			<legend>库存参数</legend>
			<div class="layui-field-box">
				<div class="layui-form-item">
					<div class="layui-input-block">
						<label class="layui-form-label">是否启用审批流程：</label>
						<div class="layui-input-block">
							<input type="radio" name="repository_oa" value="1" title="是"> <input type="radio" name="repository_oa" value="0" title="否" checked="checked">
						</div>
					</div>
				</div>
			</div>
		</fieldset>
		<fieldset class="layui-elem-field">
			<legend>推送参数</legend>
			<div class="layui-field-box">
				<div class="layui-form-item">
					<div class="layui-input-block">
						<label class="layui-form-label">是否启用微信推送：</label>
						<div class="layui-input-block">
							<input type="radio" name="enable_sms_push" value="1" title="是"> <input type="radio" name="enable_sms_push" value="0" title="否" checked="checked">
						</div>
					</div>
				</div>
			</div>
			<div class="layui-field-box">
				<div class="layui-form-item">
					<div class="layui-input-block">
						<label class="layui-form-label">是否启用短信推送：</label>
						<div class="layui-input-block">
							<input type="radio" name="enable_wechat_push" value="1" title="是"> <input type="radio" name="enable_wechat_push" value="0" title="否" checked="checked">
						</div>
					</div>
				</div>
			</div>
		</fieldset>
		<button class="layui-btn" style="float: left;width: 100px;margin: 5px;" lay-submit lay-filter="formSubmitBtn">保 存</button>
	</form>
</body>
<script type="text/javascript">
layui.use([ 'jquery', 'form'], function() {
	var $ = layui.jquery;
	var form = layui.form;
	//监听提交
	$.ajax({
		type : "POST",
		url : "sysParam/getParamlist.do",
		dataType : "json",
		success : function(data) {
			if(!!data){
				for (var idx in data){
					var param = data[idx];
					if(param["paramName"] == 'repository_oa' || param["paramName"] == 'enable_sms_push' || param["paramName"] == 'enable_wechat_push'){
						$("input[name='"+param["paramName"]+"'][value="+param["paramValue"]+"]").prop("checked", true); 
						$("button[paramName='"+param["paramName"]+"']").attr("id",param.id);
					}
					
					// $('input[name="'+param["paramName"]+'"]').val(param.paramValue)
				//	if(param['paramName'])
				}	
				
				   
			}
			form.render(); //更新全部   
			form.on('submit(formSubmitBtn)', function(data) {
				var paramList = new Array();
				for(var key in data.field){
					var param = {};
					param['paramName'] = key;
					param['paramValue'] =  data.field[key];
					paramList.push(param);
				}
				
					$.ajax({
						type : "POST",
						url : "sysParam/saveParm.do",
						data :  JSON.stringify(paramList),
						dataType : "json",
						contentType:"application/json",
						success : function(result) {
							if (result.retCode == 'success') {
								var index = parent.layer
										.getFrameIndex(window.name);
								parent.layer.close(index);//关闭当前页  
								parent.layer.msg(result.retMsg, {
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

			}
		});
	});
</script>
</html>

