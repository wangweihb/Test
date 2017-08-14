package com.ymhw.website.utils;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
/**
 * 
 * @author oswin
 */
public class ShiroSecurityUtil
{
	private static final Logger LOGGER = Logger.getLogger(ShiroSecurityUtil.class);
	
	/**
	 * shiro 认证
	 * @param configFile
	 * @param username
	 * @param password
	 * @return
	 */
	public static Subject login(String configFile, String username, String password) {
		LOGGER.info(username + "正在身份认证中.......");
		//读取配置文件，初始化SecurityManager工厂
    	Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
    	//获取SercurityManager实例
    	SecurityManager securityManager = factory.getInstance();
    	//将SercurityManager实例绑定到SecurityUtils
    	SecurityUtils.setSecurityManager(securityManager);
    	//获得当前执行的用户
    	Subject currentUser = SecurityUtils.getSubject();
    	//创建token令牌，用户名/密码
    	UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    	try
		{
			//身份认证
			currentUser.login(token);
			LOGGER.info("身份认证成功！");
		}
		catch (AuthenticationException e)
		{
			LOGGER.error("身份认证失败！" + e.getMessage(), e);
			currentUser = null;
		}
    	return currentUser;
    
	}
}
