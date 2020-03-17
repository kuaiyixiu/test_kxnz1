//自定义模块代码:
layui.define(['jquery'],function(exports){
var $ = layui.jquery;
// 设置jQuery Ajax全局的参数
    $.ajaxSetup({
        type: "POST",
        complete: function(jqXHR, textStatus, errorThrown){
        	if(jqXHR.responseText=="invalidSession"){
        		top.window.location = "login.do";
        	}
//        	alert(jqXHR.responseText+jqXHR.status);
//            switch (jqXHR.status){
//                case(500):
//                    tip.alert("服务器系统内部错误");
//                    break;
//                case(401):
//                    tip.alert("未登录");
//                    break;
//                case(403):
//                    tip.alert("无权限执行此操作");
//                    break;
//                case(408):
//                    tip.alert("请求超时");
//                    break;
//                default:
//                    tip.alert("未知错误");
//            }
        }
    });
exports('jquery_common_setting', {});
});