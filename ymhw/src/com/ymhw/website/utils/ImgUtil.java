package com.ymhw.website.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder;  
/**
 * 图片处理的工具类 
 * @author oswin
 * @date 2016年9月7日
 */
public class ImgUtil   
{  
    
    /**
     * 图片转化成base64字符串  
     * @param imgPath 待处理的图片的完全路径
     * @return
     */
    public static String getImageStr(String imgPath)  
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
       
        InputStream in = null;  
        byte[] data = null;  
        //读取图片字节数组  
        try   
        {  
            in = new FileInputStream(imgPath);          
            data = new byte[in.available()];  
            in.read(data);  
            in.close();  
        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
        }  
        //对字节数组Base64编码  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(data);//返回Base64编码过的字节数组字符串  
    }  
      
    /**
     * base64字符串转化成图片  
     * @param imgStr      图片base64字符串
     * @param newimgPath  新生成的图片 全路径
     * @return  		      生成成功-true   异常-false
     */
    public static boolean generateImage(String imgStr, String newimgPath)  
    {   //对字节数组字符串进行Base64解码并生成图片  
        if (imgStr == null) //图像数据为空  
            return false;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try   
        {  
            //Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }  
            //生成jpeg图片  
            OutputStream out = new FileOutputStream(newimgPath);      
            out.write(b);  
            out.flush();  
            out.close();  
            return true;  
        }   
        catch (Exception e)   
        {
        	e.printStackTrace();
            return false;  
        }  
    }
    
    /** 
     * 采用指定宽度、高度或压缩比例 的方式对图片进行压缩 
     * @param imgsrc 源图片地址 
     * @param imgdist 目标图片地址 
     * @param widthdist 压缩后图片宽度（当rate==null时，必传） 
     * @param heightdist 压缩后图片高度（当rate==null时，必传） 
     * @param rate 压缩比例  
     */  
    public static void reduceImg(String imgsrc, String imgdist, int widthdist,  
            int heightdist, Float rate) {  
        try {  
            File srcfile = new File(imgsrc);  
            // 检查文件是否存在  
            if (!srcfile.exists()) {  
                return;  
            }  
            // 如果rate不为空说明是按比例压缩  
            if (rate != null && rate > 0) {  
                // 获取文件高度和宽度  
                int[] results = getImgWidth(srcfile);  
                if (results == null || results[0] == 0 || results[1] == 0) {  
                    return;  
                } else {  
                    widthdist = (int) (results[0] * rate);  
                    heightdist = (int) (results[1] * rate);  
                }  
            }  
            // 开始读取文件并进行压缩  
            Image src = javax.imageio.ImageIO.read(srcfile);  
            BufferedImage tag = new BufferedImage((int) widthdist,  
                    (int) heightdist, BufferedImage.TYPE_INT_RGB);  
  
            tag.getGraphics().drawImage(  
                    src.getScaledInstance(widthdist, heightdist,  
                            Image.SCALE_SMOOTH), 0, 0, null);  
  
            FileOutputStream out = new FileOutputStream(imgdist);  
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
            encoder.encode(tag);  
            out.close();  
  
        } catch (IOException ex) {  
            ex.printStackTrace();  
        }  
    } 
    
    /** 
     * 获取图片宽度 
     *  
     * @param file 
     *            图片文件 
     * @return 宽度 
     */  
    public static int[] getImgWidth(File file) {  
        InputStream is = null;  
        BufferedImage src = null;  
        int result[] = { 0, 0 };  
        try {  
            is = new FileInputStream(file);  
            src = javax.imageio.ImageIO.read(is);  
            result[0] = src.getWidth(null); // 得到源图宽  
            result[1] = src.getHeight(null); // 得到源图高  
            is.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
    
    public static void main(String[] args)  
    {  
//    	String imgFile = "e://commondoc//testimg//5.png";//待处理的图片  
//    	String strImg = getImageStr(imgFile);  
//    	System.out.println(strImg); 
//    	String xx = "enBIq6cyA0imWthjApNkcdiSyVnsKdLN4DtENzL7mIk+U4aliJKpk9SvCKYBocPe5hVQoEMMJWQHqGn3S8SyMd4XkBvi7qPIpQOYYku2dMmg1AxgEbrWaTk+bkAlITh3eT99U/wBAT5NKx7Sv6e3V4IX+sRkPsuSLRFY1fnPqzk=";
//    	String imgFilePath = "e://commondoc//testimg//222.jpg";//新生成的图片  
//        strImg = "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";
//    	System.out.println(generateImage(strImg,imgFilePath));
        
//        String imgFile = "/upload/farmstay/1.jpg";//待处理的图片  
//        String strImg = getImageStr(imgFile);  
//        System.out.println(strImg); 
//        String xx = "enBIq6cyA0imWthjApNkcdiSyVnsKdLN4DtENzL7mIk+U4aliJKpk9SvCKYBocPe5hVQoEMMJWQHqGn3S8SyMd4XkBvi7qPIpQOYYku2dMmg1AxgEbrWaTk+bkAlITh3eT99U/wBAT5NKx7Sv6e3V4IX+sRkPsuSLRFY1fnPqzk=";
//        String imgFilePath = "e://commondoc//testimg//222.jpg";//新生成的图片  
//        System.out.println(generateImage(strImg,imgFilePath));
    	
    	System.out.println("压缩图片开始...");  
        File srcfile = new File("e://licence.png");  
        System.out.println("压缩前srcfile size:" + srcfile.length());  
        reduceImg("e://licence.png", "e://licence11.png", 500, 700,null);  
        File distfile = new File("e://licence11.png");  
        System.out.println("压缩后distfile size:" + distfile.length());  
        
        
    }  
    
}  