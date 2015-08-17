package com.lingcaibao.entityenum;

/**
 * <p>标题：字典编号枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月25日 下午3:44:02</p>
 * <p>类全名：com.lingcaibao.entityenum.DictNo</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum DictNo
{
	NODE("D0001", "国别地区"), 
	TRADE("D0002", "行业");
	private String	dictno;
	private String	dictname;

	DictNo(String dictno, String dictname)
	{
		this.dictno = dictno;
		this.dictname = dictname;
	}

	public String getDictNo()
	{
		return this.dictno;
	}

	public String getDictName()
	{
		return this.dictname;
	}
}
