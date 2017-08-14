package com.ymhw.website.controler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.ymhw.website.model.Activity;
import com.ymhw.website.model.Article;
import com.ymhw.website.model.Car;
import com.ymhw.website.model.Equip;
import com.ymhw.website.model.Farmstay;
import com.ymhw.website.model.Info;
import com.ymhw.website.model.Product;
import com.ymhw.website.model.Strategy;
import com.ymhw.website.model.User;
import com.ymhw.website.model.Welfare;
import com.ymhw.website.utils.Constant;
import com.ymhw.website.utils.MD5Util;

public class MobileIndexController extends Controller
{
	/**
	 * 移动端首页
	 * 访问链接：/mobile/
	 */
	public void index()
	{
		setAttr("activities", Activity.dao.queryHotline(6));
		int DEFAULT_NUM = 2;
		setAttr("records", Info.dao.getInfoDetails(DEFAULT_NUM));
		render("index.html");
	}
	
	/**
	 * 根据类型搜索活动列表
	 * 访问路径：/mobile/actListByType
	 */
	public void actListByType()
	{
		String type = getPara("type", "");
		if (StrKit.isBlank(type))
		{
			int SEARCH_NUM = 6;
			List<Activity> activities = new ArrayList<Activity>();
			activities = Activity.dao.querySearchList(SEARCH_NUM);
			setAttr("activities", activities);
			//默认5条
			int HOT_NUM = 5;
			List<Activity> hotActivities = new ArrayList<Activity>();
			hotActivities = Activity.dao.queryHotline(HOT_NUM);
			setAttr("hotActivities", hotActivities);
			
			render("activitySearch.html");
		}else if (type.equals("weekend")){
			int displayNum = 6;
			List<Activity> activities = new ArrayList<Activity>();
			activities = Activity.dao.queryByType2(1,displayNum);
			setAttr("activities", activities);
			render("actlist_weekend.html");
		}else if (type.equals("hot")){
			int displayNum = 6;
			List<Activity> activities = new ArrayList<Activity>();
			activities = Activity.dao.queryByType2(2,displayNum);
			setAttr("activities", activities);
			render("actlist_hot.html");
		}else if (type.equals("graduate")){
			int displayNum = 6;
			List<Activity> activities = new ArrayList<Activity>();
			activities = Activity.dao.queryByType2(3,displayNum);
			setAttr("activities", activities);
			render("actlist_graduate.html");
		}else if (type.equals("expand")){
			int displayNum = 6;
			List<Activity> activities = new ArrayList<Activity>();
			activities = Activity.dao.queryByType2(4,displayNum);
			setAttr("activities", activities);
			render("actlist_expand.html");
		}else if (type.equals("enshi")){
			int displayNum = 6;
			List<Activity> activities = new ArrayList<Activity>();
			activities = Activity.dao.queryByType2(5,displayNum);
			setAttr("activities", activities);
			render("actlist_enshi.html");
		}
	}
	
	/**
	 * 注册页面
	 * 访问路径：/mobile/registPath
	 */
	public void registPath()
	{
		render("register.html");
	}
	
	/**
	 * 登录页面
	 * 访问路径：/mobile/loginPath
	 */
	public void loginPath()
	{
		render("login.html");
	}
	
	/**
	 * 领队申请页面
	 * 访问路径：/mobile/applyforPath
	 */
	public void applyforPath()
	{
		render("applyfor.html");
	}
	
	/**
	 * 关于我们页面
	 * 访问路径：/mobile/aboutPath
	 */
	public void aboutPath()
	{
		render("about.html");
	}
	
	/**
	 * 联系页面
	 * 访问路径：/mobile/contactPath
	 */
	public void contactPath()
	{
		render("contact.html");
	}
	

	/**
	 * 服务协议页面
	 * 访问路径：/mobile/serviceAgreementPath
	 */
	public void serviceAgreementPath()
	{
		render("service_agreement.html");
	}
	
	/**
	 * 分享页面
	 * 访问路径：/mobile/sharePath
	 */
	public void sharePath()
	{
		setAttr("articles", Article.dao.getList(4));
		render("blog.html");
	}
	
	/**
	 * 会员中心页面
	 * 访问路径：/mobile/personinfoPath
	 */
	public void personinfoPath()
	{
		render("user.html");
	}
	
	/**
	 * 活动主页面
	 * 访问路径：/mobile/mainActPath
	 */
	public void mainActPath()
	{
		int displayNum = 0;
		List<Activity> activities_1 = new ArrayList<Activity>();
		activities_1 = Activity.dao.queryByType2(1,displayNum);
		setAttr("activities_1", activities_1);
		
//		List<Activity> activities_2 = new ArrayList<Activity>();
//		activities_2 = Activity.dao.queryByType2(2,displayNum);
//		setAttr("activities_2", activities_2);
//		
//		List<Activity> activities_3 = new ArrayList<Activity>();
//		activities_3 = Activity.dao.queryByType2(3,displayNum);
//		setAttr("activities_3", activities_3);
//		
//		List<Activity> activities_4 = new ArrayList<Activity>();
//		activities_4 = Activity.dao.queryByType2(4,displayNum);
//		setAttr("activities_4", activities_4);
//		
//		List<Activity> activities_5 = new ArrayList<Activity>();
//		activities_5 = Activity.dao.queryByType2(5,displayNum);
//		setAttr("activities_5", activities_5);
		
		render("activity_main.html");
	}
	
	/**
	 * 活动详情页面
	 * 访问路径：/mobile/actDetailPath
	 */
	public void actDetailPath()
	{
		int activityId = getParaToInt("id",0);
		Activity activity = Activity.dao.queryById(activityId);
		setAttr("act", activity);
		
		render("actDetail.html");
	}
	
	/**
	 * 户外拓展
	 * 访问路径：/mobile/doorPath
	 */
	public void doorPath()
	{
		render("outdoor_expand.html");
	}
	
	/**
	 * 装备租赁（移动端）
	 * 访问路径：/mobile/equipRentPath
	 */
	public void equipRentPath()
	{
		int DEFAULT_NUM = 100;
		setAttr("equips", Equip.dao.queryValidEquips(DEFAULT_NUM));
		render("equipRent.html");
	}
	
	/**
	 * 装备租赁详情
	 * 访问路劲：/mobile/equipDetailPath
	 */
	public void equipDetailPath()
	{
		render("equipment_detail.html");
	}
	
	/**
	 * 友鸣公益（移动端）
	 * 访问路径：/mobile/welfarePath
	 */
	public void welfarePath()
	{
		setAttr("welfares", Welfare.dao.queryList(100));
		render("welfare.html");
	}
	
	/**
	 * 公益详情
	 * 访问路径：/mobile/welfareDetailPath
	 */
	public void welfareDetailPath()
	{
		int id = getParaToInt("id", 0);
		Welfare welfare = Welfare.dao.queryById(id);
		if (welfare != null)
		{
			setAttr("welfare", welfare);
			render("welfareDetail.html");
		}
		else
		{
			render("error404.html");
		}
		
	}
	
	/**
	 * 企业团建
	 * /mobile/companyActPath
	 */
	public void companyActPath() {
		render("companyAct.html");
	}
	
	/**
	 * 精选攻略
	 * /mobile/strategyPath
	 */
	public void strategyPath() {
		int DEFAULT_NUM = 100;
		setAttr("strategies", Strategy.dao.getStrategys(DEFAULT_NUM));
		render("strategy.html");
	}
	
	/**
	 * 精选攻略详情
	 * /mobile/strategyDetailPath
	 */
	public void strategyDetailPath() {
		int id = getParaToInt("id", 0);
		setAttr("strategy", Strategy.dao.findById(id));
		render("strategyDetail.html");
	}
	
	
	/**
	 * 积分商城
	 * /mobile/integralMallPath
	 */
	public void integralMallPath() {
		render("integralMall.html");
	}
	
	/**
	 * 租车
	 * /mobile/carRentPath
	 */
	public void carRentPath() {
		int DEFAULT_NUM = 100;
		setAttr("cars", Car.dao.getNCars(DEFAULT_NUM, new Date()));
		render("carRent.html");
	}
	
	/**
	 * 周边目的地
	 * /mobile/informationPath
	 */
	public void informationPath() {
		int DEFAULT_NUM = 20;
		setAttr("records", Info.dao.getInfoDetails2(DEFAULT_NUM));
		render("info.html");
	}
	
	/**
	 * 周边目的地详情
	 * /mobile/infoDetailPath
	 */
	public void infoDetailPath() {
		int id = getParaToInt("id", 0);
		setAttr("record", Info.dao.getInfoAllById(id));
		render("infoDetail.html");
	}
	
	/**
	 * 农家乐详情
	 * /mobile/farmDetail
	 */
	public void farmDetail() {
		int farmId = getParaToInt("farmId", 0);
		int proId = getParaToInt("proId", 0);
		Product product = null;
		if (proId != 0) {
			product = Product.dao.getOneById(proId);
			farmId = product.getFarmId();
		} else{
			product = Product.dao.getOneByFarmId(farmId);
		}
		Farmstay farmstay = Farmstay.dao.findById(farmId);
		if (product != null) {
			setAttr("farm", farmstay);
			setAttr("product", product);
			setAttr("images", product.getPics().split(","));
			setAttr("others", Product.dao.getProductsByFarmId(farmId));
			render("farmstay.html");
		} else {
			renderHtml("<script  type='text/javascript'>alert('该农家乐、民宿暂时还没有产品!');window.location='/mobile/infoDetailPath?id="+farmstay.getDestinationId()+"';</script>");
//			redirect("/mobile/infoDetailPath?id="+farmstay.getDestinationId());
		}
	}
	
	/**
	 * 话题
	 * 路径：/mobile/topicPath
	 */
	public void topicPath() {
		int displayNum = 100;
		setAttr("topic1s", Article.dao.getListByType(1, displayNum));
		setAttr("topic2s", Article.dao.getListByType(2, displayNum));
		setAttr("topic3s", Article.dao.getListByType(3, displayNum));
		setAttr("topic4s", Article.dao.getListByType(4, displayNum));
		render("topic.html");
	}
	
	/**
	 * 个人中心
	 * 路径：/mobile/personPath
	 */
	public void personPath() {
		render("person.html");
	}
	
	/**
	 * 话题详情
	 * 路径：/mobile/topicDetailPath
	 */
	public void topicDetailPath() {
		int id = getParaToInt("id", 0);
		setAttr("topic", Article.dao.queryOneValid(id));
		render("topicDetail.html");
	}
	
	/**
	 * 移动端注册接口
	 * 请求参数:account,email,password,verifyCode
	 * 返回参数：code,msg,data  00 - 注册成功  01- 注册信息填写不完整！ 02 - 短信验证码错误！ 04 - 注册失败！
	 * 访问路径：/mobile/portRegister
	 */
	public void portRegister()
	{
		Map<String, Object> result = new HashMap<String, Object>();
		String account = getPara("account", "").trim();
		String email = getPara("email", "").trim();
		String telphone = getPara("telphone", "").trim();
		String password = getPara("password", "").trim();
		String vbcode = getPara("vbcode", "").trim();
		String vbcode_session = getSessionAttr(telphone);
		
		if(StrKit.isBlank(account) || StrKit.isBlank(email) || StrKit.isBlank(password) || StrKit.isBlank(password) || StrKit.isBlank(vbcode)){
			result.put("code","01");
			result.put("msg", "注册信息填写不完整！");
		}else if (!vbcode.equals(vbcode_session)) {
			result.put("code","02");
			result.put("msg", "短信验证码错误！");
		}else{
			User newUser = new User();
			newUser.setAccount(account);
			newUser.setPassword(MD5Util.shiroMD5(password, MD5Util.MD5_SALT));
			newUser.setEmail(email);
			newUser.setTelphone(telphone);
			newUser.setIsValid(Constant.VALID);
			newUser.setRoleId(Constant.ROLEID_GUEST);
			if(newUser.save()){
				result.put("code", "00");
				result.put("msg", "注册成功！");
			}else{
				result.put("code", "04");
				result.put("msg", "注册失败！");
			}
		}
		renderJson(result);
	}
	
	/**
	 * 登录接口
	 * 请求参数：input,password(input可为手机号、账户昵称、邮箱地址)
	 * 返回参数：code,msg,data
	 * 访问路径：/mobile/portLogin
	 */
	public void portLogin()
	{
		Map<String, Object> res = new HashMap<String, Object>();
		String input = getPara("input", "").trim();
		String  password = getPara("password", "").trim();
		if(StrKit.isBlank(input) || StrKit.isBlank(password)){
			res.put("code", "01");
			res.put("msg", "用户名或密码不能为空！");
			res.put("data", "");
		}else{
			User user = User.dao.queryLoginUser(input, MD5Util.MD5(password));
			if(user != null){
				res.put("code", "00");
				res.put("msg", "登陆成功！");
				res.put("data", "");
				
				setSessionAttr(Constant.YMHW_SESSIONUSER, user);
			}else{
				res.put("code", "02");
				res.put("msg", "用户名或密码错误！");
				res.put("data", "");
			}
		}
		System.out.println(res);
		renderJson(res);
	}
	
	/**
	 * 安全退出接口：
	 * 访问路径：/mobile/portExit
	 */
	public void portExit()
	{
		Map<String, Object> result = new HashMap<String, Object>();
		
		HttpSession session = getSession();
		session.invalidate();
		
		User ymUser = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (ymUser != null) {
			result.put("code", "00");
			result.put("msg", "注销成功！");
		}else{
			result.put("code", "01");
			result.put("msg", "注销失败！");
		}
		renderJson(result);
	}
	
	/*
	 * 根据类型选择活动
	 * 访问路径：/mobile/portActsByType
	 */
	public void portActsByType() {
		int type = getParaToInt("type", 0);
		renderJson(Activity.dao.queryByType2(type,0));
	}
	
	/*
	 * 根据类型选择周边目的地
	 * 访问路径：/mobile/portInfosByType
	 */
	public void portInfosByType() {
		int type = getParaToInt("type", 0);
		renderJson(Info.dao.getInfoDetailsByType(type));
	}
	
	
	/**
	 * 检查验证码正确
	 * @param verifyCode
	 * @return
	 */
	private boolean checkVerifyCode(String verifyCode)
	{
		if (verifyCode == null) 
		{
			return false;
		}
		
		String key = getSessionAttr(Constant.VERIFYCODE_KEY);
		return verifyCode.equalsIgnoreCase(key);
	}
}
