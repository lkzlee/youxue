package com.youxue.core.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

/**
 * 
 * JedisAop：在执行完redis相关操作后，释放redis连接
 */
@Aspect
public class JedisAop
{
	private Log logger = LogFactory.getLog(this.getClass());

	//@Resource(name = "baseRedis") 改为通过手工bean注入的方式
	private BaseRedisSentinel baseRedisSentinel;

	public BaseRedisSentinel getBaseRedisSentinel()
	{
		return baseRedisSentinel;
	}

	public void setBaseRedisSentinel(BaseRedisSentinel baseRedisSentinel)
	{
		this.baseRedisSentinel = baseRedisSentinel;
	}

	/**
	 * 
	 * @param jp
	 */
	// @Around("execution(* com.netease.lottery.redis.interfaces.*.*(..)) && @annotation(com.netease.lottery.redis.RedisWay)")
	public Object around(ProceedingJoinPoint pjp) throws Throwable
	{
		// MethodSignature methodSignature = (MethodSignature)
		// pjp.getSignature();
		// Method method = methodSignature.getMethod();
		// Object target = pjp.getTarget();
		// Stack<String> local = hierarchyLocal.get();
		// if (null == local || local.isEmpty()) {
		// local = new Stack<String>();
		// local.push("1");
		// hierarchyLocal.set(local);
		// } else {
		// local.push("1");
		// }
		// 执行目标命令
		Object returnVal = pjp.proceed();
		// local = hierarchyLocal.get();
		// if (null != local && !local.isEmpty()) {
		// local.pop();
		// if (local.empty()) {
		baseRedisSentinel.releaseJedis();
		// }
		// }
		return returnVal;
	}

	@After("@annotation(com.netease.lottery.base.common.redis.JedisWay) || @within(com.netease.lottery.base.common.redis.JedisWay)")
	public void doAfter(JoinPoint jp)
	{
		//logger.info("JedisAop Release Jedis after used!");
		baseRedisSentinel.releaseJedis();
	}

	//Handle broken client after exception by dujiepeng
	@AfterThrowing("@annotation(com.netease.lottery.base.common.redis.JedisWay) || @within(com.netease.lottery.base.common.redis.JedisWay)")
	public void afterThrowing()
	{
		logger.info("Release brokenJedis after Throwing Exception!");
		baseRedisSentinel.releaseBrokenJedis();
		//        logger.info("Exception occured:",ex);  
	}
}
