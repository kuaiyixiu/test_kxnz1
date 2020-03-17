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
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>连途</title>
<%@ include file="../base.jsp"%>
<link rel="stylesheet" type="text/css" href="js/plugin/viewerjs/viewer.min.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<script src="js/plugin/viewerjs/viewer.min.js" type="text/javascript" charset="utf-8"></script>
<script src="lib/jquery/jquery-3.4.1.js" type="text/javascript" charset="utf-8"></script>
<script src="js/plugin/pagination/pagination.js" type="text/javascript" charset="utf-8"></script>
<script src="lib/mui/js/template-web.js"></script>
<style type="text/css">
</style>
</head>
<body>
	<div class="fsh-rightPanel">
		<blockquote class="site-text layui-elem-quote">
			<h2>编辑多图文</h2>
		</blockquote>
		<div class="layui-form" action="">
			<div class="layui-row">
				<div class="wxmp-doclist">
					<div class="content">
						<div class="main active" data-t="1">
							<img src="lib/admin/images/default.png" alt="">
							<p>请输入标题</p>
						</div>
					</div><%--
					<div class="add" id="doclist_add">
						<i class="layui-icon layui-icon-add-1"></i>
					</div>
				--%></div>
				<div class="fsh-form-lg" id="add_form" style="overflow: hidden;" data-t="1">
					<div class="layui-form-item">
						<label class="layui-form-label"><i>*</i>标题</label>
						<div class="layui-input-block">
							<input type="text" name="tltle" id="add_tltle" lay-verify="required" placeholder="请输入标题" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><i>*</i>作者</label>
						<div class="layui-input-block">
							<input type="text" name="author" id="add_auth" lay-verify="required" placeholder="请输入作者" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><i>*</i>封面图</label>
						<div class="layui-input-block">
							<input type="hidden" name="picpath" id="add_picpath" value=""> <input type="hidden" name="thumbMediaId" id="add_thumbMediaId" value="">
							<div class="layui-upload-drag" id="uploader">
								<i class="layui-icon"></i>
								<p>点击上传，或将文件拖拽到此处</p>
							</div>
							<input type="checkbox" name="showpic" title="封面图片显示在正文中" lay-skin="primary" id="add_showpic" value="1">
						</div>
					</div>
					
					   <div class="layui-form-item">
                    <label class="layui-form-label"><i>*</i>摘要</label>
                    <div class="layui-input-block">
                        <input type="text" name="digest" id="add_digest" lay-verify="required" placeholder="请输入摘要"
                               class="layui-input">
                    </div>
                </div>
					

					<div class="layui-form-item">
						<label class="layui-form-label">原文链接</label>
						<div class="layui-input-block">
							<input type="text" name="fromurl" id="add_fromurl" placeholder="请输入原文链接" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">留言</label>
						<div class="layui-input-block">
							<input type="checkbox" name="needOpenComment" value="1" lay-skin="switch" lay-text="开启|关闭" checked lay-filter="comment" id="add_open_comment">
							<div class="layui-inline" id="add_comment_type" style="margin-bottom:0;">
								<input type="radio" name="onlyFansCanComment" value="0" title="所有人可留言" checked> <input type="radio" name="onlyFansCanComment" value="1" title="仅粉丝可留言">
							</div>
						</div>
					</div>

					<div class="layui-form-item">
						<label class="layui-form-label"><i>*</i>内容</label>
						<div class="layui-input-block">
							<textarea name="" id="content" cols="30" rows="10"></textarea>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"></label>
						<div class="layui-input-block">
							<button type="button" class="layui-btn" id="add_submit">立即提交</button>
							<!--                         <button type="button" class="layui-btn" id="add_canl">取消</button> -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script>
layui.use(['layedit', "upload", "form"], function () {
    var layedit = layui.layedit;
    var form = layui.form;
    layedit.set({
        uploadImage: {
            url: 'managerImg/uploadFile.do',
            type: 'post'
        },
        uploadVideo: {
            url: 'managerImg/uploadFile.do',
            type: 'post'
        },
        uploadAudio: {
            url: 'managerImg/uploadFile.do',
            type: 'post'
        },
    });
    var layeditIndex=layedit.build('content'); //建立编辑器
    var upload = layui.upload;
    var listData = [];
    var $ = layui.$;

    $.ajax({
        url: 'wechatMessage/toUpdateMoreNews.do',
        data: {id:'${id}'},
        dataType : "json",
        success: function (result) {
            if(result.retCode == 'success'){
                listData=result.retData.articles;
                showDataByt(listData[0].arId);
                $(".wxmp-doclist .main p").html(listData[0].title);
                $("#add_tltle").val(listData[0].title);
                $("#add_auth").val(listData[0].author);
                 $("#add_digest").val(listData[0].digest);
                $("#add_fromurl").val(listData[0].contentSourceUrl);
                $("#content").html(listData[0].content);
                $("#uploader").html("<img src='" + listData[0].picUrl + "' style='width: 150px;height: 150px;'/><p>点击重新上传，或将文件拖拽到此处</p>");
                $("#add_picpath").val(listData[0].picUrl);
                $("#add_thumbMediaId").val(listData[0].thumbMediaId);
                $(".wxmp-doclist .main img").attr("src",listData[0].picUrl);
                $("#add_showpic").prop("checked", listData[0].showCoverPic == 1 ? true : false);
                $("#add_open_comment").prop("checked", listData[0].needOpenComment  == 1 ? true : false);
                $("input:radio[name=onlyFansCanComment][value="+listData[0].onlyFansCanComment+"]").prop("checked",true);
                if($("#add_open_comment").prop("checked")){
                    $("#add_comment_type").show();
                }else{
                    $("#add_comment_type").hide();
                }
                layeditIndex=layedit.build('content');
                $.each(listData,function (_,v) {
                    if(_!=0){
                        $(".wxmp-doclist .content").append(template.render(TEMP(v.arId,v.title), {}));
                    }
                });
                $(".wxmp-doclist .content>.main").attr("data-t",listData[0].arId);
                form.render();
            }
        }
    });
    var TEMP =function (t,title) {
    	var img;
    	$.each(listData,function (_,v) {
            if(v.arId==t){
                if(v.picUrl){
                	img=v.picUrl
                }
            }
        });
        return '<div class="item" data-t="'+t+'">'
            + '<img src="'+(img?img:"lib/admin/images/default.png")+'" alt="">'
            + '<p>'+(title?title:"请输入标题")+'</p>'
//             + '<button class="layui-btn layui-btn-danger">删除</button>'
            + '</div>';
    };
    
    
    //开启关闭留言
    form.on('switch(comment)', function(data){
        if(data.elem.checked){
            $("#add_comment_type").show();
        }else{
            $("#add_comment_type").hide();
        }
    });
    
    
    //取消
    $("#add_canl").click(function () {
//     	location.href = "/views/index.html#/material/document/document";
    	jump("/material/document/document[type=more]");
    });
    //增加一条
    $("#doclist_add").click(function () {
        if(listData.length>8){
            layer.msg("多图文最多8条");
            return false;
        }
        syncData();
        var t = new Date().getTime();
        $(".wxmp-doclist .content").append(template.render(TEMP(t), {}));
        //设置默认数据
        listData.push(
   		{	
   			arId: t,
        	url:'',
        	mediaId:'',
        	newsIndex:listData.length,
        	newsId:0,
        	page:1,
        	pageSize:20,
        	total:0,
        	totalPage:1,
        	account:'',
        	digest:'',
        	needOpenComment:0,
        	onlyFansCanComment:0
        });
        showDataByt(t);
    });

    //点击回显
    $(".wxmp-doclist").on("click",".content>.item,.content>.main",function () {
        var t=$(this).attr("data-t");
        $(this).addClass("active").siblings(".active").removeClass("active");
        showDataByt(t);
    });

    //删除一条
    $(".wxmp-doclist").on("click", "button", function () {
        var $item=$(this).parent();
        var isShow=$item.hasClass("active");
        var t=$item.attr("data-t");
        $.each(listData,function (i,v) {
            if(v.arId==t){
                listData.splice(i, 1);
                $item.remove();
            }
        });
        
        if(isShow){
            $(".wxmp-doclist .main").trigger("click");
        }
    });

    //点击提交
    $("#add_submit").click(function () {
        syncData();
        var isValidated=true;
        var t = $("#add_form").attr("data-t");
		var data=null;
        $.each(listData,function (i,v) {
            if(v.arId == t){
            	data=v;
                return false;
            }
        });
            if(data.title == ""){
                layer.msg("请完善标题信息");
                isValidated == false;
                showDataByt(t);
                return false;
            }
            if(data.author == ""){
                layer.msg("请完善作者信息");
                isValidated == false;
                showDataByt(t);
                return false;
            }
            if(data.thumbMediaId == ""){
                layer.msg("请完善图文封面信息");
                isValidated == false;
                showDataByt(t);
                return false;
            }
            if(data.content == ""){
                layer.msg("请完善图文正文信息");
                isValidated == false;
                showDataByt(t);
                return false;
            }

         if(isValidated){
             $.ajax({
             	type:"POST",
                 url: 'wechatMessage/updateSubMoreNews.do',
                 data: {rows:JSON.stringify(data)},
                 success: function (result) {
                	 if (result.retCode == 'success') {
                         layer.msg("修改成功");
                         setTimeout(function () {
                         	location.href = "wechatMessage/msgNewsList.do";
                         }, 1000);
                     }else{
                    	 layer.msg(result.retMsg, {icon: 2});
                     }
                 },
             });
         }
    });

    //同步标题
    $("#add_tltle").keyup(function () {
        var v=$(this).val();
        var t=$("#add_form").attr("data-t");
        $(".wxmp-doclist div[data-t="+t+"] p").html(v?v:"请输入标题")
    });


    //当前数据同步到listData
    function syncData() {
        var title = $("#add_tltle").val();
        var author = $("#add_auth").val();

        var thumbMediaId = $("#add_thumbMediaId").val();
        var picpath = $("#add_picpath").val();
        var showpic = $("#add_showpic").prop("checked") ? 1 : 0;

         var digest = $("#add_digest").val();
        var fromurl = $("#add_fromurl").val();
        var needOpenComment = $("#add_open_comment").prop("checked") ? 1 : 0;
        var onlyFansCanComment = parseInt($("input[name=onlyFansCanComment]:checked").val());

        var content=layedit.getContent(layeditIndex);
        var t = $("#add_form").attr("data-t");

        $.each(listData,function (_,v) {
            if(v.arId==t){
                v.title=title;
                v.auth=author;
                v.digest=digest;
                v.thumbMediaId=thumbMediaId;
                v.showCoverPic=showpic;
                v.picUrl=picpath;
                v.contentSourceUrl=fromurl;
                v.needOpenComment=needOpenComment;
                v.onlyFansCanComment=onlyFansCanComment;
                v.content=content;
                return false;
            }
        });
    }
    //回显表单
    function showDataByt(t) {
        $.each(listData,function (_,v) {
            if(v.arId==t){
                $("#add_tltle").val(v.title ? v.title : "");
                $("#add_auth").val(v.author ? v.author : "");

                $("#add_thumbMediaId").val(v.thumbMediaId ? v.thumbMediaId : "");
                $("#add_picpath").val(v.picUrl ? v.picUrl : "");
                $("#add_showpic").prop("checked", v.showCoverPic == 1 ? true : false);
                $("#uploader").html(
                    v.picUrl
                        ? '<img src="' + v.picUrl + '" style="width: 150px;height: 150px;"/><p>点击重新上传，或将文件拖拽到此处</p>'
                        : '<i class="layui-icon"></i><p>点击上传，或将文件拖拽到此处</p>'
                );

                 $("#add_digest").val(v.digest ? v.digest : "");
                $("#add_fromurl").val(v.contentSourceUrl ? v.contentSourceUrl : "");

                $("#add_open_comment").prop("checked",v.needOpenComment == 1?true:false);
                v.needOpenComment?$("#add_comment_type").show():$("#add_comment_type").hide();
                $("input[name=onlyFansCanComment][value="+(v.onlyFansCanComment?1:0)+"]").prop("checked",true);
                $("#content").html(v.content ? v.content : "");

                form.render();
                layeditIndex = layedit.build('content');

                $(".wxmp-doclist div[data-t=" + t + "]").addClass("active").siblings(".active").removeClass("active");
                $("#add_form").attr("data-t", t);
                return false;
            }
        });
    }

    //上传
    upload.render({
        elem: '#uploader',
        url: 'mediaFile/addMateria.do',
        data:{type:'thumb'},
        size: 64,
        accept:"images",
        exts: 'JPG|jpg',
        before: function(){
            showLoading();
        },
        done: function (result) {
            layer.closeAll('loading');
            if (result.retCode == 'success') {
                $("#uploader").html("<img src='" + result.retMsg + "' style='width: 150px;height: 150px;'/><p>点击重新上传，或将文件拖拽到此处</p>");
                $("#add_picpath").val(result.retMsg);
                $("#add_thumbMediaId").val(result.retData.mediaId);
                var t = $("#add_form").attr("data-t");
                $(".wxmp-doclist div[data-t=" + t + "] img").attr("src", result.retMsg);
            } else {
                layer.msg(result.msg || "上传接口异常");
            }
        },
        error: function(){
            layer.closeAll('loading');
            layer.msg("上传接口异常");
        }
    });
});
</script>

