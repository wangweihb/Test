package com.ymhw.website.atom;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.ymhw.website.model.Car;
import com.ymhw.website.model.Carorderdetail;
import com.ymhw.website.model.Carrentstatus;
import com.ymhw.website.model.Order;
import com.ymhw.website.model.Productorderdetail;
import com.ymhw.website.utils.Constant;
import com.ymhw.website.utils.DateUtil;
import com.ymhw.website.utils.SendEmail;
import com.ymhw.website.utils.SmsTool;

public class OrderStatusUpdateAtom implements IAtom
{
	private Order order;
	
	public OrderStatusUpdateAtom(Order order)
	{
		this.order = order;
	}
	
	@Override
	public boolean run() throws SQLException
	{
		//第一步：根据订单号更新订单号中的支付状态和时间
		if (order == null)
		{
			return false;
		}
		int tmp = Db.update("update `Order` set status = 2,lastUpdateTime = ? where orderNo = ?", new Date(), order.getOrderNo());
		if (tmp <= 0)
		{
			return false;
		}
		
		//第二步:根据不同的商品类型  来分类处理订单详情 1-活动订单 2-装备订单 3-车辆订单 4-农家乐中菜品订单
		int type = order.getType();
		if (type == 1) {
			
		} else if (type == 2) {
			
		} else if (type == 3) {
			
			//支付完成之后，更新订单详情表中的记录有效
			int res1 = Db.update("update `carorderdetail` set isValid = 1,lastUpdateTime = ? where orderNo = ?", new Date(), order.getOrderNo());
			if (res1 <= 0) {
				return false;
			}
			
			//记录 车辆租用状态表  先查询相关记录 再确定是插入记录还是更新记录
			Carorderdetail detail = Carorderdetail.dao.getOrderDetailByOderNo(order.getOrderNo());
			Car car = Car.dao.getCarById(detail.getCarId());
			if (detail == null || car == null) {
				return false;
			}
			Date startdate = detail.getRentStartTime();
			Date enddate = detail.getRentEndTime();
			//前开后闭的区间
			List<Date> dates = DateUtil.dayLoop(startdate, enddate);
			for(Date curDay : dates) {
				Carrentstatus theone = Carrentstatus.dao.getByComposeId(detail.getCarId(), curDay);
				if (theone == null) {
					Carrentstatus carrentstatus = new Carrentstatus();
					carrentstatus.setCarId(detail.getCarId());
					carrentstatus.setDay(curDay);
					carrentstatus.setTotal(car.getTotal());
					carrentstatus.setRented(detail.getNum());
					//已经租满
					if (detail.getNum() >= car.getTotal()) {
						carrentstatus.setStatus(1);
					}
					carrentstatus.setIsValid(Constant.VALID);
					if (!carrentstatus.save()) {
						return false;
					}
				} else {
					int rented = theone.getRented();
					rented += detail.getNum();
					int updateResult = 0;
					if (rented >= theone.getTotal()) {
						updateResult = Db.update("update carrentstatus set rented=?,status=1 "
								+ "where isValid = 1 and carId = ? and day = ?", rented, detail.getCarId(), curDay);
					} else {
						updateResult = Db.update("update carrentstatus set rented=? "
								+ "where isValid = 1 and carId = ? and day = ?", rented, detail.getCarId(), curDay);
					}
					if (updateResult<=0) {
						return false;
					}
				}
			}
			
			//给用户发送通知短信，并通知网站工作人员处理订单
			StringBuilder builder = new StringBuilder();
			builder.append("尊敬的");
			builder.append(detail.getLmname());
			builder.append("先生/女士,您于");
			builder.append(DateUtil.date2String(order.getCreatTime(), DateUtil.FORMAT1));
			builder.append("在友鸣旅行网(www.yomiing.com)成功租用了\"");
			builder.append(order.getProductInfo(detail.getOrderNo()).get("proDesc")).append("\",请您在规定的时间到对应门店取车，订单号：");
			builder.append(order.getOrderNo());
			builder.append(".");
			boolean smsResult = SmsTool.send(detail.getLmnameTel(), builder.toString());
			System.out.println("订单短信发送结果 ： " + smsResult);
			
			StringBuilder notifyEmail = new StringBuilder();
			notifyEmail.append("订单号(租车)：").append(order.getOrderNo()).append("\r\n");
			notifyEmail.append("订单时间：").append(DateUtil.date2String(order.getCreatTime(), DateUtil.FORMAT2)).append("\r\n");
			notifyEmail.append("客户姓名：").append(detail.getLmname()).append("\r\n");
			notifyEmail.append("客户电话：").append(detail.getLmnameTel()).append("\r\n");
			notifyEmail.append("订单中：订购车辆数").append(detail.getNum()).append("辆").append("\r\n");
			notifyEmail.append("总金额：").append(order.getCost()).append("元").append("\r\n");
			SendEmail.send("1502718072@qq.com", "网站订单通知（租车）", notifyEmail.toString());
			
		} else if (type == 4) {			//农家乐中产品的订单
			//支付完成之后，更新订单详情表中的记录有效
			int res1 = Db.update("update `productorderdetail` set isValid = 1,lastUpdateTime = ? where orderNo = ?", new Date(), order.getOrderNo());
			if (res1 <= 0) {
				return false;
			}
			Productorderdetail productorderdetail = Productorderdetail.dao.getFirst(order.getOrderNo());
			if (productorderdetail == null) {
				return false;
			}
			//product表中的订购数量+1
			int res2 = Db.update("update `product` set soldNum = soldNum + ? where isValid = 1 and id = ?",
					productorderdetail.getNum() ,productorderdetail.getProId());
			if (res2 <= 0) {
				return false;
			}
			
			//给用户发送通知短信，并通知网站工作人员处理订单
			StringBuilder builder = new StringBuilder();
			builder.append("尊敬的");
			builder.append(productorderdetail.getLmname());
			builder.append("先生/女士,您于");
			builder.append(DateUtil.date2String(order.getCreatTime(), DateUtil.FORMAT1));
			builder.append("在友鸣旅行网(www.yomiing.com)成功购买了\"");
			builder.append(order.getProductInfo(productorderdetail.getOrderNo()).get("proDesc")).append("\",请您凭此短信到农家乐店铺消费，订单号：");
			builder.append(order.getOrderNo());
			builder.append(".");
			boolean smsResult = SmsTool.send(productorderdetail.getLmnameTel(), builder.toString());
			System.out.println("订单短信发送结果 ： " + smsResult);
			
			StringBuilder notifyEmail = new StringBuilder();
			notifyEmail.append("订单号(农家乐)：").append(order.getOrderNo()).append("\r\n");
			notifyEmail.append("订单时间：").append(DateUtil.date2String(order.getCreatTime(), DateUtil.FORMAT2)).append("\r\n");
			notifyEmail.append("客户姓名：").append(productorderdetail.getLmname()).append("\r\n");
			notifyEmail.append("客户电话：").append(productorderdetail.getLmnameTel()).append("\r\n");
			notifyEmail.append("订单中：购买数量").append(productorderdetail.getNum()).append("\r\n");
			notifyEmail.append("总金额：").append(order.getCost()).append("元").append("\r\n");
			SendEmail.send("1502718072@qq.com", "网站订单通知（农家乐）", notifyEmail.toString());
		}
		return true;
	}

}
