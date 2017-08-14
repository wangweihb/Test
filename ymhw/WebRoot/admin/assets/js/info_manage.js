var deleteUser = function(id) {
	$.getJSON("/admin/portInfoDelete", {"id" : id}, function(code) {
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
	window.location = "/admin/infoList?type=" + type;
}

/**
 * 根据关键字搜索用户
 */
var searchInfo = function() {
	var keyword = $("#keyword").val();
	window.location = "/admin/infoList?keyword="+keyword;
}
