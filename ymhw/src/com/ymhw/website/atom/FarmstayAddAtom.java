package com.ymhw.website.atom;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.IAtom;
import com.ymhw.website.model.Farmstay;
import com.ymhw.website.model.Product;
import com.ymhw.website.utils.Constant;
import com.ymhw.website.utils.ImgUtil;


public class FarmstayAddAtom implements IAtom
{
	private Farmstay farmstay;
	private String[] proTitles ;
	private String[] proPrices;
	private String[] proPics;
	
	public FarmstayAddAtom(Farmstay farmstay, String[] proTitles, String[] proPrices, String[] proPics)
	{
		this.farmstay = farmstay;
		this.proTitles = proTitles;
		this.proPrices = proPrices;
		this.proPics = proPics;
	}
	
	@Override
	public boolean run() throws SQLException
	{
		//info 表的处理
		boolean result = farmstay.save();
		if (!result)
		{
			//尽早回滚
			return false;
		}
		
		Farmstay res = Farmstay.dao.findFirst("select * from farmstay where shopName = ?", farmstay.getShopName());
		if (res == null)
		{
			return false;
		}
		//需要通过事务处理
		if (proTitles != null && proTitles.length >1) {
			//从1开始，首页中有个空的产品div
//			for(int i = 1; i < proTitles.length; i++) {
//				Product product = new Product();
//				product.setFarmId(res.getId());
//				product.setType(1);
//				product.setIsValid(1);
//				product.setUploadTime(new Date());
//				product.setProTitle(proTitles[i]);
//				product.setProPrice(new BigDecimal(proPrices[i]));
//				
//				String firstPart = proPics[i].split(",")[0];
//				String secondPart = proPics[i].split(",")[1];
//				String suffix = "." + firstPart.substring(firstPart.indexOf("/")+1, firstPart.indexOf(";"));
//				String newPic = Constant.URL_SEP + PropKit.get("uploadPath") 
//						+ Constant.URL_SEP + Constant.UPLOAD_FARMSTAY + Constant.URL_SEP +
//						"product" + Constant.URL_SEP + UUID.randomUUID().toString() + suffix;
//				boolean convertResult = ImgUtil.generateImage(secondPart, PathKit.getWebRootPath() + newPic);
//				System.out.println("农家乐中产品图片格式转换结果 ： " + convertResult + "\t 对应的路径 ： " + newPic);
//				product.setProPic(newPic);
//				if (!product.save() || !convertResult)
//				{
//					return false;
//				}
//			}
		}
		return true;
	}

}
