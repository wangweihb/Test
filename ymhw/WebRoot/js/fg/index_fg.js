$(function(){
	
	//领队信息滚动显示
//	jQuery(function() {
//		yxk.bbs.newHome();
//		jQuery("#slider").slide({
//			titCell: ".slider-controlNav a",
//			mainCell: ".slider-item",
//			effect: "left",
//			delayTime: 200,
//			autoPlay: true
//		});
//		jQuery("#slider").slide({
//			titCell: ".slider-controlNav a",
//			mainCell: ".slider-caption",
//			delayTime: 200,
//			autoPlay: true
//		});
//	});
	
//	jQuery(".tabox").slide({delayTime: 0});
			
	$("#actli").hover(function(){
		$("#actli").attr("class","hoverli");
	},function(){
		$("#actli").attr("class","");
	});
	
	
	$("#actli").hover(function(){
		$("#actli").attr("class","hoverli");
	},function(){
		$("#actli").attr("class","");
	});
	
	$("#tabNav2 li").hover(function(){
		var index = $("#tabNav2 li").index(this);
		$("#t1-a").attr("class","tabnav-link");
		$("#t2-a").attr("class","tabnav-link");
		$("#t3-a").attr("class","tabnav-link");
		$("#t4-a").attr("class","tabnav-link");
		
		$("#t"+(index+1)+"-a").attr("class","tabnav-hover");
		
		
		$("#step-box2-1").attr("style","display:none");
		$("#step-box2-2").attr("style","display:none");
		$("#step-box2-3").attr("style","display:none");
		$("#step-box2-4").attr("style","display:none");
		
		$("#step-box2-"+(index+1)).attr("style","display:block");
	});
});

function checkIn(index) {
	var x = window.event.clientX;
	var y = window.event.clientY;
	var obj = $(".tabcont")[index];
	if(x > obj.offsetLeft &&
		x < (obj.offsetLeft + obj.clientWidth) &&
		y > obj.offsetTop &&
		y < (obj.offsetTop + obj.clientHeight)) {
		return true;
	} else {
		return false;
	}
}

/**
 * 
 */

var tabnav = function(parentName,tabName,forwardName)
{
	/*$("#tabNav li").attr("class","");
	$("#" + tabName).attr("class","activitynav-icon");*/
	$("#" + parentName + " a").attr("class","tabnav-link");
	$("#" + tabName + " a").attr("class","tabnav-hover");
	
	$("#" + forwardName).find("div.box_item").attr("style","display:none;");
	$("#" + tabName + "-box").attr("style","display:block;");
}

var getActByType = function(parentName,tabName,type)
{
	$("#" + parentName + " a").attr("class","tabnav-link");
	$("#" + tabName + " a").attr("class","tabnav-hover");
	
	$.getJSON("/activity/portSearchByType",{"typeId":type}, function(data) {
		var objStr = JSON.stringify(data);
		var obj = jQuery.parseJSON(objStr);
		$("#actUl").empty();
		var h2title = "友鸣活动";
		if(type == 1){
			h2title = "快乐周末";
		}else if(type == 2){
			h2title = "当季热门";
		}else if(type == 3){
			h2title = "毕业狂欢";
		}else if(type == 4){
			h2title = "热门推荐";
		}else if(type == 5){
			h2title = "西部神游";
		}
		
		$.each(obj, function(i, item) {
			var startTime = item.startTime;
			var inner = '<a href="activity/display?activityId=' + item.id + '" class="effect-ruby">'+
			              '<figcaption>'+
			                '<h2>' + h2title + '</h2>'+
			                '<p> <i class="iconfont">&#xe7a6;' + startTime.substring(0,10) + '</i>'+
			                	'<i class="iconfont" style="float:right;">&#xe77e;已报名'+item.enteredNum+'人</i>'+
			                '</p>'+
			               '</figcaption>'+
			               '<img src="' + item.pic + '" />'+
			               '<div class="y-state">报名中</div>'+
			               '<div class="effect-txt">'+
			               	'<div class="effect-main-t">'+
		                		'<h5>' + item.title + '</h5>'+
		                		'<span class="act-price">￥'+item.cost+'元</span>'+
			                '</div>'+
			               '</div>'+
			            '</a>';
			$("#actUl").append(inner);
		});
	});
}