package com.lingcaibao.entityenum;

public enum BankEnum
{
	BANK_CARDTYPEJ(1, "借记卡"), 
	BANK_CARDTYPEX(2, "信用卡"), 
	;
	private int		type;
	private String	name;

	private BankEnum(int type, String name)
	{
		this.type = type;
		this.name = name;
	}

	public int getType()
	{
		return type;
	}

	public String getName()
	{
		return name;
	}

	static public String getName(int type)
	{
		for (BankEnum bank : BankEnum.values())
		{
			if (bank.getType() == type)
			{
				return bank.getName();
			}
		}
		return null;
	}
}
