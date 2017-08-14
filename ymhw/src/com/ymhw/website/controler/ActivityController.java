package com.ymhw.website.controler;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.ymhw.website.model.Activity;
import com.ymhw.website.model.Activitytype;
import com.ymhw.website.model.Signup;
import com.ymhw.website.model.User;
import com.ymhw.website.utils.Constant;
import com.ymhw.website.utils.ImgUtil;


/** 
 * 活动
 * @author      oswin 
 * @since       1.0
 * create time：  2016年3月10日 下午19:27:40  
 * 注意：带有"port" 前缀的方法可以作为接口方法
 */
public class ActivityController extends Controller
{
	private static final Log logger = Log.getLog(ActivityController.class);
	
	public void index()
	{
		
	}
	
	/**
	 * 活动发布：
	 * 访问路径：/activity/publish
	 */
	public void publish()
	{
//		int property = getParaToInt("property");
		String title = getPara("title","").trim();
		String desc = getPara("desc","").trim();
		//活动蜂鸣图片的base64字符串
		String pic = getPara("pic","").trim();		
		String content = getPara("contentList","").trim();
		String type = getPara("typeList","").trim();
		String departurePlace = getPara("departurePlace","").trim();
		Date startTime = getParaToDate("startTime",null);
		String destination = getPara("destination","").trim();
		Date endTime = getParaToDate("endTime",null);
		String gatherPlace = getPara("gatherPlace","").trim();
		Date deadline = getParaToDate("deadline",null);
		Date gatherTime = getParaToDate("gatherTime",null);
		int intensityLevel = getParaToInt("intensityLevel",1);
		int totolNum = getParaToInt("totolNum",0);
		String contactnumber = getPara("contactnumber","").trim();
		String cost = getPara("cost","");
		
		String routeDesc = getPara("routeDesc","").trim();
		String travelPlan = getPara("travelPlan","").trim();
		String requiredEquip = getPara("requiredEquip","").trim();
		String notice = getPara("notice","").trim();
		String fee = getPara("fee", "").trim();
		String enroll = getPara("enroll", "").trim();
		int vehicle = getParaToInt("vehicle", 0);
			
		Activity activity = new Activity();
		activity.setContactnumber(contactnumber);
		activity.setContent(content);
		if (StrKit.notBlank(cost)) 
		{
			activity.setCost(Float.parseFloat(cost));
		}	
		activity.setDeadline(deadline);
		activity.setDeparturePlace(departurePlace);
		activity.setDesc(desc);
		activity.setDestination(destination);
		activity.setEndTime(endTime);
		activity.setGatherPlace(gatherPlace);
		activity.setGatherTime(gatherTime);
		
		activity.setIntensityLevel(intensityLevel);
		activity.setIsValid(Constant.VALID);
		//activity.setLeaders(null);
		activity.setNotice(notice);
		
		if(!StrKit.isBlank(pic)){
			String firstPart = pic.split(",")[0];
			String secondPart = pic.split(",")[1];
			String suffix = "." + firstPart.substring(firstPart.indexOf("/")+1, firstPart.indexOf(";"));
//			String imgPath = "/upload/activity/"+UUID.randomUUID().toString() + suffix;
			String imgPath = PathKit.getWebRootPath() + File.separator + PropKit.get("uploadPath") + 
					File.separator + "activity" + File.separator + UUID.randomUUID().toString() + suffix;
			boolean convertResult = ImgUtil.generateImage(secondPart, imgPath);
			if(!convertResult){
				logger.warn("base64 字符串转图片文件错误！");
			} else {
				activity.setPic(imgPath.substring(imgPath.indexOf("/upload")));
			}
		}
		
//		activity.setPic(pic);
//		activity.setProperty(property);
	
		activity.setPublishTime(new Date());
		//activity.setQqGroup(qqGroup);
		activity.setRequiredEquip(requiredEquip);
		activity.setRouteDesc(routeDesc);
		activity.setFeeDesc(fee);
		activity.setEnroll(enroll);
		//activity.setScenicSpot(scenicSpot);
		activity.setStartTime(startTime);
		activity.setTitle(title);
		activity.setTotalNum(new Integer(totolNum));
		activity.setTravelPlan(travelPlan);
		activity.setType(type);
		activity.setVehicle(vehicle);
		
		activity.setCheckStatus(Constant.STATUS_SUMBIT_NOT_CHECK);
		
		//管理员设置数据
		User user = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (user != null)
		{
			if (user.getRoleId() == 1)
			{
				String discountPrice = getPara("discountPrice").trim();
				if (!StrKit.isBlank(discountPrice))
				{
					activity.setDiscountPrice(Float.parseFloat(discountPrice));
				}
				int isRecommend = getParaToInt("isRecommend");
				int top = getParaToInt("topStr");
				String leaders = getPara("leaders").trim();
				activity.setIsRecommend(isRecommend);
				activity.setTop(top);
				activity.setLeaders(leaders);
				activity.setCheckStatus(Constant.STATUS_CHECK_PASS);
				
				if (activity.save())
				{
					renderHtml("<script  type='text/javascript'>alert('发布成功！点击确定回主页查看！');window.location='/';</script>");
				}
				else
				{
					renderHtml("<script  type='text/javascript'>alert('发布失败！请重新发布活动！');window.location='/activity/publish';</script>");
				}
			}
			else if (user.getRoleId() == 2)
			{
				activity.setLeaders(user.getId() + "");
				if (activity.save())
				{
					renderHtml("<script  type='text/javascript'>alert('活动发布已提交！请耐心等待管理员审核！');window.location='/';</script>");
				}
				else
				{
					renderHtml("<script  type='text/javascript'>alert('发布失败！请重新发布活动！');window.location='/activity/publish';</script>");
				}
			}
		}
	}
	
	/**
	 * 编辑活动页面
	 * 访问路径：/activity/editActPath
	 */
	public void editActPath()
	{
		Integer id = getParaToInt("id");
		if (id != null && id != 0)
		{
			Activity activity = Activity.dao.queryValidById(id);
			if (activity != null)
			{
				String leaders = activity.getLeaders();
				String leaderName = "";
				if (!StrKit.isBlank(leaders))
				{
					int leaderId = Integer.parseInt(leaders.split(",")[0]);
					leaderName = User.dao.findById(leaderId).getName();
				}
				setAttr("leaderName", leaderName);
				setAttr("act", activity);
				render("checkActEdit.html");
			}
			else
			{
				renderHtml("<script  type='text/javascript'>alert('活动以被删除，请联系管理员');window.location='/checkActPath';</script>");
			}
		}
		else
		{
			renderHtml("<script  type='text/javascript'>alert('活动不存在');window.location='/checkActPath';</script>");
		}
		
	}
	
	/**
	 * 编辑活动操作
	 * 访问路径：/activity/editAct
	 */
	public void editAct()
	{
		int actId = getParaToInt("actId", 0);
		int property = getParaToInt("property", 0);
		String title = getPara("title", "").trim();
		String desc = getPara("desc", "").trim();
		//活动蜂鸣图片的base64字符串
		String pic = getPara("pic", "").trim();		
		String content = getPara("contentList", "").trim();
		String type = getPara("typeList", "").trim();
		String departurePlace = getPara("departurePlace", "").trim();
		Date startTime = getParaToDate("startTime",null);
		String destination = getPara("destination", "").trim();
		Date endTime = getParaToDate("endTime",null);
		String gatherPlace = getPara("gatherPlace", "").trim();
		Date deadline = getParaToDate("deadline",null);
		Date gatherTime = getParaToDate("gatherTime",null);
		int intensityLevel = getParaToInt("intensityLevel",1);
		int totolNum = getParaToInt("totolNum",0);
		String contactnumber = getPara("contactnumber", "").trim();
		String cost = getPara("cost");
		
		String routeDesc = getPara("routeDesc", "").trim();
		String travelPlan = getPara("travelPlan", "").trim();
		String requiredEquip = getPara("requiredEquip", "").trim();
		String notice = getPara("notice").trim();
		String feeDesc = getPara("fee", "").trim();
		String enroll = getPara("enroll", "").trim();
		int vehicle = getParaToInt("vehicle");
		
		Activity activity = Activity.dao.queryValidById(actId);
		activity.setContactnumber(contactnumber);
		activity.setContent(content);
		if (StrKit.notBlank(cost)) 
		{
			activity.setCost(Float.parseFloat(cost));
		}	
		activity.setDeadline(deadline);
		activity.setDeparturePlace(departurePlace);
		activity.setDesc(desc);
		activity.setDestination(destination);
		activity.setEndTime(endTime);
		activity.setGatherPlace(gatherPlace);
		activity.setGatherTime(gatherTime);
		
		activity.setIntensityLevel(intensityLevel);
		activity.setNotice(notice);
		activity.setPic(pic);
		activity.setProperty(property);
	
		activity.setRequiredEquip(requiredEquip);
		activity.setRouteDesc(routeDesc);
		//activity.setScenicSpot(scenicSpot);
		activity.setStartTime(startTime);
		activity.setTitle(title);
		activity.setTotalNum(new Integer(totolNum));
		activity.setTravelPlan(travelPlan);
		activity.setType(type);
		activity.setVehicle(vehicle);
		activity.setFeeDesc(feeDesc);
		activity.setEnroll(enroll);
		
		User user = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (user != null)
		{
			if (user.getRoleId() == 1)
			{
				int isRecommend = getParaToInt("isRecommend");
				int top = getParaToInt("topStr");
				Integer isPass = getParaToInt("isPass");
				String checkMsg = getPara("checkMsg");
				if (isPass == 1)
				{
					activity.setCheckStatus(Constant.STATUS_CHECK_PASS);
				}
				else
				{
					activity.setCheckStatus(Constant.STATUS_CHECK_NOT_PASS);
				}
				activity.setIsRecommend(isRecommend);
				activity.setTop(top);
				activity.setCheckMsg(checkMsg);
				if (activity.update())
				{
					renderHtml("<script  type='text/javascript'>alert('活动修改成功！');window.location='/activity/editActPath?id="+actId+"';</script>");
				}
				else
				{
					renderHtml("<script  type='text/javascript'>alert('修改失败，请重新修改！');</script>");
				}
			}
			else if (user.getRoleId() == 2)
			{
				if (activity.update())
				{
					renderHtml("<script  type='text/javascript'>alert('活动修改已提交！请耐心等待管理员审核！');window.location='/activity/editActPath?id="+actId+"';</script>");
				}
				else
				{
					renderHtml("<script  type='text/javascript'>alert('修改失败，请重新修改！');</script>");
				}
			}
		}
	}
	
	/**
	 * 活动审核
	 * 访问路径：/activity/check
	 */
	public void check()
	{
		User user = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (user != null && user.getRoleId() == 1)
		{
			Integer activityId = getParaToInt("actId");
			int isRecommend = getParaToInt("isRecommend");
			int top = getParaToInt("topStr");
			Integer isPass = getParaToInt("isPass");
			String checkMsg = getPara("checkMsg");
			Activity activity = Activity.dao.queryValidById(activityId);
			
			activity.setIsRecommend(isRecommend);
			activity.setTop(top);
			activity.setCheckMsg(checkMsg);
			
			if (isPass == 1)
			{
				activity.setCheckStatus(Constant.STATUS_CHECK_PASS);
			}
			else
			{
				activity.setCheckStatus(Constant.STATUS_CHECK_NOT_PASS);
			}
			if (activity.update())
			{
				renderHtml("<script  type='text/javascript'>alert('提交成功！');window.location='/checkActPath';</script>");
			}
			else
			{
				renderHtml("<script  type='text/javascript'>alert('提交失败，请稍后再试！');window.location='/checkActPath';</script>");
			}
		}
		else
		{
			renderHtml("<script  type='text/javascript'>alert('只有管理员才能审核活动');window.location='/login';</script>");
		}
	}
	
	/**
	 * 某个领队的所有活动<br/>
	 * 访问路径：/activity/publishActResult
	 */
	public void publishActResult()
	{
		User user = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (user != null && user.getRoleId() == 2)
		{
			//数据库某个字段的截取
			setAttr("acts", Activity.dao.queryListByLeader(user.getId()));
			render("publishActResult.html");
		}
		else
		{
			renderHtml("<script  type='text/javascript'>alert('您的登陆已失效，点击确定返回主页');window.location='/';</script>");
		}
		
	}
	/**
	 * 活动详情显示<br/>
	 * 访问路径：/activity/display
	 */
	public void display()
	{
		int activityId = getParaToInt("activityId");
		Activity activity = Activity.dao.queryById(activityId);
		setAttr("act", activity);
		
		String leaderStr = Activity.dao.getLeaderByAct(activityId);
		int leaderId = 0;
		if (!StrKit.isBlank(leaderStr)) {
			leaderId = Integer.parseInt(leaderStr);
		}
		List<Activity> others = Activity.dao.queryValidListByLeader(leaderId,activityId);
		
		Activity other1 = new Activity();
		Activity other2 = new Activity();
		
		if (others.size() >= 2)
		{
			other1 = others.get(0);
			other2 = others.get(1);
		}
		else if (others.size() == 1)
		{
			other1 = others.get(0);
		}
		User leader = User.dao.queryValidLeader(leaderId);
		setAttr("other1", other1);
		setAttr("other2", other2);
		setAttr("leader", leader);
		render("activityDisplay.html");
	}
	/**
	 * 更多优惠<br/>
	 * 默认显示前4项活动
	 * 访问路径：/activity/moreDiscount
	 */
	public void moreDiscount()
	{
		int MAX_NUM = 4;
		List<Activity> activities = new ArrayList<Activity>();
		activities = Activity.dao.queryTopDisount(MAX_NUM);
		render("");
	}
	
	/**
	 * <p>
	 * 热门推荐<br/>
	 * 默认显示前10项活动,按发布时间的倒序
	 * 访问路径：/activity/moreDiscount </p>
	 */
	public void recommend()
	{
		int RECOMMEND_NUM = 10;
		List<Activity> activities = new ArrayList<Activity>();
		activities = Activity.dao.queryRecommendAct(RECOMMEND_NUM);
		render("");
	}
	
	/**
	 * 热门活动
	 * /activity/hotAct
	 */
	public void hotAct()
	{
		int HOT_NUM = 6;
		List<Activity> activities = new ArrayList<Activity>();
		activities = Activity.dao.queryHot(HOT_NUM);
		setAttr("activities", activities);
	}
	
	/**
	 * 活动搜索页的活动列表
	 * 访问路径：/activity/searchList
	 */
	public void searchList()
	{
		int SEARCH_NUM = 6;
		List<Activity> activities = new ArrayList<Activity>();
		activities = Activity.dao.querySearchList(SEARCH_NUM);
		//默认5条
		int HOT_NUM = 5;
		List<Activity> hotActivities = new ArrayList<Activity>();
		hotActivities = Activity.dao.queryHotline(HOT_NUM);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hotActivities", hotActivities);
		map.put("activities", activities);
		setAttrs(map);
	}
	
	/**
	 * 根据类型搜索活动列表
	 * 访问路径：/activity/actListByType
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
			int displayNum = 0;
			List<Activity> activities = new ArrayList<Activity>();
			activities = Activity.dao.queryByType2(1,displayNum);
			setAttr("activities", activities);
			render("activity/actlist_weekend.html");
		}else if (type.equals("hot")){
			int displayNum = 0;
			List<Activity> activities = new ArrayList<Activity>();
			activities = Activity.dao.queryByType2(2,displayNum);
			setAttr("activities", activities);
			render("activity/actlist_hot.html");
		}else if (type.equals("graduate")){
			int displayNum = 0;
			List<Activity> activities = new ArrayList<Activity>();
			activities = Activity.dao.queryByType2(3,displayNum);
			setAttr("activities", activities);
			render("activity/actlist_graduate.html");
		}else if (type.equals("expand")){
			int displayNum = 0;
			List<Activity> activities = new ArrayList<Activity>();
			activities = Activity.dao.queryByType2(4,displayNum);
			setAttr("activities", activities);
			render("actlist_expand.html");
		}else if (type.equals("enshi")){
			int displayNum = 0;
			List<Activity> activities = new ArrayList<Activity>();
			activities = Activity.dao.queryByType2(5,displayNum);
			setAttr("activities", activities);
			render("activity/actlist_west.html");
		}else if (type.equals("suggest")){
			int displayNum = 0;
			List<Activity> activities = new ArrayList<Activity>();
			activities = Activity.dao.queryByType2(4,displayNum);
			setAttr("activities", activities);
			render("activity/actlist_suggest.html");
		}
	}
	
	/**
	 * 跳转报名页面
	 * 访问路径：/activity/signupPath
	 */
	public void signupPath()
	{
		User sessionUser = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (sessionUser == null)
		{
			renderHtml("<script  type='text/javascript'>alert('您还没有登录，请登录后报名！');window.location='/loginPath';</script>");
		}
		else
		{
			int activityId = getParaToInt("activityId",0);
			Activity activity = Activity.dao.queryById(activityId);
			setAttr("act", activity);
			render("sign_up.html");
		}
	}
	
	/**
	 * 报名，一次报名对应一条报名记录，存在着并发的问题
	 * 访问路径：/activity/signup
	 */
	public void signup()
	{
		int actId = getParaToInt("actId",0);
		int userId = getParaToInt("userId",0);
		int adultNum = getParaToInt("adultNum",0);
		int childNum = getParaToInt("childNum", 0);
		String lmName = getPara("lmName","");
		String lmNumber = getPara("lmNumber", "");
		String lmEmail = getPara("lmEmail","");
		String lmQq = getPara("lmEmail","");
		
		Signup signup = new Signup();
		signup.setActId(actId);
		signup.setUserId(userId);
		signup.setAdultNum(adultNum);
		signup.setChildNum(childNum);
		signup.setLmName(lmName);
		signup.setLmNumber(lmNumber);
		signup.setLmEmail(lmEmail);
		signup.setLmQq(lmQq);
		
		signup.save();
	}
	
	/**
	 * 跳转报名须知页面
	 * 访问路径：/activity/signupAgreePath
	 */
	public void signupAgreePath()
	{
		render("signupAgreement.html");
	}
	
	/**
	 * 下架活动（到了报名截止日期）
	 * 访问路径：/activity/portDisable
	 */
	public void portDisable()
	{
		int activityId = getParaToInt("activityId",0);
		Activity activity = Activity.dao.findById(activityId);
		if (activity == null) {
			renderText("0");
			return;
		}
		activity.setIsValid(Constant.INVALID);
		if (activity.update()) {
			renderText("1");
		}else{
			renderText("0");
		}
	}
	
	/**
	 * 删除活动
	 * 访问路径：/activity/portDisable
	 */
	public void portDelete()
	{
		int activityId = getParaToInt("activityId",0);
		Activity activity = Activity.dao.findById(activityId);
		if (activity == null) {
			renderText("0");
			return;
		}
		
		if (activity.delete()) {
			renderText("1");
		}else{
			renderText("0");
		}
	}
	
	/**
	 * 活动搜索页的热门线路
	 * 访问路径：/activity/hotline
	 */
	public void portSearchByCondition()
	{
		String types = getPara("hotTypeList").trim();
		String contents = getPara("contentTypeList").trim();
		String times = getPara("timeType").trim();
		List<Activity> activities = new ArrayList<Activity>();
		activities = Activity.dao.queryByConditionEx(types,contents,times);
		
		
		if (activities != null && activities.size() != 0)
		{
			Activity.removeDuplicate(activities);
		}
		renderJson(activities);
	}
	/**
	 * 根据活动类型切换活动列表
	 * 访问路径：/activity/portSearchByType
	 */
	public void portSearchByType()
	{
		//默认获取类型为1的活动列表
		int typeId = getParaToInt("typeId", 1);
		int displayNum = 6;
		List<Activity> activities = Activity.dao.queryByType(typeId, displayNum);
		renderJson(activities);
	}
	
	/**
	 * 领队其他活动
	 * 访问路径：/activity/portOtherActByLeader
	 */
	public void portOtherActByLeader()
	{
		
	}
	
	/**
	 * 热门线路
	 * 访问路径：/activity/portHotLines
	 */
	public void portHotLines()
	{
		List<Activity> hotlines = Activity.dao.queryHotline(5);
		renderJson(hotlines);
	}
	
	/**
	 * 所有活动（主要用户管理员管理）
	 * 访问路径：/activity/portAllAct
	 */
	public void portAllAct()
	{
		List<Activity> activites = Activity.dao.queryAll();
		renderJson(activites);
	}
	
	/**
	 * 人气推荐中的活动
	 * 访问路径：/activity/portActs
	 */
	public void portActs() {
		int DEFAULT_NUM = 6;
		int num = getParaToInt("num", 0);
		if (num == 0)
		{
			num = DEFAULT_NUM;
		}
		renderJson(Activity.dao.queryHot(num));
	}
	
	/**
	 * 获取所有活动类型
	 * 访问路径：/activity/portActTypes
	 */
	public void portActTypes() {
		renderJson(Activitytype.dao.find("select * from activitytype where isValid = 1"));
	}
	
}
