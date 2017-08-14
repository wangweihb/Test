package com.ymhw.website.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;


/** 
 * info
 * @author      ws 
 * @since       1.0
 * create time：  2016年1月16日 下午2:33:28  
 * E-mail:      wangshuo@keenyoda.net
 */
public class GlobalInterceptor implements Interceptor
{

	@Override
	public void intercept(Invocation inv)
	{
		/*Controller c = inv.getController();
        String tmp = c.getCookie("XX");
        String i18n = c.getRequest().getLocale().toString();
        if (!i18n.equals(tmp)) {
        	inv.getController().setCookie("XX", i18n,
                    Const.DEFAULT_I18N_MAX_AGE_OF_COOKIE);
        }
        inv.invoke();	*/	
	}

}
