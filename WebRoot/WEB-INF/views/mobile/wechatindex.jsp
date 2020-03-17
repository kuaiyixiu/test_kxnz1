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
<html class="ui-page-login">
<base href="<%=basePath%>">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title></title>
<link href="lib/mui/css/mui.min.css" rel="stylesheet" />
<link href="lib/mui/css/style.css" rel="stylesheet" />
<script src="lib/mui/js/mui.min.js"></script>
<script src="http://res2.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script src="lib/jquery/jquery-3.4.1.js"></script>
<link rel="stylesheet" href="lib/admin/wechatfonts/style.css">
</head>

</head>
<body>
	<header class="mui-bar mui-bar-nav">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 class="mui-title">连途门店管理系统</h1>
	</header>
	<div class="mui-content">
		<div style="background-image: url(image/wechat/wechattop.png);background-repeat:no-repeat; background-size:100% 100%;height: 200px;">
		<div style="width: 30%;height: 40px;background-image: url(image/wechat/custtype.png);background-repeat:no-repeat; background-size:100% 100%;position: relative;top:15px; vertical-align: middle;line-height: 40px;" align="center"><font color="#ffffff" >${customType.typeName }</font></div>
		<div align="center" style="position: relative;top: 15px;">
		<div style="width: 50px;height: 50px; position: relative;background-image: url(image/wechat/touxiang.png);background-repeat:no-repeat; background-size:100% 100%;">&nbsp;</div>
		<div class="mui-row" style="width: 80%;position: relative;top: 10px;" align="center"><font color="#ffffff"><label>${custom.custName }</label> | <label>${custom.cardNo }</label></font></div>
		</div>
		</div>
		<div class="mui-row" style="width: 100%;position: relative;bottom: 30px;" align="center">
		<table style="width: 100%;"><tr style="width: 100%"><td align="center" width="50%"><font color="#ffffff">余额：<fmt:formatNumber type="number" value="${custom.balance }" pattern="0.00"/> 元</font></td><td align="center" width="50%"><font color="#ffffff">积分:${custom.score }</font></td></tr></table>
		<font color="#ffffff"> </font>
		</div>
		<div class="mui-row"
			style="text-align: center;margin-top: 20px;margin-left: 3%;width: 94%;">
			<table style="width: 100%;height: 100%">
				<tr>
					<td width="38%" valign="middle"><div style="background-image: url('image/wechat/henxian.png');background-repeat:repeat-x;height: 2px;">&nbsp;</div></td>
					<td width="24%" ><font color="#999999">快速导航</font></td>
                    <td width="38%" valign="middle"><div style="background-image: url('image/wechat/henxian.png');background-repeat:repeat-x;height: 2px;">&nbsp;</div></td>
				</tr>
			</table>

		</div>
		<ul class="mui-table-view mui-grid-view mui-grid-9"
			style="margin-top: 20px;">
			<li class="mui-table-view-cell mui-media  mui-col-sm-4 mui-col-xs-4" id="addBoxOrder"><a>
			<img class="mui-icon" src="lib/admin/images/saoyisao.png" style="font-size: 2.0em;max-width: 34px;max-height: 34px;"></img>
					<div class="mui-media-body">盒子下单</div> </a>
			</li>
			<li class="mui-table-view-cell mui-media  mui-col-sm-4 mui-col-xs-4" id="myBoxOrder"><a>
			<img class="mui-icon" src="lib/admin/images/cs-hz-1.png" style="font-size: 2.0em;max-width: 34px;max-height: 34px;"></img>			
					<div class="mui-media-body">我的盒子</div> </a>
			</li>
			
 			<li class="mui-table-view-cell mui-media  mui-col-sm-4 mui-col-xs-4" id="mycar"><a><span
					class="mui-icon icon-5" style="font-size: 2.0em"></span>
					<div class="mui-media-body">我的车辆</div> </a>
			</li> 
			<li class="mui-table-view-cell mui-media mui-col-sm-4 mui-col-xs-4"
				id="appointlist"><a> <span class="mui-icon icon-6"
					style="font-size: 2.0em"><span class="path1"></span><span
						class="path2"></span><span class="path3"></span><span
						class="path4"></span><span class="path5">
						</span>
						</span>
					<div class="mui-media-body">我的预约</div> </a>
			</li>
			<li class="mui-table-view-cell mui-media mui-col-sm-4 mui-col-xs-4" id="mymeal"><a><span
					class="mui-icon icon-uniE900" style="font-size: 2.0em"></span>
					<div class="mui-media-body">我的套餐</div> </a>
			</li>
			<li class="mui-table-view-cell mui-media  mui-col-sm-4 mui-col-xs-4"
				id="inOrderList"><a><span class="mui-icon icon-7"
					style="font-size: 2.0em"><span class="path1"></span><span
						class="path2"></span><span class="path3"></span><span
						class="path4"></span> </span>
					<div class="mui-media-body">施工中订单</div> </a>
			</li>
			<li class="mui-table-view-cell mui-media mui-col-sm-4 mui-col-xs-4"
				id="histortOrderList"><a><span class="mui-icon icon-8"
					style="font-size: 2.0em"><span class="path1"></span><span
						class="path2"></span><span class="path3"></span><span
						class="path4"></span> </span>
					<div class="mui-media-body">历史订单</div> </a>
			</li>

			<li class="mui-table-view-cell mui-media mui-col-sm-4 mui-col-xs-4" id="payrecord"><a><span
					class="mui-icon icon-1" style="font-size: 2.0em"></span>
					<div class="mui-media-body">消费记录</div> </a>
			</li>
			<li class="mui-table-view-cell mui-media mui-col-sm-4 mui-col-xs-4" id="rechargeList"><a><span
					class="mui-icon icon-2" style="font-size: 2.0em"></span>
					<div class="mui-media-body">充值记录</div> </a>
			</li>
			<li class="mui-table-view-cell mui-media mui-col-sm-4 mui-col-xs-4" id="couponList"><a><span
					class="mui-icon icon-3" style="font-size: 2.0em"></span>
					<div class="mui-media-body">代金券</div> </a>
			</li>
			<li class="mui-table-view-cell mui-media mui-col-sm-4 mui-col-xs-4" id="cardsetList"><a><span
					class="mui-icon icon-3" style="font-size: 2.0em"></span>
					<div class="mui-media-body">优惠卷</div> </a>
			</li>
		</ul>
	</div>
</body>
</html>
<script>

mui('body').on('tap','#mycar',function(){//我的车辆
	 mui.openWindow({
		    url: 'wechat/car/index.do?id=${custom.id }' 
		  });
}).on('tap','#mymeal',function(){//我的套餐
	 mui.openWindow({
		    url: 'wechat/custom/mealIndex.do?custId=${custom.id }&shopId=${custom.shopId }' 
		  });
}).on('tap','#inOrderList',function(){//施工中订单
	  mui.openWindow({
		    url: 'wechat/orders/orderList/3.do' 
	});
}).on('tap','#appointlist',function(){//我的预约
	 mui.openWindow({
		    url: 'wechat/appoint/appointlist.do' 
		  });
}).on('tap','#histortOrderList',function(){//历史订单
	  mui.openWindow({
		    url: 'wechat/orders/orderList/456.do?pageType=1' 
		  });
}).on('tap','#payrecord',function(){//消费记录
	  mui.openWindow({
		    url: 'wechat/orders/orderList/456.do?pageType=2' 
		  });
}).on('tap','#rechargeList',function(){//充值记录
	  mui.openWindow({
		    url: 'wechat/orders/rechargeList.do' 
		  });
}).on('tap','#couponList',function(){//代金券
	  mui.openWindow({
		    url: 'wechat/orders/couponList.do' 
		  });
}).on('tap','#cardsetList',function(){//优惠卷
	  mui.openWindow({
		    url: 'wechat/orders/cardRecordList.do' 
		  });
}).on('tap','#myBoxOrder',function(){// 我的盒子
 	  mui.openWindow({
		url: 'wechat/box/myBoxOrderView.do' 
	  });
}).on('tap','#addBoxOrder',function(){// 下单
 	  wx.scanQRCode({
          needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
          scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
          success: function(res) {
              var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
              addBoxOrder(result);
          }
	  });
})

var wechatConf = ${wechatConfig};

wx.config({
  debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
  appId: wechatConf.appId, // 必填，公众号的唯一标识
  timestamp: wechatConf.timestamp, // 必填，生成签名的时间戳
  nonceStr: wechatConf.noncestr, // 必填，生成签名的随机串
  signature: wechatConf.signature,// 必填，签名
  jsApiList: ['scanQRCode'] // 必填，需要使用的JS接口列表
});
	
function addBoxOrder(boxIdentifier){
	if(boxIdentifier  == "-1"){
		mui.alert("扫码结果错误");
		return false;
	}
	
	mui.post('wechat/box/openLock.do',{
		"boxIdentifier": boxIdentifier
	},function(data){
		if (data.retCode == "success") {
			var lockId = data.retData;
			mui.alert(data.retMsg);
		    mui.openWindow({
		    	url: 'wechat/box/addBoxOrderView.do?lockId='+lockId 
		  	});
		}else{
			var msg = "开锁失败";
			if(data && data.retMsg){
				msg = data.retMsg;
			}
			 
			mui.alert(msg);
		}
	},'json'
	);

}

</script>

