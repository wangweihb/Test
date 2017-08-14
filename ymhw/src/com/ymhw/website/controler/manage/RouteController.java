package com.ymhw.website.controler.manage;


import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import com.ymhw.website.model.Car;
import com.ymhw.website.model.Farmstay;
import com.ymhw.website.model.Hotel;
import com.ymhw.website.model.Info;
import com.ymhw.website.utils.Constant;
import com.ymhw.website.utils.FileUtil;
import com.ymhw.website.utils.MD5Util;
import com.ymhw.website.utils.ShiroSecurityUtil;

/**
 * 后台基本路径控制
 * @author oswin
 */

public class RouteController extends Controller
{
	private static final Logger logger = Logger.getLogger(RouteController.class);
	
	/**
	 * 后台登录入口
	 * 访问路径：/manage
	 */
	public void index() 
	{
		logger.info("-------------后台管理界面---------------");
		render("login.html");
	}
	
	/**
	 * 后台主页入口（菜单区域）
	 * 访问路径：/manage/login
	 */
	public void login()
	{
		String username = getPara("username", "").trim();
		String password = getPara("password", "").trim();
		
//		User user = User.dao.queryLoginUser(username, password);
		Subject currentUser = ShiroSecurityUtil.login("classpath:shiro.ini", username, MD5Util.shiroMD5(password));
		if (currentUser != null)
		{
			setSessionAttr(Constant.YMHW_SESSIONUSER, currentUser);
			String uname = (String) currentUser.getPrincipal();
			logger.info(uname + " 登陆成功！");
			render("index.html");
		}
		else
		{
			renderHtml("<script  type='text/javascript'>javascript:alert('用户名或密码错误！');window.location='/manage/index';</script>");
		}
	}
	
	/**
	 * 后台主页入口(内容区域)
	 * 访问路径：/manage/main
	 */
	public void main()
	{
		render("index.html");
	}
	
	/**
	 * 后台主页入口(内容区域)
	 * 访问路径：/manage/index_main
	 */
	public void index_main()
	{
		render("index_main.html");
	}
	
	/**
	 * 后台权限提示页面
	 * 访问路径：/manage/noauth
	 */
	public void noauth()
	{
		render("noauth.html");
	}
	
	/**
	 * 资讯上传（周边目的地）界面
	 * 访问路径：/manage/infoUploadPath
	 */
	public void infoUploadPath()
	{
		render("info/info_manage.html");
	}
	
	/**
	 * 资讯下的农家乐信息上传（周边目的地）界面
	 * 访问路径：/manage/infoFarmUploadPath
	 */
	public void infoFarmUploadPath()
	{
		render("info/uploadFarmStay.html");
	}
	
	/**
	 * 资讯下的民宿信息上传（周边目的地）界面
	 * 访问路径：/manage/infoHotelUploadPath
	 */
	public void infoHotelUploadPath()
	{
		render("info/uploadHotel.html");
	}
	
	/**
	 * 资讯上传
	 * 访问路径：/manage/infoUpload
	 */
	public void infoUpload(){
		long rand = System.currentTimeMillis();
		String thefilepath = Constant.UPLOAD_INFORMATION_ROOT +File.separator+ Constant.UPLOAD_INFORMATION_SUBJECT + File.separator + rand;
		String theReqServer = Constant.URL_SEP + PropKit.get("uploadPath") + Constant.URL_SEP + Constant.UPLOAD_INFORMATION_ROOT +Constant.URL_SEP + 
				Constant.UPLOAD_INFORMATION_SUBJECT + Constant.URL_SEP + rand + Constant.URL_SEP;
		
		//jfinal 中对单个input 多个文件 getFiles总是只返回一个对象，但是文件已经上传到对应的路径
		getFiles(thefilepath);
		String dir = PathKit.getWebRootPath() + File.separator + PropKit.get("uploadPath") + File.separator + thefilepath;
		List<String> fileNames = FileUtil.getFileNames(dir);
		
		String infoTitle = getPara("infoTitle", "");
		String contDesc = getPara("contDesc", "");
		String provice = getPara("provice", "");
		String city = getPara("city", "");
		String region = getPara("region", "");
		String streetAddress = getPara("streetAddress", "");
		int type = getParaToInt("type", 1);
		String openStartTime = getPara("openStartTime", "");
		String openEndTime = getPara("openEndTime", "");
		String travelSeason = getPara("travelSeason", "");
		String duration = getPara("duration", "");
		String contactNumber = getPara("contactNumber", "");
		String totalPrice = getPara("totalPrice", "");
		int grade = getParaToInt("grade", 1);
		
		String transportInfo = getPara("transportInfo", "");
		
		String[] infoTags = getParaValues("infoTag");
		String[] childInfoTitles = getParaValues("childInfoTitle");
		String[] childInfoDescs = getParaValues("childInfoDesc");
		
		Info info = new Info();
		info.setSubject(infoTitle);
		info.setDesc(contDesc);
		info.setType(type);
		info.setProvice(provice);
		info.setCity(city);
		info.setRegion(region);
		info.setStreetAddress(streetAddress);
		info.setOpenStartTime(openStartTime);
		info.setOpenEndTime(openEndTime);
		info.setTravelSeason(travelSeason);
		info.setDuration(duration);
		info.setContactNumber(contactNumber);
		info.setGrade(grade);
		info.setTotalPrice(StrKit.isBlank(totalPrice) ? 0.00f : Float.parseFloat(totalPrice));
		info.setTransportInfo(transportInfo);
		info.setCreateTime(new Date());
		
		String paths = "";
		for (String filename : fileNames) {
			String imgPath =  theReqServer + filename;
			paths = paths + imgPath + ",";
		}
		if (!StrKit.isBlank(paths)) {
			paths = paths.substring(0, paths.length()-1);
		}
		info.setSubjectImg(paths);
		
		if (info.saveOne(info, infoTags, childInfoTitles, childInfoDescs)) {
			renderHtml("<script  type='text/javascript'>alert('上传成功！');window.location='/manage/infoUploadPath';</script>");
		} else {
			logger.info("资讯信息的存库失败....");
			renderHtml("<script  type='text/javascript'>alert('上传失败！');window.location='/manage/infoUploadPath';</script>");
		}
	}
	
	/**
	 * 资讯下的农家乐信息上传
	 * 访问路径：/manage/infoFarmUpload
	 */
	public void infoFarmUpload(){
		UploadFile uploadFile = getFile("pic", Constant.UPLOAD_FARMSTAY);
		int destinationId = getParaToInt("dest", 0);
		if (destinationId == 0)
		{
			renderHtml("<script  type='text/javascript'>alert('上传失败，所属周边目的地信息必须选择！');window.location='/manage/infoFarmUploadPath';</script>");
		}
		String serviceStartTime = getPara("serviceStartTime", "");
		String serviceEndTime = getPara("serviceEndTime", "");
		String parkInfo = getPara("parkInfo", "");
		String specialAct = getPara("specialAct 	", "");
		String otherEquip = getPara("otherEquip", "");
		String addedService = getPara("addedService", "");
		String attention = getPara("attention", "");
		String costStr = getPara("cost", "");
		String feePart = getPara("feePart", "");
//		String productDesc = getPara("productDesc", "");
		String shopName = getPara("shopName", "");
		String address = getPara("address", "");
		
		//相关菜品(代表图片时以base64的形式)
//		String[] proTitles = getParaValues("proTitle"); 
//		String[] proPrices = getParaValues("proPrice"); 
//		String[] proPics = getParaValues("proPic"); 
		
		Farmstay farmstay = new Farmstay();
		farmstay.setDestinationId(destinationId);
		farmstay.setServiceStartTime(serviceStartTime);
		farmstay.setServiceEndTime(serviceEndTime);
		farmstay.setParkInfo(parkInfo);
		farmstay.setSpecialAct(specialAct);
		farmstay.setOtherEquip(otherEquip);
		farmstay.setAddedService(addedService);
		farmstay.setFeePart(feePart);
		farmstay.setAttention(attention);
//		farmstay.setProductDesc(productDesc);
		farmstay.setCost(Float.parseFloat(costStr));
		farmstay.setShopName(shopName);
		farmstay.setAddress(address);
		farmstay.setUpdateTime(new Date());
		String imgPath = Constant.URL_SEP + PropKit.get("uploadPath") 
			+ Constant.URL_SEP + Constant.UPLOAD_FARMSTAY + Constant.URL_SEP + uploadFile.getFileName();
		farmstay.setPic(imgPath);
		if (farmstay.save())
		{
			renderHtml("<script  type='text/javascript'>alert('上传成功！');window.location='/manage/infoFarmUploadPath';</script>");
		} 
		else 
		{
			renderHtml("<script  type='text/javascript'>alert('上传失败！');window.location='/manage/infoFarmUploadPath';</script>");
		}
	}
	
	/**
	 * 资讯下的民宿信息上传
	 * 访问路径：/manage/infoHotelUpload
	 */
	public void infoHotelUpload(){
		UploadFile uploadFile = getFile("pic", Constant.UPLOAD_HOTEL);
		int destinationId = getParaToInt("dest", 0);
		if (destinationId == 0)
		{
			renderHtml("<script  type='text/javascript'>alert('上传失败，所属周边目的地信息必须选择！');window.location='/manage/infoFarmUploadPath';</script>");
		}
		String houseType = getPara("houseType", "");
		String bedType = getPara("bedType", "");
		String aboutEquip = getPara("aboutEquip", "");
		String provice = getPara("provice", "");
		String city = getPara("city", "");
		String region = getPara("region", "");
		String streetAddress = getPara("streetAddress", "");
		String checkinType = getPara("checkinType", "");
		String transportTip = getPara("transportTip", "");
		int isCheckout = getParaToInt("isCheckout", 0);
		String aboutService = getPara("aboutService", "");
		String feePart = getPara("feePart", "");
		String parkInfo = getPara("parkInfo", "");
		
		Hotel hotel = new Hotel();
		String imgPath = Constant.URL_SEP + PropKit.get("uploadPath") 
			+ Constant.URL_SEP + Constant.UPLOAD_HOTEL + Constant.URL_SEP + uploadFile.getFileName();
		hotel.setDestinationId(destinationId);
		hotel.setHouseType(houseType);
		hotel.setBedType(bedType);
		hotel.setAboutEquip(aboutEquip);
		hotel.setProvice(provice);
		hotel.setCity(city);
		hotel.setRegion(region);
		hotel.setStreetAddress(streetAddress);
		hotel.setParkInfo(parkInfo);
		hotel.setCheckinType(checkinType);
		hotel.setTransportTip(transportTip);
		hotel.setIsCheckout(isCheckout);
		hotel.setAboutService(aboutService);
		hotel.setFeePart(feePart);
		hotel.setUpdateTime(new Date());
		hotel.setPic(imgPath);
		
		if (hotel.save())
		{
			renderHtml("<script  type='text/javascript'>alert('上传成功！');window.location='/manage/infoHotelUploadPath';</script>");
		} 
		else 
		{
			renderHtml("<script  type='text/javascript'>alert('上传失败！');window.location='/manage/infoHotelUploadPath';</script>");
		}
	}
	
	/**
	 * 租车信息的上传界面
	 * 访问路径：/manage/carRent
	 */
	public void carRent()
	{
		render("carRent/cr_upload.html");
	}
	
	/**
	 * 租车信息的上传操作
	 * 访问路径：/manage/carUpload
	 */
	public void carUpload() {
		System.err.println("carUpload....");
		UploadFile uploadFile = getFile("showpic", Constant.UPLOAD_CAR);
		String carName = getPara("carName", "");
		int carType = getParaToInt("carType", 0);
		int szpType = getParaToInt("szpType", 0);
		int cntLimit = getParaToInt("cntLimit", 1);
		String provice = getPara("provice", "");
		String city = getPara("city", "");
		String region = getPara("region", "");
		String streetAddress = getPara("streetAddress", "");
		Date rentStartTime = getParaToDate("rentStartTime", new Date());
		Date rentEndTime = getParaToDate("rentEndTime");
		String priceStr = getPara("price", "0.0");
		String sweptVolume = getPara("sweptVolume", "");
		
		Car car = new Car();
		String imgPath = Constant.URL_SEP + PropKit.get("uploadPath") 
						+ Constant.URL_SEP + Constant.UPLOAD_CAR + Constant.URL_SEP + uploadFile.getFileName();
		car.setShowpic(imgPath);
//		car.setProvice(provice);
//		car.setCity(city);
//		car.setRegion(region);
//		car.setStreetAddress(streetAddress);
		car.setCntLimit(cntLimit);
		car.setGearType(szpType);
		car.setName(carName);
		car.setPrice(Float.parseFloat(priceStr));
//		car.setRentStartTime(rentStartTime);
//		car.setRentEndTime(rentEndTime);
		car.setType(carType);
		car.setSweptVolume(sweptVolume);
		car.setLastUpdateTime(new Date());
		
		if(car.save()) {
			renderHtml("<script  type='text/javascript'>alert('上传成功！');window.location='/manage/carRent';</script>");
		} else {
			renderHtml("<script  type='text/javascript'>alert('上传失败，请重新上传！');window.location='/manage/carRent';</script>");
		}
	}
	
	/**
	 * wangEditor富文本编辑框中图片的上传
	 * 访问路径：/manage/wangUpload
	 */
	public void wangUpload() {
		UploadFile uploadFile = getFile("ymFileName", Constant.UPLOAD_WANGEDITOR);
		logger.info("wangEditor富文本编辑框 中开始上传图片了....");
		renderText(Constant.URL_SEP + PropKit.get("uploadPath") +Constant.URL_SEP + Constant.UPLOAD_WANGEDITOR + Constant.URL_SEP + uploadFile.getFileName());
	}
	
	/**
	 * wangEditor富文本编辑框中图片的上传
	 * 访问路径：/manage/wangUpload_strategy
	 */
	public void wangUpload_strategy() {
		String dir = "wangUpload_strategy";
		String fgFileName = "fileName_strategy";
		UploadFile uploadFile = getFile(fgFileName, dir);
		logger.info("wangEditor富文本编辑框 中开始上传图片了....");
		renderText(Constant.URL_SEP + PropKit.get("uploadPath") +Constant.URL_SEP + dir + Constant.URL_SEP + uploadFile.getFileName());
	}
	
	/**
	 * wangEditor富文本编辑框中图片的上传
	 * 访问路径：/manage/wangUpload_bookInfo
	 */
	public void wangUpload_bookInfo() {
		String dir = "wangUpload_bookInfo";
		String fgFileName = "fileName_bookInfo";
		UploadFile uploadFile = getFile(fgFileName, dir);
		logger.info("wangEditor富文本编辑框 中开始上传图片了....");
		renderText(Constant.URL_SEP + PropKit.get("uploadPath") +Constant.URL_SEP + dir + Constant.URL_SEP + uploadFile.getFileName());
	}
	
	/**
	 * wangEditor富文本编辑框中图片的上传(农家乐中费用包含框中图片上传)
	 * 访问路径：/manage/wangUpload_farm_feeInfo
	 */
	public void wangUpload_farm_feeInfo() {
		String dir = "wangUpload_farm_feeInfo";
		String fgFileName = "fileName_farm_feeInfo";
		UploadFile uploadFile = getFile(fgFileName, dir);
		logger.info("wangEditor富文本编辑框 中开始上传图片了....");
		renderText(Constant.URL_SEP + PropKit.get("uploadPath") +Constant.URL_SEP + dir + Constant.URL_SEP + uploadFile.getFileName());
	}
	
	/**
	 * wangEditor富文本编辑框中图片的上传(农家乐中产品信息框中图片上传)
	 * 访问路径：/manage/wangUpload_farm_productInfo
	 */
	public void wangUpload_farm_productInfo() {
		String dir = "wangUpload_farm_productInfo";
		String fgFileName = "fileName_farm_productInfo";
		UploadFile uploadFile = getFile(fgFileName, dir);
		logger.info("wangEditor富文本编辑框 中开始上传图片了....");
		renderText(Constant.URL_SEP + PropKit.get("uploadPath") +Constant.URL_SEP + dir + Constant.URL_SEP + uploadFile.getFileName());
	}
	
	/**
	 * wangEditor富文本编辑框中图片的上传(名宿中交通指南框中图片上传)
	 * 访问路径：/manage/wangUpload_hotel_transportInfo
	 */
	public void wangUpload_hotel_transportInfo() {
		String dir = "wangUpload_hotel_transportInfo";
		String fgFileName = "fileName_hotel_transportInfo";
		UploadFile uploadFile = getFile(fgFileName, dir);
		logger.info("wangEditor富文本编辑框 中开始上传图片了....");
		renderText(Constant.URL_SEP + PropKit.get("uploadPath") +Constant.URL_SEP + dir + Constant.URL_SEP + uploadFile.getFileName());
	}
	
	/**
	 * wangEditor富文本编辑框中图片的上传(民宿中费用包含框中图片上传)
	 * 访问路径：/manage/wangUpload_hotel_feeInfo
	 */
	public void wangUpload_hotel_feeInfo() {
		String dir = "wangUpload_hotel_feeInfo";
		String fgFileName = "fileName_hotel_feeInfo";
		UploadFile uploadFile = getFile(fgFileName, dir);
		logger.info("wangEditor富文本编辑框 中开始上传图片了....");
		renderText(Constant.URL_SEP + PropKit.get("uploadPath") +Constant.URL_SEP + dir + Constant.URL_SEP + uploadFile.getFileName());
	}
	
	/**
	 * wangEditor富文本编辑框中图片的上传(民宿中费用包含框中图片上传)
	 * 访问路径：/manage/wangUpload_hotel_aboutService
	 */
	public void wangUpload_hotel_aboutService() {
		String dir = "wangUpload_hotel_aboutService";
		String fgFileName = "fileName_hotel_aboutService";
		UploadFile uploadFile = getFile(fgFileName, dir);
		logger.info("wangEditor富文本编辑框 中开始上传图片了....");
		renderText(Constant.URL_SEP + PropKit.get("uploadPath") +Constant.URL_SEP + dir + Constant.URL_SEP + uploadFile.getFileName());
	}
	
	/**
	 * wangEditor富文本编辑框中图片的上传
	 * 访问路径：/manage/wangUpload_transportInfo
	 */
	public void wangUpload_transportInfo() {
		String dir = "wangUpload_transportInfo";
		String fgFileName = "fileName_transportInfo";
		UploadFile uploadFile = getFile(fgFileName, dir);
		logger.info("wangEditor富文本编辑框 中开始上传图片了....");
		renderText(Constant.URL_SEP + PropKit.get("uploadPath") +Constant.URL_SEP + dir + Constant.URL_SEP + uploadFile.getFileName());
	}
	
	/**
	 * 访问路径：/manage/wangUpload_product_prductbuy
	 */
	public void wangUpload_product_prductbuy() {
		String dir = "wangUpload_product_prductbuy";
		String fgFileName = "fileName_product_prductbuy";
		UploadFile uploadFile = getFile(fgFileName, dir);
		logger.info("wangEditor富文本编辑框 中开始上传图片了....");
		renderText(Constant.URL_SEP + PropKit.get("uploadPath") +Constant.URL_SEP + dir + Constant.URL_SEP + uploadFile.getFileName());
	}
	
	/**
	 * 访问路径：/manage/wangUpload_product_productdetail
	 */
	public void wangUpload_product_productdetail() {
		String dir = "wangUpload_product_productdetail";
		String fgFileName = "fileName_product_productdetail";
		UploadFile uploadFile = getFile(fgFileName, dir);
		logger.info("wangEditor富文本编辑框 中开始上传图片了....");
		renderText(Constant.URL_SEP + PropKit.get("uploadPath") +Constant.URL_SEP + dir + Constant.URL_SEP + uploadFile.getFileName());
	}
	/**
	 * 接口获取所有周边目的地的标题、ID
	 * 请求地址：/manage/portInfoTiles
	 */
	public void portInfoTiles() {
		renderJson(Info.dao.getTitles());
	}
	
	/**
	 * 接口 获取周边目的地下面的所有农家乐信息
	 * 访问路径：/manage/portFarmsByInfoId
	 */
	public void portFarmsByInfoId() {
		renderJson(Farmstay.dao.getFarmsByInfoId(getParaToInt("infoId", 0)));
	}
	

}
