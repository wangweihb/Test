$(function() {
	//富文本编辑框的配置
	var tranEditor = new wangEditor('transportTips');
	tranEditor.config.uploadImgUrl = '/manage/wangUpload_hotel_transportInfo';
	tranEditor.config.uploadImgFileName = 'fileName_hotel_transportInfo';
	tranEditor.$editorContainer.css('z-index', 40);
	tranEditor.create();
	
	var feeEditor = new wangEditor('feePart');
	feeEditor.config.uploadImgUrl = '/manage/wangUpload_hotel_feeInfo';
	feeEditor.config.uploadImgFileName = 'fileName_hotel_feeInfo';
	feeEditor.$editorContainer.css('z-index', 30);
	feeEditor.create();
	
	var serviceEditor = new wangEditor('aboutService');
	serviceEditor.config.uploadImgUrl = '/manage/wangUpload_hotel_aboutService';
	serviceEditor.config.uploadImgFileName = 'fileName_hotel_aboutService';
	serviceEditor.$editorContainer.css('z-index', 20);
	serviceEditor.create();
	
	//省市区联动的设置
	var $distpicker = $('#distpicker');
  	$distpicker.distpicker({
    	province: '湖北省',
    	city: '恩施土家族苗族自治州',
    	district: '恩施市'
  	});
  	
  	$("#pic").change(function(){
	  var objUrl = getObjectURL(this.files[0]) ;
	  if (objUrl) {
	    // 在这里修改图片的地址属性
	    $("#tmpImg").attr("src", objUrl) ;
	  }
	}) ;
	
})

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
