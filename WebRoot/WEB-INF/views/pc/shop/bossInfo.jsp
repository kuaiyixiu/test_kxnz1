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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>连途</title>
<%@ include file="../base.jsp"%>
<script type="text/javascript">
	function userDefinedLoadGrd($, table) {
		var tableIns = table.render({
			elem : '#bossInfo',
			url : "user/queryBossAccount.do",
			method : 'post',
			page : true,
			limit : 10,
			limits : [ 10, 20, 30, 40, 50 ],
			toolbar: '#toolbarTop',
			cols : [ [ {
				field : 'userName',
				align : 'center',
				title : '用户名'
			},{
				field : 'userRealname',
				align : 'center',
				title : '姓名'
			}, {
				field : 'userPhone',
				align : 'center',
				title : '手机号'
			}, 
			{
				field : 'shopName',
				align : 'center',
				title : '门店名'
			}, {
				field : 'opt',
				align : 'center',
				title : '操作',
				toolbar : '#barLine'
			} ] ],
			where : {
				"keyword": $("#keyword").val().trim(),
				"bossAccount":1
			}
		});
		
		//查询事件
		var active = {
			reload: function() {
				// 执行重载
				tableIns.reload({
					page: {
						curr: 1
						// 重新从第 1 页开始
					},
					where: {
						"keyword": $("#keyword").val().trim(),
						"bossAccount":1
					}
				});
			}
		};

		$('.searchDiv .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});

	}
	
	function userDefinedToolHandle($arg, obj) {
		var data = obj.data
		var $=layui.$;
		var id=data.id;
		switch (obj.event) {
			case 'editBossAccount':
		  		var openw="600px";
		  		var openh="250px";
				var index=layer.open({
					type: 2,
					title: "修改老板信息",
					area: [openw, openh],
					fixed: false, //不固定
					content: 'user/addBossAccount.do?id='+id,
					maxmin: true,
					btn: ['确定', '关闭'],
					yes: function(index, layero){
						if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
							setYesBtnDisable("disabled");
							var body = layer.getChildFrame('body', index); //得到iframe页的body内容
							var userPhone = body.find("#userPhone").val();
							var userRealname = body.find("#userRealname").val();
							if($.trim(userPhone) == ""){
								layer.msg("手机号码不能为空", {icon : 2});
								setYesBtnDisable("undisabled");
								return ;
							}
							if($.trim(userRealname) == ""){
								layer.msg("姓名不能为空", {icon : 2});
								setYesBtnDisable("undisabled");
								return ;
							}
							var data={"userPhone":userPhone,"userRealname":userRealname,"id":id};
							var result=saveBossAccountInfo(data);
							if(result.retCode=='success'){
								layer.close(index);
								layui.table.reload('bossInfo');
								layer.msg(result.retMsg, {icon : 1});
							}else{
								layer.msg(result.retMsg, {icon : 2});
							}
						}
					}
				});
				break;
			case 'showMore':
				window.location.href="shop/shopInfo.do?id="+id;
				break;
		}
	}
	
	function userDefinedToolBarHandle(target, obj){
		var $ = layui.jquery;
		switch (obj.event) {
			case 'addBossAccount':
		  		var openw="600px";
		  		var openh="250px";
				var index=layer.open({
					type: 2,
					title: "添加老板信息",
					area: [openw, openh],
					fixed: false, //不固定
					content: 'user/addBossAccount.do',
					maxmin: true,
					btn: ['确定', '关闭'],
					yes: function(index, layero){
						if(!layui.$(".layui-layer-btn0").hasClass("layui-btn-disabled")){
							setYesBtnDisable("disabled");
							var body = layer.getChildFrame('body', index); //得到iframe页的body内容
							var userPhone = body.find("#userPhone").val();
							var userRealname = body.find("#userRealname").val();
							if($.trim(userPhone) == ""){
								layer.msg("手机号码不能为空", {icon : 2});
								setYesBtnDisable("undisabled");
								return ;
							}
							if($.trim(userRealname) == ""){
								layer.msg("姓名不能为空", {icon : 2});
								setYesBtnDisable("undisabled");
								return ;
							}
							var data={"userPhone":userPhone,"userRealname":userRealname};
							var result=saveBossAccountInfo(data);
							if(result.retCode=='success'){
								layer.close(index);
								layui.table.reload('bossInfo');
								layer.msg(result.retMsg, {icon : 1});
							}else{
								layer.msg(result.retMsg, {icon : 2});
							}
						}
					}
				});
				break;
		}
	}
	
	function saveBossAccountInfo(data){
		var $=layui.$;
		var status;
		$.ajax({
			type : "POST",
			url : "user/saveBossAccountInfo.do",
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
</head>
<body>
	<div class="page-content-wrap">
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<div class="layui-fluid">
						<div class="layui-row">
							<div class="layui-col-md8">
								<div class="layui-form-item">
									<label class="layui-form-label">关键字</label>
									<div class="layui-input-block">
										<input type="text" id="keyword" placeholder="boss名/boss手机号/门店名"
											autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-col-md2" align="center">
								<button class="layui-btn layui-btn-normal" data-type="reload"
									lay-filter="searchBtn" type="button">搜索</button>
							</div>
						</div>
					</div>
				</fieldset>
			</div>
		</form>

		<div class="layui-form" id="table-list">

			<table class="layui-table" id="bossInfo" lay-filter="tableInfo"></table>
		</div>
	</div>
</body>
</html>
<script type="text/html" id="barLine">                     
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="showMore">门店列表</a>
	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="editBossAccount">修改</a>
</script>
<script type="text/html" id="toolbarTop">
	<div class="layui-btn-container">
		<button class="layui-btn layui-btn-sm kxnz-btn-default" lay-event="addBossAccount"><i class="layui-icon">&#xe608;</i>新增</button>
	</div>
</script>