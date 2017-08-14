package com.ymhw.website.utils;

import java.io.File;

/** 
 * 常量定义
 * @author      oswin 
 * @since       1.0
 * create time：  2016年1月14日 下午5:05:57  
 * E-mail:      15002718072@qq.com
 */
public interface Constant
{
	/**有效   已激活状态*/
	int VALID = 1;
	
	/**无效   未激活状态*/
	int INVALID = 0;
	
	/**成功*/
	String SUCCESS = "1";
	
	/**失败*/
	String FAILURE = "0";
	
	/**输入信息有误*/
	String INPUT_ERROR = "9";
	
	/**账号类型*/
	int REGIST_ACCOUNT_TYPE = 1;
	
	/**邮箱类型*/
	int REGIST_EMAIL_TYPE = 2;
	
	/**手机号类型*/
	int REGIST_TELPHONE_TYPE = 3;
	
	/**session中验证码的key*/
	String VERIFYCODE_KEY = "verifyCodeKey";
	
	/**激活邮件主题*/
	String SUBJECT = "友鸣账户激活";
	
	/**session中用户key值*/
	String YMHW_SESSIONUSER = "ymhwUser";
	
	String EQUIP_PATH = "equip";
	
	/**
	 * 领队申请的状态
	 * 未提交
	 */
	int STATUS_NOT_SUBMIT = 0;
	
	/**提交未审核*/
	int STATUS_SUMBIT_NOT_CHECK = 1;
	
	/**审核未通过*/
	int STATUS_CHECK_NOT_PASS = 2;
	
	/**审核通过*/
	int STATUS_CHECK_PASS = 3;
	
	String BASE_PATH = "http://www.yomiing.com";
//	String BASE_PATH = "http://localhost:8080";
	
	String AES_KEY_CODE = "bebpcBrtYE34ia7l";
	
	/**管理员*/
	int ROLEID_ADMIN = 1;
	
	/**领队*/
	int ROLEID_LEADER = 2;
	
	/**普通会员*/
	int ROLEID_GUEST = 3;
	
	/**session的最大有效时间，以及短信验证码的有效期*/
	int SESSION_ACTIVE_TIME = 10 * 60;
	
	
	/**订单状态   0:待支付,1:支付中,2:支付成功,3:支付失败，4：已关闭 9-订单异常*/
	int BILL_STATUS_NOPAY = 0;
	int BILL_STATUS_PAYING = 1;
	int BILL_STATUS_PAYOK = 2;
	int BILL_STATUS_PAYFAIL = 3;
	int BILL_STATUS_CLOSED = 4;
	int BILL_STATUS_EXCEPTION = 9;
	
	/**wangEditor富文本编辑框中的图片上传保存路径*/
	String UPLOAD_WANGEDITOR = "wangEditor";
	
	String UPLOAD_INFORMATION_ROOT = "information";
	String UPLOAD_INFORMATION_SUBJECT = "subject";
	String UPLOAD_PRODUCT = "product";
	
	String UPLOAD_CAR = "car";
	String UPLOAD_FARMSTAY = "farmstay";
	String UPLOAD_HOTEL = "hotel";
	
	/**URL链接中的分隔符*/
	String URL_SEP = "/";
}
