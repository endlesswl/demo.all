package com.lingcaibao.util;

import com.lingcaibao.conf.Conf;

/**
 * @class UrlDoMain
 * @desc 网站全局URL设置类 方法中的全局参数通过配置文件 application.properties 设置
 * @author nzh
 */
public class UrlDoMain {
	/**
	 * 上传图片路径
	 */
	public final static String imageDoMain = Conf.get("ftp.domain");
	/**
	 * H5 参加活动需要的URL 分享使用
	 */
	public final static String h5ActivityUrl = Conf.get("lingcai.activityUrl");
	/**
	 * 网站主域名
	 */
	public final static String doMainUrl = Conf.get("lingcai.domain");
	/**
	 * wap项目域名
	 */
	public final static String wapUrl = Conf.get("lingcai.wap.url");
}
