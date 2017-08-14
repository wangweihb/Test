package com.ymhw.website.controler;

import java.util.HashMap;
import java.util.Map;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;
import com.jfinal.core.Controller;

public class AlipayController extends Controller
{
	
	/**
	 * 访问路径：
	 */
	public void pay() {
		//商品订单号
		String out_trade_no = getPara("billNo");
		
		//订单名称，必填
        String subject = getPara("proName");
        
        //付款金额，必填
        String total_fee = getPara("total");
        
        //商品描述，可空
        String body = getPara("proDesc");
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", AlipayConfig.payment_type);
		sParaTemp.put("notify_url", AlipayConfig.notify_url);
		sParaTemp.put("return_url", AlipayConfig.return_url);
		sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		//其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.O9yorI&treeId=62&articleId=103740&docType=1
        //如sParaTemp.put("参数名","参数值");
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		System.out.println(sHtmlText);
		
		renderNull();
	}

}
