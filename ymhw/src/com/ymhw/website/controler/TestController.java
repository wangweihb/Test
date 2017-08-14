package com.ymhw.website.controler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.ymhw.website.utils.Constant;
import com.ymhw.website.utils.ImgUtil;

/** 
 * info
 * @author      ws 
 * @since       1.0
 * create time：  2016年1月16日 上午10:19:03  
 * E-mail:      wangshuo@keenyoda.net
 */
public class TestController extends Controller
{
	public void index()
	{
		String newPic = PathKit.getWebRootPath() + Constant.URL_SEP + PropKit.get("uploadPath") 
		+ Constant.URL_SEP + Constant.UPLOAD_FARMSTAY + Constant.URL_SEP +
//		  UUID.randomUUID().toString() + suffix;
		"product" + Constant.URL_SEP + UUID.randomUUID().toString() + ".jpg";
		String secondPart = "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";
		boolean convertResult = ImgUtil.generateImage(secondPart, newPic);
		boolean convertResult2 = ImgUtil.generateImage(secondPart, Constant.URL_SEP + PropKit.get("uploadPath") 
		+ Constant.URL_SEP + Constant.UPLOAD_FARMSTAY + Constant.URL_SEP +
//			  UUID.randomUUID().toString() + suffix;
		"product" + Constant.URL_SEP + "ttt" + ".jpg");
		System.out.println("农家乐中产品图片格式转换结果 ： " + convertResult + convertResult2 + "\t 对应的路径 ： " + newPic);
		Map<String, Boolean> res = new HashMap<String, Boolean>();
		res.put("res1", convertResult);
		res.put("res2", convertResult2);
		renderJson(res);
	}
	
}
