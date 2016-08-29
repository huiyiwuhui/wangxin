package com.yhfund.utils;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



/**
 * @Description
 * @author liuzhaoq
 * @date 2013-7-22
 */
public class DateUtil {
	private static Logger logger = Logger.getLogger(DateUtil.class);

	public static String YEARMMDD_FORMAT = "yyyy-MM-dd";
	public static String YEARMMDD_HHMMSS_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String YEARMMDDHHMMSS_FORMAT = "yyyyMMddHHmmss";
	public static String YEARMMDD_DATE_FORMAT = "yyyyMMdd";
	public static String HHMMSS_FORMAT = "HH:mm:ss";
	public static String CHINA_YEARMMDD = "yyyy年MM月dd日";
	public static SimpleDateFormat YEARMMDD_HHMMSS = new SimpleDateFormat(
			YEARMMDD_HHMMSS_FORMAT);
	public static SimpleDateFormat YEARMMDD = new SimpleDateFormat(
			YEARMMDD_FORMAT);
	public static SimpleDateFormat YEARMMDDHHMMSS = new SimpleDateFormat(
			YEARMMDDHHMMSS_FORMAT);

	public static SimpleDateFormat YEARMMDDATA = new SimpleDateFormat(
			YEARMMDD_DATE_FORMAT);

	public static SimpleDateFormat CHINESE_FORMAT = new SimpleDateFormat(
			"MM月dd日 kk点mm分");
	public static SimpleDateFormat CHINESE_FORMAT_YEAR = new SimpleDateFormat(
			"yyyy年MM月dd日 kk点mm分");
	public static final Calendar CALENDAR = Calendar.getInstance();

	public static synchronized Date createDate(final int year, final int month,
											   final int day, final int hour, final int minute, final int second,
											   final int millisecond) {
		CALENDAR.clear();
		CALENDAR.set(year, month - 1, day, hour, minute, second);
		CALENDAR.set(Calendar.MILLISECOND, millisecond);
		return CALENDAR.getTime();
	}

	public static synchronized Date createDate(final int year, final int month,
											   final int day) {
		createDate(year, month, day, 0, 0, 0, 0);
		return CALENDAR.getTime();
	}

	public static synchronized Date createDate(final int year, final int month,
											   final int day, final int hour) {
		createDate(year, month, day, hour, 0, 0, 0);
		return CALENDAR.getTime();
	}

	public static synchronized Date createDate(final int year, final int month,
											   final int day, final int hour, final int minute) {
		createDate(year, month, day, hour, minute, 0, 0);
		return CALENDAR.getTime();
	}

	public static synchronized Date createDate(final int year, final int month,
											   final int day, final int hour, final int minute, final int second) {
		createDate(year, month, day, hour, minute, second, 0);
		return CALENDAR.getTime();
	}

	/**
	 * 判断是否在两个时间段之间 比较时间格式 yyyy-MM-dd HH:mm:ss
	 *
	 * @param startTime
	 *            开始时间
	 * @param nowTime
	 *            比较时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public static boolean isExchange(String startTime, String nowTime,
									 String endTime) {
		if (startTime == null || endTime == null)
			return false;
		Date startDate = null;
		Date endDate = null;
		Date nowDate = null;
		try {
			startDate = YEARMMDD_HHMMSS.parse(startTime);
			endDate = YEARMMDD_HHMMSS.parse(endTime);
			if (nowTime == null || "".equals(nowTime)) {
				nowDate = getNowDate();
			} else {
				nowDate = YEARMMDD_HHMMSS.parse(nowTime);
			}
		} catch (Exception e) {
		}
		return nowDate.after(startDate) && nowDate.before(endDate);
	}

	/**
	 * 判断当前时间在两个时间之内 与当前时间相比
	 *
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public static boolean isExchange(String startTime, String endTime) {
		return isExchange(startTime, null, endTime);
	}

	/**
	 * 判断时间是否是当天
	 *
	 * @param startTime
	 * @return
	 */
	public static boolean isEqual(String startTime, String nowTime) {
		Date startDate = null;
		Date nowDate = null;
		try {
			startDate = YEARMMDD.parse(startTime);
			if (nowTime == null || "".equals(nowTime)) {
				nowDate = getNowDateShort();
			} else {
				nowDate = YEARMMDD.parse(nowTime);
			}
		} catch (Exception e) {
		}
		return startDate.compareTo(nowDate) == 0 ? true : false;
	}

	public static boolean isEqual(String startTime) {
		return isEqual(startTime, null);
	}

	/**
	 * 判断是否是同一天
	 *
	 * @param startTime
	 *            yyyy-MM-dd
	 * @param endTime
	 *            yyyy-MM-dd
	 * @return
	 */
	public static boolean isSameToday(Date startTime, Date endTime) {
		if (startTime == null)
			return false;
		try {
			String startTimeStr = YEARMMDD.format(startTime);
			String endTimeStr = "";
			if (endTime == null) {
				endTimeStr = getNowStrShort();
			} else {
				endTimeStr = YEARMMDD.format(endTime);
			}
			return startTimeStr.equals(endTimeStr) ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isSameToday(Date startTime) {
		return isSameToday(startTime, null);
	}

	public static String chineseMD() {
		Date now = new Date();
		return CHINESE_FORMAT.format(now);
	}

	public static String chineseYMD() {
		Date now = new Date();
		return CHINESE_FORMAT_YEAR.format(now);
	}

	/**
	 * 获取现在时间
	 *
	 */
	public static Date getNowDateShort() {
		Date currentTime = new Date();
		String dateString = YEARMMDD.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = YEARMMDD.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 *
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static String getNowStrShort() {
		Date currentTime = new Date();
		String dateString = YEARMMDD.format(currentTime);
		return dateString;
	}

	/**
	 * 获取当日
	 *
	 * @return 返回时间格式yyyyMMdd
	 */
	public static String getToday() {
		Date currentTime = new Date();
		String dateString = YEARMMDDATA.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 *
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate() {
		Date currentTime = new Date();
		return currentTime;
	}

	public static Date getDate(String timeStr) {
		try {
			return YEARMMDDHHMMSS.parse(timeStr);
		} catch (Exception e) {
		}
		return new Date();
	}

	/**
	 * 获取当前时间
	 *
	 * @return返回短时间格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowStrDate() {
		Date currentTime = new Date();
		String dateString = YEARMMDD_HHMMSS.format(currentTime);
		return dateString;
	}

	/**
	 * 获取当前时间
	 *
	 * @return返回短时间格式 yyyyMMddHH
	 */
	public static String getNowTooSortDate() {
		Date currentTime = new Date();
		String dateString = YEARMMDDATA.format(currentTime);
		return dateString;
	}

	/**
	 * 获取当前时间
	 *
	 * @return返回短时间格式 yyyyMMddHHmmss
	 */
	public static String getNowYDate() {
		Date currentTime = new Date();
		String dateString = YEARMMDDHHMMSS.format(currentTime);
		return dateString;
	}

	/**
	 * 获取当前的小时
	 *
	 * @return
	 */
	public static int getNowHour() {
		Calendar ca = Calendar.getInstance();
		int hour = ca.get(Calendar.HOUR_OF_DAY);// 小时
		return hour;
	}

	/**
	 * 获取当前的月份中的天数
	 *
	 * @return
	 */
	public static int getNowDayOfMonth() {
		Calendar ca = Calendar.getInstance();
		int hour = ca.get(Calendar.DAY_OF_MONTH);// 小时
		return hour;
	}

	/**
	 * 获取当前的月份
	 *
	 * @return
	 */
	public static int getNowMonth() {
		Calendar ca = Calendar.getInstance();
		int hour = ca.get(Calendar.MONTH);// 月份
		return hour+1;
	}

	/**
	 * 获取当前的年份
	 *
	 * @return
	 */
	public static int getNowYear() {
		Calendar ca = Calendar.getInstance();
		int hour = ca.get(Calendar.YEAR);// 年份
		return hour;
	}
	/**
	 * 获取当前的年的第几周
	 *
	 * @return
	 */
	public static int getNowWeek() {
		Calendar ca = Calendar.getInstance();
		int week = ca.get(Calendar.WEEK_OF_YEAR);// 年中第几周
		return week;
	}

	/**
	 * 得到本日的前几个月时间 如果number=2当日为2007-9-1,那么获得2007-7-1
	 */
	public static Date getDateBeforeMonth(int number) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -number);
		return cal.getTime();
	}

	/**
	 * 根据格式得到格式化后的日期
	 *
	 * @param currDate
	 *                要格式化的日期
	 * @param format
	 *                日期格式，如yyyy-MM-dd
	 * @see java.text.SimpleDateFormat#parse(java.lang.String)
	 * @return Date 返回格式化后的日期，格式由参数<code>format</code>
	 *         定义，如yyyy-MM-dd，如2006-02-15
	 */
	public static Date getFormatDate(String currDate, String format) {
		if (currDate == null) {
			return null;
		}
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.parse(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(YEARMMDD_FORMAT);
			try {
				return dtFormatdB.parse(currDate);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	/**
	 *从20150121（String）到2015-01-21（String）
	 * @param currDate
	 * @return
	 */
	public static String getFormatDate(String currDate){
		Date d = DateUtil.getFormatDate(currDate,DateUtil.YEARMMDD_DATE_FORMAT);
		return DateUtil.getFormatDateTime(d, DateUtil.YEARMMDD_FORMAT);
	}

	/**
	 * 根据格式得到格式化后的时间
	 *
	 * @param currDate
	 *                要格式化的时间
	 * @param format
	 *                时间格式，如yyyy-MM-dd HH:mm:ss
	 * @see java.text.SimpleDateFormat#format(java.util.Date)
	 * @return String 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd
	 *         HH:mm:ss
	 */
	public static String getFormatDateTime(java.util.Date currDate, String format) {
		if (currDate == null) {
			return "";
		}
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.format(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(YEARMMDD_HHMMSS_FORMAT);
			try {
				return dtFormatdB.format(currDate);
			} catch (Exception ex) {
			}
		}
		return "";
	}

	/**
	 * 得到格式化后的日，格式为yyyyMMdd，如20060210
	 *
	 * @param currDate
	 *                要格式化的日期
	 * @return String 返回格式化后的日，格式为yyyyMMdd，如20060210
	 */
	public static String getFormatDay(java.util.Date currDate) {
		return getFormatDateTime(currDate, YEARMMDD_DATE_FORMAT);
	}

	/**
	 * 得到几天前的时间
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d,int day){
		Calendar now =Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d,int day){
		Calendar now =Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
		return now.getTime();
	}

	/**
	 *  根据星期计算日期值  定投计算日期
	 * @param beginDate
	 * @param weekday
	 * @return
	 * @throws Exception
	 */
	public static String  getDateByWeekDay(String nextWorkDay,String beginDate,int weekday) throws Exception
	{
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		int hour=cal.get(Calendar.HOUR_OF_DAY);
		String _date=format.format(cal.getTime());
		String date="";
		boolean isWeekDay=false;
		//选择开始日期为本月
		if(beginDate.compareToIgnoreCase(_date.substring(0,6))<=0)
		{
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			//当天为周六
			if(dayOfWeek==7)
			{
				cal.add(Calendar.DATE,2);
				isWeekDay=true;
			}
			//当天为周末
			if(dayOfWeek==1)
			{
				cal.add(Calendar.DATE,1);
				isWeekDay=true;
			}

			dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			if(dayOfWeek>=weekday)
			{
				cal.add(Calendar.DATE, 1*7);
				cal.set(Calendar.DAY_OF_WEEK,weekday);
				int d=cal.get(Calendar.DATE);
				if(d>28)
				{
					cal.add(Calendar.DATE, 1*7);
				}
			}
			else
			{
				cal.add(Calendar.DATE, 0*7);
				cal.set(Calendar.DAY_OF_WEEK,weekday);
				int d=cal.get(Calendar.DATE);
				if(d>28)
				{
					cal.add(Calendar.DATE, 1*7);
				}
				else
				{
					if((hour>=15&&!isWeekDay))
					{
						_date=format.format(cal.getTime());
						//下一个交易日和当前选择日期做比较
						if(_date.compareToIgnoreCase(nextWorkDay)<0)
						{
							cal.add(Calendar.DATE, 1*7);
						}
					}
				}
			}
			//想周几，这里就传几Calendar.MONDAY（TUESDAY...）
			date =format.format(cal.getTime());
			return date;
		}
		else  //如果当前年月和系统年月不一致
		{
			int year =Integer.valueOf(beginDate.substring(0,4));
			int month= Integer.valueOf(beginDate.substring(4,6));
			cal.set(year,month-1,1);
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			//当天为周六
			if(dayOfWeek==7)
			{
				cal.add(Calendar.DATE,2);
			}
			//当天为周末
			if(dayOfWeek==1)
			{
				cal.add(Calendar.DATE,1);
			}
			dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			if(dayOfWeek>weekday)
			{
				cal.add(Calendar.DATE, 1*7);
			}
			else
			{
				cal.add(Calendar.DATE, 0*7);
			}
			//想周几，这里就传几Calendar.MONDAY（TUESDAY...）
			cal.set(Calendar.DAY_OF_WEEK,weekday);
			date =format.format(cal.getTime());
			return date;
		}
	}

	/**
	 * 判断当前日期是星期几
	 *
	 * @param pTime
	 *            修要判断的时间
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	public static int dayForWeek(String pTime){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	public static void main(String[] args) {
		Date date = new Date(1386128197);
		System.out.println(DateUtil.getFormatDateTime(date, DateUtil.YEARMMDD_HHMMSS_FORMAT));
	}
}
