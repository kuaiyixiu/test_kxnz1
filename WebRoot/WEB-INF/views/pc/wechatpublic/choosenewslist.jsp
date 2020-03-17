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

<link rel="stylesheet" type="text/css" href="css/common.css" />
<script src="lib/jquery/jquery-3.4.1.js" type="text/javascript" charset="utf-8"></script>
<script src="js/plugin/pagination/pagination.js" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
.wxmp-list-image{width: auto;}
.wxmp-list-image li{width:180px;margin: 0px;height: 180px;padding: 10px;}
.wxmp-list-image li .img-box{width: 100%;height: 100%;display: block;}
.wxmp-list-image li .img-main{width: 100%;height: 100%;max-width:100%;max-height: 100%;}
.wxmp-list-image li .img-main{cursor: pointer;}
.pagination {margin: 20px 0px;}
.selectMaterial{background-color: red!important;}
.newstitle{    font-size: 16px;
    line-height: 40px;
    background: rgba(0,0,0,0.3);
    color: #fff;
    z-index: 10;
    position: relative;
    bottom: 60px;
    }
</style>
</head>
<body>
	<div class="fsh-rightPanel">
		<div class="layui-anim layui-anim-upbit">
			<div id="image_wrap">

			</div>
			<div class="layui-input-block" style="margin-left: 0px;text-align: center;">
			  <button  class="layui-btn btn-submit" lay-submit lay-filter="formSubmitBtn">确&nbsp;定</button>
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	function userDefinedLoadForm() {
		var upload = layui.upload;
		var laydate = layui.laydate;
		var $ = layui.$;

		//日期选择
		laydate.render({
			elem : '#image_time',
			range : true,
			done : function(value, date, endDate) {
				if (value) {
					$("#list_form input[name='start']").val(
							value.substring(0, 10));
					$("#list_form input[name='end']").val(value.substring(13));
				} else {
					$("#list_form input[name='start']").val("");
					$("#list_form input[name='end']").val("");
				}
			}
		});

		getImglist();
		function getImglist(param) {
			var data = {
				page : 1
			};
            if (param && param.page) {
                data.page = param.page;
            }
			if ($("#start").val() && $("#end").val()) {
				data.start = $("#start").val();
				data.end = $("#end").val();
			}
			data.limit = 12;
			data.multType = 2;
			$.ajax({
				type : "POST",
				url: 'wechatMessage/list.do',
				dataType : "json",
				data : data,
				async: false,
				success : function(result) {
					if (result.code == '0' && result.count > 0) {
						var dataList=result.data;
						var html='<ul class="wxmp-list-image">';
						for(var i=0;i<dataList.length;i++){
							var data=dataList[i];
							html=html+'<li  mediaId="'+data.mediaId+'" onclick="doSelect(this)" class="img-li"><div class="img-box"><img src="'+data.articles[0].picUrl+'" alt="" class="img-main">';
							html=html+'<h3 class="ellipsis newstitle">一号文章</h3></div><div class="btn-box" data-id="'+data.id+'">';
							html=html+'</div></li>';	
						}
						html=html+'</ul><div id="pagination" class="pagination"></div>';
						$("#image_wrap").html(html);
                        if (result.pager.total > 15) {
                            initPage(result.pager, getImglist);
                        }
                        parent.layer.iframeAuto(parent.layer.getFrameIndex(window.name));
					} else {
						showEmpty($("#image_wrap"));
					}
				},
				error : function() {
					showEmpty($("#image_wrap"));
				}
			})
		}

		// 搜索
		$("#search").click(function() {
			getImglist();
		});

		function showEmpty($ele, str) {
			$ele.html("<p class='nothing'>" + (str ? str : "没有查询到相关数据！")+ "</p>");
		}
		
		
		function initPage(page, callBack, container) {
		    !container && (container = $("#pagination"));
		    if (page.total != 0) {
		        // 创建分页
		        container.pagination(page.total, {
		            current_page: page.page - 1,//当前页，默认是从0开始
		            items_per_page: page.limit, //每页显示数目
		            num_edge_entries: 1, //边缘页数
		            num_display_entries: 4, //主体页数
		            prev_show_always: false,
		            next_show_always: false,
		            prev_text: "上一页",
		            next_text: "下一页",
		            callback: function (index) {
		                //获取列表数据并刷新
		                if ((page.page - 1) != index) {
		                    callBack({
		                        page: index + 1
		                    });
		                }
		            }
		        });
		    }
		    return page;
		}
		
		
		
		layui.form.on('submit(formSubmitBtn)', function(data) {
			  if(!layui.$(".btn-submit").hasClass("layui-btn-disabled")){
					layui.$(".btn-submit").addClass("layui-btn-disabled");
					layui.$(".btn-submit").attr("disabled","disabled");
					if($(".selectMaterial").length > 0){
						parent.mediaId = $(".selectMaterial").attr("mediaId");
						parent.layer.close(parent.layer.getFrameIndex(window.name));//关闭当前页  
					}else{
						 layer.msg("请选择素材", {icon: 2});
						 layui.$(".btn-submit").removeClass("layui-btn-disabled");
						 layui.$(".btn-submit").removeAttr("disabled");
					} 
					
			  }
			return false;
		});
	}
	
	function doSelect(obj){
		$(".img-li").removeClass("selectMaterial");
		$(obj).addClass("selectMaterial");
	}
	
</script>

