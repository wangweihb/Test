$(function(){
	$(':input').labelauty();
});

var tabOpt = function(tabName)
{
	$("#tabAll li").attr("class","");
	$("#" + tabName).attr("class","clickedOption");
}

/**
 * 跳转到活动详情页面
 * @param {Object} actId
 */
var goToDisplay = function(actId)
{
	//alert("xx" + actId);
	window.location ="/activity/display?activityId=" + actId;
}


var multiSearch = function()
{
	var hotTypeList = "";
	$("input[name='hotType']:checked").each(function(){
		hotTypeList += $(this).val() + ",";
	});
	if(!isNullOrEmpty(hotTypeList))
	{
		hotTypeList = hotTypeList.substr(0,hotTypeList.length-1)
	}
	
	var contentTypeList = "";
	$("input[name='contentType']:checked").each(function(){
		contentTypeList += $(this).val() + ",";
	});
	if(!isNullOrEmpty(contentTypeList))
	{
		contentTypeList = contentTypeList.substr(0,contentTypeList.length-1)
	}
	
	var timeType = $("input[name='timeType']:checked").val();
	if(isNullOrEmpty(timeType))
	{
		timeType = "";
	}
	
	$.getJSON("/activity/portSearchByCondition",{"hotTypeList":hotTypeList,"contentTypeList":contentTypeList,"timeType":timeType}, function(data) {
		var objStr = JSON.stringify(data);
		var obj = jQuery.parseJSON(objStr);
		$("#activitysearch_left_show").empty();
		$.each(obj, function(i, item) {
			
		var inner = '<div id="activityDispaly1" class="aboutDisplay">'+
						'<div class="aboutDisplay_left">'+
							'<a href="/activity/display?activityId=' + item.id + '">'+
								'<img src="' + item.pic + '"/>'+
							'</a>'+
						'</div>'+
						'<div class="aboutDisplay_middle">'+
							'<div class="descTip2">'+
								'<a href="/activity/display?activityId=' + item.id + '">' + item.title + '</a>'+
							'</div>'+
							'<div class="descTip3">'+
								'<span>'+
									item.desc+
								'</span>'+
							'</div>'+
							'<div class="descTip4">'+
								'<span>友鸣官方</span>'+
							'</div>'+
						'</div>'+
						
						'<div class="aboutDisplay_right">'+
							'<div class="otherdesc1">'+
									'<div class="stressed1">' + item.cost + '</div>'+
                                    '<span>促销价</span>'+
							'</div>'+
							'<div class="otherdesc2">'+
                            	'<button type="text"class="otherdesc-button btn btn-default" > <a href="/activity/display?activityId=' + item.id + '">报名</a></button>'+
							'</div>'+
						'</div>'+
					'</div>';
			$("#activitysearch_left_show").append(inner);
		});
	});
}
