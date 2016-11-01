package com.youxue.core.redis;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import com.google.common.collect.Maps;
import com.youxue.core.util.SerializeUtil;
import com.youxue.core.util.ThreadUtil;

/**
 * @author MasterWind
 *         2015年9月5日 下午4:01:37
 *         jedis代理类，直接通过该spring bean来获取相关的redis操作
 *         如果想在一个应用中访问两个redis，只需生成两个该proxy bean即可，并分别注入不同的baseRedisSentinel
 */
public class JedisProxy
{
	private static final Log LOG = LogFactory.getLog(JedisProxy.class);
	private BaseRedisSentinel baseRedisSentinel;//需要手动在bean xml中注入

	/**
	 * 获取操作的jedis实例
	 *
	 * @return jedis
	 */
	public Jedis getJedis()
	{
		return baseRedisSentinel.getJedis();
	}

	/**
	 * 释放
	 */
	public void releaseJedis()
	{
		baseRedisSentinel.releaseJedis();
	}

	/**
	 * 释放损坏资源
	 */
	public void releaseBrokenJedis()
	{
		baseRedisSentinel.releaseBrokenJedis();
	}

	/**
	 * 释放原始连接
	 */
	public void releaseOriginJedis(Jedis jedis)
	{
		baseRedisSentinel.releaseOriginJedis(jedis);
	}

	/**
	 * 释放损坏资源
	 */
	public void releaseOriginBrokenJedis(Jedis jedis)
	{
		baseRedisSentinel.releaseOriginBrokenJedis(jedis);
	}

	public BaseRedisSentinel getBaseRedisSentinel()
	{
		return baseRedisSentinel;
	}

	public void setBaseRedisSentinel(BaseRedisSentinel baseRedisSentinel)
	{
		this.baseRedisSentinel = baseRedisSentinel;
	}

	@JedisWay
	public boolean containsKey(String key)
	{
		return getJedis().exists(key);
	}

	@JedisWay
	public int sadd(String key, String... value)
	{
		if (value.length == 0)
		{
			return 0;
		}
		return getJedis().sadd(key, value).intValue();
	}

	/**
	 * @param key
	 * @param value
	 * @Discription 将 key 的值设为 value ，当且仅当 key 不存在；若给定的 key 已经存在，则 SETNX 不做任何动作。
	 */
	@JedisWay
	public long setnx(String key, String value)
	{
		return getJedis().setnx(key, value);
	}

	@JedisWay
	public Set<String> smembers(String key)
	{
		return getJedis().smembers(key);
	}

	@JedisWay
	public Long expire(String key, int seconds)
	{
		return getJedis().expire(key, seconds);
	}

	@JedisWay
	public boolean sismember(String key, String member)
	{
		return getJedis().sismember(key, member);
	}

	@JedisWay
	public Set<String> keys()
	{
		return getJedis().keys("*");
	}

	/**
	 * @return
	 * @Discription get all keys with the prefix
	 */
	@JedisWay
	public Set<String> getKeysByPrefix(String prefix)
	{
		return getJedis().keys(prefix + "*");
	}

	/**
	 * @param keys
	 * @Discription multi get all values within keys
	 */
	@JedisWay
	public List<String> mget(String[] keys)
	{
		return getJedis().mget(keys);
	}

	@JedisWay
	public List<Object> batchHessianGet(byte[]... fields)
	{
		if (fields == null || fields.length == 0)
		{
			return null;
		}
		Jedis jedis = getJedis();
		try
		{
			List<Object> obList = new ArrayList<Object>(fields.length);

			List<byte[]> valueBytes = jedis.mget(fields);
			if (CollectionUtils.isEmpty(valueBytes))
				return null;

			for (byte[] valueByte : valueBytes)
			{
				if (null == valueByte)
				{
					continue;
				}
				obList.add(SerializeUtil.hessian2Deserialize(valueByte));
			}
			return obList;
		}
		catch (Exception e)
		{
			LOG.error("Error mget fields:" + fields, e);
			return null;
		}
	}

	@JedisWay
	public int incrBy(String key, int integer)
	{
		return getJedis().incrBy(key, integer).intValue();
	}

	@JedisWay
	public void set(String key, Object value)
	{
		getJedis().set(key, value.toString());
	}

	/**
	 * @param key
	 * @param value
	 * @param second
	 * @Discription 设置key为value，超时时间second秒
	 */
	@JedisWay
	public void setex(String key, String value, int second)
	{
		getJedis().setex(key, second, value);
	}

	@JedisWay
	public void set(String key, byte[] bytes)
	{
		getJedis().set(key.getBytes(), bytes);
	}

	/**
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @Discription 通过此方法，可以指定key的存活（有效时间） 时间为秒
	 */
	@JedisWay
	public void set(String key, String value, long expireSeconds)
	{
		getJedis().setex(key, (int) (expireSeconds), value);
	}

	@JedisWay
	public void set(byte[] key, byte[] object, long expireSeconds)
	{
		getJedis().setex(key, (int) (expireSeconds), object);
	}

	@JedisWay
	public int hset(String key, String subkey, String value)
	{
		if (null == value)
		{
			return 0;
		}
		return getJedis().hset(key, subkey, value).intValue();
	}

	@JedisWay
	public int hset(String key, String subkey, byte[] value)
	{
		if (null == value)
		{
			return 0;
		}
		return getJedis().hset(key.getBytes(), subkey.getBytes(), value).intValue();
	}

	@JedisWay
	public int hset(String key, int subkey, Object value)
	{
		if (null == value)
		{
			return 0;
		}
		return getJedis().hset(key, String.valueOf(subkey), value.toString()).intValue();
	}

	@JedisWay
	public int hset(String key, String subkey, int value)
	{
		return getJedis().hset(key, subkey, String.valueOf(value)).intValue();
	}

	@JedisWay
	public String hget(String key, String subkey)
	{
		return getJedis().hget(key, subkey);
	}

	@JedisWay
	public byte[] hget(byte[] key, byte[] subkey)
	{
		return getJedis().hget(key, subkey);
	}

	@JedisWay
	public Map<String, String> hgetAll(String key)
	{
		return getJedis().hgetAll(key);
	}

	@JedisWay
	public Map<byte[], byte[]> hgetAll(byte[] key)
	{
		return getJedis().hgetAll(key);
	}

	@JedisWay
	public Map<String, String> hmget(String key, String... fields)
	{
		Map<String, String> result = new HashMap<>();
		if (fields.length != 0)
		{
			List<String> vals = getJedis().hmget(key, fields);
			int i = 0;
			for (String value : vals)
			{
				result.put(fields[i++], value);
			}
		}
		return result;
	}

	@JedisWay
	public String hmset(byte[] key, Map<byte[], byte[]> map)
	{
		if (map.isEmpty())
		{
			return "ok";
		}
		Map<byte[], byte[]> cache = new HashMap<>();
		int i = 0;
		for (Map.Entry<byte[], byte[]> m : map.entrySet())
		{
			cache.put(m.getKey(), m.getValue());
			i++;
			if (i % 100000 == 0)
			{
				getJedis().hmset(key, cache);
				cache.clear();
			}
		}
		if (!cache.isEmpty())
		{
			getJedis().hmset(key, cache);
			cache.clear();
		}
		return "ok";
	}

	@JedisWay
	public String hmset(String key, Map<String, String> map)
	{
		if (map.isEmpty())
		{
			return "ok";
		}
		if (map.size() <= 100000)
		{
			getJedis().hmset(key, map);//量不超过10w
			return "ok";
		}
		Map<String, String> cache = new HashMap<>();
		int i = 0;
		for (Map.Entry<String, String> m : map.entrySet())
		{
			cache.put(m.getKey(), m.getValue());
			i++;
			if (i % 100000 == 0)
			{
				getJedis().hmset(key, cache);
				cache.clear();
			}
		}
		if (!cache.isEmpty())
		{
			getJedis().hmset(key, cache);
			cache.clear();
		}
		return "ok";
	}

	@JedisWay
	public boolean hexist(String key, String field)
	{
		return getJedis().hexists(key, field);
	}

	@JedisWay
	public void hincrBy(final String key, final String field, final long value)
	{
		getJedis().hincrBy(key, field, value);
	}

	@JedisWay
	public Object get(String key)
	{
		return getJedis().get(key);
	}

	@JedisWay
	public byte[] get(byte[] key)
	{
		return getJedis().get(key);
	}

	@JedisWay
	public void flushall()
	{
		getJedis().flushDB();
	}

	@JedisWay
	public int hdel(String key, String... fields)
	{
		if (fields.length == 0)
		{
			return 0;
		}
		return getJedis().hdel(key, fields).intValue();
	}

	@JedisWay
	public void del(String key)
	{
		getJedis().del(key);
	}

	@JedisWay
	public void del(byte[] key)
	{
		getJedis().del(key);
	}

	@JedisWay
	public int srem(String key, String... members)
	{
		if (members.length == 0)
		{
			return 0;
		}
		return getJedis().srem(key, members).intValue();
	}

	@JedisWay
	public long hlen(String key)
	{
		return getJedis().hlen(key);
	}

	@JedisWay
	public Long rpush(final String key, final String... strings)
	{
		return getJedis().rpush(key, strings);
	}

	@JedisWay
	public String lpop(final String key)
	{
		return getJedis().lpop(key);
	}

	@JedisWay
	public Long llen(final String key)
	{
		return getJedis().llen(key);
	}

	@JedisWay
	public boolean exists(String key)
	{
		return getJedis().exists(key);
	}

	@JedisWay
	public void zadd(String key, double weight, String value)
	{
		getJedis().zadd(key, weight, value);
	}

	@JedisWay
	public void zadds(String key, Map<Double, String> valueMap)
	{
		if (MapUtils.isEmpty(valueMap))
			return;
		//插入前交换Map的key和value，以适应新的API
		getJedis().zadd(key, swapMapKeyValue(valueMap));
	}

	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	@JedisWay
	public <T> void zaddsObject(String key, Map<Double, T> inMap)
	{
		if (MapUtils.isEmpty(inMap))
			return;
		//交换Map的key和value，以适应新的API
		Map<T, Double> valueMap = swapMapKeyValue(inMap);
		Map<byte[], Double> map = new HashMap<>(valueMap.size());
		Iterator iter = valueMap.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry<T, Double> entry = (Map.Entry) iter.next();
			try
			{
				map.put(SerializeUtil.hessian2Serialize(entry.getKey()), entry.getValue());
			}
			catch (IOException e)
			{
				LOG.error("Error set java object，key:" + key, e);
			}
		}

		getJedis().zadd(key.getBytes(), map);
	}

	/**
	 * 交换valueMap中的key和value（如果value有重复，后插入的值会覆盖先插入的值）
	 * @param valueMap
	 * @return
	 */
	private <T, K> Map<T, K> swapMapKeyValue(Map<K, T> valueMap)
	{
		Map<T, K> resultMap = Maps.newHashMap();
		for (K key : valueMap.keySet())
		{
			resultMap.put(valueMap.get(key), key);
		}
		return resultMap;
	}

	@JedisWay
	public long zcard(String key)
	{
		return getJedis().zcard(key);
	}

	@JedisWay
	public Long zrank(final String key, final String member)
	{
		return getJedis().zrank(key, member);
	}

	@JedisWay
	public Long zrevrank(final String key, final String member)
	{
		return getJedis().zrevrank(key, member);
	}

	@JedisWay
	public Set<String> zrange(String key, long start, long end)
	{
		return getJedis().zrange(key, start, end);
	}

	@SuppressWarnings("unchecked")
	@JedisWay
	public <T> Set<T> zrangeObject(String key, long start, long end)
	{
		Set<byte[]> resultSet = getJedis().zrange(key.getBytes(), start, end);
		if (resultSet == null || resultSet.size() == 0)
			return null;
		Set<T> returnSet = new LinkedHashSet<T>(resultSet.size());
		for (byte[] bytes : resultSet)
		{
			try
			{
				returnSet.add((T) SerializeUtil.hessian2Deserialize(bytes));
			}
			catch (IOException e)
			{
				LOG.error("Error hessianGet object，key:" + key, e);
			}
		}
		return returnSet;
	}

	@SuppressWarnings("unchecked")
	@JedisWay
	public <T> Set<T> zrevrangeObject(String key, long start, long end)
	{
		Set<byte[]> resultSet = getJedis().zrevrange(key.getBytes(), start, end);
		if (resultSet == null || resultSet.size() == 0)
			return null;
		Set<T> returnSet = new LinkedHashSet<T>(resultSet.size());
		for (byte[] bytes : resultSet)
		{
			try
			{
				returnSet.add((T) SerializeUtil.hessian2Deserialize(bytes));
			}
			catch (IOException e)
			{
				LOG.error("Error hessianGet object，key:" + key, e);
			}
		}
		return returnSet;
	}

	@JedisWay
	public Set<String> zrevrange(String key, long start, long end)
	{
		return getJedis().zrevrange(key, start, end);
	}

	@JedisWay
	public void zremByRank(String redisKey, int start, int end)
	{
		getJedis().zremrangeByRank(redisKey, start, end);
	}

	@JedisWay
	public void zrem(final String redisKey, final String... members)
	{
		getJedis().zrem(redisKey, members);
	}

	@JedisWay
	public Set<String> zrangeByScore(final String key, final double min, final double max)
	{
		return getJedis().zrangeByScore(key, min, max);
	}

	@JedisWay
	public long publish(String channel, String msg)
	{
		long result = getJedis().publish(channel, msg);
		return result;
	}

	/**
	 * @param jedisPubSub
	 * @param channels 
	 * redis订阅方法：直接从redis连接池中获取连接，不走ThreadLocal方式，不添加@JedisWay注解（即不自动释放）
	 */
	public void subscribe(JedisPubSub jedisPubSub, String... channels)
	{
		while (!Thread.currentThread().isInterrupted())
		{
			Jedis jedis = null;
			try
			{
				jedis = baseRedisSentinel.getJedisWithOutThreadLocal();
				if (jedis != null)
				{
					jedis.subscribe(jedisPubSub, channels);
				}
				else
				{
					LOG.fatal("can not get jedis conn when subscribe!");
				}
			}
			catch (Throwable e)
			{
				LOG.error("refresh initialize redis subscribe error", e);
				if (jedis != null)
					jedis.close();
			}
			finally
			{
				ThreadUtil.sleep(5000);//如果走到finally，先暂停5秒钟，再重新订阅
			}
		}
	}

	/**
	 * @param key   : String类型的key，方法内部自动转为byte
	 * @param value ： object对象，方法内部利用hessian2自动转为byte存储
	 * @return
	 * @Discription 对对象进行序列化存储
	 */
	@JedisWay
	public String hessianSet(String key, Object value)
	{
		if (null == key || null == value)
		{
			return null;
		}
		try
		{
			byte[] keyBytes = key.getBytes("UTF-8");
			byte[] valueBytes = SerializeUtil.hessian2Serialize(value);
			String result = getJedis().set(keyBytes, valueBytes);
			return result;
		}
		catch (UnsupportedEncodingException e)
		{
			LOG.error("Error set java object，key:" + key, e);
			return null;
		}
		catch (IOException e)
		{
			LOG.error("Error set java object，key:" + key, e);
			return null;
		}
	}

	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	@JedisWay
	public void batchHessianSetex(Map<String, Object> map, int seconds)
	{
		if (map == null || map.size() <= 0)
			return;
		Iterator iterator = map.entrySet().iterator();
		try
		{
			while (iterator.hasNext())
			{
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				if (null == key || null == value)
				{
					continue;
				}
				byte[] keyBytes;
				keyBytes = key.getBytes("UTF-8");
				byte[] valueBytes = SerializeUtil.hessian2Serialize(value);
				getJedis().setex(keyBytes, seconds, valueBytes);
			}
		}
		catch (UnsupportedEncodingException e)
		{
			LOG.error("Error set java object", e);
			return;
		}
		catch (IOException e)
		{
			LOG.error("Error set java object", e);
			return;
		}
	}

	/**
	 * @param key     String类型的key，方法内部自动转为byte
	 * @param value   object对象，方法内部利用hessian2自动转为byte存储
	 * @param seconds 超时时间
	 * @return
	 * @Discription 对对象进行序列化存储，设置超时时间
	 */
	@JedisWay
	public String hessianSetex(String key, Object value, int seconds)
	{
		if (null == key || null == value)
		{
			return null;
		}
		byte[] keyBytes;
		try
		{
			keyBytes = key.getBytes("UTF-8");
			byte[] valueBytes = SerializeUtil.hessian2Serialize(value);
			String result = getJedis().setex(keyBytes, seconds, valueBytes);
			return result;
		}
		catch (UnsupportedEncodingException e)
		{
			LOG.error("Error set java object，key:" + key, e);
			return null;
		}
		catch (IOException e)
		{
			LOG.error("Error set java object，key:" + key, e);
			return null;
		}
	}

	/**
	 * @param key string类型的原始参数key
	 * @return
	 * @Discription 获取缓存中的存储对象
	 */
	@JedisWay
	public Object hessianGet(String key)
	{
		if (null == key)
		{
			return null;
		}
		try
		{
			byte[] keyBytes = key.getBytes("UTF-8");
			byte[] valueBytes = getJedis().get(keyBytes);
			if (null == valueBytes)
			{
				return null;
			}
			return SerializeUtil.hessian2Deserialize(valueBytes);
		}
		catch (UnsupportedEncodingException e)
		{
			LOG.error("Error hessianGet object，key:" + key, e);
			return null;
		}
		catch (IOException e)
		{
			LOG.error("Error hessianGet object，key:" + key, e);
			return null;
		}
	}

	/**
	 * @param key
	 * @Discription 删除缓存中的对象
	 */
	@JedisWay
	public void hessianDel(String key)
	{
		if (null == key)
		{
			return;
		}
		try
		{
			byte[] keyBytes = key.getBytes("UTF-8");
			if (getJedis().exists(keyBytes))
			{
				getJedis().del(keyBytes);
			}
		}
		catch (UnsupportedEncodingException e)
		{
			LOG.error("Error hessianGet object，key:" + key, e);
		}
	}

	@JedisWay
	public long scard(String key)
	{
		if (StringUtils.isBlank(key))
		{
			return 0;
		}
		return getJedis().scard(key);
	}
}
