$(function() {
	//加载周边目的地信息的标题
	$.getJSON("/manage/portInfoTiles",function(datas){
		$("#deflaut_dest").empty();
		$("#deflaut_dest").append("<option value='0'>请选择</option>")
		$.each(datas, function(index) {
			$("#deflaut_dest").append("<option value='"+datas[index].info_id+"'>"+datas[index].subject+"</option>")
		});
	});
	
//	$('#serviceStartTime').timepicker({ 'timeFormat': 'H:i' });
//	$('#serviceEndTime').timepicker({ 'timeFormat': 'H:i' });
    //富文本编辑框的配置
	var prductbuyEditor = new wangEditor("product_buy");
	prductbuyEditor.config.uploadImgUrl = '/manage/wangUpload_product_prductbuy';
	prductbuyEditor.config.uploadImgFileName = 'fileName_product_prductbuy';
	prductbuyEditor.$editorContainer.css('z-index', 40);
	prductbuyEditor.create();
	
	var productdetailEditor = new wangEditor("product_detail");
	productdetailEditor.config.uploadImgUrl = '/manage/wangUpload_product_productdetail';
	productdetailEditor.config.uploadImgFileName = 'fileName_product_productdetail';
	productdetailEditor.$editorContainer.css('z-index', 30);
	productdetailEditor.create();
		
	$("#pic").change(function(){
	  var objUrl = getObjectURL(this.files[0]) ;
	  if (objUrl) {
	    // 在这里修改图片的地址属性
	    $("#tmpImg").attr("src", objUrl) ;
	  }
	});
	
	$("#subjectImg").change(function(){
	  $("#imgContainer").empty();
	  for (var i = 0; i　<　this.files.length; i++) {
	  	var objUrl = getObjectURL(this.files[i]) ;
	  	var oneimg = '<img src="'+objUrl+'" width="180" height="145"/>&nbsp;&nbsp;';
	  	$("#imgContainer").append(oneimg);
	  }
	});
});

var getFarms = function(obj) {
	var infoId = $(obj).val();
	//根据周边目的地ID加载所管辖的所有农家乐
	$.getJSON("/manage/portFarmsByInfoId",{"infoId" : infoId}, function(datas){
		$("#deflaut_farm").append("<option value='0'>请选择</option>")
		$("#deflaut_farm").empty();
		$.each(datas, function(index) {
			$("#deflaut_farm").append("<option value='"+datas[index].id+"'>"+datas[index].shopName+"</option>")
		});
	});
}

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


var changePic = function(obj) {
	var file = obj.files[0];
	const reader = new FileReader();
    reader.onload = function(e) {
    	var str = e.target.result;
        $(obj).next().attr("src", str);
        $(obj).next().next().attr("value", str);
    }
    reader.readAsDataURL(file);
}

/**
 * 产品上传
 */
var addProduct = function() {
	var infoId = $("#deflaut_dest").val();
	var farmId = $("#deflaut_farm").val();
	var proTitle = $("#title").val();
	var price = $("#price").val();
	if(isNullOrEmpty(infoId)) {
		alert("必须选择对应的周边目的地!");
		return;
	}
	if(isNullOrEmpty(farmId)) {
		alert("必须选择对应的农家乐!");
		return;
	}
	if(isNullOrEmpty(proTitle)) {
		alert("必须填写产品标题!");
		return;
	}
	if(isNullOrEmpty(price) || !isNum(price)) {
		alert("价格填写格式出错!");
		return;
	}
	$("#proaddForm").attr("action", "/manage_product/add");
	$("#proaddForm").submit();
	
}
