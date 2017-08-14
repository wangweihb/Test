package com.ymhw.website.controler;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.ymhw.website.atom.CarOrderAddAtom;
import com.ymhw.website.model.Car;
import com.ymhw.website.model.Carrentstatus;
import com.ymhw.website.model.Equip;
import com.ymhw.website.model.Info;
import com.ymhw.website.model.Order;
import com.ymhw.website.model.User;
import com.ymhw.website.utils.Constant;
import com.ymhw.website.utils.DateUtil;
import com.ymhw.website.utils.OrderGenerator;

public class CarRentController extends Controller
{
	public void index(){
		int DEFAULT_EQUIP_COUNT = 8;
		setAttr("param", getPara("param", "car"));
		setAttr("equips", Equip.dao.queryValidEquips(DEFAULT_EQUIP_COUNT));
		setAttr("infos", Info.dao.getBasicInfos(DEFAULT_EQUIP_COUNT));
		render("rent.html");
	}
	
	/**
	 * 租车申请
	 * 访问路径：/carRent/applyforPath
	 */
	public void applyforPath() {
		User sessionUser = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (sessionUser == null)
		{
			renderHtml("<script  type='text/javascript'>alert('您还没有登录，请登录后租车！');window.location='/loginPath';</script>");
		}
		else
		{
			int carId = getParaToInt("carId", 0);
			String rentStartTime = getPara("rentStartTime", "");
			String rentEndTime = getPara("rentEndTime", "");
			long startTime = DateUtil.string2Date(rentStartTime, DateUtil.FORMAT0).getTime();
			long endTime = DateUtil.string2Date(rentEndTime, DateUtil.FORMAT0).getTime();
			if (startTime>=endTime) {
				rentStartTime = DateUtil.date2String(new Date(), DateUtil.FORMAT0);
				startTime = DateUtil.string2Date(rentStartTime, DateUtil.FORMAT0).getTime();
				
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, 1);
				date = calendar.getTime();
				rentEndTime = DateUtil.date2String(date, DateUtil.FORMAT0);
				endTime = DateUtil.string2Date(rentEndTime, DateUtil.FORMAT0).getTime();
			}
			int days = (int) ((endTime - startTime) / (24*60*60*1000));
			setAttr("rentStartTime", rentStartTime);
			setAttr("rentEndTime", rentEndTime);
			setAttr("days", days);
			Car car = Car.dao.getCarById(carId);
			setAttr("car", car);
			render("carRent_applyfor.html");
		}
	}
	
	/**
	 * 首页车辆信息显示：默认显示6条
	 * 访问路径：/carRent/portCars
	 */
	public void portCars() {
		int DEFAULT_COUNT = 6;
		int num = getParaToInt("num", 0);
		if (num == 0)
		{
			num = DEFAULT_COUNT;
		}
		Date day = new Date();
		renderJson(Car.dao.getNCars(num, day));
	}
	
	/**
	 * 预付款(主要完成订单的生成、订单详情记录的生成)：
	 * 访问路径：/carRent/repay
	 */
	public void repay() {
		User sessionUser = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (sessionUser == null)
		{
			renderHtml("<script  type='text/javascript'>alert('您还没有登录，请登录后报名！');window.location='/loginPath';</script>");
			return;
		}
		//type=3 代表租车订单
		int type = 3;
		String billNo = OrderGenerator.getOrderNo(type);
		int userId = sessionUser.getId();
		int carId = getParaToInt("carId", 0);
		int cost = getParaToInt("cost", 0);
		int carNum = getParaToInt("num", 0);
		String lmname = getPara("lmname", "");
		String lmnameIdcard = getPara("lmnameIdcard", "");
		String lmnameTel = getPara("lmnameTel", "");
		String rentStartTime = getPara("rentStartTime", "");
		String rentEndTime = getPara("rentEndTime", "");
		
		Map<String,  Object> params = new HashMap<String, Object>();
		params.put("billNo", billNo);
		params.put("type", type);
		params.put("userId", userId);
		params.put("carId", carId);
		params.put("cost", cost);
		params.put("num", carNum);
		params.put("lmname", lmname);
		params.put("lmnameIdcard", lmnameIdcard);
		params.put("lmnameTel", lmnameTel);
		params.put("rentStartTime", rentStartTime);
		params.put("rentEndTime", rentEndTime);
		
		Carrentstatus carrentstatus = Carrentstatus.dao.getByComposeId(carId, DateUtil.string2Date(rentStartTime, DateUtil.FORMAT0));
		if (carrentstatus != null && carrentstatus.getStatus() == 1) {
			renderHtml("<script  type='text/javascript'>alert('该车辆在这段时间已经被租满，请选择其他时段或者其他的车辆！');window.location='/carRent';</script>");
			return;
		}
		
		CarOrderAddAtom addAtom = new CarOrderAddAtom(params);
		boolean addResult = Db.tx(addAtom);
		if (addResult) {
			Map<String, String> productInfo = new HashMap<String, String>();
			productInfo = Order.dao.getProductInfo(billNo);
			setAttr("billNo", billNo);
			setAttr("proName", productInfo.get("proName"));
			setAttr("proDesc", productInfo.get("proDesc"));
			setAttr("cost", cost);
			render("pay/pay.html");
		} else {
			System.out.println("租车订单生成出错。。。");
		}
	}
	
	/**
	 * 根据各种条件搜索车辆信息
	 * 访问路径： /carRent/portCarsByCondition
	 */
	public void portCarsByCondition() {
		String types = getPara("types", "");
		int gearType = getParaToInt("gearType", 0);
		int factoryId = getParaToInt("factoryId", 0);
		String prices = getPara("prices", "");
		String rentStartTime = getPara("rentStartTime", "");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("types", types);
		param.put("gearType", gearType);
		param.put("factoryId", factoryId);
		param.put("prices", prices);
		param.put("rentStartTime", rentStartTime);
		List<Car> cars = Car.dao.queryCarsByConditon(param);
		System.out.println("查询到符合条件的车辆数量 ： " + cars.size());
		renderJson(cars);
		
	}
	
}
