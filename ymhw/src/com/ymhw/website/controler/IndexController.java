package com.ymhw.website.controler;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.ymhw.website.model.Activity;
import com.ymhw.website.model.Activityitem;
import com.ymhw.website.model.Article;
import com.ymhw.website.model.Equip;
import com.ymhw.website.model.Info;
import com.ymhw.website.model.Strategy;
import com.ymhw.website.model.User;
import com.ymhw.website.model.Welfare;
import com.ymhw.website.utils.Constant;
import com.ymhw.website.utils.ExcelExportUtil;
import com.ymhw.website.utils.ImgUtil;
import com.ymhw.website.utils.ExcelExportUtil.Pair;

/** 
 * 索引（控制层，只负责控制，不负责业务逻辑处理）
 * 前端路径索引
 * @author      ws 
 * @since       1.0
 * create time：  2016年1月8日 下午2:37:40  
 */
public class IndexController extends Controller
{
	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	public void index()
	{
		logger.info("index-------------------------------------");
		setAttr("leaders", User.dao.queryTopLeaders(4));
		int HOT_NUM = 6;
		List<Activity> activities = new ArrayList<Activity>();
		List<Activity> activities2 = new ArrayList<Activity>();
		List<Activity> activities3 = new ArrayList<Activity>();
		List<Activity> activities4 = new ArrayList<Activity>();
		List<Activity> activities5 = new ArrayList<Activity>();
		activities = Activity.dao.queryByType(1, HOT_NUM);
		activities2 = Activity.dao.queryByType(2, HOT_NUM);
		activities3 = Activity.dao.queryByType(3, HOT_NUM);
		activities4 = Activity.dao.queryByType(4, HOT_NUM);
		activities5 = Activity.dao.queryByType(5, HOT_NUM);
		setAttr("activities", activities);
		setAttr("activities2", activities2);
		setAttr("activities3", activities3);
		setAttr("activities4", activities4);
		setAttr("activities5", activities5);
		
		int displayNum = 3;
		//int type = getParaToInt("type", 0);
		List<Article> art1s = Article.dao.getListByType(1, displayNum);
		setAttr("art1s", art1s);
		
		List<Article> art2s = Article.dao.getListByType(2, displayNum);
		setAttr("art2s", art2s);
		
		List<Article> art3s = Article.dao.getListByType(3, displayNum);
		setAttr("art3s", art3s);
		
		List<Article> art4s = Article.dao.getListByType(4, displayNum);
		setAttr("art4s", art4s);
		
//		int displayEquipNum = 6;
//		List<Equip> equips = new ArrayList<Equip>();
//		equips = Equip.dao.queryValidEquips(displayEquipNum);
//		setAttr("equips", equips);
		
		int DEFAULT_NUM = 7;
		List<Strategy> strategie0s = Strategy.dao.getStrategys(0, DEFAULT_NUM);
		List<Strategy> strategie1s = Strategy.dao.getStrategys(1, DEFAULT_NUM);
		List<Strategy> strategie2s = Strategy.dao.getStrategys(2, DEFAULT_NUM);
		List<Strategy> strategie3s = Strategy.dao.getStrategys(3, DEFAULT_NUM);
		List<Strategy> strategie4s = Strategy.dao.getStrategys(4, DEFAULT_NUM);
		List<Strategy> strategie5s = Strategy.dao.getStrategys(5, DEFAULT_NUM);
		List<Strategy> strategie6s = Strategy.dao.getStrategys(6, DEFAULT_NUM);
		setAttr("strategie0s", strategie0s);
		setAttr("strategie1s", strategie1s);
		setAttr("strategie2s", strategie2s);
		setAttr("strategie3s", strategie3s);
		setAttr("strategie4s", strategie4s);
		setAttr("strategie5s", strategie5s);
		setAttr("strategie6s", strategie6s);
		
		int DEFAULT_INFO_NUM = 4;
		setAttr("info1s", Info.dao.getInfosByType(1, DEFAULT_INFO_NUM));
		setAttr("info2s", Info.dao.getInfosByType(2, DEFAULT_INFO_NUM));
		setAttr("info3s", Info.dao.getInfosByType(3, DEFAULT_INFO_NUM));
		setAttr("info4s", Info.dao.getInfosByType(4, DEFAULT_INFO_NUM));
		
		setAttr("welfare1s", Welfare.dao.queryByType(1, 1));
		setAttr("welfare3s", Welfare.dao.queryByType(3, 1));
		render("index.html");
	}
	
	/**
	 * 访问路径：/registerPath
	 */
	public void registerPath()
	{
		render("register.html");
	}
	
	/**
	 * 访问路径：/loginPath
	 */
	public void loginPath()
	{
		render("login.html");
	}
	
	/**
	 * 访问路径：/topPath
	 */
	public void topPath()
	{
		render("top.html");
	}
	
	/**
	 * 活动发布 访问路径： /publishActPath
	 */
	public void publishActPath()
	{
		User user = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (user == null)
		{
			
			String errorText = "<script  type='text/javascript'>alert('您还没有登陆，请登陆后发布活动!');window.location='/login';</script>";
			renderHtml(errorText);
			
		}
		else if (user.getRoleId() == 3)
		{
			String errorText = "<script  type='text/javascript'>alert('您还没有权限发布活动，只有管理员和领队能发布活动!');window.location='/';</script>";
			renderHtml(errorText);
		}
		else
		{
			render("publishActivity.html");
		}
		
	}
	
	
	/**
	 * 活动主页
	 * 访问路径：/actMainPath
	 */
	public void actMainPath()
	{
		render("activityMain.html");
	}
	
	

	/**
	 * 活动搜索页
	 * 访问路径：/actSearchPath
	 */
	public void actSearchPath()
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
	}
	
	/**
	 * 活动搜索页
	 * 访问路径：/actDisplayPath
	 */
	public void actDisplayPath()
	{
		render("activityDisplay.html");
	}
	
	/**
	 * 审核活动页
	 * 访问路径：/checkActPath
	 */
	public void checkActPath()
	{
		setAttr("acts", Activity.dao.queryCheckActList());
		render("checkList.html");
	}
	
	/**
	 * 审核领队页
	 * 访问路径：/portCheckLeader
	 */
	public void portCheckLeader()
	{
		//setAttr("leaders", User.dao.queryCheckLeaders());
		//render("checkList.html");
		renderJson(User.dao.queryCheckLeaders());
	}
	
	/**
	 * 领队申请
	 * 访问路径：/applyfoLeaderPath
	 */
	public void applyfoLeaderPath()
	{
		User user = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (user == null)
		{
			String errorText = "<script  type='text/javascript'>alert('您还没有权限申请领队，请先注册并登陆成功后进行申请!');window.location='/registerPath';</script>";
			renderHtml(errorText);
			
		}
		else if (user.getRoleId() == 3)
		{
			render("applyfor.html");
		}
		else 
		{
			String errorText = "<script  type='text/javascript'>alert('管理员或领队不需要再次申请！');window.location='/';</script>";
			renderHtml(errorText);
		}
	}
	
	/**
	 * 关于我们
	 * 访问路径：/aboutPath
	 */
	public void aboutPath()
	{
		render("about.html");
	}
	
	/**
	 * 装备
	 * 访问路径：/equipmentPath
	 */
	public void equipmentPath()
	{
		//默认5条
		int HOT_NUM = 5;
		List<Activity> hotActivities = new ArrayList<Activity>();
		hotActivities = Activity.dao.queryHotline(HOT_NUM);
		setAttr("hotActivities", hotActivities);
		render("equipment.html");
	}
	
	/**
	 * 装备
	 * 访问路径：/equipmentSearchPath
	 */
	public void equipmentSearchPath()
	{
		render("equipment_search.html");
	}
	
	/**
	 * 租赁服务：
	 * 访问路径：/rentPath
	 */
	public void rentPath() {
		render("rent.html");
	}
	
	/**
	 * 关于帮助
	 * 访问路径：/aboutHelpPath
	 */
	public void aboutHelpPath()
	{
		setAttr("param", getPara("param",""));
		render("aboutHelp.html");
	}
	
	/**
	 * 服务协议
	 * 访问路径：/serviceAgreePath
	 */
	public void serviceAgreePath()
	{
		render("serviceAgreement.html");
	}
	
	/**
	 * 户外拓展
	 * 访问路径：/outdoorPath
	 */
	public void outdoorPath()
	{
		render("outdoor_expansion.html");
	}
	
	/**
	 * 分享
	 * 访问路径：/sharePath
	 */
	public void sharePath()
	{
		int DISPLAY_NUM = 0;
		List<Article> articles = Article.dao.getList(DISPLAY_NUM);
		setAttr("articles", articles);
		
		int displayNum = 4;
		//int type = getParaToInt("type", 0);
		List<Article> art1s = Article.dao.getListByType(1, displayNum);
		setAttr("art1s", art1s);
		
		List<Article> art2s = Article.dao.getListByType(2, displayNum);
		setAttr("art2s", art2s);
		
		List<Article> art3s = Article.dao.getListByType(3, displayNum);
		setAttr("art3s", art3s);
		
		List<Article> art4s = Article.dao.getListByType(4, displayNum);
		setAttr("art4s", art4s);
		
		render("articleMain.html");
	}
	
	/**
	 * 分享
	 * 访问路径：/shareDisplayPath
	 */
	public void shareDisplayPath()
	{
		render("forum.html");
	}
	
	/**
	 * 装备上传页面
	 * 访问路径：/uploadEquipPath
	 */
	public void uploadEquipPath()
	{
		render("uploadEquip.html");
	}
	/**
	 * 导出测试
	 * 访问路径：/excelExport
	 */
	public void excelExport()
	{
		List<Activityitem> activityitems = Activityitem.dao.find("select * from activityitem");
		List<Pair> titles = new ArrayList<Pair>();
		titles.add(new Pair("id", "流水号"));
		titles.add(new Pair("name", "活动项"));
		titles.add(new Pair("isValid", "状态"));
		
		ExcelExportUtil.exportByRecord(getResponse(), getRequest(), "活动项表", titles , activityitems);
		renderNull();
	}
	
	/**
	 * 顶部
	 * 访问路径：/header
	 */
	public void header()
	{
		render("header.html");
	}
	
	public void test()
	{
		System.out.println("test----");
		logger.debug("logger debug test");
		logger.info("logger INFO TEST");
		logger.warn("logger warn test");
//		renderNull();
		render("test/baidumap.html");
	}
	
	public void convert()
	{
		List<User> users = User.dao.find("select id,otherCertificate from user");
		for(User obj : users){
			String picName = UUID.randomUUID().toString();
			String pic = obj.getOtherCertificate();
			if(StrKit.isBlank(pic)){
				continue;
			}
			String firstPart = pic.split(",")[0];
			String secondPart = pic.split(",")[1];
			String suffix = "." + firstPart.substring(firstPart.indexOf("/")+1, firstPart.indexOf(";"));
			String imgPath = "/upload/user/certificate/" + picName + suffix;
			boolean convertResult = ImgUtil.generateImage(secondPart, "/upload/user/certificate/" + (picName + suffix));
			if(convertResult){
				String sql = "update user set otherCertificate = '"+imgPath+"' where id = " + obj.getId() + ";";
				System.out.println(sql);
			}
		}
		renderNull();
	}
	
	public void strategyPath() {
		int DEFAULT_NUM = 10;
		List<Strategy> strategie0s = Strategy.dao.getStrategys(0, DEFAULT_NUM);
		List<Strategy> strategie1s = Strategy.dao.getStrategys(1, DEFAULT_NUM);
		List<Strategy> strategie2s = Strategy.dao.getStrategys(2, DEFAULT_NUM);
		List<Strategy> strategie3s = Strategy.dao.getStrategys(3, DEFAULT_NUM);
		List<Strategy> strategie4s = Strategy.dao.getStrategys(4, DEFAULT_NUM);
		List<Strategy> strategie5s = Strategy.dao.getStrategys(5, DEFAULT_NUM);
		List<Strategy> strategie6s = Strategy.dao.getStrategys(6, DEFAULT_NUM);
		setAttr("strategie0s", strategie0s);
		setAttr("strategie1s", strategie1s);
		setAttr("strategie2s", strategie2s);
		setAttr("strategie3s", strategie3s);
		setAttr("strategie4s", strategie4s);
		setAttr("strategie5s", strategie5s);
		setAttr("strategie6s", strategie6s);
		render("strategy.html");
	}
	
	public void strategyDetailPath() {
		int id = getParaToInt("id", 0);
		setAttr("strategy", Strategy.dao.findById(id));
		render("strategyDetail.html");
	}
	
	/**
	 * 特产页面：
	 * 访问路径：/specialtyPath
	 */
	public void specialtyPath() {
		render("specialProduct.html");
	}
	
	/**
	 * 积分商城页面：
	 * 访问路径：/integralMallPath
	 */
	public void integralMallPath() {
		render("integral-mall.html");
	}
	
	/**
	 * 联系我们：
	 * 访问路径：/contactusPath
	 */
	public void contactusPath() {
		render("contact_us.html");
	}
	
	/**
	 * 根据类型（活动、周边目的地、攻略）和相关关键字搜索
	 * 访问路径：/portRecords
	 * @throws UnsupportedEncodingException 
	 */
	public void portRecords() throws UnsupportedEncodingException {
		int type = getParaToInt("type", 1);
		String keyword = getPara("keyword", "");
		keyword = URLDecoder.decode(keyword, "UTF-8");
		int data = 0;
		if (type == 1) {
			data = Activity.dao.searchByKeyword(keyword);
		} else if (type == 2) {
			data = Info.dao.searchByKeyword(keyword);
		} else if (type == 3) {
			data = Strategy.dao.searchByKeyword(keyword);
		}
		renderJson(data);
	}
	
}
