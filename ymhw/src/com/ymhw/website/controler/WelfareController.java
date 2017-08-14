package com.ymhw.website.controler;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.ymhw.website.model.User;
import com.ymhw.website.model.Welfare;
import com.ymhw.website.utils.Constant;
import com.ymhw.website.utils.ImgUtil;

/**
 * 公益控制类
 * @author oswin
 *
 */
public class WelfareController extends Controller
{
	private static final Log logger = Log.getLog(WelfareController.class);
	/**
	 * 公益内容主页
	 * 访问路径：/welfare/
	 */
	public void index()
	{
		User user = getSessionAttr(Constant.YMHW_SESSIONUSER);
		int authorId = 0;
		if (user != null)
		{
			authorId = user.getId();
		}
		int displayNum = 5;
		setAttr("welfares", Welfare.dao.queryList(displayNum));
//		setAttr("bestHot", Welfare.dao.queryBestHot());
//		setAttr("bestNew", Welfare.dao.queryBestNew());
//		setAttr("isStopping", Welfare.dao.queryIsStoping());
//		setAttr("bestMind", Welfare.dao.queryBestMind(authorId));
		
		render("welfare.html");
	}
	/**
	 * 跳转到发布公益内容页面
	 * 访问路径：/welfare/publishPath
	 */
	public void publishPath()
	{
		User user = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (user != null)
		{
			render("publishWelfarePro.html");
		}
		else
		{
			renderHtml("<script  type='text/javascript'>alert('登录后才能发起公益项目，请先到主页进行登录！');window.location='/loginPath';</script>");
		}
		
	}
	
	/**
	 * 发布公益内容
	 * 访问路径：/welfare/publish
	 */
	public void publish()
	{
		String title = getPara("title");
		int type = getParaToInt("type");
		//活动蜂鸣图片的base64字符串
		String pic = getPara("pic").trim();		
		String content = getPara("content").trim();
		
		//管理员设置数据
		User user = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (user != null)
		{
			Welfare welfare = new Welfare();
			welfare.setTitle(title);
			welfare.setType(type);
			String firstPart = pic.split(",")[0];
			String secondPart = pic.split(",")[1];
			String suffix = "." + firstPart.substring(firstPart.indexOf("/")+1, firstPart.indexOf(";"));
			String welfareImgPath = "/upload" + File.separator + "welfare" + File.separator + UUID.randomUUID().toString() + suffix;
			boolean convertResult = ImgUtil.generateImage(secondPart, welfareImgPath);
			if(!convertResult){
				logger.warn("base64 字符串转图片文件错误！");
			} else {
				welfare.setPic(welfareImgPath);
			}
			welfare.setContent(content);
			welfare.setAuthor(user.getId());
			welfare.setPublishTime(new Date());
			welfare.setIsValid(Constant.VALID);
			
			if (welfare.save())
			{
				renderHtml("<script  type='text/javascript'>alert('项目发起成功！');window.location='/welfare';</script>");
			}
			else
			{
				renderHtml("<script  type='text/javascript'>alert('项目发起失败！请重新填写！');window.location='/welfare/publish';</script>");
			}
		}
		else
		{
			renderHtml("<script  type='text/javascript'>alert('登录后才能发起公益项目，请先到主页进行登录！');window.location='/';</script>");
		}
	}
	
	/**
	 * 公益详情页面
	 * 访问路径：/welfare/detail
	 */
	public void detail()
	{
		int id = getParaToInt("id",0);
		Welfare welfare = Welfare.dao.queryById(id);
		if(welfare == null){
			//TODO 活动不存在 即页面不存在
			render("error404.html");
		} else {
			setAttr("welfare", welfare);
			render("welfare_detail.html");
		}
	}
	
	/**
	 * 所有公益组织列表页面
	 * 访问路径：/welfare/organizationList
	 */
	public void organizationList()
	{
		render("welfare_organize.html");
	}
	
	/**
	 * 转发公益项目
	 * 访问路径：/welfare/portRetransmiss
	 * 注意：更新操作中的同步处理
	 */
	public synchronized void portRetransmiss()
	{
		int id = getParaToInt("id",0);
		int updateResult = Db.update("UPDATE welfare set retransmissNum = retransmissNum + 1 WHERE isValid = 1 and id = ?", id);
		if (updateResult != 1){
			System.out.println("转发成功！");
			renderText("00");
		} else {
			renderText("01");
			System.out.println("转发失败");
		}
	}
	
}
