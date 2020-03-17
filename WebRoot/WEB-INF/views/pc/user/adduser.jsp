<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
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
<link rel="stylesheet" type="text/css"
	href="lib/admin/layui/css/layui.css" />
<script src="lib/admin/layui/layui.js" type="text/javascript"
	charset="utf-8"></script>
<script src="lib/admin/js/common.js" type="text/javascript"
	charset="utf-8"></script>
<script type="text/javascript">
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		var laydate = layui.laydate;
		laydate.render({
			elem : '#birthday'
		});
		laydate.render({
			elem : '#entryDate'
		});
		form.render();
		form.verify({
			  idCard: function(value, item){ //value：表单的值、item：表单的DOM对象
				var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
			    if(value!=''&&!reg.test(value)){
			      return '请输入正确的身份证号';
			    }
			  }
			});  
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {	
			$.ajax({
				type : "POST",
				url : "user/saveData.do",
				data : data.field,
				dataType : "json",
				success : function(result) {
					if (result.retCode == 'success') {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭当前页  
						parent.layui.table.reload('userInfo');
						parent.layer.msg(result.retMsg, {icon : 1});
					} else {
						layer.msg(result.retMsg, {
							icon : 2
						});
					}
				}
			});
			return false;
		});

	});
</script>
</head>
<body>
	<form class="layui-form" action="" id="form"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<input type="hidden" name="id" id="id" value="${user.id }" /> 
		<input type="hidden" name="shopId" id="shopId" value="${shops.id }" />
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">手机号</label>
				<div class="layui-input-block">
					<input type="text" name="userPhone" lay-verify="phone"
						placeholder="请输入手机号" autocomplete="off" class="layui-input"
						value="${user.userPhone }">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">姓名</label>
				<div class="layui-input-block">
					<input type="text" name="userRealname" lay-verify="required"
						placeholder="请输入姓名" autocomplete="off" class="layui-input"
						value="${user.userRealname }">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">性别</label>
				<div class="layui-input-block">
					<input type="radio" name="userSex" value="1" title="男" <c:if test="${user==null or user.userSex =='1' }">checked</c:if> /> 
					<input type="radio" name="userSex" value="0" title="女" <c:if test="${user.userSex =='0' }">checked</c:if> /> 
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">身份证</label>
				<div class="layui-input-block">
					<input type="text" name="idCard" lay-verify="idCard"
						placeholder="请输入身份证" autocomplete="off" class="layui-input"
						value="${user.idCard }">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">出生日期</label>
				<div class="layui-input-block">
					<input type="text" name="birthday" id="birthday"
						placeholder="请输入出生日期" autocomplete="off"
						class="layui-input" value="<fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/>">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">职务</label>
				<div class="layui-input-block">
					<input type="text" name="post" id="post"
						placeholder="职务" autocomplete="off" class="layui-input"
						value="${user.post }">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">入职日期</label>
				<div class="layui-input-block">
					<input type="text" name="entryDate" id="entryDate"
						placeholder="请输入入职日期" autocomplete="off"
						class="layui-input" value="<fmt:formatDate value="${user.entryDate}" pattern="yyyy-MM-dd"/>">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">角色</label>
				<div class="layui-input-block">
					<select name="roleId" <c:if test="${user.roleId==2}">disabled="disabled"</c:if> lay-verify="required" >
						<%--修改角色不是老板的用户--%>	
					    <c:if test="${empty user or user.roleId!=2 }">
						<c:forEach items="${roleList }" var="role">
						<c:if test="${role.id!=2}">
							<option value="${role.id }" <c:if test="${user.roleId==role.id }">selected</c:if> >${role.name }</option>
						</c:if>
						</c:forEach>
						</c:if>
                        <%--修改角色为老板的用户--%>
						<c:if test="${user.roleId==2}">
						<c:forEach items="${roleList }" var="role">
							<option value="${role.id }" <c:if test="${user.roleId==role.id }">selected</c:if> >${role.name }</option>
						</c:forEach>
						</c:if>
					</select>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">状态</label>
				<div class="layui-input-block">
					<select name="status">
                          <option value="1" <c:if test="${user.status=='1' }">selected</c:if>>在职</option>
                          <option value="0" <c:if test="${user.status=='0' }">selected</c:if>>离职</option>
					</select>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">员工住址</label>
			<div class="layui-input-block">
				<input type="text" name="userAddress" placeholder="请输入员工住址"
					autocomplete="off" class="layui-input" value="${user.userAddress }">
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block" align="center">
				<button class="layui-btn" lay-submit lay-filter="formSubmitBtn">提交</button>
				<button class="layui-btn closeBtn" type="button" id="closeBtn" type="button">关闭</button>
			</div>
		</div>
	</form>
</body>
</html>

