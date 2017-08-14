var deleteCarFactory = function(id) {
	$.getJSON("/admin/portCarFactoryDelete", {"id" : id}, function(code) {
		if (code == true) {
			alert("已删除！");
		} else{
			alert("删除失败！");
		}
		//刷新本页
		window.location = window.location.href;
	});
}

/**
 * 根据关键字搜索商户
 */
var searchCarFactory = function() {
	var keyword = $("#keyword").val();
	window.location = "/admin/carFactoryList?keyword="+keyword;
}