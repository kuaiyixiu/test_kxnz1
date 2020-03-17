var orderFrmId = 'orderFrm';
var orderServeList = new Array();
var orderProductList = new Array();
var orderPayList = new Array();
var orderMeal = {};

function addOrdersServe(orderId,custId){  //添加服务
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		var option = {
				type: 2,
				title: '添加服务',
				area: ['1000px', '380px'],
				fixed: false, //不固定
				content:  ['orders/addOrdersServe.do?custId='+custId, 'no'],
				btn: ['确定', '关闭'],
				yes: function(index, layero){
					var addOrdersServeWin = top[orderFrmId]['layui-layer-iframe' + index];
					ajaxsaveOrdersServe(orderId,addOrdersServeWin.serverList,index);
				 }
			};
		var index = layer.open(option);
	});
}

function addOrdersProduct(orderId,custId){
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		var option = {
				type: 2,
				title: '添加产品',
				area: ['1000px', '380px'],
				fixed: false, //不固定
				content:  ['orders/addOrdersProduct.do?custId='+custId, 'no'],
				btn: ['确定', '关闭'],
				yes: function(index, layero){
					var addOrdersProductWin = top[orderFrmId]['layui-layer-iframe' + index];
					ajaxsaveOrdersProduct(orderId,addOrdersProductWin.productList,index);
				 }
			};
		layer.open(option);
	});
}

function addOrdersPay(orderId){
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		var option = {
				type: 2,
				title: '添加付款方式',
				area: ['600px', '380px'],
				fixed: false, //不固定
				content:  ['orders/addOrdersPay.do', 'no'],
				btn: ['确定', '关闭'],
				yes: function(index, layero){
					var body = layer.getChildFrame('body', index); //得到iframe页的body内容
					if (body.find("#payId").val() == ''){
						layer.msg('请选择付款方式');
						return false;
					}else if (!checkPositiveFloat(body.find("#payAmount").val())){
						layer.msg('请输入付款金额');
						return false;
					}

					var ordersPay = {};
					ordersPay.payId = body.find("#payId").val();
					ordersPay.payName = body.find("#payId").find("option:selected").text();
					ordersPay.payAmount =  body.find("#payAmount").val();
					ordersPay.remark = body.find("#remark").val();
					ordersPay.shopId = body.find("#shopId").val();
					ordersPay.addTime = new Date();
					ordersPay.canEdit = body.find("#canEdit").val();
					var tmpPayList = new Array();
					tmpPayList.push(ordersPay);
					ajaxsaveOrdersPay(orderId,tmpPayList,index);
				 },success: function(layero, index){
					 var $ = layui.jquery;
					var orderAmt  = 0;
					var payedAmt = 0;
					$(orderServeList).each(function(i,item){
						orderAmt += parseFloat(item.price);
					});	
					$(orderProductList).each(function(i,item){
						orderAmt += parseFloat(item.price) * parseFloat(item.quantity);
					});
					
					$(orderPayList).each(function(i,item){
						payedAmt += parseFloat(item.payAmount); 
					});
					var body = layer.getChildFrame('body', index); //得到iframe页的body内容
					if(orderAmt - payedAmt > 0){
						body.find("#payAmount").val((orderAmt - payedAmt).toFixed(2));
					}else{
						body.find("#payAmount").val(0);
					}
				  }
			};
		layer.open(option);
	});
}

function updateOrdersServe(id,iidno){
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		var option = {
				type: 2,
				title: '修改服务',
				area: ['600px', '450px'],
				fixed: false, //不固定
				content: 'orders/updateOrdersServe/'+id+'.do',
				btn: ['确定', '关闭'],
				yes: function(index, layero){
					var body = layer.getChildFrame('body', index); //得到iframe页的body内容
					var os = orderServeList[iidno];
					var newOs = {};
					for( var key in os ){
						newOs[key]=os[key];
					}
					
					newOs.price = body.find("#price").val();
					newOs.remark = body.find("#remark").val();
					newOs.serveUser =  body.find("#serveUser").val();
					newOs.sellUser = body.find("#sellUser").val();
					newOs.serveRoyal = body.find("#serveRoyal").val();
					newOs.sellRoyal = body.find("#sellRoyal").val();

					ajaxupdateOrdersServe(newOs,iidno,index);
				 }
			};
		layer.open(option);
	});
}
function updateOrdersProduct(id,iidno){
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		var option = {
				type: 2,
				title: '修改产品',
				area: ['600px', '450px'],
				fixed: false, //不固定
				content: 'orders/updateOrdersProduct/'+id+'.do',
				btn: ['确定', '关闭'],
				yes: function(index, layero){
					var body = layer.getChildFrame('body', index); //得到iframe页的body内容
					var os = orderProductList[iidno];
					var newOs = {};
					for( var key in os ){
						newOs[key]=os[key];
					}

					newOs.price = body.find("#price").val();
					newOs.quantity = body.find("#quantity").val();
					newOs.remark = body.find("#remark").val();
					newOs.productUser = body.find("#productUser").val();
					newOs.productRoyal = body.find("#productRoyal").val();
					
					ajaxupdateOrdersProduct (newOs,iidno,index);
				 }
			};
		 layer.open(option);
	});
}


function updateOrdersPay(id,iidno,orderId){
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		var option = {
				type: 2,
				title: '修改产品',
				area: ['600px', '450px'],
				fixed: false, //不固定
				content: 'orders/updateOrdersPay/'+id+'.do',
				btn: ['确定', '关闭'],
				yes: function(index, layero){
					var body = layer.getChildFrame('body', index); //得到iframe页的body内容
					if (body.find("#payId").val() == ''){
						layer.msg('请选择付款方式');
						return false;
					}else if (!checkPositiveFloat(body.find("#payAmount").val())){
						layer.msg('请输入付款金额');
						return false;
					}
					var ordersPay = orderPayList[iidno];
					var newOs = {};
					for( var key in ordersPay ){
						newOs[key]=ordersPay[key];
					}
					
					newOs.payId = body.find("#payId").val();
			//		newOs.payName = body.find("#payId").find("option:selected").text();
					newOs.payAmount =  body.find("#payAmount").val();
					newOs.remark = body.find("#remark").val();
					newOs.shopId = body.find("#shopId").val();
					newOs.canEdit = body.find("#canEdit").val();
					newOs.addTime = "";
					
					ajaxupdateOrdersPay(newOs,iidno,index);
					
				 }
			};
		 layer.open(option);
	});
}

function ajaxsaveOrdersServe(orderId,needSaveList,frmIndex){
	var param =	"?orderId="+orderId;
	var $ = layui.jquery;
	layui.jquery.ajax({
		type : "POST",
		url : "orders/ajaxsaveOrdersServe.do"+param,
		dataType : "json",
		contentType:"application/json",
		data : JSON.stringify(needSaveList),
		success : function(data) {
			if (data.retCode == 'success'){
				var ids =  data.retMsg.split(",");
				$(needSaveList).each(function(index,item){
					item.id = ids[index];
				});
				orderServeList = orderServeList.concat(needSaveList);
				renderServeTpl();
				if (!!frmIndex){
					layer.close(frmIndex);
				}
				
			}else{
				layer.msg(data.retMsg);
			}
		}
	});
}



function ajaxsaveOrdersProduct(orderId,needSaveList,frmIndex){
	var param =	"?orderId="+orderId;
	var $ = layui.jquery;
	layui.jquery.ajax({
		type : "POST",
		url : "orders/ajaxsaveOrdersProduct.do"+param,
		dataType : "json",
		contentType:"application/json",
		data : JSON.stringify(needSaveList),
		success : function(data) {
			if (data.retCode == 'success'){
				var ids =  data.retMsg.split(",");
				$(needSaveList).each(function(index,item){
					item.id = ids[index];
				});
				orderProductList = orderProductList.concat(needSaveList);
				renderProductTpl();	
				if (!!frmIndex){
					layer.close(frmIndex);
				}
			}else{
				layer.msg(data.retMsg);
			}
		}
	});
}



function ajaxsaveOrdersPay(orderId,tmpPayList,frmIndex){
	var $ = layui.jquery;
	layui.jquery.ajax({
		type : "POST",
		url : "orders/ajaxsaveOrdersPay.do?orderId="+orderId,
		dataType : "json",
		contentType:"application/json",
		data : JSON.stringify(tmpPayList),
		success : function(data) {
			if (data.retCode == "success"){
				var ids =  data.retMsg.split(",");
				$(tmpPayList).each(function(index,item){
					item.id = ids[index];
				});
				orderPayList = orderPayList.concat(tmpPayList);
				renderPayTpl();
				if (!!frmIndex){
					layer.close(frmIndex);
				}
			}else{
				 layer.msg(data.retMsg, {icon: 2});
			}
			
		}
	});
}


function ajaxupdateOrdersServe(newOs,iidno,frmIndex){
	var $ = layui.jquery;
	layui.jquery.ajax({
		type : "POST",
		url : "orders/ajaxupdateOrdersServe.do",
		dataType : "json",
		contentType:"application/json",
		data : JSON.stringify(newOs),
		success : function(data) {
			if (data.retCode == 'success'){
				var os = orderServeList[iidno];
				os.price = newOs.price;
				os.remark = newOs.remark;
				os.serveUser = newOs.serveUser;
				os.sellUser = newOs.sellUser;
				os.serveRoyal = newOs.serveRoyal;
				os.sellRoyal = newOs.sellRoyal;
				
				renderServeTpl();	
				layer.close(frmIndex);
			}else{
				layer.msg(data.retMsg, {icon: 2});
			}
		}
	});
}


function ajaxupdateOrdersProduct(newOs,iidno,frmIndex){
	var $ = layui.jquery;
	layui.jquery.ajax({
		type : "POST",
		url : "orders/ajaxupdateOrdersProduct.do",
		dataType : "json",
		contentType:"application/json",
		data : JSON.stringify(newOs),
		success : function(data) {
			if (data.retCode == 'success'){
				var os = orderProductList[iidno];
				os.price = newOs.price;
				os.quantity = newOs.quantity;
				os.remark = newOs.remark;
				os.productUser = newOs.productUser;
				os.productRoyal = newOs.productRoyal;
				
				renderProductTpl();	
				layer.close(frmIndex);
			}else{
				layer.msg(data.retMsg, {icon: 2});
			}
		}
	});
}

function ajaxupdateOrdersPay(newPay,iidno,frmIndex){
	var $ = layui.jquery;
	layui.jquery.ajax({
		type : "POST",
		url : "orders/ajaxupdateOrdersPay.do",
		dataType : "json",
		contentType:"application/json",
		data : JSON.stringify(newPay),
		success : function(data) {
			if (data.retCode == 'success'){
				var os = orderPayList[iidno];   
				
				os.payId = newPay.payId;
				os.payName = newPay.payName;
				os.payAmount =  newPay.payAmount;
				os.remark = newPay.remark;
				os.shopId = newPay.shopId
				os.canEdit = newPay.canEdit;
					
				renderPayTpl();	
				layer.close(frmIndex);
			}else{
				layer.msg(data.retMsg, {icon: 2});
			}
		}
	});
}



function delServe(index){
	layer.confirm('确认删除？', function(i){
		 if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
			 setYesBtnDisable("disabled");
			 layer.close(i);
			 var os = orderServeList[index];
			 var $ = layui.jquery;
			 layui.jquery.ajax({
				 type : "POST",
				 url : "orders/delOrdersServe/"+os.id+".do",
				 dataType : "json",
				 contentType:"application/json",
				 success : function(data) {
					 if (data.retCode == 'success'){
						 orderServeList.splice(index,1);
						 renderServeTpl();
					 }else{
						 layer.msg(data.retMsg, {icon: 2});
					 }
					 
					 
				 }
			 });
		 }
	});
}

function delProduct(index){
	layer.confirm('确认删除？', function(i){
		 if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
			 setYesBtnDisable("disabled");
			 layer.close(i);
			 var op = orderProductList[index];
			 var $ = layui.jquery;
			 layui.jquery.ajax({
				 type : "POST",
				 url : "orders/delOrdersProduct/"+op.id+".do",
				 dataType : "json",
				 contentType:"application/json",
				 success : function(data) {
					 if (data.retCode == 'success'){
						 orderProductList.splice(index,1);
						 renderProductTpl();
					 }else{
						 layer.msg(data.retMsg, {icon: 2});
					 }
				 }
			 });
		 }
		
		
	});
}


function delPay(index){
	 layer.confirm('确认删除？', function(i){
		 if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
			 setYesBtnDisable("disabled");
			 layer.close(i);
			 var op = orderPayList[index];
			 var $ = layui.jquery;
			 layui.jquery.ajax({
				 type : "POST",
				 url : "orders/delOrdersPay/"+op.id+".do",
				 dataType : "json",
				 contentType:"application/json",
				 success : function(data) {
					 if (data.retCode == 'success'){
						 orderPayList.splice(index,1);
						 renderPayTpl();
						 
						 var jo =  JSON.parse(data.retMsg);
						 layui.jquery(".choose-coupon").text("代金券 x "+jo.couponCount);
						 layui.jquery(".choose-cardset").text("优惠券 x "+jo.cardRecordCount);
						 if(jo.couponCount > 0){
							 layui.jquery(".choose-coupon").removeAttr("disabled");	
						 }else{
							 layui.jquery(".choose-coupon").attr("disabled","disabled");	
						 }
						 if(jo.cardRecordCount > 0){
							 layui.jquery(".choose-cardset").removeAttr("disabled");	
						 }else{
							 layui.jquery(".choose-cardset").attr("disabled","disabled");	
						 }
					 }else{
						 layer.msg(data.retMsg, {icon: 2});
					 }
					 
				 }
			 });

		 }
	 });
}


//渲染服务模版
function renderServeTpl(){
	var orderServeSection = document.getElementById('orderServeSection');
	var orderServeTpl = orderServeDemo.innerHTML;
	layui.laytpl(orderServeTpl).render(orderServeList, function(html){
		orderServeSection.innerHTML =  html;
	});	
	calcOrderAmt();
}


//渲染产品模版
function renderProductTpl(){
	var orderProductSection = document.getElementById('orderProductSection');
	var orderProductTpl = orderProductDemo.innerHTML;
	layui.laytpl(orderProductTpl).render(orderProductList, function(html){
		orderProductSection.innerHTML =  html;
	});	
	calcOrderAmt();
}


//渲染支付模版
function renderPayTpl(){
	var orderPaySection = document.getElementById('orderPaySection');
	var orderPayTpl = orderPayDemo.innerHTML;
	layui.laytpl(orderPayTpl).render(orderPayList, function(html){
		orderPaySection.innerHTML =  html;
	});	
	calcOrderAmt();
}

/**
 * 计算订单金额 orderProductList
 */
function calcOrderAmt(){
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		var $ = layui.jquery;
		var orderAmt  = 0;
		var payedAmt = 0;
		$(orderServeList).each(function(index,item){
			orderAmt += parseFloat(item.price);
		});	
		$(orderProductList).each(function(index,item){
			orderAmt += parseFloat(item.price) * parseFloat(item.quantity);
		});
		
		$(orderPayList).each(function(index,item){
			payedAmt += parseFloat(item.payAmount); 
		});
		
		var tip = "应付总额："+orderAmt;
		if (orderAmt > payedAmt){//还需支付：￥30.00
			tip += "，还需支付：￥"+(orderAmt - payedAmt)+"，可挂账";
			$("#debt").show();
			$("#pay").show();
		}else if (orderAmt == payedAmt){
			tip += "，已完成支付，可入账";
			$("#debt").hide();
			$("#pay").show();
		}else if (orderAmt < payedAmt){
			tip += "，当前已超出应付总额，请修改多余支付";
			$("#debt").hide();
			$("#pay").hide();
		}
		$("#orderAmt-i").text(tip);
	});
}


layui.use(['laydate', 'jquery', 'laytpl', 'form', 'element'], function() {
	var $ = layui.jquery;
	layui.form.on('submit(submit)', function(data) {
		data.field.operateType = 2;//已提交 待施工
		updateOrder(data);
		return false;
	});
	layui.form.on('submit(disDebt)', function(data) {
		layer.confirm('解除挂账后会使订单变为可编辑状态，是否继续？', function(index){
			 if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
				 setYesBtnDisable("disabled");
				 layer.close(index);
				 data.field.operateType = 7;//解除挂账/解除入账
				 updateOrder(data);
			 }
		});
		return false;
	});
	layui.form.on('submit(disPay)', function(data) {
		layer.confirm('反入账会使已入账订单回退到入账之前的状态，是否继续？', function(index){
			 if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
				 setYesBtnDisable("disabled");
				 layer.close(index);
				 data.field.operateType = 7;//解除挂账/解除入账
				 updateOrder(data);
			 }
		});
		return false;
	});
	layui.form.on('submit(done)', function(data) {
		data.field.operateType = 4;//施工完成
		updateOrder(data);
		return false;
	});
	layui.form.on('submit(pay)', function(data) {
		var orderAmt  = 0;
		var payedAmt = 0;
		$(orderServeList).each(function(index,item){
			orderAmt += parseFloat(item.price);
		});	
		$(orderProductList).each(function(index,item){
			orderAmt += parseFloat(item.price) * parseFloat(item.quantity);
		});
		
		$(orderPayList).each(function(index,item){
			payedAmt += parseFloat(item.payAmount); 
		});
		if (orderAmt > payedAmt){//还需支付：￥30.00
			layer.confirm('支付金额不足，是否【挂账】?', function(index){
				 if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
					 setYesBtnDisable("disabled");
					 layer.close(index);
					 data.field.operateType = 6;//挂账
					 updateOrder(data);
				 }
			});
			
		}else if (orderAmt < payedAmt){
			layer.msg('当前已超出应付总额，请修改多余支付');
		}else{
			data.field.operateType = 5;//入账
			updateOrder(data);
		}
		return false;
	});
	layui.form.on('submit(debt)', function(data) {
		var orderAmt  = 0;
		var payedAmt = 0;
		$(orderServeList).each(function(index,item){
			orderAmt += parseFloat(item.price);
		});	
		$(orderProductList).each(function(index,item){
			orderAmt += parseFloat(item.price) * parseFloat(item.quantity);
		});
		
		$(orderPayList).each(function(index,item){
			payedAmt += parseFloat(item.payAmount); 
		});
		
		if (orderAmt > payedAmt){//还需支付：￥30.00
			data.field.operateType = 6;//挂账
			updateOrder(data);
		}else if (orderAmt <= payedAmt){
			layer.msg('订单金额等于支付金额，无需【挂账】');
		}
		return false;
	});
});

function updateOrder(data){
	var $ = layui.jquery;
	$.ajax({
		type: "POST",
		url: "orders/submitOrders.do",
		data: data.field,
		dataType: "json",
		success: function(result) {
			if (result.retCode == 'success') {
				layui.use(['element','table'], function(){
					var element = parent.layui.element;
					parent.layer.msg(result.retMsg, {
						icon: 1
					});
					element.tabDelete('tab', window.name);
				});
				
			} else {
				layer.msg(result.retMsg, {
					icon: 2
				});
			}
		}
	});
}