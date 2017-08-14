package com.ymhw.website.config;


import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.ymhw.website.controler.ActivityBillController;
import com.ymhw.website.controler.ActivityController;
import com.ymhw.website.controler.AdviceController;
import com.ymhw.website.controler.AlipayController;
import com.ymhw.website.controler.ArticleController;
import com.ymhw.website.controler.CarFactoryController;
import com.ymhw.website.controler.CarRentController;
import com.ymhw.website.controler.EquipController;
import com.ymhw.website.controler.IndexController;
import com.ymhw.website.controler.InfomationController;
import com.ymhw.website.controler.LoginController;
import com.ymhw.website.controler.MobileIndexController;
import com.ymhw.website.controler.OrderController;
import com.ymhw.website.controler.PayController;
import com.ymhw.website.controler.RegisterController;
import com.ymhw.website.controler.TestController;
import com.ymhw.website.controler.UserConrtroller;
import com.ymhw.website.controler.ValidateCodeController;
import com.ymhw.website.controler.WelfareController;
import com.ymhw.website.controler.manage.AdminController;
import com.ymhw.website.controler.manage.CarRentBgController;
import com.ymhw.website.controler.manage.EquipBgController;
import com.ymhw.website.controler.manage.InfoBgController;
import com.ymhw.website.controler.manage.ProductBgController;
import com.ymhw.website.controler.manage.RouteController;
import com.ymhw.website.controler.manage.StrategyBgController;
import com.ymhw.website.model.Info;
import com.ymhw.website.model.InfoHasTag;
import com.ymhw.website.model.InfoModule;
import com.ymhw.website.model.InfoTag;
import com.ymhw.website.model._MappingKit;

/** 
 * api引导式配置
 * @author      ws 
 * @since       1.0
 * create time：  2016年1月4日 下午2:37:40  
 */
public class Config extends JFinalConfig
{
	/**
	 * 供shiro插件使用
	 */
	Routes routes;

	/**
	 * 配置常量-
	 */
	@Override
	public void configConstant(Constants me)
	{
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("a_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setBaseUploadPath(PropKit.get("uploadPath"));
		
		me.setError404View("/foreground/error404.html");
		me.setError500View("/foreground/error404.html");
		
		//RequiresGuest，RequiresAuthentication，RequiresUser验证异常，返回HTTP401状态码
		me.setError401View("/background/401.html");
		//RequiresRoles，RequiresPermissions授权异常,返回HTTP403状态码
		me.setError403View("/background/403.html");
	}

	/**
	 * 配置路由
	 */
	@Override
	public void configRoute(Routes me)
	{
		this.routes = me;
		
		/**pc端*/
		/**前端*/
		me.add("/", IndexController.class,"/foreground"); // 第三个参数为该Controller的视图存放路径
		me.add("/verifycode",ValidateCodeController.class,"/foreground");
		me.add("/register",RegisterController.class,"/foreground");
		me.add("/login",LoginController.class,"/foreground");
		me.add("/activity",ActivityController.class,"/foreground");
		me.add("/user",UserConrtroller.class,"/foreground");
		me.add("/equip",EquipController.class,"/foreground");
		me.add("/advice",AdviceController.class,"/foreground");
		me.add("/article",ArticleController.class,"/foreground");
		me.add("/welfare",WelfareController.class,"/foreground");
		me.add("/actbill",ActivityBillController.class,"/foreground");
		me.add("/infoPath",InfomationController.class,"/foreground");
		me.add("/carRent", CarRentController.class, "/foreground");
		me.add("/demo", TestController.class, "/foreground");
		me.add("/carFactory", CarFactoryController.class, "/foreground");
		me.add("/order", OrderController.class, "/foreground");
		
		/**后台管理*/
		me.add("/manage",RouteController.class,"/background");
		me.add("/manage_carRent", CarRentBgController.class, "/background/carRent");
		me.add("/manage_strategy", StrategyBgController.class, "/background/strategy");
		me.add("/manage_equip", EquipBgController.class, "/background/equip");
		me.add("/manage_product", ProductBgController.class, "/background/info");
		me.add("/manage_info", InfoBgController.class, "/background/info");
		me.add("/admin", AdminController.class, "/admin");
		
		/**移动端*/
		me.add("/mobile",MobileIndexController.class,"/mobile");
		
		/**支付宝在线支付*/
		me.add("/alipay", AlipayController.class,"/foreground");
		
		/**在线支付（第三方）*/
		me.add("/pay", PayController.class,"/foreground");
		
		
	}
	
	/**
	 * 
	 * @return
	 */
	public static C3p0Plugin createC3p0Plugin()
	{
		return new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"),
				PropKit.get("password").trim());
	}
	
	public static DruidPlugin createDruidPlugin()
	{
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"),
				PropKit.get("password").trim());
	}

	/**
	 * 配置插件
	 */
	@Override
	public void configPlugin(Plugins me)
	{
		DruidPlugin druidPlugin = createDruidPlugin();
		me.add(druidPlugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.setShowSql(true);
		arp.addMapping("info", "info_id", Info.class);
		arp.addMapping("info_has_tag", "info_id,tag_id", InfoHasTag.class);
		arp.addMapping("info_tag", "tag_id", InfoTag.class);
		arp.addMapping("info_module", "info_module_id", InfoModule.class);
		me.add(arp);
		
		//数据库表与对象的对应关系配置都在MappingKit中搞定
		_MappingKit.mapping(arp);
		
		//配置shiro插件
		/*ShiroPlugin shiroPlugin = new ShiroPlugin(this.routes);
		//登录URL，未成功时跳转
		shiroPlugin.setLoginUrl("/manage/index");
		//登录成功后跳转地址
		shiroPlugin.setSuccessUrl("/manage/login");
		//未获得授权的跳转地址
		shiroPlugin.setUnauthorizedUrl("/manage/noauth");
		me.add(shiroPlugin);*/
		
	}

	/**
	 * 配置全局拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me)
	{
		me.add(new SessionInViewInterceptor());
		//TODO：使用全局拦截器，待定
//		me.add(new ShiroInterceptor());
		
	}

	/**
	 * 配置处理器
	 */
	@Override
	public void configHandler(Handlers me)
	{

	}

	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目 运行此 main
	 * 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args)
	{
		JFinal.start("WebRoot", 88, "/", 5);
	}
}
