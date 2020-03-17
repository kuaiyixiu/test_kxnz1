<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    response.setStatus(HttpServletResponse.SC_OK);
%>
<html>
<head>
    <title>当前页面未找到</title>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <style>
        html,body{
            padding: 0;
            margin: 0;
        }
        .error-style{
            width: 100%;
            height: 100%;
            background-image: url("./image/404.png");
            background-position: center;
            background-repeat: no-repeat;

        }
    </style>
</head>
<body>
<div class="error-style"></div>
</body>
</html>
