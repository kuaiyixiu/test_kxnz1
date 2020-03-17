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
<html class="ui-page-login">
<base href="<%=basePath%>">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title></title>
    <link href="lib/mui/css/mui.min.css" rel="stylesheet" />
    <link href="lib/mui/css/style.css" rel="stylesheet" />
    <script src="lib/mui/js/mui.min.js"></script>
    <script src="lib/jquery/jquery-3.4.1.js"></script>
    <script src="lib/jquery/jsrender.js"></script>

    <style>
        .user-info{
            padding: 15px 0;
        }
        .user-info-photo img {
            height: 50px;
            width: 50px;
            border: 1px solid #c3c3c3;
            border-radius: 50%;
            margin-left: calc(50% - 25px);
        }
    </style>
</head>
<body>
    <header class="mui-bar mui-bar-nav">
        <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">用户信息</h1>
    </header>
    <div class="mui-content">
        <div class="mui-card">
            <div class="mui-card-content">
                <div class="mui-card-content-inner">
                    <div class="user-info mui-row">
                        <div class="user-info-photo mui-col-xs-3">
                            <img src="image/no_picture.jpg">
                        </div>
                        <div class="user-info-data mui-col-xs-9 mui-navigate-right">
                            <h4 class="mui-h5">用户名</h4>
                            <span class="mui-h5">职位</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</body>
</html>
