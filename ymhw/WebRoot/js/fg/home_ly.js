$(function() {

	//header drop down menu
	/*
	$(".topnavul li").hover(function(){
		$(this).addClass("topnavhover");
		$(this).find("dl").show();
		},function(){
			$(this).removeClass("topnavhover");
			$(this).find("dl").hide();
			})	
        */

	//search drop down menu
	$(".searchtype").hover(function() {
		$("#search_bar").show();
	}, function() {
		$("#search_bar").hide();
	})
	$(".searchdrop li[data-searchtype]").click(function() {
		$("#searchselect").html($(this).text());
		$("#search_bar").hide();
	})

	//nav
	$('#newnavi li').hover(function() {
		$(this).addClass('lihover')
	}, function() {
		$(this).removeClass('lihover')
	});

	//banner
	$("#bdSlide").Slide({
		effect: "fade",
		autoPlay: true,
		speed: "slow",
		timer: 3000
	})
	$(".bdBanCtrla b").eq(0).addClass("on");

	// sub banner	
	$(".snav-cont .tabl li").hover(function() {
		$(this).find("span").addClass("cur");
		$(".tabcont").eq($(this).index()).show()
	}, function() {
		$(this).find("span").removeClass("cur");
		$(".tabcont").eq($(this).index()).hide()
	})

	$(".tabcont").hover(function() {
		$(this).show();
		$(".snav-cont .tabl li").eq($(".tabcont").index($(this))).find("span").addClass("cur");
	}, function() {
		$(this).hide();
		$(".snav-cont .tabl li").eq($(".tabcont").index($(this))).find("span").removeClass("cur");
	})

	// dangji tab 
	$(".hot-block .tab-ul li").mouseover(function() {
		$(this).addClass("cur").siblings().removeClass("cur");
		$(".sametab").eq($(this).index()).addClass("sametabcur").siblings().removeClass("sametabcur");
	})

	//news block
	$(".hcont-mul li").mouseover(function() {
		$(this).addClass("cur").siblings().removeClass("cur");
	})

	//outdoor picture
	var animation = false,
		animationstring = 'animation',
		keyframeprefix = '',
		domPrefixes = 'Webkit Moz O ms Khtml'.split(' '),
		pfx = '',
		elm = document.createElement('div');

	if(elm.style.animationName !== undefined) {
		animation = true;
	}

	if(animation === false) {
		for(var i = 0; i < domPrefixes.length; i++) {
			if(elm.style[domPrefixes[i] + 'AnimationName'] !== undefined) {
				pfx = domPrefixes[i];
				animationstring = pfx + 'Animation';
				keyframeprefix = '-' + pfx.toLowerCase() + '-';
				animation = true;
				break;
			}
		}
	}

	function changepic() {
		$.ajax({
			url: "http://cms.lvye.cn/homepagecms/api/outdoor_photos/",
			//data: "p1=1&p2=2&callback=?",
			type: "post",
			processData: false,
			timeout: 15000,
			dataType: "jsonp", // not "json" we'll parse
			jsonp: "callback",
			success: function(result) {
				$("#goodpic").html(result);
				$(".fold_wrap li").css("width", "130px");
				$(".fold_wrap li").mouseover(function() {

					if(animation) {
						$(this).css("width", "388px").siblings().css("width", "97px");
					} else {
						$(this).stop().animate({
							width: "388px"
						}, 300).siblings().stop().animate({
							width: "97px"
						}, 300);
					}
				}).mouseout(function() {
					$(".fold_wrap li").css("width", "130px");
				})
			}
		});
	}
	changepic();
	//ajax switch picture
	$("#changeBtn").click(function() {
		changepic();
	})

	//lvye FAQ tab
	$(".answer-tab span").mouseover(function() {
		$(this).addClass("cur").siblings().removeClass("cur");
		$(".answer-cont").eq($(this).index()).addClass("answer-contcur").siblings().removeClass("answer-contcur");
	})

	//sales

	countDown("2016/4/1 11:11:59", "#colockbox1");

	function countDown(time, id) {
		var day_elem = $(id).find('.day');
		var hour_elem = $(id).find('.hour');
		var minute_elem = $(id).find('.minute');
		var second_elem = $(id).find('.second');
		//if(typeof end_time == "string")
		var end_time = new Date(time).getTime(), //月份是实际月份-1
			sys_second = (end_time - new Date().getTime()) / 1000;
		var timer = setInterval(function() {
			if(sys_second > 1) {
				sys_second -= 1;
				var day = Math.floor((sys_second / 3600) / 24);
				var hour = Math.floor((sys_second / 3600) % 24);
				var minute = Math.floor((sys_second / 60) % 60);
				var second = Math.floor(sys_second % 60);
				day_elem && $(day_elem).text(day); //计算天
				$(hour_elem).text(hour < 10 ? "0" + hour : hour); //计算小时
				$(minute_elem).text(minute < 10 ? "0" + minute : minute); //计算分钟
				$(second_elem).text(second < 10 ? "0" + second : second); //计算秒杀
			} else {
				clearInterval(timer);
			}
		}, 1000);
	}

	//qrcode
	$(".gotop span.ewm,.gotop em").hover(function() {
		$(".gotop em").show();
	}, function() {
		$(".gotop em").hide();
	})

	//return to top
	$(".gotop span.topup").click(function() {
		$("body,html").animate({
			scrollTop: 0
		}, 500)
	})

	//svg Tag-cloud
	var cloudTab = $("#tag-cloud-data").find("a");
	var entries = [];
	cloudTab.each(function() {
		tabL = $(this).attr("label");
		tabUrl = $(this).attr("href");
		entries.push({
			label: tabL,
			url: tabUrl,
			target: '_top'
		});
	});

	/*var entries = [ 
				{ label: tabL, url: tabUrl, target: '_top' },
			];*/
	var settings = {
		entries: entries,
		width: 278,
		height: 295,
		radius: '70%',
		radiusMin: 75,
		bgDraw: true,
		bgColor: '#fff',
		opacityOver: 1.00,
		opacityOut: 0.1,
		opacitySpeed: 6,
		fov: 800,
		speed: 0.5,
		fontFamily: "'microsoft yahei',Verdana,Arial,Helvetica,sans-serif",
		fontSize: '16',
		fontColor: '#3cb43c',
		fontWeight: 'bold', //bold
		fontStyle: 'normal', //italic 
		fontStretch: 'normal', //wider, narrower, ultra-condensed, extra-condensed, condensed, semi-condensed, semi-expanded, expanded, extra-expanded, ultra-expanded
		fontToUpperCase: true

	};

	//var svg3DTagCloud = new SVG3DTagCloud( document.getElementById( 'holder'  ), settings );
	//$('#tag-cloud').svg3DTagCloud(settings);
	//index xiala
	var $adv = $('#adv');
	var $container = $('#container');
	$adv.animate({
		top: 0
	}, 3000);
	$container.animate({
		height: 300
	}, 3000);
	setTimeout(function() {
		$adv.animate({
			top: -300
		}, 2000);
	}, 7000);
	setTimeout(function() {
		$container.animate({
			height: 0
		}, 2000);
	}, 7000);

	$('.a_cb_close').click(function() {
		$(this).parent().css('display', 'none');
	})

	//    新添加改变img尺寸
	$(document).ready(function() {
		//img尺寸
		var adv_img = $adv.children('a').children('img');
		adv_img.css('width', '100%');
	})

})