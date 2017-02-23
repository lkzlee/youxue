package com.youxue.core.service.order.dto;

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
	private String payType = "0";
	private String openId; //微信内嵌js支付时，该参数必填
	private AddTradeItemDto[] orderList;

	public String getPayType()
	{
		return payType;
	}

	public void setPayType(String payType)
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

	public String getOpenId()
	{
		return openId;
	}

	public void setOpenId(String openId)
	{
		this.openId = openId;
	}

	@Override
	public String toString()
	{
		return "AddTradeOrderDto [payType=" + payType + ", openId=" + openId + ", orderList="
				+ Arrays.toString(orderList) + "]";
	}
}
