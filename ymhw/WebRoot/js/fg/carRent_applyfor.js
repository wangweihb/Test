$(function() {
	$(".increase").click(function() {
		//数量自增
		var t = $(this).parent().find('input[class*=valuation_box]');
		var curNum = parseInt(t.val()) + 1;
		t.val(curNum)
		
		$("#carNum").html(curNum);
		
		//重新计算应付金额
		var price = parseInt($("#price").text());
		var days = parseInt($("#days").text());
		$("#total").html(price * curNum * days);
		$("#cost").attr("value", price * curNum * days);
		
	})
	$(".min").click(function() {
		var t = $(this).parent().find('input[class*=valuation_box]');
		var curNum = parseInt(t.val()) - 1;
		if(curNum < 1) {
			alert("租车数量最小值不能小于1");
			curNum = 1;
		}
		t.val(curNum);
		
		$("#carNum").html(curNum);
		
		//重新计算应付金额
		var price = parseInt($("#price").text());
		var days = parseInt($("#days").text());
		$("#total").html(price * curNum * days);
		$("#cost").attr("value", price * curNum * days);
	})

	function setTotal() {
		var s = 0;
		$("#valuation td").each(function() {
			s += parseInt($(this).find('input[class*=valuation_box]').val()) * parseFloat($(this).find('span[class*=price]').text());
		});
		$("#total").html(s.toFixed(2));
	}
	//setTotal();
	
	var curDate = new Date();
	var curTime = curDate.toLocaleDateString();
	$("#curTime").html(curTime);

})

var next = function() {
	$("#step1Contentli").removeClass();
	$("#step2Contentli").addClass("current active_step");
	
	$("#step1Content").attr("style","display:none;");
	$("#step2Content").attr("style","display:block;");
	
	$("#signup-a").attr("href","javascript:signup();");
}

var renext = function() {
	$("#step2Contentli").removeClass();
	$("#step3Contentli").addClass("current active_step");
	
	$("#step2Content").attr("style","display:none;");
	$("#step3Content").attr("style","display:block;");
}

var signup = function() {
	var actId = $("#actId").val();
	var adultNum = parseInt($(".adult-num").text());
	var childNum = parseInt($(".child-num").text());
	var lmCertType = $("#lmCertType").val();
	var lmname = $("#lmname").val();
	var lmNumber = $("#lmNumber").val();
	var lmCertNumber = $("#lmCertNumber").val();
	var remark = $("#remark").val();
	var proName = $("#proName").val();
	var proDesc = $("#proDesc").val();
	var cost = $("#total").text();
	
	if(lmname != '' && lmNumber != '' && lmCertNumber != '' && isMobileNumber(lmNumber)){
		$("#signupForm").attr("action", "/actbill/generateBill?adultNum="+adultNum+"&childNum="+childNum+"&cost="+cost);
		$("#signupForm").submit();
//		$.post("/actbill/generateBill",{"actId":actId,"lmCertType":lmCertType,"lmname":lmname,
//		"lmNumber":lmNumber,"lmCertNumber":lmCertNumber,"remark":remark,
//		"proName" : proName,"proDesc" : proDesc,"adultNum" : adultNum, "childNum" : childNum, "cost" :cost});
//		window.location = "/actbill/generateBill?actId="+actId+"&adultNum="+adultNum+"&childNum="+childNum+"&lmCertType="+lmCertType +
//		"&lmname="+encodeURIComponent(lmname)+"&lmNumber="+lmNumber+"&lmCertNumber="+lmCertNumber+"&remark="+encodeURIComponent(remark)+
//		"&proName="+encodeURIComponent(proName)+"&proDesc="+encodeURIComponent(proDesc)+"&cost="+cost;
	} else if(!isMobileNumber(lmNumber.trim())) {
		alert('手机号格式不对!');
	} else {
		alert("您的订单信息填写不完整!");
	}
}

var toOrder = function() {
	var lmname = $("#lmname").val();
	var lmnameIdcard = $("#lmnameIdcard").val();
	var lmnameTel = $("#lmnameTel").val();
	
	if(isNullOrEmpty(lmname) || isNullOrEmpty(lmnameIdcard) || isNullOrEmpty(lmnameTel)) {
		alert("您的订单信息填写不完整!");
	} else if(!isMobileNumber(lmnameTel.trim())) {
		alert('手机号格式不对!');
	} else {
		$("#carrentForm").attr("action", "/carRent/repay");
		$("#carrentForm").submit();
	}
}
