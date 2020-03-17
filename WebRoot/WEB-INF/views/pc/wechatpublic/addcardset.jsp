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
<link rel="stylesheet" type="text/css"
	href="lib/admin/layui/css/layui.css" />
<script src="lib/admin/layui/layui.js" type="text/javascript"
	charset="utf-8"></script>
<script src="lib/admin/js/common.js" type="text/javascript"
	charset="utf-8"></script>
<script type="text/javascript">
    function userDefinedLoadForm(){
    	var laydate = layui.laydate;
    	var $ = layui.$;
    	laydate.render({
    		elem: "#endDate",
    		//value: '${endDate}',
    		done: function(value, date, endDate){
    			layui.$("#endDateLabel").html(value);
    		  }
    	});
    	if($("#id") !=''){
    		$("#cardNameLabel").html($("#cardName").val());
    		$("#cardValueLabel").html($("#cardValue").val());
    		$("#endDateLabel").html($("#endDate").val());
    		$("#remarkLabel").html($("#remark").val());
    	}
    }

	function changeValue(obj) {
		var $=layui.$;
		//if(obj.name=="remark"){//备注
			//if($.trim(obj.value)!=''){
			//	$("remarkDiv").show();
			//}
		//}
		if(obj.name=="cardValue"){
			if(obj.value<0)
				obj.value=0;
			initNum(obj,2);
		}
		$("#"+obj.name+"Label").html(obj.value);
		
	}
</script>
</head>
<body>
	<div style="width: 90%;height: 98%;margin-top: 1%;margin-left: 2px;">
		<div class="layui-fluid">
			<div class="layui-row">
				<div class="layui-col-md3"
					style="background-image: url(image/wechat_card_bg.png);background-repeat:no-repeat; background-size:100% 100%;height: 667px;border:1px solid #eeeeee">
					<div
						style="width: 90%;height: 80%;background-color: #fff;margin-left: 5%;margin-top: 23%;">
						<div style="height: 1%;">&nbsp;</div>
						<div
							style="background-image: url(image/wechat_card_logo.png);background-repeat:no-repeat; background-size:98% 98%;height: 50%;margin-left: 2%;position: relative;">
						   <div
								style="background-image: url(image/wechat_card_name.png);background-repeat:no-repeat; background-size:100% 100%;top: 3%;position: absolute;width: 40%;height: 10%;display: flex;align-items: center;text-align: center;justify-content: center;">
								<font color="#fff" id="cardNameLabel">卡卷名称</font>
							</div>
							<div
								style="width: 40%;height: 20%;position: absolute;right: 4%;bottom: 20%;">
								<font color="#fff" size="7" id="cardValueLabel">0.00</font><font color="#fff">
									元</font>
							</div>
							<div
								style="background-image: url(image/wechat_card_date.png);background-repeat:no-repeat; background-size:100% 100%;bottom: 10%;position: absolute;right: 4%;width: 50%;height: 10%;display: flex;align-items: center;text-align: center;justify-content: center;">
								<font color="#fff">有效期至：<label id="endDateLabel">${endDate}</label></font>
							</div>
						</div>
						<div
							style="background-image: url(image/wechat_card_input.png);background-repeat:no-repeat; background-size:98% 98%;height: 40px;margin-left: 2%;position: relative;margin-top: 2%;vertical-align: middle;line-height: 40px;"
							align="center">
							<font color="#555555">请输入车牌号</font>
						</div>
						<div
							style="background-image: url(image/wechat_card_button.png);background-repeat:no-repeat; background-size:98% 98%;height: 40px;margin-left: 2%;position: relative;margin-top: 2%; vertical-align: middle;line-height: 40px;"
							align="center">
							<font color="#FFFFFF">立即领取</font>
						</div>
						<div style="margin-left: 2%;margin-top: 4%;" id="remarkDiv">
							<div class="layui-row">
								<font color="#666" size="2">使用说明：</font>
							</div>
							<div class="layui-row"
								style="width: 98%;display:block;word-break: break-all;word-wrap: break-word;margin-top: 2%;">
								<font color="#999999" id="remarkLabel"></font>
							</div>
						</div>
					</div>
				</div>
				<div class="layui-col-md8 layui-col-md-offset1">
				<form class="layui-form" action="" id="form">
				    <input type="hidden" name="id" id="id" value="${cardSet.id }" />
					<div class="layui-form-item">
						<label class="layui-form-label"><font color="red">*</font>卷名称</label>
						<div class="layui-input-block">
							<input type="text" name="cardName" id="cardName" required lay-verify="required"
								placeholder="请输入卷名称" autocomplete="off" class="layui-input" onblur="changeValue(this)" value="${cardSet.cardName }">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><font color="red">*</font>卷面值</label>
						<div class="layui-input-block">
							<input type="number" name="cardValue" id="cardValue" lay-verify="money"
								placeholder="请输入卷面值" autocomplete="off" class="layui-input" onblur="changeValue(this)" value="<fmt:formatNumber type="number" value="${cardSet.cardValue }" pattern="0.00"/>">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><font color="red">*</font>有效期</label>
						<div class="layui-input-block">
							<input type="text" name="endDate" id="endDate" required lay-verify="required"
								placeholder="请输入有效期" autocomplete="off" class="layui-input" value="<fmt:formatDate value="${cardSet.endDate }" pattern="yyyy-MM-dd"/>">
						</div>
					</div>
					<div class="layui-form-item layui-form-text">
						<label class="layui-form-label">使用说明</label>
						<div class="layui-input-block">
							<textarea name="remark" id="remark" placeholder="请输入使用说明" class="layui-textarea" onblur="changeValue(this)">${cardSet.remark }</textarea>
						</div>
					</div>
				</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

