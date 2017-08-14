package com.ymhw.website.atom;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import com.jfinal.plugin.activerecord.IAtom;
import com.ymhw.website.model.Order;
import com.ymhw.website.model.Productorderdetail;
import com.ymhw.website.utils.Constant;


public class FarmProductOrderAddAtom implements IAtom
{
	private Map<String, Object> params;
	
	public FarmProductOrderAddAtom(Map<String, Object> params)
	{
		this.params = params;
	}
	
	@Override
	public boolean run() throws SQLException
	{
		Order order = new Order();
		int cost = (Integer)params.get("cost");
		order.setCost(new BigDecimal(cost));
		order.setUserId((Integer)params.get("userId"));
		order.setOrderNo((String)params.get("billNo"));
		order.setType((Integer)params.get("type"));
		order.setCreatTime(new Date());
		order.setLastUpdateTime(new Date());
		order.setStatus(Constant.BILL_STATUS_NOPAY);
		//order 表的处理
		boolean result = order.save();
		if (!result)
		{
			//尽早回滚
			return false;
		}
		Productorderdetail orderDetail = new Productorderdetail();
		orderDetail.setOrderNo(order.getOrderNo());
		orderDetail.setProId((Integer)params.get("productId"));
		orderDetail.setLmname((String)params.get("lmname"));
		orderDetail.setLmnameTel((String)params.get("lmnameTel"));
		orderDetail.setNum((Integer)params.get("num"));
		orderDetail.setLastUpdateTime(new Date());
		orderDetail.setIsValid(Constant.INVALID);
		if (!orderDetail.save()) {
			return false;
		}
		return true;
	}

}
