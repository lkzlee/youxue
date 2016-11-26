package com.youxue.pc.order.dto;

import com.youxue.core.common.BaseResponseDto;

/***
 * 下单返回参数
 * @author liyongchao
 *
 */
public class AddTradeResonseDto extends BaseResponseDto
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String payUrl;

	public String getPayUrl()
	{
		return payUrl;
	}

	public void setPayUrl(String payUrl)
	{
		this.payUrl = payUrl;
	}

	@Override
	public String toString()
	{
		return "AddTradeResonseDto [payUrl=" + payUrl + "],super:" + super.toString();
	}
}
