!(function($, window) {

	/*init*/
	var ArrayProto = Array.prototype,
		ObjProto = Object.prototype,
		FuncProto = Function.prototype;
	var push = ArrayProto.push,
		slice = ArrayProto.slice,
		concat = ArrayProto.concat,
		toString = ObjProto.toString,
		hasOwnProperty = ObjProto.hasOwnProperty;
	var nativeForEach = ArrayProto.forEach,
		nativeMap = ArrayProto.map,
		nativeReduce = ArrayProto.reduce,
		nativeReduceRight = ArrayProto.reduceRight,
		nativeFilter = ArrayProto.filter,
		nativeEvery = ArrayProto.every,
		nativeSome = ArrayProto.some,
		nativeIndexOf = ArrayProto.indexOf,
		nativeLastIndexOf = ArrayProto.lastIndexOf,
		nativeIsArray = Array.isArray,
		nativeKeys = Object.keys,
		nativeBind = FuncProto.bind;
	var breaker = {};

	var yxk = {};
	yxk.plugin = {}; //插件类
	yxk.tools = {}; //工具类    
	yxk.bbs = {}; //业务逻辑
	/*tools*/
	// 遍历
	yxk.tools.each = function(obj, iterator, context) {
		if (obj == null) return;
		if (nativeForEach && obj.forEach === nativeForEach) {
			obj.forEach(iterator, context);
		} else if (obj.length === +obj.length) {
			for (var i = 0, l = obj.length; i < l; i++) {
				if (iterator.call(context, obj[i], i, obj) === breaker) return;
			}
		} else {
			for (var key in obj) {
				if (_.has(obj, key)) {
					if (iterator.call(context, obj[key], key, obj) === breaker) return;
				}
			}
		}
	};
	//合并
	yxk.tools.extend = function(obj) {
		yxk.tools.each(slice.call(arguments, 1), function(source) {
			if (source) {
				for (var prop in source) {
					obj[prop] = source[prop];
				}
			}
		});
		return obj;
	};
	/*plugin*/
	//选项卡切换
	yxk.plugin.tabs = function(ele, settings) {
		var options = {
			eventType: "mouseover", //click
			imgLoad:false
		}
		options = yxk.tools.extend(options, settings);
		$(ele).each(function() {
			var that = $(this),
				eleNav = that.find(".tabsNav"),
				eleCnt = that.find(".tabsCnt"),
				eleNavChild = eleNav.children(), //tabs切换栏
				eleCntChild = eleCnt.children(); //tabs内容

			// init
			eleNavChild.eq(0).addClass("hover");
			eleCntChild.eq(0).show();
			if(options.imgLoad){
				yxk.plugin.lazyImg(eleCntChild.eq(0).find("img"));
			}
			eleNavChild.eq(0).data("state",true);
			// 绑定切换按钮事件
			eleNavChild.on(options.eventType, function(event) {
				var index = $(this).index();

				if(!$(this).data("state")&&options.imgLoad){

					yxk.plugin.lazyImg(eleCntChild.eq(index).find("img"));

					$(this).data("state",true);
				}
				
				eleNavChild.removeClass("hover");
				eleNavChild.eq(index).addClass("hover");
				eleCntChild.hide();
				eleCntChild.eq(index).show();
				
				event.preventDefault();
			});

		});
	};
	// 图片延迟加载
	yxk.plugin.lazyImg = function(images) {
		images.each(function(index, el) {
			var that = $(this);
			$("<img />").bind("load", function() {
				that.hide().attr("src", that.data("original")).show();
			}).attr("src", that.data("original"));
		});
	};
	// 顶部切换
	yxk.plugin.topTab=function(eles){
		eles.each(function(){
			var parent=$(this).parent().eq(0);
			$(this).on("click",function(){
				var state=parent.data("state");
				if(!state){
					parent.animate({width:600}, 600).data("state",true);
					parent.siblings().animate({width:40}, 600).data("state",false);
				}
			});
		});
	};
	/*bbs*/
	//论坛列表页
	yxk.bbs.listPage = function() {
		$(".yxkMeinv").data("lazyload", function() {
			yxk.plugin.tabs(".tabsBox", {eventType: "click",imgLoad:true});
		}).lazyload();
		yxk.plugin.tabs(".bbstabs", {eventType: "click"});
	};
	yxk.bbs.common = function() {
		yxk.plugin.topTab($(".yxktopTab"));
		$(".tabHover").hover(function() {
			$(this).addClass("hover");
		}, function() {
			$(this).removeClass("hover");
		});
	};

	yxk.bbs.newHome=function(){

		yxk.plugin.tabs(".tabsActive");

		$("#owl-demo").owlCarousel({
			items: 4,
			lazyLoad: true,
			navigation: true
		});
		$("#owl-demo2").owlCarousel({
			items: 5,
			lazyLoad: true,
			navigation: true
		});

		$.getJSON('http://bbs.youxiake.com/online/api/bbsidx?callback=?', function(data) {
			var html="";
			if(data.code=="200"){
				var list=data.data;
				for (var i = 0; i < list.length; i++) {
					html+='<li>'+
							'<div class="module-listActive">'+
								'<h3 class="module-listActive-title"><a target="_blank" href="http://www.youxiake.com/online/detail/'+list[i].id+'" title="'+list[i].subject+'">'+list[i].subject+'</a></h3>'+
								'<p class="module-listActive-info">'+list[i].start_time+' '+list[i].applys+'人参与 '+list[i].photos+'张照片 发起人: <a target="_blank" href="http://www.youxiake.com/online/u/'+list[i].uid+'">'+list[i].uname+'</a></p>'+
								'<a target="_blank" href="http://www.youxiake.com/online/detail/'+list[i].id+'" class="module-listActive-img" style=" background-image:url('+list[i].thumb+')" title="'+list[i].subject+'"><p>我要参加</p>'+
								'</a>'+
							'</div>'+
						'</li>';
				};
				
			}else{
				html="<li>通信失败</li>"
			}
			$("#htmlLoad").html(html);
		});

	};
	// 注册到window下
	window.yxk = yxk;

})(jQuery, this);