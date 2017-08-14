package com.ymhw.website.controler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alipay.util.UtilDate;
import com.jfinal.core.Controller;
import com.ymhw.website.model.Activitybill;
import com.ymhw.website.model.User;
import com.ymhw.website.utils.Constant;
import com.ymhw.website.utils.OrderGenerator;
import com.ymhw.website.utils.StringTool;

/**
 * 活动订单管理
 * @author oswin
 */
public class ActivityBillController extends Controller
{
	private static final Logger log = Logger.getLogger(ActivityController.class);
	
	/**
	 * 生成订单
	 * 访问路径:/actbill/generateBill
	 * @throws UnsupportedEncodingException 
	 */
	public void generateBill() throws UnsupportedEncodingException
	{
		User sessionUser = getSessionAttr(Constant.YMHW_SESSIONUSER);
		int userId = sessionUser.getId();
		int actId = getParaToInt("actId", 0);
		int adultNum = getParaToInt("adultNum", 0);
		int childNum = getParaToInt("childNum", 0);
		int lmCertType = getParaToInt("lmCertType", 0);
		
		String lmName = getPara("lmname", "").trim();
		String lmNumber = getPara("lmNumber", "").trim();
		String lmCertNumber = getPara("lmCertNumber", "").trim();
		String remark = getPara("remark", "").trim();
		String proName = getPara("proName", "").trim();
		String proDesc = getPara("proDesc", "").trim();
		String costStr = getPara("cost", "");
		int cost = Integer.parseInt(costStr);
		String billNo = OrderGenerator.getOrderNo(1);
		
		Activitybill bill = new Activitybill();
		bill.setActId(actId);
		bill.setUserId(userId);
		bill.setAdultNum(adultNum);
		bill.setChildNum(childNum);
		bill.setLmCertType(lmCertType);
		bill.setCost((float)cost);
		bill.setLmName(lmName);
		bill.setLmNumber(lmNumber);
		bill.setLmCertNumber(lmCertNumber);
		bill.setRemark(remark);
		bill.setProName(proName);
		bill.setProDesc(proDesc);
		//订单是系统生成，唯一值
		bill.setBillNo(billNo);
		//默认未付款
		bill.setStatus(Constant.BILL_STATUS_NOPAY);
		bill.setCreateTime(new Date());
		
		//订单创建成功，直接进入付款页面，否则返回当前页
		if (bill.save())
		{
			log.info("订单已创建成功，进入支付宝付款页面。。。。。。。。。。");
			setAttr("billNo", billNo);
			setAttr("proName", proName);
			setAttr("proDesc", proDesc);
			setAttr("cost", cost);
			log.info("billNo : " + billNo + "\t proName : " + proName + "\t proDes : " + URLDecoder.decode(proDesc, "urf-8") + "\t cost : " + cost);
//			renderJsp("/alipayapi.jsp?billNo="+billNo +"&proName=" + proName + "&proDesc=" + proDesc + "&cost=" + cost);
		}
		else
		{
			setAttr("billNo", billNo);
			setAttr("proName", proName);
			setAttr("proDesc", proDesc);
			setAttr("cost", cost);
		}
		render("pay/actpay.html");
	}
	
	/**
	 * 生成订单
	 * 访问路径:/actbill/portGenerateBill
	 */
	public void portGenerateBill()
	{
		User sessionUser = getSessionAttr(Constant.YMHW_SESSIONUSER);
		int userId = sessionUser.getId();
		int actId = getParaToInt("actId", 0);
		int adultNum = getParaToInt("adultNum", 0);
		int childNum = getParaToInt("childNum", 0);
		int lmCertType = getParaToInt("lmCertType", 0);
		
		String lmName = getPara("lmname", "").trim();
		String lmNumber = getPara("lmNumber", "").trim();
		String lmCertNumber = getPara("lmCertNumber", "").trim();
		String remark = getPara("remark", "").trim();
		String proName = getPara("proName", "").trim();
		String proDesc = getPara("proDesc", "").trim();
		
		String costStr = getPara("cost", "");
		float cost = StringTool.isDouble(costStr) ? Float.parseFloat(costStr) : 0.0f;
		String billNo = OrderGenerator.getOrderNo(1);
		
		Activitybill bill = new Activitybill();
		bill.setActId(actId);
		bill.setUserId(userId);
		bill.setAdultNum(adultNum);
		bill.setChildNum(childNum);
		bill.setLmCertType(lmCertType);
		bill.setCost(cost);
		bill.setLmName(lmName);
		bill.setLmNumber(lmNumber);
		bill.setLmCertNumber(lmCertNumber);
		bill.setRemark(remark);
		bill.setProName(proName);
		bill.setProDesc(proDesc);
		//订单是系统生成，唯一值
		bill.setBillNo(billNo);
		//默认未付款
		bill.setStatus(Constant.BILL_STATUS_NOPAY);
		bill.setCreateTime(new Date());
		
		Map<String, Object> result = new HashMap<String, Object>();
		//订单创建成功，直接进入付款页面，否则返回当前页
		if (bill.save())
		{
			log.info("订单已创建成功，进入付款页面。。。。。。。。。。");
			result.put("code", "00");
			result.put("billNo", billNo);
			result.put("proName", proName);
			result.put("proDesc", proDesc);
			result.put("cost", cost);
			log.info("billNo : " + billNo + "\t proName : " + proName + "\t proDes : " + proDesc + "\t cost : " + cost);
//			renderJsp("/alipayapi.jsp?billNo="+billNo +"&proName=" + proName + "&proDesc=" + proDesc + "&cost=" + cost);
		}
		else
		{
			result.put("code", "01");
		}
		
		renderJson(result);
	}

}
