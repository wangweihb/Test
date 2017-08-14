package com.ymhw.website.controler.manage;

import java.util.Date;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.upload.UploadFile;
import com.ymhw.website.model.Car;
import com.ymhw.website.model.Carfactory;
import com.ymhw.website.utils.Constant;

/**
 * 租车服务的后台管理
 * @author oswin
 * /manage_carRent
 */
public class CarRentBgController extends Controller
{
	public void index() {
		render("cr_upload.html");
	}
	
	/**
	 * /manage_carRent/upload
	 */
	public void upload() {
		System.err.println("carUpload....");
		UploadFile uploadFile = getFile("showpic", Constant.UPLOAD_CAR);
		String carName = getPara("carName", "");
		int carType = getParaToInt("carType", 0);
		int szpType = getParaToInt("szpType", 0);
		int cntLimit = getParaToInt("cntLimit", 1);
		int factoryId = getParaToInt("factoryId", 0);
		String priceStr = getPara("price", "0.0");
		String sweptVolume = getPara("sweptVolume", "");
		
		Car car = new Car();
		String imgPath = Constant.URL_SEP + PropKit.get("uploadPath") 
						+ Constant.URL_SEP + Constant.UPLOAD_CAR + Constant.URL_SEP + uploadFile.getFileName();
		car.setShowpic(imgPath);
		car.setCntLimit(cntLimit);
		car.setGearType(szpType);
		car.setName(carName);
		car.setPrice(Float.parseFloat(priceStr));
		car.setType(carType);
		car.setSweptVolume(sweptVolume);
		car.setLastUpdateTime(new Date());
		car.setFactoryId(factoryId);
		
		if(car.save()) {
			renderHtml("<script  type='text/javascript'>alert('上传成功！');window.location='/manage_carRent';</script>");
		} else {
			renderHtml("<script  type='text/javascript'>alert('上传失败，请重新上传！');window.location='/manage_carRent';</script>");
		}
	}
	
	/**
	 * 所有租车商户
	 * /manage_carRent/portCarFactories
	 */
	public void portCarFactories() {
		renderJson(Carfactory.dao.getAllFactories());
	}
}
