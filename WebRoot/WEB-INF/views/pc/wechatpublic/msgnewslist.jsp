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
<%@ include file="../base.jsp"%>
<link rel="stylesheet" type="text/css" href="js/plugin/viewerjs/viewer.min.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<script src="js/plugin/viewerjs/viewer.min.js" type="text/javascript" charset="utf-8"></script>
<script src="lib/jquery/jquery-3.4.1.js" type="text/javascript" charset="utf-8"></script>
<script src="js/plugin/pagination/pagination.js" type="text/javascript" charset="utf-8"></script>
<script src="js/util.js"></script>
<script src="lib/mui/js/template-web.js"></script>
<script src="js/mylayui.js"></script>
</head>
<body>
	<div class="fsh-rightPanel">
    <div class="layui-anim layui-anim-upbit">
        <div class="layui-tab" style="min-width:1050px;" lay-filter="docType">
            <ul class="layui-tab-title">
                <li class="layui-this">多图文</li><%--
                <li>单图文</li>
            --%></ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item  layui-show">
                    <!--搜索框-->
                    <div class="layui-form-item wxmp-search" id="search_form2">
                        <div class="layui-inline">
                            <input type="text" name="title" placeholder="请输入关键字" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-inline">
                            <input type="text" class="layui-input laydate" id="start2" name="start">
                            <i class="iconfont icon-rili"></i>
                        </div>
                        <span class="font-primary">-</span>
                        <div class="layui-inline">
                            <input type="text" class="layui-input laydate" id="end2" name="end">
                            <i class="iconfont icon-rili"></i>
                        </div>
                        <div class="layui-inline">
                            <button class="layui-btn btn-primary" id="search2">搜索</button>
                        </div>
                        <div class="layui-inline right">
                            <button class="layui-btn btn-primary" onclick="addmultiple();">添加图文消息
                            </button>
                        </div>
                    </div>
                    <div id="list1">

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
function addmultiple(){
	var $ = layui.jquery;
	layui.use('element', function(){
		var element = parent.layui.element;
		var id = "addmultiple";
		title = "添加图文消息";
		var orderurl = 'wechatMessage/addmultiple.do';
		element.tabDelete('tab', id);
		element.tabAdd('tab', {
			title: title,
			content: '<iframe src="' + orderurl + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
			id: id
		});
		element.tabChange('tab', id);
	});	
	
}

function editmultiple(newsId){
	var $ = layui.jquery;
	layui.use('element', function(){
		var element = parent.layui.element;
		var id = "editmultiple";
		title = "修改图文消息";
		var orderurl = 'wechatMessage/editmultiple/'+newsId+'.do';
		element.tabDelete('tab', id);
		element.tabAdd('tab', {
			title: title,
			content: '<iframe src="' + orderurl + '" name="' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
			id: id
		});
		element.tabChange('tab', id);
	});	
	
}

layui.use(["laydate", "table", "layer"], function () {
    var laydate = layui.laydate;
    var layer = layui.layer;
    var element = layui.element;

    //var typet = getHashParam().type;

    getlist2();//单图文

    laydate.render({
        elem: '#start2',
        done: function (value, date, endDate) {
            var end = $("#end2").val();
            if (value && end && (new Date(end) - new Date(value)) <= 0) {
                $("#start2").val("");
                layer.msg("开始时间需小于结束时间");
            }
        }
    });
    laydate.render({
        elem: '#end2',
        done: function (value, date, endDate) {
            var start = $("#start2").val();
            if (value && start && (new Date(value) - new Date(start)) <= 0) {
                $("#end2").val("");
                layer.msg("结束时间需大于开始时间");
            }
        }
    });


    $("#search2").click(function () {
        var data = util.getFormData("search_form2");
        if (data.start && !data.end) {
            layer.msg("请输入结束时间");
            return false;

        } else if (!data.start && data.end) {
            layer.msg("请输入开始时间");
            return false;
        }
        getlist2(data);
    });

    //多图文
    function getlist2(params) {
    	var data = {
				page : 1
			};
    	
        if (params && params.page) {
        	data.page = param.page;
        } 
        data.limit = 15;
        data.multType = 2;
        $.ajax({
            url: 'wechatMessage/list.do',
            data: data,
        	dataType : "json",
            success: function (result) {
                if (result.pager.total > 0) {
                    //渲染列表
                    renderHtml({
                        targetId: 'list1',
                        template: "js/template/list-multiple",
                        htmlData: result,
                        callBack: function () {
                            if (result.pager.total > 12) {
                                //初始化分页
                                initPage(result.pager, getlist2);
                            }
                        }
                    });
                } else {
                    $('#list1').html("<p class='nothing'>没有查询到相关数据！</p>");
                }
            },
            error: function () {
                $('#list1').html("<p class='nothing'>没有查询到相关数据！</p>");
            }
        })
    }

    //多图文操作
    $("#list1").on("click", "button[event]", function () {
        var event = $(this).attr("event");
        var id = $(this).parent().attr("data-id");
        if (event == "edit") {
        	editmultiple(id);
        } else if (event == "mass") {// 群发
            showDialog({
                title: '群发'
                , template: 'js/template/fans-chose'
                , height: 600
                , yes: function (index) {
                    if (GLOBAL.choosed.length == 0) {
                        layer.msg("请选择1个粉丝");
                        return false;
                    }
                    if (GLOBAL.choosed.length > 1) {
                        layer.msg("只能选择1个粉丝");
                        return false;
                    }
                    $.ajax({
                        url: '/wxapi/sendNewsByOpenId',
                        data: {
                            id: id,
                            openid: GLOBAL.choosed[0]
                        },
                        success: function (result) {
                            if (result.success) {
                                layer.msg("群发成功");
                                layer.close(index);
                            } else {
                                layer.msg("群发失败");
                            }
                        }
                    })
                }
                , end: function () {
                    GLOBAL.choosed = [];
                }
            });
        } else if (event == "del") {// 删除
            showConfirm("确认删除？",function () {
            	  if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
						 layui.$(".layui-layer-btn0").addClass("layui-btn-disabled");
						 layui.$(".layui-layer-btn0").attr("disabled","disabled");
		                $.ajax({
		                    url: 'wechatMessage/deleteMaterial.do',
		                    data: {id: id},
		                    success: function (result) {
		                    	 if (result.retCode == 'success') {
		                            layer.msg("操作成功");
		                            getlist2();
		                        } else {
		                            layer.msg("操作异常");
		                        }
		                    }
		                })
            	  }
            	
            });
        }
    });


 

});


</script>

