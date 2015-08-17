package com.lingcaibao.statuscode;

import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
/**
 * <p>标题：二维码尺寸枚举 </p>
 * <p>公司: 北京阳光彩通 </p>
 * <p>创建日期：2014年8月6日 上午9:45:58</p>
 * <p>类全名：com.palm.lingcai.entity.entityenum.QRcodeSizeEnum</p>
 * <p>作者：JIJI </p>
 * <p>版本：1.0 </p>
 */
public enum QRcodeSizeEnum
{
	SIZE_8CM("8cm", "0.5m", 300), SIZE_12CM("12cm", "0.8m", 400), SIZE_15CM("15cm", "1m", 500), SIZE_30CM("30cm", "1.5cm", 600), SIZE_50CM("50cm", "2.5cm", 700);
	private String	sideLength;
	private String	scanDistance;
	private int		size;

	private QRcodeSizeEnum(String sideLength, String scanDistance, int size)
	{
		this.sideLength = sideLength;
		this.scanDistance = scanDistance;
		this.size = size;
	}

	// 二维码边长(cm)
	public String getSideLength()
	{
		return sideLength;
	}

	// 扫描距离(m)
	public String getScanDistance()
	{
		return scanDistance;
	}

	// 二维码尺寸
	public int getSize()
	{
		return size;
	}

	/**
	 * 获取二维码图片SIZE
	 * @param ord
	 * @return
	 */
	static public QRcodeSizeEnum getSize(int ord)
	{
		for (QRcodeSizeEnum size : QRcodeSizeEnum.values())
		{
			if (size.ordinal() == ord)
			{
				return size;
			}
		}
		return QRcodeSizeEnum.SIZE_8CM;
	}

	/**
	 * 获取二维码尺寸列表(JSON)
	 * @return
	 */
	static public String toJson()
	{
		Map<String,List<Map<String,Object>>> resultMap = Maps.newHashMap();
		List<Map<String,Object>> sizeList = Lists.newArrayList();
		for (QRcodeSizeEnum size : QRcodeSizeEnum.values())
		{
			Map<String,Object> sizeMap = Maps.newHashMap();
			sizeMap.put("ord", size.ordinal());
			sizeMap.put("sideLength", size.getScanDistance());
			sizeMap.put("scanDistance", size.getScanDistance());
			sizeMap.put("size", size.getSize());
			sizeList.add(sizeMap);
		}
		resultMap.put("sizes", sizeList);
		return JSON.toJSONString(resultMap);
	}
}
