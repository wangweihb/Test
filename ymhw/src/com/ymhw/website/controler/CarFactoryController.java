package com.ymhw.website.controler;

import com.jfinal.core.Controller;
import com.ymhw.website.model.Carfactory;

public class CarFactoryController extends Controller
{
	/**
	 * 加载所有的门店信息
	 * 访问路径： /carFactory/portAllFactories
	 */
	public void portAllFactories(){
		renderJson(Carfactory.dao.getAllFactories());
	}
	
}
