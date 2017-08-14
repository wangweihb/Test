package com.ymhw.website.utils;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.kit.StrKit;

public class NetworkUtil
{
	/** 
     * <从request对象中获取用户IP地址> 
     * @param request 
     * @return IP地址 
     */  
    public static String getIp(HttpServletRequest request) {  
        String forwards = request.getHeader("x-forwarded-for");  
        if (StrKit.isBlank(forwards) || "unknown".equalsIgnoreCase(forwards)) {  
            forwards = request.getHeader("Proxy-Client-IP");  
        }  
        if (StrKit.isBlank(forwards) || "unknown".equalsIgnoreCase(forwards)) {  
            forwards = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (StrKit.isBlank(forwards) || "unknown".equalsIgnoreCase(forwards)) {  
            forwards = request.getRemoteAddr();  
        }  
        if (StrKit.isBlank(forwards) || "unknown".equalsIgnoreCase(forwards)) {  
            forwards = request.getHeader("X-Real-IP");  
        }  
        if (forwards != null && forwards.trim().length() > 0) {  
            int index = forwards.indexOf(',');  
            return (index != -1) ? forwards.substring(0, index) : forwards;  
        }  
        return forwards;  
    }  
}
