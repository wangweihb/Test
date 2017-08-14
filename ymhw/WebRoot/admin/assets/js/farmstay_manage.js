/**
 * 根据关键字搜索用户
 */
var searchFarmstay = function() {
	var keyword = $("#keyword").val();
	window.location = "/admin/farmstayList?keyword="+keyword;
}