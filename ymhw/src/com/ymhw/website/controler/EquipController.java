package com.ymhw.website.controler;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import com.ymhw.website.model.Equip;
import com.ymhw.website.model.User;
import com.ymhw.website.utils.Constant;
import com.ymhw.website.utils.StringTool;

/**
 * 
 * @author oswin
 */
public class EquipController extends Controller
{
	/**
	 * 上传一件装备
	 * 访问路径：/equip/add
	 */
	public void add()
	{
		User sessionuser = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (sessionuser != null)
		{
			if (sessionuser.getRoleId() == Constant.ROLEID_LEADER || sessionuser.getRoleId() == Constant.ROLEID_ADMIN)
			{
				UploadFile equipFile = getFile("equipUpload",Constant.EQUIP_PATH);
				String name = getPara("name", "").trim();
				String depositStr = getPara("deposit", "").trim();
				String rent = getPara("rent", "").trim();
				String qq = getPara("qq", "").trim();
				String fixnumber = getPara("fixnumber", "").trim();
				String telephone = getPara("telephone", "").trim();
				String damage = getPara("damage", "").trim();
				String provice = getPara("provice", "").trim();
				String city = getPara("city", "").trim();
				String region = getPara("region", "").trim();
				String street = getPara("street", "").trim();
				int provider = getParaToInt("provider", 0);
				int sendWay = getParaToInt("sendWay", 1);
				int rentWay = getParaToInt("rentWay", 1);
				int total = getParaToInt("total", 0);
				String[] types = getParaValues("types");
				
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
				if (types !=null && types.length >0)
				{
					equip.setTypes(StringTool.listToString(Arrays.asList(types), ','));
				}
				equip.setProvider(provider);
				equip.setQq(qq);
				equip.setFixnumber(fixnumber);
				equip.setTelephone(telephone);
				equip.setDamage(damage);
				equip.setSendWay(sendWay);
				equip.setRentWay(rentWay);
				equip.setTotal(total);
				equip.setProvice(provice);
				equip.setCity(city);
				equip.setRegion(region);
				equip.setStreet(street);
				equip.setUploadTime(new Date());
				equip.setIsValid(Constant.VALID);
				equip.setCheckStatus(Constant.STATUS_SUMBIT_NOT_CHECK);
				equip.setUploader(sessionuser.getId());
				if (equip.save())
				{
					renderHtml("<script  type='text/javascript'>alert('装备信息已提交，请耐心等待管理员审核！');window.location='/uploadEquipPath';</script>");
				}
				else
				{
					renderHtml("<script  type='text/javascript'>alert('装备信息提交失败，请重新填写相关信息！');window.location='/uploadEquipPath';</script>");
				}
			}
			else
			{
				String errorText = "<script  type='text/javascript'>alert('您没有权限上传装备，请联系管理员！');window.location='/';</script>";
				renderHtml(errorText);
			}
		}
		else
		{
			String errorText = "<script  type='text/javascript'>alert('您还未登陆或者您的登录已失效，只有登陆成功并申请成为领队才能上传装备！');window.location='/login';</script>";
			renderHtml(errorText);
		}
	}
	
	/**
	 * 某个领队上传装备的审核结果<br/>
	 * 访问路径：/equip/equipsForUser
	 */
	public void equipsForUser()
	{
		User user = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (user != null && user.getRoleId() == Constant.ROLEID_LEADER)
		{
			setAttr("equips", Equip.dao.getEquipByLeader(user.getId()));
			render("uploadEquipResult.html");
		}
		else
		{
			renderHtml("<script  type='text/javascript'>alert('您的登陆已失效，点击确定返回主页');window.location='/';</script>");
		}
		
	}
	
	/**
	 * 领队编辑某个装备
	 * 访问路径：/equip/editEquip
	 */
	public void editEquip()
	{
		UploadFile equipFile = getFile("equipUpload",Constant.EQUIP_PATH);
		int id = getParaToInt("id", 0);
		User user = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (user != null && user.getRoleId() <= Constant.ROLEID_LEADER && id != 0)
		{
			String name = getPara("name").trim();
			String depositStr = getPara("deposit").trim();
			String rent = getPara("rent").trim();
			
			Equip exsitEquip = Equip.dao.queryInvalidEquip(id);
			if (exsitEquip != null)
			{
				exsitEquip.setName(name);
				if (!StrKit.isBlank(depositStr))
				{
					exsitEquip.setDeposit(Float.parseFloat(depositStr));
				}
				if (!StrKit.isBlank(rent))
				{
					exsitEquip.setRent(Float.parseFloat(rent));
				}
				if (equipFile != null)
				{
					exsitEquip.setEquipPic(equipFile.getUploadPath() + java.io.File.separator + equipFile.getOriginalFileName());
				}
				exsitEquip.setCheckStatus(Constant.STATUS_SUMBIT_NOT_CHECK);
				if (exsitEquip.update())
				{
					renderHtml("<script  type='text/javascript'>alert('修改成功！请耐心等待管理员的再次审核');window.location='/equip/equipsForUser';</script>");
				}
				else
				{
					renderHtml("<script  type='text/javascript'>alert('修改失败！');window.location='/equip/equipsForUser';</script>");
				}
			}
			else
			{
				renderHtml("<script  type='text/javascript'>alert('装备信息已被删除，请联系管理员！');window.location='/';</script>");
			}
		}
		else
		{
			renderHtml("<script  type='text/javascript'>alert('您的登陆已失效，点击确定返回主页');window.location='/';</script>");
		}
	}
	
	/**
	 * 进入编辑页面
	 * 访问路径：/equip/edit
	 */
	public void edit()
	{
		int id = getParaToInt("id", 0);
		User user = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (user != null && user.getRoleId() <= Constant.ROLEID_LEADER && id != 0)
		{
			setAttr("equip", Equip.dao.queryInvalidEquip(id));
			render("uploadEquipEdit.html");
		}
		else
		{
			renderHtml("<script  type='text/javascript'>alert('您的登陆已失效，点击确定返回主页');window.location='/';</script>");
		}
		
	}
	
	
	/**
	 * 获取待审核装备列表
	 * 访问路径：/equip/portCheckEuiplist
	 */
	public void portCheckEuiplist()
	{
		renderJson(Equip.dao.queryCheckEuiplist());
	}
	
	/**
	 * 装备详情
	 * 访问路径：/equip/portDisplay
	 */
	public void portDisplay()
	{
		int id = getParaToInt("equipId", 0);
		Equip equip = Equip.dao.queryCheckedEquip(id);
		renderJson(equip);
	}
	
	/**
	 * 根据装备的类型等条件查询装备
	 * 访问路径： /equip/portEquips
	 */
	public void portEquips() {
		//默认查询第一个类型的装备
		String types = getPara("types", "1");
//		Set<Equip> results = Equip.dao.queryByCondition(types);
		renderJson(Equip.dao.queryByCondition(types));
	}
	
	/**
	 * 审核
	 * 访问路径：/equip/check
	 */
	public void check()
	{
		User user = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (user != null && user.getRoleId() == 1)
		{
			Integer equipId = getParaToInt("id");
			int isRecommend = getParaToInt("isRecommend");
			int top = getParaToInt("topStr");
			Integer isPass = getParaToInt("isPass");
			String checkMsg = getPara("checkMsg");
			Equip equip = Equip.dao.queryInvalidEquip(equipId);
			
			equip.setIsRecommend(isRecommend);
			equip.setTop(top);
			equip.setCheckMsg(checkMsg);
			
			if (isPass == 1)
			{
				equip.setCheckStatus(Constant.STATUS_CHECK_PASS);
			}
			else
			{
				equip.setCheckStatus(Constant.STATUS_CHECK_NOT_PASS);
			}
			if (equip.update())
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
			renderHtml("<script  type='text/javascript'>alert('只有管理员才能审核装备');window.location='/login';</script>");
		}
	}
	
}
