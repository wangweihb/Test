package com.ymhw.website.controler;

import java.util.List;

import com.jfinal.core.Const;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.ymhw.website.model.User;
import com.ymhw.website.utils.Constant;

public class UserConrtroller extends Controller
{
	
	/**
	 * <p>领队申请,领队申请时首先要已经成功注册登陆了,已经是领队的不需要重复申请</p>
	 * 访问路径：/user/applyforLeader
	 */
	public void applyforLeader()
	{
		User user = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (user != null)
		{
			switch (user.getCheckStatus())
			{
				/**等待审核*/
				case Constant.STATUS_SUMBIT_NOT_CHECK:
					String htmlText = "<script  type='text/javascript'>alert('您以提交了领队申请的请求，请耐心等待3-5个工作日，注意到个人中心查看审核结果！');window.location='/applyfoLeaderPath';</script>";
					renderHtml(htmlText);
					break;
					
				/**审核未通过*/	
				case Constant.STATUS_CHECK_NOT_PASS:
					//String checkMsg = user.getCheckMsg();
					String htmlText2 = "<script  type='text/javascript'>alert('您的审核未通过，请到个人中心查看失败原因并修改提交材料进行再次申请！');window.location='/applyfoLeaderPath';</script>";
					renderHtml(htmlText2);
					break;
					
				/**审核以通过(已经成为领队了不需要再申请)*/	
				case Constant.STATUS_CHECK_PASS:
					String htmlText3 = "<script  type='text/javascript'>alert('您已经是领队了，不能再次申请！');window.location='/applyfoLeaderPath';</script>";
					renderHtml(htmlText3);
					break;
					
				case Constant.STATUS_NOT_SUBMIT:
					UploadFile experienceUf = getFile();
					String name = getPara("name").trim();
					int sex = getParaToInt("sex");
					String telphone = getPara("telphone").trim();
					String fixedNumber = getPara("fixedNumber").trim();
					String qq = getPara("qq").trim();
					int certificateType = getParaToInt("certificateType");
					String certificateNum = getPara("certificateNum").trim();
					int country = getParaToInt("country");
					String province = getPara("province").trim();
					String city = getPara("city").trim();
					String address = getPara("address").trim();
					String emergencyPerson = getPara("emergencyPerson").trim();
					String emergencyPhone = getPara("emergencyPhone").trim();
					int emergencyRelation = getParaToInt("emergencyRelation");
					String certificateFront = getPara("certificateFront").trim();
					String certificateBack = getPara("certificateBack").trim();
					String certificateHander = getPara("certificateHander").trim();
					String otherCertificate = getPara("otherCertificate").trim();
					String motto = getPara("motto").trim();
					String outdoorExperience = getPara("outdoorExperience").trim();
					String studyExperience = getPara("studyExperience").trim();
					String headShot = getPara("headShot");
					
					user.setAddress(address);
					user.setCertificateBack(certificateBack);
					user.setCertificateFront(certificateFront);
					user.setCertificateHander(certificateHander);
					user.setCertificateNum(certificateNum);
					user.setCertificateType(certificateType);
					user.setCity(city);
					user.setCountry(country);
					user.setEmergencyPerson(emergencyPerson);
					user.setEmergencyPhone(emergencyPhone);
					user.setEmergencyRelation(emergencyRelation);
					if (experienceUf != null)
					{
						String experienceDoc = experienceUf.getUploadPath();
						user.setExperienceDoc(experienceDoc+experienceUf.getOriginalFileName());
					}
					
					user.setFixedNumber(fixedNumber);
					user.setMotto(motto);
					user.setName(name);
					user.setOutdoorExperience(outdoorExperience);
					user.setOtherCertificate(otherCertificate);
					user.setProvince(province);
					user.setQq(qq);
					user.setSex(sex);
					user.setTelphone(telphone);
					user.setStudyExperience(studyExperience);
					user.setHeadshot(headShot);
					
					user.setCheckStatus(Constant.STATUS_SUMBIT_NOT_CHECK);
					
					boolean flag = user.update();
					String tips = "";
					if (flag)
					{
						tips = "<script  type='text/javascript'>alert('您的申请已提交成功！请耐心等待3-5个工作日，注意到个人中心查看审核结果！');window.location='/applyfoLeaderPath';</script>";
					}
					else
					{
						tips = "<script  type='text/javascript'>alert('您的申请提交失败！');window.location='/applyfoLeaderPath';</script>";
					}
					renderHtml(tips);
					break;
				default:
					
					break;
			}
		}
		else
		{
			String errorText = "<script  type='text/javascript'>alert('您还没有权限申请领队，请先注册并登陆成功后进行申请!');window.location='/registerPath';</script>";
			renderHtml(errorText);
		}
	}
	
	/**
	 * 推荐领队展示
	 * /user/portTopLeader
	 */
	public void portTopLeader()
	{
		renderJson(User.dao.queryTopLeaders(4));
	}
	
	/**
	 * 接口，下拉列表中数据（id,account,name）
	 * /user/portAllLeaders
	 */
	public void portAllLeaders()
	{
		renderJson(User.dao.queryAllLeaders(0));
	}
	
	/**
	 * 编辑领队
	 * 访问路径：/user/editLeader
	 */
	public void editLeader()
	{
		Integer id = getParaToInt("id");
		if (id != null && id != 0)
		{
			User user = User.dao.queryCheckLeader(id);
			if (user != null)
			{
				setAttr("leader", user);
				render("checkLeaderEdit.html");
			}
			else
			{
				renderHtml("<script  type='text/javascript'>alert('领队以被删除，请联系管理员');window.location='/checkLeadertPath';</script>");
			}
		}
		else
		{
			renderHtml("<script  type='text/javascript'>alert('领队信息不存在');window.location='/checkLeadertPath';</script>");
		}
	}
	
	/**
	 * 更新领队信息（guest自己修改）
	 * 访问路径：/user/updateApplyfor
	 */
	public void updateApplyfor()
	{
		Integer id = getParaToInt("userId");
		if (id != null && id != 0)
		{
			User user = User.dao.queryCheckFailLeader(id);
			if (user != null)
			{
				setAttr("leader", user);
				render("applyforUpdate.html");
			}
			else
			{
				renderHtml("<script  type='text/javascript'>alert('领队以被删除，请联系管理员');window.location='/';</script>");
			}
		}
		else
		{
			renderHtml("<script  type='text/javascript'>alert('领队信息不存在');window.location='/';</script>");
		}
	}
	/**
	 * 领队审核
	 * 访问路径：/user/check
	 */
	public void check()
	{
		User sessionuser = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (sessionuser != null && sessionuser.getRoleId() == 1)
		{
			Integer userId = getParaToInt("userId");
			Integer isPass = getParaToInt("isPass");
			String checkMsg = getPara("checkMsg");
			
			User user = User.dao.queryValidLeader(userId);
			user.setCheckMsg(checkMsg);
			if (isPass == 1)
			{
				user.setRoleId(Constant.ROLEID_LEADER);
				user.setCheckStatus(Constant.STATUS_CHECK_PASS);
			}
			else
			{
				user.setCheckStatus(Constant.STATUS_CHECK_NOT_PASS);
			}
			if (user.update())
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
			renderHtml("<script  type='text/javascript'>alert('只有管理员才能审核领队');window.location='/login';</script>");
		}
	}
	
	/**
	 * 获得领队审核结果
	 * 访问路径：/user/getCheckResult
	 */
	public void getCheckResult()
	{
		User sessionuser = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (sessionuser != null && sessionuser.getRoleId() == 3)
		{
			User user = User.dao.queryValidLeader(sessionuser.getId());
			int checkStatus = user.getCheckStatus();
			if (user.getCheckStatus() == Constant.STATUS_CHECK_PASS && user.getRoleId() == Constant.ROLEID_LEADER)
			{
				checkStatus = Constant.STATUS_CHECK_PASS;
			}
			setAttr("userId", user.getId());
			setAttr("checkStatus", checkStatus);
			setAttr("checkMsg", user.getCheckMsg());
			render("applyforResult.html");
		}
		else
		{
			renderHtml("<script  type='text/javascript'>alert('您的登陆已失效，点击确定返回主页');window.location='/';</script>");
		}
	}
	
	/**
	 * 调转到领队列表页面
	 * 访问路径：/user/leaderListPath
	 */
	public void leaderListPath()
	{
		int DEFAULT_NUM = 12;
		List<User> leaders = User.dao.queryAllLeaders(DEFAULT_NUM);
		setAttr("leaders", leaders);
		render("leaderList.html");
	}
}
