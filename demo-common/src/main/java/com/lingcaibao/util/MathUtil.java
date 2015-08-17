package com.lingcaibao.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.apache.commons.lang.StringUtils;
/**
 * <p>标题：数学常量 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月24日 下午5:58:00</p>
 * <p>类全名：com.lingcaibao.util.MathUtil</p>
 * <p>作者：nzh </p>
 * <p>@version 1.0</p>
 */
public class MathUtil
{
	/**
	 * 获取数据小数点后两位
	 * @param obj 可以是字符串数字 或者 数字
	 * @return
	 */
	public static String getNumTwoDigitsAfter(Object obj)
	{
		return obj == null ? "0.00" : String.format("%.2f", Double.valueOf(obj.toString()));
	}

	/**
	 * 获取数据小数点后两位 方法2
	 * @param obj 可以是字符串数字 或者 数字
	 * @return
	 */
	public static String getNumTwoDigitsAfter2(Object obj)
	{
		Double num = Double.valueOf(obj.toString());
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(num);
	}

	/**
	 * Object转换BigDecimal
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal toDecimal(Object value)
	{
		if (value == null || value.equals(""))
		{
			return BigDecimal.ZERO;
		} else if (value instanceof BigDecimal)
		{
			return (BigDecimal) value;
		} else if (value instanceof String)
		{
			return new BigDecimal((String) value);
		} else if (value instanceof Integer)
		{
			return new BigDecimal((Integer) value);
		} else if (value instanceof Long)
		{
			return new BigDecimal((Long) value);
		}
		return null;
	}

	/**
	 * 计算2个数的百分比
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static String percent(int num1, int num2)
	{
		// 创建一个数值格式化对象  
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位  
		numberFormat.setMaximumFractionDigits(2);
		String result = numberFormat.format((float) num1 / (float) num2 * 100);
		return result;
	}
}
