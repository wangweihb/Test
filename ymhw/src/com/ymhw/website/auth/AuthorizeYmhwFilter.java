package com.ymhw.website.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class AuthorizeYmhwFilter extends AuthorizationFilter
{
	private static final Logger LOGGER = Logger.getLogger(AuthorizeYmhwFilter.class);
	
	@Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        LOGGER.info("AuthorizeYmhwFilter 过滤中------------------------------------" + getName());
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();
        boolean result = false;
        try {
            Subject user = SecurityUtils.getSubject();
            result = user.isAuthenticated();
//            result = user.isPermitted(url);
        } catch (Exception e) {
        	LOGGER.error("check permission error", e);
        }
        LOGGER.info("AuthorizeYmhwFilter 过滤结果是 ： " + result);
        return result;
    }
    
    

}
