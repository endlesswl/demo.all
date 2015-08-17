package com.lingcaibao.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.lingcaibao.statuscode.PaymentMethodEnum;
import com.palm.commom.uitl.DateFormatUtil;

/**
 * 网页常用功能工具
 * 
 * @author nzh
 * 
 */
public class WebUtils {

	/**
	 * 隐藏手机号中间4位
	 * 
	 * @param mobile
	 * @return
	 */
	public static String hideMobile(String mobile) {
		if (StringUtils.isEmpty(mobile) || mobile.length() < 11) {
			return "****";
		}
		return mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
	}

	/**
	 * 频率转换时间 例： 参数： 1/d 结果： { 2015-03-19 2015-03-19 1 亲，一天只允许领取1次哦，分享给更多朋友吧！ }
	 * 
	 * @param frequency
	 * @return {0开始时间, 1结束时间, 2限制总次数, 3超出限制后回复}
	 */
	public static String[] getFrequencyToTime(String frequency) {

		String[] returnStr = new String[4];
		Date currentDate = new Date();

		String[] frequencyArray = frequency.replace("\"", "").split("/");
		// 按小时统计,数据库格式为1/h
		if ("h".equals(frequencyArray[1])) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
			String newh = df.format(currentDate);
			returnStr[0] = newh + ":00:00";
			returnStr[1] = newh + ":59:59";
			returnStr[3] = "亲，每小时只允许领取" + frequencyArray[0] + "次哦，分享给更多朋友吧！";
		}

		// 按天统计,数据库格式为1/d
		if ("d".equals(frequencyArray[1])) {
			String nowDay = DateFormatUtil.toNormalPatternString(currentDate);
			returnStr[0] = nowDay + " 00:00:00";
			returnStr[1] = nowDay + " 23:59:59";
			returnStr[3] = "亲，一天只允许领取" + frequencyArray[0] + "次哦，分享给更多朋友吧！";
		}
		// 按周统计,数据库格式为1/w
		if ("w".equals(frequencyArray[1])) {
			String[] wtime = DateFormatUtil.getWeekStartEnd(currentDate);
			returnStr[0] = wtime[0];
			returnStr[1] = wtime[1];
			returnStr[3] = "亲，一周只允许领取" + frequencyArray[0] + "次哦，分享给更多朋友吧！";
		}
		// 按月统计,数据库格式为1/m
		if ("m".equals(frequencyArray[1])) {
			String[] mtime = DateFormatUtil.getMonthStartEnd(currentDate);
			returnStr[0] = mtime[0];
			returnStr[1] = mtime[1];
			returnStr[3] = "亲，一月只允许领取" + frequencyArray[0] + "次哦，分享给更多朋友吧！";
		}
		// 按期通知，数据库格式为1/p
		if ("p".equals(frequencyArray[1])) {
			returnStr[0] = null;
			returnStr[1] = null;
			returnStr[3] = "亲，活动只允许领取" + frequencyArray[0] + "次哦，分享给更多朋友吧！";
		}
		// 所有统计,数据库格式为1/1
		if ("1".equals(frequencyArray[1])) {
			returnStr[0] = null;
			returnStr[1] = null;
			returnStr[3] = "亲，只允许领取" + frequencyArray[0] + "次哦，分享给更多朋友吧！";
		}
		// 所有统计,数据库格式为1/1
		if (!"0".equals(frequencyArray[1]) && StringUtils.isNumeric(frequencyArray[1])) {
			returnStr[0] = null;
			returnStr[1] = null;
			returnStr[3] = "亲，只允许领取" + frequencyArray[0] + "次哦，分享给更多朋友吧！";
		}
		returnStr[2] = frequencyArray[0];
		return returnStr;
	}

	/**
	 * 获取当前时间和频生成的字符串
	 * 
	 * @param date
	 * @param unit
	 * @return
	 */
	public static String getStarEndName(String unit) {
		// 0:不限 1h:小时 2d:日 3w:周 4m:月 5q:季度 6y:年',
		switch (unit) {
		case "0":
			return "";
		case "h":
			return DateFormatUtil.getCurrentDate("yyMMddHH");
		case "d":
			return DateFormatUtil.getCurrentDate("yyMMdd");
		case "w":
			// yyyyMMsded
			return DateFormatUtil.getWeekStartEndName();
		case "m":
			return DateFormatUtil.getCurrentDate("yyMM");
		case "q":
			// yyyysMeM
			return DateFormatUtil.getCurrentQuarterStartEndTime();
		case "y":
			return DateFormatUtil.getCurrentDate("yy");
		}
		return "";
	}

	/**
	 * 获取2个日期之间相差 的数量
	 * 
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @param unit
	 * @return
	 * @throws ParseException
	 */
	public static long dateDiff(String startTime, String endTime, String format, String unit) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
//		long ns = 1000;// 一秒钟的毫秒数
		long diff = 0;
		// 获得两个时间的毫秒时间差异
		startTime = getChangeSTime(startTime, unit);
		endTime = getChangeETime(endTime, unit);
		try {
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();

			long day = diff / nd;// 计算差多少天
			long hour = diff % nd / nh;// 计算差多少小时
			long min = diff % nd % nh / nm;// 计算差多少分钟
//			long sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果

			long num = 0;
			switch (unit) {
			case "0":
				return 0;
			case "1":
				return 1;
			case "h":
				num = (day * 24) + hour + (min > 0 ? 1 : 0);
				return num;
			case "d":
				num = day + (hour > 0 ? 1 : 0);
				return num;
			case "w":
				return DateFormatUtil.compare_week(sd.parse(startTime), sd.parse(endTime));
			case "m":
				return DateFormatUtil.compareMonth(sd.parse(startTime), sd.parse(endTime));
			case "q":
				return DateFormatUtil.getDaysMonth(sd.parse(startTime), sd.parse(endTime)) / 3;
				// case "y":
				// return DateFormatUtil.getDaysYear(sd.parse(startTime),
				// sd.parse(endTime));
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String getChangeSTime(String time, String unit) {
		switch (unit) {
		case "h":
			return time.substring(0, 10) + "00:00";
		case "d":
			return time.substring(0, 11) + "00:00:00";
		}
		return time;
	}

	public static String getChangeETime(String time, String unit) {
		switch (unit) {
		case "h":
			return time.substring(0, 10) + "59:59";
		case "d":
			return time.substring(0, 11) + "23:59:59";
		}
		return time;
	}

	public static void main(String[] args) throws ParseException {
//		System.out.println("2015-04-15 01:02:03".substring(0, 11) + "00:00:00");
		long a = dateDiff("2015-05-27 16:36:00", "2015-06-03 16:36:00", "yyyy-MM-dd HH:mm", "w");

		System.out.println(a);

		// System.out.println(hideMobile("18701358005"));
		// // System.out.println(getFrequencyToTime("2/d"));
		// for (String string : getFrequencyToTime("1/d")) {
		// System.out.println(string);
		// }
		// System.out.println(getFrequencyToTime("1/d")[3].replaceAll("1",
		// (Integer.valueOf("1") + 2)+""));
	}

	public static String getDateSx() {
		String dateString = "";
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (hour >= 6 && hour < 8) {
			dateString = "早上好";
		} else if (hour >= 8 && hour < 11) {
			dateString = "上午好";
		} else if (hour >= 11 && hour < 13) {
			dateString = "中午好";
		} else if (hour >= 13 && hour < 18) {
			dateString = "下午好";
		} else {
			dateString = "晚上好";
		}
		return dateString;
	}

	/**
	 * 获取当前时间的前6个月 时间
	 * 
	 * @return 返回格式yyyy-MM
	 */
	public static String getTimeByMonth(int month) {
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		// 获取当前时间的前6个月
		calendar.add(Calendar.MONTH, -month);
		Date dateTime = calendar.getTime();
		return matter.format(dateTime);
	}

	/**
	 * 获取当前时间的前6年 时间
	 * 
	 * @return 返回格式yyyy
	 */
	public static String getTimeByYear(int year) {
		SimpleDateFormat matter = new SimpleDateFormat("yyyy");

		Calendar calendar = Calendar.getInstance();
		// 获取当前时间的前6个月
		calendar.add(Calendar.YEAR, -year);
		Date dateTime = calendar.getTime();
		return matter.format(dateTime);
	}

	public static String paramToUrl(String url, Map<String, String> paramMap) {
		url += "?";
		for (String key : paramMap.keySet()) {
			url += "&" + key + "=" + paramMap.get(key);
		}
		return url;
	}

	public static Map<String, String> paramToURLEncoder(Map<String, String> paramMap, String encode)
			throws UnsupportedEncodingException {
		for (String key : paramMap.keySet()) {
			paramMap.put(key, URLEncoder.encode(paramMap.get(key), encode));
		}
		return paramMap;
	}

	/**
	 * 得到支付类型
	 * 
	 * @param balance
	 *            使用余额
	 * @param coupon
	 *            使用领彩卷
	 * @param alipay
	 *            使用支付宝
	 * @param unionpay
	 *            使用银联
	 */
	public static int getPaymentMethod(boolean balance, boolean coupon, boolean alipay, boolean unionpay) {
		int num = 0;
		if (balance) {
			num += 1;
		}
		if (coupon) {
			num += 2;
		}
		if (alipay) {
			num += 4;
		}
		if (unionpay) {
			num += 8;
		}
		switch (num) {
		case 1:
			return PaymentMethodEnum.ACCOUNT.ordinal();
		case 2:
			return PaymentMethodEnum.COUPON.ordinal();
		case 3:
			return PaymentMethodEnum.COM_ACCOUNT_COUPON.ordinal();
		case 4:
			return PaymentMethodEnum.ALIPAY.ordinal();
		case 5:
			return PaymentMethodEnum.COM_ACCOUNT_ALIPAY.ordinal();
		case 6:
			return PaymentMethodEnum.COM_ALIPAY_COUPON.ordinal();
		case 7:
			return PaymentMethodEnum.COM_ACCOUNT_ALIPAY_COUPON.ordinal();
		case 8:
			return PaymentMethodEnum.UNIONPAY.ordinal();
		case 9:
			return PaymentMethodEnum.COM_ACCOUNT_UNIONPAY.ordinal();
		case 10:
			return PaymentMethodEnum.COM_UNIONPAY_COUPON.ordinal();
		case 11:
			return PaymentMethodEnum.COM_ACCOUNT_UNIONPAY_COUPON.ordinal();
		}
		return 0;
	}
	
	/**
	 * 获取根据模板的类型获取图片
	 * @param type
	 * @return
	 */
	public static List<String> getImgs(Integer type){
		List<String> imgs = Lists.newArrayList();
		String imgurl = UrlDoMain.imageDoMain;
		switch(type){
		case 1:// 生日
			imgs.add(imgurl + "/image/card/birthday1.jpg");
			imgs.add(imgurl + "/image/card/birthday2.jpg");
			imgs.add(imgurl + "/image/card/birthday3.jpg");
			break;
		case 2:// 中秋
			imgs.add(imgurl + "/image/card/midautumn1.jpg");
			imgs.add(imgurl + "/image/card/midautumn2.jpg");
			break;
		case 3:// 国庆
			imgs.add(imgurl + "/image/card/National1.jpg");
			break;
		case 4:// 好友聚会
			imgs.add(imgurl + "/image/card/party1.jpg");
			imgs.add(imgurl + "/image/card/party2.jpg");
			imgs.add(imgurl + "/image/card/party3.jpg");
			break;
		case 5:// 婚礼
			imgs.add(imgurl + "/image/card/wedding1.jpg");
			imgs.add(imgurl + "/image/card/wedding2.jpg");
			break;
		}
		return imgs;
	}
}
