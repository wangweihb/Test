package com.ymhw.website.controler;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.PageFans;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.PageFansBean;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.javabeans.weibo.Company;
import com.qq.connect.oauth.Oauth;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.ymhw.website.model.User;
import com.ymhw.website.utils.Constant;
import com.ymhw.website.utils.MD5Util;

/**
 * 注册
 * 
 * @author oswin
 * @since 1.0 create time： 2016年3月1日 下午13:37:40
 */
public class LoginController extends Controller {

	/**
	 * 登录 访问路径：/login
	 */
	public void index() {
		String input = getPara("input");
		String password = getPara("password");

		if (StrKit.isBlank(input) || StrKit.isBlank(password)) {
			setAttr("loginMsg", "用户名或密码为空！");
			render("login.html");
			return;
		}
		// User user = User.dao.queryLoginUser(input.trim(),
		// MD5Util.MD5(password));
		User user = User.dao.queryLoginUser(input.trim(), MD5Util.shiroMD5(password, MD5Util.MD5_SALT));
		if (user != null) {
			setSessionAttr(Constant.YMHW_SESSIONUSER, user);
			// render("index.html");
			renderHtml("<script  type='text/javascript'>window.location='/';</script>");
		} else {
			setAttr("loginMsg", "用户名或密码错误！");
			renderHtml("<script  type='text/javascript'>alert('用户名或密码错误！');window.location='/login';</script>");
			// render("login.html");
		}
	}

	/**
	 * 退出登录 访问路径:/login/exit
	 */
	public void exit() {
		HttpSession session = getSession();
		session.invalidate();

		User ymUser = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (ymUser != null) {
			setAttr("", "");
			System.out.println("退出失败.....");
			renderHtml("<script  type='text/javascript'>window.location='/';</script>");
		} else {
			System.out.println("退出成功.....");
			renderHtml("<script  type='text/javascript'>window.location='/';</script>");
		}
	}

	/**
	 * 忘了密码？找回密码、重置密码页面 访问路径：/login/resetPwdPath
	 */
	public void resetPwdPath() {
		render("resetPwd1.html");
	}

	/**
	 * 忘了密码？找回密码、重置密码页面(第二步) 访问路径：/login/resetPwdPath2
	 */
	public void resetPwdPath2() {
		String account = getPara("account", "");
		User user = User.dao.queryUserInfo(account, 1);
		if (user == null) {
			renderHtml(
					"<script  type='text/javascript'>alert('该账号不存在，请重新输入！');window.location='/login/resetPwdPath';</script>");
		} else {
			String telphone = user.getTelphone();
			String last4 = "";
			setAttr("user", user);
			setAttr("telphone", telphone);
			if (!StrKit.isBlank(telphone)) {
				last4 = telphone.substring(telphone.length() - 4, telphone.length());
			}
			setAttr("last4", last4);
			render("resetPwd2.html");
		}
	}

	/**
	 * 忘了密码？找回密码、重置密码页面(第三步) 访问路径：/login/resetPwdPath3
	 */
	public void resetPwdPath3() {
		int userId = getParaToInt("userId", 0);
		String telphoneHidden = getPara("telphoneHidden", "");
		String vbcode = getPara("vbcode", "");
		String vbcode_session = getSessionAttr(telphoneHidden);
		System.out.println("vbcode_session : " + vbcode_session);
		if (!StrKit.isBlank(vbcode) && vbcode.equals(vbcode_session)) {
			setAttr("userId", userId);
			render("resetPwd3.html");
		} else {
			renderHtml("<script  type='text/javascript'>alert('短信验证失败！');</script>");
		}

	}

	/**
	 * 忘了密码？找回密码、重置密码页面(第四步) 访问路径：/login/resetPwdPath4
	 */
	public void resetPwdPath4() {
		int userId = getParaToInt("userId", 0);
		String newPwd = getPara("newPwd", "");
		String code = "01";
		String msg = "修改失败，请稍后重试";

		User user = User.dao.queryValidLeader(userId);
		if (user != null) {
			user.setPassword(MD5Util.MD5(newPwd));
			if (user.update()) {
				code = "00";
				msg = "密码修改成功，请返回主页重新登录";
			}
		}
		setAttr("code", code);
		setAttr("msg", msg);
		render("resetPwd4.html");
	}

	/**
	 * 使用QQ登录 访问路径：/login/qqLogin
	 */
	public void qqLogin() {
		try {
			redirect(new Oauth().getAuthorizeURL(getRequest()));
		} catch (QQConnectException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 访问路径：/login/afterLogin
	 */
	public void afterLogin() {
		try {
			AccessToken accessTokenObj = new Oauth().getAccessTokenByRequest(getRequest());
			String accessToken = null;
			String openID = null;
			long tokenExpireIn = 0L;

			if (accessTokenObj.getAccessToken().equals("")) {
				// 我们的网站被CSRF攻击了或者用户取消了授权
				// 做一些数据统计工作
				System.out.print("没有获取到响应参数");
				renderHtml("<script  type='text/javascript'>alert('用户授权失败！');window.location='/login';</script>");
			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();

				getRequest().getSession().setAttribute("demo_access_token", accessToken);
				getRequest().getSession().setAttribute("demo_token_expirein", String.valueOf(tokenExpireIn));

				// 利用获取到的accessToken 去获取当前用的openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();

				System.out.println("QQ用户登录openId : " + openID);
				getRequest().getSession().setAttribute("demo_openid", openID);
				UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
				UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
				
				if (userInfoBean.getRet() == 0) {
					String nickname = userInfoBean.getNickname();
					System.out.println("QQ用户的昵称 ：" + nickname);
					String gender = userInfoBean.getGender();
					String headshotUrl = userInfoBean.getAvatar().getAvatarURL100();
					User user = new User();
					user.setAccount(nickname);
					user.setRoleId(Constant.ROLEID_GUEST);
					setSessionAttr(Constant.YMHW_SESSIONUSER, user);
					render("index.html");
//					renderHtml("<script  type='text/javascript'>alert('登录成功！);window.location='/';</script>");
				} else {
					System.out.println("很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
					renderHtml("<script  type='text/javascript'>alert('用户授权失败！');window.location='/login';</script>");
				}
			}
		} catch (QQConnectException e) {
			e.printStackTrace();
		}
//		renderHtml("<script  type='text/javascript'>alert('用户授权失败！');window.location='/login';</script>");
	}

}
