package com.lingcaibao.statuscode;

public enum AppStoreStatus
{
	ENABLE("启用"), 
	DISABLE("废弃"), ;
	private String	name;

	private AppStoreStatus(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	static public String getName(Integer status)
	{
		if (status != null)
		{
			for (AppStoreStatus ass : AppStoreStatus.values())
			{
				if (ass.ordinal() == status)
				{
					return ass.getName();
				}
			}
		}
		return null;
	}
}
