package com.lingcaibao.service.util;

import com.lingcaibao.entity.Bill;
import com.palm.commom.uitl.Digests;
import com.palm.commom.uitl.Encodes;
/**
 * 效验标示
 * @author kelylmall
 *
 */
public class SaltUtils
{
	public static final String	HASH_ALGORITHM		= "SHA-1";
	public static final int		HASH_INTERATIONS	= 1024;
	private static String		key					= "salt";
	private static final int	SALT_SIZE			= 8;

	/**
	 * 获取交易流水标的效验标示
	 * 
	 * @param bill
	 * @return
	 */
	public static void buildBillSalt(Bill bill)
	{
		if (bill != null)
		{
			StringBuffer bStr = new StringBuffer();
			bStr.append("amount=").append(bill.getAmount()).append("&");
			bStr.append("userId=").append(bill.getUserid()).append("&");
			bStr.append("indecr=").append(bill.getIndecr()).append("&");
			bStr.append("billchannel=").append(bill.getBillchannel()).append("&");
			bStr.append("billret=").append(bill.getBillret()).append("&");
			bStr.append("prebalance=").append(bill.getPrebalance()).append("&").append(key);
			byte[] saltByte = Digests.generateSalt(SALT_SIZE);
			byte[] billSalt = Digests.sha1(bStr.toString().getBytes(), saltByte, HASH_INTERATIONS);
			bill.setSalt(Encodes.encodeHex(billSalt));
		}
	}

	/**
	 * 验证交易的效验标示
	 * @param bill
	 * @return
	 */
	public static boolean verifyBillSalt(Bill bill)
	{
		if (bill == null)
		{
			return false;
		}
		StringBuffer bStr = new StringBuffer();
		bStr.append("amount=").append(bill.getAmount()).append("&");
		bStr.append("userId=").append(bill.getUserid()).append("&");
		bStr.append("indecr=").append(bill.getIndecr()).append("&");
		bStr.append("billchannel=").append(bill.getBillchannel()).append("&");
		bStr.append("billret=").append(bill.getBillret()).append("&");
		bStr.append("prebalance=").append(bill.getPrebalance()).append("&").append(key);
		byte[] saltByte = Digests.generateSalt(SALT_SIZE);
		byte[] billSalt = Digests.sha1(bStr.toString().getBytes(), saltByte, HASH_INTERATIONS);
		String encodeHex = Encodes.encodeHex(billSalt);
		String salt = bill.getSalt();
		if (encodeHex.equals(salt))
		{
			return true;
		}
		return false;
	}


	static public void main(String[] args)
	{
		//		StringBuffer rStr=new StringBuffer();
		//		rStr.append("userId=").append(Long.toString(1009220)).append("&");
		//		rStr.append("money=").append("31000").append("&");
		//		rStr.append("method=").append("7").append("&");
		//		rStr.append("actualMoney=").append("31000").append("&");
		//		rStr.append("status").append("1").append("&");
		//		rStr.append("prebalance").append("0.50").append("&").append(key);
		//		byte[] saltByte = Digests.generateSalt(SALT_SIZE);
		//	    byte[] rechargeSalt = Digests.sha1(rStr.toString().getBytes(), saltByte,
		//	                HASH_INTERATIONS);
		//	    System.err.println(Encodes.encodeHex(rechargeSalt));
		//	    // 99d5c4fe460cc3c805037fcbc578c0bb37db03a5
		//	    // d840ff2997c08ec94eac883b75f379c23a313bb8
		//	    // 1420359844391
		//	    Calendar cal = Calendar.getInstance();
		//        Date currentTime = cal.getTime();
		//        System.err.println(currentTime.getTime());
		//		StringBuffer bStr=new StringBuffer();
		//		bStr.append("amount=").append("31000").append("&");
		//		bStr.append("userId=").append(Long.toString(1009220)).append("&");
		//		bStr.append("indecr=").append("1").append("&");
		//		bStr.append("billchannel=").append("0").append("&");
		//		bStr.append("billret=").append("1").append("&");
		//		bStr.append("prebalance=").append("0.50").append("&").append(key);
		//		
		//		byte[] saltByte = Digests.generateSalt(SALT_SIZE);
		//	    byte[] billSalt = Digests.sha1(bStr.toString().getBytes(), saltByte,
		//	                HASH_INTERATIONS);
		//	    System.err.println(Encodes.encodeHex(billSalt));
		//	    // 857c9b05767fd421fcc8d3b741214795f237a30d
	}
}
