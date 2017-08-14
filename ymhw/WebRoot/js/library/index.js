$(function(){
	//从底部上升的遮罩效果开始
	$(".con").hover(function(){
		$(this).find(".txt").stop().animate({height:"120px"},200);
		$(this).find(".con-main-l").stop().animate({height:"120px"},200);
		$(this).find(".txt h6").stop().animate({paddingTop:"0px",overflow: "visible",height:"50px"},200);
	},function(){
		$(this).find(".txt").stop().animate({height:"60px"},200);
		$(this).find(".con-main-l").stop().animate({height:"50px"},200);
		$(this).find(".txt h6").stop().animate({paddingTop:"0px",overflow: "hidden",height:"0px"},200);
	})

	$(".con-z").hover(function(){
		$(this).find(".txt").stop().animate({height:"120px"},200);
		$(this).find(".con-main-l").stop().animate({height:"120px"},200);
		$(this).find(".txt h6").stop().animate({paddingTop:"0px",overflow: "visible",height:"50px"},200);
	},function(){
		$(this).find(".txt").stop().animate({height:"90px"},200);
		$(this).find(".con-main-l").stop().animate({height:"50px"},200);
		$(this).find(".txt h6").stop().animate({paddingTop:"0px",overflow: "hidden",height:"0px"},200);
	})

	//从底部上升的遮罩效果开始
	$(".con-1").hover(function(){
		$(this).find(".txt-1").stop().animate({height:"390px"},200);
		$(this).find(".txt-1 .con-main-l").stop().animate({marginTop:"104px"},200);
		$(this).find(".txt-1 h5").stop().animate({paddingTop:"103px" ,paddingBottom:"17.6px"},200);
		$(this).find(".txt-1 h6").stop().animate({paddingTop:"60px"},200);
	},function(){
		$(this).find(".txt-1").stop().animate({height:"45px"},200);
		$(this).find(".txt-1 .con-main-l").stop().animate({marginTop:"4px"},200);
		$(this).find(".txt-1 h5").stop().animate({paddingTop:"2px" ,paddingBottom:"2px"},200);
		$(this).find(".txt-1 h6").stop().animate({paddingTop:"0px"},200);
	})
	//从底部上升的遮罩效果开始
	$(".con-3").hover(function(){
		$(this).find(".txt-3").stop().animate({height:"60px"},200);
	},function(){
		$(this).find(".txt-3").stop().animate({height:"30px"},200);
	})

});
