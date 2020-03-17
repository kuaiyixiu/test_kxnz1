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
<style type="text/css">
.layui-form-mid{float: none;padding: 0px!important;}
</style>
</head>
<body>
	<form class="layui-form" action="">
		<div class="layui-form-item">
			<label class="layui-form-label">发送对象</label>
			<div class="layui-input-block">
				<input type="radio" name="sendUserType" value="1" title="客户" checked="checked" lay-filter="sendUser"> 
				<div <c:if test="${flag ne 'isFalse'}">style="display: none;"</c:if>>
					<input lay-filter="sendUser" type="radio" name="sendUserType" value="2" title="微信粉丝">
				</div>
			</div>
		</div>
		<div class="layui-form-item" id="toPerson">
			<div class="layui-input-block">
				<button class="layui-btn" type="button" id="chooseCustom">选择客户</button>
				<label class="layui-form-label" style="float: none;display: inline;">已选择<span id="openIdCount" style="color:red;">0</span>个客户</label>
			</div>
		</div>
		<div class="layui-form-item" id="toTag" style="display: none;">
			<div class="layui-input-block">
			<label class="layui-form-label" style="float: none;display: inline;">选择标签    已选择<span id="tagCount" style="color:red;">${fansCount }</span>个粉丝</label>
			</div>
			<div class="layui-input-block">
				<input type="radio" name="tagId" value="all" count="${fansCount }" title="全部微信粉丝(每月最多发送4次)" checked="checked" lay-filter="selTag">
			</div>
			<c:forEach items="${tagList }" var="tag" varStatus="status">
				<div class="layui-input-block">
					<input type="radio" name="tagId" count="${tag.count }" value="${tag.id }" title="${tag.name }" lay-filter="selTag">
				</div>
			</c:forEach>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">发送内容</label>
			<div class="layui-input-block">
				<input type="radio" name="contentType" value="1" title="文本" checked="checked" lay-filter="selContentType"> 
				<input type="radio" name="contentType" value="2" title="图片" lay-filter="selContentType"> 
				<input type="radio" name="contentType" value="3" title="图文" lay-filter="selContentType">
			</div>
		</div>
		<div class="layui-form-item layui-form-text" id="toContent">
			<label class="layui-form-label">文本内容</label>
			<div class="layui-input-block">
				<textarea name="sendContent" placeholder="请输入文本内容" class="layui-textarea" style="width: 500px;"></textarea>
			</div>
		</div>
		<div class="layui-form-item layui-form-text"  id="toMaterial" style="display: none;">
			<label class="layui-form-label"></label>
			<div class="layui-input-block">
				<button class="layui-btn" type="button" id="chooseMaterial">选择</button>
			</div>
		</div>
		<div class="layui-form-item">
		<div class="layui-input-block">
		  <div class="layui-form-mid layui-word-aux">1、每个月每个微信粉丝最多只能收到4条消息</div>
		  <div class="layui-form-mid layui-word-aux">2、每天最多只能群发100次</div>
		</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block" align="center" style="float: left;">
				<a class="layui-btn btn-submit" lay-submit lay-filter="formSubmitBtn">确定发送</a>
			</div>
		</div>
	</form>
</body>
</html>
<script type="text/javascript">
var selTagId = "all"; //选择的标签 默认全部
var selContentType = "1"; //选择的发送内容类型 默认文本
var mediaId = "";
var selOpenId = "";
var chooseOpenIds = "";
layui.use([ 'jquery', 'form', 'layer'], function(){
	  var form = layui.form;
	  //监听提交
	  form.on('radio(sendUser)', function(data){
		  if(data.value == 1){
			  layui.jquery("#toPerson").show();
			  layui.jquery("#toTag").hide();
		  }else{
			  layui.jquery("#toPerson").hide();
			  layui.jquery("#toTag").show();
		  }
	  });
	  form.on('radio(selTag)', function(data){
		  selTagId = data.value;
		  layui.jquery("#tagCount").text($(data.elem).attr('count'))
		  
	  });
	  form.on('radio(selContentType)', function(data){
		  selContentType = data.value;
		  if(selContentType == 1){
			  layui.jquery("#toContent").show();
			  layui.jquery("#toMaterial").hide();
		  }else{
			  layui.jquery("#toMaterial").show();
			  layui.jquery("#toContent").hide();
		  }
	  });
	  
		form.on('submit(formSubmitBtn)', function(data) {
			  if(!layui.$(".btn-submit").hasClass("layui-btn-disabled")){
					layui.$(".btn-submit").addClass("layui-btn-disabled");
					layui.$(".btn-submit").attr("disabled","disabled");
					var paramData = data.field;
					paramData.selTagId = selTagId;
					paramData.selContentType = selContentType;
					paramData.mediaId = mediaId;
					paramData.chooseOpenIds = chooseOpenIds;
					if(paramData.sendUserType == 2){ //微信粉丝
						paramData.userCount =  layui.jquery("#tagCount").text();
					}
					$.ajax({
						type : "POST",
						url : "wechatMessage/sendMsg.do",
						data : paramData,
						dataType : "json",
						success : function(result) {
							if (result.retCode == 'success') {
								parent.layer.msg(result.retMsg, {
									icon : 1
								});
							} else {
								layer.msg(result.retMsg, {
									icon : 2
								});
							}
							layui.$(".btn-submit").removeAttr("disabled");
							layui.$(".btn-submit").removeClass("layui-btn-disabled");
						}
					});
			  }
			return false;
		});
		
		layui.jquery("#chooseMaterial").click(function() {
			if (selContentType == 2 || selContentType == 3){
				var url = "wechatMessage/choosemateriallist.do";
				var title ="选择图片"
				if (selContentType == 3){
				 url = "wechatMessage/choosenewslist.do";
				 title ="选择图文"
				}
				var option = {
					type : 2,
					title : title,
					area : [ '850px', '800px' ],
					fixed : false, //不固定
					content : [ url, 'no' ],
					success : function(layero, index) {
					}
				};
				layer.open(option);
			}
		});
		
		layui.jquery("#chooseCustom").click(function() {
				var url = "wechatMessage/choosecustomlist.do";
				var title ="选择客户"
				var option = {
					type : 2,
					title : title,
					area : [ '1100px', '800px' ],
					fixed : false, //不固定
					content : [ url, 'no' ],
					success : function(layero, index) {
					}
				};
				layer.open(option);

		});
		
})
	
</script>

