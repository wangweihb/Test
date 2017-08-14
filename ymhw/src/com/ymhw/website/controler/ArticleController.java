package com.ymhw.website.controler;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.ymhw.website.model.Activity;
import com.ymhw.website.model.Article;
import com.ymhw.website.model.User;
import com.ymhw.website.utils.Constant;
import com.ymhw.website.utils.ImgUtil;


/** 
 * 帖子
 * @author      oswin 
 * @since       1.0
 * create time：  2016年3月10日 下午19:27:40  
 */
public class ArticleController extends Controller
{
	private static final Log logger = Log.getLog(ArticleController.class);
	
	public void index()
	{
		
	}
	
	/**
	 * 帖子发布：
	 * 访问路径：/article/publish
	 */
	public void publish()
	{
		String title = getPara("title");
		String types = getPara("types").trim();
		//活动蜂鸣图片的base64字符串
		String pic = getPara("pic").trim();		
		String content = getPara("content").trim();
		
		//管理员设置数据
		User user = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (user != null)
		{
			Article article = new Article();
			article.setTitle(title);
			article.setTypes(types);
			String firstPart = pic.split(",")[0];
			String secondPart = pic.split(",")[1];
			String suffix = "." + firstPart.substring(firstPart.indexOf("/")+1, firstPart.indexOf(";"));
			String imgPath = "/upload" + File.separator + "article" + File.separator + UUID.randomUUID().toString() + suffix;
			boolean convertResult = ImgUtil.generateImage(secondPart, imgPath);
			if(!convertResult){
				logger.warn("base64 字符串转图片文件错误！");
			} else {
				article.setPic(imgPath);
			}
			article.setContent(content);
			article.setAuthor(user.getId());
			article.setPublishTime(new Date());
			article.setIsValid(Constant.VALID);
			
			if (article.save())
			{
				renderHtml("<script  type='text/javascript'>alert('分享成功！');window.location='/sharePath';</script>");
			}
			else
			{
				renderHtml("<script  type='text/javascript'>alert('发布失败！请重新分享！');window.location='/article/publishPath';</script>");
			}
		}
		else
		{
			renderHtml("<script  type='text/javascript'>alert('登录后才能分享，请先到主页进行登录！');window.location='/';</script>");
		}
	}
	
	/**
	 * 帖子发布路径跳转：
	 * 访问路径：/article/publishPath
	 */
	public void publishPath()
	{
		User user = getSessionAttr(Constant.YMHW_SESSIONUSER);
		if (user != null)
		{
			render("publishArticle.html");
		}
		else
		{
			renderHtml("<script  type='text/javascript'>alert('登录后才能分享，请先到主页进行登录！');window.location='/loginPath';</script>");
		}
		
		
	}
	
	/**
	 * 帖子列表：
	 * 访问路径：/article/list
	 */
	public void list()
	{
		setAttr("articles", Article.dao.getList(10));
		render("articleList.html");
	}
	
	/**
	 * 根据类型显示帖子列表
	 * 访问路径：/article/list
	 */
	public void portListByType()
	{
		int displayNum = 0;
		int type = getParaToInt("type", 0);
		displayNum = getParaToInt("displayNum", 0);
		List<Article> articles = Article.dao.getListByType(type, displayNum);
		setAttr("articles", articles);
		renderJson(articles);
	}
	
	/**
	 * 帖子详情
	 * 访问路径：/article/display
	 */
	public void display()
	{
		int id = getParaToInt("id", 1);
		Article one = Article.dao.queryOneValid(id);
		setAttr("article", one);
		render("article_detail.html");
	}
}
