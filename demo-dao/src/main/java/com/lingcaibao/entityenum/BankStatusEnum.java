package com.lingcaibao.entityenum;

public enum BankStatusEnum {
	
	BANK_DELETEFLAGZ(0,"正常"),
	BANK_DELETESTATUS(1,"删除"),;
	
	private int type;
	private String name;
	
	private BankStatusEnum(int type,String name)
	{
		this.type=type;
		this.name=name;
	}
	public int getType()
	{
		return type;
	}
	public String getName()
	{
		return name;
	}
	static public String getInfoResult(int type)
	{
		for(BankStatusEnum bank : BankStatusEnum.values())
		{
			if(bank.getType()==type)
			{
				return bank.getName();
			}
		}
		return null;
	}
}
