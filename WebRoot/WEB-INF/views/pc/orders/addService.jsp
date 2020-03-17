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
<link rel="stylesheet" type="text/css"href="lib/admin/layui/css/layui.css" />
<script src="lib/admin/layui/layui.js" type="text/javascript"charset="utf-8"></script>
<script src="lib/admin/js/common.js" type="text/javascript"charset="utf-8"></script>
<style type="text/css">
.x-table {
  min-height: 1px;
  font-size: 14px;
  margin: 0px 20px 20px 20px;
  background-color: #f5f7f9; }


.x-table > .x-tr {
  border-bottom: 1px solid #EAEDEE; }

.x-table > .x-tr:first-child {
  border-top: 1px solid #EAEDEE; }

.x-tr-left {
  padding-top: 12px;
  padding-bottom: 12px; }

.x-tr-right {
  padding-top: 8px; 
  text-align: right;
  }

.x-tr {
	padding-left:15px;
	padding-right:15px;
   background-color: #f5f7f9; }

.order-list {
    margin-left: -20px;
    margin-right: -20px;
}
.section{margin: 10px 10px 3px 10px;}
.section-title{font-size: 16px;}
.text-right{text-align: right;}
</style>
<script type="text/javascript">
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		var laydate = layui.laydate;
		laydate.render({
			elem : '#birthday'
		});
		form.render();
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			$.ajax({
				type : "POST",
				url : "menu/saveMenu.do",
				data : data.field,
				dataType : "json",
				success : function(result) {
					if (result.retCode == 'success') {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭当前页  
						parent.layui.table.reload('menuInfo');

						//if($("#id").val()!=""){//修改时

						//}else
						//  parent.refresh();
						//刷新网格数据
						//parent.table.reload('roleInfo');
						parent.layer.msg(result.retMsg, {
							icon : 1
						});
					} else {
						layer.msg(result.retMsg, {
							icon : 2
						});
					}
				}
			});
			return false;
		});

	});
</script>
</head>
<body>
	<div class="order-car-card"  style="width:70%;margin-left:20px; 
	background-image: url(&quot;//cdn.qixinginc.com/static/img/autoplus/order/no_vip_bg.png&quot;);background-repeat:no-repeat; background-size:100% 100%;;border-radius: 4px 4px;padding: 9px 17px;" >
		<div class="order-car-card-1 layui-row">
			<div class="layui-col-sm8">
				<span class="vip-category-name" style="background-image: none;color: white;">非会员 </span>
			</div>
			<div class="layui-col-sm4" style="text-align: right;">
				<!---->
				<span class="wechat-noactive">
					<i aria-hidden="true"  class="fa fa-weixin fa-lg"></i>
				</span>
			</div>
		</div>
		<div class="order-car-card-2" style="cursor: pointer;text-align: center;color: white; padding: 20px 0;font-size: 22px;">
			临牌 <span style="font-size: 14px;"> </span>
		</div>
		<div class="order-car-card-3 layui-row">
			<div class="layui-col-sm6">
			</div>
			<div class="layui-col-sm6"  style="text-align: right;">
				<div>
					<br>
				</div>
			</div>
		</div>
	</div>
	<div class="" style="margin: 20px;width: 70%;">
		<!-- 产品 -->
		<div class="layui-row section" style="">
				<div class="layui-col-sm6 section-title">服务项目 </div>
				<div class="layui-col-sm6 " style="text-align: right;"><button class="layui-btn layui-btn-sm layui-btn-radius layui-btn-normal">添 加</button> </div>
		</div>
		<div class="x-table">
			<div class="layui-row x-tr order-list">
				<div class="layui-col-sm10 x-tr-left">
					<div class="layui-col-sm1">1</div>
					<div class="layui-col-sm5">免费卡</div>
					<div class="layui-col-sm4">待完工</div>
					<div class="layui-col-sm2">0.00元</div>
				</div>
				<div class="layui-col-sm2  x-tr-right">
					<a class="layui-btn  layui-btn-xs  layui-btn-normal">查看</a>
            		<a class="layui-btn layui-btn-xs  layui-btn-normal">修改</a>
				</div>
			</div>
			<div class="layui-row x-tr order-list">
				<div class="layui-col-sm10 x-tr-left">
					<div class="layui-col-sm1">1</div>
					<div class="layui-col-sm5">免费卡</div>
					<div class="layui-col-sm4">待完工</div>
					<div class="layui-col-sm2">0.00元</div>
				</div>
				<div class="layui-col-sm2  x-tr-right">
					<a class="layui-btn  layui-btn-xs  layui-btn-normal">查看</a>
            		<a class="layui-btn layui-btn-xs  layui-btn-normal">修改</a>
				</div>
			</div>
		</div>
		
		<!-- 服务项目 -->
		<div class="layui-row section" style="">
				<div class="layui-col-sm6 section-title">销售产品 </div>
				<div class="layui-col-sm6 " style="text-align: right;"><button class="layui-btn layui-btn-sm layui-btn-radius layui-btn-normal">添 加</button> </div>
		</div>
		<div class="x-table">
			<div class="layui-row x-tr order-list">
				<div class="layui-col-sm10 x-tr-left">
					<div class="layui-col-sm1">1</div>
					<div class="layui-col-sm5">免费卡</div>
					<div class="layui-col-sm4">x 1</div>
					<div class="layui-col-sm2">0.00元</div>
				</div>
				<div class="layui-col-sm2  x-tr-right">
					<a class="layui-btn  layui-btn-xs  layui-btn-normal">查看</a>
            		<a class="layui-btn layui-btn-xs  layui-btn-normal">修改</a>
				</div>
			</div>
			<div class="layui-row x-tr order-list">
				<div class="layui-col-sm10 x-tr-left">
					<div class="layui-col-sm1">2</div>
					<div class="layui-col-sm5">洗车卡</div>
					<div class="layui-col-sm4">x 1</div>
					<div class="layui-col-sm2">0.00元</div>
				</div>
				<div class="layui-col-sm2  x-tr-right">
					<a class="layui-btn  layui-btn-xs  layui-btn-normal">查看</a>
            		<a class="layui-btn layui-btn-xs  layui-btn-normal">修改</a>
				</div>
			</div>
		</div>
		<!-- 结算信息 -->
		<div class="layui-row section" style="">
			<div class="layui-col-sm6 section-title">结算信息 </div>
			<div class="layui-col-sm6 " style="text-align: right;"><button class="layui-btn layui-btn-sm layui-btn-radius layui-btn-normal">添 加</button> </div>
		</div>
		<div class="x-table">
			<div class="layui-row x-tr order-list">
				<div class="layui-col-sm10 x-tr-left">
					<div class="layui-col-sm1">1</div>
					<div class="layui-col-sm5">微信</div>
					<div class="layui-col-sm4">2019-04-12 09:08</div>
					<div class="layui-col-sm2">108.00元</div>
				</div>
				<div class="layui-col-sm2  x-tr-right">
					<a class="layui-btn  layui-btn-xs  layui-btn-normal">查看</a>
            		<a class="layui-btn layui-btn-xs  layui-btn-normal">修改</a>
				</div>
			</div>
		</div>
		<div class="text-right" style="color: rgb(120, 120, 120);"><p>应付总额：108.00，已完成支付，可入账 </p></div>
	</div>
</body>
</html>

