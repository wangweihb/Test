$(function() {
	$(".increase").click(function() {
		//数量自增
		var t = $(this).parent().find('input[class*=valuation_box]');
		var curNum = parseInt(t.val()) + 1;
		t.val(curNum)
		
		$("#proNum").html(curNum);
		
		//重新计算应付金额
		var price = parseInt($("#price").text());
		$("#total").html(price * curNum);
		$("#cost").attr("value", price * curNum);
		
	})
	$(".min").click(function() {
		var t = $(this).parent().find('input[class*=valuation_box]');
		var curNum = parseInt(t.val()) - 1;
		if(curNum < 1) {
			alert("租车数量最小值不能小于1");
			curNum = 1;
		}
		t.val(curNum);
		
		$("#proNum").html(curNum);
		
		//重新计算应付金额
		var price = parseInt($("#price").text());
		$("#total").html(price * curNum);
		$("#cost").attr("value", price * curNum);
	})

	function setTotal() {
		var s = 0;
		$("#valuation td").each(function() {
			s += parseInt($(this).find('input[class*=valuation_box]').val()) * parseFloat($(this).find('span[class*=price]').text());
		});
		$("#total").html(s.toFixed(2));
	}

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

var toOrder = function() {
	var lmname = $("#lmname").val();
	var lmnameTel = $("#lmnameTel").val();
	
	if(isNullOrEmpty(lmname) || isNullOrEmpty(lmnameTel)) {
		alert("您的订单信息填写不完整!");
	} else if(!isMobileNumber(lmnameTel.trim())) {
		alert('手机号格式不对!');
	} else {
		$("#carrentForm").attr("action", "/infoPath/productRepay");
		$("#carrentForm").submit();
	}
}
