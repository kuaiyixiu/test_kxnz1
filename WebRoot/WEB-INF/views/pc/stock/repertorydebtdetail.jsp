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
<%@ include file="../base.jsp"%>
<title>连途</title>
<style type="text/css">
.layui-table-tool-temp {
	padding-right: 0px;
}
</style>
</head>
<body>
	<input type="hidden" id="repertoryId" name="repertoryId" value="${repertory.id }" />
	<input type="hidden" id="debtRecordId" name="debtRecordId" value="${debtRecord.id }" />
	<input type="hidden" name="kind" id="kind" value="${debtRecord.kind }" />
	<div style="width: 98%;margin-left: 1%;">
		<div style="width: 100%;margin-top: 5px;">
			<blockquote class="layui-elem-quote"><c:if test="${debtRecord.kind==2 }">采购挂账单</c:if><c:if test="${debtRecord.kind==3 }">退货挂账单</c:if> </blockquote>
			<blockquote class="layui-elem-quote layui-quote-nm">
				<label>供应商：${repertory.supplyName}</label><br> <label><c:if test="${debtRecord.kind==2 }">采购时间：</c:if><c:if test="${debtRecord.kind==3 }">退货时间：</c:if><fmt:formatDate
						value="${repertory.addTime }" pattern="yyyy-MM-dd HH:mm:ss" /> </label>
			</blockquote>
			<table class="layui-table" id="tableInfo1" lay-filter="tableInfo1"></table>
			<table class="layui-table" id="tableInfo2" lay-filter="tableInfo2"></table>
			<c:if test="${debtRecord.kind==2 }">
			<script type="text/html" id="toolbarTop1">
            <div style="float: left;">采购列表</div>
            </script>
			<script type="text/html" id="barLine">
            <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="editData">改价</a>
            </script>
            </c:if>
            <c:if test="${debtRecord.kind==3 }">
			<script type="text/html" id="toolbarTop1">
            <div style="float: left;">退货产品列表</div>
            </script>
            </c:if>
			<script type="text/html" id="toolbarTop2">
            <div style="float: left;">支付列表</div>
            <div class="layui-btn-container" style="float: right;" align="left">
            <a class="layui-btn layui-btn-sm" data-url="repertory/addpayinfo/${repertory.id}.do" lay-event="addData"><i class="layui-icon">&#xe608;</i>添加</a>
            </div>
            </script>
			<script type="text/html" id="barLine2">
            {{#  if(d.canEdit === 1){ }}
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-url="repertory/delPayData.do" tabId="tableInfo2">删除</a>
            {{#  } }} 
            </script>
			<div class="layui-btn-container  text-right section">
				<button class="layui-btn layui-btn-radius layui-btn-normal" id="hk">还款</button>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
    var needPay=0;
    var hasPay=0;
    var payAll=0;
	layui.use([ 'jquery', 'table', 'dialog', 'element' ], function() {
		var table = layui.table;
		var dialog = layui.dialog;
		var $ = layui.jquery;
		var id = $("#repertoryId").val();
		var url1 = "repertory/getCGList/" + id + ".do";
		var kind=$("#kind").val();
		if(kind=="2"){//入库挂账列表
			//采购列表
			table.render({
				elem : '#tableInfo1',
				url : url1,
				method : 'post',
				totalRow : true,
				toolbar: '#toolbarTop1',
				defaultToolbar: [],
				done : function(res, curr, count) {
					payAll=0;
					var data = res.data;
					for (var item in data) {
						payAll=payAll+data[item].sum;
					}
					needPay=payAll-hasPay;
				},
				cols : [ [{
					field : 'num',
					title : '序号',
					align : 'center',
					width : '10%',
					type  : 'numbers',
					totalRowText : '合计'
				},{
					field : 'id',
					hide : 'true',
					title : 'ID',
					align : 'center',
				},{
					field : 'productName',
					title : '产品名称',
					align : 'center',
					width : '40%',
					templet : function(d) {
						return d.productName+d.productType;
					}
				}, {
					field : 'inprice',
					title : '单价',
					align : 'center',
					width : '15%',
					templet : function(d) {
						return parseFloat(d.inprice).toFixed(2);
					}
				}, {
					field : 'inQuantity',
					title : '数量',
					align : 'center',
					width : '15%',
					totalRow : true
				}, {
					field : 'sum',
					title : '总价',
					align : 'center',
					width : '20%',
					totalRow : true,
					templet : function(d) {
						return parseFloat(d.sum).toFixed(2);
					}
				}
<%--				, {--%>
<%--					field: 'opt',--%>
<%--					width: '10%',--%>
<%--					align: 'center',--%>
<%--					title: '操作',--%>
<%--					toolbar: '#barLine'--%>
<%--				}--%>
				] ]
			});
		}else{
			table.render({
				elem : '#tableInfo1',
				url : url1,
				method : 'post',
				totalRow : true,
				toolbar: '#toolbarTop1',
				defaultToolbar: [],
				done : function(res, curr, count) {
					payAll=0;
					var data = res.data;
					for (var item in data) {
						payAll=payAll+data[item].sum;
					}
					needPay=payAll-hasPay;
				},
				cols : [ [{
					field : 'num',
					title : '序号',
					align : 'center',
					width : '10%',
					type  : 'numbers',
					totalRowText : '合计'
				},{
					field : 'id',
					hide : 'true',
					title : 'ID',
					align : 'center',
				},{
					field : 'productName',
					title : '产品名称',
					align : 'center',
					width : '40%',
					templet : function(d) {
						return d.productName+d.productType;
					}
				}, {
					field : 'inprice',
					title : '单价',
					align : 'center',
					width : '15%',
					templet : function(d) {
						return parseFloat(d.inprice).toFixed(2);
					}
				}, {
					field : 'inQuantity',
					title : '数量',
					align : 'center',
					width : '15%',
					totalRow : true
				}, {
					field : 'sum',
					title : '总价',
					align : 'center',
					width : '20%',
					totalRow : true,
					templet : function(d) {
						return parseFloat(d.sum).toFixed(2);
					}
				}] ]
			});
		}

		//采购付款
		var　 url2 = "repertory/getPayList/"+id+".do";
		table.render({
			elem : '#tableInfo2',
			url: url2,
			method:'post',
			totalRow:true,
			toolbar: '#toolbarTop2',
			defaultToolbar: [],
			done: function(res, curr, count){
				hasPay=0;
				var data = res.data;
				for (var item in data) {
					hasPay=hasPay+data[item].payAmount;
				}
				needPay=payAll-hasPay;		
			},
			cols : [ [ {
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center',
			},{
				field : 'payId',
				title : '付款方式',
				align : 'center',
				width : '40%',
				templet: function(d){
					if(d.payId==1)
						return "现金";
					else if(d.payId==2)
						return "其它";
					else if(d.payId==3)
						return "微信";
					else if(d.payId==4)
						return "支付宝";
					else
						return "";
				},
				totalRowText: '合计'
			}, {
				field : 'payAmount',
				title : '金额',
				align : 'center',
				width : '45%',
				totalRow: true,
				templet: function(d){
					return parseFloat(d.payAmount).toFixed(2);
				}
			}, {
				field : 'opt',
				title : '操作',
				align : 'center',
				width : '15%',
				toolbar: '#barLine2',
				templet: function(row) {
					return getBarRow(row);
				}
			} ] ]
		});
		//支付列表
		table.on('toolbar(tableInfo2)', function(obj) {
			switch (obj.event) {
				case 'addData':
					var url = $(this).attr('data-url');
					var openw = $(this).attr('openw');
					var openh = $(this).attr('openh');
					if (openw == undefined)
						openw = "700px";
					if (openh == undefined)
						openh = "620px"
					//将iframeObj传递给父级窗口,执行操作完成刷新
					page("添加付款项目", url, $(window.frameElement).attr('name'), w = openw, h = openh);
					break;
			};
		});
		table.on('tool(tableInfo2)', function(obj) {
			var data = obj.data
			if (obj.event === 'del') {
				var url = $(this).attr('data-url');
				var tabId = $(this).attr('tabId');
				delData($, table, dialog, obj, false, url, tabId);
			}
		});
		table.on('tool(tableInfo1)', function(obj) {
			var data = obj.data
			if (obj.event === 'editData') {
				var url = "repertory/edtcgPrice/"+data.id+".do";
				var openw = $(this).attr('openw');
				var openh = $(this).attr('openh');
				if (openw == undefined)
					openw = "700px";
				if (openh == undefined)
					openh = "620px"
				//将iframeObj传递给父级窗口,执行操作完成刷新
				page("修改价格", url, $(window.frameElement).attr('name'), w = openw, h = openh);
			}
		});
		
		  $(document).on('click','#hk',function(){
			  var debtRecordId=$("#debtRecordId").val();
			  var data={"id":debtRecordId};
				var tabId = "openCGDebtFrmId";
				var kind=$("#kind").val();
				if(kind=='3'){
					tabId="openTHDebtFrmId";
				}
			  if(payAll>hasPay){
				  layer.confirm("支付金额不足,是否继续挂账?", {icon: 3, title:'提示'}, function(index){
					  if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
					    layui.$(".layui-layer-btn0").addClass("layui-btn-disabled");
					    layui.$(".layui-layer-btn0").attr("disabled","disabled");
					  	layer.close(index);
						  var result=saveRepertoryin(data);
						  if (result.retCode == 'success'){
							  layer.msg(result.retMsg, {icon : 1});
							  parent.layui.element.tabDelete('tab', tabId);
						  }else
							  layer.msg(result.retMsg, {icon : 2});
					  }
					});
			  }else{
				  var result=saveRepertoryin(data);
				  if (result.retCode == 'success'){
					  layer.msg(result.retMsg, {icon : 1});
					  parent.layui.element.tabDelete('tab', tabId);
				  }else
					  layer.msg(result.retMsg, {icon : 2});  
			  }
		  });
		  //还款
		  function saveRepertoryin(data){
			  var res=null;
				$.ajax({
					type : "POST",
					url : "debtRecord/saveReturnDebt.do",
					data : data,
					dataType : "json",
					async:false,
					success : function(result) {
						res=result;
					}
				});
				return res;
		  }  
	});
	
	function getBarRow(row){
		var tpl = barLine2.innerHTML;
		// 如果不存在，则最后一个选中
		layui.laytpl(tpl).render(row, function(html) {
			return html;
		});
	}

</script>

</html>

