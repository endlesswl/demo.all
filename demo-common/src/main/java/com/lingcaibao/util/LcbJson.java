package com.lingcaibao.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
/**
 * 零彩宝常用 JSON转换方法
 * 
 * @author nzh
 * 
 */
public class LcbJson
{
	private static final SerializerFeature[]	features	= { SerializerFeature.WriteMapNullValue, // 输出空置字段
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
			SerializerFeature.WriteDateUseDateFormat,		};

	public static final String toJSONString(Object object)
	{
		return JSON.toJSONString(object, features);
	}

	public static final String toJSONString(Object object, SerializerFeature... features)
	{
		return JSON.toJSONString(object, features);
	}

	public static final <T> T parseObject(String text, Class<T> clazz)
	{
		return JSON.parseObject(text, clazz);
	}

	public static final <T> List<T> parseArray(String text, Class<T> clazz)
	{
		return JSON.parseArray(text, clazz);
	}

	public static final List<Object> parseArray(String text, Type[] types)
	{
		return JSON.parseArray(text, types);
	}

	public static final <T> T parseObject(String text, TypeReference<T> type)
	{
		return JSON.parseObject(text, type);
	}

	/**
	 * 实体类转Map Date 类型默认转yyyy-MM-dd HH:mm:ss
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> toMap(Object obj)
	{
		Map<String,Object> values = null;
		if (obj != null)
		{
			try
			{
				values = Maps.newHashMap();
				Class<?> cls = obj.getClass();
				Field[] fields = cls.getDeclaredFields();
				for (Field field : fields)
				{
					try
					{
						String name = field.getName();
						String strGet = "get" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
						Method methodGet = cls.getDeclaredMethod(strGet);
						Object object = methodGet.invoke(obj);
						if (object != null && Date.class.isAssignableFrom(object.getClass()))
						{
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							values.put(name, df.format(object));
						} else
						{
							values.put(name, object);
						}
					} catch (NoSuchMethodException ex)
					{
						// ex.printStackTrace();
						// System.err.println(ex.getMessage());
					}
				}
			} catch (Exception ex)
			{
				ex.printStackTrace();
				System.err.println(ex.getMessage());
			}
		}
		return values;
	}
}
