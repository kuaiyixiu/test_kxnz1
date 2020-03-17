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
</head>
<body>
	<div class="fsh-rightPanel">
		<div class="layui-anim layui-anim-upbit">
			<div class="layui-form-item wxmp-search" id="list_form" style="">
				<input type="hidden" name="start" id="start"> <input
					type="hidden" name="end" id="end">
				<div class="layui-inline" style="width: 250px">
					<input type="text" class="layui-input" id="image_time"
						placeholder="选择上传日期"> <i class="iconfont icon-rili"></i>
				</div>
				<div class="layui-inline">
					<button class="layui-btn btn-primary" id="search">搜索</button>
				</div>
				<div class="layui-inline right">
					<button class="layui-btn btn-primary" id="video_uploader">新增</button>
				</div>
			</div>
			<div id="image_wrap">

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

		var viewer = null;
		getImglist();

		var uploader = upload.render({
			elem : '#video_uploader',
			url : 'mediaFile/addMateria.do',
			size : 2048,
			accept : "images",
			exts : 'bmp|BMP|png|PNG|jpeg|JPEG|jpg|JPG|gif|GIF',
			multiple:true,
			data : {
				type : "image"
			},
			before : function() {
				showLoading();
			},
			done : function(res) {
				layer.closeAll('loading');
				if (res.retCode == 'success') {
					layer.msg("上传成功");
					getImglist();
				} else {
					layer.msg("上传失败");
				}
			},
			error : function() {
				layer.closeAll('loading');
				layer.msg("上传失败");
			}
		});

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
			data.limit = 15;
			$.ajax({
				type : "POST",
				url : 'managerImg/list.do',
				dataType : "json",
				data : data,
				async: false,
				success : function(result) {
					if (result.code == '0' && result.count > 0) {
						var dataList=result.data;
						var html='<ul class="wxmp-list-image">';
						for(var i=0;i<dataList.length;i++){
							var data=dataList[i];
							html=html+'<li><div class="img-box"><img src="'+data.url+'" alt="" class="img-main">';
							html=html+'</div><div class="btn-box" data-id="'+data.id+'">';
							html=html+'<button class="layui-btn" event="del"><i class="layui-icon layui-icon-delete"></i></button>';
							html=html+'<a class="layui-btn" href="'+data.url+'" event="del" download="'+data.url+'">';
							html=html+'<i class="layui-icon layui-icon-download-circle"></i></a></div></li>';	
						}
						html=html+'</ul><div id="pagination" class="pagination"></div>';
						$("#image_wrap").html(html);
                        if (result.pager.total > 15) {
                            initPage(result.pager, getImglist);
                            viewer=viewer?viewer.update():new Viewer($("#image_wrap")[0]);
                        }
					} else {
						showEmpty($("#image_wrap"));
					}
				},
				error : function() {
					showEmpty($("#image_wrap"));
				}
			})
		}

		// 删除
		$("#image_wrap").on("click", "button[event='del']", function() {
			var id = $(this).parent().attr("data-id");
			var a_num =0;
			layer.confirm("确认删除？", {icon: 3, title: '提示'}, function() {
				 if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
					 layui.$(".layui-layer-btn0").addClass("layui-btn-disabled");
					 layui.$(".layui-layer-btn0").attr("disabled","disabled");
					a_num+=1;
					if(a_num==1){
						$.ajax({
							type : "POST",
							url : 'managerImg/delMediaImg.do',
							data : {
								id : id
							},
							dataType : "json",
							async: false,
							success : function(result) {
								if (result.retCode == 'success') {
									layer.msg("操作成功");
									getImglist();
								} else {
									layer.msg("操作异常");
								}
							}
						})	
					}
				 }
				
				
			});
		});

		// 搜索
		$("#search").click(function() {
			getImglist();
		});

		function showEmpty($ele, str) {
			$ele.html("<p class='nothing'>" + (str ? str : "没有查询到相关数据！")
					+ "</p>");
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
	}
</script>

