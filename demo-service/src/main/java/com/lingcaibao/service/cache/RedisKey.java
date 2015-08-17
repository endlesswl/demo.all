package com.lingcaibao.service.cache;

/**
 * Redis 用到的key和相关说明
 * 
 * @author nzh
 * 
 */
public class RedisKey {

	//定制活动信息 格式 key = MARKETPLAN_{marketplanID} value = {Map}
	public static final String MARKETPLAN_ = "MARKETPLAN_";
	{
		//allNum + 奖品等级   存放对应奖品总数量
		//curNum + 奖品等级   存放对应奖品使用数量
	}
	//零彩宝活动领码key
	public static final String LINGCAIBAO_ACTION_GAME="lingcaibao_action_game:";
	//零彩宝 统一领码活动key
	public static final String LCB_CODEFREE_MARKET = "LCB_CODEFREE_MARKET_";
	
	//定制活动奖品相关信息 格式 key = MARKETPLAN_PRIZE_{marketplanID} value = {Map}
	public static final String MARKETPLAN_PRIZE_ = "MARKETPLAN_PRIZE_";
	
	//用户参加活动记录 格式 key = USER_MARKETPLAN_{userID}_{marketplanID} value = {参与次数}
	//信息先从库里获取，
	public static final String USER_MARKETPLAN_ = "USER_MARKETPLAN_";
	//用户公告阅读集合key
	public static final String USER_READ_NOTICES_KEY = "USER_READ_NOTICES";
	//用户公告阅读记录KEY前缀 = USER_NOTICE_{userID}_{channel}_{文章id}      value = {浏览总次数}
	public static final String USER_NOTICE_INFO_PREFIX ="USER_NOTICE_";

	//活动浏览记录 用户id key
	public static final String USER_BROWSE_USERID ="USER_BROWSE_USERID_";//用户id
	
	public static final String USER_BROWSE_DEL_USERID ="USER_BROWSE_DEL_USERID_";//用户id
	
	public static final String USER_BROWSE_COUNT ="USER_BROWSE_COUNT";//浏览次数
	
	public static final String USER_BROWSE_ISDELETE ="USER_BROWSE_ISDELETE";//是否删除 0 未删除 1 已删除
	
	public static final String USER_BROWSE_ACTIVITY_ID ="USER_BROWSE_ACTIVITY_ID_";//活动id
}
