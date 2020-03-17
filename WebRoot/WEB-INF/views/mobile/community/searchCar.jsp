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
<html class="ui-page-mine">
<base href="<%=basePath%>">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>车型搜索</title>
    <link href="lib/mui/css/mui.min.css" rel="stylesheet" />
    <link href="lib/mui/css/mui-icons-extra.css" rel="stylesheet" />
    <link href="lib/mui/css/app.css" rel="stylesheet" />
    <script src="lib/mui/js/mui.min.js"></script>
    <script src="lib/jquery/jquery-3.4.1.js"></script>
    <script src="lib/jquery/jsrender.js"></script>

    <link href="lib/mui/css/mui.indexedlist.css" rel="stylesheet" />
    <script src="lib/mui/js/mui.indexedlist.js"></script>
    <style>
        html,body{
            height: 100%;
        }
        .mui-indexed-list-bar{
            padding-bottom: 5px;
            padding-top: 5px;
        }
    </style>
</head>
<body>
<div id='list' class="mui-indexed-list">
    <div class="mui-indexed-list-search mui-input-row mui-search">
        <input id="search" type="search" class="mui-input-clear mui-indexed-list-search-input" placeholder="搜索车型关键字">
    </div>
    <div class="mui-indexed-list-alert"></div>
    <div class="mui-indexed-list-inner">
        <div class="mui-indexed-list-empty-alert">没有数据</div>
        <ul class="mui-table-view"></ul>
    </div>
</div>
</body>
<script type="text/javascript" charset="utf-8">
    mui.init();
    window.onload = function(){
        //从服务器获取数据
        //业务数据获取完毕，并已插入当前页面DOM；
        //注意：若为ajax请求，则需将如下代码放在处理完ajax响应数据之后；
        mui.plusReady(function(){
            //关闭等待框
            plus.nativeUI.closeWaiting();
            //显示当前页面
            mui.currentWebview.show();
        });
        var list = document.getElementById('list');
        list.style.height = (document.body.offsetHeight -3)+"px";
        window.indexedList = new mui.IndexedList(list);
    }
</script>
</html>
