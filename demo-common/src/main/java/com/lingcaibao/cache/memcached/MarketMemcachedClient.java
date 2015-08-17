package com.lingcaibao.cache.memcached;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

/**
 * <p>标题：Memcached客户端 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月12日 下午2:27:18</p>
 * <p>类全名：com.lingcaibao.cache.memcached.MarketMemcachedClient</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class MarketMemcachedClient implements DisposableBean
{
	private static Logger	logger			= LoggerFactory.getLogger(MarketMemcachedClient.class);
	private MemcachedClient	memcachedClient;
	private long			shutdownTimeout	= 2500;
	private long			updateTimeout	= 2500;

	@SuppressWarnings("unchecked")
	public <T> T get(String key)
	{
		try
		{
			return (T) memcachedClient.get(key);
		} catch (RuntimeException e)
		{
			handleException(e, key);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> Map<String,T> getBulk(Collection<String> keys)
	{
		try
		{
			return (Map<String,T>) memcachedClient.getBulk(keys);
		} catch (RuntimeException e)
		{
			handleException(e, StringUtils.join(keys, ","));
			return null;
		}
	}

	public void set(String key, int expiredTime, Object value)
	{
		memcachedClient.set(key, expiredTime, value);
	}

	public boolean safeSet(String key, int expiration, Object value)
	{
		Future<Boolean> future = memcachedClient.set(key, expiration, value);
		try
		{
			return future.get(updateTimeout, TimeUnit.MILLISECONDS);
		} catch (Exception e)
		{
			future.cancel(false);
		}
		return false;
	}

	public void delete(String key)
	{
		memcachedClient.delete(key);
	}

	public boolean safeDelete(String key)
	{
		Future<Boolean> future = memcachedClient.delete(key);
		try
		{
			return future.get(updateTimeout, TimeUnit.MILLISECONDS);
		} catch (Exception e)
		{
			future.cancel(false);
		}
		return false;
	}

	public long incr(String key, int by, long defaultValue)
	{
		return memcachedClient.incr(key, by, defaultValue);
	}

	public long decr(String key, int by, long defaultValue)
	{
		return memcachedClient.decr(key, by, defaultValue);
	}

	public Future<Long> asyncIncr(String key, int by)
	{
		return memcachedClient.asyncIncr(key, by);
	}

	public Future<Long> asyncDecr(String key, int by)
	{
		return memcachedClient.asyncDecr(key, by);
	}

	private void handleException(Exception e, String key)
	{
		logger.warn("spymemcached client receive an exception with key:" + key, e);
	}

	@Override
	public void destroy() throws Exception
	{
		if (memcachedClient != null)
		{
			memcachedClient.shutdown(shutdownTimeout, TimeUnit.MILLISECONDS);
		}
	}

	public MemcachedClient getMemcachedClient()
	{
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient)
	{
		this.memcachedClient = memcachedClient;
	}

	public void setUpdateTimeout(long updateTimeout)
	{
		this.updateTimeout = updateTimeout;
	}

	public void setShutdownTimeout(long shutdownTimeout)
	{
		this.shutdownTimeout = shutdownTimeout;
	}

	
}