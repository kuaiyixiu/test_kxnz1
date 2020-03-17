<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html class="ui-page-login">
<base href="<%=basePath%>">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>注册</title>
		<link href="lib/mui/css/mui.min.css" rel="stylesheet" />
		<script src="lib/mui/js/mui.min.js"></script>
		<script src="lib/mui/js/mui.enterfocus.js"></script>
		<script src="lib/mui/js/app.js"></script>
		<script src="lib/jquery/jquery-3.4.1.js"></script>
		<script src="lib/jquery/jquery.autocompleter.js"></script>
		<script src="lib/jquery/jquery-ui.js"></script>

	</head>
	<style>
		.mui-input-group {
			margin-top: 10px;
		}

		.mui-input-group:first-child {
			margin-top: 20px;
		}

		.mui-input-group label {
			width: 30%;
		}

		.mui-input-row label~input,
		.mui-input-row label~select,
		.mui-input-row label~textarea {
			width: 70%;
		}

		.mui-checkbox input[type=checkbox],
		.mui-radio input[type=radio] {
			top: 6px;
		}

		.mui-content-padded {
			margin-top: 25px;
		}

		.mui-btn {
			padding: 10px;
		}

        .mui-input-row .mui-radio label{
            padding-top: 3px;
            padding-bottom: 3px;
            padding-left: 0px;
        }
    </style>

	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">用户注册</h1>
		</header>
		<div class="mui-content">
			<form id='register-form' class="mui-input-group">
				<input type="hidden" id="userPhoto" name="userPhoto" value="${userPhoto}"/>
				<input type="hidden" id="userAddress" name="userAddress" value="${userAddress}"/>
				<div class="mui-input-row">
					<label>登录名</label>
					<input id='account' type="text" class="mui-input-clear mui-input" placeholder="请输入手机号"
						   oninput="value=value.replace(/[^\d]/g,'')" maxlength="15" >
				</div>
				<div class="mui-input-row">
					<label>密码</label>
					<input id='userPassword' type="password" class="mui-input-clear mui-input" placeholder="请输入密码"
						maxlength="16">
				</div>
				<div class="mui-input-row">
					<label>用户名</label>
					<input id='userRealname' type="text" class="mui-input-clear mui-input" placeholder="请输入用户名"
						maxlength="20" value="">
				</div>
				<div class="mui-input-row">
					<label>性别</label>
					<div class="mui-radio mui-inline">
						<label>男</label>
						<input name="userSex" type="radio" ${userSex == 1 ? "checked" : ""} value="1">
					</div>
					<div class="mui-radio mui-inline">
						<label>女</label>
						<input name="userSex" type="radio" ${userSex != 1 ? "checked" : ""} value="0">
					</div>
				</div>
                <div class="mui-input-row">
                    <label>邮箱</label>
                    <input id='email' type="email" class="mui-input-clear mui-input" placeholder="请输入邮箱"
						maxlength="30">
                </div>
               <%-- <div class="mui-input-row">
                    <label>邀请码</label>
                    <input id='invitationCode' type="text" class="mui-input-clear mui-input" placeholder=""
						maxlength="20">
                </div>--%>


			</form>
			<div class="mui-content-padded" style="margin-top: 50px">
				<button id='register' type="button" class="mui-btn mui-btn-block mui-btn-primary">注册</button>
			</div>
		</div>
		<script>
			mui.init();
            mui(document.body).on('tap', '.mui-btn', function(e) {
                var check = true;
                var formData={userPhoto:$("#userPhoto").val(), userAddress: $("#userAddress").val()};

				var userPhone = document.getElementById("account").value;
				if(!(/^1[3456789]\d{9}$/.test(userPhone))){
					mui.alert("手机号码有误，请重填");
					check = false;
					return false;
				}
				formData.userPhone = userPhone;

				var userPassword = document.getElementById("userPassword").value;
				if(!userPassword || userPassword.trim() == ""){
					mui.alert("密码不能为空");
					check = false;
					return false;
				}else if(!(/^[\w_]{1,16}$/.test(userPassword))){
					mui.alert("密码只能由字母、数字、下划线组成, 且长度不能超过16位");
					check = false;
					return false;
				}
				formData.userPassword = userPassword.trim();

				var userRealname = document.getElementById("userRealname").value;
				if(!userRealname || userRealname.trim() == ""){
					mui.alert("用户名不能为空");
					check = false;
					return false;
				}
				formData.userRealname = userRealname.trim();

				var email = document.getElementById("email").value;
				if(!(/^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/.test(email))){
					mui.alert("邮箱格式有误，请重填");
					check = false;
					return false;
				}
				formData.email = email;

				formData.userSex = $("#register-form input[name=userSex]:checked").val();
				formData.invitationCode = $("#invitationCode").val();

				console.log(formData)
				if(check) {
					mui.post("wechat/community/register.do", {userJson: JSON.stringify(formData)}, function (res) {
						if(res != null){
							if(res.retCode == "success"){
								mui.toast(res.retMsg,{ duration:'long', type:'div' });
                                mui.openWindow({
                                    url: 'wechat/community.do'
                                });
							}else{
								mui.alert("响应失败, 请稍后重试!");
							}
						}else{
							mui.alert("响应失败, 请稍后重试!");
						}
					});
                }
            })

		</script>
	</body>
</html>