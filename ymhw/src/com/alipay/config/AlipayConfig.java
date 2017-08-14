package com.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static final String partner = "2088421832428525";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static final String seller_id = partner;

	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
    public static final String private_key =  "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMW0TYCy3ArTiacBBQN+eVxXQO2uf6dDgaiI8Kh1INKancT3+hEGedU6zPVjnGsZuDZRf9+bUPW7eWYHO66zdYKdCxU39nnv8o8cF/4VaSfFRMaY2Qr8qtSKXbT9jYvzdnDSkTCE23MKCM+Vci3Z3DiXjKVP2h7C3EIQJKcew151AgMBAAECgYEAjeCpab1xaZeLBj0WEv7VKu422xrJ4wfv6Tl0sv9zMY/hvrNSUpt6HQpYgZUdcEKBzwFHqfk07VO/d/ei3elNXs4QpQOe4i/odMtQduuyVPOQpOa7eVVOaLa3YEAdW68cWqSfAoW07rmt229ItEEkoj7tjaNlZCTarIUKQC7+Y2ECQQDq2x+q0x0TMiXEDvKEK8g0xIoB6WjdFXepzqnk1i2PfV7UK7cYSyX+tzm1PAHYNrTB1UMZszDHDVMukLH1Qn09AkEA14Dr0qYKBIWOO9wdlsYsltr4+5cLaESlLF8VVSNc/pfAmJJt16rbJwWBRcEMF/sNk+5CU0+ss6dt/iUtEtLpmQJBAKhAeYxhGFhTOdkeuEFWOGUNVK2P8U9J/OOLKg3GHvfIYnJwKwjddYfo7g/XwJ6MeqzOwvabeFtyQWB/yu4hRyUCQDNukFmoyPFFUqnUobcvOssSHaQl61IKOAV79+Jm2zXjz9JZ+B8lpsIMXUrhhNpgT2BD1858UHJb/jNOehBpB9kCQQCcX3IlImz6EtD3/0svrWY/LPry6DA9XgKDgGfOaqpSYBnd1LfpMGs/3I4asmRyA1ewsXUVC02qeMRM7cbtrrzU";

	
	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static final String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static final String notify_url = "http://yomiing.com/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static final String return_url = "http://yomiing.com/return_url.jsp";

	// 签名方式
	public static final String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static final String log_path = "C:\\";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static final String input_charset = "utf-8";
		
	// 支付类型 ，无需修改
	public static final String payment_type = "1";
		
	// 调用的接口名，无需修改
	public static final String service = "create_direct_pay_by_user";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
//↓↓↓↓↓↓↓↓↓↓ 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	
	// 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数
	public static final String anti_phishing_key = "";
	
	// 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
	public static final String exter_invoke_ip = "";
		
//↑↑↑↑↑↑↑↑↑↑请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
}

