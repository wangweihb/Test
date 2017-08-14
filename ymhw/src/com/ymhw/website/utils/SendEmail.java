package com.ymhw.website.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/** 
 * 邮件发送工具类(发送邮箱固定，可使用公司邮件)
 * @author      oswin 
 * @since       1.0
 * create time：  2016年3月1日 下午2:37:40  
 */
public class SendEmail 
{

	public static final String HOST = "smtp.mxhichina.com";
	public static final String PROTOCOL = "smtp";
	//加密端口
	public static final int PORT = 25;
	/*public static final String FROM = "oswin888@sina.com";// 发件人的email
	public static final String PWD = "oswin888";// 发件人密码*/
	public static final String FROM = "postmaster@yomiing.com";// 发件人的email
	public static final String PWD = "YMHWymhw888";// 发件人密码

	/**
	 * 获取Session
	 * 
	 * @return
	 */
	private static Session getSession() 
	{
		Properties props = new Properties();
		props.put("mail.smtp.host", HOST);// 设置服务器地址
		props.put("mail.transport.protocol", PROTOCOL);// 设置协议
		props.put("mail.smtp.port", PORT);// 设置端口
		props.put("mail.smtp.auth", "true");	

		Session session = Session.getDefaultInstance(props);
		return session;
	}

	public static boolean send(String toEmail,String subject, String content) 
	{
		Session session = getSession();
		session.setDebug(true);
		try 
		{
			System.out.println("--send--" + content);
			
			// Instantiate a message
			Message msg = new MimeMessage(session);

			// Set message attributes
			msg.setFrom(new InternetAddress(FROM));
			InternetAddress[] address = { new InternetAddress(toEmail) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setContent(content, "text/html;charset=utf-8");			
			msg.saveChanges();
			
			Transport transport = session.getTransport("smtp"); 
			transport.connect(HOST, FROM, PWD); 
			transport.sendMessage(msg,msg.getAllRecipients()); 
			transport.close(); 
			
			return true;
		} 
		catch (MessagingException mex)
		{
			mex.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) 
	{
		//String url = "http://localhost:88/register";
		//String content = "点击<a  target='_blank' href='http://localhost:88/register'>http://localhost:88/register</a>完成注册";
		String content = "点击<a  target='_blank' href='http://www.keenyoda.com/'>http://www.keenyoda.com/</a>完成注册";
		String subject = "友鸣账号激活邮件";
		SendEmail.send("1502718072@qq.com",subject, content);
	}

}