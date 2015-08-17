package com.lingcaibao.statuscode;

import org.apache.commons.lang3.StringUtils;
/**
 * <p>标题：活动场景枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月21日 下午2:31:19</p>
 * <p>类全名：com.lingcaibao.statuscode.MarketPlanScene</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum MarketPlanScene
{
	LUCK_DRAW(0, "抽奖"), 
	SURVEY(1, "调研"), 
	TASK_MOTIVATION(2, "任务激励"), 
	PRODUCT_RELEASE(3, "新品发布"), 
	WISH(4, "送祝福"), 
	WORK_ATTENDANCE(5, "上班考勤"), 
	ABOUT_IT(6, "约么"), 
	FREE_COUPON(7, "发码活动"),
	LOTTERY_ALL(8, "全民送彩"),
	GAME(9, "游戏"),
	DISCOUNT_PROMOTIONS(10, "打折促销"),
	LIFE_ENTERTAINMENT(11, "生活娱乐"),
	GAME_APP(12, "游戏应用"),
	HOT_INFORMATION(13, "热点资讯"),
	HEALTH(14, "健康"),
	TRIP(15, "出行"),
	REPAST(16, "餐饮"),
	SHOP(17, "购物"),
	WEALTH(18, "理财"),
	;
	private Integer  type;
	private String	name;

	private MarketPlanScene(Integer type, String name)
	{
		this.type = type;
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public Integer getType() {
		return type;
	}

	/**
	 * 按场景编码获取场景名称
	 * @param code
	 * @return
	 */
	static public String getName(int ord)
	{
		for (MarketPlanScene scene : MarketPlanScene.values())
		{
			if (scene.getType() == ord)
			{
				return scene.getName();
			}
		}
		return null;
	}

	/**
	 * 按场景编码获取场景对应活动类型列表,多场景用","连接
	 * @param codeList
	 * @return
	 */
	static public String getTypes(String ords)
	{
		String types = null;
		for (MarketPlanScene scene : MarketPlanScene.values())
		{
			if (StringUtils.indexOf(ords, scene.getType() + "") >= 0)
			{
				types = StringUtils.isEmpty(types) ? MarketPlanType.getSceneTypes(scene.name()) : types + "," + MarketPlanType.getSceneTypes(scene.name());
			}
		}
		return types;
	}
	
	/**
	 * 按场景名获取场景对应活动类型列表,多场景用","连接
	 * @param name
	 * @return
	 */
	static public String getTypesByName(String name) {
		String types = null;
		for (MarketPlanScene scene : MarketPlanScene.values()) {
			if (name.contains(scene.name())) {
				types = name;
			}
		}
		return types;
	}
	
	public static void main(String[] args) {
		for (MarketPlanScene scene : MarketPlanScene.values()) {
			System.out.println(scene.name());
		}
	}
}
