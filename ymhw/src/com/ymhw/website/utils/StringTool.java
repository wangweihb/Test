package com.ymhw.website.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;


/**
 * 常用的与字符串有关的工具类
 * @author oswin
 * @date 2016年8月30日
 */
public class StringTool {
	
	/**
	 * 判断整数（int）  
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {  
	    if (null == str || "".equals(str)) {  
	        return false;  
	    }  
	    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
	    return pattern.matcher(str).matches();  
	}  
	  
	/**
	 * 判断浮点数（double和float）  
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {  
	    if (null == str || "".equals(str)) {  
	        return false;  
	    }  
	    Pattern pattern = Pattern.compile("[0-9]+\\.?[0-9]+");  
	    return pattern.matcher(str).matches();  
	}  
	
	/**
	 * 
	 * @param num
	 * @return
	 */
	public static String generateVBCode(int num){
		return getCode(num, 0);
	}
	
	/**
	 * 获得对应长度的字符串
	 * @param passLength
	 * @param type
	 * @return
	 */
	public static String getCode(int passLength, int type)  
    {  
        StringBuffer buffer = null;  
        StringBuffer sb = new StringBuffer();  
        Random r = new Random();  
        r.setSeed(new Date().getTime());  
        switch (type)  
        {  
        case 0:  
            buffer = new StringBuffer("0123456789");  
            break;  
        case 1:  
            buffer = new StringBuffer("abcdefghijklmnopqrstuvwxyz");  
            break;  
        case 2:  
            buffer = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ");  
            break;  
        case 3:  
            buffer = new StringBuffer(  
                    "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");  
            break;  
        case 4:  
            buffer = new StringBuffer(  
                    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");  
            sb.append(buffer.charAt(r.nextInt(buffer.length() - 10)));  
            passLength -= 1;  
            break;  
        case 5:  
            String s = UUID.randomUUID().toString();  
            sb.append(s.substring(0, 8) + s.substring(9, 13)  
                    + s.substring(14, 18) + s.substring(19, 23)  
                    + s.substring(24));  
        }  
  
        if (type != 5)  
        {  
            int range = buffer.length();  
            for (int i = 0; i < passLength; ++i)  
            {  
                sb.append(buffer.charAt(r.nextInt(range)));  
            }  
        }  
        return sb.toString();  
    } 
	
	/**
	 * 列表转换为字符串（中间加入分隔符）
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String listToString(List<String> list, char separator) {  
		StringBuilder sb = new StringBuilder();   
		for (int i = 0; i < list.size(); i++) {  
		    sb.append(list.get(i));  
		    if (i < list.size() - 1) {  
		        sb.append(separator);  
		    }  
		}
		return sb.toString();
	}
	
	/**
	 * list数据去重
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	public static List removeDuplicate(@SuppressWarnings("rawtypes")List list) {
	    @SuppressWarnings({ "rawtypes" })
		HashSet h = new HashSet(list);
	    list.clear();
	    list.addAll(h);
	    return list;
	}
	
	public static void main(String[] args){
		//System.out.println(getCode(6, 0));
		System.out.println(isDouble("xxxx.3"));
		System.out.println(isDouble("3.3."));
		System.out.println(isDouble("3."));
		System.out.println(isDouble("."));
		System.out.println(isDouble(".3"));
		System.out.println(isDouble("03.1"));
	}
}
