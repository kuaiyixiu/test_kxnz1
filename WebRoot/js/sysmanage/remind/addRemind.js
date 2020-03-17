layui.use(['jquery', 'laydate', 'table', 'form', 'element'], function() {
	var table = layui.table;
	var $ = layui.jquery;
	var form = layui.form;
	var laydate = layui.laydate
	laydate.render({
		elem: '#date', //指定元素
		value: formatDate1(new Date())
	});

	// 客户列表
	var userList = JSON.parse($("#userList").val());
	var tpl = usersTpl.innerHTML;
	var laytpl = layui.laytpl;
	laytpl(tpl).render(userList, function(html) {
		$("#userDiv").empty();
		$("#userDiv").append(html)
	});
	
	form.on('submit(formSubmitBtn)', function(data) {
		$.ajax({
			type : "POST",
			url : "remind/addRemind.do",
			data : data.field,
			dataType : "json",
			success : function(result) {
				if (result.retCode == 'success') {
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);//关闭当前页  
					parent.layui.table.reload('remindInfo');

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
	form.render();
});

