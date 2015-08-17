package com.lingcaibao.entity;

/**
 * <p>标题：结果对象 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月19日 下午12:04:34</p>
 * <p>类全名：com.lingcaibao.entity.Result</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class Result
{
	private Boolean	success;	// 是否成功
	private String	msg;		// 消息
	private Object	data;		// 返回的数据

	public Result()
	{
	}

	public Result(Boolean success)
	{
		super();
		this.success = success;
	}

	public Result(Boolean success, Object data)
	{
		super();
		this.success = success;
		this.data = data;
	}

	public Result(Boolean success, String msg, Object data)
	{
		super();
		this.success = success;
		this.msg = msg;
		this.data = data;
	}

	public Boolean getSuccess()
	{
		return success;
	}

	public void setSuccess(Boolean success)
	{
		this.success = success;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}
}
