package com.ymhw.website.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;

import com.jfinal.kit.StrKit;

/**
 * 
 * @param url
 *            应用地址，类似于http://ip:port/msg/
 * @param account
 *            账号
 * @param pswd
 *            密码
 * @param mobile
 *            手机号码，多个号码使用","分割
 * @param msg
 *            短信内容
 * @param needstatus
 *            是否需要状态报告，需要true，不需要false
 * @return 返回值定义参见HTTP协议文档
 * @throws Exception
 */
public class SmsTool
{
	public static final String MODEL_VBCODE = "您的验证码是";
	public static final String MODEL_NOTIFY = "尊敬的友鸣户外网会员【会飞的狼】，您已成功报名友鸣户外2016年9月20日到9月30日的活动【活动标题】，稍后网站的工作人员会与您取得联系，请您保持手机畅通。";
	
	/**
	 * 向号码集发送短信
	 * @param url
	 * @param account
	 * @param pswd
	 * @param mobile
	 * @param msg
	 * @param needstatus
	 * @param extno
	 * @return
	 * @throws Exception
	 */
	public static String batchSend(String url, String account, String pswd, String mobile, String msg,
			boolean needstatus, String extno) throws Exception
	{
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod();
		try
		{
			URI base = new URI(url, false);
			method.setURI(new URI(base, "HttpBatchSendSM", false));
			method.setQueryString(new NameValuePair[] { new NameValuePair("account", account),
					new NameValuePair("pswd", pswd), new NameValuePair("mobile", mobile),
					new NameValuePair("needstatus", String.valueOf(needstatus)), new NameValuePair("msg", msg),
					new NameValuePair("extno", extno), });
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK)
			{
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1)
				{
					baos.write(buffer, 0, len);
				}
				return URLDecoder.decode(baos.toString(), "UTF-8");
			}
			else
			{
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		}
		finally
		{
			method.releaseConnection();
		}
	}
	
	/**
	 * 
	 * @param mobiles  手机号码，多个号码使用","分割
	 * @param model 短信内容的模板（短信内容长度不能超过585个字符）
	 * @param variable 变量（短信验证码的数字）
	 */
	public static boolean send(String mobiles, String model, String variable)
	{
		String content = model + variable;
		return send(mobiles, content);
	}
	
	/**
	 * 
	 * @param mobiles  手机号码，多个号码使用","分割
	 * @param model 短信内容长度不能超过585个字符
	 */
	public static boolean send(String mobiles, String content)
	{
		String url = "http://222.73.117.158/msg/HttpBatchSendSM";// 应用地址
		String account = "ymhwvip8";// 账号
		String pswd = " Ymhw8888";// 密码
		boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
		String extno = null;// 扩展码

		try {
			String returnString = SmsTool.batchSend(url, account, pswd, mobiles, content, needstatus,  extno);
			if (!StrKit.isBlank(returnString))
			{
				String statusCodeTmp = returnString.split("\n")[0];
				String statusCode = statusCodeTmp.split(",")[1];
				if ("0".equals(statusCode))
				{
					return true;
				}
				else
				{
					//出错时，1、记录错误代码和错误信息 2、发送报警信息到相关技术人员（主要是邮件）
					System.out.println("短信接口返回的错误码是 ： " + statusCode + ", 请根据接口文档查看相应原因");
					SendEmail.send("1502718072@qq.com", "短信验证码错误反馈", "短信验证错误的具体体现，调用短信接口错误码为" 
								+ statusCode + "\n传入的 mobiles:" + mobiles + "\t content:" + content);
					return false;
				}
			}
			
		} catch (Exception e) {
			System.err.println("短信接口调用异常");
			e.printStackTrace();
			SendEmail.send("1502718072@qq.com", "短信验证码错误反馈", "短信验证错误的具体体现，短信接口调用异常!");
			return false;
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		String url = "http://222.73.117.158/msg/HttpBatchSendSM";// 应用地址
		String account = "ymhwvip8";// 账号
		String pswd = " Ymhw8888";// 密码
		String mobile = "18727721920,13094101504";// 手机号码，多个号码使用","分割
		String msg = "您好，您的验证码是55555";// 短信内容
		boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
		String extno = null;// 扩展码

		try {
			String returnString = SmsTool.batchSend(url, account, pswd, mobile, msg, needstatus,  extno);
			System.out.println(returnString);
			// TODO 处理返回值,参见HTTP协议文档
		} catch (Exception e) {
			// TODO 处理异常
			e.printStackTrace();
		}
	}

}
