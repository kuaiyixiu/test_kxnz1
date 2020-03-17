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
<link rel="stylesheet" type="text/css" href="lib/admin/layui/css/layui.css" />
<script src="lib/admin/layui/layui.js" type="text/javascript" charset="utf-8"></script>
<script src="lib/admin/js/common.js" type="text/javascript"	charset="utf-8"></script>
<script type="text/javascript">
	layui.use([ 'jquery', 'form', 'layer' ], function() {
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		form.render();
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			var transfer = layui.transfer;
			var getData = transfer.getData('demo1'); 
			if(getData.length == 0 ){
				layer.msg("请至少选择一个审核人员", {icon: 2});
				return false;
			}
			var $ = layui.jquery;
			var type = $("#type").val();
			if(type == 1){
				if(getData.length > 1 ){
					layer.msg("串签只能有一个审核人员", {icon: 2});
					return false;
				}
			}
			
			var userIds = "";
			for (idx in getData) {
				userIds = userIds + getData[idx].value + "#";
			}
			
			var nodeId = $("#nodeId").val();
			layui.jquery.ajax({
				type : "POST",
				url : "workflowTemplate/saveNodeUser.do",
				dataType : "json",
				data : {"userIds" : userIds, "nodeId" : nodeId },
				success : function(data) {
					if (data.retCode == 'success'){
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭当前页  
						parent.layer.msg(data.retMsg);
					}else{
						layer.msg(data.retMsg);
					}
				}
			});
			return false;
		});

	});
</script>

</head>
<body>
	<input type="hidden" id="nodeId" value="${node.id}">
	<input type="hidden" id="type" value="${node.type}">
	 <div id="test1" style="text-align:center;margin-top: 10px;"></div>
	 <form class="layui-form" action="" id="form"  style="width: 95%;height: 98%;margin-top: 1%;">
	 <div class="layui-form-item" style="margin-top:10px;"> 
		<div class="layui-input-block" align="center" style="margin-left:0px;">
			<button class="layui-btn" lay-submit lay-filter="formSubmitBtn">提交</button>
			<button class="layui-btn closeBtn" id="closeBtn" type="button">关闭</button>
		</div>
		</div>
	 </form>
  <script>
  layui.use('transfer', function(){
    var transfer = layui.transfer;
   
    //渲染
    transfer.render({
      elem: '#test1'  //绑定元素
      ,data:${userlist}
      ,id: 'demo1' //定义索引
      ,title: ['待选择人员', '审核人员']
      ,value: ${checkId}
    });
  });
  </script>
</body>
<script type="text/javascript">


</script>
</html>

