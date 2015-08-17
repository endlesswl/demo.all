package com.lingcaibao.conf;

import java.io.InputStreamReader;
import java.util.Properties;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Client;
/**
 * 公共函数和变量的定义
 * 
 * @author xin.pang
 * 
 */
public final class Conf
{
	private static final Logger	logger			= LoggerFactory.getLogger(Conf.class);
	// 访问用户中心的超时时间
	public static final int		nUCenterTimeout	= 1000;
	private static Properties	prop;

	private static void init()
	{
		try
		{
			prop = new Properties();
			prop.load(new InputStreamReader(Client.class.getClassLoader().getResourceAsStream("application.properties"), "UTF-8"));
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static String get(String key)
	{
		if (prop == null)
		{
			init();
		}
		return prop.getProperty(key);
	}

	public static int get(String strKey, int nDefault)
	{
		if (prop == null)
		{
			init();
		}
		return Integer.valueOf(prop.getProperty(strKey, Integer.toString(nDefault)));
	}

	public static String get(String strKey, String nDefault)
	{
		if (prop == null)
		{
			init();
		}
		return prop.getProperty(strKey, nDefault);
	}

	public static void main(String... args)
	{
	}
}
