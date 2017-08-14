package com.ymhw.website.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jfinal.log.Log;

/** 
 * 日期工具类
 * @author      ws 
 * @since       1.0
 * create time：  2016年1月14日 下午5:21:01  
 * E-mail:      wangshuo@keenyoda.net
 */
public class DateUtil
{
	private static final Log log = Log.getLog(DateUtil.class);
	
	public static final String FORMAT0 = "yyyy-MM-dd";
	
	public static final String FORMAT1 = "yyyy-MM-dd HH:mm";
	
	public static final String FORMAT2 = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 日期转换为对应字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String date2String(Date date,String pattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/***
	 * 日期字符串转对应日期
	 * @param dateStr
	 * @return
	 */
	public static Date string2Date(String dateStr,String pattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try
		{
			return sdf.parse(dateStr);
		}
		catch (ParseException e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取当前日期字符串
	 * @param pattern
	 * @return
	 */
	public static String getNowStr(String pattern)
	{
		return new SimpleDateFormat(pattern).format(System.currentTimeMillis());
	}
	
	/**
	 * 获取当前日期
	 * @param pattern
	 * @return
	 */
	public static Date getNow()
	{
		return new Date();
	}
	
	/**
	  * 获取本周一日期
	  * @return
	  */
	 public static String getThisWeekStart(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
       Calendar cal = Calendar.getInstance();
       cal.add(Calendar.WEEK_OF_MONTH, -1);
       cal.add(Calendar.DATE, -1 * cal.get(Calendar.DAY_OF_WEEK) + 2);
		return sf.format(cal.getTime()) + " 00:00:00";
	 }
	 

	/**
	  * 获取本周日日期
	  * @return
	  */
	 public static String getThisWeekEnd(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
       Calendar cal = Calendar.getInstance();
       cal.add(Calendar.WEEK_OF_MONTH, -1);
       cal.add(Calendar.DATE, -1 * cal.get(Calendar.DAY_OF_WEEK) + 8);
		return sf.format(cal.getTime()) + " 23:59:59";
	 }
	 
	 /**
	  * 获取下周一日期
	  * @return
	  */
	 public static String getNextWeekStart(){
		 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	     Calendar cal = Calendar.getInstance();
	     cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	     return sf.format(cal.getTime()) + " 00:00:00";
	 }
	 

	/**
	  * 获取下周日日期
	  * @return
	  */
	 public static String getNextWeekEnd(){
		 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	     Calendar cal = Calendar.getInstance();
	     int week = cal.get(Calendar.DAY_OF_WEEK);
        if(week>1){
           cal.add(Calendar.DAY_OF_MONTH,-(week-1)+7);
        }else{
           cal.add(Calendar.DAY_OF_MONTH,1-week+7);
        }
	     return sf.format(cal.getTime()) + " 23:59:59";
	 }
	 
	 /**
	  * 获取本月第一天日期
	  * @return
	  */
	 public static String getThisMonthStart(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar cal = Calendar.getInstance();
		cal = Calendar.getInstance();
       cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
       cal.add(Calendar.MONTH, 1);
       cal.add(Calendar.DATE, -1);
       String lastDay = sf.format(cal.getTime());
       String aDay = "";
       if (!lastDay.equals(aDay))
		{
       	cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		}
		return sf.format(cal.getTime()) + " 00:00:00";
	 }
	 

	/**
	  * 获取本月最后 一天日期
	  * @return
	  */
	 public static String getThisMonthEnd(){
		 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		 Calendar cal = Calendar.getInstance();
		 cal = Calendar.getInstance();
	     cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
	     cal.add(Calendar.MONTH, 1);
	     cal.add(Calendar.DATE, -1);
	     String lastDay = sf.format(cal.getTime());
	     String aDay = "";
        int i = 1;
        while (!lastDay.equals(aDay)) {
           cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), i);
           aDay = sf.format(cal.getTime());
           i++;
	     }
		 return sf.format(cal.getTime()) + " 23:59:59";
	 }
	 
	 /**
	  * 获取下月第一天日期
	  * @return
	  */
	 public static String getNextMonthStart(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar cal = Calendar.getInstance();
		cal = Calendar.getInstance();
       cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
       cal.add(Calendar.MONTH, 1);
       cal.add(Calendar.DATE, 0);
       String lastDay = sf.format(cal.getTime());
       String aDay = "";
       if (!lastDay.equals(aDay))
		{
       	cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		}
		return sf.format(cal.getTime()) + " 00:00:00";
	 }
	 

	/**
	  * 获取下月最后一天日期
	  * @return
	  */
	 @SuppressWarnings("deprecation")
	public static String getNextMonthEnd(){
		 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		 Calendar cal = Calendar.getInstance();
		 cal = Calendar.getInstance();
	     cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
	     cal.add(Calendar.MONTH, 1);
	     cal.add(Calendar.DATE, 0);
	     
	     Calendar   cDay1 = Calendar.getInstance();  
	     cDay1.setTime(cal.getTime());  
	     final  int  last = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);  
	     Date lastDate = cDay1.getTime();  
	     lastDate.setDate(last);  
	     
		 return sf.format(lastDate) + " 23:59:59";
	 }
	 
	 /**
	  * 遍历某个日期范围
	  * @param start  如："2017-06-01"
	  * @param end	      如："2017-06-12"
	  * @return
	  */
	 public static List<Date> dayLoop(String start, String end) {
		return dayLoop(DateUtil.string2Date(start, DateUtil.FORMAT0), DateUtil.string2Date(end, DateUtil.FORMAT0));
	}
	 
	 /**
	  * 遍历某个日期范围
	  * @param start
	  * @param end
	  * @return
	  */
	 public static List<Date> dayLoop(Date start, Date end) {
		List<Date> days = new ArrayList<Date>();
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(start);
		calendar2.setTime(end);
		while(calendar1.compareTo(calendar2) < 0) {
//				System.out.println(DateUtil.date2String(calendar1.getTime(), DateUtil.FORMAT0));
			days.add(calendar1.getTime());
			calendar1.add(Calendar.DAY_OF_YEAR, 1);
		}
		return days;
	}

	
	public static void main(String[] args)
	{
		String s = "2016-01-14";
		System.out.println(string2Date(s, FORMAT0));
		System.out.println(date2String(new Date(), FORMAT1));
	}
}
