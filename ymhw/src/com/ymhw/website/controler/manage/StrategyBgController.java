package com.ymhw.website.controler.manage;

import java.util.Date;

import com.jfinal.core.Controller;
import com.ymhw.website.model.Strategy;
import com.ymhw.website.utils.Constant;

public class StrategyBgController extends Controller
{
	/**
	 * 攻略上传页面：
	 * 访问路径：/manage_strategy/upload
	 */
	public void uploadPath() {
		render("uploadStrategy.html");
	}
	
	/**
	 * 攻略上传：
	 * 访问路径：/manage_strategy/upload
	 */
	public void upload() {
		String title = getPara("title", "");
		String content = getPara("content", "");
		int type = getParaToInt("type", 0);
		
		Strategy strategy = new Strategy();
		strategy.setTitle(title);
		strategy.setContent(content);
		strategy.setTime(new Date());
		strategy.setType(type);
		strategy.setIsValid(Constant.INVALID);
		//后续加上userId
		if (strategy.save())
		{
			renderHtml("<script  type='text/javascript'>alert('上传成功！');window.location='/manage_strategy/uploadPath';</script>");
		} 
		else
		{
			renderHtml("<script  type='text/javascript'>alert('上传失败！');window.location='/manage_strategy/uploadPath';</script>");
		}
	}
	
}
