var deleteUser = function(id) {
	$.getJSON("/admin/portUserDelete", {"id" : id}, function(code) {
		if (code == true) {
			alert("已删除！");
		} else{
			alert("删除失败！");
		}
		//刷新本页
		window.location = window.location.href;
	});
}

var searchByRole = function(e) {
	var role = $(e).val();
	window.location = "/admin/userList?roleId=" + role;
}

/**
 * 根据关键字搜索用户
 */
var searchUser = function() {
	var keyword = $("#keyword").val();
	window.location = "/admin/userList?keyword="+keyword;
}
