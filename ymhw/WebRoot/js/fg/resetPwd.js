/**
 * 发送短信验证码（找回密码）
 */
var sendsms = function()
{
	var telphone = $("#telphoneHidden").val();
	$.get("/register/portSendSms",{"telphone":telphone},function(data){
		$("#sendBut").html(data.msg);
		return false;
	});
	return false;
}

var updatePwd = function()
{
	var newPwd = $("#newPwd").val().trim();
	var renewPwd = $("#renewPwd").val().trim();
	if(isNullOrEmpty(newPwd))
	{
		alert("密码不能为空");
		return false;
	}
	else if(!checklength(newPwd,8,16))
	{
		alert("密码长度错误，长度为8~16个字符");
		return false;
	}
	else if(newPwd != renewPwd)
	{
		alert("两次密码不一致");
		return false;
	}
	else
	{
		$("#pwdForm").attr("action","/login/resetPwdPath4");
		$("#pwdForm").submit();
	}
}
