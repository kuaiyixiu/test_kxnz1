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
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>连途登录</title>
		<link rel="stylesheet" type="text/css" href="lib/admin/layui/css/layui.css" />
		<link rel="stylesheet" type="text/css" href="lib/admin/css/login.css" />
		<link rel="shortcut icon" href="image/favicon.ico" type="image/x-icon">
		<meta name="renderer" content="webkit">
		<script src="lib/admin/layui/layui.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>
	    <input type="hidden" name="error" id="error" value="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message }" />
	    <input type="hidden" name="contextPath" id="contextPath" value=<%= request.getContextPath() %> > 
		<div class="m-login-bg">
			<%--<img alt="" src="lib/admin/images/login/login3.png" style="position: fixed; top: 14%; width: 56%;  height: 40%;">
			<img alt="" src="lib/admin/images/login/login2.png" style="position: fixed;  top: 14%;  left: 46%; width: 10%; height: 15%;">
			--%><div class="m-login">
				 <h3>连途门店系统</h3>
				 <h6>连途，让门店管理变得无比轻松。</h6>
				<div class="m-login-warp">
					<form class="layui-form login-form">
<%--						<div class="layui-form-item">--%>
<%--							<input type="text" name="shopKey" id="shopKey" required lay-verify="required" placeholder="授权代码" autocomplete="off" class="layui-input">--%>
<%--						</div>--%>
						<div class="layui-form-item login-form-item" style="margin-bottom: 30px;position: relative;height: 50px;line-height: 50px;">
							<img alt="用户名" src="lib/admin/images/login/icon1.png" style="position: absolute;left: 16px;top: 11px;width: 27px;">	
							<input style="font-size: 16px;background-color: #eee;border-radius:5px;" type="text" name="username" id="username"  required lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input-user">
						</div>
						<div class="layui-form-item login-form-item"  style="margin-bottom: 30px;position: relative;height: 50px;line-height: 50px;">
							<img alt="用户名" src="lib/admin/images/login/icon2.png"  style="position: absolute;left: 16px;top: 11px;width: 27px;">	
							<input style="font-size: 16px;background-color: #eee;border-radius:5px;" type="password" name="password" id="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input-password">
						</div>
						<div class="layui-form-item" style="display: none;">
							<div class="layui-inline">
								<input type="text" name="verity" id="verity"  placeholder="验证码" autocomplete="off" class="layui-input">
							</div>
							<div class="layui-inline">
								<img class="verifyImg" id="verifyImg" name="verifyImg" src="${pageContext.request.contextPath}/getVerify.do" />
							</div>
						</div>
						<div class="layui-form-item m-login-btn">
							<div class="layui-input-block" style="margin-left:0px;">
								<button class="layui-btn layui-btn-normal" style="background-color: #657EF9;color: #fff;width:100%;height: 52px;line-height: 52px;font-size: 22px;border-radius:5px;" lay-submit lay-filter="login">登&nbsp;录</button>
							</div>
							<%--<div class="layui-inline">
								<button type="reset" class="layui-btn layui-btn-primary" style="">取消</button>
							</div>
						--%></div>
					</form>
				</div>
			</div>
		</div>
		<script>
			layui.use(['form', 'layedit','jquery', 'laydate'], function() {
				var form = layui.form,
					layer = layui.layer;
				var $ = layui.jquery;
<%--				$(document).on('click','#verifyImg',function(){--%>
<%--					$("#verifyImg").attr("src",$("#contextPath").val()+"/getVerify.do?"+Math.random());--%>
<%--			     });--%>
				
				//监听提交
				form.on('submit(login)', function(data) {	
					$.ajax({ 
					     type : "POST", 
					     url : "login.do",
					     data :data.field,
					     dataType: "json",
					     success : function(result) {
					      if (result.retCode =='success' ) { 
					    	  //layer.msg(result.retMsg, {icon: 1});
					    	  location.href = "index.do";
					      } else { 
					    	  layer.msg(result.retMsg, {icon: 2}); 
					    	  $("#verifyImg").attr("src",$("#contextPath").val()+"/getVerify.do?"+Math.random());
					      } 
					     } 
					    }); 
					return false;
				});

			});
		</script>
	</body>

</html>