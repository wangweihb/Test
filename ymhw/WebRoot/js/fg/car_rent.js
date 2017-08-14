$(function(){
	$(':input').labelauty();
	
	$('.datepick_ws').datetimepicker(
		{
			  lang:"ch",           //语言选择中文
		      format:"Y-m-d",      //格式化日期
		      timepicker:false,    //关闭时间选项
		      yearStart:2000,      //设置最小年份
		      yearEnd:2080,        //设置最大年份
		      todayButton:true    //关闭选择今天按钮
		}
	);
	var now = new Date();
	$("#rentStartTime").val(now.getFullYear() +"-"+ (now.getMonth()+1) + "-" + now.getDate());
	now.setTime(now.getTime()+1*24*60*60*1000);
	$("#rentEndTime").val(now.getFullYear() +"-"+ (now.getMonth()+1) + "-" + now.getDate());
	
	//价格选择插件
	$('.range-slider').jRange({
		from: 0,
		to: 1500,
		step: 100,
		scale: [0,300,600,900,1200,1500],
		format: '%s',
		width: 268,
		showLabels: true,
		isRange : true
	});
	
	//加载租车门店信息
	$.getJSON("/carFactory/portAllFactories", function(datas) {
		$("#cr-main-factory").empty();
		$("#cr-main-factory").append('<option value="0"> 不限</option>');
		$.each(datas, function(i) {
			var opt = '<option value="'+datas[i].id+'"> '+datas[i].region+datas[i].name+'</option>';
			$("#cr-main-factory").append(opt);
		});
	});
		
	//加载车辆信息
	$.getJSON("/carRent/portCars", function(datas) {
		$("#carInfoDiv").empty();
		$.each(datas, function(i) {
			var gearType = '手动';
			if (datas[i].gearType == '1') {
				gearType = '自动';
			}
			//今日可租状态 0可租 1-租满
			var thea = '<a class="cr-m-r-zc" target="_blank" href="javascript:toApplyfor('+datas[i].id+');" >租车</a> ';
			if('1'==datas[i].status) {
				thea = '<a class="cr-m-r-zc-full"  >租满</a> ';
			}
			//车型 0-不限 1-舒适型轿车、2-经济型轿车、3-MVP、4-豪华型轿车、5-SUV、6-7座商务型轿车、7-旅游大巴车 
			var typeStr = '不限';
			var type = datas[i].type;
			if (type == 1) {
				typeStr = '舒适型轿车';
			} else if (type == 2) {
				typeStr = '经济型轿车';
			} else if (type == 3) {
				typeStr = 'MVP';
			} else if (type == 4) {
				typeStr = '豪华型轿车';
			} else if (type == 5) {
				typeStr = 'SUV';
			} else if (type == 6) {
				typeStr = '商务型轿车';
			} else if (type == 7) {
				typeStr = '旅游大巴车';
			}
			var oneCarInfo = '<div class="cr-m-r-pj">'+
						        '<div class="cr-m-r-img"><img src="'+datas[i].showpic+'"></div>'+
						        '<div class="cr-m-r-txt">'+
						            '<div class="cr-m-r-nav">'+datas[i].name+'</div>'+
						            '<div class="cr-m-r-main">'+datas[i].sweptVolume+' | '+gearType+' | 乘坐'+datas[i].cntLimit+'人 </div>'+
						            '<ul>'+
						                '<li class="cr-h1">热门</li>'+
						                '<li class="cr-h2">'+typeStr+'</li>'+
						            '</ul>'+
						        '</div>'+
						        '<div class="cr-m-r-money"><i>限时优惠</i><br>'+
						          		  '日均价¥<span>'+datas[i].price+' </span>元'+
						        '</div>'+
						        '<div class="cr-m-r-btn"> '+
						        	thea+
						        '</div>'+
						    '</div>';
			$("#carInfoDiv").append(oneCarInfo);
		});
	});
	
	//车型选择
	$(".cr-m-l-t-m li").click(function() {
		var type = $(this).index();
		var obj = $(this).children()[0];
		for(var i = 0; i < 8; i++) {
			if(type != i) {
				$("#" + i).attr("class", "off");
				$("#" + i).children()[0].className = "cr-m-l-img cr-img-"+i;
			}
		}
		var listatus = $(this).attr("class");
		if(listatus =="on") {
			obj.className = obj.className.substring(0, obj.className.indexOf("-click"));
			$(this).attr("class", "off");
		} else if(listatus =="off") {
			obj.className = obj.className + "-click";
			$(this).attr("class", "on");
		}
		
		var prices = $("#price").val();
		var gearType = $("input[name='szp']:checked").val();
		var factoryId = $("#cr-main-factory").val();
		var lis = $(".cr-m-l-t-m li.on");
		var rentStartTime = $("#rentStartTime").val();
		var types = "";
		$.each(lis, function(i) {
			var type = lis[i].id;
			types += type + ",";
		});
		if(types != "") {
			types = types.substring(0, types.length-1);
		}
		$.getJSON("/carRent/portCarsByCondition",{"gearType" : gearType, "types" :types, "prices":prices, "factoryId":factoryId, "rentStartTime":rentStartTime}, function(datas) {
			$("#carInfoDiv").empty();
			$.each(datas, function(i) {
				var gearType = '手动';
				if (datas[i].gearType == '1') {
					gearType = '自动';
				}
				//今日可租状态 0可租 1-租满
				var thea = '<a class="cr-m-r-zc" target="_blank" href="javascript:toApplyfor('+datas[i].id+');" >租车</a> ';
				if('1'==datas[i].status) {
					thea = '<a class="cr-m-r-zc-full" >租满</a> ';
				}
				//车型 0-不限 1-舒适型轿车、2-经济型轿车、3-MVP、4-豪华型轿车、5-SUV、6-7座商务型轿车、7-旅游大巴车 
				var typeStr = '不限';
				var type = datas[i].type;
				if (type == 1) {
					typeStr = '舒适型轿车';
				} else if (type == 2) {
					typeStr = '经济型轿车';
				} else if (type == 3) {
					typeStr = 'MVP';
				} else if (type == 4) {
					typeStr = '豪华型轿车';
				} else if (type == 5) {
					typeStr = 'SUV';
				} else if (type == 6) {
					typeStr = '商务型轿车';
				} else if (type == 7) {
					typeStr = '旅游大巴车';
				}
				var oneCarInfo = '<div class="cr-m-r-pj">'+
							        '<div class="cr-m-r-img"><img src="'+datas[i].showpic+'"></div>'+
							        '<div class="cr-m-r-txt">'+
							            '<div class="cr-m-r-nav">'+datas[i].name+'</div>'+
							            '<div class="cr-m-r-main">'+datas[i].sweptVolume+' | '+gearType+' | 乘坐'+datas[i].cntLimit+'人 </div>'+
							            '<ul>'+
							                '<li class="cr-h1">热门</li>'+
							                '<li class="cr-h2">'+typeStr+'</li>'+
							            '</ul>'+
							        '</div>'+
							        '<div class="cr-m-r-money"><i>限时优惠</i><br>'+
							          		  '日均价¥<span>'+datas[i].price+' </span>元'+
							        '</div>'+
							        '<div class="cr-m-r-btn"> '+
							        	thea+
							    '</div>';
				$("#carInfoDiv").append(oneCarInfo);
			});
		});
		
	});
		
	//租车中 根据档位类型进行查询
	$("input[name='szp']").click(function() {
		//车型选择所取的值
		var lis = $(".cr-m-l-t-m li.on");
		var types = "";
		$.each(lis, function(i) {
			var type = lis[i].id;
			types += type + ",";
		});
		if(types != "") {
			types = types.substring(0, types.length-1);
		}
	
		var gearType = $(this).val();
		var prices = $("#price").val();
		var factoryId = $("#cr-main-factory").val();
		var rentStartTime = $("#rentStartTime").val();
//		alert(gearType + " "  + prices + " " + factoryId +"　 "　 +　types);
		$.getJSON("/carRent/portCarsByCondition",{"gearType" : gearType, "types" :types, "prices":prices, "factoryId":factoryId,"rentStartTime":rentStartTime}, function(datas) {
			$("#carInfoDiv").empty();
			$.each(datas, function(i) {
				var gearType = '手动';
				if (datas[i].gearType == '1') {
					gearType = '自动';
				}
				//今日可租状态 0可租 1-租满
				var thea = '<a class="cr-m-r-zc" target="_blank" href="javascript:toApplyfor('+datas[i].id+');" >租车</a> ';
				if('1'==datas[i].status) {
					thea = '<a class="cr-m-r-zc-full" >租满</a> ';
				}
				//车型 0-不限 1-舒适型轿车、2-经济型轿车、3-MVP、4-豪华型轿车、5-SUV、6-7座商务型轿车、7-旅游大巴车 
				var typeStr = '不限';
				var type = datas[i].type;
				if (type == 1) {
					typeStr = '舒适型轿车';
				} else if (type == 2) {
					typeStr = '经济型轿车';
				} else if (type == 3) {
					typeStr = 'MVP';
				} else if (type == 4) {
					typeStr = '豪华型轿车';
				} else if (type == 5) {
					typeStr = 'SUV';
				} else if (type == 6) {
					typeStr = '商务型轿车';
				} else if (type == 7) {
					typeStr = '旅游大巴车';
				}
				var oneCarInfo = '<div class="cr-m-r-pj">'+
							        '<div class="cr-m-r-img"><img src="'+datas[i].showpic+'"></div>'+
							        '<div class="cr-m-r-txt">'+
							            '<div class="cr-m-r-nav">'+datas[i].name+'</div>'+
							            '<div class="cr-m-r-main">'+datas[i].sweptVolume+' | '+gearType+' | 乘坐'+datas[i].cntLimit+'人 </div>'+
							            '<ul>'+
							                '<li class="cr-h1">热门</li>'+
							                '<li class="cr-h2">'+typeStr+'</li>'+
							            '</ul>'+
							        '</div>'+
							        '<div class="cr-m-r-money"><i>限时优惠</i><br>'+
							          		  '日均价¥<span>'+datas[i].price+' </span>元'+
							        '</div>'+
							        '<div class="cr-m-r-btn"> '+
							        	thea+
							        	'</div>'+
							    '</div>';
				$("#carInfoDiv").append(oneCarInfo);
			});
		});
	});
	
	//租装备 中按照各种条件进行查询
	$("input[name='type']").click(function() {
		var types = "";
		$("input[name='type']:checked").each(function(){
			types += $(this).val() + ",";
		});
		$.getJSON("/equip/portEquips", {"types" : types}, function(datas){
			$("#equipUl").empty();
			if(datas.length == 0) {
				$("#equipUl").append('<li class="con-z"><br><p>暂时没有此类型的装备！</p></li>');
			}
			$.each(datas, function(i) {
				var li = '<li class="con-z">' +
							'<a href="javascript:show(' + datas[i].id + ')">' +
								'<img src="' + datas[i].equipPic + '">' +
								'<div class="discount">' + 
									'<p>租金：' + datas[i].rent + '元/天</p>' +
								'</div>' +
								'<p class="absolute">' + datas[i].name + ' 押金：' + datas[i].deposit + ' ' + datas[i].provice +datas[i].city + '</p>' +
							'</a>' +
						'</li>';
				$("#equipUl").append(li);
			});
		});
	});
	
	//周边目的地 中按照各种条件进行查询
	$("input[name='infoType']").click(function() {
		
		var types = "";
		var infoTags = "";
		$("input[name='infoType']:checked").each(function(){
			types += $(this).val() + ",";
		});
		$("input[name='infoTag']:checked").each(function(){
			infoTags += $(this).val() + ",";
		});
		if(types.indexOf("0")!= -1) {
			types = "";
		}
		if(infoTags.indexOf("0")!= -1) {
			infoTags = "";
		}
		$.getJSON("/infoPath/portInfosByCondition", {"infoTypes" : types, "infoTags" : infoTags}, function(datas){
			$("#infoUl").empty();
			if(datas.length == 0) {
				$("#infoUl").append('<li class="con-z"><br><p>暂时没有相关的周边目的地！</p></li>');
			}
			$.each(datas, function(i) {
				var s = "";
				if(datas[i].type == 1) {
					s = "休闲旅游";
				} else if(datas[i].type == 2) {
					s = "激情户外";
				} else if(datas[i].type == 3) {
					s = "家庭活动";
				} else if(datas[i].type == 4) {
					s = "民宿营地";
				}
				
				var li = '<li class="con-z">'+
								'<a href="/infoPath/detail?id='+datas[i].info_id+'">'+
									'<img src="'+datas[i].img+'">'+
									'<div class="discount">'+
										'<p>'+s+'</p>'+
										'</div>'+
									'<p class="absolute">'+datas[i].subject+ '&nbsp;&nbsp;'+
										datas[i].provice + datas[i].region+'</p>'+
								'</a>'+
							'</li>';
				$("#infoUl").append(li);
			});
		});
	});
	
	$("input[name='infoTag']").click(function() {
		
		var types = "";
		var infoTags = "";
		$("input[name='infoType']:checked").each(function(){
			types += $(this).val() + ",";
		});
		$("input[name='infoTag']:checked").each(function(){
			infoTags += $(this).val() + ",";
		});
		
		if(types.indexOf("0")!= -1) {
			types = "";
		}
		if(infoTags.indexOf("0")!= -1) {
			infoTags = "";
		}
		$.getJSON("/infoPath/portInfosByCondition", {"infoTypes" : types, "infoTags" : infoTags}, function(datas){
			$("#infoUl").empty();
			if(datas.length == 0) {
				$("#infoUl").append('<li class="con-z"><br><p>暂时没有相关的周边目的地！</p></li>');
			}
			$.each(datas, function(i) {
				var s = "";
				if(datas[i].type == 1) {
					s = "休闲旅游";
				} else if(datas[i].type == 2) {
					s = "激情户外";
				} else if(datas[i].type == 3) {
					s = "家庭活动";
				} else if(datas[i].type == 4) {
					s = "民宿营地";
				}
				
				var li = '<li class="con-z">'+
								'<a href="/infoPath/detail?id='+datas[i].info_id+'">'+
									'<img src="'+datas[i].img+'">'+
									'<div class="discount">'+
										'<p>'+s+'</p>'+
										'</div>'+
									'<p class="absolute">'+datas[i].subject+ '&nbsp;&nbsp;'+
										datas[i].provice+datas[i].region+'</p>'+
								'</a>'+
							'</li>';
				$("#infoUl").append(li);
			});
		});
	});
	
	$("input[name='region']").click(function() {
		
		var regions = "";
		$("input[name='region']:checked").each(function(){
			regions += $(this).val() + ",";
		});
		
		$.getJSON("/infoPath/portInfosByCondition2", {"regions" : regions}, function(datas){
			$("#infoUl").empty();
			if(datas.length == 0) {
				$("#infoUl").append('<li class="con-z"><br><p>暂时没有相关的周边目的地！</p></li>');
			}
			$.each(datas, function(i) {
				var s = "";
				if(datas[i].type == 1) {
					s = "休闲旅游";
				} else if(datas[i].type == 2) {
					s = "激情户外";
				} else if(datas[i].type == 3) {
					s = "家庭活动";
				} else if(datas[i].type == 4) {
					s = "民宿营地";
				}
				
				var li = '<li class="con-z">'+
								'<a href="/infoPath/detail?id='+datas[i].info_id+'">'+
									'<img src="'+datas[i].img+'">'+
									'<div class="discount">'+
										'<p>'+s+'</p>'+
										'</div>'+
									'<p class="absolute">'+datas[i].subject+ '&nbsp;&nbsp;'+
										datas[i].provice+datas[i].region+'</p>'+
								'</a>'+
							'</li>';
				$("#infoUl").append(li);
			});
		});
	});
});

/**
 * 根据选择的各种条件选车
 */
var chooseCar = function() {
	//车型选择所取的值
	var lis = $(".cr-m-l-t-m li.on");
	var types = "";
	$.each(lis, function(i) {
		var type = lis[i].id;
		types += type + ",";
	});
	if(types != "") {
		types = types.substring(0, types.length-1);
	}

	var gearType = $("input[name='szp']:checked").val();
	var prices = $("#price").val();
	var factoryId = $("#cr-main-factory").val();
	var rentStartTime = $("#rentStartTime").val();
//	alert(gearType + " "  + prices + " " + factoryId +"　 "　 +　types);
	$.getJSON("/carRent/portCarsByCondition",{"gearType" : gearType, "types" :types, "prices":prices, "factoryId":factoryId,"rentStartTime":rentStartTime}, function(datas) {
		$("#carInfoDiv").empty();
		$.each(datas, function(i) {
			var gearType = '手动';
			if (datas[i].gearType == '1') {
				gearType = '自动';
			}
			//今日可租状态 0可租 1-租满
			var thea = '<a class="cr-m-r-zc" target="_blank" href="javascript:toApplyfor('+datas[i].id+');" >租车</a> ';
			if('1'==datas[i].status) {
				thea = '<a class="cr-m-r-zc-full" >租满</a> ';
			}
			//车型 0-不限 1-舒适型轿车、2-经济型轿车、3-MVP、4-豪华型轿车、5-SUV、6-7座商务型轿车、7-旅游大巴车 
			var typeStr = '不限';
			var type = datas[i].type;
			if (type == 1) {
				typeStr = '舒适型轿车';
			} else if (type == 2) {
				typeStr = '经济型轿车';
			} else if (type == 3) {
				typeStr = 'MVP';
			} else if (type == 4) {
				typeStr = '豪华型轿车';
			} else if (type == 5) {
				typeStr = 'SUV';
			} else if (type == 6) {
				typeStr = '商务型轿车';
			} else if (type == 7) {
				typeStr = '旅游大巴车';
			}
			var oneCarInfo = '<div class="cr-m-r-pj">'+
						        '<div class="cr-m-r-img"><img src="'+datas[i].showpic+'"></div>'+
						        '<div class="cr-m-r-txt">'+
						            '<div class="cr-m-r-nav">'+datas[i].name+'</div>'+
						            '<div class="cr-m-r-main">'+datas[i].sweptVolume+' | '+gearType+' | 乘坐'+datas[i].cntLimit+'人 </div>'+
						            '<ul>'+
						                '<li class="cr-h1">热门</li>'+
						                '<li class="cr-h2">'+typeStr+'</li>'+
						            '</ul>'+
						        '</div>'+
						        '<div class="cr-m-r-money"><i>限时优惠</i><br>'+
						          		  '日均价¥<span>'+datas[i].price+' </span>元'+
						        '</div>'+
						        '<div class="cr-m-r-btn"> '+
						        	thea+
						        	'</div>'+
						    '</div>';
			$("#carInfoDiv").append(oneCarInfo);
		});
	});
}

/**
 * 点击进去某一项
 */
var toStep = function(step)
{
	$("#step1Content").attr("style","display:none");
	$("#step1Contentli").attr("class","");
	
	$("#step2Content").attr("style","display:none");
	$("#step2Contentli").attr("class","");
	
	$("#step3Content").attr("style","display:none");
	$("#step3Contentli").attr("class","");
	
	$("#step4Content").attr("style","display:none");
	$("#step4Contentli").attr("class","");
	
	$("#step" + step + "Content").attr("style","display:block");
	$("#step" + step + "Contentli").attr("class","active_step");
}

var show = function(id)
{
	$.getJSON("/equip/portDisplay",{"equipId":id},function(data) {
		var objStr = JSON.stringify(data);
		var obj = jQuery.parseJSON(objStr);
		$("#name").empty();
		$("#name").append(obj.name);
		$("#equipPic").attr("src",obj.equipPic);
		
		$("#showPrice").empty();
		$("#showPrice").append(obj.showPrice);
		
		$("#region").empty();
		$("#region").append(obj.provice + obj.city);
		
		$("#sendWay").empty();
		if(obj.sendWay == 1){
			$("#sendWay").append("自取");
		}else if(obj.sendWay == 2){
			$("#sendWay").append("可送");
		}
		
		$("#rentWay").empty();
		if(obj.sendWay == 1){
			$("#rentWay").append("只租");
		}else if(obj.rentWay == 2){
			$("#rentWay").append("可卖");
		}
		
		$("#supplyTension").empty();
		if(obj.supplyTension == 1){
			$("#supplyTension").append("有货");
		}else if(obj.supplyTension == 2){
			$("#supplyTension").append("紧缺");
		}
		
		$("#damage").empty();
		$("#damage").append(obj.damage);
		
		$("#qq").empty();
		$("#qq").append(obj.qq);
		
		$("#fixnumber").empty();
		$("#fixnumber").append(obj.fixnumber);
		
		$("#telphone").empty();
		$("#telphone").append(obj.telephone);
		
	});
	$(".equipment-parinforma").css('display','block');
	$(".parinforma-body").css('display','block');
}

var clo = function()
{
	$(".equipment-parinforma").css('display','none');
	$(".parinforma-body").css('display','none');
}


/**
 * 下单
 * @param {Object} carId
 */
var toApplyfor = function(carId) {
	var rentStartTime = $("#rentStartTime").val();
	var rentEndTime = $("#rentEndTime").val();
	window.location = "/carRent/applyforPath?carId=" + carId + "&rentStartTime=" +rentStartTime+ "&rentEndTime=" +rentEndTime;
}
