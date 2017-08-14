var sub = function()
{
	var content = $("#J_detail").val();
	var email = $("#J_email").val();
	var telphone = $("#J_telephone").val();
	
	$.getJSON("/advice/portAdd",{"email":email,"telphone":telphone,"content":content}, function(data) {
		if(data == "1"){
			alert("建议已提交成功！");
		}else{
			alert("建议提交失败，请重新填写！");
		}
		window.location = "/aboutHelpPath?param=advice";
		
	});
}
