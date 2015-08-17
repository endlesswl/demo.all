package com.lingcaibao.statuscode;

import org.apache.commons.lang3.StringUtils;
/**
 * <p>标题：活动类型枚举 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年4月22日 下午8:06:15</p>
 * <p>类全名：com.lingcaibao.statuscode.MarketPlanType</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public enum MarketPlanType
{
	BIG_WHEEL("BIGWHEEL", "大转盘", "http://image.lingcaibao.com/image/2015/3/2015_3_731dac68-7a0b-4ab8-bdd6-82007a605f31.png", "LUCK_DRAW"), // 大转盘
	TURN_OVER("TURNOVER", "翻牌", "http://image.lingcaibao.com/image/2015/3/2015_3_3e96e6a6-469a-4236-a700-52f351727182.png", "LUCK_DRAW"), // 翻牌
	TIGER("TIGER", "老虎机", "http://image.lingcaibao.com/image/2015/3/2015_3_668045e9-fbc0-4197-9321-e460f178f076.png", "LUCK_DRAW"), // 老虎机
	ROLL_DICE("DICE", "掷骰子", "http://image.lingcaibao.com/image/2015/3/2015_3_aeb6bca1-d7d1-4c1f-82b9-2ab8b8277d3b.png", "LUCK_DRAW"), // 掷骰子
	SCRATCH_CARD("SCRATCHCARD", "刮刮卡", "http://image.lingcaibao.com/image/2015/3/2015_3_95fae17d-a1b7-43d4-a05f-7ee638ff8b82.png", "LUCK_DRAW"), // 刮刮卡
	FORTUNE_BAG("FORTUNEBAG", "福袋", "http://image.lingcaibao.com/image/2015/3/2015_3_a96c59c7-f83d-4454-97ec-5af1b5829a48.png", "LUCK_DRAW"), // 福袋
	AD_QA("2", "广告问答", "http://image.lingcaibao.com/image/2015/3/2015_3_1a562488-23dd-4d07-8a82-6b59549eecbe.png", "SURVEY"), // 广告问答
	QS_QA("3", "调查问卷", "http://image.lingcaibao.com/image/2015/3/2015_3_7553102c-5ba5-424b-a702-19d010833323.png", "SURVEY"), // 调查问卷
	FEED_BACK("FEEDBACK", "意见反馈", "http://image.lingcaibao.com/image/2015/3/2015_3_78c82487-e8e2-4650-9eda-11946d01a5f3.png", "SURVEY"), // 意见反馈
	VOTE("VOTE", "投票", "http://image.lingcaibao.com/image/2015/3/2015_3_7b8b5966-a307-42f5-ac7f-bd2b625f5f5e.png", "SURVEY"), // 投票
	DOWNLOAD_APP("DOWNLOADAPP", "下载客户端", "http://image.lingcaibao.com/image/2015/3/2015_3_7683f790-52da-4fde-b3b2-b4306cafa022.png", "TASK_MOTIVATION"), // 下载客户端
	MEETING("MEETING", "会议签到", "http://image.lingcaibao.com/image/2015/3/2015_3_828d9b6e-cff9-4605-a5d9-eb1e1b946f60.png", "TASK_MOTIVATION"), // 会议签到
	REGISTER("REGISTER", "注册", "http://image.lingcaibao.com/image/2015/3/2015_3_4d2814e9-7cd6-458e-8b15-af8e43892c07.png", "TASK_MOTIVATION"), // 注册
	DRIVE("DRIVE", "预约试驾", "http://image.lingcaibao.com/image/2015/3/2015_3_ade269d5-97dc-42e4-84d2-239fd4a3dfcf.png", "PRODUCT_RELEASE"), // 预约试驾
	NEW_RELEASE("NEWRELEASE", "新品销售", "http://image.lingcaibao.com/image/2015/3/2015_3_c978ddec-b69b-4e0b-a8d0-cf19d94a4679.png", "PRODUCT_RELEASE"), // 新品销售
	NEW_TRY("NEWTRY", "新品试用", "http://image.lingcaibao.com/image/2015/3/2015_3_706ff6f5-1098-4b1a-a691-127460f575dd.png", "PRODUCT_RELEASE"), // 新品销售
	HOLIDAY_WISH("7", "节日祝福", "http://image.lingcaibao.com/image/2015/3/2015_3_c2d508a9-302a-415f-b06e-6804dfdc2641.png", "WISH"), // 节日祝福
	ATTENDANCE("ATTENDANCE", "上班考勤", "http://image.lingcaibao.com/image/2015/3/2015_3_c224536a-0fdd-499e-9446-47a95159edf5.png", "WORK_ATTENDANCE"), // 上班考勤
	ABOUT("ABOUT", "约么", "", "ABOUT_IT"), // 约么
	FREE_COUPON("FREECOUPON", "发码活动", "", "FREE_COUPON"), // 发码活动
	LOTTERY_ALL("LOTTERYALL", "全民送彩", "", "LOTTERY_ALL"), // 全民送彩
	INTEGRAL_GAME("INTEGRALGAME", "积分游戏", "", "GAME"), // 积分游戏
	MONEY_GIFT("MONEYGIFT", "红包", "http://192.168.1.35/image/2015/6/2015_6_46e272962af2481fa564d2e7b0f2b354.png", "WISH"), // 红包
	GAME("GAME", "游戏", "", "GAME"), // 游戏
	LIFE_ENTERTAINMENT("LIFE_ENTERTAINMENT", "生活娱乐", "", "LIFE_ENTERTAINMENT"), // 生活娱乐
	HEALTH("HEALTH", "健康", "", "HEALTH"), // 健康
	TRIP("TRIP", "出行", "", "TRIP"), // 出行
	REPAST("REPAST", "餐饮", "", "REPAST"), // 餐饮
	SHOP("SHOP", "购物", "", "SHOP"), // 购物
	WEALTH("WEALTH", "理财", "", "WEALTH"), // 理财
	;
	private String	type;	// 活动类型
	private String	name;	// 活动名称
	private String	image;	// 类型图片
	private String	scene;	// 活动场景

	private MarketPlanType(String type, String name, String image, String scene)
	{
		this.type = type;
		this.name = name;
		this.image = image;
		this.scene = scene;
	}

	public String getType()
	{
		return this.type;
	}

	public String getName()
	{
		return this.name;
	}

	public String getImage()
	{
		return this.image;
	}

	public String getScene()
	{
		return this.scene;
	}

	static public String getName(String type)
	{
		for (MarketPlanType plan : MarketPlanType.values())
		{
			if (StringUtils.equals(plan.getType(), type))
			{
				return plan.getName();
			}
		}
		return null;
	}

	static public String getImage(String type)
	{
		for (MarketPlanType plan : MarketPlanType.values())
		{
			if (StringUtils.equals(plan.getType(), type))
			{
				return plan.getImage();
			}
		}
		return null;
	}

	/**
	 * 获取正常活动类型列表
	 * @return
	 */
	static public String getNormalTypes(String appid)
	{
		String types = null;
		for (MarketPlanType plan : MarketPlanType.values())
		{
			/*if ((!StringUtils.equals(appid, "h5")) && StringUtils.equals(plan.getType(), MarketPlanType.FREE_COUPON.getType()))
			{
				continue;
			}*/
			if (StringUtils.equals(plan.getType(), MarketPlanType.LOTTERY_ALL.getType()))
			{
				continue;
			}
			if (StringUtils.equals(plan.getType(), MarketPlanType.INTEGRAL_GAME.getType()))
			{
				continue;
			}
			if (StringUtils.equals(plan.getType(), MarketPlanType.MONEY_GIFT.getType()))
			{
				continue;
			}
			types = StringUtils.isEmpty(types) ? plan.getType() : types + "," + plan.getType();
		}
		return types;
	}

	/**
	 * 按场景获取类型列表
	 * @param scene
	 * @return
	 */
	static public String getSceneTypes(String scene)
	{
		String types = null;
		for (MarketPlanType plan : MarketPlanType.values())
		{
			if (StringUtils.equals(plan.getScene(), scene))
			{
				types = (StringUtils.isEmpty(types) ? plan.getType() : types + "," + plan.getType());
			}
		}
		return types;
	}
}
