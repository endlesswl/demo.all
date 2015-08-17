package com.lingcaibao.statuscode;

import org.apache.commons.lang3.StringUtils;

/**
 * 用户抽奖接入类型
 * 
 * @author nzh
 * 
 */
public enum LuckDrawInTypeEnum {
	H5("h5", 0, "手机浏览器"), IOS("ios", 1, "手机IOS"), ANDROID("android", 2, "手机安卓"), PC("pc", 3, "浏览器"), WX("wx", 4, "微信送彩");
	private String name;
	private Integer type;
	private String desc;

	private LuckDrawInTypeEnum(String name, Integer type, String desc) {
		this.name = name;
		this.type = type;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public Integer getType() {
		return type;
	}
	
	public String getDesc() {
		return desc;
	}

	public static Integer getType(String name) {
		if (StringUtils.isEmpty(name)) {
			return 0;
		}
		for (LuckDrawInTypeEnum luck : LuckDrawInTypeEnum.values()) {
			if (StringUtils.equals(luck.getName(), name)) {
				return luck.getType();
			}
		}
		return 0;
	}
	
	public static String getName(String type) {
		if (StringUtils.isEmpty(type)) {
			return "h5";
		}
		for (LuckDrawInTypeEnum luck : LuckDrawInTypeEnum.values()) {
			if (luck.getType() == Integer.valueOf(type)) {
				return luck.getName();
			}
		}
		return "h5";
	}
	
	public static String getDesc(String type){
		if (StringUtils.isEmpty(type)) {
			return "";
		}
		for (LuckDrawInTypeEnum luck : LuckDrawInTypeEnum.values()) {
			if (luck.getType() == Integer.valueOf(type)) {
				return luck.getDesc();
			}
		}
		return "";
	}

}
