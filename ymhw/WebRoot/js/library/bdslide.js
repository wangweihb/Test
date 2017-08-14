// JavaScript Document
(function($){
	$.fn.Slide=function(options){
		var opts = $.extend({},$.fn.Slide.deflunt,options);
		var index=1;
		var targetLi = $("." + opts.claNav + " b", $(this));//目标对象
		var ConTxt = $("." + opts.claTxt, $(this));//每篇额外内容对象
		var clickNext = $("." + opts.claNav + " .next", $(this));//点击下一个按钮
		var clickPrev = $("." + opts.claNav + " .prev", $(this));//点击上一个按钮
		var ContentBox = $("." + opts.claCon , $(this));//滚动的对象
		var ContentBoxNum=ContentBox.children().size();//滚动对象的子元素个数
		var slideH=ContentBox.children().first().height();//滚动对象的子元素个数高度，相当于滚动的高度
		var slideW=ContentBox.children().first().width();//滚动对象的子元素宽度，相当于滚动的宽度
		var autoPlay;
		var slideWH;
		if(opts.effect=="scroolY"||opts.effect=="scroolTxt"){
			slideWH=slideH;
		}else if(opts.effect=="scroolX"||opts.effect=="scroolLoop"){
			ContentBox.css("width",ContentBoxNum*slideW);
			slideWH=slideW;
		}else if(opts.effect=="fade"){
			ContentBox.children().css({'opacity':'0'});
			ContentBox.children().first().css({'opacity':'1',"z-index":"1"});
		}//通过判断动画样式设置初始值
		
		return this.each(function() {
			var $this=$(this);
			//滚动函数
			var doPlay=function(){
				$.fn.Slide.effect[opts.effect](ContentBox, targetLi, ConTxt, index, slideWH, opts);
				index++;
				if (index*opts.steps >= ContentBoxNum) {
					index = 0;
				}
			};
			//下一个
			clickNext.click(function(event){
				if(opts.effect=="fade"){
					if (index*opts.steps >= ContentBoxNum) {
						index = 0;
					}
					$.fn.Slide.effect[opts.effect](ContentBox, targetLi, ConTxt, index, slideWH, opts);
					index++;
					return false
				}
				$.fn.Slide.effectLoop.scroolLeft(ContentBox, targetLi, index, slideWH, opts,function(){
					for(var i=0;i<opts.steps;i++){
	                    ContentBox.find("li:first",$this).appendTo(ContentBox);
	                }
	                ContentBox.css({"left":"0"});
				});
				event.preventDefault();
			}).hover(function(){
				if(autoPlay){
					clearInterval(autoPlay);
				}
			},function(){
				if(autoPlay){
					clearInterval(autoPlay);
				}
				autoPlay = setInterval(doPlay, opts.timer);
			});
			//上一个
			clickPrev.click(function(event){
				if(opts.effect=="fade"){
					if (index*opts.steps <= 0) {
						index = ContentBoxNum;
					}
					$.fn.Slide.effect[opts.effect](ContentBox, targetLi, ConTxt, index-2, slideWH, opts);
					index--;
					return false
				}
				for(var i=0;i<opts.steps;i++){
	                ContentBox.find("li:last").prependTo(ContentBox);
	            }
	          	ContentBox.css({"left":-index*opts.steps*slideW});
				$.fn.Slide.effectLoop.scroolRight(ContentBox, targetLi, index, slideWH, opts);
				event.preventDefault();
			}).hover(function(){
				if(autoPlay){
					clearInterval(autoPlay);
				}
			},function(){
				if(autoPlay){
					clearInterval(autoPlay);
				}
				autoPlay = setInterval(doPlay, opts.timer);
			});
			//取消计时
			ConTxt.hover(function(){
				if(autoPlay){
					clearInterval(autoPlay);
				}
			},function(){
				if(autoPlay){
					clearInterval(autoPlay);
				}
				autoPlay = setInterval(doPlay, opts.timer);
			})
			//自动播放
			if (opts.autoPlay) {
				autoPlay = setInterval(doPlay, opts.timer);
				ContentBox.hover(function(){
					if(autoPlay){
						clearInterval(autoPlay);
					}//鼠标移入图片不自动播放
				},function(){
					if(autoPlay){
						clearInterval(autoPlay);
					}
					autoPlay=setInterval(doPlay, opts.timer);
				});
			}
			//目标事件
			targetLi.hover(function(){
				if(autoPlay){
					clearInterval(autoPlay);
				}
				index=targetLi.index(this)+1;
				window.setTimeout(function(){$.fn.Slide.effect[opts.effect](ContentBox, targetLi, ConTxt, index-1, slideWH, opts);},200);
			},function(){
				if(autoPlay){
					clearInterval(autoPlay);
				}
				autoPlay = setInterval(doPlay, opts.timer);
			});
    	});
	};
	$.fn.Slide.deflunt={
		effect : "scroolY",
		autoPlay:true,
		speed : "normal",
		timer : 1000,
		defIndex : 0,
		claNav:"bdSlideNav",//切换按钮
		claCon:"bdSlideCon",//切换内容
		claTxt:"bdName",//切换内容附加的sth.
		steps:1
	};
	$.fn.Slide.effectLoop={
		scroolLeft:function(contentObj,navObj,i,slideW,opts,callback){
			contentObj.animate({"left":-i*opts.steps*slideW},opts.speed,callback);
			if (navObj) {
				navObj.eq(i).addClass("on").siblings().removeClass("on");
			}
		},
		
		scroolRight:function(contentObj,navObj,i,slideW,opts,callback){
			contentObj.stop().animate({"left":0},opts.speed,callback);
		}
	}
	$.fn.Slide.effect={
		fade:function(contentObj,navObj,txtObj,i,slideW,opts){
			contentObj.children().eq(i).stop().animate({opacity:1},opts.speed,function(){if(txtObj.length>1){txtObj.addClass('sphl').eq(i).removeClass('sphl')}}).css({"z-index": "1"}).siblings().stop().animate({opacity: 0},opts.speed).css({"z-index":"0"});
			navObj.eq(i).addClass("on").siblings().removeClass("on");
		},
		scroolTxt:function(contentObj,undefined,i,slideH,opts){
			//alert(i*opts.steps*slideH);
			contentObj.animate({"margin-top":-opts.steps*slideH},opts.speed,function(){
                for( var j=0;j<opts.steps;j++){
                	contentObj.find("li:first").appendTo(contentObj);
                }
                contentObj.css({"margin-top":"0"});
            });
		},
		scroolX:function(contentObj,navObj,i,slideW,opts,callback){
			contentObj.stop().animate({"left":-i*opts.steps*slideW},opts.speed,callback);
			if (navObj) {
				navObj.eq(i).addClass("on").siblings().removeClass("on");
			}
		},
		scroolY:function(contentObj,navObj,i,slideH,opts){
			contentObj.stop().animate({"top":-i*opts.steps*slideH},opts.speed);
			if (navObj) {
				navObj.eq(i).addClass("on").siblings().removeClass("on");
			}
		}
	};
})(jQuery);