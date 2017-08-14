$(function() {
	// 人气推荐中内容 start
	$("#commonRecommend").empty();
	var top = '<div class="ym-sidelist-nav">'+
                    '<div class="ym-sidelist-tite">人气推荐</div>'+
                    '<div class="ym-sidelist-item hd">'+
                        '<ul>'+
                            '<li class="">租车</li>'+
                            '<li class="">活动 </li>'+
                            '<li class="on">目的地</li>'+
                        '</ul>'+
                    '</div>'+
                '</div>';
    var contentStart = '<div class="bd ym-sidelist">';
    var carUl = '<ul class="clearfix" style="display: block;" id="suggestCars"></ul>';
	var actUl = '<ul class="clearfix" style="display: none;" id="suggestActs"></ul>';
	var infoUl = '<ul class="clearfix" style="display: none;" id="suggestDestinations"></ul>';
	var content = top + contentStart + carUl + actUl + infoUl + '</div>';
	$("#commonRecommend").append(content);
	
	//人气推荐中的周边目的地
	$.getJSON("/infoPath/portInfos", function(datas){
		$("#suggestDestinations").empty();
		$.each(datas, function(index) {
			var li= '<li>'+
                        '<a href="/infoPath">'+
                            '<img  class="ym-list-img"  src="'+datas[index].subjectImg+'">'+
                            '<div class="ym-list-main">'+
                                '<div class="list-txt">'+datas[index].subject+
                                '<br>'+datas[index].provice+datas[index].region+'</div>'+
                            '</div>'+
                        '</a>'+                                   
                    '</li>';
            $("#suggestDestinations").append(li);
		});
	});
	
	
	//人气推荐中的活动
	$.getJSON("/activity/portActs", function(datas){
		$("#suggestActs").empty();
		$.each(datas, function(index) {
			var li= '<li>'+
                        '<a href="/activity/actListByType?type=weekend">'+ 
                            '<img  class="ym-list-img"  src="'+datas[index].pic+'">'+
                            '<div class="ym-list-main">'+
                                '<div class="list-txt">'+datas[index].title+ 
                                '<br>'+datas[index].cost+'元</div>'+
                            '</div>'+
                        '</a>'+                                   
                    '</li>';
            $("#suggestActs").append(li);
		});
	});
	
	
	//人气推荐中的租车信息
	$.getJSON("/carRent/portCars", function(datas){
		$("#suggestCars").empty();
		$.each(datas, function(index) {
			var gearTxt = "";
			if(0==datas[index].gearType) {
				gearTxt = "手动档";
			}
			if(1==datas[index].gearType) {
				gearTxt = "手动档";
			}
			var li= '<li>'+
                        '<a href="/carRent">'+
                            '<img  class="ym-list-img"  src="'+datas[index].showpic+'">'+
                            '<div class="ym-list-main">'+
                                '<div class="list-txt">'+datas[index].name+' '+datas[index].sweptVolume+
                                '<br>'+datas[index].price+'元/天</div>'+
                            '</div>'+
                        '</a>'+                                   
                    '</li>';
            $("#suggestCars").append(li);
		});
	});
	jQuery(".tabox").slide({delayTime: 0});
	
	// 人气推荐中内容 end
});
