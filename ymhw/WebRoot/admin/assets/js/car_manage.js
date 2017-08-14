var deleteUser = function(id) {
	$.getJSON("/admin/portCarDelete", {"id" : id}, function(code) {
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
	window.location = "/admin/carList?type=" + type;
}

var searchByFactory = function(e) {
	var factoryId = $(e).val();
	window.location = "/admin/carList?factoryId=" + factoryId;
}
/**
 * 根据关键字搜索用户
 */
var searchCar = function() {
	var keyword = $("#keyword").val();
	window.location = "/admin/carList?keyword="+keyword;
}