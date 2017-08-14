var getFarms = function(obj) {
	var infoId = $(obj).val();
	//根据周边目的地ID加载所管辖的所有农家乐
	$.getJSON("/admin/portFarmsByInfoId",{"infoId" : infoId}, function(datas){
		$("#farmId").empty();
		$("#farmId").append("<option value='0'>请选择</option>")
		$.each(datas, function(index) {
			$("#farmId").append("<option value='"+datas[index].id+"'>"+datas[index].shopName+"</option>")
		});
	});
}