$(function() {
	$('#serviceStartTime').timepicker({ 'timeFormat': 'H:i' });
	$('#serviceEndTime').timepicker({ 'timeFormat': 'H:i' });
    //富文本编辑框的配置
	var feeFarmEditor = new wangEditor("feePart_farm");
	feeFarmEditor.config.uploadImgUrl = '/manage/wangUpload_farm_feeInfo';
	feeFarmEditor.config.uploadImgFileName = 'fileName_farm_feeInfo';
	feeFarmEditor.$editorContainer.css('z-index', 40);
	feeFarmEditor.create();
	
//	var productFarmEditor = new wangEditor("productDesc_farm");
//	productFarmEditor.config.uploadImgUrl = '/manage/wangUpload_farm_productInfo';
//	productFarmEditor.config.uploadImgFileName = 'fileName_farm_productInfo';
//	productFarmEditor.$editorContainer.css('z-index', 30);
//	productFarmEditor.create();
	
	$("#pic").change(function(){
	  var objUrl = getObjectURL(this.files[0]) ;
	  if (objUrl) {
	    // 在这里修改图片的地址属性
	    $("#tmpImg").attr("src", objUrl) ;
	  }
	});
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


var changePic = function(obj) {
//	var objUrl = getObjectURL(obj.files[0]) ;
//	  if (objUrl) {
//	    // 在这里修改图片的地址属性
//	    $(obj).next().attr("src", objUrl);
//	  }
	//备注： 前端预览图片的两种方式，用Base64和objectURL(bolb)
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
 * 添加一个新的子模块
 * @param {Object} id
 */
var addModule = function(id) {
	var newModule = $("#nullModule").clone();
	newModule.attr("style", "display:block;");
	$("#allchild").append(newModule);

	
}

/**
 * 删除子模块
 * @param {Object} id
 */
var remModule = function(id) {
	$(id).parent().remove();
}

var getBase64Image = function(img){
	var canvas = document.createElement("canvas");
	canvas.width = img.width;
	canvas.height = img.height;
	
	var ctx = canvas.getContext("2d");
	ctx.drawImage(img, 0, 0, img.width, img.height);
	var dataURL = canvas.toDataURL("image/png");
	return dataURL;
}
