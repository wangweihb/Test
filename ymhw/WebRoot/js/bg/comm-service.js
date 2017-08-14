//页面加载时需要请求的数据
$(function(){
	
	//加载周边目的地信息的标题
	$.getJSON("/manage/portInfoTiles",function(datas){
		$("#deflaut_dest").empty();
		$.each(datas, function(index) {
			$("#deflaut_dest").append("<option value='"+datas[index].info_id+"'>"+datas[index].subject+"</option>")
		});
	});
});
