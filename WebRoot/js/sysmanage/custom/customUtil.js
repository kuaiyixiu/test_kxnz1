/**
 * 得到有效时间范围
 */
function getValidDate(data){
	var createDate = formatDate1(data.createDate);
	var endDate = formatDate1(data.endDate);
	
	return createDate + " ~ " + endDate;
}


/**
 * 得到内容
 * @param row
 */
function getContent(row){
	var $ = layui.jquery,
		laytpl = layui.laytpl;
	
	var html = "";
	var list = row.customMealDts;
	if(!list){
		return html;
	}
	
	var pId = row.id;
	var product = "";
	var server = "";
	$.each(list, function(index, item){
		item.pId = pId;
		laytpl(contentTpl.innerHTML).render(item, function(tplHtml) {
			item.type == 1?product += tplHtml: server +=tplHtml;
		});
	});
	if(product!=""){
		product = "<d1><dt>产品</dt>"+product+"</d1>"; 
	}
	
	if(server!=""){
		server = "<d1><dt>服务</dt>"+server+"</d1>"; 
	}
	
	return product + server;
}


/**
 * 绘制总价格
 * @param res
 */
function drawTotalHtml(res){
	var $ = layui.jquery;
	var htmls = "总价格：";
	var total = $("#total").empty();
	var number = getTotalPrice(res.data);
	number = number?number:"0";
	htmls += number;
	$("#total").html(htmls);
}

/**
 * 得到总价格
 * @param data
 */
function getTotalPrice(data){
	var $ = layui.jquery;
	var total = 0;
	$.each(data, function(i, item){
		var customMealDts = item.customMealDts; 
		if(!customMealDts){
			return;
		}
		$.each(customMealDts, function(j, items){
			 total += Number(items.price) * Number(items.quantity);
		})
	});
	
	return total;
}