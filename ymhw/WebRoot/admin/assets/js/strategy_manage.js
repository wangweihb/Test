
var deleteStrategy = function(id) {
	$.getJSON("/admin/portStrategyDelete", {"id" : id}, function(code) {
		if (code == true) {
			alert("已删除！");
		} else{
			alert("删除失败！");
		}
		//刷新本页
		window.location = window.location.href;
	});
}

var searchByType = function(e) {
	var type = $(e).val();
	window.location = "/admin/strategyList?type=" + type;
}

/**
 * 根据关键字搜索攻略
 */
var searchStrategy = function() {
	var keyword = $("#keyword").val();
	window.location = "/admin/strategyList?keyword="+keyword;
}
