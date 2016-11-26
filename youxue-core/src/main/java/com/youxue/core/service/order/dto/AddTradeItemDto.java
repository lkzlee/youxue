package com.youxue.core.service.order.dto;

import java.io.Serializable;

import com.youxue.core.vo.OrderPersonVo;
import com.youxue.core.vo.OrderVo;

/***
 * 下单相关参数
 * @author lkzlee
 *
 */
public class AddTradeItemDto extends OrderVo implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderPersonVo[] outPersonList;

	public OrderPersonVo[] getOutPersonList()
	{
		return outPersonList;
	}

	public void setOutPersonList(OrderPersonVo[] outPersonList)
	{
		this.outPersonList = outPersonList;
	}

}
