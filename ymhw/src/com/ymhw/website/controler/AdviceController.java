package com.ymhw.website.controler;

import java.util.Date;

import com.jfinal.core.Controller;
import com.ymhw.website.model.Advice;
import com.ymhw.website.utils.Constant;

public class AdviceController extends Controller
{
	public void index()
	{
		
	}
	
	/**
	 * 添加
	 * 访问路径：/advice/portAdd
	 */
	public void portAdd()
	{
		String email = getPara("email", "");
		String telphone = getPara("telphone", "");
		String content = getPara("content", "");
		Advice advice = new Advice();
		advice.setEmail(email);
		advice.setTelphone(telphone);
		advice.setContent(content);
		advice.setTime(new Date());
		advice.setIsValid(Constant.VALID);
		
		if (advice.save())
		{
			renderJson("1");
		}
		else
		{
			renderJson("0");
		}
	}
}
