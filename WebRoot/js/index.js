layui.use(['jquery','element'], function(){
  var element = layui.element;
  var $ = layui.jquery;
  
  var iconHrefPrefix = ""
	function getAhtml(obj, isHasChildren){
		//return "<a href=\"javascript:;\" data-url=\""+obj.path+"\" data-id=\""+obj.resKey+"\" data-text=\""+obj.title+"\" ><i class=\"iconfont "+obj.icon+"\"> </i>" + obj.title + "</a>";
	    // 如果是最后一个 添加额外样式
	  	var childrenClass = isHasChildren?"lastChildren":"notChildren";
	  
		return "<a href=\"javascript:;\" data-children="+childrenClass+" data-url=\""+obj.path+"\" data-id=\""+obj.resKey+"\" data-text=\""+obj.title+"\" >" + obj.title + "</a>";
	}
	
	//填充子菜单
	function getChildMenu(obj, isHasChildren){
		var html="";
		var children=obj.children;
		for(var i = 0; i < children.length; i++){
			html=html+" <dl class=\"layui-nav-child\"> "
			if (children[i].children !=null){//还有下级菜单
				var strli = "<li class=\"layui-nav-item \" >";
				//strli = strli + "<a href=\"javascript:;\"><i class=\"iconfont "+children[i].icon+"\"> </i>" + children[i].title + "</a>";
				strli = strli + "<a href=\"javascript:;\">" + children[i].title + "</a>";
				strli=strli+getChildMenu(children[i], true);
				strli = strli + "</li>";
				html=html+strli;
			}else{
				html=html+" <dd>"+getAhtml(children[i], isHasChildren)+"</dd>";
			}
			html=html+"</dl>";
		}
		return html;
		
	}
	
	
//	//动态菜单
	$.ajax({
		url: "getMenuJson.do",
		method: 'POST',
		success: function(res) {
			var html = "";
			if(res=="")
				return;
			var menu=eval(res);
			for(var i = 0; i < menu.length; i++) {
				var strli = "<li class=\"layui-nav-item ";
				if(menu[i].itemed==1)
					strli=strli+"layui-nav-itemed ";
				strli=strli+"\" >";
				//判断当前菜单是否是父类菜单
				if (menu[i].children !=null){
					strli = strli + "<a href=\"javascript:;\"><i class=\"iconfont "+menu[i].icon+"\"> </i>" + menu[i].title + "</a>";
					strli=strli+getChildMenu(menu[i]);
				}else{
					strli = strli + getAhtml(menu[i]);
				}
				strli = strli + "</li>";
				html = html + strli;
			}
			$("#memus").html(html);
			element.init(); //一定初始化一次
			
			$('#memus .layui-nav-item').click(function(){
		        $(this).siblings('li').attr('class','layui-nav-item');
		      })
		}
	})
  
});

function resetPwd(obj){//修改密码
  		var id=obj.id;
		var openw="500px";
		var openh="300px";
		var $=layui.$;
		layer.open({
			type: 2,
			title: "修改密码",
			area: [openw, openh],
			fixed: false, //不固定
			content: "user/resetPwd.do",
			btn: ['确定', '关闭'],
			yes: function(index, layero){
				var body = layer.getChildFrame('body', index); //得到iframe页的body内容
				var pwd1 = body.find("#pwd1").val();
				var pwd2 = body.find("#pwd2").val();
				var pwd3 = body.find("#pwd3").val();
				if($.trim(pwd1)==""){
					layer.msg("请输入旧密码", {icon : 2});
					body.find("#pwd1").focus();
					return;
				}
				if($.trim(pwd2)==""){
					layer.msg("请输入新密码", {icon : 2});
					body.find("#pwd2").focus();
					return;
				}
				if(pwd2!=pwd3){
					layer.msg("确认密码和新的密码不一致，请重新输入", {icon : 2});
					body.find("#pwd3").val("");
					body.find("#pwd3").focus();
					return;
				}
				var data={"pwd1":pwd1,"pwd2":pwd2,"pwd3":pwd3};
				var result=resetPassWord(data);
				if(result.retCode=='success'){
					layer.close(index);
					layer.msg(result.retMsg, {icon : 1});
				}else{
					layer.msg(result.retMsg, {icon : 2});
				}
					
			}
		});
}

/**
 * 修改密码
 * @param data
 * @returns
 */
function resetPassWord(data){
	var $=layui.$;
	var status;
	$.ajax({
		type : "POST",
		url : "user/resetPassWord.do",
		data : data,
		dataType : "json",
		async:false,
		success : function(result) {
			status=result;
		}
	});
	return status;
}

function changeShop(id){
	layui.$.post("changeShopByAdmin.do",{"shopId":id},function(data){
		var result=JSON.parse(data);
		if(result.retCode == 'success'){
			window.location.href ="index.do";
		}else{
			layer.msg(result.retMsg, {icon : 2});
		}
	});
}