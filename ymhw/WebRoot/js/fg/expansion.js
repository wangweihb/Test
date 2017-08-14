$(function(){
	$.getJSON("/activity/portHotLines", function(data) {
		var objStr = JSON.stringify(data);
		var obj = jQuery.parseJSON(objStr);
		$("#hotRoute_bottom").empty();
		$.each(obj, function(i, item) {
			var inner = '<div id="hotRoute1" class="routeContent">' +
							'<div class="routepic">' + 
								'<a href="/activity/display?activityId=' + item.id + '">' + 
									'<img src="' + item.pic + '"/>'+
								'</a>'+
							'</div>'+
							'<div class="routeDesc">' +
								'<h4>' + item.title + '</h4>'+
								'<p>' + item.desc + '</p>' +
							'</div>' +								
						'</div>';
			$("#hotRoute_bottom").append(inner);
		});
	});
});
