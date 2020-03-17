<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
.x-table{margin: 0px 20px;}
.x-tr-left {padding-top: 10px; padding-bottom: 10px;}
.x-tr-right {padding-top: 10px;}
.section-title{margin-left: 5px;}
.selectUser{display: block!important;height: 34px; line-height: 34px;width: 100%;}
.td-user{padding-left: 3px!important;padding-right: 3px!important;}
</style>
<script type="text/javascript">
	
</script>
</head>
<body>
	<form class="layui-form"  style="width: 95%;height: 98%;margin-top: 1%;">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">选择服务</label>
				<div class="layui-input-block" style="width: 400px;">
					<select name="serviceId"  lay-search  lay-filter="chooseServe" id="serviceId">
				        <option value="">请输入名称或者名称简写</option>
			      </select>
				</div>
			</div>
		</div>
		
		<div class="layui-row section" style="">
			<div class="layui-col-xs6 section-title">已选择服务： </div>
		</div>
		<table class="layui-table">
		  <colgroup>
		    <col width="10%">
		    <col width="30%">
		    <col width="15%">
		    <col width="9%">
		    <col width="9%">
		    <col width="9%">
		    <col width="9%">
		    <col width="9%">
		    <col>
		  </colgroup>
		  <thead>
		    <tr>
		      <th>序号</th>
		      <th>服务名称</th>
		      <th>单价</th>
		      <th>施工人员</th>
		      <th>提成价格</th>
		      <th>销售人员</th>
		      <th>提成价格</th>
		      <th>操作</th>
		    </tr> 
		  </thead>
		  <tbody  id="serveSection">
		  </tbody>
		</table>
		
		<%--<div class="layui-form-item bottom-btn">
			<div align="center">
				<button class="layui-btn" lay-submit lay-filter="formSubmitBtn">确定</button>
				<button class="layui-btn closeBtn" id="closeBtn">关闭</button>
			</div>
		</div>
	--%></form>
</body>
<script type="text/javascript">
var serverList = new Array();
layui.use(['form','jquery','laytpl', 'form', 'element'], function(){
	var $ = layui.jquery;
	var serviceId  = $("#serviceId");
	var dataList = new Array();
	$.ajax({
		type : "POST",
		url : "serve/getServeCustomPriceList.do",
		data: {'custId':'${custId}'},
		dataType : "json",
		success : function(data) {
			dataList = data;
			$(dataList).each(function(){
				serviceId.append("<option value='"+this.id+"'>"+this.serveName+"</option>");
			});
			layui.form.render('select'); 
		}
	});
	
	
	layui.form.on('select(chooseServe)', function(data){
		if (data.elem.selectedIndex >0){
			var serveData = dataList[data.elem.selectedIndex - 1];
			var ordersPay = {};
			ordersPay.price = serveData.price;
			ordersPay.serveName = serveData.serveName;
			ordersPay.shopId = serveData.shopId;
			ordersPay.serveId = serveData.id;
			ordersPay.quantity = 1; //默认数量 1
			ordersPay.serveStatus = 1; //待施工 1
			ordersPay.serveRoyal = 0;
			ordersPay.sellRoyal = 0;
			ordersPay.isCust =  serveData.isCust; //1是会员价
			serverList.push(ordersPay);
			renderServeTpl();
		   var index = parent.layer.getFrameIndex(window.name);
			  parent.layer.iframeAuto(index)
		}
	});	
});

function delServe(index){
	serverList.splice(index,1);
	renderServeTpl();
}


//渲染模版
function renderServeTpl(){
	var getTpl = demo.innerHTML;
	var serveSection = document.getElementById('serveSection');
	layui.laytpl(getTpl).render(serverList, function(html){
		serveSection.innerHTML =  html;
	});
}

function chooseUser(type,obj,index){
	if (type == 1) {
		if (obj.selectedIndex > 0 ){
			serverList[index]['serveUser'] = obj.value;
			calcRoyal(serverList[index],'1');
		}else{
			serverList[index]['serveUser'] = '';
			serverList[index].serveRoyal = 0;
		}
	}else{
		if (obj.selectedIndex > 0 ){
			serverList[index]['sellUser'] = obj.value;
			calcRoyal(serverList[index],'2');
		}else{
			serverList[index]['sellUser'] = '';
			serverList[index].sellRoyal = 0;
		}
	}
	renderServeTpl();
}

function calcRoyal(serveData,kind){ //计算提成
	var royal;
	layui.jquery.ajax({
		type : "POST",
		async:false, 	
		dataType : "json",
		url : "royalty/getByRoyaltyId.do",
		data : {'royaltyId':serveData.serveId,'kind':kind},
		success : function(data) {
			if (kind == 1){
				serveData.serveRoyal = 0;
			}else{
				serveData.sellRoyal = 0;
			}
			if (!!data){
				royal = data;	
				if( royal.royaltyCount > 0 ){
					if (royal.royaltyType == 1){ //固定值
						if (kind == 1){ //施工服务提成
							serveData.serveRoyal = royal.royaltyCount;
						}else{ //产品服务提成
							serveData.sellRoyal =royal.royaltyCount; 
						}
					}else{ //百分比
						if (kind == 1){ //施工服务提成
							serveData.serveRoyal = royal.royaltyCount / 100 * serveData.price;
						}else{ //产品服务提成
							serveData.sellRoyal =royal.royaltyCount / 100 * serveData.price;
						}
					}
				}
			}
		}
	});
	return royal;	
}
</script>

<script id="demo" type="text/html">
{{#  layui.each(d, function(index, item){ }}
    <tr>
      <td>{{index+1 }}</td>
      <td>{{ item.serveName }}</td>
       <td>{{ item.price }}元
		{{#  if(item.isCust == 1){ }}
					<span class="layui-badge" title="会员价">会员价</span>
		{{#  } }} 
		</td>
      <td class="td-user"><select  class="selectUser" onchange="chooseUser(1,this,{{index}})">${serveUserStr} </select></td>
      <td>{{ item.serveRoyal }}元</td>
      <td class="td-user"><select  class="selectUser" onchange="chooseUser(2,this,{{index}})">${sellUserStr} </select></td>
      <td>{{ item.sellRoyal }}元</td>
      <td><a class="layui-btn layui-btn-xs  layui-btn-normal" onclick="delServe({{index}})">删除</a></td>
    </tr>
{{#  }); }}
</script>
</html>

