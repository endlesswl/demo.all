package com.lingcaibao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Dictionary implements Serializable
{
	private static final long serialVersionUID =  -5660498827581894965L;
	//alias
	public static final String	TABLE_ALIAS			= "Dictionary";
	public static final String	ALIAS_ID			= "id";
	public static final String	ALIAS_DICTNO		= "dictno";
	public static final String	ALIAS_LEVEL			= "level";
	public static final String	ALIAS_PARENTCODE	= "parentcode";
	public static final String	ALIAS_CODE			= "code";
	public static final String	ALIAS_NAME			= "name";
	public static final String	ALIAS_REMARK		= "remark";
	public static final String	ALIAS_LASTFLAG		= "lastflag";
	/**
	 * ID
	 */
	private Long				id;
	/**
	 * 字典编号 如 0001:行业,0002:国别地区 等
	 */
	private String				dictno;
	/**
	 * 编码级次
	 */
	private Integer				level;
	/**
	 * 所属级次编码
	 */
	private String				parentcode;
	/**
	 * 编码
	 */
	private String				code;
	/**
	 * 名称
	 */
	private String				name;
	/**
	 * 描述
	 */
	private String				remark;
	/**
	 * 末级标识 0:否 1:是
	 */
	private Integer			lastflag;
	
	private List<Dictionary>	subDicts;

	public java.lang.Long getId()
	{
		return this.id;
	}

	public void setId(java.lang.Long value)
	{
		this.id = value;
	}

	public java.lang.String getDictno()
	{
		return this.dictno;
	}

	public void setDictno(java.lang.String value)
	{
		this.dictno = value;
	}

	public Integer getLevel()
	{
		return this.level;
	}

	public void setLevel(Integer value)
	{
		this.level = value;
	}

	public String getParentcode()
	{
		return this.parentcode;
	}

	public void setParentcode(String value)
	{
		this.parentcode = value;
	}

	public java.lang.String getCode()
	{
		return this.code;
	}

	public void setCode(java.lang.String value)
	{
		this.code = value;
	}

	public java.lang.String getName()
	{
		return this.name;
	}

	public void setName(java.lang.String value)
	{
		this.name = value;
	}

	public void setremark(java.lang.String value)
	{
		this.remark = value;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getLastflag() {
		return lastflag;
	}

	public void setLastflag(Integer lastflag) {
		this.lastflag = lastflag;
	}

	public List<Dictionary> getSubDicts()
	{
		return subDicts;
	}

	public void setSubDicts(List<Dictionary> subDicts)
	{
		this.subDicts = subDicts;
	}

	public void addSubDict(Dictionary subDict)
	{
		if (subDict != null)
		{
			if (subDicts == null)
			{
				subDicts = new ArrayList<Dictionary>();
			}
			subDicts.add(subDict);
		}
	}
}