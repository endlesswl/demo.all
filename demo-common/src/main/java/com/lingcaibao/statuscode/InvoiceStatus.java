package com.lingcaibao.statuscode;

public enum InvoiceStatus
{
	UNSETTLE("未结算"), 
	INVOICING("待开票"), 
	INVOICED("已开票"), 
	SEND("已邮寄"), 
	SIGN("已签收"), ;
	private String	name;

	private InvoiceStatus(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	static public String getName(int ord)
	{
		for (InvoiceStatus status : InvoiceStatus.values())
		{
			if (status.ordinal() == ord)
			{
				return status.getName();
			}
		}
		return null;
	}
}
