package com.youxue.pc.uc.dto;

import java.io.Serializable;
import java.util.List;

import com.youxue.core.vo.OrderDetailVo;

public class OrderItemDto implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String logicOrderId;
	private Integer payStatus;
	private List<OrderDetailVo> orderList;

	public String getLogicOrderId()
	{
		return logicOrderId;
	}

	public void setLogicOrderId(String logicOrderId)
	{
		this.logicOrderId = logicOrderId;
	}

	public Integer getPayStatus()
	{
		return payStatus;
	}

	public void setPayStatus(Integer payStatus)
	{
		this.payStatus = payStatus;
	}

	public List<OrderDetailVo> getOrderList()
	{
		return orderList;
	}

	public void setOrderList(List<OrderDetailVo> orderList)
	{
		this.orderList = orderList;
	}

	@Override
	public String toString()
	{
		return "OrderItemDto [logicOrderId=" + logicOrderId + ", payStatus=" + payStatus + ", orderList=" + orderList
				+ "]";
	}
}
