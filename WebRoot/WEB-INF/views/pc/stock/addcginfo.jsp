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
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		form.render();
		
		form.verify({
			quantity: function(value, item){ //value：表单的值、item：表单的DOM对象
				var iidno=$("#iidno").val();
				var count=$("#quantity").val();
				if(count<=0)
					return"输入数量不合法";
				if(iidno!=""&&typeof(iidno) != "undefined"){
					var res=ajaxMaxCount(iidno,count);
					if (res.retCode == 'error')
						return res.retMsg;
				}
			  }
			});   
		
		form.on('select(productId)', function(data){
			if(data.value!=""){
				var product=getProductInfo(data.value);
				if(product!=null){
					$("#price").val(product.amount);
					var quantity=parseInt($("#quantity").val());
					$("#sum").val(product.amount*quantity);
				}
			}
		});      
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			$.ajax({
				type : "POST",
				url : "repertory/saveDtData.do",
				data : data.field,
				dataType : "json",
				success : function(result) {
					if (result.retCode == 'success') {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭当前页  
						parent.layui.table.reload('tableInfo1');
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
		
		function getProductInfo(id){
			var proInfo=null;
			$.ajax({
				type : "POST",
				url : "product/getProductInfo.do",
				data : {"id":id},
				dataType : "json",
				async:false,
				success : function(result) {
					proInfo=result;
				}
			});
			return proInfo;
		}
		
		
		function ajaxMaxCount(iidno,count){
			var res=null;
			$.ajax({
				type : "POST",
				url : "repertory/chkDtData.do",
				data : {"id":iidno,"count":count},
				dataType : "json",
				async:false, 
				success : function(result) {
					res=result;
				}
			});
			return res;
		}

	});
	
	function chkSum(obj){
		var $ = layui.jquery;
		if(obj.id=="price"){
			initNum(obj,2);
		}else if(obj.id=="quantity"){
			//校验数量是否大于库存数量
			initNum(obj,0);				
		}
		var price=$("#price").val();
		var quantity=parseInt($("#quantity").val());
		$("#sum").val(price*quantity);
	}
	
	function qkProduct(){
		var url = "quick/qkproduct.do";
		var openw = "900px";
		var openh = "650px";
		parent.layui.layer.open({
			type: 2,
			title: "产品速查",
			area: [openw, openh],
			fixed: false, //不固定
			content: url
		});
	}
</script>
</head>
<body>
    <input type="hidden" name="kind" id="kind" value="${kind }" /> 
	<form class="layui-form" action="" id="form"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<input type="hidden" name="repertory" value="${repertoryId }" /> 
		<div class="layui-form-item">
			<label class="layui-form-label"><font color="red">*</font>选择产品</label>
			<div class="layui-input-block">
			    <c:if test="${kind=='1' }">
				<select name="productId" lay-verify="required" lay-search lay-filter="productId">
				    <option value="">选择产品</option>
					<c:forEach items="${proList }" var="product">
						<option value="${product.id }">${product.productName }</option>
					</c:forEach>
				</select>
				</c:if>
				<c:if test="${kind=='2' }">
				<div class="layui-input-inline" style="width: 80%;">
				<input type="text" name="productName" id="productName" autocomplete="off" class="layui-input layui-disabled" disabled="disabled" value=""/>
				<input type="hidden" name="productId" id="productId" value="" /> 
				<input type="hidden" name="iidno" id="iidno" value="" /> 
				</div>
				<div class="layui-form-mid layui-word-aux"><i class="layui-icon layui-icon-search" onclick="qkProduct()"></i></div>
				</c:if>
				
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><font color="red">*</font><c:if test="${kind=='1' }">入库单价</c:if><c:if test="${kind=='2' }">退货单价</c:if></label>
			<div class="layui-input-block">
				<input type="number" name="inprice" id="price" placeholder="请输入单价"
					autocomplete="off" class="layui-input" value="0.00"
					onblur="chkSum(this)" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><font color="red">*</font><c:if test="${kind=='1' }">入库数量</c:if><c:if test="${kind=='2' }">退货数量</c:if></label>
			<div class="layui-input-block">
				<input type="number" name="inQuantity" id="quantity" placeholder="请输入数量"
					autocomplete="off" class="layui-input" value="1" lay-verify="quantity"
					onblur="chkSum(this)" />
			</div>
		</div>
		<div class="layui-form-item">
		    <c:if test="${kind=='1' }">
			<label class="layui-form-label">入库总价</label>
			</c:if>
			<c:if test="${kind=='2' }">
			<label class="layui-form-label">退货总价</label>
			</c:if>
			<div class="layui-input-block">
				<input type="number" name="sum" id="sum" autocomplete="off" class="layui-input layui-disabled" value="0.00"
					disabled="disabled" />
			</div>
		</div>
		<c:if test="${kind=='1' }">
		<div class="layui-form-item">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<textarea name="remark" class="layui-textarea"></textarea>
			</div>
		</div>
		</c:if>
		<div class="layui-form-item">
			<div class="layui-input-block" align="center">
				<button class="layui-btn" lay-submit lay-filter="formSubmitBtn">提交</button>
				<button class="layui-btn closeBtn" id="closeBtn" type="button">关闭</button>
			</div>
		</div>
	</form>
</body>
</html>

