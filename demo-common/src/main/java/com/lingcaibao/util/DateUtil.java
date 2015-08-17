package com.lingcaibao.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
/**
 * <p>标题：日期工具类 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月30日 下午3:44:15</p>
 * <p>类全名：com.lingcaibao.task.util.DateUtil</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class DateUtil
{
	/**
	 *  从一个<code>java.util.Date</code>对象clone一个相同类型的对象.
	 *  目前只考虑  java.util.Date, java.sql.Date , java.sql.Timestamp 三中类型
	 *  @param   date  被clone的 <code>java.util.Date</code>对象
	 *  @return  clone的结果
	 */
	public static java.util.Date cloneDate(java.util.Date date)
	{
		return (java.util.Date) (date.clone());
	}

	final private static Calendar	staticCal	= newGregorianCalendar();	;

	static Calendar newGregorianCalendar()
	{
		final int hOffset = TimeZone.getDefault().getRawOffset() / (60 * 60 * 1000);
		return Calendar.getInstance(TimeZone.getTimeZone("GMT" + (hOffset > 0 ? "+" + hOffset : "" + hOffset) + ":00"));
	}

	/**
	 *  从一个<code>java.util.Date</code>对象得到一个表示该日期的临时<code>Calendar</code>对象.
	 *  该对象只作为临时使用。
	 *  @param  date <code>java.util.Date</code>对象
	 *  @return 表示 date 的 <code>Calendar</code>对象.
	 */
	public static Calendar getStaticCalendars(java.util.Date date)
	{
		if (date != null)
			staticCal.setTime(date);
		else
			staticCal.setTimeInMillis(System.currentTimeMillis()); // 2011-3-26 : 加
		return staticCal;
	}

	/**
	 * 从一个<code>java.util.Date</code>对象得到一个表示该日期的年份
	 *  @param  date <code>java.util.Date</code>对象,从中取年份
	 *  @return  日期 date 表示的年份
	 */
	public static int getDateYear(java.util.Date date)
	{
		if (date == null)
			return 0;
		synchronized (staticCal)
		{
			return getStaticCalendars(date).get(Calendar.YEAR); // 线程共享时 可能有问题
		}
	}

	/**
	 @return  当前日期的年
	*/
	public static int getYear()
	{
		return (new GregorianCalendar()).get(Calendar.YEAR);
	}

	/**
	 @return  当前日期的月
	*/
	public static int getMonth()
	{
		return (new GregorianCalendar()).get(Calendar.MONTH) + 1;
	}

	/**
	 @return  当前日期的日
	*/
	public static int getDay()
	{
		return (new GregorianCalendar()).get(Calendar.DATE);
	}

	/**
	 * 从一个<code>java.util.Date</code>对象得到一个表示该日期的月份
	 *  @param  date <code>java.util.Date</code>对象,从中取月份
	 *  @return  日期 date 表示的月份
	 */
	public static int getDateMonth(java.util.Date date)
	{
		if (date == null)
			return 0;
		synchronized (staticCal)
		{
			return getStaticCalendars(date).get(Calendar.MONTH) + 1;
		}
	}

	/**
	 * 从一个<code>java.util.Date</code>对象得到一个表示该日期的日
	 *  @param  date <code>java.util.Date</code>对象,从中取日
	 *  @return  日期 date 表示的日, 同 date.getDate 
	 */
	public static int getDateDay(java.util.Date date)
	{
		if (date == null)
			return 0;
		synchronized (staticCal)
		{
			return getStaticCalendars(date).get(Calendar.DATE);
		}
	}

	public static int getDateHours(java.util.Date date)
	{
		if (date == null)
			return 0;
		synchronized (staticCal)
		{
			return getStaticCalendars(date).get(Calendar.HOUR_OF_DAY);
		}
	}

	public static int getDateMinutes(java.util.Date date)
	{
		if (date == null)
			return 0;
		synchronized (staticCal)
		{
			return getStaticCalendars(date).get(Calendar.MINUTE);
		}
	}

	public static int getDateSeconds(java.util.Date date)
	{
		if (date == null)
			return 0;
		synchronized (staticCal)
		{
			return getStaticCalendars(date).get(Calendar.SECOND);
		}
	}

	/**
	 *  @return  date.getDay()
	 *   周1 ： turn 1,
	 *     2 : return 0,
	 */
	public static int getWeekDay(java.util.Date date)
	{
		if (date == null)
			return 0;
		synchronized (staticCal)
		{
			return getStaticCalendars(date).get(Calendar.DAY_OF_WEEK) - 1;
		}
	}

	/*
	  从 fromDate 日开始， date 所在 周，（fromDate 当前算 第 0 周 )
	 */
	public static int indexOfWeek(java.util.Date date, java.util.Date fromDate)
	{
		int days = DateUtil.diffDate(date, fromDate);
		int w0 = getWeekDay(fromDate);
		if (days >= 0)
		{
			if (w0 == 0)
				return days / 7;
			else
				return days < 7 - w0 ? 0 : 1 + (days - (7 - w0)) / 7; //days<7-w0 ? 0 :
		} else
		{
			if (w0 == 0)
				return -1 - (-days - 1) / 7;
			else
				return -days <= w0 ? 0 : -1 - (-days - 1 - w0) / 7; //days<7-w0 ? 0 :
		}
	}

	public static int indexOfYearWeek(java.util.Date date)
	{
		int year = DateUtil.getDateYear(date);
		return indexOfWeek(date, DateUtil.toDate(year, 1, 1));
	}

	/**
	 *  得到一个静态的 Calendar 临时对象
	 *  @return  一个静态的 Calendar 临时对象
	 */
	public static Calendar getStaticCalendars()
	{
		return getStaticCalendars(null);
	}

	/**
	 *  得到一个静态的 给定日期和时间的 Calendar 临时对象,
	 *  @param   time  给定Calendar 临时对象表示的日期和时间
	 *  @return  一个静态的给定日期和时间(long time) Calendar 临时对象
	 */
	public static Calendar getStaticCalendars(long time)
	{
		synchronized (staticCal)
		{
			staticCal.setTimeInMillis(time);
			return staticCal;
		}
	}

	/**
	从年月日得到一个Date对象
	*/
	public static java.util.Date toDate(int year, int month, int day)
	{
		synchronized (staticCal)
		{
			staticCal.clear();
			staticCal.set(Calendar.YEAR, year);
			staticCal.set(Calendar.MONTH, month - 1);
			staticCal.set(Calendar.DAY_OF_MONTH, day); // day-1??
			staticCal.set(Calendar.HOUR_OF_DAY, 0);
			staticCal.set(Calendar.MINUTE, 0);
			staticCal.set(Calendar.SECOND, 0);
			return staticCal.getTime();//.getTime();
		}
	}

	/**
	从表示日期的字符串("2000-9-7", "2002-6-1 14:15" , "2002-6-1 14:15:30")得到一个Date对象
	*/
	public static java.util.Date textToDate(String text)
	{
		if (text == null)
			return null;
		text = text.trim();
		if (text.length() == 0)
			return null;
		int r[] = new int[6];
		if (!DateUtil.parseDateTime(text, r))
			return null;
		return DateUtil.toDate(r[0], r[1], r[2], r[3], r[4], r[5]);
	}

	/**
	 @param  value  java.util.Date 或 String("2000-9-7", "2002-6-1 14:15" , "2002-6-1 14:15:30")
	    得到一个Date对象
	*/
	public static java.util.Date toDate(Object value)
	{
		if (value instanceof String)
			return textToDate((String) value);
		if (value instanceof java.util.Date)
			return (java.util.Date) value;
		if (value instanceof Long)
			return new java.util.Date(((Long) value).longValue());
		return null;
	}

	public static java.util.Date toDate(long date)
	{
		return new java.util.Date(date);
	}

	/**
	 *  从给定的 year,mongth,day 得到时间的long值表示(a point in time that is
	 *  <tt>time</tt> milliseconds after January 1, 1970 00:00:00 GMT).
	 * @param  year  年
	 * @param  month  月
	 * @param  day   日
	 * @return   给定的 year,mongth,day 得到时间的long值表示
	 */
	public static long toLongTime(int year, int month, int day)
	{
		return toLongTime(year, month, day, 0, 0, 0);
	}

	public static long toLongTime(int year, int month, int day, int hour, int min, int sec)
	{
		synchronized (staticCal)
		{
			staticCal.clear();
			staticCal.set(Calendar.YEAR, year);
			staticCal.set(Calendar.MONTH, month - 1);
			staticCal.set(Calendar.DAY_OF_MONTH, day); // day-1??
			staticCal.set(Calendar.HOUR_OF_DAY, hour);
			staticCal.set(Calendar.MINUTE, min);
			staticCal.set(Calendar.SECOND, sec);
			return staticCal.getTimeInMillis();//getTime().getTime();
		}
	}

	public static long toLongTime(int year, int month, int day, int hour, int min, int sec, int millSec)
	{
		synchronized (staticCal)
		{
			staticCal.clear();
			staticCal.set(Calendar.YEAR, year);
			staticCal.set(Calendar.MONTH, month - 1);
			staticCal.set(Calendar.DAY_OF_MONTH, day); // day-1??
			staticCal.set(Calendar.HOUR_OF_DAY, hour);
			staticCal.set(Calendar.MINUTE, min);
			staticCal.set(Calendar.SECOND, sec);
			staticCal.set(Calendar.MILLISECOND, millSec);
			return staticCal.getTimeInMillis();//getTime().getTime();
		}
	}

	public static java.util.Date toDate(int year, int month, int day, int hour, int min, int sec)
	{
		return new java.util.Date(toLongTime(year, month, day, hour, min, sec));
	}

	public static int[] getDateElements(java.util.Date date)
	{
		if (date == null)
			return null;
		synchronized (staticCal)
		{
			Calendar cal = getStaticCalendars(date);
			int ymd[] = new int[7];
			ymd[0] = cal.get(Calendar.YEAR);
			ymd[1] = cal.get(Calendar.MONTH) + 1;
			ymd[2] = cal.get(Calendar.DATE);
			ymd[3] = cal.get(Calendar.HOUR_OF_DAY);
			ymd[4] = cal.get(Calendar.MINUTE);
			ymd[5] = cal.get(Calendar.SECOND);
			ymd[6] = cal.get(Calendar.MILLISECOND);
			return ymd;
		}
	}

	public static int[] getDateElements(long date)
	{
		synchronized (staticCal)
		{
			Calendar cal = getStaticCalendars(date);
			int ymd[] = new int[7];
			ymd[0] = cal.get(Calendar.YEAR);
			ymd[1] = cal.get(Calendar.MONTH) + 1;
			ymd[2] = cal.get(Calendar.DATE);
			ymd[3] = cal.get(Calendar.HOUR_OF_DAY);
			ymd[4] = cal.get(Calendar.MINUTE);
			ymd[5] = cal.get(Calendar.SECOND);
			ymd[6] = cal.get(Calendar.MILLISECOND);
			return ymd;
		}
	}

	public static long time0ForToday(long today)
	{
		java.util.Calendar date = newGregorianCalendar();//new java.util.GregorianCalendar();
		date.setTime(new java.util.Date(today));
		date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date.getTime().getTime();
	}

	public static long getTime0(java.util.Date date)
	{
		synchronized (staticCal)
		{
			java.util.Calendar cal = getStaticCalendars(date);//newGregorianCalendar();//new java.util.GregorianCalendar();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTime().getTime();
		}
	}

	/**
	 * 当天日期(不含时分秒)
	*/
	public static java.util.Date today0()
	{
		java.util.Calendar date = newGregorianCalendar();
		date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date.getTime();
	}

	/**
	 *  提供
	 */
	public static java.util.Date today()
	{
		return new java.util.Date();
	}

	public static long todayTime()
	{
		return System.currentTimeMillis();
	}

	public static long getDateTime(Object date)
	{
		return date instanceof java.util.Date ? ((java.util.Date) date).getTime() : 0;
	}

	static public long	deltaFromServer	= Long.MIN_VALUE;

	/**
	 *  求给定 某年、某月的最大天数.例如getDaysOfMonth(2000,1)范围31,getDaysOfMonth(2000,2)返回28
	 * @param   year  年
	 * @param   month  月
	 * @return  给定年、月的最大天数(1月返回31,2月返回28或29,3月返回31,...,12月返回31)
	 */
	static public int getDaysOfMonth(int year, int month)
	{
		return (int) ((DateUtil.toLongTime(month == 12 ? (year + 1) : year, month == 12 ? 1 : (month + 1), 1) - DateUtil.toLongTime(year, month, 1)) / (24 * 60 * 60 * 1000));
	}

	static public int getDefaultHolidays(int year, int month)
	{
		GregorianCalendar cal = new GregorianCalendar(year, month - 1, 1);
		//System.out.println("cal="+cal.);
		int x = 0;
		for (int d = 0;; d++)
		{
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY)
				x |= (1 << d);
			//System.out.println("d="+(d+1)+",dayOfWeek="+dayOfWeek);
			cal.add(Calendar.DAY_OF_YEAR, 1);
			if (cal.get(Calendar.MONTH) + 1 != month)
				break;
		}
		return x;
	}

	/**
	求某一日期加或减(day为负数)后的日期
	*/
	static public java.util.Date incDate(java.util.Date date, int day)
	{
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, day);
		return cal.getTime();
	}

	static public java.util.Date incMonth(java.util.Date date, int nMonth, int options)
	{
		if (date == null || nMonth == 0)
			return date;
		int e[] = DateUtil.getDateElements(date);
		e[1] += nMonth;
		for (; e[1] > 12;)
		{
			e[1] -= 12;
			e[0]++;
		}
		for (; e[1] <= 0;)
		{
			e[1] += 12;
			e[0]--;
		}
		if ((options & 1) != 0)
		{
			int days = DateUtil.getDaysOfMonth(e[0], e[1]);
			if (e[2] > days)
				e[2] = days;
		}
		return new java.util.Date(toLongTime(e[0], e[1], e[2], e[3], e[4], e[5], e[6]));
	}

	/**
	求某一日期加或减(day为负数)后的日期
	*/
	static public void incYearMonthDay(int ymd[], int idx0, int day)
	{
		java.util.Date date = incDate(toDate(ymd[idx0 + 0], ymd[idx0 + 1], ymd[idx0 + 2]), day);
		ymd[idx0 + 0] = getDateYear(date);
		ymd[idx0 + 1] = getDateMonth(date);
		ymd[idx0 + 2] = getDateDay(date);
	}

	/**
	 *  求两个日期之间相差的天数(日期相减)
	 *   @param  date1  相减的第一的日期
	 *   @param  date2  相减的第二的日期
	 *   @return date1 与 date2 之间相差的天数
	 */
	static public int diffDate(Calendar date1, Calendar date2)
	{
		return (int) ((toLongTime(date1.get(Calendar.YEAR), date1.get(Calendar.MONTH) + 1, date1.get(Calendar.DATE)) - toLongTime(date2.get(Calendar.YEAR), date2.get(Calendar.MONTH) + 1,
				date2.get(Calendar.DATE))) / (24 * 60 * 60 * 1000));
	}

	static public int diffDate(java.util.Date date1, java.util.Date date2)
	{
		return (int) ((date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000));
	}

	static public long diffSeconds(java.util.Date date1, java.util.Date date2)
	{
		return (date1.getTime() - date2.getTime()) / 1000l;
	}

	static public final String calendarToString(final java.util.Calendar cal, int hmsFormat)
	{
		final int options = hmsFormat >> 4;
		hmsFormat &= 0xf;
		int year = cal.get(Calendar.YEAR);//((java.sql.Timestamp)value).getYear() + 1900;
		int month = cal.get(Calendar.MONTH) + 1;//((java.sql.Timestamp)value).getMonth() + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);//((java.sql.Timestamp)value).getDate();
		String yearText = Integer.toString(year);
		if ((options & 1) != 0 && yearText.length() > 2)
			yearText = yearText.substring(yearText.length() - 2);
		//if( (options&1)!=0 ) year
		String text = yearText + (month < 10 ? "-0" : "-") + month + (day < 10 ? "-0" : "-") + day;
		if (hmsFormat == 2)//|| date instanceof java.sql.Date )
			return text;
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		if (hmsFormat == 4)
			return text + " " + toStr2(hour) + ":" + toStr2(min);
		int sec = cal.get(Calendar.SECOND);
		//System.err.println("date="+date+",cal="+cal+",cal.getTime()="+cal.getTime()+",hour="+hour);//+","+date.getHours());
		if (hmsFormat == 1)
			return text + " " + toStr2(hour) + ":" + toStr2(min) + ":" + toStr2(sec);
		if (hmsFormat == 5)
			return text + " " + toStr2(hour) + ":" + toStr2(min) + ":" + toStr2(sec) + "." + cal.get(Calendar.MILLISECOND);
		if (hour == 0 && min == 0 && sec == 0)
			return text;
		return sec == 0 ? text + " " + toStr2(hour) + ":" + toStr2(min) : text + " " + toStr2(hour) + ":" + toStr2(min) + ":" + toStr2(sec);
	}

	/**
	 *	得到一个日期对象的字符串表示(yyyy-mm-dd hh:mm:ss).
	 * 当时、分、秒都为0时，表示的时间字符串中只含年、月、日形式(yyyy-mm-dd)
	 * @param  date 日期对象
	 * @param  hmsFormat  0 :
	 *                    1 ： 总是   yyyy-mm-dd hh:mm:ss 格式
	 *                    2 :  只含年、月、日形式, 无论
	 *                    4 :  yyyy-mm-dd hh:mm
	 *                    5 ： 总是   yyyy-mm-dd hh:mm:ss.ms 格式(带毫秒)
	 *                    
	 *                 0x1X :  yyyy 两位
	 * @return  date的字符串表示
	 */
	static public final String dateToString(java.util.Date date, int hmsFormat)
	{
		//  if( date==null )  // 2011-3-26 : date==null 返回 null
		// 	return null;
		//  date = new java.util.Date( date.getTime() );
		synchronized (staticCal)
		{
			//if( date instanceof java.sql.Date )
			//return calendarToString(DateUtil.getStaticCalendars(date),hmsFormat);//(java.sql.Timestamp)value;
			if (hmsFormat == 0 && date instanceof java.sql.Date)
				hmsFormat = 2;
			return calendarToString(DateUtil.getStaticCalendars(date), hmsFormat);
			//java.sql.Date d;
			//System.err.println("date="+date+",cal="+cal);
			//System.err.println(date.getTime()+","+cal.getTime().getTime()+","+new java.util.Date(date.getTime()));
			//Mon Apr 28 00:00:00 CST 2003
			//date.getYear();
			/*
			        int year = date.getYear()+1900;//cal.get(Calendar.YEAR);//((java.sql.Timestamp)value).getYear() + 1900;
			        int month = date.getMonth()+1;// cal.get(Calendar.MONTH)+1;//((java.sql.Timestamp)value).getMonth() + 1;
			        int day = date.getDate();// cal.get(Calendar.DAY_OF_MONTH);//((java.sql.Timestamp)value).getDate();
			*/
		}
		//	|| ( && ==0 && ==0) )
		//	return  date.toString();
	}

	/*
	static public final String calendarToString(java.util.Calendar cal)
	{
		return calendarToString(cal,0);
	}
	static public final String calendarToString(java.util.Calendar cal, int hmsFormat)
	{
	    final int options = hmsFormat>>4;
	    hmsFormat &= 0xf;
	    int year = cal.get(Calendar.YEAR);//((java.sql.Timestamp)value).getYear() + 1900;
	    int month =  cal.get(Calendar.MONTH)+1;//((java.sql.Timestamp)value).getMonth() + 1;
	    int day =  cal.get(Calendar.DAY_OF_MONTH);//((java.sql.Timestamp)value).getDate();
	    String yearText = Integer.toString(year);
	    if( (options&1)!=0 && yearText.length()>2 ) yearText = yearText.substring(yearText.length()-2);
	    //if( (options&1)!=0 ) year
	    String text = yearText+(month<10?"-0":"-")+month+(day<10?"-0":"-")+day;
	    if( hmsFormat==2 || date instanceof java.sql.Date )
	        return text;
	    int hour = cal.get(Calendar.HOUR_OF_DAY);
	    int min  = cal.get(Calendar.MINUTE);
	    if( hmsFormat==4 )
	        return text + " " + toStr2(hour)+":"+toStr2(min);
	    int sec  = cal.get(Calendar.SECOND);
	//System.err.println("date="+date+",cal="+cal+",cal.getTime()="+cal.getTime()+",hour="+hour);//+","+date.getHours());
	    if( hmsFormat==1 )
	        return text + " " + toStr2(hour)+":"+toStr2(min)+":"+toStr2(sec);
	    if( hour==0 && min==0 && sec==0 )
	        return text;
	    return sec==0 ?  text + " " + toStr2(hour)+":"+toStr2(min)
	               :  text + " " + toStr2(hour)+":"+toStr2(min)+":"+toStr2(sec)
	               ;
	}
	*/
	static public final String dateToString(long date, int hmsFormat)
	{
		return dateToString(new java.util.Date(date), hmsFormat);
	}

	static public final String dateToString(long date)
	{
		return dateToString(new java.util.Date(date));
	}

	final static private String toStr2(int x)
	{
		return x < 10 ? "0" + x : "" + x;
	}

	static public String dateToEnglishString(java.util.Date date)
	{
		/*
		if (date==null) return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int m = calendar.get(Calendar.MONTH);
		return "JanFebMarAprMayJunJulAugSepOctNovDec".substring(m*3,m*3+3)+'-'+calendar.get(Calendar.DATE)+'-'+calendar.get(Calendar.YEAR);
		*/
		return dateToEnglishString(date, true, '-', '-');
	}

	static public String dateToEnglishString(java.util.Date date, boolean shortMonth, char deliMD, char deliDY)
	{
		if (date == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int m = calendar.get(Calendar.MONTH);
		String month;
		if (shortMonth)
			month = "JanFebMarAprMayJunJulAugSepOctNovDec".substring(m * 3, m * 3 + 3);
		else
		{
			String emonth[] = new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
			month = emonth[m];
		}
		return month + deliMD + calendar.get(Calendar.DATE) + deliDY + calendar.get(Calendar.YEAR);
	}

	/**
	 * @param time
	 * @param fmt
	 */
	static public String secondsToString(int seconds)
	{
		int h = seconds / (60 * 60);
		int m = seconds / 60 % 60;
		int s = seconds % 60;
		return toStr2(h) + ":" + toStr2(m) + (s == 0 ? "" : ":" + toStr2(s));
		//(h<10?"0"+h:""+h) + ":"+ (m<10?"0"+m:""+m)+(s==0?"":":" + (s<10?"0"+s:""+s));
	}

	/*
	static public final String dateToString(java.util.Date date,boolean forceHaveHour)
	{
	Calendar cal = Utilities.getStaticCalendars(date);//(java.sql.Timestamp)value;
	int year = cal.get(Calendar.YEAR);//((java.sql.Timestamp)value).getYear() + 1900;
	int month = cal.get(Calendar.MONTH)+1;//((java.sql.Timestamp)value).getMonth() + 1;
	int day = cal.get(Calendar.DAY_OF_MONTH);//((java.sql.Timestamp)value).getDate();
	String ymd = ""+year+(month<10?"-0":"-")+month+(day<10?"-0":"-")+day;
	if( !forceHaveHour && (cal.get(Calendar.HOUR_OF_DAY)==0 && cal.get(Calendar.MINUTE)==0 && cal.get(Calendar.SECOND)==0) )
	{
	 return ymd;
	}
	int hour = cal.get(Calendar.HOUR);
	int min = cal.get(Calendar.MINUTE);
	int sec = cal.get(Calendar.SECOND);
	return  ymd+" "+(hour<10?"0":"")+hour+(min<10?":0":":")+min+(sec<10?":0":":")+sec;
	}
	*/
	/**
	 * 日期类型转换成字符串表示(yyyy-mm-dd hh:mm:ss)
	 * @param date
	 * @return 日期的字符串表示
	 */
	static public final String dateToString(java.util.Date date)
	{
		return dateToString(date, 0);
	}

	private static boolean isDigites(String text)
	{
		final int n = text.length();
		for (int i = 0; i < n; i++)
		{
			char c = text.charAt(i);
			if (c < '0' || c > '9')
				return false;
		}
		return true;
	}

	public final static boolean parseDate(String text, int ret[])
	{
		return parseDate(text, ret, true);
	}

	public final static boolean parseDate(String text, int ret[], boolean caseSize8)
	{
		int tmp[] = new int[6];
		if (caseSize8 && text.length() == 8 && isDigites(text))
		{
			tmp[0] = Integer.parseInt(text.substring(0, 4));
			tmp[1] = Integer.parseInt(text.substring(4, 6));
			tmp[2] = Integer.parseInt(text.substring(6, 8));
		} else
		{
			if (!parseDateTime(text + " 0:0:0", tmp))
				return false;
		}
		if (ret != null)
			for (int i = 0; i < 3; i++)
				ret[i] = tmp[i];
		return true;
	}

	public static java.util.Date setDayEndTime(java.util.Date date)
	{
		if (date == null)
			return null;
		int a[] = DateUtil.getDateElements(date);
		return DateUtil.toDate(a[0], a[1], a[2], 23, 59, 59);
	}

	public final static java.util.Date[] parseDateRange(String text, java.util.Date today, boolean caseTime)
	{
		if (text == null || (text = text.trim().toLowerCase()).length() == 0)
			return null;
		int p = text.indexOf("..");
		if (p >= 0)
		{
			String s1 = text.substring(0, p);
			java.util.Date date1 = DateUtil.toDate(s1);
			if (date1 == null && s1.trim().length() > 0)
				return null;
			String s2 = text.substring(p + 2);
			java.util.Date date2 = DateUtil.toDate(s2);
			if (date2 == null && s2.trim().length() > 0)
				return null;
			//if( date2!=)
			return date1 != null || date2 != null ? new java.util.Date[] { date1, caseTime ? setDayEndTime(date2) : date2 } : null;
		}
		if (text.charAt(0) >= 'a' && text.charAt(0) <= 'z')
		{
			if (today == null)
				today = DateUtil.today0();
			p = text.indexOf(':');
			if (p >= 0)
			{
				text = text.substring(0, p).trim();
				if (text.length() == 0)
					return null;
			}
			p = text.length();
			for (; p > 0 && text.charAt(p - 1) >= '0' && text.charAt(p - 1) <= '9'; p--)
				;
			if (p == text.length() || p <= 0)
				return null;
			int n = Integer.parseInt(text.substring(p));
			//if( n<=0 )
			//	return null;
			final int thisYear = DateUtil.getDateYear(today);
			final int thisMonth = DateUtil.getDateMonth(today);
			text = text.substring(0, p);
			Date fromDate = null, toDate = null;
			if ("monthtri".equals(text)) // 上中下 旬
			{
				if (n < 1 || n > 3)
					return null;
				int fromDay = (n - 1) * 10 + 1;
				int toDay = n == 3 ? DateUtil.getDaysOfMonth(thisYear, thisMonth) : fromDay + 9;
				fromDate = DateUtil.toDate(thisYear, thisMonth, fromDay);
				toDate = DateUtil.toDate(thisYear, thisMonth, toDay);
			} else if ("quarter".equals(text)) // 季度
			{
				if (n < 1 || n > 4)
					return null;
				int fromMonth = (n - 1) * 3 + 1, toMonth = fromMonth + 2;
				fromDate = DateUtil.toDate(thisYear, fromMonth, 1);
				toDate = DateUtil.toDate(thisYear, toMonth, DateUtil.getDaysOfMonth(thisYear, toMonth));
			} else if ("semiyear".equals(text)) // 半年
			{
				if (n < 1 || n > 2)
					return null;
				fromDate = DateUtil.toDate(thisYear, n == 1 ? 1 : 7, 1);
				toDate = DateUtil.toDate(thisYear, n == 1 ? 6 : 12, n == 1 ? 30 : 31);
			} else
			{
				final boolean total = text.charAt(text.length() - 1) == 't';
				if (total)
				{
					text = text.substring(0, text.length() - 1);
					if (text.length() == 0)
						return null;
				}
				final boolean after = text.charAt(0) == 'a';
				if (after)
				{
					text = text.substring(1);
				} else
				// 近 xxx
				{
					n = -(n - 1);
				}
				// text =  day, week, month, year
				if ("day".equals(text))
				{
					toDate = DateUtil.incDate(today, n);
					fromDate = total ? (after ? DateUtil.incDate(today, 1) : today) : toDate;
				} else if ("month".equals(text))
				{
					int toMonth = thisMonth + n;
					int fromMonth = total ? (after ? thisMonth + 1 : thisMonth) : toMonth;
					if (toMonth < fromMonth)
					{
						int tmp = fromMonth;
						fromMonth = toMonth;
						toMonth = tmp;
					}
					int fromYear = thisYear, toYear = thisYear;
					for (; fromMonth < 1; fromMonth += 12, fromYear--)
						;
					for (; fromMonth > 13; fromMonth -= 12, fromYear++)
						;
					for (; toMonth < 1; toMonth += 12, toYear--)
						;
					for (; toMonth > 13; toMonth -= 12, toYear++)
						;
					toDate = DateUtil.toDate(toYear, toMonth, DateUtil.getDaysOfMonth(toYear, toMonth));
					fromDate = DateUtil.toDate(fromYear, fromMonth, 1);
				} else if ("year".equals(text))
				{
					int toYear = thisYear + n;
					int fromYear = total ? (after ? thisYear + 1 : thisYear) : toYear;
					if (toYear < fromYear)
					{
						int tmp = fromYear;
						fromYear = toYear;
						toYear = tmp;
					}
					toDate = DateUtil.toDate(toYear, 12, 31);
					fromDate = DateUtil.toDate(fromYear, 1, 1);
				} else if ("week".equals(text))
				{
					int wd = DateUtil.getWeekDay(today);
					final Date week1Date = DateUtil.incDate(today, wd == 0 ? -6 : 1 - wd);
					// week1Date : 本周的 周一日期：
					int toWeek = n;
					int fromWeek = total ? (after ? 1 : 0) : toWeek;
					if (toWeek < fromWeek)
					{
						int tmp = fromWeek;
						fromWeek = toWeek;
						toWeek = tmp;
					}
					fromDate = DateUtil.incDate(week1Date, fromWeek * 7);
					toDate = DateUtil.incDate(week1Date, toWeek * 7 + 6);
				}
				//DateUtil.getWeekDay(date)
			}
			if (fromDate != null && toDate != null)
			{
				return toDate.getTime() < fromDate.getTime() ? new java.util.Date[] { toDate, caseTime ? setDayEndTime(fromDate) : fromDate } : new java.util.Date[] { fromDate,
						caseTime ? setDayEndTime(toDate) : toDate };
			}
			return null;
			//if( text.charAt(p-1) )
			//text = text
			//String isTotal = 
		}
		java.util.Date date = DateUtil.toDate(text);
		return date == null ? null : new java.util.Date[] { date, caseTime ? setDayEndTime(date) : date };
	}

	public final static Date parseDate(String text, String format)
	{
		if (text == null)
			return null;
		format = format.toUpperCase();
		int n = format.length();
		if (text.length() != n)
			throw new java.lang.IllegalArgumentException("日期格式错:" + text);
		int ymd[] = new int[6];
		boolean hasHour = false;
		for (int i = 0; i < n; i++)
		{
			char c = text.charAt(i);
			char fc = format.charAt(i);
			int j = "YMDHMS".indexOf(fc);
			if (j >= 0)
			{
				if (j == 3)
					hasHour = true;
				else if (j == 1 && hasHour)
					j = 4;
				int d = c - '0';
				if (d < 0 || d > 9)
					throw new java.lang.IllegalArgumentException("日期格式错:" + text);
				ymd[j] = ymd[j] * 10 + d;
			} else if (c != fc)
				throw new java.lang.IllegalArgumentException("日期格式错:" + text);
		}
		if (ymd[0] < 100)
			ymd[0] += 2000;
		return DateUtil.toDate(ymd[0], ymd[1], ymd[2], ymd[3], ymd[4], ymd[5]);
	}

	public final static String dateToString(Date date, String format)
	{
		if (date == null || format == null)
			return null;
		synchronized (staticCal)
		{
			Calendar cal = DateUtil.getStaticCalendars(date);
			int ymd[] = new int[6];
			ymd[0] = cal.get(Calendar.YEAR);
			ymd[1] = cal.get(Calendar.MONTH) + 1;
			ymd[2] = cal.get(Calendar.DAY_OF_MONTH);
			ymd[3] = cal.get(Calendar.HOUR_OF_DAY);
			ymd[4] = cal.get(Calendar.MINUTE);
			ymd[5] = cal.get(Calendar.SECOND);
			format = format.toUpperCase();
			int n = format.length();
			StringBuffer sb = new StringBuffer();
			boolean hasHour = false;
			for (int i = 0; i < n;)
			{
				char c = format.charAt(i++);
				int nd = 1;
				for (; i < n && format.charAt(i) == c; i++)
					nd++;
				int j = "YMDHMS".indexOf(c);
				if (j >= 0)
				{
					if (j == 3)
						hasHour = true;
					else if (j == 1 && hasHour)
						j = 4;
					String s = Integer.toString(j == 0 && nd == 2 ? ymd[0] % 100 : ymd[j]);
					for (; nd > s.length(); nd--)
					{
						sb.append('0');
					}
					sb.append(s);
				} else
				{
					for (; nd > 0; nd--)
					{
						sb.append(c);
					}
				}
			}
			return sb.toString();
		}
	}

	static private String[] splitDateText(String text)
	{
		java.util.List<String> list = new ArrayList<String>();
		final int n = text.length();
		int i = 0;
		int j = 0;
		for (; i < n;)
		{
			int i0 = i;
			for (; i < n; i++)
			{
				char c = text.charAt(i);
				if ((j & 1) == 0 ? (c < '0' || c > '9') : (c >= '0' && c <= '9'))
					break;
			}
			if (i <= i0)
				break;
			list.add(text.substring(i0, i).trim());
			j++;
		}
		if (i < n)
			return null;
		return list.toArray(new String[list.size()]);
	}

	private static String	hmdDelim;

	/**
	"yyyy-mm-dd hh:mm:ss"
	*/
	public final static boolean parseDateTime(String text, int ret[])
	{
		//char [] sep = new char[] { '-','-',' ',':',':' };
		if (hmdDelim == null)
		{
			try
			{
				hmdDelim = System.getProperty("Date.HMDDELIM");
			} catch (Throwable ex)
			{
			}
			if (hmdDelim == null || hmdDelim.length() == 0)
				hmdDelim = "-";
		}
		text = text.trim();
		if (ret == null)
			ret = new int[6]; // tmp only
		int l = text.length();
		if ((l == 8 || l == 10 || l == 12 || l == 14) && isDigites(text))
		{
			ret[0] = Integer.parseInt(text.substring(0, 4));
			ret[1] = Integer.parseInt(text.substring(4, 6));
			ret[2] = Integer.parseInt(text.substring(6, 8));
			ret[3] = l > 8 ? Integer.parseInt(text.substring(8, 10)) : 0;
			ret[4] = l > 10 ? Integer.parseInt(text.substring(10, 12)) : 0;
			ret[5] = l > 12 ? Integer.parseInt(text.substring(12, 14)) : 0;
		} else
		{
			String[] a = splitDateText(text);
			if (a == null || a.length < 5 || a.length > 13 || (a.length & 1) == 0)
				return false;
			for (int i = 0; i < ret.length && i * 2 < a.length; i++)
			{
				ret[i] = Integer.valueOf(a[i * 2]);
				if (i == 0)
					continue;
				final String s = a[i * 2 - 1];
				if (s.length() > 1)
					return false;
				final String validS;
				if (i <= 2)
				{
					validS = hmdDelim;//"-";
					//if( !"-".equals(a[i*]))
				} else if (i == 3)
				{
					if (s.length() > 0)
						return false;
					continue;
				} else if (i <= 5)
				{
					validS = ":";
				} else
				{
					validS = ".";
				}
				if (s.length() != 1 || validS.indexOf(s.charAt(0)) < 0)
					return false;
			}
			if (ret[0] >= 0 && ret[0] < 100)
			{
				if (ret[0] >= 70)
					ret[0] += 1900;
				else
					ret[0] += 2000;
			}
		}
		if (ret[3] < 0 || ret[3] >= 24)
			return false;
		if (ret[4] < 0 || ret[4] >= 60)
			return false;
		if (ret[5] < 0 || ret[5] >= 60)
			return false;
		return ret[0] >= 1000 && ret[0] < 3999 && ret[1] >= 1 && ret[1] <= 12 && ret[2] >= 1 && ret[2] <= getDaysOfMonth(ret[0], ret[1]);
	}
}
