package com.ymhw.website.controler;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.ymhw.website.atom.ActBillStatusUpdateAtom;
import com.ymhw.website.atom.OrderStatusUpdateAtom;
import com.ymhw.website.model.Activitybill;
import com.ymhw.website.model.Order;
import com.ymhw.website.utils.HttpUtil2;
import com.ymhw.website.utils.NetworkUtil;
import com.ymhw.website.utils.RSAUtil;

public class PayController extends Controller
{

	/**
	 * 只处理活动订单
	 * 第三支付接口接入 访问路径： /pay/start_act
	 * @throws UnsupportedEncodingException
	 */
	public void start_act() throws UnsupportedEncodingException
	{
		System.out.println("开始支付！！！");
		
		String billNo = getPara("billNo", "");
		String payChannelId = getPara("payChannelId", "2100000001");
		
		Activitybill bill = Activitybill.dao.getBillByBillNo(billNo);
		String proName = bill.getProName();
		String proDesc = bill.getProDesc();
		float cost = bill.getCost();

		String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0dYM3DXkVg9q+WcNjBPWaUwKoeRMrwdE4p4F6fiztv/Ys6F5AxGCbFW5UfbtbQavMp9Rrg3+8mJ5/Lp8sjf471NFe6EvbCcVwJ63Q6fA4xVyCAE7mQdfAlpCk9WKN7Qa/HqwO/OM6JDyOyycnjnNi3f3K2tK/JbWd/SHYOSMEDQIDAQAB";// rsa加密
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount",(int)(cost * 100) + "");
//		map.put("amount",(int)(1 * 50) + "");
		map.put("appid", "0000001460");
		map.put("body", proDesc);
		map.put("clientIp", NetworkUtil.getIp(getRequest()));
		map.put("mchntOrderNo", billNo);
		map.put("notifyUrl", "http://www.yomiing.com" + "/pay/notifyPath_act");
		map.put("returnUrl", "http://www.yomiing.com" + "/pay/returnPath");
//		map.put("notifyUrl", "http://oswin.com.tunnel.qydev.com" + "/pay/notifyPath_act");
//		map.put("returnUrl", "http://oswin.com.tunnel.qydev.com" + "/pay/returnPath");
		map.put("subject", proName);
		map.put("version", "api_NoEncrypt");
		map.put("payChannelId", payChannelId);
		String sign = doEncrypt(map, "65db3cb6a2c5b09f1beaf6ecefa9bfb3");
		map.put("signature", sign);
		String jsonObject = JSON.toJSONString(map);
		System.out.println("加密前reqParams=" + jsonObject);
		// 加密
		String reqParams = null;
		try
		{
			reqParams = Base64.encodeBase64String(
					RSAUtil.encryptByPublicKeyByPKCS1Padding(jsonObject.getBytes("utf-8"), PUBLIC_KEY));
			System.out.println("加密后reqParams=" + reqParams);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		String weixin_url = "http://trans.itppay.com/newsdk/api/v1.0/cli/order_api/0";
		String alipay_url = "http://trans.itppay.com/newsdk/api/v1.0/cli/order_pc/0";
		String parameterName = "orderInfo";
		String url = "2100000001".equals(payChannelId) ? weixin_url : alipay_url;

		String result = HttpUtil2.formPost(url, parameterName, reqParams);
		// result = new String(result.getBytes("iso8859-1"), "utf-8");
		System.out.println("请求返回参数：" + result);

		// 支付宝支付
		if ("3000000011".equals(payChannelId))
		{
			redirect(result);
		}
		else
		{
			JSONObject object = JSONObject.parseObject(result);
			String codeImg = "";
			if (object.getIntValue("respCode") == 200)
			{
				String reStr = object.getString("extra");
				codeImg = reStr.split("&")[1].split("=")[1];
				System.out.println("微信二维码图片地址 ： " + codeImg);
			}
			System.out.println("结果码： " + object.getIntValue("respCode") + "\t结果描述 ：" + object.getString("respMsg"));
			setAttr("proName", proName);
			setAttr("billNo", billNo);
			setAttr("cost", cost);
			setAttr("codeImg", codeImg);
			render("pay/showcode.html");
		}
	}

	/**
	 * 只处理活动订单
	 * 在线支付的异步通知页面 访问路径: /pay/notifyPath_act
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public void notifyPath_act() throws IOException, SQLException
	{
		System.out.println("notifyPath.............");
		getRequest().setCharacterEncoding("utf-8");
		int size = getRequest().getContentLength();
		InputStream is = getRequest().getInputStream();
		byte[] reqBodyBytes = readBytes(is, size);

		String json = new String(reqBodyBytes, "utf-8");

		System.out.println("异步通知内容：" + json + "\t内容长度 ： " + size);

		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) JSON.parseObject(json, HashMap.class);
		String upSignature = (String) map.get("signature");
		String downSignature = doEncrypt(map, "65db3cb6a2c5b09f1beaf6ecefa9bfb3");
		String jsonText = "";
		if (downSignature.equals(upSignature))
		{
			//0:待支付,1:支付中,2:支付成功,3:支付失败，4：已关闭
			int resCode = (Integer)map.get("paySt");
			System.out.println("测试回调验签成功\t 支付结果(0:待支付,1:支付中,2:支付成功,3:支付失败，4：已关闭) ：" + resCode);
			if (resCode != 2)
			{
				jsonText = "{\"success\":\"false\"}";
			}
			else
			{
				Activitybill bill = Activitybill.dao.findFirst("select * from activitybill where billNo = ?", (String)map.get("mchntOrderNo"));
				ActBillStatusUpdateAtom billStatusUpdateAtom = new ActBillStatusUpdateAtom(bill);
				boolean updateStatus = Db.tx(billStatusUpdateAtom);
				if (updateStatus)
				{
					System.out.println("订单状态更新成功！！！");
					jsonText = "{\"success\":\"true\"}";
				}
				else
				{
					jsonText = "{\"success\":\"false\"}";
				}
			}
		}
		else
		{
			System.out.println("回调验签失败");
			jsonText = "{\"success\":\"false\"}";
		}
		renderHtml(jsonText);
	}
	
	/**
	 * 第三支付接口接入 访问路径： /pay/start
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void start() throws UnsupportedEncodingException
	{
		System.out.println("开始支付！！！");
		
		String billNo = getPara("billNo", "");
		String payChannelId = getPara("payChannelId", "2100000001");
		
		Order order = Order.dao.getBillByOrderNo(billNo);
		//产品描述信息存在 订单详情表中
		Map<String, String> productinfo = Order.dao.getProductInfo(billNo);
		String proName = productinfo.get("proName");
		String proDesc = productinfo.get("proDesc");
		int cost = order.getCost().intValue();

		String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0dYM3DXkVg9q+WcNjBPWaUwKoeRMrwdE4p4F6fiztv/Ys6F5AxGCbFW5UfbtbQavMp9Rrg3+8mJ5/Lp8sjf471NFe6EvbCcVwJ63Q6fA4xVyCAE7mQdfAlpCk9WKN7Qa/HqwO/OM6JDyOyycnjnNi3f3K2tK/JbWd/SHYOSMEDQIDAQAB";// rsa加密
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount",cost * 100 + "");
//		map.put("amount",1 + "");
		map.put("appid", "0000001460");
		map.put("body", proDesc);
		map.put("clientIp", NetworkUtil.getIp(getRequest()));
		map.put("mchntOrderNo", billNo);
		map.put("notifyUrl", "http://www.yomiing.com" + "/pay/notifyPath");
		map.put("returnUrl", "http://www.yomiing.com" + "/pay/returnPath");
//		map.put("notifyUrl", "http://oswin.com.tunnel.qydev.com" + "/pay/notifyPath");
//		map.put("returnUrl", "http://oswin.com.tunnel.qydev.com" + "/pay/returnPath");
		map.put("subject", proName);
		map.put("version", "api_NoEncrypt");
		map.put("payChannelId", payChannelId);
		String sign = doEncrypt(map, "65db3cb6a2c5b09f1beaf6ecefa9bfb3");
		map.put("signature", sign);
		String jsonObject = JSON.toJSONString(map);
		System.out.println("加密前reqParams=" + jsonObject);
		// 加密
		String reqParams = null;
		try
		{
			reqParams = Base64.encodeBase64String(
					RSAUtil.encryptByPublicKeyByPKCS1Padding(jsonObject.getBytes("utf-8"), PUBLIC_KEY));
			System.out.println("加密后reqParams=" + reqParams);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		String weixin_url = "http://trans.itppay.com/newsdk/api/v1.0/cli/order_api/0";
		String alipay_url = "http://trans.itppay.com/newsdk/api/v1.0/cli/order_pc/0";
		String parameterName = "orderInfo";
		String url = "2100000001".equals(payChannelId) ? weixin_url : alipay_url;

		String result = HttpUtil2.formPost(url, parameterName, reqParams);
		// result = new String(result.getBytes("iso8859-1"), "utf-8");
		System.out.println("请求返回参数：" + result);

		// 支付宝支付
		if ("3000000011".equals(payChannelId))
		{
			redirect(result);
		}
		else
		{
			JSONObject object = JSONObject.parseObject(result);
			String codeImg = "";
			if (object.getIntValue("respCode") == 200)
			{
				String reStr = object.getString("extra");
				codeImg = reStr.split("&")[1].split("=")[1];
				System.out.println("微信二维码图片地址 ： " + codeImg);
			}
			System.out.println("结果码： " + object.getIntValue("respCode") + "\t结果描述 ：" + object.getString("respMsg"));
			setAttr("proName", proName);
			setAttr("billNo", billNo);
			setAttr("cost", cost);
			setAttr("codeImg", codeImg);
			render("pay/showcode.html");
		}
	}

	/**
	 * 
	 * 在线支付的异步通知页面 访问路径: /pay/notifyPath
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public void notifyPath() throws IOException, SQLException
	{
		System.out.println("notifyPath.............");
		getRequest().setCharacterEncoding("utf-8");
		int size = getRequest().getContentLength();
		InputStream is = getRequest().getInputStream();
		byte[] reqBodyBytes = readBytes(is, size);

		String json = new String(reqBodyBytes, "utf-8");

		System.out.println("异步通知内容：" + json + "\t内容长度 ： " + size);

		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) JSON.parseObject(json, HashMap.class);
		String upSignature = (String) map.get("signature");
		String downSignature = doEncrypt(map, "65db3cb6a2c5b09f1beaf6ecefa9bfb3");
		String jsonText = "";
		if (downSignature.equals(upSignature))
		{
			//0:待支付,1:支付中,2:支付成功,3:支付失败，4：已关闭
			int resCode = (Integer)map.get("paySt");
			System.out.println("测试回调验签成功\t 支付结果(0:待支付,1:支付中,2:支付成功,3:支付失败，4：已关闭) ：" + resCode);
			//当结果为2时，用户已支付成功，后面的逻辑出错也算成功的订单
			if (resCode != 2)
			{
				jsonText = "{\"success\":\"false\"}";
			}
			else
			{
				Order order = Order.dao.getBillByOrderNo((String)map.get("mchntOrderNo"));
				OrderStatusUpdateAtom atom = new OrderStatusUpdateAtom(order);
				boolean updateStatus = Db.tx(atom);
				if (updateStatus)
				{
					System.out.println("订单状态更新成功！！！");
					jsonText = "{\"success\":\"true\"}";
				}
				else
				{
					jsonText = "{\"success\":\"false\"}";
				}
			}
		}
		else
		{
			System.out.println("回调验签失败");
			jsonText = "{\"success\":\"false\"}";
		}
		renderHtml(jsonText);
	}
	
	public void notifyResult() {
		setAttr("res", getParaToInt("res", 0));
		render("pay/notify.html");
	}
	
	/**
	 * 查看订单的支付结果：
	 * 访问路径： /pay/payResult
	 */
	public void payResult() {
		String orderNo = getPara("orderNo", "");
		Map<String, Integer> resMap = new HashMap<String, Integer>();
		int code = 0;
		//活动订单另行处理
		if (!StrKit.isBlank(orderNo) && orderNo.length()== 19 && !orderNo.substring(17, 19).equals("01")) {
			code = Order.dao.queryPayStatus(orderNo);
		} else {
			code = Activitybill.dao.queryPayStatus(orderNo);
		}
		resMap.put("code", code);
		renderJson(resMap);
	}

	/**
	 * 在线支付前台页面通知 访问路径: /pay/returnPath
	 * @throws IOException 
	 */
	public void returnPath() throws IOException
	{
		redirect("/");
	}

	// 签名
	public static String doEncrypt(Map<String, Object> map, String mchntKey) throws UnsupportedEncodingException
	{
		Object[] keys = map.keySet().toArray();
		Arrays.sort(keys);
		StringBuilder originStr = new StringBuilder();
		for (Object key : keys)
		{
			if (null != map.get(key) && !map.get(key).toString().equals("") && !"signature".equals(key))
				originStr.append(key).append("=").append(map.get(key)).append("&");
		}
		originStr.append("key=").append(mchntKey);
		String sign = DigestUtils.md5Hex(originStr.toString().getBytes("utf-8"));
		return sign;
	}

	public static final byte[] readBytes(InputStream is, int contentLen)
	{
		if (contentLen > 0)
		{
			int readLen = 0;
			int readLengthThisTime = 0;
			byte[] message = new byte[contentLen];
			try
			{
				while (readLen != contentLen)
				{
					readLengthThisTime = is.read(message, readLen, contentLen - readLen);
					if (readLengthThisTime == -1)
					{// Should not happen.
						break;
					}
					readLen += readLengthThisTime;
				}
				return message;
			}
			catch (IOException e)
			{
				// e.printStackTrace();
			}
		}
		return new byte[] {};
	}
}
