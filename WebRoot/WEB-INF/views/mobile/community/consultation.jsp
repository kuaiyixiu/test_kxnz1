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
    <title>连途社区首页</title>
    <link href="lib/mui/css/mui.min.css" rel="stylesheet" />
    <link href="lib/mui/css/mui-icons-extra.css" rel="stylesheet" />
    <link href="lib/mui/css/app.css" rel="stylesheet" />
    <script src="lib/mui/js/mui.min.js"></script>
    <script src="lib/jquery/jquery-3.4.1.js"></script>
    <script src="lib/jquery/jsrender.js"></script>

    <style>

    </style>
</head>
<body>
    <div id="slider" class="mui-slider" style="height: 160px">
        <div class="mui-slider-group mui-slider-loop">
            <%--<div class="mui-slider-item mui-slider-item-duplicate">
                <a href="#"><img src="http://www.cheyaya.com:80/cyy/uploadFiles/uploadImgs/20200312/b6e697e4bdb2477dbf795bd285825c46.png"/></a>
            </div>
            <div class="mui-slider-item">
                <a href="#"><img src="http://www.cheyaya.com:80/cyy/uploadFiles/uploadImgs/20200312/6099fe50d04e4b98a0e1e933edc3f9c1.png"/></a>
            </div>
            <div class="mui-slider-item">
                <a href="#"><img src="http://www.cheyaya.com:80/cyy/uploadFiles/uploadImgs/20200312/a219cb540acb42a595bd28755ab04e58.jpg"/></a>
            </div>
            <div class="mui-slider-item">
                <a href="#"><img src="http://www.cheyaya.com:80/cyy/uploadFiles/uploadImgs/20200312/86ca78ecd8a04821afcedaff0bd77d26.jpg"/></a>
            </div>
            <div class="mui-slider-item">
                <a href="#"><img src="http://www.cheyaya.com:80/cyy/uploadFiles/uploadImgs/20200312/b6e697e4bdb2477dbf795bd285825c46.png"/></a>
            </div>
            <!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
            <div class="mui-slider-item mui-slider-item-duplicate">
                <a href="#"><img src="http://www.cheyaya.com:80/cyy/uploadFiles/uploadImgs/20200312/6099fe50d04e4b98a0e1e933edc3f9c1.png"></a>
            </div>--%>
        </div>
        <div class="mui-slider-indicator">
            <%--<div class="mui-indicator mui-active"></div>
            <div class="mui-indicator"></div>
            <div class="mui-indicator"></div>
            <div class="mui-indicator"></div>--%>
        </div>
    </div>
    <div></div>
</body>

<script type="text/x-jsrender" id="boxTp1">
{{for list}}
    {{if #getIndex() == 0}}
        <div class="mui-slider-item mui-slider-item-duplicate">
            <a href="{{:~root.first.href}}" title={{:~root.first.sign}}><img src="{{:~root.first.value}}"/></a>
        </div>
    {{/if}}

	<div class="mui-slider-item">
        <a href="{{:href}}" title="{{:sign}}"><img src="{{:value}}"/></a>
    </div>

    {{if #getIndex() == ~root.list.length - 1}}
        <div class="mui-slider-item mui-slider-item-duplicate">
            <a href="{{:~root.last.href}}" title={{:~root.list.sign}}><img src="{{:~root.last.value}}"/></a>
        </div>
    {{/if}}
{{/for}}
</script>
<script type="text/x-jsrender" id="boxTp2">
{{for}}
    <div class="mui-indicator
        {{if #getIndex() == 0}}
            mui-active
        {{/if}}
    "></div>
{{/for}}
</script>
<script>
    mui.init();

    mui.ready(function () {
        readBanner();
    });

    function readBanner(){
        // 加载轮播图
        mui.ajax('wechat/community/banners.do', {
            async : false,
            type : "post",
            dataType : "json",
            error : function(){$("#slider").hide();},
            success : function(res){
                if(res == null || res.length == 0){
                    $("#slider").hide();
                }else{
                    $("#slider .mui-slider-group").append($("#boxTp1").render({first: res[res.length -1], list: res, last: res[0]}));
                    $("#slider .mui-slider-indicator").append($("#boxTp2").render(res));
                    mui("#slider").slider({interval: 5000});
                }
            }
        })
    }
</script>
</html>