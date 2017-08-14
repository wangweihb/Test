package com.ymhw.website.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.log.Log;

/** 
 * info
 * @author      ws 
 * @since       1.0
 * create time：  2016年1月8日 上午11:52:57  
 * E-mail:      wangshuo@keenyoda.net
 */
public class UserInterceptor implements Interceptor
{
	/**日志对象*/
	private static final Log log = Log.getLog(UserInterceptor.class);
	
	@Override
	public void intercept(Invocation inv)
	{
		log.info("the before .....");
		inv.invoke();
		log.info("the after .....");
	}

}
