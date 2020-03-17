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
    <title>错误</title>
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
        .error-code{
            font-size: 40px;
            font-weight: normal;
            line-height: 80px;
            border-bottom: 1px solid #6a6a6a;
            text-align: center;
        }
        .error-hint{
            font-size: 25px;
            line-height: 20px;
            text-align: center;
        }
    </style>
</head>
<body>
<div><h1 class="error-code">500</h1><div class="error-hint">系统繁忙, 请稍后重试!</div></div>
<p>
    An exception was thrown: <b> <%=exception.getClass()%>:<%=exception.getMessage()%></b>
</p>
</body>
</html>
