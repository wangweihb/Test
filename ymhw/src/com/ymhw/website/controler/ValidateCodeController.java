package com.ymhw.website.controler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.jfinal.core.Controller;
import com.ymhw.website.utils.Constant;

/** 
 * 验证码
 * @author      oswin 
 * @since       1.0
 * create time：  2016年2月29日 下午2:37:40  
 * E-mail:      wangshuo@keenyoda.net
 */
public class ValidateCodeController extends Controller
{
	
	/**
	 * 访问路径：/verifycode/getCode
	 * @throws IOException
	 */
	public void getCode() throws IOException
	{
		
		BufferedImage img = new BufferedImage(68, 22, BufferedImage.TYPE_INT_RGB); 		           

		// 得到该图片的绘图对象  
		Graphics g = img.getGraphics();  
		Random r = new Random();  		
		Color c = new Color(200, 150, 255);  		
		g.setColor(c);  
		
		// 填充整个图片的颜色  		
		g.fillRect(0, 0, 68, 22);  
		
		// 向图片中输出数字和字母  		
		StringBuffer sb = new StringBuffer();  		
		char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();  		
		int index, len = ch.length;  		
		for (int i = 0; i < 4; i ++)
		{  	
			index = r.nextInt(len);  		
	        g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt(255)));  
	        g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));// 输出的字体和大小                   
	        g.drawString("" + ch[index], (i * 15) + 3, 18);//写什么数字，在图片的什么位置画  
	        sb.append(ch[index]);  		
		}  		
		getSession().setAttribute(Constant.VERIFYCODE_KEY, sb.toString()); 
		System.out.println("***********验证码(系统生成)：" + sb.toString());
		ImageIO.write(img, "JPG", getResponse().getOutputStream()); 
		renderNull();
	}
	
}
