$(function() {
	//省市区联动的设置
	var $distpicker = $('#distpicker');
  	$distpicker.distpicker({
    	province: '湖北省',
    	city: '恩施土家族苗族自治州',
    	district: '恩施市'
  	});
  	
//	$('.datepick_carRent').datetimepicker(
//		{
//			  lang:"ch",           //语言选择中文
//		      format:"Y-m-d",      //格式化日期
//		      timepicker:false,    //关闭时间选项
//		      yearStart:2000,     //设置最小年份
//		      yearEnd:2080,        //设置最大年份
//		      todayButton:false    //关闭选择今天按钮
//		}
//	);
	
	$.getJSON("/manage_carRent/portCarFactories", function(datas) {
		$("#factoryId").empty();
		$.each(datas, function(i) {
			var option = '<option value="'+datas[i].id+'">'+datas[i].name+'</option>';
			$("#factoryId").append(option);
		});
	});
	
	$("#showpic").change(function(){
	  var objUrl = getObjectURL(this.files[0]) ;
	  if (objUrl) {
	    // 在这里修改图片的地址属性
	    $("#tmpImg").attr("src", objUrl) ;
	  }
	}) ;
});

/**
 * 建立一个可存取到该file的URL
 * @param {Object} file
 */
function getObjectURL(file) {
  var url = null ; 
  // 下面函数执行的效果是一样的，只是需要针对不同的浏览器执行不同的 js 函数而已
  if (window.createObjectURL!=undefined) { // basic
    url = window.createObjectURL(file) ;
  } else if (window.URL!=undefined) { // mozilla(firefox)
    url = window.URL.createObjectURL(file) ;
  } else if (window.webkitURL!=undefined) { // webkit or chrome
    url = window.webkitURL.createObjectURL(file) ;
  }
  return url ;
}

var sub = function() {
	var carName = $("#carName").val();
	var cntLimit = $("#cntLimit").val();
	var price = $("#price").val();
	if(isNullOrEmpty($.trim(carName))) {
		alert("车辆名称不能为空！");
	} else if(!isNum(cntLimit)) {
		alert("可乘坐人数的格式错误！");
	} else if(!isNum(price)) {
		alert("价格的格式错误！");
	} else {
		$("#main").submit();
	}
}

