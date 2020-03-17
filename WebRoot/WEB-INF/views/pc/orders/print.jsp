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
<script type="text/javascript">
	function printit(oper)  
	{  
		 if(oper < 10) {
                bdhtml =window.document.body.innerHTML;//获取当前页的html代码
                sprnstr = "<!--startprint" + oper + "-->";//设置打印开始区域
                eprnstr = "<!--endprint" + oper + "-->";//设置打印结束区域
                prnhtml =bdhtml.substring(bdhtml.indexOf(sprnstr) + 18); //从开始代码向后取html
                prnhtml = prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html
                window.document.body.innerHTML= prnhtml;
                window.print();
                window.document.body.innerHTML =bdhtml;
            } else{
                window.print();
            }
	}  
</script>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>连途</title>
<link rel="stylesheet" type="text/css"href="lib/admin/layui/css/layui.css" />
	<style type="text/css">
		.text-right {
    		text-align: right;
		}
		.text-center {
    		text-align: center;
		}
	</style>
</head>
<body>

<div class="layui-form">
	<!--startprint1-->
	<div>
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px; text-align: center;">
  		<legend class="text-center">${shops.shopName }-结算单</legend>
	</fieldset>
	<table class="layui-table">
		<tr>
			<td class="text-right"width="150px">车牌</td>
			<td id="carNo"width="200px">${car.carNumber}</td>
			<td class="text-right"width="150px">车型</td>
			<td id="carType"width="200px">${car.carBrand}</td>
			<td class="text-right"width="150px">单号</td>
			<td id="orderNo"width="200px">${orders.orderNo}</td>
		</tr>
	 	<tr>
	 		<td class="text-right">姓名</td>
	 		<td id="custName">${custom.custName}</td>
	 		<td class="text-right">电话</td>
	 		<td id="custPhone">${custom.cellphone}</td>
	 		<td class="text-right">时间</td>
	 		<td id="createTime"><fmt:formatDate value="${orders.creatTime}" pattern="yyyy-MM-dd"/></td>
	 	</tr>
		<tr>
			<td class="text-right">进站里程</td>
			<td id="inboundMileage"></td>
			<td class="text-right">VIN码</td>
			<td id="vinCode">${car.carVin}</td>
			<td></td>
			<td></td>
		</tr>
 	</table>
	<table class="layui-table">
		<thead >
			<tr class="text-center">
				<td colspan="3">项目施工</td>
				<td >价格</td>
				<td >应付金额</td>
			</tr>
			<tbody class="text-center">
				<c:forEach items="${ orders.ordersServeList }" var="server" varStatus="s">
					<tr>
						<td colspan="3">${s.index+1}. ${server.serveName }</td>
						<td><fmt:formatNumber type="number" value="${server.price }" pattern="0.00"/></td>
						<td>${server.price }</td>
					</tr>
				</c:forEach>
			
				
				<tr class="text-right">
					<td colspan="4">小计</td>
					<td class="text-center">${serveAmount}</td>
				</tr>
			 </tbody>
		 </thead>
	 </table>
	 <table class="layui-table">
		<thead >
			<tr class="text-center">
				<td >产品名称</td>
				<td >产品类型</td>
				<td >数量</td>
				<td >单价</td>
				<td >应付金额</td>
			</tr>
			<tbody class="text-center">
				<c:forEach items="${ orders.ordersProductList }" var="product" varStatus="s">
					<tr>
						<td>${s.index+1}. ${product.productName }</td>
						<td>${product.productType }</td>
						<td>${product.quantity }</td>
						<td><fmt:formatNumber type="number" value="${product.price }" pattern="0.00"/></td>
						<td><fmt:formatNumber type="number" value="${product.price*product.quantity }" pattern="0.00"/></td>
					</tr>
				</c:forEach>
			
				
				<tr class="text-right">
					<td colspan="4">小计</td>
					<td class="text-center">${productAmount}</td>
				</tr>
				
			 </tbody>
		 </thead>
	 </table>
	 <table class="layui-table">
		<thead >
			<tr class="text-center">
				<td>支付方式</td>
				<td>金额</td>
			</tr>
			<tbody class="text-center">
			   <c:forEach items="${ orders.ordersPayList }" var="orderPay" varStatus="s">
					<tr>
						<td>${orderPay.payName }</td>
						<td><fmt:formatNumber type="number" value="${orderPay.payAmount }" pattern="0.00"/></td>
					</tr>
				</c:forEach> 
				<tr class="text-right">
					<td>应付总计</td>
					<td class="text-center">${orders.orderAmt }</td>
				</tr>
				<tr class="text-right">
					<td>实付总计</td>
					<td class="text-center"><fmt:formatNumber type="number" value="${orderPayAmount }" pattern="0.00"/></td>
				</tr>
			 </tbody>
		 </thead>
	 </table>
	 <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
  		<legend>备注</legend>
	 </fieldset>
	 <table class="layui-table" lay-skin="nob">
		<tr>
			<td class="text-center">客户签字:</td>
			<td class="text-center">服务顾问签字:</td>
			<td class="text-center">结算员签字:</td>
		</tr>
	 </table>
	 <div class="footer text-right" style="margin-top: 50px;">
	 	${shops.shopName }
	 	<br/>
	 	电话：${shops.shopTel } 
	 	<br/>
	 	传真：${shops.fax }
	 	<br/>
	 	地址：${shops.shopAddress }
	 </div>
	 </div>
	 <!--endprint1-->
	 <div class="text-center" style="margin-top: 50px;">
	 	<button type="button" class="layui-btn layui-btn-normal layui-btn-radius" onclick="printit(1)">打印本页</button>
	 </div>
	 
 </div>
 
</body>
</html>



