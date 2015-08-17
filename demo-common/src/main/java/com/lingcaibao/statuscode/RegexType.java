package com.lingcaibao.statuscode;

public enum RegexType {
	NONE,//NONE
	EMAIL,//邮件格式验证
	IP,//IP地址验证
	MOBILE,//手机格式
	NUMBER,//数字格式
	STRING,//STRING
	TIME,//时间格式
	URL,//域名网址
	CHINESE,//是否有汉字
	SPECIALCHAR,//判断是否含有特殊字符
	FREQUENCY,//频率格式（1/d） 0:不限 1:总共1次 h:小时 d:日 w:周 m:月 q:季度 y:年
}
