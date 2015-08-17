package com.lingcaibao.email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
/**
 * <p>标题：360Email邮件营销平台API接口 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月18日 下午8:24:58</p>
 * <p>类全名：com.lingcaibao.email.E3Api</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class E3Api
{
	private static Logger		logger	= LoggerFactory.getLogger(E3Api.class);
	static private final String	API_URL	= "http://send.360email.cn/api/1.0/sendmail.php";
	static private final String	API_KEY	= "dcd18b1652b323a100cacc96ee230dab";

	static public boolean sendPost(String recipients, String subject, String body)
	{
		boolean success = false;
		PrintWriter out = null;
		BufferedReader in = null;
		try
		{
			URL realUrl = new URL(API_URL);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(10000);
			out = new PrintWriter(conn.getOutputStream());
			String param = "token=" + API_KEY;
			param += "&tomail=" + java.net.URLEncoder.encode(recipients, "utf-8");
			param += "&subject=" + java.net.URLEncoder.encode(subject, "utf-8");
			param += "&body=" + java.net.URLEncoder.encode(body, "utf-8");
			out.print(param);
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String result = "";
			String line;
			while ((line = in.readLine()) != null)
			{
				result += line;
			}
			success = StringUtils.equals(JSON.parseObject(result).getString("result"), "success");
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("---------- E3 SEND MAIL ERR ---------" + e.getMessage());
		} finally
		{
			try
			{
				if (out != null)
				{
					out.close();
				}
				if (in != null)
				{
					in.close();
				}
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return success;
	}
}
