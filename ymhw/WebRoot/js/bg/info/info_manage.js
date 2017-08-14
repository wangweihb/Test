$(function() {
	//省市区联动的设置
	var $distpicker = $('#distpicker');
  	$distpicker.distpicker({
    	province: '湖北省',
    	city: '恩施土家族苗族自治州',
    	district: '恩施市'
  	});
	
	//富文本编辑框的配置
	var editor1 = new wangEditor('contDesc');
	editor1.config.uploadImgUrl = '/manage/wangUpload';
	editor1.config.uploadImgFileName = 'ymFileName';
	// 避免上面编辑器的 dropPanel 被下面的编辑器遮挡，保证下面的编辑器的 z-index 要小于上面的编辑器
    editor1.$editorContainer.css('z-index', 50);
    editor1.create();
    // 初始化编辑器的内容
	editor1.$txt.html('<p>请输入内容的简要描述...</p>');
	
	var transportEditor = new wangEditor("transportInfo");
	transportEditor.config.uploadImgUrl = '/manage/wangUpload_transportInfo';
	transportEditor.config.uploadImgFileName = 'fileName_transportInfo';
	transportEditor.$editorContainer.css('z-index', 10);
	transportEditor.create();
	
	$('#openStartTime').timepicker({ 'timeFormat': 'H:i' });
	$('#openEndTime').timepicker({ 'timeFormat': 'H:i' });
	
	$("#subjectImg").change(function(){
	  $("#imgContainer").empty();
	  for (var i = 0; i　<　this.files.length; i++) {
	  	var objUrl = getObjectURL(this.files[i]) ;
	  	var oneimg = '<img src="'+objUrl+'" width="180" height="145"/>&nbsp;&nbsp;';
	  	$("#imgContainer").append(oneimg);
	  }
	}) ;
	
	$("#sub").click(function() {
		var infoTitle = $("#infoTitle").val();
		$("#main").submit();
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

/**
 * 添加一个新的子模块
 * @param {Object} id
 */
var addModule = function(id) {
	var newModule = $("#nullModule").clone();
	var timestamp = new Date().getTime();
	var divId = "childInfo_" + timestamp;
	var textareaId = "childInfoDesc_" + timestamp;
	var inputId = "childInfoTitle_" + timestamp;
	
	newModule.attr("style", "display:block;");
	//设置一个不能重复的id,但name属性一样
	newModule.attr("id", divId);
	newModule.find("textarea").attr("id", textareaId).attr("name", "childInfoDesc");
	newModule.find("input").attr("id", inputId).attr("name", "childInfoTitle");
	
	$("#allchild").append(newModule);
	var newEditor = new wangEditor(textareaId);
	newEditor.create();
	return false;
	
}

/**
 * 删除子模块
 * @param {Object} id
 */
var remModule = function(id) {
	$(id).parent().remove();
}


