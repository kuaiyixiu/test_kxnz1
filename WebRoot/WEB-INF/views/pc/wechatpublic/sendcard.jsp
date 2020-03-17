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
<title>连途</title>
<%@ include file="../base.jsp"%>
<script type="text/javascript">
	//勾选的产品id集合
	var chooseAdids = new Array();
	//表格数据缓存
	var table_data = new Array();

	var checkAll = false;
	var tableIns;
	function userDefinedLoadGrd() {
		var $ = layui.$;
		var table = layui.table;
		// 会员类型
		var customTypesMap = formatCustomType();
		//初始化网格
		tableIns = layui.table.render({
			elem : '#custInfo',
			method : 'post',
			url : "cardset/queryCustoms.do",
			page : true,
			limit : 10,
			limits : [ 10, 20, 30, 40, 50 ],
			toolbar : '#toolbarTop',
			defaultToolbar : [],
			done : function(res, curr, count) {
				if (checkAll) {//全选
					for ( var i = 0; i < res.data.length; i++) {
						//这里才是真正的有效勾选
						res.data[i]["LAY_CHECKED"] = 'true';
						//找到对应数据改变勾选样式，呈现出选中效果
						var index = res.data[i]['LAY_TABLE_INDEX'];
						$(
								'.layui-table tr[data-index=' + index
										+ '] input[type="checkbox"]').prop(
								'checked', true);
						$(
								'.layui-table tr[data-index=' + index
										+ '] input[type="checkbox"]').next()
								.addClass('layui-form-checked');
					}
					return;
				}
				// 设置换页勾选之前的
				table_data = res.data;
				//循环所有数据，找出对应关系，设置checkbox选中状态
				for ( var i = 0; i < res.data.length; i++) {
					for ( var j = 0; j < chooseAdids.length; j++) {
						if (res.data[i].id == chooseAdids[j]) {
							//这里才是真正的有效勾选
							res.data[i]["LAY_CHECKED"] = 'true';
							//找到对应数据改变勾选样式，呈现出选中效果
							var index = res.data[i]['LAY_TABLE_INDEX'];
							$(
									'.layui-table tr[data-index=' + index
											+ '] input[type="checkbox"]').prop(
									'checked', true);
							$(
									'.layui-table tr[data-index=' + index
											+ '] input[type="checkbox"]')
									.next().addClass('layui-form-checked');
						}
					}
				}
			},
			cols : [ [ {
				type : 'checkbox',
				fixed : 'left',
				LAY_CHECKED : false
			}, {
				field : 'cardNo',
				align : 'center',
				title : '会员卡号'
			}, {
				field : 'custName',
				align : 'center',
				title : '会员名称'
			}, {
				field : 'cellphone',
				align : 'center',
				title : '手机号'
			}, {
				field : 'custType',
				align : 'center',
				title : '会员类型',
				templet : function(row) {
					return customTypesMap[row.custType];
				}
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
						"keyword" : $("#keyword").val().trim(),
						"custType" : $("#custType").val().trim()
					}
				});
			}
		};

		$('.searchDiv .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});

		//监听表格复选框选择
		table.on('checkbox(tableInfo)', function(obj) {
			if (obj.checked == true) {
				//选中
				if (obj.type == 'one') { //当选中一个时 obj.type默认是'one'，否则默认是'all'
					chooseAdids.push(obj.data.id);
				} else {
					for ( var i = 0; i < table_data.length; i++) {
						chooseAdids.push(table_data[i].id);
					}
				}
			} else {
				//取消选中
				if (obj.type == 'one') {
					for ( var i = 0; i < chooseAdids.length; i++) {
						if (chooseAdids[i] == obj.data.id) {
							//从下标 i 开始删除一个元素
							chooseAdids.splice(i, 1);
						}
					}
				} else {
					for ( var i = 0; i < chooseAdids.length; i++) {
						for ( var j = 0; j < table_data.length; j++) {
							if (chooseAdids[i] == table_data[j].id) {
								//从下标 i 开始删除一个元素
								chooseAdids.splice(i, 1);
							}
						}
					}
				}
			}

		})

	}

	/**
	 * 格式化会员类型
	 * {1:自助开卡类型}
	 */
	function formatCustomType() {
		var $ = layui.jquery;
		var customTypes = '${customTypes}';
		customTypes = JSON.parse(customTypes);

		var customTypesMap = {};
		$.each(customTypes, function(index, item) {
			customTypesMap[item.id] = item.typeName;
		})

		return customTypesMap;
	}

	function userDefinedToolBarHandle(target, obj) {
		var $ = layui.jquery;
		if (obj.event == "selectAll") {
			if (checkAll) {
				checkAll = false;
				tableIns.reload({});
				$("#selectAll").html("全选");
			} else {
				checkAll = true;
				tableIns.reload({});
				$("#selectAll").html("取消全选");
			}

		}

	}
	
	function changeAccount(){
		var $=layui.$;
		$("#numLabel").html($("#sumNum").val());
	}
</script>
</head>
<body>
	<div style="width: 90%;height: 98%;margin-top: 1%;margin-left: 2px;">
		<div class="layui-fluid">
			<div class="layui-row">
				<div class="layui-col-md3"
					style="background-image: url(image/wechat_card_notice.png);background-repeat:no-repeat; background-size:100% 100%;height: 667px;border:2px solid #eeeeee">
					<div
						style="width: 90%;height: 80%;margin-left: 5%;margin-top: 23%;">
						<div class="layui-row">
							<font size="3"><strong>卡卷领取通知</strong> </font>
						</div>
						<div class="layui-row" style="margin-top: 1%;">
							<font size="2" color="#999999"><fmt:formatDate value="${now }" pattern="yyyy-MM-dd"/></font>
						</div>
						<div class="layui-row" style="margin-top: 1%;">
							<font size="2">尊敬的先生/女士：</font>
						</div>
						<div class="layui-row" style="margin-top: 1%;">
							<font size="2">给您赠送<font id="numLabel">1</font>张：${cardSet.cardName }，有效期至<fmt:formatDate value="${cardSet.endDate }" pattern="yyyy-MM-dd"/>，欢迎进店消费使用【${shopName
								}】</font>
						</div>
						<div class="layui-row" style="margin-top: 2%;">
							<font size="2" color="#999999">可在【我的会员卡】-【优惠券】中查看</font>
						</div>
						<hr class="layui-bg-gray">
						<div class="layui-row" style="margin-top: 1%;">
							<font size="2">详情</font>
						</div>
					</div>
				</div>
				<div class="layui-col-md8 layui-col-md-offset1">
					<form class="layui-form">
						<div class="layui-form-item searchDiv">
							<fieldset>
								<legend>查询条件</legend>
								<div class="layui-container">
									<div class="layui-row">
										<div class="layui-col-md5">
											<div class="layui-form-item">
												<label class="layui-form-label">会员信息</label>
												<div class="layui-input-block">
													<input type="text" name="keyword" id="keyword"
														placeholder="会员卡号/会员名称/会员手机号" autocomplete="off"
														class="layui-input">
												</div>
											</div>
										</div>
										<div class="layui-col-md4">
											<div class="layui-form-item">
												<label class="layui-form-label">会员类型</label>
												<div class="layui-input-block">
													<select name="custType" id="custType">
														<option value="">全部</option>
														<c:forEach items="${customTypeList }" var="type">
															<option value="${type.id }">${type.typeName }</option>
														</c:forEach>
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
						<table class="layui-table" id="custInfo" lay-filter="tableInfo"></table>
						<script type="text/html" id="toolbarTop">
                        <div class="layui-btn-container">
                        <button class="layui-btn layui-btn-sm kxnz-btn-default" lay-event="selectAll" id="selectAll">全选</button>
                        </div>
                        </script>
					</div>
					<form class="layui-form">
						<div class="layui-form-item">
							<label class="layui-form-label">派发数量</label>
							<div class="layui-input-block">
								<input type="number" name="sumNum" id="sumNum" placeholder="请输入派发数量"
									autocomplete="off" class="layui-input" value="1" min="0"
									step="1"
									onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
									onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}" onblur="changeAccount()">
							</div>
						</div>
					</form>

				</div>
			</div>
		</div>
	</div>
</body>
</html>

