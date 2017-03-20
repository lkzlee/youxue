package com.youxue.pc.task;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.lkzlee.pay.utils.DateUtil;
import com.youxue.core.dao.OrderDao;
import com.youxue.core.dao.UserInfoDao;
import com.youxue.core.vo.OrderVo;
import com.youxue.core.vo.UserInfoVo;

@Service
public class OrderHandlerTask
{
	private final static Log log = LogFactory.getLog(OrderHandlerTask.class);

	@Resource
	private OrderDao orderDao;
	@Resource
	private UserInfoDao userInfoDao;
	private static final ExecutorService exec = Executors.newSingleThreadExecutor();

	@PostConstruct
	public void init()
	{
		log.info("----订单处理 初始化.......");
		exec.execute(new OrderHanderRunnable());
		log.info("@----订单处理  初始化完成.....");

	}

	class OrderHanderRunnable implements Runnable
	{

		@Override
		public void run()
		{
			while (true)
			{
				try
				{
					List<OrderVo> orderList = orderDao.selectUnPayOrder();
					List<OrderVo> orderList2 = orderDao.selectUnfinishedOrder();
					if (CollectionUtils.isEmpty(orderList) && CollectionUtils.isEmpty(orderList2))
					{
						log.info("要进行处理的订单为空，额外休息20分钟.....");
						Thread.sleep(20l * 60 * 1000); //休息20分钟
						continue;
					}
					if (!CollectionUtils.isEmpty(orderList))
					{
						log.info("要进行处理的未支付订单，orderSize=" + orderList.size());
						for (OrderVo r : orderList)
						{
							r.setStatus(OrderVo.DELETED); //更改订单为已删除已取消状态
							r.setUpdateTime(DateUtil.getCurrentTimestamp());
							orderDao.updateByPrimaryKeySelective(r);
						}
					}
					if (!CollectionUtils.isEmpty(orderList2))
					{
						log.info("要进行处理的待出行-->已完成订单，orderList2=" + orderList2.size());
						for (OrderVo r : orderList2)
						{
							r.setStatus(OrderVo.DONE); //更改订单为已完成状态
							r.setUpdateTime(DateUtil.getCurrentTimestamp());
							orderDao.updateByPrimaryKeySelective(r);
							UserInfoVo user = userInfoDao.selectByPrimaryKey(r.getAccountId());
							BigDecimal spend = user.getSpend() == null ? BigDecimal.ZERO : user.getSpend();
							user.setSpend(spend.add(r.getPayPrice()));
							userInfoDao.updateByPrimaryKeySelective(user);
						}
					}
					Thread.sleep(10l * 60 * 1000); //休息10分钟
				}
				catch (Exception e)
				{
					log.fatal("微信查询退款异常，请检查，msg:" + e.getMessage(), e);
				}
			}
		}
	}
}
