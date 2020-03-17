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
	<c:forEach items="${wechatCardRecords }" var="cards">
		<div style="margin-bottom: 15px;">
		<label>
				<input type="checkbox" value="${cards.id}" style="display: inline-block; width: 4%;" name="cardId" onchange="chooseCoupon();">
				<div style="display: inline-block; width: 85%; vertical-align: top;">
					<div class="" style="display: flex; cursor: pointer; color: white; height: 85px; background-color: #46B3DA; position: relative;">
						<div style="align-self: center; text-align: center; min-width: 50%; margin-left: 5%;">
							<span style="font-size: 45px;" id="cardValue_${cards.id}"><fmt:formatNumber type="number" value="${cards.wechatCardValue }" pattern="0.00"/></span> <span style="font-size: 25px;">元</span>
						</div>
						<div style="align-self: center; width: 45%; word-break: break-all;">
							<div style="margin-bottom: 5px;font-size:16px;" id="cardName_${cards.id}">${cards.wechatCardName }</div>
							<div><fmt:formatDate value="${cards.getDate }" pattern="yyyy-MM-dd"/> ~ <fmt:formatDate value="${cards.endDate }" pattern="yyyy-MM-dd"/></div>
						</div>
					</div>
					<div style="color: #333; cursor: pointer; position: relative; background-color:#e8e7e4; height: 30px; padding: 5px 30px 5px 10px;">
						<p style="margin: 0px; overflow: hidden; width: 100%; text-overflow: ellipsis; white-space: nowrap;">
							使用说明: 
							<c:if test="${empty cards.cardsetRemark  }">
								<span>无</span>
							</c:if>
							<c:if test="${not empty cards.cardsetRemark  }">
								<span>${cards.cardsetRemark }</span>
							</c:if>
						</p>
					</div>
				</div>
			</label>
		</div>
		</c:forEach>
		
		<div  class="total-bar">
		<button class="layui-btn layui-btn-normal" style="float: right;margin-right: 20px;" onclick="submitCoupon();">确定</button>
		</div>
		</div>
		
	</div>
</body>
<script type="text/javascript">

function submitCoupon(){
	layui.use([ 'jquery'], function() {
		 var $ = layui.jquery;
		 var valid = true;
		 var tmpPayList = new Array();
		 if ( $('input[name="cardId"]:checked').length > 0){
			 var cardsetIds = "";
			 $('input[name="cardId"]:checked').each(function(index,element){
				 var cardId =  $(element).val();
				 var amount =  $("#cardValue_"+ cardId).text();
				 
				var ordersPay = {};
				ordersPay.payId = '${payId}';
				ordersPay.payName = '优惠券-'+$("#cardName_"+ cardId).text();
				ordersPay.payAmount =  amount;
				ordersPay.remark = '';
				ordersPay.shopId = '${shopId}';
				ordersPay.addTime = new Date();
				ordersPay.canEdit = 1;
				ordersPay.sourceId = cardId;
				cardsetIds = cardsetIds + cardId + "," ;
				tmpPayList.push(ordersPay);
			 }); 
			 if (valid){
				$.ajax({
					type: "POST",
					url: "wechatcardrecord/submitCardset.do",
					data: {'cardsetIds':cardsetIds,'customId':'${customId}','orderId':'${orderId}'},
					dataType: "json",
					success: function(result) {
						if (result.retCode == 'success') {
							var index = parent.layer.getFrameIndex(window.name);
							parent.ajaxsaveOrdersPay('${orderId}',tmpPayList,index);
							parent.layui.jquery(".choose-cardset").text("优惠券 x "+result.retMsg)
							if(result.retMsg > 0){
								parent.layui.jquery(".choose-cardset").removeAttr("disabled");	
							}else{
								parent.layui.jquery(".choose-cardset").attr("disabled","disabled");	
								
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
			 layer.msg("请选择优惠券", {icon: 2});
			 return false;
		 }
	});
	
	
	
}


</script>

</html>

