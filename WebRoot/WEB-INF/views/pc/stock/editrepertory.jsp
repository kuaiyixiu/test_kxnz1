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
<%@ include file="../base.jsp"%>
<title>连途</title>
</head>
<body>
	<input type="hidden" id="id" name="id" value="${repertory.id }" />
	<input type="hidden" id="kind" name="kind" value="${kind }" />
	<div style="width: 98%;margin-left: 1%;">
		<div style="width: 100%;">
		<blockquote class="layui-elem-quote"><c:if test="${kind=='1' }">采购临单</c:if><c:if test="${kind=='2' }">退货临单</c:if>  </blockquote>
			<form class="layui-form" action="" id="form">
			<c:if test="${kind=='1' }">
				<div class="layui-form-item">
					<label class="layui-form-label">供应商</label>
					<div class="layui-input-block">
						<select name="supplyId" id="supplyId" lay-search 	<c:if test="${repertory.repertoryStatus eq '4' or repertory.repertoryStatus eq '5' }"> disabled="disabled"	</c:if>>
							<c:forEach items="${supplyList }" var="supply">
								<option value="${supply.id }"
									<c:if test="${supply.id==repertory.supplyId }">selected="selected"</c:if>>${supply.supplyName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				</c:if>
				<c:if test="${kind=='2' }">
				<blockquote class="layui-elem-quote layui-quote-nm">供应商：${repertory.supplyName }</blockquote>
				</c:if>
				<div class="layui-form-item">
					<label class="layui-form-label"><c:if test="${kind=='1' }">采购列表</c:if><c:if test="${kind=='2' }">退货列表</c:if></label>
					<div class="layui-input-block">
						<table class="layui-table" id="tableInfo1" lay-filter="tableInfo1"></table>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label"><c:if test="${kind=='1' }">采购付款</c:if><c:if test="${kind=='2' }">退货付款</c:if></label>
					<div class="layui-input-block">
						<table class="layui-table" id="tableInfo2" lay-filter="tableInfo2"></table>
					</div>
				</div>
				<div class="layui-form-item layui-form-text">
					<label class="layui-form-label">备注</label>
					<div class="layui-input-block">
						<textarea name="remark" id="remark" placeholder="请输入备注" class="layui-textarea" <c:if test="${repertory.repertoryStatus eq '4' or repertory.repertoryStatus eq '5' }"> disabled="disabled"	</c:if>></textarea>
					</div>
				</div>
			</form>
			<script type="text/html" id="toolbarTop1">
            <div class="layui-btn-container">
			<c:if test="${repertory.repertoryStatus ne '4' and repertory.repertoryStatus ne '5' }">
            	<a class="layui-btn layui-btn-sm" data-url="repertory/addcginfo/${repertory.id}.do" lay-event="addData"><i class="layui-icon">&#xe608;</i>新增</a>
			</c:if>
            </div>
            </script>
			<script type="text/html" id="barLine1">
			<c:if test="${repertory.repertoryStatus ne '4' and repertory.repertoryStatus ne '5' }">
            	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-url="repertory/delcgData.do" tabId="tableInfo1">删除</a>
			</c:if>	
            </script>
			<script type="text/html" id="toolbarTop2">
            <div class="layui-btn-container">
			<c:if test="${repertory.repertoryStatus ne '4' and repertory.repertoryStatus ne '5' }">
            <a class="layui-btn layui-btn-sm" data-url="repertory/addpayinfo/${repertory.id}.do" lay-event="addData"><i class="layui-icon">&#xe608;</i>新增</a>
			</c:if>	
            </div>
            </script>
			<script type="text/html" id="barLine2">
			<c:if test="${repertory.repertoryStatus ne '4' and repertory.repertoryStatus ne '5' }">
            	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-url="repertory/delPayData.do" tabId="tableInfo2">删除</a>
			</c:if>	
            </script>
			<div class="layui-btn-container  text-right section">
				<c:if test="${repertory.repertoryStatus ne '4'}">
					<c:if test="${repository_oa eq '1' }">
						<button class="layui-btn layui-btn-radius layui-btn-warm" id="gz"><c:if test="${kind=='1' }">采购挂账审批</c:if><c:if test="${kind=='2' }">挂账申请</c:if></button>
						<button class="layui-btn layui-btn-radius layui-btn-normal" id="rk"><c:if test="${kind=='1' }">采购入库审批</c:if><c:if test="${kind=='2' }">退货申请</c:if></button>
					</c:if>
					<c:if test="${repository_oa eq '0' }">
						<button class="layui-btn layui-btn-radius layui-btn-warm" id="gz"><c:if test="${kind=='1' }">采购挂账</c:if><c:if test="${kind=='2' }">挂账</c:if></button>
						<button class="layui-btn layui-btn-radius layui-btn-normal" id="rk"><c:if test="${kind=='1' }">采购入库</c:if><c:if test="${kind=='2' }">退货</c:if></button>
					</c:if>
				</c:if>
				
			</div>

		</div>
	</div>
</body>
<script type="text/javascript">
var needPay=0;
var hasPay=0;
var payAll=0;
var productIndex=null;
layui.use(['jquery','table','dialog','element'], function() {
	 var table = layui.table;
	 var dialog = layui.dialog;
	 var $ = layui.jquery;
	 var id=$("#id").val();
	 var url1 = "repertory/getCGList/"+id+".do";
		//采购列表
		table.render({
			elem : '#tableInfo1',
			url: url1,
			method:'post',
			totalRow:true,
			toolbar: '#toolbarTop1',
			defaultToolbar: [],
			done: function(res, curr, count){
				payAll=0;
				var data = res.data;
				for (var item in data) {
					payAll=payAll+data[item].sum;
				}
				needPay=payAll-hasPay;
				if(needPay>0)
					$("#gz").show();
				else
					$("#gz").hide();
				var a = $(".layui-table-total div:eq(4)").html();
				a = a.substr(0,a.indexOf("."));
				$(".layui-table-total div:eq(4)").html(a);
			},
			cols : [ [ {
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center',
			},{
				field : 'num',
				title : '序号',
				type : 'numbers',
				align : 'center',
				width : '5%',
				totalRowText: '合计'
			}, {
				field : 'productName',
				title : '产品',
				align : 'center',
				width : '40%'
			}, {
				field : 'inprice',
				title : '单价',
				align : 'center',
				width : '15%',
				templet: function(d){
					return parseFloat(d.inprice).toFixed(2);
				}
			}, {
				field : 'inQuantity',
				title : '数量',
				align : 'center',
				width : '10%',
				totalRow: true
			}, {
				field : 'sum',
				title : '总价',
				align : 'center',
				width : '15%',
				totalRow: true,
				templet: function(d){
					return parseFloat(d.sum).toFixed(2);
				}
			}, {
				field : 'opt',
				title : '操作',
				align : 'center',
				width : '15%',
				toolbar: '#barLine1'
			} ] ]
		});
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
				if(needPay>0)
					$("#gz").show();
				else
					$("#gz").hide();
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
				toolbar: '#barLine2'
			} ] ]
		});
        //产品列表
		table.on('toolbar(tableInfo1)', function(obj) {
			switch (obj.event) {
				case 'addData':
					var url = $(this).attr('data-url');
					var openw = $(this).attr('openw');
					var openh = $(this).attr('openh');
					if (openw == undefined)
						openw = "700px";
					if (openh == undefined)
						openh = "620px";
					var kind=$("#kind").val();
					if(kind=="1")
						productIndex=page("选择采购产品", url+"?kind="+kind, $(window.frameElement).attr('name'), w = openw, h = openh);//将iframeObj传递给父级窗口,执行操作完成刷新
					else
						productIndex=page("添加退货产品", url+"?kind="+kind, $(window.frameElement).attr('name'), w = openw, h = openh);
					break;
			};
		});
		table.on('tool(tableInfo1)', function(obj) {
			var data = obj.data
			if (obj.event === 'del') {
				var url = $(this).attr('data-url');
				var tabId = $(this).attr('tabId');
				delData($, table, dialog, obj, false, url, tabId);
			}
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
		
		
		  $(document).on('click','#gz',function(){
			  layer.confirm("确认挂账吗?", {icon: 3, title:'提示'}, function(index){
				  if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
					  setYesBtnDisable("disabled");
				  var supplyId=$("#supplyId").val();
				  var remark=$("#remark").val();
				  var id=$("#id").val();
				  var kind=$("#kind").val();
				  var tabId="";
				  if(kind=="1")
					  tabId="openCGFrmId";
				  else if(kind=="2")
					  tabId="openTHFrmId";
				  var data={"supplyId":supplyId,"remark":remark,"needPay":needPay,"hasPay":hasPay,"payAll":payAll,"id":id,"kind":$("#kind").val()};
				  layer.close(index);
				  var result=saveRepertoryin(data);
				  if (result.retCode == 'success'){
					  layer.msg(result.retMsg, {icon : 1});
					  parent.layui.element.tabDelete('tab', tabId);
				  }else
					  layer.msg(result.retMsg, {icon : 2});
				  }
				});
		  });
		  $(document).on('click','#rk',function(){
			  var supplyId=$("#supplyId").val();
			  var remark=$("#remark").val();
			  var id=$("#id").val();
			  var data={"supplyId":supplyId,"remark":remark,"needPay":needPay,"hasPay":hasPay,"payAll":payAll,"id":id,"kind":$("#kind").val()};
			  var kind=$("#kind").val();
			  var tabId="";
			  if(kind=="1")
				  tabId="openCGFrmId";
			  else if(kind=="2")
				  tabId="openTHFrmId";
			  if(payAll>hasPay){
				  layer.confirm("支付金额不足,是否挂账?", {icon: 3, title:'提示'}, function(index){
					  if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
						  setYesBtnDisable("disabled");
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
		  //入库&挂账入库
		  function saveRepertoryin(data){
			  data.needPay = data.needPay.toFixed(2);  
			  data.hasPay = data.hasPay.toFixed(2);  
			  data.payAll = data.payAll.toFixed(2);  
			  
			  var res=null;
				$.ajax({
					type : "POST",
					url : "repertory/saveRepertoryin.do",
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


function setProductInfo(data){
	if(productIndex!=null){
		var body = layer.getChildFrame('body', productIndex);
		body.find('#productName').val(data.productName);
		body.find('#productId').val(data.productId);
		body.find('#iidno').val(data.id);
		body.find('#price').val(data.price);
		body.find('#quantity').val(data.quantity);
		body.find('#sum').val(data.price*data.quantity);
	}
}
</script>

</html>

