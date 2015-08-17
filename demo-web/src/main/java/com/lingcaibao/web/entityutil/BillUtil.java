package com.lingcaibao.web.entityutil;

import java.util.Map;
import com.google.common.collect.Maps;
import com.lingcaibao.entity.Bill;
import com.lingcaibao.entity.UserPrize;
import com.lingcaibao.statuscode.BillChannelEnum;
import com.lingcaibao.statuscode.BillIndecrEnum;
import com.lingcaibao.statuscode.BillReturnCodeEnum;
import com.lingcaibao.statuscode.LotteryType;
import com.palm.commom.uitl.DateFormatUtil;
import com.palm.commom.uitl.StringUtil;
/**
 * <p>标题：交易记录工具类 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月29日 下午6:06:26</p>
 * <p>类全名：com.lingcaibao.web.entityutil.BillUtil</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class BillUtil
{
	public static Map<String,Object> getBillJson(Bill bill)
	{
		Map<String,Object> map = Maps.newHashMap();
		map.put("billchannel", bill.getBillchannel());
		map.put("billchannelStr", BillChannelEnum.getName(bill.getBillchannel()));
		map.put("billret", bill.getBillret());
		map.put("billretStr", BillReturnCodeEnum.getName(bill.getBillret()));
		map.put("createtime", DateFormatUtil.getTimeAll(bill.getCreatetime()));
		map.put("afterbalance", bill.getAfterbalance());
		if (BillIndecrEnum.ADD.ordinal() == bill.getIndecr())
		{
			map.put("amountAdd", bill.getAmount());//收入
			map.put("amountMun", "");//支出
		} else
		{
			map.put("amountAdd", "");//收入
			map.put("amountMun", bill.getAmount());//支出
		}
		map.put("subject", StringUtil.nullToEmpty(bill.getSubject()));
		return map;
	}

	public static Map<String,Object> getUserPrizeJson(UserPrize userPrize)
	{
		Map<String,Object> map = Maps.newHashMap();
		map.put("issueNo", userPrize.getIssueNo());
		map.put("prizeMoney", userPrize.getPrizeMoney());
		map.put("prizeMoneyAfterTax", userPrize.getPrizeMoneyAfterTax());
		map.put("tax", userPrize.getTax());
		if (userPrize.getIsfixed())
		{
			map.put("fixedPrize", "已兑奖");
		} else
		{
			map.put("fixedPrize", "未兑奖");
		}
		if (userPrize.getGameid().equals(LotteryType.QXC.name()))
		{
			map.put("gameid", LotteryType.getName(userPrize.getGameid()));
		}
		if (userPrize.getGameid().equals(LotteryType.DLT))
		{
			map.put("gameid", LotteryType.getName(userPrize.getGameid()));
		}
		if (userPrize.getGameid().equals(LotteryType.SSQ))
		{
			map.put("gameid", LotteryType.getName(userPrize.getGameid()));
		}
		if (userPrize.getGameid().equals(userPrize.getGameid()))
		{
			map.put("gameid", LotteryType.getName(userPrize.getGameid()));
		}
		return map;
	}
}
