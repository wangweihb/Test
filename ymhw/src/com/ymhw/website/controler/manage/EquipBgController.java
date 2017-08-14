package com.ymhw.website.controler.manage;

import java.util.Date;

import org.apache.shiro.subject.Subject;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import com.ymhw.website.model.Equip;
import com.ymhw.website.model.User;
import com.ymhw.website.utils.Constant;

/**
 * 
 * @author oswin
 */
public class EquipBgController extends Controller
{
	/**
	 * 装备上传页面
	 * 访问路径：/manage_equip/uploadEquipPath
	 */
	public void uploadEquipPath()
	{
		render("uploadEquip.html");
	}
	
	/**
	 * 上传一件装备
	 * 访问路径：/manage_equip/add
	 */
	public void add()
	{
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
	
	/**
	 * 某个领队上传装备的审核结果<br/>
	 * 访问路径：/manage_equip/equipsForUser
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
			renderHtml("<script  type='text/javascript'>alert('您的登陆已失效，点击确定返回主页');window.location='/manage';</script>");
		}
		
	}
	
	/**
	 * 领队编辑某个装备
	 * 访问路径：/manage_equip/editEquip
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
					renderHtml("<script  type='text/javascript'>alert('修改成功！请耐心等待管理员的再次审核');window.location='/manage_equip/equipsForUser';</script>");
				}
				else
				{
					renderHtml("<script  type='text/javascript'>alert('修改失败！');window.location='/manage_equip/equipsForUser';</script>");
				}
			}
			else
			{
				renderHtml("<script  type='text/javascript'>alert('装备信息已被删除，请联系管理员！');window.location='/manage';</script>");
			}
		}
		else
		{
			renderHtml("<script  type='text/javascript'>alert('您的登陆已失效，点击确定返回主页');window.location='/manage';</script>");
		}
	}
	
	/**
	 * 进入编辑页面
	 * 访问路径：/manage_equip/edit
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
			renderHtml("<script  type='text/javascript'>alert('您的登陆已失效，点击确定返回主页');window.location='/manage';</script>");
		}
		
	}
	
	
	/**
	 * 获取待审核装备列表
	 * 访问路径：/manage_equip/portCheckEuiplist
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
	 * 审核
	 * 访问路径：/manage_equip/check
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
			renderHtml("<script  type='text/javascript'>alert('只有管理员才能审核装备');window.location='/manage';</script>");
		}
	}
	
}
