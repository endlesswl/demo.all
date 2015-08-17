package com.lingcaibao.mail;

public enum LcbMail
{
	//JIJI("冀骥", "13718154066", "移动"),
	//FENGSHULIN("冯树林", "15810839318", "移动"),
	XUPENGJU("徐鹏举", "13104008974", "联通"),
	BIYANFEN("毕妍芬", "13241744496", "联通"),

	NIEZHIHONG("聂志红", "18701358005", "移动"),
	LIANGDALONG("梁大龙", "13426297024", "移动"),
	LUWEI("卢炜", "18511408011", "联通"),
	PANGXIN("庞鑫", "13520770811", "移动"),
	汪俊("汪俊", "18301219481", "移动"),
	ZHOUWENPENG("周文鹏", "18811319486", "移动"),
	LIUYU("刘玉", "18513098189", "联通"),
	LIJIALI("李佳丽", "13671286992", "移动"),
	WANGJIABAO("王家宝", "13840167708", "移动"),
	ZHANGHUI("张辉", "15010380525", "移动"),
	LIUXIAOQIANG("刘孝强", "18511867876", "联通"),
	YANGHONG("杨洪", "15910757104", "移动"),
	LIULEI("刘磊", "13811197057", "移动"),
	HUANGDIANJUAN("黄殿娟", "13718911088", "移动"),

	//NIEZHIHONG("聂志红", "18701358005", "移动"),
	//LIANGDALONG("梁大龙", "13426297024", "移动"),
	//LUWEI("卢炜", "18511408011", "联通"),
	//PANGXIN("庞鑫", "13520770811", "移动"),
	//汪俊("汪俊", "18301219481", "移动"),
	//ZHOUWENPENG("周文鹏", "18811319486", "移动"),
	//LIUYU("刘玉", "18513098189", "联通"),
	//LIJIALI("李佳丽", "13671286992", "移动"),
	//WANGJIABAO("王家宝", "13840167708", "移动"),
	//ZHANGHUI("张辉", "15010380525", "移动"),
	//LIUXIAOQIANG("刘孝强", "18511867876", "联通"),
	//YANGHONG("杨红", "15910757104", "移动"),
	//LIULEI("刘磊", "13811197057", "移动"),
	//HUANGDIANJUAN("黄殿娟", "13718911088", "移动"),

	;
	String	name;
	String	mobile;
	String	operator;

	private LcbMail(String name, String mobile, String operator)
	{
		this.name = name;
		this.mobile = mobile;
		this.operator = operator;
	}

	public String getName()
	{
		return this.name;
	}

	public String getMobile()
	{
		return this.mobile;
	}

	public String getOperator()
	{
		return this.operator;
	}
}
