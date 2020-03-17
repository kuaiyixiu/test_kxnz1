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

<div>
	<!--startprint1-->
    <div class="print-receipt01" style="padding: 20px;width: 228px;margin: 0 auto;">
        <header>
            <h3 style="font-size: 20px;text-align: center;margin: 0" class="ng-binding">${shops.shopName }</h3>
            <p style="font-size: 14px;text-align: center;margin: 0">结算单</p>
        </header>
        <section style="font-size: 12px;">
            <div style="padding: 10px 0;border-bottom: 1px solid #000">
                <label class="ng-binding" style="display: block;">单据号：${orders.orderNo}</label>
                <label class="ng-binding" style="display: block;">车牌：${car.carNumber}</label>
                <label class="ng-binding" style="display: block;">车型：${car.carBrand}</label>
                <label class="ng-binding" style="display: block;">客户：${custom.custName}</label>
                <label class="ng-binding" style="display: block;">时间：<fmt:formatDate value="${orders.creatTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
                <label class="ng-binding" style="display: block;">VIN码：${car.carVin}</label>
                <label class="ng-binding" style="display: block;">备注：${orders.remark}</label>
            </div>
            <div style="padding: 10px 0;border-bottom: 1px solid #000">
                <table style="width: 100%;">
                    <thead>
                    <tr>
                        <td >产品名称</td>
						<td >产品类型</td>
						<td >数量</td>
						<td >单价</td>
						<td >应付金额</td>
                    </tr>
                    </thead>
                    <tbody>
		                <c:forEach items="${ orders.ordersServeList }" var="server" varStatus="s">
							<tr>
								<td colspan="3">${s.index+1}. ${server.serveName }</td>
								<td><fmt:formatNumber type="number" value="${server.price }" pattern="0.00"/></td>
								<td>${server.price }</td>
							</tr>
						</c:forEach>
						<c:forEach items="${ orders.ordersProductList }" var="product" varStatus="s">
							<tr>
								<td>${s.index+1}. ${product.productName }</td>
								<td>${product.productType }</td>
								<td>${product.quantity }</td>
								<td><fmt:formatNumber type="number" value="${product.price }" pattern="0.00"/></td>
								<td><fmt:formatNumber type="number" value="${product.price*product.quantity }" pattern="0.00"/></td>
							</tr>
						</c:forEach>

                    </tbody>
                </table>
            </div>
            <div style="padding: 10px 0;border-bottom: 1px solid #000">
                <table style="width: 100%;">
                    <thead>
                    <tr>
                        <td>支付方式</td>
						<td>金额</td>
                    </tr>
                    </thead>
                    <tbody>
		                <c:forEach items="${ orders.ordersPayList }" var="orderPay" varStatus="s">
							<tr>
								<td>${orderPay.payName }</td>
								<td><fmt:formatNumber type="number" value="${orderPay.payAmount }" pattern="0.00"/></td>
							</tr>
						</c:forEach> 
                    </tbody>
                </table>
            </div>
            <div style="padding: 10px 0;">
                <label class="ng-binding" style="display: block;">订单总额：${orders.orderAmt }</label>
                <label class="ng-binding" style="display: block;">实收金额：<fmt:formatNumber type="number" value="${orderPayAmount }" pattern="0.00"/></label>
               
                <label class="ng-binding" style="display: block;">打印时间：<fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
                <label class="ng-binding" style="display: block;">打印人：${ userName}</label>
            </div>
        </section>
    </div>
     <!--endprint1-->
	 <div class="text-center" style="margin-top: 50px;">
	 	<button type="button" class="layui-btn layui-btn-normal layui-btn-radius" onclick="printit(1)">打印本页</button>
	 </div>
</div>
 
</body>
</html>



