package com.ymhw.website.controler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.sun.net.httpserver.Headers;
import com.ymhw.website.model.User;
import com.ymhw.website.utils.Constant;
import com.ymhw.website.utils.MD5Util;
import com.ymhw.website.utils.RegexTool;
import com.ymhw.website.utils.SendEmail;
import com.ymhw.website.utils.SmsTool;
import com.ymhw.website.utils.StringTool;

/** 
 * 注册
 * @author      oswin 
 * @since       1.0
 * create time：  2016年3月1日 下午13:37:40  
 */
public class RegisterController extends Controller
{
	
	/**
	 * 注册
	 * 访问路径：/register
	 * @throws Exception 
	 */
	public void index() throws Exception
	{
		//邮箱注册 type-0 手机号注册 - 1 - 邮箱注册    默认手机号注册
		int type = getParaToInt("registerType",0);
		String account = getPara("account","").trim();
		String email = getPara("email","").trim();
		String telphone = getPara("telphone","").trim();
		String password = getPara("password","").trim();
		String verifyCode = getPara("validateCode","").trim();
		String smsCode = getPara("smsCode", "").trim();
		
		if (StrKit.isBlank(account) || StrKit.isBlank(password))
		{
			setAttr("msg", "用户名和密码不能为空！");
			render("register.html");
			return;
		}
		
		if(type == 0)
		{
			//取得session中这个手机号码对应的短信验证码
			String vbcode_session = getSessionAttr(telphone);
			//短信验证通过
			if(smsCode.equals(vbcode_session))
			{
				User newUser = new User();
				newUser.setAccount(account);
				newUser.setPassword(MD5Util.shiroMD5(password.trim()));
				newUser.setEmail(email);
				newUser.setTelphone(telphone);
				newUser.setIsValid(Constant.VALID);
				newUser.setRoleId(Constant.ROLEID_GUEST);
				
				if (newUser.save())
				{
					render("registerSuccess.html");
					
					return;
				}
				else 
				{
					setAttr("msg", "注册失败！");
					render("register.html");
					return;
				}
			}
			else
			{
				setAttr("msg", "短信验证码错误！");
				render("register.html");
				return;
			}
		} 
		else
		{
			if (!checkVerifyCode(verifyCode))
			{
				setAttr("msg", "验证码错误");
				render("register.html");
				return;
			}
			
			User newUser = new User();
			newUser.setAccount(account);
			newUser.setPassword(MD5Util.MD5(password.trim()));
			newUser.setEmail(email);
			newUser.setTelphone(telphone);
			newUser.setIsValid(Constant.INVALID);
			newUser.setRoleId(Constant.ROLEID_GUEST);
			
			/*PropKit.use("a_little_config.txt");
			String basePath = PropKit.get("basePath").trim();*/
			String basePath = Constant.BASE_PATH;
			if (newUser.save())
			{
				setAttr("account", account);
				setAttr("email", email);
				render("registerTip.html");
//				String clientId = QEncodeUtil.aesDecrypt(email.trim(), Constant.AES_KEY_CODE);
				String clientId = email.trim();
				StringBuilder sb = new StringBuilder();
				sb.append("<div>");
				sb.append("亲爱的" + account +",您好<br /><br />");
				sb.append("您距离成功注册友鸣账号只差一步了，完成激活，开启友鸣之旅！<br />");
				sb.append("<a target='_self' href='" + basePath + "/register/activeAccount?clientId="+ clientId +"'>" + basePath + "/register/activeAccount?clientId="+ clientId +"</a> <br />");
				sb.append("如果上面的链接无法点击，请复制下面的链接粘贴到浏览器中访问。<br />");
				sb.append("<a target='_self' href='" + basePath + "/register/activeAccount?clientId="+ clientId +"'>" + basePath + "/register/activeAccount?clientId="+ clientId +"</a> <br /><br />");
				sb.append("祝您使用愉快！");
				sb.append("</div>");
				
				boolean isSendok = SendEmail.send(email, Constant.SUBJECT, sb.toString());
				if (isSendok)
				{
					System.out.println("邮件发送成功");
				}
				else 
				{
					System.out.println("邮件发送失败");
				}
			}
			else
			{
				setAttr("msg", "注册失败！");
				render("register.html");
			}
		}
	}
	
	/**
	 * 请求短信验证码
	 * @param 传入参数 telphone
	 * 访问路径：/register/portSendSms 
	 * 结果码： 00 - 成功 ; 01 - 号码格式错误;02 - 发送失败，请联系管理员  03-短信验证码10分钟有效
	 */
	public void portSendSms()
	{
		Map<String, Object> result = new HashMap<String, Object>();
		String telphone = getPara("telphone", "");
		
		if(!StrKit.isBlank(telphone) && RegexTool.isMobile(telphone)){
			
			String vbcode_session = getSessionAttr(telphone);
			if(!StrKit.isBlank(vbcode_session)){
				result.put("code", "03");
				result.put("msg", "已发送，10分钟有效");
				renderJson(result);
				return;
			}
			
			String variable = StringTool.generateVBCode(6);
			boolean sendResult = SmsTool.send(telphone, SmsTool.MODEL_VBCODE, variable);
			if(sendResult) {
				result.put("code", "00");
				result.put("msg", "发送成功，10分钟有效");
				
				HttpSession session = getRequest().getSession();
				session.setMaxInactiveInterval(Constant.SESSION_ACTIVE_TIME);
				session.setAttribute(telphone, variable);
				
				renderJson(result);
				return;
			} else {
				result.put("code", "02");
				result.put("msg", " 发送失败，请联系管理员");
				renderJson(result);
				return;
			}
		}else {
			result.put("code", "01");
			result.put("msg", "号码格式错误");
			renderJson(result);
			return;
		}
	}
	
	/**
	 * 激活账号
	 * 传入参数：clientId
	 * 访问路径：/register/activeAccount
	 * @throws Exception 
	 */
	public void activeAccount() throws Exception
	{
		System.out.println("attr : " + getAttrForStr("clientId"));
		String clientIdStr = getPara("clientId");
		System.out.println("param : " + clientIdStr);
		if (StrKit.isBlank(clientIdStr))
		{
			System.out.println("activeAccount中传入的参数值为空");
			render("registerFailure.html");
			return;
		}
		
		/*String email = QEncodeUtil.aesDecrypt(clientIdStr, Constant.AES_KEY_CODE);
		System.out.println("解密的clientId的信息 ： " + email);*/
		String email  = clientIdStr.trim();	
		
		User user = User.dao.queryUserByInput(email,Constant.INVALID);
		System.out.println("数据库中查询的用户信息是否为空 ： " + user==null);
		if (user != null)
		{
			user.setIsValid(Constant.VALID);
			if (user.update()) 
			{
				render("registerSuccess.html");
			}
			else
			{
				render("registerFailure.html");
			}
		}
		else
		{
			//账户可能已经被激活了或者账户不存在
			render("registerOtherError.html");
		}
		
	}
	
	
	/***
	 * 验证是否已经被注册
	 * @param input 注册值
	 * @param type  注册类型（1-账号注册 2-邮箱  3-手机号码注册）
	 * @return 		1-已被注册  0-未被注册
	 * 访问路径：/register/portIsRegistered
	 */
	public void portIsRegistered()
	{
		String input = getPara("input");
		Integer type = getParaToInt("type");
		
		if (input == null || type == null) 
		{
			renderText(Constant.INPUT_ERROR);
			return;
		}
		
		boolean result = User.dao.queryRegisterdInfo(input.trim(), type);
		if (result) 
		{
			renderText(Constant.SUCCESS);
		}
		else 
		{
			renderText(Constant.FAILURE);
		}		
	}
	

	/***
	 * 验证是否已经被注册(账号、手机、邮箱同时验证 主要用户移动端)
	 * @param input 注册值
	 * @return 		1-已被注册  0-未被注册
	 * 注册类型（1-账号注册 2-邮箱  3-手机号码注册）
	 * 放回结果：00-都未被注册  01-账号已被注册  02-邮箱已被注册 03-手机号已注册 04-参数错误
	 * 访问路径：/register/portIsRegistered
	 */
	public void portIsRegistered2()
	{
		String account = getPara("account","").trim();
		String email = getPara("email", "").trim();
		String telphone = getPara("telphone", "").trim();
		Map<String, Object> result = new HashMap<String, Object>();
		if(StrKit.isBlank(account) || StrKit.isBlank(email) || StrKit.isBlank(telphone))
		{
			result.put("code", "04");
			result.put("msg", "参数错误");
		}
		else
		{
			if (User.dao.queryRegisterdInfo(account, Constant.REGIST_ACCOUNT_TYPE)) 
			{
				result.put("code", "01");
				result.put("msg", "账号已被注册");
			}
			else if(User.dao.queryRegisterdInfo(email, Constant.REGIST_EMAIL_TYPE))
			{
				result.put("code", "02");
				result.put("msg", "邮箱已被注册");
			}
			else if(User.dao.queryRegisterdInfo(telphone, Constant.REGIST_TELPHONE_TYPE))
			{
				result.put("code", "03");
				result.put("msg", "手机号已注册");
			}
			else
			{
				result.put("code", "00");
				result.put("msg", "都未注册");
			}
		}
		renderJson(result);
	}
	
	/**
	 * 重发邮件
	 * 传入参数：account，email
	 * 访问路径：/register/reSendMail
	 * @throws Exception 
	 */
	public void reSendMail() throws Exception
	{
		String email = getPara("email");
		String account = getPara("account");
		
		if (StrKit.isBlank(account) || StrKit.isBlank(email))
		{
			return;
		}
		String basePath = Constant.BASE_PATH;
		
//		String clientId = QEncodeUtil.aesEncrypt(email.trim(), Constant.AES_KEY_CODE);
		String clientId = email.trim();
		
		StringBuilder sb = new StringBuilder();
		sb.append("<div>");
		sb.append("亲爱的" + account +",您好<br /><br />");
		sb.append("您距离成功注册友鸣账号只差一步了，完成激活，开启友鸣之旅！<br />");
		sb.append("<a target='_self' href='" + basePath + "/register/activeAccount?clientId="+ clientId +"'>" + basePath + "/register/activeAccount?clientId="+ clientId +"</a> <br />");
		sb.append("如果上面的链接无法点击，请复制下面的链接粘贴到浏览器中访问。<br />");
		sb.append("<a target='_self' href='" + basePath + "/register/activeAccount?clientId="+ clientId +"'>" + basePath + "/register/activeAccount?clientId="+ clientId +"</a> <br /><br />");
		sb.append("祝您使用愉快！");
		sb.append("</div>");
		SendEmail.send(email, Constant.SUBJECT, sb.toString());
		
		setAttr("account", account);
		setAttr("email", email);
		render("registerTip.html");
	}
	
	/**
	 * 检查验证码正确
	 * @param verifyCode
	 * @return
	 */
	private boolean checkVerifyCode(String verifyCode)
	{
		if (verifyCode == null) 
		{
			return false;
		}
		
		String key = getSessionAttr(Constant.VERIFYCODE_KEY);
		return verifyCode.equalsIgnoreCase(key);
	}
}
