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
</head>
<style>
.layui-unselect dl {
	height: 100px;
}

.layui-form-selected dl {
	height: 100px;
}
</style>
<script type="text/javascript">
    function userDefinedLoadForm(){
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		form.verify({
			money : function(value, item) { //value：表单的值、item：表单的DOM对象
				if (value <= 0)
					return "充值金额输入不合法，请重新输入！";
			}
		});
		initCoupon();
		$("#couponDiv").hide();
		//监听展开事件
		$(document).on('click', '#sendCoupon', function() {
			if ($("#couponDiv").is(':hidden')){
				$("#couponDiv").show();
				$("#couponDivDisplay").val("1");
				$(this).html("取消代金券");
			}else{
				$("#couponDiv").hide();
				$("#couponDivDisplay").val("0");
				$(this).html("赠送代金券");
			}
		});
		//监听代金券radiobutton
		form.on('radio(sendCouponType)', function(data) {
			var v=data.value;
			if(v=='1'){
				$("#couponLabel").html("代金券");
				initCoupon();
			}else if(v=='2'){
				$("#couponLabel").html("代金券包");
				//获取所有代金券包
				var html='';
				var obj=getCouponPackList();
				var arr=obj.retData;	
				if(arr!=null){
					for(var i=0;i<arr.length;i++)
						html=html+'<option value="'+arr[i].id+'">'+arr[i].name+'</option>';
				}
				$("#couponSelect").html(html);
				form.render("select");
			}
		});
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			$.ajax({
				type : "POST",
				url : "custom/saveRechargeData.do",
				data : data.field,
				dataType : "json",
				beforeSend:function(){ //触发ajax请求开始时执行
					//改变提交按钮上的文字并将按钮设置为不可点击 
					$(this).attr("disabled","true"); 
					},
				success : function(result) {
					if (result.retCode == 'success') {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭当前页  
						window.parent.location.reload();
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
        //获取代金券列表
		function getCouponList() {
			var proInfo = null;
			$.ajax({
				type : "POST",
				url : "coupon/getCouponInfo.do",
				data : {},
				dataType : "json",
				async : false,
				success : function(result) {
					proInfo = result;
				}
			});
			return proInfo;
		}
        //获取代金券包列表
		function getCouponPackList() {
			var proInfo = null;
			$.ajax({
				type : "POST",
				url : "coupon/getCouponPackList.do",
				data : {},
				dataType : "json",
				async : false,
				success : function(result) {
					proInfo = result;
				}
			});
			return proInfo;
		}
		//获取所有代金券
		function initCoupon(){
			var html="";
			var obj=getCouponList();
			var arr=obj.retData;	
			if(arr!=null){
				for(var i=0;i<arr.length;i++)
					html=html+'<option value="'+arr[i].id+'">'+arr[i].name+'</option>';
			}
			$("#couponSelect").html(html);
			layui.form.render("select");
		}
    }
</script>
<body>
	<form action="" class="layui-form" id="addCustomForm"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<input type="hidden" name="cardNo" value="${cardNo }" />
		<input type="hidden" name="couponDivDisplay" id="couponDivDisplay" value="0" />
		<div class="layui-form-item">
			<label class="layui-form-label">充值金额</label>
			<div class="layui-input-inline">
				<input type="number" name="rechargeAmount" lay-verify="money"
					placeholder="请输入充值金额" autocomplete="off" class="layui-input"
					value="0.00" onblur="initNum(this,2)">
			</div>
			<div class="layui-word-aux">
				<button type="button" class="layui-btn layui-btn-normal"
					id="sendCoupon">赠送代金券</button>
			</div>
		</div>
		<div id="couponDiv">
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
					<input type="radio" name="sendCouponType" value="1" title="代金券" checked lay-filter="sendCouponType" id="sendCouponType1">
					<input type="radio" name="sendCouponType" value="2" title="代金券包" lay-filter="sendCouponType">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" id="couponLabel">代金券</label>
				<div class="layui-input-block">
					<select name="couponId" lay-verify="required" id="couponSelect" lay-filter="couponSelect">
					</select>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">付款方式</label>
			<div class="layui-input-block">
				<select name="rechargeType" lay-verify="required">
					<option value="">请选择付款方式</option>
					<c:forEach items="${custPay }" var="payType">
						<option value="${payType.id }">${payType.payName }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">额外说明</label>
			<div class="layui-input-block">
				<textarea name="remark" class="layui-textarea"></textarea>
			</div>
		</div>
		<div class="layui-form-item">
			<div align="center">
				<a class="layui-btn layui-btn-normal" lay-submit
					lay-filter="formSubmitBtn">提交</a> <a
					class="layui-btn layui-btn-normal closeBtn" lay-filter="closeBtn">关闭</a>
			</div>
		</div>
	</form>
</body>
</html>





