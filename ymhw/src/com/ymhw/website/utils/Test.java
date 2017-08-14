package com.ymhw.website.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		dayLoop("2017-06-01", "2017-06-12");
	}
	
	public static List<Date> dayLoop(String start, String end) {
		List<Date> days = new ArrayList<Date>();
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		Date startDate = DateUtil.string2Date(start, DateUtil.FORMAT0);
		Date endDate = DateUtil.string2Date(end, DateUtil.FORMAT0);
		calendar1.setTime(startDate);
		calendar2.setTime(endDate);
		while(calendar1.compareTo(calendar2) < 0) {
			System.out.println(DateUtil.date2String(calendar1.getTime(), DateUtil.FORMAT0));
			days.add(calendar1.getTime());
			calendar1.add(Calendar.DAY_OF_YEAR, 1);
		}
		return days;
	}

}
