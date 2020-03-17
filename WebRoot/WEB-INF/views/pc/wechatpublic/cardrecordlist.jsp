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
<link rel="stylesheet" type="text/css" href="lib/admin/css/admin.css" />
<%@ include file="../base.jsp"%>
</head>
<body>
	<div class="page-content-wrap">
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<div class="layui-container">
						<div class="layui-row">
							<div class="layui-col-md3">
								<div class="layui-form-item">
									<label class="layui-form-label">会员信息</label>
									<div class="layui-input-block">
										<input type="text" name="keyword" id="keyword"
											placeholder="会员名称/会员手机号" autocomplete="off"
											class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-col-md3">
								<div class="layui-form-item">
									<label class="layui-form-label">卡卷名称</label>
									<div class="layui-input-block">
										<input type="text" name="wechatCardName" id="wechatCardName"
											placeholder="请出入卡卷名称" autocomplete="off"
											class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-col-md3">
								<div class="layui-form-item">
									<label class="layui-form-label">状态</label>
									<div class="layui-input-block">
										<select name="state" id="state">
										<option value="0">未使用</option>
										<option value="1">已使用</option>
										<option value="2">已作废</option>
										</select>
									</div>
								</div>
							</div>
							<div class="layui-col-md3" align="center">
								<button class="layui-btn layui-btn-normal" data-type="reload"
									lay-filter="searchBtn" type="button">搜索</button>
							</div>
						</div>
					</div>
				</fieldset>

			</div>
		</form>
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="cardRecordInfo" lay-filter="tableInfo"></table>
			<%--			<script type="text/html" id="toolbarTop">--%>
			<%--            <div class="layui-btn-container">--%>
			<%--            <button class="layui-btn layui-btn-sm" lay-event="voidDataBatch"><i class="layui-icon">&#x1006;</i>作废</button>--%>
			<%--            </div>--%>
			<%--            </script>--%>
			<script type="text/html" id="barLine">
            {{# if (d.state ==0){ }}
 	           <a class="layui-btn layui-btn-xs" lay-event="voidData">作废</a>
            {{#  } else if(d.state ==1){ }}
               <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="userecord">使用记录</a>
            {{#  }else if(d.state ==2){ }}
               <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="voidDetail">作废明细</a>
            {{#  } }}
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
		//初始化网格
		var tableIns = table.render({
			elem : '#cardRecordInfo',
			url : "wechatcardrecord/getList.do",
			page : true,
			method : 'post',
			limit : 10,
			limits : [ 10, 20, 30, 40, 50 ],
			where : {
				"state": $.trim($("#state").val()),
				"keyword":$.trim($("#keyword").val()),
				"wechatCardName":$.trim($("#wechatCardName").val())
				},
			//toolbar : '#toolbarTop',
			defaultToolbar : [ 'print', 'exports' ],
			
			done : function(res, curr, count) {
			},
			cols : [ [ {
				field : 'id',
				hide : 'true',
				title : 'ID',
				align : 'center',
			}, {
				field : 'wechatCardName',
				align : 'center',
				title : '卷名称'
			}, {
				field : 'wechatCardValue',
				align : 'center',
				title : '卷面值',
				templet : function(d) {
					return parseFloat(d.wechatCardValue).toFixed(2);
				}
			}, {
				field : 'wechatName',
				align : 'center',
				title : '微信昵称'
			}, {
				field : 'custName',
				align : 'center',
				title : '绑定客户'
			}, {
				field : 'phone',
				align : 'center',
				title : '手机号'
			}, {
				field : 'endDate',
				align : 'center',
				title : '有效期'
			}, {
				field : 'getDate',
				align : 'center',
				title : '领取日期'
			}, {
				field : 'remark',
				align : 'center',
				title : '备注'
			}, {
				field : 'state',
				align : 'center',
				title : '状态',
				templet : function(d) {
					if (d.state == 0)
						return "未使用";
					else if (d.state == 1)
						return "已使用";
					else if (d.state == 2)
						return "已作废";
				}
			}, {
				field : 'opt',
				align : 'center',
				title : '操作',
				toolbar : '#barLine'
			} ] ]
		});

		//查询事件
		var active = {
			reload : function() {
				// 执行重载
				tableIns.reload({
					page : {
						curr : 1
					// 重新从第 1 页开始
					},
					where : {
						"state": $.trim($("#state").val()),
						"keyword":$.trim($("#keyword").val()),
						"wechatCardName":$.trim($("#wechatCardName").val())
					}
				});
			}
		};

		$('.searchDiv .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
	}
/*
	function userDefinedToolBarHandle(target, obj) {
		var $ = layui.jquery;
		var table = layui.table;
		switch (obj.event) {
		case 'voidDataBatch':
			var openw = "600px";
			var openh = "250px";
			var post_flag = false;
			var index = layer
					.open({
						type : 1,
						title : "作废",
						area : [ openw, openh ],
						fixed : false, //不固定
						content : '<div style="width: 90%;margin-top: 5%;"><form class="layui-form"><div class="layui-form-item layui-form-text"><label class="layui-form-label"><font color="red">*</font>作废原因</label><div class="layui-input-block"><textarea name="voidRemark" id="voidRemark" placeholder="请输入作废原因" class="layui-textarea"></textarea></div></div></form></div>',
						maxmin : true,
						btn : [ '确定', '关闭' ],
						yes : function(index, layero) {
							//如果正在提交则直接返回，停止执行
							if (post_flag)
								return;
							//标记当前状态为正在提交状态
							post_flag = true;
							var checkStatus = table
									.checkStatus('cardRecordInfo');
							var selectData = checkStatus.data;
							if (selectData.length < 1) {
								layer.msg("请选择要作废的卡卷", {
									icon : 2
								});
								post_flag = false;
							}
							var idsArr = new Array();
							for ( var i = 0; i < selectData.length; i++) {
								var id = selectData[i].id;
								idsArr.push(id);
							}
							var voidRemark = $("#voidRemark").val();
							if ($.trim(voidRemark) == "") {
								layer.msg("作废说明不能为空", {
									icon : 2
								});
								post_flag = false;
								return;
							}
							var data = {
								"ids" : idsArr.toString(),
								"voidRemark" : voidRemark
							};
							var result = destroyInfo(data);
							if (result.retCode == 'success') {
								layer.close(index);
								layui.table.reload('cardRecordInfo');
								layer.msg(result.retMsg, {
									icon : 1
								});
							} else {
								layer.msg(result.retMsg, {
									icon : 2
								});
							}
							post_flag = false;
						}
					});
			break;
		}
	}
	*/
	/**
	 * 保存卡卷设定
	 */
	function destroyInfo(data) {
		var $ = layui.$;
		var status;
		$.ajax({
			type : "POST",
			url : "wechatcardrecord/destroyInfo.do",
			data : data,
			dataType : "json",
			async : false,
			success : function(result) {
				status = result;
			}
		});
		return status;
	}

	function userDefinedToolHandle(target, obj) {
		var $ = layui.jquery;
		var data = obj.data;
		var id = data.id;
		switch (obj.event) {
		case 'voidData':
			//设置一个对象来控制是否进入AJAX过程
			var post_flag = false;
			var openw = "600px";
			var openh = "250px";
			var index = layer.open({
						type : 1,
						title : "作废",
						area : [ openw, openh ],
						fixed : false, //不固定
						content : '<div style="width: 90%;margin-top: 5%;"><form class="layui-form"><div class="layui-form-item layui-form-text"><label class="layui-form-label"><font color="red">*</font>作废原因</label><div class="layui-input-block"><textarea name="voidRemark" id="voidRemark" placeholder="请输入作废原因" class="layui-textarea"></textarea></div></div></form></div>',
						maxmin : true,
						btn : [ '确定', '关闭' ],
						yes : function(index, layero) {
							//如果正在提交则直接返回，停止执行
							if (post_flag)
								return;
							//标记当前状态为正在提交状态
							post_flag = true;
							var voidRemark = $("#voidRemark").val();
							if ($.trim(voidRemark) == "") {
								layer.msg("作废原因不能为空", {
									icon : 2
								});
								post_flag = false;
								return;
							}
							var data = {
								"voidRemark" : voidRemark,
								"ids" : id
							};
							var result = destroyInfo(data);
							if (result.retCode == 'success') {
								layer.close(index);
								layui.table.reload('cardRecordInfo');
								layer.msg(result.retMsg, {
									icon : 1
								});
							} else {
								layer.msg(result.retMsg, {
									icon : 2
								});
							}
							post_flag = false;
						}
					});
			//layer.full(index);
			break;
		case 'userecord'://使用记录
			var openw = "500px";
			var openh = "400px";
			var index = layer.open({
						type : 2,
						title : "使用记录",
						area : [ openw, openh ],
						fixed : false, //不固定
						content : 'wechatcardrecord/userrecord.do?id=' + id,
						maxmin : true,
						btn : ['关闭' ]
					});
			break;
		case 'voidDetail'://使用记录
			var openw = "600px";
			var openh = "250px";
			var index = layer.open({
						type : 2,
						title : "作废明细",
						area : [ openw, openh ],
						fixed : false, //不固定
						content : 'wechatcardrecord/voiddetail.do?id=' + id,
						maxmin : true,
						btn : ['关闭' ]
					});
			break;
		}
	}
</script>

