package com.ymhw.website.controler.manage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import com.ymhw.website.model.Car;
import com.ymhw.website.model.Carfactory;
import com.ymhw.website.model.Equip;
import com.ymhw.website.model.Info;
import com.ymhw.website.model.Strategy;
import com.ymhw.website.model.User;
import com.ymhw.website.utils.Constant;

public class AdminController extends Controller{
	
	private static final Logger logger = Logger.getLogger(AdminController.class);
	
	/**
	 * 路径：/admin
	 */
	public void index() {
		logger.info("index......");
		render("login.html");
	}
	
	/**
	 * 路径：/main
	 */
	public void main() {
		render("index.html");
	}
	
	/**
	 * 用户列表
	 * 路径：/admin/userList
	 */
	public void userList() {
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 8);
		int roleId = getParaToInt("roleId", 0);
		String keyword = getPara("keyword", "");
		Page<User> page = User.dao.paginateByCondition(pageNumber, pageSize, roleId, keyword);
		setAttr("page", page);
		setAttr("role", roleId);
		setAttr("keyword", keyword);
		render("user_list.html");
	}
	
	
	
	/**
	 * 用户数据修改
	 * 路径：/admin/userEdit
	 */
	public void userEdit() {
		int id = getParaToInt("id", 0);
		setAttr("user", User.dao.getUserById(id));
		render("user_edit.html");
	}
	
	/**
	 * 删除用户
	 * 路径：/admin/portUserDelete
	 * note: true-删除成功
	 */
	public void portUserDelete() {
		int id = getParaToInt("id", 0);
		renderJson(User.dao.deleteById(id));
	}
	
	
	/**
	 * 攻略列表
	 * 路径：/admin/strategyList
	 */
	public void strategyList() {
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 10);
		//-1:全部
		int type = getParaToInt("type", -1);
		String keyword = getPara("keyword", "");
		Map<String, Object> param = new HashMap<>();
		param.put("type", type);
		param.put("keyword", keyword);
		Page<Strategy> page = Strategy.dao.paginateByCondition(pageNumber, pageSize, param);
		setAttr("page", page);
		setAttr("type", type);
		setAttr("keyword", keyword);
		render("strategy_list.html");
	}
	
	/**
	 * 删除攻略
	 * 路径：/admin/portStrategyDelete
	 * note: true-删除成功
	 */
	public void portStrategyDelete() {
		int id = getParaToInt("id", 0);
		renderJson(Strategy.dao.deleteById(id));
	}
	
	/**
	 * 攻略上传页面
	 * 路径：/admin/strategyAddPath
	 */
	public void strategyAddPath() {
		render("strategy_add.html");
	}
	
	/**
	 * 攻略编辑页面
	 * 路径：/admin/strategyEditPath
	 */
	public void strategyEditPath() {
		int id = getParaToInt("id", 0);
		setAttr("strategy", Strategy.dao.getStrategyById(id));
		render("strategy_edit.html");
	}
	
	
	
	/**
	 * 装备上传
	 * 路径：/admin/equipAdd
	 */
	public void equipAdd() {
		Subject curUser = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (curUser.hasRole("admin"))
		{
			UploadFile equipFile = getFile("equipUpload",Constant.EQUIP_PATH);
			String name = getPara("name").trim();
			String depositStr = getPara("deposit").trim();
			String rent = getPara("rent").trim();
			
			Equip equip = new Equip();
			equip.setName(name);
			if (!StrKit.isBlank(depositStr))
			{
				equip.setDeposit(Float.parseFloat(depositStr));
			}
			if (!StrKit.isBlank(rent))
			{
				equip.setRent(Float.parseFloat(rent));
			}
			if (equipFile != null)
			{
				equip.setEquipPic(equipFile.getUploadPath() + java.io.File.separator + equipFile.getOriginalFileName());
			}
			equip.setUploadTime(new Date());
			equip.setIsValid(Constant.VALID);
			equip.setCheckStatus(Constant.STATUS_SUMBIT_NOT_CHECK);
			//TODO:先将上传人都设置为超级管理员
			equip.setUploader(3);
			if (equip.save())
			{
				renderHtml("<script  type='text/javascript'>alert('装备信息已提交，请耐心等待管理员审核！');window.location='/manage_equip/uploadEquipPath';</script>");
			}
			else
			{
				renderHtml("<script  type='text/javascript'>alert('装备信息提交失败，请重新填写相关信息！');window.location='/manage_equip/uploadEquipPath';</script>");
			}
		}
		else
		{
			String errorText = "<script  type='text/javascript'>alert('您还未登陆或者您的登录已失效，只有登陆成功并申请成为领队才能上传装备！');window.location='/manage/login';</script>";
			renderHtml(errorText);
		}
	}
	
	public void carFactoryList() {
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 8);
		String keyword = getPara("keyword", "");
		Map<String, Object> param = new HashMap<>();
		param.put("keyword", keyword);
		Page<Carfactory> page = Carfactory.dao.paginateByCondition(pageNumber, pageSize, param);
		setAttr("page", page);
		setAttr("keyword", keyword);
		render("carFactory_list.html");
	}
	
	/**
	 * 添加租车商户页面
	 * 路径：/admin/carFactoryAddPath
	 */
	public void carFactoryAddPath() {
		render("carFactory_add.html");
	}
	
	/**
	 * 租车商户编辑页面
	 * 路径：/admin/carFactoryEditPath
	 */
	public void carFactoryEditPath() {
		int id = getParaToInt("id", 0);
		setAttr("factory", Carfactory.dao.findById(id));
		setAttr("sellers", User.dao.queryAllSeller());
		render("carFactory_edit.html");
	}
	
	/**
	 * 车辆列表
	 * 路径：/admin/carList
	 */
	public void carList() {
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 8);
		int type = getParaToInt("type", 0);
		int factoryId = getParaToInt("factoryId", 0);
		String keyword = getPara("keyword", "");
		Map<String, Object> param = new HashMap<>();
		param.put("keyword", keyword);
		param.put("type", type);
		param.put("factoryId", factoryId);
		Page<Car> page = Car.dao.paginateByCondition(pageNumber, pageSize, param);
		setAttr("page", page);
		setAttr("type", type);
		setAttr("factoryId", factoryId);
		setAttr("keyword", keyword);
		setAttr("factories", Carfactory.dao.getAllFactories());
		render("car_list.html");
	}
	
	/**
	 * 车辆上传页面
	 * 路径：/admin/carAddPath
	 */
	public void carAddPath() {
		render("car_add.html");
	}
	
	/**
	 * 车辆编辑页面
	 * 路径：/admin/carEditPath
	 */
	public void carEditPath() {
		int id = getParaToInt("id", 0);
		setAttr("car", Car.dao.getCarById(id));
		setAttr("factories", Carfactory.dao.getAllFactories());
		render("car_edit.html");
	}
	
	/**
	 * 装备列表
	 * 路径：/admin/equipList
	 */
	public void equipList() {
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 8);
		String keyword = getPara("keyword", "");
		Map<String, Object> param = new HashMap<>();
		param.put("keyword", keyword);
		Page<Equip> page = Equip.dao.paginateByCondition(pageNumber, pageSize, param);
		setAttr("page", page);
		setAttr("keyword", keyword);
		render("equip_list.html");
	}
	
	/**
	 * 装备上传页面
	 * 路径：/admin/equipAddPath
	 */
	public void equipAddPath() {
		render("equip_add.html");
	}
	
	/**
	 * 装备编辑页面
	 * 路径：/admin/equipEditPath
	 */
	public void equipEditPath() {
		int id = getParaToInt("id", 0);
		setAttr("equip", Equip.dao.queryInvalidEquip(id));
		render("equip_edit.html");
	}
	
	/**
	 * 周边目的地
	 * 路径：/admin/infoList
	 */
	public void infoList() {
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 8);
		int type = getParaToInt("type", 0);
		String keyword = getPara("keyword", "");
		Map<String, Object> param = new HashMap<>();
		param.put("keyword", keyword);
		param.put("type", type);
		Page<Info> page = Info.dao.paginateByCondition(pageNumber, pageSize, param);
		setAttr("page", page);
		setAttr("keyword", keyword);
		setAttr("type", type);
		render("info_list.html");
	}
	
	/**
	 * 周边目的地添加页面
	 * 路径：/admin/infoAddPath
	 */
	public void infoAddPath() {
		render("info_add.html");
	}
	
	/**
	 * 周边目的地编辑页面
	 * 路径：/admin/infoEditPath
	 */
	public void infoEditPath() {
		int id = getParaToInt("id", 0);
		setAttr("record", Info.dao.getInfoAllById(id));
		render("info_edit.html");
	}
	
	/**
	 * 接口:所有租车公司数据
	 * 路径：/admin/portCarFactories
	 */
	public void portCarFactories() {
		renderJson(Carfactory.dao.getAllFactories());
	}
	
	/**
	 * 接口:所有租车公司数据
	 * 路径：/admin/portSellers
	 */
	public void portSellers() {
		renderJson(User.dao.queryAllSeller());
	}
	
	
}
