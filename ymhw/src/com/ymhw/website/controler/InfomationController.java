package com.ymhw.website.controler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.ymhw.website.atom.CarOrderAddAtom;
import com.ymhw.website.atom.FarmProductOrderAddAtom;
import com.ymhw.website.model.Carrentstatus;
import com.ymhw.website.model.Farmstay;
import com.ymhw.website.model.Info;
import com.ymhw.website.model.Order;
import com.ymhw.website.model.Product;
import com.ymhw.website.model.User;
import com.ymhw.website.utils.Constant;
import com.ymhw.website.utils.DateUtil;
import com.ymhw.website.utils.OrderGenerator;

public class InfomationController extends Controller
{
	/**
	 * 资讯首页：
	 * 访问路径：/infoPath
	 */
	public void index() {
		//页面默认显示6个
		int DEFAULT_NUM = 6;
		//1-休闲旅游 2-激情户外  3-家庭活动 4-民宿营地
		List<Info> info1s = Info.dao.getInfosByType(1, DEFAULT_NUM);
		List<Info> info2s = Info.dao.getInfosByType(2, DEFAULT_NUM);
		List<Info> info3s = Info.dao.getInfosByType(3, DEFAULT_NUM);
		List<Info> info4s = Info.dao.getInfosByType(4, DEFAULT_NUM);
		setAttr("info1s", info1s);
		setAttr("info2s", info2s);
		setAttr("info3s", info3s);
		setAttr("info4s", info4s);
		render("information/index.html");
	}
	
	/**
	 * 资讯详情页面：
	 * 访问路径：/infoPath/detail
	 */
	public void detail() {
		int id = getParaToInt("id", 0);
		setAttr("record", Info.dao.getInfoAllById(id));
		render("information/introduce.html");
	}
	
	/**
	 * 农家乐详情
	 * /infoPath/farmDetail
	 */
	public void farmDetail() {
//		int farmId = getParaToInt("farmId", 0);
//		int proId = getParaToInt("proId", 0);
//		Product product = null;
//		if (proId != 0) {
//			product = Product.dao.getOneById(proId);
//			farmId = product.getFarmId();
//		} else{
//			product = Product.dao.getOneByFarmId(farmId);
//		}
//		setAttr("product", product);
//		setAttr("images", product.getPics().split(","));
//		setAttr("others", Product.dao.getProductsByFarmId(farmId));
//		render("information/farmstayDetail.html");
		
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
			render("information/farmstayDetail.html");
		} else {
			renderHtml("<script  type='text/javascript'>alert('该农家乐、民宿暂时还没有产品!');window.location='/infoPath/detail?id="+farmstay.getDestinationId()+"';</script>");
		}
	}
	
	/**
	 * 产品订购申请
	 * 访问路径：/infoPath/farmProApplyfor
	 */
	public void farmProApplyfor() {
		User sessionUser = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (sessionUser == null)
		{
			renderHtml("<script  type='text/javascript'>alert('您还没有登录，请登录后购买！');window.location='/loginPath';</script>");
		}
		setAttr("product", Product.dao.getOneById(getParaToInt("proId", 0)));
		render("information/product_applyfor.html");
	}
	
	public void list() {
		renderJson();
	}
	
	/**
	 * 预付款(主要完成订单的生成、订单详情记录的生成)：
	 * 访问路径：/infoPath/productRepay
	 */
	public void productRepay() {
		User sessionUser = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (sessionUser == null)
		{
			renderHtml("<script  type='text/javascript'>alert('您还没有登录，请登录后下单购买！');window.location='/infoPath';</script>");
			return;
		}
		//type=4 农家乐产品订单
		int type = 4;
		String billNo = OrderGenerator.getOrderNo(type);
		int userId = sessionUser.getId();
		int productId = getParaToInt("productId", 0);
		int cost = getParaToInt("cost", 0);
		int carNum = getParaToInt("num", 0);
		String lmname = getPara("lmname", "");
		String lmnameTel = getPara("lmnameTel", "");
		
		Map<String,  Object> params = new HashMap<String, Object>();
		params.put("billNo", billNo);
		params.put("type", type);
		params.put("userId", userId);
		params.put("productId", productId);
		params.put("cost", cost);
		params.put("num", carNum);
		params.put("lmname", lmname);
		params.put("lmnameTel", lmnameTel);
		
		FarmProductOrderAddAtom addAtom = new FarmProductOrderAddAtom(params);
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
	 * 获取指定个数的周边目的地信息
	 * 访问路径：/infoPath/portInfos
	 */
	public void portInfos() {
		int DEFAULT_COUNT = 6;
		int num = getParaToInt("num", 0);
		if (num == 0)
		{
			num = DEFAULT_COUNT;
		}
		renderJson(Info.dao.getNInfos(num));
	}
	
	/**
	 * 多条件搜索(查询条件为“全部”已在前端处理)
	 * 访问路径：/infoPath/portInfos
	 */
	public void portInfosByCondition() {
		String infoTypes = getPara("infoTypes", "");
		String infoTags = getPara("infoTags", "");
		String regions = getPara("regions", "");
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("infoTypes", infoTypes);
		condition.put("infoTags", infoTags);
		condition.put("regions", regions);
		renderJson(Info.dao.getInfosByConditon(condition));
	}
	
	/**
	 * 多条件搜索(查询条件为“全部”已在前端处理)
	 * 访问路径：/infoPath/portInfosByCondition2
	 * @throws UnsupportedEncodingException 
	 */
	public void portInfosByCondition2() throws UnsupportedEncodingException {
		String regions = getPara("regions", "");
		regions = URLDecoder.decode(regions, "UTF-8");
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("regions", regions);
		renderJson(Info.dao.getInfosByConditon2(condition));
	}
}
