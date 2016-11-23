package com.youxue.pc.order.dto;

import java.io.Serializable;
import java.util.Arrays;

/***
 * 下单相关参数
 * @author lkzlee
 *
 */
public class AddTradeOrderDto implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer payType = 0;
	private AddTradeItemDto[] orderList;

	public Integer getPayType()
	{
		return payType;
	}

	public void setPayType(Integer payType)
	{
		this.payType = payType;
	}

	public AddTradeItemDto[] getOrderList()
	{
		return orderList;
	}

	public void setOrderList(AddTradeItemDto[] orderList)
	{
		this.orderList = orderList;
	}

	@Override
	public String toString()
	{
		return "AddTradeOrderDto [payType=" + payType + ", orderList=" + Arrays.toString(orderList) + "]";
	}
}
