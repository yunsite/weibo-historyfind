package com.taobao.zhangjun;

import java.util.Date;

public class DateUtil {
	
	public static final long CST_GMT_INTERVAL_TIME = 8L*60L*60L*1000L;
	public static final long DAY_TIME = 24L * 60L * 60L * 1000L;
	
	public static Date getDateBeginTime(Date date){
		//获取Date的GMT标准毫秒
		long currentMillSeconds = date.getTime();
		//加CST_GMT_INTERVAL_TIME转为CST时间,然后取模得到CST时区下与1970-01-01的天数间隔
		long dateBeginTime = ( currentMillSeconds + CST_GMT_INTERVAL_TIME ) / DAY_TIME;
		//* DAY_TIME得到毫秒数，-CST_GMT_INTERVAL_TIME 去除CST时区影响转化为GMT标准毫秒
		dateBeginTime = dateBeginTime * DAY_TIME - CST_GMT_INTERVAL_TIME ;
		//构造日期类型
		Date todayBeginDate = new Date(dateBeginTime);
		return todayBeginDate;
	}
	
	public static Date getDateBeginTime(Date date,int daysInterval){
		//获取Date的GMT标准毫秒
		long currentMillSeconds = date.getTime();
		//加CST_GMT_INTERVAL_TIME转为CST时间,然后取模得到CST时区下与1970-01-01的天数间隔
		long dateBeginTime = ( currentMillSeconds + CST_GMT_INTERVAL_TIME ) / DAY_TIME;
		//* DAY_TIME得到毫秒数，-CST_GMT_INTERVAL_TIME 去除CST时区影响转化为GMT标准毫秒
		dateBeginTime = ( dateBeginTime + daysInterval ) * DAY_TIME - CST_GMT_INTERVAL_TIME ;
		//构造日期类型
		Date todayBeginDate = new Date(dateBeginTime);
		return todayBeginDate;
	}
}
