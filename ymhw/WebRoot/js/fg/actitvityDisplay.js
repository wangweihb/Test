
var tab = function(tabName)
{
	$("#adDetailNav li").attr("class","");
	$("#" + tabName).attr("class","clickedOption");
	
	$(".menu_tab").attr("style","display:none;");
	$("#" + tabName + "Info").attr("style","display:block;");
//	$("#" + tabName).html($("#travelPlanInfo").attr("value"));
//	var desc = ${act.desc};
//	alert(desc);
}

/**
 * 马上报名
 */
var signup = function(id)
{
	window.location = "/activity/signupPath?activityId="+id;
}
