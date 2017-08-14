/**
 * 左侧导航跳转
 * @param {Object} selector
 */
var checkPath = function(selector)
{
	if(selector == "activity"){
		$("#mainWrap").addClass("minHeight");
		
		$("#leaderWrap").attr("style","display:none;");
		$("#equipWrap").attr("style","display:none;");
		$("#allActWrap").attr("style","display:none;");
		$("#actWrap").attr("style","display:block;");
		window.location = "/checkActPath";
		
	}
	
	if(selector == "leader"){
		$("#leaderBody").empty();
		$("#mainWrap").addClass("minHeight");
		
		$("#actWrap").attr("style","display:none;");
		$("#equipWrap").attr("style","display:none;");
		$("#allActWrap").attr("style","display:none;");
		$("#leaderWrap").attr("style","display:block;");
		
		$.getJSON("/portCheckLeader", function(data) {
			var objStr = JSON.stringify(data);
			var obj = jQuery.parseJSON(objStr);
			$("#leaderBody").empty();
			$.each(obj, function(i, item) {
				var tr = '<tr>'
							+'<td>'+item.account + '</td>'
							+'<td>'+item.name + '</td>'
							+'<td>'+item.email + '</td>'
				         	+'<td>'+item.telphone + '</td>'
				         	+'<td><a href="/user/editLeader?id='+item.id+'">编辑</a></td>'
				         +'</tr>';
				$("#leaderBody").append(tr);
			});
		});
	}
	
	if(selector == "equip"){
		$("#equipBody").empty();
		$("#mainWrap").addClass("minHeight");
		
		$("#actWrap").attr("style","display:none;");
		$("#leaderWrap").attr("style","display:none;");
		$("#allActWrap").attr("style","display:none;");
		$("#equipWrap").attr("style","display:block;");
		
		$.getJSON("/equip/portCheckEuiplist", function(data) {
			var objStr = JSON.stringify(data);
			var obj = jQuery.parseJSON(objStr);
			$("#equipBody").empty();
			$.each(obj, function(i, item) {
				var tr = '<tr>'
							+'<td>'+item.name + '</td>'
							+'<td>'+item.uploaderName + '</td>'
							+'<td>'+item.uploadTime + '</td>'
				         	+'<td><a href="/equip/edit?id='+item.id+'">编辑</a></td>'
				         +'</tr>';
				$("#equipBody").append(tr);
			});
		});
	}
	
	if(selector == "allActivity")
	{
		$("#allActBody").empty();
		$("#mainWrap").removeClass("minHeight");
		
		$("#actWrap").attr("style","display:none;");
		$("#leaderWrap").attr("style","display:none;");
		$("#equipWrap").attr("style","display:none;");
		
		$("#allActWrap").attr("style","display:block;");
		
		$.getJSON("/activity/portAllAct", function(data) {
			var objStr = JSON.stringify(data);
			var obj = jQuery.parseJSON(objStr);
			$("#equipBody").empty();
			$.each(obj, function(i, item) {
				var operatorTr = "";
				var checkStatusTr = "";
				
				if(item.checkStatus == "1"){
					checkStatusTr = '<td>待审核</td>';
					operatorTr = '<td><a href="/activity/editActPath?id='+item.id+'">审核</a></td>'
				}
				if(item.checkStatus == "2"){
					checkStatusTr = '<td>未通过</td>';
					operatorTr = '<td><a href="/activity/editActPath?id='+item.id+'">修改</a></td>'
				}
				if(item.checkStatus == "3"){
					checkStatusTr = '<td>通过</td>';
					operatorTr = '<td><a href="/activity/editActPath?id='+item.id+'">修改</a>'
										+ '|<a href="javascript:disableAct('+item.id+');">下架</a>'
										+ '|<a href="javascript:deleteAct('+item.id+');">删除</a>'
									  +'</td>';
				}
				var tr = '<tr>'
							+'<td style="padding:0px 5px;width:20%;height:37px;"><div style="height:37px;line-height:37px;overflow:hidden;">'+item.title + '</div></td>'
							+'<td style="padding:0px 5px;width:25%;height:37px;"><div style="height:37px;line-height:37px;overflow:hidden;">'+item.desc + '</div></td>'
							+'<td>'+item.account + '</td>'
							+'<td>'+item.publishTime + '</td>'
							+'<td>'+item.deadline + '</td>'
							+checkStatusTr
				         	+operatorTr
				         +'</tr>';
				$("#allActBody").append(tr);
			});
		});
	}
}

var checkLeader = function()
{
	$("#leaderForm").submit();
}

/**
 * 下架活动
 * @param {Object} actId
 */
var disableAct = function(actId)
{
	$.get("/activity/portDisable",{"activityId":actId},function(data){
		if(data == "1"){
			alert("活动已下架！");
			window.location = "/checkActPath";
		} else if(data == "0"){
			alert("下架失败！");
		} else{
			alert("其他错误，请联系管理员！");
		}
	});
}

/**
 * 删除活动
 * @param {Object} actId
 */
var deleteAct = function(actId)
{
	$.get("/activity/portDelete",{"activityId":actId},function(data){
		if(data == "1"){
			alert("活动已删除！");
			window.location = "/checkActPath";
		} else if(data == "0"){
			alert("删除失败！");
		} else{
			alert("其他错误，请联系管理员！");
		}
	});
}
