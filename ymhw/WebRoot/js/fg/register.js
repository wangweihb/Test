$(function(){
	$('input:radio[name="registerType"]').change( function(){
		var currentVar = $("input:radio:checked").val();
		if(currentVar == 0){
			$("#emailRegDiv").attr("style","display:none");
			$("#smsRegDiv").attr("style","display:block");
		}
		
		if(currentVar == 1){
			$("#smsRegDiv").attr("style","display:none");
			$("#emailRegDiv").attr("style","display:block");
		}
	});
})


var refreshCode = function()
{
	var timenow = new Date().getTime();
	var codeImg = $("#validateCodePic");
	codeImg.attr("src","/verifycode/getCode?time="+timenow);					
}

var checkAccount = function()
{
	$("#accountRes").attr("value","0");
	var inputValue = $("#account").val().trim();
	if(isNullOrEmpty(inputValue))
	{
		setValue("accountTip","用户名不能为空");
	}
	else if(!checklength(inputValue,3,25))
	{
		setValue("accountTip","用户名长度错误");
	}
	else
	{
		$.get("/register/portIsRegistered",{"input":inputValue,"type":1},function(data){
			if(data == "1") 	//已被注册
			{
				setValue("accountTip","用户名已被注册");
			}
			else if(data == "0")
			{
				setValue("accountTip","用户名可以使用");
				$("#accountRes").attr("value","1");
			}
			else
			{
				alert("输入信息有误！");
			}
		});		
	}
	
}

var checkEmail = function()
{
	var inputValue = $("#email").val().trim();
	if(isNullOrEmpty(inputValue))
	{
		setValue("emailTip","邮箱不能为空");
	}
	else if(!isEmail(inputValue))
	{
		setValue("emailTip","格式错误");
	}
	else
	{
		$.get("/register/portIsRegistered",{"input":inputValue,"type":2},function(data){
			if(data == "1") 	//已被注册
			{					
				setValue("emailTip","邮箱已被注册");
			}
			else if(data == "0")
			{
				setValue("emailTip","邮箱可以使用");
			}
			else
			{
				alert("输入信息有误！");
			}
		});					
	}
	
}

var checkTelphone = function()
{
	var inputValue = $("#telphone").val().trim();
	if(isNullOrEmpty(inputValue))
	{
		setValue("telphoneTip","手机号不能为空");
	}
	else if(!isMobile(inputValue))
	{
		setValue("telphoneTip","格式错误");
	}
	else
	{
		$.get("/register/portIsRegistered",{"input":inputValue,"type":3},function(data){
			if(data == "1") 	//已被注册
			{					
				setValue("telphoneTip","该手机号已被注册");
			}
			else if(data == "0")
			{
				setValue("telphoneTip","手机号可以使用");
			}
			else
			{
				alert("输入信息有误！");
			}
		});					
	}
	
}

 
 var isMobile = function(mobile)
 {
 	var pattern = /^1[34578]\d{9}$/; 
 	return pattern.test(mobile); 
 }

/**
 * 检测密码的合法性
 */
var checkPassword = function()
{
	var inputValue = $("#password").val().trim();
	if(isNullOrEmpty(inputValue))
	{
		setValue("passwordTip","密码不能为空");
		
		return false;
	}
	else if(!checklength(inputValue,8,16))
	{
		setValue("passwordTip","请输入8~16个字符密码");
		return false;
	}
	else
	{
		setValue("passwordTip","");
		return true;
	}
}

var checkRepassword = function()
{
	var inputValue1 = $("#repassword").val().trim();
	var inputValue2 = $("#password").val().trim();
	
	if(!isNullOrEmpty(inputValue1) && !isNullOrEmpty(inputValue2) && inputValue1 == inputValue2)
	{
		setValue("repasswordTip","");
		return true;
	}
	else
	{
		setValue("repasswordTip","密码不一致");
		return false;
	}
}

var checkVerifyCode = function()
{
	var inputValue = $("#validateCode").val().trim();
	if(isNullOrEmpty(inputValue))
	{
		setValue("verifyCodeTip","验证码不能为空");
		return false;
	}
	else
	{
		setValue("verifyCodeTip","");
		return true;
	}
}
/**
 * 注册
 */
var reg = function()
{
//	var isPersisit = document.getElementById("isPersisit").checked;
	var accountRes = $("#accountTip").html();
	var emailRes = $("#emailTip").html();
	var telphoneRes = $("#telphoneTip").html();
	
	var currentVar = $("input:radio:checked").val();
	if(currentVar == 1 && !checkVerifyCode()) {
		return false;
	}
	
	if(accountRes == "用户名可以使用" && emailRes=="邮箱可以使用" && telphoneRes =="手机号可以使用" && checkPassword() && checkRepassword())
	{
		
		$("#registerform").attr("action","/register");
		$("#registerform").submit();
	}
	else
	{
		alert("您的注册信息填写不合法！");
		return false;
	}
	
}

/**
 * 发送短信验证码
 */
var sendsms = function()
{
	var telphone = $("#telphone").val();
	if(isNullOrEmpty(telphone) || !isMobile(telphone)){
		alert("手机号不能为空或者号码格式错误");
	} else {
		$.get("/register/portSendSms",{"telphone":telphone},function(data){
			$("#smsSpan").html("");
			$("#smsSpan").html(data.msg);
		});
	}
}
