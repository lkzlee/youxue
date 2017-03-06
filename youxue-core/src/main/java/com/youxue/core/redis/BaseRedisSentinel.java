package com.youxue.core.redis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class BaseRedisSentinel
{
	private static Log log = LogFactory.getLog(BaseRedisSentinel.class);
	/**使用threadLocal避免释放的时候传递jedis对象*/
	private static ThreadLocal<Jedis> jedisLocal = new ThreadLocal<Jedis>();

	private volatile JedisPool redisPool;
	private String server;
	private String port;
	private String password;
	private String timeout;//最长有效等待时间

	public String getServer()
	{
		return server;
	}

	public void setServer(String server)
	{
		this.server = server;
	}

	public String getPort()
	{
		return port;
	}

	public void setPort(String port)
	{
		this.port = port;
	}

	private JedisPoolConfig config;//jedis 配置

	/**
	 * spring注入的时候立即执行初始化
	 */
	public void initPool()
	{
		/**sentinel服务地址*/
		if (StringUtils.isBlank(server) || StringUtils.isBlank(port))
		{
			log.fatal("servers config is blank ,pls check!servers:" + server + ",port:" + port);
			return;
		}

		/**初始化jedis配置*/
		if (config == null)
		{
			config = new JedisPoolConfig();
			config.setMaxTotal(3030);// Maximum active connections to Redis instance
			config.setMaxIdle(30);// Number of connections to Redis that just sit there and do nothing
			config.setMinIdle(10);//// Minimum number of idle connections to Redis - these can be seen as always open and ready to serve
			config.setMaxWaitMillis(2000);//ms
		}
		if (null == redisPool)
		{
			if (StringUtils.isNotBlank(password))
			{
				redisPool = new JedisPool(config, server, Integer.valueOf(port), Integer.valueOf(timeout), password);
			}
			else
			{
				redisPool = new JedisPool(config, server, Integer.valueOf(port), Integer.valueOf(timeout));
			}
		}
		/**添加日志用于判断*/
		String curDate = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(new Date());//DateUtil.formatDate(new Date(),"yyyy-MM-ddHH：mm：ss");
		log.info("######## redis pool init finished,CurrentTime:" + curDate);
	}

	/**destroy后pool将关闭，不能被使用*/
	public void destroy()
	{
		if (null != redisPool)
		{
			try
			{
				redisPool.destroy();
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
			if (null == redisPool)
			{
				this.initPool();
			}
			jedis = redisPool.getResource();
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
			jedis = redisPool.getResource();
		}
		catch (Exception e)
		{
			if (null != jedis && null != redisPool)
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
		if (null != jedis && null != redisPool)
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
		if (null != jedis && null != redisPool)
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

	public String getPassword()
	{
		return password;
	}

	public JedisPoolConfig getConfig()
	{
		return config;
	}

	public String getTimeout()
	{
		return timeout;
	}

	public void setTimeout(String timeout)
	{
		this.timeout = timeout;
	}

	public String getServers()
	{
		return server;
	}

	public void setServers(String servers)
	{
		this.server = servers;
	}

	public static void main(String[] args) throws InterruptedException
	{
		Jedis jedis = new Jedis("101.200.148.203", 6379);
		jedis.auth("qinggu");
		jedis.set("test1", "test");
		System.out.println(jedis.get("test1"));

		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(3030);// Maximum active connections to Redis instance
		config.setMaxIdle(30);// Number of connections to Redis that just sit there and do nothing
		config.setMinIdle(10);//// Minimum number of idle connections to Redis - these can be seen as always open and ready to serve
		config.setMaxWaitMillis(2000);//ms
		JedisPool redisPool = new JedisPool(config, "101.200.148.203", 6379, 20000, "qinggu");
		for (int i = 0; i < 20; i++)
		{
			jedis = redisPool.getResource();
			jedis.set(String.valueOf(i), String.valueOf(i));
			System.out.println("set " + i);
			TimeUnit.MILLISECONDS.sleep(500);
			jedis.close();
		}
	}

	public void releaseOriginJedis(Jedis jedis)
	{
		if (null != jedis && null != redisPool)
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
		if (null != jedis && null != redisPool)
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
