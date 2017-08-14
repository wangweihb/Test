package com.ymhw.website.atom;

import java.sql.SQLException;
import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.ymhw.website.model.Activitybill;
import com.ymhw.website.utils.DateUtil;
import com.ymhw.website.utils.SendEmail;
import com.ymhw.website.utils.SmsTool;

public class ActBillStatusUpdateAtom implements IAtom
{
	private Activitybill bill;
	
	public ActBillStatusUpdateAtom(Activitybill bill)
	{
		this.bill = bill;
	}
	
	@Override
	public boolean run() throws SQLException
	{
		if (bill == null)
		{
			return false;
		}
		int tmp = Db.update("update activitybill set status = 2 where billNo = ?", bill.getBillNo());
		if (tmp <= 0)
		{
			return false;
		}
		int tmp2 = Db.update("update activity set enteredNum = enteredNum + ? where id = ?", 
				bill.getAdultNum() + bill.getChildNum(), bill.getActId());
		if (tmp2 <= 0)
		{
			return false;
		}
		// 发送通知短信 bill.getLmNumber();
		StringBuilder builder = new StringBuilder();
		builder.append("尊敬的");
		builder.append(bill.getLmName());
		builder.append("先生/女士,您于");
		builder.append(DateUtil.date2String(bill.getCreateTime(), DateUtil.FORMAT1));
		builder.append("在友鸣旅行网(www.yomiing.com)成功订购了\"");
		builder.append(bill.getProName()).append("\",订单号：");
		builder.append(bill.getBillNo());
		builder.append(".");
		boolean smsResult = SmsTool.send(bill.getLmNumber(), builder.toString());
		System.out.println("订单短信发送结果 ： " + smsResult);
		
		StringBuilder notifyEmail = new StringBuilder();
		notifyEmail.append("订单号：").append(bill.getBillNo()).append("\r\n");
		notifyEmail.append("订单时间：").append(DateUtil.date2String(bill.getCreateTime(), DateUtil.FORMAT2)).append("\r\n");
		notifyEmail.append("客户姓名：").append(bill.getLmName()).append("\r\n");
		notifyEmail.append("客户电话：").append(bill.getLmNumber()).append("\r\n");
		notifyEmail.append("订单中：成人").append(bill.getAdultNum()).append("人").append("\r\n");
		notifyEmail.append("订单中：小孩").append(bill.getChildNum()).append("人").append("\r\n");
		SendEmail.send("1502718072@qq.com", "网站订单通知", notifyEmail.toString());
		return true;
	}

}
