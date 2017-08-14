$(function(){
	var contentEditor = new wangEditor('content');
	contentEditor.config.uploadImgUrl = "/manage/wangUpload_strategy";
	contentEditor.create();
	contentEditor.config.uploadImgFileName = 'fileName_strategy';
});
