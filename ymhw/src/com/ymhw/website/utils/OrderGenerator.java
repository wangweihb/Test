package com.ymhw.website.utils;

import com.alipay.util.UtilDate;

/**
 * 订单生成器
 * @author oswin
 */
public class OrderGenerator {
	
	/**
	 * 根据商品类型生成订单号
	 * @param type 1-活动订单 2-装备订单 3-车辆订单 4-农家乐中产品订单
	 * @return
	 */
	public static String getOrderNo(int type) {
		String code = "";
		if (type<10) {
			code = "0" + type;
		} else {
			code = "" + type;
		}
		return UtilDate.getOrderNum() + code;
	}
}
