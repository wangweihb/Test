package com.ymhw.website.controler;

import com.jfinal.core.Controller;
import com.ymhw.website.model.Order;
import com.ymhw.website.model.User;
import com.ymhw.website.utils.Constant;

public class OrderController extends Controller{
	
	/**
	 * 订单生成
	 * 访问路径：/order/generate
	 */
	public void generate() {
		User sessionUser = getSessionAttr(Constant.YMHW_SESSIONUSER);
		int userId = sessionUser.getId();
	}

}
