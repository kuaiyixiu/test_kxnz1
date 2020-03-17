layui.config({
	base: 'lib/admin/js/'
}).extend({
	dialog: 'module/dialog',
});
layui.use(['layer', 'form', 'element', 'jquery', 'dialog'], function() {
	var layer = layui.layer;
	var element = layui.element;
	var form = layui.form;
	var $ = layui.jquery;
	var dialog = layui.dialog;
	var hideBtn = $('#hideBtn');
	var mainLayout = $('#main-layout');
	var mainMask = $('.main-mask');
	//监听导航点击
	element.on('nav(leftNav)', function(elem) {
		var navA = $(elem);
		var id = navA.attr('data-id');
		var url = navA.attr('data-url');
		var text = navA.attr('data-text');
		if(!url){
			return;
		}
		var isActive = $('.main-layout-tab .layui-tab-title').find("li[lay-id=" + id + "]");
		if(isActive.length > 0) {
			//切换到选项卡
			element.tabChange('tab', id);
			$("iframe[data-id='"+id+"']").attr("src",url)//切换后刷新框架
		} else {
			element.tabAdd('tab', {
				title: text,
				content: '<iframe src="' + url + '" name="iframe' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
				id: id
			});
			element.tabChange('tab', id);
			
		}
		mainLayout.removeClass('hide-side');
	});
	//监听导航点击
	element.on('nav(rightNav)', function(elem) {
		var navA = $(elem).find('a');
		var id = navA.attr('data-id');
		var url = navA.attr('data-url');
		var text = navA.attr('data-text');
		if(!url){
			return;
		}
		var isActive = $('.main-layout-tab .layui-tab-title').find("li[lay-id=" + id + "]");
		if(isActive.length > 0) {
			//切换到选项卡
			element.tabChange('tab', id);
		} else {
			element.tabAdd('tab', {
				title: text,
				content: '<iframe src="' + url + '" name="iframe' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
				id: id
			});
			element.tabChange('tab', id);
			
		}
		mainLayout.removeClass('hide-side');
	});
	//菜单隐藏显示
	hideBtn.on('click', function() {
		if(!mainLayout.hasClass('hide-side')) {
			mainLayout.addClass('hide-side');
		} else {
			mainLayout.removeClass('hide-side');
		}
	});
	//遮罩点击隐藏
	mainMask.on('click', function() {
		mainLayout.removeClass('hide-side');
	})
	
	element.on('tab(tab)', function(data){
		  var layid=this.getAttribute("lay-id");
		  if(layid=="repertory_in"){
			  if(top["iframerepertory_in"].layui!=undefined)
				  top["iframe"+layid].layui.table.reload('repertoryInInfo');
		  }else if (layid=="orderlist_mgr"){
			  if(top["iframeorderlist_mgr"].layui!=undefined)
				  top["iframe"+layid].layui.table.reload('ordersTable');
		  }else if (layid=="debt_orders"){
			  if(top["iframedebt_orders"].layui!=undefined)
				  top["iframe"+layid].layui.table.reload('ordersTable');
		  }else if (layid=="pay_orders"){
			  if(top["iframepay_orders"].layui!=undefined)
				  top["iframe"+layid].layui.table.reload('ordersTable');
		  }else if(layid=="caigou_debt"||layid=="tuihuo_debt"){
			  if(top["iframe"+layid].layui!=undefined)
				  top["iframe"+layid].layui.table.reload('repertoryInRecordInfo');
		  }else if(layid=="repertory_out"){
			  if(top["iframe"+layid].layui!=undefined)
				  top["iframe"+layid].layui.table.reload('repertoryInInfo');
		  }else if(layid=="customlist_mgr"){
			  if(top["iframe"+layid].layui!=undefined)
				  top["iframe"+layid].layui.table.reload('customInfo');
		  }else if(layid=="meal_mgr"){
			  if(top["iframe"+layid].layui!=undefined)
				  top["iframe"+layid].layui.table.reload('mealInfo');
		  }
		});
	
	
	//示范一个公告层
//	layer.open({
//		  type: 1
//		  ,title: false //不显示标题栏
//		  ,closeBtn: false
//		  ,area: '300px;'
//		  ,shade: 0.8
//		  ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
//		  ,resize: false
//		  ,btn: ['火速围观', '残忍拒绝']
//		  ,btnAlign: 'c'
//		  ,moveType: 1 //拖拽模式，0或者1
//		  ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">后台模版1.1版本今日更新：<br><br><br>数据列表页...<br><br>编辑删除弹出功能<br><br>失去焦点排序功能<br>数据列表页<br>数据列表页<br>数据列表页</div>'
//		  ,success: function(layero){
//		    var btn = layero.find('.layui-layer-btn');
//		    btn.find('.layui-layer-btn0').attr({
//		      href: 'http://www.layui.com/'
//		      ,target: '_blank'
//		    });
//		  }
//		});

});