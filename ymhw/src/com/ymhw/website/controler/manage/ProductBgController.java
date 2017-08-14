package com.ymhw.website.controler.manage;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.ymhw.website.model.Product;
import com.ymhw.website.utils.Constant;
import com.ymhw.website.utils.FileUtil;

/**
 * 后台产品管理（访问路径：/manage_product）
 * @author oswin
 */
public class ProductBgController extends Controller{
	
	public void index() {
		render("uploadProduct.html");
	}
	
	/**
	 * 上传产品（目前只对农家乐中的产品）
	 * 访问路径：/manage_product/add
	 */
	public void add() {
		long rand = System.currentTimeMillis();
		String thefilepath = Constant.UPLOAD_INFORMATION_ROOT +File.separator+ Constant.UPLOAD_PRODUCT + File.separator + rand;
		String theReqServer = Constant.URL_SEP + PropKit.get("uploadPath") + Constant.URL_SEP + Constant.UPLOAD_INFORMATION_ROOT +Constant.URL_SEP + 
				Constant.UPLOAD_PRODUCT + Constant.URL_SEP + rand + Constant.URL_SEP;
		
		//jfinal 中对单个input 多个文件 getFiles总是只返回一个对象，但是文件已经上传到对应的路径
		getFiles(thefilepath);
		String dir = PathKit.getWebRootPath() + File.separator + PropKit.get("uploadPath") + File.separator + thefilepath;
		List<String> fileNames = FileUtil.getFileNames(dir);
		
		String paths = "";
		for (String filename : fileNames) {
			String imgPath =  theReqServer + filename;
			paths = paths + imgPath + ",";
		}
		if (!StrKit.isBlank(paths)) {
			paths = paths.substring(0, paths.length()-1);
		}
		
		Product product = new Product();
		product.setType(getParaToInt("proType", 0));
		product.setCreateTime(new Date());
		product.setDestId(getParaToInt("destId", 0));
		product.setFarmId(getParaToInt("farmId", 0));
		product.setTitle(getPara("title", ""));
		product.setSubhead(getPara("subhead", ""));
		product.setPrice(new BigDecimal(getPara("price", "0")));
		product.setBuynote(getPara("product_buy", ""));
		product.setDetail(getPara("product_detail", ""));
		product.setPics(paths);
		product.setIsValid(Constant.VALID);
		
		if (product.save()) {
			renderHtml("<script  type='text/javascript'>alert('上传成功！');window.location='/manage_product';</script>");
		} else {
			System.out.println("产品信息的存库失败....");
			renderHtml("<script  type='text/javascript'>alert('上传失败！');window.location='/manage_product';</script>");
		}
	}
}
