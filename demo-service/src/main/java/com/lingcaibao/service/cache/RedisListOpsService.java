package com.lingcaibao.service.cache;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
/**
 * <p>标题：redis服务 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年3月26日 下午4:59:13</p>
 * <p>类全名：com.lingcaibao.service.cache.RedisListOpsService</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
@Service
public class RedisListOpsService
{
	@Autowired
	public StringRedisTemplate	stringRedisTemplate;

	/**
	 * 压栈
	 * 在名称为key的list头添加一个值为value的 元素
	 * @param key
	 * @param value
	 * @return
	 */
	public Long push(String key, String value)
	{
		return stringRedisTemplate.opsForList().leftPush(key, value);
	}

	/**
	 * 出栈
	 * 返回并删除名称为key的list中的首元素
	 * @param key
	 * @return
	 */
	public String pop(String key)
	{
		return stringRedisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 入队
	 * (在名称为key的list尾添加一个值为value的元素)	
	 * @param key
	 * @param value
	 * @return
	 */
	public Long in(String key, String value)
	{
		return stringRedisTemplate.opsForList().rightPush(key, value);
	}

	/**
	* 出队
	* 回并删除名称为key的list中的首元素	
	* @param key
	* @return
	*/
	public String out(String key)
	{
		return stringRedisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 栈/队列长
	 *
	 * @param key
	 * @return
	 */
	public Long length(String key)
	{
		return stringRedisTemplate.opsForList().size(key);
	}

	/**
	 * 范围检索
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> range(String key, int start, int end)
	{
		return stringRedisTemplate.opsForList().range(key, start, end);
	}

	/**
	 * 移除
	 *
	 * @param key
	 * @param i
	 * @param value
	 */
	public void remove(String key, long i, String value)
	{
		stringRedisTemplate.opsForList().remove(key, i, value);
	}

	/**
	 * 检索
	 *
	 * @param key
	 * @param index
	 * @return
	 */
	public String index(String key, long index)
	{
		return stringRedisTemplate.opsForList().index(key, index);
	}

	/**
	 * 置值
	 *
	 * @param key
	 * @param index
	 * @param value
	 */
	public void set(String key, long index, String value)
	{
		stringRedisTemplate.opsForList().set(key, index, value);
	}

	/**
	 * 普通的K-V操作，置值
	 *
	 * @param key
	 * @param value
	 */
	public void setValue(String key, String value, long expire)
	{
		stringRedisTemplate.opsForValue().set(key, value, expire);
	}

	/**
	 * 普通的K-V操作，置值
	 *
	 * @param key
	 * @param value
	 */
	public void setValue(String key, String value)
	{
		stringRedisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 普通的K-V操作，取值
	 *
	 * @param key
	 * @return
	 */
	public String getValue(String key)
	{
		return stringRedisTemplate.opsForValue().get(key);
	}

	/**
	 * 裁剪
	 *
	 * @param key
	 * @param start
	 * @param end
	 */
	public void trim(String key, long start, int end)
	{
		stringRedisTemplate.opsForList().trim(key, start, end);
	}

	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate)
	{
		this.stringRedisTemplate = stringRedisTemplate;
	}

	/**
	 * hash赋值
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void put(String key, Object hashKey, Object value)
	{
		this.stringRedisTemplate.opsForHash().put(key, hashKey, value);
	}

	/**
	 * hash赋值
	 * @param key
	 * @param m
	 */
	public void putAll(String key, Map<Object,Object> m)
	{
		this.stringRedisTemplate.opsForHash().putAll(key, m);
	}

	/**
	 * hash取值
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object get(String key, Object hashKey)
	{
		return this.stringRedisTemplate.opsForHash().get(key, hashKey);
	}

	/**
	 * hash获取所有的key值
	 * @param key
	 * @return
	 */
	public Set<Object> keys(String key){
		return this.stringRedisTemplate.opsForHash().keys(key);
	}
	
	/**
	 * 判断hash中的Key是否存在
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public boolean hasKey(String key, Object hashKey)
	{
		return this.stringRedisTemplate.opsForHash().hasKey(key, hashKey);
	}
	
	/**
	 * 删除hash中的key和值  支持多个Key删除
	 * @param key
	 * @param hashKeys
	 */
	public void delete(String key, Object... hashKeys){
		this.stringRedisTemplate.opsForHash().delete(key, hashKeys);
	}

	/**
	 * 保存redis对象方法支持
	 * 
	 * @param key
	 * @param obj 实体类 必须有get set方法的对象
	 */
	public void saveRedis(String key, Object obj) {
		Class<?> clazz = obj.getClass();

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			String paramName = field.getName();
			try {
				Method method = clazz.getMethod(
						"get" + paramName.substring(0, 1).toUpperCase()
								+ paramName.substring(1), new Class[] {});
				Object newObj = method.invoke(obj, new Object[] {});
				if (newObj != null && Date.class.isAssignableFrom(newObj.getClass())) {
					DateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					newObj = df.format(newObj);
				} else if(newObj != null) {
					newObj += "";
				} else {
					newObj = "";
				}
				this.put(key, paramName, newObj);
			} catch (NoSuchMethodException e) {
//				e.printStackTrace();
			} catch (SecurityException e) {
//				e.printStackTrace();
			} catch (IllegalAccessException e) {
//				e.printStackTrace();
			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
			} catch (InvocationTargetException e) {
//				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
	}
}
