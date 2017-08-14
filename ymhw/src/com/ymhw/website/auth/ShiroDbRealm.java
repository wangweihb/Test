package com.ymhw.website.auth;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;


import com.ymhw.website.model.User;
import com.ymhw.website.utils.Constant;

/**
 * 自定义realm,一般只定义一个
 * @author oswin
 */
public class ShiroDbRealm extends AuthorizingRealm
{
	private static final Logger LOGGER = Logger.getLogger(ShiroDbRealm.class);
	
	@Override
	public String getName()
	{
		return "shiroDbRealm";
	}
	
	/**
     * 用户登录后给用户授权（主要用于perms，roles，ssl，rest，port等授权过滤器中）
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc)
	{
		LOGGER.info("登录成功，给用户授权中............");
		String username = (String) pc.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(User.dao.findRoles(username));
		authorizationInfo.setStringPermissions(User.dao.findPermissions(username));
		
        return authorizationInfo;
	}
	
	/**
	 * 登录是验证（主要用于anon，authcBasic，auchc，user等认证过滤器中）
	 * @param authcToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException
	{
		LOGGER.info("登录认证中.....................");
		String username = (String) authcToken.getPrincipal();
		User user = User.dao.queryUserInfo(username, Constant.REGIST_ACCOUNT_TYPE);
		if (user != null)
		{
			return new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(), getName());
		}
		return null;
	}
	
	/**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String principal) {
    	LOGGER.info("clearCachedAuthorizationInfo.....................");
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }
 
    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
    	LOGGER.info("clearAllCachedAuthorizationInfo.....................");
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }

}
