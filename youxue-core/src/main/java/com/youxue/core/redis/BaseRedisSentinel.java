package com.youxue.core.redis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class BaseRedisSentinel
{
	private static Log log = LogFactory.getLog(BaseRedisSentinel.class);
	/**使用threadLocal避免释放的时候传递jedis对象*/
	private static ThreadLocal<Jedis> jedisLocal = new ThreadLocal<Jedis>();

	private volatile JedisSentinelPool redisSentinelPool;
	private String sentinels;
	private String masterName;
	private String password;
	private String database;//连接sentinel的密码
	private String timeout;//最长有效等待时间

	private JedisPoolConfig config;//jedis 配置

	/**
	 * spring注入的时候立即执行初始化
	 */
	public void initPool()
	{
		/**sentinel服务地址*/
		if (StringUtils.isBlank(sentinels))
		{
			log.fatal("sentinels config is blank ,pls check!sentinels:" + sentinels);
			return;
		}
		String[] sentinelArr = sentinels.split("\\,");

		/**初始化jedis配置*/
		if (config == null)
		{
			config = new JedisPoolConfig();
			config.setMaxTotal(3030);// Maximum active connections to Redis instance
			config.setMaxIdle(30);// Number of connections to Redis that just sit there and do nothing
			config.setMinIdle(10);//// Minimum number of idle connections to Redis - these can be seen as always open and ready to serve
			config.setMaxWaitMillis(2000);//ms
		}
		final Set<String> sentinelsSet = new HashSet<String>();
		if (null == sentinelArr)
		{
			sentinelsSet.add("redis1.youxue.com:26379");
			sentinelsSet.add("redis2.youxue.com:26379");
		}
		else
		{
			for (int i = 0; i < sentinelArr.length; i++)
			{
				sentinelsSet.add(sentinelArr[i]);
			}
		}
		if (null == redisSentinelPool)
		{
			redisSentinelPool = new JedisSentinelPool(masterName, sentinelsSet, config, Integer.valueOf(timeout),
					password);
		}
		/**添加日志用于判断*/
		String curDate = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(new Date());//DateUtil.formatDate(new Date(),"yyyy-MM-ddHH：mm：ss");
		log.info("Monitoring current master,CurrentTime:" + curDate + "------"
				+ redisSentinelPool.getCurrentHostMaster());
	}

	/**destroy后pool将关闭，不能被使用*/
	public void destroy()
	{
		if (null != redisSentinelPool)
		{
			try
			{
				redisSentinelPool.destroy();
			}
			catch (Exception e)
			{
				log.error("exception while destroy redisSentinelPool", e);
			}
		}
	}

	/**
	 * 获取jedis
	 * @return
	 */
	public Jedis getJedis()
	{
		Jedis jedis = null;
		try
		{
			/**去本线程中的jedis*/
			jedis = jedisLocal.get();
			if (null != jedis)
			{
				try
				{
					// 取出来后执行ping检查下是否依然存活
					if (jedis.isConnected())
					{
						return jedis;
					}
				}
				catch (Exception e)
				{
					releaseBrokenJedis();
				}
			}
			if (null == redisSentinelPool)
			{
				this.initPool();
			}
			jedis = redisSentinelPool.getResource();
			jedisLocal.set(jedis);// 设置本线程中的jedis
		}
		catch (Exception e)
		{
			if (jedis != null)
				jedis.close();
			if (null != jedisLocal.get())
			{
				jedisLocal.remove();
			}
			log.fatal("Could not get a resource from the pool, pls check the host and port settings", e);
		}
		return jedis;
	}

	/**
	 * 屏蔽threadLocal，直接从连接池中获取jedis连接
	 * @return
	 */
	public Jedis getJedisWithOutThreadLocal()
	{
		Jedis jedis = null;
		try
		{
			jedis = redisSentinelPool.getResource();
		}
		catch (Exception e)
		{
			if (null != jedis && null != redisSentinelPool)
			{
				jedis.close();
			}
			log.fatal("Could not get a resource from the pool, pls check the host and port settings", e);
		}
		return jedis;
	}

	/**
	 * 释放
	 */
	public void releaseJedis()
	{
		Jedis jedis = jedisLocal.get();
		if (null != jedis && null != redisSentinelPool)
		{
			if (jedis.isConnected())
			{
				jedis.close();
			}
		}
		jedisLocal.remove();
	}

	/**
	 * 释放损坏资源
	 */
	public void releaseBrokenJedis()
	{
		Jedis jedis = jedisLocal.get();
		if (null != jedis && null != redisSentinelPool)
		{
			jedis.close();
		}
		jedisLocal.remove();
	}

	/**
	 * 新API,释放jedis（不需要考虑异常）
	 * @param jedis
	 */
	public void closeJedis()
	{
		Jedis jedis = jedisLocal.get();
		if (null != jedis)
		{
			jedis.close();
		}
		jedisLocal.remove();
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setConfig(JedisPoolConfig config)
	{
		this.config = config;
	}

	public String getSentinels()
	{
		return sentinels;
	}

	public void setSentinels(String sentinels)
	{
		this.sentinels = sentinels;
	}

	public String getMasterName()
	{
		return masterName;
	}

	public void setMasterName(String masterName)
	{
		this.masterName = masterName;
	}

	public String getPassword()
	{
		return password;
	}

	public JedisPoolConfig getConfig()
	{
		return config;
	}

	public void setDatabase(String database)
	{
		this.database = database;
	}

	public String getTimeout()
	{
		return timeout;
	}

	public void setTimeout(String timeout)
	{
		this.timeout = timeout;
	}

	public String getDatabase()
	{
		return database;
	}

	public static void main(String[] args) throws InterruptedException
	{
		final BaseRedisSentinel baseRedis = new BaseRedisSentinel();
		baseRedis.setSentinels("127.0.0.1:5555,127.0.0.1:6666");
		baseRedis.setDatabase("0");
		baseRedis.setTimeout("10000");
		baseRedis.setMasterName("master");
		baseRedis.setPassword("1qaz2wsx!~");
		//		baseRedis.setPassword("123");

		baseRedis.initPool();
		for (int i = 0; i < 20; i++)
		{
			Jedis jedis = baseRedis.getJedis();
			jedis.set(String.valueOf(i), String.valueOf(i));
			System.out.println("set " + i);
			TimeUnit.MILLISECONDS.sleep(500);
		}
	}

	public void releaseOriginJedis(Jedis jedis)
	{
		if (null != jedis && null != redisSentinelPool)
		{
			if (jedis.isConnected())
			{
				jedis.close();
			}
		}
	}

	/**
	 * 释放损坏资源
	 */
	public void releaseOriginBrokenJedis(Jedis jedis)
	{
		if (null != jedis && null != redisSentinelPool)
		{
			jedis.close();
		}
	}

	/**
	 * 新API,释放传入的jedis（不需要考虑异常）
	 * @param jedis
	 */
	public void closeOriginJedis(Jedis jedis)
	{
		if (null != jedis)
		{
			jedis.close();
		}
	}
}
