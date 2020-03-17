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
<style type="text/css">
* {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}
input[type=checkbox], input[type=radio] {
    margin: 4px 0 0;
    line-height: normal;
}

.useAmount[disabled] {
    border: none !important;
    color: grey !important;
    background: gainsboro !important;
    cursor: not-allowed;
    pointer-events:none;
}
</style>

</head>
<body>
	<div style="padding: 15px;position: relative;">
	<div style="max-height: 500px; overflow-y: auto;">
	<c:forEach items="${custCoupons }" var="coupon">
		<div style="margin-bottom: 15px;">
		<label>
				<input type="checkbox" value="${coupon.id}" style="display: inline-block; width: 4%;" name="couponId" onchange="chooseCoupon();">
				<div style="display: inline-block; width: 60%; vertical-align: top;">
					<div class="" style="display: flex; cursor: pointer; color: white; height: 85px; background-color: #ed9701; position: relative;">
						<div style="align-self: center; text-align: center; min-width: 50%; margin-left: 5%;">
							<span style="font-size: 45px;"><fmt:formatNumber type="number" value="${coupon.availAmount }" pattern="0.00"/></span> <span style="font-size: 25px;">元</span>
						</div>
						<div style="align-self: center; width: 45%; word-break: break-all;">
							<div style="margin-bottom: 5px;font-size:16px;" id="couponName_${coupon.id}">${coupon.couponName }</div>
							<div><fmt:formatDate value="${coupon.createTime }" pattern="yyyy-MM-dd"/> ~ <fmt:formatDate value="${coupon.endTime }" pattern="yyyy-MM-dd"/></div>
						</div>
					</div>
					<div style="color: #333; cursor: pointer; position: relative; background-color:#e8e7e4; height: 30px; padding: 5px 30px 5px 10px;">
						<p style="margin: 0px; overflow: hidden; width: 100%; text-overflow: ellipsis; white-space: nowrap;">
							使用说明: 
							<c:if test="${empty coupon.remark  }">
								<span>无</span>
							</c:if>
							<c:if test="${not empty coupon.remark  }">
								<span>${coupon.remark }</span>
							</c:if>
						</p>
					</div>
				</div>
			</label>
			<div style="padding: 15px; width: 33%; height: 116px; display: inline-block; float: right; border: 2px solid  #ffebc3;">
				<div style="margin-bottom: 10px;">该代金券 原始价值<fmt:formatNumber type="number" value="${coupon.totalAmount }" pattern="0.00"/>元</div>
				<div  style="margin-bottom: 5px; font-size: 12px;">剩余价值<fmt:formatNumber type="number" value="${coupon.availAmount }" pattern="0.00"/>元，本次抵用</div>
				<div style="letter-spacing: 4px;">
					￥<input disabled="disabled"  availAmount="<fmt:formatNumber type="number" value="${coupon.availAmount }" pattern="0.00"/>" class="useAmount" type="text" id="useAmount_${coupon.id}"  style="height:22px;line-height:22px; padding-left: 5px; color: black; width: 50%; border: 1px solid #e8e7e4; border-radius: 4px; background: #ffebc3;">元
				</div>
			</div>
		</div>
		</c:forEach>
		
		<div  class="total-bar">
		<button class="layui-btn layui-btn-normal" style="float: right;margin-right: 20px;" onclick="submitCoupon();">确定</button>
		</div>
		</div>
		
	</div>
</body>
<script type="text/javascript">

function chooseCoupon(){
	layui.use([ 'jquery'], function() {
		 var $ = layui.jquery;
	//	 var couponId =  $('input[name="couponId"]:checked').val();
		 //处理disabled
	//	 $("#useAmount_"+couponId).removeAttr("disabled");
		 
		 $('input[name="couponId"]:checked').each(function(index,element){
			 $("#useAmount_"+ $(element).val()).removeAttr("disabled");
		 });  
		 $('input[name="couponId"]').not("input:checked").each(function(index,element){
			 $("#useAmount_"+ $(element).val()).attr("disabled","disabled");
			 $("#useAmount_"+ $(element).val()).val("");
		 }); 
	});
}




function submitCoupon(){
	layui.use([ 'jquery'], function() {
		 var $ = layui.jquery;
		 var valid = true;
		 var tmpPayList = new Array();
		 if ( $('input[name="couponId"]:checked').length > 0){
			 var couponIds = "";
			 var amounts = "";
			 $('input[name="couponId"]:checked').each(function(index,element){
				 var couponId =  $(element).val();
				 var amount =  $("#useAmount_"+ couponId).val();
				 if (!$.trim(amount)){
					 layer.msg("请输入正确的抵扣价格", {icon: 2});
					 valid = false;
					 return false;			 
				 }
				 var availAmount = $("#useAmount_"+ couponId).attr("availAmount");
				 if(parseFloat(amount) > parseFloat(availAmount)){
					 layer.msg("代金券余额不足", {icon: 2});
					 valid = false;
					 return false;	
				 }
				 
				var ordersPay = {};
				ordersPay.payId = '${payId}';
				ordersPay.payName = '代金券-'+$("#couponName_"+ couponId).text();
				ordersPay.payAmount =  amount;
				ordersPay.remark = '';
				ordersPay.shopId = '${shopId}';
				ordersPay.addTime = new Date();
				ordersPay.canEdit = 1;
				ordersPay.sourceId = couponId;
				tmpPayList.push(ordersPay);
				couponIds = couponIds + couponId + "," ;
				amounts = amounts + amount + "," ;
			 }); 
			 if (valid){
				$.ajax({
					type: "POST",
					url: "coupon/submitCoupon.do",
					data: {'couponIds':couponIds,'amounts':amounts,'customId':'${customId}','orderId':'${orderId}'},
					dataType: "json",
					success: function(result) {
						if (result.retCode == 'success') {
							var index = parent.layer.getFrameIndex(window.name);
							parent.ajaxsaveOrdersPay('${orderId}',tmpPayList,index);
							parent.layui.jquery(".choose-coupon").text("代金券 x "+result.retMsg)
							if(result.retMsg > 0){
								parent.layui.jquery(".choose-coupon").removeAttr("disabled");	
							}else{
								parent.layui.jquery(".choose-coupon").attr("disabled","disabled");	
								
							}
							parent.layer.close(index);//关闭当前页  
						} else {
							layer.msg(result.retMsg, {
								icon: 2
							});
						}
					}
				});
			 }
		 }else{
			 layer.msg("请选择代金券", {icon: 2});
			 return false;
		 }
	});
	
	
	
}


</script>

</html>

