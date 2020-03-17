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
<body>
	<div class="page-content-wrap">
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<c:if test="${user.bossAccount == 1}">
						<div class="layui-inline">
							<label class="layui-form-label">门店名称</label>
							<div class="layui-input-inline">
								<select name="shopId" id="shopId">
								   <c:forEach items="${shopList }" var="s">
								   <option value="${s.id }" <c:if test="${s.id==shops.id }">selected="selected"</c:if> >${s.shopName }</option>
								   </c:forEach>
								</select>
							</div>
						</div>
					</c:if>
					<div class="layui-inline">
						<label class="layui-form-label">产品名称</label>
						<div class="layui-input-inline">
							<input type="text" name="productName" id="productName"
								autocomplete="off" class="layui-input" />
						</div>
					</div>
					<button class="layui-btn layui-btn-normal" data-type="reload"
						lay-filter="searchBtn" type="button">搜索</button>
				</fieldset>

			</div>
		</form>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="productInfo" lay-filter="tableInfo"></table>
			<script type="text/html" id="barLine">
            <a class="layui-btn layui-btn-xs" lay-event="detail" data-url="product/detailData.do">流水</a>
            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="chageShop" data-url="product/allocateData.do">调拨</a>
            </script>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
/**
 * 加载表格
 * @param $
 * @param table
 * @param parentId 父级id
 * @param id 当前等级id
 * @param type back返回类型
 * @returns
 */
function userDefinedLoadGrd($, table, parentId, id) {
	var　 url = "product/getStockList.do";
	//初始化网格
	var tableIns = table.render({
		elem: '#productInfo',
		url: url,
		page: true,
		method:'post',
		limit: 10,
		limits: [10, 20, 30, 40, 50],
		where: {
			"productName": $.trim($("#productName").val()),
			"shopId":$("#shopId").val()
		},
		cols: [
			[{
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center',
			}, {
				field: 'productName',
				width: '20%',
				align: 'center',
				title: '产品名称'
			}, {
				field: 'productType',
				width: '15%',
				align: 'center',
				title: '产品型号'
			},{
				field: 'carType',
				width: '15%',
				align: 'center',
				title: '适用车型'
			},{
				field: 'price',
				width: '15%',
				align: 'center',
				title: '售价'
			},{
				field: 'quantity',
				width: '10%',
				align: 'center',
				title: '余量'
			},{
				field: 'opt',
				width: '25%',
				align: 'center',
				title: '操作',
				toolbar: '#barLine'
			}]
		]
	});
}


layui.use(['jquery', 'table', 'form'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	form.render();
	//查询事件
	var active = {
		reload: function() {
			// 执行重载
			table.reload('productInfo', {
				page: {
					curr: 1
					// 重新从第 1 页开始
				},
				where: {
					"productName": $.trim($("#productName").val()),
					"shopId":$("#shopId").val()
				}
			});
		}
	};

	$('.searchDiv .layui-btn').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});


});

function userDefinedToolHandle(jo, obj){
	var iframeObj = layui.$(window.frameElement).attr('name');
	var data = obj.data;
	var $=layui.$;
	var url=jo.attr('data-url');
  	var id=data.id;
	if(obj.event=="detail"){//流水
		var openw="1200px";
		var openh="680px"
		layer.open({
			type: 2,
			title: "库存流水",
			area: [openw, openh],
			fixed: false, //不固定
            offset:'10%',
			content: url+"?id="+id,
			btn: ['关闭']
		});
	}else if(obj.event=="chageShop"){
		var openw="1200px";
		var openh="680px"
		layer.open({
			type: 2,
			title: "库存调拨",
			area: [openw, openh],
			fixed: false, //不固定
            offset:'10%',
			content: url+"?id="+id,
			btn: ['确定', '关闭'],
			yes: function(index, layero){
				var body = layer.getChildFrame('body', index); //得到iframe页的body内容
				var relationShop = body.find("#relationShop").val();
				var relationShopName = body.find("#relationShop option:selected").text();
				var shopName=body.find("#shopName").val();
				var shopId=body.find("#shopId").val();
				if(!relationShop){
					layer.msg("请先选择调入门店", {icon : 2});
					return;
				}
				//校验要调入的门店是否包含要调拨的产品
				var productId = body.find("#id").val();
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var grdData=iframeWin.getData();
				if(grdData.length<1){
					layer.msg("调拨数量为空", {icon : 2});
					return;
				}
				var result=checkExist(productId,relationShop);
				var objectData=new Object();
				objectData.grdData=JSON.stringify(grdData);//调拨数据
				objectData.relationShop=relationShop;//调入门店
				objectData.relationShopName=relationShopName;
				objectData.shopName=shopName;
				objectData.shopId=shopId;
				objectData.productId=productId;//调出产品
				var ret;
				if(result.retCode=='success'){//直接调入产品
					objectData.relationProductId=result.retDesc;
					ret=saveAllocationInfo(objectData);
					if(ret.retCode=='success'){
						layer.close(index);
						layer.msg(ret.retMsg, {icon : 1});
						layui.table.reload('productInfo');
					}else{
						layer.msg(ret.retMsg, {icon : 2});
					}
				}else{//提示没有同名的产品
					var openw1="600px";
					var openh1="500px";
					layer.open({
						type: 2,
						title: result.retMsg,
						area: [openw1, openh1],
						fixed: false, //不固定
						offset:'10%',
						content: "product/relationProduct.do?productId="+productId+"&relationShop="+relationShop,
						btn: ['确定', '关闭'],
						yes: function(index1, layero1){
							var body1 = layer.getChildFrame('body', index1); //得到iframe页的body内容
							var div = layer.getChildFrame('.layui-show', index1);
							var divId=div.attr("id");
							objectData.kind=divId;//调出产品
							if(divId=="fitProduct"){//匹配调入方产品
								var relationProductId = body1.find("#productId").val();
								if(!relationProductId){
									layer.msg("请选择匹配调入方产品", {icon : 2});
								}
								objectData.relationProductId=relationProductId;
							}
							ret=saveAllocationInfo(objectData);
							if(ret.retCode=='success'){
								layer.close(index1);
								layer.close(index);
								layer.msg(ret.retMsg, {icon : 1});
								layui.table.reload('productInfo');
							}else{
								layer.msg(result.retMsg, {icon : 2});
							}
						}
					});
				}				
			}
		});
	}
	
	}
	
/**
 * 校验产品在门店里面是否包含同名的产品
 */
function checkExist(productId,relationShop){
	var $=layui.$;
	var status;
	$.ajax({
		type : "POST",
		url : "product/checkExist.do",
		data : {"id":productId,"shopId":relationShop},
		dataType : "json",
		async:false,
		success : function(result) {
			status=result;
		}
	});
	return status;
}
	
	
function saveAllocationInfo(data){
	var $=layui.$;
	var status;
	$.ajax({
		type : "POST",
		url : "product/saveAllocationInfo.do",
		data : data,
		dataType : "json",
		async:false,
		success : function(result) {
			status=result;
		}
	});
	return status;
}

</script>

